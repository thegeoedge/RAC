import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmpRoleFunctionPermission } from '../emp-role-function-permission.model';
import { EmpRoleFunctionPermissionService } from '../service/emp-role-function-permission.service';

const empRoleFunctionPermissionResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmpRoleFunctionPermission> => {
  const id = route.params.id;
  if (id) {
    return inject(EmpRoleFunctionPermissionService)
      .find(id)
      .pipe(
        mergeMap((empRoleFunctionPermission: HttpResponse<IEmpRoleFunctionPermission>) => {
          if (empRoleFunctionPermission.body) {
            return of(empRoleFunctionPermission.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default empRoleFunctionPermissionResolve;
