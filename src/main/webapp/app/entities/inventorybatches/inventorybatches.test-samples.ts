import dayjs from 'dayjs/esm';

import { IInventorybatches, NewInventorybatches } from './inventorybatches.model';

export const sampleWithRequiredData: IInventorybatches = {
  id: 20736,
};

export const sampleWithPartialData: IInventorybatches = {
  id: 21256,
  txdate: dayjs('2024-08-22T19:59'),
  cost: 8852.09,
  price: 18078.04,
  pricewithoutvat: 15111.75,
  lmd: dayjs('2024-08-22T20:13'),
  lineid: 21491,
  manufacturedate: dayjs('2024-08-22T12:24'),
  costtotal: 1324.31,
};

export const sampleWithFullData: IInventorybatches = {
  id: 4078,
  itemid: 16880,
  code: 'via and',
  txdate: dayjs('2024-08-23T05:19'),
  cost: 21855.12,
  price: 18694.12,
  costwithoutvat: 15382.58,
  pricewithoutvat: 22755.84,
  notes: 'majority yearly gosh',
  lmu: 25866,
  lmd: dayjs('2024-08-23T00:53'),
  lineid: 15540,
  manufacturedate: dayjs('2024-08-22T18:57'),
  expiredate: dayjs('2024-08-22T21:15'),
  quantity: 5749.7,
  addeddate: dayjs('2024-08-23T07:36'),
  costtotal: 12679.8,
  returnprice: 9738.63,
};

export const sampleWithNewData: NewInventorybatches = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
