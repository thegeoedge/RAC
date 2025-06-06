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
import { IAutocarejob } from '../autocarejob.model';
import { EntityArrayResponseType, AutocarejobService } from '../service/autocarejob.service';
import { AutocarejobDeleteDialogComponent } from '../delete/autocarejob-delete-dialog.component';
import { AutocareclosejobComponent } from '../list/autocareclosejob.component';

@Component({
  standalone: true,
  selector: 'jhi-autocareopenjob',
  templateUrl: './autocareopenjob.componenet.html',
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
    AutocareclosejobComponent,
  ],
})
export class AutocareopenjobComponent implements OnInit {
  subscription: Subscription | null = null;
  autocarejobs?: IAutocarejob[];
  isLoading = false;
  filteredAutocarejobs: IAutocarejob[] = [];
  searchText: string = '';

  sortState = sortStateSignal({});

  itemsPerPage = 150;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected autocarejobService = inject(AutocarejobService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IAutocarejob): number => this.autocarejobService.getAutocarejobIdentifier(item);
  emproles: string[] = [];
  showJobLink: boolean = false;
  showJobLink1: boolean = false;
  showJobLink2: boolean = false;
  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
    const emprolesString = localStorage.getItem('emproles');
    this.emproles = emprolesString ? JSON.parse(emprolesString) : [];

    console.log('checckkkkkkkk', this.emproles);
    //Add new inventory item
    // Advisor Instruction     Issue Items   Add new sales invoice
    this.showJobLink = this.emproles.includes('Add new inventory item');
    this.showJobLink1 = this.emproles.includes('Add new inventory item');
    this.showJobLink2 = this.emproles.includes('Add new inventory item');
  }

  delete(autocarejob: IAutocarejob): void {
    const modalRef = this.modalService.open(AutocarejobDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.autocarejob = autocarejob;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
        const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

        this.autocarejobs = this.autocarejobs?.filter(
          job => !job.isjobclose, // Only show open jobs for today
        );

        this.filterJobs(); // Apply filtering when loading data
      },
    });
  }

  filterJobs(): void {
    if (!this.autocarejobs) return;

    const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

    this.filteredAutocarejobs = this.autocarejobs.filter(
      job => job.vehiclenumber?.toLowerCase().includes(this.searchText.toLowerCase()) && !job.isjobclose, // Ensure job date matches today
    );
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
    this.autocarejobs = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IAutocarejob[] | null): IAutocarejob[] {
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
    return this.autocarejobService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
