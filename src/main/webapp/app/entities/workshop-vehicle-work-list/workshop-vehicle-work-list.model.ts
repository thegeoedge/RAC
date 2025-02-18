import dayjs from 'dayjs/esm';

export interface IWorkshopVehicleWorkList {
  id: number;
  vehicleworkid?: number | null;
  lineid?: number | null;
  workid?: number | null;
  workshopwork?: string | null;
  isjobdone?: boolean | null;
  jobdonedate?: dayjs.Dayjs | null;
  jobnumber?: string | null;
  jobvalue?: number | null;
  estimatevalue?: number | null;
}

export type NewWorkshopVehicleWorkList = Omit<IWorkshopVehicleWorkList, 'id'> & { id: null };
