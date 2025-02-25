import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SalesInvoiceDummyService } from '../sales-invoice-dummy/service/sales-invoice-dummy.service';

@Component({
  selector: 'jhi-printinvoice',
  standalone: true,
  imports: [],
  templateUrl: './printinvoice.component.html',
  styleUrl: './printinvoice.component.scss',
})
export class PrintinvoiceComponent implements OnInit {
  protected salesInvoiceDummyService = inject(SalesInvoiceDummyService);

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
  getSalesInvoicelines(id: number): void {
    this.salesInvoiceDummyService.fetchInvoiceLines(id).subscribe({
      next: response => {
        console.log('Sales Invoice lines Data:', response.body);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }
  getSalesServicelines(id: number): void {
    this.salesInvoiceDummyService.fetchService(id).subscribe({
      next: response => {
        console.log('Sales Service lines Data:', response.body);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }
  getSalesSercolines(id: number): void {
    this.salesInvoiceDummyService.fetchServiceCommon(id).subscribe({
      next: response => {
        console.log('Sales Service common Data:', response.body);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }
}
