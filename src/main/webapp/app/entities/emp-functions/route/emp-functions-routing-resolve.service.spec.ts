import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IEmpFunctions } from '../emp-functions.model';
import { EmpFunctionsService } from '../service/emp-functions.service';

import empFunctionsResolve from './emp-functions-routing-resolve.service';

describe('EmpFunctions routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: EmpFunctionsService;
  let resultEmpFunctions: IEmpFunctions | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(EmpFunctionsService);
    resultEmpFunctions = undefined;
  });

  describe('resolve', () => {
    it('should return IEmpFunctions returned by find', () => {
      // GIVEN
      service.find = jest.fn(functionId => of(new HttpResponse({ body: { functionId } })));
      mockActivatedRouteSnapshot.params = { functionId: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        empFunctionsResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmpFunctions = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEmpFunctions).toEqual({ functionId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        empFunctionsResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmpFunctions = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultEmpFunctions).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEmpFunctions>({ body: null })));
      mockActivatedRouteSnapshot.params = { functionId: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        empFunctionsResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmpFunctions = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEmpFunctions).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
