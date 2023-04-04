package com.rp.web.rest;

import com.rp.domain.WorkspaceLocation;
import com.rp.repository.WorkspaceLocationRepository;
import com.rp.service.WorkspaceLocationService;
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
 * REST controller for managing {@link com.rp.domain.WorkspaceLocation}.
 */
@RestController
@RequestMapping("/api")
public class WorkspaceLocationResource {

    private final Logger log = LoggerFactory.getLogger(WorkspaceLocationResource.class);

    private static final String ENTITY_NAME = "workspaceLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkspaceLocationService workspaceLocationService;

    private final WorkspaceLocationRepository workspaceLocationRepository;

    public WorkspaceLocationResource(
        WorkspaceLocationService workspaceLocationService,
        WorkspaceLocationRepository workspaceLocationRepository
    ) {
        this.workspaceLocationService = workspaceLocationService;
        this.workspaceLocationRepository = workspaceLocationRepository;
    }

    /**
     * {@code POST  /workspace-locations} : Create a new workspaceLocation.
     *
     * @param workspaceLocation the workspaceLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workspaceLocation, or with status {@code 400 (Bad Request)} if the workspaceLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workspace-locations")
    public ResponseEntity<WorkspaceLocation> createWorkspaceLocation(@RequestBody WorkspaceLocation workspaceLocation)
        throws URISyntaxException {
        log.debug("REST request to save WorkspaceLocation : {}", workspaceLocation);
        if (workspaceLocation.getId() != null) {
            throw new BadRequestAlertException("A new workspaceLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkspaceLocation result = workspaceLocationService.save(workspaceLocation);
        return ResponseEntity
            .created(new URI("/api/workspace-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workspace-locations/:id} : Updates an existing workspaceLocation.
     *
     * @param id the id of the workspaceLocation to save.
     * @param workspaceLocation the workspaceLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workspaceLocation,
     * or with status {@code 400 (Bad Request)} if the workspaceLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workspaceLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workspace-locations/{id}")
    public ResponseEntity<WorkspaceLocation> updateWorkspaceLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkspaceLocation workspaceLocation
    ) throws URISyntaxException {
        log.debug("REST request to update WorkspaceLocation : {}, {}", id, workspaceLocation);
        if (workspaceLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workspaceLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workspaceLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkspaceLocation result = workspaceLocationService.update(workspaceLocation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workspaceLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /workspace-locations/:id} : Partial updates given fields of an existing workspaceLocation, field will ignore if it is null
     *
     * @param id the id of the workspaceLocation to save.
     * @param workspaceLocation the workspaceLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workspaceLocation,
     * or with status {@code 400 (Bad Request)} if the workspaceLocation is not valid,
     * or with status {@code 404 (Not Found)} if the workspaceLocation is not found,
     * or with status {@code 500 (Internal Server Error)} if the workspaceLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/workspace-locations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkspaceLocation> partialUpdateWorkspaceLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkspaceLocation workspaceLocation
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkspaceLocation partially : {}, {}", id, workspaceLocation);
        if (workspaceLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workspaceLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workspaceLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkspaceLocation> result = workspaceLocationService.partialUpdate(workspaceLocation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workspaceLocation.getId().toString())
        );
    }

    /**
     * {@code GET  /workspace-locations} : get all the workspaceLocations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workspaceLocations in body.
     */
    @GetMapping("/workspace-locations")
    public ResponseEntity<List<WorkspaceLocation>> getAllWorkspaceLocations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of WorkspaceLocations");
        Page<WorkspaceLocation> page = workspaceLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /workspace-locations/:id} : get the "id" workspaceLocation.
     *
     * @param id the id of the workspaceLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workspaceLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workspace-locations/{id}")
    public ResponseEntity<WorkspaceLocation> getWorkspaceLocation(@PathVariable Long id) {
        log.debug("REST request to get WorkspaceLocation : {}", id);
        Optional<WorkspaceLocation> workspaceLocation = workspaceLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workspaceLocation);
    }

    /**
     * {@code DELETE  /workspace-locations/:id} : delete the "id" workspaceLocation.
     *
     * @param id the id of the workspaceLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workspace-locations/{id}")
    public ResponseEntity<Void> deleteWorkspaceLocation(@PathVariable Long id) {
        log.debug("REST request to delete WorkspaceLocation : {}", id);
        workspaceLocationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
