import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrintsalesinvoiceComponent } from './printsalesinvoice.component';

describe('PrintsalesinvoiceComponent', () => {
  let component: PrintsalesinvoiceComponent;
  let fixture: ComponentFixture<PrintsalesinvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrintsalesinvoiceComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PrintsalesinvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
