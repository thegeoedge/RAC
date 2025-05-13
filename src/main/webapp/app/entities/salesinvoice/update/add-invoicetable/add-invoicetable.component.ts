import { CommonModule } from '@angular/common';
import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { FormArray, FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';
import { combineLatest, Subscription } from 'rxjs';
import { MessageCommunicationService } from 'app/core/util/message.communication.service';
@Component({
  selector: 'jhi-add-invoicetable',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-invoicetable.component.html',
  styleUrls: ['./add-invoicetable.component.scss'],
})
export class AddInvoicetableComponent implements OnInit, OnDestroy {
  form!: FormGroup;
  salesInvoiceLines!: FormArray;
  salesInvoiceCommonLines!: FormArray;
  private subscription!: Subscription;
  messagenotify = inject(MessageCommunicationService);
  constructor(
    private salesInvoiceService: SalesInvoiceLinesService,
    private fb: FormBuilder,
  ) {}
  showInvoiceLines = false;

  toggleInvoiceLines() {
    this.showInvoiceLines = !this.showInvoiceLines;
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      salesInvoiceLines: this.fb.array([]), // This will hold your invoice lines
      salesInvoiceCommonLines: this.fb.array([]), // This will hold your common lines
    });

    this.salesInvoiceLines = this.form.get('salesInvoiceLines') as FormArray;
    this.salesInvoiceCommonLines = this.form.get('salesInvoiceCommonLines') as FormArray;

    // Subscription for salesInvoiceLines
    this.subscription = this.salesInvoiceService.salesInvoiceLines$.subscribe((invoiceLines: FormArray) => {
      invoiceLines.controls.forEach(line => {
        const lineCode = line.get('itemcode')?.value || line.get('name')?.value || line.get('id')?.value;
        const description = line.get('description')?.value || line.get('servicename')?.value || line.get('serviceName')?.value;
        const itemprice = line.get('itemprice')?.value || line.get('value')?.value || line.get('sellingprice')?.value;

        // Check if the lineId already exists in salesInvoiceLines to avoid duplicates
        const existingIndex = this.salesInvoiceLines.controls.findIndex(control => {
          const group = control as FormGroup;
          return group.get('lineId')?.value === lineCode;
        });

        // Only add the line if it doesn't already exist
        if (existingIndex === -1) {
          // Create the group for each invoice line
          const group = this.fb.group({
            lineId: [lineCode],
            description: [description],
            itemprice: [itemprice],
          });

          this.salesInvoiceLines.push(group);
        }
      });
    });

    // Subscription for salesInvoiceCommonLines (if needed, you can also prevent duplicates here)
    // If there's any functionality here, you can repeat a similar approach as above
  }

  removeLine(index: number): void {
    const lineId =
      this.salesInvoiceLines.at(index)?.get('lineId')?.value ||
      this.salesInvoiceLines.at(index)?.get('description')?.value ||
      this.salesInvoiceLines.at(index)?.get('name')?.value;
    this.salesInvoiceLines.removeAt(index);
    this.messagenotify.pushNotification('DELETE_ITEM', lineId);
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
