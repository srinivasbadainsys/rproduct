package com.rp.repository;

import com.rp.domain.ClientNote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClientNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientNoteRepository extends JpaRepository<ClientNote, Long> {}
