package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SystemSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SystemSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Long> {}
