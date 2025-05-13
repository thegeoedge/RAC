import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IEmpRoleFunctionPermission } from '../emp-role-function-permission.model';
import { EmpRoleFunctionPermissionService } from '../service/emp-role-function-permission.service';

import empRoleFunctionPermissionResolve from './emp-role-function-permission-routing-resolve.service';

describe('EmpRoleFunctionPermission routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: EmpRoleFunctionPermissionService;
  let resultEmpRoleFunctionPermission: IEmpRoleFunctionPermission | null | undefined;

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
    service = TestBed.inject(EmpRoleFunctionPermissionService);
    resultEmpRoleFunctionPermission = undefined;
  });

  describe('resolve', () => {
    it('should return IEmpRoleFunctionPermission returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        empRoleFunctionPermissionResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmpRoleFunctionPermission = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEmpRoleFunctionPermission).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        empRoleFunctionPermissionResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmpRoleFunctionPermission = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultEmpRoleFunctionPermission).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEmpRoleFunctionPermission>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        empRoleFunctionPermissionResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEmpRoleFunctionPermission = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEmpRoleFunctionPermission).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
