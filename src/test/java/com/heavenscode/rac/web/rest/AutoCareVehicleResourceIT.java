package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutoCareVehicleAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.AutoCareVehicle;
import com.heavenscode.rac.repository.AutoCareVehicleRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
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
 * Integration tests for the {@link AutoCareVehicleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutoCareVehicleResourceIT {

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;
    private static final Integer SMALLER_CUSTOMER_ID = 1 - 1;

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_TEL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_BRAND_ID = 1;
    private static final Integer UPDATED_BRAND_ID = 2;
    private static final Integer SMALLER_BRAND_ID = 1 - 1;

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_MILLAGE = 1D;
    private static final Double UPDATED_MILLAGE = 2D;
    private static final Double SMALLER_MILLAGE = 1D - 1D;

    private static final String DEFAULT_MANUFACTURE_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURE_YEAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_SERVICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_SERVICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NEXT_SERVICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEXT_SERVICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/auto-care-vehicles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutoCareVehicleRepository autoCareVehicleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutoCareVehicleMockMvc;

    private AutoCareVehicle autoCareVehicle;

    private AutoCareVehicle insertedAutoCareVehicle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoCareVehicle createEntity() {
        return new AutoCareVehicle()
            .customerId(DEFAULT_CUSTOMER_ID)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .customerTel(DEFAULT_CUSTOMER_TEL)
            .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
            .brandId(DEFAULT_BRAND_ID)
            .brandName(DEFAULT_BRAND_NAME)
            .model(DEFAULT_MODEL)
            .millage(DEFAULT_MILLAGE)
            .manufactureYear(DEFAULT_MANUFACTURE_YEAR)
            .lastServiceDate(DEFAULT_LAST_SERVICE_DATE)
            .nextServiceDate(DEFAULT_NEXT_SERVICE_DATE)
            .description(DEFAULT_DESCRIPTION)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoCareVehicle createUpdatedEntity() {
        return new AutoCareVehicle()
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerTel(UPDATED_CUSTOMER_TEL)
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .brandId(UPDATED_BRAND_ID)
            .brandName(UPDATED_BRAND_NAME)
            .model(UPDATED_MODEL)
            .millage(UPDATED_MILLAGE)
            .manufactureYear(UPDATED_MANUFACTURE_YEAR)
            .lastServiceDate(UPDATED_LAST_SERVICE_DATE)
            .nextServiceDate(UPDATED_NEXT_SERVICE_DATE)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);
    }

    @BeforeEach
    public void initTest() {
        autoCareVehicle = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutoCareVehicle != null) {
            autoCareVehicleRepository.delete(insertedAutoCareVehicle);
            insertedAutoCareVehicle = null;
        }
    }

    @Test
    @Transactional
    void createAutoCareVehicle() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AutoCareVehicle
        var returnedAutoCareVehicle = om.readValue(
            restAutoCareVehicleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autoCareVehicle)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AutoCareVehicle.class
        );

        // Validate the AutoCareVehicle in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutoCareVehicleUpdatableFieldsEquals(returnedAutoCareVehicle, getPersistedAutoCareVehicle(returnedAutoCareVehicle));

        insertedAutoCareVehicle = returnedAutoCareVehicle;
    }

    @Test
    @Transactional
    void createAutoCareVehicleWithExistingId() throws Exception {
        // Create the AutoCareVehicle with an existing ID
        autoCareVehicle.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoCareVehicleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autoCareVehicle)))
            .andExpect(status().isBadRequest());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutoCareVehicles() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList
        restAutoCareVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoCareVehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerTel").value(hasItem(DEFAULT_CUSTOMER_TEL)))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER)))
            .andExpect(jsonPath("$.[*].brandId").value(hasItem(DEFAULT_BRAND_ID)))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].millage").value(hasItem(DEFAULT_MILLAGE)))
            .andExpect(jsonPath("$.[*].manufactureYear").value(hasItem(DEFAULT_MANUFACTURE_YEAR)))
            .andExpect(jsonPath("$.[*].lastServiceDate").value(hasItem(DEFAULT_LAST_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextServiceDate").value(hasItem(DEFAULT_NEXT_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())));
    }

    @Test
    @Transactional
    void getAutoCareVehicle() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get the autoCareVehicle
        restAutoCareVehicleMockMvc
            .perform(get(ENTITY_API_URL_ID, autoCareVehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autoCareVehicle.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.customerTel").value(DEFAULT_CUSTOMER_TEL))
            .andExpect(jsonPath("$.vehicleNumber").value(DEFAULT_VEHICLE_NUMBER))
            .andExpect(jsonPath("$.brandId").value(DEFAULT_BRAND_ID))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.millage").value(DEFAULT_MILLAGE))
            .andExpect(jsonPath("$.manufactureYear").value(DEFAULT_MANUFACTURE_YEAR))
            .andExpect(jsonPath("$.lastServiceDate").value(DEFAULT_LAST_SERVICE_DATE.toString()))
            .andExpect(jsonPath("$.nextServiceDate").value(DEFAULT_NEXT_SERVICE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()));
    }

    @Test
    @Transactional
    void getAutoCareVehiclesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        Long id = autoCareVehicle.getId();

        defaultAutoCareVehicleFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutoCareVehicleFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutoCareVehicleFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerId equals to
        defaultAutoCareVehicleFiltering("customerId.equals=" + DEFAULT_CUSTOMER_ID, "customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerId in
        defaultAutoCareVehicleFiltering(
            "customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID,
            "customerId.in=" + UPDATED_CUSTOMER_ID
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerId is not null
        defaultAutoCareVehicleFiltering("customerId.specified=true", "customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerId is greater than or equal to
        defaultAutoCareVehicleFiltering(
            "customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID,
            "customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerId is less than or equal to
        defaultAutoCareVehicleFiltering(
            "customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID,
            "customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerId is less than
        defaultAutoCareVehicleFiltering("customerId.lessThan=" + UPDATED_CUSTOMER_ID, "customerId.lessThan=" + DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerId is greater than
        defaultAutoCareVehicleFiltering("customerId.greaterThan=" + SMALLER_CUSTOMER_ID, "customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerName equals to
        defaultAutoCareVehicleFiltering("customerName.equals=" + DEFAULT_CUSTOMER_NAME, "customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerName in
        defaultAutoCareVehicleFiltering(
            "customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME,
            "customerName.in=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerName is not null
        defaultAutoCareVehicleFiltering("customerName.specified=true", "customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerName contains
        defaultAutoCareVehicleFiltering("customerName.contains=" + DEFAULT_CUSTOMER_NAME, "customerName.contains=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerName does not contain
        defaultAutoCareVehicleFiltering(
            "customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME,
            "customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerTelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerTel equals to
        defaultAutoCareVehicleFiltering("customerTel.equals=" + DEFAULT_CUSTOMER_TEL, "customerTel.equals=" + UPDATED_CUSTOMER_TEL);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerTelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerTel in
        defaultAutoCareVehicleFiltering(
            "customerTel.in=" + DEFAULT_CUSTOMER_TEL + "," + UPDATED_CUSTOMER_TEL,
            "customerTel.in=" + UPDATED_CUSTOMER_TEL
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerTel is not null
        defaultAutoCareVehicleFiltering("customerTel.specified=true", "customerTel.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerTelContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerTel contains
        defaultAutoCareVehicleFiltering("customerTel.contains=" + DEFAULT_CUSTOMER_TEL, "customerTel.contains=" + UPDATED_CUSTOMER_TEL);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByCustomerTelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where customerTel does not contain
        defaultAutoCareVehicleFiltering(
            "customerTel.doesNotContain=" + UPDATED_CUSTOMER_TEL,
            "customerTel.doesNotContain=" + DEFAULT_CUSTOMER_TEL
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByVehicleNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where vehicleNumber equals to
        defaultAutoCareVehicleFiltering("vehicleNumber.equals=" + DEFAULT_VEHICLE_NUMBER, "vehicleNumber.equals=" + UPDATED_VEHICLE_NUMBER);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByVehicleNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where vehicleNumber in
        defaultAutoCareVehicleFiltering(
            "vehicleNumber.in=" + DEFAULT_VEHICLE_NUMBER + "," + UPDATED_VEHICLE_NUMBER,
            "vehicleNumber.in=" + UPDATED_VEHICLE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByVehicleNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where vehicleNumber is not null
        defaultAutoCareVehicleFiltering("vehicleNumber.specified=true", "vehicleNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByVehicleNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where vehicleNumber contains
        defaultAutoCareVehicleFiltering(
            "vehicleNumber.contains=" + DEFAULT_VEHICLE_NUMBER,
            "vehicleNumber.contains=" + UPDATED_VEHICLE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByVehicleNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where vehicleNumber does not contain
        defaultAutoCareVehicleFiltering(
            "vehicleNumber.doesNotContain=" + UPDATED_VEHICLE_NUMBER,
            "vehicleNumber.doesNotContain=" + DEFAULT_VEHICLE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandId equals to
        defaultAutoCareVehicleFiltering("brandId.equals=" + DEFAULT_BRAND_ID, "brandId.equals=" + UPDATED_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandId in
        defaultAutoCareVehicleFiltering("brandId.in=" + DEFAULT_BRAND_ID + "," + UPDATED_BRAND_ID, "brandId.in=" + UPDATED_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandId is not null
        defaultAutoCareVehicleFiltering("brandId.specified=true", "brandId.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandId is greater than or equal to
        defaultAutoCareVehicleFiltering("brandId.greaterThanOrEqual=" + DEFAULT_BRAND_ID, "brandId.greaterThanOrEqual=" + UPDATED_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandId is less than or equal to
        defaultAutoCareVehicleFiltering("brandId.lessThanOrEqual=" + DEFAULT_BRAND_ID, "brandId.lessThanOrEqual=" + SMALLER_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandId is less than
        defaultAutoCareVehicleFiltering("brandId.lessThan=" + UPDATED_BRAND_ID, "brandId.lessThan=" + DEFAULT_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandId is greater than
        defaultAutoCareVehicleFiltering("brandId.greaterThan=" + SMALLER_BRAND_ID, "brandId.greaterThan=" + DEFAULT_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandName equals to
        defaultAutoCareVehicleFiltering("brandName.equals=" + DEFAULT_BRAND_NAME, "brandName.equals=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandName in
        defaultAutoCareVehicleFiltering(
            "brandName.in=" + DEFAULT_BRAND_NAME + "," + UPDATED_BRAND_NAME,
            "brandName.in=" + UPDATED_BRAND_NAME
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandName is not null
        defaultAutoCareVehicleFiltering("brandName.specified=true", "brandName.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandName contains
        defaultAutoCareVehicleFiltering("brandName.contains=" + DEFAULT_BRAND_NAME, "brandName.contains=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByBrandNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where brandName does not contain
        defaultAutoCareVehicleFiltering("brandName.doesNotContain=" + UPDATED_BRAND_NAME, "brandName.doesNotContain=" + DEFAULT_BRAND_NAME);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where model equals to
        defaultAutoCareVehicleFiltering("model.equals=" + DEFAULT_MODEL, "model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByModelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where model in
        defaultAutoCareVehicleFiltering("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL, "model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where model is not null
        defaultAutoCareVehicleFiltering("model.specified=true", "model.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByModelContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where model contains
        defaultAutoCareVehicleFiltering("model.contains=" + DEFAULT_MODEL, "model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByModelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where model does not contain
        defaultAutoCareVehicleFiltering("model.doesNotContain=" + UPDATED_MODEL, "model.doesNotContain=" + DEFAULT_MODEL);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByMillageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where millage equals to
        defaultAutoCareVehicleFiltering("millage.equals=" + DEFAULT_MILLAGE, "millage.equals=" + UPDATED_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByMillageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where millage in
        defaultAutoCareVehicleFiltering("millage.in=" + DEFAULT_MILLAGE + "," + UPDATED_MILLAGE, "millage.in=" + UPDATED_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByMillageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where millage is not null
        defaultAutoCareVehicleFiltering("millage.specified=true", "millage.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByMillageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where millage is greater than or equal to
        defaultAutoCareVehicleFiltering("millage.greaterThanOrEqual=" + DEFAULT_MILLAGE, "millage.greaterThanOrEqual=" + UPDATED_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByMillageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where millage is less than or equal to
        defaultAutoCareVehicleFiltering("millage.lessThanOrEqual=" + DEFAULT_MILLAGE, "millage.lessThanOrEqual=" + SMALLER_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByMillageIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where millage is less than
        defaultAutoCareVehicleFiltering("millage.lessThan=" + UPDATED_MILLAGE, "millage.lessThan=" + DEFAULT_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByMillageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where millage is greater than
        defaultAutoCareVehicleFiltering("millage.greaterThan=" + SMALLER_MILLAGE, "millage.greaterThan=" + DEFAULT_MILLAGE);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByManufactureYearIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where manufactureYear equals to
        defaultAutoCareVehicleFiltering(
            "manufactureYear.equals=" + DEFAULT_MANUFACTURE_YEAR,
            "manufactureYear.equals=" + UPDATED_MANUFACTURE_YEAR
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByManufactureYearIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where manufactureYear in
        defaultAutoCareVehicleFiltering(
            "manufactureYear.in=" + DEFAULT_MANUFACTURE_YEAR + "," + UPDATED_MANUFACTURE_YEAR,
            "manufactureYear.in=" + UPDATED_MANUFACTURE_YEAR
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByManufactureYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where manufactureYear is not null
        defaultAutoCareVehicleFiltering("manufactureYear.specified=true", "manufactureYear.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByManufactureYearContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where manufactureYear contains
        defaultAutoCareVehicleFiltering(
            "manufactureYear.contains=" + DEFAULT_MANUFACTURE_YEAR,
            "manufactureYear.contains=" + UPDATED_MANUFACTURE_YEAR
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByManufactureYearNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where manufactureYear does not contain
        defaultAutoCareVehicleFiltering(
            "manufactureYear.doesNotContain=" + UPDATED_MANUFACTURE_YEAR,
            "manufactureYear.doesNotContain=" + DEFAULT_MANUFACTURE_YEAR
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLastServiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lastServiceDate equals to
        defaultAutoCareVehicleFiltering(
            "lastServiceDate.equals=" + DEFAULT_LAST_SERVICE_DATE,
            "lastServiceDate.equals=" + UPDATED_LAST_SERVICE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLastServiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lastServiceDate in
        defaultAutoCareVehicleFiltering(
            "lastServiceDate.in=" + DEFAULT_LAST_SERVICE_DATE + "," + UPDATED_LAST_SERVICE_DATE,
            "lastServiceDate.in=" + UPDATED_LAST_SERVICE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLastServiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lastServiceDate is not null
        defaultAutoCareVehicleFiltering("lastServiceDate.specified=true", "lastServiceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByNextServiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where nextServiceDate equals to
        defaultAutoCareVehicleFiltering(
            "nextServiceDate.equals=" + DEFAULT_NEXT_SERVICE_DATE,
            "nextServiceDate.equals=" + UPDATED_NEXT_SERVICE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByNextServiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where nextServiceDate in
        defaultAutoCareVehicleFiltering(
            "nextServiceDate.in=" + DEFAULT_NEXT_SERVICE_DATE + "," + UPDATED_NEXT_SERVICE_DATE,
            "nextServiceDate.in=" + UPDATED_NEXT_SERVICE_DATE
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByNextServiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where nextServiceDate is not null
        defaultAutoCareVehicleFiltering("nextServiceDate.specified=true", "nextServiceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where description equals to
        defaultAutoCareVehicleFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where description in
        defaultAutoCareVehicleFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where description is not null
        defaultAutoCareVehicleFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where description contains
        defaultAutoCareVehicleFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where description does not contain
        defaultAutoCareVehicleFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmu equals to
        defaultAutoCareVehicleFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmu in
        defaultAutoCareVehicleFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmu is not null
        defaultAutoCareVehicleFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmu is greater than or equal to
        defaultAutoCareVehicleFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmu is less than or equal to
        defaultAutoCareVehicleFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmu is less than
        defaultAutoCareVehicleFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmu is greater than
        defaultAutoCareVehicleFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmd equals to
        defaultAutoCareVehicleFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmd in
        defaultAutoCareVehicleFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutoCareVehiclesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        // Get all the autoCareVehicleList where lmd is not null
        defaultAutoCareVehicleFiltering("lmd.specified=true", "lmd.specified=false");
    }

    private void defaultAutoCareVehicleFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutoCareVehicleShouldBeFound(shouldBeFound);
        defaultAutoCareVehicleShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutoCareVehicleShouldBeFound(String filter) throws Exception {
        restAutoCareVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoCareVehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerTel").value(hasItem(DEFAULT_CUSTOMER_TEL)))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER)))
            .andExpect(jsonPath("$.[*].brandId").value(hasItem(DEFAULT_BRAND_ID)))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].millage").value(hasItem(DEFAULT_MILLAGE)))
            .andExpect(jsonPath("$.[*].manufactureYear").value(hasItem(DEFAULT_MANUFACTURE_YEAR)))
            .andExpect(jsonPath("$.[*].lastServiceDate").value(hasItem(DEFAULT_LAST_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextServiceDate").value(hasItem(DEFAULT_NEXT_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())));

        // Check, that the count call also returns 1
        restAutoCareVehicleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutoCareVehicleShouldNotBeFound(String filter) throws Exception {
        restAutoCareVehicleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutoCareVehicleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutoCareVehicle() throws Exception {
        // Get the autoCareVehicle
        restAutoCareVehicleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutoCareVehicle() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autoCareVehicle
        AutoCareVehicle updatedAutoCareVehicle = autoCareVehicleRepository.findById(autoCareVehicle.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAutoCareVehicle are not directly saved in db
        em.detach(updatedAutoCareVehicle);
        updatedAutoCareVehicle
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerTel(UPDATED_CUSTOMER_TEL)
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .brandId(UPDATED_BRAND_ID)
            .brandName(UPDATED_BRAND_NAME)
            .model(UPDATED_MODEL)
            .millage(UPDATED_MILLAGE)
            .manufactureYear(UPDATED_MANUFACTURE_YEAR)
            .lastServiceDate(UPDATED_LAST_SERVICE_DATE)
            .nextServiceDate(UPDATED_NEXT_SERVICE_DATE)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);

        restAutoCareVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutoCareVehicle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutoCareVehicle))
            )
            .andExpect(status().isOk());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutoCareVehicleToMatchAllProperties(updatedAutoCareVehicle);
    }

    @Test
    @Transactional
    void putNonExistingAutoCareVehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoCareVehicle.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoCareVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autoCareVehicle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autoCareVehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutoCareVehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoCareVehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoCareVehicleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autoCareVehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutoCareVehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoCareVehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoCareVehicleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autoCareVehicle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutoCareVehicleWithPatch() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autoCareVehicle using partial update
        AutoCareVehicle partialUpdatedAutoCareVehicle = new AutoCareVehicle();
        partialUpdatedAutoCareVehicle.setId(autoCareVehicle.getId());

        partialUpdatedAutoCareVehicle
            .customerId(UPDATED_CUSTOMER_ID)
            .customerTel(UPDATED_CUSTOMER_TEL)
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .brandName(UPDATED_BRAND_NAME)
            .model(UPDATED_MODEL)
            .manufactureYear(UPDATED_MANUFACTURE_YEAR)
            .lastServiceDate(UPDATED_LAST_SERVICE_DATE)
            .nextServiceDate(UPDATED_NEXT_SERVICE_DATE)
            .description(UPDATED_DESCRIPTION)
            .lmd(UPDATED_LMD);

        restAutoCareVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoCareVehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutoCareVehicle))
            )
            .andExpect(status().isOk());

        // Validate the AutoCareVehicle in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutoCareVehicleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutoCareVehicle, autoCareVehicle),
            getPersistedAutoCareVehicle(autoCareVehicle)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutoCareVehicleWithPatch() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autoCareVehicle using partial update
        AutoCareVehicle partialUpdatedAutoCareVehicle = new AutoCareVehicle();
        partialUpdatedAutoCareVehicle.setId(autoCareVehicle.getId());

        partialUpdatedAutoCareVehicle
            .customerId(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerTel(UPDATED_CUSTOMER_TEL)
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .brandId(UPDATED_BRAND_ID)
            .brandName(UPDATED_BRAND_NAME)
            .model(UPDATED_MODEL)
            .millage(UPDATED_MILLAGE)
            .manufactureYear(UPDATED_MANUFACTURE_YEAR)
            .lastServiceDate(UPDATED_LAST_SERVICE_DATE)
            .nextServiceDate(UPDATED_NEXT_SERVICE_DATE)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);

        restAutoCareVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoCareVehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutoCareVehicle))
            )
            .andExpect(status().isOk());

        // Validate the AutoCareVehicle in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutoCareVehicleUpdatableFieldsEquals(
            partialUpdatedAutoCareVehicle,
            getPersistedAutoCareVehicle(partialUpdatedAutoCareVehicle)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutoCareVehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoCareVehicle.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoCareVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autoCareVehicle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autoCareVehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutoCareVehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoCareVehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoCareVehicleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autoCareVehicle))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutoCareVehicle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autoCareVehicle.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoCareVehicleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autoCareVehicle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoCareVehicle in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutoCareVehicle() throws Exception {
        // Initialize the database
        insertedAutoCareVehicle = autoCareVehicleRepository.saveAndFlush(autoCareVehicle);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autoCareVehicle
        restAutoCareVehicleMockMvc
            .perform(delete(ENTITY_API_URL_ID, autoCareVehicle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autoCareVehicleRepository.count();
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

    protected AutoCareVehicle getPersistedAutoCareVehicle(AutoCareVehicle autoCareVehicle) {
        return autoCareVehicleRepository.findById(autoCareVehicle.getId()).orElseThrow();
    }

    protected void assertPersistedAutoCareVehicleToMatchAllProperties(AutoCareVehicle expectedAutoCareVehicle) {
        assertAutoCareVehicleAllPropertiesEquals(expectedAutoCareVehicle, getPersistedAutoCareVehicle(expectedAutoCareVehicle));
    }

    protected void assertPersistedAutoCareVehicleToMatchUpdatableProperties(AutoCareVehicle expectedAutoCareVehicle) {
        assertAutoCareVehicleAllUpdatablePropertiesEquals(expectedAutoCareVehicle, getPersistedAutoCareVehicle(expectedAutoCareVehicle));
    }
}
