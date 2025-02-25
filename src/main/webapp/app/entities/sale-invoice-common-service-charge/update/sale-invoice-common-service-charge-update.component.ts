import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormsModule, ReactiveFormsModule, FormArray, FormGroup, FormControl } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';

import { ISaleInvoiceCommonServiceCharge, NewSaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';
import {
  SaleInvoiceCommonServiceChargeFormGroup,
  SaleInvoiceCommonServiceChargeFormService,
} from './sale-invoice-common-service-charge-form.service';
import { IServicesubcategory } from 'app/entities/servicesubcategory/servicesubcategory.model';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';

@Component({
  standalone: true,
  selector: 'jhi-sale-invoice-common-service-charge-update',
  templateUrl: './sale-invoice-common-service-charge-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SaleInvoiceCommonServiceChargeUpdateComponent implements OnInit {
  isSaving = false;
  saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge[] = [];
  filteredItems: IServicesubcategory[][] = [];
  showCodeField: boolean = true;
  protected saleInvoiceCommonServiceChargeService = inject(SaleInvoiceCommonServiceChargeService);
  protected saleInvoiceCommonServiceChargeFormService = inject(SaleInvoiceCommonServiceChargeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  commonServiceOptions: ICommonserviceoption[] = [];
  @Output() totalUpdated = new EventEmitter<number>();
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormGroup = new FormGroup({
    serviceCharges: new FormArray([]),
  });
  get serviceChargesArray(): FormArray {
    return this.editForm.get('serviceCharges') as FormArray;
  }
  totalsum: number = 0; // Global variable to store total value
  updateLineTotal(): void {
    // Calculate the total by summing up all values in the serviceChargeLines array
    const total = this.serviceChargesArray.controls
      .map(control => control.get('value')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    // Emit the total to the parent component
    this.totalUpdated.emit(total);
    console.log('Updated Total cccccccccccccccccccccccc:', total); // Log the updated total
    this.totalsum = total;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saleInvoiceCommonServiceCharges }) => {
      if (saleInvoiceCommonServiceCharges && saleInvoiceCommonServiceCharges.length > 0) {
        this.saleInvoiceCommonServiceCharge = saleInvoiceCommonServiceCharges;
        this.updateForm(saleInvoiceCommonServiceCharges);
      } else {
        this.addServiceChargeDummy(); // Add default row when no data is available
      }
    });
    this.fetchCommonServiceOptions();
  }
  fetchCommonServiceOptions(): void {
    this.saleInvoiceCommonServiceChargeService
      .getElementsByUserInputCode()
      .pipe(debounceTime(300)) // Avoid frequent API calls
      .subscribe({
        next: (response: HttpResponse<ICommonserviceoption[]>) => {
          this.commonServiceOptions = response.body || [];
          console.log('API response items:', this.commonServiceOptions);
        },
        error: err => {
          console.error('Error fetching common service options:', err);
        },
      });
  }
  totalfetch: number = 0;
  onCheckboxChange(event: Event, option: ICommonserviceoption): void {
    const checkbox = event.target as HTMLInputElement;

    // Log the checkbox state (checked or unchecked) and the option details
    console.log('Checkbox changed:', checkbox.checked);
    console.log('Selected option:', option);

    if (checkbox.checked) {
      this.totalfetch += option.value ?? 0;
      // Create the form group for the selected option
      const formGroup = new FormGroup({
        id: new FormControl(option.id),
        description: new FormControl(option.description),
        name: new FormControl(option.name),
        value: new FormControl(option.value),
        mainid: new FormControl(option.mainid),
        code: new FormControl(''),
      });

      // Check if this is the first row being added
      if (this.serviceChargesArray.length === 0) {
        // Add the first selected option to the first row (default row behavior)
        this.serviceChargesArray.push(formGroup);
      } else {
        // If the array already has rows, insert the selected option at the start of the array
        const firstRow = this.serviceChargesArray.at(0) as FormGroup;

        // Check if there's already a default row and replace its values with the new selection
        if (!firstRow.get('id')?.value) {
          // Replace the default row with the first selected value
          firstRow.patchValue({
            id: option.id,
            description: option.description,
            name: option.name,
            value: option.value,
            mainid: option.mainid,
          });
        } else {
          // If there is no default row, simply push to the array
          this.serviceChargesArray.push(formGroup);
        }
      }
    } else {
      // Remove the option if unchecked from the serviceChargeDummies FormArray
      const index = this.serviceChargesArray.controls.findIndex(control => {
        const group = control as FormGroup;
        return group.get('id')?.value === option.id;
      });

      if (index !== -1) {
        this.serviceChargesArray.removeAt(index);
      }
    }
    console.log('Total Fetched Value:', this.totalfetch);
    this.calculateTotal(this.totalfetch); 
  }
  onItemCodeSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.name === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected item:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.serviceChargesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        description: selectedItem.description,
        name: selectedItem.name,

        mainid: selectedItem.mainid,
        // Add any other fields you want to update with the selected item's details
      });
      console.log(salesInvoiceLineGroup.value);
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }

  calculateTotal(total: number): void {
    console.log('Total Value:', total + this.totalsum); // Log the total value
    this.totalUpdated.emit(total + this.totalsum); // Emit total to parent
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

    this.saleInvoiceCommonServiceChargeService
      .getElementsByUserInputCode() // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IServicesubcategory[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.name); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: (error: any) => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }
  previousState(): void {
    window.history.back();
  }

  save(inid: number): void {
    this.isSaving = true;

    const serviceCharges = this.serviceChargesArray.value.map((line: any, index: number) => ({
      ...line,
      invoiceid: inid, // Assign invoice ID
      lineid: line.lineid ?? index + 1, // Ensure unique line ID
      optionid: index + 1, // Default option ID to 0
    }));

    console.log('Modified sales invoice lines:', serviceCharges);

    // Log each dummy's id to check its value
    console.log('Service Charge Dummies before saving:', serviceCharges);

    const requests: Observable<HttpResponse<ISaleInvoiceCommonServiceCharge>>[] = serviceCharges.map(
      (dummy: ISaleInvoiceCommonServiceCharge | NewSaleInvoiceCommonServiceCharge) => {
        console.log('Processing Dummy - ID:', dummy.id); // Log ID of each dummy

        return dummy.id
          ? this.saleInvoiceCommonServiceChargeService.update(dummy)
          : this.saleInvoiceCommonServiceChargeService.create({ ...dummy, id: null });
      },
    );

    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaleInvoiceCommonServiceCharge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }
  addServiceChargeDummy(): void {
    // Push a new form group into the serviceChargeDummies array
    const newDummy = this.saleInvoiceCommonServiceChargeFormService.createSaleInvoiceCommonServiceChargeFormGroup();
    this.serviceChargesArray.push(newDummy);
  }
  removeServiceChargeDummy(index: number): void {
    this.serviceChargesArray.removeAt(index);
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

  protected updateForm(saleInvoiceCommonServiceCharges: ISaleInvoiceCommonServiceCharge[]): void {
    this.serviceChargesArray.clear();
    saleInvoiceCommonServiceCharges.forEach(dummy => {
      this.serviceChargesArray.push(this.saleInvoiceCommonServiceChargeFormService.createSaleInvoiceCommonServiceChargeFormGroup(dummy));
    });
  }
}
