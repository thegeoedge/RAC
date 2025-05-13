package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.BinCardAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.BinCard;
import com.heavenscode.rac.repository.BinCardRepository;
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
 * Integration tests for the {@link BinCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BinCardResourceIT {

    private static final Long DEFAULT_ITEM_ID = 1L;
    private static final Long UPDATED_ITEM_ID = 2L;
    private static final Long SMALLER_ITEM_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TX_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TX_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY_IN = 1D;
    private static final Double UPDATED_QTY_IN = 2D;
    private static final Double SMALLER_QTY_IN = 1D - 1D;

    private static final Double DEFAULT_QTY_OUT = 1D;
    private static final Double UPDATED_QTY_OUT = 2D;
    private static final Double SMALLER_QTY_OUT = 1D - 1D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final Integer DEFAULT_L_MU = 1;
    private static final Integer UPDATED_L_MU = 2;
    private static final Integer SMALLER_L_MU = 1 - 1;

    private static final Instant DEFAULT_L_MD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_L_MD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REFERENCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECORD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_BATCH_ID = 1L;
    private static final Long UPDATED_BATCH_ID = 2L;
    private static final Long SMALLER_BATCH_ID = 1L - 1L;

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;
    private static final Long SMALLER_LOCATION_ID = 1L - 1L;

    private static final String DEFAULT_LOCATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_OPENING = 1D;
    private static final Double UPDATED_OPENING = 2D;
    private static final Double SMALLER_OPENING = 1D - 1D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_DOC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bin-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BinCardRepository binCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBinCardMockMvc;

    private BinCard binCard;

    private BinCard insertedBinCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BinCard createEntity() {
        return new BinCard()
            .itemID(DEFAULT_ITEM_ID)
            .itemCode(DEFAULT_ITEM_CODE)
            .reference(DEFAULT_REFERENCE)
            .txDate(DEFAULT_TX_DATE)
            .qtyIn(DEFAULT_QTY_IN)
            .qtyOut(DEFAULT_QTY_OUT)
            .price(DEFAULT_PRICE)
            .lMU(DEFAULT_L_MU)
            .lMD(DEFAULT_L_MD)
            .referenceCode(DEFAULT_REFERENCE_CODE)
            .recordDate(DEFAULT_RECORD_DATE)
            .batchId(DEFAULT_BATCH_ID)
            .locationID(DEFAULT_LOCATION_ID)
            .locationCode(DEFAULT_LOCATION_CODE)
            .opening(DEFAULT_OPENING)
            .description(DEFAULT_DESCRIPTION)
            .referenceDoc(DEFAULT_REFERENCE_DOC);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BinCard createUpdatedEntity() {
        return new BinCard()
            .itemID(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .reference(UPDATED_REFERENCE)
            .txDate(UPDATED_TX_DATE)
            .qtyIn(UPDATED_QTY_IN)
            .qtyOut(UPDATED_QTY_OUT)
            .price(UPDATED_PRICE)
            .lMU(UPDATED_L_MU)
            .lMD(UPDATED_L_MD)
            .referenceCode(UPDATED_REFERENCE_CODE)
            .recordDate(UPDATED_RECORD_DATE)
            .batchId(UPDATED_BATCH_ID)
            .locationID(UPDATED_LOCATION_ID)
            .locationCode(UPDATED_LOCATION_CODE)
            .opening(UPDATED_OPENING)
            .description(UPDATED_DESCRIPTION)
            .referenceDoc(UPDATED_REFERENCE_DOC);
    }

    @BeforeEach
    public void initTest() {
        binCard = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBinCard != null) {
            binCardRepository.delete(insertedBinCard);
            insertedBinCard = null;
        }
    }

    @Test
    @Transactional
    void createBinCard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BinCard
        var returnedBinCard = om.readValue(
            restBinCardMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(binCard)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BinCard.class
        );

        // Validate the BinCard in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBinCardUpdatableFieldsEquals(returnedBinCard, getPersistedBinCard(returnedBinCard));

        insertedBinCard = returnedBinCard;
    }

    @Test
    @Transactional
    void createBinCardWithExistingId() throws Exception {
        // Create the BinCard with an existing ID
        binCard.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBinCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(binCard)))
            .andExpect(status().isBadRequest());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBinCards() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList
        restBinCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(binCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemID").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].txDate").value(hasItem(DEFAULT_TX_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyIn").value(hasItem(DEFAULT_QTY_IN)))
            .andExpect(jsonPath("$.[*].qtyOut").value(hasItem(DEFAULT_QTY_OUT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].lMU").value(hasItem(DEFAULT_L_MU)))
            .andExpect(jsonPath("$.[*].lMD").value(hasItem(DEFAULT_L_MD.toString())))
            .andExpect(jsonPath("$.[*].referenceCode").value(hasItem(DEFAULT_REFERENCE_CODE)))
            .andExpect(jsonPath("$.[*].recordDate").value(hasItem(DEFAULT_RECORD_DATE.toString())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationID").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationCode").value(hasItem(DEFAULT_LOCATION_CODE)))
            .andExpect(jsonPath("$.[*].opening").value(hasItem(DEFAULT_OPENING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].referenceDoc").value(hasItem(DEFAULT_REFERENCE_DOC)));
    }

    @Test
    @Transactional
    void getBinCard() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get the binCard
        restBinCardMockMvc
            .perform(get(ENTITY_API_URL_ID, binCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(binCard.getId().intValue()))
            .andExpect(jsonPath("$.itemID").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.txDate").value(DEFAULT_TX_DATE.toString()))
            .andExpect(jsonPath("$.qtyIn").value(DEFAULT_QTY_IN))
            .andExpect(jsonPath("$.qtyOut").value(DEFAULT_QTY_OUT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.lMU").value(DEFAULT_L_MU))
            .andExpect(jsonPath("$.lMD").value(DEFAULT_L_MD.toString()))
            .andExpect(jsonPath("$.referenceCode").value(DEFAULT_REFERENCE_CODE))
            .andExpect(jsonPath("$.recordDate").value(DEFAULT_RECORD_DATE.toString()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID.intValue()))
            .andExpect(jsonPath("$.locationID").value(DEFAULT_LOCATION_ID.intValue()))
            .andExpect(jsonPath("$.locationCode").value(DEFAULT_LOCATION_CODE))
            .andExpect(jsonPath("$.opening").value(DEFAULT_OPENING))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.referenceDoc").value(DEFAULT_REFERENCE_DOC));
    }

    @Test
    @Transactional
    void getBinCardsByIdFiltering() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        Long id = binCard.getId();

        defaultBinCardFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBinCardFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBinCardFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemID equals to
        defaultBinCardFiltering("itemID.equals=" + DEFAULT_ITEM_ID, "itemID.equals=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemID in
        defaultBinCardFiltering("itemID.in=" + DEFAULT_ITEM_ID + "," + UPDATED_ITEM_ID, "itemID.in=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemID is not null
        defaultBinCardFiltering("itemID.specified=true", "itemID.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByItemIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemID is greater than or equal to
        defaultBinCardFiltering("itemID.greaterThanOrEqual=" + DEFAULT_ITEM_ID, "itemID.greaterThanOrEqual=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemID is less than or equal to
        defaultBinCardFiltering("itemID.lessThanOrEqual=" + DEFAULT_ITEM_ID, "itemID.lessThanOrEqual=" + SMALLER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemID is less than
        defaultBinCardFiltering("itemID.lessThan=" + UPDATED_ITEM_ID, "itemID.lessThan=" + DEFAULT_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemID is greater than
        defaultBinCardFiltering("itemID.greaterThan=" + SMALLER_ITEM_ID, "itemID.greaterThan=" + DEFAULT_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemCode equals to
        defaultBinCardFiltering("itemCode.equals=" + DEFAULT_ITEM_CODE, "itemCode.equals=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemCode in
        defaultBinCardFiltering("itemCode.in=" + DEFAULT_ITEM_CODE + "," + UPDATED_ITEM_CODE, "itemCode.in=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemCode is not null
        defaultBinCardFiltering("itemCode.specified=true", "itemCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByItemCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemCode contains
        defaultBinCardFiltering("itemCode.contains=" + DEFAULT_ITEM_CODE, "itemCode.contains=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByItemCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where itemCode does not contain
        defaultBinCardFiltering("itemCode.doesNotContain=" + UPDATED_ITEM_CODE, "itemCode.doesNotContain=" + DEFAULT_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where reference equals to
        defaultBinCardFiltering("reference.equals=" + DEFAULT_REFERENCE, "reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where reference in
        defaultBinCardFiltering("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE, "reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where reference is not null
        defaultBinCardFiltering("reference.specified=true", "reference.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where reference contains
        defaultBinCardFiltering("reference.contains=" + DEFAULT_REFERENCE, "reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where reference does not contain
        defaultBinCardFiltering("reference.doesNotContain=" + UPDATED_REFERENCE, "reference.doesNotContain=" + DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    void getAllBinCardsByTxDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where txDate equals to
        defaultBinCardFiltering("txDate.equals=" + DEFAULT_TX_DATE, "txDate.equals=" + UPDATED_TX_DATE);
    }

    @Test
    @Transactional
    void getAllBinCardsByTxDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where txDate in
        defaultBinCardFiltering("txDate.in=" + DEFAULT_TX_DATE + "," + UPDATED_TX_DATE, "txDate.in=" + UPDATED_TX_DATE);
    }

    @Test
    @Transactional
    void getAllBinCardsByTxDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where txDate is not null
        defaultBinCardFiltering("txDate.specified=true", "txDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyInIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyIn equals to
        defaultBinCardFiltering("qtyIn.equals=" + DEFAULT_QTY_IN, "qtyIn.equals=" + UPDATED_QTY_IN);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyInIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyIn in
        defaultBinCardFiltering("qtyIn.in=" + DEFAULT_QTY_IN + "," + UPDATED_QTY_IN, "qtyIn.in=" + UPDATED_QTY_IN);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyInIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyIn is not null
        defaultBinCardFiltering("qtyIn.specified=true", "qtyIn.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyInIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyIn is greater than or equal to
        defaultBinCardFiltering("qtyIn.greaterThanOrEqual=" + DEFAULT_QTY_IN, "qtyIn.greaterThanOrEqual=" + UPDATED_QTY_IN);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyInIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyIn is less than or equal to
        defaultBinCardFiltering("qtyIn.lessThanOrEqual=" + DEFAULT_QTY_IN, "qtyIn.lessThanOrEqual=" + SMALLER_QTY_IN);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyInIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyIn is less than
        defaultBinCardFiltering("qtyIn.lessThan=" + UPDATED_QTY_IN, "qtyIn.lessThan=" + DEFAULT_QTY_IN);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyInIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyIn is greater than
        defaultBinCardFiltering("qtyIn.greaterThan=" + SMALLER_QTY_IN, "qtyIn.greaterThan=" + DEFAULT_QTY_IN);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyOutIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyOut equals to
        defaultBinCardFiltering("qtyOut.equals=" + DEFAULT_QTY_OUT, "qtyOut.equals=" + UPDATED_QTY_OUT);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyOutIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyOut in
        defaultBinCardFiltering("qtyOut.in=" + DEFAULT_QTY_OUT + "," + UPDATED_QTY_OUT, "qtyOut.in=" + UPDATED_QTY_OUT);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyOutIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyOut is not null
        defaultBinCardFiltering("qtyOut.specified=true", "qtyOut.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyOutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyOut is greater than or equal to
        defaultBinCardFiltering("qtyOut.greaterThanOrEqual=" + DEFAULT_QTY_OUT, "qtyOut.greaterThanOrEqual=" + UPDATED_QTY_OUT);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyOutIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyOut is less than or equal to
        defaultBinCardFiltering("qtyOut.lessThanOrEqual=" + DEFAULT_QTY_OUT, "qtyOut.lessThanOrEqual=" + SMALLER_QTY_OUT);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyOutIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyOut is less than
        defaultBinCardFiltering("qtyOut.lessThan=" + UPDATED_QTY_OUT, "qtyOut.lessThan=" + DEFAULT_QTY_OUT);
    }

    @Test
    @Transactional
    void getAllBinCardsByQtyOutIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where qtyOut is greater than
        defaultBinCardFiltering("qtyOut.greaterThan=" + SMALLER_QTY_OUT, "qtyOut.greaterThan=" + DEFAULT_QTY_OUT);
    }

    @Test
    @Transactional
    void getAllBinCardsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where price equals to
        defaultBinCardFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllBinCardsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where price in
        defaultBinCardFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllBinCardsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where price is not null
        defaultBinCardFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where price is greater than or equal to
        defaultBinCardFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllBinCardsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where price is less than or equal to
        defaultBinCardFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllBinCardsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where price is less than
        defaultBinCardFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllBinCardsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where price is greater than
        defaultBinCardFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMUIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMU equals to
        defaultBinCardFiltering("lMU.equals=" + DEFAULT_L_MU, "lMU.equals=" + UPDATED_L_MU);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMUIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMU in
        defaultBinCardFiltering("lMU.in=" + DEFAULT_L_MU + "," + UPDATED_L_MU, "lMU.in=" + UPDATED_L_MU);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMUIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMU is not null
        defaultBinCardFiltering("lMU.specified=true", "lMU.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsBylMUIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMU is greater than or equal to
        defaultBinCardFiltering("lMU.greaterThanOrEqual=" + DEFAULT_L_MU, "lMU.greaterThanOrEqual=" + UPDATED_L_MU);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMUIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMU is less than or equal to
        defaultBinCardFiltering("lMU.lessThanOrEqual=" + DEFAULT_L_MU, "lMU.lessThanOrEqual=" + SMALLER_L_MU);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMUIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMU is less than
        defaultBinCardFiltering("lMU.lessThan=" + UPDATED_L_MU, "lMU.lessThan=" + DEFAULT_L_MU);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMUIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMU is greater than
        defaultBinCardFiltering("lMU.greaterThan=" + SMALLER_L_MU, "lMU.greaterThan=" + DEFAULT_L_MU);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMD equals to
        defaultBinCardFiltering("lMD.equals=" + DEFAULT_L_MD, "lMD.equals=" + UPDATED_L_MD);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMD in
        defaultBinCardFiltering("lMD.in=" + DEFAULT_L_MD + "," + UPDATED_L_MD, "lMD.in=" + UPDATED_L_MD);
    }

    @Test
    @Transactional
    void getAllBinCardsBylMDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where lMD is not null
        defaultBinCardFiltering("lMD.specified=true", "lMD.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceCode equals to
        defaultBinCardFiltering("referenceCode.equals=" + DEFAULT_REFERENCE_CODE, "referenceCode.equals=" + UPDATED_REFERENCE_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceCode in
        defaultBinCardFiltering(
            "referenceCode.in=" + DEFAULT_REFERENCE_CODE + "," + UPDATED_REFERENCE_CODE,
            "referenceCode.in=" + UPDATED_REFERENCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceCode is not null
        defaultBinCardFiltering("referenceCode.specified=true", "referenceCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceCode contains
        defaultBinCardFiltering("referenceCode.contains=" + DEFAULT_REFERENCE_CODE, "referenceCode.contains=" + UPDATED_REFERENCE_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceCode does not contain
        defaultBinCardFiltering(
            "referenceCode.doesNotContain=" + UPDATED_REFERENCE_CODE,
            "referenceCode.doesNotContain=" + DEFAULT_REFERENCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllBinCardsByRecordDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where recordDate equals to
        defaultBinCardFiltering("recordDate.equals=" + DEFAULT_RECORD_DATE, "recordDate.equals=" + UPDATED_RECORD_DATE);
    }

    @Test
    @Transactional
    void getAllBinCardsByRecordDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where recordDate in
        defaultBinCardFiltering("recordDate.in=" + DEFAULT_RECORD_DATE + "," + UPDATED_RECORD_DATE, "recordDate.in=" + UPDATED_RECORD_DATE);
    }

    @Test
    @Transactional
    void getAllBinCardsByRecordDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where recordDate is not null
        defaultBinCardFiltering("recordDate.specified=true", "recordDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByBatchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where batchId equals to
        defaultBinCardFiltering("batchId.equals=" + DEFAULT_BATCH_ID, "batchId.equals=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByBatchIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where batchId in
        defaultBinCardFiltering("batchId.in=" + DEFAULT_BATCH_ID + "," + UPDATED_BATCH_ID, "batchId.in=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByBatchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where batchId is not null
        defaultBinCardFiltering("batchId.specified=true", "batchId.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByBatchIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where batchId is greater than or equal to
        defaultBinCardFiltering("batchId.greaterThanOrEqual=" + DEFAULT_BATCH_ID, "batchId.greaterThanOrEqual=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByBatchIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where batchId is less than or equal to
        defaultBinCardFiltering("batchId.lessThanOrEqual=" + DEFAULT_BATCH_ID, "batchId.lessThanOrEqual=" + SMALLER_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByBatchIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where batchId is less than
        defaultBinCardFiltering("batchId.lessThan=" + UPDATED_BATCH_ID, "batchId.lessThan=" + DEFAULT_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByBatchIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where batchId is greater than
        defaultBinCardFiltering("batchId.greaterThan=" + SMALLER_BATCH_ID, "batchId.greaterThan=" + DEFAULT_BATCH_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationID equals to
        defaultBinCardFiltering("locationID.equals=" + DEFAULT_LOCATION_ID, "locationID.equals=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationID in
        defaultBinCardFiltering("locationID.in=" + DEFAULT_LOCATION_ID + "," + UPDATED_LOCATION_ID, "locationID.in=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationID is not null
        defaultBinCardFiltering("locationID.specified=true", "locationID.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationID is greater than or equal to
        defaultBinCardFiltering(
            "locationID.greaterThanOrEqual=" + DEFAULT_LOCATION_ID,
            "locationID.greaterThanOrEqual=" + UPDATED_LOCATION_ID
        );
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationID is less than or equal to
        defaultBinCardFiltering("locationID.lessThanOrEqual=" + DEFAULT_LOCATION_ID, "locationID.lessThanOrEqual=" + SMALLER_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationID is less than
        defaultBinCardFiltering("locationID.lessThan=" + UPDATED_LOCATION_ID, "locationID.lessThan=" + DEFAULT_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationID is greater than
        defaultBinCardFiltering("locationID.greaterThan=" + SMALLER_LOCATION_ID, "locationID.greaterThan=" + DEFAULT_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationCode equals to
        defaultBinCardFiltering("locationCode.equals=" + DEFAULT_LOCATION_CODE, "locationCode.equals=" + UPDATED_LOCATION_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationCode in
        defaultBinCardFiltering(
            "locationCode.in=" + DEFAULT_LOCATION_CODE + "," + UPDATED_LOCATION_CODE,
            "locationCode.in=" + UPDATED_LOCATION_CODE
        );
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationCode is not null
        defaultBinCardFiltering("locationCode.specified=true", "locationCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationCode contains
        defaultBinCardFiltering("locationCode.contains=" + DEFAULT_LOCATION_CODE, "locationCode.contains=" + UPDATED_LOCATION_CODE);
    }

    @Test
    @Transactional
    void getAllBinCardsByLocationCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where locationCode does not contain
        defaultBinCardFiltering(
            "locationCode.doesNotContain=" + UPDATED_LOCATION_CODE,
            "locationCode.doesNotContain=" + DEFAULT_LOCATION_CODE
        );
    }

    @Test
    @Transactional
    void getAllBinCardsByOpeningIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where opening equals to
        defaultBinCardFiltering("opening.equals=" + DEFAULT_OPENING, "opening.equals=" + UPDATED_OPENING);
    }

    @Test
    @Transactional
    void getAllBinCardsByOpeningIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where opening in
        defaultBinCardFiltering("opening.in=" + DEFAULT_OPENING + "," + UPDATED_OPENING, "opening.in=" + UPDATED_OPENING);
    }

    @Test
    @Transactional
    void getAllBinCardsByOpeningIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where opening is not null
        defaultBinCardFiltering("opening.specified=true", "opening.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByOpeningIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where opening is greater than or equal to
        defaultBinCardFiltering("opening.greaterThanOrEqual=" + DEFAULT_OPENING, "opening.greaterThanOrEqual=" + UPDATED_OPENING);
    }

    @Test
    @Transactional
    void getAllBinCardsByOpeningIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where opening is less than or equal to
        defaultBinCardFiltering("opening.lessThanOrEqual=" + DEFAULT_OPENING, "opening.lessThanOrEqual=" + SMALLER_OPENING);
    }

    @Test
    @Transactional
    void getAllBinCardsByOpeningIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where opening is less than
        defaultBinCardFiltering("opening.lessThan=" + UPDATED_OPENING, "opening.lessThan=" + DEFAULT_OPENING);
    }

    @Test
    @Transactional
    void getAllBinCardsByOpeningIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where opening is greater than
        defaultBinCardFiltering("opening.greaterThan=" + SMALLER_OPENING, "opening.greaterThan=" + DEFAULT_OPENING);
    }

    @Test
    @Transactional
    void getAllBinCardsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where description equals to
        defaultBinCardFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBinCardsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where description in
        defaultBinCardFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBinCardsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where description is not null
        defaultBinCardFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where description contains
        defaultBinCardFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBinCardsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where description does not contain
        defaultBinCardFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceDocIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceDoc equals to
        defaultBinCardFiltering("referenceDoc.equals=" + DEFAULT_REFERENCE_DOC, "referenceDoc.equals=" + UPDATED_REFERENCE_DOC);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceDocIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceDoc in
        defaultBinCardFiltering(
            "referenceDoc.in=" + DEFAULT_REFERENCE_DOC + "," + UPDATED_REFERENCE_DOC,
            "referenceDoc.in=" + UPDATED_REFERENCE_DOC
        );
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceDocIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceDoc is not null
        defaultBinCardFiltering("referenceDoc.specified=true", "referenceDoc.specified=false");
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceDocContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceDoc contains
        defaultBinCardFiltering("referenceDoc.contains=" + DEFAULT_REFERENCE_DOC, "referenceDoc.contains=" + UPDATED_REFERENCE_DOC);
    }

    @Test
    @Transactional
    void getAllBinCardsByReferenceDocNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        // Get all the binCardList where referenceDoc does not contain
        defaultBinCardFiltering(
            "referenceDoc.doesNotContain=" + UPDATED_REFERENCE_DOC,
            "referenceDoc.doesNotContain=" + DEFAULT_REFERENCE_DOC
        );
    }

    private void defaultBinCardFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBinCardShouldBeFound(shouldBeFound);
        defaultBinCardShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBinCardShouldBeFound(String filter) throws Exception {
        restBinCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(binCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemID").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].txDate").value(hasItem(DEFAULT_TX_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyIn").value(hasItem(DEFAULT_QTY_IN)))
            .andExpect(jsonPath("$.[*].qtyOut").value(hasItem(DEFAULT_QTY_OUT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].lMU").value(hasItem(DEFAULT_L_MU)))
            .andExpect(jsonPath("$.[*].lMD").value(hasItem(DEFAULT_L_MD.toString())))
            .andExpect(jsonPath("$.[*].referenceCode").value(hasItem(DEFAULT_REFERENCE_CODE)))
            .andExpect(jsonPath("$.[*].recordDate").value(hasItem(DEFAULT_RECORD_DATE.toString())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationID").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationCode").value(hasItem(DEFAULT_LOCATION_CODE)))
            .andExpect(jsonPath("$.[*].opening").value(hasItem(DEFAULT_OPENING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].referenceDoc").value(hasItem(DEFAULT_REFERENCE_DOC)));

        // Check, that the count call also returns 1
        restBinCardMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBinCardShouldNotBeFound(String filter) throws Exception {
        restBinCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBinCardMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBinCard() throws Exception {
        // Get the binCard
        restBinCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBinCard() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the binCard
        BinCard updatedBinCard = binCardRepository.findById(binCard.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBinCard are not directly saved in db
        em.detach(updatedBinCard);
        updatedBinCard
            .itemID(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .reference(UPDATED_REFERENCE)
            .txDate(UPDATED_TX_DATE)
            .qtyIn(UPDATED_QTY_IN)
            .qtyOut(UPDATED_QTY_OUT)
            .price(UPDATED_PRICE)
            .lMU(UPDATED_L_MU)
            .lMD(UPDATED_L_MD)
            .referenceCode(UPDATED_REFERENCE_CODE)
            .recordDate(UPDATED_RECORD_DATE)
            .batchId(UPDATED_BATCH_ID)
            .locationID(UPDATED_LOCATION_ID)
            .locationCode(UPDATED_LOCATION_CODE)
            .opening(UPDATED_OPENING)
            .description(UPDATED_DESCRIPTION)
            .referenceDoc(UPDATED_REFERENCE_DOC);

        restBinCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBinCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBinCard))
            )
            .andExpect(status().isOk());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBinCardToMatchAllProperties(updatedBinCard);
    }

    @Test
    @Transactional
    void putNonExistingBinCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binCard.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBinCardMockMvc
            .perform(put(ENTITY_API_URL_ID, binCard.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(binCard)))
            .andExpect(status().isBadRequest());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBinCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(binCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBinCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(binCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBinCardWithPatch() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the binCard using partial update
        BinCard partialUpdatedBinCard = new BinCard();
        partialUpdatedBinCard.setId(binCard.getId());

        partialUpdatedBinCard
            .itemID(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .qtyIn(UPDATED_QTY_IN)
            .lMU(UPDATED_L_MU)
            .lMD(UPDATED_L_MD)
            .recordDate(UPDATED_RECORD_DATE)
            .batchId(UPDATED_BATCH_ID)
            .description(UPDATED_DESCRIPTION);

        restBinCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBinCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBinCard))
            )
            .andExpect(status().isOk());

        // Validate the BinCard in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBinCardUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBinCard, binCard), getPersistedBinCard(binCard));
    }

    @Test
    @Transactional
    void fullUpdateBinCardWithPatch() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the binCard using partial update
        BinCard partialUpdatedBinCard = new BinCard();
        partialUpdatedBinCard.setId(binCard.getId());

        partialUpdatedBinCard
            .itemID(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .reference(UPDATED_REFERENCE)
            .txDate(UPDATED_TX_DATE)
            .qtyIn(UPDATED_QTY_IN)
            .qtyOut(UPDATED_QTY_OUT)
            .price(UPDATED_PRICE)
            .lMU(UPDATED_L_MU)
            .lMD(UPDATED_L_MD)
            .referenceCode(UPDATED_REFERENCE_CODE)
            .recordDate(UPDATED_RECORD_DATE)
            .batchId(UPDATED_BATCH_ID)
            .locationID(UPDATED_LOCATION_ID)
            .locationCode(UPDATED_LOCATION_CODE)
            .opening(UPDATED_OPENING)
            .description(UPDATED_DESCRIPTION)
            .referenceDoc(UPDATED_REFERENCE_DOC);

        restBinCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBinCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBinCard))
            )
            .andExpect(status().isOk());

        // Validate the BinCard in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBinCardUpdatableFieldsEquals(partialUpdatedBinCard, getPersistedBinCard(partialUpdatedBinCard));
    }

    @Test
    @Transactional
    void patchNonExistingBinCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binCard.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBinCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, binCard.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(binCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBinCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(binCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBinCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        binCard.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBinCardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(binCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BinCard in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBinCard() throws Exception {
        // Initialize the database
        insertedBinCard = binCardRepository.saveAndFlush(binCard);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the binCard
        restBinCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, binCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return binCardRepository.count();
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

    protected BinCard getPersistedBinCard(BinCard binCard) {
        return binCardRepository.findById(binCard.getId()).orElseThrow();
    }

    protected void assertPersistedBinCardToMatchAllProperties(BinCard expectedBinCard) {
        assertBinCardAllPropertiesEquals(expectedBinCard, getPersistedBinCard(expectedBinCard));
    }

    protected void assertPersistedBinCardToMatchUpdatableProperties(BinCard expectedBinCard) {
        assertBinCardAllUpdatablePropertiesEquals(expectedBinCard, getPersistedBinCard(expectedBinCard));
    }
}
