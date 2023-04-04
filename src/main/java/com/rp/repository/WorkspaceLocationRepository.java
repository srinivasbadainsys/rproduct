package com.rp.repository;

import com.rp.domain.WorkspaceLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkspaceLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkspaceLocationRepository extends JpaRepository<WorkspaceLocation, Long> {}
