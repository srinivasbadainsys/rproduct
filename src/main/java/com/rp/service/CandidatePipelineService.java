package com.rp.service;

import com.rp.domain.CandidatePipeline;
import com.rp.repository.CandidatePipelineRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CandidatePipeline}.
 */
@Service
@Transactional
public class CandidatePipelineService {

    private final Logger log = LoggerFactory.getLogger(CandidatePipelineService.class);

    private final CandidatePipelineRepository candidatePipelineRepository;

    public CandidatePipelineService(CandidatePipelineRepository candidatePipelineRepository) {
        this.candidatePipelineRepository = candidatePipelineRepository;
    }

    /**
     * Save a candidatePipeline.
     *
     * @param candidatePipeline the entity to save.
     * @return the persisted entity.
     */
    public CandidatePipeline save(CandidatePipeline candidatePipeline) {
        log.debug("Request to save CandidatePipeline : {}", candidatePipeline);
        return candidatePipelineRepository.save(candidatePipeline);
    }

    /**
     * Update a candidatePipeline.
     *
     * @param candidatePipeline the entity to save.
     * @return the persisted entity.
     */
    public CandidatePipeline update(CandidatePipeline candidatePipeline) {
        log.debug("Request to update CandidatePipeline : {}", candidatePipeline);
        return candidatePipelineRepository.save(candidatePipeline);
    }

    /**
     * Partially update a candidatePipeline.
     *
     * @param candidatePipeline the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CandidatePipeline> partialUpdate(CandidatePipeline candidatePipeline) {
        log.debug("Request to partially update CandidatePipeline : {}", candidatePipeline);

        return candidatePipelineRepository
            .findById(candidatePipeline.getId())
            .map(existingCandidatePipeline -> {
                if (candidatePipeline.getJobId() != null) {
                    existingCandidatePipeline.setJobId(candidatePipeline.getJobId());
                }
                if (candidatePipeline.getStatusId() != null) {
                    existingCandidatePipeline.setStatusId(candidatePipeline.getStatusId());
                }
                if (candidatePipeline.getSubmissionStatus() != null) {
                    existingCandidatePipeline.setSubmissionStatus(candidatePipeline.getSubmissionStatus());
                }
                if (candidatePipeline.getSubmissionStage() != null) {
                    existingCandidatePipeline.setSubmissionStage(candidatePipeline.getSubmissionStage());
                }
                if (candidatePipeline.getRecruiterActions() != null) {
                    existingCandidatePipeline.setRecruiterActions(candidatePipeline.getRecruiterActions());
                }
                if (candidatePipeline.getCandidateResponses() != null) {
                    existingCandidatePipeline.setCandidateResponses(candidatePipeline.getCandidateResponses());
                }
                if (candidatePipeline.getPipelineType() != null) {
                    existingCandidatePipeline.setPipelineType(candidatePipeline.getPipelineType());
                }
                if (candidatePipeline.getReasonForNewJob() != null) {
                    existingCandidatePipeline.setReasonForNewJob(candidatePipeline.getReasonForNewJob());
                }

                return existingCandidatePipeline;
            })
            .map(candidatePipelineRepository::save);
    }

    /**
     * Get all the candidatePipelines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CandidatePipeline> findAll(Pageable pageable) {
        log.debug("Request to get all CandidatePipelines");
        return candidatePipelineRepository.findAll(pageable);
    }

    /**
     * Get one candidatePipeline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CandidatePipeline> findOne(Long id) {
        log.debug("Request to get CandidatePipeline : {}", id);
        return candidatePipelineRepository.findById(id);
    }

    /**
     * Delete the candidatePipeline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CandidatePipeline : {}", id);
        candidatePipelineRepository.deleteById(id);
    }
}
