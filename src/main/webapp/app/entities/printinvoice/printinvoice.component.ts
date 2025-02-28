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
  invoiceItems: any[] = [
    { itemName: 'Oil Filter', itemCost: 5, quantity: 2, sellingPrice: 15, lineTotal: 30 },
    { itemName: 'Brake Pads', itemCost: 10, quantity: 1, sellingPrice: 50, lineTotal: 40 },
    { itemName: 'Spark Plugs', itemCost: 2, quantity: 4, sellingPrice: 10, lineTotal: 32 },
    { itemName: 'Engine Oil', itemCost: 8, quantity: 2, sellingPrice: 25, lineTotal: 34 },
    { itemName: 'Air Filter', itemCost: 6, quantity: 1, sellingPrice: 20, lineTotal: 14 },
  ];
  getSalesInvoicelines(id: number): void {
    this.salesInvoiceDummyService.fetchInvoiceLinesdummies(id).subscribe({
      next: response => {
        console.log('Sales Invoice lines Data:', response.body);
        this.invoiceItems = response.body; // Assign response to invoiceItems
        console.log(this.invoiceItems);
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }

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
