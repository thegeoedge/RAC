import { Component, inject, NgModule, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutocareappointment } from '../autocareappointment.model';
import { AutocareappointmentService } from '../service/autocareappointment.service';
import { AutocareappointmentFormService, AutocareappointmentFormGroup } from './autocareappointment-form.service';
import { AutocareappointmenttypeService } from 'app/entities/autocareappointmenttype/service/autocareappointmenttype.service';
import { IAutocareappointmenttype } from 'app/entities/autocareappointmenttype/autocareappointmenttype.model';
import dayjs from 'dayjs/esm';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { AutocarehoistService } from 'app/entities/autocarehoist/service/autocarehoist.service';
import { HoisttypeService } from 'app/entities/hoisttype/service/hoisttype.service';
import { AutocaretimetableService } from 'app/entities/autocaretimetable/service/autocaretimetable.service';
import { NgbAccordionCollapse, NgbAccordionHeader, NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import utc from 'dayjs/esm/plugin/utc';

import timezone from 'dayjs/esm/plugin/timezone';
dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.extend(utc);

@Component({
  standalone: true,
  selector: 'jhi-autocareappointment-update',
  templateUrl: './autocareappointment-update.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, NgbAccordionHeader, NgbAccordionCollapse],
  styles: [
    `
      .form-group {
        display: flex;
        align-items: center;
        gap: 1rem; /* Adjust spacing between label and input */
      }

      label {
        margin-bottom: 0; /* Remove bottom margin to align labels vertically */
      }

      input[type='checkbox'] {
        margin: 0; /* Remove default margin from checkboxes */
      }

      .mb-3 {
        margin-bottom: 1rem; /* Adjust spacing between groups */
      }

      table {
        border-collapse: collapse;
        width: 100%;
      }

      th,
      td {
        padding: 10px;
        border: 1px solid #ddd;
        text-align: center;
      }
    `,
  ],
})
export class AutocareappointmentUpdateComponent implements OnInit {
  hideIsNoAnswer: boolean = true;
  isSaving = false;
  selectedTab: number = 0;
  autocareappointment: IAutocareappointment | null = null;
  autocareappointmenttypes: IAutocareappointmenttype[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  selectedTime: string | null = null;
  selectedHoist: number | null = null;
  hoistData: any[] = [];
  hoistTypeData: any[] = [];
  timetableData: any[] = [];

  hoists: { id: number; name: string; times: string[] }[] = [];
  constructor(private fb: FormBuilder) {
    this.editForm = this.autocareappointmentFormService.createAutocareappointmentFormGroup();
  }
  protected autocareappointmentService = inject(AutocareappointmentService);
  protected autocareappointmentFormService = inject(AutocareappointmentFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected autocareappointmenttypeService = inject(AutocareappointmenttypeService);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocarehoistService = inject(AutocarehoistService);
  protected autocarehoisttypeService = inject(HoisttypeService);
  protected autocaretimetableService = inject(AutocaretimetableService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocareappointmentFormGroup = this.autocareappointmentFormService.createAutocareappointmentFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocareappointment }) => {
      this.autocareappointment = autocareappointment;
      if (autocareappointment) {
        this.updateForm(autocareappointment);
      }
      this.loadDataFromOtherEntities();
      // this.loadCustomerDetails();
      this.loadHoistAppointmentTime();
    });
  }
  openIndex: number | null = null;

  openAccordion(index: number) {
    this.openIndex = index;
  }
  closeAccordion(index: number) {
    setTimeout(() => {
      if (this.openIndex === index) {
        this.openIndex = null;
      }
    }, 500);
  }
  getHoistsByType(hoistTypeId: number): any[] {
    return this.hoistData.filter(hoist => hoist.hoisttypeid === hoistTypeId);
  }

  getHoistTypeName(hoisttypeid: number): string | undefined {
    const hoistType = this.hoistTypeData.find(ht => ht.id === hoisttypeid);
    return hoistType ? hoistType.hoisttype : undefined;
  }

  // selectTime(timetable: any): void {
  //   const appointmentDate = this.editForm.get('appointmentdate')?.value;
  //   console.log('new appointment date : ', appointmentDate);
  //   const dd = dayjs('1900-01-01 08:00:00', 'YYYY-MM-DD HH:mm:ss');
  //   console.log('check static value', dd);
  //   if (appointmentDate && timetable.hoisttime) {
  //     const appointmentDateTime = dayjs(appointmentDate)
  //       .hour(dayjs(timetable.hoisttime).hour())
  //       .minute(dayjs(timetable.hoisttime).minute())
  //       .second(0)
  //       .format('YYYY-MM-DDTHH:mm');

  //     // const appointmentDateTime = dayjs(appointmentDate)

  //     console.log('new appointment time : ', appointmentDateTime);

  //     this.editForm.get('appointmenttime')?.patchValue(appointmentDateTime);

  //     this.selectedTime = timetable.hoisttime;
  //     this.selectedHoist = timetable.hoistid;
  //   }
  // }

  selectTime(timetable: any, hoistId: number): void {
    console.log('send timetableeee : ', timetable);
    console.log('send timetableeee : ', timetable.hoisttime);
    const appointmentDate = this.editForm.get('appointmentdate')?.value;
    console.log('new appointment date : ', appointmentDate);

    if (appointmentDate && timetable.hoisttime) {
      // Convert hoisttime to UTC and combine with the appointment date
      const hoistTimeInUTC = dayjs(timetable.hoisttime, 'HH:mm:ss').utc();
      console.log('timatable.hoisttime before attachment : ', hoistTimeInUTC);

      // Convert the appointment date to Asia/Colombo timezone
      const appointmentDateTime = dayjs(appointmentDate)
        .hour(hoistTimeInUTC.hour())
        .minute(hoistTimeInUTC.minute())
        .second(0)
        .tz('Asia/Colombo', true) // Convert the time to the Asia/Colombo timezone
        .format('YYYY-MM-DDTHH:mm');

      console.log('new appointment time (Asia/Colombo) : ', appointmentDateTime);

      // Update the appointmenttime in the form
      this.editForm.get('appointmenttime')?.patchValue(timetable.hoisttime);
      const appointmentDat = this.editForm.get('appointmenttime')?.value;
      console.log('new appointment timeeeeeeeeee : ', appointmentDat);
      console.log('hoistid :', hoistId);
      this.editForm.get('hoistid')?.patchValue(hoistId);

      // Optionally store the selected time and hoist for reference
      this.selectedTime = timetable.hoisttime;
      this.selectedHoist = timetable.hoistid;
      this.loadHoistAppointmentTime();
    } else {
      alert('Please select an Appointment Date');
    }
  }

  loadDataFromOtherEntities() {
    this.autocareappointmenttypeService.query().subscribe((res: any) => {
      this.autocareappointmenttypes = res.body;
    });

    // for (let i = 0; i < 31; i++) {
    //   this.customervehicleService.query({ size: 2000, page: i }).subscribe((res: any) => {
    //     if (res.body) {
    //       this.customervehicles = this.customervehicles.concat(res.body); // Append results to the array
    //     }
    //   });
    // }
  }

  filteredVehicles: ICustomervehicle[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 2) {
      // Use the new service method to fetch matching results
      this.customervehicleService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = response.body || [];
        console.log('Filtered vehicles:', this.filteredVehicles);
        // Update the vehicle ID field if there's any vehicle found
        if (this.filteredVehicles.length > 0) {
          this.editForm.get('vehicleid')?.patchValue(this.filteredVehicles[0].id); // Patching vehicle ID
        }
      });
    } else {
      // Clear the suggestions if input is too short
      this.filteredVehicles = [];
    }
  }

  searchedCustomer: ICustomer | null = null;

  onVehicleSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedVehicleNumber = input.value;

    // Find the selected vehicle from the filtered list
    const selectedVehicle = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);

    if (selectedVehicle && selectedVehicle.customerid != null) {
      // Fetch the customer details if the vehicle has a valid customer ID
      this.customerService.find(selectedVehicle.customerid).subscribe(res => {
        this.searchedCustomer = res.body;
        console.log(this.searchedCustomer?.fullname);
        const storedUserId = localStorage.getItem('userId');
        const userIdNumber = parseInt(storedUserId!, 10); // Parse userId to number
        this.editForm.get('lmu')?.patchValue(userIdNumber);
        // Patch the form with the customer's details
        this.editForm.get('customername')?.patchValue(this.searchedCustomer?.fullname);
        this.editForm.get('contactnumber')?.patchValue(this.searchedCustomer?.residencephone);
        this.editForm.get('customerid')?.patchValue(this.searchedCustomer?.id);
      });
    } else {
      console.error('Invalid customer ID or vehicle not found:', selectedVehicle);
    }
  }

  onclickconfirmed(): void {
    console.log('onclickconfirmed method called'); // Log function call
    const isConfirmed = this.editForm.get('isconformed')?.value; // Access checkbox value
    console.log('isConfirmed value:', isConfirmed); // Log current value of isconformed

    if (isConfirmed) {
      const storedUserId = localStorage.getItem('userId'); // Retrieve userId from localStorage
      console.log('storedUserId value:', storedUserId); // Log userId value

      if (storedUserId) {
        const userIdNumber = parseInt(storedUserId, 10); // Parse userId to number
        this.editForm.get('conformedby')?.patchValue(userIdNumber); // Update form control
        console.log('Confirmed by user ID:', userIdNumber); // Log confirmation
      } else {
        console.error('No user ID found in local storage');
      }
    } else {
      console.log('isconformed is not ticked');
      this.editForm.get('conformedby')?.patchValue(null); // Clear the conformedby field if unchecked
    }
  }
  filteredTimeSlots: any[] = []; // Add this property to store time slots
  allAppointments: any[] = [];
  // Ensure dayjs is imported for date manipulation

  onDateChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    const appointmentDate = inputElement.value; // Get selected date

    console.log('Selected Date:', appointmentDate);
    const fulldate = appointmentDate + 'T00:00:00';
    console.log('Selected Date:', fulldate);

    const selectedDate = new Date(fulldate).toISOString(); // Convert to ISO 8601 format
    console.log('Selected Date (ISO Format):', selectedDate);

    // Query for appointments after the selected date
    this.autocareappointmentService
      .query({
        'appointmenttime.greaterThan': selectedDate, // Filter appointments where appointmentDate > fulldate
        // Sort results by id in descending order
      })
      .subscribe(response => {
        const appointments = response.body ? response.body || [] : [];

        // Format the appointment times to the same format as the filtered time slots
        const formattedAppointments = appointments.map(appointment => ({
          ...appointment,
          appointmenttime: dayjs(appointment.appointmenttime).format('YYYY-MM-DDTHH:mm'), // Convert to normal date-time format
        }));

        // Add formatted appointments to the allAppointments array
        this.allAppointments.push(...formattedAppointments);
        console.log('Appointments Added to Array:', this.allAppointments);

        if (!appointmentDate || !this.timetableData.length) {
          console.warn('No appointment date selected or timetable data is empty.');
          return;
        }

        // Generate time slots by combining the selected date with each hoist time
        this.filteredTimeSlots = this.timetableData
          .map(timetable => {
            if (!timetable.hoisttime || !timetable.hoisttypename) {
              console.warn('Missing hoist time or hoist type name in timetable:', timetable);
              return null;
            }

            // Convert hoist time to UTC first
            const hoistTimeInUTC = dayjs(timetable.hoisttime, 'HH:mm:ss').utc();

            // Merge with the selected date and convert to Asia/Colombo timezone
            const formattedDateTime = dayjs(appointmentDate)
              .hour(hoistTimeInUTC.hour())
              .minute(hoistTimeInUTC.minute())
              .second(0)
              .tz('Asia/Colombo', true) // Convert to Asia/Colombo timezone
              .format('YYYY-MM-DDTHH:mm');

            return {
              hoisttypename: timetable.hoisttypename,
              hoisttime: formattedDateTime,
              hoisttypeid: timetable.hoisttypeid,
              id: timetable.id,
            };
          })
          .filter(Boolean); // Remove null values if any hoist time or name was missing

        console.log('Generated Time Slots:', this.filteredTimeSlots);

        // Now, check for matches between filtered time slots and appointments
        this.filteredTimeSlots.forEach(filteredSlot => {
          console.log('Filtered Slot:', filteredSlot); // Debug log to check data
          this.allAppointments.forEach(appointment => {
            console.log('Appointment:', appointment); // Debug log to check data

            // Ensure appointment.hoistid and filteredSlot.id are comparable (same type)
            if (filteredSlot.id === appointment.hoistid) {
              console.log('Matching IDs:', filteredSlot.id, appointment.hoistid);

              // Check if appointmenttime and hoisttime are in the same format
              if (filteredSlot.hoisttime === appointment.appointmenttime) {
                console.log('Match Found:', {
                  filteredSlot,
                  appointment,
                });
              } else {
                console.log('Time mismatch:', filteredSlot.hoisttime, appointment.appointmenttime);
              }
            } else {
              console.log('ID mismatch:', filteredSlot.id, appointment.hoistid);
            }
          });
        });
      });
  }

  loadHoistAppointmentTime(): void {
    this.autocarehoisttypeService.query().subscribe(response => {
      this.hoistTypeData = response.body || [];
      console.log('Hoist Type : ', this.hoistTypeData);
    });
    this.autocarehoistService.query().subscribe(response => {
      this.hoistData = response.body || [];
      console.log('Hoists : ', this.hoistData);
    });
    this.autocaretimetableService.query({ size: 2000, page: 0 }).subscribe(response => {
      this.timetableData = response.body || [];

      console.log('Time slots : ', this.timetableData);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autocareappointment = this.autocareappointmentFormService.getAutocareappointment(this.editForm);
    if (autocareappointment.id !== null) {
      autocareappointment.lmd = dayjs();
      this.subscribeToSaveResponse(this.autocareappointmentService.update(autocareappointment));
    } else {
      autocareappointment.addeddate = dayjs();
      autocareappointment.lmd = dayjs();
      this.subscribeToSaveResponse(this.autocareappointmentService.create(autocareappointment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutocareappointment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autocareappointment: IAutocareappointment): void {
    this.autocareappointment = autocareappointment;
    this.autocareappointmentFormService.resetForm(this.editForm, autocareappointment);
  }
}
