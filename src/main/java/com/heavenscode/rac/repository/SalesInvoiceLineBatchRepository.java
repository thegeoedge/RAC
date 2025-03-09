package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SalesInvoiceLineBatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesInvoiceLineBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesInvoiceLineBatchRepository
    extends JpaRepository<SalesInvoiceLineBatch, Long>, JpaSpecificationExecutor<SalesInvoiceLineBatch> {}
