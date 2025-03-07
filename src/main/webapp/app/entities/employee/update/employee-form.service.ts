import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployee, NewEmployee } from '../employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployee for edit and NewEmployeeFormGroupInput for create.
 */
type EmployeeFormGroupInput = IEmployee | PartialWithRequiredKeyOf<NewEmployee>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmployee | NewEmployee> = Omit<T, 'lmd' | 'dateJoined' | 'dateResigned'> & {
  lmd?: string | null;
  dateJoined?: string | null;
  dateResigned?: string | null;
};

type EmployeeFormRawValue = FormValueOf<IEmployee>;

type NewEmployeeFormRawValue = FormValueOf<NewEmployee>;

type EmployeeFormDefaults = Pick<NewEmployee, 'id' | 'lmd' | 'dateJoined' | 'dateResigned' | 'isActive'>;

type EmployeeFormGroupContent = {
  id: FormControl<EmployeeFormRawValue['id'] | NewEmployee['id']>;
  code: FormControl<EmployeeFormRawValue['code']>;
  fullName: FormControl<EmployeeFormRawValue['fullName']>;
  nameWithInitials: FormControl<EmployeeFormRawValue['nameWithInitials']>;
  surname: FormControl<EmployeeFormRawValue['surname']>;
  nicNumber: FormControl<EmployeeFormRawValue['nicNumber']>;
  nicIssueDate: FormControl<EmployeeFormRawValue['nicIssueDate']>;
  passportNo: FormControl<EmployeeFormRawValue['passportNo']>;
  passportIssueDate: FormControl<EmployeeFormRawValue['passportIssueDate']>;
  passportExpDate: FormControl<EmployeeFormRawValue['passportExpDate']>;
  dateOfBirth: FormControl<EmployeeFormRawValue['dateOfBirth']>;
  age: FormControl<EmployeeFormRawValue['age']>;
  bloodGroup: FormControl<EmployeeFormRawValue['bloodGroup']>;
  gender: FormControl<EmployeeFormRawValue['gender']>;
  phone2: FormControl<EmployeeFormRawValue['phone2']>;
  maritalStatus: FormControl<EmployeeFormRawValue['maritalStatus']>;
  marriedDate: FormControl<EmployeeFormRawValue['marriedDate']>;
  nationality: FormControl<EmployeeFormRawValue['nationality']>;
  permanentAddress: FormControl<EmployeeFormRawValue['permanentAddress']>;
  temporaryAddress: FormControl<EmployeeFormRawValue['temporaryAddress']>;
  home: FormControl<EmployeeFormRawValue['home']>;
  mobile: FormControl<EmployeeFormRawValue['mobile']>;
  fax: FormControl<EmployeeFormRawValue['fax']>;
  email: FormControl<EmployeeFormRawValue['email']>;
  emergencyContactPerson: FormControl<EmployeeFormRawValue['emergencyContactPerson']>;
  emergencyNumber: FormControl<EmployeeFormRawValue['emergencyNumber']>;
  city: FormControl<EmployeeFormRawValue['city']>;
  district: FormControl<EmployeeFormRawValue['district']>;
  province: FormControl<EmployeeFormRawValue['province']>;
  electorate: FormControl<EmployeeFormRawValue['electorate']>;
  mainRoad: FormControl<EmployeeFormRawValue['mainRoad']>;
  modeOfTransport: FormControl<EmployeeFormRawValue['modeOfTransport']>;
  distance: FormControl<EmployeeFormRawValue['distance']>;
  travelTime: FormControl<EmployeeFormRawValue['travelTime']>;
  username: FormControl<EmployeeFormRawValue['username']>;
  password: FormControl<EmployeeFormRawValue['password']>;
  departmentID: FormControl<EmployeeFormRawValue['departmentID']>;
  departmentCode: FormControl<EmployeeFormRawValue['departmentCode']>;
  empRegDate: FormControl<EmployeeFormRawValue['empRegDate']>;
  lmu: FormControl<EmployeeFormRawValue['lmu']>;
  lmd: FormControl<EmployeeFormRawValue['lmd']>;
  roleId: FormControl<EmployeeFormRawValue['roleId']>;
  roleName: FormControl<EmployeeFormRawValue['roleName']>;
  epf: FormControl<EmployeeFormRawValue['epf']>;
  etf: FormControl<EmployeeFormRawValue['etf']>;
  dateJoined: FormControl<EmployeeFormRawValue['dateJoined']>;
  dateResigned: FormControl<EmployeeFormRawValue['dateResigned']>;
  designation: FormControl<EmployeeFormRawValue['designation']>;
  jobStatusId: FormControl<EmployeeFormRawValue['jobStatusId']>;
  jobStatusName: FormControl<EmployeeFormRawValue['jobStatusName']>;
  imagePath: FormControl<EmployeeFormRawValue['imagePath']>;
  bankAccountNo: FormControl<EmployeeFormRawValue['bankAccountNo']>;
  bankId: FormControl<EmployeeFormRawValue['bankId']>;
  bankName: FormControl<EmployeeFormRawValue['bankName']>;
  branchId: FormControl<EmployeeFormRawValue['branchId']>;
  branchName: FormControl<EmployeeFormRawValue['branchName']>;
  salaryPaymentBasis: FormControl<EmployeeFormRawValue['salaryPaymentBasis']>;
  empStatus: FormControl<EmployeeFormRawValue['empStatus']>;
  religion: FormControl<EmployeeFormRawValue['religion']>;
  experience: FormControl<EmployeeFormRawValue['experience']>;
  qualifications: FormControl<EmployeeFormRawValue['qualifications']>;
  attendanceCode: FormControl<EmployeeFormRawValue['attendanceCode']>;
  isActive: FormControl<EmployeeFormRawValue['isActive']>;
};

