package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummyAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeDummyRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link SaleInvoiceCommonServiceChargeDummyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleInvoiceCommonServiceChargeDummyResourceIT {

    private static final Integer DEFAULT_INVOICEID = 1;
    private static final Integer UPDATED_INVOICEID = 2;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;

    private static final Integer DEFAULT_OPTIONID = 1;
    private static final Integer UPDATED_OPTIONID = 2;

    private static final Integer DEFAULT_MAINID = 1;
    private static final Integer UPDATED_MAINID = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final String ENTITY_API_URL = "/api/sale-invoice-common-service-charge-dummies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleInvoiceCommonServiceChargeDummyMockMvc;

    private SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy;

    private SaleInvoiceCommonServiceChargeDummy insertedSaleInvoiceCommonServiceChargeDummy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleInvoiceCommonServiceChargeDummy createEntity() {
        return new SaleInvoiceCommonServiceChargeDummy()
            .invoiceid(DEFAULT_INVOICEID)
            .lineid(DEFAULT_LINEID)
            .optionid(DEFAULT_OPTIONID)
            .mainid(DEFAULT_MAINID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleInvoiceCommonServiceChargeDummy createUpdatedEntity() {
        return new SaleInvoiceCommonServiceChargeDummy()
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);
    }

    @BeforeEach
    public void initTest() {
        saleInvoiceCommonServiceChargeDummy = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSaleInvoiceCommonServiceChargeDummy != null) {
            saleInvoiceCommonServiceChargeDummyRepository.delete(insertedSaleInvoiceCommonServiceChargeDummy);
            insertedSaleInvoiceCommonServiceChargeDummy = null;
        }
    }

    @Test
    @Transactional
    void createSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaleInvoiceCommonServiceChargeDummy
        var returnedSaleInvoiceCommonServiceChargeDummy = om.readValue(
            restSaleInvoiceCommonServiceChargeDummyMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaleInvoiceCommonServiceChargeDummy.class
        );

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(
            returnedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(returnedSaleInvoiceCommonServiceChargeDummy)
        );

        insertedSaleInvoiceCommonServiceChargeDummy = returnedSaleInvoiceCommonServiceChargeDummy;
    }

    @Test
    @Transactional
    void createSaleInvoiceCommonServiceChargeDummyWithExistingId() throws Exception {
        // Create the SaleInvoiceCommonServiceChargeDummy with an existing ID
        saleInvoiceCommonServiceChargeDummy.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummies() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoiceCommonServiceChargeDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    void getSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get the saleInvoiceCommonServiceChargeDummy
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saleInvoiceCommonServiceChargeDummy.getId().intValue()))
            .andExpect(jsonPath("$.invoiceid").value(DEFAULT_INVOICEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.optionid").value(DEFAULT_OPTIONID))
            .andExpect(jsonPath("$.mainid").value(DEFAULT_MAINID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Get the saleInvoiceCommonServiceChargeDummy
        restSaleInvoiceCommonServiceChargeDummyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceChargeDummy
        SaleInvoiceCommonServiceChargeDummy updatedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository
            .findById(saleInvoiceCommonServiceChargeDummy.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSaleInvoiceCommonServiceChargeDummy are not directly saved in db
        em.detach(updatedSaleInvoiceCommonServiceChargeDummy);
        updatedSaleInvoiceCommonServiceChargeDummy
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);

        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSaleInvoiceCommonServiceChargeDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSaleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaleInvoiceCommonServiceChargeDummyToMatchAllProperties(updatedSaleInvoiceCommonServiceChargeDummy);
    }

    @Test
    @Transactional
    void putNonExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleInvoiceCommonServiceChargeDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceChargeDummy using partial update
        SaleInvoiceCommonServiceChargeDummy partialUpdatedSaleInvoiceCommonServiceChargeDummy = new SaleInvoiceCommonServiceChargeDummy();
        partialUpdatedSaleInvoiceCommonServiceChargeDummy.setId(saleInvoiceCommonServiceChargeDummy.getId());

        partialUpdatedSaleInvoiceCommonServiceChargeDummy
            .invoiceid(UPDATED_INVOICEID)
            .optionid(UPDATED_OPTIONID)
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE);

        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleInvoiceCommonServiceChargeDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaleInvoiceCommonServiceChargeDummy, saleInvoiceCommonServiceChargeDummy),
            getPersistedSaleInvoiceCommonServiceChargeDummy(saleInvoiceCommonServiceChargeDummy)
        );
    }

    @Test
    @Transactional
    void fullUpdateSaleInvoiceCommonServiceChargeDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceChargeDummy using partial update
        SaleInvoiceCommonServiceChargeDummy partialUpdatedSaleInvoiceCommonServiceChargeDummy = new SaleInvoiceCommonServiceChargeDummy();
        partialUpdatedSaleInvoiceCommonServiceChargeDummy.setId(saleInvoiceCommonServiceChargeDummy.getId());

        partialUpdatedSaleInvoiceCommonServiceChargeDummy
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);

        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleInvoiceCommonServiceChargeDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(
            partialUpdatedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(partialUpdatedSaleInvoiceCommonServiceChargeDummy)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saleInvoiceCommonServiceChargeDummy
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(delete(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saleInvoiceCommonServiceChargeDummyRepository.count();
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

    protected SaleInvoiceCommonServiceChargeDummy getPersistedSaleInvoiceCommonServiceChargeDummy(
        SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy
    ) {
        return saleInvoiceCommonServiceChargeDummyRepository.findById(saleInvoiceCommonServiceChargeDummy.getId()).orElseThrow();
    }

    protected void assertPersistedSaleInvoiceCommonServiceChargeDummyToMatchAllProperties(
        SaleInvoiceCommonServiceChargeDummy expectedSaleInvoiceCommonServiceChargeDummy
    ) {
        assertSaleInvoiceCommonServiceChargeDummyAllPropertiesEquals(
            expectedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(expectedSaleInvoiceCommonServiceChargeDummy)
        );
    }

    protected void assertPersistedSaleInvoiceCommonServiceChargeDummyToMatchUpdatableProperties(
        SaleInvoiceCommonServiceChargeDummy expectedSaleInvoiceCommonServiceChargeDummy
    ) {
        assertSaleInvoiceCommonServiceChargeDummyAllUpdatablePropertiesEquals(
            expectedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(expectedSaleInvoiceCommonServiceChargeDummy)
        );
    }
}
