import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmpFunctions } from '../emp-functions.model';
import { EmpFunctionsService } from '../service/emp-functions.service';
import { EmpFunctionsFormGroup, EmpFunctionsFormService } from './emp-functions-form.service';

@Component({
  selector: 'jhi-emp-functions-update',
  templateUrl: './emp-functions-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  standalone: true,
})
export class EmpFunctionsUpdateComponent implements OnInit {
  isSaving = false;
  empFunctions: IEmpFunctions | null = null;

  protected empFunctionsService = inject(EmpFunctionsService);
  protected empFunctionsFormService = inject(EmpFunctionsFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpFunctionsFormGroup = this.empFunctionsFormService.createEmpFunctionsFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empFunctions }) => {
      this.empFunctions = empFunctions;
      if (empFunctions) {
        this.updateForm(empFunctions);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empFunctions = this.empFunctionsFormService.getEmpFunctions(this.editForm);
    if (empFunctions.functionId !== null) {
      this.subscribeToSaveResponse(this.empFunctionsService.update(empFunctions));
    } else {
      this.subscribeToSaveResponse(this.empFunctionsService.create(empFunctions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpFunctions>>): void {
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

  protected updateForm(empFunctions: IEmpFunctions): void {
    this.empFunctions = empFunctions;
    this.empFunctionsFormService.resetForm(this.editForm, empFunctions);
  }
}
