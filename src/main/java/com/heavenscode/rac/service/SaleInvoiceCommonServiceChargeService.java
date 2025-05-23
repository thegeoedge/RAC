package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge}.
 */
@Service
@Transactional
public class SaleInvoiceCommonServiceChargeService {

    private static final Logger LOG = LoggerFactory.getLogger(SaleInvoiceCommonServiceChargeService.class);

    private final SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository;
    private final JdbcTemplate jdbcTemplate;

    public SaleInvoiceCommonServiceChargeService(
        SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository,
        JdbcTemplate jdbcTemplate
    ) {
        this.saleInvoiceCommonServiceChargeRepository = saleInvoiceCommonServiceChargeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a saleInvoiceCommonServiceCharge.
     *
     * @param saleInvoiceCommonServiceCharge the entity to save.
     * @return the persisted entity.
     */
    public SaleInvoiceCommonServiceCharge save(SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge) {
        LOG.debug("Request to save SaleInvoiceCommonServiceCharge : {}", saleInvoiceCommonServiceCharge);
        return saleInvoiceCommonServiceChargeRepository.save(saleInvoiceCommonServiceCharge);
    }

    /**
     * Update a saleInvoiceCommonServiceCharge.
     *
     * @param saleInvoiceCommonServiceCharge the entity to save.
     * @return the persisted entity.
     */
    public SaleInvoiceCommonServiceCharge update(SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge) {
        LOG.debug("Request to update SaleInvoiceCommonServiceCharge : {}", saleInvoiceCommonServiceCharge);
        return saleInvoiceCommonServiceChargeRepository.save(saleInvoiceCommonServiceCharge);
    }

    public List<SaleInvoiceCommonServiceCharge> fetchJobInvoiceLiness(int invoiceID) {
        String sql = "SELECT * FROM SaleInvoiceCommonServiceCharge WHERE InvoiceID = ?";
        return jdbcTemplate.query(sql, new Object[] { invoiceID }, (rs, rowNum) -> {
            SaleInvoiceCommonServiceCharge line = new SaleInvoiceCommonServiceCharge();
            line.setInvoiceId(rs.getInt("InvoiceID"));
            line.setLineId(rs.getInt("LineID"));
            line.setOptionId(rs.getInt("OptionID"));
            line.setMainId(rs.getInt("MainID"));
            line.setCode(rs.getString("Code"));
            line.setName(rs.getString("Name"));
            line.setDescription(rs.getString("Description"));
            line.setValue(rs.getFloat("Value"));

            line.setDiscount(rs.getFloat("Discount"));
            line.setServicePrice(rs.getFloat("ServicePrice"));
            return line;
        });
    }

    /**
     * Partially update a saleInvoiceCommonServiceCharge.
     *
     * @param saleInvoiceCommonServiceCharge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleInvoiceCommonServiceCharge> partialUpdate(SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge) {
        LOG.debug("Request to partially update SaleInvoiceCommonServiceCharge : {}", saleInvoiceCommonServiceCharge);

        return saleInvoiceCommonServiceChargeRepository
            .findById(saleInvoiceCommonServiceCharge.getInvoiceId())
            .map(existingSaleInvoiceCommonServiceCharge -> {
                if (saleInvoiceCommonServiceCharge.getLineId() != null) {
                    existingSaleInvoiceCommonServiceCharge.setLineId(saleInvoiceCommonServiceCharge.getLineId());
                }
                if (saleInvoiceCommonServiceCharge.getOptionId() != null) {
                    existingSaleInvoiceCommonServiceCharge.setOptionId(saleInvoiceCommonServiceCharge.getOptionId());
                }
                if (saleInvoiceCommonServiceCharge.getMainId() != null) {
                    existingSaleInvoiceCommonServiceCharge.setMainId(saleInvoiceCommonServiceCharge.getMainId());
                }
                if (saleInvoiceCommonServiceCharge.getCode() != null) {
                    existingSaleInvoiceCommonServiceCharge.setCode(saleInvoiceCommonServiceCharge.getCode());
                }
                if (saleInvoiceCommonServiceCharge.getName() != null) {
                    existingSaleInvoiceCommonServiceCharge.setName(saleInvoiceCommonServiceCharge.getName());
                }
                if (saleInvoiceCommonServiceCharge.getDescription() != null) {
                    existingSaleInvoiceCommonServiceCharge.setDescription(saleInvoiceCommonServiceCharge.getDescription());
                }
                if (saleInvoiceCommonServiceCharge.getValue() != null) {
                    existingSaleInvoiceCommonServiceCharge.setValue(saleInvoiceCommonServiceCharge.getValue());
                }
                if (saleInvoiceCommonServiceCharge.getDiscount() != null) {
                    existingSaleInvoiceCommonServiceCharge.setDiscount(saleInvoiceCommonServiceCharge.getDiscount());
                }
                if (saleInvoiceCommonServiceCharge.getServicePrice() != null) {
                    existingSaleInvoiceCommonServiceCharge.setServicePrice(saleInvoiceCommonServiceCharge.getServicePrice());
                }

                return existingSaleInvoiceCommonServiceCharge;
            })
            .map(saleInvoiceCommonServiceChargeRepository::save);
    }

    /**
     * Get one saleInvoiceCommonServiceCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleInvoiceCommonServiceCharge> findOne(Integer id) {
        LOG.debug("Request to get SaleInvoiceCommonServiceCharge : {}", id);
        return saleInvoiceCommonServiceChargeRepository.findById(id);
    }

    /**
     * Delete the saleInvoiceCommonServiceCharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete SaleInvoiceCommonServiceCharge : {}", id);
        saleInvoiceCommonServiceChargeRepository.deleteById(id);
    }
}
