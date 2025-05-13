import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AutoCareVehicleDetailComponent } from './auto-care-vehicle-detail.component';

describe('AutoCareVehicle Management Detail Component', () => {
  let comp: AutoCareVehicleDetailComponent;
  let fixture: ComponentFixture<AutoCareVehicleDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AutoCareVehicleDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./auto-care-vehicle-detail.component').then(m => m.AutoCareVehicleDetailComponent),
              resolve: { autoCareVehicle: () => of({ id: 11116 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AutoCareVehicleDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutoCareVehicleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load autoCareVehicle on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AutoCareVehicleDetailComponent);

      // THEN
      expect(instance.autoCareVehicle()).toEqual(expect.objectContaining({ id: 11116 }));
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
