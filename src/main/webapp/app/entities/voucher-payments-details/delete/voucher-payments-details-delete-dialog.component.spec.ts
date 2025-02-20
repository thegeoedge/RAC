jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { VoucherPaymentsDetailsService } from '../service/voucher-payments-details.service';

import { VoucherPaymentsDetailsDeleteDialogComponent } from './voucher-payments-details-delete-dialog.component';

describe('VoucherPaymentsDetails Management Delete Component', () => {
  let comp: VoucherPaymentsDetailsDeleteDialogComponent;
  let fixture: ComponentFixture<VoucherPaymentsDetailsDeleteDialogComponent>;
  let service: VoucherPaymentsDetailsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VoucherPaymentsDetailsDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(VoucherPaymentsDetailsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VoucherPaymentsDetailsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(VoucherPaymentsDetailsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
