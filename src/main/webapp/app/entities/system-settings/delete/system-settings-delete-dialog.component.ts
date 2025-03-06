import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISystemSettings } from '../system-settings.model';
import { SystemSettingsService } from '../service/system-settings.service';

@Component({
  standalone: true,
  templateUrl: './system-settings-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SystemSettingsDeleteDialogComponent {
  systemSettings?: ISystemSettings;

  protected systemSettingsService = inject(SystemSettingsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.systemSettingsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
