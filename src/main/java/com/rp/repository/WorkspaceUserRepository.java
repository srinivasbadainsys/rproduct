package com.rp.repository;

import com.rp.domain.WorkspaceUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkspaceUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {}
