import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AutoCareVehicleResolve from './route/auto-care-vehicle-routing-resolve.service';

const autoCareVehicleRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/auto-care-vehicle.component').then(m => m.AutoCareVehicleComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/auto-care-vehicle-detail.component').then(m => m.AutoCareVehicleDetailComponent),
    resolve: {
      autoCareVehicle: AutoCareVehicleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/auto-care-vehicle-update.component').then(m => m.AutoCareVehicleUpdateComponent),
    resolve: {
      autoCareVehicle: AutoCareVehicleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/auto-care-vehicle-update.component').then(m => m.AutoCareVehicleUpdateComponent),
    resolve: {
      autoCareVehicle: AutoCareVehicleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default autoCareVehicleRoute;
