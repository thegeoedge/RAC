package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.AutoCareVehicle;
import com.heavenscode.rac.repository.AutoCareVehicleRepository;
import com.heavenscode.rac.service.criteria.AutoCareVehicleCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AutoCareVehicle} entities in the database.
 * The main input is a {@link AutoCareVehicleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AutoCareVehicle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutoCareVehicleQueryService extends QueryService<AutoCareVehicle> {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCareVehicleQueryService.class);

    private final AutoCareVehicleRepository autoCareVehicleRepository;

    public AutoCareVehicleQueryService(AutoCareVehicleRepository autoCareVehicleRepository) {
        this.autoCareVehicleRepository = autoCareVehicleRepository;
    }

    /**
     * Return a {@link Page} of {@link AutoCareVehicle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AutoCareVehicle> findByCriteria(AutoCareVehicleCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AutoCareVehicle> specification = createSpecification(criteria);
        return autoCareVehicleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutoCareVehicleCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<AutoCareVehicle> specification = createSpecification(criteria);
        return autoCareVehicleRepository.count(specification);
    }

    /**
     * Function to convert {@link AutoCareVehicleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AutoCareVehicle> createSpecification(AutoCareVehicleCriteria criteria) {
        Specification<AutoCareVehicle> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AutoCareVehicle_.id));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), AutoCareVehicle_.customerId));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerName(), AutoCareVehicle_.customerName));
            }
            if (criteria.getCustomerTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerTel(), AutoCareVehicle_.customerTel));
            }
            if (criteria.getVehicleNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVehicleNumber(), AutoCareVehicle_.vehicleNumber));
            }
            if (criteria.getBrandId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBrandId(), AutoCareVehicle_.brandId));
            }
            if (criteria.getBrandName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrandName(), AutoCareVehicle_.brandName));
            }
            if (criteria.getModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModel(), AutoCareVehicle_.model));
            }
            if (criteria.getMillage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMillage(), AutoCareVehicle_.millage));
            }
            if (criteria.getManufactureYear() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getManufactureYear(), AutoCareVehicle_.manufactureYear)
                );
            }
            if (criteria.getLastServiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastServiceDate(), AutoCareVehicle_.lastServiceDate));
            }
            if (criteria.getNextServiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextServiceDate(), AutoCareVehicle_.nextServiceDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AutoCareVehicle_.description));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), AutoCareVehicle_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), AutoCareVehicle_.lmd));
            }
        }
        return specification;
    }
}
