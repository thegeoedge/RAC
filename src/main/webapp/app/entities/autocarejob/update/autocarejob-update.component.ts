import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs'; // Import Dayjs
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AutocarejobInstructionComponent } from './autocarejob-instruction.component';
import { IAutocarejob } from '../autocarejob.model';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { AutocareappointmentService } from 'app/entities/autocareappointment/service/autocareappointment.service';
import { IAutocareappointment } from 'app/entities/autocareappointment/autocareappointment.model';
import { AutocarejobService } from '../service/autocarejob.service';
import { AutocarejobFormService, AutocarejobFormGroup } from './autocarejob-form.service';
import { HttpClient } from '@angular/common/http';
import { AutoCareVehicleService } from 'app/entities/auto-care-vehicle/service/auto-care-vehicle.service';
@Component({
  standalone: true,
  selector: 'jhi-autocarejob-update',
  templateUrl: './autocarejob-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, AutocarejobInstructionComponent],
})
export class AutocarejobUpdateComponent implements OnInit {
  [x: string]: any;
  isSaving = false;
  autocarejob: IAutocarejob | null = null;
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  autocareappointments: IAutocareappointment[] = [];
  @ViewChild(AutocarejobInstructionComponent) autocarejobinstructionComponent!: AutocarejobInstructionComponent;
  protected autocarejobService = inject(AutocarejobService);
  protected autocarejobFormService = inject(AutocarejobFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocareappointmentService = inject(AutocareappointmentService);
  vehicesearch = inject(AutoCareVehicleService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();

  todayAppointments: IAutocareappointment[] = []; // New property for today's appointments
  storedUserIdNumber: number = 0;
  jobopenby: number = 0;
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      const storedUserId = localStorage.getItem('userId'); // Retrieve userId from localStorage
      console.log('storedUserId value:', storedUserId);
      this.jobopenby = Number(storedUserId);
      console.log(this.storedUserIdNumber);
      this.autocarejob = autocarejob;
      if (autocarejob) {
        this.updateForm(autocarejob);
      }
    });
    // Fetch all appointments
    this.loadAllAppointments();
    // this.loadAppointments();
  }

  private getNextJobNumber(): number {
    const today = dayjs().format('YYYY-MM-DD');
    const storedDate = localStorage.getItem('jobNumberDate');
    let jobNumber = 1;

    if (storedDate === today) {
      // If the date is the same, increment the job number
      jobNumber = parseInt(localStorage.getItem('jobNumber') || '1', 10) + 1;
    } else {
      // If the date is different, reset the job number to 1
      localStorage.setItem('jobNumberDate', today);
    }

    // Update the job number in local storage
    localStorage.setItem('jobNumber', jobNumber.toString());

    return jobNumber;
  }

  loadAllAppointments(): void {
    let allAppointments: IAutocareappointment[] = [];
    const size = 20;

    const fetchPage = () => {
      const yesterday = dayjs().subtract(1, 'day').hour(23).minute(59).second(0).format('YYYY-MM-DDTHH:mm:ss[Z]');

      console.log('Yesterday (ISO Format):', yesterday);

      this.autocareappointmentService.fetchDate(yesterday).subscribe({
        next: (res: HttpResponse<IAutocareappointment[]>) => {
          const appointments = res.body || [];

          if (appointments.length > 0) {
            allAppointments = [...allAppointments, ...appointments]; // Append new appointments
            console.log('Fetched Appointments:', appointments);
            console.log('All Appointments:', allAppointments);

            this.todayAppointments = this.filterTodayAppointments(allAppointments);
            console.log('Filtered Today Appointments:', this.todayAppointments);
          } else {
            console.warn('No new appointments found.');
          }
        },
        error: err => {
          console.error('Failed to load appointments', err);
        },
      });
    };

    fetchPage();
  }

  jobType: string | null = null;
  customername: string | null = null;
  appointmentnum: number | null = null;
  vehicleNo: string | null = null;
  customerTel: string | null = null;
  customerid: number = 0;
  vehicleid: number = 0;
  jobtypeid: number = 0;
  vehicletypeid: number = 0;

  loadAppointment(appointment: any): void {
    console.log('Loading appointment:', appointment);

    this.customerTel = appointment.contactnumber;
    this.vehicleNo = appointment.vehiclenumber;
    this.customername = appointment.customername;
    this.appointmentnum = appointment.appointmenttype;
    this.vehicleid = appointment.vehicleid;
    this.jobtypeid = appointment.appointmenttype;

    this.customerid = appointment?.customerid || 0;
    console.log('appointmemntsssssssssss', appointment.customerid);
    this.editForm.get('customerid')?.patchValue(appointment.customerid);
    const apiParams = {
      'vehiclenumber.equals': appointment.vehiclenumber,
      page: 0,
      size: 20,
    };

    this.customervehicleService.query(apiParams).subscribe(
      response => {
        if (response.body && response.body.length > 0) {
          console.log('Response:', response.body[0].typeid); // Logs the first item
          this.vehicletypeid = response.body[0]?.typeid || 0;
        } else {
          console.log('No data found.');
        }
      },
      error => {
        console.error('Error:', error);
      },
    );

    if (this.appointmentnum !== null) {
      const jobType = this.jobTypeMap[this.appointmentnum];
      if (jobType) {
        console.log('Appointment type is:', jobType);
        this.jobType = jobType;
      } else {
        console.log('Unknown appointment type');
      }
    } else {
      console.log('Appointment type is not defined');
    }

    // **Save used appointment in localStorage**
    let usedAppointments = JSON.parse(localStorage.getItem('usedAppointments') || '[]');
    usedAppointments.push(appointment.id);
    localStorage.setItem('usedAppointments', JSON.stringify(usedAppointments));

    // **Remove from local list**
    this.todayAppointments = this.todayAppointments.filter(a => a.id !== appointment.id);
  }

  jobTypeMap: { [key: number]: string } = {
    1: 'Full Service and Other Services',
    2: 'Detailing services',
    3: 'Performance Care',
    4: 'Other',
  };
  filterTodayAppointments(appointments: IAutocareappointment[]): IAutocareappointment[] {
    console.log('Returning all appointments:', appointments);
    return appointments; // No filtering, return all appointments as they are
  }

  previousState(): void {
    window.history.back();
  }

  filteredVehicles: IAutocareappointment[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 2) {
      // Use the new service method to fetch matching results
      this.autocareappointmentService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = response.body || [];
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

    const selectedVehicle = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);

    if (selectedVehicle) {
      console.log('Selected Vehicle:', selectedVehicle.vehiclenumber);

      // Make API call to get full vehicle details (with ID)
      this.vehicesearch.query({ 'vehicleNumber.equals': selectedVehicle.vehiclenumber }).subscribe(
        response => {
          const responseVehicle = response.body?.[0]; // Assuming the API returns an array

          if (responseVehicle) {
            const jobTypeText = this.jobTypeMap[selectedVehicle.appointmenttype ?? 0];
            this.editForm.get('customerid')?.patchValue(responseVehicle.customerId || selectedVehicle.customerid || 0);
            this.editForm.get('jobtypename')?.patchValue(jobTypeText);
            this.editForm.get('customername')?.patchValue(selectedVehicle.customername || '');
            this.editForm.get('customertel')?.patchValue(selectedVehicle.contactnumber || '');
            this.editForm.get('jobtypeid')?.patchValue(selectedVehicle.appointmenttype ?? null);
            this.editForm.get('vehicleid')?.patchValue(responseVehicle.id || selectedVehicle.vehicleid); // ✅ Use ID from API
            this.checkjobtypeid(jobTypeText || '');
            console.log('Updated Form:', this.editForm.value);
          } else {
            console.error('No vehicle found in API response');
          }
        },
        error => {
          console.error('Error fetching vehicle data from API:', error);
        },
      );
    } else {
      console.error('No matching vehicle found for:', selectedVehicleNumber);
    }
  }
  checkjobtypeid(jobtypetext: string | null | undefined): void {
    const jobtype = [
      { id: 1, name: 'Full Service and Other Services' },
      { id: 2, name: 'Detailing services' },
      { id: 3, name: 'Performance Care' },
      { id: 4, name: 'Other' },
    ];

    const foundJobType = jobtype.find(job => job.name === jobtypetext);
    if (foundJobType) {
      this.editForm.get('jobtypeid')?.patchValue(foundJobType.id);
      console.log('Job Type ID set to:', foundJobType.id);
    } else {
      console.error('Job type not found for:', jobtypetext);
    }
  }
  save(): void {
    this.isSaving = true;
    const autocarejob = this.autocarejobFormService.getAutocarejob(this.editForm);

    // Ensure jobtypeid and vehicleid are set
    autocarejob.jobtypeid = this.editForm.get('jobtypeid')?.value;
    autocarejob.vehicleid = this.editForm.get('vehicleid')?.value;

    if (autocarejob.id !== null) {
      this.subscribeToSaveResponse(this.autocarejobService.update(autocarejob));
    } else {
      // Create new job
      const jobNumber = this.getNextJobNumber(); // Get the next job number
      autocarejob.jobnumber = jobNumber; // Set the job number

      // Patch the job number into the form control
      this.editForm.get('jobnumber')?.patchValue(jobNumber);
      console.log('job hereeeeeeeeeeeeeeeee', autocarejob);
      this.checkjobtypeid(autocarejob.jobtypename);
      this.subscribeToSaveResponse(this.autocarejobService.create(autocarejob));
    }
  }
  invid: number = 0;
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutocarejob>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        this.onSaveSuccess(), console.log('Save Successfully:', response.body); // Log the response
        alert('Save Successful! Job ID: ' + response.body?.id); // Display an alert

        this.invid = response.body?.id ?? 0;
        console.log('Saved Job ID:', this.invid); // Log the response

        // Save as a number in local storage (stored as a string but retrieved as a number)
        localStorage.setItem('invid', JSON.stringify(this.invid)); // Use JSON.stringify to store properly

        // Retrieve from local storage and convert back to a number
        const storedInvid = JSON.parse(localStorage.getItem('invid') || '0'); // Use JSON.parse
        console.log('Retrieved from Local Storage (as number):', storedInvid);
      },
      error: error => {
        console.error('Save Failed:', error);
        this.onSaveError();
      },
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

  protected updateForm(autocarejob: IAutocarejob): void {
    this.autocarejob = autocarejob;
    this.autocarejobFormService.resetForm(this.editForm, autocarejob);
  }
}
