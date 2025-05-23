import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin, of } from 'rxjs';
import { catchError, debounceTime, finalize, first, map, tap } from 'rxjs/operators';
import { SalesInvoiceLineBatchService } from 'app/entities/sales-invoice-line-batch/service/sales-invoice-line-batch.service';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { ISalesInvoiceLines } from '../sales-invoice-lines.model';
import { SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';
import { SalesInvoiceLinesFormGroup, SalesInvoiceLinesFormService } from './sales-invoice-lines-form.service';
import { FormBuilder, FormArray, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs';
import CommonModule from 'app/shared/shared.module';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';
import { InventorybatchesService } from 'app/entities/inventorybatches/service/inventorybatches.service';
import { IBinCard } from 'app/entities/bin-card/bin-card.model';
import { reference } from '@popperjs/core';
import { BinCardService } from 'app/entities/bin-card/service/bin-card.service';
import { MessageCommunicationService } from 'app/core/util/message.communication.service';
import { AddInvoicetableComponent } from 'app/entities/salesinvoice/update/add-invoicetable/add-invoicetable.component';
import { ReceiptModalComponent } from 'app/entities/receipt-modal/receipt-modal.component';
import { v4 as uuidv4 } from 'uuid';
import { AccountsService } from 'app/entities/accounts/service/accounts.service';
import { SalesinvoiceService } from 'app/entities/salesinvoice/service/salesinvoice.service';
import { TransactionsService } from 'app/entities/transactions/service/transactions.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines-update',
  templateUrl: './sales-invoice-lines-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, AddInvoicetableComponent, ReceiptModalComponent],
})
export class SalesInvoiceLinesUpdateComponent implements OnInit {
  isSaving = false;
  @Output() totalUpdated = new EventEmitter<number>();
  salesInvoiceLines: ISalesInvoiceLines[] = []; // Now an array of sales invoice lines
  filteredItems: IInventory[][] = []; // Array of arrays to store filtered items for each row
  showCodeField: boolean = false;
  bincard: IBinCard[] = [];
  bincreate = inject(BinCardService);
  messagenotify = inject(MessageCommunicationService);
  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected salesInvoiceLinesFormService = inject(SalesInvoiceLinesFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  linebatches = inject(SalesInvoiceLineBatchService);
  inventoryedit = inject(InventoryService);
  salesinvoice = inject(SalesinvoiceService);
  inevntorybatchservice = inject(InventorybatchesService);
  transactionsave = inject(TransactionsService);
  @Input() invoicecode: string | null = null;
  @Input() selectedItem: any;
  @Input() fetchedItems: any;
  @Input() nextvalue: any;
  sharedSubId: string = '';
  acc = inject(AccountsService);
  @ViewChild(AddInvoicetableComponent)
  AddInvoicetableComponent!: AddInvoicetableComponent;
  customeraccid = inject(CustomerService);
  @ViewChild(ReceiptModalComponent)
  ReceiptModalComponent!: ReceiptModalComponent;
  // Use FormArray to handle multiple lines
  editForm: FormGroup = this.fb.group({
    salesInvoiceLines: this.fb.array([]), // Define a FormArray
  });
  finaltotal: number = 0;
  toggleShowCodeField(): void {
    this.showCodeField = !this.showCodeField;
  }
  get salesInvoiceLinesDummyArray(): FormArray {
    return this.editForm.get('salesInvoiceLines') as FormArray;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedItem'] && this.selectedItem) {
      console.log('Before Adding Item:', this.salesInvoiceLinesArray.controls);
      this.addItemToFormArray(this.selectedItem);
      console.log('After Adding Item:', this.salesInvoiceLinesArray.controls);
    }
    if (changes['fetchedItems'] && this.fetchedItems) {
      // Loop through the fetchedItems array and add each item to the form array
      this.fetchedItems.forEach((item: any) => {
        this.addItemToFormArray(item);
      });
      console.log('Fetched Items on Change:', this.fetchedItems); // Log fetched items
    }
    console.log('check hereeeeee', this.salesInvoiceLinesDummyArray.value);
    this.setvaluesbincar();
    this.salesInvoiceLinesService.updateSalesInvoiceLines(this.salesInvoiceLinesDummyArray);
    //this.salesaccfetch();
    //this.updatecustomermain();
    // this.editinventory();
    const total = this.salesInvoiceLinesDummyArray.controls.reduce((sum: number, control: any) => {
      const val = Number(control.get('linetotal')?.value || 0);
      return isFinite(val) && val < 1e100 ? sum + val : sum;
    }, 0);
    console.log('Total from controlsxxxxxxxxxxxxxxxxxxxxxx:', total);
    setTimeout(() => {
      this.totalUpdated.emit(total);
    });
  }

  transaction = [
    {
      id: null, // Set id to null as required by NewTransactions type
      accountId: 0,
      accountCode: '',
      debit: 0,
      credit: 0,
      date: dayjs(),
      refDoc: '',
      refId: 0,
      subId: '',
      source: 'Invoice',
      paymentTermId: 0,
      paymentTermName: '',
      lmu: 0,
      lmd: dayjs(),
    },
  ]; // Non-null assertion operator indicates it will be assigned later

