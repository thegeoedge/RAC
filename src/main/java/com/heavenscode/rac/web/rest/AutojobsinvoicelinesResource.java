package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsinvoicelines;
import com.heavenscode.rac.repository.AutojobsinvoicelinesRepository;
import com.heavenscode.rac.service.AutojobsinvoicelinesQueryService;
import com.heavenscode.rac.service.AutojobsinvoicelinesService;
import com.heavenscode.rac.service.criteria.AutojobsinvoicelinesCriteria;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsinvoicelines}.
 */
@RestController
@RequestMapping("/api/autojobsinvoicelines")
public class AutojobsinvoicelinesResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinesResource.class);

    private static final String ENTITY_NAME = "autojobsinvoicelines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsinvoicelinesService autojobsinvoicelinesService;

    private final AutojobsinvoicelinesRepository autojobsinvoicelinesRepository;

    private final AutojobsinvoicelinesQueryService autojobsinvoicelinesQueryService;

    public AutojobsinvoicelinesResource(
        AutojobsinvoicelinesService autojobsinvoicelinesService,
        AutojobsinvoicelinesRepository autojobsinvoicelinesRepository,
        AutojobsinvoicelinesQueryService autojobsinvoicelinesQueryService
    ) {
        this.autojobsinvoicelinesService = autojobsinvoicelinesService;
        this.autojobsinvoicelinesRepository = autojobsinvoicelinesRepository;
        this.autojobsinvoicelinesQueryService = autojobsinvoicelinesQueryService;
    }

    /**
     * {@code POST  /autojobsinvoicelines} : Create a new autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the autojobsinvoicelines to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsinvoicelines, or with status {@code 400 (Bad Request)} if the autojobsinvoicelines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.*/

    @PostMapping("")
    public ResponseEntity<String> createAutojobsinvoicelines(@RequestBody Autojobsinvoicelines autojobsinvoicelines)
        throws URISyntaxException {
        LOG.debug("REST request to insert Autojobsinvoicelines line");

        // Pass the received Autojobsinvoicelines object to the service
        int rows = autojobsinvoicelinesService.insertautojobInvoiceLine(autojobsinvoicelines);

        // Return response after insertion
        return ResponseEntity.created(new URI("/api/autojobsinvoicelines/" + autojobsinvoicelines.getInvocieid())).body(
            "Inserted " + rows + " row(s) successfully."
        );
    }

    /**
     * {@code POST  /autojobsinvoicelines} : Create a new autojobsinvoicelines.
     *
     * @param autojobsinvoicelines  the list of autojobsinvoicelines to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsinvoicelines,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoicelines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
    
   @PostMapping("")
public ResponseEntity<String> createAutojobsinvoicelines(@RequestBody List<Autojobsinvoicelines> autojobsinvoicelines)
    throws URISyntaxException {
    
    LOG.info("Creating {} AutojobsInvoiceLines", autojobsinvoicelines.size());

    int totalRowsAffected = 0;
 

    for (Autojobsinvoicelines line : autojobsinvoicelines ) {
        LOG.info("Processing LineId: {}", line.getLineid());

        int rowsAffected = autojobsinvoicelinesService.insertautojobInvoiceLine(line);
        totalRowsAffected += rowsAffected;
    }

    if (totalRowsAffected > 0) {
        return ResponseEntity.created(new URI("/api/autojobsinvoicelines")).body(
            "Successfully created " + totalRowsAffected + " Autojobs Invoice Lines!"
        );
    } else {
        return ResponseEntity.badRequest().body("Failed to create Autojobs Invoice Lines.");
    }
}
 */

    /**
     * {@code PUT  /autojobsinvoicelines/:id} : Updates an existing autojobsinvoicelines.
     *
     * @param id the id of the autojobsinvoicelines to save.
     * @param autojobsinvoicelines the autojobsinvoicelines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoicelines,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoicelines is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoicelines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{invocieid}")
    public ResponseEntity<Autojobsinvoicelines> updateAutojobsinvoicelines(
        @PathVariable(value = "invocieid", required = false) final Integer invocieid,
        @RequestBody Autojobsinvoicelines autojobsinvoicelines
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsinvoicelines : {}, {}", invocieid, autojobsinvoicelines);
        if (autojobsinvoicelines.getInvocieid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invocieid, autojobsinvoicelines.getInvocieid())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoicelinesRepository.existsById(invocieid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsinvoicelines = autojobsinvoicelinesService.update(autojobsinvoicelines);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoicelines.getInvocieid().toString())
            )
            .body(autojobsinvoicelines);
    }

    /**
     * {@code PATCH  /autojobsinvoicelines/:id} : Partial updates given fields of an existing autojobsinvoicelines, field will ignore if it is null
     *
     * @param invocieid the id of the autojobsinvoicelines to save.
     * @param autojobsinvoicelines the autojobsinvoicelines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoicelines,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoicelines is not valid,
     * or with status {@code 404 (Not Found)} if the autojobsinvoicelines is not found,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoicelines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{invocieid}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsinvoicelines> partialUpdateAutojobsinvoicelines(
        @PathVariable(value = "invocieid", required = false) final Integer invocieid,
        @RequestBody Autojobsinvoicelines autojobsinvoicelines
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Autojobsinvoicelines partially : {}, {}", invocieid, autojobsinvoicelines);
        if (autojobsinvoicelines.getInvocieid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invocieid, autojobsinvoicelines.getInvocieid())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoicelinesRepository.existsById(invocieid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsinvoicelines> result = autojobsinvoicelinesService.partialUpdate(autojobsinvoicelines);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoicelines.getInvocieid().toString())
        );
    }

    /**
     * {@code GET  /autojobsinvoicelines} : get all the autojobsinvoicelines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoicelines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsinvoicelines>> getAllAutojobsinvoicelines(
        AutojobsinvoicelinesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autojobsinvoicelines by criteria: {}", criteria);

        Page<Autojobsinvoicelines> page = autojobsinvoicelinesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autojobsinvoicelines/count} : count all the autojobsinvoicelines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutojobsinvoicelines(AutojobsinvoicelinesCriteria criteria) {
        LOG.debug("REST request to count Autojobsinvoicelines by criteria: {}", criteria);
        return ResponseEntity.ok().body(autojobsinvoicelinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autojobsinvoicelines/:id} : get the "id" autojobsinvoicelines.
     *
     * @param invocieid the id of the autojobsinvoicelines to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsinvoicelines, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{invocieid}")
    public ResponseEntity<Autojobsinvoicelines> getAutojobsinvoicelines(@PathVariable("invocieid") Integer invocieid) {
        LOG.debug("REST request to get Autojobsinvoicelines : {}", invocieid);
        Optional<Autojobsinvoicelines> autojobsinvoicelines = autojobsinvoicelinesService.findOne(invocieid);
        return ResponseUtil.wrapOrNotFound(autojobsinvoicelines);
    }

    /**
     * {@code GET  /autojobsinvoicelines/by-invoice-id} : get all the autojobsinvoicelines by invoice ID.
     *
     * @param invocieID the invoice ID to filter lines.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoicelines in body.
     */
    @GetMapping("/by-invoice-id")
    public ResponseEntity<List<Autojobsinvoicelines>> getAutojobsinvoicelinesByInvoiceId(
        @RequestParam(required = false) Integer invocieID
    ) {
        LOG.debug("REST request to get Autojobsinvoicelines for InvocieID: {}", invocieID);

        List<Autojobsinvoicelines> result;

        if (invocieID != null) {
            result = autojobsinvoicelinesService.fetchJobInvoiceLines(invocieID);
        } else {
            result = new ArrayList<>(); // Or optionally fetch all or return error
        }

        return ResponseEntity.ok().body(result);
    }
    /**
     * {@code DELETE  /autojobsinvoicelines/:id} : delete the "id" autojobsinvoicelines.
     *
     * @param id the id of the autojobsinvoicelines to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsinvoicelines(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autojobsinvoicelines : {}", id);
        autojobsinvoicelinesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    } */
}
