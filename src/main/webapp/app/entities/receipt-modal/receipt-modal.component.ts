import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, inject, input } from '@angular/core';
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
import { ReceiptLinesService } from '../receipt-lines/service/receipt-lines.service';
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
  @Input() accountId: number = 0;

  isSaving = false;
  field_input1: string = 'field_input1'; // Define this property here00
  field_input2: string = 'field_input2';
  field_input3: string = 'field_input3';
  field_input4: string = 'field_input4';
  field_input5: string = 'field_input5';
  field_input6: string = 'field_input6';
  selectedOption: number = 0;
  banks: IBanks[] = [];
  bankbranch: IBankbranch[] = [];

  salesinvoiceupdate = inject(SalesinvoiceUpdateComponent);

  protected receiptpaymentsdetailsService = inject(ReceiptpaymentsdetailsService);
  protected receiptpaymentsdetailsFormService = inject(ReceiptpaymentsdetailsFormService);
  protected banksService = inject(BanksService);
  protected bankbranchService = inject(BankbranchService);
  reciptService = inject(ReceiptService);
  reciptlines = inject(ReceiptLinesService);
  paymentdetails = inject(ReceiptpaymentsdetailsService);
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
  @Output() methodpending: EventEmitter<number> = new EventEmitter<number>(); // Define the EventEmitter

  onItemCodeInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const value = inputElement.value;
    console.log(`Input value: ${value}`);
    this.cash = parseFloat(value);
    console.log('Cash:', this.cash);
    this.methodpending.emit(this.cash);
    console.log('Total Amount:', this.totalamount);
    this.balance = this.totalamount - this.cash;
    console.log('Balance:', this.balance);
  }
  cheque: number = 0;
  onItemChequeInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const cheqvalue = inputElement.value;
    console.log(`Input value: ${cheqvalue}`);
    this.cheque = parseFloat(cheqvalue);
    console.log('Cheque:', this.cheque);
  }
  cheqdate: Date | null = null;
  onItemChequedate(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const cheqdate = inputElement.value;
    console.log(`Input value: ${cheqdate}`);
    this.cheqdate = new Date(cheqdate);
    console.log('Cheque Date:', this.cheqdate);
  }

  cheqam: number = 0;
  onItemChequeAmInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const cheqvalue = inputElement.value;
    console.log(`Input value: ${cheqvalue}`);
    this.cheqam = parseFloat(cheqvalue);
    console.log('Chequeamount:', this.cheqam);
  }

  bankid: number = 0;
  bankname: string = '';
  onItemBankInput($event: Event): void {
    const selectedBank = ($event.target as HTMLSelectElement).value;

    if (!selectedBank) {
      console.log('No bank selected');
      return;
    }

    // Get selected object
    const selectedObject = this.banks.find(bank => bank.name === selectedBank);

    if (selectedObject) {
      console.log(`Bank ID: ${selectedObject.id}, Bank Name: ${selectedObject.name}`);
      this.bankid = Number(selectedObject.id);
      this.bankname = selectedObject.name ? selectedObject.name.toString() : '';
      console.log('Bank ID:', this.bankid);
      console.log('Bank Name:', this.bankname);
    } else {
      console.log('Selected bank not found in the list');
    }
  }
  Branch: string = '';
  onItemChequebranchInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const branchvalue = inputElement.value;
    console.log(`Input value: ${branchvalue}`);
    this.Branch = branchvalue;
    console.log('Branchesss:', this.Branch);
  }

  termid: number = 0;
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
  receiptlines = {
    id: 0, // For the record's ID
    lineid: 1, // LineID field (Numeric)
    invoicecode: 'string', // Invoice code (String)
    invoicetype: 'string', // Invoice type (String)
    originalamount: 0, // Original amount (Numeric)
    amountowing: 0, // Amount owing (Numeric)
    discountavailable: 0, // Discount available (Numeric)
    discounttaken: 0, // Discount taken (Numeric)
    amountreceived: 0, // Amount received (Numeric)
    lmu: 0, // Last Modified User (Numeric)
    lmd: dayjs('2025-02-27T16:44:59.467Z'), // Last Modified Date (Date)
    accountid: 0, // Account ID (Numeric)
  };
  receiptPaymentDetail = {
    id: 0,
    lineid: 0,
    paymentamount: 0,
    totalreceiptamount: 0,
    checkqueamount: 0,
    checkqueno: '',
    checkquedate: dayjs('2025-02-27T16:44:59.467Z'),
    checkqueexpiredate: dayjs('2025-02-27T16:44:59.467Z'),
    bankname: '',
    bankid: 0,
    bankbranchname: '',
    bankbranchid: 0,
    creditcardno: '',
    creditcardamount: 0,
    reference: '',
    otherdetails: '',
    lmu: 0,
    lmd: dayjs('2025-02-27T16:44:59.467Z'),
    termid: 0,
    termname: '',
    accountno: '',
    accountnumber: '',
    chequereturndate: dayjs(), // FIX: Use dayjs() instead of an empty string
    isdeposit: false,
    depositeddate: dayjs(), // FIX: Use dayjs() instead of an empty string
    chequestatuschangeddate: dayjs(),
    returnchequesttledate: dayjs(),
    chequestatusid: 0,
    ispdcheque: false,
    depositdate: dayjs(),
    accountid: 0,
    accountcode: '',
    bankdepositbankname: '',
    bankdepositbankid: 0,
    bankdepositbankbranchname: '',
    bankdepositbankbranchid: 0,
    returnchequefine: 0,
    companybankid: 0,
    isbankreconciliation: false,
  };

  id: number = 0;
  paymentmethod: String = '';
  save(): void {
    this.isSaving = true;
    const receiptpaymentsdetails = this.receiptpaymentsdetailsFormService.getReceiptpaymentsdetails(this.editForm);

    if (receiptpaymentsdetails.id !== null) {
      // this.subscribeToSaveResponse(this.receiptpaymentsdetailsService.update(receiptpaymentsdetails));
    } else {
      // First, save receipt and wait for the response
      this.subscribeToSaveResponse(this.reciptService.create(this.receipt), receiptId => {
        // After getting the receipt ID, assign it to receiptlines
        this.receiptlines.accountid = this.accountId;
        this.receiptlines.amountreceived = this.cash;
        this.receiptlines.id = receiptId; // Use the received ID

        console.log('Assigned receipt ID to receipt lines:', this.receiptlines.id);
        console.log('chqqqq', this.checkno);
        // Now, save receiptlines
        this.subscribeToSaveResponse(this.reciptlines.create(this.receiptlines));
        this.receiptPaymentDetail.id = receiptId;
        this.receiptPaymentDetail.lineid = 1;
        this.receiptPaymentDetail.termid = this.termid;
        this.receiptPaymentDetail.termname = this.method.toString();
        this.receiptPaymentDetail.checkqueamount = this.cheqam;
        this.receiptPaymentDetail.checkqueno = this.cheque ? this.cheque.toString() : '';
        this.receiptPaymentDetail.checkqueexpiredate = this.cheqdate ? dayjs(this.cheqdate.toISOString()) : dayjs();
        this.receiptPaymentDetail.checkquedate = this.cheqdate ? dayjs(this.cheqdate.toISOString()) : dayjs();
        this.receiptPaymentDetail.bankname = this.bankname;
        this.receiptPaymentDetail.bankid = this.bankid;
        this.receiptPaymentDetail.bankbranchname = this.Branch;
        this.receiptPaymentDetail.accountid = this.accountId;
        this.receiptPaymentDetail.chequestatusid = 1;
        this.receiptPaymentDetail.paymentamount = this.cheqam;
        console.log('hhhhhhh', this.receiptPaymentDetail);
        this.subscribeToSaveResponse(this.paymentdetails.create(this.receiptPaymentDetail));
        this.salesinvoiceupdate.save();
      });
    }
  }

  // Modified subscribeToSaveResponse to accept a callback
  protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>, callback?: (id: number) => void): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        console.log('✅ Save response:', response.body.id); // Logs the response ID
        this.id = response.body.id; // Save ID to this.id
        if (callback) {
          callback(response.body.id); // Call the callback function with the ID
        }
      },
      error: err => {
        console.error('❌ Error occurred while saving:', err); // Logs the full error
        if (err.error) {
          console.error('🚨 Error Response Body:', err.error); // Logs the actual error body
        }
        this.onSaveError();
      },
    });
  }

  @Output() methodChanged: EventEmitter<string> = new EventEmitter<string>(); // Define the EventEmitter

  method: String = '';
  onOptionChange(option: number): void {
    this.selectedOption = option;
    console.log('aaaa', this.accountId);
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
    this.method = paymentMethod;
    console.log(this.method);
    this.methodChanged.emit(this.method.toString());
    console.log('Selected Term ID:', termid);
    this.receipt.term = paymentMethod;
    this.receipt.termid = termid;
    this.termid = termid;
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
