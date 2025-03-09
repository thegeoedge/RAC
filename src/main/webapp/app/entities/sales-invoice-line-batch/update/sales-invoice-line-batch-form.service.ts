import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISalesInvoiceLineBatch, NewSalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISalesInvoiceLineBatch for edit and NewSalesInvoiceLineBatchFormGroupInput for create.
 */
type SalesInvoiceLineBatchFormGroupInput = ISalesInvoiceLineBatch | PartialWithRequiredKeyOf<NewSalesInvoiceLineBatch>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISalesInvoiceLineBatch | NewSalesInvoiceLineBatch> = Omit<
  T,
  'txDate' | 'manufactureDate' | 'expiredDate' | 'lmd'
> & {
  txDate?: string | null;
  manufactureDate?: string | null;
  expiredDate?: string | null;
  lmd?: string | null;
};

type SalesInvoiceLineBatchFormRawValue = FormValueOf<ISalesInvoiceLineBatch>;

type NewSalesInvoiceLineBatchFormRawValue = FormValueOf<NewSalesInvoiceLineBatch>;

type SalesInvoiceLineBatchFormDefaults = Pick<
  NewSalesInvoiceLineBatch,
  'id' | 'txDate' | 'manufactureDate' | 'expiredDate' | 'lmd' | 'nbt' | 'vat'
>;

type SalesInvoiceLineBatchFormGroupContent = {
  id: FormControl<SalesInvoiceLineBatchFormRawValue['id'] | NewSalesInvoiceLineBatch['id']>;
  lineId: FormControl<SalesInvoiceLineBatchFormRawValue['lineId']>;
  batchLineId: FormControl<SalesInvoiceLineBatchFormRawValue['batchLineId']>;
  itemId: FormControl<SalesInvoiceLineBatchFormRawValue['itemId']>;
  code: FormControl<SalesInvoiceLineBatchFormRawValue['code']>;
  batchId: FormControl<SalesInvoiceLineBatchFormRawValue['batchId']>;
  batchCode: FormControl<SalesInvoiceLineBatchFormRawValue['batchCode']>;
  txDate: FormControl<SalesInvoiceLineBatchFormRawValue['txDate']>;
  manufactureDate: FormControl<SalesInvoiceLineBatchFormRawValue['manufactureDate']>;
  expiredDate: FormControl<SalesInvoiceLineBatchFormRawValue['expiredDate']>;
  qty: FormControl<SalesInvoiceLineBatchFormRawValue['qty']>;
  cost: FormControl<SalesInvoiceLineBatchFormRawValue['cost']>;
  price: FormControl<SalesInvoiceLineBatchFormRawValue['price']>;
  notes: FormControl<SalesInvoiceLineBatchFormRawValue['notes']>;
  lmu: FormControl<SalesInvoiceLineBatchFormRawValue['lmu']>;
  lmd: FormControl<SalesInvoiceLineBatchFormRawValue['lmd']>;
  nbt: FormControl<SalesInvoiceLineBatchFormRawValue['nbt']>;
  vat: FormControl<SalesInvoiceLineBatchFormRawValue['vat']>;
  addedById: FormControl<SalesInvoiceLineBatchFormRawValue['addedById']>;
};

