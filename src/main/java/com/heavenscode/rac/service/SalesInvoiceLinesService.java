package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SalesInvoiceLines;
import com.heavenscode.rac.repository.SalesInvoiceLinesRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SalesInvoiceLines}.
 */
@Service
@Transactional
public class SalesInvoiceLinesService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLinesService.class);

    private final SalesInvoiceLinesRepository salesInvoiceLinesRepository;
    private final JdbcTemplate jdbcTemplate;

    public SalesInvoiceLinesService(SalesInvoiceLinesRepository salesInvoiceLinesRepository, JdbcTemplate jdbcTemplate) {
        this.salesInvoiceLinesRepository = salesInvoiceLinesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a salesInvoiceLines.
     *
     * @param salesInvoiceLines the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceLines save(SalesInvoiceLines salesInvoiceLines) {
        LOG.debug("Request to save SalesInvoiceLines : {}", salesInvoiceLines);
        return salesInvoiceLinesRepository.save(salesInvoiceLines);
    }

    public List<SalesInvoiceLines> fetchSalesInvoiceLines(int invocieID) {
        String sql = "SELECT * FROM SalesInvoiceLines WHERE InvocieID = ?";
        return jdbcTemplate.query(sql, new Object[] { invocieID }, (rs, rowNum) -> {
            SalesInvoiceLines line = new SalesInvoiceLines();
            line.setInvoiceid(rs.getInt("InvocieID"));
            line.setLineid(rs.getInt("LineID"));
            line.setItemid(rs.getInt("ItemID"));
            line.setItemcode(rs.getString("ItemCode"));
            line.setItemname(rs.getString("ItemName"));
            line.setDescription(rs.getString("Description"));
            line.setUnitofmeasurement(rs.getString("UnitOfMeasurement"));
            line.setQuantity(rs.getFloat("Quantity"));
            line.setItemcost(rs.getFloat("ItemCost"));
            line.setItemprice(rs.getFloat("ItemPrice"));
            line.setDiscount(rs.getFloat("Discount"));
            line.setTax(rs.getFloat("Tax"));
            line.setSellingprice(rs.getFloat("SellingPrice"));
            line.setLinetotal(rs.getFloat("LineTotal"));
            line.setLmu(rs.getInt("LMU"));

            line.setNbt(rs.getBoolean("NBT"));
            line.setVat(rs.getBoolean("VAT"));
            return line;
        });
    }

    /**
     * Update a salesInvoiceLines.
     *
     * @param salesInvoiceLines the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceLines update(SalesInvoiceLines salesInvoiceLines) {
        LOG.debug("Request to update SalesInvoiceLines : {}", salesInvoiceLines);
        return salesInvoiceLinesRepository.save(salesInvoiceLines);
    }

    /**
     * Partially update a salesInvoiceLines.
     *
     * @param salesInvoiceLines the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesInvoiceLines> partialUpdate(SalesInvoiceLines salesInvoiceLines) {
        LOG.debug("Request to partially update SalesInvoiceLines : {}", salesInvoiceLines);

        return salesInvoiceLinesRepository
            .findById(salesInvoiceLines.getInvoiceid())
            .map(existingSalesInvoiceLines -> {
                if (salesInvoiceLines.getLineid() != null) {
                    existingSalesInvoiceLines.setLineid(salesInvoiceLines.getLineid());
                }
                if (salesInvoiceLines.getItemid() != null) {
                    existingSalesInvoiceLines.setItemid(salesInvoiceLines.getItemid());
                }
                if (salesInvoiceLines.getItemcode() != null) {
                    existingSalesInvoiceLines.setItemcode(salesInvoiceLines.getItemcode());
                }
                if (salesInvoiceLines.getItemname() != null) {
                    existingSalesInvoiceLines.setItemname(salesInvoiceLines.getItemname());
                }
                if (salesInvoiceLines.getDescription() != null) {
                    existingSalesInvoiceLines.setDescription(salesInvoiceLines.getDescription());
                }
                if (salesInvoiceLines.getUnitofmeasurement() != null) {
                    existingSalesInvoiceLines.setUnitofmeasurement(salesInvoiceLines.getUnitofmeasurement());
                }
                if (salesInvoiceLines.getQuantity() != null) {
                    existingSalesInvoiceLines.setQuantity(salesInvoiceLines.getQuantity());
                }
                if (salesInvoiceLines.getItemcost() != null) {
                    existingSalesInvoiceLines.setItemcost(salesInvoiceLines.getItemcost());
                }
                if (salesInvoiceLines.getItemprice() != null) {
                    existingSalesInvoiceLines.setItemprice(salesInvoiceLines.getItemprice());
                }
                if (salesInvoiceLines.getDiscount() != null) {
                    existingSalesInvoiceLines.setDiscount(salesInvoiceLines.getDiscount());
                }
                if (salesInvoiceLines.getTax() != null) {
                    existingSalesInvoiceLines.setTax(salesInvoiceLines.getTax());
                }
                if (salesInvoiceLines.getSellingprice() != null) {
                    existingSalesInvoiceLines.setSellingprice(salesInvoiceLines.getSellingprice());
                }
                if (salesInvoiceLines.getLinetotal() != null) {
                    existingSalesInvoiceLines.setLinetotal(salesInvoiceLines.getLinetotal());
                }
                if (salesInvoiceLines.getLmu() != null) {
                    existingSalesInvoiceLines.setLmu(salesInvoiceLines.getLmu());
                }
                if (salesInvoiceLines.getLmd() != null) {
                    existingSalesInvoiceLines.setLmd(salesInvoiceLines.getLmd());
                }
                if (salesInvoiceLines.getNbt() != null) {
                    existingSalesInvoiceLines.setNbt(salesInvoiceLines.getNbt());
                }
                if (salesInvoiceLines.getVat() != null) {
                    existingSalesInvoiceLines.setVat(salesInvoiceLines.getVat());
                }

                return existingSalesInvoiceLines;
            })
            .map(salesInvoiceLinesRepository::save);
    }

    /**
     * Get one salesInvoiceLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesInvoiceLines> findOne(Integer id) {
        LOG.debug("Request to get SalesInvoiceLines : {}", id);
        return salesInvoiceLinesRepository.findById(id);
    }

    /**
     * Delete the salesInvoiceLines by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete SalesInvoiceLines : {}", id);
        salesInvoiceLinesRepository.deleteById(id);
    }
}
