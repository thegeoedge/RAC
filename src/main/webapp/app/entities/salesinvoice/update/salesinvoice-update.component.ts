import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { ISalesinvoice } from '../salesinvoice.model';
import { SalesinvoiceService } from '../service/salesinvoice.service';
import { SalesinvoiceFormService, SalesinvoiceFormGroup } from './salesinvoice-form.service';
import { SalesInvoiceLinesUpdateComponent } from '../../sales-invoice-lines/update/sales-invoice-lines-update.component';
import { SaleInvoiceCommonServiceChargeUpdateComponent } from '../../sale-invoice-common-service-charge/update/sale-invoice-common-service-charge-update.component';
import { SalesInvoiceServiceChargeLineUpdateComponent } from '../../sales-invoice-service-charge-line/update/sales-invoice-service-charge-line-update.component';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';

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
  protected vehicletypesService = inject(VehicletypeService);
  protected salesinvoiceService = inject(SalesinvoiceService);
  protected salesinvoiceFormService = inject(SalesinvoiceFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // Initialize editForm with SalesinvoiceFormService
  editForm: SalesinvoiceFormGroup = this.salesinvoiceFormService.createSalesinvoiceFormGroup();
  cdr: any;
  discountOption: string = 'percentage'; // Default value
  discountValue: number = 0;
  subTotal: number = 0;

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesinvoice }) => {
      const id = salesinvoice['id'];
      this.salesinvoice = salesinvoice;
      if (salesinvoice) {
        this.updateForm(salesinvoice);
        this.loadSalesInvoiceDummy(id);
      }
      console.log('Query id:', id);
    });
    this.loadVehicleTypes();
    // Subscribe to form control valueChanges
    this.editForm.get('valueDiscount')?.valueChanges.subscribe(() => this.calculateDiscount());
    this.editForm.get('subTotal')?.valueChanges.subscribe(() => this.calculateDiscount());
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

  private loadSalesInvoiceDummy(id: number): void {
    this.salesInvoiceService.find(id).subscribe(response => {
      const salesInvoiceDummy = response.body;
      console.log('Retrieved data:', salesInvoiceDummy);
      if (salesInvoiceDummy) {
        // Create a new object and assign customername to customerName
        const transformedData = {
          ...salesInvoiceDummy,
          id: null as unknown as number,
          customerName: (salesInvoiceDummy as any).customername,
          vehicleNo: (salesInvoiceDummy as any).vehicleno,
          customerAddress: (salesInvoiceDummy as any).customeraddress,
          // Assigning API response field to the correct model field
          subTotal: Number((salesInvoiceDummy as any).subtotal) || 0, // Ensure it's a number
          netTotal: Number((salesInvoiceDummy as any).nettotal) || 0, // Replace "8888" with a dynamic value
          totalTax: Number((salesInvoiceDummy as any).totaltax) || 0,
          totalDiscount: Number((salesInvoiceDummy as any).totaldiscount) || 0,
          originalInvoiceId: (salesInvoiceDummy as any).id ? Number((salesInvoiceDummy as any).id) : null,
          originalInvoiceCode: (salesInvoiceDummy as any).code, // Convert ID to number safely
          amountOwing: Number((salesInvoiceDummy as any).amountowing) || 0,
        };

        this.updateForm(transformedData);
      }
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
            if (this.salesInvoiceLinesUpdateComponent) {
              this.salesInvoiceLinesUpdateComponent.save(response.body.id); // Call save from the child component
            }
            if (this.SalesInvoiceServiceChargeLinesUpdateComponent) {
              this.SalesInvoiceServiceChargeLinesUpdateComponent.save(response.body.id); // Call save from the child component
            }
            if (this.SaleInvoiceCommonServiceChargesUpdateComponent) {
              this.SaleInvoiceCommonServiceChargesUpdateComponent.save(response.body.id); // Call save from the child component
            }
          }
        } else if (response.status === 200) {
          console.log('Sales invoice updated:', response.body);
        }
        this.onSaveSuccess();
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
