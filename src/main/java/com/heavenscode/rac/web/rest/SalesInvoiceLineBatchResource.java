package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SalesInvoiceLineBatch;
import com.heavenscode.rac.repository.SalesInvoiceLineBatchRepository;
import com.heavenscode.rac.service.SalesInvoiceLineBatchQueryService;
import com.heavenscode.rac.service.SalesInvoiceLineBatchService;
import com.heavenscode.rac.service.criteria.SalesInvoiceLineBatchCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.SalesInvoiceLineBatch}.
 */
@RestController
@RequestMapping("/api/sales-invoice-line-batches")
public class SalesInvoiceLineBatchResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLineBatchResource.class);

    private static final String ENTITY_NAME = "salesInvoiceLineBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesInvoiceLineBatchService salesInvoiceLineBatchService;

    private final SalesInvoiceLineBatchRepository salesInvoiceLineBatchRepository;

    private final SalesInvoiceLineBatchQueryService salesInvoiceLineBatchQueryService;

    public SalesInvoiceLineBatchResource(
        SalesInvoiceLineBatchService salesInvoiceLineBatchService,
        SalesInvoiceLineBatchRepository salesInvoiceLineBatchRepository,
        SalesInvoiceLineBatchQueryService salesInvoiceLineBatchQueryService
    ) {
        this.salesInvoiceLineBatchService = salesInvoiceLineBatchService;
        this.salesInvoiceLineBatchRepository = salesInvoiceLineBatchRepository;
        this.salesInvoiceLineBatchQueryService = salesInvoiceLineBatchQueryService;
    }

    /**
     * {@code POST  /sales-invoice-line-batches} : Create a new SalesInvoiceLineBatch.
     *
     * @param batch The sales invoice line batch data.
     * @return ResponseEntity with status {@code 201 (Created)} or {@code 400 (Bad Request)} if insertion fails.
     
    @PostMapping("")
    public ResponseEntity<String> createSalesInvoiceLineBatch(@RequestBody SalesInvoiceLineBatch batch)
            throws URISyntaxException {

        // Log the incoming batch data
        LOG.info("Creating SalesInvoiceLineBatch with the following details:");
        LOG.info("LineId: {}", batch.getLineId());
        LOG.info("BatchLineId: {}", batch.getBatchLineId());
        LOG.info("ItemID: {}", batch.getItemId());
        LOG.info("Code: {}", batch.getCode());
        LOG.info("BatchId: {}", batch.getBatchId());
        LOG.info("BatchCode: {}", batch.getBatchCode());
        LOG.info("TxDate: {}", batch.getTxDate());
        LOG.info("ManufactureDate: {}", batch.getManufactureDate());
        LOG.info("ExpiredDate: {}", batch.getExpiredDate());
        LOG.info("Qty: {}", batch.getQty());
        LOG.info("Cost: {}", batch.getCost());
        LOG.info("Price: {}", batch.getPrice());
        LOG.info("Notes: {}", batch.getNotes());
        LOG.info("LMU: {}", batch.getLmu());
        LOG.info("LMD: {}", batch.getLmd());
        LOG.info("NBT: {}", batch.getNbt());
        LOG.info("VAT: {}", batch.getVat());
        LOG.info("AddedById: {}", batch.getAddedById());

        // Call the service to insert the batch
        int rowsAffected = salesInvoiceLineBatchService.insertSalesInvoiceLineBatch(batch);

        // Return appropriate response based on the result
        if (rowsAffected > 0) {
            return ResponseEntity.created(new URI("/api/sales-invoice-line-batches"))
                    .body("Sales Invoice Line Batch created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to create Sales Invoice Line Batch.");
        }
    }

*/

    @PostMapping("")
    public ResponseEntity<String> createSalesInvoiceLineBatches(@RequestBody List<SalesInvoiceLineBatch> batches)
        throws URISyntaxException {
        LOG.info("Creating {} SalesInvoiceLineBatches", batches.size());

        int totalRowsAffected = 0;

        for (SalesInvoiceLineBatch batch : batches) {
            LOG.info("Processing BatchLineId: {}", batch.getBatchLineId());

            int rowsAffected = salesInvoiceLineBatchService.insertSalesInvoiceLineBatch(batch);
            totalRowsAffected += rowsAffected;
        }

        if (totalRowsAffected > 0) {
            return ResponseEntity.created(new URI("/api/sales-invoice-line-batches")).body(
                "Successfully created " + totalRowsAffected + " Sales Invoice Line Batches!"
            );
        } else {
            return ResponseEntity.badRequest().body("Failed to create Sales Invoice Line Batches.");
        }
    }

    /**
    
     * {@code POST  /sales-invoice-line-batches} : Create a new salesInvoiceLineBatch.
     *
     * @param salesInvoiceLineBatch the salesInvoiceLineBatch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesInvoiceLineBatch, or with status {@code 400 (Bad Request)} if the salesInvoiceLineBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // @PostMapping("")
    //// public ResponseEntity<SalesInvoiceLineBatch> createSalesInvoiceLineBatch(@RequestBody SalesInvoiceLineBatch salesInvoiceLineBatch)
    //    throws URISyntaxException {
    //    LOG.debug("REST request to save SalesInvoiceLineBatch : {}", salesInvoiceLineBatch);

    //   salesInvoiceLineBatch = salesInvoiceLineBatchService.save(salesInvoiceLineBatch);
    //   return ResponseEntity.created(new URI("/api/sales-invoice-line-batches/" + salesInvoiceLineBatch.getId()))
    //      .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, salesInvoiceLineBatch.getId().toString()))
    //    .body(salesInvoiceLineBatch);
    // }

    /**
     * {@code PUT  /sales-invoice-line-batches/:id} : Updates an existing salesInvoiceLineBatch.
     *
     * @param id the id of the salesInvoiceLineBatch to save.
     * @param salesInvoiceLineBatch the salesInvoiceLineBatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceLineBatch,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceLineBatch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceLineBatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalesInvoiceLineBatch> updateSalesInvoiceLineBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceLineBatch salesInvoiceLineBatch
    ) throws URISyntaxException {
        LOG.debug("REST request to update SalesInvoiceLineBatch : {}, {}", id, salesInvoiceLineBatch);
        if (salesInvoiceLineBatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceLineBatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceLineBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salesInvoiceLineBatch = salesInvoiceLineBatchService.update(salesInvoiceLineBatch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceLineBatch.getId().toString()))
            .body(salesInvoiceLineBatch);
    }

    /**
     * {@code PATCH  /sales-invoice-line-batches/:id} : Partial updates given fields of an existing salesInvoiceLineBatch, field will ignore if it is null
     *
     * @param id the id of the salesInvoiceLineBatch to save.
     * @param salesInvoiceLineBatch the salesInvoiceLineBatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceLineBatch,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceLineBatch is not valid,
     * or with status {@code 404 (Not Found)} if the salesInvoiceLineBatch is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceLineBatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesInvoiceLineBatch> partialUpdateSalesInvoiceLineBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceLineBatch salesInvoiceLineBatch
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SalesInvoiceLineBatch partially : {}, {}", id, salesInvoiceLineBatch);
        if (salesInvoiceLineBatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceLineBatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceLineBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesInvoiceLineBatch> result = salesInvoiceLineBatchService.partialUpdate(salesInvoiceLineBatch);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceLineBatch.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-invoice-line-batches} : get all the salesInvoiceLineBatches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesInvoiceLineBatches in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SalesInvoiceLineBatch>> getAllSalesInvoiceLineBatches(
        SalesInvoiceLineBatchCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SalesInvoiceLineBatches by criteria: {}", criteria);

        Page<SalesInvoiceLineBatch> page = salesInvoiceLineBatchQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-invoice-line-batches/count} : count all the salesInvoiceLineBatches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSalesInvoiceLineBatches(SalesInvoiceLineBatchCriteria criteria) {
        LOG.debug("REST request to count SalesInvoiceLineBatches by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesInvoiceLineBatchQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales-invoice-line-batches/:id} : get the "id" salesInvoiceLineBatch.
     *
     * @param id the id of the salesInvoiceLineBatch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesInvoiceLineBatch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesInvoiceLineBatch> getSalesInvoiceLineBatch(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SalesInvoiceLineBatch : {}", id);
        Optional<SalesInvoiceLineBatch> salesInvoiceLineBatch = salesInvoiceLineBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesInvoiceLineBatch);
    }

    /**
     * {@code DELETE  /sales-invoice-line-batches/:id} : delete the "id" salesInvoiceLineBatch.
     *
     * @param id the id of the salesInvoiceLineBatch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesInvoiceLineBatch(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SalesInvoiceLineBatch : {}", id);
        salesInvoiceLineBatchService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
