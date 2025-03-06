import dayjs from 'dayjs/esm';

export interface ISystemSettings {
  id: number;
  key?: string | null;
  lastValue?: string | null;
  nextValue?: string | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
}

export type NewSystemSettings = Omit<ISystemSettings, 'id'> & { id: null };
