package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Bankbranch;
import com.heavenscode.rac.repository.BankbranchRepository;
import com.heavenscode.rac.service.criteria.BankbranchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Bankbranch} entities in the database.
 * The main input is a {@link BankbranchCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Bankbranch} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankbranchQueryService extends QueryService<Bankbranch> {

    private static final Logger LOG = LoggerFactory.getLogger(BankbranchQueryService.class);

    private final BankbranchRepository bankbranchRepository;

    public BankbranchQueryService(BankbranchRepository bankbranchRepository) {
        this.bankbranchRepository = bankbranchRepository;
    }

    /**
     * Return a {@link Page} of {@link Bankbranch} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bankbranch> findByCriteria(BankbranchCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bankbranch> specification = createSpecification(criteria);
        return bankbranchRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankbranchCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Bankbranch> specification = createSpecification(criteria);
        return bankbranchRepository.count(specification);
    }

    /**
     * Function to convert {@link BankbranchCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bankbranch> createSpecification(BankbranchCriteria criteria) {
        Specification<Bankbranch> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bankbranch_.id));
            }
            if (criteria.getBankcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankcode(), Bankbranch_.bankcode));
            }
            if (criteria.getBranchcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchcode(), Bankbranch_.branchcode));
            }
            if (criteria.getBranchname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchname(), Bankbranch_.branchname));
            }
        }
        return specification;
    }
}
