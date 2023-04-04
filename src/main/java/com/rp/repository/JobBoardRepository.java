package com.rp.repository;

import com.rp.domain.JobBoard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobBoardRepository extends JpaRepository<JobBoard, Long> {}
