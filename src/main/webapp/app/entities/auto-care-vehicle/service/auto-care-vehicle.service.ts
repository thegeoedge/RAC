import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAutoCareVehicle, NewAutoCareVehicle } from '../auto-care-vehicle.model';

export type PartialUpdateAutoCareVehicle = Partial<IAutoCareVehicle> & Pick<IAutoCareVehicle, 'id'>;

type RestOf<T extends IAutoCareVehicle | NewAutoCareVehicle> = Omit<T, 'lastServiceDate' | 'nextServiceDate' | 'lmd'> & {
  lastServiceDate?: string | null;
  nextServiceDate?: string | null;
  lmd?: string | null;
};

export type RestAutoCareVehicle = RestOf<IAutoCareVehicle>;

export type NewRestAutoCareVehicle = RestOf<NewAutoCareVehicle>;

export type PartialUpdateRestAutoCareVehicle = RestOf<PartialUpdateAutoCareVehicle>;

export type EntityResponseType = HttpResponse<IAutoCareVehicle>;
export type EntityArrayResponseType = HttpResponse<IAutoCareVehicle[]>;

@Injectable({ providedIn: 'root' })
export class AutoCareVehicleService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/auto-care-vehicles');

  create(autoCareVehicle: NewAutoCareVehicle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autoCareVehicle);
    return this.http
      .post<RestAutoCareVehicle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(autoCareVehicle: IAutoCareVehicle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autoCareVehicle);
    return this.http
      .put<RestAutoCareVehicle>(`${this.resourceUrl}/${this.getAutoCareVehicleIdentifier(autoCareVehicle)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(autoCareVehicle: PartialUpdateAutoCareVehicle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autoCareVehicle);
    return this.http
      .patch<RestAutoCareVehicle>(`${this.resourceUrl}/${this.getAutoCareVehicleIdentifier(autoCareVehicle)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAutoCareVehicle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAutoCareVehicle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAutoCareVehicleIdentifier(autoCareVehicle: Pick<IAutoCareVehicle, 'id'>): number {
    return autoCareVehicle.id;
  }

  compareAutoCareVehicle(o1: Pick<IAutoCareVehicle, 'id'> | null, o2: Pick<IAutoCareVehicle, 'id'> | null): boolean {
    return o1 && o2 ? this.getAutoCareVehicleIdentifier(o1) === this.getAutoCareVehicleIdentifier(o2) : o1 === o2;
  }

  addAutoCareVehicleToCollectionIfMissing<Type extends Pick<IAutoCareVehicle, 'id'>>(
    autoCareVehicleCollection: Type[],
    ...autoCareVehiclesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autoCareVehicles: Type[] = autoCareVehiclesToCheck.filter(isPresent);
    if (autoCareVehicles.length > 0) {
      const autoCareVehicleCollectionIdentifiers = autoCareVehicleCollection.map(autoCareVehicleItem =>
        this.getAutoCareVehicleIdentifier(autoCareVehicleItem),
      );
      const autoCareVehiclesToAdd = autoCareVehicles.filter(autoCareVehicleItem => {
        const autoCareVehicleIdentifier = this.getAutoCareVehicleIdentifier(autoCareVehicleItem);
        if (autoCareVehicleCollectionIdentifiers.includes(autoCareVehicleIdentifier)) {
          return false;
        }
        autoCareVehicleCollectionIdentifiers.push(autoCareVehicleIdentifier);
        return true;
      });
      return [...autoCareVehiclesToAdd, ...autoCareVehicleCollection];
    }
    return autoCareVehicleCollection;
  }

  protected convertDateFromClient<T extends IAutoCareVehicle | NewAutoCareVehicle | PartialUpdateAutoCareVehicle>(
    autoCareVehicle: T,
  ): RestOf<T> {
    return {
      ...autoCareVehicle,
      lastServiceDate: autoCareVehicle.lastServiceDate?.toJSON() ?? null,
      nextServiceDate: autoCareVehicle.nextServiceDate?.toJSON() ?? null,
      lmd: autoCareVehicle.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAutoCareVehicle: RestAutoCareVehicle): IAutoCareVehicle {
    return {
      ...restAutoCareVehicle,
      lastServiceDate: restAutoCareVehicle.lastServiceDate ? dayjs(restAutoCareVehicle.lastServiceDate) : undefined,
      nextServiceDate: restAutoCareVehicle.nextServiceDate ? dayjs(restAutoCareVehicle.nextServiceDate) : undefined,
      lmd: restAutoCareVehicle.lmd ? dayjs(restAutoCareVehicle.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAutoCareVehicle>): HttpResponse<IAutoCareVehicle> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAutoCareVehicle[]>): HttpResponse<IAutoCareVehicle[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
