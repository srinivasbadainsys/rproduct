package com.rp.service;

import com.rp.domain.JobBoardSharedTo;
import com.rp.repository.JobBoardSharedToRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobBoardSharedTo}.
 */
@Service
@Transactional
public class JobBoardSharedToService {

    private final Logger log = LoggerFactory.getLogger(JobBoardSharedToService.class);

    private final JobBoardSharedToRepository jobBoardSharedToRepository;

    public JobBoardSharedToService(JobBoardSharedToRepository jobBoardSharedToRepository) {
        this.jobBoardSharedToRepository = jobBoardSharedToRepository;
    }

    /**
     * Save a jobBoardSharedTo.
     *
     * @param jobBoardSharedTo the entity to save.
     * @return the persisted entity.
     */
    public JobBoardSharedTo save(JobBoardSharedTo jobBoardSharedTo) {
        log.debug("Request to save JobBoardSharedTo : {}", jobBoardSharedTo);
        return jobBoardSharedToRepository.save(jobBoardSharedTo);
    }

    /**
     * Update a jobBoardSharedTo.
     *
     * @param jobBoardSharedTo the entity to save.
     * @return the persisted entity.
     */
    public JobBoardSharedTo update(JobBoardSharedTo jobBoardSharedTo) {
        log.debug("Request to update JobBoardSharedTo : {}", jobBoardSharedTo);
        return jobBoardSharedToRepository.save(jobBoardSharedTo);
    }

    /**
     * Partially update a jobBoardSharedTo.
     *
     * @param jobBoardSharedTo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobBoardSharedTo> partialUpdate(JobBoardSharedTo jobBoardSharedTo) {
        log.debug("Request to partially update JobBoardSharedTo : {}", jobBoardSharedTo);

        return jobBoardSharedToRepository
            .findById(jobBoardSharedTo.getId())
            .map(existingJobBoardSharedTo -> {
                if (jobBoardSharedTo.getJobBoardId() != null) {
                    existingJobBoardSharedTo.setJobBoardId(jobBoardSharedTo.getJobBoardId());
                }
                if (jobBoardSharedTo.getSharedToEmails() != null) {
                    existingJobBoardSharedTo.setSharedToEmails(jobBoardSharedTo.getSharedToEmails());
                }
                if (jobBoardSharedTo.getSharedToUserIds() != null) {
                    existingJobBoardSharedTo.setSharedToUserIds(jobBoardSharedTo.getSharedToUserIds());
                }
                if (jobBoardSharedTo.getSharedToTeamIds() != null) {
                    existingJobBoardSharedTo.setSharedToTeamIds(jobBoardSharedTo.getSharedToTeamIds());
                }
                if (jobBoardSharedTo.getMaxExamilsMonthly() != null) {
                    existingJobBoardSharedTo.setMaxExamilsMonthly(jobBoardSharedTo.getMaxExamilsMonthly());
                }
                if (jobBoardSharedTo.getExpiresOn() != null) {
                    existingJobBoardSharedTo.setExpiresOn(jobBoardSharedTo.getExpiresOn());
                }

                return existingJobBoardSharedTo;
            })
            .map(jobBoardSharedToRepository::save);
    }

    /**
     * Get all the jobBoardSharedTos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobBoardSharedTo> findAll(Pageable pageable) {
        log.debug("Request to get all JobBoardSharedTos");
        return jobBoardSharedToRepository.findAll(pageable);
    }

    /**
     * Get one jobBoardSharedTo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobBoardSharedTo> findOne(Long id) {
        log.debug("Request to get JobBoardSharedTo : {}", id);
        return jobBoardSharedToRepository.findById(id);
    }

    /**
     * Delete the jobBoardSharedTo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobBoardSharedTo : {}", id);
        jobBoardSharedToRepository.deleteById(id);
    }
}
