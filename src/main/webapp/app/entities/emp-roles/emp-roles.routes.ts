import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import EmpRolesResolve from './route/emp-roles-routing-resolve.service';

const empRolesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/emp-roles.component').then(m => m.EmpRolesComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':roleId/view',
    loadComponent: () => import('./detail/emp-roles-detail.component').then(m => m.EmpRolesDetailComponent),
    resolve: {
      empRoles: EmpRolesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/emp-roles-update.component').then(m => m.EmpRolesUpdateComponent),
    resolve: {
      empRoles: EmpRolesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':roleId/edit',
    loadComponent: () => import('./update/emp-roles-update.component').then(m => m.EmpRolesUpdateComponent),
    resolve: {
      empRoles: EmpRolesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default empRolesRoute;
