import { Component, Input, OnChanges, SimpleChanges, inject, input } from '@angular/core';
import { ReceiptpaymentsdetailsService } from '../receiptpaymentsdetails/service/receiptpaymentsdetails.service';
import {
  ReceiptpaymentsdetailsFormService,
  ReceiptpaymentsdetailsFormGroup,
} from 'app/entities/receiptpaymentsdetails/update/receiptpaymentsdetails-form.service';
import { IBanks } from 'app/entities/banks/banks.model';
import { BanksService } from 'app/entities/banks/service/banks.service';
import { IBankbranch } from 'app/entities/bankbranch/bankbranch.model';
import { BankbranchService } from 'app/entities/bankbranch/service/bankbranch.service';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { finalize } from 'rxjs/operators';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';
import { toWords } from 'number-to-words';
import { ReceiptService } from '../receipt/service/receipt.service';
import dayjs from 'dayjs/esm';
import { SalesinvoiceUpdateComponent } from '../salesinvoice/update/salesinvoice-update.component';
@Component({
  selector: 'app-receipt-modal',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './receipt-modal.component.html',
  styleUrl: './receipt-modal.component.scss',
})
export class ReceiptModalComponent implements OnChanges {
  @Input() receiptpaymentsdetails: String | null = null;
  @Input() newcode: string | null = null;
  @Input() receiptdate: Date | null = null;
  @Input() totalamountinword: string | null = null;
  @Input() customername: string | null = null;
  @Input() totalamount: number = 0;
  @Input() customeraddress: string | null = null;
  @Input() comments: string | null = null;
  @Input() term: string | null = null;
  @Input() date: Date | null = null;
  @Input() amount: number = 0;
  @Input() vehicleno: string | null = null;
  @Input() checkdate: Date | null = null;
  @Input() checkno: string | null = null;
  @Input() bank: string | null = null;
  @Input() customerid: number = 0;
  @Input() isactive: boolean = true;
  @Input() deposited: boolean = true;
  @Input() createdby: number = 0;

  isSaving = false;
  field_input1: string = 'field_input1'; // Define this property here00
  selectedOption: number = 0;
  banks: IBanks[] = [];
  bankbranch: IBankbranch[] = [];

  salesinvoiceupdate = inject(SalesinvoiceUpdateComponent);

