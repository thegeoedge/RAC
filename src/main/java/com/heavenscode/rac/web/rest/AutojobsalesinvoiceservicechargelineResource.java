package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
import com.heavenscode.rac.service.AutojobsalesinvoiceservicechargelineQueryService;
import com.heavenscode.rac.service.AutojobsalesinvoiceservicechargelineService;
import com.heavenscode.rac.service.criteria.AutojobsalesinvoiceservicechargelineCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline}.
 */
@RestController
@RequestMapping("/api/autojobsalesinvoiceservicechargelines")
public class AutojobsalesinvoiceservicechargelineResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsalesinvoiceservicechargelineResource.class);

    private static final String ENTITY_NAME = "autojobsalesinvoiceservicechargeline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsalesinvoiceservicechargelineService autojobsalesinvoiceservicechargelineService;

    private final AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;

    private final AutojobsalesinvoiceservicechargelineQueryService autojobsalesinvoiceservicechargelineQueryService;

    public AutojobsalesinvoiceservicechargelineResource(
        AutojobsalesinvoiceservicechargelineService autojobsalesinvoiceservicechargelineService,
        AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository,
        AutojobsalesinvoiceservicechargelineQueryService autojobsalesinvoiceservicechargelineQueryService
    ) {
        this.autojobsalesinvoiceservicechargelineService = autojobsalesinvoiceservicechargelineService;
        this.autojobsalesinvoiceservicechargelineRepository = autojobsalesinvoiceservicechargelineRepository;
        this.autojobsalesinvoiceservicechargelineQueryService = autojobsalesinvoiceservicechargelineQueryService;
    }

    /**
     * {@code POST  /autojobsalesinvoiceservicechargelines} : Create a new autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the autojobsalesinvoiceservicechargeline to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsalesinvoiceservicechargeline, or with status {@code 400 (Bad Request)} if the autojobsalesinvoiceservicechargeline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> createAutojobsalesinvoiceservicechargeline(
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug("REST request to save Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);

        autojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineService.save(autojobsalesinvoiceservicechargeline);
        return ResponseEntity.created(
            new URI("/api/autojobsalesinvoiceservicechargelines/" + autojobsalesinvoiceservicechargeline.getInvoiceid())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsalesinvoiceservicechargeline.getInvoiceid().toString()
                )
            )
            .body(autojobsalesinvoiceservicechargeline);
    }

    /**
     * {@code PUT  /autojobsalesinvoiceservicechargelines/:id} : Updates an existing autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to save.
     * @param autojobsalesinvoiceservicechargeline the autojobsalesinvoiceservicechargeline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsalesinvoiceservicechargeline,
     * or with status {@code 400 (Bad Request)} if the autojobsalesinvoiceservicechargeline is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autojobsalesinvoiceservicechargeline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{invoiceid}")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> updateAutojobsalesinvoiceservicechargeline(
        @PathVariable(value = "invoiceid", required = false) final Integer invoiceid,
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsalesinvoiceservicechargeline : {}, {}", invoiceid, autojobsalesinvoiceservicechargeline);
        if (autojobsalesinvoiceservicechargeline.getInvoiceid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invoiceid, autojobsalesinvoiceservicechargeline.getInvoiceid())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsalesinvoiceservicechargelineRepository.existsById(invoiceid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineService.update(autojobsalesinvoiceservicechargeline);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsalesinvoiceservicechargeline.getInvoiceid().toString()
                )
            )
            .body(autojobsalesinvoiceservicechargeline);
    }

    /**
     * {@code PATCH  /autojobsalesinvoiceservicechargelines/:id} : Partial updates given fields of an existing autojobsalesinvoiceservicechargeline, field will ignore if it is null
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to save.
     * @param autojobsalesinvoiceservicechargeline the autojobsalesinvoiceservicechargeline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsalesinvoiceservicechargeline,
     * or with status {@code 400 (Bad Request)} if the autojobsalesinvoiceservicechargeline is not valid,
     * or with status {@code 404 (Not Found)} if the autojobsalesinvoiceservicechargeline is not found,
     * or with status {@code 500 (Internal Server Error)} if the autojobsalesinvoiceservicechargeline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{invoiceid}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> partialUpdateAutojobsalesinvoiceservicechargeline(
        @PathVariable(value = "invoiceid", required = false) final Integer invoiceid,
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update Autojobsalesinvoiceservicechargeline partially : {}, {}",
            invoiceid,
            autojobsalesinvoiceservicechargeline
        );
        if (autojobsalesinvoiceservicechargeline.getInvoiceid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(invoiceid, autojobsalesinvoiceservicechargeline.getInvoiceid())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsalesinvoiceservicechargelineRepository.existsById(invoiceid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsalesinvoiceservicechargeline> result = autojobsalesinvoiceservicechargelineService.partialUpdate(
            autojobsalesinvoiceservicechargeline
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                autojobsalesinvoiceservicechargeline.getInvoiceid().toString()
            )
        );
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines} : get all the autojobsalesinvoiceservicechargelines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsalesinvoiceservicechargelines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsalesinvoiceservicechargeline>> getAllAutojobsalesinvoiceservicechargelines(
        AutojobsalesinvoiceservicechargelineCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autojobsalesinvoiceservicechargelines by criteria: {}", criteria);

        Page<Autojobsalesinvoiceservicechargeline> page = autojobsalesinvoiceservicechargelineQueryService.findByCriteria(
            criteria,
            pageable
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines/count} : count all the autojobsalesinvoiceservicechargelines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> countAutojobsalesinvoiceservicechargelines(AutojobsalesinvoiceservicechargelineCriteria criteria) {
        Long count = autojobsalesinvoiceservicechargelineQueryService.countByCriteria(criteria);
        return ResponseEntity.ok().body(count.intValue());
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines/by-invoice-id} : get all the autojobsinvoicelines by invoice ID.
     *
     * @param invoiceID the invoice ID to filter lines.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoicelines in body.
     */
    @GetMapping("/by-invoice-ids")
    public ResponseEntity<List<Autojobsalesinvoiceservicechargeline>> getAutojobsinvoicelinesByInvoiceId(
        @RequestParam(required = false) Integer invoiceID
    ) {
        LOG.debug("REST request to get Autojobsinvoicelines for InvocieID: {}", invoiceID);

        List<Autojobsalesinvoiceservicechargeline> result;

        if (invoiceID != null) {
            result = autojobsalesinvoiceservicechargelineService.fetchServiceChargeLines(invoiceID);
        } else {
            result = new ArrayList<>(); // Or optionally fetch all or return error
        }

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines/:id} : get the "id" autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsalesinvoiceservicechargeline, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{invoiceid}")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> getAutojobsalesinvoiceservicechargeline(
        @PathVariable("invoiceid") Integer invoiceid
    ) {
        LOG.debug("REST request to get Autojobsalesinvoiceservicechargeline : {}", invoiceid);
        Optional<Autojobsalesinvoiceservicechargeline> autojobsalesinvoiceservicechargeline =
            autojobsalesinvoiceservicechargelineService.findOne(invoiceid);
        return ResponseUtil.wrapOrNotFound(autojobsalesinvoiceservicechargeline);
    }
    /**
     * {@code DELETE  /autojobsalesinvoiceservicechargelines/:id} : delete the "id" autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsalesinvoiceservicechargeline(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete Autojobsalesinvoiceservicechargeline : {}", id);
        autojobsalesinvoiceservicechargelineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    } */
}
