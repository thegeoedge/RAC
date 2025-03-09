package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Inventorybatches;
import com.heavenscode.rac.repository.InventorybatchesRepository;
import com.heavenscode.rac.service.InventorybatchesQueryService;
import com.heavenscode.rac.service.InventorybatchesService;
import com.heavenscode.rac.service.criteria.InventorybatchesCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Inventorybatches}.
 */
@RestController
@RequestMapping("/api/inventorybatches")
public class InventorybatchesResource {

    private static final Logger LOG = LoggerFactory.getLogger(InventorybatchesResource.class);

    private static final String ENTITY_NAME = "inventorybatches";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventorybatchesService inventorybatchesService;

    private final InventorybatchesRepository inventorybatchesRepository;

    private final InventorybatchesQueryService inventorybatchesQueryService;

    public InventorybatchesResource(
        InventorybatchesService inventorybatchesService,
        InventorybatchesRepository inventorybatchesRepository,
        InventorybatchesQueryService inventorybatchesQueryService
    ) {
        this.inventorybatchesService = inventorybatchesService;
        this.inventorybatchesRepository = inventorybatchesRepository;
        this.inventorybatchesQueryService = inventorybatchesQueryService;
    }

    /**
     * {@code POST  /inventorybatches} : Create a new inventorybatches.
     *
     * @param inventorybatches the inventorybatches to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventorybatches, or with status {@code 400 (Bad Request)} if the inventorybatches has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inventorybatches> createInventorybatches(@RequestBody Inventorybatches inventorybatches)
        throws URISyntaxException {
        LOG.debug("REST request to save Inventorybatches : {}", inventorybatches);
        if (inventorybatches.getId() != null) {
            throw new BadRequestAlertException("A new inventorybatches cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventorybatches = inventorybatchesService.save(inventorybatches);
        return ResponseEntity.created(new URI("/api/inventorybatches/" + inventorybatches.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inventorybatches.getId().toString()))
            .body(inventorybatches);
    }

    /**
     * {@code PUT  /inventorybatches/:id} : Updates an existing inventorybatches.
     *
     * @param id the id of the inventorybatches to save.
     * @param inventorybatches the inventorybatches to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventorybatches,
     * or with status {@code 400 (Bad Request)} if the inventorybatches is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventorybatches couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inventorybatches> updateInventorybatches(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inventorybatches inventorybatches
    ) throws URISyntaxException {
        LOG.debug("REST request to update Inventorybatches : {}, {}", id, inventorybatches);
        if (inventorybatches.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventorybatches.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventorybatchesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inventorybatches = inventorybatchesService.update(inventorybatches);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inventorybatches.getId().toString()))
            .body(inventorybatches);
    }

    /**
     * {@code PATCH  /inventorybatches/:id} : Partial updates given fields of an existing inventorybatches, field will ignore if it is null
     *
     * @param id the id of the inventorybatches to save.
     * @param inventorybatches the inventorybatches to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventorybatches,
     * or with status {@code 400 (Bad Request)} if the inventorybatches is not valid,
     * or with status {@code 404 (Not Found)} if the inventorybatches is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventorybatches couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inventorybatches> partialUpdateInventorybatches(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inventorybatches inventorybatches
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Inventorybatches partially : {}, {}", id, inventorybatches);
        if (inventorybatches.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventorybatches.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventorybatchesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inventorybatches> result = inventorybatchesService.partialUpdate(inventorybatches);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inventorybatches.getId().toString())
        );
    }

    /**
     * {@code GET  /inventorybatches} : get all the inventorybatches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventorybatches in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Inventorybatches>> getAllInventorybatches(
        InventorybatchesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Inventorybatches by criteria: {}", criteria);

        Page<Inventorybatches> page = inventorybatchesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventorybatches/count} : count all the inventorybatches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInventorybatches(InventorybatchesCriteria criteria) {
        LOG.debug("REST request to count Inventorybatches by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventorybatchesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventorybatches/:id} : get the "id" inventorybatches.
     *
     * @param id the id of the inventorybatches to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventorybatches, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventorybatches> getInventorybatches(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Inventorybatches : {}", id);
        Optional<Inventorybatches> inventorybatches = inventorybatchesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventorybatches);
    }

    /**
     * {@code DELETE  /inventorybatches/:id} : delete the "id" inventorybatches.
     *
     * @param id the id of the inventorybatches to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventorybatches(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Inventorybatches : {}", id);
        inventorybatchesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
