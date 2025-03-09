package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SalesInvoiceLineBatch;
import com.heavenscode.rac.repository.SalesInvoiceLineBatchRepository;
import com.heavenscode.rac.service.criteria.SalesInvoiceLineBatchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalesInvoiceLineBatch} entities in the database.
 * The main input is a {@link SalesInvoiceLineBatchCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SalesInvoiceLineBatch} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesInvoiceLineBatchQueryService extends QueryService<SalesInvoiceLineBatch> {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLineBatchQueryService.class);

    private final SalesInvoiceLineBatchRepository salesInvoiceLineBatchRepository;

    public SalesInvoiceLineBatchQueryService(SalesInvoiceLineBatchRepository salesInvoiceLineBatchRepository) {
        this.salesInvoiceLineBatchRepository = salesInvoiceLineBatchRepository;
    }

    /**
     * Return a {@link Page} of {@link SalesInvoiceLineBatch} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesInvoiceLineBatch> findByCriteria(SalesInvoiceLineBatchCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesInvoiceLineBatch> specification = createSpecification(criteria);
        return salesInvoiceLineBatchRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesInvoiceLineBatchCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SalesInvoiceLineBatch> specification = createSpecification(criteria);
        return salesInvoiceLineBatchRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesInvoiceLineBatchCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesInvoiceLineBatch> createSpecification(SalesInvoiceLineBatchCriteria criteria) {
        Specification<SalesInvoiceLineBatch> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesInvoiceLineBatch_.id));
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineId(), SalesInvoiceLineBatch_.lineId));
            }
            if (criteria.getBatchLineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBatchLineId(), SalesInvoiceLineBatch_.batchLineId));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemId(), SalesInvoiceLineBatch_.itemId));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SalesInvoiceLineBatch_.code));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBatchId(), SalesInvoiceLineBatch_.batchId));
            }
            if (criteria.getBatchCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchCode(), SalesInvoiceLineBatch_.batchCode));
            }
            if (criteria.getTxDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTxDate(), SalesInvoiceLineBatch_.txDate));
            }
            if (criteria.getManufactureDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getManufactureDate(), SalesInvoiceLineBatch_.manufactureDate)
                );
            }
            if (criteria.getExpiredDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiredDate(), SalesInvoiceLineBatch_.expiredDate));
            }
            if (criteria.getQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQty(), SalesInvoiceLineBatch_.qty));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), SalesInvoiceLineBatch_.cost));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), SalesInvoiceLineBatch_.price));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), SalesInvoiceLineBatch_.notes));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), SalesInvoiceLineBatch_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), SalesInvoiceLineBatch_.lmd));
            }
            if (criteria.getNbt() != null) {
                specification = specification.and(buildSpecification(criteria.getNbt(), SalesInvoiceLineBatch_.nbt));
            }
            if (criteria.getVat() != null) {
                specification = specification.and(buildSpecification(criteria.getVat(), SalesInvoiceLineBatch_.vat));
            }
            if (criteria.getAddedById() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddedById(), SalesInvoiceLineBatch_.addedById));
            }
        }
        return specification;
    }
}
