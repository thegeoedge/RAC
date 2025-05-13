import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../emp-role-function-permission.test-samples';

import { EmpRoleFunctionPermissionFormService } from './emp-role-function-permission-form.service';

describe('EmpRoleFunctionPermission Form Service', () => {
  let service: EmpRoleFunctionPermissionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpRoleFunctionPermissionFormService);
  });

  describe('Service methods', () => {
    describe('createEmpRoleFunctionPermissionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpRoleFunctionPermissionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleId: expect.any(Object),
            functionId: expect.any(Object),
          }),
        );
      });

      it('passing IEmpRoleFunctionPermission should create a new form with FormGroup', () => {
        const formGroup = service.createEmpRoleFunctionPermissionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleId: expect.any(Object),
            functionId: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmpRoleFunctionPermission', () => {
      it('should return NewEmpRoleFunctionPermission for default EmpRoleFunctionPermission initial value', () => {
        const formGroup = service.createEmpRoleFunctionPermissionFormGroup(sampleWithNewData);

        const empRoleFunctionPermission = service.getEmpRoleFunctionPermission(formGroup) as any;

        expect(empRoleFunctionPermission).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmpRoleFunctionPermission for empty EmpRoleFunctionPermission initial value', () => {
        const formGroup = service.createEmpRoleFunctionPermissionFormGroup();

        const empRoleFunctionPermission = service.getEmpRoleFunctionPermission(formGroup) as any;

        expect(empRoleFunctionPermission).toMatchObject({});
      });

      it('should return IEmpRoleFunctionPermission', () => {
        const formGroup = service.createEmpRoleFunctionPermissionFormGroup(sampleWithRequiredData);

        const empRoleFunctionPermission = service.getEmpRoleFunctionPermission(formGroup) as any;

        expect(empRoleFunctionPermission).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmpRoleFunctionPermission should not enable id FormControl', () => {
        const formGroup = service.createEmpRoleFunctionPermissionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmpRoleFunctionPermission should disable id FormControl', () => {
        const formGroup = service.createEmpRoleFunctionPermissionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
