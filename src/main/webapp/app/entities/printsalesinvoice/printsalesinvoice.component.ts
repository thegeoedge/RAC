import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SalesInvoiceDummyService } from '../sales-invoice-dummy/service/sales-invoice-dummy.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'jhi-printsalesinvoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './printsalesinvoice.component.html',
  styleUrl: './printsalesinvoice.component.scss',
})
export class PrintsalesinvoiceComponent implements OnInit {
  private route = inject(ActivatedRoute);
  protected salesInvoiceService = inject(SalesInvoiceDummyService);

  ngOnInit(): void {
    // Get 'id' from the query params
    this.route.queryParams.subscribe(params => {
      const id = params['id']; // Extract the ID
      console.log('ID from URL:', id);

      if (id) {
        this.getSalesInvoice(id); // Call the function to fetch data
        this.getSalesInvoicelines(id);
        this.getSalesServicelines(id);
        this.getSalesSercolines(id);
      }
    });
  }
  getSalesInvoice(id: number): void {
    this.salesInvoiceService.find(id).subscribe({
      next: response => {
        console.log('Sales Invoice Data:', response.body);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }
  //: any[] = []; // Initialize an array to store invoice lines

  getSalesInvoicelines(id: number): void {
    this.salesInvoiceService.fetchInvoiceLines(id).subscribe({
      next: response => {
        console.log('Sales Invoice lines Data:', response.body);

        // Ensure response.body is an array before assigning
        if (Array.isArray(response.body)) {
          this.invoiceItems = response.body; // Directly assign the response
        } else {
          console.error('Invalid data format: Expected an array', response.body);
          this.invoiceItems = []; // Reset to avoid issues
        }

        console.log('Updated Invoice Items:', this.invoiceItems);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }

  // Initialize as an empty array
  invoiceItems: any[] = [];
  invoiceChargeItems: any[] = [];
  invoicecommonChargeItems: any[] = [];

  getSalesServicelines(id: number): void {
    this.salesInvoiceService.fetchService(id).subscribe({
      next: response => {
        console.log('Sales Service lines Data:', response.body);
        if (Array.isArray(response.body)) {
          this.invoiceChargeItems = response.body; // Directly assign the response
        } else {
          console.error('Invalid data format: Expected an array', response.body);
          this.invoiceChargeItems = []; // Reset to avoid issues
        }
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }
  getSalesSercolines(id: number): void {
    this.salesInvoiceService.fetchServiceCommon(id).subscribe({
      next: response => {
        console.log('Sales Service common Data:', response.body);
        if (Array.isArray(response.body)) {
          this.invoicecommonChargeItems = response.body; // Directly assign the response
        } else {
          console.error('Invalid data format: Expected an array', response.body);
          this.invoicecommonChargeItems = []; // Reset to avoid issues
        }
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }

  getTotalAmount(): number {
    let total = 0;

    // Sum up invoiceItems line totals
    if (this.invoiceItems) {
      total += this.invoiceItems.reduce((sum, item) => sum + (item.linetotal || 0), 0);
    }

    // Sum up invoiceChargeItems service prices
    if (this.invoiceChargeItems) {
      total += this.invoiceChargeItems.reduce((sum, item) => sum + (item.servicePrice || 0), 0);
    }

    // Sum up invoicecommonChargeItems values
    if (this.invoicecommonChargeItems) {
      total += this.invoicecommonChargeItems.reduce((sum, item) => sum + (item.value || 0), 0);
    }

    return total;
  }
}
