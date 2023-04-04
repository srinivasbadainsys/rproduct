package com.rp.service;

import com.rp.domain.JobLocation;
import com.rp.repository.JobLocationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobLocation}.
 */
@Service
@Transactional
public class JobLocationService {

    private final Logger log = LoggerFactory.getLogger(JobLocationService.class);

    private final JobLocationRepository jobLocationRepository;

    public JobLocationService(JobLocationRepository jobLocationRepository) {
        this.jobLocationRepository = jobLocationRepository;
    }

    /**
     * Save a jobLocation.
     *
     * @param jobLocation the entity to save.
     * @return the persisted entity.
     */
    public JobLocation save(JobLocation jobLocation) {
        log.debug("Request to save JobLocation : {}", jobLocation);
        return jobLocationRepository.save(jobLocation);
    }

    /**
     * Update a jobLocation.
     *
     * @param jobLocation the entity to save.
     * @return the persisted entity.
     */
    public JobLocation update(JobLocation jobLocation) {
        log.debug("Request to update JobLocation : {}", jobLocation);
        return jobLocationRepository.save(jobLocation);
    }

    /**
     * Partially update a jobLocation.
     *
     * @param jobLocation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobLocation> partialUpdate(JobLocation jobLocation) {
        log.debug("Request to partially update JobLocation : {}", jobLocation);

        return jobLocationRepository
            .findById(jobLocation.getId())
            .map(existingJobLocation -> {
                if (jobLocation.getJobId() != null) {
                    existingJobLocation.setJobId(jobLocation.getJobId());
                }
                if (jobLocation.getArea() != null) {
                    existingJobLocation.setArea(jobLocation.getArea());
                }
                if (jobLocation.getCity() != null) {
                    existingJobLocation.setCity(jobLocation.getCity());
                }
                if (jobLocation.getState() != null) {
                    existingJobLocation.setState(jobLocation.getState());
                }
                if (jobLocation.getStateCode() != null) {
                    existingJobLocation.setStateCode(jobLocation.getStateCode());
                }
                if (jobLocation.getCountry() != null) {
                    existingJobLocation.setCountry(jobLocation.getCountry());
                }
                if (jobLocation.getCountryCode() != null) {
                    existingJobLocation.setCountryCode(jobLocation.getCountryCode());
                }
                if (jobLocation.getZipCode() != null) {
                    existingJobLocation.setZipCode(jobLocation.getZipCode());
                }
                if (jobLocation.getLat() != null) {
                    existingJobLocation.setLat(jobLocation.getLat());
                }
                if (jobLocation.getLon() != null) {
                    existingJobLocation.setLon(jobLocation.getLon());
                }
                if (jobLocation.getContinent() != null) {
                    existingJobLocation.setContinent(jobLocation.getContinent());
                }
                if (jobLocation.getContinentCode() != null) {
                    existingJobLocation.setContinentCode(jobLocation.getContinentCode());
                }
                if (jobLocation.getPoint() != null) {
                    existingJobLocation.setPoint(jobLocation.getPoint());
                }

                return existingJobLocation;
            })
            .map(jobLocationRepository::save);
    }

    /**
     * Get all the jobLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobLocation> findAll(Pageable pageable) {
        log.debug("Request to get all JobLocations");
        return jobLocationRepository.findAll(pageable);
    }

    /**
     * Get one jobLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobLocation> findOne(Long id) {
        log.debug("Request to get JobLocation : {}", id);
        return jobLocationRepository.findById(id);
    }

    /**
     * Delete the jobLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobLocation : {}", id);
        jobLocationRepository.deleteById(id);
    }
}
