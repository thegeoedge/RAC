package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import com.heavenscode.rac.repository.AutojobsaleinvoicecommonservicechargeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge}.
 */
@Service
@Transactional
public class AutojobsaleinvoicecommonservicechargeService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsaleinvoicecommonservicechargeService.class);

    private final AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository;
    private final JdbcTemplate jdbcTemplate;

    public AutojobsaleinvoicecommonservicechargeService(
        AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository,
        JdbcTemplate jdbcTemplate
    ) {
        this.autojobsaleinvoicecommonservicechargeRepository = autojobsaleinvoicecommonservicechargeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a autojobsaleinvoicecommonservicecharge.
     *
     * @param autojobsaleinvoicecommonservicecharge the entity to save.
     * @return the persisted entity.
     */
    public Autojobsaleinvoicecommonservicecharge save(Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge) {
        LOG.debug("Request to save Autojobsaleinvoicecommonservicecharge : {}", autojobsaleinvoicecommonservicecharge);
        return autojobsaleinvoicecommonservicechargeRepository.save(autojobsaleinvoicecommonservicecharge);
    }

    /**
     * Update a autojobsaleinvoicecommonservicecharge.
     *
     * @param autojobsaleinvoicecommonservicecharge the entity to save.
     * @return the persisted entity.
     */
    public Autojobsaleinvoicecommonservicecharge update(Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge) {
        LOG.debug("Request to update Autojobsaleinvoicecommonservicecharge : {}", autojobsaleinvoicecommonservicecharge);
        return autojobsaleinvoicecommonservicechargeRepository.save(autojobsaleinvoicecommonservicecharge);
    }

    public List<Autojobsaleinvoicecommonservicecharge> fetchJobInvoiceLiness(int invoiceID) {
        String sql = "SELECT * FROM AutoJobSaleInvoiceCommonServiceCharge WHERE InvoiceID = ?";
        return jdbcTemplate.query(sql, new Object[] { invoiceID }, (rs, rowNum) -> {
            Autojobsaleinvoicecommonservicecharge line = new Autojobsaleinvoicecommonservicecharge();
            line.setInvoiceid(rs.getInt("InvoiceID"));
            line.setLineid(rs.getInt("LineID"));
            line.setOptionid(rs.getInt("OptionID"));
            line.setMainid(rs.getInt("MainID"));
            line.setCode(rs.getString("Code"));
            line.setName(rs.getString("Name"));
            line.setDescription(rs.getString("Description"));
            line.setValue(rs.getFloat("Value"));
            line.setAddedbyid(rs.getInt("AddedById"));
            line.setDiscount(rs.getFloat("Discount"));
            line.setServiceprice(rs.getFloat("ServicePrice"));
            return line;
        });
    }

    /**
     * Partially update a autojobsaleinvoicecommonservicecharge.
     *
     * @param autojobsaleinvoicecommonservicecharge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsaleinvoicecommonservicecharge> partialUpdate(
        Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) {
        LOG.debug("Request to partially update Autojobsaleinvoicecommonservicecharge : {}", autojobsaleinvoicecommonservicecharge);

        return autojobsaleinvoicecommonservicechargeRepository
            .findById(autojobsaleinvoicecommonservicecharge.getInvoiceid())
            .map(existingAutojobsaleinvoicecommonservicecharge -> {
                if (autojobsaleinvoicecommonservicecharge.getLineid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setLineid(autojobsaleinvoicecommonservicecharge.getLineid());
                }
                if (autojobsaleinvoicecommonservicecharge.getOptionid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setOptionid(autojobsaleinvoicecommonservicecharge.getOptionid());
                }
                if (autojobsaleinvoicecommonservicecharge.getMainid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setMainid(autojobsaleinvoicecommonservicecharge.getMainid());
                }
                if (autojobsaleinvoicecommonservicecharge.getCode() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setCode(autojobsaleinvoicecommonservicecharge.getCode());
                }
                if (autojobsaleinvoicecommonservicecharge.getName() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setName(autojobsaleinvoicecommonservicecharge.getName());
                }
                if (autojobsaleinvoicecommonservicecharge.getDescription() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setDescription(autojobsaleinvoicecommonservicecharge.getDescription());
                }
                if (autojobsaleinvoicecommonservicecharge.getValue() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setValue(autojobsaleinvoicecommonservicecharge.getValue());
                }
                if (autojobsaleinvoicecommonservicecharge.getAddedbyid() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setAddedbyid(autojobsaleinvoicecommonservicecharge.getAddedbyid());
                }
                if (autojobsaleinvoicecommonservicecharge.getDiscount() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setDiscount(autojobsaleinvoicecommonservicecharge.getDiscount());
                }
                if (autojobsaleinvoicecommonservicecharge.getServiceprice() != null) {
                    existingAutojobsaleinvoicecommonservicecharge.setServiceprice(autojobsaleinvoicecommonservicecharge.getServiceprice());
                }

                return existingAutojobsaleinvoicecommonservicecharge;
            })
            .map(autojobsaleinvoicecommonservicechargeRepository::save);
    }

    /**
     * Get one autojobsaleinvoicecommonservicecharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsaleinvoicecommonservicecharge> findOne(Integer id) {
        LOG.debug("Request to get Autojobsaleinvoicecommonservicecharge : {}", id);
        return autojobsaleinvoicecommonservicechargeRepository.findById(id);
    }

    /**
     * Delete the autojobsaleinvoicecommonservicecharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Autojobsaleinvoicecommonservicecharge : {}", id);
        autojobsaleinvoicecommonservicechargeRepository.deleteById(id);
    }
}
