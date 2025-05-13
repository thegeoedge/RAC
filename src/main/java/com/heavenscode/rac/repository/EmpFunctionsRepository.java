package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.EmpFunctions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpFunctions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpFunctionsRepository extends JpaRepository<EmpFunctions, Integer>, JpaSpecificationExecutor<EmpFunctions> {}
