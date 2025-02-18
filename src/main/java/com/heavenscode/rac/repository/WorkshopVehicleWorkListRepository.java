package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.WorkshopVehicleWorkList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkshopVehicleWorkList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkshopVehicleWorkListRepository extends JpaRepository<WorkshopVehicleWorkList, Long> {}
