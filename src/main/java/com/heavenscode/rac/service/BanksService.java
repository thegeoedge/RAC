package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Banks;
import com.heavenscode.rac.repository.BanksRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Banks}.
 */
@Service
@Transactional
public class BanksService {

    private static final Logger LOG = LoggerFactory.getLogger(BanksService.class);

    private final BanksRepository banksRepository;

    public BanksService(BanksRepository banksRepository) {
        this.banksRepository = banksRepository;
    }

    /**
     * Save a banks.
     *
     * @param banks the entity to save.
     * @return the persisted entity.
     */
    public Banks save(Banks banks) {
        LOG.debug("Request to save Banks : {}", banks);
        return banksRepository.save(banks);
    }

    /**
     * Update a banks.
     *
     * @param banks the entity to save.
     * @return the persisted entity.
     */
    public Banks update(Banks banks) {
        LOG.debug("Request to update Banks : {}", banks);
        return banksRepository.save(banks);
    }

    /**
     * Partially update a banks.
     *
     * @param banks the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Banks> partialUpdate(Banks banks) {
        LOG.debug("Request to partially update Banks : {}", banks);

        return banksRepository
            .findById(banks.getId())
            .map(existingBanks -> {
                if (banks.getCode() != null) {
                    existingBanks.setCode(banks.getCode());
                }
                if (banks.getName() != null) {
                    existingBanks.setName(banks.getName());
                }
                if (banks.getDescription() != null) {
                    existingBanks.setDescription(banks.getDescription());
                }
                if (banks.getLmu() != null) {
                    existingBanks.setLmu(banks.getLmu());
                }
                if (banks.getLmd() != null) {
                    existingBanks.setLmd(banks.getLmd());
                }

                return existingBanks;
            })
            .map(banksRepository::save);
    }

    /**
     * Get one banks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Banks> findOne(Long id) {
        LOG.debug("Request to get Banks : {}", id);
        return banksRepository.findById(id);
    }

    /**
     * Delete the banks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Banks : {}", id);
        banksRepository.deleteById(id);
    }
}
