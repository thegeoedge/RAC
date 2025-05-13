import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EmpRolesService } from '../service/emp-roles.service';
import { IEmpRoles } from '../emp-roles.model';
import { EmpRolesFormService } from './emp-roles-form.service';

import { EmpRolesUpdateComponent } from './emp-roles-update.component';

describe('EmpRoles Management Update Component', () => {
  let comp: EmpRolesUpdateComponent;
  let fixture: ComponentFixture<EmpRolesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empRolesFormService: EmpRolesFormService;
  let empRolesService: EmpRolesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpRolesUpdateComponent],
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
      .overrideTemplate(EmpRolesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpRolesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empRolesFormService = TestBed.inject(EmpRolesFormService);
    empRolesService = TestBed.inject(EmpRolesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const empRoles: IEmpRoles = { roleId: 55 };

      activatedRoute.data = of({ empRoles });
      comp.ngOnInit();

      expect(comp.empRoles).toEqual(empRoles);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpRoles>>();
      const empRoles = { roleId: 31402 };
      jest.spyOn(empRolesFormService, 'getEmpRoles').mockReturnValue(empRoles);
      jest.spyOn(empRolesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empRoles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empRoles }));
      saveSubject.complete();

      // THEN
      expect(empRolesFormService.getEmpRoles).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empRolesService.update).toHaveBeenCalledWith(expect.objectContaining(empRoles));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpRoles>>();
      const empRoles = { roleId: 31402 };
      jest.spyOn(empRolesFormService, 'getEmpRoles').mockReturnValue({ roleId: null });
      jest.spyOn(empRolesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empRoles: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empRoles }));
      saveSubject.complete();

      // THEN
      expect(empRolesFormService.getEmpRoles).toHaveBeenCalled();
      expect(empRolesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpRoles>>();
      const empRoles = { roleId: 31402 };
      jest.spyOn(empRolesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empRoles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empRolesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
