import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';
import { SalesInvoiceLineBatchService } from '../service/sales-invoice-line-batch.service';

const salesInvoiceLineBatchResolve = (route: ActivatedRouteSnapshot): Observable<null | ISalesInvoiceLineBatch> => {
  const id = route.params.id;
  if (id) {
    return inject(SalesInvoiceLineBatchService)
      .find(id)
      .pipe(
        mergeMap((salesInvoiceLineBatch: HttpResponse<ISalesInvoiceLineBatch>) => {
          if (salesInvoiceLineBatch.body) {
            return of(salesInvoiceLineBatch.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default salesInvoiceLineBatchResolve;
