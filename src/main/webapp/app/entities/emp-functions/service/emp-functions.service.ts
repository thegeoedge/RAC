import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpFunctions, NewEmpFunctions } from '../emp-functions.model';

export type PartialUpdateEmpFunctions = Partial<IEmpFunctions> & Pick<IEmpFunctions, 'functionId'>;

export type EntityResponseType = HttpResponse<IEmpFunctions>;
export type EntityArrayResponseType = HttpResponse<IEmpFunctions[]>;

@Injectable({ providedIn: 'root' })
export class EmpFunctionsService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emp-functions');

  create(empFunctions: NewEmpFunctions): Observable<EntityResponseType> {
    return this.http.post<IEmpFunctions>(this.resourceUrl, empFunctions, { observe: 'response' });
  }

  update(empFunctions: IEmpFunctions): Observable<EntityResponseType> {
    return this.http.put<IEmpFunctions>(`${this.resourceUrl}/${this.getEmpFunctionsIdentifier(empFunctions)}`, empFunctions, {
      observe: 'response',
    });
  }

  partialUpdate(empFunctions: PartialUpdateEmpFunctions): Observable<EntityResponseType> {
    return this.http.patch<IEmpFunctions>(`${this.resourceUrl}/${this.getEmpFunctionsIdentifier(empFunctions)}`, empFunctions, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpFunctions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpFunctions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpFunctionsIdentifier(empFunctions: Pick<IEmpFunctions, 'functionId'>): number {
    return empFunctions.functionId;
  }

  compareEmpFunctions(o1: Pick<IEmpFunctions, 'functionId'> | null, o2: Pick<IEmpFunctions, 'functionId'> | null): boolean {
    return o1 && o2 ? this.getEmpFunctionsIdentifier(o1) === this.getEmpFunctionsIdentifier(o2) : o1 === o2;
  }

  addEmpFunctionsToCollectionIfMissing<Type extends Pick<IEmpFunctions, 'functionId'>>(
    empFunctionsCollection: Type[],
    ...empFunctionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const empFunctions: Type[] = empFunctionsToCheck.filter(isPresent);
    if (empFunctions.length > 0) {
      const empFunctionsCollectionIdentifiers = empFunctionsCollection.map(empFunctionsItem =>
        this.getEmpFunctionsIdentifier(empFunctionsItem),
      );
      const empFunctionsToAdd = empFunctions.filter(empFunctionsItem => {
        const empFunctionsIdentifier = this.getEmpFunctionsIdentifier(empFunctionsItem);
        if (empFunctionsCollectionIdentifiers.includes(empFunctionsIdentifier)) {
          return false;
        }
        empFunctionsCollectionIdentifiers.push(empFunctionsIdentifier);
        return true;
      });
      return [...empFunctionsToAdd, ...empFunctionsCollection];
    }
    return empFunctionsCollection;
  }
}
