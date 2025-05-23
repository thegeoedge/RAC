package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import com.heavenscode.rac.repository.AutojobsaleinvoicecommonservicechargeRepository;
import com.heavenscode.rac.service.AutojobsaleinvoicecommonservicechargeQueryService;
import com.heavenscode.rac.service.AutojobsaleinvoicecommonservicechargeService;
import com.heavenscode.rac.service.criteria.AutojobsaleinvoicecommonservicechargeCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge}.
 */
@RestController
@RequestMapping("/api/autojobsaleinvoicecommonservicecharges")
public class AutojobsaleinvoicecommonservicechargeResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsaleinvoicecommonservicechargeResource.class);

    private static final String ENTITY_NAME = "autojobsaleinvoicecommonservicecharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsaleinvoicecommonservicechargeService autojobsaleinvoicecommonservicechargeService;

    private final AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository;

    private final AutojobsaleinvoicecommonservicechargeQueryService autojobsaleinvoicecommonservicechargeQueryService;

    public AutojobsaleinvoicecommonservicechargeResource(
        AutojobsaleinvoicecommonservicechargeService autojobsaleinvoicecommonservicechargeService,
        AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository,
        AutojobsaleinvoicecommonservicechargeQueryService autojobsaleinvoicecommonservicechargeQueryService
    ) {
        this.autojobsaleinvoicecommonservicechargeService = autojobsaleinvoicecommonservicechargeService;
        this.autojobsaleinvoicecommonservicechargeRepository = autojobsaleinvoicecommonservicechargeRepository;
        this.autojobsaleinvoicecommonservicechargeQueryService = autojobsaleinvoicecommonservicechargeQueryService;
    }

    /**
     * {@code POST  /autojobsaleinvoicecommonservicecharges} : Create a new autojobsaleinvoicecommonservicecharge.
     *
     * @param autojobsaleinvoicecommonservicecharge the autojobsaleinvoicecommonservicecharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsaleinvoicecommonservicecharge, or with status {@code 400 (Bad Request)} if the autojobsaleinvoicecommonservicecharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autojobsaleinvoicecommonservicecharge> createAutojobsaleinvoicecommonservicecharge(
        @RequestBody Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) throws URISyntaxException {
        LOG.debug("REST request to save Autojobsaleinvoicecommonservicecharge : {}", autojobsaleinvoicecommonservicecharge);

        autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeService.save(autojobsaleinvoicecommonservicecharge);
        return ResponseEntity.created(
            new URI("/api/autojobsaleinvoicecommonservicecharges/" + autojobsaleinvoicecommonservicecharge.getInvoiceid())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsaleinvoicecommonservicecharge.getInvoiceid().toString()
                )
            )
            .body(autojobsaleinvoicecommonservicecharge);
    }

    /**
     * {@code PUT  /autojobsaleinvoicecommonservicecharges/:id} : Updates an existing autojobsaleinvoicecommonservicecharge.
     *
     * @param id the id of the autojobsaleinvoicecommonservicecharge to save.
     * @param autojobsaleinvoicecommonservicecharge the autojobsaleinvoicecommonservicecharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsaleinvoicecommonservicecharge,
     * or with status {@code 400 (Bad Request)} if the autojobsaleinvoicecommonservicecharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autojobsaleinvoicecommonservicecharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{invoiceid}")
    public ResponseEntity<Autojobsaleinvoicecommonservicecharge> updateAutojobsaleinvoicecommonservicecharge(
        @PathVariable(value = "invoiceid", required = false) final Integer invoiceid,
        @RequestBody Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to update Autojobsaleinvoicecommonservicecharge : {}, {}",
            invoiceid,
            autojobsaleinvoicecommonservicecharge
        );
        if (autojobsaleinvoicecommonservicecharge.getInvoiceid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invoiceid, autojobsaleinvoicecommonservicecharge.getInvoiceid())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsaleinvoicecommonservicechargeRepository.existsById(invoiceid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeService.update(autojobsaleinvoicecommonservicecharge);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsaleinvoicecommonservicecharge.getInvoiceid().toString()
                )
            )
            .body(autojobsaleinvoicecommonservicecharge);
    }

    /**
     * {@code GET  /autojobsaleinvoicecommonservicecharges/by-invoice-id} : get all the autojobsinvoicelines by invoice ID.
     *
     * @param invoiceID the invoice ID to filter lines.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoicelines in body.
     */
    @GetMapping("/by-invoice-idss")
    public ResponseEntity<List<Autojobsaleinvoicecommonservicecharge>> getAutojobsinvoicelinesByInvoiceId(
        @RequestParam(required = false) Integer invoiceID
    ) {
        LOG.debug("REST request to get Autojobsinvoicelines for InvocieID: {}", invoiceID);

        List<Autojobsaleinvoicecommonservicecharge> result;

        if (invoiceID != null) {
            result = autojobsaleinvoicecommonservicechargeService.fetchJobInvoiceLiness(invoiceID);
        } else {
            result = new ArrayList<>(); // Or optionally fetch all or return error
        }

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /autojobsaleinvoicecommonservicecharges/:id} : Partial updates given fields of an existing autojobsaleinvoicecommonservicecharge, field will ignore if it is null
     *
     * @param id the id of the autojobsaleinvoicecommonservicecharge to save.
     * @param autojobsaleinvoicecommonservicecharge the autojobsaleinvoicecommonservicecharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsaleinvoicecommonservicecharge,
     * or with status {@code 400 (Bad Request)} if the autojobsaleinvoicecommonservicecharge is not valid,
     * or with status {@code 404 (Not Found)} if the autojobsaleinvoicecommonservicecharge is not found,
     * or with status {@code 500 (Internal Server Error)} if the autojobsaleinvoicecommonservicecharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{invoiceid}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsaleinvoicecommonservicecharge> partialUpdateAutojobsaleinvoicecommonservicecharge(
        @PathVariable(value = "invoiceid", required = false) final Integer invoiceid,
        @RequestBody Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update Autojobsaleinvoicecommonservicecharge partially : {}, {}",
            invoiceid,
            autojobsaleinvoicecommonservicecharge
        );
        if (autojobsaleinvoicecommonservicecharge.getInvoiceid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invoiceid, autojobsaleinvoicecommonservicecharge.getInvoiceid())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsaleinvoicecommonservicechargeRepository.existsById(invoiceid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsaleinvoicecommonservicecharge> result = autojobsaleinvoicecommonservicechargeService.partialUpdate(
            autojobsaleinvoicecommonservicecharge
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                autojobsaleinvoicecommonservicecharge.getInvoiceid().toString()
            )
        );
    }

    /**
     * {@code GET  /autojobsaleinvoicecommonservicecharges} : get all the autojobsaleinvoicecommonservicecharges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsaleinvoicecommonservicecharges in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsaleinvoicecommonservicecharge>> getAllAutojobsaleinvoicecommonservicecharges(
        AutojobsaleinvoicecommonservicechargeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autojobsaleinvoicecommonservicecharges by criteria: {}", criteria);

        Page<Autojobsaleinvoicecommonservicecharge> page = autojobsaleinvoicecommonservicechargeQueryService.findByCriteria(
            criteria,
            pageable
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autojobsaleinvoicecommonservicecharges/count} : count all the autojobsaleinvoicecommonservicecharges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutojobsaleinvoicecommonservicecharges(AutojobsaleinvoicecommonservicechargeCriteria criteria) {
        LOG.debug("REST request to count Autojobsaleinvoicecommonservicecharges by criteria: {}", criteria);
        return ResponseEntity.ok().body(autojobsaleinvoicecommonservicechargeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autojobsaleinvoicecommonservicecharges/:id} : get the "id" autojobsaleinvoicecommonservicecharge.
     *
     * @param id the id of the autojobsaleinvoicecommonservicecharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsaleinvoicecommonservicecharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{invoiceid}")
    public ResponseEntity<Autojobsaleinvoicecommonservicecharge> getAutojobsaleinvoicecommonservicecharge(
        @PathVariable("invoiceid") Integer invoiceid
    ) {
        LOG.debug("REST request to get Autojobsaleinvoicecommonservicecharge : {}", invoiceid);
        Optional<Autojobsaleinvoicecommonservicecharge> autojobsaleinvoicecommonservicecharge =
            autojobsaleinvoicecommonservicechargeService.findOne(invoiceid);
        return ResponseUtil.wrapOrNotFound(autojobsaleinvoicecommonservicecharge);
    }
    /**
     * {@code DELETE  /autojobsaleinvoicecommonservicecharges/:id} : delete the "id" autojobsaleinvoicecommonservicecharge.
     *
     * @param id the id of the autojobsaleinvoicecommonservicecharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsaleinvoicecommonservicecharge(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autojobsaleinvoicecommonservicecharge : {}", id);
        autojobsaleinvoicecommonservicechargeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }*/
}
