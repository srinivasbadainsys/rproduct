package com.rp.repository;

import com.rp.domain.RUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RUserRepository extends JpaRepository<RUser, Long> {}
