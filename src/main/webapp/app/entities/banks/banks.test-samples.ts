import dayjs from 'dayjs/esm';

import { IBanks, NewBanks } from './banks.model';

export const sampleWithRequiredData: IBanks = {
  id: 27866,
};

export const sampleWithPartialData: IBanks = {
  id: 11681,
  code: 'huzzah needy fooey',
  name: 'given',
  description: 'failing birth',
  lmd: dayjs('2024-10-02T11:34'),
};

export const sampleWithFullData: IBanks = {
  id: 4563,
  code: 'upwardly',
  name: 'overconfidently blah down',
  description: 'in silently',
  lmu: 7035,
  lmd: dayjs('2024-10-03T06:52'),
};

export const sampleWithNewData: NewBanks = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
