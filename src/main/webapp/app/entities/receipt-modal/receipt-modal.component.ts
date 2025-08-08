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
import { PaymentMethodService } from '../payment-method/service/payment-method.service';
import { CustomerService } from '../customer/service/customer.service';
import { v4 as uuidv4 } from 'uuid';
import { ITransactions } from '../transactions/transactions.model';
import { TransactionsService } from '../transactions/service/transactions.service';
import { SalesInvoiceLinesService } from '../sales-invoice-lines/service/sales-invoice-lines.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { AccountsService } from '../accounts/service/accounts.service';
import { SalesinvoiceService } from '../salesinvoice/service/salesinvoice.service';
import { PartialUpdateSystemSettings, SystemSettingsService } from '../system-settings/service/system-settings.service';
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
  @Input() invoicecode: string | null = null;
  @Input() sharedSubId: string | null = null;
  subid: string = '';
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
  salesInvoiceService = inject(SalesinvoiceService);
  salesinvoiceupdate = inject(SalesinvoiceUpdateComponent);
  invoicelines = inject(SalesInvoiceLinesService);

  systemsettings = inject(SystemSettingsService);
  protected receiptpaymentsdetailsService = inject(ReceiptpaymentsdetailsService);
  protected receiptpaymentsdetailsFormService = inject(ReceiptpaymentsdetailsFormService);
  protected banksService = inject(BanksService);
  protected bankbranchService = inject(BankbranchService);
  reciptService = inject(ReceiptService);
  reciptlines = inject(ReceiptLinesService);
  paymentdetails = inject(ReceiptpaymentsdetailsService);
  customeraccid = inject(CustomerService);
  transtactions = inject(TransactionsService);
  acc = inject(AccountsService);
  nextvalue: string = ''; // Correct declaration
  newnextvalue: string = '';
  newlastvalue: string = '';
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReceiptpaymentsdetailsFormGroup = this.receiptpaymentsdetailsFormService.createReceiptpaymentsdetailsFormGroup();
  paymentmethods = inject(PaymentMethodService);
  ngOnChanges(changes: SimpleChanges): void {
    this.systemsettings.find(6).subscribe(response => {
      console.log('Full System Settings Responseee:', response);

      if (response.body) {
        console.log('System Settings Next Value:', response.body.nextValue);
        console.log('System Settings Last Value:', response.body.lastValue);

        this.nextvalue = response.body.nextValue ?? '';
        this.newlastvalue = response.body.lastValue ?? ''; // Ensure lastValue is assigned

        console.log('Current Next Value:', this.nextvalue);
        console.log('Current Last Value:', this.newlastvalue);

        // Increment both values
        const newId = this.incrementId(this.nextvalue);
        const newLastId = this.incrementId(this.newlastvalue.toString());

        console.log('New Next Value:', newId);
        console.log('New Last Value:', newLastId);

        // Store new values
        this.newnextvalue = newId;
        this.newlastvalue = newLastId;
        console.log('New Next Valuwwwwwwwwwwwwwwwwwwwwe:', this.newnextvalue);
        console.log('New Last Valwwwwwue:', this.newlastvalue);
        //  this.editForm.patchValue({ code: this.nextvalue });
        //const salesinvoice = this.salesinvoiceFormService.getSalesinvoice(this.editForm);
        // console.log('salessssssssssssswwwsssss', salesinvoice);
      } else {
        console.log('No body found in response');
      }
    });
    if (changes['receiptpaymentsdetails'] && changes['receiptpaymentsdetails'].currentValue) {
      console.log('Updated receiptpaymentsdetails:', changes['receiptpaymentsdetails'].currentValue); // Logs the new value
      this.updateForm(this.receiptpaymentsdetails);
      this.loadBanks();
      // this.loadBankBranch();
    }
  }
  accountsId: number = 0;
  accountcode: string = '';

  fetchacc(): void {
    console.log('Fetching account ID... >>>>>>>>>>>>>>>>>>>', this.customerid);
    this.invoicelines.getSubId();
    // Make sure you pass the correct query params for filtering by customerid
    this.customeraccid.query({ 'id.equals': this.customerid }).subscribe((res: HttpResponse<any[]>) => {
      const accounts = res.body || [];
      console.log('Fetched accounts:', accounts);

      // You may not even need to filter again if the query already filters by customerId
      const selectedAccount = accounts.length > 0 ? accounts[0] : null;

      if (selectedAccount) {
        this.accountcode = selectedAccount.accountcode;
        this.transaction.accountCode = selectedAccount.accountcode;
        this.transaction.accountId = selectedAccount.accountid;
        this.accountsId = selectedAccount.id;
        console.log('Selected Account ID:', this.accountId);
      } else {
        console.log('No account found for customer ID:', this.customerid);
      }
    });
  }

  // Log for debugging
  ngOnInit() {
    console.log('selectedOption:', this.selectedOption);

    // Call the method to fetch account ID
    // Call the method to fetch payment methods
  }
  previousState(): void {
    window.history.back();
  }
  paymentType: string = '';
  finalcommisonamount: number = 0;

  onpaymentOptionChange(option: string): void {
    this.paymentType = option;
    console.log('Payment Option Changed:', this.paymentType);
    console.log('Total Amount:', this.totalamount);

    this.fetchpaymentmethod();
    console.log(this.items);

    let commissionRate = 0;

    if (this.paymentType === 'visa') {
      commissionRate = this.items[2].commission;
      console.log('Visa selected - Commission %:', commissionRate);
    } else if (this.paymentType === 'paypal') {
      commissionRate = this.items[1].commission;
      console.log('PayPal selected - Commission %:', commissionRate);
    } else if (this.paymentType === 'amex') {
      commissionRate = this.items[0].commission;
      console.log('Amex selected - Commission %:', commissionRate);
    }

    this.finalcommisonamount = (this.totalamount * commissionRate) / 100;
    const finalTotal = this.totalamount + this.finalcommisonamount;

    console.log('Final Commission Amount:', this.finalcommisonamount.toFixed(2));
    console.log('Final Total (with commission):', finalTotal.toFixed(2));
  }

  fetchpaymentmethod(): void {
    this.paymentmethods.query({ size: 1000 }).subscribe((res: HttpResponse<any[]>) => {
      this.items = res.body || []; // Assign the response body to the items array
      console.log('Fetched payment methodxxxxxxxxxxxxxxxxxs:', this.items); // Log the fetched payment methods
    });
    console.log('Fetched payment methods:', this.items); // Log the fetched payment methods
  }

  loadBanks(): void {
    this.banksService.query({ size: 1000 }).subscribe((res: HttpResponse<IBanks[]>) => {
      this.banks = res.body || [];
    });
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
    id: null, // Set id to null as required by NewTransactions type
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
  }; // Non-null assertion operator indicates it will be assigned later

  reciptnocustransaction = {
    id: null, // Set id to null as required by NewTransactions type
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

  receiptnocustransactions(recid: number, reccode: String, subid: string, termid: number, termname: string): void {
    this.reciptnocustransaction.refId = recid;
    this.reciptnocustransaction.subId = subid;
    this.reciptnocustransaction.refDoc = reccode ? reccode.toString() : '';
    this.reciptnocustransaction.debit = this.totalamount;
    this.reciptnocustransaction.paymentTermId = termid;
    this.reciptnocustransaction.paymentTermName = termname;
    this.reciptnocustransaction.accountId = this.accountId;
    this.reciptnocustransaction.accountCode = this.accountcode;
    //console.log('Sales Income Transaction:', this.salesInvoiceLinesService.getprofit());
    this.transtactions.create(this.reciptnocustransaction).subscribe({
      next: response => {
        console.log('Transaction created successfully sales incomingggggggggsttttttttttttttttttt:', response.body);
      },
      error: error => {
        console.error('Error creating transaction:', error);
      },
    });
  }

  reciptnocustomerupdate(accountid: number): void {
    console.log('Total from controls:', this.totalamount);
    this.acc.query({ 'id.equals': accountid }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];

        if (account) {
          console.log('Fetched Sales Account:', account);
          //const profit = total - itemcost;
          //  this.salesInvoiceLinesService.setprofit(profit);
          //this.salesincometransactions(inid);
          const originalAmount = Number(account.amount || 0);
          const originalCredit = Number(account.debitamount || 0);

          const updatedAmount = originalAmount - this.totalamount;
          const updatedCredit = originalCredit + this.totalamount;

          const updatePayload = {
            id: this.accountId,
            debitamount: updatedCredit,
            amount: updatedAmount,
          };

          this.acc.partialUpdate(updatePayload).subscribe({
            next: response => {
              console.log(`Successfully updated account ID in noooooooooooooooooo come in come  ${account.id}`, response);
            },
            error: error => {
              console.error(`Failed to update account ID ${account.id}`, error);
            },
          });
        } else {
          console.warn('No account found with ID 7.');
        }
      },
      error: err => {
        console.error('Error fetching account with ID 7:', err);
      },
    });
  }

  receiptmainacctransaction = {
    id: null, // Set id to null as required by NewTransactions type
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
    //  this.receipttransaction.paymentTermId= termid;
    //this.receipttransaction.paymentTermName = null;
    this.receipttransaction.accountId = this.accountId;
    this.receipttransaction.accountCode = this.accountcode;
    //console.log('Sales Income Transaction:', this.salesInvoiceLinesService.getprofit());
    this.transtactions.create(this.receipttransaction).subscribe({
      next: response => {
        console.log('Transaction created successfully sales incomingggggggggsttttttttttttttttttt:', response.body);
      },
      error: error => {
        console.error('Error creating transaction:', error);
      },
    });
  }

  receipttransaction = {
    id: null, // Set id to null as required by NewTransactions type
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
    //  this.receipttransaction.paymentTermId= termid;
    //this.receipttransaction.paymentTermName = null;
    this.receipttransaction.accountId = this.accountId;
    this.receipttransaction.accountCode = this.accountcode;
    //console.log('Sales Income Transaction:', this.salesInvoiceLinesService.getprofit());
    this.transtactions.create(this.receipttransaction).subscribe({
      next: response => {
        console.log('Transaction created successfully sales incomingggggggggsttttttttttttttttttt:', response.body);
      },
      error: error => {
        console.error('Error creating transaction:', error);
      },
    });
  }

  updaterecipttransactionwithcustomer(): void {
    // <-- Should log the actual total

    //console.log('Total from controlsdddddddddddddddddddddddddddddddddddd:', itemcost);

    console.log('Total from controls:', this.totalamount);
    this.acc.query({ 'id.equals': this.accountId }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];

        if (account) {
          console.log('Fetched Sales Account:', account);
          //const profit = total - itemcost;
          //  this.salesInvoiceLinesService.setprofit(profit);
          //this.salesincometransactions(inid);
          const originalAmount = Number(account.amount || 0);
          const originalCredit = Number(account.creditamount || 0);

          const updatedAmount = originalAmount - this.totalamount;
          const updatedCredit = originalCredit + this.totalamount;

          const updatePayload = {
            id: this.accountId,
            creditamount: updatedCredit,
            amount: updatedAmount,
          };

          this.acc.partialUpdate(updatePayload).subscribe({
            next: response => {
              console.log(`Successfully updated account ID in rrrrrrrrrrrrrrrrrrrrrrr come in come  ${account.id}`, response);
            },
            error: error => {
              console.error(`Failed to update account ID ${account.id}`, error);
            },
          });
        } else {
          console.warn('No account found with ID 7.');
        }
      },
      error: err => {
        console.error('Error fetching account with ID 7:', err);
      },
    });
  }

  accountmethod(name: string): void {
    console.log('Account ID:>>>>>>>>>>>>', name);
    if (name == 'bankdeposit') {
      name = 'Current Assets';
    }
    this.acc.query({ 'name.contains': name }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        console.log('Fetched accounts:', accounts);

        if (accounts.length > 0) {
          console.log('First account:', accounts[0]);
          // You can assign values if needed:
          this.accountId = accounts[0].id;
          this.accountcode = accounts[0].code;
          // this.accountName = accounts[0].name;
        } else {
          console.log('No matching accounts found');
        }
      },
      error: err => {
        console.error('Error fetching accounts:', err);
      },
    });
  }

  addtrasction(): void {
    console.log('Adding transaction...');
    this.customername = this.salesInvoiceService.getCustomerName();
    console.log('Account ID:>>>>>>>>>>>>', this.customername);
    this.acc.query({ 'name.contains': this.customername }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        console.log('Fetched accountsssss:', accounts[0].amount + this.totalamount);
        console.log('Fetched accountsssss:', accounts[0].debitamount + this.totalamount);

        console.log('Fetched accountsssss:', accounts[0].id);
        const account = accounts[0];
        const updatedAccount = {
          id: account.id,
          debitamount: account.debitamount + this.totalamount,
          amount: account.amount + this.totalamount,
        };

        // Call partial update (PATCH)
        this.acc.partialUpdate(updatedAccount).subscribe({
          next: () => {
            console.log('Account updated successfully.');
          },
          error: err => {
            console.error('Failed to update account:', err);
          },
        });
        // Do something with the accounts
        this.transaction.subId = this.invoicelines.getSubId();

        this.transaction.refDoc = this.invoicecode ? this.invoicecode.toString() : '';
        this.transaction.debit = this.totalamount;

        console.log('Updated transaction:', this.transaction);
        console.log('Transaction ID:', this.invoicecode);

        this.transtactions.create(this.transaction).subscribe({
          next: response => {
            console.log('Transaction created successfully:', response.body);
          },
          error: error => {
            console.error('Error creating transaction:', error);
          },
        });
      },
      error: err => {
        console.error('Failed to fetch accounts:', err);
      },
    });
  }

  items: any[] = []; // Your data array, adjust accordingly

  cash: number = 0;
  balance: number = 0;
  @Output() methodpending: EventEmitter<{ cash: number; balance: number }> = new EventEmitter<{ cash: number; balance: number }>(); // Define the EventEmitter

  onItemCodeInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const value = inputElement.value;
    console.log(`Input value: ${value}`);
    this.cash = parseFloat(value);
    console.log('Cash:', this.cash);

    console.log('Total Amount:', this.totalamount);
    this.balance = this.totalamount - this.cash;
    console.log('Balance:', this.balance);
    this.methodpending.emit({ cash: this.cash, balance: this.balance }); // Emit the value as an object
  }

  @Output() methods: EventEmitter<{ method: string; totalAmount: number }> = new EventEmitter();

  onMethodChange(event: Event): void {
    this.methods.emit({ method: (event.target as HTMLSelectElement).value, totalAmount: this.totalamount }); // Emit the selected method and totalAmount
  }

  updatecustomermain(amountrec: number): void {
    // const total = this.salesinvoice.getTotal();

    // console.log('Total from controls:', total);

    this.acc.query({ 'id.equals': 7 }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts = res.body || [];
        const account = accounts[0];

        if (account) {
          console.log('Fetched Sales Account:', account);

          const originalAmount = Number(account.amount || 0);
          const originalCredit = Number(account.debitamount || 0);

          console.log('Original Amount:', originalAmount);
          console.log('Original Credit:', originalCredit);

          const updatedAmount = originalAmount - amountrec;
          const updatedCredit = originalCredit + amountrec;

          console.log('Updated Amount:', updatedAmount);
          console.log('Updated Credit:', updatedCredit);

          const updatePayload = {
            id: 7,
            debitamount: updatedCredit,
            amount: updatedAmount,
          };

          this.acc.partialUpdate(updatePayload).subscribe({
            next: response => {
              console.log('Successfully updated account partialiiiiiiiiiiiiiiiiiii:', response);
            },
            error: error => {
              console.error('Failed to update account ID:', error);
            },
          });
        } else {
          console.warn('No account found with ID 7.');
        }
      },
      error: err => {
        console.error('Error fetching account with ID 7:', err);
      },
    });
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
  ref: string = '';
  onRef(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const cheqdate = inputElement.value;
    console.log(`Input value: ${cheqdate}`);
    this.ref = cheqdate;
    console.log('Reference:', this.ref);
  }
  cheqam: number = 0;
  onItemChequeAmInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const cheqvalue = inputElement.value;
    this.cheqam = this.totalamount;
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
      this.bankbranch = []; // Clear previous branches if nothing is selected
      return;
    }

    const selectedObject = this.banks.find(bank => bank.name === selectedBank);

    if (selectedObject) {
      this.bankid = Number(selectedObject.id);
      this.bankname = selectedObject.name ? selectedObject.name.toString() : '';

      // Clear previous branches immediately
      this.bankbranch = [];

      this.bankbranchService.query({ 'bankcode.equals': selectedObject.code }).subscribe((res: HttpResponse<IBankbranch[]>) => {
        this.bankbranch = res.body || [];

        // Log here, inside the subscribe block
        console.log('Bank Branches:', this.bankbranch);
      });
    } else {
      console.log('Selected bank not found in the list');
      this.bankbranch = [];
    }
  }

  Branch: string = '';

  onItemChequebranchInput(event: Event): void {
    const selectedBranch = (event.target as HTMLSelectElement).value;
    this.Branch = selectedBranch;
    console.log('Selected Branch:', this.Branch);
  }

  termid: number = 0;
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
    lmd: dayjs(), // Last Modified Date (Date)
    accountid: 0, // Account ID (Numeric)
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
    chequereturndate: null, // FIX: Use dayjs() instead of an empty string
    isdeposit: false,
    depositeddate: null, // FIX: Use dayjs() instead of an empty string
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

  id: number = 0;
  paymentmethod: String = '';
  save(): void {
    const storedUserId = localStorage.getItem('empId');
    const userIdNumber = parseInt(storedUserId!, 10);
    console.log('User ID from localStoragerrrrrrrrrrr:', userIdNumber);

    if (this.method != 'credit') {
      this.isSaving = true;
      const receiptpaymentsdetails = this.receiptpaymentsdetailsFormService.getReceiptpaymentsdetails(this.editForm);

      // this.subscribeToSaveResponse(this.receiptpaymentsdetailsService.update(receiptpaymentsdetails));
      this.receipt.totalamount = Math.floor(this.receipt.totalamount ?? 0);
      this.receipt.amount = Math.floor(this.receipt.totalamount ?? 0);
      this.receipt.createdby = userIdNumber;
      // First, save receipt and wait for the response
      this.subscribeToSaveResponse(this.reciptService.create(this.receipt), receiptId => {
        // After getting the receipt ID, assign it to receiptlines
        this.receiptlines.accountid = this.accountId;
        // this.receiptlines.accountcode=this.accountcode;
        this.receiptlines.amountreceived = this.cash;
        this.receiptlines.id = receiptId; // Use the received ID
        this.receiptlines.invoicecode = this.invoicecode ? this.invoicecode.toString() : '';
        this.receiptlines.originalamount = this.totalamount;
        this.receiptlines.lmu = userIdNumber;
        //this.receiptlines.amountreceived = this.cheqam;
        console.log('this.cheqamcccccccccccccc', this.cheqam);
        if (this.cheqam != 0) {
          this.receiptlines.amountreceived = this.cheqam;
          this.receiptlines.amountowing = this.totalamount - this.cheqam;
        }
        if (this.cheqam == 0) {
          this.receiptlines.amountreceived = this.cash;
          this.receiptlines.amountowing = this.totalamount - this.cash;
        }
        if (this.method == 'bankdeposit' || this.method == 'card') {
          this.receiptlines.amountreceived = 0;
          this.receiptlines.amountowing = 0;
        }
        if (this.method == 'card') {
          this.receiptlines.amountreceived = this.totalamount;
        }

        console.log('Assigned receipt ID to receipt linesmmmmmmmmmmmmmmmmmmmmmmmmm:', this.receiptlines);
        console.log('chqqqq', this.checkno);
        // Now, save receiptlines
        if (this.cheqam === 0) {
          this.cheqam = this.totalamount;
        }
        this.subscribeToSaveResponse(this.reciptlines.create(this.receiptlines));
        this.receiptPaymentDetail.id = receiptId;
        this.receiptPaymentDetail.lineid = 1;
        this.receiptPaymentDetail.termid = this.termid;
        this.receiptPaymentDetail.termname = this.method.toString();
        this.receiptPaymentDetail.reference = this.ref;
        this.receiptPaymentDetail.lmu = userIdNumber;
        if (this.method == 'cheque') {
          this.receiptPaymentDetail.checkqueamount = this.cheqam;
          this.receiptPaymentDetail.checkqueexpiredate = this.cheqdate ? dayjs(this.cheqdate.toISOString()) : dayjs();
          this.receiptPaymentDetail.checkquedate = this.cheqdate ? dayjs(this.cheqdate.toISOString()) : dayjs();
        }
        this.receiptPaymentDetail.checkqueno = this.cheque ? this.cheque.toString() : '';

        this.receiptPaymentDetail.bankname = this.bankname;
        this.receiptPaymentDetail.bankid = this.bankid;
        this.receiptPaymentDetail.bankbranchname = this.Branch;
        this.receiptPaymentDetail.accountid = this.accountId;
        this.receiptPaymentDetail.chequestatusid = 1;
        this.receiptPaymentDetail.accountcode = this.accountcode;
        if (this.method == 'cash') {
          this.cheqam = this.cash;
        }
        this.receiptPaymentDetail.paymentamount = this.cheqam;
        console.log('hhhhhhh', this.receiptPaymentDetail);
        const systemSettingsUpdatee: PartialUpdateSystemSettings = {
          id: 6,
          lastValue: this.newnextvalue,
          nextValue: this.incrementId(this.newnextvalue.toString()),
        };

        console.log('System Settings Update:', systemSettingsUpdatee);

        this.salesInvoiceService.partialUpdatee(systemSettingsUpdatee).subscribe({
          next: response => {
            console.log('System Settings Updatedddddddddddddddddddddd:', response);
          },
          error: err => {
            console.error('Error updating system settings:', err);
          },
        });
        this.receiptPaymentDetail.paymentamount = Math.floor(this.receiptPaymentDetail.paymentamount ?? 0);
        this.subscribeToSaveResponse(this.paymentdetails.create(this.receiptPaymentDetail));

        this.salesinvoiceupdate.save();

        this.subid = uuidv4();

        if (this.salesInvoiceService.getCustomerName() != 'CASH') {
          // alert('Customer name is not CASH, updating customer transaction.'+this.customername);
          this.updaterecipttransactionwithcustomer();
          this.receipttransactions(receiptId, this.receipt.code, this.subid, this.termid, this.method.toString());
        }
        if (this.salesInvoiceService.getCustomerName() == 'CASH') {
          //alert('Customer name is CASH, skipping customer transaction update.');
          this.reciptnocustomerupdate(this.accountId);
          this.receiptnocustransactions(receiptId, this.receipt.code, this.subid, this.termid, this.method.toString());
        }

        this.updatecustomermain(this.cheqam);

        this.receiptmainacctransactions(receiptId, this.receipt.code, this.subid, this.cheqam);

        //  this.fetchacc();
        // this.addtrasction();
      });
    } else if (this.method == 'credit') {
      this.isSaving = true;
      this.salesinvoiceupdate.save();
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
  dcrementId(id: string): string {
    const match = id.match(/^([A-Za-z]+)(\d+)$/);
    if (!match) return id; // Return as is if it doesn't match the pattern

    const prefix = match[1]; // Extract letters (e.g., "SI")
    const number = parseInt(match[2], 10) - 1; // Increment the number part

    return `${prefix}${number}`;
  }
  incrementId(id: string): string {
    const match = id.match(/^([A-Za-z]+)(\d+)$/);
    if (!match) return id; // Return as is if it doesn't match the pattern

    const prefix = match[1]; // Extract letters (e.g., "SI")
    const number = parseInt(match[2], 10) + 1; // Increment the number part

    return `${prefix}${number}`;
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

    this.receipt.code = this.newnextvalue ?? '';
    this.receipt.receiptdate = this.receiptdate ? dayjs(this.receiptdate.toISOString()) : dayjs();
    this.receipt.vehicleno = this.vehicleno ?? '';

    // Logging all values

    let paymentMethod = '';
    let termid = 0;
    switch (option) {
      case 1:
        paymentMethod = 'cash';
        termid = 1;
        break;
      case 2:
        paymentMethod = 'credit';
        termid = 2;
        this.methods.emit({ method: paymentMethod, totalAmount: this.totalamount }); // Emit the method string directly
        break;
      case 3:
        paymentMethod = 'cheque';
        termid = 3;
        break;
      case 4:
        paymentMethod = 'card';
        termid = 4;
        this.fetchpaymentmethod();
        break;
      case 5:
        paymentMethod = 'bankdeposit';
        termid = 5;
        break;
      default:
        paymentMethod = 'Unknown';
    }

    console.log('Selected Payment Method:', paymentMethod);
    this.accountmethod(paymentMethod);

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
