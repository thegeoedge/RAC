package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SalesInvoiceLines;
import com.heavenscode.rac.repository.SalesInvoiceLinesRepository;
import com.heavenscode.rac.service.SalesInvoiceLinesQueryService;
import com.heavenscode.rac.service.SalesInvoiceLinesService;
import com.heavenscode.rac.service.criteria.SalesInvoiceLinesCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.SalesInvoiceLines}.
 */
@RestController
@RequestMapping("/api/sales-invoice-lines")
public class SalesInvoiceLinesResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLinesResource.class);

    private static final String ENTITY_NAME = "salesInvoiceLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesInvoiceLinesService salesInvoiceLinesService;

    private final SalesInvoiceLinesRepository salesInvoiceLinesRepository;

    private final SalesInvoiceLinesQueryService salesInvoiceLinesQueryService;

    public SalesInvoiceLinesResource(
        SalesInvoiceLinesService salesInvoiceLinesService,
        SalesInvoiceLinesRepository salesInvoiceLinesRepository,
        SalesInvoiceLinesQueryService salesInvoiceLinesQueryService
    ) {
        this.salesInvoiceLinesService = salesInvoiceLinesService;
        this.salesInvoiceLinesRepository = salesInvoiceLinesRepository;
        this.salesInvoiceLinesQueryService = salesInvoiceLinesQueryService;
    }

    /**
     * {@code POST  /sales-invoice-lines} : Create a new salesInvoiceLines.
     *
     * @param salesInvoiceLines the salesInvoiceLines to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesInvoiceLines, or with status {@code 400 (Bad Request)} if the salesInvoiceLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SalesInvoiceLines> createSalesInvoiceLines(@RequestBody SalesInvoiceLines salesInvoiceLines)
        throws URISyntaxException {
        LOG.debug("REST request to save SalesInvoiceLines : {}", salesInvoiceLines);
        if (salesInvoiceLines.getId() != null) {
            throw new BadRequestAlertException("A new salesInvoiceLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        salesInvoiceLines = salesInvoiceLinesService.save(salesInvoiceLines);
        return ResponseEntity.created(new URI("/api/sales-invoice-lines/" + salesInvoiceLines.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, salesInvoiceLines.getId().toString()))
            .body(salesInvoiceLines);
    }

    /**
     * {@code PUT  /sales-invoice-lines/:id} : Updates an existing salesInvoiceLines.
     *
     * @param id the id of the salesInvoiceLines to save.
     * @param salesInvoiceLines the salesInvoiceLines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceLines,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceLines is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceLines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalesInvoiceLines> updateSalesInvoiceLines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceLines salesInvoiceLines
    ) throws URISyntaxException {
        LOG.debug("REST request to update SalesInvoiceLines : {}, {}", id, salesInvoiceLines);
        if (salesInvoiceLines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceLines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceLinesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salesInvoiceLines = salesInvoiceLinesService.update(salesInvoiceLines);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceLines.getId().toString()))
            .body(salesInvoiceLines);
    }

    /**
     * {@code PATCH  /sales-invoice-lines/:id} : Partial updates given fields of an existing salesInvoiceLines, field will ignore if it is null
     *
     * @param id the id of the salesInvoiceLines to save.
     * @param salesInvoiceLines the salesInvoiceLines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceLines,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceLines is not valid,
     * or with status {@code 404 (Not Found)} if the salesInvoiceLines is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceLines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesInvoiceLines> partialUpdateSalesInvoiceLines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceLines salesInvoiceLines
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SalesInvoiceLines partially : {}, {}", id, salesInvoiceLines);
        if (salesInvoiceLines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceLines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceLinesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesInvoiceLines> result = salesInvoiceLinesService.partialUpdate(salesInvoiceLines);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceLines.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-invoice-lines} : get all the salesInvoiceLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesInvoiceLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SalesInvoiceLines>> getAllSalesInvoiceLines(
        SalesInvoiceLinesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SalesInvoiceLines by criteria: {}", criteria);

        Page<SalesInvoiceLines> page = salesInvoiceLinesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-invoice-lines/count} : count all the salesInvoiceLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSalesInvoiceLines(SalesInvoiceLinesCriteria criteria) {
        LOG.debug("REST request to count SalesInvoiceLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesInvoiceLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales-invoice-lines/:id} : get the "id" salesInvoiceLines.
     *
     * @param id the id of the salesInvoiceLines to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesInvoiceLines, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesInvoiceLines> getSalesInvoiceLines(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SalesInvoiceLines : {}", id);
        Optional<SalesInvoiceLines> salesInvoiceLines = salesInvoiceLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesInvoiceLines);
    }

    /**
     * {@code DELETE  /sales-invoice-lines/:id} : delete the "id" salesInvoiceLines.
     *
     * @param id the id of the salesInvoiceLines to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesInvoiceLines(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SalesInvoiceLines : {}", id);
        salesInvoiceLinesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
