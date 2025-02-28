import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs'; // Import Dayjs
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IAutocarejob } from '../autocarejob.model';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';

import { AutocareappointmentService } from 'app/entities/autocareappointment/service/autocareappointment.service';
import { IAutocareappointment } from 'app/entities/autocareappointment/autocareappointment.model';
import { AutocarejobService } from '../service/autocarejob.service';
import { AutojobsinvoicelinebatchesService } from 'app/entities/autojobsinvoicelinebatches/service/autojobsinvoicelinebatches.service';
import { AutocarejobFormService, AutocarejobFormGroup } from '../update/autocarejob-form.service';
import { AutojobsinvoiceUpdateComponent } from 'app/entities/autojobsinvoice/update/autojobsinvoice-update.component';
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { AutojobsalesinvoiceservicechargelineComponent } from 'app/entities/autojobsalesinvoiceservicechargeline/list/autojobsalesinvoiceservicechargeline.component';
import { AutojobsalesinvoiceservicechargelineService } from 'app/entities/autojobsalesinvoiceservicechargeline/service/autojobsalesinvoiceservicechargeline.service';
import { AutojobsaleinvoicecommonservicechargeComponent } from 'app/entities/autojobsaleinvoicecommonservicecharge/list/autojobsaleinvoicecommonservicecharge.component';
import { AutojobsaleinvoicecommonservicechargeService } from 'app/entities/autojobsaleinvoicecommonservicecharge/service/autojobsaleinvoicecommonservicecharge.service';
import { ISalesinvoice } from 'app/entities/salesinvoice/salesinvoice.model';
import { SalesinvoiceService } from 'app/entities/salesinvoice/service/salesinvoice.service';
import { SalesInvoiceServiceChargeLineService } from 'app/entities/sales-invoice-service-charge-line/service/sales-invoice-service-charge-line.service';
import { SaleInvoiceCommonServiceChargeService } from 'app/entities/sale-invoice-common-service-charge/service/sale-invoice-common-service-charge.service';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-itemissue',
  templateUrl: './autocarejob-itemissue.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, AutojobsinvoiceUpdateComponent],
})
export class AutocarejobitemissueComponent implements OnInit {
  isSaving = false;
  autocarejob: IAutocarejob | null = null;
  salesinvoice: ISalesinvoice | null = null;
  autocarejobsinvoices: IAutojobsinvoice[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  autocareappointments: IAutocareappointment[] = [];
  jobinvoicelinebatches = inject(AutojobsinvoicelinebatchesService);
  protected autocarejobService = inject(AutocarejobService);
  protected autocarejobFormService = inject(AutocarejobFormService);
  autojobsinvoiceService = inject(AutojobsinvoiceService);
  autojobsinvoicelinesService = inject(AutojobsinvoicelinesService);
  autojobsservicelinesService = inject(AutojobsalesinvoiceservicechargelineService);
  autojobsalescommonService = inject(AutojobsaleinvoicecommonservicechargeService);
  salesinvoiceService = inject(SalesinvoiceService);
  salesinvoiceservicechargelineService = inject(SalesInvoiceServiceChargeLineService);
  salesinvoicecommonservicechargeService = inject(SaleInvoiceCommonServiceChargeService);
  salesinvoicelineService = inject(SalesInvoiceLinesService);
  protected activatedRoute = inject(ActivatedRoute);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocareappointmentService = inject(AutocareappointmentService);
  issuedItems: any[] = []; // Items that are issued
  availableItems: any[] = [];

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      this.autocarejob = autocarejob;

      if (autocarejob) {
        console.log(this.autocarejob?.customername);
        this.updateForm(autocarejob);
      }
      this.fetchhistory();
    });
  }

  previousState(): void {
    window.history.back();
  }

  autojobsInvoicesMap: {
    [key: number]: {
      invoice: IAutojobsinvoice;
      invoiceLines: any[];
    };
  } = {};

  get allInvoiceLines(): any[] {
    return Object.values(this.autojobsInvoicesMap)
      .flatMap(invoice => invoice.invoiceLines)
      .filter(line => !this.issuedItems.some(issued => issued.id === line.id));
  }

  fetchhistory(): void {
    this.autojobsinvoiceService.query({ 'jobid.equals': this.autocarejob?.id }).subscribe((res: HttpResponse<IAutojobsinvoice[]>) => {
      if (res.body && res.body.length > 0) {
        res.body.forEach(invoice => {
          const invoiceId = invoice.id!;
          this.autojobsInvoicesMap[invoiceId] = {
            invoice,
            invoiceLines: [],
          };

          // Fetch Invoice Lines
          this.autojobsinvoicelinesService.query({ 'invocieid.equals': invoiceId }).subscribe((linesRes: HttpResponse<any[]>) => {
            this.autojobsInvoicesMap[invoiceId].invoiceLines = linesRes.body || [];
          });
        });
      }
    });
  }

  //issue an item
  issueItem(issuedItem: any): void {
    if (!issuedItem) {
      console.warn('Invalid item selection.');
      return;
    }

    const nextLineId = this.itemsArray.length > 0 ? Math.max(...this.itemsArray.map(item => item.lineid), 0) + 1 : 1;
    const nextbatchlineid = this.itemsArray.length > 0 ? Math.max(...this.itemsArray.map(item => item.batchlineid), 0) + 1 : 1;
    const newItem = {
      id: 0,
      lineid: nextLineId,
      batchlineid: nextbatchlineid,
      itemid: issuedItem.id,
      code: issuedItem.code ?? '',
      batchid: 0,
      batchcode: '',
      txdate: dayjs().toISOString(),
      manufacturedate: dayjs().toISOString(),
      expireddate: dayjs().toISOString(),
      qty: 1,
      cost: 0,
      price: 0,
      notes: issuedItem.description ?? '',
      lmu: 0,
      lmd: dayjs().toISOString(),
      nbt: false,
      vat: false,
      discount: 0,
      total: issuedItem.lastsellingprice ?? 0,
      issued: false,
      issuedby: 0,
      issueddatetime: dayjs().toISOString(),
      addedbyid: 0,
      canceloptid: 0,
      cancelopt: '',
      cancelby: 0,
    };

    this.itemsArray.push(newItem);
    this.issuedItems.push({ ...issuedItem });

    console.log('Issued Items:', this.itemsArray);
  }

  //Cancel issued item
  cancelIssuedItem(item: any): void {
    this.issuedItems = this.issuedItems.filter(i => i.id !== item.id);
  }

  removeAvailableItem(line: any) {
    const index = this.allInvoiceLines.indexOf(line);
    if (index !== -1) {
      this.allInvoiceLines.splice(index, 1);
    }
  }

  itemsArray: Array<{
    id: number;
    lineid: number;
    batchlineid: number;
    itemid: number;
    code: string;
    batchid: number;
    batchcode: string;
    txdate: string;
    manufacturedate: string;
    expireddate: string;
    qty: number;
    cost: number;
    price: number;
    notes: string;
    lmu: number;
    lmd: string;
    nbt: boolean;
    vat: boolean;
    discount: number;
    total: number;
    issued: boolean;
    issuedby: number;
    issueddatetime: string;
    addedbyid: number;
    canceloptid: number;
    cancelopt: string;
    cancelby: number;
  }> = [];

  saveAll(): void {
    if (this.itemsArray.length === 0) {
      console.warn('No items to save.');
      return;
    }

    this.isSaving = true;

    this.itemsArray.forEach(item => {
      const itemWithDayjsDates = {
        ...item,
        txdate: dayjs(item.txdate).toISOString(),
        manufacturedate: dayjs(item.manufacturedate).toISOString(),
        expireddate: dayjs(item.expireddate).toISOString(),
        lmd: dayjs(item.lmd).toISOString(),
        issueddatetime: dayjs(item.issueddatetime).toISOString(),
      };

      // this.jobinvoicelinebatches.create(itemWithDayjsDates).subscribe({
      //   next: createResponse => {
      //     console.log('item created successfully:', createResponse);
      //   },
      //   error: createError => {
      //     console.error('Error creating service:', createError.body);
      //   }
      // });
    });
  }

  save(): void {
    this.isSaving = true;
    const autocarejob = this.autocarejobFormService.getAutocarejob(this.editForm);
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autocarejob: IAutocarejob): void {
    this.autocarejob = autocarejob;
    this.autocarejobFormService.resetForm(this.editForm, autocarejob);
  }
}
