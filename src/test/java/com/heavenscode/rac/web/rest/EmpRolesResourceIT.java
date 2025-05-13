package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.EmpRolesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.EmpRoles;
import com.heavenscode.rac.repository.EmpRolesRepository;
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
 * Integration tests for the {@link EmpRolesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpRolesResourceIT {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emp-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{roleId}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpRolesRepository empRolesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpRolesMockMvc;

    private EmpRoles empRoles;

    private EmpRoles insertedEmpRoles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpRoles createEntity() {
        return new EmpRoles().roleName(DEFAULT_ROLE_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpRoles createUpdatedEntity() {
        return new EmpRoles().roleName(UPDATED_ROLE_NAME);
    }

    @BeforeEach
    public void initTest() {
        empRoles = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpRoles != null) {
            empRolesRepository.delete(insertedEmpRoles);
            insertedEmpRoles = null;
        }
    }

    @Test
    @Transactional
    void createEmpRoles() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmpRoles
        var returnedEmpRoles = om.readValue(
            restEmpRolesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empRoles)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmpRoles.class
        );

        // Validate the EmpRoles in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmpRolesUpdatableFieldsEquals(returnedEmpRoles, getPersistedEmpRoles(returnedEmpRoles));

        insertedEmpRoles = returnedEmpRoles;
    }

    @Test
    @Transactional
    void createEmpRolesWithExistingId() throws Exception {
        // Create the EmpRoles with an existing ID
        empRoles.setRoleId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empRoles)))
            .andExpect(status().isBadRequest());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpRoles() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        // Get all the empRolesList
        restEmpRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=roleId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(empRoles.getRoleId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)));
    }

    @Test
    @Transactional
    void getEmpRoles() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        // Get the empRoles
        restEmpRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, empRoles.getRoleId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.roleId").value(empRoles.getRoleId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME));
    }

    @Test
    @Transactional
    void getEmpRolesByIdFiltering() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        Integer id = empRoles.getRoleId();

        defaultEmpRolesFiltering("roleId.equals=" + id, "roleId.notEquals=" + id);

        defaultEmpRolesFiltering("roleId.greaterThanOrEqual=" + id, "roleId.greaterThan=" + id);

        defaultEmpRolesFiltering("roleId.lessThanOrEqual=" + id, "roleId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpRolesByRoleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        // Get all the empRolesList where roleName equals to
        defaultEmpRolesFiltering("roleName.equals=" + DEFAULT_ROLE_NAME, "roleName.equals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmpRolesByRoleNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        // Get all the empRolesList where roleName in
        defaultEmpRolesFiltering("roleName.in=" + DEFAULT_ROLE_NAME + "," + UPDATED_ROLE_NAME, "roleName.in=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmpRolesByRoleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        // Get all the empRolesList where roleName is not null
        defaultEmpRolesFiltering("roleName.specified=true", "roleName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpRolesByRoleNameContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        // Get all the empRolesList where roleName contains
        defaultEmpRolesFiltering("roleName.contains=" + DEFAULT_ROLE_NAME, "roleName.contains=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmpRolesByRoleNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        // Get all the empRolesList where roleName does not contain
        defaultEmpRolesFiltering("roleName.doesNotContain=" + UPDATED_ROLE_NAME, "roleName.doesNotContain=" + DEFAULT_ROLE_NAME);
    }

    private void defaultEmpRolesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmpRolesShouldBeFound(shouldBeFound);
        defaultEmpRolesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpRolesShouldBeFound(String filter) throws Exception {
        restEmpRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=roleId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(empRoles.getRoleId())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)));

        // Check, that the count call also returns 1
        restEmpRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=roleId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpRolesShouldNotBeFound(String filter) throws Exception {
        restEmpRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=roleId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpRolesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=roleId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpRoles() throws Exception {
        // Get the empRoles
        restEmpRolesMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpRoles() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empRoles
        EmpRoles updatedEmpRoles = empRolesRepository.findById(empRoles.getRoleId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpRoles are not directly saved in db
        em.detach(updatedEmpRoles);
        updatedEmpRoles.roleName(UPDATED_ROLE_NAME);

        restEmpRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpRoles.getRoleId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmpRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpRolesToMatchAllProperties(updatedEmpRoles);
    }

    @Test
    @Transactional
    void putNonExistingEmpRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoles.setRoleId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empRoles.getRoleId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoles.setRoleId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoles.setRoleId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRolesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empRoles)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpRolesWithPatch() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empRoles using partial update
        EmpRoles partialUpdatedEmpRoles = new EmpRoles();
        partialUpdatedEmpRoles.setRoleId(empRoles.getRoleId());

        restEmpRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpRoles.getRoleId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmpRoles in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpRolesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmpRoles, empRoles), getPersistedEmpRoles(empRoles));
    }

    @Test
    @Transactional
    void fullUpdateEmpRolesWithPatch() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empRoles using partial update
        EmpRoles partialUpdatedEmpRoles = new EmpRoles();
        partialUpdatedEmpRoles.setRoleId(empRoles.getRoleId());

        partialUpdatedEmpRoles.roleName(UPDATED_ROLE_NAME);

        restEmpRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpRoles.getRoleId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpRoles))
            )
            .andExpect(status().isOk());

        // Validate the EmpRoles in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpRolesUpdatableFieldsEquals(partialUpdatedEmpRoles, getPersistedEmpRoles(partialUpdatedEmpRoles));
    }

    @Test
    @Transactional
    void patchNonExistingEmpRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoles.setRoleId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empRoles.getRoleId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoles.setRoleId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empRoles))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empRoles.setRoleId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpRolesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empRoles)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpRoles() throws Exception {
        // Initialize the database
        insertedEmpRoles = empRolesRepository.saveAndFlush(empRoles);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empRoles
        restEmpRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, empRoles.getRoleId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empRolesRepository.count();
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

    protected EmpRoles getPersistedEmpRoles(EmpRoles empRoles) {
        return empRolesRepository.findById(empRoles.getRoleId()).orElseThrow();
    }

    protected void assertPersistedEmpRolesToMatchAllProperties(EmpRoles expectedEmpRoles) {
        assertEmpRolesAllPropertiesEquals(expectedEmpRoles, getPersistedEmpRoles(expectedEmpRoles));
    }

    protected void assertPersistedEmpRolesToMatchUpdatableProperties(EmpRoles expectedEmpRoles) {
        assertEmpRolesAllUpdatablePropertiesEquals(expectedEmpRoles, getPersistedEmpRoles(expectedEmpRoles));
    }
}
