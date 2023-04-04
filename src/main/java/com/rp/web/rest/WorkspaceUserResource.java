package com.rp.web.rest;

import com.rp.domain.WorkspaceUser;
import com.rp.repository.WorkspaceUserRepository;
import com.rp.service.WorkspaceUserService;
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
 * REST controller for managing {@link com.rp.domain.WorkspaceUser}.
 */
@RestController
@RequestMapping("/api")
public class WorkspaceUserResource {

    private final Logger log = LoggerFactory.getLogger(WorkspaceUserResource.class);

    private static final String ENTITY_NAME = "workspaceUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkspaceUserService workspaceUserService;

    private final WorkspaceUserRepository workspaceUserRepository;

    public WorkspaceUserResource(WorkspaceUserService workspaceUserService, WorkspaceUserRepository workspaceUserRepository) {
        this.workspaceUserService = workspaceUserService;
        this.workspaceUserRepository = workspaceUserRepository;
    }

    /**
     * {@code POST  /workspace-users} : Create a new workspaceUser.
     *
     * @param workspaceUser the workspaceUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workspaceUser, or with status {@code 400 (Bad Request)} if the workspaceUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workspace-users")
    public ResponseEntity<WorkspaceUser> createWorkspaceUser(@RequestBody WorkspaceUser workspaceUser) throws URISyntaxException {
        log.debug("REST request to save WorkspaceUser : {}", workspaceUser);
        if (workspaceUser.getId() != null) {
            throw new BadRequestAlertException("A new workspaceUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkspaceUser result = workspaceUserService.save(workspaceUser);
        return ResponseEntity
            .created(new URI("/api/workspace-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workspace-users/:id} : Updates an existing workspaceUser.
     *
     * @param id the id of the workspaceUser to save.
     * @param workspaceUser the workspaceUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workspaceUser,
     * or with status {@code 400 (Bad Request)} if the workspaceUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workspaceUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workspace-users/{id}")
    public ResponseEntity<WorkspaceUser> updateWorkspaceUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkspaceUser workspaceUser
    ) throws URISyntaxException {
        log.debug("REST request to update WorkspaceUser : {}, {}", id, workspaceUser);
        if (workspaceUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workspaceUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workspaceUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkspaceUser result = workspaceUserService.update(workspaceUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workspaceUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /workspace-users/:id} : Partial updates given fields of an existing workspaceUser, field will ignore if it is null
     *
     * @param id the id of the workspaceUser to save.
     * @param workspaceUser the workspaceUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workspaceUser,
     * or with status {@code 400 (Bad Request)} if the workspaceUser is not valid,
     * or with status {@code 404 (Not Found)} if the workspaceUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the workspaceUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/workspace-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkspaceUser> partialUpdateWorkspaceUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkspaceUser workspaceUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkspaceUser partially : {}, {}", id, workspaceUser);
        if (workspaceUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workspaceUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workspaceUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkspaceUser> result = workspaceUserService.partialUpdate(workspaceUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workspaceUser.getId().toString())
        );
    }

    /**
     * {@code GET  /workspace-users} : get all the workspaceUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workspaceUsers in body.
     */
    @GetMapping("/workspace-users")
    public ResponseEntity<List<WorkspaceUser>> getAllWorkspaceUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of WorkspaceUsers");
        Page<WorkspaceUser> page = workspaceUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /workspace-users/:id} : get the "id" workspaceUser.
     *
     * @param id the id of the workspaceUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workspaceUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workspace-users/{id}")
    public ResponseEntity<WorkspaceUser> getWorkspaceUser(@PathVariable Long id) {
        log.debug("REST request to get WorkspaceUser : {}", id);
        Optional<WorkspaceUser> workspaceUser = workspaceUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workspaceUser);
    }

    /**
     * {@code DELETE  /workspace-users/:id} : delete the "id" workspaceUser.
     *
     * @param id the id of the workspaceUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workspace-users/{id}")
    public ResponseEntity<Void> deleteWorkspaceUser(@PathVariable Long id) {
        log.debug("REST request to delete WorkspaceUser : {}", id);
        workspaceUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
