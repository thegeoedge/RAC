package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SalesInvoiceLineBatch;
import com.heavenscode.rac.repository.SalesInvoiceLineBatchRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SalesInvoiceLineBatch}.
 */
@Service
@Transactional
public class SalesInvoiceLineBatchService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLineBatchService.class);

    private final SalesInvoiceLineBatchRepository salesInvoiceLineBatchRepository;

    private static final Logger logger = LoggerFactory.getLogger(SalesInvoiceLineBatchService.class);

    private final JdbcTemplate jdbcTemplate;

    public SalesInvoiceLineBatchService(SalesInvoiceLineBatchRepository salesInvoiceLineBatchRepository, JdbcTemplate jdbcTemplate) {
        this.salesInvoiceLineBatchRepository = salesInvoiceLineBatchRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertSalesInvoiceLineBatch(SalesInvoiceLineBatch batch) {
        // Define SQL for inserting the batch
        String sql =
            "INSERT INTO SalesInvoiceLineBatches " +
            "(ID,LineId, BatchLineId, ItemID, Code, BatchId, BatchCode, TxDate, " +
            "ManufactureDate, ExpiredDate, Qty, Cost, Price, Notes, " +
            "LMU, LMD, AddedById) " +
            "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
            sql, // The SQL query should be the first parameter
            batch.getId(), // ID
            batch.getLineId(), // LineId
            batch.getBatchLineId(), // BatchLineId
            batch.getItemId(), // ItemID
            batch.getCode(), // Code
            batch.getBatchId(), // BatchId
            batch.getBatchCode(), // BatchCode
            toTimestamp(batch.getTxDate()), // TxDate
            toTimestamp(batch.getManufactureDate()), // ManufactureDate
            toTimestamp(batch.getExpiredDate()), // ExpiredDate   // ExpiredDate
            batch.getQty(), // Qty
            batch.getCost(), // Cost
            batch.getPrice(), // Price
            batch.getNotes(), // Notes
            batch.getLmu(), // LMU
            toTimestamp(batch.getLmd()), // LMD
            batch.getAddedById() // AddedById
        );
    }

    private Timestamp toTimestamp(Instant instant) {
        return (instant != null) ? Timestamp.valueOf(LocalDateTime.ofInstant(instant, ZoneId.systemDefault())) : null;
    }

    /**
     * Save a salesInvoiceLineBatch.
     *
     * @param salesInvoiceLineBatch the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceLineBatch save(SalesInvoiceLineBatch salesInvoiceLineBatch) {
        LOG.debug("Request to save SalesInvoiceLineBatch : {}", salesInvoiceLineBatch);
        return salesInvoiceLineBatchRepository.save(salesInvoiceLineBatch);
    }

    /**
     * Update a salesInvoiceLineBatch.
     *
     * @param salesInvoiceLineBatch the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceLineBatch update(SalesInvoiceLineBatch salesInvoiceLineBatch) {
        LOG.debug("Request to update SalesInvoiceLineBatch : {}", salesInvoiceLineBatch);
        return salesInvoiceLineBatchRepository.save(salesInvoiceLineBatch);
    }

    /**
     * Partially update a salesInvoiceLineBatch.
     *
     * @param salesInvoiceLineBatch the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesInvoiceLineBatch> partialUpdate(SalesInvoiceLineBatch salesInvoiceLineBatch) {
        LOG.debug("Request to partially update SalesInvoiceLineBatch : {}", salesInvoiceLineBatch);

        return salesInvoiceLineBatchRepository
            .findById(salesInvoiceLineBatch.getId())
            .map(existingSalesInvoiceLineBatch -> {
                if (salesInvoiceLineBatch.getLineId() != null) {
                    existingSalesInvoiceLineBatch.setLineId(salesInvoiceLineBatch.getLineId());
                }
                if (salesInvoiceLineBatch.getBatchLineId() != null) {
                    existingSalesInvoiceLineBatch.setBatchLineId(salesInvoiceLineBatch.getBatchLineId());
                }
                if (salesInvoiceLineBatch.getItemId() != null) {
                    existingSalesInvoiceLineBatch.setItemId(salesInvoiceLineBatch.getItemId());
                }
                if (salesInvoiceLineBatch.getCode() != null) {
                    existingSalesInvoiceLineBatch.setCode(salesInvoiceLineBatch.getCode());
                }
                if (salesInvoiceLineBatch.getBatchId() != null) {
                    existingSalesInvoiceLineBatch.setBatchId(salesInvoiceLineBatch.getBatchId());
                }
                if (salesInvoiceLineBatch.getBatchCode() != null) {
                    existingSalesInvoiceLineBatch.setBatchCode(salesInvoiceLineBatch.getBatchCode());
                }
                if (salesInvoiceLineBatch.getTxDate() != null) {
                    existingSalesInvoiceLineBatch.setTxDate(salesInvoiceLineBatch.getTxDate());
                }
                if (salesInvoiceLineBatch.getManufactureDate() != null) {
                    existingSalesInvoiceLineBatch.setManufactureDate(salesInvoiceLineBatch.getManufactureDate());
                }
                if (salesInvoiceLineBatch.getExpiredDate() != null) {
                    existingSalesInvoiceLineBatch.setExpiredDate(salesInvoiceLineBatch.getExpiredDate());
                }
                if (salesInvoiceLineBatch.getQty() != null) {
                    existingSalesInvoiceLineBatch.setQty(salesInvoiceLineBatch.getQty());
                }
                if (salesInvoiceLineBatch.getCost() != null) {
                    existingSalesInvoiceLineBatch.setCost(salesInvoiceLineBatch.getCost());
                }
                if (salesInvoiceLineBatch.getPrice() != null) {
                    existingSalesInvoiceLineBatch.setPrice(salesInvoiceLineBatch.getPrice());
                }
                if (salesInvoiceLineBatch.getNotes() != null) {
                    existingSalesInvoiceLineBatch.setNotes(salesInvoiceLineBatch.getNotes());
                }
                if (salesInvoiceLineBatch.getLmu() != null) {
                    existingSalesInvoiceLineBatch.setLmu(salesInvoiceLineBatch.getLmu());
                }
                if (salesInvoiceLineBatch.getLmd() != null) {
                    existingSalesInvoiceLineBatch.setLmd(salesInvoiceLineBatch.getLmd());
                }
                if (salesInvoiceLineBatch.getNbt() != null) {
                    existingSalesInvoiceLineBatch.setNbt(salesInvoiceLineBatch.getNbt());
                }
                if (salesInvoiceLineBatch.getVat() != null) {
                    existingSalesInvoiceLineBatch.setVat(salesInvoiceLineBatch.getVat());
                }
                if (salesInvoiceLineBatch.getAddedById() != null) {
                    existingSalesInvoiceLineBatch.setAddedById(salesInvoiceLineBatch.getAddedById());
                }

                return existingSalesInvoiceLineBatch;
            })
            .map(salesInvoiceLineBatchRepository::save);
    }

    /**
     * Get one salesInvoiceLineBatch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesInvoiceLineBatch> findOne(Long id) {
        LOG.debug("Request to get SalesInvoiceLineBatch : {}", id);
        return salesInvoiceLineBatchRepository.findById(id);
    }

    /**
     * Delete the salesInvoiceLineBatch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SalesInvoiceLineBatch : {}", id);
        salesInvoiceLineBatchRepository.deleteById(id);
    }
}
