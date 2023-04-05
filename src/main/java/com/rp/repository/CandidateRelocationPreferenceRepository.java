package com.rp.repository;

import com.rp.domain.CandidateRelocationPreference;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CandidateRelocationPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRelocationPreferenceRepository extends JpaRepository<CandidateRelocationPreference, Long> {}
