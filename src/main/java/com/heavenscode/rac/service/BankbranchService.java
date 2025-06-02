package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Bankbranch;
import com.heavenscode.rac.repository.BankbranchRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Bankbranch}.
 */
@Service
@Transactional
public class BankbranchService {

    private static final Logger LOG = LoggerFactory.getLogger(BankbranchService.class);

    private final BankbranchRepository bankbranchRepository;

    public BankbranchService(BankbranchRepository bankbranchRepository) {
        this.bankbranchRepository = bankbranchRepository;
    }

    /**
     * Save a bankbranch.
     *
     * @param bankbranch the entity to save.
     * @return the persisted entity.
     */
    public Bankbranch save(Bankbranch bankbranch) {
        LOG.debug("Request to save Bankbranch : {}", bankbranch);
        return bankbranchRepository.save(bankbranch);
    }

    /**
     * Update a bankbranch.
     *
     * @param bankbranch the entity to save.
     * @return the persisted entity.
     */
    public Bankbranch update(Bankbranch bankbranch) {
        LOG.debug("Request to update Bankbranch : {}", bankbranch);
        return bankbranchRepository.save(bankbranch);
    }

    /**
     * Partially update a bankbranch.
     *
     * @param bankbranch the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Bankbranch> partialUpdate(Bankbranch bankbranch) {
        LOG.debug("Request to partially update Bankbranch : {}", bankbranch);

        return bankbranchRepository
            .findById(bankbranch.getId())
            .map(existingBankbranch -> {
                if (bankbranch.getBankcode() != null) {
                    existingBankbranch.setBankcode(bankbranch.getBankcode());
                }
                if (bankbranch.getBranchcode() != null) {
                    existingBankbranch.setBranchcode(bankbranch.getBranchcode());
                }
                if (bankbranch.getBranchname() != null) {
                    existingBankbranch.setBranchname(bankbranch.getBranchname());
                }

                return existingBankbranch;
            })
            .map(bankbranchRepository::save);
    }

    /**
     * Get one bankbranch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Bankbranch> findOne(Long id) {
        LOG.debug("Request to get Bankbranch : {}", id);
        return bankbranchRepository.findById(id);
    }

    /**
     * Delete the bankbranch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Bankbranch : {}", id);
        bankbranchRepository.deleteById(id);
    }
}
