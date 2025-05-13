package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.TransactionsAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Transactions;
import com.heavenscode.rac.repository.TransactionsRepository;
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
 * Integration tests for the {@link TransactionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionsResourceIT {

    private static final Integer DEFAULT_ACCOUNT_ID = 1;
    private static final Integer UPDATED_ACCOUNT_ID = 2;
    private static final Integer SMALLER_ACCOUNT_ID = 1 - 1;

    private static final String DEFAULT_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_DEBIT = 1D;
    private static final Double UPDATED_DEBIT = 2D;
    private static final Double SMALLER_DEBIT = 1D - 1D;

    private static final Double DEFAULT_CREDIT = 1D;
    private static final Double UPDATED_CREDIT = 2D;
    private static final Double SMALLER_CREDIT = 1D - 1D;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REF_DOC = "AAAAAAAAAA";
    private static final String UPDATED_REF_DOC = "BBBBBBBBBB";

    private static final Integer DEFAULT_REF_ID = 1;
    private static final Integer UPDATED_REF_ID = 2;
    private static final Integer SMALLER_REF_ID = 1 - 1;

    private static final String DEFAULT_SUB_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_TERM_ID = 1;
    private static final Integer UPDATED_PAYMENT_TERM_ID = 2;
    private static final Integer SMALLER_PAYMENT_TERM_ID = 1 - 1;

    private static final String DEFAULT_PAYMENT_TERM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionsMockMvc;

    private Transactions transactions;

    private Transactions insertedTransactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transactions createEntity() {
        return new Transactions()
            .accountId(DEFAULT_ACCOUNT_ID)
            .accountCode(DEFAULT_ACCOUNT_CODE)
            .debit(DEFAULT_DEBIT)
            .credit(DEFAULT_CREDIT)
            .date(DEFAULT_DATE)
            .refDoc(DEFAULT_REF_DOC)
            .refId(DEFAULT_REF_ID)
            .subId(DEFAULT_SUB_ID)
            .source(DEFAULT_SOURCE)
            .paymentTermId(DEFAULT_PAYMENT_TERM_ID)
            .paymentTermName(DEFAULT_PAYMENT_TERM_NAME)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transactions createUpdatedEntity() {
        return new Transactions()
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .debit(UPDATED_DEBIT)
            .credit(UPDATED_CREDIT)
            .date(UPDATED_DATE)
            .refDoc(UPDATED_REF_DOC)
            .refId(UPDATED_REF_ID)
            .subId(UPDATED_SUB_ID)
            .source(UPDATED_SOURCE)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .paymentTermName(UPDATED_PAYMENT_TERM_NAME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);
    }

    @BeforeEach
    public void initTest() {
        transactions = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTransactions != null) {
            transactionsRepository.delete(insertedTransactions);
            insertedTransactions = null;
        }
    }

    @Test
    @Transactional
    void createTransactions() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Transactions
        var returnedTransactions = om.readValue(
            restTransactionsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transactions)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Transactions.class
        );

        // Validate the Transactions in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTransactionsUpdatableFieldsEquals(returnedTransactions, getPersistedTransactions(returnedTransactions));

        insertedTransactions = returnedTransactions;
    }

    @Test
    @Transactional
    void createTransactionsWithExistingId() throws Exception {
        // Create the Transactions with an existing ID
        transactions.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transactions)))
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransactions() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].debit").value(hasItem(DEFAULT_DEBIT)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].refDoc").value(hasItem(DEFAULT_REF_DOC)))
            .andExpect(jsonPath("$.[*].refId").value(hasItem(DEFAULT_REF_ID)))
            .andExpect(jsonPath("$.[*].subId").value(hasItem(DEFAULT_SUB_ID)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].paymentTermId").value(hasItem(DEFAULT_PAYMENT_TERM_ID)))
            .andExpect(jsonPath("$.[*].paymentTermName").value(hasItem(DEFAULT_PAYMENT_TERM_NAME)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())));
    }

    @Test
    @Transactional
    void getTransactions() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get the transactions
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL_ID, transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactions.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.accountCode").value(DEFAULT_ACCOUNT_CODE))
            .andExpect(jsonPath("$.debit").value(DEFAULT_DEBIT))
            .andExpect(jsonPath("$.credit").value(DEFAULT_CREDIT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.refDoc").value(DEFAULT_REF_DOC))
            .andExpect(jsonPath("$.refId").value(DEFAULT_REF_ID))
            .andExpect(jsonPath("$.subId").value(DEFAULT_SUB_ID))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.paymentTermId").value(DEFAULT_PAYMENT_TERM_ID))
            .andExpect(jsonPath("$.paymentTermName").value(DEFAULT_PAYMENT_TERM_NAME))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()));
    }

    @Test
    @Transactional
    void getTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        Long id = transactions.getId();

        defaultTransactionsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTransactionsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTransactionsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountId equals to
        defaultTransactionsFiltering("accountId.equals=" + DEFAULT_ACCOUNT_ID, "accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountId in
        defaultTransactionsFiltering("accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID, "accountId.in=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountId is not null
        defaultTransactionsFiltering("accountId.specified=true", "accountId.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountId is greater than or equal to
        defaultTransactionsFiltering(
            "accountId.greaterThanOrEqual=" + DEFAULT_ACCOUNT_ID,
            "accountId.greaterThanOrEqual=" + UPDATED_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountId is less than or equal to
        defaultTransactionsFiltering("accountId.lessThanOrEqual=" + DEFAULT_ACCOUNT_ID, "accountId.lessThanOrEqual=" + SMALLER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountId is less than
        defaultTransactionsFiltering("accountId.lessThan=" + UPDATED_ACCOUNT_ID, "accountId.lessThan=" + DEFAULT_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountId is greater than
        defaultTransactionsFiltering("accountId.greaterThan=" + SMALLER_ACCOUNT_ID, "accountId.greaterThan=" + DEFAULT_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountCode equals to
        defaultTransactionsFiltering("accountCode.equals=" + DEFAULT_ACCOUNT_CODE, "accountCode.equals=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountCode in
        defaultTransactionsFiltering(
            "accountCode.in=" + DEFAULT_ACCOUNT_CODE + "," + UPDATED_ACCOUNT_CODE,
            "accountCode.in=" + UPDATED_ACCOUNT_CODE
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountCode is not null
        defaultTransactionsFiltering("accountCode.specified=true", "accountCode.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountCode contains
        defaultTransactionsFiltering("accountCode.contains=" + DEFAULT_ACCOUNT_CODE, "accountCode.contains=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllTransactionsByAccountCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where accountCode does not contain
        defaultTransactionsFiltering(
            "accountCode.doesNotContain=" + UPDATED_ACCOUNT_CODE,
            "accountCode.doesNotContain=" + DEFAULT_ACCOUNT_CODE
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByDebitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where debit equals to
        defaultTransactionsFiltering("debit.equals=" + DEFAULT_DEBIT, "debit.equals=" + UPDATED_DEBIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByDebitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where debit in
        defaultTransactionsFiltering("debit.in=" + DEFAULT_DEBIT + "," + UPDATED_DEBIT, "debit.in=" + UPDATED_DEBIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByDebitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where debit is not null
        defaultTransactionsFiltering("debit.specified=true", "debit.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByDebitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where debit is greater than or equal to
        defaultTransactionsFiltering("debit.greaterThanOrEqual=" + DEFAULT_DEBIT, "debit.greaterThanOrEqual=" + UPDATED_DEBIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByDebitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where debit is less than or equal to
        defaultTransactionsFiltering("debit.lessThanOrEqual=" + DEFAULT_DEBIT, "debit.lessThanOrEqual=" + SMALLER_DEBIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByDebitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where debit is less than
        defaultTransactionsFiltering("debit.lessThan=" + UPDATED_DEBIT, "debit.lessThan=" + DEFAULT_DEBIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByDebitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where debit is greater than
        defaultTransactionsFiltering("debit.greaterThan=" + SMALLER_DEBIT, "debit.greaterThan=" + DEFAULT_DEBIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where credit equals to
        defaultTransactionsFiltering("credit.equals=" + DEFAULT_CREDIT, "credit.equals=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCreditIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where credit in
        defaultTransactionsFiltering("credit.in=" + DEFAULT_CREDIT + "," + UPDATED_CREDIT, "credit.in=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where credit is not null
        defaultTransactionsFiltering("credit.specified=true", "credit.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByCreditIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where credit is greater than or equal to
        defaultTransactionsFiltering("credit.greaterThanOrEqual=" + DEFAULT_CREDIT, "credit.greaterThanOrEqual=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCreditIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where credit is less than or equal to
        defaultTransactionsFiltering("credit.lessThanOrEqual=" + DEFAULT_CREDIT, "credit.lessThanOrEqual=" + SMALLER_CREDIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCreditIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where credit is less than
        defaultTransactionsFiltering("credit.lessThan=" + UPDATED_CREDIT, "credit.lessThan=" + DEFAULT_CREDIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCreditIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where credit is greater than
        defaultTransactionsFiltering("credit.greaterThan=" + SMALLER_CREDIT, "credit.greaterThan=" + DEFAULT_CREDIT);
    }

    @Test
    @Transactional
    void getAllTransactionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where date equals to
        defaultTransactionsFiltering("date.equals=" + DEFAULT_DATE, "date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTransactionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where date in
        defaultTransactionsFiltering("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE, "date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTransactionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where date is not null
        defaultTransactionsFiltering("date.specified=true", "date.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByRefDocIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refDoc equals to
        defaultTransactionsFiltering("refDoc.equals=" + DEFAULT_REF_DOC, "refDoc.equals=" + UPDATED_REF_DOC);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefDocIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refDoc in
        defaultTransactionsFiltering("refDoc.in=" + DEFAULT_REF_DOC + "," + UPDATED_REF_DOC, "refDoc.in=" + UPDATED_REF_DOC);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefDocIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refDoc is not null
        defaultTransactionsFiltering("refDoc.specified=true", "refDoc.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByRefDocContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refDoc contains
        defaultTransactionsFiltering("refDoc.contains=" + DEFAULT_REF_DOC, "refDoc.contains=" + UPDATED_REF_DOC);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefDocNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refDoc does not contain
        defaultTransactionsFiltering("refDoc.doesNotContain=" + UPDATED_REF_DOC, "refDoc.doesNotContain=" + DEFAULT_REF_DOC);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refId equals to
        defaultTransactionsFiltering("refId.equals=" + DEFAULT_REF_ID, "refId.equals=" + UPDATED_REF_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refId in
        defaultTransactionsFiltering("refId.in=" + DEFAULT_REF_ID + "," + UPDATED_REF_ID, "refId.in=" + UPDATED_REF_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refId is not null
        defaultTransactionsFiltering("refId.specified=true", "refId.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByRefIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refId is greater than or equal to
        defaultTransactionsFiltering("refId.greaterThanOrEqual=" + DEFAULT_REF_ID, "refId.greaterThanOrEqual=" + UPDATED_REF_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refId is less than or equal to
        defaultTransactionsFiltering("refId.lessThanOrEqual=" + DEFAULT_REF_ID, "refId.lessThanOrEqual=" + SMALLER_REF_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refId is less than
        defaultTransactionsFiltering("refId.lessThan=" + UPDATED_REF_ID, "refId.lessThan=" + DEFAULT_REF_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByRefIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where refId is greater than
        defaultTransactionsFiltering("refId.greaterThan=" + SMALLER_REF_ID, "refId.greaterThan=" + DEFAULT_REF_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where source equals to
        defaultTransactionsFiltering("source.equals=" + DEFAULT_SOURCE, "source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllTransactionsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where source in
        defaultTransactionsFiltering("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE, "source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllTransactionsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where source is not null
        defaultTransactionsFiltering("source.specified=true", "source.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsBySourceContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where source contains
        defaultTransactionsFiltering("source.contains=" + DEFAULT_SOURCE, "source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllTransactionsBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where source does not contain
        defaultTransactionsFiltering("source.doesNotContain=" + UPDATED_SOURCE, "source.doesNotContain=" + DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermId equals to
        defaultTransactionsFiltering("paymentTermId.equals=" + DEFAULT_PAYMENT_TERM_ID, "paymentTermId.equals=" + UPDATED_PAYMENT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermId in
        defaultTransactionsFiltering(
            "paymentTermId.in=" + DEFAULT_PAYMENT_TERM_ID + "," + UPDATED_PAYMENT_TERM_ID,
            "paymentTermId.in=" + UPDATED_PAYMENT_TERM_ID
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermId is not null
        defaultTransactionsFiltering("paymentTermId.specified=true", "paymentTermId.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermId is greater than or equal to
        defaultTransactionsFiltering(
            "paymentTermId.greaterThanOrEqual=" + DEFAULT_PAYMENT_TERM_ID,
            "paymentTermId.greaterThanOrEqual=" + UPDATED_PAYMENT_TERM_ID
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermId is less than or equal to
        defaultTransactionsFiltering(
            "paymentTermId.lessThanOrEqual=" + DEFAULT_PAYMENT_TERM_ID,
            "paymentTermId.lessThanOrEqual=" + SMALLER_PAYMENT_TERM_ID
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermId is less than
        defaultTransactionsFiltering(
            "paymentTermId.lessThan=" + UPDATED_PAYMENT_TERM_ID,
            "paymentTermId.lessThan=" + DEFAULT_PAYMENT_TERM_ID
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermId is greater than
        defaultTransactionsFiltering(
            "paymentTermId.greaterThan=" + SMALLER_PAYMENT_TERM_ID,
            "paymentTermId.greaterThan=" + DEFAULT_PAYMENT_TERM_ID
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermName equals to
        defaultTransactionsFiltering(
            "paymentTermName.equals=" + DEFAULT_PAYMENT_TERM_NAME,
            "paymentTermName.equals=" + UPDATED_PAYMENT_TERM_NAME
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermName in
        defaultTransactionsFiltering(
            "paymentTermName.in=" + DEFAULT_PAYMENT_TERM_NAME + "," + UPDATED_PAYMENT_TERM_NAME,
            "paymentTermName.in=" + UPDATED_PAYMENT_TERM_NAME
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermName is not null
        defaultTransactionsFiltering("paymentTermName.specified=true", "paymentTermName.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermNameContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermName contains
        defaultTransactionsFiltering(
            "paymentTermName.contains=" + DEFAULT_PAYMENT_TERM_NAME,
            "paymentTermName.contains=" + UPDATED_PAYMENT_TERM_NAME
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByPaymentTermNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where paymentTermName does not contain
        defaultTransactionsFiltering(
            "paymentTermName.doesNotContain=" + UPDATED_PAYMENT_TERM_NAME,
            "paymentTermName.doesNotContain=" + DEFAULT_PAYMENT_TERM_NAME
        );
    }

    @Test
    @Transactional
    void getAllTransactionsByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmu equals to
        defaultTransactionsFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmu in
        defaultTransactionsFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmu is not null
        defaultTransactionsFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmu is greater than or equal to
        defaultTransactionsFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmu is less than or equal to
        defaultTransactionsFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmu is less than
        defaultTransactionsFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmu is greater than
        defaultTransactionsFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmd equals to
        defaultTransactionsFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmd in
        defaultTransactionsFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllTransactionsByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lmd is not null
        defaultTransactionsFiltering("lmd.specified=true", "lmd.specified=false");
    }

    private void defaultTransactionsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTransactionsShouldBeFound(shouldBeFound);
        defaultTransactionsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionsShouldBeFound(String filter) throws Exception {
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].debit").value(hasItem(DEFAULT_DEBIT)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(DEFAULT_CREDIT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].refDoc").value(hasItem(DEFAULT_REF_DOC)))
            .andExpect(jsonPath("$.[*].refId").value(hasItem(DEFAULT_REF_ID)))
            .andExpect(jsonPath("$.[*].subId").value(hasItem(DEFAULT_SUB_ID)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].paymentTermId").value(hasItem(DEFAULT_PAYMENT_TERM_ID)))
            .andExpect(jsonPath("$.[*].paymentTermName").value(hasItem(DEFAULT_PAYMENT_TERM_NAME)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())));

        // Check, that the count call also returns 1
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionsShouldNotBeFound(String filter) throws Exception {
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransactions() throws Exception {
        // Get the transactions
        restTransactionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransactions() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transactions
        Transactions updatedTransactions = transactionsRepository.findById(transactions.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTransactions are not directly saved in db
        em.detach(updatedTransactions);
        updatedTransactions
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .debit(UPDATED_DEBIT)
            .credit(UPDATED_CREDIT)
            .date(UPDATED_DATE)
            .refDoc(UPDATED_REF_DOC)
            .refId(UPDATED_REF_ID)
            .subId(UPDATED_SUB_ID)
            .source(UPDATED_SOURCE)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .paymentTermName(UPDATED_PAYMENT_TERM_NAME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);

        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTransactions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTransactions))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTransactionsToMatchAllProperties(updatedTransactions);
    }

    @Test
    @Transactional
    void putNonExistingTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transactions.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(transactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(transactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(transactions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionsWithPatch() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transactions using partial update
        Transactions partialUpdatedTransactions = new Transactions();
        partialUpdatedTransactions.setId(transactions.getId());

        partialUpdatedTransactions.debit(UPDATED_DEBIT).date(UPDATED_DATE).source(UPDATED_SOURCE).paymentTermId(UPDATED_PAYMENT_TERM_ID);

        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTransactions))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransactionsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTransactions, transactions),
            getPersistedTransactions(transactions)
        );
    }

    @Test
    @Transactional
    void fullUpdateTransactionsWithPatch() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transactions using partial update
        Transactions partialUpdatedTransactions = new Transactions();
        partialUpdatedTransactions.setId(transactions.getId());

        partialUpdatedTransactions
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .debit(UPDATED_DEBIT)
            .credit(UPDATED_CREDIT)
            .date(UPDATED_DATE)
            .refDoc(UPDATED_REF_DOC)
            .refId(UPDATED_REF_ID)
            .subId(UPDATED_SUB_ID)
            .source(UPDATED_SOURCE)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .paymentTermName(UPDATED_PAYMENT_TERM_NAME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);

        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTransactions))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransactionsUpdatableFieldsEquals(partialUpdatedTransactions, getPersistedTransactions(partialUpdatedTransactions));
    }

    @Test
    @Transactional
    void patchNonExistingTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transactions.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(transactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(transactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(transactions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactions() throws Exception {
        // Initialize the database
        insertedTransactions = transactionsRepository.saveAndFlush(transactions);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the transactions
        restTransactionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return transactionsRepository.count();
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

    protected Transactions getPersistedTransactions(Transactions transactions) {
        return transactionsRepository.findById(transactions.getId()).orElseThrow();
    }

    protected void assertPersistedTransactionsToMatchAllProperties(Transactions expectedTransactions) {
        assertTransactionsAllPropertiesEquals(expectedTransactions, getPersistedTransactions(expectedTransactions));
    }

    protected void assertPersistedTransactionsToMatchUpdatableProperties(Transactions expectedTransactions) {
        assertTransactionsAllUpdatablePropertiesEquals(expectedTransactions, getPersistedTransactions(expectedTransactions));
    }
}
