package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.EmpRoles;
import com.heavenscode.rac.repository.EmpRolesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.EmpRoles}.
 */
@Service
@Transactional
public class EmpRolesService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpRolesService.class);

    private final EmpRolesRepository empRolesRepository;

    public EmpRolesService(EmpRolesRepository empRolesRepository) {
        this.empRolesRepository = empRolesRepository;
    }

    /**
     * Save a empRoles.
     *
     * @param empRoles the entity to save.
     * @return the persisted entity.
     */
    public EmpRoles save(EmpRoles empRoles) {
        LOG.debug("Request to save EmpRoles : {}", empRoles);
        return empRolesRepository.save(empRoles);
    }

    /**
     * Update a empRoles.
     *
     * @param empRoles the entity to save.
     * @return the persisted entity.
     */
    public EmpRoles update(EmpRoles empRoles) {
        LOG.debug("Request to update EmpRoles : {}", empRoles);
        return empRolesRepository.save(empRoles);
    }

    /**
     * Partially update a empRoles.
     *
     * @param empRoles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmpRoles> partialUpdate(EmpRoles empRoles) {
        LOG.debug("Request to partially update EmpRoles : {}", empRoles);

        return empRolesRepository
            .findById(empRoles.getRoleId())
            .map(existingEmpRoles -> {
                if (empRoles.getRoleName() != null) {
                    existingEmpRoles.setRoleName(empRoles.getRoleName());
                }

                return existingEmpRoles;
            })
            .map(empRolesRepository::save);
    }

    /**
     * Get one empRoles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpRoles> findOne(Integer id) {
        LOG.debug("Request to get EmpRoles : {}", id);
        return empRolesRepository.findById(id);
    }

    /**
     * Delete the empRoles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete EmpRoles : {}", id);
        empRolesRepository.deleteById(id);
    }
}
