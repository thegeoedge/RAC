import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EmpRoleFunctionPermissionService } from '../service/emp-role-function-permission.service';
import { IEmpRoleFunctionPermission } from '../emp-role-function-permission.model';
import { EmpRoleFunctionPermissionFormService } from './emp-role-function-permission-form.service';

import { EmpRoleFunctionPermissionUpdateComponent } from './emp-role-function-permission-update.component';

describe('EmpRoleFunctionPermission Management Update Component', () => {
  let comp: EmpRoleFunctionPermissionUpdateComponent;
  let fixture: ComponentFixture<EmpRoleFunctionPermissionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empRoleFunctionPermissionFormService: EmpRoleFunctionPermissionFormService;
  let empRoleFunctionPermissionService: EmpRoleFunctionPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpRoleFunctionPermissionUpdateComponent],
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
      .overrideTemplate(EmpRoleFunctionPermissionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpRoleFunctionPermissionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empRoleFunctionPermissionFormService = TestBed.inject(EmpRoleFunctionPermissionFormService);
    empRoleFunctionPermissionService = TestBed.inject(EmpRoleFunctionPermissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const empRoleFunctionPermission: IEmpRoleFunctionPermission = { id: 11292 };

      activatedRoute.data = of({ empRoleFunctionPermission });
      comp.ngOnInit();

      expect(comp.empRoleFunctionPermission).toEqual(empRoleFunctionPermission);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpRoleFunctionPermission>>();
      const empRoleFunctionPermission = { id: 23088 };
      jest.spyOn(empRoleFunctionPermissionFormService, 'getEmpRoleFunctionPermission').mockReturnValue(empRoleFunctionPermission);
      jest.spyOn(empRoleFunctionPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empRoleFunctionPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empRoleFunctionPermission }));
      saveSubject.complete();

      // THEN
      expect(empRoleFunctionPermissionFormService.getEmpRoleFunctionPermission).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empRoleFunctionPermissionService.update).toHaveBeenCalledWith(expect.objectContaining(empRoleFunctionPermission));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpRoleFunctionPermission>>();
      const empRoleFunctionPermission = { id: 23088 };
      jest.spyOn(empRoleFunctionPermissionFormService, 'getEmpRoleFunctionPermission').mockReturnValue({ id: null });
      jest.spyOn(empRoleFunctionPermissionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empRoleFunctionPermission: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empRoleFunctionPermission }));
      saveSubject.complete();

      // THEN
      expect(empRoleFunctionPermissionFormService.getEmpRoleFunctionPermission).toHaveBeenCalled();
      expect(empRoleFunctionPermissionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpRoleFunctionPermission>>();
      const empRoleFunctionPermission = { id: 23088 };
      jest.spyOn(empRoleFunctionPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empRoleFunctionPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empRoleFunctionPermissionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
