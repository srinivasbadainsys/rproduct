package com.rp.service;

import com.rp.domain.JobWork;
import com.rp.repository.JobWorkRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobWork}.
 */
@Service
@Transactional
public class JobWorkService {

    private final Logger log = LoggerFactory.getLogger(JobWorkService.class);

    private final JobWorkRepository jobWorkRepository;

    public JobWorkService(JobWorkRepository jobWorkRepository) {
        this.jobWorkRepository = jobWorkRepository;
    }

    /**
     * Save a jobWork.
     *
     * @param jobWork the entity to save.
     * @return the persisted entity.
     */
    public JobWork save(JobWork jobWork) {
        log.debug("Request to save JobWork : {}", jobWork);
        return jobWorkRepository.save(jobWork);
    }

    /**
     * Update a jobWork.
     *
     * @param jobWork the entity to save.
     * @return the persisted entity.
     */
    public JobWork update(JobWork jobWork) {
        log.debug("Request to update JobWork : {}", jobWork);
        return jobWorkRepository.save(jobWork);
    }

    /**
     * Partially update a jobWork.
     *
     * @param jobWork the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobWork> partialUpdate(JobWork jobWork) {
        log.debug("Request to partially update JobWork : {}", jobWork);

        return jobWorkRepository
            .findById(jobWork.getId())
            .map(existingJobWork -> {
                if (jobWork.getJobId() != null) {
                    existingJobWork.setJobId(jobWork.getJobId());
                }
                if (jobWork.getAssignedToTeams() != null) {
                    existingJobWork.setAssignedToTeams(jobWork.getAssignedToTeams());
                }
                if (jobWork.getAssignedToUsers() != null) {
                    existingJobWork.setAssignedToUsers(jobWork.getAssignedToUsers());
                }

                return existingJobWork;
            })
            .map(jobWorkRepository::save);
    }

    /**
     * Get all the jobWorks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobWork> findAll(Pageable pageable) {
        log.debug("Request to get all JobWorks");
        return jobWorkRepository.findAll(pageable);
    }

    /**
     * Get one jobWork by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobWork> findOne(Long id) {
        log.debug("Request to get JobWork : {}", id);
        return jobWorkRepository.findById(id);
    }

    /**
     * Delete the jobWork by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobWork : {}", id);
        jobWorkRepository.deleteById(id);
    }
}
