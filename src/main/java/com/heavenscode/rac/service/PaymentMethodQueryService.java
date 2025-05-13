package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.PaymentMethod;
import com.heavenscode.rac.repository.PaymentMethodRepository;
import com.heavenscode.rac.service.criteria.PaymentMethodCriteria;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PaymentMethod} entities in the database.
 * The main input is a {@link PaymentMethodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentMethod} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentMethodQueryService extends QueryService<PaymentMethod> {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentMethodQueryService.class);

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodQueryService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentMethod} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentMethod> findByCriteria(PaymentMethodCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<PaymentMethod> specification = createSpecification(criteria);
        return paymentMethodRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentMethodCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<PaymentMethod> specification = createSpecification(criteria);
        return paymentMethodRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentMethodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentMethod> createSpecification(PaymentMethodCriteria criteria) {
        Specification<PaymentMethod> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentMethod_.id));
            }
            if (criteria.getPaymentMethodName() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPaymentMethodName(), PaymentMethod_.paymentMethodName)
                );
            }
            if (criteria.getCommission() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommission(), PaymentMethod_.commission));
            }
            if (criteria.getCompanyBankAccountId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCompanyBankAccountId(), PaymentMethod_.companyBankAccountId)
                );
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), PaymentMethod_.lmd));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), PaymentMethod_.lmu));
            }
        }
        return specification;
    }
}
