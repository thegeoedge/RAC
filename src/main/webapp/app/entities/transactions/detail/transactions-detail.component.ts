import { Component, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { DataUtils } from 'app/core/util/data-util.service';
import { ITransactions } from '../transactions.model';

@Component({
  selector: 'jhi-transactions-detail',
  templateUrl: './transactions-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
  standalone: true,
})
export class TransactionsDetailComponent {
  transactions = input<ITransactions | null>(null);

  protected dataUtils = inject(DataUtils);

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
