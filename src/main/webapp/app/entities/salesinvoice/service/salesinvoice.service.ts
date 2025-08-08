import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { map, catchError, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { HttpErrorResponse } from '@angular/common/http';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesinvoice, NewSalesinvoice } from '../salesinvoice.model';
import { RestInventory } from 'app/entities/inventory/service/inventory.service';
import { PartialUpdateSystemSettings, RestSystemSettings } from 'app/entities/system-settings/service/system-settings.service';

export type PartialUpdateSalesinvoice = Partial<ISalesinvoice> & Pick<ISalesinvoice, 'id'>;

type RestOf<T extends ISalesinvoice | NewSalesinvoice> = Omit<
  T,
  'invoicedate' | 'createddate' | 'delieverydate' | 'lmd' | 'invcanceldate'
> & {
  invoicedate?: string | null;
  createddate?: string | null;
  delieverydate?: string | null;
  lmd?: string | null;
  invcanceldate?: string | null;
};

export type RestSalesinvoice = RestOf<ISalesinvoice>;

export type NewRestSalesinvoice = RestOf<NewSalesinvoice>;

export type PartialUpdateRestSalesinvoice = RestOf<PartialUpdateSalesinvoice>;

export type EntityResponseType = HttpResponse<ISalesinvoice>;
export type EntityArrayResponseType = HttpResponse<ISalesinvoice[]>;

