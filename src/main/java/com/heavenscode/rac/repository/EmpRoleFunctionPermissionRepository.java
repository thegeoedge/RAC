package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.EmpRoleFunctionPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpRoleFunctionPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpRoleFunctionPermissionRepository
    extends JpaRepository<EmpRoleFunctionPermission, Integer>, JpaSpecificationExecutor<EmpRoleFunctionPermission> {}