export type SalesInvoiceLineBatchFormGroup = FormGroup<SalesInvoiceLineBatchFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceLineBatchFormService {
  createSalesInvoiceLineBatchFormGroup(
    salesInvoiceLineBatch: SalesInvoiceLineBatchFormGroupInput = { id: null },
  ): SalesInvoiceLineBatchFormGroup {
    const salesInvoiceLineBatchRawValue = this.convertSalesInvoiceLineBatchToSalesInvoiceLineBatchRawValue({
      ...this.getFormDefaults(),
      ...salesInvoiceLineBatch,
    });
    return new FormGroup<SalesInvoiceLineBatchFormGroupContent>({
      id: new FormControl(
        { value: salesInvoiceLineBatchRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lineId: new FormControl(salesInvoiceLineBatchRawValue.lineId),
      batchLineId: new FormControl(salesInvoiceLineBatchRawValue.batchLineId),
      itemId: new FormControl(salesInvoiceLineBatchRawValue.itemId),
      code: new FormControl(salesInvoiceLineBatchRawValue.code),
      batchId: new FormControl(salesInvoiceLineBatchRawValue.batchId),
      batchCode: new FormControl(salesInvoiceLineBatchRawValue.batchCode),
      txDate: new FormControl(salesInvoiceLineBatchRawValue.txDate),
      manufactureDate: new FormControl(salesInvoiceLineBatchRawValue.manufactureDate),
      expiredDate: new FormControl(salesInvoiceLineBatchRawValue.expiredDate),
      qty: new FormControl(salesInvoiceLineBatchRawValue.qty),
      cost: new FormControl(salesInvoiceLineBatchRawValue.cost),
      price: new FormControl(salesInvoiceLineBatchRawValue.price),
      notes: new FormControl(salesInvoiceLineBatchRawValue.notes),
      lmu: new FormControl(salesInvoiceLineBatchRawValue.lmu),
      lmd: new FormControl(salesInvoiceLineBatchRawValue.lmd),
      nbt: new FormControl(salesInvoiceLineBatchRawValue.nbt),
      vat: new FormControl(salesInvoiceLineBatchRawValue.vat),
      addedById: new FormControl(salesInvoiceLineBatchRawValue.addedById),
    });
  }

  getSalesInvoiceLineBatch(form: SalesInvoiceLineBatchFormGroup): ISalesInvoiceLineBatch | NewSalesInvoiceLineBatch {
    return this.convertSalesInvoiceLineBatchRawValueToSalesInvoiceLineBatch(
      form.getRawValue() as SalesInvoiceLineBatchFormRawValue | NewSalesInvoiceLineBatchFormRawValue,
    );
  }

  resetForm(form: SalesInvoiceLineBatchFormGroup, salesInvoiceLineBatch: SalesInvoiceLineBatchFormGroupInput): void {
    const salesInvoiceLineBatchRawValue = this.convertSalesInvoiceLineBatchToSalesInvoiceLineBatchRawValue({
      ...this.getFormDefaults(),
      ...salesInvoiceLineBatch,
    });
    form.reset(
      {
        ...salesInvoiceLineBatchRawValue,
        id: { value: salesInvoiceLineBatchRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SalesInvoiceLineBatchFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      txDate: currentTime,
      manufactureDate: currentTime,
      expiredDate: currentTime,
      lmd: currentTime,
      nbt: false,
      vat: false,
    };
  }

  private convertSalesInvoiceLineBatchRawValueToSalesInvoiceLineBatch(
    rawSalesInvoiceLineBatch: SalesInvoiceLineBatchFormRawValue | NewSalesInvoiceLineBatchFormRawValue,
  ): ISalesInvoiceLineBatch | NewSalesInvoiceLineBatch {
    return {
      ...rawSalesInvoiceLineBatch,
      txDate: dayjs(rawSalesInvoiceLineBatch.txDate, DATE_TIME_FORMAT),
      manufactureDate: dayjs(rawSalesInvoiceLineBatch.manufactureDate, DATE_TIME_FORMAT),
      expiredDate: dayjs(rawSalesInvoiceLineBatch.expiredDate, DATE_TIME_FORMAT),
      lmd: dayjs(rawSalesInvoiceLineBatch.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertSalesInvoiceLineBatchToSalesInvoiceLineBatchRawValue(
    salesInvoiceLineBatch: ISalesInvoiceLineBatch | (Partial<NewSalesInvoiceLineBatch> & SalesInvoiceLineBatchFormDefaults),
  ): SalesInvoiceLineBatchFormRawValue | PartialWithRequiredKeyOf<NewSalesInvoiceLineBatchFormRawValue> {
    return {
      ...salesInvoiceLineBatch,
      txDate: salesInvoiceLineBatch.txDate ? salesInvoiceLineBatch.txDate.format(DATE_TIME_FORMAT) : undefined,
      manufactureDate: salesInvoiceLineBatch.manufactureDate ? salesInvoiceLineBatch.manufactureDate.format(DATE_TIME_FORMAT) : undefined,
      expiredDate: salesInvoiceLineBatch.expiredDate ? salesInvoiceLineBatch.expiredDate.format(DATE_TIME_FORMAT) : undefined,
      lmd: salesInvoiceLineBatch.lmd ? salesInvoiceLineBatch.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
