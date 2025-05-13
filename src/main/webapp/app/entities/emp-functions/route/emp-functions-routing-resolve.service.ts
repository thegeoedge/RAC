import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmpFunctions } from '../emp-functions.model';
import { EmpFunctionsService } from '../service/emp-functions.service';

const empFunctionsResolve = (route: ActivatedRouteSnapshot): Observable<null | IEmpFunctions> => {
  const id = route.params.functionId;
  if (id) {
    return inject(EmpFunctionsService)
      .find(id)
      .pipe(
        mergeMap((empFunctions: HttpResponse<IEmpFunctions>) => {
          if (empFunctions.body) {
            return of(empFunctions.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default empFunctionsResolve;
