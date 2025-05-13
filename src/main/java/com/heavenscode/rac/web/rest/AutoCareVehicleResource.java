package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.AutoCareVehicle;
import com.heavenscode.rac.repository.AutoCareVehicleRepository;
import com.heavenscode.rac.service.AutoCareVehicleQueryService;
import com.heavenscode.rac.service.AutoCareVehicleService;
import com.heavenscode.rac.service.criteria.AutoCareVehicleCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.AutoCareVehicle}.
 */
@RestController
@RequestMapping("/api/auto-care-vehicles")
public class AutoCareVehicleResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCareVehicleResource.class);

    private static final String ENTITY_NAME = "autoCareVehicle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutoCareVehicleService autoCareVehicleService;

    private final AutoCareVehicleRepository autoCareVehicleRepository;

    private final AutoCareVehicleQueryService autoCareVehicleQueryService;

    public AutoCareVehicleResource(
        AutoCareVehicleService autoCareVehicleService,
        AutoCareVehicleRepository autoCareVehicleRepository,
        AutoCareVehicleQueryService autoCareVehicleQueryService
    ) {
        this.autoCareVehicleService = autoCareVehicleService;
        this.autoCareVehicleRepository = autoCareVehicleRepository;
        this.autoCareVehicleQueryService = autoCareVehicleQueryService;
    }

    /**
     * {@code POST  /auto-care-vehicles} : Create a new autoCareVehicle.
     *
     * @param autoCareVehicle the autoCareVehicle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autoCareVehicle, or with status {@code 400 (Bad Request)} if the autoCareVehicle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AutoCareVehicle> createAutoCareVehicle(@RequestBody AutoCareVehicle autoCareVehicle) throws URISyntaxException {
        LOG.debug("REST request to save AutoCareVehicle : {}", autoCareVehicle);
        if (autoCareVehicle.getId() != null) {
            throw new BadRequestAlertException("A new autoCareVehicle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        autoCareVehicle = autoCareVehicleService.save(autoCareVehicle);
        return ResponseEntity.created(new URI("/api/auto-care-vehicles/" + autoCareVehicle.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, autoCareVehicle.getId().toString()))
            .body(autoCareVehicle);
    }

    /**
     * {@code PUT  /auto-care-vehicles/:id} : Updates an existing autoCareVehicle.
     *
     * @param id the id of the autoCareVehicle to save.
     * @param autoCareVehicle the autoCareVehicle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoCareVehicle,
     * or with status {@code 400 (Bad Request)} if the autoCareVehicle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autoCareVehicle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AutoCareVehicle> updateAutoCareVehicle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AutoCareVehicle autoCareVehicle
    ) throws URISyntaxException {
        LOG.debug("REST request to update AutoCareVehicle : {}, {}", id, autoCareVehicle);
        if (autoCareVehicle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoCareVehicle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoCareVehicleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autoCareVehicle = autoCareVehicleService.update(autoCareVehicle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autoCareVehicle.getId().toString()))
            .body(autoCareVehicle);
    }

    /**
     * {@code PATCH  /auto-care-vehicles/:id} : Partial updates given fields of an existing autoCareVehicle, field will ignore if it is null
     *
     * @param id the id of the autoCareVehicle to save.
     * @param autoCareVehicle the autoCareVehicle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoCareVehicle,
     * or with status {@code 400 (Bad Request)} if the autoCareVehicle is not valid,
     * or with status {@code 404 (Not Found)} if the autoCareVehicle is not found,
     * or with status {@code 500 (Internal Server Error)} if the autoCareVehicle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AutoCareVehicle> partialUpdateAutoCareVehicle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AutoCareVehicle autoCareVehicle
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AutoCareVehicle partially : {}, {}", id, autoCareVehicle);
        if (autoCareVehicle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoCareVehicle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoCareVehicleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AutoCareVehicle> result = autoCareVehicleService.partialUpdate(autoCareVehicle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autoCareVehicle.getId().toString())
        );
    }

    /**
     * {@code GET  /auto-care-vehicles} : get all the autoCareVehicles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autoCareVehicles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AutoCareVehicle>> getAllAutoCareVehicles(
        AutoCareVehicleCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get AutoCareVehicles by criteria: {}", criteria);

        Page<AutoCareVehicle> page = autoCareVehicleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /auto-care-vehicles/count} : count all the autoCareVehicles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutoCareVehicles(AutoCareVehicleCriteria criteria) {
        LOG.debug("REST request to count AutoCareVehicles by criteria: {}", criteria);
        return ResponseEntity.ok().body(autoCareVehicleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /auto-care-vehicles/:id} : get the "id" autoCareVehicle.
     *
     * @param id the id of the autoCareVehicle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autoCareVehicle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AutoCareVehicle> getAutoCareVehicle(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AutoCareVehicle : {}", id);
        Optional<AutoCareVehicle> autoCareVehicle = autoCareVehicleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autoCareVehicle);
    }

    /**
     * {@code DELETE  /auto-care-vehicles/:id} : delete the "id" autoCareVehicle.
     *
     * @param id the id of the autoCareVehicle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutoCareVehicle(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AutoCareVehicle : {}", id);
        autoCareVehicleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
