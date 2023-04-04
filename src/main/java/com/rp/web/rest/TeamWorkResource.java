package com.rp.web.rest;

import com.rp.domain.TeamWork;
import com.rp.repository.TeamWorkRepository;
import com.rp.service.TeamWorkService;
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
 * REST controller for managing {@link com.rp.domain.TeamWork}.
 */
@RestController
@RequestMapping("/api")
public class TeamWorkResource {

    private final Logger log = LoggerFactory.getLogger(TeamWorkResource.class);

    private static final String ENTITY_NAME = "teamWork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamWorkService teamWorkService;

    private final TeamWorkRepository teamWorkRepository;

    public TeamWorkResource(TeamWorkService teamWorkService, TeamWorkRepository teamWorkRepository) {
        this.teamWorkService = teamWorkService;
        this.teamWorkRepository = teamWorkRepository;
    }

    /**
     * {@code POST  /team-works} : Create a new teamWork.
     *
     * @param teamWork the teamWork to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamWork, or with status {@code 400 (Bad Request)} if the teamWork has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-works")
    public ResponseEntity<TeamWork> createTeamWork(@RequestBody TeamWork teamWork) throws URISyntaxException {
        log.debug("REST request to save TeamWork : {}", teamWork);
        if (teamWork.getId() != null) {
            throw new BadRequestAlertException("A new teamWork cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamWork result = teamWorkService.save(teamWork);
        return ResponseEntity
            .created(new URI("/api/team-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-works/:id} : Updates an existing teamWork.
     *
     * @param id the id of the teamWork to save.
     * @param teamWork the teamWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamWork,
     * or with status {@code 400 (Bad Request)} if the teamWork is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-works/{id}")
    public ResponseEntity<TeamWork> updateTeamWork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamWork teamWork
    ) throws URISyntaxException {
        log.debug("REST request to update TeamWork : {}, {}", id, teamWork);
        if (teamWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamWork.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamWorkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeamWork result = teamWorkService.update(teamWork);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamWork.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /team-works/:id} : Partial updates given fields of an existing teamWork, field will ignore if it is null
     *
     * @param id the id of the teamWork to save.
     * @param teamWork the teamWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamWork,
     * or with status {@code 400 (Bad Request)} if the teamWork is not valid,
     * or with status {@code 404 (Not Found)} if the teamWork is not found,
     * or with status {@code 500 (Internal Server Error)} if the teamWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/team-works/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamWork> partialUpdateTeamWork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamWork teamWork
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeamWork partially : {}, {}", id, teamWork);
        if (teamWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamWork.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamWorkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeamWork> result = teamWorkService.partialUpdate(teamWork);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamWork.getId().toString())
        );
    }

    /**
     * {@code GET  /team-works} : get all the teamWorks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamWorks in body.
     */
    @GetMapping("/team-works")
    public ResponseEntity<List<TeamWork>> getAllTeamWorks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TeamWorks");
        Page<TeamWork> page = teamWorkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-works/:id} : get the "id" teamWork.
     *
     * @param id the id of the teamWork to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamWork, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-works/{id}")
    public ResponseEntity<TeamWork> getTeamWork(@PathVariable Long id) {
        log.debug("REST request to get TeamWork : {}", id);
        Optional<TeamWork> teamWork = teamWorkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamWork);
    }

    /**
     * {@code DELETE  /team-works/:id} : delete the "id" teamWork.
     *
     * @param id the id of the teamWork to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-works/{id}")
    public ResponseEntity<Void> deleteTeamWork(@PathVariable Long id) {
        log.debug("REST request to delete TeamWork : {}", id);
        teamWorkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
