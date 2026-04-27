import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { ISalesInvoiceLines } from '../sales-invoice-lines.model';
import { SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';
import { SalesInvoiceLinesFormGroup, SalesInvoiceLinesFormService } from './sales-invoice-lines-form.service';
import { FormBuilder, FormArray, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';
import CommonModule from 'app/shared/shared.module';
import { DecimalInputDirective } from 'app/shared/decimal-input.directive';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { NewAutojobsinvoicelines } from 'app/entities/autojobsinvoicelines/autojobsinvoicelines.model';
import { TransactionsService } from 'app/entities/transactions/service/transactions.service';
import { AccountsService } from 'app/entities/accounts/service/accounts.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { SalesinvoiceService } from 'app/entities/salesinvoice/service/salesinvoice.service';
import { NewTransactions } from 'app/entities/transactions/transactions.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines-update',
  templateUrl: './sales-invoice-lines-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, DecimalInputDirective],
})
export class SalesInvoiceLinesUpdateComponent implements OnInit {
  isSaving = false;
  @Output() totalUpdated = new EventEmitter<number>();
  salesInvoiceLines: ISalesInvoiceLines[] = []; // Now an array of sales invoice lines
  filteredItems: IInventory[][] = []; // Array of arrays to store filtered items for each row
  showCodeField: boolean = false;
  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected salesInvoiceLinesFormService = inject(SalesInvoiceLinesFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  @Input() selectedItem: any;
  @Input() fetchedItems: any;
  @Input() sourceInvoiceId: number | null = null;
  protected autojobsinvoicelinesService = inject(AutojobsinvoicelinesService);
  protected transactionsService = inject(TransactionsService);
  protected accountsService = inject(AccountsService);
  protected customerService = inject(CustomerService);
  protected salesinvoiceService = inject(SalesinvoiceService);
  // Use FormArray to handle multiple lines
  editForm: FormGroup = this.fb.group({
    salesInvoiceLines: this.fb.array([]), // Define a FormArray
  });
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
  }

