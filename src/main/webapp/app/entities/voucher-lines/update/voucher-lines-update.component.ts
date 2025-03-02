import { Component, OnInit, inject, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVoucherLines } from '../voucher-lines.model';
import { VoucherLinesService } from '../service/voucher-lines.service';
import { VoucherLinesFormGroup, VoucherLinesFormService } from './voucher-lines-form.service';
import { VoucherUpdateComponent } from 'app/entities/voucher/update/voucher-update.component';
import dayjs from 'dayjs/esm';
import voucherPaymentsDetailsRoute from 'app/entities/voucher-payments-details/voucher-payments-details.routes';
import { VoucherPaymentsDetailsService } from 'app/entities/voucher-payments-details/service/voucher-payments-details.service';

@Component({
  standalone: true,
  selector: 'jhi-voucher-lines-update',
  templateUrl: './voucher-lines-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VoucherLinesUpdateComponent implements OnInit {
  isSaving = false;
  voucherLines: IVoucherLines[] = [];
  @Input() accountId: number = 0;
  @Input() amount: number = 0;
  @Input() originalamount: number = 0;
  @Input() code: String = '';
  @Input() name: String = '';
  @Input() id: number = 0;
  protected voucherLinesService = inject(VoucherLinesService);
  protected voucherLinesFormService = inject(VoucherLinesFormService);
  payment = inject(VoucherPaymentsDetailsService);
  voucher = inject(VoucherUpdateComponent);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  get voucherlinesArray(): FormArray {
    return this.editForm.get('voucherlines') as FormArray;
  }
  editForm: FormGroup = new FormGroup({
    voucherlines: new FormArray([]),
  });
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherLines }) => {
      this.voucherLines = voucherLines;
      if (voucherLines) {
        this.updateForm(voucherLines);
      } else {
        this.addVoucherLine(); // Add default row when no data is available
      }
    });
  }
  paymentDetails = {
    id: 0,
    lineID: 0,
    paymentAmount: 0 as number, // type is explicitly defined
    totalVoucherAmount: 0 as number,
    checkqueAmount: 0 as number,
    checkqueNo: '',
    checkqueDate: dayjs('2025-02-27T16:44:59.467Z'), // Date as string (ISO format)
    checkqueExpireDate: dayjs('2025-02-27T16:44:59.467Z'), // Date as string (ISO format)
    bankName: '',
    bankID: 0,
    creditCardNo: '',
    creditCardAmount: 0 as number,
    reference: '',
    otherDetails: '',
    lmu: '0',
    lmd: dayjs('2025-02-27T16:44:59.467Z'), // Date as string (ISO format)
    termID: 0,
    termName: '',
    accountNo: 0,
    accountNumber: 0 as number,
    accountId: 0,
    accountCode: '',
    chequeStatusId: 0,
    isDeposit: false,
    depositedDate: dayjs('2025-02-27T16:44:59.467Z'), // Date as string (ISO format)
    companyBankId: 0,
    isBankReconciliation: false,
  };

  // You can now access the object and type-check it:

  // In VoucherLinesUpdateComponent

  // Function to calculate and update amountOwing
  calculateAmountOwing(i: number, line: FormGroup): void {
    const originalAmount = line.get('originalAmount')?.value || 0;
    const amountReceived = line.get('amountReceived')?.value || 0;

    const amountOwing = originalAmount - amountReceived;
    line.get('amountOwing')?.setValue(amountOwing); // Update the amountOwing form control
    console.log(this.voucherlinesArray);
  }
  onAmountReceivedChange(i: number): void {
    const line = this.voucherlinesArray.at(i) as FormGroup;
    const amountReceived = line.get('amountReceived')?.value;
    console.log(this.accountId);
    console.log('Line Index:', i);
    console.log('Amount Received:', amountReceived);
    console.log(this.voucherlinesArray);
    // Optionally, call your existing method to handle amount owing calculation
    this.voucherlinesArray.controls.forEach(line => {
      (line as FormGroup).get('accountId')?.setValue(this.accountId);
    });
    this.voucherlinesArray.controls.forEach(line => {
      (line as FormGroup).get('lineID')?.setValue(1);
    });
    this.voucherlinesArray.controls.forEach(line => {
      (line as FormGroup).get('grnCode')?.setValue(this.code);
    });
    this.voucherlinesArray.controls.forEach(line => {
      (line as FormGroup).get('id')?.setValue(this.id);
    });

    this.calculateAmountOwing(i, line);
  }

  // Function to get the current amountOwing value
  getAmountOwing(i: number, line: FormGroup): number {
    if (i === 0) {
      return this.originalamount - this.amount; // For the first row
    }
    return line.get('amountOwing')?.value || 0;
  }

  previousState(): void {
    window.history.back();
  }

  // Method to display account ID
  displayAccountId(accountId: number): void {
    if (this.voucherLines) {
      this.voucherLines.forEach(voucherLine => (voucherLine.accountId = accountId)); // Set the accountId on each VoucherLine model
    }
    if (this.voucherLines) {
      this.voucherLines.forEach(voucherLine => {
        console.log('Account ID in VoucherLinesComponent:', voucherLine.accountId); // Log the accountId from each VoucherLine model
      });
    }
  }

  addVoucherLine(): void {
    // Push a new form group into the voucherlinesArray
    const newDummy = this.voucherLinesFormService.createVoucherLinesFormGroup();
    this.voucherlinesArray.push(newDummy);

    // Log the updated voucherlinesArray
    console.log('Voucher lines after adding a new one:', this.voucherlinesArray.value);
  }

  removeServiceChargeDummy(index: number): void {
    this.voucherlinesArray.removeAt(index);
  }

  async save(): Promise<void> {
    this.isSaving = true;
    const id = await this.voucher.save();
    if (id !== null) {
      this.id = id;
    }
    const voucherLines = this.voucherLinesFormService.getVoucherLines(this.voucherlinesArray);
    console.log('lines workkeeeeeeeeeeeeed');
    console.log();
    console.log(this.voucherlinesArray);
    voucherLines.forEach(voucherLine => {
      const newVoucherLine = { ...voucherLine, id: id };
      console.log('loggggggggggggggggg', newVoucherLine);
      this.subscribeToSaveResponse(this.voucherLinesService.create(newVoucherLine));
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherLines>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        console.log('Save Responselinesssssssssss:', response); // ✅ Log the full response
        this.paymentDetails.id = this.id;
        this.paymentDetails.termName = 'cash';
        this.paymentDetails.termID = 1;
        this.paymentDetails.accountId = this.accountId;
        this.paymentDetails.lineID = 1;
        this.paymentDetails.paymentAmount = this.originalamount;
        this.paymentDetails.totalVoucherAmount = this.originalamount;
        console.log('payyyyyyyyyy', this.paymentDetails);
        this.voucherLinesService.createay(this.paymentDetails).subscribe({
          next: response => {
            console.log('Response received:', response); // Check the full response here
            // Handle success logic
            // this.onSaveSuccess();
          },
          error: error => {
            console.error('Error during save:', error); // Log the error
            this.onSaveError();
          },
        });
        // this.onSaveSuccess();
      },
      error: error => {
        console.error('Save Error:', error); // ✅ Log any error response
        this.onSaveError();
      },
    });
  }

  /// protected onSaveSuccess(): void {
  // this.previousState();
  //  }
  removeVoucherLine(index: number): void {
    this.voucherlinesArray.removeAt(index);
  }
  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(voucherLines: IVoucherLines[]): void {
    // Clear existing form data
    this.voucherlinesArray.clear(); // Clear previous form array contents

    if (voucherLines.length > 0) {
      // Populate form with existing data
      voucherLines.forEach(dummy => {
        this.voucherlinesArray.push(this.voucherLinesFormService.createVoucherLinesFormGroup(dummy));
      });
      voucherLines.forEach(line => {
        // Ensure each line is converted to a form group
        this.voucherlinesArray.push(this.voucherLinesFormService.createVoucherLinesFormGroup(line));
      });
    } else {
      // If no data is provided, add a default voucher line
      this.addVoucherLine();
    }
  }
}
