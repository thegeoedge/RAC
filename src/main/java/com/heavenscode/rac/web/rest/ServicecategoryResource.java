package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Servicecategory;
import com.heavenscode.rac.repository.ServicecategoryRepository;
import com.heavenscode.rac.service.ServicecategoryQueryService;
import com.heavenscode.rac.service.ServicecategoryService;
import com.heavenscode.rac.service.criteria.ServicecategoryCriteria;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Servicecategory}.
 */
@RestController
@RequestMapping("/api/servicecategories")
public class ServicecategoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServicecategoryResource.class);

    private static final String ENTITY_NAME = "servicecategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicecategoryService servicecategoryService;

    private final ServicecategoryRepository servicecategoryRepository;

    private final ServicecategoryQueryService servicecategoryQueryService;

    public ServicecategoryResource(
        ServicecategoryService servicecategoryService,
        ServicecategoryRepository servicecategoryRepository,
        ServicecategoryQueryService servicecategoryQueryService
    ) {
        this.servicecategoryService = servicecategoryService;
        this.servicecategoryRepository = servicecategoryRepository;
        this.servicecategoryQueryService = servicecategoryQueryService;
    }

    /**
     * {@code POST  /servicecategories} : Create a new servicecategory.
     *
     * @param servicecategory the servicecategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicecategory, or with status {@code 400 (Bad Request)} if the servicecategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Servicecategory> createServicecategory(@RequestBody Servicecategory servicecategory) throws URISyntaxException {
        LOG.debug("REST request to save Servicecategory : {}", servicecategory);
        if (servicecategory.getId() != null) {
            throw new BadRequestAlertException("A new servicecategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        servicecategory = servicecategoryService.save(servicecategory);
        return ResponseEntity.created(new URI("/api/servicecategories/" + servicecategory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, servicecategory.getId().toString()))
            .body(servicecategory);
    }

    /**
     * {@code PUT  /servicecategories/:id} : Updates an existing servicecategory.
     *
     * @param id the id of the servicecategory to save.
     * @param servicecategory the servicecategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicecategory,
     * or with status {@code 400 (Bad Request)} if the servicecategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicecategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Servicecategory> updateServicecategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Servicecategory servicecategory
    ) throws URISyntaxException {
        LOG.debug("REST request to update Servicecategory : {}, {}", id, servicecategory);
        if (servicecategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicecategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicecategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicecategory = servicecategoryService.update(servicecategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servicecategory.getId().toString()))
            .body(servicecategory);
    }

    /**
     * {@code PATCH  /servicecategories/:id} : Partial updates given fields of an existing servicecategory, field will ignore if it is null
     *
     * @param id the id of the servicecategory to save.
     * @param servicecategory the servicecategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicecategory,
     * or with status {@code 400 (Bad Request)} if the servicecategory is not valid,
     * or with status {@code 404 (Not Found)} if the servicecategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicecategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Servicecategory> partialUpdateServicecategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Servicecategory servicecategory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Servicecategory partially : {}, {}", id, servicecategory);
        if (servicecategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicecategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicecategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Servicecategory> result = servicecategoryService.partialUpdate(servicecategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servicecategory.getId().toString())
        );
    }

    /**
     * {@code GET  /servicecategories} : get all the servicecategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicecategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Servicecategory>> getAllServicecategories(
        ServicecategoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Servicecategories by criteria: {}", criteria);

        Page<Servicecategory> page = servicecategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servicecategories/count} : count all the servicecategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countServicecategories(ServicecategoryCriteria criteria) {
        LOG.debug("REST request to count Servicecategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(servicecategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /servicecategories/:id} : get the "id" servicecategory.
     *
     * @param id the id of the servicecategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicecategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Servicecategory> getServicecategory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Servicecategory : {}", id);
        Optional<Servicecategory> servicecategory = servicecategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicecategory);
    }

    /**
     * {@code DELETE  /servicecategories/:id} : delete the "id" servicecategory.
     *
     * @param id the id of the servicecategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicecategory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Servicecategory : {}", id);
        servicecategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
