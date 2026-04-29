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
import { AutocarejobService } from '../autocarejob/service/autocarejob.service';
import { ReceiptLinesService } from '../receipt-lines/service/receipt-lines.service';
import { TransactionsService } from '../transactions/service/transactions.service';
import { SalesInvoiceLinesService } from '../sales-invoice-lines/service/sales-invoice-lines.service';
import { AccountsService } from '../accounts/service/accounts.service';
import { SalesinvoiceService } from '../salesinvoice/service/salesinvoice.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
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
  @Input() accountCode: string = '';
  @Input() invoicecode: string | null = null;
  @Input() sharedSubId: string | null = null;
  subid: string = '';

  isSaving = false;
  field_input1: string = 'field_input1'; // Define this property here00
  selectedOption: number = 0;
  banks: IBanks[] = [];
  bankbranch: IBankbranch[] = [];

  salesinvoiceupdate = inject(SalesinvoiceUpdateComponent);

  protected receiptpaymentsdetailsService = inject(ReceiptpaymentsdetailsService);
  protected receiptpaymentsdetailsFormService = inject(ReceiptpaymentsdetailsFormService);
  protected banksService = inject(BanksService);
  bankbranchService = inject(BankbranchService);
  reciptService = inject(ReceiptService);
  autocarejobService = inject(AutocarejobService);
  reciptlines = inject(ReceiptLinesService);
  paymentdetails = inject(ReceiptpaymentsdetailsService);
  customeraccid = inject(CustomerService);
  transtactions = inject(TransactionsService);
  acc = inject(AccountsService);
  salesInvoiceService = inject(SalesinvoiceService);
  invoicelines = inject(SalesInvoiceLinesService);

  nextvalue: string = '';
  newnextvalue: string = '';
  newlastvalue: string = '';
  items: any[] = [];
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReceiptpaymentsdetailsFormGroup = this.receiptpaymentsdetailsFormService.createReceiptpaymentsdetailsFormGroup();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['receiptpaymentsdetails'] && changes['receiptpaymentsdetails'].currentValue) {
      console.log('Updated receiptpaymentsdetails:', changes['receiptpaymentsdetails'].currentValue);
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

  accountsId: number = 0;

  fetchacc(): void {
    this.customeraccid.query({ 'id.equals': this.customerid }).subscribe((res: HttpResponse<any[]>) => {
      const accounts = res.body || [];
      const selectedAccount = accounts.length > 0 ? accounts[0] : null;
      if (selectedAccount) {
        this.accountCode = selectedAccount.accountcode;
        this.transaction.accountCode = selectedAccount.accountcode;
        this.transaction.accountId = selectedAccount.accountid;
        this.accountsId = selectedAccount.id;
      }
    });
  }

  paymentType: string = '';
  finalcommisonamount: number = 0;

  onpaymentOptionChange(option: string): void {
    this.paymentType = option;
    this.fetchpaymentmethod();
    let commissionRate = 0;
    if (this.items && this.items.length > 0) {
      if (this.paymentType === 'visa') {
        commissionRate = this.items[2]?.commission || 0;
      } else if (this.paymentType === 'paypal') {
        commissionRate = this.items[1]?.commission || 0;
      } else if (this.paymentType === 'amex') {
        commissionRate = this.items[0]?.commission || 0;
      }
    }
    this.finalcommisonamount = (this.totalamount * commissionRate) / 100;
  }

  fetchpaymentmethod(): void {
    // Implementation skipped as PaymentMethodService is missing
  }

  account = {
    id: null,
    code: '',
    date: dayjs(),
    name: '',
    description: '',
    type: 0,
    parent: 0,
    balance: 0,
    lmu: 0,
    lmd: dayjs(),
    hasbatches: null as boolean | null,
    accountvalue: 0,
    accountlevel: 0,
    accountsnumberingsystem: 0,
    subparentid: 0,
    canedit: null as boolean | null,
    amount: 0,
    creditamount: 0,
    debitamount: 0,
    debitorcredit: '',
    reporttype: 0,
  };

  transaction = {
    id: null,
    accountId: 0,
    accountCode: '',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'invoice',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };

  reciptnocustransaction = {
    id: null,
    accountId: 0,
    accountCode: '',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Recipt',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };

  reciptnocustransactions(recid: number, reccode: String, subid: string, termid: number, termname: string): void {
    this.reciptnocustransaction.refId = recid;
    this.reciptnocustransaction.subId = subid;
    this.reciptnocustransaction.refDoc = reccode ? reccode.toString() : '';
    this.reciptnocustransaction.debit = this.totalamount;
    this.reciptnocustransaction.paymentTermId = termid;
    this.reciptnocustransaction.paymentTermName = termname;
    this.reciptnocustransaction.accountId = this.accountId;
    this.reciptnocustransaction.accountCode = this.accountCode;
    this.transtactions.create(this.reciptnocustransaction as any).subscribe();
  }

  reciptnocustomerupdate(accountid: number): void {
    this.acc.query({ 'id.equals': accountid }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];
        if (account) {
          const updatedAmount = Number(account.amount || 0) - this.totalamount;
          const updatedCredit = Number(account.debitamount || 0) + this.totalamount;
          this.acc.partialUpdate({ id: this.accountId, debitamount: updatedCredit, amount: updatedAmount }).subscribe();
        }
      },
    });
  }

  receiptmainacctransaction = {
    id: null,
    accountId: 33,
    accountCode: '42',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Receipt-Trade Receivables',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };

  receiptmainacctransactions(recid: number, reccode: String, subid: string, totalrecived: number): void {
    this.receipttransaction.refId = recid;
    this.receipttransaction.subId = subid;
    this.receipttransaction.refDoc = reccode ? reccode.toString() : '';
    this.receipttransaction.debit = totalrecived;
    this.receipttransaction.accountId = this.accountId;
    this.receipttransaction.accountCode = this.accountCode;
    this.transtactions.create(this.receipttransaction as any).subscribe();
  }

  receipttransaction = {
    id: null,
    accountId: 33,
    accountCode: '42',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Recipt',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };

  receipttransactions(recid: number, reccode: String, subid: string, termid: number, termname: string): void {
    this.receipttransaction.refId = recid;
    this.receipttransaction.subId = subid;
    this.receipttransaction.refDoc = reccode ? reccode.toString() : '';
    this.receipttransaction.credit = this.totalamount;
    this.receipttransaction.accountId = this.accountId;
    this.receipttransaction.accountCode = this.accountCode;
    this.transtactions.create(this.receipttransaction as any).subscribe();
  }

  updaterecipttransactionwithcustomer(): void {
    this.acc.query({ 'id.equals': this.accountId }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];
        if (account) {
          const updatedAmount = Number(account.amount || 0) - this.totalamount;
          const updatedCredit = Number(account.creditamount || 0) + this.totalamount;
          this.acc.partialUpdate({ id: this.accountId, creditamount: updatedCredit, amount: updatedAmount }).subscribe();
        }
      },
    });
  }

  accountmethod(name: string): void {
    let queryName = name;
    if (name == 'bankdeposit') queryName = 'Current Assets';
    this.acc.query({ 'name.contains': queryName }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        if (accounts.length > 0) {
          this.accountId = accounts[0].id;
          this.accountCode = accounts[0].code;
        }
      },
    });
  }

  updatecustomermain(amountrec: number): void {
    this.acc.query({ 'id.equals': 7 }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts = res.body || [];
        const account = accounts[0];
        if (account) {
          const updatedAmount = Number(account.amount || 0) - amountrec;
          const updatedCredit = Number(account.debitamount || 0) + amountrec;
          this.acc.partialUpdate({ id: 7, debitamount: updatedCredit, amount: updatedAmount }).subscribe();
        }
      },
    });
  }

  incrementId(id: string): string {
    const match = id.match(/^([A-Za-z]+)(\d+)$/);
    if (!match) return id;
    const prefix = match[1];
    const number = parseInt(match[2], 10) + 1;
    return `${prefix}${number}`;
  }

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
    receiptdate: dayjs(),
    customername: 'string',
    customeraddress: 'string',
    totalamount: 0,
    totalamountinword: 'string',
    comments: 'string',
    lmu: 0,
    lmd: dayjs(),
    termid: 0,
    term: 'string',
    date: dayjs(),
    amount: 0,
    checkdate: dayjs(),
    checkno: 'string',
    bank: 'string',
    customerid: 0,
    isactive: true,
    deposited: true,
    createdby: 0,
    vehicleno: 'string',
    id: null as number | null,
  };

  receiptlines = {
    id: 0,
    lineid: 1,
    invoicecode: 'string',
    invoicetype: 'string',
    originalamount: 0,
    amountowing: 0,
    discountavailable: 0,
    discounttaken: 0,
    amountreceived: 0,
    lmu: 0,
    lmd: dayjs(),
    accountid: 0,
  };

  receiptPaymentDetail = {
    id: 0,
    lineid: 0,
    paymentamount: 0,
    totalreceiptamount: null,
    checkqueamount: 0,
    checkqueno: '',
    checkquedate: null as dayjs.Dayjs | null,
    checkqueexpiredate: null as dayjs.Dayjs | null,
    bankname: '',
    bankid: 0,
    bankbranchname: '',
    bankbranchid: 0,
    creditcardno: '',
    creditcardamount: 0,
    reference: '',
    otherdetails: '',
    lmu: 0,
    lmd: dayjs(),
    termid: 0,
    termname: '',
    accountno: '',
    accountnumber: '',
    chequereturndate: null,
    isdeposit: false,
    depositeddate: null,
    chequestatuschangeddate: null,
    returnchequesttledate: null,
    chequestatusid: 0,
    ispdcheque: false,
    depositdate: null,
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

  method: string = '';
  id: number = 0;

  finishBilling(): void {
    this.save();
  }

  save(): void {
    this.isSaving = true;
    const storedUserId = localStorage.getItem('empId');
    const userIdNumber = storedUserId ? parseInt(storedUserId, 10) : 0;
    const finalUserId = isNaN(userIdNumber) ? 0 : userIdNumber;

    if (this.receipt) {
      this.receipt.lmu = finalUserId;
      this.receipt.lmd = dayjs();
      this.receipt.customername = this.customername ?? '';
      this.receipt.totalamount = this.totalamount;
      this.receipt.totalamountinword = this.totalamountinword ?? '';

      this.subscribeToSaveResponseWithCallback(this.reciptService.create(this.receipt as any), (receiptId: number) => {
        const paymentAmount = this.cash || this.totalamount || 0;
        const safeAccountId = this.accountId && !isNaN(Number(this.accountId)) ? Number(this.accountId) : 0;

        const receiptLinePayload: any = {
          id: receiptId,
          lineid: 1,
          invoicecode: this.invoicecode ?? '',
          invoicetype: 'Sales Invoice',
          originalamount: this.totalamount || 0,
          amountowing: (this.totalamount || 0) - (paymentAmount || 0),
          discountavailable: 0,
          discounttaken: 0,
          amountreceived: paymentAmount || 0,
          lmu: finalUserId,
          lmd: dayjs(),
          accountid: safeAccountId,
        };
        console.log('ReceiptLines Payload:', receiptLinePayload);
        this.reciptlines.create(receiptLinePayload).subscribe({
          next: () => console.log('ReceiptLines saved OK'),
          error: (err: any) => console.error('ReceiptLines error:', err),
        });

        const receiptPaymentsPayload: any = {
          id: receiptId,
          lineid: 1,
          paymentamount: paymentAmount || 0,
          totalreceiptamount: this.totalamount || 0,
          lmu: finalUserId,
          lmd: dayjs(),
          termid: this.receipt.termid || 0,
          termname: this.method || '',
          accountid: safeAccountId,
          accountcode: this.accountCode || '',
          isdeposit: false,
          ispdcheque: false,
          checkqueamount: 0,
          checkqueno: '',
          checkquedate: null,
          checkqueexpiredate: null,
          bankname: '',
          bankid: 0,
          bankbranchid: 0,
          creditcardno: '',
          creditcardamount: 0,
          reference: 'Sales Invoice',
          otherdetails: '',
          accountno: '',
          accountnumber: '',
          chequereturndate: null,
          depositeddate: null,
          chequestatuschangeddate: null,
          returnchequesttledate: null,
          chequestatusid: 0,
          depositdate: null,
          bankdepositbankname: '',
          bankdepositbankid: 0,
          bankdepositbankbranchname: '',
          bankdepositbankbranchid: 0,
          returnchequefine: 0,
          companybankid: 0,
          isbankreconciliation: false,
        };
        console.log('ReceiptPayments Payload:', receiptPaymentsPayload);
        this.paymentdetails.create(receiptPaymentsPayload).subscribe({
          next: () => console.log('Receiptpaymentsdetails saved OK'),
          error: (err: any) => console.error('Receiptpaymentsdetails error:', err),
        });

        this.salesinvoiceupdate.save();

        this.subid = crypto.randomUUID();

        if (this.customername != 'CASH') {
          this.updaterecipttransactionwithcustomer();
          this.receipttransactions(receiptId, this.receipt.code, this.subid, this.receipt.termid, this.method);
        } else {
          this.reciptnocustomerupdate(this.accountId);
          this.reciptnocustransactions(receiptId, this.receipt.code, this.subid, this.receipt.termid, this.method);
        }

        this.updatecustomermain(paymentAmount);
        this.receiptmainacctransactions(receiptId, this.receipt.code, this.subid, paymentAmount);
      });
    } else {
      this.isSaving = true;
      this.salesinvoiceupdate.save();
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        this.id = response.body.id;
        this.onSaveSuccess();
      },
      error: () => this.onSaveError(),
    });
  }

  protected subscribeToSaveResponseWithCallback(result: Observable<HttpResponse<any>>, callback: (id: number) => void): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        this.id = response.body.id;
        callback(response.body.id);
        this.onSaveSuccess();
      },
      error: () => this.onSaveError(),
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
    this.method = paymentMethod;
    this.accountmethod(paymentMethod);
    this.receipt.term = paymentMethod;
    this.receipt.termid = termid;

    console.log('totalamount:', this.totalamount);

    let totalAmountInWords = toWords(this.totalamount).replace(/,/g, '').replace(/and/g, 'and'); // Formatting the words
    console.log(totalAmountInWords + ' Rupees Only');
    this.receipt.totalamountinword = totalAmountInWords + ' Rupees Only';
    console.log('Updated Receipt:', this.receipt);
  }

  protected onSaveSuccess(): void {
    // Hide modal and remove backdrop to prevent screen lock (darker screen)
    const modalElement = document.getElementById('exampleModal');
    if (modalElement) {
      modalElement.classList.remove('show');
      modalElement.setAttribute('aria-hidden', 'true');
      modalElement.style.display = 'none';
    }

    // Remove all backdrops and reset body classes
    document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
    document.body.classList.remove('modal-open');
    document.body.style.overflow = '';
    document.body.style.paddingRight = '';

    // Brief delay to allow DOM updates before navigation
    setTimeout(() => {
      this.previousState();
    }, 300);
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
