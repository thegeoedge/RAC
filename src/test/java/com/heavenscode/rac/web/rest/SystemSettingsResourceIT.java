package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SystemSettingsAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SystemSettings;
import com.heavenscode.rac.repository.SystemSettingsRepository;
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
 * Integration tests for the {@link SystemSettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SystemSettingsResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NEXT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_NEXT_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/system-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemSettingsMockMvc;

    private SystemSettings systemSettings;

    private SystemSettings insertedSystemSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSettings createEntity() {
        return new SystemSettings()
            .key(DEFAULT_KEY)
            .lastValue(DEFAULT_LAST_VALUE)
            .nextValue(DEFAULT_NEXT_VALUE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSettings createUpdatedEntity() {
        return new SystemSettings()
            .key(UPDATED_KEY)
            .lastValue(UPDATED_LAST_VALUE)
            .nextValue(UPDATED_NEXT_VALUE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);
    }

    @BeforeEach
    public void initTest() {
        systemSettings = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSystemSettings != null) {
            systemSettingsRepository.delete(insertedSystemSettings);
            insertedSystemSettings = null;
        }
    }

    @Test
    @Transactional
    void createSystemSettings() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SystemSettings
        var returnedSystemSettings = om.readValue(
            restSystemSettingsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemSettings)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SystemSettings.class
        );

        // Validate the SystemSettings in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSystemSettingsUpdatableFieldsEquals(returnedSystemSettings, getPersistedSystemSettings(returnedSystemSettings));

        insertedSystemSettings = returnedSystemSettings;
    }

    @Test
    @Transactional
    void createSystemSettingsWithExistingId() throws Exception {
        // Create the SystemSettings with an existing ID
        systemSettings.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemSettingsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemSettings)))
            .andExpect(status().isBadRequest());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSystemSettings() throws Exception {
        // Initialize the database
        insertedSystemSettings = systemSettingsRepository.saveAndFlush(systemSettings);

        // Get all the systemSettingsList
        restSystemSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].lastValue").value(hasItem(DEFAULT_LAST_VALUE)))
            .andExpect(jsonPath("$.[*].nextValue").value(hasItem(DEFAULT_NEXT_VALUE)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())));
    }

    @Test
    @Transactional
    void getSystemSettings() throws Exception {
        // Initialize the database
        insertedSystemSettings = systemSettingsRepository.saveAndFlush(systemSettings);

        // Get the systemSettings
        restSystemSettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, systemSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemSettings.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.lastValue").value(DEFAULT_LAST_VALUE))
            .andExpect(jsonPath("$.nextValue").value(DEFAULT_NEXT_VALUE))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSystemSettings() throws Exception {
        // Get the systemSettings
        restSystemSettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSystemSettings() throws Exception {
        // Initialize the database
        insertedSystemSettings = systemSettingsRepository.saveAndFlush(systemSettings);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the systemSettings
        SystemSettings updatedSystemSettings = systemSettingsRepository.findById(systemSettings.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSystemSettings are not directly saved in db
        em.detach(updatedSystemSettings);
        updatedSystemSettings
            .key(UPDATED_KEY)
            .lastValue(UPDATED_LAST_VALUE)
            .nextValue(UPDATED_NEXT_VALUE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);

        restSystemSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSystemSettings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSystemSettings))
            )
            .andExpect(status().isOk());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSystemSettingsToMatchAllProperties(updatedSystemSettings);
    }

    @Test
    @Transactional
    void putNonExistingSystemSettings() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemSettings.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, systemSettings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(systemSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSystemSettings() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemSettings.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(systemSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSystemSettings() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemSettings.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSettingsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(systemSettings)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSystemSettingsWithPatch() throws Exception {
        // Initialize the database
        insertedSystemSettings = systemSettingsRepository.saveAndFlush(systemSettings);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the systemSettings using partial update
        SystemSettings partialUpdatedSystemSettings = new SystemSettings();
        partialUpdatedSystemSettings.setId(systemSettings.getId());

        partialUpdatedSystemSettings.lastValue(UPDATED_LAST_VALUE).nextValue(UPDATED_NEXT_VALUE).lmd(UPDATED_LMD);

        restSystemSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSystemSettings))
            )
            .andExpect(status().isOk());

        // Validate the SystemSettings in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSystemSettingsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSystemSettings, systemSettings),
            getPersistedSystemSettings(systemSettings)
        );
    }

    @Test
    @Transactional
    void fullUpdateSystemSettingsWithPatch() throws Exception {
        // Initialize the database
        insertedSystemSettings = systemSettingsRepository.saveAndFlush(systemSettings);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the systemSettings using partial update
        SystemSettings partialUpdatedSystemSettings = new SystemSettings();
        partialUpdatedSystemSettings.setId(systemSettings.getId());

        partialUpdatedSystemSettings
            .key(UPDATED_KEY)
            .lastValue(UPDATED_LAST_VALUE)
            .nextValue(UPDATED_NEXT_VALUE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD);

        restSystemSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSystemSettings))
            )
            .andExpect(status().isOk());

        // Validate the SystemSettings in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSystemSettingsUpdatableFieldsEquals(partialUpdatedSystemSettings, getPersistedSystemSettings(partialUpdatedSystemSettings));
    }

    @Test
    @Transactional
    void patchNonExistingSystemSettings() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemSettings.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, systemSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(systemSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSystemSettings() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemSettings.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(systemSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSystemSettings() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        systemSettings.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSettingsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(systemSettings)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemSettings in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSystemSettings() throws Exception {
        // Initialize the database
        insertedSystemSettings = systemSettingsRepository.saveAndFlush(systemSettings);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the systemSettings
        restSystemSettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, systemSettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return systemSettingsRepository.count();
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

    protected SystemSettings getPersistedSystemSettings(SystemSettings systemSettings) {
        return systemSettingsRepository.findById(systemSettings.getId()).orElseThrow();
    }

    protected void assertPersistedSystemSettingsToMatchAllProperties(SystemSettings expectedSystemSettings) {
        assertSystemSettingsAllPropertiesEquals(expectedSystemSettings, getPersistedSystemSettings(expectedSystemSettings));
    }

    protected void assertPersistedSystemSettingsToMatchUpdatableProperties(SystemSettings expectedSystemSettings) {
        assertSystemSettingsAllUpdatablePropertiesEquals(expectedSystemSettings, getPersistedSystemSettings(expectedSystemSettings));
    }
}
