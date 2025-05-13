import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEmpRoleFunctionPermission } from '../emp-role-function-permission.model';

@Component({
  selector: 'jhi-emp-role-function-permission-detail',
  templateUrl: './emp-role-function-permission-detail.component.html',
  imports: [SharedModule, RouterModule],
  standalone: true,
})
export class EmpRoleFunctionPermissionDetailComponent {
  empRoleFunctionPermission = input<IEmpRoleFunctionPermission | null>(null);

  previousState(): void {
    window.history.back();
  }
}
