package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SalesInvoiceLinesDummy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesInvoiceLinesDummy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesInvoiceLinesDummyRepository
    extends JpaRepository<SalesInvoiceLinesDummy, Long>, JpaSpecificationExecutor<SalesInvoiceLinesDummy> {}
