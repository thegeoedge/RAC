import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SystemSettingsService } from '../service/system-settings.service';
import { ISystemSettings } from '../system-settings.model';
import { SystemSettingsFormService } from './system-settings-form.service';

import { SystemSettingsUpdateComponent } from './system-settings-update.component';

describe('SystemSettings Management Update Component', () => {
  let comp: SystemSettingsUpdateComponent;
  let fixture: ComponentFixture<SystemSettingsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let systemSettingsFormService: SystemSettingsFormService;
  let systemSettingsService: SystemSettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SystemSettingsUpdateComponent],
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
      .overrideTemplate(SystemSettingsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SystemSettingsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    systemSettingsFormService = TestBed.inject(SystemSettingsFormService);
    systemSettingsService = TestBed.inject(SystemSettingsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const systemSettings: ISystemSettings = { id: 456 };

      activatedRoute.data = of({ systemSettings });
      comp.ngOnInit();

      expect(comp.systemSettings).toEqual(systemSettings);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISystemSettings>>();
      const systemSettings = { id: 123 };
      jest.spyOn(systemSettingsFormService, 'getSystemSettings').mockReturnValue(systemSettings);
      jest.spyOn(systemSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: systemSettings }));
      saveSubject.complete();

      // THEN
      expect(systemSettingsFormService.getSystemSettings).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(systemSettingsService.update).toHaveBeenCalledWith(expect.objectContaining(systemSettings));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISystemSettings>>();
      const systemSettings = { id: 123 };
      jest.spyOn(systemSettingsFormService, 'getSystemSettings').mockReturnValue({ id: null });
      jest.spyOn(systemSettingsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemSettings: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: systemSettings }));
      saveSubject.complete();

      // THEN
      expect(systemSettingsFormService.getSystemSettings).toHaveBeenCalled();
      expect(systemSettingsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISystemSettings>>();
      const systemSettings = { id: 123 };
      jest.spyOn(systemSettingsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ systemSettings });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(systemSettingsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
