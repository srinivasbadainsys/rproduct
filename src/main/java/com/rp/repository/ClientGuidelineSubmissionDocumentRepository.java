package com.rp.repository;

import com.rp.domain.ClientGuidelineSubmissionDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClientGuidelineSubmissionDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientGuidelineSubmissionDocumentRepository extends JpaRepository<ClientGuidelineSubmissionDocument, Long> {}
