import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AutoCareVehicleService } from '../service/auto-care-vehicle.service';
import { IAutoCareVehicle } from '../auto-care-vehicle.model';
import { AutoCareVehicleFormService } from './auto-care-vehicle-form.service';

import { AutoCareVehicleUpdateComponent } from './auto-care-vehicle-update.component';

describe('AutoCareVehicle Management Update Component', () => {
  let comp: AutoCareVehicleUpdateComponent;
  let fixture: ComponentFixture<AutoCareVehicleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autoCareVehicleFormService: AutoCareVehicleFormService;
  let autoCareVehicleService: AutoCareVehicleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutoCareVehicleUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AutoCareVehicleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AutoCareVehicleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    autoCareVehicleFormService = TestBed.inject(AutoCareVehicleFormService);
    autoCareVehicleService = TestBed.inject(AutoCareVehicleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const autoCareVehicle: IAutoCareVehicle = { id: 21145 };

      activatedRoute.data = of({ autoCareVehicle });
      comp.ngOnInit();

      expect(comp.autoCareVehicle).toEqual(autoCareVehicle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutoCareVehicle>>();
      const autoCareVehicle = { id: 11116 };
      jest.spyOn(autoCareVehicleFormService, 'getAutoCareVehicle').mockReturnValue(autoCareVehicle);
      jest.spyOn(autoCareVehicleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autoCareVehicle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autoCareVehicle }));
      saveSubject.complete();

      // THEN
      expect(autoCareVehicleFormService.getAutoCareVehicle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(autoCareVehicleService.update).toHaveBeenCalledWith(expect.objectContaining(autoCareVehicle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutoCareVehicle>>();
      const autoCareVehicle = { id: 11116 };
      jest.spyOn(autoCareVehicleFormService, 'getAutoCareVehicle').mockReturnValue({ id: null });
      jest.spyOn(autoCareVehicleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autoCareVehicle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autoCareVehicle }));
      saveSubject.complete();

      // THEN
      expect(autoCareVehicleFormService.getAutoCareVehicle).toHaveBeenCalled();
      expect(autoCareVehicleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutoCareVehicle>>();
      const autoCareVehicle = { id: 11116 };
      jest.spyOn(autoCareVehicleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autoCareVehicle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(autoCareVehicleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
