import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IAutoCareVehicle } from '../auto-care-vehicle.model';

@Component({
  standalone: true,
  selector: 'jhi-auto-care-vehicle-detail',
  templateUrl: './auto-care-vehicle-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class AutoCareVehicleDetailComponent {
  autoCareVehicle = input<IAutoCareVehicle | null>(null);

  previousState(): void {
    window.history.back();
  }
}
