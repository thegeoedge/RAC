import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../emp-roles.test-samples';

import { EmpRolesFormService } from './emp-roles-form.service';

describe('EmpRoles Form Service', () => {
  let service: EmpRolesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpRolesFormService);
  });

  describe('Service methods', () => {
    describe('createEmpRolesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpRolesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            roleId: expect.any(Object),
            roleName: expect.any(Object),
          }),
        );
      });

      it('passing IEmpRoles should create a new form with FormGroup', () => {
        const formGroup = service.createEmpRolesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            roleId: expect.any(Object),
            roleName: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmpRoles', () => {
      it('should return NewEmpRoles for default EmpRoles initial value', () => {
        const formGroup = service.createEmpRolesFormGroup(sampleWithNewData);

        const empRoles = service.getEmpRoles(formGroup) as any;

        expect(empRoles).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmpRoles for empty EmpRoles initial value', () => {
        const formGroup = service.createEmpRolesFormGroup();

        const empRoles = service.getEmpRoles(formGroup) as any;

        expect(empRoles).toMatchObject({});
      });

      it('should return IEmpRoles', () => {
        const formGroup = service.createEmpRolesFormGroup(sampleWithRequiredData);

        const empRoles = service.getEmpRoles(formGroup) as any;

        expect(empRoles).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmpRoles should not enable roleId FormControl', () => {
        const formGroup = service.createEmpRolesFormGroup();
        expect(formGroup.controls.roleId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.roleId.disabled).toBe(true);
      });

      it('passing NewEmpRoles should disable roleId FormControl', () => {
        const formGroup = service.createEmpRolesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.roleId.disabled).toBe(true);

        service.resetForm(formGroup, { roleId: null });

        expect(formGroup.controls.roleId.disabled).toBe(true);
      });
    });
  });
});
