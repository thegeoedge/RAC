import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEmpRoles, NewEmpRoles } from '../emp-roles.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { roleId: unknown }> = Partial<Omit<T, 'roleId'>> & { roleId: T['roleId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpRoles for edit and NewEmpRolesFormGroupInput for create.
 */
type EmpRolesFormGroupInput = IEmpRoles | PartialWithRequiredKeyOf<NewEmpRoles>;

type EmpRolesFormDefaults = Pick<NewEmpRoles, 'roleId'>;

type EmpRolesFormGroupContent = {
  roleId: FormControl<IEmpRoles['roleId'] | NewEmpRoles['roleId']>;
  roleName: FormControl<IEmpRoles['roleName']>;
};

export type EmpRolesFormGroup = FormGroup<EmpRolesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpRolesFormService {
  createEmpRolesFormGroup(empRoles: EmpRolesFormGroupInput = { roleId: null }): EmpRolesFormGroup {
    const empRolesRawValue = {
      ...this.getFormDefaults(),
      ...empRoles,
    };
    return new FormGroup<EmpRolesFormGroupContent>({
      roleId: new FormControl(
        { value: empRolesRawValue.roleId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      roleName: new FormControl(empRolesRawValue.roleName),
    });
  }

  getEmpRoles(form: EmpRolesFormGroup): IEmpRoles | NewEmpRoles {
    return form.getRawValue() as IEmpRoles | NewEmpRoles;
  }

  resetForm(form: EmpRolesFormGroup, empRoles: EmpRolesFormGroupInput): void {
    const empRolesRawValue = { ...this.getFormDefaults(), ...empRoles };
    form.reset(
      {
        ...empRolesRawValue,
        roleId: { value: empRolesRawValue.roleId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmpRolesFormDefaults {
    return {
      roleId: null,
    };
  }
}
