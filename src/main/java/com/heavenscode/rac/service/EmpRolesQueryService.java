package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.EmpRoles;
import com.heavenscode.rac.repository.EmpRolesRepository;
import com.heavenscode.rac.service.criteria.EmpRolesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmpRoles} entities in the database.
 * The main input is a {@link EmpRolesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EmpRoles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpRolesQueryService extends QueryService<EmpRoles> {

    private static final Logger LOG = LoggerFactory.getLogger(EmpRolesQueryService.class);

    private final EmpRolesRepository empRolesRepository;

    public EmpRolesQueryService(EmpRolesRepository empRolesRepository) {
        this.empRolesRepository = empRolesRepository;
    }

    /**
     * Return a {@link Page} of {@link EmpRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpRoles> findByCriteria(EmpRolesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmpRoles> specification = createSpecification(criteria);
        return empRolesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpRolesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<EmpRoles> specification = createSpecification(criteria);
        return empRolesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpRolesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmpRoles> createSpecification(EmpRolesCriteria criteria) {
        Specification<EmpRoles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoleId(), EmpRoles_.roleId));
            }
            if (criteria.getRoleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleName(), EmpRoles_.roleName));
            }
        }
        return specification;
    }
}
