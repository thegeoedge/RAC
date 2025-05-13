import dayjs from 'dayjs/esm';

import { IAutoCareVehicle, NewAutoCareVehicle } from './auto-care-vehicle.model';

export const sampleWithRequiredData: IAutoCareVehicle = {
  id: 11747,
};

export const sampleWithPartialData: IAutoCareVehicle = {
  id: 28380,
  customerId: 9229,
  customerName: 'plus barring',
  vehicleNumber: 'farmer excepting',
  brandName: 'interviewer mmm tall',
  millage: 2578.85,
  manufactureYear: 'factorize towards',
  lastServiceDate: dayjs('2025-04-05T18:13'),
  nextServiceDate: dayjs('2025-04-06T00:33'),
  description: 'wallaby before nippy',
  lmu: 18010,
  lmd: dayjs('2025-04-05T21:00'),
};

export const sampleWithFullData: IAutoCareVehicle = {
  id: 17824,
  customerId: 27077,
  customerName: 'for deceivingly potentially',
  customerTel: 'twist',
  vehicleNumber: 'muted',
  brandId: 7976,
  brandName: 'embalm arrange',
  model: 'likewise wide shimmering',
  millage: 4794.04,
  manufactureYear: 'er',
  lastServiceDate: dayjs('2025-04-06T09:34'),
  nextServiceDate: dayjs('2025-04-06T09:23'),
  description: 'ugh through aha',
  lmu: 8804,
  lmd: dayjs('2025-04-06T09:32'),
};

export const sampleWithNewData: NewAutoCareVehicle = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
