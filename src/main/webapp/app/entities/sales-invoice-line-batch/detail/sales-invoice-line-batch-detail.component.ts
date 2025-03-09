import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-line-batch-detail',
  templateUrl: './sales-invoice-line-batch-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalesInvoiceLineBatchDetailComponent {
  salesInvoiceLineBatch = input<ISalesInvoiceLineBatch | null>(null);

  previousState(): void {
    window.history.back();
  }
}
