package com.rp.service;

import com.rp.domain.JobCustomAttribute;
import com.rp.repository.JobCustomAttributeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobCustomAttribute}.
 */
@Service
@Transactional
public class JobCustomAttributeService {

    private final Logger log = LoggerFactory.getLogger(JobCustomAttributeService.class);

    private final JobCustomAttributeRepository jobCustomAttributeRepository;

    public JobCustomAttributeService(JobCustomAttributeRepository jobCustomAttributeRepository) {
        this.jobCustomAttributeRepository = jobCustomAttributeRepository;
    }

    /**
     * Save a jobCustomAttribute.
     *
     * @param jobCustomAttribute the entity to save.
     * @return the persisted entity.
     */
    public JobCustomAttribute save(JobCustomAttribute jobCustomAttribute) {
        log.debug("Request to save JobCustomAttribute : {}", jobCustomAttribute);
        return jobCustomAttributeRepository.save(jobCustomAttribute);
    }

    /**
     * Update a jobCustomAttribute.
     *
     * @param jobCustomAttribute the entity to save.
     * @return the persisted entity.
     */
    public JobCustomAttribute update(JobCustomAttribute jobCustomAttribute) {
        log.debug("Request to update JobCustomAttribute : {}", jobCustomAttribute);
        return jobCustomAttributeRepository.save(jobCustomAttribute);
    }

    /**
     * Partially update a jobCustomAttribute.
     *
     * @param jobCustomAttribute the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobCustomAttribute> partialUpdate(JobCustomAttribute jobCustomAttribute) {
        log.debug("Request to partially update JobCustomAttribute : {}", jobCustomAttribute);

        return jobCustomAttributeRepository
            .findById(jobCustomAttribute.getId())
            .map(existingJobCustomAttribute -> {
                if (jobCustomAttribute.getJobId() != null) {
                    existingJobCustomAttribute.setJobId(jobCustomAttribute.getJobId());
                }
                if (jobCustomAttribute.getAttributeName() != null) {
                    existingJobCustomAttribute.setAttributeName(jobCustomAttribute.getAttributeName());
                }
                if (jobCustomAttribute.getAttributeType() != null) {
                    existingJobCustomAttribute.setAttributeType(jobCustomAttribute.getAttributeType());
                }
                if (jobCustomAttribute.getAttributeValue() != null) {
                    existingJobCustomAttribute.setAttributeValue(jobCustomAttribute.getAttributeValue());
                }

                return existingJobCustomAttribute;
            })
            .map(jobCustomAttributeRepository::save);
    }

    /**
     * Get all the jobCustomAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobCustomAttribute> findAll(Pageable pageable) {
        log.debug("Request to get all JobCustomAttributes");
        return jobCustomAttributeRepository.findAll(pageable);
    }

    /**
     * Get one jobCustomAttribute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobCustomAttribute> findOne(Long id) {
        log.debug("Request to get JobCustomAttribute : {}", id);
        return jobCustomAttributeRepository.findById(id);
    }

    /**
     * Delete the jobCustomAttribute by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobCustomAttribute : {}", id);
        jobCustomAttributeRepository.deleteById(id);
    }
}
