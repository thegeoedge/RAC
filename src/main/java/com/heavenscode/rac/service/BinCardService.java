package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.BinCard;
import com.heavenscode.rac.repository.BinCardRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.BinCard}.
 */
@Service
@Transactional
public class BinCardService {

    private static final Logger LOG = LoggerFactory.getLogger(BinCardService.class);

    private final BinCardRepository binCardRepository;

    public BinCardService(BinCardRepository binCardRepository) {
        this.binCardRepository = binCardRepository;
    }

    /**
     * Save a binCard.
     *
     * @param binCard the entity to save.
     * @return the persisted entity.
     */
    public BinCard save(BinCard binCard) {
        LOG.debug("Request to save BinCard : {}", binCard);
        return binCardRepository.save(binCard);
    }

    /**
     * Update a binCard.
     *
     * @param binCard the entity to save.
     * @return the persisted entity.
     */
    public BinCard update(BinCard binCard) {
        LOG.debug("Request to update BinCard : {}", binCard);
        return binCardRepository.save(binCard);
    }

    /**
     * Partially update a binCard.
     *
     * @param binCard the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BinCard> partialUpdate(BinCard binCard) {
        LOG.debug("Request to partially update BinCard : {}", binCard);

        return binCardRepository
            .findById(binCard.getId())
            .map(existingBinCard -> {
                if (binCard.getItemID() != null) {
                    existingBinCard.setItemID(binCard.getItemID());
                }
                if (binCard.getItemCode() != null) {
                    existingBinCard.setItemCode(binCard.getItemCode());
                }
                if (binCard.getReference() != null) {
                    existingBinCard.setReference(binCard.getReference());
                }
                if (binCard.getTxDate() != null) {
                    existingBinCard.setTxDate(binCard.getTxDate());
                }
                if (binCard.getQtyIn() != null) {
                    existingBinCard.setQtyIn(binCard.getQtyIn());
                }
                if (binCard.getQtyOut() != null) {
                    existingBinCard.setQtyOut(binCard.getQtyOut());
                }
                if (binCard.getPrice() != null) {
                    existingBinCard.setPrice(binCard.getPrice());
                }
                if (binCard.getlMU() != null) {
                    existingBinCard.setlMU(binCard.getlMU());
                }
                if (binCard.getlMD() != null) {
                    existingBinCard.setlMD(binCard.getlMD());
                }
                if (binCard.getReferenceCode() != null) {
                    existingBinCard.setReferenceCode(binCard.getReferenceCode());
                }
                if (binCard.getRecordDate() != null) {
                    existingBinCard.setRecordDate(binCard.getRecordDate());
                }
                if (binCard.getBatchId() != null) {
                    existingBinCard.setBatchId(binCard.getBatchId());
                }
                if (binCard.getLocationID() != null) {
                    existingBinCard.setLocationID(binCard.getLocationID());
                }
                if (binCard.getLocationCode() != null) {
                    existingBinCard.setLocationCode(binCard.getLocationCode());
                }
                if (binCard.getOpening() != null) {
                    existingBinCard.setOpening(binCard.getOpening());
                }
                if (binCard.getDescription() != null) {
                    existingBinCard.setDescription(binCard.getDescription());
                }
                if (binCard.getReferenceDoc() != null) {
                    existingBinCard.setReferenceDoc(binCard.getReferenceDoc());
                }

                return existingBinCard;
            })
            .map(binCardRepository::save);
    }

    /**
     * Get one binCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BinCard> findOne(Long id) {
        LOG.debug("Request to get BinCard : {}", id);
        return binCardRepository.findById(id);
    }

    /**
     * Delete the binCard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete BinCard : {}", id);
        binCardRepository.deleteById(id);
    }
}
