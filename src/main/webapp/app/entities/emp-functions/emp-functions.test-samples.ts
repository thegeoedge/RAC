import { IEmpFunctions, NewEmpFunctions } from './emp-functions.model';

export const sampleWithRequiredData: IEmpFunctions = {
  functionId: 30686,
};

export const sampleWithPartialData: IEmpFunctions = {
  functionId: 1565,
  functionName: 'silent retrospectivity gosh',
};

export const sampleWithFullData: IEmpFunctions = {
  functionId: 5258,
  functionName: 'culture fill',
  moduleId: 13487,
};

export const sampleWithNewData: NewEmpFunctions = {
  functionId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
