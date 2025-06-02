import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IBankbranch } from '../bankbranch.model';

@Component({
  selector: 'jhi-bankbranch-detail',
  templateUrl: './bankbranch-detail.component.html',
})
export class BankbranchDetailComponent {
  bankbranch = input<IBankbranch | null>(null);

  previousState(): void {
    window.history.back();
  }
}
