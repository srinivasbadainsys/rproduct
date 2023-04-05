package com.rp.service;

import com.rp.domain.CandidateRelocationPreference;
import com.rp.repository.CandidateRelocationPreferenceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CandidateRelocationPreference}.
 */
@Service
@Transactional
public class CandidateRelocationPreferenceService {

    private final Logger log = LoggerFactory.getLogger(CandidateRelocationPreferenceService.class);

    private final CandidateRelocationPreferenceRepository candidateRelocationPreferenceRepository;

    public CandidateRelocationPreferenceService(CandidateRelocationPreferenceRepository candidateRelocationPreferenceRepository) {
        this.candidateRelocationPreferenceRepository = candidateRelocationPreferenceRepository;
    }

    /**
     * Save a candidateRelocationPreference.
     *
     * @param candidateRelocationPreference the entity to save.
     * @return the persisted entity.
     */
    public CandidateRelocationPreference save(CandidateRelocationPreference candidateRelocationPreference) {
        log.debug("Request to save CandidateRelocationPreference : {}", candidateRelocationPreference);
        return candidateRelocationPreferenceRepository.save(candidateRelocationPreference);
    }

    /**
     * Update a candidateRelocationPreference.
     *
     * @param candidateRelocationPreference the entity to save.
     * @return the persisted entity.
     */
    public CandidateRelocationPreference update(CandidateRelocationPreference candidateRelocationPreference) {
        log.debug("Request to update CandidateRelocationPreference : {}", candidateRelocationPreference);
        return candidateRelocationPreferenceRepository.save(candidateRelocationPreference);
    }

    /**
     * Partially update a candidateRelocationPreference.
     *
     * @param candidateRelocationPreference the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CandidateRelocationPreference> partialUpdate(CandidateRelocationPreference candidateRelocationPreference) {
        log.debug("Request to partially update CandidateRelocationPreference : {}", candidateRelocationPreference);

        return candidateRelocationPreferenceRepository
            .findById(candidateRelocationPreference.getId())
            .map(existingCandidateRelocationPreference -> {
                if (candidateRelocationPreference.getCandidateId() != null) {
                    existingCandidateRelocationPreference.setCandidateId(candidateRelocationPreference.getCandidateId());
                }
                if (candidateRelocationPreference.getCity() != null) {
                    existingCandidateRelocationPreference.setCity(candidateRelocationPreference.getCity());
                }
                if (candidateRelocationPreference.getState() != null) {
                    existingCandidateRelocationPreference.setState(candidateRelocationPreference.getState());
                }
                if (candidateRelocationPreference.getStateCode() != null) {
                    existingCandidateRelocationPreference.setStateCode(candidateRelocationPreference.getStateCode());
                }
                if (candidateRelocationPreference.getCounty() != null) {
                    existingCandidateRelocationPreference.setCounty(candidateRelocationPreference.getCounty());
                }
                if (candidateRelocationPreference.getCountry() != null) {
                    existingCandidateRelocationPreference.setCountry(candidateRelocationPreference.getCountry());
                }
                if (candidateRelocationPreference.getCountryCode() != null) {
                    existingCandidateRelocationPreference.setCountryCode(candidateRelocationPreference.getCountryCode());
                }
                if (candidateRelocationPreference.getZipCode() != null) {
                    existingCandidateRelocationPreference.setZipCode(candidateRelocationPreference.getZipCode());
                }

                return existingCandidateRelocationPreference;
            })
            .map(candidateRelocationPreferenceRepository::save);
    }

    /**
     * Get all the candidateRelocationPreferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CandidateRelocationPreference> findAll(Pageable pageable) {
        log.debug("Request to get all CandidateRelocationPreferences");
        return candidateRelocationPreferenceRepository.findAll(pageable);
    }

    /**
     * Get one candidateRelocationPreference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CandidateRelocationPreference> findOne(Long id) {
        log.debug("Request to get CandidateRelocationPreference : {}", id);
        return candidateRelocationPreferenceRepository.findById(id);
    }

    /**
     * Delete the candidateRelocationPreference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CandidateRelocationPreference : {}", id);
        candidateRelocationPreferenceRepository.deleteById(id);
    }
}
