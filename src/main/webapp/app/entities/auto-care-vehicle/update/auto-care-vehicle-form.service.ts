import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAutoCareVehicle, NewAutoCareVehicle } from '../auto-care-vehicle.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAutoCareVehicle for edit and NewAutoCareVehicleFormGroupInput for create.
 */
type AutoCareVehicleFormGroupInput = IAutoCareVehicle | PartialWithRequiredKeyOf<NewAutoCareVehicle>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAutoCareVehicle | NewAutoCareVehicle> = Omit<T, 'lastServiceDate' | 'nextServiceDate' | 'lmd'> & {
  lastServiceDate?: string | null;
  nextServiceDate?: string | null;
  lmd?: string | null;
};

type AutoCareVehicleFormRawValue = FormValueOf<IAutoCareVehicle>;

type NewAutoCareVehicleFormRawValue = FormValueOf<NewAutoCareVehicle>;

type AutoCareVehicleFormDefaults = Pick<NewAutoCareVehicle, 'id' | 'lastServiceDate' | 'nextServiceDate' | 'lmd'>;

type AutoCareVehicleFormGroupContent = {
  id: FormControl<AutoCareVehicleFormRawValue['id'] | NewAutoCareVehicle['id']>;
  customerId: FormControl<AutoCareVehicleFormRawValue['customerId']>;
  customerName: FormControl<AutoCareVehicleFormRawValue['customerName']>;
  customerTel: FormControl<AutoCareVehicleFormRawValue['customerTel']>;
  vehicleNumber: FormControl<AutoCareVehicleFormRawValue['vehicleNumber']>;
  brandId: FormControl<AutoCareVehicleFormRawValue['brandId']>;
  brandName: FormControl<AutoCareVehicleFormRawValue['brandName']>;
  model: FormControl<AutoCareVehicleFormRawValue['model']>;
  millage: FormControl<AutoCareVehicleFormRawValue['millage']>;
  manufactureYear: FormControl<AutoCareVehicleFormRawValue['manufactureYear']>;
  lastServiceDate: FormControl<AutoCareVehicleFormRawValue['lastServiceDate']>;
  nextServiceDate: FormControl<AutoCareVehicleFormRawValue['nextServiceDate']>;
  description: FormControl<AutoCareVehicleFormRawValue['description']>;
  lmu: FormControl<AutoCareVehicleFormRawValue['lmu']>;
  lmd: FormControl<AutoCareVehicleFormRawValue['lmd']>;
};

export type AutoCareVehicleFormGroup = FormGroup<AutoCareVehicleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AutoCareVehicleFormService {
  createAutoCareVehicleFormGroup(autoCareVehicle: AutoCareVehicleFormGroupInput = { id: null }): AutoCareVehicleFormGroup {
    const autoCareVehicleRawValue = this.convertAutoCareVehicleToAutoCareVehicleRawValue({
      ...this.getFormDefaults(),
      ...autoCareVehicle,
    });
    return new FormGroup<AutoCareVehicleFormGroupContent>({
      id: new FormControl(
        { value: autoCareVehicleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      customerId: new FormControl(autoCareVehicleRawValue.customerId),
      customerName: new FormControl(autoCareVehicleRawValue.customerName),
      customerTel: new FormControl(autoCareVehicleRawValue.customerTel),
      vehicleNumber: new FormControl(autoCareVehicleRawValue.vehicleNumber),
      brandId: new FormControl(autoCareVehicleRawValue.brandId),
      brandName: new FormControl(autoCareVehicleRawValue.brandName),
      model: new FormControl(autoCareVehicleRawValue.model),
      millage: new FormControl(autoCareVehicleRawValue.millage),
      manufactureYear: new FormControl(autoCareVehicleRawValue.manufactureYear),
      lastServiceDate: new FormControl(autoCareVehicleRawValue.lastServiceDate),
      nextServiceDate: new FormControl(autoCareVehicleRawValue.nextServiceDate),
      description: new FormControl(autoCareVehicleRawValue.description),
      lmu: new FormControl(autoCareVehicleRawValue.lmu),
      lmd: new FormControl(autoCareVehicleRawValue.lmd),
    });
  }

  getAutoCareVehicle(form: AutoCareVehicleFormGroup): IAutoCareVehicle | NewAutoCareVehicle {
    return this.convertAutoCareVehicleRawValueToAutoCareVehicle(
      form.getRawValue() as AutoCareVehicleFormRawValue | NewAutoCareVehicleFormRawValue,
    );
  }

  resetForm(form: AutoCareVehicleFormGroup, autoCareVehicle: AutoCareVehicleFormGroupInput): void {
    const autoCareVehicleRawValue = this.convertAutoCareVehicleToAutoCareVehicleRawValue({ ...this.getFormDefaults(), ...autoCareVehicle });
    form.reset(
      {
        ...autoCareVehicleRawValue,
        id: { value: autoCareVehicleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AutoCareVehicleFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastServiceDate: currentTime,
      nextServiceDate: currentTime,
      lmd: currentTime,
    };
  }

  private convertAutoCareVehicleRawValueToAutoCareVehicle(
    rawAutoCareVehicle: AutoCareVehicleFormRawValue | NewAutoCareVehicleFormRawValue,
  ): IAutoCareVehicle | NewAutoCareVehicle {
    return {
      ...rawAutoCareVehicle,
      lastServiceDate: dayjs(rawAutoCareVehicle.lastServiceDate, DATE_TIME_FORMAT),
      nextServiceDate: dayjs(rawAutoCareVehicle.nextServiceDate, DATE_TIME_FORMAT),
      lmd: dayjs(rawAutoCareVehicle.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertAutoCareVehicleToAutoCareVehicleRawValue(
    autoCareVehicle: IAutoCareVehicle | (Partial<NewAutoCareVehicle> & AutoCareVehicleFormDefaults),
  ): AutoCareVehicleFormRawValue | PartialWithRequiredKeyOf<NewAutoCareVehicleFormRawValue> {
    return {
      ...autoCareVehicle,
      lastServiceDate: autoCareVehicle.lastServiceDate ? autoCareVehicle.lastServiceDate.format(DATE_TIME_FORMAT) : undefined,
      nextServiceDate: autoCareVehicle.nextServiceDate ? autoCareVehicle.nextServiceDate.format(DATE_TIME_FORMAT) : undefined,
      lmd: autoCareVehicle.lmd ? autoCareVehicle.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
