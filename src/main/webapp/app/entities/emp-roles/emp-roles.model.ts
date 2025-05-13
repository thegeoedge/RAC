export interface IEmpRoles {
  roleId: number;
  roleName?: string | null;
}

export type NewEmpRoles = Omit<IEmpRoles, 'roleId'> & { roleId: null };
