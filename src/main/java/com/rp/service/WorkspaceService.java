package com.rp.service;

import com.rp.domain.Workspace;
import com.rp.repository.WorkspaceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Workspace}.
 */
@Service
@Transactional
public class WorkspaceService {

    private final Logger log = LoggerFactory.getLogger(WorkspaceService.class);

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    /**
     * Save a workspace.
     *
     * @param workspace the entity to save.
     * @return the persisted entity.
     */
    public Workspace save(Workspace workspace) {
        log.debug("Request to save Workspace : {}", workspace);
        return workspaceRepository.save(workspace);
    }

    /**
     * Update a workspace.
     *
     * @param workspace the entity to save.
     * @return the persisted entity.
     */
    public Workspace update(Workspace workspace) {
        log.debug("Request to update Workspace : {}", workspace);
        return workspaceRepository.save(workspace);
    }

    /**
     * Partially update a workspace.
     *
     * @param workspace the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Workspace> partialUpdate(Workspace workspace) {
        log.debug("Request to partially update Workspace : {}", workspace);

        return workspaceRepository
            .findById(workspace.getId())
            .map(existingWorkspace -> {
                if (workspace.getName() != null) {
                    existingWorkspace.setName(workspace.getName());
                }
                if (workspace.getOrgName() != null) {
                    existingWorkspace.setOrgName(workspace.getOrgName());
                }
                if (workspace.getAbout() != null) {
                    existingWorkspace.setAbout(workspace.getAbout());
                }
                if (workspace.getLink() != null) {
                    existingWorkspace.setLink(workspace.getLink());
                }
                if (workspace.getOrgURLs() != null) {
                    existingWorkspace.setOrgURLs(workspace.getOrgURLs());
                }
                if (workspace.getOwnerUserId() != null) {
                    existingWorkspace.setOwnerUserId(workspace.getOwnerUserId());
                }
                if (workspace.getMainPhoneNumber() != null) {
                    existingWorkspace.setMainPhoneNumber(workspace.getMainPhoneNumber());
                }
                if (workspace.getAltPhoneNumbers() != null) {
                    existingWorkspace.setAltPhoneNumbers(workspace.getAltPhoneNumbers());
                }
                if (workspace.getMainContactEmail() != null) {
                    existingWorkspace.setMainContactEmail(workspace.getMainContactEmail());
                }
                if (workspace.getAltContactEmails() != null) {
                    existingWorkspace.setAltContactEmails(workspace.getAltContactEmails());
                }
                if (workspace.getStatus() != null) {
                    existingWorkspace.setStatus(workspace.getStatus());
                }
                if (workspace.getEnableAutoJoin() != null) {
                    existingWorkspace.setEnableAutoJoin(workspace.getEnableAutoJoin());
                }
                if (workspace.getMaxUsers() != null) {
                    existingWorkspace.setMaxUsers(workspace.getMaxUsers());
                }
                if (workspace.getTags() != null) {
                    existingWorkspace.setTags(workspace.getTags());
                }
                if (workspace.getDomains() != null) {
                    existingWorkspace.setDomains(workspace.getDomains());
                }

                return existingWorkspace;
            })
            .map(workspaceRepository::save);
    }

    /**
     * Get all the workspaces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Workspace> findAll(Pageable pageable) {
        log.debug("Request to get all Workspaces");
        return workspaceRepository.findAll(pageable);
    }

    /**
     * Get one workspace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Workspace> findOne(Long id) {
        log.debug("Request to get Workspace : {}", id);
        return workspaceRepository.findById(id);
    }

    /**
     * Delete the workspace by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Workspace : {}", id);
        workspaceRepository.deleteById(id);
    }
}
