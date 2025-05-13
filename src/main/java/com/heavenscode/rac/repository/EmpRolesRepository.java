package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.EmpRoles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpRoles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpRolesRepository extends JpaRepository<EmpRoles, Integer>, JpaSpecificationExecutor<EmpRoles> {}
