import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrintsalesinvoiceComponent } from './printsalesinvoice.component';

const routes: Routes = [
  { path: '', component: PrintsalesinvoiceComponent }, // This makes `/printinvoice` load this component
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PrintSalesInvoiceRoutingModule {}
