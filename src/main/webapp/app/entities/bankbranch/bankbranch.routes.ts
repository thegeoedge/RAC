import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import BankbranchResolve from './route/bankbranch-routing-resolve.service';

const bankbranchRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/bankbranch.component').then(m => m.BankbranchComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/bankbranch-detail.component').then(m => m.BankbranchDetailComponent),
    resolve: {
      bankbranch: BankbranchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/bankbranch-update.component').then(m => m.BankbranchUpdateComponent),
    resolve: {
      bankbranch: BankbranchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/bankbranch-update.component').then(m => m.BankbranchUpdateComponent),
    resolve: {
      bankbranch: BankbranchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bankbranchRoute;
