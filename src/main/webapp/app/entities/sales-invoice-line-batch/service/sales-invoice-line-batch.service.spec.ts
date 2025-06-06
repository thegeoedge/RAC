import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../sales-invoice-line-batch.test-samples';

import { RestSalesInvoiceLineBatch, SalesInvoiceLineBatchService } from './sales-invoice-line-batch.service';

const requireRestSample: RestSalesInvoiceLineBatch = {
  ...sampleWithRequiredData,
  txDate: sampleWithRequiredData.txDate?.toJSON(),
  manufactureDate: sampleWithRequiredData.manufactureDate?.toJSON(),
  expiredDate: sampleWithRequiredData.expiredDate?.toJSON(),
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('SalesInvoiceLineBatch Service', () => {
  let service: SalesInvoiceLineBatchService;
  let httpMock: HttpTestingController;
  let expectedResult: ISalesInvoiceLineBatch | ISalesInvoiceLineBatch[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SalesInvoiceLineBatchService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SalesInvoiceLineBatch', () => {
      const salesInvoiceLineBatch = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(salesInvoiceLineBatch).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesInvoiceLineBatch', () => {
      const salesInvoiceLineBatch = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(salesInvoiceLineBatch).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesInvoiceLineBatch', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesInvoiceLineBatch', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SalesInvoiceLineBatch', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSalesInvoiceLineBatchToCollectionIfMissing', () => {
      it('should add a SalesInvoiceLineBatch to an empty array', () => {
        const salesInvoiceLineBatch: ISalesInvoiceLineBatch = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceLineBatchToCollectionIfMissing([], salesInvoiceLineBatch);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceLineBatch);
      });

      it('should not add a SalesInvoiceLineBatch to an array that contains it', () => {
        const salesInvoiceLineBatch: ISalesInvoiceLineBatch = sampleWithRequiredData;
        const salesInvoiceLineBatchCollection: ISalesInvoiceLineBatch[] = [
          {
            ...salesInvoiceLineBatch,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSalesInvoiceLineBatchToCollectionIfMissing(salesInvoiceLineBatchCollection, salesInvoiceLineBatch);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesInvoiceLineBatch to an array that doesn't contain it", () => {
        const salesInvoiceLineBatch: ISalesInvoiceLineBatch = sampleWithRequiredData;
        const salesInvoiceLineBatchCollection: ISalesInvoiceLineBatch[] = [sampleWithPartialData];
        expectedResult = service.addSalesInvoiceLineBatchToCollectionIfMissing(salesInvoiceLineBatchCollection, salesInvoiceLineBatch);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceLineBatch);
      });

      it('should add only unique SalesInvoiceLineBatch to an array', () => {
        const salesInvoiceLineBatchArray: ISalesInvoiceLineBatch[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const salesInvoiceLineBatchCollection: ISalesInvoiceLineBatch[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceLineBatchToCollectionIfMissing(
          salesInvoiceLineBatchCollection,
          ...salesInvoiceLineBatchArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesInvoiceLineBatch: ISalesInvoiceLineBatch = sampleWithRequiredData;
        const salesInvoiceLineBatch2: ISalesInvoiceLineBatch = sampleWithPartialData;
        expectedResult = service.addSalesInvoiceLineBatchToCollectionIfMissing([], salesInvoiceLineBatch, salesInvoiceLineBatch2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceLineBatch);
        expect(expectedResult).toContain(salesInvoiceLineBatch2);
      });

      it('should accept null and undefined values', () => {
        const salesInvoiceLineBatch: ISalesInvoiceLineBatch = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceLineBatchToCollectionIfMissing([], null, salesInvoiceLineBatch, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceLineBatch);
      });

      it('should return initial array if no SalesInvoiceLineBatch is added', () => {
        const salesInvoiceLineBatchCollection: ISalesInvoiceLineBatch[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceLineBatchToCollectionIfMissing(salesInvoiceLineBatchCollection, undefined, null);
        expect(expectedResult).toEqual(salesInvoiceLineBatchCollection);
      });
    });

    describe('compareSalesInvoiceLineBatch', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSalesInvoiceLineBatch(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 9680 };
        const entity2 = null;

        const compareResult1 = service.compareSalesInvoiceLineBatch(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLineBatch(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 9680 };
        const entity2 = { id: 9777 };

        const compareResult1 = service.compareSalesInvoiceLineBatch(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLineBatch(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 9680 };
        const entity2 = { id: 9680 };

        const compareResult1 = service.compareSalesInvoiceLineBatch(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLineBatch(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
