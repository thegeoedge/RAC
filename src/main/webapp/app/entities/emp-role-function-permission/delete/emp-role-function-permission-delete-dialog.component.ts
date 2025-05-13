import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEmpRoleFunctionPermission } from '../emp-role-function-permission.model';
import { EmpRoleFunctionPermissionService } from '../service/emp-role-function-permission.service';

@Component({
  templateUrl: './emp-role-function-permission-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
  standalone: true,
})
export class EmpRoleFunctionPermissionDeleteDialogComponent {
  empRoleFunctionPermission?: IEmpRoleFunctionPermission;

  protected empRoleFunctionPermissionService = inject(EmpRoleFunctionPermissionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empRoleFunctionPermissionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
