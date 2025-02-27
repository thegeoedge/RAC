package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsaleinvoicecommonservicechargeAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import com.heavenscode.rac.repository.AutojobsaleinvoicecommonservicechargeRepository;
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
 * Integration tests for the {@link AutojobsaleinvoicecommonservicechargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsaleinvoicecommonservicechargeResourceIT {

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

    private static final Integer DEFAULT_ADDEDBYID = 1;
    private static final Integer UPDATED_ADDEDBYID = 2;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;

    private static final Float DEFAULT_SERVICEPRICE = 1F;
    private static final Float UPDATED_SERVICEPRICE = 2F;

    private static final String ENTITY_API_URL = "/api/autojobsaleinvoicecommonservicecharges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsaleinvoicecommonservicechargeMockMvc;

    private Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge;

    private Autojobsaleinvoicecommonservicecharge insertedAutojobsaleinvoicecommonservicecharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsaleinvoicecommonservicecharge createEntity() {
        return new Autojobsaleinvoicecommonservicecharge()
            .invoiceid(DEFAULT_INVOICEID)
            .lineid(DEFAULT_LINEID)
            .optionid(DEFAULT_OPTIONID)
            .mainid(DEFAULT_MAINID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .addedbyid(DEFAULT_ADDEDBYID)
            .discount(DEFAULT_DISCOUNT)
            .serviceprice(DEFAULT_SERVICEPRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsaleinvoicecommonservicecharge createUpdatedEntity() {
        return new Autojobsaleinvoicecommonservicecharge()
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);
    }

    @BeforeEach
    public void initTest() {
        autojobsaleinvoicecommonservicecharge = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsaleinvoicecommonservicecharge != null) {
            autojobsaleinvoicecommonservicechargeRepository.delete(insertedAutojobsaleinvoicecommonservicecharge);
            insertedAutojobsaleinvoicecommonservicecharge = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsaleinvoicecommonservicecharge
        var returnedAutojobsaleinvoicecommonservicecharge = om.readValue(
            restAutojobsaleinvoicecommonservicechargeMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsaleinvoicecommonservicecharge.class
        );

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsaleinvoicecommonservicechargeUpdatableFieldsEquals(
            returnedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(returnedAutojobsaleinvoicecommonservicecharge)
        );

        insertedAutojobsaleinvoicecommonservicecharge = returnedAutojobsaleinvoicecommonservicecharge;
    }

    @Test
    @Transactional
    void createAutojobsaleinvoicecommonservicechargeWithExistingId() throws Exception {
        // Create the Autojobsaleinvoicecommonservicecharge with an existing ID
        autojobsaleinvoicecommonservicecharge.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicecharges() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsaleinvoicecommonservicecharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceprice").value(hasItem(DEFAULT_SERVICEPRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get the autojobsaleinvoicecommonservicecharge
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsaleinvoicecommonservicecharge.getId().intValue()))
            .andExpect(jsonPath("$.invoiceid").value(DEFAULT_INVOICEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.optionid").value(DEFAULT_OPTIONID))
            .andExpect(jsonPath("$.mainid").value(DEFAULT_MAINID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.addedbyid").value(DEFAULT_ADDEDBYID))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.serviceprice").value(DEFAULT_SERVICEPRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Get the autojobsaleinvoicecommonservicecharge
        restAutojobsaleinvoicecommonservicechargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsaleinvoicecommonservicecharge
        Autojobsaleinvoicecommonservicecharge updatedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository
            .findById(autojobsaleinvoicecommonservicecharge.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsaleinvoicecommonservicecharge are not directly saved in db
        em.detach(updatedAutojobsaleinvoicecommonservicecharge);
        updatedAutojobsaleinvoicecommonservicecharge
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsaleinvoicecommonservicecharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsaleinvoicecommonservicechargeToMatchAllProperties(updatedAutojobsaleinvoicecommonservicecharge);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsaleinvoicecommonservicechargeWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsaleinvoicecommonservicecharge using partial update
        Autojobsaleinvoicecommonservicecharge partialUpdatedAutojobsaleinvoicecommonservicecharge =
            new Autojobsaleinvoicecommonservicecharge();
        partialUpdatedAutojobsaleinvoicecommonservicecharge.setId(autojobsaleinvoicecommonservicecharge.getId());

        partialUpdatedAutojobsaleinvoicecommonservicecharge
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsaleinvoicecommonservicecharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsaleinvoicecommonservicechargeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsaleinvoicecommonservicecharge, autojobsaleinvoicecommonservicecharge),
            getPersistedAutojobsaleinvoicecommonservicecharge(autojobsaleinvoicecommonservicecharge)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsaleinvoicecommonservicechargeWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsaleinvoicecommonservicecharge using partial update
        Autojobsaleinvoicecommonservicecharge partialUpdatedAutojobsaleinvoicecommonservicecharge =
            new Autojobsaleinvoicecommonservicecharge();
        partialUpdatedAutojobsaleinvoicecommonservicecharge.setId(autojobsaleinvoicecommonservicecharge.getId());

        partialUpdatedAutojobsaleinvoicecommonservicecharge
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsaleinvoicecommonservicecharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsaleinvoicecommonservicechargeUpdatableFieldsEquals(
            partialUpdatedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(partialUpdatedAutojobsaleinvoicecommonservicecharge)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsaleinvoicecommonservicecharge
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsaleinvoicecommonservicechargeRepository.count();
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

    protected Autojobsaleinvoicecommonservicecharge getPersistedAutojobsaleinvoicecommonservicecharge(
        Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) {
        return autojobsaleinvoicecommonservicechargeRepository.findById(autojobsaleinvoicecommonservicecharge.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsaleinvoicecommonservicechargeToMatchAllProperties(
        Autojobsaleinvoicecommonservicecharge expectedAutojobsaleinvoicecommonservicecharge
    ) {
        assertAutojobsaleinvoicecommonservicechargeAllPropertiesEquals(
            expectedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(expectedAutojobsaleinvoicecommonservicecharge)
        );
    }

    protected void assertPersistedAutojobsaleinvoicecommonservicechargeToMatchUpdatableProperties(
        Autojobsaleinvoicecommonservicecharge expectedAutojobsaleinvoicecommonservicecharge
    ) {
        assertAutojobsaleinvoicecommonservicechargeAllUpdatablePropertiesEquals(
            expectedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(expectedAutojobsaleinvoicecommonservicecharge)
        );
    }
}
