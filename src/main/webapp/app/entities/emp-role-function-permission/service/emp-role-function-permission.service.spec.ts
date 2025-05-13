import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEmpRoleFunctionPermission } from '../emp-role-function-permission.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../emp-role-function-permission.test-samples';

import { EmpRoleFunctionPermissionService } from './emp-role-function-permission.service';

const requireRestSample: IEmpRoleFunctionPermission = {
  ...sampleWithRequiredData,
};

describe('EmpRoleFunctionPermission Service', () => {
  let service: EmpRoleFunctionPermissionService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmpRoleFunctionPermission | IEmpRoleFunctionPermission[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EmpRoleFunctionPermissionService);
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

    it('should create a EmpRoleFunctionPermission', () => {
      const empRoleFunctionPermission = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(empRoleFunctionPermission).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmpRoleFunctionPermission', () => {
      const empRoleFunctionPermission = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(empRoleFunctionPermission).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmpRoleFunctionPermission', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmpRoleFunctionPermission', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmpRoleFunctionPermission', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmpRoleFunctionPermissionToCollectionIfMissing', () => {
      it('should add a EmpRoleFunctionPermission to an empty array', () => {
        const empRoleFunctionPermission: IEmpRoleFunctionPermission = sampleWithRequiredData;
        expectedResult = service.addEmpRoleFunctionPermissionToCollectionIfMissing([], empRoleFunctionPermission);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empRoleFunctionPermission);
      });

      it('should not add a EmpRoleFunctionPermission to an array that contains it', () => {
        const empRoleFunctionPermission: IEmpRoleFunctionPermission = sampleWithRequiredData;
        const empRoleFunctionPermissionCollection: IEmpRoleFunctionPermission[] = [
          {
            ...empRoleFunctionPermission,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmpRoleFunctionPermissionToCollectionIfMissing(
          empRoleFunctionPermissionCollection,
          empRoleFunctionPermission,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmpRoleFunctionPermission to an array that doesn't contain it", () => {
        const empRoleFunctionPermission: IEmpRoleFunctionPermission = sampleWithRequiredData;
        const empRoleFunctionPermissionCollection: IEmpRoleFunctionPermission[] = [sampleWithPartialData];
        expectedResult = service.addEmpRoleFunctionPermissionToCollectionIfMissing(
          empRoleFunctionPermissionCollection,
          empRoleFunctionPermission,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empRoleFunctionPermission);
      });

      it('should add only unique EmpRoleFunctionPermission to an array', () => {
        const empRoleFunctionPermissionArray: IEmpRoleFunctionPermission[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const empRoleFunctionPermissionCollection: IEmpRoleFunctionPermission[] = [sampleWithRequiredData];
        expectedResult = service.addEmpRoleFunctionPermissionToCollectionIfMissing(
          empRoleFunctionPermissionCollection,
          ...empRoleFunctionPermissionArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const empRoleFunctionPermission: IEmpRoleFunctionPermission = sampleWithRequiredData;
        const empRoleFunctionPermission2: IEmpRoleFunctionPermission = sampleWithPartialData;
        expectedResult = service.addEmpRoleFunctionPermissionToCollectionIfMissing(
          [],
          empRoleFunctionPermission,
          empRoleFunctionPermission2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empRoleFunctionPermission);
        expect(expectedResult).toContain(empRoleFunctionPermission2);
      });

      it('should accept null and undefined values', () => {
        const empRoleFunctionPermission: IEmpRoleFunctionPermission = sampleWithRequiredData;
        expectedResult = service.addEmpRoleFunctionPermissionToCollectionIfMissing([], null, empRoleFunctionPermission, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empRoleFunctionPermission);
      });

      it('should return initial array if no EmpRoleFunctionPermission is added', () => {
        const empRoleFunctionPermissionCollection: IEmpRoleFunctionPermission[] = [sampleWithRequiredData];
        expectedResult = service.addEmpRoleFunctionPermissionToCollectionIfMissing(empRoleFunctionPermissionCollection, undefined, null);
        expect(expectedResult).toEqual(empRoleFunctionPermissionCollection);
      });
    });

    describe('compareEmpRoleFunctionPermission', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmpRoleFunctionPermission(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 23088 };
        const entity2 = null;

        const compareResult1 = service.compareEmpRoleFunctionPermission(entity1, entity2);
        const compareResult2 = service.compareEmpRoleFunctionPermission(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 23088 };
        const entity2 = { id: 11292 };

        const compareResult1 = service.compareEmpRoleFunctionPermission(entity1, entity2);
        const compareResult2 = service.compareEmpRoleFunctionPermission(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 23088 };
        const entity2 = { id: 23088 };

        const compareResult1 = service.compareEmpRoleFunctionPermission(entity1, entity2);
        const compareResult2 = service.compareEmpRoleFunctionPermission(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
