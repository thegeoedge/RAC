import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISystemSettings } from '../system-settings.model';
import { SystemSettingsService } from '../service/system-settings.service';
import { SystemSettingsFormGroup, SystemSettingsFormService } from './system-settings-form.service';

@Component({
  standalone: true,
  selector: 'jhi-system-settings-update',
  templateUrl: './system-settings-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SystemSettingsUpdateComponent implements OnInit {
  isSaving = false;
  systemSettings: ISystemSettings | null = null;

  protected systemSettingsService = inject(SystemSettingsService);
  protected systemSettingsFormService = inject(SystemSettingsFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SystemSettingsFormGroup = this.systemSettingsFormService.createSystemSettingsFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemSettings }) => {
      this.systemSettings = systemSettings;
      if (systemSettings) {
        this.updateForm(systemSettings);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const systemSettings = this.systemSettingsFormService.getSystemSettings(this.editForm);
    if (systemSettings.id !== null) {
      this.subscribeToSaveResponse(this.systemSettingsService.update(systemSettings));
    } else {
      this.subscribeToSaveResponse(this.systemSettingsService.create(systemSettings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemSettings>>): void {
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

  protected updateForm(systemSettings: ISystemSettings): void {
    this.systemSettings = systemSettings;
    this.systemSettingsFormService.resetForm(this.editForm, systemSettings);
  }
}
