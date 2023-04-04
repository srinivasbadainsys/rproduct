package com.rp.service;

import com.rp.domain.JobBoardPost;
import com.rp.repository.JobBoardPostRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobBoardPost}.
 */
@Service
@Transactional
public class JobBoardPostService {

    private final Logger log = LoggerFactory.getLogger(JobBoardPostService.class);

    private final JobBoardPostRepository jobBoardPostRepository;

    public JobBoardPostService(JobBoardPostRepository jobBoardPostRepository) {
        this.jobBoardPostRepository = jobBoardPostRepository;
    }

    /**
     * Save a jobBoardPost.
     *
     * @param jobBoardPost the entity to save.
     * @return the persisted entity.
     */
    public JobBoardPost save(JobBoardPost jobBoardPost) {
        log.debug("Request to save JobBoardPost : {}", jobBoardPost);
        return jobBoardPostRepository.save(jobBoardPost);
    }

    /**
     * Update a jobBoardPost.
     *
     * @param jobBoardPost the entity to save.
     * @return the persisted entity.
     */
    public JobBoardPost update(JobBoardPost jobBoardPost) {
        log.debug("Request to update JobBoardPost : {}", jobBoardPost);
        return jobBoardPostRepository.save(jobBoardPost);
    }

    /**
     * Partially update a jobBoardPost.
     *
     * @param jobBoardPost the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobBoardPost> partialUpdate(JobBoardPost jobBoardPost) {
        log.debug("Request to partially update JobBoardPost : {}", jobBoardPost);

        return jobBoardPostRepository
            .findById(jobBoardPost.getId())
            .map(existingJobBoardPost -> {
                if (jobBoardPost.getJobId() != null) {
                    existingJobBoardPost.setJobId(jobBoardPost.getJobId());
                }
                if (jobBoardPost.getStatus() != null) {
                    existingJobBoardPost.setStatus(jobBoardPost.getStatus());
                }

                return existingJobBoardPost;
            })
            .map(jobBoardPostRepository::save);
    }

    /**
     * Get all the jobBoardPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobBoardPost> findAll(Pageable pageable) {
        log.debug("Request to get all JobBoardPosts");
        return jobBoardPostRepository.findAll(pageable);
    }

    /**
     * Get one jobBoardPost by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobBoardPost> findOne(Long id) {
        log.debug("Request to get JobBoardPost : {}", id);
        return jobBoardPostRepository.findById(id);
    }

    /**
     * Delete the jobBoardPost by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobBoardPost : {}", id);
        jobBoardPostRepository.deleteById(id);
    }
}
