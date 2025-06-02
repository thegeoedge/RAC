package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.BanksAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Banks;
import com.heavenscode.rac.repository.BanksRepository;
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
 * Integration tests for the {@link BanksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BanksResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/banks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BanksRepository banksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBanksMockMvc;

    private Banks banks;

    private Banks insertedBanks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banks createEntity() {
        return new Banks().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION).lmu(DEFAULT_LMU).lmd(DEFAULT_LMD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banks createUpdatedEntity() {
        return new Banks().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION).lmu(UPDATED_LMU).lmd(UPDATED_LMD);
    }

    @BeforeEach
    public void initTest() {
        banks = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBanks != null) {
            banksRepository.delete(insertedBanks);
            insertedBanks = null;
        }
    }

    @Test
    @Transactional
    void createBanks() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Banks
        var returnedBanks = om.readValue(
            restBanksMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(banks)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Banks.class
        );

        // Validate the Banks in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBanksUpdatableFieldsEquals(returnedBanks, getPersistedBanks(returnedBanks));

        insertedBanks = returnedBanks;
    }

    @Test
    @Transactional
    void createBanksWithExistingId() throws Exception {
        // Create the Banks with an existing ID
        banks.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(banks)))
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanks() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banks.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())));
    }

    @Test
    @Transactional
    void getBanks() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get the banks
        restBanksMockMvc
            .perform(get(ENTITY_API_URL_ID, banks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banks.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()));
    }

    @Test
    @Transactional
    void getBanksByIdFiltering() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        Long id = banks.getId();

        defaultBanksFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBanksFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBanksFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBanksByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where code equals to
        defaultBanksFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where code in
        defaultBanksFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where code is not null
        defaultBanksFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where code contains
        defaultBanksFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where code does not contain
        defaultBanksFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where name equals to
        defaultBanksFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where name in
        defaultBanksFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where name is not null
        defaultBanksFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where name contains
        defaultBanksFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where name does not contain
        defaultBanksFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where description equals to
        defaultBanksFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBanksByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where description in
        defaultBanksFiltering("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION, "description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBanksByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where description is not null
        defaultBanksFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where description contains
        defaultBanksFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBanksByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where description does not contain
        defaultBanksFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBanksByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmu equals to
        defaultBanksFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBanksByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmu in
        defaultBanksFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBanksByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmu is not null
        defaultBanksFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmu is greater than or equal to
        defaultBanksFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBanksByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmu is less than or equal to
        defaultBanksFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllBanksByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmu is less than
        defaultBanksFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllBanksByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmu is greater than
        defaultBanksFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllBanksByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmd equals to
        defaultBanksFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllBanksByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmd in
        defaultBanksFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllBanksByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        // Get all the banksList where lmd is not null
        defaultBanksFiltering("lmd.specified=true", "lmd.specified=false");
    }

    private void defaultBanksFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBanksShouldBeFound(shouldBeFound);
        defaultBanksShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBanksShouldBeFound(String filter) throws Exception {
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banks.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())));

        // Check, that the count call also returns 1
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBanksShouldNotBeFound(String filter) throws Exception {
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBanks() throws Exception {
        // Get the banks
        restBanksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBanks() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banks
        Banks updatedBanks = banksRepository.findById(banks.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBanks are not directly saved in db
        em.detach(updatedBanks);
        updatedBanks.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION).lmu(UPDATED_LMU).lmd(UPDATED_LMD);

        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBanks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBanksToMatchAllProperties(updatedBanks);
    }

    @Test
    @Transactional
    void putNonExistingBanks() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banks.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(put(ENTITY_API_URL_ID, banks.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(banks)))
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanks() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banks.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(banks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanks() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banks.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(banks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBanksWithPatch() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banks using partial update
        Banks partialUpdatedBanks = new Banks();
        partialUpdatedBanks.setId(banks.getId());

        partialUpdatedBanks.description(UPDATED_DESCRIPTION).lmu(UPDATED_LMU);

        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanks.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBanksUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBanks, banks), getPersistedBanks(banks));
    }

    @Test
    @Transactional
    void fullUpdateBanksWithPatch() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the banks using partial update
        Banks partialUpdatedBanks = new Banks();
        partialUpdatedBanks.setId(banks.getId());

        partialUpdatedBanks.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION).lmu(UPDATED_LMU).lmd(UPDATED_LMD);

        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanks.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBanksUpdatableFieldsEquals(partialUpdatedBanks, getPersistedBanks(partialUpdatedBanks));
    }

    @Test
    @Transactional
    void patchNonExistingBanks() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banks.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banks.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(banks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanks() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banks.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(banks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanks() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        banks.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(banks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banks in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanks() throws Exception {
        // Initialize the database
        insertedBanks = banksRepository.saveAndFlush(banks);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the banks
        restBanksMockMvc
            .perform(delete(ENTITY_API_URL_ID, banks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return banksRepository.count();
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

    protected Banks getPersistedBanks(Banks banks) {
        return banksRepository.findById(banks.getId()).orElseThrow();
    }

    protected void assertPersistedBanksToMatchAllProperties(Banks expectedBanks) {
        assertBanksAllPropertiesEquals(expectedBanks, getPersistedBanks(expectedBanks));
    }

    protected void assertPersistedBanksToMatchUpdatableProperties(Banks expectedBanks) {
        assertBanksAllUpdatablePropertiesEquals(expectedBanks, getPersistedBanks(expectedBanks));
    }
}
