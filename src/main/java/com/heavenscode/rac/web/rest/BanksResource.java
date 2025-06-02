package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Banks;
import com.heavenscode.rac.repository.BanksRepository;
import com.heavenscode.rac.service.BanksQueryService;
import com.heavenscode.rac.service.BanksService;
import com.heavenscode.rac.service.criteria.BanksCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Banks}.
 */
@RestController
@RequestMapping("/api/banks")
public class BanksResource {

    private static final Logger LOG = LoggerFactory.getLogger(BanksResource.class);

    private static final String ENTITY_NAME = "banks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BanksService banksService;

    private final BanksRepository banksRepository;

    private final BanksQueryService banksQueryService;

    public BanksResource(BanksService banksService, BanksRepository banksRepository, BanksQueryService banksQueryService) {
        this.banksService = banksService;
        this.banksRepository = banksRepository;
        this.banksQueryService = banksQueryService;
    }

    /**
     * {@code POST  /banks} : Create a new banks.
     *
     * @param banks the banks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banks, or with status {@code 400 (Bad Request)} if the banks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Banks> createBanks(@RequestBody Banks banks) throws URISyntaxException {
        LOG.debug("REST request to save Banks : {}", banks);
        if (banks.getId() != null) {
            throw new BadRequestAlertException("A new banks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        banks = banksService.save(banks);
        return ResponseEntity.created(new URI("/api/banks/" + banks.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, banks.getId().toString()))
            .body(banks);
    }

    /**
     * {@code PUT  /banks/:id} : Updates an existing banks.
     *
     * @param id the id of the banks to save.
     * @param banks the banks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banks,
     * or with status {@code 400 (Bad Request)} if the banks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Banks> updateBanks(@PathVariable(value = "id", required = false) final Long id, @RequestBody Banks banks)
        throws URISyntaxException {
        LOG.debug("REST request to update Banks : {}, {}", id, banks);
        if (banks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        banks = banksService.update(banks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, banks.getId().toString()))
            .body(banks);
    }

    /**
     * {@code PATCH  /banks/:id} : Partial updates given fields of an existing banks, field will ignore if it is null
     *
     * @param id the id of the banks to save.
     * @param banks the banks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banks,
     * or with status {@code 400 (Bad Request)} if the banks is not valid,
     * or with status {@code 404 (Not Found)} if the banks is not found,
     * or with status {@code 500 (Internal Server Error)} if the banks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Banks> partialUpdateBanks(@PathVariable(value = "id", required = false) final Long id, @RequestBody Banks banks)
        throws URISyntaxException {
        LOG.debug("REST request to partial update Banks partially : {}, {}", id, banks);
        if (banks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banks.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Banks> result = banksService.partialUpdate(banks);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, banks.getId().toString())
        );
    }

    /**
     * {@code GET  /banks} : get all the banks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Banks>> getAllBanks(
        BanksCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Banks by criteria: {}", criteria);

        Page<Banks> page = banksQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banks/count} : count all the banks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBanks(BanksCriteria criteria) {
        LOG.debug("REST request to count Banks by criteria: {}", criteria);
        return ResponseEntity.ok().body(banksQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /banks/:id} : get the "id" banks.
     *
     * @param id the id of the banks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Banks> getBanks(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Banks : {}", id);
        Optional<Banks> banks = banksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banks);
    }

    /**
     * {@code DELETE  /banks/:id} : delete the "id" banks.
     *
     * @param id the id of the banks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanks(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Banks : {}", id);
        banksService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
