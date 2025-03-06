import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SystemSettingsResolve from './route/system-settings-routing-resolve.service';

const systemSettingsRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/system-settings.component').then(m => m.SystemSettingsComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/system-settings-detail.component').then(m => m.SystemSettingsDetailComponent),
    resolve: {
      systemSettings: SystemSettingsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/system-settings-update.component').then(m => m.SystemSettingsUpdateComponent),
    resolve: {
      systemSettings: SystemSettingsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/system-settings-update.component').then(m => m.SystemSettingsUpdateComponent),
    resolve: {
      systemSettings: SystemSettingsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default systemSettingsRoute;
