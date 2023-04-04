package com.rp.service;

import com.rp.domain.TeamWork;
import com.rp.repository.TeamWorkRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TeamWork}.
 */
@Service
@Transactional
public class TeamWorkService {

    private final Logger log = LoggerFactory.getLogger(TeamWorkService.class);

    private final TeamWorkRepository teamWorkRepository;

    public TeamWorkService(TeamWorkRepository teamWorkRepository) {
        this.teamWorkRepository = teamWorkRepository;
    }

    /**
     * Save a teamWork.
     *
     * @param teamWork the entity to save.
     * @return the persisted entity.
     */
    public TeamWork save(TeamWork teamWork) {
        log.debug("Request to save TeamWork : {}", teamWork);
        return teamWorkRepository.save(teamWork);
    }

    /**
     * Update a teamWork.
     *
     * @param teamWork the entity to save.
     * @return the persisted entity.
     */
    public TeamWork update(TeamWork teamWork) {
        log.debug("Request to update TeamWork : {}", teamWork);
        return teamWorkRepository.save(teamWork);
    }

    /**
     * Partially update a teamWork.
     *
     * @param teamWork the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeamWork> partialUpdate(TeamWork teamWork) {
        log.debug("Request to partially update TeamWork : {}", teamWork);

        return teamWorkRepository
            .findById(teamWork.getId())
            .map(existingTeamWork -> {
                if (teamWork.getJobId() != null) {
                    existingTeamWork.setJobId(teamWork.getJobId());
                }
                if (teamWork.getTeamId() != null) {
                    existingTeamWork.setTeamId(teamWork.getTeamId());
                }

                return existingTeamWork;
            })
            .map(teamWorkRepository::save);
    }

    /**
     * Get all the teamWorks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamWork> findAll(Pageable pageable) {
        log.debug("Request to get all TeamWorks");
        return teamWorkRepository.findAll(pageable);
    }

    /**
     * Get one teamWork by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeamWork> findOne(Long id) {
        log.debug("Request to get TeamWork : {}", id);
        return teamWorkRepository.findById(id);
    }

    /**
     * Delete the teamWork by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TeamWork : {}", id);
        teamWorkRepository.deleteById(id);
    }
}
