package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsinvoicelines;
import com.heavenscode.rac.repository.AutojobsinvoicelinesRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsinvoicelines}.
 */
@Service
@Transactional
public class AutojobsinvoicelinesService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinesService.class);

    private final AutojobsinvoicelinesRepository autojobsinvoicelinesRepository;
    private final JdbcTemplate jdbcTemplate;

    public AutojobsinvoicelinesService(AutojobsinvoicelinesRepository autojobsinvoicelinesRepository, JdbcTemplate jdbcTemplate) {
        this.autojobsinvoicelinesRepository = autojobsinvoicelinesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }/*
public int insertautojobInvoiceLine() {
    String sql =
        "INSERT INTO AutoJobsInvoiceLines " +
        "(InvocieID, LineID, ItemID, ItemCode, ItemName, Description, UnitOfMeasurement, Quantity, ItemCost, ItemPrice, Discount, Tax, SellingPrice, LineTotal, LMU, LMD, NBT, VAT) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    return jdbcTemplate.update(
        sql,
        236756,                                 // InvocieID
        1,                                      // LineID
        101,                                    // ItemID
        "ITEM-001",                             // ItemCode
        "Brake Pad",                            // ItemName
        "Front brake pad set",                  // Description
        "PCS",                                  // UnitOfMeasurement
        4.0,                                    // Quantity
        15.00,                                  // ItemCost
        25.00,                                  // ItemPrice
        2.5,                                    // Discount
        3.0,                                    // Tax
        22.5,                                   // SellingPrice
        90.0,                                   // LineTotal
        1,                                      // LMU
       "2024-05-13T14:22:00Z" , // LMD as Timestamp
        true,                                   // NBT
        false                                   // VAT
    );
}
*/

    public int insertautojobInvoiceLine(Autojobsinvoicelines autojobsinvoicelines) {
        // Define SQL for inserting the batch
        String sql =
            "INSERT INTO AutoJobsInvoiceLines " +
            "(LineID, InvocieID, ItemID, ItemCode, ItemName, Description, UnitOfMeasurement, Quantity, ItemCost, ItemPrice, Discount, Tax, SellingPrice, LineTotal, LMU, LMD, NBT, VAT) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return jdbcTemplate.update(
            sql,
            autojobsinvoicelines.getLineid(),
            autojobsinvoicelines.getInvocieid(),
            autojobsinvoicelines.getItemid(),
            autojobsinvoicelines.getItemcode(),
            autojobsinvoicelines.getItemname(),
            autojobsinvoicelines.getDescription(),
            autojobsinvoicelines.getUnitofmeasurement(),
            autojobsinvoicelines.getQuantity(),
            autojobsinvoicelines.getItemcost(),
            autojobsinvoicelines.getItemprice(),
            autojobsinvoicelines.getDiscount(),
            autojobsinvoicelines.getTax(),
            autojobsinvoicelines.getSellingprice(),
            autojobsinvoicelines.getLinetotal(),
            autojobsinvoicelines.getLmu(),
            autojobsinvoicelines.getLmd(),
            autojobsinvoicelines.getNbt(),
            autojobsinvoicelines.getVat()
        );
    }

    public List<Autojobsinvoicelines> fetchJobInvoiceLines(int invocieID) {
        String sql = "SELECT * FROM AutoJobsInvoiceLines WHERE InvocieID = ?";
        return jdbcTemplate.query(sql, new Object[] { invocieID }, (rs, rowNum) -> {
            Autojobsinvoicelines line = new Autojobsinvoicelines();
            line.setInvocieid(rs.getInt("InvocieID"));
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

    /*
     *
     * Save a autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoicelines save(Autojobsinvoicelines autojobsinvoicelines) {
        LOG.debug("Request to save Autojobsinvoicelines : {}", autojobsinvoicelines);
        return autojobsinvoicelinesRepository.save(autojobsinvoicelines);
    }

    /**
     * Update a autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoicelines update(Autojobsinvoicelines autojobsinvoicelines) {
        LOG.debug("Request to update Autojobsinvoicelines : {}", autojobsinvoicelines);
        return autojobsinvoicelinesRepository.save(autojobsinvoicelines);
    }

    /**
     * Partially update a autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsinvoicelines> partialUpdate(Autojobsinvoicelines autojobsinvoicelines) {
        LOG.debug("Request to partially update Autojobsinvoicelines : {}", autojobsinvoicelines);

        return autojobsinvoicelinesRepository
            .findById(autojobsinvoicelines.getInvocieid())
            .map(existingAutojobsinvoicelines -> {
                if (autojobsinvoicelines.getLineid() != null) {
                    existingAutojobsinvoicelines.setLineid(autojobsinvoicelines.getLineid());
                }
                if (autojobsinvoicelines.getItemid() != null) {
                    existingAutojobsinvoicelines.setItemid(autojobsinvoicelines.getItemid());
                }
                if (autojobsinvoicelines.getItemcode() != null) {
                    existingAutojobsinvoicelines.setItemcode(autojobsinvoicelines.getItemcode());
                }
                if (autojobsinvoicelines.getItemname() != null) {
                    existingAutojobsinvoicelines.setItemname(autojobsinvoicelines.getItemname());
                }
                if (autojobsinvoicelines.getDescription() != null) {
                    existingAutojobsinvoicelines.setDescription(autojobsinvoicelines.getDescription());
                }
                if (autojobsinvoicelines.getUnitofmeasurement() != null) {
                    existingAutojobsinvoicelines.setUnitofmeasurement(autojobsinvoicelines.getUnitofmeasurement());
                }
                if (autojobsinvoicelines.getQuantity() != null) {
                    existingAutojobsinvoicelines.setQuantity(autojobsinvoicelines.getQuantity());
                }
                if (autojobsinvoicelines.getItemcost() != null) {
                    existingAutojobsinvoicelines.setItemcost(autojobsinvoicelines.getItemcost());
                }
                if (autojobsinvoicelines.getItemprice() != null) {
                    existingAutojobsinvoicelines.setItemprice(autojobsinvoicelines.getItemprice());
                }
                if (autojobsinvoicelines.getDiscount() != null) {
                    existingAutojobsinvoicelines.setDiscount(autojobsinvoicelines.getDiscount());
                }
                if (autojobsinvoicelines.getTax() != null) {
                    existingAutojobsinvoicelines.setTax(autojobsinvoicelines.getTax());
                }
                if (autojobsinvoicelines.getSellingprice() != null) {
                    existingAutojobsinvoicelines.setSellingprice(autojobsinvoicelines.getSellingprice());
                }
                if (autojobsinvoicelines.getLinetotal() != null) {
                    existingAutojobsinvoicelines.setLinetotal(autojobsinvoicelines.getLinetotal());
                }
                if (autojobsinvoicelines.getLmu() != null) {
                    existingAutojobsinvoicelines.setLmu(autojobsinvoicelines.getLmu());
                }
                if (autojobsinvoicelines.getLmd() != null) {
                    existingAutojobsinvoicelines.setLmd(autojobsinvoicelines.getLmd());
                }
                if (autojobsinvoicelines.getNbt() != null) {
                    existingAutojobsinvoicelines.setNbt(autojobsinvoicelines.getNbt());
                }
                if (autojobsinvoicelines.getVat() != null) {
                    existingAutojobsinvoicelines.setVat(autojobsinvoicelines.getVat());
                }

                return existingAutojobsinvoicelines;
            })
            .map(autojobsinvoicelinesRepository::save);
    }

    /**
     * Get one autojobsinvoicelines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsinvoicelines> findOne(Integer id) {
        LOG.debug("Request to get Autojobsinvoicelines : {}", id);
        return autojobsinvoicelinesRepository.findById(id);
    }

    /**
     * Delete the autojobsinvoicelines by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Autojobsinvoicelines : {}", id);
        autojobsinvoicelinesRepository.deleteById(id);
    }
}
