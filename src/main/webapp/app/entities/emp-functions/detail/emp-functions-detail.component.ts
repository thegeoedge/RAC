import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEmpFunctions } from '../emp-functions.model';

@Component({
  selector: 'jhi-emp-functions-detail',
  templateUrl: './emp-functions-detail.component.html',
  imports: [SharedModule, RouterModule],
  standalone: true,
})
export class EmpFunctionsDetailComponent {
  empFunctions = input<IEmpFunctions | null>(null);

  previousState(): void {
    window.history.back();
  }
}
