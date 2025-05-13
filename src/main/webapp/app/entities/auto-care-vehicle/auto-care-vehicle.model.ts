import dayjs from 'dayjs/esm';

export interface IAutoCareVehicle {
  id: number;
  customerId?: number | null;
  customerName?: string | null;
  customerTel?: string | null;
  vehicleNumber?: string | null;
  brandId?: number | null;
  brandName?: string | null;
  model?: string | null;
  millage?: number | null;
  manufactureYear?: string | null;
  lastServiceDate?: dayjs.Dayjs | null;
  nextServiceDate?: dayjs.Dayjs | null;
  description?: string | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
}

export type NewAutoCareVehicle = Omit<IAutoCareVehicle, 'id'> & { id: null };
