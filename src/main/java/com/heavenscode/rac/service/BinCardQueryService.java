package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.BinCard;
import com.heavenscode.rac.repository.BinCardRepository;
import com.heavenscode.rac.service.criteria.BinCardCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BinCard} entities in the database.
 * The main input is a {@link BinCardCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link BinCard} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BinCardQueryService extends QueryService<BinCard> {

    private static final Logger LOG = LoggerFactory.getLogger(BinCardQueryService.class);

    private final BinCardRepository binCardRepository;

    public BinCardQueryService(BinCardRepository binCardRepository) {
        this.binCardRepository = binCardRepository;
    }

    /**
     * Return a {@link Page} of {@link BinCard} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BinCard> findByCriteria(BinCardCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BinCard> specification = createSpecification(criteria);
        return binCardRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BinCardCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<BinCard> specification = createSpecification(criteria);
        return binCardRepository.count(specification);
    }

    /**
     * Function to convert {@link BinCardCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BinCard> createSpecification(BinCardCriteria criteria) {
        Specification<BinCard> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BinCard_.id));
            }
            if (criteria.getItemID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemID(), BinCard_.itemID));
            }
            if (criteria.getItemCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemCode(), BinCard_.itemCode));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), BinCard_.reference));
            }
            if (criteria.getTxDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTxDate(), BinCard_.txDate));
            }
            if (criteria.getQtyIn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyIn(), BinCard_.qtyIn));
            }
            if (criteria.getQtyOut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyOut(), BinCard_.qtyOut));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), BinCard_.price));
            }
            if (criteria.getlMU() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getlMU(), BinCard_.lMU));
            }
            if (criteria.getlMD() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getlMD(), BinCard_.lMD));
            }
            if (criteria.getReferenceCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceCode(), BinCard_.referenceCode));
            }
            if (criteria.getRecordDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecordDate(), BinCard_.recordDate));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBatchId(), BinCard_.batchId));
            }
            if (criteria.getLocationID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLocationID(), BinCard_.locationID));
            }
            if (criteria.getLocationCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocationCode(), BinCard_.locationCode));
            }
            if (criteria.getOpening() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpening(), BinCard_.opening));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), BinCard_.description));
            }
            if (criteria.getReferenceDoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceDoc(), BinCard_.referenceDoc));
            }
        }
        return specification;
    }
}
