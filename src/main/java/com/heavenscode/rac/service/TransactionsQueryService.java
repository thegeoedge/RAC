package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Transactions;
import com.heavenscode.rac.repository.TransactionsRepository;
import com.heavenscode.rac.service.criteria.TransactionsCriteria;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Transactions} entities in the database.
 * The main input is a {@link TransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Transactions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionsQueryService extends QueryService<Transactions> {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsQueryService.class);

    private final TransactionsRepository transactionsRepository;

    public TransactionsQueryService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    /**
     * Return a {@link List} of {@link Transactions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Transactions> findByCriteria(TransactionsCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Transactions> specification = createSpecification(criteria);
        return transactionsRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Transactions> specification = createSpecification(criteria);
        return transactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transactions> createSpecification(TransactionsCriteria criteria) {
        Specification<Transactions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transactions_.id));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountId(), Transactions_.accountId));
            }
            if (criteria.getAccountCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountCode(), Transactions_.accountCode));
            }
            if (criteria.getDebit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDebit(), Transactions_.debit));
            }
            if (criteria.getCredit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCredit(), Transactions_.credit));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Transactions_.date));
            }
            if (criteria.getRefDoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefDoc(), Transactions_.refDoc));
            }
            if (criteria.getRefId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefId(), Transactions_.refId));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), Transactions_.source));
            }
            if (criteria.getPaymentTermId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentTermId(), Transactions_.paymentTermId));
            }
            if (criteria.getPaymentTermName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentTermName(), Transactions_.paymentTermName));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Transactions_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Transactions_.lmd));
            }
        }
        return specification;
    }
}
