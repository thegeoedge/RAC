package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.EmpFunctions;
import com.heavenscode.rac.repository.EmpFunctionsRepository;
import com.heavenscode.rac.service.EmpFunctionsQueryService;
import com.heavenscode.rac.service.EmpFunctionsService;
import com.heavenscode.rac.service.criteria.EmpFunctionsCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.EmpFunctions}.
 */
@RestController
@RequestMapping("/api/emp-functions")
public class EmpFunctionsResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmpFunctionsResource.class);

    private static final String ENTITY_NAME = "empFunctions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpFunctionsService empFunctionsService;

    private final EmpFunctionsRepository empFunctionsRepository;

    private final EmpFunctionsQueryService empFunctionsQueryService;

    public EmpFunctionsResource(
        EmpFunctionsService empFunctionsService,
        EmpFunctionsRepository empFunctionsRepository,
        EmpFunctionsQueryService empFunctionsQueryService
    ) {
        this.empFunctionsService = empFunctionsService;
        this.empFunctionsRepository = empFunctionsRepository;
        this.empFunctionsQueryService = empFunctionsQueryService;
    }

    /**
     * {@code POST  /emp-functions} : Create a new empFunctions.
     *
     * @param empFunctions the empFunctions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empFunctions, or with status {@code 400 (Bad Request)} if the empFunctions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmpFunctions> createEmpFunctions(@Valid @RequestBody EmpFunctions empFunctions) throws URISyntaxException {
        LOG.debug("REST request to save EmpFunctions : {}", empFunctions);

        empFunctions = empFunctionsService.save(empFunctions);
        return ResponseEntity.created(new URI("/api/emp-functions/" + empFunctions.getFunctionId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, empFunctions.getFunctionId().toString()))
            .body(empFunctions);
    }

    /**
     * {@code PUT  /emp-functions/:functionId} : Updates an existing empFunctions.
     *
     * @param functionId the id of the empFunctions to save.
     * @param empFunctions the empFunctions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empFunctions,
     * or with status {@code 400 (Bad Request)} if the empFunctions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empFunctions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{functionId}")
    public ResponseEntity<EmpFunctions> updateEmpFunctions(
        @PathVariable(value = "functionId", required = false) final Integer functionId,
        @Valid @RequestBody EmpFunctions empFunctions
    ) throws URISyntaxException {
        LOG.debug("REST request to update EmpFunctions : {}, {}", functionId, empFunctions);
        if (empFunctions.getFunctionId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(functionId, empFunctions.getFunctionId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empFunctionsRepository.existsById(functionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empFunctions = empFunctionsService.update(empFunctions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empFunctions.getFunctionId().toString()))
            .body(empFunctions);
    }

    /**
     * {@code PATCH  /emp-functions/:functionId} : Partial updates given fields of an existing empFunctions, field will ignore if it is null
     *
     * @param functionId the id of the empFunctions to save.
     * @param empFunctions the empFunctions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empFunctions,
     * or with status {@code 400 (Bad Request)} if the empFunctions is not valid,
     * or with status {@code 404 (Not Found)} if the empFunctions is not found,
     * or with status {@code 500 (Internal Server Error)} if the empFunctions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{functionId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpFunctions> partialUpdateEmpFunctions(
        @PathVariable(value = "functionId", required = false) final Integer functionId,
        @NotNull @RequestBody EmpFunctions empFunctions
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EmpFunctions partially : {}, {}", functionId, empFunctions);
        if (empFunctions.getFunctionId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(functionId, empFunctions.getFunctionId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empFunctionsRepository.existsById(functionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpFunctions> result = empFunctionsService.partialUpdate(empFunctions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empFunctions.getFunctionId().toString())
        );
    }

    /**
     * {@code GET  /emp-functions} : get all the empFunctions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empFunctions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EmpFunctions>> getAllEmpFunctions(
        EmpFunctionsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get EmpFunctions by criteria: {}", criteria);

        Page<EmpFunctions> page = empFunctionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emp-functions/count} : count all the empFunctions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEmpFunctions(EmpFunctionsCriteria criteria) {
        LOG.debug("REST request to count EmpFunctions by criteria: {}", criteria);
        return ResponseEntity.ok().body(empFunctionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emp-functions/:id} : get the "id" empFunctions.
     *
     * @param id the id of the empFunctions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empFunctions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpFunctions> getEmpFunctions(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get EmpFunctions : {}", id);
        Optional<EmpFunctions> empFunctions = empFunctionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empFunctions);
    }

    /**
     * {@code DELETE  /emp-functions/:id} : delete the "id" empFunctions.
     *
     * @param id the id of the empFunctions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpFunctions(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete EmpFunctions : {}", id);
        empFunctionsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
