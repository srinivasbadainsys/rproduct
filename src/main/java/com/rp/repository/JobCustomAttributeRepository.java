package com.rp.repository;

import com.rp.domain.JobCustomAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobCustomAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobCustomAttributeRepository extends JpaRepository<JobCustomAttribute, Long> {}
