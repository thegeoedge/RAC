import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IBanks } from '../banks.model';

@Component({
  selector: 'jhi-banks-detail',
  templateUrl: './banks-detail.component.html',
  standalone: true,
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class BanksDetailComponent {
  banks = input<IBanks | null>(null);

  previousState(): void {
    window.history.back();
  }
}
