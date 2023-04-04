package com.rp.repository;

import com.rp.domain.JobBoardPost;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobBoardPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobBoardPostRepository extends JpaRepository<JobBoardPost, Long> {}
