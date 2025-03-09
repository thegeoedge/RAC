import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesInvoiceLineBatch, NewSalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';

export type PartialUpdateSalesInvoiceLineBatch = Partial<ISalesInvoiceLineBatch> & Pick<ISalesInvoiceLineBatch, 'id'>;

type RestOf<T extends ISalesInvoiceLineBatch | NewSalesInvoiceLineBatch> = Omit<T, 'txDate' | 'manufactureDate' | 'expiredDate' | 'lmd'> & {
  txDate?: string | null;
  manufactureDate?: string | null;
  expiredDate?: string | null;
  lmd?: string | null;
};

export type RestSalesInvoiceLineBatch = RestOf<ISalesInvoiceLineBatch>;

export type NewRestSalesInvoiceLineBatch = RestOf<NewSalesInvoiceLineBatch>;

export type PartialUpdateRestSalesInvoiceLineBatch = RestOf<PartialUpdateSalesInvoiceLineBatch>;

export type EntityResponseType = HttpResponse<ISalesInvoiceLineBatch>;
export type EntityArrayResponseType = HttpResponse<ISalesInvoiceLineBatch[]>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceLineBatchService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-invoice-line-batches');

  create(salesInvoiceLineBatch: NewSalesInvoiceLineBatch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLineBatch);
    return this.http
      .post<RestSalesInvoiceLineBatch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(salesInvoiceLineBatch: ISalesInvoiceLineBatch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLineBatch);
    return this.http
      .put<RestSalesInvoiceLineBatch>(`${this.resourceUrl}/${this.getSalesInvoiceLineBatchIdentifier(salesInvoiceLineBatch)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(salesInvoiceLineBatch: PartialUpdateSalesInvoiceLineBatch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLineBatch);
    return this.http
      .patch<RestSalesInvoiceLineBatch>(`${this.resourceUrl}/${this.getSalesInvoiceLineBatchIdentifier(salesInvoiceLineBatch)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSalesInvoiceLineBatch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSalesInvoiceLineBatch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalesInvoiceLineBatchIdentifier(salesInvoiceLineBatch: Pick<ISalesInvoiceLineBatch, 'id'>): number {
    return salesInvoiceLineBatch.id;
  }

  compareSalesInvoiceLineBatch(o1: Pick<ISalesInvoiceLineBatch, 'id'> | null, o2: Pick<ISalesInvoiceLineBatch, 'id'> | null): boolean {
    return o1 && o2 ? this.getSalesInvoiceLineBatchIdentifier(o1) === this.getSalesInvoiceLineBatchIdentifier(o2) : o1 === o2;
  }

  addSalesInvoiceLineBatchToCollectionIfMissing<Type extends Pick<ISalesInvoiceLineBatch, 'id'>>(
    salesInvoiceLineBatchCollection: Type[],
    ...salesInvoiceLineBatchesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salesInvoiceLineBatches: Type[] = salesInvoiceLineBatchesToCheck.filter(isPresent);
    if (salesInvoiceLineBatches.length > 0) {
      const salesInvoiceLineBatchCollectionIdentifiers = salesInvoiceLineBatchCollection.map(salesInvoiceLineBatchItem =>
        this.getSalesInvoiceLineBatchIdentifier(salesInvoiceLineBatchItem),
      );
      const salesInvoiceLineBatchesToAdd = salesInvoiceLineBatches.filter(salesInvoiceLineBatchItem => {
        const salesInvoiceLineBatchIdentifier = this.getSalesInvoiceLineBatchIdentifier(salesInvoiceLineBatchItem);
        if (salesInvoiceLineBatchCollectionIdentifiers.includes(salesInvoiceLineBatchIdentifier)) {
          return false;
        }
        salesInvoiceLineBatchCollectionIdentifiers.push(salesInvoiceLineBatchIdentifier);
        return true;
      });
      return [...salesInvoiceLineBatchesToAdd, ...salesInvoiceLineBatchCollection];
    }
    return salesInvoiceLineBatchCollection;
  }

  protected convertDateFromClient<T extends ISalesInvoiceLineBatch | NewSalesInvoiceLineBatch | PartialUpdateSalesInvoiceLineBatch>(
    salesInvoiceLineBatch: T,
  ): RestOf<T> {
    return {
      ...salesInvoiceLineBatch,
      txDate: salesInvoiceLineBatch.txDate?.toJSON() ?? null,
      manufactureDate: salesInvoiceLineBatch.manufactureDate?.toJSON() ?? null,
      expiredDate: salesInvoiceLineBatch.expiredDate?.toJSON() ?? null,
      lmd: salesInvoiceLineBatch.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSalesInvoiceLineBatch: RestSalesInvoiceLineBatch): ISalesInvoiceLineBatch {
    return {
      ...restSalesInvoiceLineBatch,
      txDate: restSalesInvoiceLineBatch.txDate ? dayjs(restSalesInvoiceLineBatch.txDate) : undefined,
      manufactureDate: restSalesInvoiceLineBatch.manufactureDate ? dayjs(restSalesInvoiceLineBatch.manufactureDate) : undefined,
      expiredDate: restSalesInvoiceLineBatch.expiredDate ? dayjs(restSalesInvoiceLineBatch.expiredDate) : undefined,
      lmd: restSalesInvoiceLineBatch.lmd ? dayjs(restSalesInvoiceLineBatch.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSalesInvoiceLineBatch>): HttpResponse<ISalesInvoiceLineBatch> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSalesInvoiceLineBatch[]>): HttpResponse<ISalesInvoiceLineBatch[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
