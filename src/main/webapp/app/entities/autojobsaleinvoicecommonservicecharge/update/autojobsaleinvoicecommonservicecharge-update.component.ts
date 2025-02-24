import { Component, OnInit, inject, ChangeDetectorRef, Input, OnChanges, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';
import { AutojobsaleinvoicecommonservicechargeService } from '../service/autojobsaleinvoicecommonservicecharge.service';
import {
  AutojobsaleinvoicecommonservicechargeFormGroup,
  AutojobsaleinvoicecommonservicechargeFormService,
} from './autojobsaleinvoicecommonservicecharge-form.service';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import { CommonserviceoptionService } from 'app/entities/commonserviceoption/service/commonserviceoption.service';

@Component({
  standalone: true,
  selector: 'jhi-autojobsaleinvoicecommonservicecharge-update',
  templateUrl: './autojobsaleinvoicecommonservicecharge-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutojobsaleinvoicecommonservicechargeUpdateComponent implements OnInit {
  isSaving = false;
  autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge | null = null;
  commonserviceoption: ICommonserviceoption[] = [];
  selectedcommonServices: ICommonserviceoption[] = [];
  totalcommonServiceCharge = 0;
  @Input() formData: IAutojobsaleinvoicecommonservicecharge | null = null;

  protected autojobsaleinvoicecommonservicechargeService = inject(AutojobsaleinvoicecommonservicechargeService);
  protected autojobsaleinvoicecommonservicechargeFormService = inject(AutojobsaleinvoicecommonservicechargeFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected commonserviceoptionService = inject(CommonserviceoptionService);
  protected cdr = inject(ChangeDetectorRef);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutojobsaleinvoicecommonservicechargeFormGroup =
    this.autojobsaleinvoicecommonservicechargeFormService.createAutojobsaleinvoicecommonservicechargeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autojobsaleinvoicecommonservicecharge }) => {
      this.autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicecharge;
      if (autojobsaleinvoicecommonservicecharge) {
        this.updateForm(autojobsaleinvoicecommonservicecharge);
      }
      this.loadDataFromCommonServiceOptionEntities();
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

  loadDataFromCommonServiceOptionEntities() {
    this.commonserviceoptionService.query().subscribe((res: any) => {
      this.commonserviceoption = res.body;
    });
  }

  onServiceSelectionChange(service: ICommonserviceoption, event: any) {
    if (event.target.checked) {
      this.selectedcommonServices.push(service);
    } else {
      this.selectedcommonServices = this.selectedcommonServices.filter(s => s.id !== service.id);
    }
    this.calculateTotalCharge();
  }

  calculateTotalCharge() {
    this.totalcommonServiceCharge = this.selectedcommonServices.reduce((sum, service) => sum + (service.value || 0), 0);
    this.cdr.detectChanges(); // Ensure UI updates
  }

  save(): void {
    this.isSaving = true;
    const autojobsaleinvoicecommonservicecharge =
      this.autojobsaleinvoicecommonservicechargeFormService.getAutojobsaleinvoicecommonservicecharge(this.editForm);
    if (autojobsaleinvoicecommonservicecharge.id !== null) {
      this.subscribeToSaveResponse(this.autojobsaleinvoicecommonservicechargeService.update(autojobsaleinvoicecommonservicecharge));
    } else {
      this.subscribeToSaveResponse(this.autojobsaleinvoicecommonservicechargeService.create(autojobsaleinvoicecommonservicecharge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutojobsaleinvoicecommonservicecharge>>): void {
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

  protected updateForm(autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge): void {
    this.autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicecharge;
    this.autojobsaleinvoicecommonservicechargeFormService.resetForm(this.editForm, autojobsaleinvoicecommonservicecharge);
  }
}
