import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutoCareVehicle } from '../auto-care-vehicle.model';
import { AutoCareVehicleService } from '../service/auto-care-vehicle.service';

const autoCareVehicleResolve = (route: ActivatedRouteSnapshot): Observable<null | IAutoCareVehicle> => {
  const id = route.params.id;
  if (id) {
    return inject(AutoCareVehicleService)
      .find(id)
      .pipe(
        mergeMap((autoCareVehicle: HttpResponse<IAutoCareVehicle>) => {
          if (autoCareVehicle.body) {
            return of(autoCareVehicle.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default autoCareVehicleResolve;
