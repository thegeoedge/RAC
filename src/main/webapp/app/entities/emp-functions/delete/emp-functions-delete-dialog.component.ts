import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEmpFunctions } from '../emp-functions.model';
import { EmpFunctionsService } from '../service/emp-functions.service';

@Component({
  templateUrl: './emp-functions-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
  standalone: true,
})
export class EmpFunctionsDeleteDialogComponent {
  empFunctions?: IEmpFunctions;

  protected empFunctionsService = inject(EmpFunctionsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empFunctionsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
