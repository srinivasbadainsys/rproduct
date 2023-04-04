package com.rp.repository;

import com.rp.domain.ClientAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClientAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {}
