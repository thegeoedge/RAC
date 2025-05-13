package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autocarejob;
import com.heavenscode.rac.repository.AutocarejobRepository;
import com.heavenscode.rac.service.AutocarejobQueryService;
import com.heavenscode.rac.service.AutocarejobService;
import com.heavenscode.rac.service.criteria.AutocarejobCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autocarejob}.
 */
@RestController
@RequestMapping("/api/autocarejobs")
public class AutocarejobResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutocarejobResource.class);

    private static final String ENTITY_NAME = "autocarejob";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutocarejobService autocarejobService;

    private final AutocarejobRepository autocarejobRepository;

    private final AutocarejobQueryService autocarejobQueryService;

    public AutocarejobResource(
        AutocarejobService autocarejobService,
        AutocarejobRepository autocarejobRepository,
        AutocarejobQueryService autocarejobQueryService
    ) {
        this.autocarejobService = autocarejobService;
        this.autocarejobRepository = autocarejobRepository;
        this.autocarejobQueryService = autocarejobQueryService;
    }

    /**
     * {@code POST  /autocarejobs} : Create a new autocarejob.
     *
     * @param autocarejob the autocarejob to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autocarejob, or with status {@code 400 (Bad Request)} if the autocarejob has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autocarejob> createAutocarejob(@RequestBody Autocarejob autocarejob) throws URISyntaxException {
        LOG.debug("REST request to save Autocarejob : {}", autocarejob);
        if (autocarejob.getId() != null) {
            throw new BadRequestAlertException("A new autocarejob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        autocarejob = autocarejobService.save(autocarejob);
        return ResponseEntity.created(new URI("/api/autocarejobs/" + autocarejob.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, autocarejob.getId().toString()))
            .body(autocarejob);
    }

    /**
     * {@code PUT  /autocarejobs/:id} : Updates an existing autocarejob.
     *
     * @param id the id of the autocarejob to save.
     * @param autocarejob the autocarejob to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autocarejob,
     * or with status {@code 400 (Bad Request)} if the autocarejob is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autocarejob couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autocarejob> updateAutocarejob(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autocarejob autocarejob
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autocarejob : {}, {}", id, autocarejob);
        if (autocarejob.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autocarejob.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autocarejobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autocarejob = autocarejobService.update(autocarejob);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autocarejob.getId().toString()))
            .body(autocarejob);
    }

    /**
     * {@code PATCH  /autocarejobs/:id} : Partial updates given fields of an existing autocarejob, field will ignore if it is null
     *
     * @param id the id of the autocarejob to save.
     * @param autocarejob the autocarejob to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autocarejob,
     * or with status {@code 400 (Bad Request)} if the autocarejob is not valid,
     * or with status {@code 404 (Not Found)} if the autocarejob is not found,
     * or with status {@code 500 (Internal Server Error)} if the autocarejob couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autocarejob> partialUpdateAutocarejob(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autocarejob autocarejob
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Autocarejob partially : {}, {}", id, autocarejob);
        if (autocarejob.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autocarejob.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autocarejobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autocarejob> result = autocarejobService.partialUpdate(autocarejob);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autocarejob.getId().toString())
        );
    }

    /**
     * {@code GET  /autocarejobs} : get all the autocarejobs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autocarejobs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autocarejob>> getAllAutocarejobs(
        AutocarejobCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autocarejobs by criteria: {}", criteria);

        Page<Autocarejob> page = autocarejobQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autocarejobs/count} : count all the autocarejobs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutocarejobs(AutocarejobCriteria criteria) {
        LOG.debug("REST request to count Autocarejobs by criteria: {}", criteria);
        return ResponseEntity.ok().body(autocarejobQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autocarejobs/:id} : get the "id" autocarejob.
     *
     * @param id the id of the autocarejob to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autocarejob, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autocarejob> getAutocarejob(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Autocarejob : {}", id);
        Optional<Autocarejob> autocarejob = autocarejobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autocarejob);
    }

    /**
     * {@code DELETE  /autocarejobs/:id} : delete the "id" autocarejob.
     *
     * @param id the id of the autocarejob to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutocarejob(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autocarejob : {}", id);
        autocarejobService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
