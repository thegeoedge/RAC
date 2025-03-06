import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISystemSettings, NewSystemSettings } from '../system-settings.model';

export type PartialUpdateSystemSettings = Partial<ISystemSettings> & Pick<ISystemSettings, 'id'>;

type RestOf<T extends ISystemSettings | NewSystemSettings> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

export type RestSystemSettings = RestOf<ISystemSettings>;

export type NewRestSystemSettings = RestOf<NewSystemSettings>;

export type PartialUpdateRestSystemSettings = RestOf<PartialUpdateSystemSettings>;

export type EntityResponseType = HttpResponse<ISystemSettings>;
export type EntityArrayResponseType = HttpResponse<ISystemSettings[]>;

@Injectable({ providedIn: 'root' })
export class SystemSettingsService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/system-settings');

  create(systemSettings: NewSystemSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemSettings);
    return this.http
      .post<RestSystemSettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(systemSettings: ISystemSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemSettings);
    return this.http
      .put<RestSystemSettings>(`${this.resourceUrl}/${this.getSystemSettingsIdentifier(systemSettings)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(systemSettings: PartialUpdateSystemSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemSettings);
    return this.http
      .patch<RestSystemSettings>(`${this.resourceUrl}/${this.getSystemSettingsIdentifier(systemSettings)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSystemSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSystemSettings[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSystemSettingsIdentifier(systemSettings: Pick<ISystemSettings, 'id'>): number {
    return systemSettings.id;
  }

  compareSystemSettings(o1: Pick<ISystemSettings, 'id'> | null, o2: Pick<ISystemSettings, 'id'> | null): boolean {
    return o1 && o2 ? this.getSystemSettingsIdentifier(o1) === this.getSystemSettingsIdentifier(o2) : o1 === o2;
  }

  addSystemSettingsToCollectionIfMissing<Type extends Pick<ISystemSettings, 'id'>>(
    systemSettingsCollection: Type[],
    ...systemSettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const systemSettings: Type[] = systemSettingsToCheck.filter(isPresent);
    if (systemSettings.length > 0) {
      const systemSettingsCollectionIdentifiers = systemSettingsCollection.map(systemSettingsItem =>
        this.getSystemSettingsIdentifier(systemSettingsItem),
      );
      const systemSettingsToAdd = systemSettings.filter(systemSettingsItem => {
        const systemSettingsIdentifier = this.getSystemSettingsIdentifier(systemSettingsItem);
        if (systemSettingsCollectionIdentifiers.includes(systemSettingsIdentifier)) {
          return false;
        }
        systemSettingsCollectionIdentifiers.push(systemSettingsIdentifier);
        return true;
      });
      return [...systemSettingsToAdd, ...systemSettingsCollection];
    }
    return systemSettingsCollection;
  }

  protected convertDateFromClient<T extends ISystemSettings | NewSystemSettings | PartialUpdateSystemSettings>(
    systemSettings: T,
  ): RestOf<T> {
    return {
      ...systemSettings,
      lmd: systemSettings.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSystemSettings: RestSystemSettings): ISystemSettings {
    return {
      ...restSystemSettings,
      lmd: restSystemSettings.lmd ? dayjs(restSystemSettings.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSystemSettings>): HttpResponse<ISystemSettings> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSystemSettings[]>): HttpResponse<ISystemSettings[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
