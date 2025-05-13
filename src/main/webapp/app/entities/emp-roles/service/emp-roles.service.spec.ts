import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEmpRoles } from '../emp-roles.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../emp-roles.test-samples';

import { EmpRolesService } from './emp-roles.service';

const requireRestSample: IEmpRoles = {
  ...sampleWithRequiredData,
};

describe('EmpRoles Service', () => {
  let service: EmpRolesService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmpRoles | IEmpRoles[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EmpRolesService);
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

    it('should create a EmpRoles', () => {
      const empRoles = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(empRoles).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmpRoles', () => {
      const empRoles = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(empRoles).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmpRoles', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmpRoles', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmpRoles', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmpRolesToCollectionIfMissing', () => {
      it('should add a EmpRoles to an empty array', () => {
        const empRoles: IEmpRoles = sampleWithRequiredData;
        expectedResult = service.addEmpRolesToCollectionIfMissing([], empRoles);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empRoles);
      });

      it('should not add a EmpRoles to an array that contains it', () => {
        const empRoles: IEmpRoles = sampleWithRequiredData;
        const empRolesCollection: IEmpRoles[] = [
          {
            ...empRoles,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmpRolesToCollectionIfMissing(empRolesCollection, empRoles);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmpRoles to an array that doesn't contain it", () => {
        const empRoles: IEmpRoles = sampleWithRequiredData;
        const empRolesCollection: IEmpRoles[] = [sampleWithPartialData];
        expectedResult = service.addEmpRolesToCollectionIfMissing(empRolesCollection, empRoles);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empRoles);
      });

      it('should add only unique EmpRoles to an array', () => {
        const empRolesArray: IEmpRoles[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const empRolesCollection: IEmpRoles[] = [sampleWithRequiredData];
        expectedResult = service.addEmpRolesToCollectionIfMissing(empRolesCollection, ...empRolesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const empRoles: IEmpRoles = sampleWithRequiredData;
        const empRoles2: IEmpRoles = sampleWithPartialData;
        expectedResult = service.addEmpRolesToCollectionIfMissing([], empRoles, empRoles2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empRoles);
        expect(expectedResult).toContain(empRoles2);
      });

      it('should accept null and undefined values', () => {
        const empRoles: IEmpRoles = sampleWithRequiredData;
        expectedResult = service.addEmpRolesToCollectionIfMissing([], null, empRoles, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empRoles);
      });

      it('should return initial array if no EmpRoles is added', () => {
        const empRolesCollection: IEmpRoles[] = [sampleWithRequiredData];
        expectedResult = service.addEmpRolesToCollectionIfMissing(empRolesCollection, undefined, null);
        expect(expectedResult).toEqual(empRolesCollection);
      });
    });

    describe('compareEmpRoles', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmpRoles(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { roleId: 31402 };
        const entity2 = null;

        const compareResult1 = service.compareEmpRoles(entity1, entity2);
        const compareResult2 = service.compareEmpRoles(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { roleId: 31402 };
        const entity2 = { roleId: 55 };

        const compareResult1 = service.compareEmpRoles(entity1, entity2);
        const compareResult2 = service.compareEmpRoles(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { roleId: 31402 };
        const entity2 = { roleId: 31402 };

        const compareResult1 = service.compareEmpRoles(entity1, entity2);
        const compareResult2 = service.compareEmpRoles(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
