export interface IEmpFunctions {
  functionId: number;
  functionName?: string | null;
  moduleId?: number | null;
}

export type NewEmpFunctions = Omit<IEmpFunctions, 'functionId'> & { functionId: null };
