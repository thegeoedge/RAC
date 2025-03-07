package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Employee;
import com.heavenscode.rac.repository.EmployeeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee save(Employee employee) {
        LOG.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Update a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee update(Employee employee) {
        LOG.debug("Request to update Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Partially update a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Employee> partialUpdate(Employee employee) {
        LOG.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getCode() != null) {
                    existingEmployee.setCode(employee.getCode());
                }
                if (employee.getFullName() != null) {
                    existingEmployee.setFullName(employee.getFullName());
                }
                if (employee.getNameWithInitials() != null) {
                    existingEmployee.setNameWithInitials(employee.getNameWithInitials());
                }
                if (employee.getSurname() != null) {
                    existingEmployee.setSurname(employee.getSurname());
                }
                if (employee.getNicNumber() != null) {
                    existingEmployee.setNicNumber(employee.getNicNumber());
                }
                if (employee.getNicIssueDate() != null) {
                    existingEmployee.setNicIssueDate(employee.getNicIssueDate());
                }
                if (employee.getPassportNo() != null) {
                    existingEmployee.setPassportNo(employee.getPassportNo());
                }
                if (employee.getPassportIssueDate() != null) {
                    existingEmployee.setPassportIssueDate(employee.getPassportIssueDate());
                }
                if (employee.getPassportExpDate() != null) {
                    existingEmployee.setPassportExpDate(employee.getPassportExpDate());
                }
                if (employee.getDateOfBirth() != null) {
                    existingEmployee.setDateOfBirth(employee.getDateOfBirth());
                }
                if (employee.getAge() != null) {
                    existingEmployee.setAge(employee.getAge());
                }
                if (employee.getBloodGroup() != null) {
                    existingEmployee.setBloodGroup(employee.getBloodGroup());
                }
                if (employee.getGender() != null) {
                    existingEmployee.setGender(employee.getGender());
                }
                if (employee.getPhone2() != null) {
                    existingEmployee.setPhone2(employee.getPhone2());
                }
                if (employee.getMaritalStatus() != null) {
                    existingEmployee.setMaritalStatus(employee.getMaritalStatus());
                }
                if (employee.getMarriedDate() != null) {
                    existingEmployee.setMarriedDate(employee.getMarriedDate());
                }
                if (employee.getNationality() != null) {
                    existingEmployee.setNationality(employee.getNationality());
                }
                if (employee.getPermanentAddress() != null) {
                    existingEmployee.setPermanentAddress(employee.getPermanentAddress());
                }
                if (employee.getTemporaryAddress() != null) {
                    existingEmployee.setTemporaryAddress(employee.getTemporaryAddress());
                }
                if (employee.getHome() != null) {
                    existingEmployee.setHome(employee.getHome());
                }
                if (employee.getMobile() != null) {
                    existingEmployee.setMobile(employee.getMobile());
                }
                if (employee.getFax() != null) {
                    existingEmployee.setFax(employee.getFax());
                }
                if (employee.getEmail() != null) {
                    existingEmployee.setEmail(employee.getEmail());
                }
                if (employee.getEmergencyContactPerson() != null) {
                    existingEmployee.setEmergencyContactPerson(employee.getEmergencyContactPerson());
                }
                if (employee.getEmergencyNumber() != null) {
                    existingEmployee.setEmergencyNumber(employee.getEmergencyNumber());
                }
                if (employee.getCity() != null) {
                    existingEmployee.setCity(employee.getCity());
                }
                if (employee.getDistrict() != null) {
                    existingEmployee.setDistrict(employee.getDistrict());
                }
                if (employee.getProvince() != null) {
                    existingEmployee.setProvince(employee.getProvince());
                }
                if (employee.getElectorate() != null) {
                    existingEmployee.setElectorate(employee.getElectorate());
                }
                if (employee.getMainRoad() != null) {
                    existingEmployee.setMainRoad(employee.getMainRoad());
                }
                if (employee.getModeOfTransport() != null) {
                    existingEmployee.setModeOfTransport(employee.getModeOfTransport());
                }
                if (employee.getDistance() != null) {
                    existingEmployee.setDistance(employee.getDistance());
                }
                if (employee.getTravelTime() != null) {
                    existingEmployee.setTravelTime(employee.getTravelTime());
                }
                if (employee.getUsername() != null) {
                    existingEmployee.setUsername(employee.getUsername());
                }
                if (employee.getPassword() != null) {
                    existingEmployee.setPassword(employee.getPassword());
                }
                if (employee.getDepartmentID() != null) {
                    existingEmployee.setDepartmentID(employee.getDepartmentID());
                }
                if (employee.getDepartmentCode() != null) {
                    existingEmployee.setDepartmentCode(employee.getDepartmentCode());
                }
                if (employee.getEmpRegDate() != null) {
                    existingEmployee.setEmpRegDate(employee.getEmpRegDate());
                }
                if (employee.getLmu() != null) {
                    existingEmployee.setLmu(employee.getLmu());
                }
                if (employee.getLmd() != null) {
                    existingEmployee.setLmd(employee.getLmd());
                }
                if (employee.getRoleId() != null) {
                    existingEmployee.setRoleId(employee.getRoleId());
                }
                if (employee.getRoleName() != null) {
                    existingEmployee.setRoleName(employee.getRoleName());
                }
                if (employee.getEpf() != null) {
                    existingEmployee.setEpf(employee.getEpf());
                }
                if (employee.getEtf() != null) {
                    existingEmployee.setEtf(employee.getEtf());
                }
                if (employee.getDateJoined() != null) {
                    existingEmployee.setDateJoined(employee.getDateJoined());
                }
                if (employee.getDateResigned() != null) {
                    existingEmployee.setDateResigned(employee.getDateResigned());
                }
                if (employee.getDesignation() != null) {
                    existingEmployee.setDesignation(employee.getDesignation());
                }
                if (employee.getJobStatusId() != null) {
                    existingEmployee.setJobStatusId(employee.getJobStatusId());
                }
                if (employee.getJobStatusName() != null) {
                    existingEmployee.setJobStatusName(employee.getJobStatusName());
                }
                if (employee.getImagePath() != null) {
                    existingEmployee.setImagePath(employee.getImagePath());
                }
                if (employee.getBankAccountNo() != null) {
                    existingEmployee.setBankAccountNo(employee.getBankAccountNo());
                }
                if (employee.getBankId() != null) {
                    existingEmployee.setBankId(employee.getBankId());
                }
                if (employee.getBankName() != null) {
                    existingEmployee.setBankName(employee.getBankName());
                }
                if (employee.getBranchId() != null) {
                    existingEmployee.setBranchId(employee.getBranchId());
                }
                if (employee.getBranchName() != null) {
                    existingEmployee.setBranchName(employee.getBranchName());
                }
                if (employee.getSalaryPaymentBasis() != null) {
                    existingEmployee.setSalaryPaymentBasis(employee.getSalaryPaymentBasis());
                }
                if (employee.getEmpStatus() != null) {
                    existingEmployee.setEmpStatus(employee.getEmpStatus());
                }
                if (employee.getReligion() != null) {
                    existingEmployee.setReligion(employee.getReligion());
                }
                if (employee.getExperience() != null) {
                    existingEmployee.setExperience(employee.getExperience());
                }
                if (employee.getQualifications() != null) {
                    existingEmployee.setQualifications(employee.getQualifications());
                }
                if (employee.getAttendanceCode() != null) {
                    existingEmployee.setAttendanceCode(employee.getAttendanceCode());
                }
                if (employee.getIsActive() != null) {
                    existingEmployee.setIsActive(employee.getIsActive());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        LOG.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
