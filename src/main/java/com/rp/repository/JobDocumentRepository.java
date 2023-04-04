package com.rp.repository;

import com.rp.domain.JobDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobDocumentRepository extends JpaRepository<JobDocument, Long> {}
