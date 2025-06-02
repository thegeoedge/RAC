import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import BanksResolve from './route/banks-routing-resolve.service';

const banksRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/banks.component').then(m => m.BanksComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/banks-detail.component').then(m => m.BanksDetailComponent),
    resolve: {
      banks: BanksResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/banks-update.component').then(m => m.BanksUpdateComponent),
    resolve: {
      banks: BanksResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/banks-update.component').then(m => m.BanksUpdateComponent),
    resolve: {
      banks: BanksResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default banksRoute;
