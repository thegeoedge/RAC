import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAutoCareVehicle } from '../auto-care-vehicle.model';
import { AutoCareVehicleService } from '../service/auto-care-vehicle.service';

@Component({
  standalone: true,
  templateUrl: './auto-care-vehicle-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AutoCareVehicleDeleteDialogComponent {
  autoCareVehicle?: IAutoCareVehicle;

  protected autoCareVehicleService = inject(AutoCareVehicleService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autoCareVehicleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
