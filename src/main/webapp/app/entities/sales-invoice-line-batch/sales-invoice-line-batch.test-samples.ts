import dayjs from 'dayjs/esm';

import { ISalesInvoiceLineBatch, NewSalesInvoiceLineBatch } from './sales-invoice-line-batch.model';

export const sampleWithRequiredData: ISalesInvoiceLineBatch = {
  id: 1002,
};

export const sampleWithPartialData: ISalesInvoiceLineBatch = {
  id: 8490,
  lineId: 7224,
  batchLineId: 21199,
  batchId: 475,
  batchCode: 'yippee questioningly via',
  txDate: dayjs('2025-03-07T01:43'),
  manufactureDate: dayjs('2025-03-07T12:40'),
  qty: 7313.34,
  cost: 30199.37,
  notes: 'since',
  lmu: 26488,
};

export const sampleWithFullData: ISalesInvoiceLineBatch = {
  id: 12364,
  lineId: 17164,
  batchLineId: 25098,
  itemId: 27544,
  code: 'aw dutiful carefully',
  batchId: 18128,
  batchCode: 'near',
  txDate: dayjs('2025-03-07T11:50'),
  manufactureDate: dayjs('2025-03-06T23:59'),
  expiredDate: dayjs('2025-03-06T22:05'),
  qty: 3816.59,
  cost: 25926.3,
  price: 17661.99,
  notes: 'stuff sleet biodegrade',
  lmu: 6737,
  lmd: dayjs('2025-03-07T03:48'),
  nbt: false,
  vat: true,
  addedById: 26151,
};

export const sampleWithNewData: NewSalesInvoiceLineBatch = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
