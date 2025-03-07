package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Employee;
import com.heavenscode.rac.repository.EmployeeRepository;
import com.heavenscode.rac.service.criteria.EmployeeCriteria;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Employee} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeQueryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Return a {@link List} of {@link Employee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Employee> findByCriteria(EmployeeCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employee_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Employee_.code));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Employee_.fullName));
            }
            if (criteria.getNameWithInitials() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameWithInitials(), Employee_.nameWithInitials));
            }
            if (criteria.getSurname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurname(), Employee_.surname));
            }
            if (criteria.getNicNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNicNumber(), Employee_.nicNumber));
            }
            if (criteria.getNicIssueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNicIssueDate(), Employee_.nicIssueDate));
            }
            if (criteria.getPassportNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportNo(), Employee_.passportNo));
            }
            if (criteria.getPassportIssueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassportIssueDate(), Employee_.passportIssueDate));
            }
            if (criteria.getPassportExpDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassportExpDate(), Employee_.passportExpDate));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Employee_.dateOfBirth));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Employee_.age));
            }
            if (criteria.getBloodGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBloodGroup(), Employee_.bloodGroup));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), Employee_.gender));
            }
            if (criteria.getPhone2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone2(), Employee_.phone2));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalStatus(), Employee_.maritalStatus));
            }
            if (criteria.getMarriedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMarriedDate(), Employee_.marriedDate));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), Employee_.nationality));
            }
            if (criteria.getPermanentAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermanentAddress(), Employee_.permanentAddress));
            }
            if (criteria.getTemporaryAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemporaryAddress(), Employee_.temporaryAddress));
            }
            if (criteria.getHome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHome(), Employee_.home));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), Employee_.mobile));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Employee_.fax));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Employee_.email));
            }
            if (criteria.getEmergencyContactPerson() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getEmergencyContactPerson(), Employee_.emergencyContactPerson)
                );
            }
            if (criteria.getEmergencyNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmergencyNumber(), Employee_.emergencyNumber));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Employee_.city));
            }
            if (criteria.getDistrict() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrict(), Employee_.district));
            }
            if (criteria.getProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvince(), Employee_.province));
            }
            if (criteria.getElectorate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElectorate(), Employee_.electorate));
            }
            if (criteria.getMainRoad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMainRoad(), Employee_.mainRoad));
            }
            if (criteria.getModeOfTransport() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModeOfTransport(), Employee_.modeOfTransport));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistance(), Employee_.distance));
            }
            if (criteria.getTravelTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTravelTime(), Employee_.travelTime));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), Employee_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Employee_.password));
            }
            if (criteria.getDepartmentID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentID(), Employee_.departmentID));
            }
            if (criteria.getDepartmentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartmentCode(), Employee_.departmentCode));
            }
            if (criteria.getEmpRegDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmpRegDate(), Employee_.empRegDate));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLmu(), Employee_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Employee_.lmd));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoleId(), Employee_.roleId));
            }
            if (criteria.getRoleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleName(), Employee_.roleName));
            }
            if (criteria.getEpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEpf(), Employee_.epf));
            }
            if (criteria.getEtf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEtf(), Employee_.etf));
            }
            if (criteria.getDateJoined() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateJoined(), Employee_.dateJoined));
            }
            if (criteria.getDateResigned() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateResigned(), Employee_.dateResigned));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Employee_.designation));
            }
            if (criteria.getJobStatusId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobStatusId(), Employee_.jobStatusId));
            }
            if (criteria.getJobStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobStatusName(), Employee_.jobStatusName));
            }
            if (criteria.getImagePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagePath(), Employee_.imagePath));
            }
            if (criteria.getBankAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountNo(), Employee_.bankAccountNo));
            }
            if (criteria.getBankId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankId(), Employee_.bankId));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), Employee_.bankName));
            }
            if (criteria.getBranchId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBranchId(), Employee_.branchId));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), Employee_.branchName));
            }
            if (criteria.getSalaryPaymentBasis() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalaryPaymentBasis(), Employee_.salaryPaymentBasis));
            }
            if (criteria.getEmpStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpStatus(), Employee_.empStatus));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReligion(), Employee_.religion));
            }
            if (criteria.getExperience() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExperience(), Employee_.experience));
            }
            if (criteria.getQualifications() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQualifications(), Employee_.qualifications));
            }
            if (criteria.getAttendanceCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttendanceCode(), Employee_.attendanceCode));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Employee_.isActive));
            }
        }
        return specification;
    }
}
