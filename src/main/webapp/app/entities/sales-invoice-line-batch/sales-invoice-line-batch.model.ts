import dayjs from 'dayjs/esm';

export interface ISalesInvoiceLineBatch {
  id: number;
  lineId?: number | null;
  batchLineId?: number | null;
  itemId?: number | null;
  code?: string | null;
  batchId?: number | null;
  batchCode?: string | null;
  txDate?: dayjs.Dayjs | null;
  manufactureDate?: dayjs.Dayjs | null;
  expiredDate?: dayjs.Dayjs | null;
  qty?: number | null;
  cost?: number | null;
  price?: number | null;
  notes?: string | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  nbt?: boolean | null;
  vat?: boolean | null;
  addedById?: number | null;
}

export type NewSalesInvoiceLineBatch = Omit<ISalesInvoiceLineBatch, 'id'> & { id: null };
