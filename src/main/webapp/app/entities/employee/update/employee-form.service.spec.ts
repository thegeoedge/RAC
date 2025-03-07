import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../employee.test-samples';

import { EmployeeFormService } from './employee-form.service';

describe('Employee Form Service', () => {
  let service: EmployeeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeFormService);
  });

  describe('Service methods', () => {
    describe('createEmployeeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmployeeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            fullName: expect.any(Object),
            nameWithInitials: expect.any(Object),
            surname: expect.any(Object),
            nicNumber: expect.any(Object),
            nicIssueDate: expect.any(Object),
            passportNo: expect.any(Object),
            passportIssueDate: expect.any(Object),
            passportExpDate: expect.any(Object),
            dateOfBirth: expect.any(Object),
            age: expect.any(Object),
            bloodGroup: expect.any(Object),
            gender: expect.any(Object),
            phone2: expect.any(Object),
            maritalStatus: expect.any(Object),
            marriedDate: expect.any(Object),
            nationality: expect.any(Object),
            permanentAddress: expect.any(Object),
            temporaryAddress: expect.any(Object),
            home: expect.any(Object),
            mobile: expect.any(Object),
            fax: expect.any(Object),
            email: expect.any(Object),
            emergencyContactPerson: expect.any(Object),
            emergencyNumber: expect.any(Object),
            city: expect.any(Object),
            district: expect.any(Object),
            province: expect.any(Object),
            electorate: expect.any(Object),
            mainRoad: expect.any(Object),
            modeOfTransport: expect.any(Object),
            distance: expect.any(Object),
            travelTime: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
            departmentID: expect.any(Object),
            departmentCode: expect.any(Object),
            empRegDate: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            roleId: expect.any(Object),
            roleName: expect.any(Object),
            epf: expect.any(Object),
            etf: expect.any(Object),
            dateJoined: expect.any(Object),
            dateResigned: expect.any(Object),
            designation: expect.any(Object),
            jobStatusId: expect.any(Object),
            jobStatusName: expect.any(Object),
            imagePath: expect.any(Object),
            bankAccountNo: expect.any(Object),
            bankId: expect.any(Object),
            bankName: expect.any(Object),
            branchId: expect.any(Object),
            branchName: expect.any(Object),
            salaryPaymentBasis: expect.any(Object),
            empStatus: expect.any(Object),
            religion: expect.any(Object),
            experience: expect.any(Object),
            qualifications: expect.any(Object),
            attendanceCode: expect.any(Object),
            isActive: expect.any(Object),
          }),
        );
      });

      it('passing IEmployee should create a new form with FormGroup', () => {
        const formGroup = service.createEmployeeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            fullName: expect.any(Object),
            nameWithInitials: expect.any(Object),
            surname: expect.any(Object),
            nicNumber: expect.any(Object),
            nicIssueDate: expect.any(Object),
            passportNo: expect.any(Object),
            passportIssueDate: expect.any(Object),
            passportExpDate: expect.any(Object),
            dateOfBirth: expect.any(Object),
            age: expect.any(Object),
            bloodGroup: expect.any(Object),
            gender: expect.any(Object),
            phone2: expect.any(Object),
            maritalStatus: expect.any(Object),
            marriedDate: expect.any(Object),
            nationality: expect.any(Object),
            permanentAddress: expect.any(Object),
            temporaryAddress: expect.any(Object),
            home: expect.any(Object),
            mobile: expect.any(Object),
            fax: expect.any(Object),
            email: expect.any(Object),
            emergencyContactPerson: expect.any(Object),
            emergencyNumber: expect.any(Object),
            city: expect.any(Object),
            district: expect.any(Object),
            province: expect.any(Object),
            electorate: expect.any(Object),
            mainRoad: expect.any(Object),
            modeOfTransport: expect.any(Object),
            distance: expect.any(Object),
            travelTime: expect.any(Object),
            username: expect.any(Object),
            password: expect.any(Object),
            departmentID: expect.any(Object),
            departmentCode: expect.any(Object),
            empRegDate: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            roleId: expect.any(Object),
            roleName: expect.any(Object),
            epf: expect.any(Object),
            etf: expect.any(Object),
            dateJoined: expect.any(Object),
            dateResigned: expect.any(Object),
            designation: expect.any(Object),
            jobStatusId: expect.any(Object),
            jobStatusName: expect.any(Object),
            imagePath: expect.any(Object),
            bankAccountNo: expect.any(Object),
            bankId: expect.any(Object),
            bankName: expect.any(Object),
            branchId: expect.any(Object),
            branchName: expect.any(Object),
            salaryPaymentBasis: expect.any(Object),
            empStatus: expect.any(Object),
            religion: expect.any(Object),
            experience: expect.any(Object),
            qualifications: expect.any(Object),
            attendanceCode: expect.any(Object),
            isActive: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmployee', () => {
      it('should return NewEmployee for default Employee initial value', () => {
        const formGroup = service.createEmployeeFormGroup(sampleWithNewData);

        const employee = service.getEmployee(formGroup) as any;

        expect(employee).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmployee for empty Employee initial value', () => {
        const formGroup = service.createEmployeeFormGroup();

        const employee = service.getEmployee(formGroup) as any;

        expect(employee).toMatchObject({});
      });

      it('should return IEmployee', () => {
        const formGroup = service.createEmployeeFormGroup(sampleWithRequiredData);

        const employee = service.getEmployee(formGroup) as any;

        expect(employee).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmployee should not enable id FormControl', () => {
        const formGroup = service.createEmployeeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmployee should disable id FormControl', () => {
        const formGroup = service.createEmployeeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