  fetchaccdet(): void {
    console.log('Selected Items >>>>>>>>:', this.salesInvoiceLinesDummyArray.value);

    this.salesInvoiceLinesService.setSubId(this.sharedSubId);

    const fetchObservables = this.salesInvoiceLinesDummyArray.value.map((item: any, index: number) => {
      if (item.itemcode) {
        return this.inventoryedit.query({ 'code.equals': item.itemcode }).pipe(
          map((res: HttpResponse<any[]>) => {
            const inventoryData = res.body?.[0];
            if (inventoryData) {
              if (!this.transaction[index]) {
                this.transaction[index] = {
                  id: null,
                  accountId: 0,
                  accountCode: '',
                  debit: 0,
                  credit: 0,
                  date: dayjs(),
                  refDoc: '',
                  refId: 0,
                  subId: '',
                  source: 'Invoice',
                  paymentTermId: 0,
                  paymentTermName: '',
                  lmu: 0,
                  lmd: dayjs(),
                };
              }
              this.transaction[index].accountId = inventoryData.accountid;
              this.transaction[index].accountCode = inventoryData.accountcode;
              this.transaction[index].subId = this.sharedSubId;

              const matchingFormControl = this.salesInvoiceLinesDummyArray.controls.find((ctrl: any) => {
                return ctrl.get('itemcode')?.value?.toString() === inventoryData.code;
              });

              if (matchingFormControl) {
                const linetotal = matchingFormControl.get('linetotal')?.value || 0;
                this.transaction[index].credit = linetotal;
                console.log(`Set debit ${linetotal} for transaction with accountCode ${inventoryData.accountcode}`);
              }
            }
          }),
          catchError(error => {
            console.error(`Error fetching inventory for ${item.itemcode}:`, error);
            return of(null); // continue with other items
          }),
        );
      } else {
        console.warn('Missing itemcode for an item:', item);
        return of(null);
      }
    });

    forkJoin(fetchObservables).subscribe(() => {
      console.log('All inventory fetches done. Transaction Data:', this.transaction);
      this.inventorytransac();
      this.fetchacc(); // 🔁 Now fetch accounts after all inventory data is loaded
    });
  }

  //  const account = accounts[0];
  // const updatedAccount = {
  //  id: account.id,
  //   debitamount: account.debitamount + this.totalamount,
  //    amount: account.amount + this.totalamount,
  //    };

  // Call partial update (PATCH)
  //  this.acc.partialUpdate(updatedAccount).subscribe({
  //   next: () => {
  //     console.log('Account updated successfully.');
  //  },
  //  error: (err) => {
  //   console.error('Failed to update account:', err);
  // }
  // });
  // Do something with the accounts
  // },
  // error: (err) => {
  // console.error('Failed to fetch accounts:', err);

