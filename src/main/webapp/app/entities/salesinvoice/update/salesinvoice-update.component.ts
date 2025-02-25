import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { debounceTime } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { ISalesInvoiceLines } from 'app/entities/sales-invoice-lines/sales-invoice-lines.model';
import { ISaleInvoiceCommonServiceCharge } from 'app/entities/sale-invoice-common-service-charge/sale-invoice-common-service-charge.model';
import { ISalesInvoiceServiceChargeLine } from 'app/entities/sales-invoice-service-charge-line/sales-invoice-service-charge-line/sales-invoice-service-charge-line.model';
import { IAutojobsaleinvoicecommonservicecharge } from 'app/entities/autojobsaleinvoicecommonservicecharge/autojobsaleinvoicecommonservicecharge.model';
import { ISalesinvoice } from '../salesinvoice.model';
import { SalesinvoiceService } from '../service/salesinvoice.service';
import { SalesinvoiceFormService, SalesinvoiceFormGroup } from './salesinvoice-form.service';
import { SalesInvoiceLinesUpdateComponent } from '../../sales-invoice-lines/update/sales-invoice-lines-update.component';
import { SaleInvoiceCommonServiceChargeUpdateComponent } from '../../sale-invoice-common-service-charge/update/sale-invoice-common-service-charge-update.component';
import { SalesInvoiceServiceChargeLineUpdateComponent } from '../../sales-invoice-service-charge-line/update/sales-invoice-service-charge-line-update.component';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { NewAutojobsalesinvoiceservicechargeline } from 'app/entities/autojobsalesinvoiceservicechargeline/autojobsalesinvoiceservicechargeline.model';
@Component({
  standalone: true,
  selector: 'jhi-salesinvoice-update',
  templateUrl: './salesinvoice-update.component.html',
  imports: [
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    SalesInvoiceLinesUpdateComponent,
    SaleInvoiceCommonServiceChargeUpdateComponent,
    SalesInvoiceServiceChargeLineUpdateComponent,
  ],
})
export class SalesinvoiceUpdateComponent implements OnInit {
  isSaving = false;
  salesinvoice: ISalesinvoice | null = null;
  showCodeField: boolean = false;
  @ViewChild(SalesInvoiceLinesUpdateComponent) salesInvoiceLinesUpdateComponent!: SalesInvoiceLinesUpdateComponent;

  @ViewChild(SalesInvoiceServiceChargeLineUpdateComponent)
  SalesInvoiceServiceChargeLinesUpdateComponent!: SalesInvoiceServiceChargeLineUpdateComponent;

  @ViewChild(SaleInvoiceCommonServiceChargeUpdateComponent)
  SaleInvoiceCommonServiceChargesUpdateComponent!: SaleInvoiceCommonServiceChargeUpdateComponent;
  protected salesInvoiceService = inject(SalesinvoiceService);
  autojobinvoice = inject(AutojobsinvoiceService);
  protected vehicletypesService = inject(VehicletypeService);
  protected salesinvoiceService = inject(SalesinvoiceService);
  protected salesinvoiceFormService = inject(SalesinvoiceFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);

  filteredItems: IInventory[][] = [];
  ISalesInvoiceLines: ISalesInvoiceLines[] = [];
  ISalesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine[] = [];
  NewAutojobsalesinvoiceservicechargeline: NewAutojobsalesinvoiceservicechargeline[] = [];
  ISaleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge[] = [];
  // Initialize editForm with SalesinvoiceFormService
  editForm: SalesinvoiceFormGroup = this.salesinvoiceFormService.createSalesinvoiceFormGroup();
  discountOption: string = 'percentage'; // Default value
  discountValue: number = 0;
  subTotal: number = 0;
  i: number = 0;

  ngOnInit(): void {
    console.log('starttt');

    // this.servicelines(id);
    // this.servicecommonlines(id);
    // this.invoicelines(id);
    // Extract ID from query params in case it's not in route data
    this.activatedRoute.queryParams.subscribe(params => {
      console.log('Query Params ID:', params['id']);
      this.loadSalesInvoiceDummy(params['id']);
      this.invoicelines(params['id']);
      this.servicelines(params['id']);
      this.servicecommonlines(params['id']);
    });

    this.loadVehicleTypes();

    // Subscribe to form control valueChanges
    this.editForm.get('valuediscount')?.valueChanges.subscribe(() => this.calculateDiscount());
    this.editForm.get('subtotal')?.valueChanges.subscribe(() => this.calculateDiscount());
  }

