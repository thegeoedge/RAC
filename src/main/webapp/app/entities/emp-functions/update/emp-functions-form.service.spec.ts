import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../emp-functions.test-samples';

import { EmpFunctionsFormService } from './emp-functions-form.service';

describe('EmpFunctions Form Service', () => {
  let service: EmpFunctionsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpFunctionsFormService);
  });

  describe('Service methods', () => {
    describe('createEmpFunctionsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpFunctionsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            functionId: expect.any(Object),
            functionName: expect.any(Object),
            moduleId: expect.any(Object),
          }),
        );
      });

      it('passing IEmpFunctions should create a new form with FormGroup', () => {
        const formGroup = service.createEmpFunctionsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            functionId: expect.any(Object),
            functionName: expect.any(Object),
            moduleId: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmpFunctions', () => {
      it('should return NewEmpFunctions for default EmpFunctions initial value', () => {
        const formGroup = service.createEmpFunctionsFormGroup(sampleWithNewData);

        const empFunctions = service.getEmpFunctions(formGroup) as any;

        expect(empFunctions).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmpFunctions for empty EmpFunctions initial value', () => {
        const formGroup = service.createEmpFunctionsFormGroup();

        const empFunctions = service.getEmpFunctions(formGroup) as any;

        expect(empFunctions).toMatchObject({});
      });

      it('should return IEmpFunctions', () => {
        const formGroup = service.createEmpFunctionsFormGroup(sampleWithRequiredData);

        const empFunctions = service.getEmpFunctions(formGroup) as any;

        expect(empFunctions).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmpFunctions should not enable functionId FormControl', () => {
        const formGroup = service.createEmpFunctionsFormGroup();
        expect(formGroup.controls.functionId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.functionId.disabled).toBe(true);
      });

      it('passing NewEmpFunctions should disable functionId FormControl', () => {
        const formGroup = service.createEmpFunctionsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.functionId.disabled).toBe(true);

        service.resetForm(formGroup, { functionId: null });

        expect(formGroup.controls.functionId.disabled).toBe(true);
      });
    });
  });
});
