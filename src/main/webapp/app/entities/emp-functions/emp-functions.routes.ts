import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import EmpFunctionsResolve from './route/emp-functions-routing-resolve.service';

const empFunctionsRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/emp-functions.component').then(m => m.EmpFunctionsComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':functionId/view',
    loadComponent: () => import('./detail/emp-functions-detail.component').then(m => m.EmpFunctionsDetailComponent),
    resolve: {
      empFunctions: EmpFunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/emp-functions-update.component').then(m => m.EmpFunctionsUpdateComponent),
    resolve: {
      empFunctions: EmpFunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':functionId/edit',
    loadComponent: () => import('./update/emp-functions-update.component').then(m => m.EmpFunctionsUpdateComponent),
    resolve: {
      empFunctions: EmpFunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default empFunctionsRoute;
