package com.rp.service;

import com.rp.domain.JobDocument;
import com.rp.repository.JobDocumentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobDocument}.
 */
@Service
@Transactional
public class JobDocumentService {

    private final Logger log = LoggerFactory.getLogger(JobDocumentService.class);

    private final JobDocumentRepository jobDocumentRepository;

    public JobDocumentService(JobDocumentRepository jobDocumentRepository) {
        this.jobDocumentRepository = jobDocumentRepository;
    }

    /**
     * Save a jobDocument.
     *
     * @param jobDocument the entity to save.
     * @return the persisted entity.
     */
    public JobDocument save(JobDocument jobDocument) {
        log.debug("Request to save JobDocument : {}", jobDocument);
        return jobDocumentRepository.save(jobDocument);
    }

    /**
     * Update a jobDocument.
     *
     * @param jobDocument the entity to save.
     * @return the persisted entity.
     */
    public JobDocument update(JobDocument jobDocument) {
        log.debug("Request to update JobDocument : {}", jobDocument);
        return jobDocumentRepository.save(jobDocument);
    }

    /**
     * Partially update a jobDocument.
     *
     * @param jobDocument the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobDocument> partialUpdate(JobDocument jobDocument) {
        log.debug("Request to partially update JobDocument : {}", jobDocument);

        return jobDocumentRepository
            .findById(jobDocument.getId())
            .map(existingJobDocument -> {
                if (jobDocument.getJobId() != null) {
                    existingJobDocument.setJobId(jobDocument.getJobId());
                }
                if (jobDocument.getDocumentTitle() != null) {
                    existingJobDocument.setDocumentTitle(jobDocument.getDocumentTitle());
                }
                if (jobDocument.getDocumentLocation() != null) {
                    existingJobDocument.setDocumentLocation(jobDocument.getDocumentLocation());
                }
                if (jobDocument.getDocumentPassword() != null) {
                    existingJobDocument.setDocumentPassword(jobDocument.getDocumentPassword());
                }

                return existingJobDocument;
            })
            .map(jobDocumentRepository::save);
    }

    /**
     * Get all the jobDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobDocument> findAll(Pageable pageable) {
        log.debug("Request to get all JobDocuments");
        return jobDocumentRepository.findAll(pageable);
    }

    /**
     * Get one jobDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobDocument> findOne(Long id) {
        log.debug("Request to get JobDocument : {}", id);
        return jobDocumentRepository.findById(id);
    }

    /**
     * Delete the jobDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobDocument : {}", id);
        jobDocumentRepository.deleteById(id);
    }
}
