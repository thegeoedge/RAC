package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.EmployeeAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Employee;
import com.heavenscode.rac.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_WITH_INITIALS = "AAAAAAAAAA";
    private static final String UPDATED_NAME_WITH_INITIALS = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NIC_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NIC_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NIC_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NIC_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NIC_ISSUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PASSPORT_NO = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PASSPORT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PASSPORT_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PASSPORT_ISSUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_PASSPORT_EXP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PASSPORT_EXP_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PASSPORT_EXP_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String DEFAULT_BLOOD_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_BLOOD_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MARRIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MARRIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MARRIED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPORARY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_TEMPORARY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HOME = "AAAAAAAAAA";
    private static final String UPDATED_HOME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMERGENCY_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_EMERGENCY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_ELECTORATE = "AAAAAAAAAA";
    private static final String UPDATED_ELECTORATE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_ROAD = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_ROAD = "BBBBBBBBBB";

    private static final String DEFAULT_MODE_OF_TRANSPORT = "AAAAAAAAAA";
    private static final String UPDATED_MODE_OF_TRANSPORT = "BBBBBBBBBB";

    private static final String DEFAULT_DISTANCE = "AAAAAAAAAA";
    private static final String UPDATED_DISTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_TRAVEL_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TRAVEL_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEPARTMENT_ID = 1;
    private static final Integer UPDATED_DEPARTMENT_ID = 2;
    private static final Integer SMALLER_DEPARTMENT_ID = 1 - 1;

    private static final String DEFAULT_DEPARTMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMP_REG_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMP_REG_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EMP_REG_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_LMU = "AAAAAAAAAA";
    private static final String UPDATED_LMU = "BBBBBBBBBB";

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ROLE_ID = 1;
    private static final Integer UPDATED_ROLE_ID = 2;
    private static final Integer SMALLER_ROLE_ID = 1 - 1;

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EPF = "AAAAAAAAAA";
    private static final String UPDATED_EPF = "BBBBBBBBBB";

    private static final String DEFAULT_ETF = "AAAAAAAAAA";
    private static final String UPDATED_ETF = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_JOINED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_JOINED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_RESIGNED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_RESIGNED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_JOB_STATUS_ID = 1;
    private static final Integer UPDATED_JOB_STATUS_ID = 2;
    private static final Integer SMALLER_JOB_STATUS_ID = 1 - 1;

    private static final String DEFAULT_JOB_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_STATUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_BANK_ID = 1;
    private static final Integer UPDATED_BANK_ID = 2;
    private static final Integer SMALLER_BANK_ID = 1 - 1;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BRANCH_ID = 1;
    private static final Integer UPDATED_BRANCH_ID = 2;
    private static final Integer SMALLER_BRANCH_ID = 1 - 1;

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SALARY_PAYMENT_BASIS = "AAAAAAAAAA";
    private static final String UPDATED_SALARY_PAYMENT_BASIS = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EMP_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RELIGION = "AAAAAAAAAA";
    private static final String UPDATED_RELIGION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATIONS = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_ATTENDANCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDANCE_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    private Employee insertedEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity() {
        return new Employee()
            .code(DEFAULT_CODE)
            .fullName(DEFAULT_FULL_NAME)
            .nameWithInitials(DEFAULT_NAME_WITH_INITIALS)
            .surname(DEFAULT_SURNAME)
            .nicNumber(DEFAULT_NIC_NUMBER)
            .nicIssueDate(DEFAULT_NIC_ISSUE_DATE)
            .passportNo(DEFAULT_PASSPORT_NO)
            .passportIssueDate(DEFAULT_PASSPORT_ISSUE_DATE)
            .passportExpDate(DEFAULT_PASSPORT_EXP_DATE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .age(DEFAULT_AGE)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .gender(DEFAULT_GENDER)
            .phone2(DEFAULT_PHONE_2)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .marriedDate(DEFAULT_MARRIED_DATE)
            .nationality(DEFAULT_NATIONALITY)
            .permanentAddress(DEFAULT_PERMANENT_ADDRESS)
            .temporaryAddress(DEFAULT_TEMPORARY_ADDRESS)
            .home(DEFAULT_HOME)
            .mobile(DEFAULT_MOBILE)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .emergencyContactPerson(DEFAULT_EMERGENCY_CONTACT_PERSON)
            .emergencyNumber(DEFAULT_EMERGENCY_NUMBER)
            .city(DEFAULT_CITY)
            .district(DEFAULT_DISTRICT)
            .province(DEFAULT_PROVINCE)
            .electorate(DEFAULT_ELECTORATE)
            .mainRoad(DEFAULT_MAIN_ROAD)
            .modeOfTransport(DEFAULT_MODE_OF_TRANSPORT)
            .distance(DEFAULT_DISTANCE)
            .travelTime(DEFAULT_TRAVEL_TIME)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .departmentID(DEFAULT_DEPARTMENT_ID)
            .departmentCode(DEFAULT_DEPARTMENT_CODE)
            .empRegDate(DEFAULT_EMP_REG_DATE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .roleId(DEFAULT_ROLE_ID)
            .roleName(DEFAULT_ROLE_NAME)
            .epf(DEFAULT_EPF)
            .etf(DEFAULT_ETF)
            .dateJoined(DEFAULT_DATE_JOINED)
            .dateResigned(DEFAULT_DATE_RESIGNED)
            .designation(DEFAULT_DESIGNATION)
            .jobStatusId(DEFAULT_JOB_STATUS_ID)
            .jobStatusName(DEFAULT_JOB_STATUS_NAME)
            .imagePath(DEFAULT_IMAGE_PATH)
            .bankAccountNo(DEFAULT_BANK_ACCOUNT_NO)
            .bankId(DEFAULT_BANK_ID)
            .bankName(DEFAULT_BANK_NAME)
            .branchId(DEFAULT_BRANCH_ID)
            .branchName(DEFAULT_BRANCH_NAME)
            .salaryPaymentBasis(DEFAULT_SALARY_PAYMENT_BASIS)
            .empStatus(DEFAULT_EMP_STATUS)
            .religion(DEFAULT_RELIGION)
            .experience(DEFAULT_EXPERIENCE)
            .qualifications(DEFAULT_QUALIFICATIONS)
            .attendanceCode(DEFAULT_ATTENDANCE_CODE)
            .isActive(DEFAULT_IS_ACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity() {
        return new Employee()
            .code(UPDATED_CODE)
            .fullName(UPDATED_FULL_NAME)
            .nameWithInitials(UPDATED_NAME_WITH_INITIALS)
            .surname(UPDATED_SURNAME)
            .nicNumber(UPDATED_NIC_NUMBER)
            .nicIssueDate(UPDATED_NIC_ISSUE_DATE)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportIssueDate(UPDATED_PASSPORT_ISSUE_DATE)
            .passportExpDate(UPDATED_PASSPORT_EXP_DATE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .age(UPDATED_AGE)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .gender(UPDATED_GENDER)
            .phone2(UPDATED_PHONE_2)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .marriedDate(UPDATED_MARRIED_DATE)
            .nationality(UPDATED_NATIONALITY)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .temporaryAddress(UPDATED_TEMPORARY_ADDRESS)
            .home(UPDATED_HOME)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .emergencyContactPerson(UPDATED_EMERGENCY_CONTACT_PERSON)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .province(UPDATED_PROVINCE)
            .electorate(UPDATED_ELECTORATE)
            .mainRoad(UPDATED_MAIN_ROAD)
            .modeOfTransport(UPDATED_MODE_OF_TRANSPORT)
            .distance(UPDATED_DISTANCE)
            .travelTime(UPDATED_TRAVEL_TIME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .departmentID(UPDATED_DEPARTMENT_ID)
            .departmentCode(UPDATED_DEPARTMENT_CODE)
            .empRegDate(UPDATED_EMP_REG_DATE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .roleId(UPDATED_ROLE_ID)
            .roleName(UPDATED_ROLE_NAME)
            .epf(UPDATED_EPF)
            .etf(UPDATED_ETF)
            .dateJoined(UPDATED_DATE_JOINED)
            .dateResigned(UPDATED_DATE_RESIGNED)
            .designation(UPDATED_DESIGNATION)
            .jobStatusId(UPDATED_JOB_STATUS_ID)
            .jobStatusName(UPDATED_JOB_STATUS_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .bankAccountNo(UPDATED_BANK_ACCOUNT_NO)
            .bankId(UPDATED_BANK_ID)
            .bankName(UPDATED_BANK_NAME)
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME)
            .salaryPaymentBasis(UPDATED_SALARY_PAYMENT_BASIS)
            .empStatus(UPDATED_EMP_STATUS)
            .religion(UPDATED_RELIGION)
            .experience(UPDATED_EXPERIENCE)
            .qualifications(UPDATED_QUALIFICATIONS)
            .attendanceCode(UPDATED_ATTENDANCE_CODE)
            .isActive(UPDATED_IS_ACTIVE);
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmployee != null) {
            employeeRepository.delete(insertedEmployee);
            insertedEmployee = null;
        }
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Employee
        var returnedEmployee = om.readValue(
            restEmployeeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employee)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Employee.class
        );

        // Validate the Employee in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmployeeUpdatableFieldsEquals(returnedEmployee, getPersistedEmployee(returnedEmployee));

        insertedEmployee = returnedEmployee;
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].nameWithInitials").value(hasItem(DEFAULT_NAME_WITH_INITIALS)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].nicNumber").value(hasItem(DEFAULT_NIC_NUMBER)))
            .andExpect(jsonPath("$.[*].nicIssueDate").value(hasItem(DEFAULT_NIC_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportIssueDate").value(hasItem(DEFAULT_PASSPORT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].passportExpDate").value(hasItem(DEFAULT_PASSPORT_EXP_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].marriedDate").value(hasItem(DEFAULT_MARRIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].temporaryAddress").value(hasItem(DEFAULT_TEMPORARY_ADDRESS)))
            .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].emergencyContactPerson").value(hasItem(DEFAULT_EMERGENCY_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].emergencyNumber").value(hasItem(DEFAULT_EMERGENCY_NUMBER)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].electorate").value(hasItem(DEFAULT_ELECTORATE)))
            .andExpect(jsonPath("$.[*].mainRoad").value(hasItem(DEFAULT_MAIN_ROAD)))
            .andExpect(jsonPath("$.[*].modeOfTransport").value(hasItem(DEFAULT_MODE_OF_TRANSPORT)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].travelTime").value(hasItem(DEFAULT_TRAVEL_TIME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].departmentID").value(hasItem(DEFAULT_DEPARTMENT_ID)))
            .andExpect(jsonPath("$.[*].departmentCode").value(hasItem(DEFAULT_DEPARTMENT_CODE)))
            .andExpect(jsonPath("$.[*].empRegDate").value(hasItem(DEFAULT_EMP_REG_DATE.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)))
            .andExpect(jsonPath("$.[*].epf").value(hasItem(DEFAULT_EPF)))
            .andExpect(jsonPath("$.[*].etf").value(hasItem(DEFAULT_ETF)))
            .andExpect(jsonPath("$.[*].dateJoined").value(hasItem(DEFAULT_DATE_JOINED.toString())))
            .andExpect(jsonPath("$.[*].dateResigned").value(hasItem(DEFAULT_DATE_RESIGNED.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].jobStatusId").value(hasItem(DEFAULT_JOB_STATUS_ID)))
            .andExpect(jsonPath("$.[*].jobStatusName").value(hasItem(DEFAULT_JOB_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].bankId").value(hasItem(DEFAULT_BANK_ID)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].salaryPaymentBasis").value(hasItem(DEFAULT_SALARY_PAYMENT_BASIS)))
            .andExpect(jsonPath("$.[*].empStatus").value(hasItem(DEFAULT_EMP_STATUS)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].qualifications").value(hasItem(DEFAULT_QUALIFICATIONS)))
            .andExpect(jsonPath("$.[*].attendanceCode").value(hasItem(DEFAULT_ATTENDANCE_CODE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.nameWithInitials").value(DEFAULT_NAME_WITH_INITIALS))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.nicNumber").value(DEFAULT_NIC_NUMBER))
            .andExpect(jsonPath("$.nicIssueDate").value(DEFAULT_NIC_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.passportNo").value(DEFAULT_PASSPORT_NO))
            .andExpect(jsonPath("$.passportIssueDate").value(DEFAULT_PASSPORT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.passportExpDate").value(DEFAULT_PASSPORT_EXP_DATE.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.marriedDate").value(DEFAULT_MARRIED_DATE.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS))
            .andExpect(jsonPath("$.temporaryAddress").value(DEFAULT_TEMPORARY_ADDRESS))
            .andExpect(jsonPath("$.home").value(DEFAULT_HOME))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.emergencyContactPerson").value(DEFAULT_EMERGENCY_CONTACT_PERSON))
            .andExpect(jsonPath("$.emergencyNumber").value(DEFAULT_EMERGENCY_NUMBER))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.electorate").value(DEFAULT_ELECTORATE))
            .andExpect(jsonPath("$.mainRoad").value(DEFAULT_MAIN_ROAD))
            .andExpect(jsonPath("$.modeOfTransport").value(DEFAULT_MODE_OF_TRANSPORT))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE))
            .andExpect(jsonPath("$.travelTime").value(DEFAULT_TRAVEL_TIME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.departmentID").value(DEFAULT_DEPARTMENT_ID))
            .andExpect(jsonPath("$.departmentCode").value(DEFAULT_DEPARTMENT_CODE))
            .andExpect(jsonPath("$.empRegDate").value(DEFAULT_EMP_REG_DATE.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME))
            .andExpect(jsonPath("$.epf").value(DEFAULT_EPF))
            .andExpect(jsonPath("$.etf").value(DEFAULT_ETF))
            .andExpect(jsonPath("$.dateJoined").value(DEFAULT_DATE_JOINED.toString()))
            .andExpect(jsonPath("$.dateResigned").value(DEFAULT_DATE_RESIGNED.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.jobStatusId").value(DEFAULT_JOB_STATUS_ID))
            .andExpect(jsonPath("$.jobStatusName").value(DEFAULT_JOB_STATUS_NAME))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO))
            .andExpect(jsonPath("$.bankId").value(DEFAULT_BANK_ID))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.salaryPaymentBasis").value(DEFAULT_SALARY_PAYMENT_BASIS))
            .andExpect(jsonPath("$.empStatus").value(DEFAULT_EMP_STATUS))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.qualifications").value(DEFAULT_QUALIFICATIONS))
            .andExpect(jsonPath("$.attendanceCode").value(DEFAULT_ATTENDANCE_CODE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEmployeeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEmployeeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code equals to
        defaultEmployeeFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code in
        defaultEmployeeFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code is not null
        defaultEmployeeFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code contains
        defaultEmployeeFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where code does not contain
        defaultEmployeeFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName equals to
        defaultEmployeeFiltering("fullName.equals=" + DEFAULT_FULL_NAME, "fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName in
        defaultEmployeeFiltering("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME, "fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName is not null
        defaultEmployeeFiltering("fullName.specified=true", "fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName contains
        defaultEmployeeFiltering("fullName.contains=" + DEFAULT_FULL_NAME, "fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName does not contain
        defaultEmployeeFiltering("fullName.doesNotContain=" + UPDATED_FULL_NAME, "fullName.doesNotContain=" + DEFAULT_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByNameWithInitialsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nameWithInitials equals to
        defaultEmployeeFiltering(
            "nameWithInitials.equals=" + DEFAULT_NAME_WITH_INITIALS,
            "nameWithInitials.equals=" + UPDATED_NAME_WITH_INITIALS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNameWithInitialsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nameWithInitials in
        defaultEmployeeFiltering(
            "nameWithInitials.in=" + DEFAULT_NAME_WITH_INITIALS + "," + UPDATED_NAME_WITH_INITIALS,
            "nameWithInitials.in=" + UPDATED_NAME_WITH_INITIALS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNameWithInitialsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nameWithInitials is not null
        defaultEmployeeFiltering("nameWithInitials.specified=true", "nameWithInitials.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNameWithInitialsContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nameWithInitials contains
        defaultEmployeeFiltering(
            "nameWithInitials.contains=" + DEFAULT_NAME_WITH_INITIALS,
            "nameWithInitials.contains=" + UPDATED_NAME_WITH_INITIALS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNameWithInitialsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nameWithInitials does not contain
        defaultEmployeeFiltering(
            "nameWithInitials.doesNotContain=" + UPDATED_NAME_WITH_INITIALS,
            "nameWithInitials.doesNotContain=" + DEFAULT_NAME_WITH_INITIALS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySurnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where surname equals to
        defaultEmployeeFiltering("surname.equals=" + DEFAULT_SURNAME, "surname.equals=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySurnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where surname in
        defaultEmployeeFiltering("surname.in=" + DEFAULT_SURNAME + "," + UPDATED_SURNAME, "surname.in=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySurnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where surname is not null
        defaultEmployeeFiltering("surname.specified=true", "surname.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySurnameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where surname contains
        defaultEmployeeFiltering("surname.contains=" + DEFAULT_SURNAME, "surname.contains=" + UPDATED_SURNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySurnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where surname does not contain
        defaultEmployeeFiltering("surname.doesNotContain=" + UPDATED_SURNAME, "surname.doesNotContain=" + DEFAULT_SURNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByNicNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicNumber equals to
        defaultEmployeeFiltering("nicNumber.equals=" + DEFAULT_NIC_NUMBER, "nicNumber.equals=" + UPDATED_NIC_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByNicNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicNumber in
        defaultEmployeeFiltering("nicNumber.in=" + DEFAULT_NIC_NUMBER + "," + UPDATED_NIC_NUMBER, "nicNumber.in=" + UPDATED_NIC_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByNicNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicNumber is not null
        defaultEmployeeFiltering("nicNumber.specified=true", "nicNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNicNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicNumber contains
        defaultEmployeeFiltering("nicNumber.contains=" + DEFAULT_NIC_NUMBER, "nicNumber.contains=" + UPDATED_NIC_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByNicNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicNumber does not contain
        defaultEmployeeFiltering("nicNumber.doesNotContain=" + UPDATED_NIC_NUMBER, "nicNumber.doesNotContain=" + DEFAULT_NIC_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByNicIssueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicIssueDate equals to
        defaultEmployeeFiltering("nicIssueDate.equals=" + DEFAULT_NIC_ISSUE_DATE, "nicIssueDate.equals=" + UPDATED_NIC_ISSUE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByNicIssueDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicIssueDate in
        defaultEmployeeFiltering(
            "nicIssueDate.in=" + DEFAULT_NIC_ISSUE_DATE + "," + UPDATED_NIC_ISSUE_DATE,
            "nicIssueDate.in=" + UPDATED_NIC_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNicIssueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicIssueDate is not null
        defaultEmployeeFiltering("nicIssueDate.specified=true", "nicIssueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNicIssueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicIssueDate is greater than or equal to
        defaultEmployeeFiltering(
            "nicIssueDate.greaterThanOrEqual=" + DEFAULT_NIC_ISSUE_DATE,
            "nicIssueDate.greaterThanOrEqual=" + UPDATED_NIC_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNicIssueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicIssueDate is less than or equal to
        defaultEmployeeFiltering(
            "nicIssueDate.lessThanOrEqual=" + DEFAULT_NIC_ISSUE_DATE,
            "nicIssueDate.lessThanOrEqual=" + SMALLER_NIC_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNicIssueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicIssueDate is less than
        defaultEmployeeFiltering("nicIssueDate.lessThan=" + UPDATED_NIC_ISSUE_DATE, "nicIssueDate.lessThan=" + DEFAULT_NIC_ISSUE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByNicIssueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nicIssueDate is greater than
        defaultEmployeeFiltering(
            "nicIssueDate.greaterThan=" + SMALLER_NIC_ISSUE_DATE,
            "nicIssueDate.greaterThan=" + DEFAULT_NIC_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportNo equals to
        defaultEmployeeFiltering("passportNo.equals=" + DEFAULT_PASSPORT_NO, "passportNo.equals=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportNo in
        defaultEmployeeFiltering(
            "passportNo.in=" + DEFAULT_PASSPORT_NO + "," + UPDATED_PASSPORT_NO,
            "passportNo.in=" + UPDATED_PASSPORT_NO
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportNo is not null
        defaultEmployeeFiltering("passportNo.specified=true", "passportNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportNoContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportNo contains
        defaultEmployeeFiltering("passportNo.contains=" + DEFAULT_PASSPORT_NO, "passportNo.contains=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportNoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportNo does not contain
        defaultEmployeeFiltering("passportNo.doesNotContain=" + UPDATED_PASSPORT_NO, "passportNo.doesNotContain=" + DEFAULT_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportIssueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportIssueDate equals to
        defaultEmployeeFiltering(
            "passportIssueDate.equals=" + DEFAULT_PASSPORT_ISSUE_DATE,
            "passportIssueDate.equals=" + UPDATED_PASSPORT_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportIssueDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportIssueDate in
        defaultEmployeeFiltering(
            "passportIssueDate.in=" + DEFAULT_PASSPORT_ISSUE_DATE + "," + UPDATED_PASSPORT_ISSUE_DATE,
            "passportIssueDate.in=" + UPDATED_PASSPORT_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportIssueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportIssueDate is not null
        defaultEmployeeFiltering("passportIssueDate.specified=true", "passportIssueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportIssueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportIssueDate is greater than or equal to
        defaultEmployeeFiltering(
            "passportIssueDate.greaterThanOrEqual=" + DEFAULT_PASSPORT_ISSUE_DATE,
            "passportIssueDate.greaterThanOrEqual=" + UPDATED_PASSPORT_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportIssueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportIssueDate is less than or equal to
        defaultEmployeeFiltering(
            "passportIssueDate.lessThanOrEqual=" + DEFAULT_PASSPORT_ISSUE_DATE,
            "passportIssueDate.lessThanOrEqual=" + SMALLER_PASSPORT_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportIssueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportIssueDate is less than
        defaultEmployeeFiltering(
            "passportIssueDate.lessThan=" + UPDATED_PASSPORT_ISSUE_DATE,
            "passportIssueDate.lessThan=" + DEFAULT_PASSPORT_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportIssueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportIssueDate is greater than
        defaultEmployeeFiltering(
            "passportIssueDate.greaterThan=" + SMALLER_PASSPORT_ISSUE_DATE,
            "passportIssueDate.greaterThan=" + DEFAULT_PASSPORT_ISSUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportExpDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportExpDate equals to
        defaultEmployeeFiltering(
            "passportExpDate.equals=" + DEFAULT_PASSPORT_EXP_DATE,
            "passportExpDate.equals=" + UPDATED_PASSPORT_EXP_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportExpDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportExpDate in
        defaultEmployeeFiltering(
            "passportExpDate.in=" + DEFAULT_PASSPORT_EXP_DATE + "," + UPDATED_PASSPORT_EXP_DATE,
            "passportExpDate.in=" + UPDATED_PASSPORT_EXP_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportExpDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportExpDate is not null
        defaultEmployeeFiltering("passportExpDate.specified=true", "passportExpDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportExpDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportExpDate is greater than or equal to
        defaultEmployeeFiltering(
            "passportExpDate.greaterThanOrEqual=" + DEFAULT_PASSPORT_EXP_DATE,
            "passportExpDate.greaterThanOrEqual=" + UPDATED_PASSPORT_EXP_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportExpDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportExpDate is less than or equal to
        defaultEmployeeFiltering(
            "passportExpDate.lessThanOrEqual=" + DEFAULT_PASSPORT_EXP_DATE,
            "passportExpDate.lessThanOrEqual=" + SMALLER_PASSPORT_EXP_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportExpDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportExpDate is less than
        defaultEmployeeFiltering(
            "passportExpDate.lessThan=" + UPDATED_PASSPORT_EXP_DATE,
            "passportExpDate.lessThan=" + DEFAULT_PASSPORT_EXP_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPassportExpDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passportExpDate is greater than
        defaultEmployeeFiltering(
            "passportExpDate.greaterThan=" + SMALLER_PASSPORT_EXP_DATE,
            "passportExpDate.greaterThan=" + DEFAULT_PASSPORT_EXP_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth equals to
        defaultEmployeeFiltering("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH, "dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth in
        defaultEmployeeFiltering(
            "dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH,
            "dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is not null
        defaultEmployeeFiltering("dateOfBirth.specified=true", "dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is greater than or equal to
        defaultEmployeeFiltering(
            "dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH,
            "dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is less than or equal to
        defaultEmployeeFiltering(
            "dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH,
            "dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is less than
        defaultEmployeeFiltering("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH, "dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateOfBirth is greater than
        defaultEmployeeFiltering("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH, "dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age equals to
        defaultEmployeeFiltering("age.equals=" + DEFAULT_AGE, "age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age in
        defaultEmployeeFiltering("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE, "age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is not null
        defaultEmployeeFiltering("age.specified=true", "age.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is greater than or equal to
        defaultEmployeeFiltering("age.greaterThanOrEqual=" + DEFAULT_AGE, "age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is less than or equal to
        defaultEmployeeFiltering("age.lessThanOrEqual=" + DEFAULT_AGE, "age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is less than
        defaultEmployeeFiltering("age.lessThan=" + UPDATED_AGE, "age.lessThan=" + DEFAULT_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where age is greater than
        defaultEmployeeFiltering("age.greaterThan=" + SMALLER_AGE, "age.greaterThan=" + DEFAULT_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeesByBloodGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup equals to
        defaultEmployeeFiltering("bloodGroup.equals=" + DEFAULT_BLOOD_GROUP, "bloodGroup.equals=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    void getAllEmployeesByBloodGroupIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup in
        defaultEmployeeFiltering(
            "bloodGroup.in=" + DEFAULT_BLOOD_GROUP + "," + UPDATED_BLOOD_GROUP,
            "bloodGroup.in=" + UPDATED_BLOOD_GROUP
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByBloodGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup is not null
        defaultEmployeeFiltering("bloodGroup.specified=true", "bloodGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBloodGroupContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup contains
        defaultEmployeeFiltering("bloodGroup.contains=" + DEFAULT_BLOOD_GROUP, "bloodGroup.contains=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    void getAllEmployeesByBloodGroupNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup does not contain
        defaultEmployeeFiltering("bloodGroup.doesNotContain=" + UPDATED_BLOOD_GROUP, "bloodGroup.doesNotContain=" + DEFAULT_BLOOD_GROUP);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender equals to
        defaultEmployeeFiltering("gender.equals=" + DEFAULT_GENDER, "gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender in
        defaultEmployeeFiltering("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER, "gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender is not null
        defaultEmployeeFiltering("gender.specified=true", "gender.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender contains
        defaultEmployeeFiltering("gender.contains=" + DEFAULT_GENDER, "gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender does not contain
        defaultEmployeeFiltering("gender.doesNotContain=" + UPDATED_GENDER, "gender.doesNotContain=" + DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhone2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone2 equals to
        defaultEmployeeFiltering("phone2.equals=" + DEFAULT_PHONE_2, "phone2.equals=" + UPDATED_PHONE_2);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhone2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone2 in
        defaultEmployeeFiltering("phone2.in=" + DEFAULT_PHONE_2 + "," + UPDATED_PHONE_2, "phone2.in=" + UPDATED_PHONE_2);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhone2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone2 is not null
        defaultEmployeeFiltering("phone2.specified=true", "phone2.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPhone2ContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone2 contains
        defaultEmployeeFiltering("phone2.contains=" + DEFAULT_PHONE_2, "phone2.contains=" + UPDATED_PHONE_2);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhone2NotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phone2 does not contain
        defaultEmployeeFiltering("phone2.doesNotContain=" + UPDATED_PHONE_2, "phone2.doesNotContain=" + DEFAULT_PHONE_2);
    }

    @Test
    @Transactional
    void getAllEmployeesByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus equals to
        defaultEmployeeFiltering("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS, "maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeesByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus in
        defaultEmployeeFiltering(
            "maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS,
            "maritalStatus.in=" + UPDATED_MARITAL_STATUS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus is not null
        defaultEmployeeFiltering("maritalStatus.specified=true", "maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMaritalStatusContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus contains
        defaultEmployeeFiltering("maritalStatus.contains=" + DEFAULT_MARITAL_STATUS, "maritalStatus.contains=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeesByMaritalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus does not contain
        defaultEmployeeFiltering(
            "maritalStatus.doesNotContain=" + UPDATED_MARITAL_STATUS,
            "maritalStatus.doesNotContain=" + DEFAULT_MARITAL_STATUS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMarriedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where marriedDate equals to
        defaultEmployeeFiltering("marriedDate.equals=" + DEFAULT_MARRIED_DATE, "marriedDate.equals=" + UPDATED_MARRIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByMarriedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where marriedDate in
        defaultEmployeeFiltering(
            "marriedDate.in=" + DEFAULT_MARRIED_DATE + "," + UPDATED_MARRIED_DATE,
            "marriedDate.in=" + UPDATED_MARRIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMarriedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where marriedDate is not null
        defaultEmployeeFiltering("marriedDate.specified=true", "marriedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMarriedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where marriedDate is greater than or equal to
        defaultEmployeeFiltering(
            "marriedDate.greaterThanOrEqual=" + DEFAULT_MARRIED_DATE,
            "marriedDate.greaterThanOrEqual=" + UPDATED_MARRIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMarriedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where marriedDate is less than or equal to
        defaultEmployeeFiltering(
            "marriedDate.lessThanOrEqual=" + DEFAULT_MARRIED_DATE,
            "marriedDate.lessThanOrEqual=" + SMALLER_MARRIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByMarriedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where marriedDate is less than
        defaultEmployeeFiltering("marriedDate.lessThan=" + UPDATED_MARRIED_DATE, "marriedDate.lessThan=" + DEFAULT_MARRIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByMarriedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where marriedDate is greater than
        defaultEmployeeFiltering("marriedDate.greaterThan=" + SMALLER_MARRIED_DATE, "marriedDate.greaterThan=" + DEFAULT_MARRIED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality equals to
        defaultEmployeeFiltering("nationality.equals=" + DEFAULT_NATIONALITY, "nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality in
        defaultEmployeeFiltering(
            "nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY,
            "nationality.in=" + UPDATED_NATIONALITY
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality is not null
        defaultEmployeeFiltering("nationality.specified=true", "nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality contains
        defaultEmployeeFiltering("nationality.contains=" + DEFAULT_NATIONALITY, "nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nationality does not contain
        defaultEmployeeFiltering("nationality.doesNotContain=" + UPDATED_NATIONALITY, "nationality.doesNotContain=" + DEFAULT_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByPermanentAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress equals to
        defaultEmployeeFiltering(
            "permanentAddress.equals=" + DEFAULT_PERMANENT_ADDRESS,
            "permanentAddress.equals=" + UPDATED_PERMANENT_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPermanentAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress in
        defaultEmployeeFiltering(
            "permanentAddress.in=" + DEFAULT_PERMANENT_ADDRESS + "," + UPDATED_PERMANENT_ADDRESS,
            "permanentAddress.in=" + UPDATED_PERMANENT_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPermanentAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress is not null
        defaultEmployeeFiltering("permanentAddress.specified=true", "permanentAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPermanentAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress contains
        defaultEmployeeFiltering(
            "permanentAddress.contains=" + DEFAULT_PERMANENT_ADDRESS,
            "permanentAddress.contains=" + UPDATED_PERMANENT_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByPermanentAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress does not contain
        defaultEmployeeFiltering(
            "permanentAddress.doesNotContain=" + UPDATED_PERMANENT_ADDRESS,
            "permanentAddress.doesNotContain=" + DEFAULT_PERMANENT_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByTemporaryAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where temporaryAddress equals to
        defaultEmployeeFiltering(
            "temporaryAddress.equals=" + DEFAULT_TEMPORARY_ADDRESS,
            "temporaryAddress.equals=" + UPDATED_TEMPORARY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByTemporaryAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where temporaryAddress in
        defaultEmployeeFiltering(
            "temporaryAddress.in=" + DEFAULT_TEMPORARY_ADDRESS + "," + UPDATED_TEMPORARY_ADDRESS,
            "temporaryAddress.in=" + UPDATED_TEMPORARY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByTemporaryAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where temporaryAddress is not null
        defaultEmployeeFiltering("temporaryAddress.specified=true", "temporaryAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByTemporaryAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where temporaryAddress contains
        defaultEmployeeFiltering(
            "temporaryAddress.contains=" + DEFAULT_TEMPORARY_ADDRESS,
            "temporaryAddress.contains=" + UPDATED_TEMPORARY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByTemporaryAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where temporaryAddress does not contain
        defaultEmployeeFiltering(
            "temporaryAddress.doesNotContain=" + UPDATED_TEMPORARY_ADDRESS,
            "temporaryAddress.doesNotContain=" + DEFAULT_TEMPORARY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByHomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where home equals to
        defaultEmployeeFiltering("home.equals=" + DEFAULT_HOME, "home.equals=" + UPDATED_HOME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where home in
        defaultEmployeeFiltering("home.in=" + DEFAULT_HOME + "," + UPDATED_HOME, "home.in=" + UPDATED_HOME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where home is not null
        defaultEmployeeFiltering("home.specified=true", "home.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByHomeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where home contains
        defaultEmployeeFiltering("home.contains=" + DEFAULT_HOME, "home.contains=" + UPDATED_HOME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where home does not contain
        defaultEmployeeFiltering("home.doesNotContain=" + UPDATED_HOME, "home.doesNotContain=" + DEFAULT_HOME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile equals to
        defaultEmployeeFiltering("mobile.equals=" + DEFAULT_MOBILE, "mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile in
        defaultEmployeeFiltering("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE, "mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile is not null
        defaultEmployeeFiltering("mobile.specified=true", "mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile contains
        defaultEmployeeFiltering("mobile.contains=" + DEFAULT_MOBILE, "mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobile does not contain
        defaultEmployeeFiltering("mobile.doesNotContain=" + UPDATED_MOBILE, "mobile.doesNotContain=" + DEFAULT_MOBILE);
    }

    @Test
    @Transactional
    void getAllEmployeesByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fax equals to
        defaultEmployeeFiltering("fax.equals=" + DEFAULT_FAX, "fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllEmployeesByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fax in
        defaultEmployeeFiltering("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX, "fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllEmployeesByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fax is not null
        defaultEmployeeFiltering("fax.specified=true", "fax.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFaxContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fax contains
        defaultEmployeeFiltering("fax.contains=" + DEFAULT_FAX, "fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllEmployeesByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fax does not contain
        defaultEmployeeFiltering("fax.doesNotContain=" + UPDATED_FAX, "fax.doesNotContain=" + DEFAULT_FAX);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to
        defaultEmployeeFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in
        defaultEmployeeFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email contains
        defaultEmployeeFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email does not contain
        defaultEmployeeFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContactPerson equals to
        defaultEmployeeFiltering(
            "emergencyContactPerson.equals=" + DEFAULT_EMERGENCY_CONTACT_PERSON,
            "emergencyContactPerson.equals=" + UPDATED_EMERGENCY_CONTACT_PERSON
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyContactPersonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContactPerson in
        defaultEmployeeFiltering(
            "emergencyContactPerson.in=" + DEFAULT_EMERGENCY_CONTACT_PERSON + "," + UPDATED_EMERGENCY_CONTACT_PERSON,
            "emergencyContactPerson.in=" + UPDATED_EMERGENCY_CONTACT_PERSON
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyContactPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContactPerson is not null
        defaultEmployeeFiltering("emergencyContactPerson.specified=true", "emergencyContactPerson.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyContactPersonContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContactPerson contains
        defaultEmployeeFiltering(
            "emergencyContactPerson.contains=" + DEFAULT_EMERGENCY_CONTACT_PERSON,
            "emergencyContactPerson.contains=" + UPDATED_EMERGENCY_CONTACT_PERSON
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyContactPersonNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContactPerson does not contain
        defaultEmployeeFiltering(
            "emergencyContactPerson.doesNotContain=" + UPDATED_EMERGENCY_CONTACT_PERSON,
            "emergencyContactPerson.doesNotContain=" + DEFAULT_EMERGENCY_CONTACT_PERSON
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyNumber equals to
        defaultEmployeeFiltering(
            "emergencyNumber.equals=" + DEFAULT_EMERGENCY_NUMBER,
            "emergencyNumber.equals=" + UPDATED_EMERGENCY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyNumber in
        defaultEmployeeFiltering(
            "emergencyNumber.in=" + DEFAULT_EMERGENCY_NUMBER + "," + UPDATED_EMERGENCY_NUMBER,
            "emergencyNumber.in=" + UPDATED_EMERGENCY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyNumber is not null
        defaultEmployeeFiltering("emergencyNumber.specified=true", "emergencyNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyNumber contains
        defaultEmployeeFiltering(
            "emergencyNumber.contains=" + DEFAULT_EMERGENCY_NUMBER,
            "emergencyNumber.contains=" + UPDATED_EMERGENCY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmergencyNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyNumber does not contain
        defaultEmployeeFiltering(
            "emergencyNumber.doesNotContain=" + UPDATED_EMERGENCY_NUMBER,
            "emergencyNumber.doesNotContain=" + DEFAULT_EMERGENCY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where city equals to
        defaultEmployeeFiltering("city.equals=" + DEFAULT_CITY, "city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where city in
        defaultEmployeeFiltering("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY, "city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where city is not null
        defaultEmployeeFiltering("city.specified=true", "city.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCityContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where city contains
        defaultEmployeeFiltering("city.contains=" + DEFAULT_CITY, "city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where city does not contain
        defaultEmployeeFiltering("city.doesNotContain=" + UPDATED_CITY, "city.doesNotContain=" + DEFAULT_CITY);
    }

    @Test
    @Transactional
    void getAllEmployeesByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where district equals to
        defaultEmployeeFiltering("district.equals=" + DEFAULT_DISTRICT, "district.equals=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDistrictIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where district in
        defaultEmployeeFiltering("district.in=" + DEFAULT_DISTRICT + "," + UPDATED_DISTRICT, "district.in=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDistrictIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where district is not null
        defaultEmployeeFiltering("district.specified=true", "district.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDistrictContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where district contains
        defaultEmployeeFiltering("district.contains=" + DEFAULT_DISTRICT, "district.contains=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDistrictNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where district does not contain
        defaultEmployeeFiltering("district.doesNotContain=" + UPDATED_DISTRICT, "district.doesNotContain=" + DEFAULT_DISTRICT);
    }

    @Test
    @Transactional
    void getAllEmployeesByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where province equals to
        defaultEmployeeFiltering("province.equals=" + DEFAULT_PROVINCE, "province.equals=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where province in
        defaultEmployeeFiltering("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE, "province.in=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where province is not null
        defaultEmployeeFiltering("province.specified=true", "province.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByProvinceContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where province contains
        defaultEmployeeFiltering("province.contains=" + DEFAULT_PROVINCE, "province.contains=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByProvinceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where province does not contain
        defaultEmployeeFiltering("province.doesNotContain=" + UPDATED_PROVINCE, "province.doesNotContain=" + DEFAULT_PROVINCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByElectorateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where electorate equals to
        defaultEmployeeFiltering("electorate.equals=" + DEFAULT_ELECTORATE, "electorate.equals=" + UPDATED_ELECTORATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByElectorateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where electorate in
        defaultEmployeeFiltering("electorate.in=" + DEFAULT_ELECTORATE + "," + UPDATED_ELECTORATE, "electorate.in=" + UPDATED_ELECTORATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByElectorateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where electorate is not null
        defaultEmployeeFiltering("electorate.specified=true", "electorate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByElectorateContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where electorate contains
        defaultEmployeeFiltering("electorate.contains=" + DEFAULT_ELECTORATE, "electorate.contains=" + UPDATED_ELECTORATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByElectorateNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where electorate does not contain
        defaultEmployeeFiltering("electorate.doesNotContain=" + UPDATED_ELECTORATE, "electorate.doesNotContain=" + DEFAULT_ELECTORATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByMainRoadIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mainRoad equals to
        defaultEmployeeFiltering("mainRoad.equals=" + DEFAULT_MAIN_ROAD, "mainRoad.equals=" + UPDATED_MAIN_ROAD);
    }

    @Test
    @Transactional
    void getAllEmployeesByMainRoadIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mainRoad in
        defaultEmployeeFiltering("mainRoad.in=" + DEFAULT_MAIN_ROAD + "," + UPDATED_MAIN_ROAD, "mainRoad.in=" + UPDATED_MAIN_ROAD);
    }

    @Test
    @Transactional
    void getAllEmployeesByMainRoadIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mainRoad is not null
        defaultEmployeeFiltering("mainRoad.specified=true", "mainRoad.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMainRoadContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mainRoad contains
        defaultEmployeeFiltering("mainRoad.contains=" + DEFAULT_MAIN_ROAD, "mainRoad.contains=" + UPDATED_MAIN_ROAD);
    }

    @Test
    @Transactional
    void getAllEmployeesByMainRoadNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mainRoad does not contain
        defaultEmployeeFiltering("mainRoad.doesNotContain=" + UPDATED_MAIN_ROAD, "mainRoad.doesNotContain=" + DEFAULT_MAIN_ROAD);
    }

    @Test
    @Transactional
    void getAllEmployeesByModeOfTransportIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where modeOfTransport equals to
        defaultEmployeeFiltering(
            "modeOfTransport.equals=" + DEFAULT_MODE_OF_TRANSPORT,
            "modeOfTransport.equals=" + UPDATED_MODE_OF_TRANSPORT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByModeOfTransportIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where modeOfTransport in
        defaultEmployeeFiltering(
            "modeOfTransport.in=" + DEFAULT_MODE_OF_TRANSPORT + "," + UPDATED_MODE_OF_TRANSPORT,
            "modeOfTransport.in=" + UPDATED_MODE_OF_TRANSPORT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByModeOfTransportIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where modeOfTransport is not null
        defaultEmployeeFiltering("modeOfTransport.specified=true", "modeOfTransport.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByModeOfTransportContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where modeOfTransport contains
        defaultEmployeeFiltering(
            "modeOfTransport.contains=" + DEFAULT_MODE_OF_TRANSPORT,
            "modeOfTransport.contains=" + UPDATED_MODE_OF_TRANSPORT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByModeOfTransportNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where modeOfTransport does not contain
        defaultEmployeeFiltering(
            "modeOfTransport.doesNotContain=" + UPDATED_MODE_OF_TRANSPORT,
            "modeOfTransport.doesNotContain=" + DEFAULT_MODE_OF_TRANSPORT
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where distance equals to
        defaultEmployeeFiltering("distance.equals=" + DEFAULT_DISTANCE, "distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where distance in
        defaultEmployeeFiltering("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE, "distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where distance is not null
        defaultEmployeeFiltering("distance.specified=true", "distance.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDistanceContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where distance contains
        defaultEmployeeFiltering("distance.contains=" + DEFAULT_DISTANCE, "distance.contains=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByDistanceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where distance does not contain
        defaultEmployeeFiltering("distance.doesNotContain=" + UPDATED_DISTANCE, "distance.doesNotContain=" + DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByTravelTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where travelTime equals to
        defaultEmployeeFiltering("travelTime.equals=" + DEFAULT_TRAVEL_TIME, "travelTime.equals=" + UPDATED_TRAVEL_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByTravelTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where travelTime in
        defaultEmployeeFiltering(
            "travelTime.in=" + DEFAULT_TRAVEL_TIME + "," + UPDATED_TRAVEL_TIME,
            "travelTime.in=" + UPDATED_TRAVEL_TIME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByTravelTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where travelTime is not null
        defaultEmployeeFiltering("travelTime.specified=true", "travelTime.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByTravelTimeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where travelTime contains
        defaultEmployeeFiltering("travelTime.contains=" + DEFAULT_TRAVEL_TIME, "travelTime.contains=" + UPDATED_TRAVEL_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByTravelTimeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where travelTime does not contain
        defaultEmployeeFiltering("travelTime.doesNotContain=" + UPDATED_TRAVEL_TIME, "travelTime.doesNotContain=" + DEFAULT_TRAVEL_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username equals to
        defaultEmployeeFiltering("username.equals=" + DEFAULT_USERNAME, "username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username in
        defaultEmployeeFiltering("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME, "username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username is not null
        defaultEmployeeFiltering("username.specified=true", "username.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username contains
        defaultEmployeeFiltering("username.contains=" + DEFAULT_USERNAME, "username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username does not contain
        defaultEmployeeFiltering("username.doesNotContain=" + UPDATED_USERNAME, "username.doesNotContain=" + DEFAULT_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password equals to
        defaultEmployeeFiltering("password.equals=" + DEFAULT_PASSWORD, "password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password in
        defaultEmployeeFiltering("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD, "password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password is not null
        defaultEmployeeFiltering("password.specified=true", "password.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password contains
        defaultEmployeeFiltering("password.contains=" + DEFAULT_PASSWORD, "password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where password does not contain
        defaultEmployeeFiltering("password.doesNotContain=" + UPDATED_PASSWORD, "password.doesNotContain=" + DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentID equals to
        defaultEmployeeFiltering("departmentID.equals=" + DEFAULT_DEPARTMENT_ID, "departmentID.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentID in
        defaultEmployeeFiltering(
            "departmentID.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID,
            "departmentID.in=" + UPDATED_DEPARTMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentID is not null
        defaultEmployeeFiltering("departmentID.specified=true", "departmentID.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentID is greater than or equal to
        defaultEmployeeFiltering(
            "departmentID.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID,
            "departmentID.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentID is less than or equal to
        defaultEmployeeFiltering(
            "departmentID.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID,
            "departmentID.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentID is less than
        defaultEmployeeFiltering("departmentID.lessThan=" + UPDATED_DEPARTMENT_ID, "departmentID.lessThan=" + DEFAULT_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentID is greater than
        defaultEmployeeFiltering("departmentID.greaterThan=" + SMALLER_DEPARTMENT_ID, "departmentID.greaterThan=" + DEFAULT_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentCode equals to
        defaultEmployeeFiltering("departmentCode.equals=" + DEFAULT_DEPARTMENT_CODE, "departmentCode.equals=" + UPDATED_DEPARTMENT_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentCode in
        defaultEmployeeFiltering(
            "departmentCode.in=" + DEFAULT_DEPARTMENT_CODE + "," + UPDATED_DEPARTMENT_CODE,
            "departmentCode.in=" + UPDATED_DEPARTMENT_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentCode is not null
        defaultEmployeeFiltering("departmentCode.specified=true", "departmentCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentCode contains
        defaultEmployeeFiltering(
            "departmentCode.contains=" + DEFAULT_DEPARTMENT_CODE,
            "departmentCode.contains=" + UPDATED_DEPARTMENT_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where departmentCode does not contain
        defaultEmployeeFiltering(
            "departmentCode.doesNotContain=" + UPDATED_DEPARTMENT_CODE,
            "departmentCode.doesNotContain=" + DEFAULT_DEPARTMENT_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpRegDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empRegDate equals to
        defaultEmployeeFiltering("empRegDate.equals=" + DEFAULT_EMP_REG_DATE, "empRegDate.equals=" + UPDATED_EMP_REG_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpRegDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empRegDate in
        defaultEmployeeFiltering(
            "empRegDate.in=" + DEFAULT_EMP_REG_DATE + "," + UPDATED_EMP_REG_DATE,
            "empRegDate.in=" + UPDATED_EMP_REG_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpRegDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empRegDate is not null
        defaultEmployeeFiltering("empRegDate.specified=true", "empRegDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpRegDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empRegDate is greater than or equal to
        defaultEmployeeFiltering(
            "empRegDate.greaterThanOrEqual=" + DEFAULT_EMP_REG_DATE,
            "empRegDate.greaterThanOrEqual=" + UPDATED_EMP_REG_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpRegDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empRegDate is less than or equal to
        defaultEmployeeFiltering(
            "empRegDate.lessThanOrEqual=" + DEFAULT_EMP_REG_DATE,
            "empRegDate.lessThanOrEqual=" + SMALLER_EMP_REG_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpRegDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empRegDate is less than
        defaultEmployeeFiltering("empRegDate.lessThan=" + UPDATED_EMP_REG_DATE, "empRegDate.lessThan=" + DEFAULT_EMP_REG_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpRegDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empRegDate is greater than
        defaultEmployeeFiltering("empRegDate.greaterThan=" + SMALLER_EMP_REG_DATE, "empRegDate.greaterThan=" + DEFAULT_EMP_REG_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmu equals to
        defaultEmployeeFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllEmployeesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmu in
        defaultEmployeeFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllEmployeesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmu is not null
        defaultEmployeeFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLmuContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmu contains
        defaultEmployeeFiltering("lmu.contains=" + DEFAULT_LMU, "lmu.contains=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllEmployeesByLmuNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmu does not contain
        defaultEmployeeFiltering("lmu.doesNotContain=" + UPDATED_LMU, "lmu.doesNotContain=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllEmployeesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmd equals to
        defaultEmployeeFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllEmployeesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmd in
        defaultEmployeeFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllEmployeesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lmd is not null
        defaultEmployeeFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleId equals to
        defaultEmployeeFiltering("roleId.equals=" + DEFAULT_ROLE_ID, "roleId.equals=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleId in
        defaultEmployeeFiltering("roleId.in=" + DEFAULT_ROLE_ID + "," + UPDATED_ROLE_ID, "roleId.in=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleId is not null
        defaultEmployeeFiltering("roleId.specified=true", "roleId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleId is greater than or equal to
        defaultEmployeeFiltering("roleId.greaterThanOrEqual=" + DEFAULT_ROLE_ID, "roleId.greaterThanOrEqual=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleId is less than or equal to
        defaultEmployeeFiltering("roleId.lessThanOrEqual=" + DEFAULT_ROLE_ID, "roleId.lessThanOrEqual=" + SMALLER_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleId is less than
        defaultEmployeeFiltering("roleId.lessThan=" + UPDATED_ROLE_ID, "roleId.lessThan=" + DEFAULT_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleId is greater than
        defaultEmployeeFiltering("roleId.greaterThan=" + SMALLER_ROLE_ID, "roleId.greaterThan=" + DEFAULT_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleName equals to
        defaultEmployeeFiltering("roleName.equals=" + DEFAULT_ROLE_NAME, "roleName.equals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleName in
        defaultEmployeeFiltering("roleName.in=" + DEFAULT_ROLE_NAME + "," + UPDATED_ROLE_NAME, "roleName.in=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleName is not null
        defaultEmployeeFiltering("roleName.specified=true", "roleName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleName contains
        defaultEmployeeFiltering("roleName.contains=" + DEFAULT_ROLE_NAME, "roleName.contains=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByRoleNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where roleName does not contain
        defaultEmployeeFiltering("roleName.doesNotContain=" + UPDATED_ROLE_NAME, "roleName.doesNotContain=" + DEFAULT_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEpfIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where epf equals to
        defaultEmployeeFiltering("epf.equals=" + DEFAULT_EPF, "epf.equals=" + UPDATED_EPF);
    }

    @Test
    @Transactional
    void getAllEmployeesByEpfIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where epf in
        defaultEmployeeFiltering("epf.in=" + DEFAULT_EPF + "," + UPDATED_EPF, "epf.in=" + UPDATED_EPF);
    }

    @Test
    @Transactional
    void getAllEmployeesByEpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where epf is not null
        defaultEmployeeFiltering("epf.specified=true", "epf.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEpfContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where epf contains
        defaultEmployeeFiltering("epf.contains=" + DEFAULT_EPF, "epf.contains=" + UPDATED_EPF);
    }

    @Test
    @Transactional
    void getAllEmployeesByEpfNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where epf does not contain
        defaultEmployeeFiltering("epf.doesNotContain=" + UPDATED_EPF, "epf.doesNotContain=" + DEFAULT_EPF);
    }

    @Test
    @Transactional
    void getAllEmployeesByEtfIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where etf equals to
        defaultEmployeeFiltering("etf.equals=" + DEFAULT_ETF, "etf.equals=" + UPDATED_ETF);
    }

    @Test
    @Transactional
    void getAllEmployeesByEtfIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where etf in
        defaultEmployeeFiltering("etf.in=" + DEFAULT_ETF + "," + UPDATED_ETF, "etf.in=" + UPDATED_ETF);
    }

    @Test
    @Transactional
    void getAllEmployeesByEtfIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where etf is not null
        defaultEmployeeFiltering("etf.specified=true", "etf.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEtfContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where etf contains
        defaultEmployeeFiltering("etf.contains=" + DEFAULT_ETF, "etf.contains=" + UPDATED_ETF);
    }

    @Test
    @Transactional
    void getAllEmployeesByEtfNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where etf does not contain
        defaultEmployeeFiltering("etf.doesNotContain=" + UPDATED_ETF, "etf.doesNotContain=" + DEFAULT_ETF);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateJoinedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoined equals to
        defaultEmployeeFiltering("dateJoined.equals=" + DEFAULT_DATE_JOINED, "dateJoined.equals=" + UPDATED_DATE_JOINED);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateJoinedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoined in
        defaultEmployeeFiltering(
            "dateJoined.in=" + DEFAULT_DATE_JOINED + "," + UPDATED_DATE_JOINED,
            "dateJoined.in=" + UPDATED_DATE_JOINED
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateJoinedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateJoined is not null
        defaultEmployeeFiltering("dateJoined.specified=true", "dateJoined.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDateResignedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateResigned equals to
        defaultEmployeeFiltering("dateResigned.equals=" + DEFAULT_DATE_RESIGNED, "dateResigned.equals=" + UPDATED_DATE_RESIGNED);
    }

    @Test
    @Transactional
    void getAllEmployeesByDateResignedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateResigned in
        defaultEmployeeFiltering(
            "dateResigned.in=" + DEFAULT_DATE_RESIGNED + "," + UPDATED_DATE_RESIGNED,
            "dateResigned.in=" + UPDATED_DATE_RESIGNED
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDateResignedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where dateResigned is not null
        defaultEmployeeFiltering("dateResigned.specified=true", "dateResigned.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation equals to
        defaultEmployeeFiltering("designation.equals=" + DEFAULT_DESIGNATION, "designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation in
        defaultEmployeeFiltering(
            "designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION,
            "designation.in=" + UPDATED_DESIGNATION
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation is not null
        defaultEmployeeFiltering("designation.specified=true", "designation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDesignationContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation contains
        defaultEmployeeFiltering("designation.contains=" + DEFAULT_DESIGNATION, "designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where designation does not contain
        defaultEmployeeFiltering("designation.doesNotContain=" + UPDATED_DESIGNATION, "designation.doesNotContain=" + DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusId equals to
        defaultEmployeeFiltering("jobStatusId.equals=" + DEFAULT_JOB_STATUS_ID, "jobStatusId.equals=" + UPDATED_JOB_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusId in
        defaultEmployeeFiltering(
            "jobStatusId.in=" + DEFAULT_JOB_STATUS_ID + "," + UPDATED_JOB_STATUS_ID,
            "jobStatusId.in=" + UPDATED_JOB_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusId is not null
        defaultEmployeeFiltering("jobStatusId.specified=true", "jobStatusId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusId is greater than or equal to
        defaultEmployeeFiltering(
            "jobStatusId.greaterThanOrEqual=" + DEFAULT_JOB_STATUS_ID,
            "jobStatusId.greaterThanOrEqual=" + UPDATED_JOB_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusId is less than or equal to
        defaultEmployeeFiltering(
            "jobStatusId.lessThanOrEqual=" + DEFAULT_JOB_STATUS_ID,
            "jobStatusId.lessThanOrEqual=" + SMALLER_JOB_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusId is less than
        defaultEmployeeFiltering("jobStatusId.lessThan=" + UPDATED_JOB_STATUS_ID, "jobStatusId.lessThan=" + DEFAULT_JOB_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusId is greater than
        defaultEmployeeFiltering("jobStatusId.greaterThan=" + SMALLER_JOB_STATUS_ID, "jobStatusId.greaterThan=" + DEFAULT_JOB_STATUS_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusName equals to
        defaultEmployeeFiltering("jobStatusName.equals=" + DEFAULT_JOB_STATUS_NAME, "jobStatusName.equals=" + UPDATED_JOB_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusName in
        defaultEmployeeFiltering(
            "jobStatusName.in=" + DEFAULT_JOB_STATUS_NAME + "," + UPDATED_JOB_STATUS_NAME,
            "jobStatusName.in=" + UPDATED_JOB_STATUS_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusName is not null
        defaultEmployeeFiltering("jobStatusName.specified=true", "jobStatusName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusName contains
        defaultEmployeeFiltering("jobStatusName.contains=" + DEFAULT_JOB_STATUS_NAME, "jobStatusName.contains=" + UPDATED_JOB_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByJobStatusNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where jobStatusName does not contain
        defaultEmployeeFiltering(
            "jobStatusName.doesNotContain=" + UPDATED_JOB_STATUS_NAME,
            "jobStatusName.doesNotContain=" + DEFAULT_JOB_STATUS_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByImagePathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imagePath equals to
        defaultEmployeeFiltering("imagePath.equals=" + DEFAULT_IMAGE_PATH, "imagePath.equals=" + UPDATED_IMAGE_PATH);
    }

    @Test
    @Transactional
    void getAllEmployeesByImagePathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imagePath in
        defaultEmployeeFiltering("imagePath.in=" + DEFAULT_IMAGE_PATH + "," + UPDATED_IMAGE_PATH, "imagePath.in=" + UPDATED_IMAGE_PATH);
    }

    @Test
    @Transactional
    void getAllEmployeesByImagePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imagePath is not null
        defaultEmployeeFiltering("imagePath.specified=true", "imagePath.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByImagePathContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imagePath contains
        defaultEmployeeFiltering("imagePath.contains=" + DEFAULT_IMAGE_PATH, "imagePath.contains=" + UPDATED_IMAGE_PATH);
    }

    @Test
    @Transactional
    void getAllEmployeesByImagePathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imagePath does not contain
        defaultEmployeeFiltering("imagePath.doesNotContain=" + UPDATED_IMAGE_PATH, "imagePath.doesNotContain=" + DEFAULT_IMAGE_PATH);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo equals to
        defaultEmployeeFiltering("bankAccountNo.equals=" + DEFAULT_BANK_ACCOUNT_NO, "bankAccountNo.equals=" + UPDATED_BANK_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo in
        defaultEmployeeFiltering(
            "bankAccountNo.in=" + DEFAULT_BANK_ACCOUNT_NO + "," + UPDATED_BANK_ACCOUNT_NO,
            "bankAccountNo.in=" + UPDATED_BANK_ACCOUNT_NO
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByBankAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo is not null
        defaultEmployeeFiltering("bankAccountNo.specified=true", "bankAccountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBankAccountNoContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo contains
        defaultEmployeeFiltering("bankAccountNo.contains=" + DEFAULT_BANK_ACCOUNT_NO, "bankAccountNo.contains=" + UPDATED_BANK_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo does not contain
        defaultEmployeeFiltering(
            "bankAccountNo.doesNotContain=" + UPDATED_BANK_ACCOUNT_NO,
            "bankAccountNo.doesNotContain=" + DEFAULT_BANK_ACCOUNT_NO
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByBankIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankId equals to
        defaultEmployeeFiltering("bankId.equals=" + DEFAULT_BANK_ID, "bankId.equals=" + UPDATED_BANK_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankId in
        defaultEmployeeFiltering("bankId.in=" + DEFAULT_BANK_ID + "," + UPDATED_BANK_ID, "bankId.in=" + UPDATED_BANK_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankId is not null
        defaultEmployeeFiltering("bankId.specified=true", "bankId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBankIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankId is greater than or equal to
        defaultEmployeeFiltering("bankId.greaterThanOrEqual=" + DEFAULT_BANK_ID, "bankId.greaterThanOrEqual=" + UPDATED_BANK_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankId is less than or equal to
        defaultEmployeeFiltering("bankId.lessThanOrEqual=" + DEFAULT_BANK_ID, "bankId.lessThanOrEqual=" + SMALLER_BANK_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankId is less than
        defaultEmployeeFiltering("bankId.lessThan=" + UPDATED_BANK_ID, "bankId.lessThan=" + DEFAULT_BANK_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankId is greater than
        defaultEmployeeFiltering("bankId.greaterThan=" + SMALLER_BANK_ID, "bankId.greaterThan=" + DEFAULT_BANK_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName equals to
        defaultEmployeeFiltering("bankName.equals=" + DEFAULT_BANK_NAME, "bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName in
        defaultEmployeeFiltering("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME, "bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName is not null
        defaultEmployeeFiltering("bankName.specified=true", "bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBankNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName contains
        defaultEmployeeFiltering("bankName.contains=" + DEFAULT_BANK_NAME, "bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName does not contain
        defaultEmployeeFiltering("bankName.doesNotContain=" + UPDATED_BANK_NAME, "bankName.doesNotContain=" + DEFAULT_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchId equals to
        defaultEmployeeFiltering("branchId.equals=" + DEFAULT_BRANCH_ID, "branchId.equals=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchId in
        defaultEmployeeFiltering("branchId.in=" + DEFAULT_BRANCH_ID + "," + UPDATED_BRANCH_ID, "branchId.in=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchId is not null
        defaultEmployeeFiltering("branchId.specified=true", "branchId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchId is greater than or equal to
        defaultEmployeeFiltering("branchId.greaterThanOrEqual=" + DEFAULT_BRANCH_ID, "branchId.greaterThanOrEqual=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchId is less than or equal to
        defaultEmployeeFiltering("branchId.lessThanOrEqual=" + DEFAULT_BRANCH_ID, "branchId.lessThanOrEqual=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchId is less than
        defaultEmployeeFiltering("branchId.lessThan=" + UPDATED_BRANCH_ID, "branchId.lessThan=" + DEFAULT_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchId is greater than
        defaultEmployeeFiltering("branchId.greaterThan=" + SMALLER_BRANCH_ID, "branchId.greaterThan=" + DEFAULT_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName equals to
        defaultEmployeeFiltering("branchName.equals=" + DEFAULT_BRANCH_NAME, "branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName in
        defaultEmployeeFiltering(
            "branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME,
            "branchName.in=" + UPDATED_BRANCH_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName is not null
        defaultEmployeeFiltering("branchName.specified=true", "branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName contains
        defaultEmployeeFiltering("branchName.contains=" + DEFAULT_BRANCH_NAME, "branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where branchName does not contain
        defaultEmployeeFiltering("branchName.doesNotContain=" + UPDATED_BRANCH_NAME, "branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryPaymentBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryPaymentBasis equals to
        defaultEmployeeFiltering(
            "salaryPaymentBasis.equals=" + DEFAULT_SALARY_PAYMENT_BASIS,
            "salaryPaymentBasis.equals=" + UPDATED_SALARY_PAYMENT_BASIS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryPaymentBasisIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryPaymentBasis in
        defaultEmployeeFiltering(
            "salaryPaymentBasis.in=" + DEFAULT_SALARY_PAYMENT_BASIS + "," + UPDATED_SALARY_PAYMENT_BASIS,
            "salaryPaymentBasis.in=" + UPDATED_SALARY_PAYMENT_BASIS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryPaymentBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryPaymentBasis is not null
        defaultEmployeeFiltering("salaryPaymentBasis.specified=true", "salaryPaymentBasis.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryPaymentBasisContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryPaymentBasis contains
        defaultEmployeeFiltering(
            "salaryPaymentBasis.contains=" + DEFAULT_SALARY_PAYMENT_BASIS,
            "salaryPaymentBasis.contains=" + UPDATED_SALARY_PAYMENT_BASIS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryPaymentBasisNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salaryPaymentBasis does not contain
        defaultEmployeeFiltering(
            "salaryPaymentBasis.doesNotContain=" + UPDATED_SALARY_PAYMENT_BASIS,
            "salaryPaymentBasis.doesNotContain=" + DEFAULT_SALARY_PAYMENT_BASIS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empStatus equals to
        defaultEmployeeFiltering("empStatus.equals=" + DEFAULT_EMP_STATUS, "empStatus.equals=" + UPDATED_EMP_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empStatus in
        defaultEmployeeFiltering("empStatus.in=" + DEFAULT_EMP_STATUS + "," + UPDATED_EMP_STATUS, "empStatus.in=" + UPDATED_EMP_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empStatus is not null
        defaultEmployeeFiltering("empStatus.specified=true", "empStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpStatusContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empStatus contains
        defaultEmployeeFiltering("empStatus.contains=" + DEFAULT_EMP_STATUS, "empStatus.contains=" + UPDATED_EMP_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmpStatusNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where empStatus does not contain
        defaultEmployeeFiltering("empStatus.doesNotContain=" + UPDATED_EMP_STATUS, "empStatus.doesNotContain=" + DEFAULT_EMP_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeesByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion equals to
        defaultEmployeeFiltering("religion.equals=" + DEFAULT_RELIGION, "religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeesByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion in
        defaultEmployeeFiltering("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION, "religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeesByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion is not null
        defaultEmployeeFiltering("religion.specified=true", "religion.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByReligionContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion contains
        defaultEmployeeFiltering("religion.contains=" + DEFAULT_RELIGION, "religion.contains=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeesByReligionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion does not contain
        defaultEmployeeFiltering("religion.doesNotContain=" + UPDATED_RELIGION, "religion.doesNotContain=" + DEFAULT_RELIGION);
    }

    @Test
    @Transactional
    void getAllEmployeesByExperienceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where experience equals to
        defaultEmployeeFiltering("experience.equals=" + DEFAULT_EXPERIENCE, "experience.equals=" + UPDATED_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByExperienceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where experience in
        defaultEmployeeFiltering("experience.in=" + DEFAULT_EXPERIENCE + "," + UPDATED_EXPERIENCE, "experience.in=" + UPDATED_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByExperienceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where experience is not null
        defaultEmployeeFiltering("experience.specified=true", "experience.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByExperienceContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where experience contains
        defaultEmployeeFiltering("experience.contains=" + DEFAULT_EXPERIENCE, "experience.contains=" + UPDATED_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByExperienceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where experience does not contain
        defaultEmployeeFiltering("experience.doesNotContain=" + UPDATED_EXPERIENCE, "experience.doesNotContain=" + DEFAULT_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualifications equals to
        defaultEmployeeFiltering("qualifications.equals=" + DEFAULT_QUALIFICATIONS, "qualifications.equals=" + UPDATED_QUALIFICATIONS);
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualifications in
        defaultEmployeeFiltering(
            "qualifications.in=" + DEFAULT_QUALIFICATIONS + "," + UPDATED_QUALIFICATIONS,
            "qualifications.in=" + UPDATED_QUALIFICATIONS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualifications is not null
        defaultEmployeeFiltering("qualifications.specified=true", "qualifications.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationsContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualifications contains
        defaultEmployeeFiltering("qualifications.contains=" + DEFAULT_QUALIFICATIONS, "qualifications.contains=" + UPDATED_QUALIFICATIONS);
    }

    @Test
    @Transactional
    void getAllEmployeesByQualificationsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where qualifications does not contain
        defaultEmployeeFiltering(
            "qualifications.doesNotContain=" + UPDATED_QUALIFICATIONS,
            "qualifications.doesNotContain=" + DEFAULT_QUALIFICATIONS
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAttendanceCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceCode equals to
        defaultEmployeeFiltering("attendanceCode.equals=" + DEFAULT_ATTENDANCE_CODE, "attendanceCode.equals=" + UPDATED_ATTENDANCE_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByAttendanceCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceCode in
        defaultEmployeeFiltering(
            "attendanceCode.in=" + DEFAULT_ATTENDANCE_CODE + "," + UPDATED_ATTENDANCE_CODE,
            "attendanceCode.in=" + UPDATED_ATTENDANCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAttendanceCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceCode is not null
        defaultEmployeeFiltering("attendanceCode.specified=true", "attendanceCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByAttendanceCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceCode contains
        defaultEmployeeFiltering(
            "attendanceCode.contains=" + DEFAULT_ATTENDANCE_CODE,
            "attendanceCode.contains=" + UPDATED_ATTENDANCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByAttendanceCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where attendanceCode does not contain
        defaultEmployeeFiltering(
            "attendanceCode.doesNotContain=" + UPDATED_ATTENDANCE_CODE,
            "attendanceCode.doesNotContain=" + DEFAULT_ATTENDANCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllEmployeesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isActive equals to
        defaultEmployeeFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isActive in
        defaultEmployeeFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where isActive is not null
        defaultEmployeeFiltering("isActive.specified=true", "isActive.specified=false");
    }

    private void defaultEmployeeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmployeeShouldBeFound(shouldBeFound);
        defaultEmployeeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].nameWithInitials").value(hasItem(DEFAULT_NAME_WITH_INITIALS)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].nicNumber").value(hasItem(DEFAULT_NIC_NUMBER)))
            .andExpect(jsonPath("$.[*].nicIssueDate").value(hasItem(DEFAULT_NIC_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportIssueDate").value(hasItem(DEFAULT_PASSPORT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].passportExpDate").value(hasItem(DEFAULT_PASSPORT_EXP_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].marriedDate").value(hasItem(DEFAULT_MARRIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].temporaryAddress").value(hasItem(DEFAULT_TEMPORARY_ADDRESS)))
            .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].emergencyContactPerson").value(hasItem(DEFAULT_EMERGENCY_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].emergencyNumber").value(hasItem(DEFAULT_EMERGENCY_NUMBER)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].electorate").value(hasItem(DEFAULT_ELECTORATE)))
            .andExpect(jsonPath("$.[*].mainRoad").value(hasItem(DEFAULT_MAIN_ROAD)))
            .andExpect(jsonPath("$.[*].modeOfTransport").value(hasItem(DEFAULT_MODE_OF_TRANSPORT)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].travelTime").value(hasItem(DEFAULT_TRAVEL_TIME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].departmentID").value(hasItem(DEFAULT_DEPARTMENT_ID)))
            .andExpect(jsonPath("$.[*].departmentCode").value(hasItem(DEFAULT_DEPARTMENT_CODE)))
            .andExpect(jsonPath("$.[*].empRegDate").value(hasItem(DEFAULT_EMP_REG_DATE.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)))
            .andExpect(jsonPath("$.[*].epf").value(hasItem(DEFAULT_EPF)))
            .andExpect(jsonPath("$.[*].etf").value(hasItem(DEFAULT_ETF)))
            .andExpect(jsonPath("$.[*].dateJoined").value(hasItem(DEFAULT_DATE_JOINED.toString())))
            .andExpect(jsonPath("$.[*].dateResigned").value(hasItem(DEFAULT_DATE_RESIGNED.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].jobStatusId").value(hasItem(DEFAULT_JOB_STATUS_ID)))
            .andExpect(jsonPath("$.[*].jobStatusName").value(hasItem(DEFAULT_JOB_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].bankId").value(hasItem(DEFAULT_BANK_ID)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].salaryPaymentBasis").value(hasItem(DEFAULT_SALARY_PAYMENT_BASIS)))
            .andExpect(jsonPath("$.[*].empStatus").value(hasItem(DEFAULT_EMP_STATUS)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].qualifications").value(hasItem(DEFAULT_QUALIFICATIONS)))
            .andExpect(jsonPath("$.[*].attendanceCode").value(hasItem(DEFAULT_ATTENDANCE_CODE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployee() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .code(UPDATED_CODE)
            .fullName(UPDATED_FULL_NAME)
            .nameWithInitials(UPDATED_NAME_WITH_INITIALS)
            .surname(UPDATED_SURNAME)
            .nicNumber(UPDATED_NIC_NUMBER)
            .nicIssueDate(UPDATED_NIC_ISSUE_DATE)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportIssueDate(UPDATED_PASSPORT_ISSUE_DATE)
            .passportExpDate(UPDATED_PASSPORT_EXP_DATE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .age(UPDATED_AGE)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .gender(UPDATED_GENDER)
            .phone2(UPDATED_PHONE_2)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .marriedDate(UPDATED_MARRIED_DATE)
            .nationality(UPDATED_NATIONALITY)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .temporaryAddress(UPDATED_TEMPORARY_ADDRESS)
            .home(UPDATED_HOME)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .emergencyContactPerson(UPDATED_EMERGENCY_CONTACT_PERSON)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .province(UPDATED_PROVINCE)
            .electorate(UPDATED_ELECTORATE)
            .mainRoad(UPDATED_MAIN_ROAD)
            .modeOfTransport(UPDATED_MODE_OF_TRANSPORT)
            .distance(UPDATED_DISTANCE)
            .travelTime(UPDATED_TRAVEL_TIME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .departmentID(UPDATED_DEPARTMENT_ID)
            .departmentCode(UPDATED_DEPARTMENT_CODE)
            .empRegDate(UPDATED_EMP_REG_DATE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .roleId(UPDATED_ROLE_ID)
            .roleName(UPDATED_ROLE_NAME)
            .epf(UPDATED_EPF)
            .etf(UPDATED_ETF)
            .dateJoined(UPDATED_DATE_JOINED)
            .dateResigned(UPDATED_DATE_RESIGNED)
            .designation(UPDATED_DESIGNATION)
            .jobStatusId(UPDATED_JOB_STATUS_ID)
            .jobStatusName(UPDATED_JOB_STATUS_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .bankAccountNo(UPDATED_BANK_ACCOUNT_NO)
            .bankId(UPDATED_BANK_ID)
            .bankName(UPDATED_BANK_NAME)
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME)
            .salaryPaymentBasis(UPDATED_SALARY_PAYMENT_BASIS)
            .empStatus(UPDATED_EMP_STATUS)
            .religion(UPDATED_RELIGION)
            .experience(UPDATED_EXPERIENCE)
            .qualifications(UPDATED_QUALIFICATIONS)
            .attendanceCode(UPDATED_ATTENDANCE_CODE)
            .isActive(UPDATED_IS_ACTIVE);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmployeeToMatchAllProperties(updatedEmployee);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employee.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employee.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .code(UPDATED_CODE)
            .nameWithInitials(UPDATED_NAME_WITH_INITIALS)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportIssueDate(UPDATED_PASSPORT_ISSUE_DATE)
            .passportExpDate(UPDATED_PASSPORT_EXP_DATE)
            .age(UPDATED_AGE)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .gender(UPDATED_GENDER)
            .phone2(UPDATED_PHONE_2)
            .nationality(UPDATED_NATIONALITY)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .temporaryAddress(UPDATED_TEMPORARY_ADDRESS)
            .home(UPDATED_HOME)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .emergencyContactPerson(UPDATED_EMERGENCY_CONTACT_PERSON)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .mainRoad(UPDATED_MAIN_ROAD)
            .distance(UPDATED_DISTANCE)
            .password(UPDATED_PASSWORD)
            .departmentID(UPDATED_DEPARTMENT_ID)
            .departmentCode(UPDATED_DEPARTMENT_CODE)
            .empRegDate(UPDATED_EMP_REG_DATE)
            .lmu(UPDATED_LMU)
            .roleId(UPDATED_ROLE_ID)
            .epf(UPDATED_EPF)
            .etf(UPDATED_ETF)
            .dateJoined(UPDATED_DATE_JOINED)
            .dateResigned(UPDATED_DATE_RESIGNED)
            .designation(UPDATED_DESIGNATION)
            .bankId(UPDATED_BANK_ID)
            .bankName(UPDATED_BANK_NAME)
            .branchId(UPDATED_BRANCH_ID)
            .qualifications(UPDATED_QUALIFICATIONS)
            .attendanceCode(UPDATED_ATTENDANCE_CODE);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmployee, employee), getPersistedEmployee(employee));
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .code(UPDATED_CODE)
            .fullName(UPDATED_FULL_NAME)
            .nameWithInitials(UPDATED_NAME_WITH_INITIALS)
            .surname(UPDATED_SURNAME)
            .nicNumber(UPDATED_NIC_NUMBER)
            .nicIssueDate(UPDATED_NIC_ISSUE_DATE)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportIssueDate(UPDATED_PASSPORT_ISSUE_DATE)
            .passportExpDate(UPDATED_PASSPORT_EXP_DATE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .age(UPDATED_AGE)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .gender(UPDATED_GENDER)
            .phone2(UPDATED_PHONE_2)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .marriedDate(UPDATED_MARRIED_DATE)
            .nationality(UPDATED_NATIONALITY)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .temporaryAddress(UPDATED_TEMPORARY_ADDRESS)
            .home(UPDATED_HOME)
            .mobile(UPDATED_MOBILE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .emergencyContactPerson(UPDATED_EMERGENCY_CONTACT_PERSON)
            .emergencyNumber(UPDATED_EMERGENCY_NUMBER)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .province(UPDATED_PROVINCE)
            .electorate(UPDATED_ELECTORATE)
            .mainRoad(UPDATED_MAIN_ROAD)
            .modeOfTransport(UPDATED_MODE_OF_TRANSPORT)
            .distance(UPDATED_DISTANCE)
            .travelTime(UPDATED_TRAVEL_TIME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .departmentID(UPDATED_DEPARTMENT_ID)
            .departmentCode(UPDATED_DEPARTMENT_CODE)
            .empRegDate(UPDATED_EMP_REG_DATE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .roleId(UPDATED_ROLE_ID)
            .roleName(UPDATED_ROLE_NAME)
            .epf(UPDATED_EPF)
            .etf(UPDATED_ETF)
            .dateJoined(UPDATED_DATE_JOINED)
            .dateResigned(UPDATED_DATE_RESIGNED)
            .designation(UPDATED_DESIGNATION)
            .jobStatusId(UPDATED_JOB_STATUS_ID)
            .jobStatusName(UPDATED_JOB_STATUS_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .bankAccountNo(UPDATED_BANK_ACCOUNT_NO)
            .bankId(UPDATED_BANK_ID)
            .bankName(UPDATED_BANK_NAME)
            .branchId(UPDATED_BRANCH_ID)
            .branchName(UPDATED_BRANCH_NAME)
            .salaryPaymentBasis(UPDATED_SALARY_PAYMENT_BASIS)
            .empStatus(UPDATED_EMP_STATUS)
            .religion(UPDATED_RELIGION)
            .experience(UPDATED_EXPERIENCE)
            .qualifications(UPDATED_QUALIFICATIONS)
            .attendanceCode(UPDATED_ATTENDANCE_CODE)
            .isActive(UPDATED_IS_ACTIVE);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeeUpdatableFieldsEquals(partialUpdatedEmployee, getPersistedEmployee(partialUpdatedEmployee));
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employee.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        insertedEmployee = employeeRepository.saveAndFlush(employee);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return employeeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Employee getPersistedEmployee(Employee employee) {
        return employeeRepository.findById(employee.getId()).orElseThrow();
    }

    protected void assertPersistedEmployeeToMatchAllProperties(Employee expectedEmployee) {
        assertEmployeeAllPropertiesEquals(expectedEmployee, getPersistedEmployee(expectedEmployee));
    }

    protected void assertPersistedEmployeeToMatchUpdatableProperties(Employee expectedEmployee) {
        assertEmployeeAllUpdatablePropertiesEquals(expectedEmployee, getPersistedEmployee(expectedEmployee));
    }
}
