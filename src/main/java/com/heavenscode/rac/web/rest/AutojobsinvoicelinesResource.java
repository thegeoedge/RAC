package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsinvoicelines;
import com.heavenscode.rac.repository.AutojobsinvoicelinesRepository;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsinvoicelines}.
 */
@RestController
@RequestMapping("/api/autojobsinvoicelines")
@Transactional
public class AutojobsinvoicelinesResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinesResource.class);

    private static final String ENTITY_NAME = "autojobsinvoicelines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsinvoicelinesRepository autojobsinvoicelinesRepository;

    public AutojobsinvoicelinesResource(AutojobsinvoicelinesRepository autojobsinvoicelinesRepository) {
        this.autojobsinvoicelinesRepository = autojobsinvoicelinesRepository;
    }

    /**
     * {@code POST  /autojobsinvoicelines} : Create a new autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the autojobsinvoicelines to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsinvoicelines, or with status {@code 400 (Bad Request)} if the autojobsinvoicelines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autojobsinvoicelines> createAutojobsinvoicelines(@RequestBody Autojobsinvoicelines autojobsinvoicelines)
        throws URISyntaxException {
        LOG.debug("REST request to save Autojobsinvoicelines : {}", autojobsinvoicelines);
        if (autojobsinvoicelines.getId() != null) {
            throw new BadRequestAlertException("A new autojobsinvoicelines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        autojobsinvoicelines = autojobsinvoicelinesRepository.save(autojobsinvoicelines);
        return ResponseEntity.created(new URI("/api/autojobsinvoicelines/" + autojobsinvoicelines.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, autojobsinvoicelines.getId().toString()))
            .body(autojobsinvoicelines);
    }

    /**
     * {@code PUT  /autojobsinvoicelines/:id} : Updates an existing autojobsinvoicelines.
     *
     * @param id the id of the autojobsinvoicelines to save.
     * @param autojobsinvoicelines the autojobsinvoicelines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoicelines,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoicelines is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoicelines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autojobsinvoicelines> updateAutojobsinvoicelines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsinvoicelines autojobsinvoicelines
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsinvoicelines : {}, {}", id, autojobsinvoicelines);
        if (autojobsinvoicelines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsinvoicelines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoicelinesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsinvoicelines = autojobsinvoicelinesRepository.save(autojobsinvoicelines);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoicelines.getId().toString()))
            .body(autojobsinvoicelines);
    }

    /**
     * {@code PATCH  /autojobsinvoicelines/:id} : Partial updates given fields of an existing autojobsinvoicelines, field will ignore if it is null
     *
     * @param id the id of the autojobsinvoicelines to save.
     * @param autojobsinvoicelines the autojobsinvoicelines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoicelines,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoicelines is not valid,
     * or with status {@code 404 (Not Found)} if the autojobsinvoicelines is not found,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoicelines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsinvoicelines> partialUpdateAutojobsinvoicelines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsinvoicelines autojobsinvoicelines
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Autojobsinvoicelines partially : {}, {}", id, autojobsinvoicelines);
        if (autojobsinvoicelines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsinvoicelines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoicelinesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsinvoicelines> result = autojobsinvoicelinesRepository
            .findById(autojobsinvoicelines.getId())
            .map(existingAutojobsinvoicelines -> {
                if (autojobsinvoicelines.getInvocieid() != null) {
                    existingAutojobsinvoicelines.setInvocieid(autojobsinvoicelines.getInvocieid());
                }
                if (autojobsinvoicelines.getLineid() != null) {
                    existingAutojobsinvoicelines.setLineid(autojobsinvoicelines.getLineid());
                }
                if (autojobsinvoicelines.getItemid() != null) {
                    existingAutojobsinvoicelines.setItemid(autojobsinvoicelines.getItemid());
                }
                if (autojobsinvoicelines.getItemcode() != null) {
                    existingAutojobsinvoicelines.setItemcode(autojobsinvoicelines.getItemcode());
                }
                if (autojobsinvoicelines.getItemname() != null) {
                    existingAutojobsinvoicelines.setItemname(autojobsinvoicelines.getItemname());
                }
                if (autojobsinvoicelines.getDescription() != null) {
                    existingAutojobsinvoicelines.setDescription(autojobsinvoicelines.getDescription());
                }
                if (autojobsinvoicelines.getUnitofmeasurement() != null) {
                    existingAutojobsinvoicelines.setUnitofmeasurement(autojobsinvoicelines.getUnitofmeasurement());
                }
                if (autojobsinvoicelines.getQuantity() != null) {
                    existingAutojobsinvoicelines.setQuantity(autojobsinvoicelines.getQuantity());
                }
                if (autojobsinvoicelines.getItemcost() != null) {
                    existingAutojobsinvoicelines.setItemcost(autojobsinvoicelines.getItemcost());
                }
                if (autojobsinvoicelines.getItemprice() != null) {
                    existingAutojobsinvoicelines.setItemprice(autojobsinvoicelines.getItemprice());
                }
                if (autojobsinvoicelines.getDiscount() != null) {
                    existingAutojobsinvoicelines.setDiscount(autojobsinvoicelines.getDiscount());
                }
                if (autojobsinvoicelines.getTax() != null) {
                    existingAutojobsinvoicelines.setTax(autojobsinvoicelines.getTax());
                }
                if (autojobsinvoicelines.getSellingprice() != null) {
                    existingAutojobsinvoicelines.setSellingprice(autojobsinvoicelines.getSellingprice());
                }
                if (autojobsinvoicelines.getLinetotal() != null) {
                    existingAutojobsinvoicelines.setLinetotal(autojobsinvoicelines.getLinetotal());
                }
                if (autojobsinvoicelines.getLmu() != null) {
                    existingAutojobsinvoicelines.setLmu(autojobsinvoicelines.getLmu());
                }
                if (autojobsinvoicelines.getLmd() != null) {
                    existingAutojobsinvoicelines.setLmd(autojobsinvoicelines.getLmd());
                }
                if (autojobsinvoicelines.getNbt() != null) {
                    existingAutojobsinvoicelines.setNbt(autojobsinvoicelines.getNbt());
                }
                if (autojobsinvoicelines.getVat() != null) {
                    existingAutojobsinvoicelines.setVat(autojobsinvoicelines.getVat());
                }

                return existingAutojobsinvoicelines;
            })
            .map(autojobsinvoicelinesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoicelines.getId().toString())
        );
    }

    /**
     * {@code GET  /autojobsinvoicelines} : get all the autojobsinvoicelines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoicelines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsinvoicelines>> getAllAutojobsinvoicelines(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of Autojobsinvoicelines");
        Page<Autojobsinvoicelines> page = autojobsinvoicelinesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autojobsinvoicelines/:id} : get the "id" autojobsinvoicelines.
     *
     * @param id the id of the autojobsinvoicelines to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsinvoicelines, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autojobsinvoicelines> getAutojobsinvoicelines(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Autojobsinvoicelines : {}", id);
        Optional<Autojobsinvoicelines> autojobsinvoicelines = autojobsinvoicelinesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autojobsinvoicelines);
    }

    /**
     * {@code DELETE  /autojobsinvoicelines/:id} : delete the "id" autojobsinvoicelines.
     *
     * @param id the id of the autojobsinvoicelines to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsinvoicelines(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autojobsinvoicelines : {}", id);
        autojobsinvoicelinesRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
