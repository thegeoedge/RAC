import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpRoleFunctionPermission, NewEmpRoleFunctionPermission } from '../emp-role-function-permission.model';

export type PartialUpdateEmpRoleFunctionPermission = Partial<IEmpRoleFunctionPermission> & Pick<IEmpRoleFunctionPermission, 'id'>;

export type EntityResponseType = HttpResponse<IEmpRoleFunctionPermission>;
export type EntityArrayResponseType = HttpResponse<IEmpRoleFunctionPermission[]>;

@Injectable({ providedIn: 'root' })
export class EmpRoleFunctionPermissionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emp-role-function-permissions');

  create(empRoleFunctionPermission: NewEmpRoleFunctionPermission): Observable<EntityResponseType> {
    return this.http.post<IEmpRoleFunctionPermission>(this.resourceUrl, empRoleFunctionPermission, { observe: 'response' });
  }

  update(empRoleFunctionPermission: IEmpRoleFunctionPermission): Observable<EntityResponseType> {
    return this.http.put<IEmpRoleFunctionPermission>(
      `${this.resourceUrl}/${this.getEmpRoleFunctionPermissionIdentifier(empRoleFunctionPermission)}`,
      empRoleFunctionPermission,
      { observe: 'response' },
    );
  }

  partialUpdate(empRoleFunctionPermission: PartialUpdateEmpRoleFunctionPermission): Observable<EntityResponseType> {
    return this.http.patch<IEmpRoleFunctionPermission>(
      `${this.resourceUrl}/${this.getEmpRoleFunctionPermissionIdentifier(empRoleFunctionPermission)}`,
      empRoleFunctionPermission,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpRoleFunctionPermission>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpRoleFunctionPermission[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpRoleFunctionPermissionIdentifier(empRoleFunctionPermission: Pick<IEmpRoleFunctionPermission, 'id'>): number {
    return empRoleFunctionPermission.id;
  }

  compareEmpRoleFunctionPermission(
    o1: Pick<IEmpRoleFunctionPermission, 'id'> | null,
    o2: Pick<IEmpRoleFunctionPermission, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getEmpRoleFunctionPermissionIdentifier(o1) === this.getEmpRoleFunctionPermissionIdentifier(o2) : o1 === o2;
  }

  addEmpRoleFunctionPermissionToCollectionIfMissing<Type extends Pick<IEmpRoleFunctionPermission, 'id'>>(
    empRoleFunctionPermissionCollection: Type[],
    ...empRoleFunctionPermissionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const empRoleFunctionPermissions: Type[] = empRoleFunctionPermissionsToCheck.filter(isPresent);
    if (empRoleFunctionPermissions.length > 0) {
      const empRoleFunctionPermissionCollectionIdentifiers = empRoleFunctionPermissionCollection.map(empRoleFunctionPermissionItem =>
        this.getEmpRoleFunctionPermissionIdentifier(empRoleFunctionPermissionItem),
      );
      const empRoleFunctionPermissionsToAdd = empRoleFunctionPermissions.filter(empRoleFunctionPermissionItem => {
        const empRoleFunctionPermissionIdentifier = this.getEmpRoleFunctionPermissionIdentifier(empRoleFunctionPermissionItem);
        if (empRoleFunctionPermissionCollectionIdentifiers.includes(empRoleFunctionPermissionIdentifier)) {
          return false;
        }
        empRoleFunctionPermissionCollectionIdentifiers.push(empRoleFunctionPermissionIdentifier);
        return true;
      });
      return [...empRoleFunctionPermissionsToAdd, ...empRoleFunctionPermissionCollection];
    }
    return empRoleFunctionPermissionCollection;
  }
}
