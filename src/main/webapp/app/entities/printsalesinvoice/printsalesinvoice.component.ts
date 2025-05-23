import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';

import { SalesInvoiceDummyService } from '../sales-invoice-dummy/service/sales-invoice-dummy.service';
import { SalesInvoiceLinesService } from '../sales-invoice-lines/service/sales-invoice-lines.service';
import { SalesInvoiceServiceChargeLineService } from 'app/entities/sales-invoice-service-charge-line/service/sales-invoice-service-charge-line.service';
import { SaleInvoiceCommonServiceChargeService } from 'app/entities/sale-invoice-common-service-charge/service/sale-invoice-common-service-charge.service';

@Component({
  selector: 'jhi-printsalesinvoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './printsalesinvoice.component.html',
  styleUrl: './printsalesinvoice.component.scss',
})
export class PrintsalesinvoiceComponent implements OnInit {
  sidebarVisible: boolean = true;

  private route = inject(ActivatedRoute);
  private router = inject(Router);

  protected salesInvoiceService = inject(SalesInvoiceDummyService);
  salesinvoicelineService = inject(SalesInvoiceLinesService);
  salesinvoiceservicechargelineService = inject(SalesInvoiceServiceChargeLineService);
  salesinvoicecommonservicechargeService = inject(SaleInvoiceCommonServiceChargeService);

  invoiceItems: any[] = [];
  invoiceChargeItems: any[] = [];
  invoicecommonChargeItems: any[] = [];

  ngOnInit(): void {
    // Handle sidebar visibility based on route

    // Fetch data when 'id' exists in query params
    this.route.queryParams.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.getSalesInvoice(id);
        this.getSalesInvoicelines(id);
        this.getSalesServicelines(id);
        this.getSalesSercolines(id);
      }
    });
  }

  private shouldHideSidebar(url: string): boolean {
    return url.includes('/printsalesinvoice') || url.includes('/login');
  }

  private setSidebarVisibility(isVisible: boolean): void {
    const sidenav = document.querySelector('.sidebar') as HTMLElement;
    if (sidenav) {
      sidenav.classList.toggle('sidebar-hidden', !isVisible);
    }
  }
  get totalInvoiceRows() {
    return this.invoiceItems.length + this.invoiceChargeItems.length + this.invoicecommonChargeItems.length;
  }

  get blankRowsCount() {
    // Only add blanks if less than 20 rows total
    return this.totalInvoiceRows < 51 ? 51 - this.totalInvoiceRows : 0;
  }

  salesInvoice: any = null;

  getSalesInvoice(id: number): void {
    this.salesInvoiceService.find(id).subscribe({
      next: response => {
        console.log('Sales Invoice Data:', response.body);
        this.salesInvoice = response.body;
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
      },
    });
  }

  getSalesInvoicelines(id: number): void {
    this.salesinvoicelineService.fetchInvoiceLines(id).subscribe({
      next: response => {
        console.log('Sales Invoice lines Data:', response.body);
        this.invoiceItems = Array.isArray(response.body) ? response.body : [];
      },
      error: err => {
        console.error('Error fetching Sales Invoice lines:', err);
      },
    });
  }

  getSalesServicelines(id: number): void {
    this.salesinvoiceservicechargelineService.fetchService(id).subscribe({
      next: response => {
        console.log('Sales Service lines Data:', response.body);
        this.invoiceChargeItems = Array.isArray(response.body) ? response.body : [];
      },
      error: err => {
        console.error('Error fetching Sales Service lines:', err);
      },
    });
  }

  getSalesSercolines(id: number): void {
    this.salesinvoicecommonservicechargeService.fetchServiceCommon(id).subscribe({
      next: response => {
        console.log('Sales Service common Data:', response.body);
        this.invoicecommonChargeItems = Array.isArray(response.body) ? response.body : [];
      },
      error: err => {
        console.error('Error fetching Sales Common Service lines:', err);
      },
    });
  }

  getTotalAmount(): number {
    let total = 0;

    total += this.invoiceItems.reduce((sum, item) => sum + (item.linetotal || 0), 0);
    total += this.invoiceChargeItems.reduce((sum, item) => sum + (item.servicePrice || 0), 0);
    total += this.invoicecommonChargeItems.reduce((sum, item) => sum + (item.value || 0), 0);

    return total;
  }
}
