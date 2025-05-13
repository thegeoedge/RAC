package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.EmpFunctionsAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.EmpFunctions;
import com.heavenscode.rac.repository.EmpFunctionsRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
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
 * Integration tests for the {@link EmpFunctionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpFunctionsResourceIT {

    private static final String DEFAULT_FUNCTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MODULE_ID = 1;
    private static final Integer UPDATED_MODULE_ID = 2;
    private static final Integer SMALLER_MODULE_ID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/emp-functions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{functionId}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpFunctionsRepository empFunctionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpFunctionsMockMvc;

    private EmpFunctions empFunctions;

    private EmpFunctions insertedEmpFunctions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpFunctions createEntity() {
        return new EmpFunctions().functionName(DEFAULT_FUNCTION_NAME).moduleId(DEFAULT_MODULE_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpFunctions createUpdatedEntity() {
        return new EmpFunctions().functionName(UPDATED_FUNCTION_NAME).moduleId(UPDATED_MODULE_ID);
    }

    @BeforeEach
    public void initTest() {
        empFunctions = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpFunctions != null) {
            empFunctionsRepository.delete(insertedEmpFunctions);
            insertedEmpFunctions = null;
        }
    }

    @Test
    @Transactional
    void createEmpFunctions() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmpFunctions
        var returnedEmpFunctions = om.readValue(
            restEmpFunctionsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empFunctions)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmpFunctions.class
        );

        // Validate the EmpFunctions in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmpFunctionsUpdatableFieldsEquals(returnedEmpFunctions, getPersistedEmpFunctions(returnedEmpFunctions));

        insertedEmpFunctions = returnedEmpFunctions;
    }

    @Test
    @Transactional
    void createEmpFunctionsWithExistingId() throws Exception {
        // Create the EmpFunctions with an existing ID
        empFunctions.setFunctionId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpFunctionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empFunctions)))
            .andExpect(status().isBadRequest());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpFunctions() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList
        restEmpFunctionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=functionId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].functionId").value(hasItem(empFunctions.getFunctionId().intValue())))
            .andExpect(jsonPath("$.[*].functionName").value(hasItem(DEFAULT_FUNCTION_NAME)))
            .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID)));
    }

    @Test
    @Transactional
    void getEmpFunctions() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get the empFunctions
        restEmpFunctionsMockMvc
            .perform(get(ENTITY_API_URL_ID, empFunctions.getFunctionId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.functionId").value(empFunctions.getFunctionId().intValue()))
            .andExpect(jsonPath("$.functionName").value(DEFAULT_FUNCTION_NAME))
            .andExpect(jsonPath("$.moduleId").value(DEFAULT_MODULE_ID));
    }

    @Test
    @Transactional
    void getEmpFunctionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        Integer id = empFunctions.getFunctionId();

        defaultEmpFunctionsFiltering("functionId.equals=" + id, "functionId.notEquals=" + id);

        defaultEmpFunctionsFiltering("functionId.greaterThanOrEqual=" + id, "functionId.greaterThan=" + id);

        defaultEmpFunctionsFiltering("functionId.lessThanOrEqual=" + id, "functionId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByFunctionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where functionName equals to
        defaultEmpFunctionsFiltering("functionName.equals=" + DEFAULT_FUNCTION_NAME, "functionName.equals=" + UPDATED_FUNCTION_NAME);
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByFunctionNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where functionName in
        defaultEmpFunctionsFiltering(
            "functionName.in=" + DEFAULT_FUNCTION_NAME + "," + UPDATED_FUNCTION_NAME,
            "functionName.in=" + UPDATED_FUNCTION_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByFunctionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where functionName is not null
        defaultEmpFunctionsFiltering("functionName.specified=true", "functionName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByFunctionNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where functionName contains
        defaultEmpFunctionsFiltering("functionName.contains=" + DEFAULT_FUNCTION_NAME, "functionName.contains=" + UPDATED_FUNCTION_NAME);
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByFunctionNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where functionName does not contain
        defaultEmpFunctionsFiltering(
            "functionName.doesNotContain=" + UPDATED_FUNCTION_NAME,
            "functionName.doesNotContain=" + DEFAULT_FUNCTION_NAME
        );
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByModuleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where moduleId equals to
        defaultEmpFunctionsFiltering("moduleId.equals=" + DEFAULT_MODULE_ID, "moduleId.equals=" + UPDATED_MODULE_ID);
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByModuleIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where moduleId in
        defaultEmpFunctionsFiltering("moduleId.in=" + DEFAULT_MODULE_ID + "," + UPDATED_MODULE_ID, "moduleId.in=" + UPDATED_MODULE_ID);
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByModuleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where moduleId is not null
        defaultEmpFunctionsFiltering("moduleId.specified=true", "moduleId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByModuleIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where moduleId is greater than or equal to
        defaultEmpFunctionsFiltering(
            "moduleId.greaterThanOrEqual=" + DEFAULT_MODULE_ID,
            "moduleId.greaterThanOrEqual=" + UPDATED_MODULE_ID
        );
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByModuleIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where moduleId is less than or equal to
        defaultEmpFunctionsFiltering("moduleId.lessThanOrEqual=" + DEFAULT_MODULE_ID, "moduleId.lessThanOrEqual=" + SMALLER_MODULE_ID);
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByModuleIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where moduleId is less than
        defaultEmpFunctionsFiltering("moduleId.lessThan=" + UPDATED_MODULE_ID, "moduleId.lessThan=" + DEFAULT_MODULE_ID);
    }

    @Test
    @Transactional
    void getAllEmpFunctionsByModuleIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        // Get all the empFunctionsList where moduleId is greater than
        defaultEmpFunctionsFiltering("moduleId.greaterThan=" + SMALLER_MODULE_ID, "moduleId.greaterThan=" + DEFAULT_MODULE_ID);
    }

    private void defaultEmpFunctionsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmpFunctionsShouldBeFound(shouldBeFound);
        defaultEmpFunctionsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpFunctionsShouldBeFound(String filter) throws Exception {
        restEmpFunctionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=functionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].functionId").value(hasItem(empFunctions.getFunctionId())))
            .andExpect(jsonPath("$.[*].functionName").value(hasItem(DEFAULT_FUNCTION_NAME)))
            .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID)));

        // Check, that the count call also returns 1
        restEmpFunctionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=functionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpFunctionsShouldNotBeFound(String filter) throws Exception {
        restEmpFunctionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=functionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpFunctionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=functionId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpFunctions() throws Exception {
        // Get the empFunctions
        restEmpFunctionsMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpFunctions() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empFunctions
        EmpFunctions updatedEmpFunctions = empFunctionsRepository.findById(empFunctions.getFunctionId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpFunctions are not directly saved in db
        em.detach(updatedEmpFunctions);
        updatedEmpFunctions.functionName(UPDATED_FUNCTION_NAME).moduleId(UPDATED_MODULE_ID);

        restEmpFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpFunctions.getFunctionId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmpFunctions))
            )
            .andExpect(status().isOk());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpFunctionsToMatchAllProperties(updatedEmpFunctions);
    }

    @Test
    @Transactional
    void putNonExistingEmpFunctions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empFunctions.setFunctionId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empFunctions.getFunctionId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empFunctions))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpFunctions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empFunctions.setFunctionId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empFunctions))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpFunctions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empFunctions.setFunctionId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpFunctionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empFunctions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpFunctionsWithPatch() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empFunctions using partial update
        EmpFunctions partialUpdatedEmpFunctions = new EmpFunctions();
        partialUpdatedEmpFunctions.setFunctionId(empFunctions.getFunctionId());

        partialUpdatedEmpFunctions.functionName(UPDATED_FUNCTION_NAME).moduleId(UPDATED_MODULE_ID);

        restEmpFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpFunctions.getFunctionId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpFunctions))
            )
            .andExpect(status().isOk());

        // Validate the EmpFunctions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpFunctionsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmpFunctions, empFunctions),
            getPersistedEmpFunctions(empFunctions)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmpFunctionsWithPatch() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empFunctions using partial update
        EmpFunctions partialUpdatedEmpFunctions = new EmpFunctions();
        partialUpdatedEmpFunctions.setFunctionId(empFunctions.getFunctionId());

        partialUpdatedEmpFunctions.functionName(UPDATED_FUNCTION_NAME).moduleId(UPDATED_MODULE_ID);

        restEmpFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpFunctions.getFunctionId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpFunctions))
            )
            .andExpect(status().isOk());

        // Validate the EmpFunctions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpFunctionsUpdatableFieldsEquals(partialUpdatedEmpFunctions, getPersistedEmpFunctions(partialUpdatedEmpFunctions));
    }

    @Test
    @Transactional
    void patchNonExistingEmpFunctions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empFunctions.setFunctionId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empFunctions.getFunctionId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empFunctions))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpFunctions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empFunctions.setFunctionId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empFunctions))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpFunctions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empFunctions.setFunctionId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpFunctionsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empFunctions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpFunctions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpFunctions() throws Exception {
        // Initialize the database
        insertedEmpFunctions = empFunctionsRepository.saveAndFlush(empFunctions);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empFunctions
        restEmpFunctionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, empFunctions.getFunctionId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empFunctionsRepository.count();
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

    protected EmpFunctions getPersistedEmpFunctions(EmpFunctions empFunctions) {
        return empFunctionsRepository.findById(empFunctions.getFunctionId()).orElseThrow();
    }

    protected void assertPersistedEmpFunctionsToMatchAllProperties(EmpFunctions expectedEmpFunctions) {
        assertEmpFunctionsAllPropertiesEquals(expectedEmpFunctions, getPersistedEmpFunctions(expectedEmpFunctions));
    }

    protected void assertPersistedEmpFunctionsToMatchUpdatableProperties(EmpFunctions expectedEmpFunctions) {
        assertEmpFunctionsAllUpdatablePropertiesEquals(expectedEmpFunctions, getPersistedEmpFunctions(expectedEmpFunctions));
    }
}
