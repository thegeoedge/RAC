jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AutoCareVehicleService } from '../service/auto-care-vehicle.service';

import { AutoCareVehicleDeleteDialogComponent } from './auto-care-vehicle-delete-dialog.component';

describe('AutoCareVehicle Management Delete Component', () => {
  let comp: AutoCareVehicleDeleteDialogComponent;
  let fixture: ComponentFixture<AutoCareVehicleDeleteDialogComponent>;
  let service: AutoCareVehicleService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutoCareVehicleDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AutoCareVehicleDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AutoCareVehicleDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AutoCareVehicleService);
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
