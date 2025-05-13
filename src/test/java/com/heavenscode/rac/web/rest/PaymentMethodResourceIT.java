package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.PaymentMethodAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.PaymentMethod;
import com.heavenscode.rac.repository.PaymentMethodRepository;
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
 * Integration tests for the {@link PaymentMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentMethodResourceIT {

    private static final String DEFAULT_PAYMENT_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_COMMISSION = 1D;
    private static final Double UPDATED_COMMISSION = 2D;
    private static final Double SMALLER_COMMISSION = 1D - 1D;

    private static final Integer DEFAULT_COMPANY_BANK_ACCOUNT_ID = 1;
    private static final Integer UPDATED_COMPANY_BANK_ACCOUNT_ID = 2;
    private static final Integer SMALLER_COMPANY_BANK_ACCOUNT_ID = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final String ENTITY_API_URL = "/api/payment-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMethodMockMvc;

    private PaymentMethod paymentMethod;

    private PaymentMethod insertedPaymentMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createEntity() {
        return new PaymentMethod()
            .paymentMethodName(DEFAULT_PAYMENT_METHOD_NAME)
            .commission(DEFAULT_COMMISSION)
            .companyBankAccountId(DEFAULT_COMPANY_BANK_ACCOUNT_ID)
            .lmd(DEFAULT_LMD)
            .lmu(DEFAULT_LMU);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createUpdatedEntity() {
        return new PaymentMethod()
            .paymentMethodName(UPDATED_PAYMENT_METHOD_NAME)
            .commission(UPDATED_COMMISSION)
            .companyBankAccountId(UPDATED_COMPANY_BANK_ACCOUNT_ID)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);
    }

    @BeforeEach
    public void initTest() {
        paymentMethod = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPaymentMethod != null) {
            paymentMethodRepository.delete(insertedPaymentMethod);
            insertedPaymentMethod = null;
        }
    }

    @Test
    @Transactional
    void createPaymentMethod() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PaymentMethod
        var returnedPaymentMethod = om.readValue(
            restPaymentMethodMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentMethod)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaymentMethod.class
        );

        // Validate the PaymentMethod in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPaymentMethodUpdatableFieldsEquals(returnedPaymentMethod, getPersistedPaymentMethod(returnedPaymentMethod));

        insertedPaymentMethod = returnedPaymentMethod;
    }

    @Test
    @Transactional
    void createPaymentMethodWithExistingId() throws Exception {
        // Create the PaymentMethod with an existing ID
        paymentMethod.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentMethod)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentMethods() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethodName").value(hasItem(DEFAULT_PAYMENT_METHOD_NAME)))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION)))
            .andExpect(jsonPath("$.[*].companyBankAccountId").value(hasItem(DEFAULT_COMPANY_BANK_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)));
    }

    @Test
    @Transactional
    void getPaymentMethod() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get the paymentMethod
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethod.getId().intValue()))
            .andExpect(jsonPath("$.paymentMethodName").value(DEFAULT_PAYMENT_METHOD_NAME))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION))
            .andExpect(jsonPath("$.companyBankAccountId").value(DEFAULT_COMPANY_BANK_ACCOUNT_ID))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU));
    }

    @Test
    @Transactional
    void getPaymentMethodsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        Long id = paymentMethod.getId();

        defaultPaymentMethodFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPaymentMethodFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPaymentMethodFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentMethodNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentMethodName equals to
        defaultPaymentMethodFiltering(
            "paymentMethodName.equals=" + DEFAULT_PAYMENT_METHOD_NAME,
            "paymentMethodName.equals=" + UPDATED_PAYMENT_METHOD_NAME
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentMethodNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentMethodName in
        defaultPaymentMethodFiltering(
            "paymentMethodName.in=" + DEFAULT_PAYMENT_METHOD_NAME + "," + UPDATED_PAYMENT_METHOD_NAME,
            "paymentMethodName.in=" + UPDATED_PAYMENT_METHOD_NAME
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentMethodNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentMethodName is not null
        defaultPaymentMethodFiltering("paymentMethodName.specified=true", "paymentMethodName.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentMethodNameContainsSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentMethodName contains
        defaultPaymentMethodFiltering(
            "paymentMethodName.contains=" + DEFAULT_PAYMENT_METHOD_NAME,
            "paymentMethodName.contains=" + UPDATED_PAYMENT_METHOD_NAME
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByPaymentMethodNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where paymentMethodName does not contain
        defaultPaymentMethodFiltering(
            "paymentMethodName.doesNotContain=" + UPDATED_PAYMENT_METHOD_NAME,
            "paymentMethodName.doesNotContain=" + DEFAULT_PAYMENT_METHOD_NAME
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where commission equals to
        defaultPaymentMethodFiltering("commission.equals=" + DEFAULT_COMMISSION, "commission.equals=" + UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where commission in
        defaultPaymentMethodFiltering(
            "commission.in=" + DEFAULT_COMMISSION + "," + UPDATED_COMMISSION,
            "commission.in=" + UPDATED_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where commission is not null
        defaultPaymentMethodFiltering("commission.specified=true", "commission.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where commission is greater than or equal to
        defaultPaymentMethodFiltering(
            "commission.greaterThanOrEqual=" + DEFAULT_COMMISSION,
            "commission.greaterThanOrEqual=" + UPDATED_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where commission is less than or equal to
        defaultPaymentMethodFiltering(
            "commission.lessThanOrEqual=" + DEFAULT_COMMISSION,
            "commission.lessThanOrEqual=" + SMALLER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where commission is less than
        defaultPaymentMethodFiltering("commission.lessThan=" + UPDATED_COMMISSION, "commission.lessThan=" + DEFAULT_COMMISSION);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where commission is greater than
        defaultPaymentMethodFiltering("commission.greaterThan=" + SMALLER_COMMISSION, "commission.greaterThan=" + DEFAULT_COMMISSION);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCompanyBankAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where companyBankAccountId equals to
        defaultPaymentMethodFiltering(
            "companyBankAccountId.equals=" + DEFAULT_COMPANY_BANK_ACCOUNT_ID,
            "companyBankAccountId.equals=" + UPDATED_COMPANY_BANK_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCompanyBankAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where companyBankAccountId in
        defaultPaymentMethodFiltering(
            "companyBankAccountId.in=" + DEFAULT_COMPANY_BANK_ACCOUNT_ID + "," + UPDATED_COMPANY_BANK_ACCOUNT_ID,
            "companyBankAccountId.in=" + UPDATED_COMPANY_BANK_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCompanyBankAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where companyBankAccountId is not null
        defaultPaymentMethodFiltering("companyBankAccountId.specified=true", "companyBankAccountId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCompanyBankAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where companyBankAccountId is greater than or equal to
        defaultPaymentMethodFiltering(
            "companyBankAccountId.greaterThanOrEqual=" + DEFAULT_COMPANY_BANK_ACCOUNT_ID,
            "companyBankAccountId.greaterThanOrEqual=" + UPDATED_COMPANY_BANK_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCompanyBankAccountIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where companyBankAccountId is less than or equal to
        defaultPaymentMethodFiltering(
            "companyBankAccountId.lessThanOrEqual=" + DEFAULT_COMPANY_BANK_ACCOUNT_ID,
            "companyBankAccountId.lessThanOrEqual=" + SMALLER_COMPANY_BANK_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCompanyBankAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where companyBankAccountId is less than
        defaultPaymentMethodFiltering(
            "companyBankAccountId.lessThan=" + UPDATED_COMPANY_BANK_ACCOUNT_ID,
            "companyBankAccountId.lessThan=" + DEFAULT_COMPANY_BANK_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByCompanyBankAccountIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where companyBankAccountId is greater than
        defaultPaymentMethodFiltering(
            "companyBankAccountId.greaterThan=" + SMALLER_COMPANY_BANK_ACCOUNT_ID,
            "companyBankAccountId.greaterThan=" + DEFAULT_COMPANY_BANK_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmd equals to
        defaultPaymentMethodFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmd in
        defaultPaymentMethodFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmd is not null
        defaultPaymentMethodFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmu equals to
        defaultPaymentMethodFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmu in
        defaultPaymentMethodFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmu is not null
        defaultPaymentMethodFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmu is greater than or equal to
        defaultPaymentMethodFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmu is less than or equal to
        defaultPaymentMethodFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmu is less than
        defaultPaymentMethodFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllPaymentMethodsByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList where lmu is greater than
        defaultPaymentMethodFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    private void defaultPaymentMethodFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPaymentMethodShouldBeFound(shouldBeFound);
        defaultPaymentMethodShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentMethodShouldBeFound(String filter) throws Exception {
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethodName").value(hasItem(DEFAULT_PAYMENT_METHOD_NAME)))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION)))
            .andExpect(jsonPath("$.[*].companyBankAccountId").value(hasItem(DEFAULT_COMPANY_BANK_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)));

        // Check, that the count call also returns 1
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentMethodShouldNotBeFound(String filter) throws Exception {
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaymentMethod() throws Exception {
        // Get the paymentMethod
        restPaymentMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentMethod() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentMethod
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.findById(paymentMethod.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaymentMethod are not directly saved in db
        em.detach(updatedPaymentMethod);
        updatedPaymentMethod
            .paymentMethodName(UPDATED_PAYMENT_METHOD_NAME)
            .commission(UPDATED_COMMISSION)
            .companyBankAccountId(UPDATED_COMPANY_BANK_ACCOUNT_ID)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);

        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaymentMethodToMatchAllProperties(updatedPaymentMethod);
    }

    @Test
    @Transactional
    void putNonExistingPaymentMethod() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentMethod.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentMethod.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentMethod() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentMethod.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentMethod() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentMethod.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentMethod)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod.paymentMethodName(UPDATED_PAYMENT_METHOD_NAME).lmd(UPDATED_LMD).lmu(UPDATED_LMU);

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentMethodUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPaymentMethod, paymentMethod),
            getPersistedPaymentMethod(paymentMethod)
        );
    }

    @Test
    @Transactional
    void fullUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod
            .paymentMethodName(UPDATED_PAYMENT_METHOD_NAME)
            .commission(UPDATED_COMMISSION)
            .companyBankAccountId(UPDATED_COMPANY_BANK_ACCOUNT_ID)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentMethodUpdatableFieldsEquals(partialUpdatedPaymentMethod, getPersistedPaymentMethod(partialUpdatedPaymentMethod));
    }

    @Test
    @Transactional
    void patchNonExistingPaymentMethod() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentMethod.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentMethod() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentMethod.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentMethod))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentMethod() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentMethod.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paymentMethod)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentMethod() throws Exception {
        // Initialize the database
        insertedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethod);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paymentMethod
        restPaymentMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentMethod.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paymentMethodRepository.count();
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

    protected PaymentMethod getPersistedPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.findById(paymentMethod.getId()).orElseThrow();
    }

    protected void assertPersistedPaymentMethodToMatchAllProperties(PaymentMethod expectedPaymentMethod) {
        assertPaymentMethodAllPropertiesEquals(expectedPaymentMethod, getPersistedPaymentMethod(expectedPaymentMethod));
    }

    protected void assertPersistedPaymentMethodToMatchUpdatableProperties(PaymentMethod expectedPaymentMethod) {
        assertPaymentMethodAllUpdatablePropertiesEquals(expectedPaymentMethod, getPersistedPaymentMethod(expectedPaymentMethod));
    }
}
