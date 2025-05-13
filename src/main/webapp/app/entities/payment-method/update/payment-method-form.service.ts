import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPaymentMethod, NewPaymentMethod } from '../payment-method.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentMethod for edit and NewPaymentMethodFormGroupInput for create.
 */
type PaymentMethodFormGroupInput = IPaymentMethod | PartialWithRequiredKeyOf<NewPaymentMethod>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPaymentMethod | NewPaymentMethod> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

type PaymentMethodFormRawValue = FormValueOf<IPaymentMethod>;

type NewPaymentMethodFormRawValue = FormValueOf<NewPaymentMethod>;

type PaymentMethodFormDefaults = Pick<NewPaymentMethod, 'id' | 'lmd'>;

type PaymentMethodFormGroupContent = {
  id: FormControl<PaymentMethodFormRawValue['id'] | NewPaymentMethod['id']>;
  paymentMethodName: FormControl<PaymentMethodFormRawValue['paymentMethodName']>;
  commission: FormControl<PaymentMethodFormRawValue['commission']>;
  companyBankAccountId: FormControl<PaymentMethodFormRawValue['companyBankAccountId']>;
  lmd: FormControl<PaymentMethodFormRawValue['lmd']>;
  lmu: FormControl<PaymentMethodFormRawValue['lmu']>;
};

export type PaymentMethodFormGroup = FormGroup<PaymentMethodFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodFormService {
  createPaymentMethodFormGroup(paymentMethod: PaymentMethodFormGroupInput = { id: null }): PaymentMethodFormGroup {
    const paymentMethodRawValue = this.convertPaymentMethodToPaymentMethodRawValue({
      ...this.getFormDefaults(),
      ...paymentMethod,
    });
    return new FormGroup<PaymentMethodFormGroupContent>({
      id: new FormControl(
        { value: paymentMethodRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      paymentMethodName: new FormControl(paymentMethodRawValue.paymentMethodName),
      commission: new FormControl(paymentMethodRawValue.commission),
      companyBankAccountId: new FormControl(paymentMethodRawValue.companyBankAccountId),
      lmd: new FormControl(paymentMethodRawValue.lmd),
      lmu: new FormControl(paymentMethodRawValue.lmu),
    });
  }

  getPaymentMethod(form: PaymentMethodFormGroup): IPaymentMethod | NewPaymentMethod {
    return this.convertPaymentMethodRawValueToPaymentMethod(form.getRawValue() as PaymentMethodFormRawValue | NewPaymentMethodFormRawValue);
  }

  resetForm(form: PaymentMethodFormGroup, paymentMethod: PaymentMethodFormGroupInput): void {
    const paymentMethodRawValue = this.convertPaymentMethodToPaymentMethodRawValue({ ...this.getFormDefaults(), ...paymentMethod });
    form.reset(
      {
        ...paymentMethodRawValue,
        id: { value: paymentMethodRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PaymentMethodFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lmd: currentTime,
    };
  }

  private convertPaymentMethodRawValueToPaymentMethod(
    rawPaymentMethod: PaymentMethodFormRawValue | NewPaymentMethodFormRawValue,
  ): IPaymentMethod | NewPaymentMethod {
    return {
      ...rawPaymentMethod,
      lmd: dayjs(rawPaymentMethod.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertPaymentMethodToPaymentMethodRawValue(
    paymentMethod: IPaymentMethod | (Partial<NewPaymentMethod> & PaymentMethodFormDefaults),
  ): PaymentMethodFormRawValue | PartialWithRequiredKeyOf<NewPaymentMethodFormRawValue> {
    return {
      ...paymentMethod,
      lmd: paymentMethod.lmd ? paymentMethod.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
