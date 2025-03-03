import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SalesInvoiceDummyService } from '../sales-invoice-dummy/service/sales-invoice-dummy.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'jhi-printinvoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './printinvoice.component.html',
  styleUrl: './printinvoice.component.scss',
})
export class PrintinvoiceComponent implements OnInit {
  protected salesInvoiceDummyService = inject(SalesInvoiceDummyService);
  invoice: any;
  constructor(private route: ActivatedRoute) {}

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

  // Function to fetch invoice data
  getSalesInvoice(id: number): void {
    this.salesInvoiceDummyService.find(id).subscribe({
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
    this.salesInvoiceDummyService.fetchInvoiceLinesdummies(id).subscribe({
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

  getSalesServicelines(id: number): void {
    this.salesInvoiceDummyService.fetchServicedummy(id).subscribe({
      next: response => {
        console.log('Sales Service lines Data:', response.body);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }
  getSalesSercolines(id: number): void {
    this.salesInvoiceDummyService.fetchServiceCommondummy(id).subscribe({
      next: response => {
        console.log('Sales Service common Data:', response.body);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }
}
