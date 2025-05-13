import { Component, NgZone, OnInit, inject } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { FilterComponent, FilterOptions, IFilterOption, IFilterOptions } from 'app/shared/filter';
import { ISalesInvoiceLines } from '../sales-invoice-lines.model';

import { EntityArrayResponseType, SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';
import { SalesInvoiceLinesDeleteDialogComponent } from '../delete/sales-invoice-lines-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines',
  templateUrl: './sales-invoice-lines.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    FilterComponent,
    ItemCountComponent,
  ],
})
export class SalesInvoiceLinesComponent implements OnInit {
  subscription: Subscription | null = null;
  salesInvoiceLines?: ISalesInvoiceLines[];
  isLoading = false;

  sortState = sortStateSignal({});
  filters: IFilterOptions = new FilterOptions();
  field_input1: string = 'field_input1';
  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  selectedSearchType: string = 'jobdate';
  searchValue: string = '';
  public router = inject(Router);
  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);
  salesinvoicelines = inject(SalesInvoiceLinesService);
  trackId = (item: ISalesInvoiceLines): number => this.salesInvoiceLinesService.getSalesInvoiceLinesIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.sortState(), filterOptions));
  }

  delete(salesInvoiceLines: ISalesInvoiceLines): void {
    const modalRef = this.modalService.open(SalesInvoiceLinesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesInvoiceLines = salesInvoiceLines;
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
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        console.log('Response from Backend:', res); // Log the response to console
        this.onResponseSuccess(res);
      },
      error: err => {
        console.error('Error fetching data:', err); // Log any errors that occur
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page, event, this.filters.filterOptions);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState(), this.filters.filterOptions);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
    this.filters.initializeFromParams(params);
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.salesInvoiceLines = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: ISalesInvoiceLines[] | null): ISalesInvoiceLines[] {
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

      queryObject['jobdate.greaterThan'] = `${jobDate}T00:00:00.000Z`;
    } else if (this.selectedSearchType === 'vehicle') {
      if (this.searchValue) {
        queryObject['vehiclenumber.contains'] = this.searchValue;
      }
    } else if (this.selectedSearchType === 'customer') {
      if (this.searchValue) {
        queryObject['customername.contains'] = this.searchValue;
      }
    }

    console.log('Query Object:', queryObject);

    return this.salesinvoicelines.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number, sortState: SortState, filterOptions?: IFilterOption[]): void {
    const queryParamsObj: any = {
      page,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(sortState),
    };

    filterOptions?.forEach(filterOption => {
      queryParamsObj[filterOption.nameAsQueryParam()] = filterOption.values;
    });

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
