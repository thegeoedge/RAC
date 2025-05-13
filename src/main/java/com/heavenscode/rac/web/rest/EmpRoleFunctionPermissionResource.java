package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.EmpRoleFunctionPermission;
import com.heavenscode.rac.repository.EmpRoleFunctionPermissionRepository;
import com.heavenscode.rac.service.EmpRoleFunctionPermissionQueryService;
import com.heavenscode.rac.service.EmpRoleFunctionPermissionService;
import com.heavenscode.rac.service.criteria.EmpRoleFunctionPermissionCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.EmpRoleFunctionPermission}.
 */
@RestController
@RequestMapping("/api/emp-role-function-permissions")
public class EmpRoleFunctionPermissionResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmpRoleFunctionPermissionResource.class);

    private static final String ENTITY_NAME = "empRoleFunctionPermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpRoleFunctionPermissionService empRoleFunctionPermissionService;

    private final EmpRoleFunctionPermissionRepository empRoleFunctionPermissionRepository;

    private final EmpRoleFunctionPermissionQueryService empRoleFunctionPermissionQueryService;

    public EmpRoleFunctionPermissionResource(
        EmpRoleFunctionPermissionService empRoleFunctionPermissionService,
        EmpRoleFunctionPermissionRepository empRoleFunctionPermissionRepository,
        EmpRoleFunctionPermissionQueryService empRoleFunctionPermissionQueryService
    ) {
        this.empRoleFunctionPermissionService = empRoleFunctionPermissionService;
        this.empRoleFunctionPermissionRepository = empRoleFunctionPermissionRepository;
        this.empRoleFunctionPermissionQueryService = empRoleFunctionPermissionQueryService;
    }

    /**
     * {@code POST  /emp-role-function-permissions} : Create a new empRoleFunctionPermission.
     *
     * @param empRoleFunctionPermission the empRoleFunctionPermission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empRoleFunctionPermission, or with status {@code 400 (Bad Request)} if the empRoleFunctionPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmpRoleFunctionPermission> createEmpRoleFunctionPermission(
        @Valid @RequestBody EmpRoleFunctionPermission empRoleFunctionPermission
    ) throws URISyntaxException {
        LOG.debug("REST request to save EmpRoleFunctionPermission : {}", empRoleFunctionPermission);
        if (empRoleFunctionPermission.getId() != null) {
            throw new BadRequestAlertException("A new empRoleFunctionPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        empRoleFunctionPermission = empRoleFunctionPermissionService.save(empRoleFunctionPermission);
        return ResponseEntity.created(new URI("/api/emp-role-function-permissions/" + empRoleFunctionPermission.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, empRoleFunctionPermission.getId().toString())
            )
            .body(empRoleFunctionPermission);
    }

    /**
     * {@code PUT  /emp-role-function-permissions/:id} : Updates an existing empRoleFunctionPermission.
     *
     * @param id the id of the empRoleFunctionPermission to save.
     * @param empRoleFunctionPermission the empRoleFunctionPermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empRoleFunctionPermission,
     * or with status {@code 400 (Bad Request)} if the empRoleFunctionPermission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empRoleFunctionPermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpRoleFunctionPermission> updateEmpRoleFunctionPermission(
        @PathVariable(value = "id", required = false) final Integer id,
        @Valid @RequestBody EmpRoleFunctionPermission empRoleFunctionPermission
    ) throws URISyntaxException {
        LOG.debug("REST request to update EmpRoleFunctionPermission : {}, {}", id, empRoleFunctionPermission);
        if (empRoleFunctionPermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empRoleFunctionPermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empRoleFunctionPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empRoleFunctionPermission = empRoleFunctionPermissionService.update(empRoleFunctionPermission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empRoleFunctionPermission.getId().toString()))
            .body(empRoleFunctionPermission);
    }

    /**
     * {@code PATCH  /emp-role-function-permissions/:id} : Partial updates given fields of an existing empRoleFunctionPermission, field will ignore if it is null
     *
     * @param id the id of the empRoleFunctionPermission to save.
     * @param empRoleFunctionPermission the empRoleFunctionPermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empRoleFunctionPermission,
     * or with status {@code 400 (Bad Request)} if the empRoleFunctionPermission is not valid,
     * or with status {@code 404 (Not Found)} if the empRoleFunctionPermission is not found,
     * or with status {@code 500 (Internal Server Error)} if the empRoleFunctionPermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpRoleFunctionPermission> partialUpdateEmpRoleFunctionPermission(
        @PathVariable(value = "id", required = false) final Integer id,
        @NotNull @RequestBody EmpRoleFunctionPermission empRoleFunctionPermission
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EmpRoleFunctionPermission partially : {}, {}", id, empRoleFunctionPermission);
        if (empRoleFunctionPermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empRoleFunctionPermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empRoleFunctionPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpRoleFunctionPermission> result = empRoleFunctionPermissionService.partialUpdate(empRoleFunctionPermission);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empRoleFunctionPermission.getId().toString())
        );
    }

    /**
     * {@code GET  /emp-role-function-permissions} : get all the empRoleFunctionPermissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empRoleFunctionPermissions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EmpRoleFunctionPermission>> getAllEmpRoleFunctionPermissions(
        EmpRoleFunctionPermissionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get EmpRoleFunctionPermissions by criteria: {}", criteria);

        Page<EmpRoleFunctionPermission> page = empRoleFunctionPermissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emp-role-function-permissions/count} : count all the empRoleFunctionPermissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEmpRoleFunctionPermissions(EmpRoleFunctionPermissionCriteria criteria) {
        LOG.debug("REST request to count EmpRoleFunctionPermissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(empRoleFunctionPermissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emp-role-function-permissions/:id} : get the "id" empRoleFunctionPermission.
     *
     * @param id the id of the empRoleFunctionPermission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empRoleFunctionPermission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpRoleFunctionPermission> getEmpRoleFunctionPermission(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get EmpRoleFunctionPermission : {}", id);
        Optional<EmpRoleFunctionPermission> empRoleFunctionPermission = empRoleFunctionPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empRoleFunctionPermission);
    }

    /**
     * {@code DELETE  /emp-role-function-permissions/:id} : delete the "id" empRoleFunctionPermission.
     *
     * @param id the id of the empRoleFunctionPermission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpRoleFunctionPermission(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete EmpRoleFunctionPermission : {}", id);
        empRoleFunctionPermissionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
