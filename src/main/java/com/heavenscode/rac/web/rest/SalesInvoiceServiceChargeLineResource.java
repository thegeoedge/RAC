package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineRepository;
import com.heavenscode.rac.service.SalesInvoiceServiceChargeLineQueryService;
import com.heavenscode.rac.service.SalesInvoiceServiceChargeLineService;
import com.heavenscode.rac.service.criteria.SalesInvoiceServiceChargeLineCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine}.
 */
@RestController
@RequestMapping("/api/sales-invoice-service-charge-lines")
public class SalesInvoiceServiceChargeLineResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceServiceChargeLineResource.class);

    private static final String ENTITY_NAME = "salesInvoiceServiceChargeLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesInvoiceServiceChargeLineService salesInvoiceServiceChargeLineService;

    private final SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository;

    private final SalesInvoiceServiceChargeLineQueryService salesInvoiceServiceChargeLineQueryService;

    public SalesInvoiceServiceChargeLineResource(
        SalesInvoiceServiceChargeLineService salesInvoiceServiceChargeLineService,
        SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository,
        SalesInvoiceServiceChargeLineQueryService salesInvoiceServiceChargeLineQueryService
    ) {
        this.salesInvoiceServiceChargeLineService = salesInvoiceServiceChargeLineService;
        this.salesInvoiceServiceChargeLineRepository = salesInvoiceServiceChargeLineRepository;
        this.salesInvoiceServiceChargeLineQueryService = salesInvoiceServiceChargeLineQueryService;
    }

    /**
     * {@code POST  /sales-invoice-service-charge-lines} : Create a new salesInvoiceServiceChargeLine.
     *
     * @param salesInvoiceServiceChargeLine the salesInvoiceServiceChargeLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesInvoiceServiceChargeLine, or with status {@code 400 (Bad Request)} if the salesInvoiceServiceChargeLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SalesInvoiceServiceChargeLine> createSalesInvoiceServiceChargeLine(
        @RequestBody SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine
    ) throws URISyntaxException {
        LOG.debug("REST request to save SalesInvoiceServiceChargeLine : {}", salesInvoiceServiceChargeLine);

        salesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineService.save(salesInvoiceServiceChargeLine);
        return ResponseEntity.created(new URI("/api/sales-invoice-service-charge-lines/" + salesInvoiceServiceChargeLine.getInvoiceId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    salesInvoiceServiceChargeLine.getInvoiceId().toString()
                )
            )
            .body(salesInvoiceServiceChargeLine);
    }

    /**
     * {@code PUT  /sales-invoice-service-charge-lines/:id} : Updates an existing salesInvoiceServiceChargeLine.
     *
     * @param id the id of the salesInvoiceServiceChargeLine to save.
     * @param salesInvoiceServiceChargeLine the salesInvoiceServiceChargeLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceServiceChargeLine,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceServiceChargeLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceServiceChargeLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{invoiceid}")
    public ResponseEntity<SalesInvoiceServiceChargeLine> updateSalesInvoiceServiceChargeLine(
        @PathVariable(value = "invoiceid", required = false) final Integer invoiceid,
        @RequestBody SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine
    ) throws URISyntaxException {
        LOG.debug("REST request to update SalesInvoiceServiceChargeLine : {}, {}", invoiceid, salesInvoiceServiceChargeLine);
        if (salesInvoiceServiceChargeLine.getInvoiceId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invoiceid, salesInvoiceServiceChargeLine.getInvoiceId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceServiceChargeLineRepository.existsById(invoiceid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineService.update(salesInvoiceServiceChargeLine);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    salesInvoiceServiceChargeLine.getInvoiceId().toString()
                )
            )
            .body(salesInvoiceServiceChargeLine);
    }

    /**
     * {@code PATCH  /sales-invoice-service-charge-lines/:id} : Partial updates given fields of an existing salesInvoiceServiceChargeLine, field will ignore if it is null
     *
     * @param id the id of the salesInvoiceServiceChargeLine to save.
     * @param salesInvoiceServiceChargeLine the salesInvoiceServiceChargeLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceServiceChargeLine,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceServiceChargeLine is not valid,
     * or with status {@code 404 (Not Found)} if the salesInvoiceServiceChargeLine is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceServiceChargeLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{invoiceid}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesInvoiceServiceChargeLine> partialUpdateSalesInvoiceServiceChargeLine(
        @PathVariable(value = "invoiceid", required = false) final Integer invoiceid,
        @RequestBody SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update SalesInvoiceServiceChargeLine partially : {}, {}",
            invoiceid,
            salesInvoiceServiceChargeLine
        );
        if (salesInvoiceServiceChargeLine.getInvoiceId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invoiceid, salesInvoiceServiceChargeLine.getInvoiceId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceServiceChargeLineRepository.existsById(invoiceid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesInvoiceServiceChargeLine> result = salesInvoiceServiceChargeLineService.partialUpdate(salesInvoiceServiceChargeLine);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceServiceChargeLine.getInvoiceId().toString())
        );
    }

    /**
     * {@code GET  /sales-invoice-service-charge-lines/by-invoice-id} : get all the autojobsinvoicelines by invoice ID.
     *
     * @param invoiceID the invoice ID to filter lines.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoicelines in body.
     */
    @GetMapping("/by-invoice-ids")
    public ResponseEntity<List<SalesInvoiceServiceChargeLine>> getAutosalesinvoiceservicechargelinesByInvoiceId(
        @RequestParam(required = false) Integer invoiceID
    ) {
        LOG.debug("REST request to get Autojobsinvoicelines for InvocieID: {}", invoiceID);

        List<SalesInvoiceServiceChargeLine> result;

        if (invoiceID != null) {
            result = salesInvoiceServiceChargeLineService.fetchServiceChargeLines(invoiceID);
        } else {
            result = new ArrayList<>(); // Or optionally fetch all or return error
        }

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /sales-invoice-service-charge-lines} : get all the salesInvoiceServiceChargeLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesInvoiceServiceChargeLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SalesInvoiceServiceChargeLine>> getAllSalesInvoiceServiceChargeLines(
        SalesInvoiceServiceChargeLineCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SalesInvoiceServiceChargeLines by criteria: {}", criteria);

        Page<SalesInvoiceServiceChargeLine> page = salesInvoiceServiceChargeLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-invoice-service-charge-lines/count} : count all the salesInvoiceServiceChargeLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSalesInvoiceServiceChargeLines(SalesInvoiceServiceChargeLineCriteria criteria) {
        LOG.debug("REST request to count SalesInvoiceServiceChargeLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesInvoiceServiceChargeLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales-invoice-service-charge-lines/:id} : get the "id" salesInvoiceServiceChargeLine.
     *
     * @param id the id of the salesInvoiceServiceChargeLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesInvoiceServiceChargeLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{invoiceid}")
    public ResponseEntity<SalesInvoiceServiceChargeLine> getSalesInvoiceServiceChargeLine(@PathVariable("invoiceid") Integer invoiceid) {
        LOG.debug("REST request to get SalesInvoiceServiceChargeLine : {}", invoiceid);
        Optional<SalesInvoiceServiceChargeLine> salesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineService.findOne(invoiceid);
        return ResponseUtil.wrapOrNotFound(salesInvoiceServiceChargeLine);
    }
    /**
     * {@code DELETE  /sales-invoice-service-charge-lines/:id} : delete the "id" salesInvoiceServiceChargeLine.
     *
     * @param id the id of the salesInvoiceServiceChargeLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   
    @DeleteMapping("/{invoiceid}")
    public ResponseEntity<Void> deleteSalesInvoiceServiceChargeLine(@PathVariable("invoiceid") Integer invoiceid) {
        LOG.debug("REST request to delete SalesInvoiceServiceChargeLine : {}", id);
        salesInvoiceServiceChargeLineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }  */
}
