package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.PaymentMethod;
import com.heavenscode.rac.repository.PaymentMethodRepository;
import com.heavenscode.rac.service.PaymentMethodQueryService;
import com.heavenscode.rac.service.PaymentMethodService;
import com.heavenscode.rac.service.criteria.PaymentMethodCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.PaymentMethod}.
 */
@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodResource {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentMethodResource.class);

    private static final String ENTITY_NAME = "paymentMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentMethodService paymentMethodService;

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMethodQueryService paymentMethodQueryService;

    public PaymentMethodResource(
        PaymentMethodService paymentMethodService,
        PaymentMethodRepository paymentMethodRepository,
        PaymentMethodQueryService paymentMethodQueryService
    ) {
        this.paymentMethodService = paymentMethodService;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodQueryService = paymentMethodQueryService;
    }

    /**
     * {@code POST  /payment-methods} : Create a new paymentMethod.
     *
     * @param paymentMethod the paymentMethod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentMethod, or with status {@code 400 (Bad Request)} if the paymentMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentMethod> createPaymentMethod(@RequestBody PaymentMethod paymentMethod) throws URISyntaxException {
        LOG.debug("REST request to save PaymentMethod : {}", paymentMethod);
        if (paymentMethod.getId() != null) {
            throw new BadRequestAlertException("A new paymentMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentMethod = paymentMethodService.save(paymentMethod);
        return ResponseEntity.created(new URI("/api/payment-methods/" + paymentMethod.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, paymentMethod.getId().toString()))
            .body(paymentMethod);
    }

    /**
     * {@code PUT  /payment-methods/:id} : Updates an existing paymentMethod.
     *
     * @param id the id of the paymentMethod to save.
     * @param paymentMethod the paymentMethod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethod,
     * or with status {@code 400 (Bad Request)} if the paymentMethod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentMethod paymentMethod
    ) throws URISyntaxException {
        LOG.debug("REST request to update PaymentMethod : {}, {}", id, paymentMethod);
        if (paymentMethod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethod.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentMethod = paymentMethodService.update(paymentMethod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentMethod.getId().toString()))
            .body(paymentMethod);
    }

    /**
     * {@code PATCH  /payment-methods/:id} : Partial updates given fields of an existing paymentMethod, field will ignore if it is null
     *
     * @param id the id of the paymentMethod to save.
     * @param paymentMethod the paymentMethod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethod,
     * or with status {@code 400 (Bad Request)} if the paymentMethod is not valid,
     * or with status {@code 404 (Not Found)} if the paymentMethod is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentMethod> partialUpdatePaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentMethod paymentMethod
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PaymentMethod partially : {}, {}", id, paymentMethod);
        if (paymentMethod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethod.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentMethod> result = paymentMethodService.partialUpdate(paymentMethod);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentMethod.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-methods} : get all the paymentMethods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentMethods in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods(PaymentMethodCriteria criteria) {
        LOG.debug("REST request to get PaymentMethods by criteria: {}", criteria);

        List<PaymentMethod> entityList = paymentMethodQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /payment-methods/count} : count all the paymentMethods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPaymentMethods(PaymentMethodCriteria criteria) {
        LOG.debug("REST request to count PaymentMethods by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentMethodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-methods/:id} : get the "id" paymentMethod.
     *
     * @param id the id of the paymentMethod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentMethod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethod(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PaymentMethod : {}", id);
        Optional<PaymentMethod> paymentMethod = paymentMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentMethod);
    }

    /**
     * {@code DELETE  /payment-methods/:id} : delete the "id" paymentMethod.
     *
     * @param id the id of the paymentMethod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PaymentMethod : {}", id);
        paymentMethodService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
