import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpRoles } from '../emp-roles.model';
import { EmpRolesService } from '../service/emp-roles.service';
import { EmpRolesFormGroup, EmpRolesFormService } from './emp-roles-form.service';

@Component({
  selector: 'jhi-emp-roles-update',
  templateUrl: './emp-roles-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  standalone: true,
})
export class EmpRolesUpdateComponent implements OnInit {
  isSaving = false;
  empRoles: IEmpRoles | null = null;

  protected empRolesService = inject(EmpRolesService);
  protected empRolesFormService = inject(EmpRolesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpRolesFormGroup = this.empRolesFormService.createEmpRolesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empRoles }) => {
      this.empRoles = empRoles;
      if (empRoles) {
        this.updateForm(empRoles);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empRoles = this.empRolesFormService.getEmpRoles(this.editForm);
    if (empRoles.roleId !== null) {
      this.subscribeToSaveResponse(this.empRolesService.update(empRoles));
    } else {
      this.subscribeToSaveResponse(this.empRolesService.create(empRoles));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpRoles>>): void {
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

  protected updateForm(empRoles: IEmpRoles): void {
    this.empRoles = empRoles;
    this.empRolesFormService.resetForm(this.editForm, empRoles);
  }
}
