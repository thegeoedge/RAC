package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsinvoice;
import com.heavenscode.rac.repository.AutojobsinvoiceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsinvoice}.
 */
@Service
@Transactional
public class AutojobsinvoiceService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoiceService.class);

    private final AutojobsinvoiceRepository autojobsinvoiceRepository;

    public AutojobsinvoiceService(AutojobsinvoiceRepository autojobsinvoiceRepository) {
        this.autojobsinvoiceRepository = autojobsinvoiceRepository;
    }

    /**
     * Save a autojobsinvoice.
     *
     * @param autojobsinvoice the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoice save(Autojobsinvoice autojobsinvoice) {
        LOG.debug("Request to save Autojobsinvoice : {}", autojobsinvoice);
        return autojobsinvoiceRepository.save(autojobsinvoice);
    }

    /**
     * Update a autojobsinvoice.
     *
     * @param autojobsinvoice the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoice update(Autojobsinvoice autojobsinvoice) {
        LOG.debug("Request to update Autojobsinvoice : {}", autojobsinvoice);
        return autojobsinvoiceRepository.save(autojobsinvoice);
    }

    /**
     * Partially update a autojobsinvoice.
     *
     * @param autojobsinvoice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsinvoice> partialUpdate(Autojobsinvoice autojobsinvoice) {
        LOG.debug("Request to partially update Autojobsinvoice : {}", autojobsinvoice);

        return autojobsinvoiceRepository
            .findById(autojobsinvoice.getId())
            .map(existingAutojobsinvoice -> {
                if (autojobsinvoice.getCode() != null) {
                    existingAutojobsinvoice.setCode(autojobsinvoice.getCode());
                }
                if (autojobsinvoice.getInvoicedate() != null) {
                    existingAutojobsinvoice.setInvoicedate(autojobsinvoice.getInvoicedate());
                }
                if (autojobsinvoice.getCreateddate() != null) {
                    existingAutojobsinvoice.setCreateddate(autojobsinvoice.getCreateddate());
                }
                if (autojobsinvoice.getJobid() != null) {
                    existingAutojobsinvoice.setJobid(autojobsinvoice.getJobid());
                }
                if (autojobsinvoice.getQuoteid() != null) {
                    existingAutojobsinvoice.setQuoteid(autojobsinvoice.getQuoteid());
                }
                if (autojobsinvoice.getOrderid() != null) {
                    existingAutojobsinvoice.setOrderid(autojobsinvoice.getOrderid());
                }
                if (autojobsinvoice.getDelieverydate() != null) {
                    existingAutojobsinvoice.setDelieverydate(autojobsinvoice.getDelieverydate());
                }
                if (autojobsinvoice.getAutojobsrepid() != null) {
                    existingAutojobsinvoice.setAutojobsrepid(autojobsinvoice.getAutojobsrepid());
                }
                if (autojobsinvoice.getAutojobsrepname() != null) {
                    existingAutojobsinvoice.setAutojobsrepname(autojobsinvoice.getAutojobsrepname());
                }
                if (autojobsinvoice.getDelieverfrom() != null) {
                    existingAutojobsinvoice.setDelieverfrom(autojobsinvoice.getDelieverfrom());
                }
                if (autojobsinvoice.getCustomerid() != null) {
                    existingAutojobsinvoice.setCustomerid(autojobsinvoice.getCustomerid());
                }
                if (autojobsinvoice.getCustomername() != null) {
                    existingAutojobsinvoice.setCustomername(autojobsinvoice.getCustomername());
                }
                if (autojobsinvoice.getCustomeraddress() != null) {
                    existingAutojobsinvoice.setCustomeraddress(autojobsinvoice.getCustomeraddress());
                }
                if (autojobsinvoice.getDeliveryaddress() != null) {
                    existingAutojobsinvoice.setDeliveryaddress(autojobsinvoice.getDeliveryaddress());
                }
                if (autojobsinvoice.getSubtotal() != null) {
                    existingAutojobsinvoice.setSubtotal(autojobsinvoice.getSubtotal());
                }
                if (autojobsinvoice.getTotaltax() != null) {
                    existingAutojobsinvoice.setTotaltax(autojobsinvoice.getTotaltax());
                }
                if (autojobsinvoice.getTotaldiscount() != null) {
                    existingAutojobsinvoice.setTotaldiscount(autojobsinvoice.getTotaldiscount());
                }
                if (autojobsinvoice.getNettotal() != null) {
                    existingAutojobsinvoice.setNettotal(autojobsinvoice.getNettotal());
                }
                if (autojobsinvoice.getMessage() != null) {
                    existingAutojobsinvoice.setMessage(autojobsinvoice.getMessage());
                }
                if (autojobsinvoice.getLmu() != null) {
                    existingAutojobsinvoice.setLmu(autojobsinvoice.getLmu());
                }
                if (autojobsinvoice.getLmd() != null) {
                    existingAutojobsinvoice.setLmd(autojobsinvoice.getLmd());
                }
                if (autojobsinvoice.getPaidamount() != null) {
                    existingAutojobsinvoice.setPaidamount(autojobsinvoice.getPaidamount());
                }
                if (autojobsinvoice.getAmountowing() != null) {
                    existingAutojobsinvoice.setAmountowing(autojobsinvoice.getAmountowing());
                }
                if (autojobsinvoice.getIsactive() != null) {
                    existingAutojobsinvoice.setIsactive(autojobsinvoice.getIsactive());
                }
                if (autojobsinvoice.getLocationid() != null) {
                    existingAutojobsinvoice.setLocationid(autojobsinvoice.getLocationid());
                }
                if (autojobsinvoice.getLocationcode() != null) {
                    existingAutojobsinvoice.setLocationcode(autojobsinvoice.getLocationcode());
                }
                if (autojobsinvoice.getReferencecode() != null) {
                    existingAutojobsinvoice.setReferencecode(autojobsinvoice.getReferencecode());
                }
                if (autojobsinvoice.getCreatedbyid() != null) {
                    existingAutojobsinvoice.setCreatedbyid(autojobsinvoice.getCreatedbyid());
                }
                if (autojobsinvoice.getCreatedbyname() != null) {
                    existingAutojobsinvoice.setCreatedbyname(autojobsinvoice.getCreatedbyname());
                }
                if (autojobsinvoice.getAutocarecompanyid() != null) {
                    existingAutojobsinvoice.setAutocarecompanyid(autojobsinvoice.getAutocarecompanyid());
                }

                return existingAutojobsinvoice;
            })
            .map(autojobsinvoiceRepository::save);
    }

    /**
     * Get one autojobsinvoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsinvoice> findOne(Long id) {
        LOG.debug("Request to get Autojobsinvoice : {}", id);
        return autojobsinvoiceRepository.findById(id);
    }

    /**
     * Delete the autojobsinvoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Autojobsinvoice : {}", id);
        autojobsinvoiceRepository.deleteById(id);
    }
}
