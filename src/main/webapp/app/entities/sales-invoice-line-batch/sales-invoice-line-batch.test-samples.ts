import dayjs from 'dayjs/esm';

import { ISalesInvoiceLineBatch, NewSalesInvoiceLineBatch } from './sales-invoice-line-batch.model';

export const sampleWithRequiredData: ISalesInvoiceLineBatch = {
  id: 3289,
};

export const sampleWithPartialData: ISalesInvoiceLineBatch = {
  id: 11721,
  lineId: 28272,
  itemId: 1678,
  code: 'at truly however',
  cost: 13664.36,
  price: 30254.8,
  notes: 'bludgeon',
  lmu: 21893,
  nbt: false,
  vat: false,
  addedById: 30392,
};

export const sampleWithFullData: ISalesInvoiceLineBatch = {
  id: 15819,
  lineId: 22216,
  batchLineId: 30029,
  itemId: 3077,
  code: 'although',
  batchId: 18275,
  batchCode: 'investigate',
  txDate: dayjs('2025-03-06T16:43'),
  manufactureDate: dayjs('2025-03-06T18:31'),
  expiredDate: dayjs('2025-03-06T21:31'),
  qty: 4418.03,
  cost: 18260.45,
  price: 9667.82,
  notes: 'chatter',
  lmu: 17405,
  lmd: dayjs('2025-03-07T06:27'),
  nbt: true,
  vat: true,
  addedById: 6555,
};

export const sampleWithNewData: NewSalesInvoiceLineBatch = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
