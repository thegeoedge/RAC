import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IPaymentMethod } from '../payment-method.model';

@Component({
  selector: 'jhi-payment-method-detail',
  templateUrl: './payment-method-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
  standalone: true,
})
export class PaymentMethodDetailComponent {
  paymentMethod = input<IPaymentMethod | null>(null);

  previousState(): void {
    window.history.back();
  }
}
