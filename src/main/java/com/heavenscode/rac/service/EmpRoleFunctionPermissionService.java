package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.EmpRoleFunctionPermission;
import com.heavenscode.rac.repository.EmpRoleFunctionPermissionRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.EmpRoleFunctionPermission}.
 */
@Service
@Transactional
public class EmpRoleFunctionPermissionService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpRoleFunctionPermissionService.class);

    private final EmpRoleFunctionPermissionRepository empRoleFunctionPermissionRepository;

    public EmpRoleFunctionPermissionService(EmpRoleFunctionPermissionRepository empRoleFunctionPermissionRepository) {
        this.empRoleFunctionPermissionRepository = empRoleFunctionPermissionRepository;
    }

    /**
     * Save a empRoleFunctionPermission.
     *
     * @param empRoleFunctionPermission the entity to save.
     * @return the persisted entity.
     */
    public EmpRoleFunctionPermission save(EmpRoleFunctionPermission empRoleFunctionPermission) {
        LOG.debug("Request to save EmpRoleFunctionPermission : {}", empRoleFunctionPermission);
        return empRoleFunctionPermissionRepository.save(empRoleFunctionPermission);
    }

    /**
     * Update a empRoleFunctionPermission.
     *
     * @param empRoleFunctionPermission the entity to save.
     * @return the persisted entity.
     */
    public EmpRoleFunctionPermission update(EmpRoleFunctionPermission empRoleFunctionPermission) {
        LOG.debug("Request to update EmpRoleFunctionPermission : {}", empRoleFunctionPermission);
        return empRoleFunctionPermissionRepository.save(empRoleFunctionPermission);
    }

    /**
     * Partially update a empRoleFunctionPermission.
     *
     * @param empRoleFunctionPermission the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmpRoleFunctionPermission> partialUpdate(EmpRoleFunctionPermission empRoleFunctionPermission) {
        LOG.debug("Request to partially update EmpRoleFunctionPermission : {}", empRoleFunctionPermission);

        return empRoleFunctionPermissionRepository
            .findById(empRoleFunctionPermission.getId())
            .map(existingEmpRoleFunctionPermission -> {
                if (empRoleFunctionPermission.getRoleId() != null) {
                    existingEmpRoleFunctionPermission.setRoleId(empRoleFunctionPermission.getRoleId());
                }
                if (empRoleFunctionPermission.getFunctionId() != null) {
                    existingEmpRoleFunctionPermission.setFunctionId(empRoleFunctionPermission.getFunctionId());
                }

                return existingEmpRoleFunctionPermission;
            })
            .map(empRoleFunctionPermissionRepository::save);
    }

    /**
     * Get one empRoleFunctionPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpRoleFunctionPermission> findOne(Integer id) {
        LOG.debug("Request to get EmpRoleFunctionPermission : {}", id);
        return empRoleFunctionPermissionRepository.findById(id);
    }

    /**
     * Delete the empRoleFunctionPermission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete EmpRoleFunctionPermission : {}", id);
        empRoleFunctionPermissionRepository.deleteById(id);
    }
}
