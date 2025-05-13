import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEmpFunctions, NewEmpFunctions } from '../emp-functions.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { functionId: unknown }> = Partial<Omit<T, 'functionId'>> & { functionId: T['functionId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpFunctions for edit and NewEmpFunctionsFormGroupInput for create.
 */
type EmpFunctionsFormGroupInput = IEmpFunctions | PartialWithRequiredKeyOf<NewEmpFunctions>;

type EmpFunctionsFormDefaults = Pick<NewEmpFunctions, 'functionId'>;

type EmpFunctionsFormGroupContent = {
  functionId: FormControl<IEmpFunctions['functionId'] | NewEmpFunctions['functionId']>;
  functionName: FormControl<IEmpFunctions['functionName']>;
  moduleId: FormControl<IEmpFunctions['moduleId']>;
};

export type EmpFunctionsFormGroup = FormGroup<EmpFunctionsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpFunctionsFormService {
  createEmpFunctionsFormGroup(empFunctions: EmpFunctionsFormGroupInput = { functionId: null }): EmpFunctionsFormGroup {
    const empFunctionsRawValue = {
      ...this.getFormDefaults(),
      ...empFunctions,
    };
    return new FormGroup<EmpFunctionsFormGroupContent>({
      functionId: new FormControl(
        { value: empFunctionsRawValue.functionId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      functionName: new FormControl(empFunctionsRawValue.functionName),
      moduleId: new FormControl(empFunctionsRawValue.moduleId),
    });
  }

  getEmpFunctions(form: EmpFunctionsFormGroup): IEmpFunctions | NewEmpFunctions {
    return form.getRawValue() as IEmpFunctions | NewEmpFunctions;
  }

  resetForm(form: EmpFunctionsFormGroup, empFunctions: EmpFunctionsFormGroupInput): void {
    const empFunctionsRawValue = { ...this.getFormDefaults(), ...empFunctions };
    form.reset(
      {
        ...empFunctionsRawValue,
        functionId: { value: empFunctionsRawValue.functionId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmpFunctionsFormDefaults {
    return {
      functionId: null,
    };
  }
}
