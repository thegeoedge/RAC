package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmployeeCriteriaTest {

    @Test
    void newEmployeeCriteriaHasAllFiltersNullTest() {
        var employeeCriteria = new EmployeeCriteria();
        assertThat(employeeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void employeeCriteriaFluentMethodsCreatesFiltersTest() {
        var employeeCriteria = new EmployeeCriteria();

        setAllFilters(employeeCriteria);

        assertThat(employeeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void employeeCriteriaCopyCreatesNullFilterTest() {
        var employeeCriteria = new EmployeeCriteria();
        var copy = employeeCriteria.copy();

        assertThat(employeeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(employeeCriteria)
        );
    }

    @Test
    void employeeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var employeeCriteria = new EmployeeCriteria();
        setAllFilters(employeeCriteria);

        var copy = employeeCriteria.copy();

        assertThat(employeeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(employeeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var employeeCriteria = new EmployeeCriteria();

        assertThat(employeeCriteria).hasToString("EmployeeCriteria{}");
    }

    private static void setAllFilters(EmployeeCriteria employeeCriteria) {
        employeeCriteria.id();
        employeeCriteria.code();
        employeeCriteria.fullName();
        employeeCriteria.nameWithInitials();
        employeeCriteria.surname();
        employeeCriteria.nicNumber();
        employeeCriteria.nicIssueDate();
        employeeCriteria.passportNo();
        employeeCriteria.passportIssueDate();
        employeeCriteria.passportExpDate();
        employeeCriteria.dateOfBirth();
        employeeCriteria.age();
        employeeCriteria.bloodGroup();
        employeeCriteria.gender();
        employeeCriteria.phone2();
        employeeCriteria.maritalStatus();
        employeeCriteria.marriedDate();
        employeeCriteria.nationality();
        employeeCriteria.permanentAddress();
        employeeCriteria.temporaryAddress();
        employeeCriteria.home();
        employeeCriteria.mobile();
        employeeCriteria.fax();
        employeeCriteria.email();
        employeeCriteria.emergencyContactPerson();
        employeeCriteria.emergencyNumber();
        employeeCriteria.city();
        employeeCriteria.district();
        employeeCriteria.province();
        employeeCriteria.electorate();
        employeeCriteria.mainRoad();
        employeeCriteria.modeOfTransport();
        employeeCriteria.distance();
        employeeCriteria.travelTime();
        employeeCriteria.username();
        employeeCriteria.password();
        employeeCriteria.departmentID();
        employeeCriteria.departmentCode();
        employeeCriteria.empRegDate();
        employeeCriteria.lmu();
        employeeCriteria.lmd();
        employeeCriteria.roleId();
        employeeCriteria.roleName();
        employeeCriteria.epf();
        employeeCriteria.etf();
        employeeCriteria.dateJoined();
        employeeCriteria.dateResigned();
        employeeCriteria.designation();
        employeeCriteria.jobStatusId();
        employeeCriteria.jobStatusName();
        employeeCriteria.imagePath();
        employeeCriteria.bankAccountNo();
        employeeCriteria.bankId();
        employeeCriteria.bankName();
        employeeCriteria.branchId();
        employeeCriteria.branchName();
        employeeCriteria.salaryPaymentBasis();
        employeeCriteria.empStatus();
        employeeCriteria.religion();
        employeeCriteria.experience();
        employeeCriteria.qualifications();
        employeeCriteria.attendanceCode();
        employeeCriteria.isActive();
        employeeCriteria.distinct();
    }

    private static Condition<EmployeeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getFullName()) &&
                condition.apply(criteria.getNameWithInitials()) &&
                condition.apply(criteria.getSurname()) &&
                condition.apply(criteria.getNicNumber()) &&
                condition.apply(criteria.getNicIssueDate()) &&
                condition.apply(criteria.getPassportNo()) &&
                condition.apply(criteria.getPassportIssueDate()) &&
                condition.apply(criteria.getPassportExpDate()) &&
                condition.apply(criteria.getDateOfBirth()) &&
                condition.apply(criteria.getAge()) &&
                condition.apply(criteria.getBloodGroup()) &&
                condition.apply(criteria.getGender()) &&
                condition.apply(criteria.getPhone2()) &&
                condition.apply(criteria.getMaritalStatus()) &&
                condition.apply(criteria.getMarriedDate()) &&
                condition.apply(criteria.getNationality()) &&
                condition.apply(criteria.getPermanentAddress()) &&
                condition.apply(criteria.getTemporaryAddress()) &&
                condition.apply(criteria.getHome()) &&
                condition.apply(criteria.getMobile()) &&
                condition.apply(criteria.getFax()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getEmergencyContactPerson()) &&
                condition.apply(criteria.getEmergencyNumber()) &&
                condition.apply(criteria.getCity()) &&
                condition.apply(criteria.getDistrict()) &&
                condition.apply(criteria.getProvince()) &&
                condition.apply(criteria.getElectorate()) &&
                condition.apply(criteria.getMainRoad()) &&
                condition.apply(criteria.getModeOfTransport()) &&
                condition.apply(criteria.getDistance()) &&
                condition.apply(criteria.getTravelTime()) &&
                condition.apply(criteria.getUsername()) &&
                condition.apply(criteria.getPassword()) &&
                condition.apply(criteria.getDepartmentID()) &&
                condition.apply(criteria.getDepartmentCode()) &&
                condition.apply(criteria.getEmpRegDate()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getRoleId()) &&
                condition.apply(criteria.getRoleName()) &&
                condition.apply(criteria.getEpf()) &&
                condition.apply(criteria.getEtf()) &&
                condition.apply(criteria.getDateJoined()) &&
                condition.apply(criteria.getDateResigned()) &&
                condition.apply(criteria.getDesignation()) &&
                condition.apply(criteria.getJobStatusId()) &&
                condition.apply(criteria.getJobStatusName()) &&
                condition.apply(criteria.getImagePath()) &&
                condition.apply(criteria.getBankAccountNo()) &&
                condition.apply(criteria.getBankId()) &&
                condition.apply(criteria.getBankName()) &&
                condition.apply(criteria.getBranchId()) &&
                condition.apply(criteria.getBranchName()) &&
                condition.apply(criteria.getSalaryPaymentBasis()) &&
                condition.apply(criteria.getEmpStatus()) &&
                condition.apply(criteria.getReligion()) &&
                condition.apply(criteria.getExperience()) &&
                condition.apply(criteria.getQualifications()) &&
                condition.apply(criteria.getAttendanceCode()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmployeeCriteria> copyFiltersAre(EmployeeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getFullName(), copy.getFullName()) &&
                condition.apply(criteria.getNameWithInitials(), copy.getNameWithInitials()) &&
                condition.apply(criteria.getSurname(), copy.getSurname()) &&
                condition.apply(criteria.getNicNumber(), copy.getNicNumber()) &&
                condition.apply(criteria.getNicIssueDate(), copy.getNicIssueDate()) &&
                condition.apply(criteria.getPassportNo(), copy.getPassportNo()) &&
                condition.apply(criteria.getPassportIssueDate(), copy.getPassportIssueDate()) &&
                condition.apply(criteria.getPassportExpDate(), copy.getPassportExpDate()) &&
                condition.apply(criteria.getDateOfBirth(), copy.getDateOfBirth()) &&
                condition.apply(criteria.getAge(), copy.getAge()) &&
                condition.apply(criteria.getBloodGroup(), copy.getBloodGroup()) &&
                condition.apply(criteria.getGender(), copy.getGender()) &&
                condition.apply(criteria.getPhone2(), copy.getPhone2()) &&
                condition.apply(criteria.getMaritalStatus(), copy.getMaritalStatus()) &&
                condition.apply(criteria.getMarriedDate(), copy.getMarriedDate()) &&
                condition.apply(criteria.getNationality(), copy.getNationality()) &&
                condition.apply(criteria.getPermanentAddress(), copy.getPermanentAddress()) &&
                condition.apply(criteria.getTemporaryAddress(), copy.getTemporaryAddress()) &&
                condition.apply(criteria.getHome(), copy.getHome()) &&
                condition.apply(criteria.getMobile(), copy.getMobile()) &&
                condition.apply(criteria.getFax(), copy.getFax()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getEmergencyContactPerson(), copy.getEmergencyContactPerson()) &&
                condition.apply(criteria.getEmergencyNumber(), copy.getEmergencyNumber()) &&
                condition.apply(criteria.getCity(), copy.getCity()) &&
                condition.apply(criteria.getDistrict(), copy.getDistrict()) &&
                condition.apply(criteria.getProvince(), copy.getProvince()) &&
                condition.apply(criteria.getElectorate(), copy.getElectorate()) &&
                condition.apply(criteria.getMainRoad(), copy.getMainRoad()) &&
                condition.apply(criteria.getModeOfTransport(), copy.getModeOfTransport()) &&
                condition.apply(criteria.getDistance(), copy.getDistance()) &&
                condition.apply(criteria.getTravelTime(), copy.getTravelTime()) &&
                condition.apply(criteria.getUsername(), copy.getUsername()) &&
                condition.apply(criteria.getPassword(), copy.getPassword()) &&
                condition.apply(criteria.getDepartmentID(), copy.getDepartmentID()) &&
                condition.apply(criteria.getDepartmentCode(), copy.getDepartmentCode()) &&
                condition.apply(criteria.getEmpRegDate(), copy.getEmpRegDate()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getRoleId(), copy.getRoleId()) &&
                condition.apply(criteria.getRoleName(), copy.getRoleName()) &&
                condition.apply(criteria.getEpf(), copy.getEpf()) &&
                condition.apply(criteria.getEtf(), copy.getEtf()) &&
                condition.apply(criteria.getDateJoined(), copy.getDateJoined()) &&
                condition.apply(criteria.getDateResigned(), copy.getDateResigned()) &&
                condition.apply(criteria.getDesignation(), copy.getDesignation()) &&
                condition.apply(criteria.getJobStatusId(), copy.getJobStatusId()) &&
                condition.apply(criteria.getJobStatusName(), copy.getJobStatusName()) &&
                condition.apply(criteria.getImagePath(), copy.getImagePath()) &&
                condition.apply(criteria.getBankAccountNo(), copy.getBankAccountNo()) &&
                condition.apply(criteria.getBankId(), copy.getBankId()) &&
                condition.apply(criteria.getBankName(), copy.getBankName()) &&
                condition.apply(criteria.getBranchId(), copy.getBranchId()) &&
                condition.apply(criteria.getBranchName(), copy.getBranchName()) &&
                condition.apply(criteria.getSalaryPaymentBasis(), copy.getSalaryPaymentBasis()) &&
                condition.apply(criteria.getEmpStatus(), copy.getEmpStatus()) &&
                condition.apply(criteria.getReligion(), copy.getReligion()) &&
                condition.apply(criteria.getExperience(), copy.getExperience()) &&
                condition.apply(criteria.getQualifications(), copy.getQualifications()) &&
                condition.apply(criteria.getAttendanceCode(), copy.getAttendanceCode()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
