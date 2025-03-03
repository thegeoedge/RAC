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

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-update',
  templateUrl: './autocarejob-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, AutocarejobInstructionComponent],
})
export class AutocarejobUpdateComponent implements OnInit {
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

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();

  todayAppointments: IAutocareappointment[] = []; // New property for today's appointments

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      this.autocarejob = autocarejob;
      if (autocarejob) {
        this.updateForm(autocarejob);
      }
    });
    // Fetch all appointments
    this.loadAllAppointments();
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
    let page = 20;
    const size = 20;

    const fetchPage = () => {
      const yesterday = dayjs().subtract(1, 'day').startOf('day').format('YYYY-MM-DDTHH:mm:ss[Z]');
      console.log('Yesterday (ISO Format):', yesterday);

      this.autocareappointmentService.fetchDate(yesterday).subscribe({
        next: (res: HttpResponse<IAutocareappointment[]>) => {
          const appointments = res.body || [];
          allAppointments = [...allAppointments, ...appointments];
          console.log('appointmentsss', appointments);
          if (appointments.length === size) {
            page++; // Fetch the next page if current page is full
            fetchPage();
          } else {
            this.todayAppointments = this.filterTodayAppointments(allAppointments);
          }
        },
        error: () => {
          console.error('Failed to load appointments');
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
  loadAppointment(appointment: any): void {
    console.log('Loading appointment:', appointment);

    this.customerTel = appointment.contactnumber;
    this.vehicleNo = appointment.vehiclenumber;
    this.customername = appointment.customername;
    this.appointmentnum = appointment.appointmenttype;

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
    const today = dayjs().startOf('day'); // Get today's date at midnight
    console.log('Today:', today);

    let usedAppointments = JSON.parse(localStorage.getItem('usedAppointments') || '[]');

    return appointments.filter(appointment => {
      if (!appointment.conformdate || usedAppointments.includes(appointment.id)) {
        return false; // Exclude null dates and used appointments
      }
      const appointmentDate = dayjs(appointment.conformdate).startOf('day'); // Get appointment date at midnight
      return appointmentDate.isSame(today);
    });
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
      console.log('Selected Vehicle:', selectedVehicle);

      const jobTypeText = this.jobTypeMap[selectedVehicle.appointmenttype ?? 0];
      this.editForm.get('jobtypename')?.patchValue(jobTypeText);
      // Example: Populate other fields as needed
      this.editForm.get('customername')?.patchValue(selectedVehicle.customername || '');
      this.editForm.get('customertel')?.patchValue(selectedVehicle.contactnumber || '');
      this.editForm.get('jobtypeid')?.patchValue(selectedVehicle.appointmenttype ?? null);
      this.editForm.get('vehicleid')?.patchValue(selectedVehicle.id ?? null); // Set vehicleid
    } else {
      console.error('No matching vehicle found for:', selectedVehicleNumber);
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
