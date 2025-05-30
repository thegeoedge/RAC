import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IBillingserviceoptionvalues } from '../billingserviceoptionvalues.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../billingserviceoptionvalues.test-samples';

import { BillingserviceoptionvaluesService, RestBillingserviceoptionvalues } from './billingserviceoptionvalues.service';

const requireRestSample: RestBillingserviceoptionvalues = {
  ...sampleWithRequiredData,
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('Billingserviceoptionvalues Service', () => {
  let service: BillingserviceoptionvaluesService;
  let httpMock: HttpTestingController;
  let expectedResult: IBillingserviceoptionvalues | IBillingserviceoptionvalues[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BillingserviceoptionvaluesService);
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

    it('should create a Billingserviceoptionvalues', () => {
      const billingserviceoptionvalues = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(billingserviceoptionvalues).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Billingserviceoptionvalues', () => {
      const billingserviceoptionvalues = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(billingserviceoptionvalues).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Billingserviceoptionvalues', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Billingserviceoptionvalues', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Billingserviceoptionvalues', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBillingserviceoptionvaluesToCollectionIfMissing', () => {
      it('should add a Billingserviceoptionvalues to an empty array', () => {
        const billingserviceoptionvalues: IBillingserviceoptionvalues = sampleWithRequiredData;
        expectedResult = service.addBillingserviceoptionvaluesToCollectionIfMissing([], billingserviceoptionvalues);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(billingserviceoptionvalues);
      });

      it('should not add a Billingserviceoptionvalues to an array that contains it', () => {
        const billingserviceoptionvalues: IBillingserviceoptionvalues = sampleWithRequiredData;
        const billingserviceoptionvaluesCollection: IBillingserviceoptionvalues[] = [
          {
            ...billingserviceoptionvalues,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBillingserviceoptionvaluesToCollectionIfMissing(
          billingserviceoptionvaluesCollection,
          billingserviceoptionvalues,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Billingserviceoptionvalues to an array that doesn't contain it", () => {
        const billingserviceoptionvalues: IBillingserviceoptionvalues = sampleWithRequiredData;
        const billingserviceoptionvaluesCollection: IBillingserviceoptionvalues[] = [sampleWithPartialData];
        expectedResult = service.addBillingserviceoptionvaluesToCollectionIfMissing(
          billingserviceoptionvaluesCollection,
          billingserviceoptionvalues,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(billingserviceoptionvalues);
      });

      it('should add only unique Billingserviceoptionvalues to an array', () => {
        const billingserviceoptionvaluesArray: IBillingserviceoptionvalues[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const billingserviceoptionvaluesCollection: IBillingserviceoptionvalues[] = [sampleWithRequiredData];
        expectedResult = service.addBillingserviceoptionvaluesToCollectionIfMissing(
          billingserviceoptionvaluesCollection,
          ...billingserviceoptionvaluesArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const billingserviceoptionvalues: IBillingserviceoptionvalues = sampleWithRequiredData;
        const billingserviceoptionvalues2: IBillingserviceoptionvalues = sampleWithPartialData;
        expectedResult = service.addBillingserviceoptionvaluesToCollectionIfMissing(
          [],
          billingserviceoptionvalues,
          billingserviceoptionvalues2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(billingserviceoptionvalues);
        expect(expectedResult).toContain(billingserviceoptionvalues2);
      });

      it('should accept null and undefined values', () => {
        const billingserviceoptionvalues: IBillingserviceoptionvalues = sampleWithRequiredData;
        expectedResult = service.addBillingserviceoptionvaluesToCollectionIfMissing([], null, billingserviceoptionvalues, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(billingserviceoptionvalues);
      });

      it('should return initial array if no Billingserviceoptionvalues is added', () => {
        const billingserviceoptionvaluesCollection: IBillingserviceoptionvalues[] = [sampleWithRequiredData];
        expectedResult = service.addBillingserviceoptionvaluesToCollectionIfMissing(billingserviceoptionvaluesCollection, undefined, null);
        expect(expectedResult).toEqual(billingserviceoptionvaluesCollection);
      });
    });

    describe('compareBillingserviceoptionvalues', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBillingserviceoptionvalues(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBillingserviceoptionvalues(entity1, entity2);
        const compareResult2 = service.compareBillingserviceoptionvalues(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBillingserviceoptionvalues(entity1, entity2);
        const compareResult2 = service.compareBillingserviceoptionvalues(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBillingserviceoptionvalues(entity1, entity2);
        const compareResult2 = service.compareBillingserviceoptionvalues(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
