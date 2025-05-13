import dayjs from 'dayjs/esm';

import { IPaymentMethod, NewPaymentMethod } from './payment-method.model';

export const sampleWithRequiredData: IPaymentMethod = {
  id: 21285,
};

export const sampleWithPartialData: IPaymentMethod = {
  id: 17810,
  commission: 3267.03,
  lmu: 9298,
};

export const sampleWithFullData: IPaymentMethod = {
  id: 4693,
  paymentMethodName: 'nor unusual now',
  commission: 13234.03,
  companyBankAccountId: 6640,
  lmd: dayjs('2025-04-23T00:18'),
  lmu: 28234,
};

export const sampleWithNewData: NewPaymentMethod = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
