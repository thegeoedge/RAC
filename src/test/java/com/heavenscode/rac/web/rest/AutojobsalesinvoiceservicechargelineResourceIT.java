package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsalesinvoiceservicechargelineAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
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
 * Integration tests for the {@link AutojobsalesinvoiceservicechargelineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsalesinvoiceservicechargelineResourceIT {

    private static final Integer DEFAULT_INVOICEID = 1;
    private static final Integer UPDATED_INVOICEID = 2;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;

    private static final Integer DEFAULT_OPTIONID = 1;
    private static final Integer UPDATED_OPTIONID = 2;

    private static final String DEFAULT_SERVICENAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICEDISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICEDISCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final Integer DEFAULT_ADDEDBYID = 1;
    private static final Integer UPDATED_ADDEDBYID = 2;

    private static final Boolean DEFAULT_ISCUSTOMERSRVICE = false;
    private static final Boolean UPDATED_ISCUSTOMERSRVICE = true;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;

    private static final Float DEFAULT_SERVICEPRICE = 1F;
    private static final Float UPDATED_SERVICEPRICE = 2F;

    private static final String ENTITY_API_URL = "/api/autojobsalesinvoiceservicechargelines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsalesinvoiceservicechargelineMockMvc;

    private Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline;

    private Autojobsalesinvoiceservicechargeline insertedAutojobsalesinvoiceservicechargeline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsalesinvoiceservicechargeline createEntity() {
        return new Autojobsalesinvoiceservicechargeline()
            .invoiceid(DEFAULT_INVOICEID)
            .lineid(DEFAULT_LINEID)
            .optionid(DEFAULT_OPTIONID)
            .servicename(DEFAULT_SERVICENAME)
            .servicediscription(DEFAULT_SERVICEDISCRIPTION)
            .value(DEFAULT_VALUE)
            .addedbyid(DEFAULT_ADDEDBYID)
            .iscustomersrvice(DEFAULT_ISCUSTOMERSRVICE)
            .discount(DEFAULT_DISCOUNT)
            .serviceprice(DEFAULT_SERVICEPRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsalesinvoiceservicechargeline createUpdatedEntity() {
        return new Autojobsalesinvoiceservicechargeline()
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);
    }

    @BeforeEach
    public void initTest() {
        autojobsalesinvoiceservicechargeline = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsalesinvoiceservicechargeline != null) {
            autojobsalesinvoiceservicechargelineRepository.delete(insertedAutojobsalesinvoiceservicechargeline);
            insertedAutojobsalesinvoiceservicechargeline = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsalesinvoiceservicechargeline
        var returnedAutojobsalesinvoiceservicechargeline = om.readValue(
            restAutojobsalesinvoiceservicechargelineMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsalesinvoiceservicechargeline.class
        );

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsalesinvoiceservicechargelineUpdatableFieldsEquals(
            returnedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(returnedAutojobsalesinvoiceservicechargeline)
        );

        insertedAutojobsalesinvoiceservicechargeline = returnedAutojobsalesinvoiceservicechargeline;
    }

    @Test
    @Transactional
    void createAutojobsalesinvoiceservicechargelineWithExistingId() throws Exception {
        // Create the Autojobsalesinvoiceservicechargeline with an existing ID
        autojobsalesinvoiceservicechargeline.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelines() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsalesinvoiceservicechargeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].servicename").value(hasItem(DEFAULT_SERVICENAME)))
            .andExpect(jsonPath("$.[*].servicediscription").value(hasItem(DEFAULT_SERVICEDISCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].iscustomersrvice").value(hasItem(DEFAULT_ISCUSTOMERSRVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceprice").value(hasItem(DEFAULT_SERVICEPRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getAutojobsalesinvoiceservicechargeline() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get the autojobsalesinvoiceservicechargeline
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsalesinvoiceservicechargeline.getId().intValue()))
            .andExpect(jsonPath("$.invoiceid").value(DEFAULT_INVOICEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.optionid").value(DEFAULT_OPTIONID))
            .andExpect(jsonPath("$.servicename").value(DEFAULT_SERVICENAME))
            .andExpect(jsonPath("$.servicediscription").value(DEFAULT_SERVICEDISCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.addedbyid").value(DEFAULT_ADDEDBYID))
            .andExpect(jsonPath("$.iscustomersrvice").value(DEFAULT_ISCUSTOMERSRVICE.booleanValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.serviceprice").value(DEFAULT_SERVICEPRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        // Get the autojobsalesinvoiceservicechargeline
        restAutojobsalesinvoiceservicechargelineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsalesinvoiceservicechargeline
        Autojobsalesinvoiceservicechargeline updatedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository
            .findById(autojobsalesinvoiceservicechargeline.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsalesinvoiceservicechargeline are not directly saved in db
        em.detach(updatedAutojobsalesinvoiceservicechargeline);
        updatedAutojobsalesinvoiceservicechargeline
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsalesinvoiceservicechargeline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsalesinvoiceservicechargelineToMatchAllProperties(updatedAutojobsalesinvoiceservicechargeline);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsalesinvoiceservicechargelineWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsalesinvoiceservicechargeline using partial update
        Autojobsalesinvoiceservicechargeline partialUpdatedAutojobsalesinvoiceservicechargeline =
            new Autojobsalesinvoiceservicechargeline();
        partialUpdatedAutojobsalesinvoiceservicechargeline.setId(autojobsalesinvoiceservicechargeline.getId());

        partialUpdatedAutojobsalesinvoiceservicechargeline
            .invoiceid(UPDATED_INVOICEID)
            .optionid(UPDATED_OPTIONID)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT);

        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsalesinvoiceservicechargeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsalesinvoiceservicechargeline in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsalesinvoiceservicechargelineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsalesinvoiceservicechargeline, autojobsalesinvoiceservicechargeline),
            getPersistedAutojobsalesinvoiceservicechargeline(autojobsalesinvoiceservicechargeline)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsalesinvoiceservicechargelineWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsalesinvoiceservicechargeline using partial update
        Autojobsalesinvoiceservicechargeline partialUpdatedAutojobsalesinvoiceservicechargeline =
            new Autojobsalesinvoiceservicechargeline();
        partialUpdatedAutojobsalesinvoiceservicechargeline.setId(autojobsalesinvoiceservicechargeline.getId());

        partialUpdatedAutojobsalesinvoiceservicechargeline
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsalesinvoiceservicechargeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsalesinvoiceservicechargeline in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsalesinvoiceservicechargelineUpdatableFieldsEquals(
            partialUpdatedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(partialUpdatedAutojobsalesinvoiceservicechargeline)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsalesinvoiceservicechargeline() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsalesinvoiceservicechargeline
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsalesinvoiceservicechargelineRepository.count();
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

    protected Autojobsalesinvoiceservicechargeline getPersistedAutojobsalesinvoiceservicechargeline(
        Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) {
        return autojobsalesinvoiceservicechargelineRepository.findById(autojobsalesinvoiceservicechargeline.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsalesinvoiceservicechargelineToMatchAllProperties(
        Autojobsalesinvoiceservicechargeline expectedAutojobsalesinvoiceservicechargeline
    ) {
        assertAutojobsalesinvoiceservicechargelineAllPropertiesEquals(
            expectedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(expectedAutojobsalesinvoiceservicechargeline)
        );
    }

    protected void assertPersistedAutojobsalesinvoiceservicechargelineToMatchUpdatableProperties(
        Autojobsalesinvoiceservicechargeline expectedAutojobsalesinvoiceservicechargeline
    ) {
        assertAutojobsalesinvoiceservicechargelineAllUpdatablePropertiesEquals(
            expectedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(expectedAutojobsalesinvoiceservicechargeline)
        );
    }
}
