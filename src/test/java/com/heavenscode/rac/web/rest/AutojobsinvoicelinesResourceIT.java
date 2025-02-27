package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsinvoicelinesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsinvoicelines;
import com.heavenscode.rac.repository.AutojobsinvoicelinesRepository;
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
 * Integration tests for the {@link AutojobsinvoicelinesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsinvoicelinesResourceIT {

    private static final Integer DEFAULT_INVOCIEID = 1;
    private static final Integer UPDATED_INVOCIEID = 2;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;

    private static final Integer DEFAULT_ITEMID = 1;
    private static final Integer UPDATED_ITEMID = 2;

    private static final String DEFAULT_ITEMCODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEMNAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEMNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_UNITOFMEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_UNITOFMEASUREMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;

    private static final Float DEFAULT_ITEMCOST = 1F;
    private static final Float UPDATED_ITEMCOST = 2F;

    private static final Float DEFAULT_ITEMPRICE = 1F;
    private static final Float UPDATED_ITEMPRICE = 2F;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;

    private static final Float DEFAULT_SELLINGPRICE = 1F;
    private static final Float UPDATED_SELLINGPRICE = 2F;

    private static final Float DEFAULT_LINETOTAL = 1F;
    private static final Float UPDATED_LINETOTAL = 2F;

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_NBT = false;
    private static final Boolean UPDATED_NBT = true;

    private static final Boolean DEFAULT_VAT = false;
    private static final Boolean UPDATED_VAT = true;

    private static final String ENTITY_API_URL = "/api/autojobsinvoicelines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsinvoicelinesRepository autojobsinvoicelinesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsinvoicelinesMockMvc;

    private Autojobsinvoicelines autojobsinvoicelines;

    private Autojobsinvoicelines insertedAutojobsinvoicelines;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsinvoicelines createEntity() {
        return new Autojobsinvoicelines()
            .invocieid(DEFAULT_INVOCIEID)
            .lineid(DEFAULT_LINEID)
            .itemid(DEFAULT_ITEMID)
            .itemcode(DEFAULT_ITEMCODE)
            .itemname(DEFAULT_ITEMNAME)
            .description(DEFAULT_DESCRIPTION)
            .unitofmeasurement(DEFAULT_UNITOFMEASUREMENT)
            .quantity(DEFAULT_QUANTITY)
            .itemcost(DEFAULT_ITEMCOST)
            .itemprice(DEFAULT_ITEMPRICE)
            .discount(DEFAULT_DISCOUNT)
            .tax(DEFAULT_TAX)
            .sellingprice(DEFAULT_SELLINGPRICE)
            .linetotal(DEFAULT_LINETOTAL)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .nbt(DEFAULT_NBT)
            .vat(DEFAULT_VAT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsinvoicelines createUpdatedEntity() {
        return new Autojobsinvoicelines()
            .invocieid(UPDATED_INVOCIEID)
            .lineid(UPDATED_LINEID)
            .itemid(UPDATED_ITEMID)
            .itemcode(UPDATED_ITEMCODE)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemcost(UPDATED_ITEMCOST)
            .itemprice(UPDATED_ITEMPRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingprice(UPDATED_SELLINGPRICE)
            .linetotal(UPDATED_LINETOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);
    }

    @BeforeEach
    public void initTest() {
        autojobsinvoicelines = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsinvoicelines != null) {
            autojobsinvoicelinesRepository.delete(insertedAutojobsinvoicelines);
            insertedAutojobsinvoicelines = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsinvoicelines
        var returnedAutojobsinvoicelines = om.readValue(
            restAutojobsinvoicelinesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelines)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsinvoicelines.class
        );

        // Validate the Autojobsinvoicelines in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsinvoicelinesUpdatableFieldsEquals(
            returnedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(returnedAutojobsinvoicelines)
        );

        insertedAutojobsinvoicelines = returnedAutojobsinvoicelines;
    }

    @Test
    @Transactional
    void createAutojobsinvoicelinesWithExistingId() throws Exception {
        // Create the Autojobsinvoicelines with an existing ID
        autojobsinvoicelines.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsinvoicelinesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelines)))
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsinvoicelines.getId().intValue())))
            .andExpect(jsonPath("$.[*].invocieid").value(hasItem(DEFAULT_INVOCIEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].itemid").value(hasItem(DEFAULT_ITEMID)))
            .andExpect(jsonPath("$.[*].itemcode").value(hasItem(DEFAULT_ITEMCODE)))
            .andExpect(jsonPath("$.[*].itemname").value(hasItem(DEFAULT_ITEMNAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitofmeasurement").value(hasItem(DEFAULT_UNITOFMEASUREMENT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].itemcost").value(hasItem(DEFAULT_ITEMCOST.doubleValue())))
            .andExpect(jsonPath("$.[*].itemprice").value(hasItem(DEFAULT_ITEMPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingprice").value(hasItem(DEFAULT_SELLINGPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].linetotal").value(hasItem(DEFAULT_LINETOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())));
    }

    @Test
    @Transactional
    void getAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get the autojobsinvoicelines
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsinvoicelines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsinvoicelines.getId().intValue()))
            .andExpect(jsonPath("$.invocieid").value(DEFAULT_INVOCIEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.itemid").value(DEFAULT_ITEMID))
            .andExpect(jsonPath("$.itemcode").value(DEFAULT_ITEMCODE))
            .andExpect(jsonPath("$.itemname").value(DEFAULT_ITEMNAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.unitofmeasurement").value(DEFAULT_UNITOFMEASUREMENT))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.itemcost").value(DEFAULT_ITEMCOST.doubleValue()))
            .andExpect(jsonPath("$.itemprice").value(DEFAULT_ITEMPRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.sellingprice").value(DEFAULT_SELLINGPRICE.doubleValue()))
            .andExpect(jsonPath("$.linetotal").value(DEFAULT_LINETOTAL.doubleValue()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.nbt").value(DEFAULT_NBT.booleanValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsinvoicelines() throws Exception {
        // Get the autojobsinvoicelines
        restAutojobsinvoicelinesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelines
        Autojobsinvoicelines updatedAutojobsinvoicelines = autojobsinvoicelinesRepository
            .findById(autojobsinvoicelines.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsinvoicelines are not directly saved in db
        em.detach(updatedAutojobsinvoicelines);
        updatedAutojobsinvoicelines
            .invocieid(UPDATED_INVOCIEID)
            .lineid(UPDATED_LINEID)
            .itemid(UPDATED_ITEMID)
            .itemcode(UPDATED_ITEMCODE)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemcost(UPDATED_ITEMCOST)
            .itemprice(UPDATED_ITEMPRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingprice(UPDATED_SELLINGPRICE)
            .linetotal(UPDATED_LINETOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);

        restAutojobsinvoicelinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsinvoicelines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsinvoicelines))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsinvoicelinesToMatchAllProperties(updatedAutojobsinvoicelines);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsinvoicelines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsinvoicelinesWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelines using partial update
        Autojobsinvoicelines partialUpdatedAutojobsinvoicelines = new Autojobsinvoicelines();
        partialUpdatedAutojobsinvoicelines.setId(autojobsinvoicelines.getId());

        partialUpdatedAutojobsinvoicelines
            .lineid(UPDATED_LINEID)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX);

        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoicelines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoicelines))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoicelinesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsinvoicelines, autojobsinvoicelines),
            getPersistedAutojobsinvoicelines(autojobsinvoicelines)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsinvoicelinesWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelines using partial update
        Autojobsinvoicelines partialUpdatedAutojobsinvoicelines = new Autojobsinvoicelines();
        partialUpdatedAutojobsinvoicelines.setId(autojobsinvoicelines.getId());

        partialUpdatedAutojobsinvoicelines
            .invocieid(UPDATED_INVOCIEID)
            .lineid(UPDATED_LINEID)
            .itemid(UPDATED_ITEMID)
            .itemcode(UPDATED_ITEMCODE)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemcost(UPDATED_ITEMCOST)
            .itemprice(UPDATED_ITEMPRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingprice(UPDATED_SELLINGPRICE)
            .linetotal(UPDATED_LINETOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);

        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoicelines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoicelines))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoicelinesUpdatableFieldsEquals(
            partialUpdatedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(partialUpdatedAutojobsinvoicelines)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsinvoicelines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autojobsinvoicelines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsinvoicelines
        restAutojobsinvoicelinesMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsinvoicelines.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsinvoicelinesRepository.count();
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

    protected Autojobsinvoicelines getPersistedAutojobsinvoicelines(Autojobsinvoicelines autojobsinvoicelines) {
        return autojobsinvoicelinesRepository.findById(autojobsinvoicelines.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsinvoicelinesToMatchAllProperties(Autojobsinvoicelines expectedAutojobsinvoicelines) {
        assertAutojobsinvoicelinesAllPropertiesEquals(
            expectedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(expectedAutojobsinvoicelines)
        );
    }

    protected void assertPersistedAutojobsinvoicelinesToMatchUpdatableProperties(Autojobsinvoicelines expectedAutojobsinvoicelines) {
        assertAutojobsinvoicelinesAllUpdatablePropertiesEquals(
            expectedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(expectedAutojobsinvoicelines)
        );
    }
}