  vehicletypes: IVehicletype[] = [];
  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      console.log('Loaded Vehicle Types:', this.vehicletypes); // Display the loaded vehicle types in the console
    });
  }

  onDiscountValueChange(event: any): void {
    this.discountValue = event.target.value; // Update the discountValue with the input value
    console.log('Updated Discount Value:', this.discountValue); // Log the updated value to the console

    this.calculateDiscount(); // Log the updated value to the console
    // Call the function to calculate discount
  }
  buyquantity: number = 0; // Store the buy quantity value

  // Function to handle changes in the quantity field
  onBuyQtyChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    this.buyquantity = Number(inputElement.value);
    console.log('Buy Quantity:', this.buyquantity);
  }

  onsubtotalValueChange(event: any): void {
    this.subTotal = event.target.value; // Update the discountValue with the input value
    console.log('Updated Discount Value:', this.subTotal); // Log the updated value to the console
    // Call the function to calculate discount
  }

  calculateDiscount(): void {
    console.log('Form Values:', this.editForm.value); // Debug the entire form

    const subTotal = Number(this.editForm.get('subTotal')?.value) || 0;
    const valueDiscount = this.discountValue;

    console.log('Selected Discount Option:', this.discountOption); // Log the selected option
    console.log('Sub Total:', subTotal);
    console.log('Discount Value:', valueDiscount);

    let totalDiscount = 0;

    if (this.discountOption === 'percentage') {
      totalDiscount = (subTotal * Number(valueDiscount)) / 100;
    } else if (this.discountOption === 'value') {
      totalDiscount = valueDiscount;
    }

    totalDiscount = Math.min(totalDiscount, subTotal);
    const netTotal = subTotal - totalDiscount;

    console.log('Total Discount:', totalDiscount);
    console.log('Net Total:', netTotal);

    this.editForm.patchValue({
      totaldiscount: Number(totalDiscount.toFixed(2)),
      nettotal: Number(netTotal.toFixed(2)),
    });
  }

  onDiscountOptionChange(option: string): void {
    this.discountOption = option;
    console.log('Discount Option Changed:', this.discountOption);
    this.calculateDiscount(); // Recalculate discount based on the new option
  }

  total1: number = 0; // Total from first child
  total2: number = 0;
  total3: number = 0;
  subtotal: number = 0; // Store subtotal

  receiveTotal(total: number, source: string) {
    if (source === 'child1') {
      this.total1 = total; // Update total from first child
    } else if (source === 'child2') {
      this.total2 = total; // Update total from second child
    } else if (source === 'child3') {
      this.total3 = total; // Update total from second child
    }

    this.subTotal = this.total1 + this.total2 + this.total3; // Combine the totals
    console.log('Total1:', this.total1, 'Total2:', this.total2, 'SubTotal:', this.subTotal);

    this.editForm.patchValue({
      subtotal: this.subTotal,
    });
  }

  fetchedServicesCommon: { itemname: string; sellingprice: number }[] = [];

  private servicecommonlines(id: number): void {
    this.salesInvoiceService.fetchServiceCommon(id).subscribe(
      (res: HttpResponse<ISaleInvoiceCommonServiceCharge[]>) => {
        if (res.body && res.body.length > 0) {
          // Clear previous fetched items before adding new ones
          this.fetchedServicesCommon = [];

          res.body.forEach(item => {
            this.fetchedServicesCommon.push({
              itemname: item.name ?? '',

              sellingprice: item.value ?? 0,
            });
          });

          // Log the complete array of fetched items
          console.log('Fetched Itemssssscommon:', res.body);
        } else {
          console.log('No invoice lines found.');
        }
      },
      error => {
        console.error('Error fetching invoice lines:', error);
      },
    );
  }

  fetchedServices: { itemname: string; sellingprice: number }[] = [];

  private servicelines(id: number): void {
    this.salesInvoiceService.fetchService(id).subscribe(
      (res: HttpResponse<NewAutojobsalesinvoiceservicechargeline[]>) => {
        if (res.body && res.body.length > 0) {
          // Clear previous fetched items before adding new ones
          this.fetchedServices = [];

          res.body.forEach(item => {
            this.fetchedServices.push({
              itemname: item.servicename ?? '',

              sellingprice: item.value ?? 0,
            });
          });
          console.log(this.fetchedServices);
          // Log the complete array of fetched items
          console.log('Fetched Itemssssssssssssssssss:', res.body);
        } else {
          console.log('No invoice lines found.');
        }
      },
      error => {
        console.error('Error fetching invoice lines:', error);
      },
    );
  }

  fetchedItems: { itemname: string; quantity: number; sellingprice: number }[] = [];

  private invoicelines(id: number): void {
    this.salesInvoiceService.fetchInvoiceLines(id).subscribe(
      (res: HttpResponse<ISalesInvoiceLines[]>) => {
        if (res.body && res.body.length > 0) {
          console.log('counts', res.body);
          // Clear previous fetched items before adding new ones
          this.fetchedItems = [];

          res.body.forEach(item => {
            this.fetchedItems.push({
              itemname: item.itemname ?? '',
              quantity: item.quantity ?? 0,
              sellingprice: item.sellingprice ?? 0,
            });
          });

          // Log the complete array of fetched items
          console.log('Fetched Items:', this.fetchedItems);
        } else {
          console.log('No invoice lines found.');
        }
      },
      error => {
        console.error('Error fetching invoice lines:', error);
      },
    );
  }

  private loadSalesInvoiceDummy(id: number): void {
    console.log('iddddd', id);
    this.salesInvoiceService.fetchJobInvoice(id).subscribe(response => {
      const salesInvoiceDummy = response.body[0];
      console.log('Retrieved dataaaaaaaaaaaaa:', response);
      console.log('Retrieved dataaaaaaaaaaaaa:', salesInvoiceDummy);

      const customerNameValue = this.editForm.get('customername')?.value || '';
      // Create a new object and assign customername to customerName
      const transformedData = {
        id: null as unknown as number,
        customername: (salesInvoiceDummy as any).customername,
        vehicleno: (salesInvoiceDummy as any).vehicleno,
        customeraddress: (salesInvoiceDummy as any).customeraddress,

        subtotal: Number((salesInvoiceDummy as any).subtotal) || 0, // Ensure it's a number
        nettotal: Number((salesInvoiceDummy as any).nettotal) || 0, // Replace "8888" with a dynamic value
        totaltax: Number((salesInvoiceDummy as any).totaltax) || 0,
        totaldiscount: Number((salesInvoiceDummy as any).totaldiscount) || 0,
      };

      this.updateForm(transformedData);
      console.log('Transformed Data:', transformedData);
    });
  }

  selectedItem: { code: string; name: string; availablequantity: number; lastsellingprice: number } | null = null;

  itemname: string = ''; // Variable to hold the selected item's name
  availablequantity: number = 0;
  lastsellingprice: number = 0;
  code: string = '';
  onItemCodeSelect(event: Event, index: number): void {
    const inputElement = event.target as HTMLInputElement;
    const selectedCode = inputElement.value;

    // Find the selected item based on the code
    const selectedItem = this.filteredItems[index]?.find(item => item.code === selectedCode);

    if (selectedItem) {
      console.log('Selected Item:', selectedItem);
      this.itemname = selectedItem.name ?? ''; // Update itemName with the selected item's name or an empty string if undefined
      this.availablequantity = selectedItem.availablequantity ?? 0;
      this.lastsellingprice = selectedItem.lastsellingprice ?? 0;
      this.code = selectedItem.code ?? '';
    } else {
      console.warn('No matching item found for:', selectedCode);
      this.itemname = ''; // Clear itemName if no match is found
    }
  }
  onAddItem(): void {
    // Store the selected item as an object
    this.selectedItem = {
      code: this.code,
      name: this.itemname,
      availablequantity: this.buyquantity,
      lastsellingprice: this.lastsellingprice,
    };

    // Log the selected item to the console
    console.log('Selected Item:', this.selectedItem);
    console.log('Returned Buy Quantity:', this.buyquantity);
    // Call the function to get the buy quantity

    // Optionally reset the inputs after adding
    this.itemname = '';
    this.availablequantity = 0;
    this.lastsellingprice = 0;
    this.code = '';
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

    this.salesInvoiceLinesService
      .getElementsByUserInputCode(value) // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IInventory[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.code && item.name); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: error => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesinvoice = this.salesinvoiceFormService.getSalesinvoice(this.editForm);
    if (salesinvoice.id !== null) {
      this.subscribeToSaveResponse(this.salesinvoiceService.update(salesinvoice));
    } else {
      this.subscribeToSaveResponse(this.salesinvoiceService.create(salesinvoice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesinvoice>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        if (response.status === 201) {
          if (response.body) {
            console.log('Sales invoice created:', response.body.id);
            console.log('Full response body on creation:', response.body); // Log full response body on creation

            // Call save from the child components if available
            if (this.salesInvoiceLinesUpdateComponent) {
              this.salesInvoiceLinesUpdateComponent.save(response.body.id); // Call save from the child component
            }
            if (this.SalesInvoiceServiceChargeLinesUpdateComponent) {
              this.SalesInvoiceServiceChargeLinesUpdateComponent.save(response.body.id); // Call save from the child component
            }
            if (this.SaleInvoiceCommonServiceChargesUpdateComponent) {
              this.SaleInvoiceCommonServiceChargesUpdateComponent.save(response.body.id); // Call save from the child component
            }
            // alert("sucess?")
          }
        } else if (response.status === 200) {
          console.log('Sales invoice updated:', response.body);
          console.log('Full response body on update:', response.body); // Log full response body on update
        }
        // Uncomment if you have an onSaveSuccess method for successful operations
        // this.onSaveSuccess();
      },
      error: err => {
        console.error('Error Response:', err); // Log the error response
        this.onSaveError();
      },
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // API for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(salesinvoice: ISalesinvoice): void {
    this.salesinvoice = salesinvoice;
    this.salesinvoiceFormService.resetForm(this.editForm, salesinvoice);
  }
}
