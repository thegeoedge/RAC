import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutoCareVehicle } from '../auto-care-vehicle.model';
import { AutoCareVehicleService } from '../service/auto-care-vehicle.service';
import { AutoCareVehicleFormGroup, AutoCareVehicleFormService } from './auto-care-vehicle-form.service';

@Component({
  standalone: true,
  selector: 'jhi-auto-care-vehicle-update',
  templateUrl: './auto-care-vehicle-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutoCareVehicleUpdateComponent implements OnInit {
  isSaving = false;
  autoCareVehicle: IAutoCareVehicle | null = null;

  protected autoCareVehicleService = inject(AutoCareVehicleService);
  protected autoCareVehicleFormService = inject(AutoCareVehicleFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutoCareVehicleFormGroup = this.autoCareVehicleFormService.createAutoCareVehicleFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autoCareVehicle }) => {
      this.autoCareVehicle = autoCareVehicle;
      if (autoCareVehicle) {
        this.updateForm(autoCareVehicle);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autoCareVehicle = this.autoCareVehicleFormService.getAutoCareVehicle(this.editForm);
    if (autoCareVehicle.id !== null) {
      this.subscribeToSaveResponse(this.autoCareVehicleService.update(autoCareVehicle));
    } else {
      this.subscribeToSaveResponse(this.autoCareVehicleService.create(autoCareVehicle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutoCareVehicle>>): void {
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

  protected updateForm(autoCareVehicle: IAutoCareVehicle): void {
    this.autoCareVehicle = autoCareVehicle;
    this.autoCareVehicleFormService.resetForm(this.editForm, autoCareVehicle);
  }
}
