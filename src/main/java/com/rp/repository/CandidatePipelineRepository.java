package com.rp.repository;

import com.rp.domain.CandidatePipeline;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CandidatePipeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatePipelineRepository extends JpaRepository<CandidatePipeline, Long> {}
