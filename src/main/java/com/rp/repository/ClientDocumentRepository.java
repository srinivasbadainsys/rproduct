package com.rp.repository;

import com.rp.domain.ClientDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClientDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientDocumentRepository extends JpaRepository<ClientDocument, Long> {}
