package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Billingserviceoptionvalues;
import com.heavenscode.rac.repository.BillingserviceoptionvaluesRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Billingserviceoptionvalues}.
 */
@Service
@Transactional
public class BillingserviceoptionvaluesService {

    private static final Logger LOG = LoggerFactory.getLogger(BillingserviceoptionvaluesService.class);

    private final BillingserviceoptionvaluesRepository billingserviceoptionvaluesRepository;
    private final JdbcTemplate jdbcTemplate;

    public BillingserviceoptionvaluesService(
        BillingserviceoptionvaluesRepository billingserviceoptionvaluesRepository,
        JdbcTemplate jdbcTemplate
    ) {
        this.billingserviceoptionvaluesRepository = billingserviceoptionvaluesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a billingserviceoptionvalues.
     *
     * @param billingserviceoptionvalues the entity to save.
     * @return the persisted entity.
     */
    public Billingserviceoptionvalues save(Billingserviceoptionvalues billingserviceoptionvalues) {
        LOG.debug("Request to save Billingserviceoptionvalues : {}", billingserviceoptionvalues);
        return billingserviceoptionvaluesRepository.save(billingserviceoptionvalues);
    }

    public List<Billingserviceoptionvalues> fetchoptionvalues(int vehicletypeid) {
        String sql = "SELECT * FROM billingserviceoptionvalues WHERE vehicletypeid = ?";
        return jdbcTemplate.query(sql, new Object[] { vehicletypeid }, (rs, rowNum) -> {
            Billingserviceoptionvalues item = new Billingserviceoptionvalues();
            item.setVehicletypeid(rs.getInt("vehicletypeid"));
            item.setBillingserviceoptionid(rs.getInt("billingserviceoptionid"));
            item.setValue(rs.getFloat("value"));
            item.setLmd(rs.getTimestamp("lmd").toInstant());
            item.setLmu(rs.getInt("lmu"));
            return item;
        });
    }

    /**
     * Update a billingserviceoptionvalues.
     *
     * @param billingserviceoptionvalues the entity to save.
     * @return the persisted entity.
     */
    public Billingserviceoptionvalues update(Billingserviceoptionvalues billingserviceoptionvalues) {
        LOG.debug("Request to update Billingserviceoptionvalues : {}", billingserviceoptionvalues);
        return billingserviceoptionvaluesRepository.save(billingserviceoptionvalues);
    }

    /**
     * Partially update a billingserviceoptionvalues.
     *
     * @param billingserviceoptionvalues the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Billingserviceoptionvalues> partialUpdate(Billingserviceoptionvalues billingserviceoptionvalues) {
        LOG.debug("Request to partially update Billingserviceoptionvalues : {}", billingserviceoptionvalues);

        return billingserviceoptionvaluesRepository
            .findById(billingserviceoptionvalues.getVehicletypeid())
            .map(existingBillingserviceoptionvalues -> {
                if (billingserviceoptionvalues.getBillingserviceoptionid() != null) {
                    existingBillingserviceoptionvalues.setBillingserviceoptionid(billingserviceoptionvalues.getBillingserviceoptionid());
                }
                if (billingserviceoptionvalues.getValue() != null) {
                    existingBillingserviceoptionvalues.setValue(billingserviceoptionvalues.getValue());
                }
                if (billingserviceoptionvalues.getLmd() != null) {
                    existingBillingserviceoptionvalues.setLmd(billingserviceoptionvalues.getLmd());
                }
                if (billingserviceoptionvalues.getLmu() != null) {
                    existingBillingserviceoptionvalues.setLmu(billingserviceoptionvalues.getLmu());
                }

                return existingBillingserviceoptionvalues;
            })
            .map(billingserviceoptionvaluesRepository::save);
    }

    /**
     * Get one billingserviceoptionvalues by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Billingserviceoptionvalues> findOne(Integer id) {
        LOG.debug("Request to get Billingserviceoptionvalues : {}", id);
        return billingserviceoptionvaluesRepository.findById(id);
    }

    /**
     * Delete the billingserviceoptionvalues by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Billingserviceoptionvalues : {}", id);
        billingserviceoptionvaluesRepository.deleteById(id);
    }
}
