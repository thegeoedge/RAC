package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.BinCard;
import com.heavenscode.rac.repository.BinCardRepository;
import com.heavenscode.rac.service.BinCardQueryService;
import com.heavenscode.rac.service.BinCardService;
import com.heavenscode.rac.service.criteria.BinCardCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.BinCard}.
 */
@RestController
@RequestMapping("/api/bin-cards")
public class BinCardResource {

    private static final Logger LOG = LoggerFactory.getLogger(BinCardResource.class);

    private static final String ENTITY_NAME = "binCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BinCardService binCardService;

    private final BinCardRepository binCardRepository;

    private final BinCardQueryService binCardQueryService;

    public BinCardResource(BinCardService binCardService, BinCardRepository binCardRepository, BinCardQueryService binCardQueryService) {
        this.binCardService = binCardService;
        this.binCardRepository = binCardRepository;
        this.binCardQueryService = binCardQueryService;
    }

    /**
     * {@code POST  /bin-cards} : Create a new binCard.
     *
     * @param binCard the binCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new binCard, or with status {@code 400 (Bad Request)} if the binCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BinCard> createBinCard(@RequestBody BinCard binCard) throws URISyntaxException {
        LOG.debug("REST request to save BinCard : {}", binCard);
        if (binCard.getId() != null) {
            throw new BadRequestAlertException("A new binCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        binCard = binCardService.save(binCard);
        return ResponseEntity.created(new URI("/api/bin-cards/" + binCard.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, binCard.getId().toString()))
            .body(binCard);
    }

    /**
     * {@code PUT  /bin-cards/:id} : Updates an existing binCard.
     *
     * @param id the id of the binCard to save.
     * @param binCard the binCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated binCard,
     * or with status {@code 400 (Bad Request)} if the binCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the binCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BinCard> updateBinCard(@PathVariable(value = "id", required = false) final Long id, @RequestBody BinCard binCard)
        throws URISyntaxException {
        LOG.debug("REST request to update BinCard : {}, {}", id, binCard);
        if (binCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, binCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!binCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        binCard = binCardService.update(binCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, binCard.getId().toString()))
            .body(binCard);
    }

    /**
     * {@code PATCH  /bin-cards/:id} : Partial updates given fields of an existing binCard, field will ignore if it is null
     *
     * @param id the id of the binCard to save.
     * @param binCard the binCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated binCard,
     * or with status {@code 400 (Bad Request)} if the binCard is not valid,
     * or with status {@code 404 (Not Found)} if the binCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the binCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BinCard> partialUpdateBinCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BinCard binCard
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BinCard partially : {}, {}", id, binCard);
        if (binCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, binCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!binCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BinCard> result = binCardService.partialUpdate(binCard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, binCard.getId().toString())
        );
    }

    /**
     * {@code GET  /bin-cards} : get all the binCards.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of binCards in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BinCard>> getAllBinCards(
        BinCardCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get BinCards by criteria: {}", criteria);

        Page<BinCard> page = binCardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bin-cards/count} : count all the binCards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBinCards(BinCardCriteria criteria) {
        LOG.debug("REST request to count BinCards by criteria: {}", criteria);
        return ResponseEntity.ok().body(binCardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bin-cards/:id} : get the "id" binCard.
     *
     * @param id the id of the binCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the binCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BinCard> getBinCard(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BinCard : {}", id);
        Optional<BinCard> binCard = binCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(binCard);
    }

    /**
     * {@code DELETE  /bin-cards/:id} : delete the "id" binCard.
     *
     * @param id the id of the binCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBinCard(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BinCard : {}", id);
        binCardService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
