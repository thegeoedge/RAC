package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.EmpRoles;
import com.heavenscode.rac.repository.EmpRolesRepository;
import com.heavenscode.rac.service.EmpRolesQueryService;
import com.heavenscode.rac.service.EmpRolesService;
import com.heavenscode.rac.service.criteria.EmpRolesCriteria;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.EmpRoles}.
 */
@RestController
@RequestMapping("/api/emp-roles")
public class EmpRolesResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmpRolesResource.class);

    private static final String ENTITY_NAME = "empRoles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpRolesService empRolesService;

    private final EmpRolesRepository empRolesRepository;

    private final EmpRolesQueryService empRolesQueryService;

    public EmpRolesResource(
        EmpRolesService empRolesService,
        EmpRolesRepository empRolesRepository,
        EmpRolesQueryService empRolesQueryService
    ) {
        this.empRolesService = empRolesService;
        this.empRolesRepository = empRolesRepository;
        this.empRolesQueryService = empRolesQueryService;
    }

    /**
     * {@code POST  /emp-roles} : Create a new empRoles.
     *
     * @param empRoles the empRoles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empRoles, or with status {@code 400 (Bad Request)} if the empRoles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmpRoles> createEmpRoles(@Valid @RequestBody EmpRoles empRoles) throws URISyntaxException {
        LOG.debug("REST request to save EmpRoles : {}", empRoles);

        empRoles = empRolesService.save(empRoles);
        return ResponseEntity.created(new URI("/api/emp-roles/" + empRoles.getRoleId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, empRoles.getRoleId().toString()))
            .body(empRoles);
    }

    /**
     * {@code PUT  /emp-roles/:roleId} : Updates an existing empRoles.
     *
     * @param roleId the id of the empRoles to save.
     * @param empRoles the empRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empRoles,
     * or with status {@code 400 (Bad Request)} if the empRoles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{roleId}")
    public ResponseEntity<EmpRoles> updateEmpRoles(
        @PathVariable(value = "roleId", required = false) final Integer roleId,
        @Valid @RequestBody EmpRoles empRoles
    ) throws URISyntaxException {
        LOG.debug("REST request to update EmpRoles : {}, {}", roleId, empRoles);
        if (empRoles.getRoleId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(roleId, empRoles.getRoleId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empRolesRepository.existsById(roleId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empRoles = empRolesService.update(empRoles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empRoles.getRoleId().toString()))
            .body(empRoles);
    }

    /**
     * {@code PATCH  /emp-roles/:roleId} : Partial updates given fields of an existing empRoles, field will ignore if it is null
     *
     * @param roleId the id of the empRoles to save.
     * @param empRoles the empRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empRoles,
     * or with status {@code 400 (Bad Request)} if the empRoles is not valid,
     * or with status {@code 404 (Not Found)} if the empRoles is not found,
     * or with status {@code 500 (Internal Server Error)} if the empRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{roleId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpRoles> partialUpdateEmpRoles(
        @PathVariable(value = "roleId", required = false) final Integer roleId,
        @NotNull @RequestBody EmpRoles empRoles
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EmpRoles partially : {}, {}", roleId, empRoles);
        if (empRoles.getRoleId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(roleId, empRoles.getRoleId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empRolesRepository.existsById(roleId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpRoles> result = empRolesService.partialUpdate(empRoles);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empRoles.getRoleId().toString())
        );
    }

    /**
     * {@code GET  /emp-roles} : get all the empRoles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empRoles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EmpRoles>> getAllEmpRoles(
        EmpRolesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get EmpRoles by criteria: {}", criteria);

        Page<EmpRoles> page = empRolesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emp-roles/count} : count all the empRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEmpRoles(EmpRolesCriteria criteria) {
        LOG.debug("REST request to count EmpRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(empRolesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emp-roles/:id} : get the "id" empRoles.
     *
     * @param id the id of the empRoles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empRoles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpRoles> getEmpRoles(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get EmpRoles : {}", id);
        Optional<EmpRoles> empRoles = empRolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empRoles);
    }

    /**
     * {@code DELETE  /emp-roles/:id} : delete the "id" empRoles.
     *
     * @param id the id of the empRoles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpRoles(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete EmpRoles : {}", id);
        empRolesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
