package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
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
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class AutojobsalesinvoiceservicechargelineResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsalesinvoiceservicechargelineResource.class);

    private static final String ENTITY_NAME = "autojobsalesinvoiceservicechargeline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;

    public AutojobsalesinvoiceservicechargelineResource(
        AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository
    ) {
        this.autojobsalesinvoiceservicechargelineRepository = autojobsalesinvoiceservicechargelineRepository;
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
        if (autojobsalesinvoiceservicechargeline.getId() != null) {
            throw new BadRequestAlertException(
                "A new autojobsalesinvoiceservicechargeline cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        autojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.save(autojobsalesinvoiceservicechargeline);
        return ResponseEntity.created(new URI("/api/autojobsalesinvoiceservicechargelines/" + autojobsalesinvoiceservicechargeline.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsalesinvoiceservicechargeline.getId().toString()
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
    @PutMapping("/{id}")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> updateAutojobsalesinvoiceservicechargeline(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsalesinvoiceservicechargeline : {}, {}", id, autojobsalesinvoiceservicechargeline);
        if (autojobsalesinvoiceservicechargeline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsalesinvoiceservicechargeline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsalesinvoiceservicechargelineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.save(autojobsalesinvoiceservicechargeline);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsalesinvoiceservicechargeline.getId().toString()
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
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> partialUpdateAutojobsalesinvoiceservicechargeline(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update Autojobsalesinvoiceservicechargeline partially : {}, {}",
            id,
            autojobsalesinvoiceservicechargeline
        );
        if (autojobsalesinvoiceservicechargeline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsalesinvoiceservicechargeline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsalesinvoiceservicechargelineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsalesinvoiceservicechargeline> result = autojobsalesinvoiceservicechargelineRepository
            .findById(autojobsalesinvoiceservicechargeline.getId())
            .map(existingAutojobsalesinvoiceservicechargeline -> {
                if (autojobsalesinvoiceservicechargeline.getInvoiceid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setInvoiceid(autojobsalesinvoiceservicechargeline.getInvoiceid());
                }
                if (autojobsalesinvoiceservicechargeline.getLineid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setLineid(autojobsalesinvoiceservicechargeline.getLineid());
                }
                if (autojobsalesinvoiceservicechargeline.getOptionid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setOptionid(autojobsalesinvoiceservicechargeline.getOptionid());
                }
                if (autojobsalesinvoiceservicechargeline.getServicename() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServicename(autojobsalesinvoiceservicechargeline.getServicename());
                }
                if (autojobsalesinvoiceservicechargeline.getServicediscription() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServicediscription(
                        autojobsalesinvoiceservicechargeline.getServicediscription()
                    );
                }
                if (autojobsalesinvoiceservicechargeline.getValue() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setValue(autojobsalesinvoiceservicechargeline.getValue());
                }
                if (autojobsalesinvoiceservicechargeline.getAddedbyid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setAddedbyid(autojobsalesinvoiceservicechargeline.getAddedbyid());
                }
                if (autojobsalesinvoiceservicechargeline.getIscustomersrvice() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setIscustomersrvice(
                        autojobsalesinvoiceservicechargeline.getIscustomersrvice()
                    );
                }
                if (autojobsalesinvoiceservicechargeline.getDiscount() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setDiscount(autojobsalesinvoiceservicechargeline.getDiscount());
                }
                if (autojobsalesinvoiceservicechargeline.getServiceprice() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServiceprice(autojobsalesinvoiceservicechargeline.getServiceprice());
                }

                return existingAutojobsalesinvoiceservicechargeline;
            })
            .map(autojobsalesinvoiceservicechargelineRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsalesinvoiceservicechargeline.getId().toString())
        );
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines} : get all the autojobsalesinvoiceservicechargelines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsalesinvoiceservicechargelines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsalesinvoiceservicechargeline>> getAllAutojobsalesinvoiceservicechargelines(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of Autojobsalesinvoiceservicechargelines");
        Page<Autojobsalesinvoiceservicechargeline> page = autojobsalesinvoiceservicechargelineRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines/:id} : get the "id" autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsalesinvoiceservicechargeline, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> getAutojobsalesinvoiceservicechargeline(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Autojobsalesinvoiceservicechargeline : {}", id);
        Optional<Autojobsalesinvoiceservicechargeline> autojobsalesinvoiceservicechargeline =
            autojobsalesinvoiceservicechargelineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autojobsalesinvoiceservicechargeline);
    }

    /**
     * {@code DELETE  /autojobsalesinvoiceservicechargelines/:id} : delete the "id" autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsalesinvoiceservicechargeline(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autojobsalesinvoiceservicechargeline : {}", id);
        autojobsalesinvoiceservicechargelineRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
