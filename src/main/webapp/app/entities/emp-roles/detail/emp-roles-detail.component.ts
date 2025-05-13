import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEmpRoles } from '../emp-roles.model';

@Component({
  selector: 'jhi-emp-roles-detail',
  templateUrl: './emp-roles-detail.component.html',
  imports: [SharedModule, RouterModule],
  standalone: true,
})
export class EmpRolesDetailComponent {
  empRoles = input<IEmpRoles | null>(null);

  previousState(): void {
    window.history.back();
  }
}
