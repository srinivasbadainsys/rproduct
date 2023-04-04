package com.rp.web.rest;

import com.rp.domain.TeamMember;
import com.rp.repository.TeamMemberRepository;
import com.rp.service.TeamMemberService;
import com.rp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.rp.domain.TeamMember}.
 */
@RestController
@RequestMapping("/api")
public class TeamMemberResource {

    private final Logger log = LoggerFactory.getLogger(TeamMemberResource.class);

    private static final String ENTITY_NAME = "teamMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamMemberService teamMemberService;

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberResource(TeamMemberService teamMemberService, TeamMemberRepository teamMemberRepository) {
        this.teamMemberService = teamMemberService;
        this.teamMemberRepository = teamMemberRepository;
    }

    /**
     * {@code POST  /team-members} : Create a new teamMember.
     *
     * @param teamMember the teamMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamMember, or with status {@code 400 (Bad Request)} if the teamMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-members")
    public ResponseEntity<TeamMember> createTeamMember(@RequestBody TeamMember teamMember) throws URISyntaxException {
        log.debug("REST request to save TeamMember : {}", teamMember);
        if (teamMember.getId() != null) {
            throw new BadRequestAlertException("A new teamMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamMember result = teamMemberService.save(teamMember);
        return ResponseEntity
            .created(new URI("/api/team-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-members/:id} : Updates an existing teamMember.
     *
     * @param id the id of the teamMember to save.
     * @param teamMember the teamMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamMember,
     * or with status {@code 400 (Bad Request)} if the teamMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-members/{id}")
    public ResponseEntity<TeamMember> updateTeamMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamMember teamMember
    ) throws URISyntaxException {
        log.debug("REST request to update TeamMember : {}, {}", id, teamMember);
        if (teamMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamMember.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeamMember result = teamMemberService.update(teamMember);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /team-members/:id} : Partial updates given fields of an existing teamMember, field will ignore if it is null
     *
     * @param id the id of the teamMember to save.
     * @param teamMember the teamMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamMember,
     * or with status {@code 400 (Bad Request)} if the teamMember is not valid,
     * or with status {@code 404 (Not Found)} if the teamMember is not found,
     * or with status {@code 500 (Internal Server Error)} if the teamMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/team-members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamMember> partialUpdateTeamMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamMember teamMember
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeamMember partially : {}, {}", id, teamMember);
        if (teamMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamMember.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeamMember> result = teamMemberService.partialUpdate(teamMember);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamMember.getId().toString())
        );
    }

    /**
     * {@code GET  /team-members} : get all the teamMembers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamMembers in body.
     */
    @GetMapping("/team-members")
    public ResponseEntity<List<TeamMember>> getAllTeamMembers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TeamMembers");
        Page<TeamMember> page = teamMemberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-members/:id} : get the "id" teamMember.
     *
     * @param id the id of the teamMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-members/{id}")
    public ResponseEntity<TeamMember> getTeamMember(@PathVariable Long id) {
        log.debug("REST request to get TeamMember : {}", id);
        Optional<TeamMember> teamMember = teamMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamMember);
    }

    /**
     * {@code DELETE  /team-members/:id} : delete the "id" teamMember.
     *
     * @param id the id of the teamMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-members/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        log.debug("REST request to delete TeamMember : {}", id);
        teamMemberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
