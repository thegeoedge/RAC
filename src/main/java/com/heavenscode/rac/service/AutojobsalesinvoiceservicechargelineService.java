package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline}.
 */
@Service
@Transactional
public class AutojobsalesinvoiceservicechargelineService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsalesinvoiceservicechargelineService.class);

    private final AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;
    private final JdbcTemplate jdbcTemplate;

    public AutojobsalesinvoiceservicechargelineService(
        AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository,
        JdbcTemplate jdbcTemplate
    ) {
        this.autojobsalesinvoiceservicechargelineRepository = autojobsalesinvoiceservicechargelineRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the entity to save.
     * @return the persisted entity.
     */
    public Autojobsalesinvoiceservicechargeline save(Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline) {
        LOG.debug("Request to save Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);
        return autojobsalesinvoiceservicechargelineRepository.save(autojobsalesinvoiceservicechargeline);
    }

    /**
     * Update a autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the entity to save.
     * @return the persisted entity.
     */
    public Autojobsalesinvoiceservicechargeline update(Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline) {
        LOG.debug("Request to update Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);
        return autojobsalesinvoiceservicechargelineRepository.save(autojobsalesinvoiceservicechargeline);
    }

    public List<Autojobsalesinvoiceservicechargeline> fetchServiceChargeLines(int invoiceId) {
        String sql = "SELECT * FROM AutoJobSalesInvoiceServiceChargeLine WHERE InvoiceId  = ?";
        return jdbcTemplate.query(sql, new Object[] { invoiceId }, (rs, rowNum) -> {
            Autojobsalesinvoiceservicechargeline line = new Autojobsalesinvoiceservicechargeline();
            line.setInvoiceid(rs.getInt("invoiceid"));
            line.setLineid(rs.getInt("lineid"));
            line.setOptionid(rs.getInt("optionid"));
            line.setServicename(rs.getString("servicename"));
            line.setServicediscription(rs.getString("servicediscription"));
            line.setValue(rs.getFloat("value"));
            line.setAddedbyid(rs.getInt("addedbyid"));
            line.setIscustomersrvice(rs.getBoolean("iscustomersrvice"));
            line.setDiscount(rs.getFloat("discount"));
            line.setServiceprice(rs.getFloat("serviceprice"));
            return line;
        });
    }

    /**
     * Partially update a autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsalesinvoiceservicechargeline> partialUpdate(
        Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) {
        LOG.debug("Request to partially update Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);

        return autojobsalesinvoiceservicechargelineRepository
            .findById(autojobsalesinvoiceservicechargeline.getInvoiceid())
            .map(existingAutojobsalesinvoiceservicechargeline -> {
                if (autojobsalesinvoiceservicechargeline.getLineid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setLineid(autojobsalesinvoiceservicechargeline.getLineid());
                }
                if (autojobsalesinvoiceservicechargeline.getOptionid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setOptionid(autojobsalesinvoiceservicechargeline.getOptionid());
                }
                if (autojobsalesinvoiceservicechargeline.getServicename() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServicename(autojobsalesinvoiceservicechargeline.getServicename());
                }
                if (autojobsalesinvoiceservicechargeline.getServicediscription() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServicediscription(
                        autojobsalesinvoiceservicechargeline.getServicediscription()
                    );
                }
                if (autojobsalesinvoiceservicechargeline.getValue() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setValue(autojobsalesinvoiceservicechargeline.getValue());
                }
                if (autojobsalesinvoiceservicechargeline.getAddedbyid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setAddedbyid(autojobsalesinvoiceservicechargeline.getAddedbyid());
                }
                if (autojobsalesinvoiceservicechargeline.getIscustomersrvice() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setIscustomersrvice(
                        autojobsalesinvoiceservicechargeline.getIscustomersrvice()
                    );
                }
                if (autojobsalesinvoiceservicechargeline.getDiscount() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setDiscount(autojobsalesinvoiceservicechargeline.getDiscount());
                }
                if (autojobsalesinvoiceservicechargeline.getServiceprice() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServiceprice(autojobsalesinvoiceservicechargeline.getServiceprice());
                }

                return existingAutojobsalesinvoiceservicechargeline;
            })
            .map(autojobsalesinvoiceservicechargelineRepository::save);
    }

    /**
     * Get one autojobsalesinvoiceservicechargeline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsalesinvoiceservicechargeline> findOne(Integer id) {
        LOG.debug("Request to get Autojobsalesinvoiceservicechargeline : {}", id);
        return autojobsalesinvoiceservicechargelineRepository.findById(id);
    }

    /**
     * Delete the autojobsalesinvoiceservicechargeline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Autojobsalesinvoiceservicechargeline : {}", id);
        autojobsalesinvoiceservicechargelineRepository.deleteById(id);
    }
}
