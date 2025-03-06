import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISystemSettings } from '../system-settings.model';

@Component({
  standalone: true,
  selector: 'jhi-system-settings-detail',
  templateUrl: './system-settings-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SystemSettingsDetailComponent {
  systemSettings = input<ISystemSettings | null>(null);

  previousState(): void {
    window.history.back();
  }
}
