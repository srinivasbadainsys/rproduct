package com.rp.repository;

import com.rp.domain.JobLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobLocationRepository extends JpaRepository<JobLocation, Long> {}
