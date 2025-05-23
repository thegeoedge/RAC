package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine}.
 */
@Service
@Transactional
public class SalesInvoiceServiceChargeLineService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceServiceChargeLineService.class);

    private final SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository;
    private final JdbcTemplate jdbcTemplate;

    public SalesInvoiceServiceChargeLineService(
        SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository,
        JdbcTemplate jdbcTemplate
    ) {
        this.salesInvoiceServiceChargeLineRepository = salesInvoiceServiceChargeLineRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a salesInvoiceServiceChargeLine.
     *
     * @param salesInvoiceServiceChargeLine the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceServiceChargeLine save(SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine) {
        LOG.debug("Request to save SalesInvoiceServiceChargeLine : {}", salesInvoiceServiceChargeLine);
        return salesInvoiceServiceChargeLineRepository.save(salesInvoiceServiceChargeLine);
    }

    public List<SalesInvoiceServiceChargeLine> fetchServiceChargeLines(int invoiceId) {
        String sql = "SELECT * FROM SalesInvoiceServiceChargeLine WHERE InvoiceId  = ?";
        return jdbcTemplate.query(sql, new Object[] { invoiceId }, (rs, rowNum) -> {
            SalesInvoiceServiceChargeLine line = new SalesInvoiceServiceChargeLine();
            line.setInvoiceId(rs.getInt("invoiceid"));
            line.setLineId(rs.getInt("lineid"));
            line.setOptionId(rs.getInt("optionid"));
            line.setServiceName(rs.getString("servicename"));
            line.setServiceDescription(rs.getString("servicediscription"));
            line.setValue(rs.getFloat("value"));

            line.setIsCustomerService(rs.getBoolean("iscustomersrvice"));
            line.setDiscount(rs.getFloat("discount"));
            line.setServicePrice(rs.getFloat("serviceprice"));
            return line;
        });
    }

    /**
     * Update a salesInvoiceServiceChargeLine.
     *
     * @param salesInvoiceServiceChargeLine the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceServiceChargeLine update(SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine) {
        LOG.debug("Request to update SalesInvoiceServiceChargeLine : {}", salesInvoiceServiceChargeLine);
        return salesInvoiceServiceChargeLineRepository.save(salesInvoiceServiceChargeLine);
    }

    /**
     * Partially update a salesInvoiceServiceChargeLine.
     *
     * @param salesInvoiceServiceChargeLine the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesInvoiceServiceChargeLine> partialUpdate(SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine) {
        LOG.debug("Request to partially update SalesInvoiceServiceChargeLine : {}", salesInvoiceServiceChargeLine);

        return salesInvoiceServiceChargeLineRepository
            .findById(salesInvoiceServiceChargeLine.getInvoiceId())
            .map(existingSalesInvoiceServiceChargeLine -> {
                if (salesInvoiceServiceChargeLine.getLineId() != null) {
                    existingSalesInvoiceServiceChargeLine.setLineId(salesInvoiceServiceChargeLine.getLineId());
                }
                if (salesInvoiceServiceChargeLine.getOptionId() != null) {
                    existingSalesInvoiceServiceChargeLine.setOptionId(salesInvoiceServiceChargeLine.getOptionId());
                }
                if (salesInvoiceServiceChargeLine.getServiceName() != null) {
                    existingSalesInvoiceServiceChargeLine.setServiceName(salesInvoiceServiceChargeLine.getServiceName());
                }
                if (salesInvoiceServiceChargeLine.getServiceDescription() != null) {
                    existingSalesInvoiceServiceChargeLine.setServiceDescription(salesInvoiceServiceChargeLine.getServiceDescription());
                }
                if (salesInvoiceServiceChargeLine.getValue() != null) {
                    existingSalesInvoiceServiceChargeLine.setValue(salesInvoiceServiceChargeLine.getValue());
                }
                if (salesInvoiceServiceChargeLine.getIsCustomerService() != null) {
                    existingSalesInvoiceServiceChargeLine.setIsCustomerService(salesInvoiceServiceChargeLine.getIsCustomerService());
                }
                if (salesInvoiceServiceChargeLine.getDiscount() != null) {
                    existingSalesInvoiceServiceChargeLine.setDiscount(salesInvoiceServiceChargeLine.getDiscount());
                }
                if (salesInvoiceServiceChargeLine.getServicePrice() != null) {
                    existingSalesInvoiceServiceChargeLine.setServicePrice(salesInvoiceServiceChargeLine.getServicePrice());
                }

                return existingSalesInvoiceServiceChargeLine;
            })
            .map(salesInvoiceServiceChargeLineRepository::save);
    }

    /**
     * Get one salesInvoiceServiceChargeLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesInvoiceServiceChargeLine> findOne(Integer id) {
        LOG.debug("Request to get SalesInvoiceServiceChargeLine : {}", id);
        return salesInvoiceServiceChargeLineRepository.findById(id);
    }

    /**
     * Delete the salesInvoiceServiceChargeLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete SalesInvoiceServiceChargeLine : {}", id);
        salesInvoiceServiceChargeLineRepository.deleteById(id);
    }
}