  addItemToFormArray(item: any): void {
    const resolvedSellingPrice = Number(item.lastsellingprice ?? item.sellingprice ?? item.itemprice ?? 0);
    const newItem = this.fb.group({
      itemid: [item.id ?? item.itemid ?? null],
      itemcode: [item.code || item.itemcode || ''], // Match template
      itemname: [item.name || item.itemname], // Match template
      description: [item.description ?? null],
      unitofmeasurement: [item.unitofmeasurement ?? null],
      quantity: [item.availablequantity ?? item.quantity ?? 0],
      itemcost: [Number(item.lastcost ?? item.itemcost ?? 0)],
      itemprice: [resolvedSellingPrice],
      tax: [Number(item.tax ?? 0)],
      sellingprice: [resolvedSellingPrice], // Match template
      linetotal: [{ value: 0, disabled: true }], // Match template
      discount: [Number(item.discount ?? 0)],
      isNew: [item.isNew ?? false],
      sourceLineId: [item.lineid ?? null],
    });
    console.log('New Item Addedaazzz:', newItem.value);
    console.log(this.selectedItem);
    // Calculate lineTotal dynamically when quantity or sellingPrice changes
    this.listenToQuantityAndPriceChanges(newItem);

    this.salesInvoiceLinesDummyArray.push(newItem);
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
    const quantity = formGroup.get('quantity')?.value;
    const sellingPrice = formGroup.get('sellingprice')?.value;
    const lineTotalControl = formGroup.get('linetotal');

    // Calculate line total: quantity * sellingPrice
    const lineTotal = quantity * sellingPrice;
    lineTotalControl?.setValue(lineTotal, { emitEvent: false }); // Set the value without emitting the event to avoid infinite loop

    // Calculate the total of all lineTotals in the form array
    const total = this.salesInvoiceLinesDummyArray.controls
      .map(control => control.get('linetotal')?.value || lineTotal)
      .reduce((acc, value) => acc + value, lineTotal);
    console.log('Totallll:', total);
    // Emit the updated total of all lineTotals
    this.totalUpdated.emit(total);
  }
  ngOnInit(): void {
    console.log('Selected Item on Initttt:', this.selectedItem); // Log selected item

    this.activatedRoute.data.subscribe(({ salesInvoiceLines }) => {
      if (salesInvoiceLines) {
        if (Array.isArray(salesInvoiceLines)) {
          if (salesInvoiceLines.length > 0) {
            this.salesInvoiceLines = salesInvoiceLines;
            this.updateForm(this.salesInvoiceLines);
          }
        } else if (salesInvoiceLines.id !== null && salesInvoiceLines.id !== undefined) {
          // It's a single object with a valid ID
          this.salesInvoiceLines = [salesInvoiceLines];
          this.updateForm(this.salesInvoiceLines);
        }
      }
      console.log('Sales Invoice Lines:', this.salesInvoiceLines);
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
        description: selectedItem.description ?? null,
        unitofmeasurement: selectedItem.unitofmeasurement ?? null,
        itemcost: Number(selectedItem.lastcost ?? 0),
        itemprice: Number(selectedItem.lastsellingprice ?? 0),
        sellingprice: Number(selectedItem.lastsellingprice ?? 0),
        tax: Number((selectedItem as any).tax ?? 0),
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
        description: selectedItem.description ?? null,
        unitofmeasurement: selectedItem.unitofmeasurement ?? null,
        itemcost: Number(selectedItem.lastcost ?? 0),
        itemprice: Number(selectedItem.lastsellingprice ?? 0),
        sellingprice: Number(selectedItem.lastsellingprice ?? 0),
        tax: Number((selectedItem as any).tax ?? 0),
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
    this.salesInvoiceLinesArray.removeAt(index);
    this.calculateTotal();
  }

  previousState(): void {
    window.history.back();
  }

  save(inid: number): void {
    this.isSaving = true;

    // Ensure the form is initialized properly
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

    // Assign invoiceid to all rows and ensure unique lineid across all aggregated lines
    salesInvoiceLines = salesInvoiceLines.map((line, index) => ({
      ...line,
      invoiceid: inid, // Assign the provided invoice ID
      lineid: index + 1, // Recalculate unique lineid for the current invoice
      description: line.description ?? line.itemname ?? null,
    }));

    console.log('Modified sales invoice lines:', salesInvoiceLines);

    const saveObservables: Observable<HttpResponse<ISalesInvoiceLines>>[] = [];

    // Calculate next lineid for AutoJobsInvoiceLines if we have a sourceInvoiceId
    let nextAutoLineId = 1;
    if (this.sourceInvoiceId) {
      const existingLineIds = salesInvoiceLines.filter(l => (l as any).sourceLineId !== null).map(l => Number((l as any).sourceLineId));
      if (existingLineIds.length > 0) {
        nextAutoLineId = Math.max(...existingLineIds) + 1;
      }
    }

    salesInvoiceLines.forEach(line => {
      // If the line doesn't have an ID, or if it belongs to a different invoice (aggregated data), create it as new in SalesInvoiceLines
      if (line.id === null || line.id === undefined || line.invoiceid !== inid) {
        saveObservables.push(this.salesInvoiceLinesService.create({ ...line, id: null, invoiceid: inid }));

        // ONLY save to AutoJobsInvoiceLines if it's TRULY a new item added on this page
        if (this.sourceInvoiceId && (line as any).isNew) {
          const autoLine: NewAutojobsinvoicelines = {
            id: null,
            invocieid: this.sourceInvoiceId, // Note the typo in model: 'invocieid'
            lineid: nextAutoLineId++,
            itemid: line.itemid,
            itemcode: line.itemcode,
            itemname: line.itemname,
            description: line.description,
            unitofmeasurement: line.unitofmeasurement,
            quantity: line.quantity,
            itemcost: line.itemcost,
            itemprice: line.itemprice,
            discount: line.discount,
            tax: line.tax,
            sellingprice: line.sellingprice,
            linetotal: line.linetotal,
          };
          saveObservables.push(this.autojobsinvoicelinesService.create(autoLine) as any);
        }
      } else {
        // If it's an existing line for THIS invoice, update it
        saveObservables.push(this.salesInvoiceLinesService.update(line));
      }
    });

    // If there are any observables, subscribe to them
    if (saveObservables.length > 0) {
      forkJoin(saveObservables)
        .pipe(finalize(() => this.onSaveFinalize()))
        .subscribe({
          next: response => {
            console.log('Save successful. Server response:', response);
            this.onSaveSuccess();
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
    this.calculateTotal();
  }

  transactionmodule(inid: number): void {
    this.salesinvoiceService.find(inid).subscribe(res => {
      const invoice = res.body;
      if (invoice) {
        const total = this.salesInvoiceLinesDummyArray.controls
          .map(control => control.get('linetotal')?.value || 0)
          .reduce((acc, value) => acc + value, 0);
        const totalCost = this.salesInvoiceLinesDummyArray.controls
          .map(control => (control.get('itemcost')?.value || 0) * (control.get('quantity')?.value || 0))
          .reduce((acc, val) => acc + val, 0);
        const profit = total - totalCost;

        this.salestransaction(invoice, total);
        // closestockupdate is removed to avoid double-counting inventory costs
        this.customermaintransactions(invoice, profit);
        this.updatesalesincome(invoice, profit);
        this.addtrasction(invoice, total);
        this.inventorytransac(invoice);
      }
    });
  }

  private salestransaction(invoice: any, total: number): void {
    const transaction: NewTransactions = {
      id: null,
      accountId: 41,
      accountCode: '513',
      debit: 0,
      credit: total,
      date: dayjs(),
      refDoc: invoice.code,
      refId: invoice.id,
      subId: this.salesInvoiceLinesService.getSubId(),
      source: 'Sales',
      lmu: invoice.lmu,
      lmd: dayjs(),
    };
    this.transactionsService.create(transaction).subscribe();

    this.accountsService.find(41).subscribe(res => {
      const account = res.body;
      if (account) {
        this.accountsService.updateBalance(account.id, (account.balance ?? 0) + total).subscribe();
      }
    });
  }

  private customermaintransactions(invoice: any, profit: number): void {
    const transaction: NewTransactions = {
      id: null,
      accountId: 7,
      accountCode: '116',
      debit: 0,
      credit: profit,
      date: dayjs(),
      refDoc: invoice.code,
      refId: invoice.id,
      subId: this.salesInvoiceLinesService.getSubId(),
      source: 'Sales',
      lmu: invoice.lmu,
      lmd: dayjs(),
    };
    this.transactionsService.create(transaction).subscribe();

    this.accountsService.find(7).subscribe(res => {
      const account = res.body;
      if (account) {
        this.accountsService.updateBalance(account.id, (account.balance ?? 0) + profit).subscribe();
      }
    });
  }

  private updatesalesincome(invoice: any, profit: number): void {
    this.salesInvoiceLinesService.setprofit(profit);

    const transaction: NewTransactions = {
      id: null,
      accountId: 33,
      accountCode: '42',
      debit: profit,
      credit: 0,
      date: dayjs(),
      refDoc: invoice.code,
      refId: invoice.id,
      subId: this.salesInvoiceLinesService.getSubId(),
      source: 'Sales',
      lmu: invoice.lmu,
      lmd: dayjs(),
    };
    this.transactionsService.create(transaction).subscribe();

    this.accountsService.find(33).subscribe(res => {
      const account = res.body;
      if (account) {
        this.accountsService.updateBalance(account.id, (account.balance ?? 0) + profit).subscribe();
      }
    });
  }

  private addtrasction(invoice: any, total: number): void {
    if (invoice.customerid) {
      this.customerService.find(invoice.customerid).subscribe(res => {
        const customer = res.body;
        if (customer) {
          this.salesinvoiceService.fetchReceiptAccountId(customer.fullname || '').subscribe(resAcc => {
            const accounts = resAcc.body;
            if (accounts && accounts.length > 0) {
              const account = accounts[0];
              const transaction: NewTransactions = {
                id: null,
                accountId: account.id,
                accountCode: account.code,
                debit: total,
                credit: 0,
                date: dayjs(),
                refDoc: invoice.code,
                refId: invoice.id,
                subId: this.salesInvoiceLinesService.getSubId(),
                source: 'Sales',
                lmu: invoice.lmu,
                lmd: dayjs(),
              };
              this.transactionsService.create(transaction).subscribe();

              this.accountsService.updateBalance(account.id, (account.balance ?? 0) + total).subscribe();
            }
          });
        }
      });
    }
  }

  private inventorytransac(invoice: any): void {
    this.salesInvoiceLinesDummyArray.controls.forEach(control => {
      const line = (control as FormGroup).getRawValue();
      const lineCost = (line.itemcost || 0) * (line.quantity || 0);

      if (lineCost > 0) {
        const transaction: NewTransactions = {
          id: null,
          accountId: 125, // Credit Stock
          accountCode: '125',
          debit: 0,
          credit: lineCost,
          date: dayjs(),
          refDoc: invoice.code,
          refId: invoice.id,
          subId: this.salesInvoiceLinesService.getSubId(),
          source: 'Inventory',
          lmu: invoice.lmu,
          lmd: dayjs(),
        };
        this.transactionsService.create(transaction).subscribe();

        // Add matching Debit to CLSSTK to balance the inventory credit
        const debitTransaction: NewTransactions = {
          ...transaction,
          accountId: 125,
          accountCode: 'CLSSTK',
          debit: lineCost,
          credit: 0,
        };
        this.transactionsService.create(debitTransaction).subscribe();
      }
    });
  }
}
