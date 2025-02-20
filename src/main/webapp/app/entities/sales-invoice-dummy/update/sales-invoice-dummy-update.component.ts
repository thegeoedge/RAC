import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { SaleInvoiceCommonServiceChargeDummyUpdateComponent } from '../../sale-invoice-common-service-charge-dummy/update/sale-invoice-common-service-charge-dummy-update.component';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule, FormGroup, FormControl } from '@angular/forms';
import { Component, inject, OnInit, viewChild, ViewChild } from '@angular/core';
import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';
import { SalesInvoiceDummyService } from '../service/sales-invoice-dummy.service';
import { SalesInvoiceDummyFormGroup, SalesInvoiceDummyFormService } from './sales-invoice-dummy-form.service';
import { SalesInvoiceLinesDummyUpdateComponent } from '../../sales-invoice-lines-dummy/update/sales-invoice-lines-dummy-update.component';
import { SalesInvoiceServiceChargeLineDummyUpdateComponent } from '../../sales-invoice-service-charge-line-dummy/update/sales-invoice-service-charge-line-dummy-update.component';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-dummy-update',
  templateUrl: './sales-invoice-dummy-update.component.html',
  imports: [
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    SaleInvoiceCommonServiceChargeDummyUpdateComponent,
    SalesInvoiceLinesDummyUpdateComponent,
    SalesInvoiceServiceChargeLineDummyUpdateComponent,
  ],
})
export class SalesInvoiceDummyUpdateComponent implements OnInit {
  isSaving = false;
  @ViewChild(SalesInvoiceServiceChargeLineDummyUpdateComponent)
  SalesInvoiceServiceChargeLineDummyUpdateComponent!: SalesInvoiceServiceChargeLineDummyUpdateComponent;
  @ViewChild(SalesInvoiceLinesDummyUpdateComponent) SalesInvoiceLinesDummyUpdateComponent!: SalesInvoiceLinesDummyUpdateComponent;
  @ViewChild(SaleInvoiceCommonServiceChargeDummyUpdateComponent)
  SaleInvoiceCommonServiceChargeDummyUpdateComponent!: SaleInvoiceCommonServiceChargeDummyUpdateComponent;
  salesInvoiceDummy: ISalesInvoiceDummy | null = null;
  showCodeField: boolean = false;
  protected salesInvoiceDummyService = inject(SalesInvoiceDummyService);
  protected salesInvoiceDummyFormService = inject(SalesInvoiceDummyFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected vehicletypesService = inject(VehicletypeService);
  discountOption: string = 'percentage'; // Default value
  discountValue: number = 0;
  editForm: SalesInvoiceDummyFormGroup = this.salesInvoiceDummyFormService.createSalesInvoiceDummyFormGroup();
  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.loadSalesInvoiceDummy(id);
      }
      console.log('Query id:', id);
    });
    this.loadVehicleTypes();
    // Subscribe to form control valueChanges
    this.editForm.get('valueDiscount')?.valueChanges.subscribe(() => this.calculateDiscount());
    this.editForm.get('subTotal')?.valueChanges.subscribe(() => this.calculateDiscount());
  }
  onDiscountValueChange(event: any): void {
    this.discountValue = event.target.value; // Update the discountValue with the input value
    console.log('Updated Discount Value:', this.discountValue);

    this.calculateDiscount(); // Log the updated value to the console
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
      totalDiscount: Number(totalDiscount.toFixed(2)),
      netTotal: Number(netTotal.toFixed(2)),
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
  subTotal: number = 0; // Store subtotal
  // Store subtotal

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
      subTotal: this.subTotal,
    });
  }
  vehicletypes: IVehicletype[] = [];
  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      console.log('Loaded Vehicle Types:', this.vehicletypes); // Display the loaded vehicle types in the console
    });
  }

  private loadSalesInvoiceDummy(id: number): void {
    this.salesInvoiceDummyService.find(id).subscribe(response => {
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
    const salesInvoiceDummy = this.salesInvoiceDummyFormService.getSalesInvoiceDummy(this.editForm);
    if (salesInvoiceDummy.id !== null) {
      this.subscribeToSaveResponse(this.salesInvoiceDummyService.update(salesInvoiceDummy));
    } else {
      this.subscribeToSaveResponse(this.salesInvoiceDummyService.create(salesInvoiceDummy));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceDummy>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: res => {
        console.log('Success Responseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee:', res.body); // Log the success response

        if (res.status === 201) {
          if (res.body) {
            console.log('Sales invoice created:', res.body.id);
            if (this.SalesInvoiceServiceChargeLineDummyUpdateComponent) {
              this.SalesInvoiceServiceChargeLineDummyUpdateComponent.save(res.body.id); // Call save from the child component
            }
            if (this.SalesInvoiceLinesDummyUpdateComponent) {
              this.SalesInvoiceLinesDummyUpdateComponent.save(res.body.id); // Call save from the child component
            }
            if (this.SaleInvoiceCommonServiceChargeDummyUpdateComponent) {
              this.SaleInvoiceCommonServiceChargeDummyUpdateComponent.save(res.body.id); // Call save from the child component
            }
          }
        } else if (res.status === 200) {
          console.log('Sales invoice updated:', res.body);
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
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(salesInvoiceDummy: ISalesInvoiceDummy): void {
    this.salesInvoiceDummy = salesInvoiceDummy;
    this.salesInvoiceDummyFormService.resetForm(this.editForm, salesInvoiceDummy);
  }
}