  protected receiptpaymentsdetailsService = inject(ReceiptpaymentsdetailsService);
  protected receiptpaymentsdetailsFormService = inject(ReceiptpaymentsdetailsFormService);
  protected banksService = inject(BanksService);
  protected bankbranchService = inject(BankbranchService);
  reciptService = inject(ReceiptService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReceiptpaymentsdetailsFormGroup = this.receiptpaymentsdetailsFormService.createReceiptpaymentsdetailsFormGroup();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['receiptpaymentsdetails'] && changes['receiptpaymentsdetails'].currentValue) {
      console.log('Updated receiptpaymentsdetails:', changes['receiptpaymentsdetails'].currentValue); // Logs the new value
      this.updateForm(this.receiptpaymentsdetails);
      this.loadBanks();
      this.loadBankBranch();
    }
  }

  // Log for debugging
  ngOnInit() {
    console.log('selectedOption:', this.selectedOption);
  }
  previousState(): void {
    window.history.back();
  }

  loadBanks(): void {
    this.banksService.query({ size: 1000 }).subscribe((res: HttpResponse<IBanks[]>) => {
      this.banks = res.body || [];
    });
  }
  items: any[] = []; // Your data array, adjust accordingly

  cash: number = 0;
  balance: number = 0;
  onItemCodeInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const value = inputElement.value;
    console.log(`Input value: ${value}`);
    this.cash = parseFloat(value);
    console.log('Cash:', this.cash);

    console.log('Total Amount:', this.totalamount);
    this.balance = this.totalamount - this.cash;
    console.log('Balance:', this.balance);
  }

  loadBankBranch(): void {
    this.bankbranchService.query({ size: 1000 }).subscribe((res: HttpResponse<IBankbranch[]>) => {
      this.bankbranch = res.body || [];
    });
  }
  receipt = {
    code: 'string',
    receiptdate: dayjs('2025-02-27T16:44:59.467Z'),
    customername: 'string',
    customeraddress: 'string',
    totalamount: 0,
    totalamountinword: 'string',
    comments: 'string',
    lmu: 0,
    lmd: dayjs('2025-02-27T16:44:59.467Z'),
    termid: 0,
    term: 'string',
    date: dayjs('2025-02-27T16:44:59.467Z'),
    amount: 0,
    checkdate: dayjs('2025-02-27T16:44:59.467Z'),
    checkno: 'string',
    bank: 'string',
    customerid: 0,
    isactive: true,
    deposited: true,
    createdby: 0,
    vehicleno: 'string', // Added vehicle number
    id: null, // Added id property
  };
  save(): void {
    this.isSaving = true;
    const receiptpaymentsdetails = this.receiptpaymentsdetailsFormService.getReceiptpaymentsdetails(this.editForm);

    if (receiptpaymentsdetails.id !== null) {
      this.subscribeToSaveResponse(this.receiptpaymentsdetailsService.update(receiptpaymentsdetails));
    } else {
      this.salesinvoiceupdate.save();
      this.subscribeToSaveResponse(this.reciptService.create(this.receipt));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        console.log('Save response:', response); // Logs the response here
        this.onSaveSuccess();
      },
      error: err => {
        console.error('Error occurred while saving:', err); // Logs any errors
        this.onSaveError();
      },
    });
  }

  onOptionChange(option: number): void {
    this.selectedOption = option;

    // Updating receipt object with the required properties
    this.receipt.totalamount = this.totalamount;
    this.receipt.customername = this.customername ?? '';
    this.receipt.customeraddress = this.customeraddress ?? '';
    this.receipt.comments = this.comments ?? '';

    this.receipt.date = this.date ? dayjs(this.date.toISOString()) : dayjs();

    this.receipt.amount = this.amount ?? 0;
    this.receipt.checkdate = this.checkdate ? dayjs(this.checkdate.toISOString()) : dayjs();
    this.receipt.checkno = this.checkno ?? '';
    this.receipt.bank = this.bank ?? '';
    this.receipt.customerid = this.customerid ?? 0;
    this.receipt.isactive = this.isactive ?? true;
    this.receipt.deposited = this.deposited ?? true;
    this.receipt.createdby = this.createdby ?? 0;
    this.receipt.totalamountinword = this.totalamountinword ?? '';
    this.receipt.code = this.newcode ?? '';
    this.receipt.receiptdate = this.receiptdate ? dayjs(this.receiptdate.toISOString()) : dayjs();
    this.receipt.vehicleno = this.vehicleno ?? '';

    // Logging all values

    let paymentMethod = '';
    let termid = 0;
    switch (option) {
      case 1:
        paymentMethod = 'Cash';
        termid = 1;
        break;
      case 2:
        paymentMethod = 'Credit';
        termid = 2;
        break;
      case 3:
        paymentMethod = 'Cheque';
        termid = 3;
        break;
      case 4:
        paymentMethod = 'Card/Other';
        termid = 4;
        break;
      case 5:
        paymentMethod = 'Bank';
        termid = 5;
        break;
      default:
        paymentMethod = 'Unknown';
    }

    console.log('Selected Payment Method:', paymentMethod);
    console.log('Selected Term ID:', termid);
    this.receipt.term = paymentMethod;
    this.receipt.termid = termid;

    console.log('totalamount:', this.totalamount);

    let totalAmountInWords = toWords(this.totalamount).replace(/,/g, '').replace(/and/g, 'and'); // Formatting the words
    console.log(totalAmountInWords + ' Rupees Only');
    this.receipt.totalamountinword = totalAmountInWords + ' Rupees Only';
    console.log('Updated Receipt:', this.receipt);
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(receiptpaymentsdetails: any): void {
    this.receiptpaymentsdetailsFormService.resetForm(this.editForm, receiptpaymentsdetails);
  }
}
