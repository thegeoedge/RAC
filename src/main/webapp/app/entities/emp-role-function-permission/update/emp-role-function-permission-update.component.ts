import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpRoleFunctionPermission } from '../emp-role-function-permission.model';
import { EmpRoleFunctionPermissionService } from '../service/emp-role-function-permission.service';
import { EmpRoleFunctionPermissionFormGroup, EmpRoleFunctionPermissionFormService } from './emp-role-function-permission-form.service';

@Component({
  selector: 'jhi-emp-role-function-permission-update',
  templateUrl: './emp-role-function-permission-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  standalone: true,
})
export class EmpRoleFunctionPermissionUpdateComponent implements OnInit {
  isSaving = false;
  empRoleFunctionPermission: IEmpRoleFunctionPermission | null = null;

  protected empRoleFunctionPermissionService = inject(EmpRoleFunctionPermissionService);
  protected empRoleFunctionPermissionFormService = inject(EmpRoleFunctionPermissionFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpRoleFunctionPermissionFormGroup = this.empRoleFunctionPermissionFormService.createEmpRoleFunctionPermissionFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empRoleFunctionPermission }) => {
      this.empRoleFunctionPermission = empRoleFunctionPermission;
      if (empRoleFunctionPermission) {
        this.updateForm(empRoleFunctionPermission);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empRoleFunctionPermission = this.empRoleFunctionPermissionFormService.getEmpRoleFunctionPermission(this.editForm);
    if (empRoleFunctionPermission.id !== null) {
      this.subscribeToSaveResponse(this.empRoleFunctionPermissionService.update(empRoleFunctionPermission));
    } else {
      this.subscribeToSaveResponse(this.empRoleFunctionPermissionService.create(empRoleFunctionPermission));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpRoleFunctionPermission>>): void {
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

  protected updateForm(empRoleFunctionPermission: IEmpRoleFunctionPermission): void {
    this.empRoleFunctionPermission = empRoleFunctionPermission;
    this.empRoleFunctionPermissionFormService.resetForm(this.editForm, empRoleFunctionPermission);
  }
}
