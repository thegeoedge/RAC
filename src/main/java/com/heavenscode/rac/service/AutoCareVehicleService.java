package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.AutoCareVehicle;
import com.heavenscode.rac.repository.AutoCareVehicleRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.AutoCareVehicle}.
 */
@Service
@Transactional
public class AutoCareVehicleService {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCareVehicleService.class);

    private final AutoCareVehicleRepository autoCareVehicleRepository;

    public AutoCareVehicleService(AutoCareVehicleRepository autoCareVehicleRepository) {
        this.autoCareVehicleRepository = autoCareVehicleRepository;
    }

    /**
     * Save a autoCareVehicle.
     *
     * @param autoCareVehicle the entity to save.
     * @return the persisted entity.
     */
    public AutoCareVehicle save(AutoCareVehicle autoCareVehicle) {
        LOG.debug("Request to save AutoCareVehicle : {}", autoCareVehicle);
        return autoCareVehicleRepository.save(autoCareVehicle);
    }

    /**
     * Update a autoCareVehicle.
     *
     * @param autoCareVehicle the entity to save.
     * @return the persisted entity.
     */
    public AutoCareVehicle update(AutoCareVehicle autoCareVehicle) {
        LOG.debug("Request to update AutoCareVehicle : {}", autoCareVehicle);
        return autoCareVehicleRepository.save(autoCareVehicle);
    }

    /**
     * Partially update a autoCareVehicle.
     *
     * @param autoCareVehicle the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AutoCareVehicle> partialUpdate(AutoCareVehicle autoCareVehicle) {
        LOG.debug("Request to partially update AutoCareVehicle : {}", autoCareVehicle);

        return autoCareVehicleRepository
            .findById(autoCareVehicle.getId())
            .map(existingAutoCareVehicle -> {
                if (autoCareVehicle.getCustomerId() != null) {
                    existingAutoCareVehicle.setCustomerId(autoCareVehicle.getCustomerId());
                }
                if (autoCareVehicle.getCustomerName() != null) {
                    existingAutoCareVehicle.setCustomerName(autoCareVehicle.getCustomerName());
                }
                if (autoCareVehicle.getCustomerTel() != null) {
                    existingAutoCareVehicle.setCustomerTel(autoCareVehicle.getCustomerTel());
                }
                if (autoCareVehicle.getVehicleNumber() != null) {
                    existingAutoCareVehicle.setVehicleNumber(autoCareVehicle.getVehicleNumber());
                }
                if (autoCareVehicle.getBrandId() != null) {
                    existingAutoCareVehicle.setBrandId(autoCareVehicle.getBrandId());
                }
                if (autoCareVehicle.getBrandName() != null) {
                    existingAutoCareVehicle.setBrandName(autoCareVehicle.getBrandName());
                }
                if (autoCareVehicle.getModel() != null) {
                    existingAutoCareVehicle.setModel(autoCareVehicle.getModel());
                }
                if (autoCareVehicle.getMillage() != null) {
                    existingAutoCareVehicle.setMillage(autoCareVehicle.getMillage());
                }
                if (autoCareVehicle.getManufactureYear() != null) {
                    existingAutoCareVehicle.setManufactureYear(autoCareVehicle.getManufactureYear());
                }
                if (autoCareVehicle.getLastServiceDate() != null) {
                    existingAutoCareVehicle.setLastServiceDate(autoCareVehicle.getLastServiceDate());
                }
                if (autoCareVehicle.getNextServiceDate() != null) {
                    existingAutoCareVehicle.setNextServiceDate(autoCareVehicle.getNextServiceDate());
                }
                if (autoCareVehicle.getDescription() != null) {
                    existingAutoCareVehicle.setDescription(autoCareVehicle.getDescription());
                }
                if (autoCareVehicle.getLmu() != null) {
                    existingAutoCareVehicle.setLmu(autoCareVehicle.getLmu());
                }
                if (autoCareVehicle.getLmd() != null) {
                    existingAutoCareVehicle.setLmd(autoCareVehicle.getLmd());
                }

                return existingAutoCareVehicle;
            })
            .map(autoCareVehicleRepository::save);
    }

    /**
     * Get one autoCareVehicle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutoCareVehicle> findOne(Long id) {
        LOG.debug("Request to get AutoCareVehicle : {}", id);
        return autoCareVehicleRepository.findById(id);
    }

    /**
     * Delete the autoCareVehicle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete AutoCareVehicle : {}", id);
        autoCareVehicleRepository.deleteById(id);
    }
}
