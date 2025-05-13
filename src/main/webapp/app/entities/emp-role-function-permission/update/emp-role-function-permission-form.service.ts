import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEmpRoleFunctionPermission, NewEmpRoleFunctionPermission } from '../emp-role-function-permission.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpRoleFunctionPermission for edit and NewEmpRoleFunctionPermissionFormGroupInput for create.
 */
type EmpRoleFunctionPermissionFormGroupInput = IEmpRoleFunctionPermission | PartialWithRequiredKeyOf<NewEmpRoleFunctionPermission>;

type EmpRoleFunctionPermissionFormDefaults = Pick<NewEmpRoleFunctionPermission, 'id'>;

type EmpRoleFunctionPermissionFormGroupContent = {
  id: FormControl<IEmpRoleFunctionPermission['id'] | NewEmpRoleFunctionPermission['id']>;
  roleId: FormControl<IEmpRoleFunctionPermission['roleId']>;
  functionId: FormControl<IEmpRoleFunctionPermission['functionId']>;
};

export type EmpRoleFunctionPermissionFormGroup = FormGroup<EmpRoleFunctionPermissionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpRoleFunctionPermissionFormService {
  createEmpRoleFunctionPermissionFormGroup(
    empRoleFunctionPermission: EmpRoleFunctionPermissionFormGroupInput = { id: null },
  ): EmpRoleFunctionPermissionFormGroup {
    const empRoleFunctionPermissionRawValue = {
      ...this.getFormDefaults(),
      ...empRoleFunctionPermission,
    };
    return new FormGroup<EmpRoleFunctionPermissionFormGroupContent>({
      id: new FormControl(
        { value: empRoleFunctionPermissionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      roleId: new FormControl(empRoleFunctionPermissionRawValue.roleId),
      functionId: new FormControl(empRoleFunctionPermissionRawValue.functionId),
    });
  }

  getEmpRoleFunctionPermission(form: EmpRoleFunctionPermissionFormGroup): IEmpRoleFunctionPermission | NewEmpRoleFunctionPermission {
    return form.getRawValue() as IEmpRoleFunctionPermission | NewEmpRoleFunctionPermission;
  }

  resetForm(form: EmpRoleFunctionPermissionFormGroup, empRoleFunctionPermission: EmpRoleFunctionPermissionFormGroupInput): void {
    const empRoleFunctionPermissionRawValue = { ...this.getFormDefaults(), ...empRoleFunctionPermission };
    form.reset(
      {
        ...empRoleFunctionPermissionRawValue,
        id: { value: empRoleFunctionPermissionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmpRoleFunctionPermissionFormDefaults {
    return {
      id: null,
    };
  }
}
