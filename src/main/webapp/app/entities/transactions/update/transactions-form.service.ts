import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransactions, NewTransactions } from '../transactions.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITransactions for edit and NewTransactionsFormGroupInput for create.
 */
type TransactionsFormGroupInput = ITransactions | PartialWithRequiredKeyOf<NewTransactions>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITransactions | NewTransactions> = Omit<T, 'date' | 'lmd'> & {
  date?: string | null;
  lmd?: string | null;
};

type TransactionsFormRawValue = FormValueOf<ITransactions>;

type NewTransactionsFormRawValue = FormValueOf<NewTransactions>;

type TransactionsFormDefaults = Pick<NewTransactions, 'id' | 'date' | 'lmd'>;

type TransactionsFormGroupContent = {
  id: FormControl<TransactionsFormRawValue['id'] | NewTransactions['id']>;
  accountId: FormControl<TransactionsFormRawValue['accountId']>;
  accountCode: FormControl<TransactionsFormRawValue['accountCode']>;
  debit: FormControl<TransactionsFormRawValue['debit']>;
  credit: FormControl<TransactionsFormRawValue['credit']>;
  date: FormControl<TransactionsFormRawValue['date']>;
  refDoc: FormControl<TransactionsFormRawValue['refDoc']>;
  refId: FormControl<TransactionsFormRawValue['refId']>;
  subId: FormControl<TransactionsFormRawValue['subId']>;
  source: FormControl<TransactionsFormRawValue['source']>;
  paymentTermId: FormControl<TransactionsFormRawValue['paymentTermId']>;
  paymentTermName: FormControl<TransactionsFormRawValue['paymentTermName']>;
  lmu: FormControl<TransactionsFormRawValue['lmu']>;
  lmd: FormControl<TransactionsFormRawValue['lmd']>;
};

export type TransactionsFormGroup = FormGroup<TransactionsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TransactionsFormService {
  createTransactionsFormGroup(transactions: TransactionsFormGroupInput = { id: null }): TransactionsFormGroup {
    const transactionsRawValue = this.convertTransactionsToTransactionsRawValue({
      ...this.getFormDefaults(),
      ...transactions,
    });
    return new FormGroup<TransactionsFormGroupContent>({
      id: new FormControl(
        { value: transactionsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      accountId: new FormControl(transactionsRawValue.accountId),
      accountCode: new FormControl(transactionsRawValue.accountCode),
      debit: new FormControl(transactionsRawValue.debit),
      credit: new FormControl(transactionsRawValue.credit),
      date: new FormControl(transactionsRawValue.date),
      refDoc: new FormControl(transactionsRawValue.refDoc),
      refId: new FormControl(transactionsRawValue.refId),
      subId: new FormControl(transactionsRawValue.subId),
      source: new FormControl(transactionsRawValue.source),
      paymentTermId: new FormControl(transactionsRawValue.paymentTermId),
      paymentTermName: new FormControl(transactionsRawValue.paymentTermName),
      lmu: new FormControl(transactionsRawValue.lmu),
      lmd: new FormControl(transactionsRawValue.lmd),
    });
  }

  getTransactions(form: TransactionsFormGroup): ITransactions | NewTransactions {
    return this.convertTransactionsRawValueToTransactions(form.getRawValue() as TransactionsFormRawValue | NewTransactionsFormRawValue);
  }

  resetForm(form: TransactionsFormGroup, transactions: TransactionsFormGroupInput): void {
    const transactionsRawValue = this.convertTransactionsToTransactionsRawValue({ ...this.getFormDefaults(), ...transactions });
    form.reset(
      {
        ...transactionsRawValue,
        id: { value: transactionsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TransactionsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
      lmd: currentTime,
    };
  }

  private convertTransactionsRawValueToTransactions(
    rawTransactions: TransactionsFormRawValue | NewTransactionsFormRawValue,
  ): ITransactions | NewTransactions {
    return {
      ...rawTransactions,
      date: dayjs(rawTransactions.date, DATE_TIME_FORMAT),
      lmd: dayjs(rawTransactions.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertTransactionsToTransactionsRawValue(
    transactions: ITransactions | (Partial<NewTransactions> & TransactionsFormDefaults),
  ): TransactionsFormRawValue | PartialWithRequiredKeyOf<NewTransactionsFormRawValue> {
    return {
      ...transactions,
      date: transactions.date ? transactions.date.format(DATE_TIME_FORMAT) : undefined,
      lmd: transactions.lmd ? transactions.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
