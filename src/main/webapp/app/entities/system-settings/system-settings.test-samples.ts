import dayjs from 'dayjs/esm';

import { ISystemSettings, NewSystemSettings } from './system-settings.model';

export const sampleWithRequiredData: ISystemSettings = {
  id: 24813,
};

export const sampleWithPartialData: ISystemSettings = {
  id: 22798,
  key: 'before so',
  lastValue: 'marten',
  nextValue: 'instead',
};

export const sampleWithFullData: ISystemSettings = {
  id: 14971,
  key: 'if lively',
  lastValue: 'kiss though plus',
  nextValue: 'why',
  lmu: 29529,
  lmd: dayjs('2025-03-04T16:06'),
};

export const sampleWithNewData: NewSystemSettings = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
