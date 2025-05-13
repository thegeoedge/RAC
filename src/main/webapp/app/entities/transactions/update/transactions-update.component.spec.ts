import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TransactionsService } from '../service/transactions.service';
import { ITransactions } from '../transactions.model';
import { TransactionsFormService } from './transactions-form.service';

import { TransactionsUpdateComponent } from './transactions-update.component';

describe('Transactions Management Update Component', () => {
  let comp: TransactionsUpdateComponent;
  let fixture: ComponentFixture<TransactionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transactionsFormService: TransactionsFormService;
  let transactionsService: TransactionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TransactionsUpdateComponent],
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
      .overrideTemplate(TransactionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransactionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transactionsFormService = TestBed.inject(TransactionsFormService);
    transactionsService = TestBed.inject(TransactionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const transactions: ITransactions = { id: 11145 };

      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      expect(comp.transactions).toEqual(transactions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransactions>>();
      const transactions = { id: 30270 };
      jest.spyOn(transactionsFormService, 'getTransactions').mockReturnValue(transactions);
      jest.spyOn(transactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactions }));
      saveSubject.complete();

      // THEN
      expect(transactionsFormService.getTransactions).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transactionsService.update).toHaveBeenCalledWith(expect.objectContaining(transactions));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransactions>>();
      const transactions = { id: 30270 };
      jest.spyOn(transactionsFormService, 'getTransactions').mockReturnValue({ id: null });
      jest.spyOn(transactionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transactions }));
      saveSubject.complete();

      // THEN
      expect(transactionsFormService.getTransactions).toHaveBeenCalled();
      expect(transactionsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransactions>>();
      const transactions = { id: 30270 };
      jest.spyOn(transactionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transactions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transactionsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
