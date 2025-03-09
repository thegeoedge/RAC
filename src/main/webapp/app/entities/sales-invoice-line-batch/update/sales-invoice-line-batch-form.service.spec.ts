import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sales-invoice-line-batch.test-samples';

import { SalesInvoiceLineBatchFormService } from './sales-invoice-line-batch-form.service';

describe('SalesInvoiceLineBatch Form Service', () => {
  let service: SalesInvoiceLineBatchFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalesInvoiceLineBatchFormService);
  });

  describe('Service methods', () => {
    describe('createSalesInvoiceLineBatchFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSalesInvoiceLineBatchFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineId: expect.any(Object),
            batchLineId: expect.any(Object),
            itemId: expect.any(Object),
            code: expect.any(Object),
            batchId: expect.any(Object),
            batchCode: expect.any(Object),
            txDate: expect.any(Object),
            manufactureDate: expect.any(Object),
            expiredDate: expect.any(Object),
            qty: expect.any(Object),
            cost: expect.any(Object),
            price: expect.any(Object),
            notes: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
            addedById: expect.any(Object),
          }),
        );
      });

      it('passing ISalesInvoiceLineBatch should create a new form with FormGroup', () => {
        const formGroup = service.createSalesInvoiceLineBatchFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineId: expect.any(Object),
            batchLineId: expect.any(Object),
            itemId: expect.any(Object),
            code: expect.any(Object),
            batchId: expect.any(Object),
            batchCode: expect.any(Object),
            txDate: expect.any(Object),
            manufactureDate: expect.any(Object),
            expiredDate: expect.any(Object),
            qty: expect.any(Object),
            cost: expect.any(Object),
            price: expect.any(Object),
            notes: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
            addedById: expect.any(Object),
          }),
        );
      });
    });

    describe('getSalesInvoiceLineBatch', () => {
      it('should return NewSalesInvoiceLineBatch for default SalesInvoiceLineBatch initial value', () => {
        const formGroup = service.createSalesInvoiceLineBatchFormGroup(sampleWithNewData);

        const salesInvoiceLineBatch = service.getSalesInvoiceLineBatch(formGroup) as any;

        expect(salesInvoiceLineBatch).toMatchObject(sampleWithNewData);
      });

      it('should return NewSalesInvoiceLineBatch for empty SalesInvoiceLineBatch initial value', () => {
        const formGroup = service.createSalesInvoiceLineBatchFormGroup();

        const salesInvoiceLineBatch = service.getSalesInvoiceLineBatch(formGroup) as any;

        expect(salesInvoiceLineBatch).toMatchObject({});
      });

      it('should return ISalesInvoiceLineBatch', () => {
        const formGroup = service.createSalesInvoiceLineBatchFormGroup(sampleWithRequiredData);

        const salesInvoiceLineBatch = service.getSalesInvoiceLineBatch(formGroup) as any;

        expect(salesInvoiceLineBatch).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISalesInvoiceLineBatch should not enable id FormControl', () => {
        const formGroup = service.createSalesInvoiceLineBatchFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSalesInvoiceLineBatch should disable id FormControl', () => {
        const formGroup = service.createSalesInvoiceLineBatchFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
