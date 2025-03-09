package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Inventorybatches;
import com.heavenscode.rac.repository.InventorybatchesRepository;
import com.heavenscode.rac.service.criteria.InventorybatchesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Inventorybatches} entities in the database.
 * The main input is a {@link InventorybatchesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Inventorybatches} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventorybatchesQueryService extends QueryService<Inventorybatches> {

    private static final Logger LOG = LoggerFactory.getLogger(InventorybatchesQueryService.class);

    private final InventorybatchesRepository inventorybatchesRepository;

    public InventorybatchesQueryService(InventorybatchesRepository inventorybatchesRepository) {
        this.inventorybatchesRepository = inventorybatchesRepository;
    }

    /**
     * Return a {@link Page} of {@link Inventorybatches} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Inventorybatches> findByCriteria(InventorybatchesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inventorybatches> specification = createSpecification(criteria);
        return inventorybatchesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventorybatchesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Inventorybatches> specification = createSpecification(criteria);
        return inventorybatchesRepository.count(specification);
    }

    /**
     * Function to convert {@link InventorybatchesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inventorybatches> createSpecification(InventorybatchesCriteria criteria) {
        Specification<Inventorybatches> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Inventorybatches_.id));
            }
            if (criteria.getItemid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemid(), Inventorybatches_.itemid));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Inventorybatches_.code));
            }
            if (criteria.getTxdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTxdate(), Inventorybatches_.txdate));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), Inventorybatches_.cost));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Inventorybatches_.price));
            }
            if (criteria.getCostwithoutvat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostwithoutvat(), Inventorybatches_.costwithoutvat));
            }
            if (criteria.getPricewithoutvat() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPricewithoutvat(), Inventorybatches_.pricewithoutvat)
                );
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Inventorybatches_.notes));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Inventorybatches_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Inventorybatches_.lmd));
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineid(), Inventorybatches_.lineid));
            }
            if (criteria.getManufacturedate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getManufacturedate(), Inventorybatches_.manufacturedate)
                );
            }
            if (criteria.getExpiredate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiredate(), Inventorybatches_.expiredate));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Inventorybatches_.quantity));
            }
            if (criteria.getAddeddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddeddate(), Inventorybatches_.addeddate));
            }
            if (criteria.getCosttotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCosttotal(), Inventorybatches_.costtotal));
            }
            if (criteria.getReturnprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReturnprice(), Inventorybatches_.returnprice));
            }
        }
        return specification;
    }
}
