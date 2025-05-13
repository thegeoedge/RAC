export interface IEmpRoleFunctionPermission {
  id: number;
  roleId?: number | null;
  functionId?: number | null;
}

export type NewEmpRoleFunctionPermission = Omit<IEmpRoleFunctionPermission, 'id'> & { id: null };
