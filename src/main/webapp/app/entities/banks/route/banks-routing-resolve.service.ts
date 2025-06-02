import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBanks } from '../banks.model';
import { BanksService } from '../service/banks.service';

const banksResolve = (route: ActivatedRouteSnapshot): Observable<null | IBanks> => {
  const id = route.params.id;
  if (id) {
    return inject(BanksService)
      .find(id)
      .pipe(
        mergeMap((banks: HttpResponse<IBanks>) => {
          if (banks.body) {
            return of(banks.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default banksResolve;
