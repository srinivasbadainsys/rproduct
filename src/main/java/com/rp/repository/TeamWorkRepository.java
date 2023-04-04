package com.rp.repository;

import com.rp.domain.TeamWork;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeamWork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamWorkRepository extends JpaRepository<TeamWork, Long> {}
