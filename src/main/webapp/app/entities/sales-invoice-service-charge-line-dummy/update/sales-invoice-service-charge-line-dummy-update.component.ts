import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import {
  ISalesInvoiceServiceChargeLineDummy,
  NewSalesInvoiceServiceChargeLineDummy,
} from '../sales-invoice-service-charge-line-dummy.model';
import { SalesInvoiceServiceChargeLineDummyService } from '../service/sales-invoice-service-charge-line-dummy.service';
import { SalesInvoiceServiceChargeLineDummyFormService } from './sales-invoice-service-charge-line-dummy-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-service-charge-line-dummy-update',
  templateUrl: './sales-invoice-service-charge-line-dummy-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SalesInvoiceServiceChargeLineDummyUpdateComponent implements OnInit {
  isSaving = false;
  showCodeField: boolean = true;
  salesInvoiceServiceChargeLines: ISalesInvoiceServiceChargeLineDummy[] = [];
  filteredItems: IBillingserviceoption[][] = [];
  protected salesInvoiceServiceChargeLineDummyService = inject(SalesInvoiceServiceChargeLineDummyService);
  protected salesInvoiceServiceChargeLineDummyFormService = inject(SalesInvoiceServiceChargeLineDummyFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  @Output() totalUpdated = new EventEmitter<number>(); // Emit total to parent
  protected vehicletypesService = inject(VehicletypeService);
  editForm: FormGroup = new FormGroup({
    serviceChargeLines: new FormArray([]),
  });
  typeid: number = 0;
  vehicletypes: IVehicletype[] = [];
  get serviceChargeLinesArray(): FormArray {
    return this.editForm.get('serviceChargeLines') as FormArray;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesInvoiceServiceChargeLines }) => {
      if (salesInvoiceServiceChargeLines && salesInvoiceServiceChargeLines.length > 0) {
        this.salesInvoiceServiceChargeLines = salesInvoiceServiceChargeLines;
        this.updateForm(salesInvoiceServiceChargeLines);
      } else {
        this.addServiceChargeLine();
      }
    });
    this.loadVehicleTypes();
  }
  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      console.log('Loaded Vehicle Types:', this.vehicletypes); // Display the loaded vehicle types in the console
    });
  }
  allBillingServiceOptions: any[] = [];

  onDropdownChange(event: Event): void {
    // Get the selected dropdown value (the id of the selected vehicle type)
    const selectedValue = (event.target as HTMLSelectElement).value;
    const typeid = Number(selectedValue); // Parse the selected value to a number

    // Log the selected typeid to the console
    console.log('Selected Vehicle Type ID:', typeid);
    this.typeid = typeid;
    // Clear previous data before loading new data
    this.allBillingServiceOptions = [];

    // Make the API call to get the billingserviceoption ids
    this.salesInvoiceServiceChargeLineDummyService.getElementsByID(typeid).subscribe({
      next: response => {
        // Log the full response to understand its structure
        console.log('API Response:', response);

        // Check if response.body is an array and contains the 'billingserviceoption' field
        if (response.body && Array.isArray(response.body)) {
          // Extract the 'billingserviceoption' ids from the response
          const billingserviceoptionIds = response.body.map((item: any) => item.billingserviceoptionid);

          // Log the billingserviceoption ids to the console
          console.log('Billing Service Option IDs:', billingserviceoptionIds);

          // Now, use the getbillingid function to fetch details for each billingserviceoptionid
          billingserviceoptionIds.forEach(id => {
            this.salesInvoiceServiceChargeLineDummyService.getbillingid(id).subscribe({
              next: billingResponse => {
                // Log the detailed response for each billingserviceoption
                console.log('Billing Service Option Details for ID ' + id, billingResponse.body);

                // Add the billing service option details to the allBillingServiceOptions array
                if (billingResponse.body) {
                  this.allBillingServiceOptions.push(billingResponse.body);
                }
                console.log('All Billing Service Options:', this.allBillingServiceOptions);
              },

              error: err => {
                console.error('Error fetching details for Billing Service Option ID ' + id, err);
              },
            });
          });
        } else {
          console.error('Unexpected API response format:', response);
        }
      },
      error: err => {
        console.error('API Error:', err); // Log if there's an error
      },
    });
  }

  addToTable() {
    if (this.selectedServices.length === 0) {
      return; // No selected services, nothing to add
    }

    const existingRows = this.serviceChargeLinesArray.controls.length;
    console.log('typeid:', this.typeid);

    // Temporary map to store responses
    const serviceResponses = new Map<number, any>();

    // Iterate over selected services
    this.selectedServices.forEach((service, index) => {
      console.log('Selected service ID:', service.id);

      console.log(`Sending API request to fetch billing values with params: service.id=${service.id}, typeid=${this.typeid}`);

      this.salesInvoiceServiceChargeLineDummyService.biliingvalues(service.id, this.typeid).subscribe(response => {
        console.log('API Response:', response);

        const billingValues = response.body; // Assuming the response contains the data in 'body'
        console.log('Billing values:', billingValues);

        serviceResponses.set(service.id, billingValues && billingValues.length > 0 ? billingValues[0].value : '');

        if (index === 0 && existingRows > 0) {
          // Update first row if it exists
          const firstRow = this.serviceChargeLinesArray.controls[0];
          firstRow.get('serviceName')?.setValue(service.servicename);
          firstRow.get('iqd')?.setValue(service.id);
          firstRow.get('value')?.setValue(serviceResponses.get(service.id));
        } else {
          // Add new row with fetched value
          this.serviceChargeLinesArray.push(
            this.fb.group({
              serviceName: [service.servicename],
              value: [serviceResponses.get(service.id)], // Set fetched value
              isCustomerService: [false],
              id: [service.id],
            }),
          );
        }
      });
    });

    // Reset selected services after processing
    this.selectedServices = [];
  }

  selectedServices: { id: number; servicename: string }[] = [];

  onCheckboxChange(event: any, servicename: string, id: number) {
    if (event.target.checked) {
      // Add the service with its id to the selected services list
      this.selectedServices.push({ id, servicename });
    } else {
      // Remove the service with its id from the selected services list
      this.selectedServices = this.selectedServices.filter(service => service.id !== id);
    }

    // Log selected services to the console
    console.log('Selected Services:', this.selectedServices);
  }

  onDropdownChan1ge(event: Event): number {
    return parseInt((event.target as HTMLSelectElement).value, 10);
  }

  previousState(): void {
    window.history.back();
  }

  onItemCodeInput(event: Event, index: number): void {
    // Type assertion: Treat event target as HTMLInputElement
    const inputElement = <HTMLInputElement>event.target;
    const value = inputElement.value; // Get the value typed by the user

    // Log the input value to the console when a user types
    console.log('User typed:', value);

    if (!value) {
      this.filteredItems[index] = []; // Clear suggestions if input is empty
      return;
    }

    console.log('Fetching items for value:', value);
    const dropdownElement = document.querySelector('.form-select') as HTMLSelectElement;
    if (!dropdownElement) {
      console.error('Dropdown not found');
      return;
    }

    // Get the selected dropdown value

    this.salesInvoiceServiceChargeLineDummyService
      .getElementsByUserInputCode(value) // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IBillingserviceoption[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.servicename); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: error => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }
  calculateTotal(): void {
    const total = this.serviceChargeLinesArray.controls.map(control => control.get('value')?.value || 0).reduce((acc, val) => acc + val, 0);

    console.log('Total Value:', total);
    this.totalUpdated.emit(total); // Emit total to parent
  }

  save(inid: number): void {
    this.isSaving = true;

    const serviceChargeLines = this.serviceChargeLinesArray.value.map((line: any, index: number) => ({
      ...line,
      invoiceId: inid, // Assign invoice ID
      lineId: line.lineid ?? index + 1, // Ensure unique line ID
      optionId: index + 1,
    }));

    console.log('Modified sales invoice lines:', serviceChargeLines);

    const requests: Observable<HttpResponse<ISalesInvoiceServiceChargeLineDummy>>[] = serviceChargeLines.map(
      (line: ISalesInvoiceServiceChargeLineDummy | NewSalesInvoiceServiceChargeLineDummy) => {
        console.log('Processing line:', line);
        console.log('Line ID:', line?.id); // Use optional chaining to avoid errors

        return line.id
          ? this.salesInvoiceServiceChargeLineDummyService.update(line)
          : this.salesInvoiceServiceChargeLineDummyService.create(line as NewSalesInvoiceServiceChargeLineDummy);
      },
    );

    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Handle API error
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(salesInvoiceServiceChargeLines: ISalesInvoiceServiceChargeLineDummy[]): void {
    this.serviceChargeLinesArray.clear();
    salesInvoiceServiceChargeLines.forEach(line => {
      this.serviceChargeLinesArray.push(
        this.salesInvoiceServiceChargeLineDummyFormService.createSalesInvoiceServiceChargeLineDummyFormGroup(line),
      );
    });
  }

  addServiceChargeLine(): void {
    const newDummy = this.salesInvoiceServiceChargeLineDummyFormService.createSalesInvoiceServiceChargeLineDummyFormGroup();
    this.serviceChargeLinesArray.push(newDummy);
  }

  removeServiceChargeLine(index: number): void {
    this.serviceChargeLinesArray.removeAt(index);
  }
}
