import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISystemSettings } from '../system-settings.model';
import { SystemSettingsService } from '../service/system-settings.service';

const systemSettingsResolve = (route: ActivatedRouteSnapshot): Observable<null | ISystemSettings> => {
  const id = route.params.id;
  if (id) {
    return inject(SystemSettingsService)
      .find(id)
      .pipe(
        mergeMap((systemSettings: HttpResponse<ISystemSettings>) => {
          if (systemSettings.body) {
            return of(systemSettings.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default systemSettingsResolve;
