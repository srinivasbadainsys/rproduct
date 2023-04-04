package com.rp.service;

import com.rp.domain.TeamMember;
import com.rp.repository.TeamMemberRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TeamMember}.
 */
@Service
@Transactional
public class TeamMemberService {

    private final Logger log = LoggerFactory.getLogger(TeamMemberService.class);

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    /**
     * Save a teamMember.
     *
     * @param teamMember the entity to save.
     * @return the persisted entity.
     */
    public TeamMember save(TeamMember teamMember) {
        log.debug("Request to save TeamMember : {}", teamMember);
        return teamMemberRepository.save(teamMember);
    }

    /**
     * Update a teamMember.
     *
     * @param teamMember the entity to save.
     * @return the persisted entity.
     */
    public TeamMember update(TeamMember teamMember) {
        log.debug("Request to update TeamMember : {}", teamMember);
        return teamMemberRepository.save(teamMember);
    }

    /**
     * Partially update a teamMember.
     *
     * @param teamMember the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeamMember> partialUpdate(TeamMember teamMember) {
        log.debug("Request to partially update TeamMember : {}", teamMember);

        return teamMemberRepository
            .findById(teamMember.getId())
            .map(existingTeamMember -> {
                if (teamMember.getTeamId() != null) {
                    existingTeamMember.setTeamId(teamMember.getTeamId());
                }

                return existingTeamMember;
            })
            .map(teamMemberRepository::save);
    }

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamMember> findAll(Pageable pageable) {
        log.debug("Request to get all TeamMembers");
        return teamMemberRepository.findAll(pageable);
    }

    /**
     * Get one teamMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeamMember> findOne(Long id) {
        log.debug("Request to get TeamMember : {}", id);
        return teamMemberRepository.findById(id);
    }

    /**
     * Delete the teamMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TeamMember : {}", id);
        teamMemberRepository.deleteById(id);
    }
}
