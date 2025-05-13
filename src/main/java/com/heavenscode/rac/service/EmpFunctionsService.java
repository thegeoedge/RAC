package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.EmpFunctions;
import com.heavenscode.rac.repository.EmpFunctionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.EmpFunctions}.
 */
@Service
@Transactional
public class EmpFunctionsService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpFunctionsService.class);

    private final EmpFunctionsRepository empFunctionsRepository;

    public EmpFunctionsService(EmpFunctionsRepository empFunctionsRepository) {
        this.empFunctionsRepository = empFunctionsRepository;
    }

    /**
     * Save a empFunctions.
     *
     * @param empFunctions the entity to save.
     * @return the persisted entity.
     */
    public EmpFunctions save(EmpFunctions empFunctions) {
        LOG.debug("Request to save EmpFunctions : {}", empFunctions);
        return empFunctionsRepository.save(empFunctions);
    }

    /**
     * Update a empFunctions.
     *
     * @param empFunctions the entity to save.
     * @return the persisted entity.
     */
    public EmpFunctions update(EmpFunctions empFunctions) {
        LOG.debug("Request to update EmpFunctions : {}", empFunctions);
        return empFunctionsRepository.save(empFunctions);
    }

    /**
     * Partially update a empFunctions.
     *
     * @param empFunctions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmpFunctions> partialUpdate(EmpFunctions empFunctions) {
        LOG.debug("Request to partially update EmpFunctions : {}", empFunctions);

        return empFunctionsRepository
            .findById(empFunctions.getFunctionId())
            .map(existingEmpFunctions -> {
                if (empFunctions.getFunctionName() != null) {
                    existingEmpFunctions.setFunctionName(empFunctions.getFunctionName());
                }
                if (empFunctions.getModuleId() != null) {
                    existingEmpFunctions.setModuleId(empFunctions.getModuleId());
                }

                return existingEmpFunctions;
            })
            .map(empFunctionsRepository::save);
    }

    /**
     * Get one empFunctions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpFunctions> findOne(Integer id) {
        LOG.debug("Request to get EmpFunctions : {}", id);
        return empFunctionsRepository.findById(id);
    }

    /**
     * Delete the empFunctions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete EmpFunctions : {}", id);
        empFunctionsRepository.deleteById(id);
    }
}
