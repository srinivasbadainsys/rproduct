package com.rp.web.rest;

import com.rp.domain.UserWork;
import com.rp.repository.UserWorkRepository;
import com.rp.service.UserWorkService;
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
 * REST controller for managing {@link com.rp.domain.UserWork}.
 */
@RestController
@RequestMapping("/api")
public class UserWorkResource {

    private final Logger log = LoggerFactory.getLogger(UserWorkResource.class);

    private static final String ENTITY_NAME = "userWork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserWorkService userWorkService;

    private final UserWorkRepository userWorkRepository;

    public UserWorkResource(UserWorkService userWorkService, UserWorkRepository userWorkRepository) {
        this.userWorkService = userWorkService;
        this.userWorkRepository = userWorkRepository;
    }

    /**
     * {@code POST  /user-works} : Create a new userWork.
     *
     * @param userWork the userWork to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userWork, or with status {@code 400 (Bad Request)} if the userWork has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-works")
    public ResponseEntity<UserWork> createUserWork(@RequestBody UserWork userWork) throws URISyntaxException {
        log.debug("REST request to save UserWork : {}", userWork);
        if (userWork.getId() != null) {
            throw new BadRequestAlertException("A new userWork cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserWork result = userWorkService.save(userWork);
        return ResponseEntity
            .created(new URI("/api/user-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-works/:id} : Updates an existing userWork.
     *
     * @param id the id of the userWork to save.
     * @param userWork the userWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userWork,
     * or with status {@code 400 (Bad Request)} if the userWork is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-works/{id}")
    public ResponseEntity<UserWork> updateUserWork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserWork userWork
    ) throws URISyntaxException {
        log.debug("REST request to update UserWork : {}, {}", id, userWork);
        if (userWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userWork.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userWorkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserWork result = userWorkService.update(userWork);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userWork.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-works/:id} : Partial updates given fields of an existing userWork, field will ignore if it is null
     *
     * @param id the id of the userWork to save.
     * @param userWork the userWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userWork,
     * or with status {@code 400 (Bad Request)} if the userWork is not valid,
     * or with status {@code 404 (Not Found)} if the userWork is not found,
     * or with status {@code 500 (Internal Server Error)} if the userWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-works/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserWork> partialUpdateUserWork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserWork userWork
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserWork partially : {}, {}", id, userWork);
        if (userWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userWork.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userWorkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserWork> result = userWorkService.partialUpdate(userWork);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userWork.getId().toString())
        );
    }

    /**
     * {@code GET  /user-works} : get all the userWorks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userWorks in body.
     */
    @GetMapping("/user-works")
    public ResponseEntity<List<UserWork>> getAllUserWorks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserWorks");
        Page<UserWork> page = userWorkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-works/:id} : get the "id" userWork.
     *
     * @param id the id of the userWork to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userWork, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-works/{id}")
    public ResponseEntity<UserWork> getUserWork(@PathVariable Long id) {
        log.debug("REST request to get UserWork : {}", id);
        Optional<UserWork> userWork = userWorkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userWork);
    }

    /**
     * {@code DELETE  /user-works/:id} : delete the "id" userWork.
     *
     * @param id the id of the userWork to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-works/{id}")
    public ResponseEntity<Void> deleteUserWork(@PathVariable Long id) {
        log.debug("REST request to delete UserWork : {}", id);
        userWorkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
