import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { ISalesInvoiceLinesDummy, NewSalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';
import { SalesInvoiceLinesDummyService } from '../service/sales-invoice-lines-dummy.service';
import { SalesInvoiceLinesDummyFormService } from './sales-invoice-lines-dummy-form.service';
import CommonModule from 'app/shared/shared.module';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines-dummy-update',
  templateUrl: './sales-invoice-lines-dummy-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, CommonModule],
})
export class SalesInvoiceLinesDummyUpdateComponent implements OnInit {
  isSaving = false;
  @Output() totalUpdated = new EventEmitter<number>();
  salesInvoiceLinesDummy: ISalesInvoiceLinesDummy[] = [];
  filteredItems: IInventory[][] = []; // Array of arrays to store filtered items for each row
  showCodeField: boolean = false;
  protected salesInvoiceLinesDummyService = inject(SalesInvoiceLinesDummyService);
  protected salesInvoiceLinesDummyFormService = inject(SalesInvoiceLinesDummyFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);

  // Define FormGroup with FormArray
  editForm: FormGroup = new FormGroup({
    salesInvoiceLinesDummy: new FormArray([]),
  });
  toggleShowCodeField(): void {
    this.showCodeField = !this.showCodeField;
  }
  get salesInvoiceLinesDummyArray(): FormArray {
    return this.editForm.get('salesInvoiceLinesDummy') as FormArray;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesInvoiceLinesDummy }) => {
      if (salesInvoiceLinesDummy && salesInvoiceLinesDummy.length > 0) {
        this.salesInvoiceLinesDummy = salesInvoiceLinesDummy;
        this.updateForm(salesInvoiceLinesDummy);
      } else {
        this.addInvoiceLine(); // Ensure at least one row is added
      }
    });
  }

  previousState(): void {
    window.history.back();
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

    this.salesInvoiceLinesDummyService
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
    salesInvoiceLineGroup.patchValue({ lineTotal: newSellingPrice });
    this.calculateTotal();
  }

  onItemCodeSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.code === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected item:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.salesInvoiceLinesDummyArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        itemCode: selectedItem.code,
        itemName: selectedItem.name,
        itemId: selectedItem.id, // Update other fields as necessary
        description: selectedItem.description,
        sellingPrice: selectedItem.lastsellingprice,

        // Add any other fields you want to update with the selected item's details
      });
      this.onQuantityChange(index);
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }
  calculateTotal(): void {
    const total = this.salesInvoiceLinesDummyArray.controls
      .map(control => control.get('lineTotal')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    console.log('Total Selling Price:', total);
    this.totalUpdated.emit(total);
  }
  save(inid: number): void {
    this.isSaving = true;
    const salesInvoiceLinesDummy = this.salesInvoiceLinesDummyArray.value.map((line: any, index: number) => ({
      ...line,
      invoiceId: inid, // Assign invoice ID
      lineId: line.lineid ?? index + 1, // Ensure unique line ID
    }));

    console.log('Modified sales invoice lines:', salesInvoiceLinesDummy);

    console.log('Service Charge Dummies before saving:', salesInvoiceLinesDummy);
    salesInvoiceLinesDummy.forEach((line: ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy) => {
      console.log(typeof line.lmd); // Will print the type of lmd (e.g., 'string', 'number', etc.)
    });
    salesInvoiceLinesDummy.forEach((line: ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy) => {
      line.lmd = null;
    });

    const requests: Observable<HttpResponse<ISalesInvoiceLinesDummy>>[] = salesInvoiceLinesDummy.map(
      (line: ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy) => {
        return line.id ? this.salesInvoiceLinesDummyService.update(line) : this.salesInvoiceLinesDummyService.create({ ...line, id: null });
      },
    );

    // Execute all requests sequentially
    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceLinesDummy[]>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Handle API error if needed
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(salesInvoiceLinesDummy: ISalesInvoiceLinesDummy[]): void {
    this.salesInvoiceLinesDummyArray.clear(); // Clear existing form data
    salesInvoiceLinesDummy.forEach(dummy => {
      this.salesInvoiceLinesDummyArray.push(this.salesInvoiceLinesDummyFormService.createSalesInvoiceLinesDummyFormGroup(dummy));
    });
    if (salesInvoiceLinesDummy.length > 0) {
      // Populate form with existing data
      salesInvoiceLinesDummy.forEach(line => {
        this.salesInvoiceLinesDummyArray.push(this.salesInvoiceLinesDummyFormService.createSalesInvoiceLinesDummyFormGroup(line));
      });
    } else {
      // Add an empty line when no data is available
      this.addInvoiceLine();
    }
  }

  addInvoiceLine(): void {
    const newRow = this.salesInvoiceLinesDummyFormService.createSalesInvoiceLinesDummyFormGroup();
    console.log('Adding row:', newRow.value);
    this.salesInvoiceLinesDummyArray.push(newRow);
  }

  removeInvoiceLine(index: number): void {
    this.salesInvoiceLinesDummyArray.removeAt(index);
  }
}
