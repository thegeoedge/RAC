import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EmpFunctionsService } from '../service/emp-functions.service';
import { IEmpFunctions } from '../emp-functions.model';
import { EmpFunctionsFormService } from './emp-functions-form.service';

import { EmpFunctionsUpdateComponent } from './emp-functions-update.component';

describe('EmpFunctions Management Update Component', () => {
  let comp: EmpFunctionsUpdateComponent;
  let fixture: ComponentFixture<EmpFunctionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empFunctionsFormService: EmpFunctionsFormService;
  let empFunctionsService: EmpFunctionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpFunctionsUpdateComponent],
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
      .overrideTemplate(EmpFunctionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpFunctionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empFunctionsFormService = TestBed.inject(EmpFunctionsFormService);
    empFunctionsService = TestBed.inject(EmpFunctionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const empFunctions: IEmpFunctions = { functionId: 12769 };

      activatedRoute.data = of({ empFunctions });
      comp.ngOnInit();

      expect(comp.empFunctions).toEqual(empFunctions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpFunctions>>();
      const empFunctions = { functionId: 15909 };
      jest.spyOn(empFunctionsFormService, 'getEmpFunctions').mockReturnValue(empFunctions);
      jest.spyOn(empFunctionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empFunctions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empFunctions }));
      saveSubject.complete();

      // THEN
      expect(empFunctionsFormService.getEmpFunctions).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empFunctionsService.update).toHaveBeenCalledWith(expect.objectContaining(empFunctions));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpFunctions>>();
      const empFunctions = { functionId: 15909 };
      jest.spyOn(empFunctionsFormService, 'getEmpFunctions').mockReturnValue({ functionId: null });
      jest.spyOn(empFunctionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empFunctions: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empFunctions }));
      saveSubject.complete();

      // THEN
      expect(empFunctionsFormService.getEmpFunctions).toHaveBeenCalled();
      expect(empFunctionsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpFunctions>>();
      const empFunctions = { functionId: 15909 };
      jest.spyOn(empFunctionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empFunctions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empFunctionsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
