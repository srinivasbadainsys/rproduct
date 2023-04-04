package com.rp.repository;

import com.rp.domain.JobBoardSharedTo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobBoardSharedTo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobBoardSharedToRepository extends JpaRepository<JobBoardSharedTo, Long> {}
