import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentMethod, NewPaymentMethod } from '../payment-method.model';

export type PartialUpdatePaymentMethod = Partial<IPaymentMethod> & Pick<IPaymentMethod, 'id'>;

type RestOf<T extends IPaymentMethod | NewPaymentMethod> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

export type RestPaymentMethod = RestOf<IPaymentMethod>;

export type NewRestPaymentMethod = RestOf<NewPaymentMethod>;

export type PartialUpdateRestPaymentMethod = RestOf<PartialUpdatePaymentMethod>;

export type EntityResponseType = HttpResponse<IPaymentMethod>;
export type EntityArrayResponseType = HttpResponse<IPaymentMethod[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-methods');

  create(paymentMethod: NewPaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethod);
    return this.http
      .post<RestPaymentMethod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(paymentMethod: IPaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethod);
    return this.http
      .put<RestPaymentMethod>(`${this.resourceUrl}/${this.getPaymentMethodIdentifier(paymentMethod)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(paymentMethod: PartialUpdatePaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethod);
    return this.http
      .patch<RestPaymentMethod>(`${this.resourceUrl}/${this.getPaymentMethodIdentifier(paymentMethod)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPaymentMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPaymentMethod[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentMethodIdentifier(paymentMethod: Pick<IPaymentMethod, 'id'>): number {
    return paymentMethod.id;
  }

  comparePaymentMethod(o1: Pick<IPaymentMethod, 'id'> | null, o2: Pick<IPaymentMethod, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentMethodIdentifier(o1) === this.getPaymentMethodIdentifier(o2) : o1 === o2;
  }

  addPaymentMethodToCollectionIfMissing<Type extends Pick<IPaymentMethod, 'id'>>(
    paymentMethodCollection: Type[],
    ...paymentMethodsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentMethods: Type[] = paymentMethodsToCheck.filter(isPresent);
    if (paymentMethods.length > 0) {
      const paymentMethodCollectionIdentifiers = paymentMethodCollection.map(paymentMethodItem =>
        this.getPaymentMethodIdentifier(paymentMethodItem),
      );
      const paymentMethodsToAdd = paymentMethods.filter(paymentMethodItem => {
        const paymentMethodIdentifier = this.getPaymentMethodIdentifier(paymentMethodItem);
        if (paymentMethodCollectionIdentifiers.includes(paymentMethodIdentifier)) {
          return false;
        }
        paymentMethodCollectionIdentifiers.push(paymentMethodIdentifier);
        return true;
      });
      return [...paymentMethodsToAdd, ...paymentMethodCollection];
    }
    return paymentMethodCollection;
  }

  protected convertDateFromClient<T extends IPaymentMethod | NewPaymentMethod | PartialUpdatePaymentMethod>(paymentMethod: T): RestOf<T> {
    return {
      ...paymentMethod,
      lmd: paymentMethod.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPaymentMethod: RestPaymentMethod): IPaymentMethod {
    return {
      ...restPaymentMethod,
      lmd: restPaymentMethod.lmd ? dayjs(restPaymentMethod.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPaymentMethod>): HttpResponse<IPaymentMethod> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPaymentMethod[]>): HttpResponse<IPaymentMethod[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
