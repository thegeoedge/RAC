import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';
import { SalesInvoiceLineBatchService } from '../service/sales-invoice-line-batch.service';

@Component({
  standalone: true,
  templateUrl: './sales-invoice-line-batch-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SalesInvoiceLineBatchDeleteDialogComponent {
  salesInvoiceLineBatch?: ISalesInvoiceLineBatch;

  protected salesInvoiceLineBatchService = inject(SalesInvoiceLineBatchService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesInvoiceLineBatchService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
