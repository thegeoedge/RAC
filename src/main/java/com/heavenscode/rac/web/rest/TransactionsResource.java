package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Transactions;
import com.heavenscode.rac.repository.TransactionsRepository;
import com.heavenscode.rac.service.TransactionsQueryService;
import com.heavenscode.rac.service.TransactionsService;
import com.heavenscode.rac.service.criteria.TransactionsCriteria;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.Transactions}.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionsResource {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsResource.class);

    private static final String ENTITY_NAME = "transactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionsService transactionsService;

    private final TransactionsRepository transactionsRepository;

    private final TransactionsQueryService transactionsQueryService;

    public TransactionsResource(
        TransactionsService transactionsService,
        TransactionsRepository transactionsRepository,
        TransactionsQueryService transactionsQueryService
    ) {
        this.transactionsService = transactionsService;
        this.transactionsRepository = transactionsRepository;
        this.transactionsQueryService = transactionsQueryService;
    }

    /**
     * {@code POST  /transactions} : Create a new transactions.
     *
     * @param transactions the transactions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactions, or with status {@code 400 (Bad Request)} if the transactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Transactions> createTransactions(@RequestBody Transactions transactions) throws URISyntaxException {
        LOG.debug("REST request to save Transactions : {}", transactions);

        transactions = transactionsService.save(transactions);
        return ResponseEntity.created(new URI("/api/transactions/" + transactions.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, transactions.getId().toString()))
            .body(transactions);
    }

    /**
     * {@code PUT  /transactions/:id} : Updates an existing transactions.
     *
     * @param id the id of the transactions to save.
     * @param transactions the transactions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactions,
     * or with status {@code 400 (Bad Request)} if the transactions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Transactions> updateTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transactions transactions
    ) throws URISyntaxException {
        LOG.debug("REST request to update Transactions : {}, {}", id, transactions);
        if (transactions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        transactions = transactionsService.update(transactions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactions.getId().toString()))
            .body(transactions);
    }

    /**
     * {@code PATCH  /transactions/:id} : Partial updates given fields of an existing transactions, field will ignore if it is null
     *
     * @param id the id of the transactions to save.
     * @param transactions the transactions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactions,
     * or with status {@code 400 (Bad Request)} if the transactions is not valid,
     * or with status {@code 404 (Not Found)} if the transactions is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Transactions> partialUpdateTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transactions transactions
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Transactions partially : {}, {}", id, transactions);
        if (transactions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Transactions> result = transactionsService.partialUpdate(transactions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactions.getId().toString())
        );
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Transactions>> getAllTransactions(TransactionsCriteria criteria) {
        LOG.debug("REST request to get Transactions by criteria: {}", criteria);

        List<Transactions> entityList = transactionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /transactions/count} : count all the transactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTransactions(TransactionsCriteria criteria) {
        LOG.debug("REST request to count Transactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transactions/:id} : get the "id" transactions.
     *
     * @param id the id of the transactions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transactions> getTransactions(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Transactions : {}", id);
        Optional<Transactions> transactions = transactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactions);
    }

    /**
     * {@code DELETE  /transactions/:id} : delete the "id" transactions.
     *
     * @param id the id of the transactions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactions(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Transactions : {}", id);
        transactionsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
