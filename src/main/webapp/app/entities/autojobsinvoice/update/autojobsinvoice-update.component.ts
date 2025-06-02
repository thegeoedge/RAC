import { Component, inject, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutojobsinvoice } from '../autojobsinvoice.model';
import { AutojobsinvoiceService } from '../service/autojobsinvoice.service';
import { AutojobsinvoiceFormService, AutojobsinvoiceFormGroup } from './autojobsinvoice-form.service';

@Component({
  standalone: true,
  selector: 'jhi-autojobsinvoice-update',
  templateUrl: './autojobsinvoice-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutojobsinvoiceUpdateComponent implements OnInit, OnChanges {
  isSaving = false;
  autojobsinvoice: IAutojobsinvoice | null = null;

  // Add an @Input() property to receive data from the parent component
  @Input() formData: IAutojobsinvoice | null = null;

  protected autojobsinvoiceService = inject(AutojobsinvoiceService);
  protected autojobsinvoiceFormService = inject(AutojobsinvoiceFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutojobsinvoiceFormGroup = this.autojobsinvoiceFormService.createAutojobsinvoiceFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autojobsinvoice }) => {
      this.autojobsinvoice = autojobsinvoice;
      if (autojobsinvoice) {
        this.updateForm(autojobsinvoice);
      }
    });

    // If formData is provided via @Input(), update the form
    if (this.formData) {
      this.updateForm(this.formData);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    // If formData changes, update the form
    if (changes.formData && this.formData) {
      this.updateForm(this.formData);
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): Observable<number> {
    // Ensure this method is public
    this.isSaving = true;
    const autojobsinvoice = this.autojobsinvoiceFormService.getAutojobsinvoice(this.editForm);
    console.log(autojobsinvoice);
    autojobsinvoice.subtotal = Math.floor(autojobsinvoice.subtotal ?? 0);
    autojobsinvoice.nettotal = Math.floor(autojobsinvoice.nettotal ?? 0);

    if (autojobsinvoice.id !== null) {
      return this.subscribeToSaveResponse(this.autojobsinvoiceService.update(autojobsinvoice));
    } else {
      return this.subscribeToSaveResponse(this.autojobsinvoiceService.create(autojobsinvoice));
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutojobsinvoice>>): Observable<number> {
    return new Observable(observer => {
      result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
        next: response => {
          console.log('Save Successfulllllllllllllllllllllllllllll:', response);
          if (response.body) {
            const invoiceId = response.body.id;
            console.log('Saved Invoice ID:', invoiceId);
            observer.next(invoiceId); // Emit the invoice ID
            observer.complete();
          } else {
            console.error('Response body is null');
            observer.error('Response body is null');
          }
        },
        error: error => {
          console.error('Save Failed:', error);
          this.onSaveError();
          observer.error(error);
        },
      });
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

  protected updateForm(autojobsinvoice: IAutojobsinvoice): void {
    this.autojobsinvoice = autojobsinvoice;
    this.autojobsinvoiceFormService.resetForm(this.editForm, autojobsinvoice);
  }
}