export type EmployeeFormGroup = FormGroup<EmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeFormService {
  createEmployeeFormGroup(employee: EmployeeFormGroupInput = { id: null }): EmployeeFormGroup {
    const employeeRawValue = this.convertEmployeeToEmployeeRawValue({
      ...this.getFormDefaults(),
      ...employee,
    });
    return new FormGroup<EmployeeFormGroupContent>({
      id: new FormControl(
        { value: employeeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(employeeRawValue.code),
      fullName: new FormControl(employeeRawValue.fullName),
      nameWithInitials: new FormControl(employeeRawValue.nameWithInitials),
      surname: new FormControl(employeeRawValue.surname),
      nicNumber: new FormControl(employeeRawValue.nicNumber),
      nicIssueDate: new FormControl(employeeRawValue.nicIssueDate),
      passportNo: new FormControl(employeeRawValue.passportNo),
      passportIssueDate: new FormControl(employeeRawValue.passportIssueDate),
      passportExpDate: new FormControl(employeeRawValue.passportExpDate),
      dateOfBirth: new FormControl(employeeRawValue.dateOfBirth),
      age: new FormControl(employeeRawValue.age),
      bloodGroup: new FormControl(employeeRawValue.bloodGroup),
      gender: new FormControl(employeeRawValue.gender),
      phone2: new FormControl(employeeRawValue.phone2),
      maritalStatus: new FormControl(employeeRawValue.maritalStatus),
      marriedDate: new FormControl(employeeRawValue.marriedDate),
      nationality: new FormControl(employeeRawValue.nationality),
      permanentAddress: new FormControl(employeeRawValue.permanentAddress),
      temporaryAddress: new FormControl(employeeRawValue.temporaryAddress),
      home: new FormControl(employeeRawValue.home),
      mobile: new FormControl(employeeRawValue.mobile),
      fax: new FormControl(employeeRawValue.fax),
      email: new FormControl(employeeRawValue.email),
      emergencyContactPerson: new FormControl(employeeRawValue.emergencyContactPerson),
      emergencyNumber: new FormControl(employeeRawValue.emergencyNumber),
      city: new FormControl(employeeRawValue.city),
      district: new FormControl(employeeRawValue.district),
      province: new FormControl(employeeRawValue.province),
      electorate: new FormControl(employeeRawValue.electorate),
      mainRoad: new FormControl(employeeRawValue.mainRoad),
      modeOfTransport: new FormControl(employeeRawValue.modeOfTransport),
      distance: new FormControl(employeeRawValue.distance),
      travelTime: new FormControl(employeeRawValue.travelTime),
      username: new FormControl(employeeRawValue.username),
      password: new FormControl(employeeRawValue.password),
      departmentID: new FormControl(employeeRawValue.departmentID),
      departmentCode: new FormControl(employeeRawValue.departmentCode),
      empRegDate: new FormControl(employeeRawValue.empRegDate),
      lmu: new FormControl(employeeRawValue.lmu),
      lmd: new FormControl(employeeRawValue.lmd),
      roleId: new FormControl(employeeRawValue.roleId),
      roleName: new FormControl(employeeRawValue.roleName),
      epf: new FormControl(employeeRawValue.epf),
      etf: new FormControl(employeeRawValue.etf),
      dateJoined: new FormControl(employeeRawValue.dateJoined),
      dateResigned: new FormControl(employeeRawValue.dateResigned),
      designation: new FormControl(employeeRawValue.designation),
      jobStatusId: new FormControl(employeeRawValue.jobStatusId),
      jobStatusName: new FormControl(employeeRawValue.jobStatusName),
      imagePath: new FormControl(employeeRawValue.imagePath),
      bankAccountNo: new FormControl(employeeRawValue.bankAccountNo),
      bankId: new FormControl(employeeRawValue.bankId),
      bankName: new FormControl(employeeRawValue.bankName),
      branchId: new FormControl(employeeRawValue.branchId),
      branchName: new FormControl(employeeRawValue.branchName),
      salaryPaymentBasis: new FormControl(employeeRawValue.salaryPaymentBasis),
      empStatus: new FormControl(employeeRawValue.empStatus),
      religion: new FormControl(employeeRawValue.religion),
      experience: new FormControl(employeeRawValue.experience),
      qualifications: new FormControl(employeeRawValue.qualifications),
      attendanceCode: new FormControl(employeeRawValue.attendanceCode),
      isActive: new FormControl(employeeRawValue.isActive),
    });
  }

  getEmployee(form: EmployeeFormGroup): IEmployee | NewEmployee {
    return this.convertEmployeeRawValueToEmployee(form.getRawValue() as EmployeeFormRawValue | NewEmployeeFormRawValue);
  }

  resetForm(form: EmployeeFormGroup, employee: EmployeeFormGroupInput): void {
    const employeeRawValue = this.convertEmployeeToEmployeeRawValue({ ...this.getFormDefaults(), ...employee });
    form.reset(
      {
        ...employeeRawValue,
        id: { value: employeeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lmd: currentTime,
      dateJoined: currentTime,
      dateResigned: currentTime,
      isActive: false,
    };
  }

  private convertEmployeeRawValueToEmployee(rawEmployee: EmployeeFormRawValue | NewEmployeeFormRawValue): IEmployee | NewEmployee {
    return {
      ...rawEmployee,
      lmd: dayjs(rawEmployee.lmd, DATE_TIME_FORMAT),
      dateJoined: dayjs(rawEmployee.dateJoined, DATE_TIME_FORMAT),
      dateResigned: dayjs(rawEmployee.dateResigned, DATE_TIME_FORMAT),
    };
  }

  private convertEmployeeToEmployeeRawValue(
    employee: IEmployee | (Partial<NewEmployee> & EmployeeFormDefaults),
  ): EmployeeFormRawValue | PartialWithRequiredKeyOf<NewEmployeeFormRawValue> {
    return {
      ...employee,
      lmd: employee.lmd ? employee.lmd.format(DATE_TIME_FORMAT) : undefined,
      dateJoined: employee.dateJoined ? employee.dateJoined.format(DATE_TIME_FORMAT) : undefined,
      dateResigned: employee.dateResigned ? employee.dateResigned.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
