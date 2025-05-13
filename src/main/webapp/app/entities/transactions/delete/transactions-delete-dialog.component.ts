import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITransactions } from '../transactions.model';
import { TransactionsService } from '../service/transactions.service';

@Component({
  templateUrl: './transactions-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
  standalone: true,
})
export class TransactionsDeleteDialogComponent {
  transactions?: ITransactions;

  protected transactionsService = inject(TransactionsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transactionsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
