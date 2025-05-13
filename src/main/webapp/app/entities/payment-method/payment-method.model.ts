import dayjs from 'dayjs/esm';

export interface IPaymentMethod {
  id: number;
  paymentMethodName?: string | null;
  commission?: number | null;
  companyBankAccountId?: number | null;
  lmd?: dayjs.Dayjs | null;
  lmu?: number | null;
}

export type NewPaymentMethod = Omit<IPaymentMethod, 'id'> & { id: null };
