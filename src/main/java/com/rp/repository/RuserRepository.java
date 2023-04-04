package com.rp.repository;

import com.rp.domain.Ruser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ruser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuserRepository extends JpaRepository<Ruser, Long> {}
