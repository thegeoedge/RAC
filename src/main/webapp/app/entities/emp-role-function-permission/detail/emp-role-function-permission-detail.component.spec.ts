import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmpRoleFunctionPermissionDetailComponent } from './emp-role-function-permission-detail.component';

describe('EmpRoleFunctionPermission Management Detail Component', () => {
  let comp: EmpRoleFunctionPermissionDetailComponent;
  let fixture: ComponentFixture<EmpRoleFunctionPermissionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpRoleFunctionPermissionDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./emp-role-function-permission-detail.component').then(m => m.EmpRoleFunctionPermissionDetailComponent),
              resolve: { empRoleFunctionPermission: () => of({ id: 23088 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmpRoleFunctionPermissionDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmpRoleFunctionPermissionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load empRoleFunctionPermission on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmpRoleFunctionPermissionDetailComponent);

      // THEN
      expect(instance.empRoleFunctionPermission()).toEqual(expect.objectContaining({ id: 23088 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
