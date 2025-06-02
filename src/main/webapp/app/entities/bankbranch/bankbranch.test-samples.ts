import { IBankbranch, NewBankbranch } from './bankbranch.model';

export const sampleWithRequiredData: IBankbranch = {
  id: 20977,
};

export const sampleWithPartialData: IBankbranch = {
  id: 31094,
  branchcode: 'beret pants',
};

export const sampleWithFullData: IBankbranch = {
  id: 20021,
  bankcode: 'spotless',
  branchcode: 'wrongly coliseum',
  branchname: 'wrong and briefly',
};

export const sampleWithNewData: NewBankbranch = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
