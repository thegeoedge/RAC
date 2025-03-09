import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SalesInvoiceLineBatchService } from '../service/sales-invoice-line-batch.service';
import { ISalesInvoiceLineBatch } from '../sales-invoice-line-batch.model';
import { SalesInvoiceLineBatchFormService } from './sales-invoice-line-batch-form.service';

import { SalesInvoiceLineBatchUpdateComponent } from './sales-invoice-line-batch-update.component';

describe('SalesInvoiceLineBatch Management Update Component', () => {
  let comp: SalesInvoiceLineBatchUpdateComponent;
  let fixture: ComponentFixture<SalesInvoiceLineBatchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesInvoiceLineBatchFormService: SalesInvoiceLineBatchFormService;
  let salesInvoiceLineBatchService: SalesInvoiceLineBatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceLineBatchUpdateComponent],
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
      .overrideTemplate(SalesInvoiceLineBatchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesInvoiceLineBatchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesInvoiceLineBatchFormService = TestBed.inject(SalesInvoiceLineBatchFormService);
    salesInvoiceLineBatchService = TestBed.inject(SalesInvoiceLineBatchService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesInvoiceLineBatch: ISalesInvoiceLineBatch = { id: 456 };

      activatedRoute.data = of({ salesInvoiceLineBatch });
      comp.ngOnInit();

      expect(comp.salesInvoiceLineBatch).toEqual(salesInvoiceLineBatch);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLineBatch>>();
      const salesInvoiceLineBatch = { id: 123 };
      jest.spyOn(salesInvoiceLineBatchFormService, 'getSalesInvoiceLineBatch').mockReturnValue(salesInvoiceLineBatch);
      jest.spyOn(salesInvoiceLineBatchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLineBatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceLineBatch }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceLineBatchFormService.getSalesInvoiceLineBatch).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesInvoiceLineBatchService.update).toHaveBeenCalledWith(expect.objectContaining(salesInvoiceLineBatch));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLineBatch>>();
      const salesInvoiceLineBatch = { id: 123 };
      jest.spyOn(salesInvoiceLineBatchFormService, 'getSalesInvoiceLineBatch').mockReturnValue({ id: null });
      jest.spyOn(salesInvoiceLineBatchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLineBatch: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceLineBatch }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceLineBatchFormService.getSalesInvoiceLineBatch).toHaveBeenCalled();
      expect(salesInvoiceLineBatchService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLineBatch>>();
      const salesInvoiceLineBatch = { id: 123 };
      jest.spyOn(salesInvoiceLineBatchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLineBatch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesInvoiceLineBatchService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
