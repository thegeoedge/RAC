import { Component, EventEmitter, OnInit, Output, Input, inject, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormsModule, ReactiveFormsModule, FormArray, FormGroup, FormControl, FormBuilder } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';

import { ISaleInvoiceCommonServiceCharge, NewSaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';
import {
  SaleInvoiceCommonServiceChargeFormGroup,
  SaleInvoiceCommonServiceChargeFormService,
} from './sale-invoice-common-service-charge-form.service';
import { IServicesubcategory } from 'app/entities/servicesubcategory/servicesubcategory.model';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import { MessageCommunicationService } from 'app/core/util/message.communication.service';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';
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
  messagenotify = inject(MessageCommunicationService);
  protected saleInvoiceCommonServiceChargeService = inject(SaleInvoiceCommonServiceChargeService);
  protected saleInvoiceCommonServiceChargeFormService = inject(SaleInvoiceCommonServiceChargeFormService);
  protected activatedRoute = inject(ActivatedRoute);
  salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  @Input() fetchedServicesCommon: any;
  commonServiceOptions: ICommonserviceoption[] = [];
  @Output() totalUpdated = new EventEmitter<number>();
  protected fb = inject(FormBuilder);
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
    const total = this.serviceChargesArray.controls.map(control => control.get('value')?.value || 0).reduce((acc, value) => acc + value, 0);

    // Emit the total to the parent component
    this.totalUpdated.emit(total);
    console.log('Updated Total cccccccccccccccccccccccc:', total); // Log the updated total
    this.totalsum = total;
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['fetchedServicesCommon'] && this.fetchedServicesCommon) {
      // Loop through the fetchedItems array and add each item to the form array
      this.fetchedServicesCommon.forEach((item: any) => {
        this.addItemToFormArray(item);
      });
      this.salesInvoiceLinesService.updateSalesInvoiceLines(this.serviceChargesArray);
      console.log('Fetched Items on Change:', this.fetchedServicesCommon); // Log fetched items
    }
    const total = this.serviceChargesArray.controls.map(control => control.get('value')?.value || 0).reduce((acc, value) => acc + value, 0);
    this.saleInvoiceCommonServiceChargeService.settotalservicecommonlines(total);
  }
  addItemToFormArray(item: any): void {
    // Create a new form group for the item
    const newItem = this.fb.group({
      name: [item.itemname],
      value: [item.sellingprice],
      isCustomerService: [false],
      description: [item.itemname],
      code: [item.itemcode],
    });

    // Add the new form group to the form array
    this.serviceChargesArray.push(newItem);
    //  this.salesInvoiceLinesService.updateSalesInvoiceCommonLines(this.serviceChargesArray);
    this.totalvalue(newItem);
  }
  totalvalue(formGroup: FormGroup): void {
    const value = formGroup.get('value');

    // Update the line total when the value changes
    value?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal());

    // Initialize the line total when the form is added
    this.updateLineTotal();
  }
  ngOnInit(): void {
    this.messagenotify.notificationAnnounced$.subscribe(message => {
      if (message.topic === 'DELETE_ITEM') {
        const itemId = message.message;
        this.removeInvoiceLinecode(itemId);
      }
    });
    this.activatedRoute.data.subscribe(({ saleInvoiceCommonServiceCharges }) => {
      if (saleInvoiceCommonServiceCharges && saleInvoiceCommonServiceCharges.length > 0) {
        this.saleInvoiceCommonServiceCharge = saleInvoiceCommonServiceCharges;
        this.updateForm(saleInvoiceCommonServiceCharges);
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
  removeInvoiceLinecode(code: any): void {
    // Find index of the form group in the array matching the lineId
    const index = this.serviceChargesArray.controls.findIndex(control => {
      const group = control as FormGroup;
      return group.get('name')?.value === code;
    });

    // If found, remove it and notify
    if (index !== -1) {
      this.serviceChargesArray.removeAt(index);
      this.messagenotify.pushNotification('DELETE_ITEM', code);
    }
  }

  totalfetch: number = 0;
  onCheckboxChange(event: Event, option: ICommonserviceoption): void {
    const checkbox = event.target as HTMLInputElement;
    console.log('Checkbox changed:', checkbox.checked);
    console.log('Selected option:', option);

    if (checkbox.checked) {
      this.totalfetch += option.value ?? 0;

      const formGroup = new FormGroup({
        id: new FormControl(option.id),
        description: new FormControl(option.description),
        name: new FormControl(option.name),
        value: new FormControl(option.value),
        mainid: new FormControl(option.mainid),
        code: new FormControl(option.code),
      });

      // Always push a new item without overwriting anything
      this.serviceChargesArray.push(formGroup);
    } else {
      const index = this.serviceChargesArray.controls.findIndex(control => {
        return control.get('id')?.value === option.id;
      });

      if (index !== -1) {
        this.serviceChargesArray.removeAt(index);
        this.totalfetch -= option.value ?? 0;
      }
    }

    console.log('Total Fetched Value:', this.totalfetch);
    this.calculateTotal(this.totalfetch);
    this.salesInvoiceLinesService.updateSalesInvoiceCommonLines(this.serviceChargesArray);
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
      invoiceId: inid,
      lineId: line.lineid ?? index + 1,
      optionId: index + 1,
      mainId: 1,
      id: null, // Ensures the backend treats it as a new entity
    }));

    console.log('Creating service charges with invoice ID:', inid);
    console.log('Prepared service charges for creation:', serviceCharges);

    const requests: Observable<HttpResponse<ISaleInvoiceCommonServiceCharge>>[] = serviceCharges.map(
      (dummy: NewSaleInvoiceCommonServiceCharge) => this.saleInvoiceCommonServiceChargeService.create(dummy),
    );

    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: responses => {
          console.log('All service charges successfully created:');
          responses.forEach((res, index) => {
            console.log(`Service charge #${index + 1} response:`, res.body);
          });
          // this.onSaveSuccess(); // Optional
        },
        error: err => {
          console.error('Error occurred while creating service charges:', err);
          this.onSaveError();
        },
      });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaleInvoiceCommonServiceCharge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      //  next: () => this.onSaveSuccess(),
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
    this.updateTotalInvoiceLines();
  }
  updateTotalInvoiceLines(): void {
    const total = this.serviceChargesArray.controls.map(control => control.get('value')?.value || 0).reduce((acc, value) => acc + value, 0);

    console.log('Updated Totallll:', total);
    this.salesInvoiceLinesService.settotalinvoicelines(total);
    setTimeout(() => {
      this.totalUpdated.emit(total);
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

  protected updateForm(saleInvoiceCommonServiceCharges: ISaleInvoiceCommonServiceCharge[]): void {
    this.serviceChargesArray.clear();
    saleInvoiceCommonServiceCharges.forEach(dummy => {
      this.serviceChargesArray.push(this.saleInvoiceCommonServiceChargeFormService.createSaleInvoiceCommonServiceChargeFormGroup(dummy));
    });
  }
}
