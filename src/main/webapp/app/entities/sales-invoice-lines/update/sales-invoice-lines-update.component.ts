import { Component, OnInit, inject } from '@angular/core';
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
import dayjs from 'dayjs';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines-update',
  templateUrl: './sales-invoice-lines-update.component.html',
  styleUrls: ['./sales-invoice-lines.component.css'],
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SalesInvoiceLinesUpdateComponent implements OnInit {
  isSaving = false;
  salesInvoiceLines: ISalesInvoiceLines[] = []; // Now an array of sales invoice lines

  filteredItems: IInventory[][] = []; // Array of arrays to store filtered items for each row

  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected salesInvoiceLinesFormService = inject(SalesInvoiceLinesFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);

  // Use FormArray to handle multiple lines
  editForm: FormGroup = this.fb.group({
    salesInvoiceLines: this.fb.array([]), // Define a FormArray
  });

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesInvoiceLines }) => {
      // Ensure salesInvoiceLines is always an array
      this.salesInvoiceLines = Array.isArray(salesInvoiceLines) ? salesInvoiceLines : [salesInvoiceLines];

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

  // Add a new line to the form
  addSalesInvoiceLine(): void {
    this.salesInvoiceLinesArray.push(this.salesInvoiceLinesFormService.createSalesInvoiceLinesFormGroup());
  }

  // Remove a line from the form
  removeSalesInvoiceLine(index: number): void {
    this.salesInvoiceLinesArray.removeAt(index);
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

    // Assign invoiceid to all rows and ensure unique lineid
    salesInvoiceLines = salesInvoiceLines.map((line, index) => ({
      ...line,
      invoiceid: inid, // Assign the provided invoice ID
      lineid: line.lineid ?? index + 1, // Assign a unique `lineid` if it's missing
    }));

    console.log('Modified sales invoice lines:', salesInvoiceLines);

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
}
