import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAutoCareVehicle } from '../auto-care-vehicle.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../auto-care-vehicle.test-samples';

import { AutoCareVehicleService, RestAutoCareVehicle } from './auto-care-vehicle.service';

const requireRestSample: RestAutoCareVehicle = {
  ...sampleWithRequiredData,
  lastServiceDate: sampleWithRequiredData.lastServiceDate?.toJSON(),
  nextServiceDate: sampleWithRequiredData.nextServiceDate?.toJSON(),
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('AutoCareVehicle Service', () => {
  let service: AutoCareVehicleService;
  let httpMock: HttpTestingController;
  let expectedResult: IAutoCareVehicle | IAutoCareVehicle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AutoCareVehicleService);
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

    it('should create a AutoCareVehicle', () => {
      const autoCareVehicle = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(autoCareVehicle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AutoCareVehicle', () => {
      const autoCareVehicle = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(autoCareVehicle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AutoCareVehicle', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AutoCareVehicle', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AutoCareVehicle', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAutoCareVehicleToCollectionIfMissing', () => {
      it('should add a AutoCareVehicle to an empty array', () => {
        const autoCareVehicle: IAutoCareVehicle = sampleWithRequiredData;
        expectedResult = service.addAutoCareVehicleToCollectionIfMissing([], autoCareVehicle);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autoCareVehicle);
      });

      it('should not add a AutoCareVehicle to an array that contains it', () => {
        const autoCareVehicle: IAutoCareVehicle = sampleWithRequiredData;
        const autoCareVehicleCollection: IAutoCareVehicle[] = [
          {
            ...autoCareVehicle,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAutoCareVehicleToCollectionIfMissing(autoCareVehicleCollection, autoCareVehicle);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AutoCareVehicle to an array that doesn't contain it", () => {
        const autoCareVehicle: IAutoCareVehicle = sampleWithRequiredData;
        const autoCareVehicleCollection: IAutoCareVehicle[] = [sampleWithPartialData];
        expectedResult = service.addAutoCareVehicleToCollectionIfMissing(autoCareVehicleCollection, autoCareVehicle);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autoCareVehicle);
      });

      it('should add only unique AutoCareVehicle to an array', () => {
        const autoCareVehicleArray: IAutoCareVehicle[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const autoCareVehicleCollection: IAutoCareVehicle[] = [sampleWithRequiredData];
        expectedResult = service.addAutoCareVehicleToCollectionIfMissing(autoCareVehicleCollection, ...autoCareVehicleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const autoCareVehicle: IAutoCareVehicle = sampleWithRequiredData;
        const autoCareVehicle2: IAutoCareVehicle = sampleWithPartialData;
        expectedResult = service.addAutoCareVehicleToCollectionIfMissing([], autoCareVehicle, autoCareVehicle2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autoCareVehicle);
        expect(expectedResult).toContain(autoCareVehicle2);
      });

      it('should accept null and undefined values', () => {
        const autoCareVehicle: IAutoCareVehicle = sampleWithRequiredData;
        expectedResult = service.addAutoCareVehicleToCollectionIfMissing([], null, autoCareVehicle, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autoCareVehicle);
      });

      it('should return initial array if no AutoCareVehicle is added', () => {
        const autoCareVehicleCollection: IAutoCareVehicle[] = [sampleWithRequiredData];
        expectedResult = service.addAutoCareVehicleToCollectionIfMissing(autoCareVehicleCollection, undefined, null);
        expect(expectedResult).toEqual(autoCareVehicleCollection);
      });
    });

    describe('compareAutoCareVehicle', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAutoCareVehicle(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 11116 };
        const entity2 = null;

        const compareResult1 = service.compareAutoCareVehicle(entity1, entity2);
        const compareResult2 = service.compareAutoCareVehicle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 11116 };
        const entity2 = { id: 21145 };

        const compareResult1 = service.compareAutoCareVehicle(entity1, entity2);
        const compareResult2 = service.compareAutoCareVehicle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 11116 };
        const entity2 = { id: 11116 };

        const compareResult1 = service.compareAutoCareVehicle(entity1, entity2);
        const compareResult2 = service.compareAutoCareVehicle(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
