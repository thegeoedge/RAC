import { Component, EventEmitter, OnInit, Input, Output, inject, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import { ISalesInvoiceServiceChargeLine, NewSalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';
import {
  SalesInvoiceServiceChargeLineFormGroup,
  SalesInvoiceServiceChargeLineFormService,
} from './sales-invoice-service-charge-line-form.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { MessageCommunicationService } from 'app/core/util/message.communication.service';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';
@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-service-charge-line-update',
  templateUrl: './sales-invoice-service-charge-line-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SalesInvoiceServiceChargeLineUpdateComponent implements OnInit {
  isSaving = false;
  showCodeField: boolean = true;
  salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine[] = [];
  filteredItems: IBillingserviceoption[][] = [];
  protected salesInvoiceServiceChargeLineService = inject(SalesInvoiceServiceChargeLineService);
  protected salesInvoiceServiceChargeLineFormService = inject(SalesInvoiceServiceChargeLineFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  messagenotify = inject(MessageCommunicationService);
  @Output() totalUpdated = new EventEmitter<number>(); // Emit total to parent
  protected vehicletypesService = inject(VehicletypeService);
  @Input() fetchedServices: any;
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormGroup = new FormGroup({
    serviceChargeLines: new FormArray([]),
  });
  salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  typeid: number = 0;
  totalfetch: number = 0;
  vehicletypes: IVehicletype[] = [];
  get serviceChargeLinesArray(): FormArray {
    return this.editForm.get('serviceChargeLines') as FormArray;
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['fetchedServices'] && this.fetchedServices) {
      // Loop through the fetchedItems array and add each item to the form array
      this.fetchedServices.forEach((item: any) => {
        this.addItemToFormArray(item);
      });
      this.salesInvoiceLinesService.updateSalesInvoiceLines(this.serviceChargeLinesArray);
      console.log('Fetched Items on Change:', this.serviceChargeLinesArray); // Log fetched items
    }
  }
  addItemToFormArray(item: any): void {
    // Create a new form group for the item
    const newItem = this.fb.group({
      serviceName: [item.itemname],
      value: [item.sellingprice],
      isCustomerService: [false],
    });

    // Add the new form group to the form array
    this.serviceChargeLinesArray.push(newItem);
    this.totalvalue(newItem);
  }
  ngOnInit(): void {
    this.messagenotify.notificationAnnounced$.subscribe(message => {
      if (message.topic === 'DELETE_ITEM') {
        const itemId = message.message;
        this.removeInvoiceLinecode(itemId);
      }
    });
    console.log('Total fetched valuezzzzzzzzzzzzz:', this.serviceChargeLinesArray);
    this.activatedRoute.data.subscribe(({ salesInvoiceServiceChargeLines }) => {
      if (salesInvoiceServiceChargeLines && salesInvoiceServiceChargeLines.length > 0) {
        this.salesInvoiceServiceChargeLine = salesInvoiceServiceChargeLines;
        this.updateForm(salesInvoiceServiceChargeLines);
      }
    });
    this.loadVehicleTypes();
  }
  totalvalue(formGroup: FormGroup): void {
    const value = formGroup.get('value');

    // Update the line total when the value changes
    value?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal());

    // Initialize the line total when the form is added
    this.updateLineTotal();
  }

  updateLineTotal(): void {
    // Calculate the total by summing up all values in the serviceChargeLines array
    const total = this.serviceChargeLinesArray.controls
      .map(control => control.get('value')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    // Emit the total to the parent component
    this.totalUpdated.emit(total);
    console.log('Updated Total ssssssssssser:', total); // Log the updated total
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
    this.salesInvoiceServiceChargeLineService.getElementsByID(typeid).subscribe({
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
            this.salesInvoiceServiceChargeLineService.getbillingid(id).subscribe({
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
      return;
    }

    const existingRows = this.serviceChargeLinesArray.controls.length;
    console.log('typeid:', this.typeid);

    let completedRequests = 0;
    let totalFetchedValue = 0;

    this.selectedServices.forEach((service, index) => {
      console.log('Selected service ID:', service.id);
      console.log(`Sending API request to fetch billing values with params: service.id=${service.id}, typeid=${this.typeid}`);

      this.salesInvoiceServiceChargeLineService.biliingvalues(service.id, this.typeid).subscribe(response => {
        console.log('API Responseeeeeee:', response);

        const billingValues = response.body;
        const fetchedValue = billingValues && billingValues.length > 0 ? billingValues[0].value : 0;
        totalFetchedValue += fetchedValue ?? 0;

        // Always add a new row instead of modifying existing ones
        this.serviceChargeLinesArray.push(
          this.fb.group({
            serviceName: [service.servicename],
            value: [fetchedValue],
            isCustomerService: [false],
            id: [service.id],
          }),
        );

        completedRequests++;
        this.salesInvoiceLinesService.updateSalesInvoiceLines(this.serviceChargeLinesArray);
        this.totalfetch = totalFetchedValue;
        console.log('Current total fetched value:', this.totalfetch);

        this.calculateTotal(this.totalfetch);
      });
    });

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
    const formArray = new FormArray(
      this.selectedServices.map(service =>
        this.fb.group({
          id: [service.id],
          servicename: [service.servicename],
        }),
      ),
    );
    // this.salesInvoiceLinesService.updateSalesInvoiceCommonLines(formArray);
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

    this.salesInvoiceServiceChargeLineService
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
  removeInvoiceLinecode(code: any): void {
    // Find index of the form group in the array matching the lineId
    const index = this.serviceChargeLinesArray.controls.findIndex(control => {
      const group = control as FormGroup;
      return group.get('id')?.value === code || group.get('serviceName')?.value === code;
    });

    // If found, remove it and notify
    if (index !== -1) {
      this.serviceChargeLinesArray.removeAt(index);
      this.messagenotify.pushNotification('DELETE_ITEM', code);
    }
  }
  onItemCodeSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.servicename === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected item:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.serviceChargeLinesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        serviceDescription: selectedItem.servicediscription,
        name: selectedItem.servicename,

        // Add any other fields you want to update with the selected item's details
      });
      console.log(salesInvoiceLineGroup.value);
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }

  calculateTotal(total: number): void {
    console.log('Total Value:', total); // Log the correct total value
    this.totalUpdated.emit(total); // Emit total to parent
    // Small delay to allow UI updates
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

    const requests: Observable<HttpResponse<ISalesInvoiceServiceChargeLine>>[] = serviceChargeLines.map(
      (line: ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine) => {
        console.log('Processing line:', line);
        console.log('Line ID:', line?.id); // Use optional chaining to avoid errors

        return line.id
          ? this.salesInvoiceServiceChargeLineService.update(line)
          : this.salesInvoiceServiceChargeLineService.create(line as NewSalesInvoiceServiceChargeLine);
      },
    );

    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceServiceChargeLine>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
  addServiceChargeLine(): void {
    const newDummy = this.salesInvoiceServiceChargeLineFormService.createSalesInvoiceServiceChargeLineFormGroup();
    this.serviceChargeLinesArray.push(newDummy);
  }

  removeServiceChargeLine(index: number): void {
    this.serviceChargeLinesArray.removeAt(index);
  }

  protected updateForm(salesInvoiceServiceChargeLines: ISalesInvoiceServiceChargeLine[]): void {
    this.serviceChargeLinesArray.clear();
    salesInvoiceServiceChargeLines.forEach(line => {
      this.serviceChargeLinesArray.push(this.salesInvoiceServiceChargeLineFormService.createSalesInvoiceServiceChargeLineFormGroup(line));
    });
  }
}
