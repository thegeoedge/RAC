package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.EmpRoleFunctionPermissionAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.EmpRoleFunctionPermission;
import com.heavenscode.rac.repository.EmpRoleFunctionPermissionRepository;
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
 * Integration tests for the {@link EmpRoleFunctionPermissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpRoleFunctionPermissionResourceIT {

    private static final Integer DEFAULT_ROLE_ID = 1;
    private static final Integer UPDATED_ROLE_ID = 2;
    private static final Integer SMALLER_ROLE_ID = 1 - 1;

    private static final Integer DEFAULT_FUNCTION_ID = 1;
    private static final Integer UPDATED_FUNCTION_ID = 2;
    private static final Integer SMALLER_FUNCTION_ID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/emp-role-function-permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpRoleFunctionPermissionRepository empRoleFunctionPermissionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpRoleFunctionPermissionMockMvc;

    private EmpRoleFunctionPermission empRoleFunctionPermission;

    private EmpRoleFunctionPermission insertedEmpRoleFunctionPermission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpRoleFunctionPermission createEntity() {
        return new EmpRoleFunctionPermission().roleId(DEFAULT_ROLE_ID).functionId(DEFAULT_FUNCTION_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpRoleFunctionPermission createUpdatedEntity() {
        return new EmpRoleFunctionPermission().roleId(UPDATED_ROLE_ID).functionId(UPDATED_FUNCTION_ID);
    }

    @BeforeEach
    public void initTest() {
        empRoleFunctionPermission = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpRoleFunctionPermission != null) {
            empRoleFunctionPermissionRepository.delete(insertedEmpRoleFunctionPermission);
            insertedEmpRoleFunctionPermission = null;
        }
    }

    @Test
    @Transactional
    void createEmpRoleFunctionPermission() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmpRoleFunctionPermission
        var returnedEmpRoleFunctionPermission = om.readValue(
            restEmpRoleFunctionPermissionMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empRoleFunctionPermission))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmpRoleFunctionPermission.class
        );

        // Validate the EmpRoleFunctionPermission in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmpRoleFunctionPermissionUpdatableFieldsEquals(
            returnedEmpRoleFunctionPermission,
            getPersistedEmpRoleFunctionPermission(returnedEmpRoleFunctionPermission)
        );

        insertedEmpRoleFunctionPermission = returnedEmpRoleFunctionPermission;
    }

    @Test
    @Transactional
    void createEmpRoleFunctionPermissionWithExistingId() throws Exception {
        // Create the EmpRoleFunctionPermission with an existing ID
        empRoleFunctionPermission.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpRoleFunctionPermissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empRoleFunctionPermission)))
            .andExpect(status().isBadRequest());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissions() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList
        restEmpRoleFunctionPermissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empRoleFunctionPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].functionId").value(hasItem(DEFAULT_FUNCTION_ID)));
    }

    @Test
    @Transactional
    void getEmpRoleFunctionPermission() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get the empRoleFunctionPermission
        restEmpRoleFunctionPermissionMockMvc
            .perform(get(ENTITY_API_URL_ID, empRoleFunctionPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empRoleFunctionPermission.getId().intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.functionId").value(DEFAULT_FUNCTION_ID));
    }

    @Test
    @Transactional
    void getEmpRoleFunctionPermissionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        Integer id = empRoleFunctionPermission.getId();

        defaultEmpRoleFunctionPermissionFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEmpRoleFunctionPermissionFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEmpRoleFunctionPermissionFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByRoleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where roleId equals to
        defaultEmpRoleFunctionPermissionFiltering("roleId.equals=" + DEFAULT_ROLE_ID, "roleId.equals=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByRoleIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where roleId in
        defaultEmpRoleFunctionPermissionFiltering("roleId.in=" + DEFAULT_ROLE_ID + "," + UPDATED_ROLE_ID, "roleId.in=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByRoleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where roleId is not null
        defaultEmpRoleFunctionPermissionFiltering("roleId.specified=true", "roleId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByRoleIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where roleId is greater than or equal to
        defaultEmpRoleFunctionPermissionFiltering(
            "roleId.greaterThanOrEqual=" + DEFAULT_ROLE_ID,
            "roleId.greaterThanOrEqual=" + UPDATED_ROLE_ID
        );
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByRoleIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where roleId is less than or equal to
        defaultEmpRoleFunctionPermissionFiltering("roleId.lessThanOrEqual=" + DEFAULT_ROLE_ID, "roleId.lessThanOrEqual=" + SMALLER_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByRoleIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where roleId is less than
        defaultEmpRoleFunctionPermissionFiltering("roleId.lessThan=" + UPDATED_ROLE_ID, "roleId.lessThan=" + DEFAULT_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByRoleIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where roleId is greater than
        defaultEmpRoleFunctionPermissionFiltering("roleId.greaterThan=" + SMALLER_ROLE_ID, "roleId.greaterThan=" + DEFAULT_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByFunctionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where functionId equals to
        defaultEmpRoleFunctionPermissionFiltering("functionId.equals=" + DEFAULT_FUNCTION_ID, "functionId.equals=" + UPDATED_FUNCTION_ID);
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByFunctionIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where functionId in
        defaultEmpRoleFunctionPermissionFiltering(
            "functionId.in=" + DEFAULT_FUNCTION_ID + "," + UPDATED_FUNCTION_ID,
            "functionId.in=" + UPDATED_FUNCTION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByFunctionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where functionId is not null
        defaultEmpRoleFunctionPermissionFiltering("functionId.specified=true", "functionId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByFunctionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where functionId is greater than or equal to
        defaultEmpRoleFunctionPermissionFiltering(
            "functionId.greaterThanOrEqual=" + DEFAULT_FUNCTION_ID,
            "functionId.greaterThanOrEqual=" + UPDATED_FUNCTION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByFunctionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where functionId is less than or equal to
        defaultEmpRoleFunctionPermissionFiltering(
            "functionId.lessThanOrEqual=" + DEFAULT_FUNCTION_ID,
            "functionId.lessThanOrEqual=" + SMALLER_FUNCTION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByFunctionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where functionId is less than
        defaultEmpRoleFunctionPermissionFiltering(
            "functionId.lessThan=" + UPDATED_FUNCTION_ID,
            "functionId.lessThan=" + DEFAULT_FUNCTION_ID
        );
    }

    @Test
    @Transactional
    void getAllEmpRoleFunctionPermissionsByFunctionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        // Get all the empRoleFunctionPermissionList where functionId is greater than
        defaultEmpRoleFunctionPermissionFiltering(
            "functionId.greaterThan=" + SMALLER_FUNCTION_ID,
            "functionId.greaterThan=" + DEFAULT_FUNCTION_ID
        );
    }

    private void defaultEmpRoleFunctionPermissionFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmpRoleFunctionPermissionShouldBeFound(shouldBeFound);
        defaultEmpRoleFunctionPermissionShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpRoleFunctionPermissionShouldBeFound(String filter) throws Exception {
        restEmpRoleFunctionPermissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empRoleFunctionPermission.getId())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].functionId").value(hasItem(DEFAULT_FUNCTION_ID)));

        // Check, that the count call also returns 1
        restEmpRoleFunctionPermissionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpRoleFunctionPermissionShouldNotBeFound(String filter) throws Exception {
        restEmpRoleFunctionPermissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpRoleFunctionPermissionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpRoleFunctionPermission() throws Exception {
        // Get the empRoleFunctionPermission
        restEmpRoleFunctionPermissionMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpRoleFunctionPermission() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empRoleFunctionPermission
        EmpRoleFunctionPermission updatedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository
            .findById(empRoleFunctionPermission.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedEmpRoleFunctionPermission are not directly saved in db
        em.detach(updatedEmpRoleFunctionPermission);
        updatedEmpRoleFunctionPermission.roleId(UPDATED_ROLE_ID).functionId(UPDATED_FUNCTION_ID);

        restEmpRoleFunctionPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpRoleFunctionPermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmpRoleFunctionPermission))
            )
            .andExpect(status().isOk());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpRoleFunctionPermissionToMatchAllProperties(updatedEmpRoleFunctionPermission);
    }

    @Test
    @Transactional
    void putNonExistingEmpRoleFunctionPermission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoleFunctionPermission.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpRoleFunctionPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empRoleFunctionPermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empRoleFunctionPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpRoleFunctionPermission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoleFunctionPermission.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRoleFunctionPermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empRoleFunctionPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpRoleFunctionPermission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoleFunctionPermission.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRoleFunctionPermissionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empRoleFunctionPermission)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpRoleFunctionPermissionWithPatch() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empRoleFunctionPermission using partial update
        EmpRoleFunctionPermission partialUpdatedEmpRoleFunctionPermission = new EmpRoleFunctionPermission();
        partialUpdatedEmpRoleFunctionPermission.setId(empRoleFunctionPermission.getId());

        partialUpdatedEmpRoleFunctionPermission.roleId(UPDATED_ROLE_ID);

        restEmpRoleFunctionPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpRoleFunctionPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpRoleFunctionPermission))
            )
            .andExpect(status().isOk());

        // Validate the EmpRoleFunctionPermission in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpRoleFunctionPermissionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmpRoleFunctionPermission, empRoleFunctionPermission),
            getPersistedEmpRoleFunctionPermission(empRoleFunctionPermission)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmpRoleFunctionPermissionWithPatch() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empRoleFunctionPermission using partial update
        EmpRoleFunctionPermission partialUpdatedEmpRoleFunctionPermission = new EmpRoleFunctionPermission();
        partialUpdatedEmpRoleFunctionPermission.setId(empRoleFunctionPermission.getId());

        partialUpdatedEmpRoleFunctionPermission.roleId(UPDATED_ROLE_ID).functionId(UPDATED_FUNCTION_ID);

        restEmpRoleFunctionPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpRoleFunctionPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpRoleFunctionPermission))
            )
            .andExpect(status().isOk());

        // Validate the EmpRoleFunctionPermission in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpRoleFunctionPermissionUpdatableFieldsEquals(
            partialUpdatedEmpRoleFunctionPermission,
            getPersistedEmpRoleFunctionPermission(partialUpdatedEmpRoleFunctionPermission)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEmpRoleFunctionPermission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoleFunctionPermission.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpRoleFunctionPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empRoleFunctionPermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empRoleFunctionPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpRoleFunctionPermission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoleFunctionPermission.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRoleFunctionPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empRoleFunctionPermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpRoleFunctionPermission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoleFunctionPermission.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRoleFunctionPermissionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empRoleFunctionPermission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpRoleFunctionPermission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpRoleFunctionPermission() throws Exception {
        // Initialize the database
        insertedEmpRoleFunctionPermission = empRoleFunctionPermissionRepository.saveAndFlush(empRoleFunctionPermission);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empRoleFunctionPermission
        restEmpRoleFunctionPermissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, empRoleFunctionPermission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empRoleFunctionPermissionRepository.count();
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

    protected EmpRoleFunctionPermission getPersistedEmpRoleFunctionPermission(EmpRoleFunctionPermission empRoleFunctionPermission) {
        return empRoleFunctionPermissionRepository.findById(empRoleFunctionPermission.getId()).orElseThrow();
    }

    protected void assertPersistedEmpRoleFunctionPermissionToMatchAllProperties(
        EmpRoleFunctionPermission expectedEmpRoleFunctionPermission
    ) {
        assertEmpRoleFunctionPermissionAllPropertiesEquals(
            expectedEmpRoleFunctionPermission,
            getPersistedEmpRoleFunctionPermission(expectedEmpRoleFunctionPermission)
        );
    }

    protected void assertPersistedEmpRoleFunctionPermissionToMatchUpdatableProperties(
        EmpRoleFunctionPermission expectedEmpRoleFunctionPermission
    ) {
        assertEmpRoleFunctionPermissionAllUpdatablePropertiesEquals(
            expectedEmpRoleFunctionPermission,
            getPersistedEmpRoleFunctionPermission(expectedEmpRoleFunctionPermission)
        );
    }
}
