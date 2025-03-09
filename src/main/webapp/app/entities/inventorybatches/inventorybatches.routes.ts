import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import InventorybatchesResolve from './route/inventorybatches-routing-resolve.service';

const inventorybatchesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/inventorybatches.component').then(m => m.InventorybatchesComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/inventorybatches-detail.component').then(m => m.InventorybatchesDetailComponent),
    resolve: {
      inventorybatches: InventorybatchesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/inventorybatches-update.component').then(m => m.InventorybatchesUpdateComponent),
    resolve: {
      inventorybatches: InventorybatchesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/inventorybatches-update.component').then(m => m.InventorybatchesUpdateComponent),
    resolve: {
      inventorybatches: InventorybatchesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default inventorybatchesRoute;
