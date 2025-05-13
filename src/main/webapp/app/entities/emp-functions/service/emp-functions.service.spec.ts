import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEmpFunctions } from '../emp-functions.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../emp-functions.test-samples';

import { EmpFunctionsService } from './emp-functions.service';

const requireRestSample: IEmpFunctions = {
  ...sampleWithRequiredData,
};

describe('EmpFunctions Service', () => {
  let service: EmpFunctionsService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmpFunctions | IEmpFunctions[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EmpFunctionsService);
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

    it('should create a EmpFunctions', () => {
      const empFunctions = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(empFunctions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmpFunctions', () => {
      const empFunctions = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(empFunctions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmpFunctions', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmpFunctions', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmpFunctions', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmpFunctionsToCollectionIfMissing', () => {
      it('should add a EmpFunctions to an empty array', () => {
        const empFunctions: IEmpFunctions = sampleWithRequiredData;
        expectedResult = service.addEmpFunctionsToCollectionIfMissing([], empFunctions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empFunctions);
      });

      it('should not add a EmpFunctions to an array that contains it', () => {
        const empFunctions: IEmpFunctions = sampleWithRequiredData;
        const empFunctionsCollection: IEmpFunctions[] = [
          {
            ...empFunctions,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmpFunctionsToCollectionIfMissing(empFunctionsCollection, empFunctions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmpFunctions to an array that doesn't contain it", () => {
        const empFunctions: IEmpFunctions = sampleWithRequiredData;
        const empFunctionsCollection: IEmpFunctions[] = [sampleWithPartialData];
        expectedResult = service.addEmpFunctionsToCollectionIfMissing(empFunctionsCollection, empFunctions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empFunctions);
      });

      it('should add only unique EmpFunctions to an array', () => {
        const empFunctionsArray: IEmpFunctions[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const empFunctionsCollection: IEmpFunctions[] = [sampleWithRequiredData];
        expectedResult = service.addEmpFunctionsToCollectionIfMissing(empFunctionsCollection, ...empFunctionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const empFunctions: IEmpFunctions = sampleWithRequiredData;
        const empFunctions2: IEmpFunctions = sampleWithPartialData;
        expectedResult = service.addEmpFunctionsToCollectionIfMissing([], empFunctions, empFunctions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empFunctions);
        expect(expectedResult).toContain(empFunctions2);
      });

      it('should accept null and undefined values', () => {
        const empFunctions: IEmpFunctions = sampleWithRequiredData;
        expectedResult = service.addEmpFunctionsToCollectionIfMissing([], null, empFunctions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empFunctions);
      });

      it('should return initial array if no EmpFunctions is added', () => {
        const empFunctionsCollection: IEmpFunctions[] = [sampleWithRequiredData];
        expectedResult = service.addEmpFunctionsToCollectionIfMissing(empFunctionsCollection, undefined, null);
        expect(expectedResult).toEqual(empFunctionsCollection);
      });
    });

    describe('compareEmpFunctions', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmpFunctions(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { functionId: 15909 };
        const entity2 = null;

        const compareResult1 = service.compareEmpFunctions(entity1, entity2);
        const compareResult2 = service.compareEmpFunctions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { functionId: 15909 };
        const entity2 = { functionId: 12769 };

        const compareResult1 = service.compareEmpFunctions(entity1, entity2);
        const compareResult2 = service.compareEmpFunctions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { functionId: 15909 };
        const entity2 = { functionId: 15909 };

        const compareResult1 = service.compareEmpFunctions(entity1, entity2);
        const compareResult2 = service.compareEmpFunctions(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
