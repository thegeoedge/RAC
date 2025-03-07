package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Employee} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter fullName;

    private StringFilter nameWithInitials;

    private StringFilter surname;

    private StringFilter nicNumber;

    private LocalDateFilter nicIssueDate;

    private StringFilter passportNo;

    private LocalDateFilter passportIssueDate;

    private LocalDateFilter passportExpDate;

    private LocalDateFilter dateOfBirth;

    private IntegerFilter age;

    private StringFilter bloodGroup;

    private StringFilter gender;

    private StringFilter phone2;

    private StringFilter maritalStatus;

    private LocalDateFilter marriedDate;

    private StringFilter nationality;

    private StringFilter permanentAddress;

    private StringFilter temporaryAddress;

    private StringFilter home;

    private StringFilter mobile;

    private StringFilter fax;

    private StringFilter email;

    private StringFilter emergencyContactPerson;

    private StringFilter emergencyNumber;

    private StringFilter city;

    private StringFilter district;

    private StringFilter province;

    private StringFilter electorate;

    private StringFilter mainRoad;

    private StringFilter modeOfTransport;

    private StringFilter distance;

    private StringFilter travelTime;

    private StringFilter username;

    private StringFilter password;

    private IntegerFilter departmentID;

    private StringFilter departmentCode;

    private LocalDateFilter empRegDate;

    private StringFilter lmu;

    private InstantFilter lmd;

    private IntegerFilter roleId;

    private StringFilter roleName;

    private StringFilter epf;

    private StringFilter etf;

    private InstantFilter dateJoined;

    private InstantFilter dateResigned;

    private StringFilter designation;

    private IntegerFilter jobStatusId;

    private StringFilter jobStatusName;

    private StringFilter imagePath;

    private StringFilter bankAccountNo;

    private IntegerFilter bankId;

    private StringFilter bankName;

    private IntegerFilter branchId;

    private StringFilter branchName;

    private StringFilter salaryPaymentBasis;

    private StringFilter empStatus;

    private StringFilter religion;

    private StringFilter experience;

    private StringFilter qualifications;

    private StringFilter attendanceCode;

    private BooleanFilter isActive;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.fullName = other.optionalFullName().map(StringFilter::copy).orElse(null);
        this.nameWithInitials = other.optionalNameWithInitials().map(StringFilter::copy).orElse(null);
        this.surname = other.optionalSurname().map(StringFilter::copy).orElse(null);
        this.nicNumber = other.optionalNicNumber().map(StringFilter::copy).orElse(null);
        this.nicIssueDate = other.optionalNicIssueDate().map(LocalDateFilter::copy).orElse(null);
        this.passportNo = other.optionalPassportNo().map(StringFilter::copy).orElse(null);
        this.passportIssueDate = other.optionalPassportIssueDate().map(LocalDateFilter::copy).orElse(null);
        this.passportExpDate = other.optionalPassportExpDate().map(LocalDateFilter::copy).orElse(null);
        this.dateOfBirth = other.optionalDateOfBirth().map(LocalDateFilter::copy).orElse(null);
        this.age = other.optionalAge().map(IntegerFilter::copy).orElse(null);
        this.bloodGroup = other.optionalBloodGroup().map(StringFilter::copy).orElse(null);
        this.gender = other.optionalGender().map(StringFilter::copy).orElse(null);
        this.phone2 = other.optionalPhone2().map(StringFilter::copy).orElse(null);
        this.maritalStatus = other.optionalMaritalStatus().map(StringFilter::copy).orElse(null);
        this.marriedDate = other.optionalMarriedDate().map(LocalDateFilter::copy).orElse(null);
        this.nationality = other.optionalNationality().map(StringFilter::copy).orElse(null);
        this.permanentAddress = other.optionalPermanentAddress().map(StringFilter::copy).orElse(null);
        this.temporaryAddress = other.optionalTemporaryAddress().map(StringFilter::copy).orElse(null);
        this.home = other.optionalHome().map(StringFilter::copy).orElse(null);
        this.mobile = other.optionalMobile().map(StringFilter::copy).orElse(null);
        this.fax = other.optionalFax().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.emergencyContactPerson = other.optionalEmergencyContactPerson().map(StringFilter::copy).orElse(null);
        this.emergencyNumber = other.optionalEmergencyNumber().map(StringFilter::copy).orElse(null);
        this.city = other.optionalCity().map(StringFilter::copy).orElse(null);
        this.district = other.optionalDistrict().map(StringFilter::copy).orElse(null);
        this.province = other.optionalProvince().map(StringFilter::copy).orElse(null);
        this.electorate = other.optionalElectorate().map(StringFilter::copy).orElse(null);
        this.mainRoad = other.optionalMainRoad().map(StringFilter::copy).orElse(null);
        this.modeOfTransport = other.optionalModeOfTransport().map(StringFilter::copy).orElse(null);
        this.distance = other.optionalDistance().map(StringFilter::copy).orElse(null);
        this.travelTime = other.optionalTravelTime().map(StringFilter::copy).orElse(null);
        this.username = other.optionalUsername().map(StringFilter::copy).orElse(null);
        this.password = other.optionalPassword().map(StringFilter::copy).orElse(null);
        this.departmentID = other.optionalDepartmentID().map(IntegerFilter::copy).orElse(null);
        this.departmentCode = other.optionalDepartmentCode().map(StringFilter::copy).orElse(null);
        this.empRegDate = other.optionalEmpRegDate().map(LocalDateFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(StringFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.roleId = other.optionalRoleId().map(IntegerFilter::copy).orElse(null);
        this.roleName = other.optionalRoleName().map(StringFilter::copy).orElse(null);
        this.epf = other.optionalEpf().map(StringFilter::copy).orElse(null);
        this.etf = other.optionalEtf().map(StringFilter::copy).orElse(null);
        this.dateJoined = other.optionalDateJoined().map(InstantFilter::copy).orElse(null);
        this.dateResigned = other.optionalDateResigned().map(InstantFilter::copy).orElse(null);
        this.designation = other.optionalDesignation().map(StringFilter::copy).orElse(null);
        this.jobStatusId = other.optionalJobStatusId().map(IntegerFilter::copy).orElse(null);
        this.jobStatusName = other.optionalJobStatusName().map(StringFilter::copy).orElse(null);
        this.imagePath = other.optionalImagePath().map(StringFilter::copy).orElse(null);
        this.bankAccountNo = other.optionalBankAccountNo().map(StringFilter::copy).orElse(null);
        this.bankId = other.optionalBankId().map(IntegerFilter::copy).orElse(null);
        this.bankName = other.optionalBankName().map(StringFilter::copy).orElse(null);
        this.branchId = other.optionalBranchId().map(IntegerFilter::copy).orElse(null);
        this.branchName = other.optionalBranchName().map(StringFilter::copy).orElse(null);
        this.salaryPaymentBasis = other.optionalSalaryPaymentBasis().map(StringFilter::copy).orElse(null);
        this.empStatus = other.optionalEmpStatus().map(StringFilter::copy).orElse(null);
        this.religion = other.optionalReligion().map(StringFilter::copy).orElse(null);
        this.experience = other.optionalExperience().map(StringFilter::copy).orElse(null);
        this.qualifications = other.optionalQualifications().map(StringFilter::copy).orElse(null);
        this.attendanceCode = other.optionalAttendanceCode().map(StringFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public Optional<StringFilter> optionalFullName() {
        return Optional.ofNullable(fullName);
    }

    public StringFilter fullName() {
        if (fullName == null) {
            setFullName(new StringFilter());
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getNameWithInitials() {
        return nameWithInitials;
    }

    public Optional<StringFilter> optionalNameWithInitials() {
        return Optional.ofNullable(nameWithInitials);
    }

    public StringFilter nameWithInitials() {
        if (nameWithInitials == null) {
            setNameWithInitials(new StringFilter());
        }
        return nameWithInitials;
    }

    public void setNameWithInitials(StringFilter nameWithInitials) {
        this.nameWithInitials = nameWithInitials;
    }

    public StringFilter getSurname() {
        return surname;
    }

    public Optional<StringFilter> optionalSurname() {
        return Optional.ofNullable(surname);
    }

    public StringFilter surname() {
        if (surname == null) {
            setSurname(new StringFilter());
        }
        return surname;
    }

    public void setSurname(StringFilter surname) {
        this.surname = surname;
    }

    public StringFilter getNicNumber() {
        return nicNumber;
    }

    public Optional<StringFilter> optionalNicNumber() {
        return Optional.ofNullable(nicNumber);
    }

    public StringFilter nicNumber() {
        if (nicNumber == null) {
            setNicNumber(new StringFilter());
        }
        return nicNumber;
    }

    public void setNicNumber(StringFilter nicNumber) {
        this.nicNumber = nicNumber;
    }

    public LocalDateFilter getNicIssueDate() {
        return nicIssueDate;
    }

    public Optional<LocalDateFilter> optionalNicIssueDate() {
        return Optional.ofNullable(nicIssueDate);
    }

    public LocalDateFilter nicIssueDate() {
        if (nicIssueDate == null) {
            setNicIssueDate(new LocalDateFilter());
        }
        return nicIssueDate;
    }

    public void setNicIssueDate(LocalDateFilter nicIssueDate) {
        this.nicIssueDate = nicIssueDate;
    }

    public StringFilter getPassportNo() {
        return passportNo;
    }

    public Optional<StringFilter> optionalPassportNo() {
        return Optional.ofNullable(passportNo);
    }

    public StringFilter passportNo() {
        if (passportNo == null) {
            setPassportNo(new StringFilter());
        }
        return passportNo;
    }

    public void setPassportNo(StringFilter passportNo) {
        this.passportNo = passportNo;
    }

    public LocalDateFilter getPassportIssueDate() {
        return passportIssueDate;
    }

    public Optional<LocalDateFilter> optionalPassportIssueDate() {
        return Optional.ofNullable(passportIssueDate);
    }

    public LocalDateFilter passportIssueDate() {
        if (passportIssueDate == null) {
            setPassportIssueDate(new LocalDateFilter());
        }
        return passportIssueDate;
    }

    public void setPassportIssueDate(LocalDateFilter passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public LocalDateFilter getPassportExpDate() {
        return passportExpDate;
    }

    public Optional<LocalDateFilter> optionalPassportExpDate() {
        return Optional.ofNullable(passportExpDate);
    }

    public LocalDateFilter passportExpDate() {
        if (passportExpDate == null) {
            setPassportExpDate(new LocalDateFilter());
        }
        return passportExpDate;
    }

    public void setPassportExpDate(LocalDateFilter passportExpDate) {
        this.passportExpDate = passportExpDate;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public Optional<LocalDateFilter> optionalDateOfBirth() {
        return Optional.ofNullable(dateOfBirth);
    }

    public LocalDateFilter dateOfBirth() {
        if (dateOfBirth == null) {
            setDateOfBirth(new LocalDateFilter());
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public Optional<IntegerFilter> optionalAge() {
        return Optional.ofNullable(age);
    }

    public IntegerFilter age() {
        if (age == null) {
            setAge(new IntegerFilter());
        }
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getBloodGroup() {
        return bloodGroup;
    }

    public Optional<StringFilter> optionalBloodGroup() {
        return Optional.ofNullable(bloodGroup);
    }

    public StringFilter bloodGroup() {
        if (bloodGroup == null) {
            setBloodGroup(new StringFilter());
        }
        return bloodGroup;
    }

    public void setBloodGroup(StringFilter bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public StringFilter getGender() {
        return gender;
    }

    public Optional<StringFilter> optionalGender() {
        return Optional.ofNullable(gender);
    }

    public StringFilter gender() {
        if (gender == null) {
            setGender(new StringFilter());
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getPhone2() {
        return phone2;
    }

    public Optional<StringFilter> optionalPhone2() {
        return Optional.ofNullable(phone2);
    }

    public StringFilter phone2() {
        if (phone2 == null) {
            setPhone2(new StringFilter());
        }
        return phone2;
    }

    public void setPhone2(StringFilter phone2) {
        this.phone2 = phone2;
    }

    public StringFilter getMaritalStatus() {
        return maritalStatus;
    }

    public Optional<StringFilter> optionalMaritalStatus() {
        return Optional.ofNullable(maritalStatus);
    }

    public StringFilter maritalStatus() {
        if (maritalStatus == null) {
            setMaritalStatus(new StringFilter());
        }
        return maritalStatus;
    }

    public void setMaritalStatus(StringFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public LocalDateFilter getMarriedDate() {
        return marriedDate;
    }

    public Optional<LocalDateFilter> optionalMarriedDate() {
        return Optional.ofNullable(marriedDate);
    }

    public LocalDateFilter marriedDate() {
        if (marriedDate == null) {
            setMarriedDate(new LocalDateFilter());
        }
        return marriedDate;
    }

    public void setMarriedDate(LocalDateFilter marriedDate) {
        this.marriedDate = marriedDate;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public Optional<StringFilter> optionalNationality() {
        return Optional.ofNullable(nationality);
    }

    public StringFilter nationality() {
        if (nationality == null) {
            setNationality(new StringFilter());
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public StringFilter getPermanentAddress() {
        return permanentAddress;
    }

    public Optional<StringFilter> optionalPermanentAddress() {
        return Optional.ofNullable(permanentAddress);
    }

    public StringFilter permanentAddress() {
        if (permanentAddress == null) {
            setPermanentAddress(new StringFilter());
        }
        return permanentAddress;
    }

    public void setPermanentAddress(StringFilter permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public StringFilter getTemporaryAddress() {
        return temporaryAddress;
    }

    public Optional<StringFilter> optionalTemporaryAddress() {
        return Optional.ofNullable(temporaryAddress);
    }

    public StringFilter temporaryAddress() {
        if (temporaryAddress == null) {
            setTemporaryAddress(new StringFilter());
        }
        return temporaryAddress;
    }

    public void setTemporaryAddress(StringFilter temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public StringFilter getHome() {
        return home;
    }

    public Optional<StringFilter> optionalHome() {
        return Optional.ofNullable(home);
    }

    public StringFilter home() {
        if (home == null) {
            setHome(new StringFilter());
        }
        return home;
    }

    public void setHome(StringFilter home) {
        this.home = home;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public Optional<StringFilter> optionalMobile() {
        return Optional.ofNullable(mobile);
    }

    public StringFilter mobile() {
        if (mobile == null) {
            setMobile(new StringFilter());
        }
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getFax() {
        return fax;
    }

    public Optional<StringFilter> optionalFax() {
        return Optional.ofNullable(fax);
    }

    public StringFilter fax() {
        if (fax == null) {
            setFax(new StringFilter());
        }
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getEmergencyContactPerson() {
        return emergencyContactPerson;
    }

    public Optional<StringFilter> optionalEmergencyContactPerson() {
        return Optional.ofNullable(emergencyContactPerson);
    }

    public StringFilter emergencyContactPerson() {
        if (emergencyContactPerson == null) {
            setEmergencyContactPerson(new StringFilter());
        }
        return emergencyContactPerson;
    }

    public void setEmergencyContactPerson(StringFilter emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson;
    }

    public StringFilter getEmergencyNumber() {
        return emergencyNumber;
    }

    public Optional<StringFilter> optionalEmergencyNumber() {
        return Optional.ofNullable(emergencyNumber);
    }

    public StringFilter emergencyNumber() {
        if (emergencyNumber == null) {
            setEmergencyNumber(new StringFilter());
        }
        return emergencyNumber;
    }

    public void setEmergencyNumber(StringFilter emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public StringFilter getCity() {
        return city;
    }

    public Optional<StringFilter> optionalCity() {
        return Optional.ofNullable(city);
    }

    public StringFilter city() {
        if (city == null) {
            setCity(new StringFilter());
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getDistrict() {
        return district;
    }

    public Optional<StringFilter> optionalDistrict() {
        return Optional.ofNullable(district);
    }

    public StringFilter district() {
        if (district == null) {
            setDistrict(new StringFilter());
        }
        return district;
    }

    public void setDistrict(StringFilter district) {
        this.district = district;
    }

    public StringFilter getProvince() {
        return province;
    }

    public Optional<StringFilter> optionalProvince() {
        return Optional.ofNullable(province);
    }

    public StringFilter province() {
        if (province == null) {
            setProvince(new StringFilter());
        }
        return province;
    }

    public void setProvince(StringFilter province) {
        this.province = province;
    }

    public StringFilter getElectorate() {
        return electorate;
    }

    public Optional<StringFilter> optionalElectorate() {
        return Optional.ofNullable(electorate);
    }

    public StringFilter electorate() {
        if (electorate == null) {
            setElectorate(new StringFilter());
        }
        return electorate;
    }

    public void setElectorate(StringFilter electorate) {
        this.electorate = electorate;
    }

    public StringFilter getMainRoad() {
        return mainRoad;
    }

    public Optional<StringFilter> optionalMainRoad() {
        return Optional.ofNullable(mainRoad);
    }

    public StringFilter mainRoad() {
        if (mainRoad == null) {
            setMainRoad(new StringFilter());
        }
        return mainRoad;
    }

    public void setMainRoad(StringFilter mainRoad) {
        this.mainRoad = mainRoad;
    }

    public StringFilter getModeOfTransport() {
        return modeOfTransport;
    }

    public Optional<StringFilter> optionalModeOfTransport() {
        return Optional.ofNullable(modeOfTransport);
    }

    public StringFilter modeOfTransport() {
        if (modeOfTransport == null) {
            setModeOfTransport(new StringFilter());
        }
        return modeOfTransport;
    }

    public void setModeOfTransport(StringFilter modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public StringFilter getDistance() {
        return distance;
    }

    public Optional<StringFilter> optionalDistance() {
        return Optional.ofNullable(distance);
    }

    public StringFilter distance() {
        if (distance == null) {
            setDistance(new StringFilter());
        }
        return distance;
    }

    public void setDistance(StringFilter distance) {
        this.distance = distance;
    }

    public StringFilter getTravelTime() {
        return travelTime;
    }

    public Optional<StringFilter> optionalTravelTime() {
        return Optional.ofNullable(travelTime);
    }

    public StringFilter travelTime() {
        if (travelTime == null) {
            setTravelTime(new StringFilter());
        }
        return travelTime;
    }

    public void setTravelTime(StringFilter travelTime) {
        this.travelTime = travelTime;
    }

    public StringFilter getUsername() {
        return username;
    }

    public Optional<StringFilter> optionalUsername() {
        return Optional.ofNullable(username);
    }

    public StringFilter username() {
        if (username == null) {
            setUsername(new StringFilter());
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public Optional<StringFilter> optionalPassword() {
        return Optional.ofNullable(password);
    }

    public StringFilter password() {
        if (password == null) {
            setPassword(new StringFilter());
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public IntegerFilter getDepartmentID() {
        return departmentID;
    }

    public Optional<IntegerFilter> optionalDepartmentID() {
        return Optional.ofNullable(departmentID);
    }

    public IntegerFilter departmentID() {
        if (departmentID == null) {
            setDepartmentID(new IntegerFilter());
        }
        return departmentID;
    }

    public void setDepartmentID(IntegerFilter departmentID) {
        this.departmentID = departmentID;
    }

    public StringFilter getDepartmentCode() {
        return departmentCode;
    }

    public Optional<StringFilter> optionalDepartmentCode() {
        return Optional.ofNullable(departmentCode);
    }

    public StringFilter departmentCode() {
        if (departmentCode == null) {
            setDepartmentCode(new StringFilter());
        }
        return departmentCode;
    }

    public void setDepartmentCode(StringFilter departmentCode) {
        this.departmentCode = departmentCode;
    }

    public LocalDateFilter getEmpRegDate() {
        return empRegDate;
    }

    public Optional<LocalDateFilter> optionalEmpRegDate() {
        return Optional.ofNullable(empRegDate);
    }

    public LocalDateFilter empRegDate() {
        if (empRegDate == null) {
            setEmpRegDate(new LocalDateFilter());
        }
        return empRegDate;
    }

    public void setEmpRegDate(LocalDateFilter empRegDate) {
        this.empRegDate = empRegDate;
    }

    public StringFilter getLmu() {
        return lmu;
    }

    public Optional<StringFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public StringFilter lmu() {
        if (lmu == null) {
            setLmu(new StringFilter());
        }
        return lmu;
    }

    public void setLmu(StringFilter lmu) {
        this.lmu = lmu;
    }

    public InstantFilter getLmd() {
        return lmd;
    }

    public Optional<InstantFilter> optionalLmd() {
        return Optional.ofNullable(lmd);
    }

    public InstantFilter lmd() {
        if (lmd == null) {
            setLmd(new InstantFilter());
        }
        return lmd;
    }

    public void setLmd(InstantFilter lmd) {
        this.lmd = lmd;
    }

    public IntegerFilter getRoleId() {
        return roleId;
    }

    public Optional<IntegerFilter> optionalRoleId() {
        return Optional.ofNullable(roleId);
    }

    public IntegerFilter roleId() {
        if (roleId == null) {
            setRoleId(new IntegerFilter());
        }
        return roleId;
    }

    public void setRoleId(IntegerFilter roleId) {
        this.roleId = roleId;
    }

    public StringFilter getRoleName() {
        return roleName;
    }

    public Optional<StringFilter> optionalRoleName() {
        return Optional.ofNullable(roleName);
    }

    public StringFilter roleName() {
        if (roleName == null) {
            setRoleName(new StringFilter());
        }
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
    }

    public StringFilter getEpf() {
        return epf;
    }

    public Optional<StringFilter> optionalEpf() {
        return Optional.ofNullable(epf);
    }

    public StringFilter epf() {
        if (epf == null) {
            setEpf(new StringFilter());
        }
        return epf;
    }

    public void setEpf(StringFilter epf) {
        this.epf = epf;
    }

    public StringFilter getEtf() {
        return etf;
    }

    public Optional<StringFilter> optionalEtf() {
        return Optional.ofNullable(etf);
    }

    public StringFilter etf() {
        if (etf == null) {
            setEtf(new StringFilter());
        }
        return etf;
    }

    public void setEtf(StringFilter etf) {
        this.etf = etf;
    }

    public InstantFilter getDateJoined() {
        return dateJoined;
    }

    public Optional<InstantFilter> optionalDateJoined() {
        return Optional.ofNullable(dateJoined);
    }

    public InstantFilter dateJoined() {
        if (dateJoined == null) {
            setDateJoined(new InstantFilter());
        }
        return dateJoined;
    }

    public void setDateJoined(InstantFilter dateJoined) {
        this.dateJoined = dateJoined;
    }

    public InstantFilter getDateResigned() {
        return dateResigned;
    }

    public Optional<InstantFilter> optionalDateResigned() {
        return Optional.ofNullable(dateResigned);
    }

    public InstantFilter dateResigned() {
        if (dateResigned == null) {
            setDateResigned(new InstantFilter());
        }
        return dateResigned;
    }

    public void setDateResigned(InstantFilter dateResigned) {
        this.dateResigned = dateResigned;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public Optional<StringFilter> optionalDesignation() {
        return Optional.ofNullable(designation);
    }

    public StringFilter designation() {
        if (designation == null) {
            setDesignation(new StringFilter());
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public IntegerFilter getJobStatusId() {
        return jobStatusId;
    }

    public Optional<IntegerFilter> optionalJobStatusId() {
        return Optional.ofNullable(jobStatusId);
    }

    public IntegerFilter jobStatusId() {
        if (jobStatusId == null) {
            setJobStatusId(new IntegerFilter());
        }
        return jobStatusId;
    }

    public void setJobStatusId(IntegerFilter jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public StringFilter getJobStatusName() {
        return jobStatusName;
    }

    public Optional<StringFilter> optionalJobStatusName() {
        return Optional.ofNullable(jobStatusName);
    }

    public StringFilter jobStatusName() {
        if (jobStatusName == null) {
            setJobStatusName(new StringFilter());
        }
        return jobStatusName;
    }

    public void setJobStatusName(StringFilter jobStatusName) {
        this.jobStatusName = jobStatusName;
    }

    public StringFilter getImagePath() {
        return imagePath;
    }

    public Optional<StringFilter> optionalImagePath() {
        return Optional.ofNullable(imagePath);
    }

    public StringFilter imagePath() {
        if (imagePath == null) {
            setImagePath(new StringFilter());
        }
        return imagePath;
    }

    public void setImagePath(StringFilter imagePath) {
        this.imagePath = imagePath;
    }

    public StringFilter getBankAccountNo() {
        return bankAccountNo;
    }

    public Optional<StringFilter> optionalBankAccountNo() {
        return Optional.ofNullable(bankAccountNo);
    }

    public StringFilter bankAccountNo() {
        if (bankAccountNo == null) {
            setBankAccountNo(new StringFilter());
        }
        return bankAccountNo;
    }

    public void setBankAccountNo(StringFilter bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public IntegerFilter getBankId() {
        return bankId;
    }

    public Optional<IntegerFilter> optionalBankId() {
        return Optional.ofNullable(bankId);
    }

    public IntegerFilter bankId() {
        if (bankId == null) {
            setBankId(new IntegerFilter());
        }
        return bankId;
    }

    public void setBankId(IntegerFilter bankId) {
        this.bankId = bankId;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public Optional<StringFilter> optionalBankName() {
        return Optional.ofNullable(bankName);
    }

    public StringFilter bankName() {
        if (bankName == null) {
            setBankName(new StringFilter());
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public IntegerFilter getBranchId() {
        return branchId;
    }

    public Optional<IntegerFilter> optionalBranchId() {
        return Optional.ofNullable(branchId);
    }

    public IntegerFilter branchId() {
        if (branchId == null) {
            setBranchId(new IntegerFilter());
        }
        return branchId;
    }

    public void setBranchId(IntegerFilter branchId) {
        this.branchId = branchId;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public Optional<StringFilter> optionalBranchName() {
        return Optional.ofNullable(branchName);
    }

    public StringFilter branchName() {
        if (branchName == null) {
            setBranchName(new StringFilter());
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getSalaryPaymentBasis() {
        return salaryPaymentBasis;
    }

    public Optional<StringFilter> optionalSalaryPaymentBasis() {
        return Optional.ofNullable(salaryPaymentBasis);
    }

    public StringFilter salaryPaymentBasis() {
        if (salaryPaymentBasis == null) {
            setSalaryPaymentBasis(new StringFilter());
        }
        return salaryPaymentBasis;
    }

    public void setSalaryPaymentBasis(StringFilter salaryPaymentBasis) {
        this.salaryPaymentBasis = salaryPaymentBasis;
    }

    public StringFilter getEmpStatus() {
        return empStatus;
    }

    public Optional<StringFilter> optionalEmpStatus() {
        return Optional.ofNullable(empStatus);
    }

    public StringFilter empStatus() {
        if (empStatus == null) {
            setEmpStatus(new StringFilter());
        }
        return empStatus;
    }

    public void setEmpStatus(StringFilter empStatus) {
        this.empStatus = empStatus;
    }

    public StringFilter getReligion() {
        return religion;
    }

    public Optional<StringFilter> optionalReligion() {
        return Optional.ofNullable(religion);
    }

    public StringFilter religion() {
        if (religion == null) {
            setReligion(new StringFilter());
        }
        return religion;
    }

    public void setReligion(StringFilter religion) {
        this.religion = religion;
    }

    public StringFilter getExperience() {
        return experience;
    }

    public Optional<StringFilter> optionalExperience() {
        return Optional.ofNullable(experience);
    }

    public StringFilter experience() {
        if (experience == null) {
            setExperience(new StringFilter());
        }
        return experience;
    }

    public void setExperience(StringFilter experience) {
        this.experience = experience;
    }

    public StringFilter getQualifications() {
        return qualifications;
    }

    public Optional<StringFilter> optionalQualifications() {
        return Optional.ofNullable(qualifications);
    }

    public StringFilter qualifications() {
        if (qualifications == null) {
            setQualifications(new StringFilter());
        }
        return qualifications;
    }

    public void setQualifications(StringFilter qualifications) {
        this.qualifications = qualifications;
    }

    public StringFilter getAttendanceCode() {
        return attendanceCode;
    }

    public Optional<StringFilter> optionalAttendanceCode() {
        return Optional.ofNullable(attendanceCode);
    }

    public StringFilter attendanceCode() {
        if (attendanceCode == null) {
            setAttendanceCode(new StringFilter());
        }
        return attendanceCode;
    }

    public void setAttendanceCode(StringFilter attendanceCode) {
        this.attendanceCode = attendanceCode;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public Optional<BooleanFilter> optionalIsActive() {
        return Optional.ofNullable(isActive);
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            setIsActive(new BooleanFilter());
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(nameWithInitials, that.nameWithInitials) &&
            Objects.equals(surname, that.surname) &&
            Objects.equals(nicNumber, that.nicNumber) &&
            Objects.equals(nicIssueDate, that.nicIssueDate) &&
            Objects.equals(passportNo, that.passportNo) &&
            Objects.equals(passportIssueDate, that.passportIssueDate) &&
            Objects.equals(passportExpDate, that.passportExpDate) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(age, that.age) &&
            Objects.equals(bloodGroup, that.bloodGroup) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(phone2, that.phone2) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(marriedDate, that.marriedDate) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(permanentAddress, that.permanentAddress) &&
            Objects.equals(temporaryAddress, that.temporaryAddress) &&
            Objects.equals(home, that.home) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(email, that.email) &&
            Objects.equals(emergencyContactPerson, that.emergencyContactPerson) &&
            Objects.equals(emergencyNumber, that.emergencyNumber) &&
            Objects.equals(city, that.city) &&
            Objects.equals(district, that.district) &&
            Objects.equals(province, that.province) &&
            Objects.equals(electorate, that.electorate) &&
            Objects.equals(mainRoad, that.mainRoad) &&
            Objects.equals(modeOfTransport, that.modeOfTransport) &&
            Objects.equals(distance, that.distance) &&
            Objects.equals(travelTime, that.travelTime) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(departmentID, that.departmentID) &&
            Objects.equals(departmentCode, that.departmentCode) &&
            Objects.equals(empRegDate, that.empRegDate) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(roleName, that.roleName) &&
            Objects.equals(epf, that.epf) &&
            Objects.equals(etf, that.etf) &&
            Objects.equals(dateJoined, that.dateJoined) &&
            Objects.equals(dateResigned, that.dateResigned) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(jobStatusId, that.jobStatusId) &&
            Objects.equals(jobStatusName, that.jobStatusName) &&
            Objects.equals(imagePath, that.imagePath) &&
            Objects.equals(bankAccountNo, that.bankAccountNo) &&
            Objects.equals(bankId, that.bankId) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(salaryPaymentBasis, that.salaryPaymentBasis) &&
            Objects.equals(empStatus, that.empStatus) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(experience, that.experience) &&
            Objects.equals(qualifications, that.qualifications) &&
            Objects.equals(attendanceCode, that.attendanceCode) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            fullName,
            nameWithInitials,
            surname,
            nicNumber,
            nicIssueDate,
            passportNo,
            passportIssueDate,
            passportExpDate,
            dateOfBirth,
            age,
            bloodGroup,
            gender,
            phone2,
            maritalStatus,
            marriedDate,
            nationality,
            permanentAddress,
            temporaryAddress,
            home,
            mobile,
            fax,
            email,
            emergencyContactPerson,
            emergencyNumber,
            city,
            district,
            province,
            electorate,
            mainRoad,
            modeOfTransport,
            distance,
            travelTime,
            username,
            password,
            departmentID,
            departmentCode,
            empRegDate,
            lmu,
            lmd,
            roleId,
            roleName,
            epf,
            etf,
            dateJoined,
            dateResigned,
            designation,
            jobStatusId,
            jobStatusName,
            imagePath,
            bankAccountNo,
            bankId,
            bankName,
            branchId,
            branchName,
            salaryPaymentBasis,
            empStatus,
            religion,
            experience,
            qualifications,
            attendanceCode,
            isActive,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalFullName().map(f -> "fullName=" + f + ", ").orElse("") +
            optionalNameWithInitials().map(f -> "nameWithInitials=" + f + ", ").orElse("") +
            optionalSurname().map(f -> "surname=" + f + ", ").orElse("") +
            optionalNicNumber().map(f -> "nicNumber=" + f + ", ").orElse("") +
            optionalNicIssueDate().map(f -> "nicIssueDate=" + f + ", ").orElse("") +
            optionalPassportNo().map(f -> "passportNo=" + f + ", ").orElse("") +
            optionalPassportIssueDate().map(f -> "passportIssueDate=" + f + ", ").orElse("") +
            optionalPassportExpDate().map(f -> "passportExpDate=" + f + ", ").orElse("") +
            optionalDateOfBirth().map(f -> "dateOfBirth=" + f + ", ").orElse("") +
            optionalAge().map(f -> "age=" + f + ", ").orElse("") +
            optionalBloodGroup().map(f -> "bloodGroup=" + f + ", ").orElse("") +
            optionalGender().map(f -> "gender=" + f + ", ").orElse("") +
            optionalPhone2().map(f -> "phone2=" + f + ", ").orElse("") +
            optionalMaritalStatus().map(f -> "maritalStatus=" + f + ", ").orElse("") +
            optionalMarriedDate().map(f -> "marriedDate=" + f + ", ").orElse("") +
            optionalNationality().map(f -> "nationality=" + f + ", ").orElse("") +
            optionalPermanentAddress().map(f -> "permanentAddress=" + f + ", ").orElse("") +
            optionalTemporaryAddress().map(f -> "temporaryAddress=" + f + ", ").orElse("") +
            optionalHome().map(f -> "home=" + f + ", ").orElse("") +
            optionalMobile().map(f -> "mobile=" + f + ", ").orElse("") +
            optionalFax().map(f -> "fax=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalEmergencyContactPerson().map(f -> "emergencyContactPerson=" + f + ", ").orElse("") +
            optionalEmergencyNumber().map(f -> "emergencyNumber=" + f + ", ").orElse("") +
            optionalCity().map(f -> "city=" + f + ", ").orElse("") +
            optionalDistrict().map(f -> "district=" + f + ", ").orElse("") +
            optionalProvince().map(f -> "province=" + f + ", ").orElse("") +
            optionalElectorate().map(f -> "electorate=" + f + ", ").orElse("") +
            optionalMainRoad().map(f -> "mainRoad=" + f + ", ").orElse("") +
            optionalModeOfTransport().map(f -> "modeOfTransport=" + f + ", ").orElse("") +
            optionalDistance().map(f -> "distance=" + f + ", ").orElse("") +
            optionalTravelTime().map(f -> "travelTime=" + f + ", ").orElse("") +
            optionalUsername().map(f -> "username=" + f + ", ").orElse("") +
            optionalPassword().map(f -> "password=" + f + ", ").orElse("") +
            optionalDepartmentID().map(f -> "departmentID=" + f + ", ").orElse("") +
            optionalDepartmentCode().map(f -> "departmentCode=" + f + ", ").orElse("") +
            optionalEmpRegDate().map(f -> "empRegDate=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalRoleId().map(f -> "roleId=" + f + ", ").orElse("") +
            optionalRoleName().map(f -> "roleName=" + f + ", ").orElse("") +
            optionalEpf().map(f -> "epf=" + f + ", ").orElse("") +
            optionalEtf().map(f -> "etf=" + f + ", ").orElse("") +
            optionalDateJoined().map(f -> "dateJoined=" + f + ", ").orElse("") +
            optionalDateResigned().map(f -> "dateResigned=" + f + ", ").orElse("") +
            optionalDesignation().map(f -> "designation=" + f + ", ").orElse("") +
            optionalJobStatusId().map(f -> "jobStatusId=" + f + ", ").orElse("") +
            optionalJobStatusName().map(f -> "jobStatusName=" + f + ", ").orElse("") +
            optionalImagePath().map(f -> "imagePath=" + f + ", ").orElse("") +
            optionalBankAccountNo().map(f -> "bankAccountNo=" + f + ", ").orElse("") +
            optionalBankId().map(f -> "bankId=" + f + ", ").orElse("") +
            optionalBankName().map(f -> "bankName=" + f + ", ").orElse("") +
            optionalBranchId().map(f -> "branchId=" + f + ", ").orElse("") +
            optionalBranchName().map(f -> "branchName=" + f + ", ").orElse("") +
            optionalSalaryPaymentBasis().map(f -> "salaryPaymentBasis=" + f + ", ").orElse("") +
            optionalEmpStatus().map(f -> "empStatus=" + f + ", ").orElse("") +
            optionalReligion().map(f -> "religion=" + f + ", ").orElse("") +
            optionalExperience().map(f -> "experience=" + f + ", ").orElse("") +
            optionalQualifications().map(f -> "qualifications=" + f + ", ").orElse("") +
            optionalAttendanceCode().map(f -> "attendanceCode=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
