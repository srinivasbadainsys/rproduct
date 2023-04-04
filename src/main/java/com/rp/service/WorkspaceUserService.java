package com.rp.service;

import com.rp.domain.WorkspaceUser;
import com.rp.repository.WorkspaceUserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkspaceUser}.
 */
@Service
@Transactional
public class WorkspaceUserService {

    private final Logger log = LoggerFactory.getLogger(WorkspaceUserService.class);

    private final WorkspaceUserRepository workspaceUserRepository;

    public WorkspaceUserService(WorkspaceUserRepository workspaceUserRepository) {
        this.workspaceUserRepository = workspaceUserRepository;
    }

    /**
     * Save a workspaceUser.
     *
     * @param workspaceUser the entity to save.
     * @return the persisted entity.
     */
    public WorkspaceUser save(WorkspaceUser workspaceUser) {
        log.debug("Request to save WorkspaceUser : {}", workspaceUser);
        return workspaceUserRepository.save(workspaceUser);
    }

    /**
     * Update a workspaceUser.
     *
     * @param workspaceUser the entity to save.
     * @return the persisted entity.
     */
    public WorkspaceUser update(WorkspaceUser workspaceUser) {
        log.debug("Request to update WorkspaceUser : {}", workspaceUser);
        return workspaceUserRepository.save(workspaceUser);
    }

    /**
     * Partially update a workspaceUser.
     *
     * @param workspaceUser the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkspaceUser> partialUpdate(WorkspaceUser workspaceUser) {
        log.debug("Request to partially update WorkspaceUser : {}", workspaceUser);

        return workspaceUserRepository
            .findById(workspaceUser.getId())
            .map(existingWorkspaceUser -> {
                if (workspaceUser.getUserId() != null) {
                    existingWorkspaceUser.setUserId(workspaceUser.getUserId());
                }
                if (workspaceUser.getInvitationKey() != null) {
                    existingWorkspaceUser.setInvitationKey(workspaceUser.getInvitationKey());
                }
                if (workspaceUser.getOwns() != null) {
                    existingWorkspaceUser.setOwns(workspaceUser.getOwns());
                }
                if (workspaceUser.getAccepted() != null) {
                    existingWorkspaceUser.setAccepted(workspaceUser.getAccepted());
                }
                if (workspaceUser.getInvited() != null) {
                    existingWorkspaceUser.setInvited(workspaceUser.getInvited());
                }
                if (workspaceUser.getRequested() != null) {
                    existingWorkspaceUser.setRequested(workspaceUser.getRequested());
                }
                if (workspaceUser.getBarred() != null) {
                    existingWorkspaceUser.setBarred(workspaceUser.getBarred());
                }
                if (workspaceUser.getRoles() != null) {
                    existingWorkspaceUser.setRoles(workspaceUser.getRoles());
                }
                if (workspaceUser.getRequestedOn() != null) {
                    existingWorkspaceUser.setRequestedOn(workspaceUser.getRequestedOn());
                }

                return existingWorkspaceUser;
            })
            .map(workspaceUserRepository::save);
    }

    /**
     * Get all the workspaceUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkspaceUser> findAll(Pageable pageable) {
        log.debug("Request to get all WorkspaceUsers");
        return workspaceUserRepository.findAll(pageable);
    }

    /**
     * Get one workspaceUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkspaceUser> findOne(Long id) {
        log.debug("Request to get WorkspaceUser : {}", id);
        return workspaceUserRepository.findById(id);
    }

    /**
     * Delete the workspaceUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkspaceUser : {}", id);
        workspaceUserRepository.deleteById(id);
    }
}
