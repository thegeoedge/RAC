package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE depending on your DB
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "namewithinitials")
    private String nameWithInitials;

    @Column(name = "surname")
    private String surname;

    @Column(name = "nicnumber")
    private String nicNumber;

    @Column(name = "nicissuedate")
    private LocalDate nicIssueDate;

    @Column(name = "passortno")
    private String passportNo;

    @Column(name = "passportissuedate")
    private LocalDate passportIssueDate;

    @Column(name = "passportexpdate")
    private LocalDate passportExpDate;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "bloodgroup")
    private String bloodGroup;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "maritalstatus")
    private String maritalStatus;

    @Column(name = "marrieddate")
    private LocalDate marriedDate;

    @Column(name = "natinality")
    private String nationality;

    @Column(name = "permenentaddress")
    private String permanentAddress;

    @Column(name = "temporaryaddress")
    private String temporaryAddress;

    @Column(name = "home")
    private String home;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "emergencycontactperson")
    private String emergencyContactPerson;

    @Column(name = "emergencynumber")
    private String emergencyNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "distric")
    private String district;

    @Column(name = "province")
    private String province;

    @Column(name = "electorate")
    private String electorate;

    @Column(name = "mainraod")
    private String mainRoad;

    @Column(name = "modeoftransport")
    private String modeOfTransport;

    @Column(name = "distance")
    private String distance;

    @Column(name = "travltime")
    private String travelTime;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "departmentid")
    private Integer departmentID;

    @Column(name = "departmentcode")
    private String departmentCode;

    @Column(name = "empregdate")
    private LocalDate empRegDate;

    @Column(name = "lmu")
    private String lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "rolename")
    private String roleName;

    @Column(name = "epf")
    private String epf;

    @Column(name = "etf")
    private String etf;

    @Column(name = "datejoined")
    private Instant dateJoined;

    @Column(name = "dateresigned")
    private Instant dateResigned;

    @Column(name = "designation")
    private String designation;

    @Column(name = "jobstatusid")
    private Integer jobStatusId;

    @Column(name = "jobstatusname")
    private String jobStatusName;

    @Column(name = "imagepath")
    private String imagePath;

    @Column(name = "bankaccountno")
    private String bankAccountNo;

    @Column(name = "bankid")
    private Integer bankId;

    @Column(name = "bankname")
    private String bankName;

    @Column(name = "branchid")
    private Integer branchId;

    @Column(name = "branchname")
    private String branchName;

    @Column(name = "salarypaymentbasis")
    private String salaryPaymentBasis;

    @Column(name = "empstatus")
    private String empStatus;

    @Column(name = "religion")
    private String religion;

    @Column(name = "experience")
    private String experience;

    @Column(name = "qualifications")
    private String qualifications;

    @Column(name = "attendencecode")
    private String attendanceCode;

    @Column(name = "isactive")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Employee code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Employee fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNameWithInitials() {
        return this.nameWithInitials;
    }

    public Employee nameWithInitials(String nameWithInitials) {
        this.setNameWithInitials(nameWithInitials);
        return this;
    }

    public void setNameWithInitials(String nameWithInitials) {
        this.nameWithInitials = nameWithInitials;
    }

    public String getSurname() {
        return this.surname;
    }

    public Employee surname(String surname) {
        this.setSurname(surname);
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNicNumber() {
        return this.nicNumber;
    }

    public Employee nicNumber(String nicNumber) {
        this.setNicNumber(nicNumber);
        return this;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public LocalDate getNicIssueDate() {
        return this.nicIssueDate;
    }

    public Employee nicIssueDate(LocalDate nicIssueDate) {
        this.setNicIssueDate(nicIssueDate);
        return this;
    }

    public void setNicIssueDate(LocalDate nicIssueDate) {
        this.nicIssueDate = nicIssueDate;
    }

    public String getPassportNo() {
        return this.passportNo;
    }

    public Employee passportNo(String passportNo) {
        this.setPassportNo(passportNo);
        return this;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public LocalDate getPassportIssueDate() {
        return this.passportIssueDate;
    }

    public Employee passportIssueDate(LocalDate passportIssueDate) {
        this.setPassportIssueDate(passportIssueDate);
        return this;
    }

    public void setPassportIssueDate(LocalDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public LocalDate getPassportExpDate() {
        return this.passportExpDate;
    }

    public Employee passportExpDate(LocalDate passportExpDate) {
        this.setPassportExpDate(passportExpDate);
        return this;
    }

    public void setPassportExpDate(LocalDate passportExpDate) {
        this.passportExpDate = passportExpDate;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Employee dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return this.age;
    }

    public Employee age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return this.bloodGroup;
    }

    public Employee bloodGroup(String bloodGroup) {
        this.setBloodGroup(bloodGroup);
        return this;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGender() {
        return this.gender;
    }

    public Employee gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public Employee phone2(String phone2) {
        this.setPhone2(phone2);
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public Employee maritalStatus(String maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public LocalDate getMarriedDate() {
        return this.marriedDate;
    }

    public Employee marriedDate(LocalDate marriedDate) {
        this.setMarriedDate(marriedDate);
        return this;
    }

    public void setMarriedDate(LocalDate marriedDate) {
        this.marriedDate = marriedDate;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Employee nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPermanentAddress() {
        return this.permanentAddress;
    }

    public Employee permanentAddress(String permanentAddress) {
        this.setPermanentAddress(permanentAddress);
        return this;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getTemporaryAddress() {
        return this.temporaryAddress;
    }

    public Employee temporaryAddress(String temporaryAddress) {
        this.setTemporaryAddress(temporaryAddress);
        return this;
    }

    public void setTemporaryAddress(String temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public String getHome() {
        return this.home;
    }

    public Employee home(String home) {
        this.setHome(home);
        return this;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Employee mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return this.fax;
    }

    public Employee fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContactPerson() {
        return this.emergencyContactPerson;
    }

    public Employee emergencyContactPerson(String emergencyContactPerson) {
        this.setEmergencyContactPerson(emergencyContactPerson);
        return this;
    }

    public void setEmergencyContactPerson(String emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson;
    }

    public String getEmergencyNumber() {
        return this.emergencyNumber;
    }

    public Employee emergencyNumber(String emergencyNumber) {
        this.setEmergencyNumber(emergencyNumber);
        return this;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getCity() {
        return this.city;
    }

    public Employee city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }

    public Employee district(String district) {
        this.setDistrict(district);
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return this.province;
    }

    public Employee province(String province) {
        this.setProvince(province);
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getElectorate() {
        return this.electorate;
    }

    public Employee electorate(String electorate) {
        this.setElectorate(electorate);
        return this;
    }

    public void setElectorate(String electorate) {
        this.electorate = electorate;
    }

    public String getMainRoad() {
        return this.mainRoad;
    }

    public Employee mainRoad(String mainRoad) {
        this.setMainRoad(mainRoad);
        return this;
    }

    public void setMainRoad(String mainRoad) {
        this.mainRoad = mainRoad;
    }

    public String getModeOfTransport() {
        return this.modeOfTransport;
    }

    public Employee modeOfTransport(String modeOfTransport) {
        this.setModeOfTransport(modeOfTransport);
        return this;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public String getDistance() {
        return this.distance;
    }

    public Employee distance(String distance) {
        this.setDistance(distance);
        return this;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTravelTime() {
        return this.travelTime;
    }

    public Employee travelTime(String travelTime) {
        this.setTravelTime(travelTime);
        return this;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getUsername() {
        return this.username;
    }

    public Employee username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Employee password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDepartmentID() {
        return this.departmentID;
    }

    public Employee departmentID(Integer departmentID) {
        this.setDepartmentID(departmentID);
        return this;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentCode() {
        return this.departmentCode;
    }

    public Employee departmentCode(String departmentCode) {
        this.setDepartmentCode(departmentCode);
        return this;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public LocalDate getEmpRegDate() {
        return this.empRegDate;
    }

    public Employee empRegDate(LocalDate empRegDate) {
        this.setEmpRegDate(empRegDate);
        return this;
    }

    public void setEmpRegDate(LocalDate empRegDate) {
        this.empRegDate = empRegDate;
    }

    public String getLmu() {
        return this.lmu;
    }

    public Employee lmu(String lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(String lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public Employee lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public Employee roleId(Integer roleId) {
        this.setRoleId(roleId);
        return this;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public Employee roleName(String roleName) {
        this.setRoleName(roleName);
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEpf() {
        return this.epf;
    }

    public Employee epf(String epf) {
        this.setEpf(epf);
        return this;
    }

    public void setEpf(String epf) {
        this.epf = epf;
    }

    public String getEtf() {
        return this.etf;
    }

    public Employee etf(String etf) {
        this.setEtf(etf);
        return this;
    }

    public void setEtf(String etf) {
        this.etf = etf;
    }

    public Instant getDateJoined() {
        return this.dateJoined;
    }

    public Employee dateJoined(Instant dateJoined) {
        this.setDateJoined(dateJoined);
        return this;
    }

    public void setDateJoined(Instant dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Instant getDateResigned() {
        return this.dateResigned;
    }

    public Employee dateResigned(Instant dateResigned) {
        this.setDateResigned(dateResigned);
        return this;
    }

    public void setDateResigned(Instant dateResigned) {
        this.dateResigned = dateResigned;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Employee designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getJobStatusId() {
        return this.jobStatusId;
    }

    public Employee jobStatusId(Integer jobStatusId) {
        this.setJobStatusId(jobStatusId);
        return this;
    }

    public void setJobStatusId(Integer jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public String getJobStatusName() {
        return this.jobStatusName;
    }

    public Employee jobStatusName(String jobStatusName) {
        this.setJobStatusName(jobStatusName);
        return this;
    }

    public void setJobStatusName(String jobStatusName) {
        this.jobStatusName = jobStatusName;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Employee imagePath(String imagePath) {
        this.setImagePath(imagePath);
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBankAccountNo() {
        return this.bankAccountNo;
    }

    public Employee bankAccountNo(String bankAccountNo) {
        this.setBankAccountNo(bankAccountNo);
        return this;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public Integer getBankId() {
        return this.bankId;
    }

    public Employee bankId(Integer bankId) {
        this.setBankId(bankId);
        return this;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return this.bankName;
    }

    public Employee bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getBranchId() {
        return this.branchId;
    }

    public Employee branchId(Integer branchId) {
        this.setBranchId(branchId);
        return this;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public Employee branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSalaryPaymentBasis() {
        return this.salaryPaymentBasis;
    }

    public Employee salaryPaymentBasis(String salaryPaymentBasis) {
        this.setSalaryPaymentBasis(salaryPaymentBasis);
        return this;
    }

    public void setSalaryPaymentBasis(String salaryPaymentBasis) {
        this.salaryPaymentBasis = salaryPaymentBasis;
    }

    public String getEmpStatus() {
        return this.empStatus;
    }

    public Employee empStatus(String empStatus) {
        this.setEmpStatus(empStatus);
        return this;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public String getReligion() {
        return this.religion;
    }

    public Employee religion(String religion) {
        this.setReligion(religion);
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getExperience() {
        return this.experience;
    }

    public Employee experience(String experience) {
        this.setExperience(experience);
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualifications() {
        return this.qualifications;
    }

    public Employee qualifications(String qualifications) {
        this.setQualifications(qualifications);
        return this;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getAttendanceCode() {
        return this.attendanceCode;
    }

    public Employee attendanceCode(String attendanceCode) {
        this.setAttendanceCode(attendanceCode);
        return this;
    }

    public void setAttendanceCode(String attendanceCode) {
        this.attendanceCode = attendanceCode;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Employee isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", nameWithInitials='" + getNameWithInitials() + "'" +
            ", surname='" + getSurname() + "'" +
            ", nicNumber='" + getNicNumber() + "'" +
            ", nicIssueDate='" + getNicIssueDate() + "'" +
            ", passportNo='" + getPassportNo() + "'" +
            ", passportIssueDate='" + getPassportIssueDate() + "'" +
            ", passportExpDate='" + getPassportExpDate() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", age=" + getAge() +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", gender='" + getGender() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", marriedDate='" + getMarriedDate() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", permanentAddress='" + getPermanentAddress() + "'" +
            ", temporaryAddress='" + getTemporaryAddress() + "'" +
            ", home='" + getHome() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", emergencyContactPerson='" + getEmergencyContactPerson() + "'" +
            ", emergencyNumber='" + getEmergencyNumber() + "'" +
            ", city='" + getCity() + "'" +
            ", district='" + getDistrict() + "'" +
            ", province='" + getProvince() + "'" +
            ", electorate='" + getElectorate() + "'" +
            ", mainRoad='" + getMainRoad() + "'" +
            ", modeOfTransport='" + getModeOfTransport() + "'" +
            ", distance='" + getDistance() + "'" +
            ", travelTime='" + getTravelTime() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", departmentID=" + getDepartmentID() +
            ", departmentCode='" + getDepartmentCode() + "'" +
            ", empRegDate='" + getEmpRegDate() + "'" +
            ", lmu='" + getLmu() + "'" +
            ", lmd='" + getLmd() + "'" +
            ", roleId=" + getRoleId() +
            ", roleName='" + getRoleName() + "'" +
            ", epf='" + getEpf() + "'" +
            ", etf='" + getEtf() + "'" +
            ", dateJoined='" + getDateJoined() + "'" +
            ", dateResigned='" + getDateResigned() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", jobStatusId=" + getJobStatusId() +
            ", jobStatusName='" + getJobStatusName() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", bankAccountNo='" + getBankAccountNo() + "'" +
            ", bankId=" + getBankId() +
            ", bankName='" + getBankName() + "'" +
            ", branchId=" + getBranchId() +
            ", branchName='" + getBranchName() + "'" +
            ", salaryPaymentBasis='" + getSalaryPaymentBasis() + "'" +
            ", empStatus='" + getEmpStatus() + "'" +
            ", religion='" + getReligion() + "'" +
            ", experience='" + getExperience() + "'" +
            ", qualifications='" + getQualifications() + "'" +
            ", attendanceCode='" + getAttendanceCode() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
