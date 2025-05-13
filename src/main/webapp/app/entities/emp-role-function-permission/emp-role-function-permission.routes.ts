import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import EmpRoleFunctionPermissionResolve from './route/emp-role-function-permission-routing-resolve.service';

const empRoleFunctionPermissionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/emp-role-function-permission.component').then(m => m.EmpRoleFunctionPermissionComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/emp-role-function-permission-detail.component').then(m => m.EmpRoleFunctionPermissionDetailComponent),
    resolve: {
      empRoleFunctionPermission: EmpRoleFunctionPermissionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/emp-role-function-permission-update.component').then(m => m.EmpRoleFunctionPermissionUpdateComponent),
    resolve: {
      empRoleFunctionPermission: EmpRoleFunctionPermissionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/emp-role-function-permission-update.component').then(m => m.EmpRoleFunctionPermissionUpdateComponent),
    resolve: {
      empRoleFunctionPermission: EmpRoleFunctionPermissionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default empRoleFunctionPermissionRoute;
