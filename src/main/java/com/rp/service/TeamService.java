package com.rp.service;

import com.rp.domain.Team;
import com.rp.repository.TeamRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Team}.
 */
@Service
@Transactional
public class TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Save a team.
     *
     * @param team the entity to save.
     * @return the persisted entity.
     */
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        return teamRepository.save(team);
    }

    /**
     * Update a team.
     *
     * @param team the entity to save.
     * @return the persisted entity.
     */
    public Team update(Team team) {
        log.debug("Request to update Team : {}", team);
        return teamRepository.save(team);
    }

    /**
     * Partially update a team.
     *
     * @param team the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Team> partialUpdate(Team team) {
        log.debug("Request to partially update Team : {}", team);

        return teamRepository
            .findById(team.getId())
            .map(existingTeam -> {
                if (team.getName() != null) {
                    existingTeam.setName(team.getName());
                }
                if (team.getTeamGroupEmail() != null) {
                    existingTeam.setTeamGroupEmail(team.getTeamGroupEmail());
                }
                if (team.getType() != null) {
                    existingTeam.setType(team.getType());
                }
                if (team.getNotifyOnJobPostingToUsers() != null) {
                    existingTeam.setNotifyOnJobPostingToUsers(team.getNotifyOnJobPostingToUsers());
                }
                if (team.getNotifyOnJobSharingToUsers() != null) {
                    existingTeam.setNotifyOnJobSharingToUsers(team.getNotifyOnJobSharingToUsers());
                }
                if (team.getNotifyOnJobClosingToUsers() != null) {
                    existingTeam.setNotifyOnJobClosingToUsers(team.getNotifyOnJobClosingToUsers());
                }
                if (team.getNotifyOnCandSubmissionToUsers() != null) {
                    existingTeam.setNotifyOnCandSubmissionToUsers(team.getNotifyOnCandSubmissionToUsers());
                }
                if (team.getNotifyOnCandStatusChangeToUsers() != null) {
                    existingTeam.setNotifyOnCandStatusChangeToUsers(team.getNotifyOnCandStatusChangeToUsers());
                }

                return existingTeam;
            })
            .map(teamRepository::save);
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Team> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamRepository.findAll(pageable);
    }

    /**
     * Get one team by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Team> findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        return teamRepository.findById(id);
    }

    /**
     * Delete the team by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.deleteById(id);
    }
}
