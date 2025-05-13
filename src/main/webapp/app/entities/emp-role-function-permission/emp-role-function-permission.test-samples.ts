import { IEmpRoleFunctionPermission, NewEmpRoleFunctionPermission } from './emp-role-function-permission.model';

export const sampleWithRequiredData: IEmpRoleFunctionPermission = {
  id: 10981,
};

export const sampleWithPartialData: IEmpRoleFunctionPermission = {
  id: 22007,
  roleId: 5712,
};

export const sampleWithFullData: IEmpRoleFunctionPermission = {
  id: 16661,
  roleId: 20917,
  functionId: 11369,
};

export const sampleWithNewData: NewEmpRoleFunctionPermission = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
