package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Autocarejob;
import com.heavenscode.rac.repository.AutocarejobRepository;
import com.heavenscode.rac.service.criteria.AutocarejobCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Autocarejob} entities in the database.
 * The main input is a {@link AutocarejobCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Autocarejob} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutocarejobQueryService extends QueryService<Autocarejob> {

    private static final Logger LOG = LoggerFactory.getLogger(AutocarejobQueryService.class);

    private final AutocarejobRepository autocarejobRepository;

    public AutocarejobQueryService(AutocarejobRepository autocarejobRepository) {
        this.autocarejobRepository = autocarejobRepository;
    }

    /**
     * Return a {@link Page} of {@link Autocarejob} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autocarejob> findByCriteria(AutocarejobCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autocarejob> specification = createSpecification(criteria);
        return autocarejobRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutocarejobCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Autocarejob> specification = createSpecification(criteria);
        return autocarejobRepository.count(specification);
    }

    /**
     * Function to convert {@link AutocarejobCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autocarejob> createSpecification(AutocarejobCriteria criteria) {
        Specification<Autocarejob> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autocarejob_.id));
            }
            if (criteria.getJobnumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobnumber(), Autocarejob_.jobnumber));
            }
            if (criteria.getVehicleid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicleid(), Autocarejob_.vehicleid));
            }
            if (criteria.getVehiclenumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVehiclenumber(), Autocarejob_.vehiclenumber));
            }
            if (criteria.getMillage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMillage(), Autocarejob_.millage));
            }
            if (criteria.getNextmillage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextmillage(), Autocarejob_.nextmillage));
            }
            if (criteria.getNextservicedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextservicedate(), Autocarejob_.nextservicedate));
            }
            if (criteria.getVehicletypeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicletypeid(), Autocarejob_.vehicletypeid));
            }
            if (criteria.getJobtypeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobtypeid(), Autocarejob_.jobtypeid));
            }
            if (criteria.getJobtypename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobtypename(), Autocarejob_.jobtypename));
            }
            if (criteria.getJobopenby() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobopenby(), Autocarejob_.jobopenby));
            }
            if (criteria.getJobopentime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobopentime(), Autocarejob_.jobopentime));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Autocarejob_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Autocarejob_.lmd));
            }
            if (criteria.getSpecialrquirments() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getSpecialrquirments(), Autocarejob_.specialrquirments)
                );
            }
            if (criteria.getSpecialinstructions() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getSpecialinstructions(), Autocarejob_.specialinstructions)
                );
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Autocarejob_.remarks));
            }
            if (criteria.getNextserviceinstructions() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getNextserviceinstructions(), Autocarejob_.nextserviceinstructions)
                );
            }
            if (criteria.getLastserviceinstructions() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getLastserviceinstructions(), Autocarejob_.lastserviceinstructions)
                );
            }
            if (criteria.getIsadvisorchecked() != null) {
                specification = specification.and(buildSpecification(criteria.getIsadvisorchecked(), Autocarejob_.isadvisorchecked));
            }
            if (criteria.getIsempallocated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsempallocated(), Autocarejob_.isempallocated));
            }
            if (criteria.getJobclosetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobclosetime(), Autocarejob_.jobclosetime));
            }
            if (criteria.getIsjobclose() != null) {
                specification = specification.and(buildSpecification(criteria.getIsjobclose(), Autocarejob_.isjobclose));
            }
            if (criteria.getIsfeedback() != null) {
                specification = specification.and(buildSpecification(criteria.getIsfeedback(), Autocarejob_.isfeedback));
            }
            if (criteria.getFeedbackstatusid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeedbackstatusid(), Autocarejob_.feedbackstatusid));
            }
            if (criteria.getCustomername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomername(), Autocarejob_.customername));
            }
            if (criteria.getCustomertel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomertel(), Autocarejob_.customertel));
            }
            if (criteria.getCustomerid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerid(), Autocarejob_.customerid));
            }
            if (criteria.getAdvisorfinalcheck() != null) {
                specification = specification.and(buildSpecification(criteria.getAdvisorfinalcheck(), Autocarejob_.advisorfinalcheck));
            }
            if (criteria.getJobdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobdate(), Autocarejob_.jobdate));
            }
            if (criteria.getIscompanyservice() != null) {
                specification = specification.and(buildSpecification(criteria.getIscompanyservice(), Autocarejob_.iscompanyservice));
            }
            if (criteria.getFreeservicenumber() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getFreeservicenumber(), Autocarejob_.freeservicenumber)
                );
            }
            if (criteria.getCompanyid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyid(), Autocarejob_.companyid));
            }
            if (criteria.getUpdatetocustomer() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdatetocustomer(), Autocarejob_.updatetocustomer));
            }
            if (criteria.getNextgearoilmilage() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getNextgearoilmilage(), Autocarejob_.nextgearoilmilage)
                );
            }
            if (criteria.getIsjobinvoiced() != null) {
                specification = specification.and(buildSpecification(criteria.getIsjobinvoiced(), Autocarejob_.isjobinvoiced));
            }
            if (criteria.getIswaiting() != null) {
                specification = specification.and(buildSpecification(criteria.getIswaiting(), Autocarejob_.iswaiting));
            }
            if (criteria.getIscustomercomment() != null) {
                specification = specification.and(buildSpecification(criteria.getIscustomercomment(), Autocarejob_.iscustomercomment));
            }
            if (criteria.getImagefolder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagefolder(), Autocarejob_.imagefolder));
            }
            if (criteria.getFrontimage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFrontimage(), Autocarejob_.frontimage));
            }
            if (criteria.getLeftimage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeftimage(), Autocarejob_.leftimage));
            }
            if (criteria.getRightimage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRightimage(), Autocarejob_.rightimage));
            }
            if (criteria.getBackimage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBackimage(), Autocarejob_.backimage));
            }
            if (criteria.getDashboardimage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDashboardimage(), Autocarejob_.dashboardimage));
            }
        }
        return specification;
    }
}
