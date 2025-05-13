import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SalesInvoiceLineBatchDetailComponent } from './sales-invoice-line-batch-detail.component';

describe('SalesInvoiceLineBatch Management Detail Component', () => {
  let comp: SalesInvoiceLineBatchDetailComponent;
  let fixture: ComponentFixture<SalesInvoiceLineBatchDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalesInvoiceLineBatchDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./sales-invoice-line-batch-detail.component').then(m => m.SalesInvoiceLineBatchDetailComponent),
              resolve: { salesInvoiceLineBatch: () => of({ id: 9680 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SalesInvoiceLineBatchDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInvoiceLineBatchDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesInvoiceLineBatch on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SalesInvoiceLineBatchDetailComponent);

      // THEN
      expect(instance.salesInvoiceLineBatch()).toEqual(expect.objectContaining({ id: 9680 }));
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
