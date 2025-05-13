import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpRoles, NewEmpRoles } from '../emp-roles.model';

export type PartialUpdateEmpRoles = Partial<IEmpRoles> & Pick<IEmpRoles, 'roleId'>;

export type EntityResponseType = HttpResponse<IEmpRoles>;
export type EntityArrayResponseType = HttpResponse<IEmpRoles[]>;

@Injectable({ providedIn: 'root' })
export class EmpRolesService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emp-roles');

  create(empRoles: NewEmpRoles): Observable<EntityResponseType> {
    return this.http.post<IEmpRoles>(this.resourceUrl, empRoles, { observe: 'response' });
  }

  update(empRoles: IEmpRoles): Observable<EntityResponseType> {
    return this.http.put<IEmpRoles>(`${this.resourceUrl}/${this.getEmpRolesIdentifier(empRoles)}`, empRoles, { observe: 'response' });
  }

  partialUpdate(empRoles: PartialUpdateEmpRoles): Observable<EntityResponseType> {
    return this.http.patch<IEmpRoles>(`${this.resourceUrl}/${this.getEmpRolesIdentifier(empRoles)}`, empRoles, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpRoles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpRoles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpRolesIdentifier(empRoles: Pick<IEmpRoles, 'roleId'>): number {
    return empRoles.roleId;
  }

  compareEmpRoles(o1: Pick<IEmpRoles, 'roleId'> | null, o2: Pick<IEmpRoles, 'roleId'> | null): boolean {
    return o1 && o2 ? this.getEmpRolesIdentifier(o1) === this.getEmpRolesIdentifier(o2) : o1 === o2;
  }

  addEmpRolesToCollectionIfMissing<Type extends Pick<IEmpRoles, 'roleId'>>(
    empRolesCollection: Type[],
    ...empRolesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const empRoles: Type[] = empRolesToCheck.filter(isPresent);
    if (empRoles.length > 0) {
      const empRolesCollectionIdentifiers = empRolesCollection.map(empRolesItem => this.getEmpRolesIdentifier(empRolesItem));
      const empRolesToAdd = empRoles.filter(empRolesItem => {
        const empRolesIdentifier = this.getEmpRolesIdentifier(empRolesItem);
        if (empRolesCollectionIdentifiers.includes(empRolesIdentifier)) {
          return false;
        }
        empRolesCollectionIdentifiers.push(empRolesIdentifier);
        return true;
      });
      return [...empRolesToAdd, ...empRolesCollection];
    }
    return empRolesCollection;
  }
}
