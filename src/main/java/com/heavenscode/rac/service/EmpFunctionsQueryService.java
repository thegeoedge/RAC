package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.EmpFunctions;
import com.heavenscode.rac.repository.EmpFunctionsRepository;
import com.heavenscode.rac.service.criteria.EmpFunctionsCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmpFunctions} entities in the database.
 * The main input is a {@link EmpFunctionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EmpFunctions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpFunctionsQueryService extends QueryService<EmpFunctions> {

    private static final Logger LOG = LoggerFactory.getLogger(EmpFunctionsQueryService.class);

    private final EmpFunctionsRepository empFunctionsRepository;

    public EmpFunctionsQueryService(EmpFunctionsRepository empFunctionsRepository) {
        this.empFunctionsRepository = empFunctionsRepository;
    }

    /**
     * Return a {@link Page} of {@link EmpFunctions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpFunctions> findByCriteria(EmpFunctionsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmpFunctions> specification = createSpecification(criteria);
        return empFunctionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpFunctionsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<EmpFunctions> specification = createSpecification(criteria);
        return empFunctionsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpFunctionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmpFunctions> createSpecification(EmpFunctionsCriteria criteria) {
        Specification<EmpFunctions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getFunctionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFunctionId(), EmpFunctions_.functionId));
            }
            if (criteria.getFunctionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFunctionName(), EmpFunctions_.functionName));
            }
            if (criteria.getModuleId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModuleId(), EmpFunctions_.moduleId));
            }
        }
        return specification;
    }
}
