import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { TransactionsService } from '../service/transactions.service';
import { ITransactions } from '../transactions.model';
import { TransactionsFormGroup, TransactionsFormService } from './transactions-form.service';

@Component({
  selector: 'jhi-transactions-update',
  templateUrl: './transactions-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  standalone: true,
})
export class TransactionsUpdateComponent implements OnInit {
  isSaving = false;
  transactions: ITransactions | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected transactionsService = inject(TransactionsService);
  protected transactionsFormService = inject(TransactionsFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TransactionsFormGroup = this.transactionsFormService.createTransactionsFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactions }) => {
      this.transactions = transactions;
      if (transactions) {
        this.updateForm(transactions);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('racApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactions = this.transactionsFormService.getTransactions(this.editForm);
    if (transactions.id !== null) {
      this.subscribeToSaveResponse(this.transactionsService.update(transactions));
    } else {
      this.subscribeToSaveResponse(this.transactionsService.create(transactions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactions>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(transactions: ITransactions): void {
    this.transactions = transactions;
    this.transactionsFormService.resetForm(this.editForm, transactions);
  }
}
