package com.rp.repository;

import com.rp.domain.BusinessUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BusinessUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {}