@Injectable({ providedIn: 'root' })
export class SalesinvoiceService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/salesinvoices');

  protected resourceInvoiceLinesUrl = this.applicationConfigService.getEndpointFor('api/autojobsinvoicelines');
  protected resourceInvoiceLinesUrli = this.applicationConfigService.getEndpointFor('api/autojobsalesinvoiceservicechargelines');

  protected resourceInvoiceLinesUrlsercom = this.applicationConfigService.getEndpointFor(
    'api/autojobsaleinvoicecommonservicecharges/by-invoice-idss',
  );
  protected resourceJobInvoiceLinesUrlz = this.applicationConfigService.getEndpointFor('api/autojobsinvoicelines/by-invoice-id');
  resourceJobInvoiceLinesUrly = this.applicationConfigService.getEndpointFor('api/autojobsalesinvoiceservicechargelines/by-invoice-ids');
  fetchService(id: number): Observable<HttpResponse<any>> {
    const params = new HttpParams().set('invoiceID', id.toString());
    return this.http.get<any>(`${this.resourceJobInvoiceLinesUrly}`, { params, observe: 'response' });
  }

  resourceJobInvoiceUrl = this.applicationConfigService.getEndpointFor('api/autojobsinvoices');

  fetchJobInvoice(id: number): Observable<HttpResponse<any>> {
    return this.http.get<any>(`/api/autojobsinvoices?id.equals=${id}`, { observe: 'response' });
  }

  fetchServiceCommon(id: number): Observable<HttpResponse<any>> {
    const params = new HttpParams().set('invoiceID', id.toString());
    return this.http.get<any>(`${this.resourceInvoiceLinesUrlsercom}`, { params, observe: 'response' });
  }
  vehicleno: string = '';
  setVehicleNo(vehicleno: string): void {
    this.vehicleno = vehicleno;
  }
  getVehicleNo(): string {
    return this.vehicleno;
  }

  total: number = 0;
  setTotal(total: number): void {
    this.total = total;
  }
  getTotal(): number {
    return this.total;
  }
  private customername: string = '';
  setcustomer(name: string): void {
    this.customername = name;
  }

  getCustomerName(): string {
    return this.customername;
  }

  private customerid: number = 0;
  setCustomerId(id: number): void {
    this.customerid = id;
  }
  getCustomerId(): number {
    return this.customerid;
  }

  fetchReceiptCode(): Observable<HttpResponse<any>> {
    return this.http.get<HttpResponse<any>>('/api/receipts?page=0&size=20&sort=id,desc', { observe: 'response' });
  }

  fetchReceiptAccountId(name: string): Observable<HttpResponse<any>> {
    const url = `/api/accounts?name.equals=${encodeURIComponent(name)}`;
    console.log('Request URL:', url); // Log the request URL for debugging purposes
    return this.http.get<HttpResponse<any>>(url, { observe: 'response' }).pipe(
      map(response => {
        console.log('Response Data:', response.body); // Log the response body to see the fetched data
        return response;
      }),
      catchError(error => {
        console.error('Error fetching account:', error); // Log any errors in the console
        throw error;
      }),
    );
  }

  fetchInvoiceLines(id: number): Observable<HttpResponse<any>> {
    const params = new HttpParams().set('invocieID', id.toString());
    return this.http.get<any>(`${this.resourceJobInvoiceLinesUrlz}`, { params, observe: 'response' });
  }

  create(salesinvoice: NewSalesinvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesinvoice);
    return this.http
      .post<RestSalesinvoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  getElementsByUserInputCode(userInputCode: string): Observable<EntityArrayResponseType> {
    const url = this.applicationConfigService.getEndpointFor(
      `api/inventories?code.contains=${userInputCode}&availablequantity.greaterThan=0&page=0&size=200000`,
    );
    return this.http
      .get<IInventory[]>(url, { observe: 'response' })
      .pipe(map((res: HttpResponse<IInventory[]>) => this.convertResponseArrayFromServer(res as HttpResponse<RestInventory[]>)));
  }

  update(salesinvoice: ISalesinvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesinvoice);
    return this.http
      .put<RestSalesinvoice>(`${this.resourceUrl}/${this.getSalesinvoiceIdentifier(salesinvoice)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }
  protected resourceUrly = this.applicationConfigService.getEndpointFor('api/system-settings');
  partialUpdatee(systemSettings: PartialUpdateSystemSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(systemSettings);
    return this.http
      .patch<RestSystemSettings>(`${this.resourceUrly}/${this.getSystemSettingsIdentifier(systemSettings)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  getSystemSettingsIdentifier(systemSettings: Pick<PartialUpdateSystemSettings, 'id'>): number {
    return systemSettings.id;
  }

  partialUpdate(salesinvoice: PartialUpdateSalesinvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesinvoice);
    return this.http
      .patch<RestSalesinvoice>(`${this.resourceUrl}/${this.getSalesinvoiceIdentifier(salesinvoice)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSalesinvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSalesinvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalesinvoiceIdentifier(salesinvoice: Pick<ISalesinvoice, 'id'>): number {
    return salesinvoice.id;
  }

  compareSalesinvoice(o1: Pick<ISalesinvoice, 'id'> | null, o2: Pick<ISalesinvoice, 'id'> | null): boolean {
    return o1 && o2 ? this.getSalesinvoiceIdentifier(o1) === this.getSalesinvoiceIdentifier(o2) : o1 === o2;
  }

  addSalesinvoiceToCollectionIfMissing<Type extends Pick<ISalesinvoice, 'id'>>(
    salesinvoiceCollection: Type[],
    ...salesinvoicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salesinvoices: Type[] = salesinvoicesToCheck.filter(isPresent);
    if (salesinvoices.length > 0) {
      const salesinvoiceCollectionIdentifiers = salesinvoiceCollection.map(salesinvoiceItem =>
        this.getSalesinvoiceIdentifier(salesinvoiceItem),
      );
      const salesinvoicesToAdd = salesinvoices.filter(salesinvoiceItem => {
        const salesinvoiceIdentifier = this.getSalesinvoiceIdentifier(salesinvoiceItem);
        if (salesinvoiceCollectionIdentifiers.includes(salesinvoiceIdentifier)) {
          return false;
        }
        salesinvoiceCollectionIdentifiers.push(salesinvoiceIdentifier);
        return true;
      });
      return [...salesinvoicesToAdd, ...salesinvoiceCollection];
    }
    return salesinvoiceCollection;
  }

  protected convertDateFromClient<T extends ISalesinvoice | NewSalesinvoice | PartialUpdateSalesinvoice>(salesinvoice: T): RestOf<T> {
    return {
      ...salesinvoice,
      invoicedate: salesinvoice.invoicedate?.toJSON() ?? null,
      createddate: salesinvoice.createddate?.toJSON() ?? null,
      delieverydate: salesinvoice.delieverydate?.toJSON() ?? null,
      lmd: salesinvoice.lmd?.toJSON() ?? null,
      invcanceldate: salesinvoice.invcanceldate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSalesinvoice: RestSalesinvoice): ISalesinvoice {
    return {
      ...restSalesinvoice,
      invoicedate: restSalesinvoice.invoicedate ? dayjs(restSalesinvoice.invoicedate) : undefined,
      createddate: restSalesinvoice.createddate ? dayjs(restSalesinvoice.createddate) : undefined,
      delieverydate: restSalesinvoice.delieverydate ? dayjs(restSalesinvoice.delieverydate) : undefined,
      lmd: restSalesinvoice.lmd ? dayjs(restSalesinvoice.lmd) : undefined,
      invcanceldate: restSalesinvoice.invcanceldate ? dayjs(restSalesinvoice.invcanceldate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSalesinvoice>): HttpResponse<ISalesinvoice> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSalesinvoice[]>): HttpResponse<ISalesinvoice[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
