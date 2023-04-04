package com.rp.service;

import com.rp.domain.Ruser;
import com.rp.repository.RuserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ruser}.
 */
@Service
@Transactional
public class RuserService {

    private final Logger log = LoggerFactory.getLogger(RuserService.class);

    private final RuserRepository ruserRepository;

    public RuserService(RuserRepository ruserRepository) {
        this.ruserRepository = ruserRepository;
    }

    /**
     * Save a ruser.
     *
     * @param ruser the entity to save.
     * @return the persisted entity.
     */
    public Ruser save(Ruser ruser) {
        log.debug("Request to save Ruser : {}", ruser);
        return ruserRepository.save(ruser);
    }

    /**
     * Update a ruser.
     *
     * @param ruser the entity to save.
     * @return the persisted entity.
     */
    public Ruser update(Ruser ruser) {
        log.debug("Request to update Ruser : {}", ruser);
        return ruserRepository.save(ruser);
    }

    /**
     * Partially update a ruser.
     *
     * @param ruser the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ruser> partialUpdate(Ruser ruser) {
        log.debug("Request to partially update Ruser : {}", ruser);

        return ruserRepository
            .findById(ruser.getId())
            .map(existingRuser -> {
                if (ruser.getLogin() != null) {
                    existingRuser.setLogin(ruser.getLogin());
                }
                if (ruser.getPassword() != null) {
                    existingRuser.setPassword(ruser.getPassword());
                }
                if (ruser.getFirstName() != null) {
                    existingRuser.setFirstName(ruser.getFirstName());
                }
                if (ruser.getLastName() != null) {
                    existingRuser.setLastName(ruser.getLastName());
                }
                if (ruser.getEmail() != null) {
                    existingRuser.setEmail(ruser.getEmail());
                }
                if (ruser.getActivated() != null) {
                    existingRuser.setActivated(ruser.getActivated());
                }
                if (ruser.getLangKey() != null) {
                    existingRuser.setLangKey(ruser.getLangKey());
                }
                if (ruser.getImageUrl() != null) {
                    existingRuser.setImageUrl(ruser.getImageUrl());
                }
                if (ruser.getActivationKey() != null) {
                    existingRuser.setActivationKey(ruser.getActivationKey());
                }
                if (ruser.getResetKey() != null) {
                    existingRuser.setResetKey(ruser.getResetKey());
                }
                if (ruser.getResetDate() != null) {
                    existingRuser.setResetDate(ruser.getResetDate());
                }

                return existingRuser;
            })
            .map(ruserRepository::save);
    }

    /**
     * Get all the rusers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ruser> findAll(Pageable pageable) {
        log.debug("Request to get all Rusers");
        return ruserRepository.findAll(pageable);
    }

    /**
     * Get one ruser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ruser> findOne(Long id) {
        log.debug("Request to get Ruser : {}", id);
        return ruserRepository.findById(id);
    }

    /**
     * Delete the ruser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ruser : {}", id);
        ruserRepository.deleteById(id);
    }
}
