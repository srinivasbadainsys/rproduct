package com.rp.repository;

import com.rp.domain.CatalogueValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CatalogueValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogueValueRepository extends JpaRepository<CatalogueValue, Long> {}
