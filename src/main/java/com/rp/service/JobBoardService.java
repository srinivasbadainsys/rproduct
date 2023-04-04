package com.rp.service;

import com.rp.domain.JobBoard;
import com.rp.repository.JobBoardRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobBoard}.
 */
@Service
@Transactional
public class JobBoardService {

    private final Logger log = LoggerFactory.getLogger(JobBoardService.class);

    private final JobBoardRepository jobBoardRepository;

    public JobBoardService(JobBoardRepository jobBoardRepository) {
        this.jobBoardRepository = jobBoardRepository;
    }

    /**
     * Save a jobBoard.
     *
     * @param jobBoard the entity to save.
     * @return the persisted entity.
     */
    public JobBoard save(JobBoard jobBoard) {
        log.debug("Request to save JobBoard : {}", jobBoard);
        return jobBoardRepository.save(jobBoard);
    }

    /**
     * Update a jobBoard.
     *
     * @param jobBoard the entity to save.
     * @return the persisted entity.
     */
    public JobBoard update(JobBoard jobBoard) {
        log.debug("Request to update JobBoard : {}", jobBoard);
        return jobBoardRepository.save(jobBoard);
    }

    /**
     * Partially update a jobBoard.
     *
     * @param jobBoard the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobBoard> partialUpdate(JobBoard jobBoard) {
        log.debug("Request to partially update JobBoard : {}", jobBoard);

        return jobBoardRepository
            .findById(jobBoard.getId())
            .map(existingJobBoard -> {
                if (jobBoard.getJobBoardName() != null) {
                    existingJobBoard.setJobBoardName(jobBoard.getJobBoardName());
                }
                if (jobBoard.getJobBoardType() != null) {
                    existingJobBoard.setJobBoardType(jobBoard.getJobBoardType());
                }
                if (jobBoard.getUsername() != null) {
                    existingJobBoard.setUsername(jobBoard.getUsername());
                }
                if (jobBoard.getPassword() != null) {
                    existingJobBoard.setPassword(jobBoard.getPassword());
                }
                if (jobBoard.getSettings() != null) {
                    existingJobBoard.setSettings(jobBoard.getSettings());
                }
                if (jobBoard.getAutoRefresh() != null) {
                    existingJobBoard.setAutoRefresh(jobBoard.getAutoRefresh());
                }
                if (jobBoard.getJobDuration() != null) {
                    existingJobBoard.setJobDuration(jobBoard.getJobDuration());
                }

                return existingJobBoard;
            })
            .map(jobBoardRepository::save);
    }

    /**
     * Get all the jobBoards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobBoard> findAll(Pageable pageable) {
        log.debug("Request to get all JobBoards");
        return jobBoardRepository.findAll(pageable);
    }

    /**
     * Get one jobBoard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobBoard> findOne(Long id) {
        log.debug("Request to get JobBoard : {}", id);
        return jobBoardRepository.findById(id);
    }

    /**
     * Delete the jobBoard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobBoard : {}", id);
        jobBoardRepository.deleteById(id);
    }
}
