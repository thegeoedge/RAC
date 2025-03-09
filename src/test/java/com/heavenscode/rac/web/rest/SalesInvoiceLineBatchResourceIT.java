package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SalesInvoiceLineBatchAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static com.heavenscode.rac.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SalesInvoiceLineBatch;
import com.heavenscode.rac.repository.SalesInvoiceLineBatchRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SalesInvoiceLineBatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesInvoiceLineBatchResourceIT {

    private static final Long DEFAULT_LINE_ID = 1L;
    private static final Long UPDATED_LINE_ID = 2L;
    private static final Long SMALLER_LINE_ID = 1L - 1L;

    private static final Long DEFAULT_BATCH_LINE_ID = 1L;
    private static final Long UPDATED_BATCH_LINE_ID = 2L;
    private static final Long SMALLER_BATCH_LINE_ID = 1L - 1L;

    private static final Long DEFAULT_ITEM_ID = 1L;
    private static final Long UPDATED_ITEM_ID = 2L;
    private static final Long SMALLER_ITEM_ID = 1L - 1L;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_BATCH_ID = 1L;
    private static final Long UPDATED_BATCH_ID = 2L;
    private static final Long SMALLER_BATCH_ID = 1L - 1L;

    private static final String DEFAULT_BATCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TX_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TX_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MANUFACTURE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MANUFACTURE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;
    private static final Double SMALLER_QTY = 1D - 1D;

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Long DEFAULT_LMU = 1L;
    private static final Long UPDATED_LMU = 2L;
    private static final Long SMALLER_LMU = 1L - 1L;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_NBT = false;
    private static final Boolean UPDATED_NBT = true;

    private static final Boolean DEFAULT_VAT = false;
    private static final Boolean UPDATED_VAT = true;

    private static final Long DEFAULT_ADDED_BY_ID = 1L;
    private static final Long UPDATED_ADDED_BY_ID = 2L;
    private static final Long SMALLER_ADDED_BY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/sales-invoice-line-batches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalesInvoiceLineBatchRepository salesInvoiceLineBatchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesInvoiceLineBatchMockMvc;

    private SalesInvoiceLineBatch salesInvoiceLineBatch;

    private SalesInvoiceLineBatch insertedSalesInvoiceLineBatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceLineBatch createEntity() {
        return new SalesInvoiceLineBatch()
            .lineId(DEFAULT_LINE_ID)
            .batchLineId(DEFAULT_BATCH_LINE_ID)
            .itemId(DEFAULT_ITEM_ID)
            .code(DEFAULT_CODE)
            .batchId(DEFAULT_BATCH_ID)
            .batchCode(DEFAULT_BATCH_CODE)
            .txDate(DEFAULT_TX_DATE)
            .manufactureDate(DEFAULT_MANUFACTURE_DATE)
            .expiredDate(DEFAULT_EXPIRED_DATE)
            .qty(DEFAULT_QTY)
            .cost(DEFAULT_COST)
            .price(DEFAULT_PRICE)
            .notes(DEFAULT_NOTES)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .nbt(DEFAULT_NBT)
            .vat(DEFAULT_VAT)
            .addedById(DEFAULT_ADDED_BY_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceLineBatch createUpdatedEntity() {
        return new SalesInvoiceLineBatch()
            .lineId(UPDATED_LINE_ID)
            .batchLineId(UPDATED_BATCH_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .code(UPDATED_CODE)
            .batchId(UPDATED_BATCH_ID)
            .batchCode(UPDATED_BATCH_CODE)
            .txDate(UPDATED_TX_DATE)
            .manufactureDate(UPDATED_MANUFACTURE_DATE)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .qty(UPDATED_QTY)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT)
            .addedById(UPDATED_ADDED_BY_ID);
    }

    @BeforeEach
    public void initTest() {
        salesInvoiceLineBatch = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSalesInvoiceLineBatch != null) {
            salesInvoiceLineBatchRepository.delete(insertedSalesInvoiceLineBatch);
            insertedSalesInvoiceLineBatch = null;
        }
    }

    @Test
    @Transactional
    void createSalesInvoiceLineBatch() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SalesInvoiceLineBatch
        var returnedSalesInvoiceLineBatch = om.readValue(
            restSalesInvoiceLineBatchMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLineBatch)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SalesInvoiceLineBatch.class
        );

        // Validate the SalesInvoiceLineBatch in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSalesInvoiceLineBatchUpdatableFieldsEquals(
            returnedSalesInvoiceLineBatch,
            getPersistedSalesInvoiceLineBatch(returnedSalesInvoiceLineBatch)
        );

        insertedSalesInvoiceLineBatch = returnedSalesInvoiceLineBatch;
    }

    @Test
    @Transactional
    void createSalesInvoiceLineBatchWithExistingId() throws Exception {
        // Create the SalesInvoiceLineBatch with an existing ID
        salesInvoiceLineBatch.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesInvoiceLineBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLineBatch)))
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatches() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList
        restSalesInvoiceLineBatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceLineBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].batchLineId").value(hasItem(DEFAULT_BATCH_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].batchCode").value(hasItem(DEFAULT_BATCH_CODE)))
            .andExpect(jsonPath("$.[*].txDate").value(hasItem(DEFAULT_TX_DATE.toString())))
            .andExpect(jsonPath("$.[*].manufactureDate").value(hasItem(DEFAULT_MANUFACTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiredDate").value(hasItem(DEFAULT_EXPIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(sameNumber(DEFAULT_COST))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU.intValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())))
            .andExpect(jsonPath("$.[*].addedById").value(hasItem(DEFAULT_ADDED_BY_ID.intValue())));
    }

    @Test
    @Transactional
    void getSalesInvoiceLineBatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get the salesInvoiceLineBatch
        restSalesInvoiceLineBatchMockMvc
            .perform(get(ENTITY_API_URL_ID, salesInvoiceLineBatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesInvoiceLineBatch.getId().intValue()))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID.intValue()))
            .andExpect(jsonPath("$.batchLineId").value(DEFAULT_BATCH_LINE_ID.intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID.intValue()))
            .andExpect(jsonPath("$.batchCode").value(DEFAULT_BATCH_CODE))
            .andExpect(jsonPath("$.txDate").value(DEFAULT_TX_DATE.toString()))
            .andExpect(jsonPath("$.manufactureDate").value(DEFAULT_MANUFACTURE_DATE.toString()))
            .andExpect(jsonPath("$.expiredDate").value(DEFAULT_EXPIRED_DATE.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.cost").value(sameNumber(DEFAULT_COST)))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU.intValue()))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.nbt").value(DEFAULT_NBT.booleanValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.booleanValue()))
            .andExpect(jsonPath("$.addedById").value(DEFAULT_ADDED_BY_ID.intValue()));
    }

    @Test
    @Transactional
    void getSalesInvoiceLineBatchesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        Long id = salesInvoiceLineBatch.getId();

        defaultSalesInvoiceLineBatchFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSalesInvoiceLineBatchFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSalesInvoiceLineBatchFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lineId equals to
        defaultSalesInvoiceLineBatchFiltering("lineId.equals=" + DEFAULT_LINE_ID, "lineId.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lineId in
        defaultSalesInvoiceLineBatchFiltering("lineId.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID, "lineId.in=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lineId is not null
        defaultSalesInvoiceLineBatchFiltering("lineId.specified=true", "lineId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lineId is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering(
            "lineId.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lineId is less than or equal to
        defaultSalesInvoiceLineBatchFiltering("lineId.lessThanOrEqual=" + DEFAULT_LINE_ID, "lineId.lessThanOrEqual=" + SMALLER_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lineId is less than
        defaultSalesInvoiceLineBatchFiltering("lineId.lessThan=" + UPDATED_LINE_ID, "lineId.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lineId is greater than
        defaultSalesInvoiceLineBatchFiltering("lineId.greaterThan=" + SMALLER_LINE_ID, "lineId.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchLineId equals to
        defaultSalesInvoiceLineBatchFiltering("batchLineId.equals=" + DEFAULT_BATCH_LINE_ID, "batchLineId.equals=" + UPDATED_BATCH_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchLineId in
        defaultSalesInvoiceLineBatchFiltering(
            "batchLineId.in=" + DEFAULT_BATCH_LINE_ID + "," + UPDATED_BATCH_LINE_ID,
            "batchLineId.in=" + UPDATED_BATCH_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchLineId is not null
        defaultSalesInvoiceLineBatchFiltering("batchLineId.specified=true", "batchLineId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchLineId is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering(
            "batchLineId.greaterThanOrEqual=" + DEFAULT_BATCH_LINE_ID,
            "batchLineId.greaterThanOrEqual=" + UPDATED_BATCH_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchLineId is less than or equal to
        defaultSalesInvoiceLineBatchFiltering(
            "batchLineId.lessThanOrEqual=" + DEFAULT_BATCH_LINE_ID,
            "batchLineId.lessThanOrEqual=" + SMALLER_BATCH_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchLineId is less than
        defaultSalesInvoiceLineBatchFiltering(
            "batchLineId.lessThan=" + UPDATED_BATCH_LINE_ID,
            "batchLineId.lessThan=" + DEFAULT_BATCH_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchLineId is greater than
        defaultSalesInvoiceLineBatchFiltering(
            "batchLineId.greaterThan=" + SMALLER_BATCH_LINE_ID,
            "batchLineId.greaterThan=" + DEFAULT_BATCH_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where itemId equals to
        defaultSalesInvoiceLineBatchFiltering("itemId.equals=" + DEFAULT_ITEM_ID, "itemId.equals=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where itemId in
        defaultSalesInvoiceLineBatchFiltering("itemId.in=" + DEFAULT_ITEM_ID + "," + UPDATED_ITEM_ID, "itemId.in=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where itemId is not null
        defaultSalesInvoiceLineBatchFiltering("itemId.specified=true", "itemId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where itemId is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering(
            "itemId.greaterThanOrEqual=" + DEFAULT_ITEM_ID,
            "itemId.greaterThanOrEqual=" + UPDATED_ITEM_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where itemId is less than or equal to
        defaultSalesInvoiceLineBatchFiltering("itemId.lessThanOrEqual=" + DEFAULT_ITEM_ID, "itemId.lessThanOrEqual=" + SMALLER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where itemId is less than
        defaultSalesInvoiceLineBatchFiltering("itemId.lessThan=" + UPDATED_ITEM_ID, "itemId.lessThan=" + DEFAULT_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where itemId is greater than
        defaultSalesInvoiceLineBatchFiltering("itemId.greaterThan=" + SMALLER_ITEM_ID, "itemId.greaterThan=" + DEFAULT_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where code equals to
        defaultSalesInvoiceLineBatchFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where code in
        defaultSalesInvoiceLineBatchFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where code is not null
        defaultSalesInvoiceLineBatchFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where code contains
        defaultSalesInvoiceLineBatchFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where code does not contain
        defaultSalesInvoiceLineBatchFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchId equals to
        defaultSalesInvoiceLineBatchFiltering("batchId.equals=" + DEFAULT_BATCH_ID, "batchId.equals=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchId in
        defaultSalesInvoiceLineBatchFiltering("batchId.in=" + DEFAULT_BATCH_ID + "," + UPDATED_BATCH_ID, "batchId.in=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchId is not null
        defaultSalesInvoiceLineBatchFiltering("batchId.specified=true", "batchId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchId is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering(
            "batchId.greaterThanOrEqual=" + DEFAULT_BATCH_ID,
            "batchId.greaterThanOrEqual=" + UPDATED_BATCH_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchId is less than or equal to
        defaultSalesInvoiceLineBatchFiltering("batchId.lessThanOrEqual=" + DEFAULT_BATCH_ID, "batchId.lessThanOrEqual=" + SMALLER_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchId is less than
        defaultSalesInvoiceLineBatchFiltering("batchId.lessThan=" + UPDATED_BATCH_ID, "batchId.lessThan=" + DEFAULT_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchId is greater than
        defaultSalesInvoiceLineBatchFiltering("batchId.greaterThan=" + SMALLER_BATCH_ID, "batchId.greaterThan=" + DEFAULT_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchCode equals to
        defaultSalesInvoiceLineBatchFiltering("batchCode.equals=" + DEFAULT_BATCH_CODE, "batchCode.equals=" + UPDATED_BATCH_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchCode in
        defaultSalesInvoiceLineBatchFiltering(
            "batchCode.in=" + DEFAULT_BATCH_CODE + "," + UPDATED_BATCH_CODE,
            "batchCode.in=" + UPDATED_BATCH_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchCode is not null
        defaultSalesInvoiceLineBatchFiltering("batchCode.specified=true", "batchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchCode contains
        defaultSalesInvoiceLineBatchFiltering("batchCode.contains=" + DEFAULT_BATCH_CODE, "batchCode.contains=" + UPDATED_BATCH_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByBatchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where batchCode does not contain
        defaultSalesInvoiceLineBatchFiltering(
            "batchCode.doesNotContain=" + UPDATED_BATCH_CODE,
            "batchCode.doesNotContain=" + DEFAULT_BATCH_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByTxDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where txDate equals to
        defaultSalesInvoiceLineBatchFiltering("txDate.equals=" + DEFAULT_TX_DATE, "txDate.equals=" + UPDATED_TX_DATE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByTxDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where txDate in
        defaultSalesInvoiceLineBatchFiltering("txDate.in=" + DEFAULT_TX_DATE + "," + UPDATED_TX_DATE, "txDate.in=" + UPDATED_TX_DATE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByTxDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where txDate is not null
        defaultSalesInvoiceLineBatchFiltering("txDate.specified=true", "txDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByManufactureDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where manufactureDate equals to
        defaultSalesInvoiceLineBatchFiltering(
            "manufactureDate.equals=" + DEFAULT_MANUFACTURE_DATE,
            "manufactureDate.equals=" + UPDATED_MANUFACTURE_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByManufactureDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where manufactureDate in
        defaultSalesInvoiceLineBatchFiltering(
            "manufactureDate.in=" + DEFAULT_MANUFACTURE_DATE + "," + UPDATED_MANUFACTURE_DATE,
            "manufactureDate.in=" + UPDATED_MANUFACTURE_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByManufactureDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where manufactureDate is not null
        defaultSalesInvoiceLineBatchFiltering("manufactureDate.specified=true", "manufactureDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByExpiredDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where expiredDate equals to
        defaultSalesInvoiceLineBatchFiltering("expiredDate.equals=" + DEFAULT_EXPIRED_DATE, "expiredDate.equals=" + UPDATED_EXPIRED_DATE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByExpiredDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where expiredDate in
        defaultSalesInvoiceLineBatchFiltering(
            "expiredDate.in=" + DEFAULT_EXPIRED_DATE + "," + UPDATED_EXPIRED_DATE,
            "expiredDate.in=" + UPDATED_EXPIRED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByExpiredDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where expiredDate is not null
        defaultSalesInvoiceLineBatchFiltering("expiredDate.specified=true", "expiredDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where qty equals to
        defaultSalesInvoiceLineBatchFiltering("qty.equals=" + DEFAULT_QTY, "qty.equals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByQtyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where qty in
        defaultSalesInvoiceLineBatchFiltering("qty.in=" + DEFAULT_QTY + "," + UPDATED_QTY, "qty.in=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where qty is not null
        defaultSalesInvoiceLineBatchFiltering("qty.specified=true", "qty.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where qty is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering("qty.greaterThanOrEqual=" + DEFAULT_QTY, "qty.greaterThanOrEqual=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where qty is less than or equal to
        defaultSalesInvoiceLineBatchFiltering("qty.lessThanOrEqual=" + DEFAULT_QTY, "qty.lessThanOrEqual=" + SMALLER_QTY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where qty is less than
        defaultSalesInvoiceLineBatchFiltering("qty.lessThan=" + UPDATED_QTY, "qty.lessThan=" + DEFAULT_QTY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where qty is greater than
        defaultSalesInvoiceLineBatchFiltering("qty.greaterThan=" + SMALLER_QTY, "qty.greaterThan=" + DEFAULT_QTY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where cost equals to
        defaultSalesInvoiceLineBatchFiltering("cost.equals=" + DEFAULT_COST, "cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where cost in
        defaultSalesInvoiceLineBatchFiltering("cost.in=" + DEFAULT_COST + "," + UPDATED_COST, "cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where cost is not null
        defaultSalesInvoiceLineBatchFiltering("cost.specified=true", "cost.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where cost is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering("cost.greaterThanOrEqual=" + DEFAULT_COST, "cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where cost is less than or equal to
        defaultSalesInvoiceLineBatchFiltering("cost.lessThanOrEqual=" + DEFAULT_COST, "cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where cost is less than
        defaultSalesInvoiceLineBatchFiltering("cost.lessThan=" + UPDATED_COST, "cost.lessThan=" + DEFAULT_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where cost is greater than
        defaultSalesInvoiceLineBatchFiltering("cost.greaterThan=" + SMALLER_COST, "cost.greaterThan=" + DEFAULT_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where price equals to
        defaultSalesInvoiceLineBatchFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where price in
        defaultSalesInvoiceLineBatchFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where price is not null
        defaultSalesInvoiceLineBatchFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where price is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where price is less than or equal to
        defaultSalesInvoiceLineBatchFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where price is less than
        defaultSalesInvoiceLineBatchFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where price is greater than
        defaultSalesInvoiceLineBatchFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where notes equals to
        defaultSalesInvoiceLineBatchFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where notes in
        defaultSalesInvoiceLineBatchFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where notes is not null
        defaultSalesInvoiceLineBatchFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where notes contains
        defaultSalesInvoiceLineBatchFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where notes does not contain
        defaultSalesInvoiceLineBatchFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmu equals to
        defaultSalesInvoiceLineBatchFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmu in
        defaultSalesInvoiceLineBatchFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmu is not null
        defaultSalesInvoiceLineBatchFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmu is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmu is less than or equal to
        defaultSalesInvoiceLineBatchFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmu is less than
        defaultSalesInvoiceLineBatchFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmu is greater than
        defaultSalesInvoiceLineBatchFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmd equals to
        defaultSalesInvoiceLineBatchFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmd in
        defaultSalesInvoiceLineBatchFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where lmd is not null
        defaultSalesInvoiceLineBatchFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNbtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where nbt equals to
        defaultSalesInvoiceLineBatchFiltering("nbt.equals=" + DEFAULT_NBT, "nbt.equals=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNbtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where nbt in
        defaultSalesInvoiceLineBatchFiltering("nbt.in=" + DEFAULT_NBT + "," + UPDATED_NBT, "nbt.in=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByNbtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where nbt is not null
        defaultSalesInvoiceLineBatchFiltering("nbt.specified=true", "nbt.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByVatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where vat equals to
        defaultSalesInvoiceLineBatchFiltering("vat.equals=" + DEFAULT_VAT, "vat.equals=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByVatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where vat in
        defaultSalesInvoiceLineBatchFiltering("vat.in=" + DEFAULT_VAT + "," + UPDATED_VAT, "vat.in=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByVatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where vat is not null
        defaultSalesInvoiceLineBatchFiltering("vat.specified=true", "vat.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByAddedByIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where addedById equals to
        defaultSalesInvoiceLineBatchFiltering("addedById.equals=" + DEFAULT_ADDED_BY_ID, "addedById.equals=" + UPDATED_ADDED_BY_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByAddedByIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where addedById in
        defaultSalesInvoiceLineBatchFiltering(
            "addedById.in=" + DEFAULT_ADDED_BY_ID + "," + UPDATED_ADDED_BY_ID,
            "addedById.in=" + UPDATED_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByAddedByIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where addedById is not null
        defaultSalesInvoiceLineBatchFiltering("addedById.specified=true", "addedById.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByAddedByIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where addedById is greater than or equal to
        defaultSalesInvoiceLineBatchFiltering(
            "addedById.greaterThanOrEqual=" + DEFAULT_ADDED_BY_ID,
            "addedById.greaterThanOrEqual=" + UPDATED_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByAddedByIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where addedById is less than or equal to
        defaultSalesInvoiceLineBatchFiltering(
            "addedById.lessThanOrEqual=" + DEFAULT_ADDED_BY_ID,
            "addedById.lessThanOrEqual=" + SMALLER_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByAddedByIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where addedById is less than
        defaultSalesInvoiceLineBatchFiltering("addedById.lessThan=" + UPDATED_ADDED_BY_ID, "addedById.lessThan=" + DEFAULT_ADDED_BY_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLineBatchesByAddedByIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        // Get all the salesInvoiceLineBatchList where addedById is greater than
        defaultSalesInvoiceLineBatchFiltering(
            "addedById.greaterThan=" + SMALLER_ADDED_BY_ID,
            "addedById.greaterThan=" + DEFAULT_ADDED_BY_ID
        );
    }

    private void defaultSalesInvoiceLineBatchFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSalesInvoiceLineBatchShouldBeFound(shouldBeFound);
        defaultSalesInvoiceLineBatchShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesInvoiceLineBatchShouldBeFound(String filter) throws Exception {
        restSalesInvoiceLineBatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceLineBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].batchLineId").value(hasItem(DEFAULT_BATCH_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].batchCode").value(hasItem(DEFAULT_BATCH_CODE)))
            .andExpect(jsonPath("$.[*].txDate").value(hasItem(DEFAULT_TX_DATE.toString())))
            .andExpect(jsonPath("$.[*].manufactureDate").value(hasItem(DEFAULT_MANUFACTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiredDate").value(hasItem(DEFAULT_EXPIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(sameNumber(DEFAULT_COST))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU.intValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())))
            .andExpect(jsonPath("$.[*].addedById").value(hasItem(DEFAULT_ADDED_BY_ID.intValue())));

        // Check, that the count call also returns 1
        restSalesInvoiceLineBatchMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesInvoiceLineBatchShouldNotBeFound(String filter) throws Exception {
        restSalesInvoiceLineBatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesInvoiceLineBatchMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesInvoiceLineBatch() throws Exception {
        // Get the salesInvoiceLineBatch
        restSalesInvoiceLineBatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesInvoiceLineBatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLineBatch
        SalesInvoiceLineBatch updatedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository
            .findById(salesInvoiceLineBatch.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSalesInvoiceLineBatch are not directly saved in db
        em.detach(updatedSalesInvoiceLineBatch);
        updatedSalesInvoiceLineBatch
            .lineId(UPDATED_LINE_ID)
            .batchLineId(UPDATED_BATCH_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .code(UPDATED_CODE)
            .batchId(UPDATED_BATCH_ID)
            .batchCode(UPDATED_BATCH_CODE)
            .txDate(UPDATED_TX_DATE)
            .manufactureDate(UPDATED_MANUFACTURE_DATE)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .qty(UPDATED_QTY)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT)
            .addedById(UPDATED_ADDED_BY_ID);

        restSalesInvoiceLineBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesInvoiceLineBatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSalesInvoiceLineBatch))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalesInvoiceLineBatchToMatchAllProperties(updatedSalesInvoiceLineBatch);
    }

    @Test
    @Transactional
    void putNonExistingSalesInvoiceLineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLineBatch.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceLineBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesInvoiceLineBatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceLineBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesInvoiceLineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLineBatch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLineBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceLineBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesInvoiceLineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLineBatch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLineBatchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLineBatch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesInvoiceLineBatchWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLineBatch using partial update
        SalesInvoiceLineBatch partialUpdatedSalesInvoiceLineBatch = new SalesInvoiceLineBatch();
        partialUpdatedSalesInvoiceLineBatch.setId(salesInvoiceLineBatch.getId());

        partialUpdatedSalesInvoiceLineBatch
            .lineId(UPDATED_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .batchId(UPDATED_BATCH_ID)
            .batchCode(UPDATED_BATCH_CODE)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .cost(UPDATED_COST)
            .lmu(UPDATED_LMU)
            .vat(UPDATED_VAT);

        restSalesInvoiceLineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceLineBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceLineBatch))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLineBatch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceLineBatchUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSalesInvoiceLineBatch, salesInvoiceLineBatch),
            getPersistedSalesInvoiceLineBatch(salesInvoiceLineBatch)
        );
    }

    @Test
    @Transactional
    void fullUpdateSalesInvoiceLineBatchWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLineBatch using partial update
        SalesInvoiceLineBatch partialUpdatedSalesInvoiceLineBatch = new SalesInvoiceLineBatch();
        partialUpdatedSalesInvoiceLineBatch.setId(salesInvoiceLineBatch.getId());

        partialUpdatedSalesInvoiceLineBatch
            .lineId(UPDATED_LINE_ID)
            .batchLineId(UPDATED_BATCH_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .code(UPDATED_CODE)
            .batchId(UPDATED_BATCH_ID)
            .batchCode(UPDATED_BATCH_CODE)
            .txDate(UPDATED_TX_DATE)
            .manufactureDate(UPDATED_MANUFACTURE_DATE)
            .expiredDate(UPDATED_EXPIRED_DATE)
            .qty(UPDATED_QTY)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT)
            .addedById(UPDATED_ADDED_BY_ID);

        restSalesInvoiceLineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceLineBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceLineBatch))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLineBatch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceLineBatchUpdatableFieldsEquals(
            partialUpdatedSalesInvoiceLineBatch,
            getPersistedSalesInvoiceLineBatch(partialUpdatedSalesInvoiceLineBatch)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSalesInvoiceLineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLineBatch.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceLineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesInvoiceLineBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceLineBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesInvoiceLineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLineBatch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceLineBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesInvoiceLineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLineBatch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLineBatchMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(salesInvoiceLineBatch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceLineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesInvoiceLineBatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLineBatch = salesInvoiceLineBatchRepository.saveAndFlush(salesInvoiceLineBatch);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salesInvoiceLineBatch
        restSalesInvoiceLineBatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesInvoiceLineBatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salesInvoiceLineBatchRepository.count();
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

    protected SalesInvoiceLineBatch getPersistedSalesInvoiceLineBatch(SalesInvoiceLineBatch salesInvoiceLineBatch) {
        return salesInvoiceLineBatchRepository.findById(salesInvoiceLineBatch.getId()).orElseThrow();
    }

    protected void assertPersistedSalesInvoiceLineBatchToMatchAllProperties(SalesInvoiceLineBatch expectedSalesInvoiceLineBatch) {
        assertSalesInvoiceLineBatchAllPropertiesEquals(
            expectedSalesInvoiceLineBatch,
            getPersistedSalesInvoiceLineBatch(expectedSalesInvoiceLineBatch)
        );
    }

    protected void assertPersistedSalesInvoiceLineBatchToMatchUpdatableProperties(SalesInvoiceLineBatch expectedSalesInvoiceLineBatch) {
        assertSalesInvoiceLineBatchAllUpdatablePropertiesEquals(
            expectedSalesInvoiceLineBatch,
            getPersistedSalesInvoiceLineBatch(expectedSalesInvoiceLineBatch)
        );
    }
}
