import dayjs from 'dayjs/esm';

import { IBinCard, NewBinCard } from './bin-card.model';

export const sampleWithRequiredData: IBinCard = {
  id: 17726,
};

export const sampleWithPartialData: IBinCard = {
  id: 22081,
  qtyIn: 4889.88,
  qtyOut: 30788.77,
  referenceCode: 'astride as',
  recordDate: dayjs('2025-04-01T16:05'),
  batchId: 22198,
  opening: 7066.08,
  referenceDoc: 'accomplished unsightly',
};

export const sampleWithFullData: IBinCard = {
  id: 15481,
  itemID: 762,
  itemCode: 'who institutionalize victoriously',
  reference: 'worriedly behind',
  txDate: dayjs('2025-04-01T15:22'),
  qtyIn: 11966.07,
  qtyOut: 12205.65,
  price: 26790.06,
  lMU: 16851,
  lMD: dayjs('2025-04-02T11:25'),
  referenceCode: 'fumigate lava justly',
  recordDate: dayjs('2025-04-02T04:37'),
  batchId: 15929,
  locationID: 17675,
  locationCode: 'afore',
  opening: 7471.63,
  description: 'guard',
  referenceDoc: 'writ ick plagiarise',
};

export const sampleWithNewData: NewBinCard = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
