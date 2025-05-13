package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.AutoCareVehicle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AutoCareVehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoCareVehicleRepository extends JpaRepository<AutoCareVehicle, Long>, JpaSpecificationExecutor<AutoCareVehicle> {}
