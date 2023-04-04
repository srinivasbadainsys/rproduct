package com.rp.repository;

import com.rp.domain.JobWork;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobWork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobWorkRepository extends JpaRepository<JobWork, Long> {}
