package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeDummyRepository;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy}.
 */
@RestController
@RequestMapping("/api/sale-invoice-common-service-charge-dummies")
@Transactional
public class SaleInvoiceCommonServiceChargeDummyResource {

    private static final Logger LOG = LoggerFactory.getLogger(SaleInvoiceCommonServiceChargeDummyResource.class);

    private static final String ENTITY_NAME = "saleInvoiceCommonServiceChargeDummy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository;

    public SaleInvoiceCommonServiceChargeDummyResource(
        SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository
    ) {
        this.saleInvoiceCommonServiceChargeDummyRepository = saleInvoiceCommonServiceChargeDummyRepository;
    }

    /**
     * {@code POST  /sale-invoice-common-service-charge-dummies} : Create a new saleInvoiceCommonServiceChargeDummy.
     *
     * @param saleInvoiceCommonServiceChargeDummy the saleInvoiceCommonServiceChargeDummy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saleInvoiceCommonServiceChargeDummy, or with status {@code 400 (Bad Request)} if the saleInvoiceCommonServiceChargeDummy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaleInvoiceCommonServiceChargeDummy> createSaleInvoiceCommonServiceChargeDummy(
        @RequestBody SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to save SaleInvoiceCommonServiceChargeDummy : {}", saleInvoiceCommonServiceChargeDummy);
        if (saleInvoiceCommonServiceChargeDummy.getId() != null) {
            throw new BadRequestAlertException(
                "A new saleInvoiceCommonServiceChargeDummy cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        saleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.save(saleInvoiceCommonServiceChargeDummy);
        return ResponseEntity.created(
            new URI("/api/sale-invoice-common-service-charge-dummies/" + saleInvoiceCommonServiceChargeDummy.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    saleInvoiceCommonServiceChargeDummy.getId().toString()
                )
            )
            .body(saleInvoiceCommonServiceChargeDummy);
    }

    /**
     * {@code PUT  /sale-invoice-common-service-charge-dummies/:id} : Updates an existing saleInvoiceCommonServiceChargeDummy.
     *
     * @param id the id of the saleInvoiceCommonServiceChargeDummy to save.
     * @param saleInvoiceCommonServiceChargeDummy the saleInvoiceCommonServiceChargeDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleInvoiceCommonServiceChargeDummy,
     * or with status {@code 400 (Bad Request)} if the saleInvoiceCommonServiceChargeDummy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saleInvoiceCommonServiceChargeDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SaleInvoiceCommonServiceChargeDummy> updateSaleInvoiceCommonServiceChargeDummy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to update SaleInvoiceCommonServiceChargeDummy : {}, {}", id, saleInvoiceCommonServiceChargeDummy);
        if (saleInvoiceCommonServiceChargeDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleInvoiceCommonServiceChargeDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleInvoiceCommonServiceChargeDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.save(saleInvoiceCommonServiceChargeDummy);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    saleInvoiceCommonServiceChargeDummy.getId().toString()
                )
            )
            .body(saleInvoiceCommonServiceChargeDummy);
    }

    /**
     * {@code PATCH  /sale-invoice-common-service-charge-dummies/:id} : Partial updates given fields of an existing saleInvoiceCommonServiceChargeDummy, field will ignore if it is null
     *
     * @param id the id of the saleInvoiceCommonServiceChargeDummy to save.
     * @param saleInvoiceCommonServiceChargeDummy the saleInvoiceCommonServiceChargeDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleInvoiceCommonServiceChargeDummy,
     * or with status {@code 400 (Bad Request)} if the saleInvoiceCommonServiceChargeDummy is not valid,
     * or with status {@code 404 (Not Found)} if the saleInvoiceCommonServiceChargeDummy is not found,
     * or with status {@code 500 (Internal Server Error)} if the saleInvoiceCommonServiceChargeDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleInvoiceCommonServiceChargeDummy> partialUpdateSaleInvoiceCommonServiceChargeDummy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update SaleInvoiceCommonServiceChargeDummy partially : {}, {}",
            id,
            saleInvoiceCommonServiceChargeDummy
        );
        if (saleInvoiceCommonServiceChargeDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleInvoiceCommonServiceChargeDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleInvoiceCommonServiceChargeDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleInvoiceCommonServiceChargeDummy> result = saleInvoiceCommonServiceChargeDummyRepository
            .findById(saleInvoiceCommonServiceChargeDummy.getId())
            .map(existingSaleInvoiceCommonServiceChargeDummy -> {
                if (saleInvoiceCommonServiceChargeDummy.getInvoiceid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setInvoiceid(saleInvoiceCommonServiceChargeDummy.getInvoiceid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getLineid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setLineid(saleInvoiceCommonServiceChargeDummy.getLineid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getOptionid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setOptionid(saleInvoiceCommonServiceChargeDummy.getOptionid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getMainid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setMainid(saleInvoiceCommonServiceChargeDummy.getMainid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getCode() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setCode(saleInvoiceCommonServiceChargeDummy.getCode());
                }
                if (saleInvoiceCommonServiceChargeDummy.getName() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setName(saleInvoiceCommonServiceChargeDummy.getName());
                }
                if (saleInvoiceCommonServiceChargeDummy.getDescription() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setDescription(saleInvoiceCommonServiceChargeDummy.getDescription());
                }
                if (saleInvoiceCommonServiceChargeDummy.getValue() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setValue(saleInvoiceCommonServiceChargeDummy.getValue());
                }

                return existingSaleInvoiceCommonServiceChargeDummy;
            })
            .map(saleInvoiceCommonServiceChargeDummyRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleInvoiceCommonServiceChargeDummy.getId().toString())
        );
    }

    /**
     * {@code GET  /sale-invoice-common-service-charge-dummies} : get all the saleInvoiceCommonServiceChargeDummies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saleInvoiceCommonServiceChargeDummies in body.
     */
    @GetMapping("")
    public List<SaleInvoiceCommonServiceChargeDummy> getAllSaleInvoiceCommonServiceChargeDummies() {
        LOG.debug("REST request to get all SaleInvoiceCommonServiceChargeDummies");
        return saleInvoiceCommonServiceChargeDummyRepository.findAll();
    }

    /**
     * {@code GET  /sale-invoice-common-service-charge-dummies/:id} : get the "id" saleInvoiceCommonServiceChargeDummy.
     *
     * @param id the id of the saleInvoiceCommonServiceChargeDummy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saleInvoiceCommonServiceChargeDummy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SaleInvoiceCommonServiceChargeDummy> getSaleInvoiceCommonServiceChargeDummy(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SaleInvoiceCommonServiceChargeDummy : {}", id);
        Optional<SaleInvoiceCommonServiceChargeDummy> saleInvoiceCommonServiceChargeDummy =
            saleInvoiceCommonServiceChargeDummyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(saleInvoiceCommonServiceChargeDummy);
    }

    /**
     * {@code DELETE  /sale-invoice-common-service-charge-dummies/:id} : delete the "id" saleInvoiceCommonServiceChargeDummy.
     *
     * @param id the id of the saleInvoiceCommonServiceChargeDummy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleInvoiceCommonServiceChargeDummy(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SaleInvoiceCommonServiceChargeDummy : {}", id);
        saleInvoiceCommonServiceChargeDummyRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
