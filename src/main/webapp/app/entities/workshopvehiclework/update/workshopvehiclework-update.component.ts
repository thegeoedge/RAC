import { Component, inject, OnInit, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkshopvehiclework } from '../workshopvehiclework.model';
import { WorkshopvehicleworkService } from '../service/workshopvehiclework.service';
import { WorkshopvehicleworkFormService, WorkshopvehicleworkFormGroup } from './workshopvehiclework-form.service';
import { IWorkshopworklist } from 'app/entities/workshopworklist/workshopworklist.model';
import { WorkshopworklistService } from 'app/entities/workshopworklist/service/workshopworklist.service';

@Component({
  standalone: true,
  selector: 'jhi-workshopvehiclework-update',
  templateUrl: './workshopvehiclework-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WorkshopvehicleworkUpdateComponent implements OnInit {
  isSaving = false;
  workshopvehiclework: IWorkshopvehiclework | null = null;
  workshopworklist: IWorkshopworklist[] = [];
  selectedworkItems: IWorkshopworklist[] = [];
  @Input() formData: any;

  protected workshopvehicleworkService = inject(WorkshopvehicleworkService);
  protected workshopvehicleworkFormService = inject(WorkshopvehicleworkFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected workshopworklistService = inject(WorkshopworklistService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: WorkshopvehicleworkFormGroup = this.workshopvehicleworkFormService.createWorkshopvehicleworkFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workshopvehiclework }) => {
      this.workshopvehiclework = workshopvehiclework;
      if (workshopvehiclework) {
        this.updateForm(workshopvehiclework);
      }
      this.loadDataFromWorkshopWorklistEntities();
    });
  }

  loadDataFromWorkshopWorklistEntities() {
    this.workshopworklistService.query({ size: 1000 }).subscribe((res: any) => {
      this.workshopworklist = res.body;
    });
  }

  onworkServiceSelectionChange(item: any, event: any): void {
    if (event.target.checked) {
      this.selectedworkItems.push(item);
    } else {
      this.selectedworkItems = this.selectedworkItems.filter(selectedworkItem => selectedworkItem !== item);
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workshopvehiclework = this.workshopvehicleworkFormService.getWorkshopvehiclework(this.editForm);
    if (workshopvehiclework.id !== null) {
      this.subscribeToSaveResponse(this.workshopvehicleworkService.update(workshopvehiclework));
    } else {
      this.subscribeToSaveResponse(this.workshopvehicleworkService.create(workshopvehiclework));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkshopvehiclework>>): void {
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

  protected updateForm(workshopvehiclework: IWorkshopvehiclework): void {
    this.workshopvehiclework = workshopvehiclework;
    this.workshopvehicleworkFormService.resetForm(this.editForm, workshopvehiclework);
  }
}
