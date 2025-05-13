import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmpFunctionsDetailComponent } from './emp-functions-detail.component';

describe('EmpFunctions Management Detail Component', () => {
  let comp: EmpFunctionsDetailComponent;
  let fixture: ComponentFixture<EmpFunctionsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpFunctionsDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./emp-functions-detail.component').then(m => m.EmpFunctionsDetailComponent),
              resolve: { empFunctions: () => of({ functionId: 15909 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmpFunctionsDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmpFunctionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load empFunctions on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmpFunctionsDetailComponent);

      // THEN
      expect(instance.empFunctions()).toEqual(expect.objectContaining({ functionId: 15909 }));
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
