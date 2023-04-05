package com.rp.service;

import com.rp.domain.RUser;
import com.rp.repository.RUserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RUser}.
 */
@Service
@Transactional
public class RUserService {

    private final Logger log = LoggerFactory.getLogger(RUserService.class);

    private final RUserRepository rUserRepository;

    public RUserService(RUserRepository rUserRepository) {
        this.rUserRepository = rUserRepository;
    }

    /**
     * Save a rUser.
     *
     * @param rUser the entity to save.
     * @return the persisted entity.
     */
    public RUser save(RUser rUser) {
        log.debug("Request to save RUser : {}", rUser);
        return rUserRepository.save(rUser);
    }

    /**
     * Update a rUser.
     *
     * @param rUser the entity to save.
     * @return the persisted entity.
     */
    public RUser update(RUser rUser) {
        log.debug("Request to update RUser : {}", rUser);
        // no save call needed as we have no fields that can be updated
        return rUser;
    }

    /**
     * Partially update a rUser.
     *
     * @param rUser the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RUser> partialUpdate(RUser rUser) {
        log.debug("Request to partially update RUser : {}", rUser);

        return rUserRepository
            .findById(rUser.getId())
            .map(existingRUser -> {
                return existingRUser;
            })// .map(rUserRepository::save)
        ;
    }

    /**
     * Get all the rUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RUser> findAll(Pageable pageable) {
        log.debug("Request to get all RUsers");
        return rUserRepository.findAll(pageable);
    }

    /**
     * Get one rUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RUser> findOne(Long id) {
        log.debug("Request to get RUser : {}", id);
        return rUserRepository.findById(id);
    }

    /**
     * Delete the rUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RUser : {}", id);
        rUserRepository.deleteById(id);
    }
}
