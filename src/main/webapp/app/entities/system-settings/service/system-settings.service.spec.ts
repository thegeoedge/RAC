import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISystemSettings } from '../system-settings.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../system-settings.test-samples';

import { RestSystemSettings, SystemSettingsService } from './system-settings.service';

const requireRestSample: RestSystemSettings = {
  ...sampleWithRequiredData,
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('SystemSettings Service', () => {
  let service: SystemSettingsService;
  let httpMock: HttpTestingController;
  let expectedResult: ISystemSettings | ISystemSettings[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SystemSettingsService);
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

    it('should create a SystemSettings', () => {
      const systemSettings = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(systemSettings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SystemSettings', () => {
      const systemSettings = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(systemSettings).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SystemSettings', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SystemSettings', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SystemSettings', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSystemSettingsToCollectionIfMissing', () => {
      it('should add a SystemSettings to an empty array', () => {
        const systemSettings: ISystemSettings = sampleWithRequiredData;
        expectedResult = service.addSystemSettingsToCollectionIfMissing([], systemSettings);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(systemSettings);
      });

      it('should not add a SystemSettings to an array that contains it', () => {
        const systemSettings: ISystemSettings = sampleWithRequiredData;
        const systemSettingsCollection: ISystemSettings[] = [
          {
            ...systemSettings,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSystemSettingsToCollectionIfMissing(systemSettingsCollection, systemSettings);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SystemSettings to an array that doesn't contain it", () => {
        const systemSettings: ISystemSettings = sampleWithRequiredData;
        const systemSettingsCollection: ISystemSettings[] = [sampleWithPartialData];
        expectedResult = service.addSystemSettingsToCollectionIfMissing(systemSettingsCollection, systemSettings);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(systemSettings);
      });

      it('should add only unique SystemSettings to an array', () => {
        const systemSettingsArray: ISystemSettings[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const systemSettingsCollection: ISystemSettings[] = [sampleWithRequiredData];
        expectedResult = service.addSystemSettingsToCollectionIfMissing(systemSettingsCollection, ...systemSettingsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const systemSettings: ISystemSettings = sampleWithRequiredData;
        const systemSettings2: ISystemSettings = sampleWithPartialData;
        expectedResult = service.addSystemSettingsToCollectionIfMissing([], systemSettings, systemSettings2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(systemSettings);
        expect(expectedResult).toContain(systemSettings2);
      });

      it('should accept null and undefined values', () => {
        const systemSettings: ISystemSettings = sampleWithRequiredData;
        expectedResult = service.addSystemSettingsToCollectionIfMissing([], null, systemSettings, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(systemSettings);
      });

      it('should return initial array if no SystemSettings is added', () => {
        const systemSettingsCollection: ISystemSettings[] = [sampleWithRequiredData];
        expectedResult = service.addSystemSettingsToCollectionIfMissing(systemSettingsCollection, undefined, null);
        expect(expectedResult).toEqual(systemSettingsCollection);
      });
    });

    describe('compareSystemSettings', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSystemSettings(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSystemSettings(entity1, entity2);
        const compareResult2 = service.compareSystemSettings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSystemSettings(entity1, entity2);
        const compareResult2 = service.compareSystemSettings(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSystemSettings(entity1, entity2);
        const compareResult2 = service.compareSystemSettings(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
