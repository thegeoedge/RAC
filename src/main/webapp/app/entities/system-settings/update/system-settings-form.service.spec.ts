import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../system-settings.test-samples';

import { SystemSettingsFormService } from './system-settings-form.service';

describe('SystemSettings Form Service', () => {
  let service: SystemSettingsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SystemSettingsFormService);
  });

  describe('Service methods', () => {
    describe('createSystemSettingsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSystemSettingsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            key: expect.any(Object),
            lastValue: expect.any(Object),
            nextValue: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
          }),
        );
      });

      it('passing ISystemSettings should create a new form with FormGroup', () => {
        const formGroup = service.createSystemSettingsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            key: expect.any(Object),
            lastValue: expect.any(Object),
            nextValue: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
          }),
        );
      });
    });

    describe('getSystemSettings', () => {
      it('should return NewSystemSettings for default SystemSettings initial value', () => {
        const formGroup = service.createSystemSettingsFormGroup(sampleWithNewData);

        const systemSettings = service.getSystemSettings(formGroup) as any;

        expect(systemSettings).toMatchObject(sampleWithNewData);
      });

      it('should return NewSystemSettings for empty SystemSettings initial value', () => {
        const formGroup = service.createSystemSettingsFormGroup();

        const systemSettings = service.getSystemSettings(formGroup) as any;

        expect(systemSettings).toMatchObject({});
      });

      it('should return ISystemSettings', () => {
        const formGroup = service.createSystemSettingsFormGroup(sampleWithRequiredData);

        const systemSettings = service.getSystemSettings(formGroup) as any;

        expect(systemSettings).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISystemSettings should not enable id FormControl', () => {
        const formGroup = service.createSystemSettingsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSystemSettings should disable id FormControl', () => {
        const formGroup = service.createSystemSettingsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
