import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISystemSettings, NewSystemSettings } from '../system-settings.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISystemSettings for edit and NewSystemSettingsFormGroupInput for create.
 */
type SystemSettingsFormGroupInput = ISystemSettings | PartialWithRequiredKeyOf<NewSystemSettings>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISystemSettings | NewSystemSettings> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

type SystemSettingsFormRawValue = FormValueOf<ISystemSettings>;

type NewSystemSettingsFormRawValue = FormValueOf<NewSystemSettings>;

type SystemSettingsFormDefaults = Pick<NewSystemSettings, 'id' | 'lmd'>;

type SystemSettingsFormGroupContent = {
  id: FormControl<SystemSettingsFormRawValue['id'] | NewSystemSettings['id']>;
  key: FormControl<SystemSettingsFormRawValue['key']>;
  lastValue: FormControl<SystemSettingsFormRawValue['lastValue']>;
  nextValue: FormControl<SystemSettingsFormRawValue['nextValue']>;
  lmu: FormControl<SystemSettingsFormRawValue['lmu']>;
  lmd: FormControl<SystemSettingsFormRawValue['lmd']>;
};

export type SystemSettingsFormGroup = FormGroup<SystemSettingsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SystemSettingsFormService {
  createSystemSettingsFormGroup(systemSettings: SystemSettingsFormGroupInput = { id: null }): SystemSettingsFormGroup {
    const systemSettingsRawValue = this.convertSystemSettingsToSystemSettingsRawValue({
      ...this.getFormDefaults(),
      ...systemSettings,
    });
    return new FormGroup<SystemSettingsFormGroupContent>({
      id: new FormControl(
        { value: systemSettingsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      key: new FormControl(systemSettingsRawValue.key),
      lastValue: new FormControl(systemSettingsRawValue.lastValue),
      nextValue: new FormControl(systemSettingsRawValue.nextValue),
      lmu: new FormControl(systemSettingsRawValue.lmu),
      lmd: new FormControl(systemSettingsRawValue.lmd),
    });
  }

  getSystemSettings(form: SystemSettingsFormGroup): ISystemSettings | NewSystemSettings {
    return this.convertSystemSettingsRawValueToSystemSettings(
      form.getRawValue() as SystemSettingsFormRawValue | NewSystemSettingsFormRawValue,
    );
  }

  resetForm(form: SystemSettingsFormGroup, systemSettings: SystemSettingsFormGroupInput): void {
    const systemSettingsRawValue = this.convertSystemSettingsToSystemSettingsRawValue({ ...this.getFormDefaults(), ...systemSettings });
    form.reset(
      {
        ...systemSettingsRawValue,
        id: { value: systemSettingsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SystemSettingsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lmd: currentTime,
    };
  }

  private convertSystemSettingsRawValueToSystemSettings(
    rawSystemSettings: SystemSettingsFormRawValue | NewSystemSettingsFormRawValue,
  ): ISystemSettings | NewSystemSettings {
    return {
      ...rawSystemSettings,
      lmd: dayjs(rawSystemSettings.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertSystemSettingsToSystemSettingsRawValue(
    systemSettings: ISystemSettings | (Partial<NewSystemSettings> & SystemSettingsFormDefaults),
  ): SystemSettingsFormRawValue | PartialWithRequiredKeyOf<NewSystemSettingsFormRawValue> {
    return {
      ...systemSettings,
      lmd: systemSettings.lmd ? systemSettings.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
