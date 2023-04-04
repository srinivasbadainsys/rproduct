package com.rp.service;

import com.rp.domain.WorkspaceLocation;
import com.rp.repository.WorkspaceLocationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkspaceLocation}.
 */
@Service
@Transactional
public class WorkspaceLocationService {

    private final Logger log = LoggerFactory.getLogger(WorkspaceLocationService.class);

    private final WorkspaceLocationRepository workspaceLocationRepository;

    public WorkspaceLocationService(WorkspaceLocationRepository workspaceLocationRepository) {
        this.workspaceLocationRepository = workspaceLocationRepository;
    }

    /**
     * Save a workspaceLocation.
     *
     * @param workspaceLocation the entity to save.
     * @return the persisted entity.
     */
    public WorkspaceLocation save(WorkspaceLocation workspaceLocation) {
        log.debug("Request to save WorkspaceLocation : {}", workspaceLocation);
        return workspaceLocationRepository.save(workspaceLocation);
    }

    /**
     * Update a workspaceLocation.
     *
     * @param workspaceLocation the entity to save.
     * @return the persisted entity.
     */
    public WorkspaceLocation update(WorkspaceLocation workspaceLocation) {
        log.debug("Request to update WorkspaceLocation : {}", workspaceLocation);
        return workspaceLocationRepository.save(workspaceLocation);
    }

    /**
     * Partially update a workspaceLocation.
     *
     * @param workspaceLocation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkspaceLocation> partialUpdate(WorkspaceLocation workspaceLocation) {
        log.debug("Request to partially update WorkspaceLocation : {}", workspaceLocation);

        return workspaceLocationRepository
            .findById(workspaceLocation.getId())
            .map(existingWorkspaceLocation -> {
                if (workspaceLocation.getArea() != null) {
                    existingWorkspaceLocation.setArea(workspaceLocation.getArea());
                }
                if (workspaceLocation.getCity() != null) {
                    existingWorkspaceLocation.setCity(workspaceLocation.getCity());
                }
                if (workspaceLocation.getState() != null) {
                    existingWorkspaceLocation.setState(workspaceLocation.getState());
                }
                if (workspaceLocation.getStateCode() != null) {
                    existingWorkspaceLocation.setStateCode(workspaceLocation.getStateCode());
                }
                if (workspaceLocation.getCountry() != null) {
                    existingWorkspaceLocation.setCountry(workspaceLocation.getCountry());
                }
                if (workspaceLocation.getCountryCode() != null) {
                    existingWorkspaceLocation.setCountryCode(workspaceLocation.getCountryCode());
                }
                if (workspaceLocation.getZipCode() != null) {
                    existingWorkspaceLocation.setZipCode(workspaceLocation.getZipCode());
                }
                if (workspaceLocation.getLat() != null) {
                    existingWorkspaceLocation.setLat(workspaceLocation.getLat());
                }
                if (workspaceLocation.getLon() != null) {
                    existingWorkspaceLocation.setLon(workspaceLocation.getLon());
                }
                if (workspaceLocation.getContinent() != null) {
                    existingWorkspaceLocation.setContinent(workspaceLocation.getContinent());
                }
                if (workspaceLocation.getContinentCode() != null) {
                    existingWorkspaceLocation.setContinentCode(workspaceLocation.getContinentCode());
                }
                if (workspaceLocation.getPoint() != null) {
                    existingWorkspaceLocation.setPoint(workspaceLocation.getPoint());
                }

                return existingWorkspaceLocation;
            })
            .map(workspaceLocationRepository::save);
    }

    /**
     * Get all the workspaceLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkspaceLocation> findAll(Pageable pageable) {
        log.debug("Request to get all WorkspaceLocations");
        return workspaceLocationRepository.findAll(pageable);
    }

    /**
     * Get one workspaceLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkspaceLocation> findOne(Long id) {
        log.debug("Request to get WorkspaceLocation : {}", id);
        return workspaceLocationRepository.findById(id);
    }

    /**
     * Delete the workspaceLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkspaceLocation : {}", id);
        workspaceLocationRepository.deleteById(id);
    }
}