  account = [
    {
      // Set id to null as required by NewTransactions type
      accountId: 0,
      creditamount: 0,
      amount: 0,
      accountcode: '',
    },
  ];
  fetchacc() {
    this.transaction.forEach((tran, i) => {
      if (tran.accountId) {
        this.acc.query({ 'id.equals': tran.accountId }).subscribe({
          next: (res: HttpResponse<any[]>) => {
            const accounts: any[] = res.body || [];
            const account = accounts[0];
            console.log('Account Data:', accounts);
            if (account) {
              const updatedAccount = {
                accountId: tran.accountId,
                creditamount: account.creditamount + tran.credit,
                amount: account.amount - tran.credit,
                accountcode: tran.accountCode,
              };

              // Set or update the value in the `account` array
              this.account[i] = updatedAccount;

              // Logging for debug
              console.log(`Account Data for transaction[${i}]:`);
              console.log('Amount:', updatedAccount.amount);
              console.log('Debit Amount:', updatedAccount.creditamount);
              console.log('Account ID:', updatedAccount.accountId);

              console.log('Account Code>>>>>>>>>>>>>>>>:', this.account);
              this.partialUpdate(); // Call partialUpdate after setting the account data
            } else {
              console.warn(`No account found for accountId ${tran.accountId}`);
            }
          },
          error: err => {
            console.error(`Error fetching account data for accountId ${tran.accountId}:`, err);
          },
        });
      }
    });
  }
  inventorytransac(): void {
    const transactionCalls = this.transaction.map((tran: any) => this.transactionsave.create(tran));

    forkJoin(transactionCalls).subscribe({
      next: responses => {
        console.log('All transactions created successfullyinvennnnnnnnnnnnnnnn:', responses);
      },
      error: error => {
        console.error('Error creating transactions:', error);
      },
    });
  }
  partialUpdate(): void {
    this.account.forEach(acc => {
      if (acc.accountId) {
        const updatePayload = {
          id: acc.accountId,
          creditamount: acc.creditamount,
          amount: acc.amount,
        };

        this.acc.partialUpdate(updatePayload).subscribe({
          next: response => {
            console.log(`Successfully updated account ID ${acc.accountId}`, response);
          },
          error: error => {
            console.error(`Failed to update account ID ${acc.accountId}`, error);
          },
        });
      } else {
        console.warn('Skipping update due to missing accountId:', acc);
      }
    });
  }
  transactionsalesacc = {
    id: null, // Set id to null as required by NewTransactions type
    accountId: 41,
    accountCode: '513',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: '',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };
  salestransaction(): void {
    const total = this.salesInvoiceLinesDummyArray.controls.reduce((sum: number, control: any) => {
      const val = Number(control.get('linetotal')?.value || 0);
      return isFinite(val) && val < 1e100 ? sum + val : sum;
    }, 0);
    this.transactionsalesacc.subId = this.salesInvoiceLinesService.getSubId();
    this.transactionsalesacc.refDoc = this.invoicecode ? this.invoicecode.toString() : '';
    this.transactionsalesacc.credit = total;
    this.transactionsave.create(this.transactionsalesacc).subscribe({
      next: response => {
        console.log('Transaction created successfully888888888888888888888888888888:', response.body);
      },
      error: error => {
        console.error('Error creating transaction:', error);
      },
    });
  }
  salesaccfetch(): void {
    const total = this.salesInvoiceLinesDummyArray.controls.reduce((sum: number, control: any) => {
      const val = Number(control.get('linetotal')?.value || 0);
      return isFinite(val) && val < 1e100 ? sum + val : sum;
    }, 0);

    console.log('Total from controls:', total);

    this.acc.query({ 'id.equals': 41 }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];

        if (account) {
          console.log('Fetched Sales Account:', accounts);

          const originalAmount = Number(account.amount || 0);
          const originalCredit = Number(account.creditamount || 0);

          const updatedAmount = originalAmount + total;
          const updatedCredit = originalCredit + total;

          const safeFloat = (val: number): number => {
            if (!isFinite(val) || isNaN(val)) return 0;
            const MAX_SQL_FLOAT = 1.79e308;
            return Math.min(+val.toFixed(6), MAX_SQL_FLOAT);
          };

          const updatedAccount = {
            id: account.id,
            creditamount: safeFloat(updatedCredit),
            amount: safeFloat(updatedAmount),
            // canedit: !!account.canedit
          };

          this.acc.partialUpdate(updatedAccount).subscribe({
            next: () => {
              console.log('Sales Account updated successfully.');
            },
            error: err => {
              console.error('Failed to update sales account:', err);
            },
          });
        } else {
          console.warn('No account found with ID 41.');
        }
      },
      error: err => {
        console.error('Error fetching account with ID 41:', err);
      },
    });
  }

  transactionclosingstock = {
    id: null, // Set id to null as required by NewTransactions type
    accountId: 125,
    accountCode: 'CLSSTK',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'FinishGoodsTransfer',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };

  closingstocktransaction(): void {
    const total = this.salesInvoiceLinesDummyArray.controls.reduce((sum: number, control: any) => {
      const val = Number(control.get('linetotal')?.value || 0);
      return isFinite(val) && val < 1e100 ? sum + val : sum;
    }, 0);
    this.transactionclosingstock.subId = this.salesInvoiceLinesService.getSubId();
    this.transactionclosingstock.refDoc = this.invoicecode ? this.invoicecode.toString() : '';
    this.transactionclosingstock.debit = total;
    this.transactionsave.create(this.transactionclosingstock).subscribe({
      next: response => {
        console.log('Transaction created successfully888888888888888888888888888888:', response.body);
      },
      error: error => {
        console.error('Error creating transaction:', error);
      },
    });
  }

  closestockupdate(): void {
    const total = this.salesInvoiceLinesDummyArray.controls.reduce((sum: number, control: any) => {
      const val = Number(control.get('linetotal')?.value || 0);
      return isFinite(val) && val < 1e100 ? sum + val : sum;
    }, 0);
    this.closingstocktransaction();
    console.log('Total from controls:', total);
    this.acc.query({ 'id.equals': 125 }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];

        if (account) {
          console.log('Fetched Sales cccccccccccccccccccccccc Account:', account);
          const originalAmount = Number(account.amount || 0);
          const originalDebit = Number(account.debitamount || 0);
          console.log('Original Amount:', originalAmount);
          console.log('Original Credit:', originalDebit);

          const updatedAmount = originalAmount + total;
          const updatedDebit = originalDebit + total;
          console.log('Updated Amount:', updatedAmount);
          console.log('Updated Credit:', updatedDebit);
          const updatePayload = {
            id: 125,
            debitamount: updatedDebit,
            amount: updatedAmount,
          };

          this.acc.partialUpdate(updatePayload).subscribe({
            next: response => {
              console.log(`Successfully updated account ID ccccccccccccccccccccc `, response);
            },
            error: error => {
              console.error(`Failed to update account ID  `, error);
            },
          });
          // TODO: Add logic here to update or process the account
        } else {
          console.warn('No account found with ID 125.');
        }
      },
      error: err => {
        console.error('Error fetching account with ID 125:', err);
      },
    });
  }

  customermaintransaction = {
    id: null, // Set id to null as required by NewTransactions type
    accountId: 7,
    accountCode: '116',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Invoice',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };
  customermaintransactions(amountOwing: number, inid: number): void {
    this.customermaintransaction.refId = inid;
    this.customermaintransaction.subId = this.salesInvoiceLinesService.getSubId();
    this.customermaintransaction.refDoc = this.invoicecode ? this.invoicecode.toString() : '';
    this.customermaintransaction.credit = amountOwing;
    this.transactionsave.create(this.customermaintransaction).subscribe({
      next: response => {
        console.log('Transaction created successfully1222222222222222222222:', response.body);
      },
      error: error => {
        console.error('Error creating transaction:', error);
      },
    });
  }

  updatecustomermain(amountOwing: number, inid: number): void {
    const total = this.salesinvoice.getTotal();

    console.log('Total from controls:', total);

    this.acc.query({ 'id.equals': 7 }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts = res.body || [];
        const account = accounts[0];

        if (account) {
          console.log('Fetched Sales Account:', account);

          const originalAmount = Number(account.amount || 0);
          const originalCredit = Number(account.creditamount || 0);

          console.log('Original Amount:', originalAmount);
          console.log('Original Credit:', originalCredit);

          const updatedAmount = originalAmount + amountOwing;
          const updatedCredit = originalCredit + amountOwing;

          console.log('Updated Amount:', updatedAmount);
          console.log('Updated Credit:', updatedCredit);

          const updatePayload = {
            id: 7,
            creditamount: updatedCredit,
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

  updatesalesincome(inid: number): void {
    console.log('logggggggggggggggggggg', this.account[0]);
    const total = this.salesinvoice.getTotal();
    this.salesInvoiceLinesService.settotalinvoicelines(total);
    const itemcost = this.salesInvoiceLinesDummyArray.controls.reduce((sum: number, control: any) => {
      const val = Number(control.get('itemcost')?.value || 0);
      return isFinite(val) && val < 1e100 ? sum + val : sum;
    }, 0);
    // <-- Should log the actual total

    console.log('Total from controlsdddddddddddddddddddddddddddddddddddd:', itemcost);

    console.log('Total from controls:', total);
    this.acc.query({ 'id.equals': 33 }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];

        if (account) {
          console.log('Fetched Sales Account:', account);
          const profit = total - itemcost;
          this.salesInvoiceLinesService.setprofit(profit);
          this.salesincometransactions(inid);
          const originalAmount = Number(account.amount || 0);
          const originalCredit = Number(account.debitamount || 0);

          const updatedAmount = originalAmount + profit;
          const updatedCredit = originalCredit + profit;

          const updatePayload = {
            id: 33,
            deitamount: updatedCredit,
            amount: updatedAmount,
          };

          this.acc.partialUpdate(updatePayload).subscribe({
            next: response => {
              console.log(`Successfully updated account ID in come in come  ${account.id}`, response);
            },
            error: error => {
              console.error(`Failed to update account ID ${account.id}`, error);
            },
          });
          const profits = total - itemcost;
          console.log('Profixxxxxxxxxxxxxxxxxxts:', profits);
        } else {
          console.warn('No account found with ID 7.');
        }
      },
      error: err => {
        console.error('Error fetching account with ID 7:', err);
      },
    });
  }
  salesincometransaction = {
    id: null, // Set id to null as required by NewTransactions type
    accountId: 33,
    accountCode: '42',
    debit: 0,
    credit: 0,
    date: dayjs(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Invoice',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs(),
  };

  salesincometransactions(inid: number): void {
    this.salesincometransaction.refId = inid;
    this.salesincometransaction.subId = this.salesInvoiceLinesService.getSubId();
    this.salesincometransaction.refDoc = this.invoicecode ? this.invoicecode.toString() : '';
    this.salesincometransaction.debit = this.salesInvoiceLinesService.getprofit();
    console.log('Sales Income Transaction:', this.salesInvoiceLinesService.getprofit());
    this.transactionsave.create(this.salesincometransaction).subscribe({
      next: response => {
        console.log('Transaction created successfully sales incomingggggggggs:', response.body);
      },
      error: error => {
        console.error('Error creating transaction:', error);
      },
    });
  }

  setvaluesbincar(): void {
    this.bincard = this.salesInvoiceLinesDummyArray.value.map((item: any) => ({
      itemID: item.itemid,
      itemCode: item.itemcode,
      qtyOut: item.quantity,
      reference: 'SalesInvoice',
      price: item.sellingprice,
      locationID: 1,
      lmd: dayjs().toISOString(),
      recordDate: dayjs().toDate(),
      batchId: item.itemid,
      referenceCode: this.nextvalue,
    }));

    console.log('Updated bincard:', this.bincard);
  }
  createbin(): void {
    if (!this.bincard || this.bincard.length === 0) {
      console.warn('No bin records to create.');
      return;
    }

    // Step 1: Fetch inventory data for each bin item
    const inventoryRequests = this.bincard.map(bin =>
      this.inventoryedit.find(bin.itemID ?? 0).pipe(
        catchError(error => {
          console.error(`Error fetching inventory for itemID ${bin.itemID}:`, error);
          return of(null); // Continue even if some fail
        }),
      ),
    );

    // Step 2: Process responses and create bins with updated 'opening'
    forkJoin(inventoryRequests).subscribe({
      next: responses => {
        const updatedBins = this.bincard.map((bin, index) => {
          const inventoryData = responses[index]?.body;
          const availableQuantity = inventoryData?.availablequantity ?? 0;
          return {
            ...bin,
            opening: availableQuantity,
            id: null as null,
          };
        });

        // Step 3: Create bin records using updatedBins
        const createRequests = updatedBins.map(bin =>
          this.bincreate.create(bin).pipe(
            catchError(error => {
              console.error('Error creating bin:', error);
              return of(null); // Continue execution on error
            }),
          ),
        );

        forkJoin(createRequests).subscribe({
          next: results => console.log('All bin records created successfully:', results),
          error: err => console.error('Error in batch creation:', err),
          complete: () => console.log('Batch creation process completed.'),
        });
      },
      error: err => console.error('Error fetching inventory data:', err),
    });
  }

  editinventory(): void {
    if (!this.bincard || this.bincard.length === 0) {
      console.warn('No bin records available for inventory lookup.');
      return;
    }

    // Fetch inventory for each `itemID`
    const inventoryRequests = this.bincard.map(bin =>
      this.inventoryedit.find(bin.itemID ?? 0).pipe(
        catchError(error => {
          console.error(`Error fetching inventory for itemID ${bin.itemID}:`, error);
          return of(null); // Return null if request fails
        }),
      ),
    );

    // Execute all requests in parallel
    forkJoin(inventoryRequests).subscribe({
      next: responses => {
        const patchRequests = this.bincard.map((bin, index) => {
          const inventoryData = responses[index]?.body;

          if (!inventoryData) {
            console.warn(`No inventory data found for itemID ${bin.itemID}`);
            return of(null); // Skip this item
          }
          const availableQuantity = inventoryData.availablequantity ?? 0;
          const updatedQuantity = (inventoryData.availablequantity ?? 0) - (bin.qtyOut ?? 0);

          console.log(`Updating inventory for itemID ${bin.itemID}: ${inventoryData.availablequantity} → ${updatedQuantity}`);

          // Call PATCH API to update inventory
          return this.inventoryedit.partialUpdate({ id: bin.itemID ?? 0, availablequantity: updatedQuantity }).pipe(
            tap(updatedResponse => console.log(`Updated inventory response for itemID ${bin.itemID}:`, updatedResponse)),
            catchError(error => {
              console.error(`Error updating inventory for itemID ${bin.itemID}:`, error);
              return of(null);
            }),
          );
        });

        // Execute all PATCH requests in parallel
        forkJoin(patchRequests).subscribe({
          next: updateResponses => {
            console.log('Inventory updated successfully:', updateResponses);
          },
          error: err => console.error('Error updating inventory:', err),
          complete: () => console.log('Inventory update process completed.'),
        });
      },
      error: err => console.error('Error fetching inventory:', err),
      complete: () => console.log('Inventory lookup process completed.'),
    });

    //this.transactionmodule(inid);
  }
  precentage: string = '';
  discountvalue: string = '';
  addItemToFormArray(item: any): void {
    const newItem = this.fb.group({
      itemcode: [item.code || item.itemcode || ''], // Match template
      itemid: [item.id || item.itemid || null], // Match template
      itemname: [item.name || item.itemname], // Match template

      quantity: [item.availablequantity || item.quantity],
      description: [item.name || item.itemname], // Match template
      sellingprice: [item.lastsellingprice || item.lastsellingprice || item.sellingprice], // Match template
      linetotal: [{ value: 0, disabled: true }], // Match template
      unitofmeasurement: [item.unitofmeasurement || ''], // Match template
      discount: [0],
      itemcost: [item.lastcost || item.itemcost || 0],
      itemprice: [item.lastsellingprice || item.itemprice || 0],
    });
    console.log('Item discountzzzzzzzzzzzz:', item.discount);
    if (typeof item.discount === 'string' && item.discount.includes('%')) {
      console.log('Item discount (percentage):', item.discount);
      this.precentage = '%';
      this.discountvalue = item.discount.replace('%');
      console.log('Discount valuexxxxxxxxx:', this.discountvalue);
    }
    if (typeof item.discount === 'string' && item.discount.includes('v')) {
      this.precentage = 'v';
      this.discountvalue = item.discount.replace('v');
      console.log('Item discount (value):', item.discount);
    }
    console.log('Item discount:', item.discount);
    console.log('Discount valuexxxxxxxxx:', this.discountvalue);
    this.discountvalue = item.discount;
    console.log('New Item Addedaazzz:', newItem.value);
    console.log(this.selectedItem);
    // Calculate lineTotal dynamically when quantity or sellingPrice changes
    this.listenToQuantityAndPriceChanges(newItem);

    this.salesInvoiceLinesDummyArray.push(newItem);
    console.log('check hereeeeee', this.salesInvoiceLinesDummyArray.value);
  }
  listenToQuantityAndPriceChanges(formGroup: FormGroup): void {
    const quantityControl = formGroup.get('quantity');
    const sellingPriceControl = formGroup.get('sellingprice');

    // Use debounceTime to avoid too frequent updates (e.g., wait 300ms after the user stops typing)
    quantityControl?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal(formGroup));
    sellingPriceControl?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal(formGroup));

    // Also update lineTotal when the form is initialized
    this.updateLineTotal(formGroup);
  }
  updateLineTotal(formGroup: FormGroup): void {
    const quantity = formGroup.get('quantity')?.value || 0;
    const sellingPrice = formGroup.get('sellingprice')?.value || 0;
    const lineTotalControl = formGroup.get('linetotal');
    console.log('linexxxxxxxx', sellingPrice);
    const lineTotal = quantity * sellingPrice;
    let finalTotal = lineTotal;
    console.log('Line Total:', finalTotal);
    lineTotalControl?.setValue(finalTotal, { emitEvent: false });
    if (this.precentage.includes('%')) {
      const discount = parseFloat(this.discountvalue) / 100;
      finalTotal = lineTotal - lineTotal * discount;
      lineTotalControl?.setValue(finalTotal, { emitEvent: false });
    } else if (this.precentage.includes('v')) {
      const discount = parseFloat(this.discountvalue);
      finalTotal = lineTotal - discount;
      lineTotalControl?.setValue(finalTotal, { emitEvent: false });
    } else {
      console.log('Discount valuezzzzzzzzzz:', this.discountvalue);
      finalTotal = lineTotal;
      lineTotalControl?.setValue(finalTotal, { emitEvent: false });
      finalTotal = Math.max(0, finalTotal); // Ensure it's not negative}
    }

    // Calculate the total of all lineTotals in the form array
    const total = this.salesInvoiceLinesDummyArray.controls
      .map(control => control.get('linetotal')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    console.log('Totallll:', total);
    this.salesInvoiceLinesService.settotalinvoicelines(total);
    setTimeout(() => {
      this.totalUpdated.emit(total);
    });
  }
  transactions = {
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

  addtrasction(): void {
    console.log('Adding transaction...');
    const totalamount = this.salesinvoice.getTotal();
    const customername = this.salesinvoice.getCustomerName();
    console.log('Account ID:>>>>>>>>>>>>', customername);
    this.acc.query({ 'name.contains': customername }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        console.log('Fetched accountsssss:', accounts[0].amount + totalamount);
        console.log('Fetched accountsssss:', accounts[0].debitamount + totalamount);

        console.log('Fetched accountsssss:', accounts[0].id);
        const account = accounts[0];
        const updatedAccount = {
          id: account.id,
          debitamount: account.debitamount + totalamount,
          amount: account.amount + totalamount,
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
        this.transactions.subId = this.salesInvoiceLinesService.getSubId();

        this.transactions.refDoc = this.invoicecode ? this.invoicecode.toString() : '';
        this.transactions.debit = totalamount;

        console.log('Updated transaction:', this.transactions);
        console.log('Transaction ID:', this.invoicecode);

        this.transactionsave.create(this.transactions).subscribe({
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
  accountsId: number = 0;
  accountcode: string = '';

  fetchaccq(): void {
    this.salesinvoice.getCustomerId();
    console.log('Fetching account ID... >>>>>>>>>>>>>>>>>>>', this.salesinvoice.getCustomerId());
    this.salesInvoiceLinesService.getSubId();
    // Make sure you pass the correct query params for filtering by customerid
    this.customeraccid.query({ 'id.equals': this.salesinvoice.getCustomerId() }).subscribe((res: HttpResponse<any[]>) => {
      const accounts = res.body || [];
      console.log('Fetched accounts:', accounts);

      // You may not even need to filter again if the query already filters by customerId
      const selectedAccount = accounts.length > 0 ? accounts[0] : null;

      if (selectedAccount) {
        this.accountcode = selectedAccount.accountcode;
        this.transactions.accountCode = selectedAccount.accountcode;
        this.transactions.accountId = selectedAccount.accountid;
        this.accountsId = selectedAccount.id;
        console.log('Selected Account ID:', this.accountsId);
      } else {
        console.log('No account found for customer ID:', this.salesinvoice.getCustomerId());
      }
    });
  }

  ngOnInit(): void {
    console.log('Selected Item on Initttt:', this.selectedItem); // Log selected item
    this.messagenotify.notificationAnnounced$.subscribe(message => {
      if (message.topic === 'DELETE_ITEM') {
        const itemId = message.message;
        this.removeInvoiceLinecode(itemId);
      }
    });
    this.activatedRoute.data.subscribe(({ salesInvoiceLines }) => {
      // Ensure salesInvoiceLines is always an array
      this.salesInvoiceLines = Array.isArray(salesInvoiceLines) ? salesInvoiceLines : [salesInvoiceLines];

      if (salesInvoiceLines.length > 0) {
        this.updateForm(salesInvoiceLines);
      }

      console.log('Sales Invoice Lines:', this.salesInvoiceLines); // Add this line to see if the data is correct
      this.updateForm(this.salesInvoiceLines);
    });
  }

  onItemCodeSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.code === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected itemssss:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        itemcode: selectedItem.code,
        itemname: selectedItem.name,
        itemid: selectedItem.id, // Update other fields as necessary
        // Add any other fields you want to update with the selected item's details
      });
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }

  onItemNameSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.name === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected item:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        itemcode: selectedItem.code,
        itemname: selectedItem.name,
        itemid: selectedItem.id, // Update other fields as necessary
        // Add any other fields you want to update with the selected item's details
      });
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }
  // Get FormArray instance
  get salesInvoiceLinesArray(): FormArray {
    return this.editForm.get('salesInvoiceLines') as FormArray;
  }
  onItemCodeInput(event: Event, index: number): void {
    // Type assertion: Treat event target as HTMLInputElement
    const inputElement = <HTMLInputElement>event.target;
    const value = inputElement.value; // Get the value typed by the user

    // Log the input value to the console when a user types
    console.log('User typed:', value);

    if (!value) {
      this.filteredItems[index] = []; // Clear suggestions if input is empty
      return;
    }

    console.log('Fetching items for value:', value);

    this.salesInvoiceLinesService
      .getElementsByUserInputCode(value) // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IInventory[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.code && item.name); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: error => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }

  onQuantityChange(index: number): void {
    const salesInvoiceLineGroup = this.salesInvoiceLinesDummyArray.at(index) as FormGroup;

    const quantity = salesInvoiceLineGroup.get('quantity')?.value || 0;
    const itemPrice = salesInvoiceLineGroup.get('sellingPrice')?.value || 0; // Fixed price per unit

    // Ensure quantity is never negative
    const validQuantity = Math.max(0, quantity);

    // Calculate selling price
    const newSellingPrice = validQuantity * itemPrice;

    // Update selling price in the form
    salesInvoiceLineGroup.patchValue({ linetotal: newSellingPrice });
    const lineTotal = salesInvoiceLineGroup.get('linetotal')?.value;
    console.log('Line Totarrrrrrrrrl:', lineTotal);
    // Emit the updated lineTotal value
    this.totalUpdated.emit(lineTotal);
  }
  onItemNameInput(event: Event, index: number): void {
    // Type assertion: Treat event target as HTMLInputElement
    const inputElement = <HTMLInputElement>event.target;
    const value = inputElement.value; // Get the value typed by the user

    // Log the input value to the console when a user types
    console.log('User typed:', value);

    if (!value) {
      this.filteredItems[index] = []; // Clear suggestions if input is empty
      return;
    }

    console.log('Fetching items for value:', value);

    this.salesInvoiceLinesService
      .getElementsByUserInputName(value) // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IInventory[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.code && item.name); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: error => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }
  calculateTotal(): void {
    const total = this.salesInvoiceLinesDummyArray.controls
      .map(control => control.get('linetotal')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    console.log('Total Selling Price:', total);
    this.totalUpdated.emit(total);
  }

  // Add a new line to the form
  addSalesInvoiceLine(): void {
    const newRow = this.salesInvoiceLinesFormService.createSalesInvoiceLinesFormGroup();
    console.log('Adding row:', newRow.value);
    this.salesInvoiceLinesArray.push(this.salesInvoiceLinesFormService.createSalesInvoiceLinesFormGroup());
  }

  // Remove a line from the form
  removeSalesInvoiceLine(index: number): void {
    this.messagenotify.pushNotification('DELETE_ITEM', this.salesInvoiceLinesArray.at(index).get('itemid')?.value);
    this.salesInvoiceLinesArray.removeAt(index);
    this.updateTotalInvoiceLines();
  }
  transactionmodule(inid: number): void {
    console.log('this is valuessss xxxxxxxxxxxxxxxxxxxxxxxxxxx:', this.salesInvoiceLinesDummyArray.value);
    console.log('readddddddddddddddddddddddddddddddddddddddddddd', this.salesinvoice.getTotal());
    this.salesinvoice.query({ 'id.equals': inid }).subscribe({
      next: res => {
        console.log('Full responsevvvvvvvvvvvvvvvvvvvvvvvvv:', res);
        if (res.body && res.body[0]) {
          console.log('SalesInvoice datavvvvvvvvvvvvv:', res.body[0].amountowing);
          this.sharedSubId = uuidv4();
          if (this.salesInvoiceLinesDummyArray.value.length === 0) {
            this.fetchaccdet();
            this.fetchacc();
          }

          this.salesaccfetch();
          this.salestransaction();
          this.closestockupdate();
          this.updatecustomermain(Number(res.body[0].amountowing), inid);
          this.customermaintransactions(Number(res.body[0].amountowing), inid);
          this.updatesalesincome(inid);
          this.fetchaccq();
          this.addtrasction();

          window.history.back();
        } else {
          console.warn('SalesInvoice data is null or empty.');
        }
      },
      error: err => {
        console.error('Error fetching sales invoice:', err);
      },
    });
  }
  previousState(): void {
    window.history.back();
  }

  async dcreate(): Promise<void> {
    this.createbin();
  }

  async creates(): Promise<void> {
    this.createbin();
  }

  batchItems: Array<{
    id: number;
    lineId: number;
    batchLineId: number;
    itemId: number;
    code: string;
    batchId: number;
    batchCode: string;
    txDate: string; // ISO date string representation
    manufactureDate: string; // ISO date string representation
    expiredDate: string; // ISO date string representation
    qty: number;
    cost: number; // BigDecimal equivalent in Java
    price: number; // BigDecimal equivalent in Java
    notes: string;
    lmu: number; // Long equivalent in Java
    lmd: string; // ISO date string representation
    nbt: boolean;
    vat: boolean;
    addedById: number;
  }> = [];

  save(inid: number): void {
    this.isSaving = true;

    if (!this.editForm) {
      console.error('Form is not initialized');
      return; // Exit if the form is not initialized
    }

    // Ensure the form is a FormGroup and check if 'salesInvoiceLines' is a FormArray
    if (!(this.editForm.get('salesInvoiceLines') instanceof FormArray)) {
      console.error('Form is not an instance of FormArray');
      return; // Exit if salesInvoiceLines is not a FormArray
    }

    // Check if the form is valid
    if (!this.editForm.valid) {
      console.error('Form is invalid', this.editForm.errors);
      return; // Exit if the form is not valid
    }

    // Get the invoice lines from the form (now it's a FormArray)
    let salesInvoiceLines = this.salesInvoiceLinesFormService.getSalesInvoiceLines(this.salesInvoiceLinesArray);

    // Assign invoiceid to all rows and ensure unique lineid
    salesInvoiceLines = salesInvoiceLines.map((line, index) => ({
      ...line,
      invoiceid: inid, // Assign the provided invoice ID
      lineid: line.lineid ?? index + 1, // Assign a unique `lineid` if it's missing
    }));

    // Example query for a different itemcode (11540) and price
    //this.inevntorybatchservice.query({ 'itemid.equals': '11540', 'price.equals': '1800' }).subscribe((res: HttpResponse<any[]>) => {
    // console.log('Response Body:', res.body);
    // console.log('Response Headers:', res.headers.keys()); // Logs available headers
    // console.log('Specific Header (Example - X-Total-Count):', res.headers.get('X-Total-Count'));
    //  });

    console.log('Modified sales invoice lines:', salesInvoiceLines);

    // Example query for a different itemcode (11540) and price
    //this.inevntorybatchservice.query({ 'itemid.equals': '11540', 'price.equals': '1800' }).subscribe((res: HttpResponse<any[]>) => {
    // console.log('Response Body:', res.body);
    // console.log('Response Headers:', res.headers.keys()); // Logs available headers
    // console.log('Specific Header (Example - X-Total-Count):', res.headers.get('X-Total-Count'));
    //});
    console.log('Before insert, batchLineIdddddddddddddd:', this.batchItems);

    const saveObservables: Observable<HttpResponse<ISalesInvoiceLines>>[] = [];

    salesInvoiceLines.forEach(line => {
      if (line.id === null || line.id === undefined) {
        // If id is null or undefined, it's a new line, so we create it
        saveObservables.push(this.salesInvoiceLinesService.create({ ...line, id: null }));
      } else {
        // If id is not null, it's an existing line, so we update it
        saveObservables.push(this.salesInvoiceLinesService.update(line));
      }
    });
    this.createbin();
    this.editinventory();
    // If there are any observables, subscribe to them
    if (saveObservables.length > 0) {
      forkJoin(saveObservables)
        .pipe(finalize(() => this.onSaveFinalize()))
        .subscribe({
          next: async (responses: HttpResponse<any>[]) => {
            console.log('Save successful. Server response:', responses);

            // Extract the first available invoiceid
            const firstInvoiceId = responses.find(res => res.body?.invoiceid)?.body?.invoiceid;

            if (firstInvoiceId !== undefined) {
              console.log('First Invoice ID:', firstInvoiceId);

              const itemCodes = [...salesInvoiceLines.map(line => line.itemid)]; // Copy array
              console.log('Item Codes:', itemCodes);

              // Function to process each itemid one by one
              const processNextItem = async () => {
                if (itemCodes.length === 0) {
                  console.log('All items processed, now creating batches...');
                  try {
                    console.log('Final batchItems array:', this.batchItems);
                    const response = await this.linebatches.create(this.batchItems).toPromise();
                    console.log('Batch creation response:', response);
                  } catch (error) {
                    console.error('Error creating batches:', error);
                  }
                  return;
                }

                const currentItemCode = itemCodes.shift(); // Get the first itemid and remove it from the array
                console.log(`Processing itemid: ${currentItemCode}`);

                this.inevntorybatchservice
                  .query({
                    'itemid.equals': currentItemCode,
                    'quantity.greaterThan': 0,
                    sort: ['id,asc'],
                  })
                  .subscribe((res: HttpResponse<any[]>) => {
                    if (res.body) {
                      console.log(`Full Response for itemid ${currentItemCode}:`, res.body[0]);
                      const firstItem = res.body[0];

                      const matchingLine = salesInvoiceLines.find(line => line.itemid === currentItemCode);

                      this.batchItems.push({
                        id: firstInvoiceId,
                        batchId: firstItem.id,
                        batchCode: firstItem.code,
                        code: matchingLine?.itemcode ?? '',
                        lineId: matchingLine?.lineid ?? 0,
                        batchLineId: matchingLine?.lineid ?? 0,
                        itemId: firstItem.itemid,
                        txDate: dayjs().toISOString(),
                        manufactureDate: dayjs().toISOString(),
                        expiredDate: dayjs().toISOString(),
                        qty: matchingLine?.quantity ?? 0,
                        cost: firstItem.cost,
                        price: matchingLine?.linetotal ?? 0,
                        notes: '',
                        lmu: 1,
                        lmd: new Date().toISOString(),
                        nbt: false,
                        vat: false,
                        addedById: 1,
                      });

                      console.log('Updated batchItems:', this.batchItems);
                    }

                    processNextItem(); // Process the next item
                  });
              };

              // Start processing
              processNextItem();

              console.log('Modified sales invoice lines:', salesInvoiceLines);
            } else {
              console.log('No Invoice ID found in response.');
            }
            // Replace with the appropriate method call from BinCardService

            //this.onSaveSuccess();
          },
          error: error => {
            console.error('Save failed. Server error:', error);
            this.onSaveError();
          },
        });
    } else {
      this.onSaveFinalize();
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceLines>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // API for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
  protected updateForm(salesInvoiceLines: ISalesInvoiceLines[]): void {
    console.log('Received Sales Invoice Lines:', salesInvoiceLines);

    // Check the type of salesInvoiceLines
    console.log('Type of salesInvoiceLines:', Array.isArray(salesInvoiceLines) ? 'Array' : 'Not an Array');

    // Check if salesInvoiceLines is valid (non-empty array)
    if (!Array.isArray(salesInvoiceLines) || salesInvoiceLines.length === 0) {
      console.log('No sales invoice lines provided!');
      return; // Don't proceed if the data is invalid or empty
    }

    // If the data is valid, continue processing
    this.salesInvoiceLines = salesInvoiceLines;

    // Reset form and add each line to the form array
    this.salesInvoiceLinesArray.clear(); // Clear the existing form array before adding new lines

    // Add each line of salesInvoiceLines to the form array
    salesInvoiceLines.forEach(line => {
      const formGroup = this.salesInvoiceLinesFormService.createSalesInvoiceLinesFormGroup(line);
      console.log('Created form group:', formGroup.value); // Log the form group values
      this.salesInvoiceLinesArray.push(formGroup);
    });
  }

  removeInvoiceLine(index: number): void {
    this.salesInvoiceLinesDummyArray.removeAt(index);
  }
  updateTotalInvoiceLines(): void {
    const total = this.salesInvoiceLinesArray.controls
      .map(control => control.get('linetotal')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    console.log('Updated Totallll:', total);
    this.salesInvoiceLinesService.settotalinvoicelines(total);
    setTimeout(() => {
      this.totalUpdated.emit(total);
    });
  }

  removeInvoiceLinecode(code: any): void {
    // Find index of the form group in the array matching the lineId
    const index = this.salesInvoiceLinesArray.controls.findIndex(control => {
      const group = control as FormGroup;
      return group.get('itemcode')?.value === code;
    });

    // If found, remove it and notify
    if (index !== -1) {
      this.salesInvoiceLinesArray.removeAt(index);
      this.messagenotify.pushNotification('DELETE_ITEM', code);
      this.updateTotalInvoiceLines();
    }
  }
}
