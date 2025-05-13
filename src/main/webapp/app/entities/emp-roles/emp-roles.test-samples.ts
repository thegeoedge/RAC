import { IEmpRoles, NewEmpRoles } from './emp-roles.model';

export const sampleWithRequiredData: IEmpRoles = {
  roleId: 28646,
};

export const sampleWithPartialData: IEmpRoles = {
  roleId: 16427,
  roleName: 'hmph scram crystallize',
};

export const sampleWithFullData: IEmpRoles = {
  roleId: 17038,
  roleName: 'gummy inasmuch long',
};

export const sampleWithNewData: NewEmpRoles = {
  roleId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
