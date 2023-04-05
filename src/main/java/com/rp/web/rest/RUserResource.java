package com.rp.web.rest;

import com.rp.domain.RUser;
import com.rp.repository.RUserRepository;
import com.rp.service.RUserService;
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
 * REST controller for managing {@link com.rp.domain.RUser}.
 */
@RestController
@RequestMapping("/api")
public class RUserResource {

    private final Logger log = LoggerFactory.getLogger(RUserResource.class);

    private static final String ENTITY_NAME = "rUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RUserService rUserService;

    private final RUserRepository rUserRepository;

    public RUserResource(RUserService rUserService, RUserRepository rUserRepository) {
        this.rUserService = rUserService;
        this.rUserRepository = rUserRepository;
    }

    /**
     * {@code POST  /r-users} : Create a new rUser.
     *
     * @param rUser the rUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rUser, or with status {@code 400 (Bad Request)} if the rUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/r-users")
    public ResponseEntity<RUser> createRUser(@RequestBody RUser rUser) throws URISyntaxException {
        log.debug("REST request to save RUser : {}", rUser);
        if (rUser.getId() != null) {
            throw new BadRequestAlertException("A new rUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RUser result = rUserService.save(rUser);
        return ResponseEntity
            .created(new URI("/api/r-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /r-users/:id} : Updates an existing rUser.
     *
     * @param id the id of the rUser to save.
     * @param rUser the rUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rUser,
     * or with status {@code 400 (Bad Request)} if the rUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/r-users/{id}")
    public ResponseEntity<RUser> updateRUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody RUser rUser)
        throws URISyntaxException {
        log.debug("REST request to update RUser : {}, {}", id, rUser);
        if (rUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RUser result = rUserService.update(rUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /r-users/:id} : Partial updates given fields of an existing rUser, field will ignore if it is null
     *
     * @param id the id of the rUser to save.
     * @param rUser the rUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rUser,
     * or with status {@code 400 (Bad Request)} if the rUser is not valid,
     * or with status {@code 404 (Not Found)} if the rUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the rUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/r-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RUser> partialUpdateRUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody RUser rUser)
        throws URISyntaxException {
        log.debug("REST request to partial update RUser partially : {}, {}", id, rUser);
        if (rUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RUser> result = rUserService.partialUpdate(rUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rUser.getId().toString())
        );
    }

    /**
     * {@code GET  /r-users} : get all the rUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rUsers in body.
     */
    @GetMapping("/r-users")
    public ResponseEntity<List<RUser>> getAllRUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RUsers");
        Page<RUser> page = rUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /r-users/:id} : get the "id" rUser.
     *
     * @param id the id of the rUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/r-users/{id}")
    public ResponseEntity<RUser> getRUser(@PathVariable Long id) {
        log.debug("REST request to get RUser : {}", id);
        Optional<RUser> rUser = rUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rUser);
    }

    /**
     * {@code DELETE  /r-users/:id} : delete the "id" rUser.
     *
     * @param id the id of the rUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/r-users/{id}")
    public ResponseEntity<Void> deleteRUser(@PathVariable Long id) {
        log.debug("REST request to delete RUser : {}", id);
        rUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
