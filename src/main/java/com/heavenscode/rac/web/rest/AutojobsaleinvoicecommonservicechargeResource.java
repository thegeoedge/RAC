package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import com.heavenscode.rac.repository.AutojobsaleinvoicecommonservicechargeRepository;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge}.
 */
@RestController
@RequestMapping("/api/autojobsaleinvoicecommonservicecharges")
@Transactional
public class AutojobsaleinvoicecommonservicechargeResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsaleinvoicecommonservicechargeResource.class);

    private static final String ENTITY_NAME = "autojobsaleinvoicecommonservicecharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository;

    public AutojobsaleinvoicecommonservicechargeResource(
        AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository
    ) {
        this.autojobsaleinvoicecommonservicechargeRepository = autojobsaleinvoicecommonservicechargeRepository;
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
        if (autojobsaleinvoicecommonservicecharge.getId() != null) {
            throw new BadRequestAlertException(
                "A new autojobsaleinvoicecommonservicecharge cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.save(autojobsaleinvoicecommonservicecharge);
        return ResponseEntity.created(
            new URI("/api/autojobsaleinvoicecommonservicecharges/" + autojobsaleinvoicecommonservicecharge.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsaleinvoicecommonservicecharge.getId().toString()
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
    @PutMapping("/{id}")
    public ResponseEntity<Autojobsaleinvoicecommonservicecharge> updateAutojobsaleinvoicecommonservicecharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsaleinvoicecommonservicecharge : {}, {}", id, autojobsaleinvoicecommonservicecharge);
        if (autojobsaleinvoicecommonservicecharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsaleinvoicecommonservicecharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsaleinvoicecommonservicechargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.save(autojobsaleinvoicecommonservicecharge);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsaleinvoicecommonservicecharge.getId().toString()
                )
            )
            .body(autojobsaleinvoicecommonservicecharge);
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
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsaleinvoicecommonservicecharge> partialUpdateAutojobsaleinvoicecommonservicecharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update Autojobsaleinvoicecommonservicecharge partially : {}, {}",
            id,
            autojobsaleinvoicecommonservicecharge
        );
        if (autojobsaleinvoicecommonservicecharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsaleinvoicecommonservicecharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsaleinvoicecommonservicechargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsaleinvoicecommonservicecharge> result = autojobsaleinvoicecommonservicechargeRepository
            .findById(autojobsaleinvoicecommonservicecharge.getId())
            .map(existingAutojobsaleinvoicecommonservicecharge -> {
                if (autojobsaleinvoicecommonservicecharge.getInvoiceid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setInvoiceid(autojobsaleinvoicecommonservicecharge.getInvoiceid());
                }
                if (autojobsaleinvoicecommonservicecharge.getLineid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setLineid(autojobsaleinvoicecommonservicecharge.getLineid());
                }
                if (autojobsaleinvoicecommonservicecharge.getOptionid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setOptionid(autojobsaleinvoicecommonservicecharge.getOptionid());
                }
                if (autojobsaleinvoicecommonservicecharge.getMainid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setMainid(autojobsaleinvoicecommonservicecharge.getMainid());
                }
                if (autojobsaleinvoicecommonservicecharge.getCode() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setCode(autojobsaleinvoicecommonservicecharge.getCode());
                }
                if (autojobsaleinvoicecommonservicecharge.getName() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setName(autojobsaleinvoicecommonservicecharge.getName());
                }
                if (autojobsaleinvoicecommonservicecharge.getDescription() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setDescription(autojobsaleinvoicecommonservicecharge.getDescription());
                }
                if (autojobsaleinvoicecommonservicecharge.getValue() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setValue(autojobsaleinvoicecommonservicecharge.getValue());
                }
                if (autojobsaleinvoicecommonservicecharge.getAddedbyid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setAddedbyid(autojobsaleinvoicecommonservicecharge.getAddedbyid());
                }
                if (autojobsaleinvoicecommonservicecharge.getDiscount() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setDiscount(autojobsaleinvoicecommonservicecharge.getDiscount());
                }
                if (autojobsaleinvoicecommonservicecharge.getServiceprice() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setServiceprice(autojobsaleinvoicecommonservicecharge.getServiceprice());
                }

                return existingAutojobsaleinvoicecommonservicecharge;
            })
            .map(autojobsaleinvoicecommonservicechargeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                autojobsaleinvoicecommonservicecharge.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /autojobsaleinvoicecommonservicecharges} : get all the autojobsaleinvoicecommonservicecharges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsaleinvoicecommonservicecharges in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsaleinvoicecommonservicecharge>> getAllAutojobsaleinvoicecommonservicecharges(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of Autojobsaleinvoicecommonservicecharges");
        Page<Autojobsaleinvoicecommonservicecharge> page = autojobsaleinvoicecommonservicechargeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autojobsaleinvoicecommonservicecharges/:id} : get the "id" autojobsaleinvoicecommonservicecharge.
     *
     * @param id the id of the autojobsaleinvoicecommonservicecharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsaleinvoicecommonservicecharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autojobsaleinvoicecommonservicecharge> getAutojobsaleinvoicecommonservicecharge(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Autojobsaleinvoicecommonservicecharge : {}", id);
        Optional<Autojobsaleinvoicecommonservicecharge> autojobsaleinvoicecommonservicecharge =
            autojobsaleinvoicecommonservicechargeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autojobsaleinvoicecommonservicecharge);
    }

    /**
     * {@code DELETE  /autojobsaleinvoicecommonservicecharges/:id} : delete the "id" autojobsaleinvoicecommonservicecharge.
     *
     * @param id the id of the autojobsaleinvoicecommonservicecharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsaleinvoicecommonservicecharge(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autojobsaleinvoicecommonservicecharge : {}", id);
        autojobsaleinvoicecommonservicechargeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
