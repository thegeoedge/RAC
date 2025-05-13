import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmpRoles } from '../emp-roles.model';
import { EmpRolesService } from '../service/emp-roles.service';

const empRolesResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmpRoles> => {
  const id = route.params.roleId;
  if (id) {
    return inject(EmpRolesService)
      .find(id)
      .pipe(
        mergeMap((empRoles: HttpResponse<IEmpRoles>) => {
          if (empRoles.body) {
            return of(empRoles.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default empRolesResolve;
