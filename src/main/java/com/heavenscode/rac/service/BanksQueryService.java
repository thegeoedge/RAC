package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Banks;
import com.heavenscode.rac.repository.BanksRepository;
import com.heavenscode.rac.service.criteria.BanksCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Banks} entities in the database.
 * The main input is a {@link BanksCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Banks} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BanksQueryService extends QueryService<Banks> {

    private static final Logger LOG = LoggerFactory.getLogger(BanksQueryService.class);

    private final BanksRepository banksRepository;

    public BanksQueryService(BanksRepository banksRepository) {
        this.banksRepository = banksRepository;
    }

    /**
     * Return a {@link Page} of {@link Banks} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Banks> findByCriteria(BanksCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Banks> specification = createSpecification(criteria);
        return banksRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BanksCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Banks> specification = createSpecification(criteria);
        return banksRepository.count(specification);
    }

    /**
     * Function to convert {@link BanksCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Banks> createSpecification(BanksCriteria criteria) {
        Specification<Banks> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Banks_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Banks_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Banks_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Banks_.description));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Banks_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Banks_.lmd));
            }
        }
        return specification;
    }
}
