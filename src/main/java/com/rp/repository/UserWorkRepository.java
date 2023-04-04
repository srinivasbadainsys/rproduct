package com.rp.repository;

import com.rp.domain.UserWork;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserWork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserWorkRepository extends JpaRepository<UserWork, Long> {}
