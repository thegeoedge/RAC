import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../transactions.test-samples';

import { TransactionsFormService } from './transactions-form.service';

describe('Transactions Form Service', () => {
  let service: TransactionsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionsFormService);
  });

  describe('Service methods', () => {
    describe('createTransactionsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTransactionsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountId: expect.any(Object),
            accountCode: expect.any(Object),
            debit: expect.any(Object),
            credit: expect.any(Object),
            date: expect.any(Object),
            refDoc: expect.any(Object),
            refId: expect.any(Object),
            subId: expect.any(Object),
            source: expect.any(Object),
            paymentTermId: expect.any(Object),
            paymentTermName: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
          }),
        );
      });

      it('passing ITransactions should create a new form with FormGroup', () => {
        const formGroup = service.createTransactionsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountId: expect.any(Object),
            accountCode: expect.any(Object),
            debit: expect.any(Object),
            credit: expect.any(Object),
            date: expect.any(Object),
            refDoc: expect.any(Object),
            refId: expect.any(Object),
            subId: expect.any(Object),
            source: expect.any(Object),
            paymentTermId: expect.any(Object),
            paymentTermName: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
          }),
        );
      });
    });

    describe('getTransactions', () => {
      it('should return NewTransactions for default Transactions initial value', () => {
        const formGroup = service.createTransactionsFormGroup(sampleWithNewData);

        const transactions = service.getTransactions(formGroup) as any;

        expect(transactions).toMatchObject(sampleWithNewData);
      });

      it('should return NewTransactions for empty Transactions initial value', () => {
        const formGroup = service.createTransactionsFormGroup();

        const transactions = service.getTransactions(formGroup) as any;

        expect(transactions).toMatchObject({});
      });

      it('should return ITransactions', () => {
        const formGroup = service.createTransactionsFormGroup(sampleWithRequiredData);

        const transactions = service.getTransactions(formGroup) as any;

        expect(transactions).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITransactions should not enable id FormControl', () => {
        const formGroup = service.createTransactionsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTransactions should disable id FormControl', () => {
        const formGroup = service.createTransactionsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
