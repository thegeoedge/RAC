package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Inventorybatches;
import com.heavenscode.rac.repository.InventorybatchesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Inventorybatches}.
 */
@Service
@Transactional
public class InventorybatchesService {

    private static final Logger LOG = LoggerFactory.getLogger(InventorybatchesService.class);

    private final InventorybatchesRepository inventorybatchesRepository;

    public InventorybatchesService(InventorybatchesRepository inventorybatchesRepository) {
        this.inventorybatchesRepository = inventorybatchesRepository;
    }

    /**
     * Save a inventorybatches.
     *
     * @param inventorybatches the entity to save.
     * @return the persisted entity.
     */
    public Inventorybatches save(Inventorybatches inventorybatches) {
        LOG.debug("Request to save Inventorybatches : {}", inventorybatches);
        return inventorybatchesRepository.save(inventorybatches);
    }

    /**
     * Update a inventorybatches.
     *
     * @param inventorybatches the entity to save.
     * @return the persisted entity.
     */
    public Inventorybatches update(Inventorybatches inventorybatches) {
        LOG.debug("Request to update Inventorybatches : {}", inventorybatches);
        return inventorybatchesRepository.save(inventorybatches);
    }

    /**
     * Partially update a inventorybatches.
     *
     * @param inventorybatches the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Inventorybatches> partialUpdate(Inventorybatches inventorybatches) {
        LOG.debug("Request to partially update Inventorybatches : {}", inventorybatches);

        return inventorybatchesRepository
            .findById(inventorybatches.getId())
            .map(existingInventorybatches -> {
                if (inventorybatches.getItemid() != null) {
                    existingInventorybatches.setItemid(inventorybatches.getItemid());
                }
                if (inventorybatches.getCode() != null) {
                    existingInventorybatches.setCode(inventorybatches.getCode());
                }
                if (inventorybatches.getTxdate() != null) {
                    existingInventorybatches.setTxdate(inventorybatches.getTxdate());
                }
                if (inventorybatches.getCost() != null) {
                    existingInventorybatches.setCost(inventorybatches.getCost());
                }
                if (inventorybatches.getPrice() != null) {
                    existingInventorybatches.setPrice(inventorybatches.getPrice());
                }
                if (inventorybatches.getCostwithoutvat() != null) {
                    existingInventorybatches.setCostwithoutvat(inventorybatches.getCostwithoutvat());
                }
                if (inventorybatches.getPricewithoutvat() != null) {
                    existingInventorybatches.setPricewithoutvat(inventorybatches.getPricewithoutvat());
                }
                if (inventorybatches.getNotes() != null) {
                    existingInventorybatches.setNotes(inventorybatches.getNotes());
                }
                if (inventorybatches.getLmu() != null) {
                    existingInventorybatches.setLmu(inventorybatches.getLmu());
                }
                if (inventorybatches.getLmd() != null) {
                    existingInventorybatches.setLmd(inventorybatches.getLmd());
                }
                if (inventorybatches.getLineid() != null) {
                    existingInventorybatches.setLineid(inventorybatches.getLineid());
                }
                if (inventorybatches.getManufacturedate() != null) {
                    existingInventorybatches.setManufacturedate(inventorybatches.getManufacturedate());
                }
                if (inventorybatches.getExpiredate() != null) {
                    existingInventorybatches.setExpiredate(inventorybatches.getExpiredate());
                }
                if (inventorybatches.getQuantity() != null) {
                    existingInventorybatches.setQuantity(inventorybatches.getQuantity());
                }
                if (inventorybatches.getAddeddate() != null) {
                    existingInventorybatches.setAddeddate(inventorybatches.getAddeddate());
                }
                if (inventorybatches.getCosttotal() != null) {
                    existingInventorybatches.setCosttotal(inventorybatches.getCosttotal());
                }
                if (inventorybatches.getReturnprice() != null) {
                    existingInventorybatches.setReturnprice(inventorybatches.getReturnprice());
                }

                return existingInventorybatches;
            })
            .map(inventorybatchesRepository::save);
    }

    /**
     * Get one inventorybatches by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Inventorybatches> findOne(Long id) {
        LOG.debug("Request to get Inventorybatches : {}", id);
        return inventorybatchesRepository.findById(id);
    }

    /**
     * Delete the inventorybatches by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Inventorybatches : {}", id);
        inventorybatchesRepository.deleteById(id);
    }
}
