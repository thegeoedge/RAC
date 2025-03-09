package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.InventorybatchesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Inventorybatches;
import com.heavenscode.rac.repository.InventorybatchesRepository;
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
 * Integration tests for the {@link InventorybatchesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventorybatchesResourceIT {

    private static final Integer DEFAULT_ITEMID = 1;
    private static final Integer UPDATED_ITEMID = 2;
    private static final Integer SMALLER_ITEMID = 1 - 1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TXDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TXDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_COST = 1F;
    private static final Float UPDATED_COST = 2F;
    private static final Float SMALLER_COST = 1F - 1F;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final Float DEFAULT_COSTWITHOUTVAT = 1F;
    private static final Float UPDATED_COSTWITHOUTVAT = 2F;
    private static final Float SMALLER_COSTWITHOUTVAT = 1F - 1F;

    private static final Float DEFAULT_PRICEWITHOUTVAT = 1F;
    private static final Float UPDATED_PRICEWITHOUTVAT = 2F;
    private static final Float SMALLER_PRICEWITHOUTVAT = 1F - 1F;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;
    private static final Integer SMALLER_LINEID = 1 - 1;

    private static final Instant DEFAULT_MANUFACTUREDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MANUFACTUREDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIREDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIREDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;
    private static final Float SMALLER_QUANTITY = 1F - 1F;

    private static final Instant DEFAULT_ADDEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADDEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_COSTTOTAL = 1F;
    private static final Float UPDATED_COSTTOTAL = 2F;
    private static final Float SMALLER_COSTTOTAL = 1F - 1F;

    private static final Float DEFAULT_RETURNPRICE = 1F;
    private static final Float UPDATED_RETURNPRICE = 2F;
    private static final Float SMALLER_RETURNPRICE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/inventorybatches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InventorybatchesRepository inventorybatchesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventorybatchesMockMvc;

    private Inventorybatches inventorybatches;

    private Inventorybatches insertedInventorybatches;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventorybatches createEntity() {
        return new Inventorybatches()
            .itemid(DEFAULT_ITEMID)
            .code(DEFAULT_CODE)
            .txdate(DEFAULT_TXDATE)
            .cost(DEFAULT_COST)
            .price(DEFAULT_PRICE)
            .costwithoutvat(DEFAULT_COSTWITHOUTVAT)
            .pricewithoutvat(DEFAULT_PRICEWITHOUTVAT)
            .notes(DEFAULT_NOTES)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .lineid(DEFAULT_LINEID)
            .manufacturedate(DEFAULT_MANUFACTUREDATE)
            .expiredate(DEFAULT_EXPIREDATE)
            .quantity(DEFAULT_QUANTITY)
            .addeddate(DEFAULT_ADDEDDATE)
            .costtotal(DEFAULT_COSTTOTAL)
            .returnprice(DEFAULT_RETURNPRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventorybatches createUpdatedEntity() {
        return new Inventorybatches()
            .itemid(UPDATED_ITEMID)
            .code(UPDATED_CODE)
            .txdate(UPDATED_TXDATE)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .costwithoutvat(UPDATED_COSTWITHOUTVAT)
            .pricewithoutvat(UPDATED_PRICEWITHOUTVAT)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .lineid(UPDATED_LINEID)
            .manufacturedate(UPDATED_MANUFACTUREDATE)
            .expiredate(UPDATED_EXPIREDATE)
            .quantity(UPDATED_QUANTITY)
            .addeddate(UPDATED_ADDEDDATE)
            .costtotal(UPDATED_COSTTOTAL)
            .returnprice(UPDATED_RETURNPRICE);
    }

    @BeforeEach
    public void initTest() {
        inventorybatches = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedInventorybatches != null) {
            inventorybatchesRepository.delete(insertedInventorybatches);
            insertedInventorybatches = null;
        }
    }

    @Test
    @Transactional
    void createInventorybatches() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inventorybatches
        var returnedInventorybatches = om.readValue(
            restInventorybatchesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventorybatches)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inventorybatches.class
        );

        // Validate the Inventorybatches in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInventorybatchesUpdatableFieldsEquals(returnedInventorybatches, getPersistedInventorybatches(returnedInventorybatches));

        insertedInventorybatches = returnedInventorybatches;
    }

    @Test
    @Transactional
    void createInventorybatchesWithExistingId() throws Exception {
        // Create the Inventorybatches with an existing ID
        inventorybatches.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventorybatchesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventorybatches)))
            .andExpect(status().isBadRequest());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInventorybatches() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList
        restInventorybatchesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventorybatches.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemid").value(hasItem(DEFAULT_ITEMID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].txdate").value(hasItem(DEFAULT_TXDATE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].costwithoutvat").value(hasItem(DEFAULT_COSTWITHOUTVAT.doubleValue())))
            .andExpect(jsonPath("$.[*].pricewithoutvat").value(hasItem(DEFAULT_PRICEWITHOUTVAT.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].manufacturedate").value(hasItem(DEFAULT_MANUFACTUREDATE.toString())))
            .andExpect(jsonPath("$.[*].expiredate").value(hasItem(DEFAULT_EXPIREDATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].addeddate").value(hasItem(DEFAULT_ADDEDDATE.toString())))
            .andExpect(jsonPath("$.[*].costtotal").value(hasItem(DEFAULT_COSTTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].returnprice").value(hasItem(DEFAULT_RETURNPRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getInventorybatches() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get the inventorybatches
        restInventorybatchesMockMvc
            .perform(get(ENTITY_API_URL_ID, inventorybatches.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventorybatches.getId().intValue()))
            .andExpect(jsonPath("$.itemid").value(DEFAULT_ITEMID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.txdate").value(DEFAULT_TXDATE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.costwithoutvat").value(DEFAULT_COSTWITHOUTVAT.doubleValue()))
            .andExpect(jsonPath("$.pricewithoutvat").value(DEFAULT_PRICEWITHOUTVAT.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.manufacturedate").value(DEFAULT_MANUFACTUREDATE.toString()))
            .andExpect(jsonPath("$.expiredate").value(DEFAULT_EXPIREDATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.addeddate").value(DEFAULT_ADDEDDATE.toString()))
            .andExpect(jsonPath("$.costtotal").value(DEFAULT_COSTTOTAL.doubleValue()))
            .andExpect(jsonPath("$.returnprice").value(DEFAULT_RETURNPRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getInventorybatchesByIdFiltering() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        Long id = inventorybatches.getId();

        defaultInventorybatchesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultInventorybatchesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultInventorybatchesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByItemidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where itemid equals to
        defaultInventorybatchesFiltering("itemid.equals=" + DEFAULT_ITEMID, "itemid.equals=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByItemidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where itemid in
        defaultInventorybatchesFiltering("itemid.in=" + DEFAULT_ITEMID + "," + UPDATED_ITEMID, "itemid.in=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByItemidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where itemid is not null
        defaultInventorybatchesFiltering("itemid.specified=true", "itemid.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByItemidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where itemid is greater than or equal to
        defaultInventorybatchesFiltering("itemid.greaterThanOrEqual=" + DEFAULT_ITEMID, "itemid.greaterThanOrEqual=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByItemidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where itemid is less than or equal to
        defaultInventorybatchesFiltering("itemid.lessThanOrEqual=" + DEFAULT_ITEMID, "itemid.lessThanOrEqual=" + SMALLER_ITEMID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByItemidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where itemid is less than
        defaultInventorybatchesFiltering("itemid.lessThan=" + UPDATED_ITEMID, "itemid.lessThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByItemidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where itemid is greater than
        defaultInventorybatchesFiltering("itemid.greaterThan=" + SMALLER_ITEMID, "itemid.greaterThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where code equals to
        defaultInventorybatchesFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where code in
        defaultInventorybatchesFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where code is not null
        defaultInventorybatchesFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where code contains
        defaultInventorybatchesFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where code does not contain
        defaultInventorybatchesFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByTxdateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where txdate equals to
        defaultInventorybatchesFiltering("txdate.equals=" + DEFAULT_TXDATE, "txdate.equals=" + UPDATED_TXDATE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByTxdateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where txdate in
        defaultInventorybatchesFiltering("txdate.in=" + DEFAULT_TXDATE + "," + UPDATED_TXDATE, "txdate.in=" + UPDATED_TXDATE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByTxdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where txdate is not null
        defaultInventorybatchesFiltering("txdate.specified=true", "txdate.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where cost equals to
        defaultInventorybatchesFiltering("cost.equals=" + DEFAULT_COST, "cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where cost in
        defaultInventorybatchesFiltering("cost.in=" + DEFAULT_COST + "," + UPDATED_COST, "cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where cost is not null
        defaultInventorybatchesFiltering("cost.specified=true", "cost.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where cost is greater than or equal to
        defaultInventorybatchesFiltering("cost.greaterThanOrEqual=" + DEFAULT_COST, "cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where cost is less than or equal to
        defaultInventorybatchesFiltering("cost.lessThanOrEqual=" + DEFAULT_COST, "cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where cost is less than
        defaultInventorybatchesFiltering("cost.lessThan=" + UPDATED_COST, "cost.lessThan=" + DEFAULT_COST);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where cost is greater than
        defaultInventorybatchesFiltering("cost.greaterThan=" + SMALLER_COST, "cost.greaterThan=" + DEFAULT_COST);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where price equals to
        defaultInventorybatchesFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where price in
        defaultInventorybatchesFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where price is not null
        defaultInventorybatchesFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where price is greater than or equal to
        defaultInventorybatchesFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where price is less than or equal to
        defaultInventorybatchesFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where price is less than
        defaultInventorybatchesFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where price is greater than
        defaultInventorybatchesFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostwithoutvatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costwithoutvat equals to
        defaultInventorybatchesFiltering(
            "costwithoutvat.equals=" + DEFAULT_COSTWITHOUTVAT,
            "costwithoutvat.equals=" + UPDATED_COSTWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostwithoutvatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costwithoutvat in
        defaultInventorybatchesFiltering(
            "costwithoutvat.in=" + DEFAULT_COSTWITHOUTVAT + "," + UPDATED_COSTWITHOUTVAT,
            "costwithoutvat.in=" + UPDATED_COSTWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostwithoutvatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costwithoutvat is not null
        defaultInventorybatchesFiltering("costwithoutvat.specified=true", "costwithoutvat.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostwithoutvatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costwithoutvat is greater than or equal to
        defaultInventorybatchesFiltering(
            "costwithoutvat.greaterThanOrEqual=" + DEFAULT_COSTWITHOUTVAT,
            "costwithoutvat.greaterThanOrEqual=" + UPDATED_COSTWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostwithoutvatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costwithoutvat is less than or equal to
        defaultInventorybatchesFiltering(
            "costwithoutvat.lessThanOrEqual=" + DEFAULT_COSTWITHOUTVAT,
            "costwithoutvat.lessThanOrEqual=" + SMALLER_COSTWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostwithoutvatIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costwithoutvat is less than
        defaultInventorybatchesFiltering(
            "costwithoutvat.lessThan=" + UPDATED_COSTWITHOUTVAT,
            "costwithoutvat.lessThan=" + DEFAULT_COSTWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCostwithoutvatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costwithoutvat is greater than
        defaultInventorybatchesFiltering(
            "costwithoutvat.greaterThan=" + SMALLER_COSTWITHOUTVAT,
            "costwithoutvat.greaterThan=" + DEFAULT_COSTWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPricewithoutvatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where pricewithoutvat equals to
        defaultInventorybatchesFiltering(
            "pricewithoutvat.equals=" + DEFAULT_PRICEWITHOUTVAT,
            "pricewithoutvat.equals=" + UPDATED_PRICEWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPricewithoutvatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where pricewithoutvat in
        defaultInventorybatchesFiltering(
            "pricewithoutvat.in=" + DEFAULT_PRICEWITHOUTVAT + "," + UPDATED_PRICEWITHOUTVAT,
            "pricewithoutvat.in=" + UPDATED_PRICEWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPricewithoutvatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where pricewithoutvat is not null
        defaultInventorybatchesFiltering("pricewithoutvat.specified=true", "pricewithoutvat.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPricewithoutvatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where pricewithoutvat is greater than or equal to
        defaultInventorybatchesFiltering(
            "pricewithoutvat.greaterThanOrEqual=" + DEFAULT_PRICEWITHOUTVAT,
            "pricewithoutvat.greaterThanOrEqual=" + UPDATED_PRICEWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPricewithoutvatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where pricewithoutvat is less than or equal to
        defaultInventorybatchesFiltering(
            "pricewithoutvat.lessThanOrEqual=" + DEFAULT_PRICEWITHOUTVAT,
            "pricewithoutvat.lessThanOrEqual=" + SMALLER_PRICEWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPricewithoutvatIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where pricewithoutvat is less than
        defaultInventorybatchesFiltering(
            "pricewithoutvat.lessThan=" + UPDATED_PRICEWITHOUTVAT,
            "pricewithoutvat.lessThan=" + DEFAULT_PRICEWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByPricewithoutvatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where pricewithoutvat is greater than
        defaultInventorybatchesFiltering(
            "pricewithoutvat.greaterThan=" + SMALLER_PRICEWITHOUTVAT,
            "pricewithoutvat.greaterThan=" + DEFAULT_PRICEWITHOUTVAT
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where notes equals to
        defaultInventorybatchesFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where notes in
        defaultInventorybatchesFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where notes is not null
        defaultInventorybatchesFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where notes contains
        defaultInventorybatchesFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where notes does not contain
        defaultInventorybatchesFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmu equals to
        defaultInventorybatchesFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmu in
        defaultInventorybatchesFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmu is not null
        defaultInventorybatchesFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmu is greater than or equal to
        defaultInventorybatchesFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmu is less than or equal to
        defaultInventorybatchesFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmu is less than
        defaultInventorybatchesFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmu is greater than
        defaultInventorybatchesFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmd equals to
        defaultInventorybatchesFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmd in
        defaultInventorybatchesFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lmd is not null
        defaultInventorybatchesFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lineid equals to
        defaultInventorybatchesFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lineid in
        defaultInventorybatchesFiltering("lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID, "lineid.in=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lineid is not null
        defaultInventorybatchesFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lineid is greater than or equal to
        defaultInventorybatchesFiltering("lineid.greaterThanOrEqual=" + DEFAULT_LINEID, "lineid.greaterThanOrEqual=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lineid is less than or equal to
        defaultInventorybatchesFiltering("lineid.lessThanOrEqual=" + DEFAULT_LINEID, "lineid.lessThanOrEqual=" + SMALLER_LINEID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lineid is less than
        defaultInventorybatchesFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where lineid is greater than
        defaultInventorybatchesFiltering("lineid.greaterThan=" + SMALLER_LINEID, "lineid.greaterThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByManufacturedateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where manufacturedate equals to
        defaultInventorybatchesFiltering(
            "manufacturedate.equals=" + DEFAULT_MANUFACTUREDATE,
            "manufacturedate.equals=" + UPDATED_MANUFACTUREDATE
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByManufacturedateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where manufacturedate in
        defaultInventorybatchesFiltering(
            "manufacturedate.in=" + DEFAULT_MANUFACTUREDATE + "," + UPDATED_MANUFACTUREDATE,
            "manufacturedate.in=" + UPDATED_MANUFACTUREDATE
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByManufacturedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where manufacturedate is not null
        defaultInventorybatchesFiltering("manufacturedate.specified=true", "manufacturedate.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByExpiredateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where expiredate equals to
        defaultInventorybatchesFiltering("expiredate.equals=" + DEFAULT_EXPIREDATE, "expiredate.equals=" + UPDATED_EXPIREDATE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByExpiredateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where expiredate in
        defaultInventorybatchesFiltering(
            "expiredate.in=" + DEFAULT_EXPIREDATE + "," + UPDATED_EXPIREDATE,
            "expiredate.in=" + UPDATED_EXPIREDATE
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByExpiredateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where expiredate is not null
        defaultInventorybatchesFiltering("expiredate.specified=true", "expiredate.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where quantity equals to
        defaultInventorybatchesFiltering("quantity.equals=" + DEFAULT_QUANTITY, "quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where quantity in
        defaultInventorybatchesFiltering("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY, "quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where quantity is not null
        defaultInventorybatchesFiltering("quantity.specified=true", "quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where quantity is greater than or equal to
        defaultInventorybatchesFiltering(
            "quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.greaterThanOrEqual=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where quantity is less than or equal to
        defaultInventorybatchesFiltering("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY, "quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where quantity is less than
        defaultInventorybatchesFiltering("quantity.lessThan=" + UPDATED_QUANTITY, "quantity.lessThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where quantity is greater than
        defaultInventorybatchesFiltering("quantity.greaterThan=" + SMALLER_QUANTITY, "quantity.greaterThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByAddeddateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where addeddate equals to
        defaultInventorybatchesFiltering("addeddate.equals=" + DEFAULT_ADDEDDATE, "addeddate.equals=" + UPDATED_ADDEDDATE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByAddeddateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where addeddate in
        defaultInventorybatchesFiltering(
            "addeddate.in=" + DEFAULT_ADDEDDATE + "," + UPDATED_ADDEDDATE,
            "addeddate.in=" + UPDATED_ADDEDDATE
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByAddeddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where addeddate is not null
        defaultInventorybatchesFiltering("addeddate.specified=true", "addeddate.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCosttotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costtotal equals to
        defaultInventorybatchesFiltering("costtotal.equals=" + DEFAULT_COSTTOTAL, "costtotal.equals=" + UPDATED_COSTTOTAL);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCosttotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costtotal in
        defaultInventorybatchesFiltering(
            "costtotal.in=" + DEFAULT_COSTTOTAL + "," + UPDATED_COSTTOTAL,
            "costtotal.in=" + UPDATED_COSTTOTAL
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCosttotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costtotal is not null
        defaultInventorybatchesFiltering("costtotal.specified=true", "costtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCosttotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costtotal is greater than or equal to
        defaultInventorybatchesFiltering(
            "costtotal.greaterThanOrEqual=" + DEFAULT_COSTTOTAL,
            "costtotal.greaterThanOrEqual=" + UPDATED_COSTTOTAL
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCosttotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costtotal is less than or equal to
        defaultInventorybatchesFiltering(
            "costtotal.lessThanOrEqual=" + DEFAULT_COSTTOTAL,
            "costtotal.lessThanOrEqual=" + SMALLER_COSTTOTAL
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCosttotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costtotal is less than
        defaultInventorybatchesFiltering("costtotal.lessThan=" + UPDATED_COSTTOTAL, "costtotal.lessThan=" + DEFAULT_COSTTOTAL);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByCosttotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where costtotal is greater than
        defaultInventorybatchesFiltering("costtotal.greaterThan=" + SMALLER_COSTTOTAL, "costtotal.greaterThan=" + DEFAULT_COSTTOTAL);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByReturnpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where returnprice equals to
        defaultInventorybatchesFiltering("returnprice.equals=" + DEFAULT_RETURNPRICE, "returnprice.equals=" + UPDATED_RETURNPRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByReturnpriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where returnprice in
        defaultInventorybatchesFiltering(
            "returnprice.in=" + DEFAULT_RETURNPRICE + "," + UPDATED_RETURNPRICE,
            "returnprice.in=" + UPDATED_RETURNPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByReturnpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where returnprice is not null
        defaultInventorybatchesFiltering("returnprice.specified=true", "returnprice.specified=false");
    }

    @Test
    @Transactional
    void getAllInventorybatchesByReturnpriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where returnprice is greater than or equal to
        defaultInventorybatchesFiltering(
            "returnprice.greaterThanOrEqual=" + DEFAULT_RETURNPRICE,
            "returnprice.greaterThanOrEqual=" + UPDATED_RETURNPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByReturnpriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where returnprice is less than or equal to
        defaultInventorybatchesFiltering(
            "returnprice.lessThanOrEqual=" + DEFAULT_RETURNPRICE,
            "returnprice.lessThanOrEqual=" + SMALLER_RETURNPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventorybatchesByReturnpriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where returnprice is less than
        defaultInventorybatchesFiltering("returnprice.lessThan=" + UPDATED_RETURNPRICE, "returnprice.lessThan=" + DEFAULT_RETURNPRICE);
    }

    @Test
    @Transactional
    void getAllInventorybatchesByReturnpriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        // Get all the inventorybatchesList where returnprice is greater than
        defaultInventorybatchesFiltering(
            "returnprice.greaterThan=" + SMALLER_RETURNPRICE,
            "returnprice.greaterThan=" + DEFAULT_RETURNPRICE
        );
    }

    private void defaultInventorybatchesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInventorybatchesShouldBeFound(shouldBeFound);
        defaultInventorybatchesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventorybatchesShouldBeFound(String filter) throws Exception {
        restInventorybatchesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventorybatches.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemid").value(hasItem(DEFAULT_ITEMID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].txdate").value(hasItem(DEFAULT_TXDATE.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].costwithoutvat").value(hasItem(DEFAULT_COSTWITHOUTVAT.doubleValue())))
            .andExpect(jsonPath("$.[*].pricewithoutvat").value(hasItem(DEFAULT_PRICEWITHOUTVAT.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].manufacturedate").value(hasItem(DEFAULT_MANUFACTUREDATE.toString())))
            .andExpect(jsonPath("$.[*].expiredate").value(hasItem(DEFAULT_EXPIREDATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].addeddate").value(hasItem(DEFAULT_ADDEDDATE.toString())))
            .andExpect(jsonPath("$.[*].costtotal").value(hasItem(DEFAULT_COSTTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].returnprice").value(hasItem(DEFAULT_RETURNPRICE.doubleValue())));

        // Check, that the count call also returns 1
        restInventorybatchesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventorybatchesShouldNotBeFound(String filter) throws Exception {
        restInventorybatchesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventorybatchesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventorybatches() throws Exception {
        // Get the inventorybatches
        restInventorybatchesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInventorybatches() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventorybatches
        Inventorybatches updatedInventorybatches = inventorybatchesRepository.findById(inventorybatches.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInventorybatches are not directly saved in db
        em.detach(updatedInventorybatches);
        updatedInventorybatches
            .itemid(UPDATED_ITEMID)
            .code(UPDATED_CODE)
            .txdate(UPDATED_TXDATE)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .costwithoutvat(UPDATED_COSTWITHOUTVAT)
            .pricewithoutvat(UPDATED_PRICEWITHOUTVAT)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .lineid(UPDATED_LINEID)
            .manufacturedate(UPDATED_MANUFACTUREDATE)
            .expiredate(UPDATED_EXPIREDATE)
            .quantity(UPDATED_QUANTITY)
            .addeddate(UPDATED_ADDEDDATE)
            .costtotal(UPDATED_COSTTOTAL)
            .returnprice(UPDATED_RETURNPRICE);

        restInventorybatchesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInventorybatches.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInventorybatches))
            )
            .andExpect(status().isOk());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInventorybatchesToMatchAllProperties(updatedInventorybatches);
    }

    @Test
    @Transactional
    void putNonExistingInventorybatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventorybatches.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventorybatchesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventorybatches.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventorybatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventorybatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventorybatches.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventorybatchesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventorybatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventorybatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventorybatches.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventorybatchesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventorybatches)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventorybatchesWithPatch() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventorybatches using partial update
        Inventorybatches partialUpdatedInventorybatches = new Inventorybatches();
        partialUpdatedInventorybatches.setId(inventorybatches.getId());

        partialUpdatedInventorybatches
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .pricewithoutvat(UPDATED_PRICEWITHOUTVAT)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .lineid(UPDATED_LINEID)
            .costtotal(UPDATED_COSTTOTAL);

        restInventorybatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventorybatches.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventorybatches))
            )
            .andExpect(status().isOk());

        // Validate the Inventorybatches in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventorybatchesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInventorybatches, inventorybatches),
            getPersistedInventorybatches(inventorybatches)
        );
    }

    @Test
    @Transactional
    void fullUpdateInventorybatchesWithPatch() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventorybatches using partial update
        Inventorybatches partialUpdatedInventorybatches = new Inventorybatches();
        partialUpdatedInventorybatches.setId(inventorybatches.getId());

        partialUpdatedInventorybatches
            .itemid(UPDATED_ITEMID)
            .code(UPDATED_CODE)
            .txdate(UPDATED_TXDATE)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .costwithoutvat(UPDATED_COSTWITHOUTVAT)
            .pricewithoutvat(UPDATED_PRICEWITHOUTVAT)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .lineid(UPDATED_LINEID)
            .manufacturedate(UPDATED_MANUFACTUREDATE)
            .expiredate(UPDATED_EXPIREDATE)
            .quantity(UPDATED_QUANTITY)
            .addeddate(UPDATED_ADDEDDATE)
            .costtotal(UPDATED_COSTTOTAL)
            .returnprice(UPDATED_RETURNPRICE);

        restInventorybatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventorybatches.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventorybatches))
            )
            .andExpect(status().isOk());

        // Validate the Inventorybatches in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventorybatchesUpdatableFieldsEquals(
            partialUpdatedInventorybatches,
            getPersistedInventorybatches(partialUpdatedInventorybatches)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInventorybatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventorybatches.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventorybatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventorybatches.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventorybatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventorybatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventorybatches.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventorybatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventorybatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventorybatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventorybatches.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventorybatchesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inventorybatches)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventorybatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventorybatches() throws Exception {
        // Initialize the database
        insertedInventorybatches = inventorybatchesRepository.saveAndFlush(inventorybatches);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inventorybatches
        restInventorybatchesMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventorybatches.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inventorybatchesRepository.count();
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

    protected Inventorybatches getPersistedInventorybatches(Inventorybatches inventorybatches) {
        return inventorybatchesRepository.findById(inventorybatches.getId()).orElseThrow();
    }

    protected void assertPersistedInventorybatchesToMatchAllProperties(Inventorybatches expectedInventorybatches) {
        assertInventorybatchesAllPropertiesEquals(expectedInventorybatches, getPersistedInventorybatches(expectedInventorybatches));
    }

    protected void assertPersistedInventorybatchesToMatchUpdatableProperties(Inventorybatches expectedInventorybatches) {
        assertInventorybatchesAllUpdatablePropertiesEquals(
            expectedInventorybatches,
            getPersistedInventorybatches(expectedInventorybatches)
        );
    }
}
