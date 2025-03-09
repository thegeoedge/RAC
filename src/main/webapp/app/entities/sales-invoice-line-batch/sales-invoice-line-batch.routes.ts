import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SalesInvoiceLineBatchResolve from './route/sales-invoice-line-batch-routing-resolve.service';

const salesInvoiceLineBatchRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sales-invoice-line-batch.component').then(m => m.SalesInvoiceLineBatchComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sales-invoice-line-batch-detail.component').then(m => m.SalesInvoiceLineBatchDetailComponent),
    resolve: {
      salesInvoiceLineBatch: SalesInvoiceLineBatchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sales-invoice-line-batch-update.component').then(m => m.SalesInvoiceLineBatchUpdateComponent),
    resolve: {
      salesInvoiceLineBatch: SalesInvoiceLineBatchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sales-invoice-line-batch-update.component').then(m => m.SalesInvoiceLineBatchUpdateComponent),
    resolve: {
      salesInvoiceLineBatch: SalesInvoiceLineBatchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salesInvoiceLineBatchRoute;
