import dayjs from 'dayjs/esm';

import { ITransactions, NewTransactions } from './transactions.model';

export const sampleWithRequiredData: ITransactions = {
  id: 2261,
};

export const sampleWithPartialData: ITransactions = {
  id: 16881,
  credit: 22151.3,
  date: dayjs('2025-05-04T17:16'),
  refDoc: 'psst even',
  subId: '../fake-data/blob/hipster.txt',
  paymentTermId: 27531,
  paymentTermName: 'whoa shady',
  lmu: 27168,
  lmd: dayjs('2025-05-03T23:56'),
};

export const sampleWithFullData: ITransactions = {
  id: 24023,
  accountId: 24401,
  accountCode: 'norm syringe biodegradable',
  debit: 24865.94,
  credit: 26915.74,
  date: dayjs('2025-05-04T13:43'),
  refDoc: 'who',
  refId: 7078,
  subId: '../fake-data/blob/hipster.txt',
  source: 'indeed',
  paymentTermId: 24161,
  paymentTermName: 'boulevard scram excepting',
  lmu: 18587,
  lmd: dayjs('2025-05-04T03:49'),
};

export const sampleWithNewData: NewTransactions = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
