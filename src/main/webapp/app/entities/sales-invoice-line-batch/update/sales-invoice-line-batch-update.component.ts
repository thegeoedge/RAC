import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';
import { SalesInvoiceLineBatchService } from '../service/sales-invoice-line-batch.service';
import { SalesInvoiceLineBatchFormGroup, SalesInvoiceLineBatchFormService } from './sales-invoice-line-batch-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-line-batch-update',
  templateUrl: './sales-invoice-line-batch-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SalesInvoiceLineBatchUpdateComponent implements OnInit {
  isSaving = false;
  salesInvoiceLineBatch: ISalesInvoiceLineBatch | null = null;

  protected salesInvoiceLineBatchService = inject(SalesInvoiceLineBatchService);
  protected salesInvoiceLineBatchFormService = inject(SalesInvoiceLineBatchFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SalesInvoiceLineBatchFormGroup = this.salesInvoiceLineBatchFormService.createSalesInvoiceLineBatchFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesInvoiceLineBatch }) => {
      this.salesInvoiceLineBatch = salesInvoiceLineBatch;
      if (salesInvoiceLineBatch) {
        this.updateForm(salesInvoiceLineBatch);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesInvoiceLineBatch = this.salesInvoiceLineBatchFormService.getSalesInvoiceLineBatch(this.editForm);
    if (salesInvoiceLineBatch.id !== null) {
      this.subscribeToSaveResponse(this.salesInvoiceLineBatchService.update(salesInvoiceLineBatch));
    } else {
      this.subscribeToSaveResponse(this.salesInvoiceLineBatchService.create(salesInvoiceLineBatch));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceLineBatch>>): void {
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

  protected updateForm(salesInvoiceLineBatch: ISalesInvoiceLineBatch): void {
    this.salesInvoiceLineBatch = salesInvoiceLineBatch;
    this.salesInvoiceLineBatchFormService.resetForm(this.editForm, salesInvoiceLineBatch);
  }
}
