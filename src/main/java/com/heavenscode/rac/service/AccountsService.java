package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Accounts;
import com.heavenscode.rac.repository.AccountsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Accounts}.
 */
@Service
@Transactional
public class AccountsService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountsService.class);

    private final AccountsRepository accountsRepository;
    private final JdbcTemplate jdbcTemplate;

    public AccountsService(AccountsRepository accountsRepository, JdbcTemplate jdbcTemplate) {
        this.accountsRepository = accountsRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    public Accounts save(Accounts accounts) {
        LOG.debug("Request to save Accounts : {}", accounts);
        return accountsRepository.save(accounts);
    }

    public List<Accounts> fetchAccountsByName(String name) {
        String sql = "SELECT * FROM Accounts WHERE Name = ?";
        return jdbcTemplate.query(sql, new Object[] { name }, (rs, rowNum) -> {
            Accounts account = new Accounts();
            account.setId(rs.getLong("ID"));
            account.setCode(rs.getString("Code"));
            account.setDate(rs.getTimestamp("Date") != null ? rs.getTimestamp("Date").toInstant() : null);
            account.setName(rs.getString("Name"));
            account.setDescription(rs.getString("Description"));
            account.setType(rs.getInt("Type"));
            account.setParent(rs.getInt("Parent"));
            account.setBalance(rs.getFloat("Balance"));
            account.setLmu(rs.getInt("LMU"));
            account.setLmd(rs.getTimestamp("LMD") != null ? rs.getTimestamp("LMD").toInstant() : null);
            account.setHasbatches(rs.getBoolean("HasBatches"));
            account.setAccountvalue(rs.getFloat("AccountValue"));
            account.setAccountlevel(rs.getInt("AccountLevel"));
            account.setAccountsnumberingsystem(rs.getLong("AccountsNumberingSystem"));
            account.setSubparentid(rs.getInt("SubParentID"));
            account.setCanedit(rs.getBoolean("CanEdit"));
            account.setAmount(rs.getDouble("Amount"));
            account.setCreditamount(rs.getFloat("CreditAmount"));
            account.setDebitamount(rs.getDouble("DebitAmount"));
            account.setDebitorcredit(rs.getString("DebitOrCredit"));
            account.setReporttype(rs.getInt("ReportType"));
            return account;
        });
    }

    /**
     * Update a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    public Accounts update(Accounts accounts) {
        LOG.debug("Request to update Accounts : {}", accounts);
        return accountsRepository.save(accounts);
    }

    /**
     * Partially update a accounts.
     *
     * @param accounts the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Accounts> partialUpdate(Accounts accounts) {
        LOG.debug("Request to partially update Accounts : {}", accounts);

        return accountsRepository
            .findById(accounts.getId())
            .map(existingAccounts -> {
                if (accounts.getCode() != null) {
                    existingAccounts.setCode(accounts.getCode());
                }
                if (accounts.getDate() != null) {
                    existingAccounts.setDate(accounts.getDate());
                }
                if (accounts.getName() != null) {
                    existingAccounts.setName(accounts.getName());
                }
                if (accounts.getDescription() != null) {
                    existingAccounts.setDescription(accounts.getDescription());
                }
                if (accounts.getType() != null) {
                    existingAccounts.setType(accounts.getType());
                }
                if (accounts.getParent() != null) {
                    existingAccounts.setParent(accounts.getParent());
                }
                if (accounts.getBalance() != null) {
                    existingAccounts.setBalance(accounts.getBalance());
                }
                if (accounts.getLmu() != null) {
                    existingAccounts.setLmu(accounts.getLmu());
                }
                if (accounts.getLmd() != null) {
                    existingAccounts.setLmd(accounts.getLmd());
                }
                if (accounts.getHasbatches() != null) {
                    existingAccounts.setHasbatches(accounts.getHasbatches());
                }
                if (accounts.getAccountvalue() != null) {
                    existingAccounts.setAccountvalue(accounts.getAccountvalue());
                }
                if (accounts.getAccountlevel() != null) {
                    existingAccounts.setAccountlevel(accounts.getAccountlevel());
                }
                if (accounts.getAccountsnumberingsystem() != null) {
                    existingAccounts.setAccountsnumberingsystem(accounts.getAccountsnumberingsystem());
                }
                if (accounts.getSubparentid() != null) {
                    existingAccounts.setSubparentid(accounts.getSubparentid());
                }
                if (accounts.getCanedit() != null) {
                    existingAccounts.setCanedit(accounts.getCanedit());
                }
                if (accounts.getAmount() != null) {
                    existingAccounts.setAmount(accounts.getAmount());
                }
                if (accounts.getCreditamount() != null) {
                    existingAccounts.setCreditamount(accounts.getCreditamount());
                }
                if (accounts.getDebitamount() != null) {
                    existingAccounts.setDebitamount(accounts.getDebitamount());
                }
                if (accounts.getDebitorcredit() != null) {
                    existingAccounts.setDebitorcredit(accounts.getDebitorcredit());
                }
                if (accounts.getReporttype() != null) {
                    existingAccounts.setReporttype(accounts.getReporttype());
                }

                return existingAccounts;
            })
            .map(accountsRepository::save);
    }

    /**
     * Get one accounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Accounts> findOne(Long id) {
        LOG.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id);
    }

    /**
     * Delete the accounts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
    }
}
