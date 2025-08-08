import { Component, NgZone, inject, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { ISalesinvoice } from '../salesinvoice.model';
import { EntityArrayResponseType, SalesinvoiceService } from '../service/salesinvoice.service';
import { SalesinvoiceDeleteDialogComponent } from '../delete/salesinvoice-delete-dialog.component';
import { faPrint } from '@fortawesome/free-solid-svg-icons';

@Component({
  standalone: true,
  selector: 'jhi-salesinvoice',
  templateUrl: './salesinvoice.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ItemCountComponent,
  ],
})
export class SalesinvoiceComponent implements OnInit {
  subscription: Subscription | null = null;
  salesinvoices?: ISalesinvoice[];
  isLoading = false;
  faPrint = faPrint;
  sortState = sortStateSignal({});
  searchValue: string = '';
  selectedSearchType: string = 'jobdate';
  field_input1: string = 'field_input1';
  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected salesinvoiceService = inject(SalesinvoiceService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: ISalesinvoice): number => this.salesinvoiceService.getSalesinvoiceIdentifier(item);
  emproles: string[] = [];
  showJobLink2: boolean = false;
  showJobLink1: boolean = false;
  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
    const today = new Date().toISOString().split('T')[0];
    this.searchValue = today;
    this.selectedSearchType = 'jobdate';
  }

  delete(salesinvoice: ISalesinvoice): void {
    const modalRef = this.modalService.open(SalesinvoiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesinvoice = salesinvoice;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }
  onInputChange(): void {
    //this.page = 1; // optional: reset to first page
    this.load();
  }
  load(): void {
    const rolesFromStorage = JSON.parse(localStorage.getItem('emproles') || '[]');
    console.log('checckkkkkkkk45', rolesFromStorage);
    console.log('hhhhhhrolessssssss', this.emproles);

    // Add to this.emproles
    this.emproles.push(...rolesFromStorage);
    console.log('Updated emproles:', this.emproles);

    this.showJobLink2 = this.emproles.includes('Add new sales invoice');
    this.showJobLink1 = this.emproles.includes('Search sales invoice');
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page, event);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState());
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.salesinvoices = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: ISalesinvoice[] | null): ISalesinvoice[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page } = this;
    this.isLoading = true;
    const pageToLoad: number = page;

    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    if (this.selectedSearchType === 'jobdate') {
      let jobDate = this.searchValue;

      if (!jobDate) {
        const today = new Date().toISOString().split('T')[0];
        jobDate = today;
      }

      queryObject['invoicedate.greaterThanOrEqual'] = `${jobDate}T00:00:00.000Z`;
      queryObject['invoicedate.lessThanOrEqual'] = `${jobDate}T23:59:59.999Z`;
    } else if (this.selectedSearchType === 'vehicle') {
      if (this.searchValue) {
        queryObject['vehicleno.contains'] = this.searchValue;
      }
    } else if (this.selectedSearchType === 'customer') {
      if (this.searchValue) {
        queryObject['customername.contains'] = this.searchValue;
      }
    }

    console.log('Query Object:', queryObject);

    return this.salesinvoiceService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number, sortState: SortState): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
