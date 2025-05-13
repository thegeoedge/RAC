import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../auto-care-vehicle.test-samples';

import { AutoCareVehicleFormService } from './auto-care-vehicle-form.service';

describe('AutoCareVehicle Form Service', () => {
  let service: AutoCareVehicleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutoCareVehicleFormService);
  });

  describe('Service methods', () => {
    describe('createAutoCareVehicleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAutoCareVehicleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customerId: expect.any(Object),
            customerName: expect.any(Object),
            customerTel: expect.any(Object),
            vehicleNumber: expect.any(Object),
            brandId: expect.any(Object),
            brandName: expect.any(Object),
            model: expect.any(Object),
            millage: expect.any(Object),
            manufactureYear: expect.any(Object),
            lastServiceDate: expect.any(Object),
            nextServiceDate: expect.any(Object),
            description: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
          }),
        );
      });

      it('passing IAutoCareVehicle should create a new form with FormGroup', () => {
        const formGroup = service.createAutoCareVehicleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customerId: expect.any(Object),
            customerName: expect.any(Object),
            customerTel: expect.any(Object),
            vehicleNumber: expect.any(Object),
            brandId: expect.any(Object),
            brandName: expect.any(Object),
            model: expect.any(Object),
            millage: expect.any(Object),
            manufactureYear: expect.any(Object),
            lastServiceDate: expect.any(Object),
            nextServiceDate: expect.any(Object),
            description: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
          }),
        );
      });
    });

    describe('getAutoCareVehicle', () => {
      it('should return NewAutoCareVehicle for default AutoCareVehicle initial value', () => {
        const formGroup = service.createAutoCareVehicleFormGroup(sampleWithNewData);

        const autoCareVehicle = service.getAutoCareVehicle(formGroup) as any;

        expect(autoCareVehicle).toMatchObject(sampleWithNewData);
      });

      it('should return NewAutoCareVehicle for empty AutoCareVehicle initial value', () => {
        const formGroup = service.createAutoCareVehicleFormGroup();

        const autoCareVehicle = service.getAutoCareVehicle(formGroup) as any;

        expect(autoCareVehicle).toMatchObject({});
      });

      it('should return IAutoCareVehicle', () => {
        const formGroup = service.createAutoCareVehicleFormGroup(sampleWithRequiredData);

        const autoCareVehicle = service.getAutoCareVehicle(formGroup) as any;

        expect(autoCareVehicle).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAutoCareVehicle should not enable id FormControl', () => {
        const formGroup = service.createAutoCareVehicleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAutoCareVehicle should disable id FormControl', () => {
        const formGroup = service.createAutoCareVehicleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
