package com.rp.web.rest;

import com.rp.domain.JobBoardSharedTo;
import com.rp.repository.JobBoardSharedToRepository;
import com.rp.service.JobBoardSharedToService;
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
 * REST controller for managing {@link com.rp.domain.JobBoardSharedTo}.
 */
@RestController
@RequestMapping("/api")
public class JobBoardSharedToResource {

    private final Logger log = LoggerFactory.getLogger(JobBoardSharedToResource.class);

    private static final String ENTITY_NAME = "jobBoardSharedTo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobBoardSharedToService jobBoardSharedToService;

    private final JobBoardSharedToRepository jobBoardSharedToRepository;

    public JobBoardSharedToResource(
        JobBoardSharedToService jobBoardSharedToService,
        JobBoardSharedToRepository jobBoardSharedToRepository
    ) {
        this.jobBoardSharedToService = jobBoardSharedToService;
        this.jobBoardSharedToRepository = jobBoardSharedToRepository;
    }

    /**
     * {@code POST  /job-board-shared-tos} : Create a new jobBoardSharedTo.
     *
     * @param jobBoardSharedTo the jobBoardSharedTo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobBoardSharedTo, or with status {@code 400 (Bad Request)} if the jobBoardSharedTo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-board-shared-tos")
    public ResponseEntity<JobBoardSharedTo> createJobBoardSharedTo(@RequestBody JobBoardSharedTo jobBoardSharedTo)
        throws URISyntaxException {
        log.debug("REST request to save JobBoardSharedTo : {}", jobBoardSharedTo);
        if (jobBoardSharedTo.getId() != null) {
            throw new BadRequestAlertException("A new jobBoardSharedTo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobBoardSharedTo result = jobBoardSharedToService.save(jobBoardSharedTo);
        return ResponseEntity
            .created(new URI("/api/job-board-shared-tos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-board-shared-tos/:id} : Updates an existing jobBoardSharedTo.
     *
     * @param id the id of the jobBoardSharedTo to save.
     * @param jobBoardSharedTo the jobBoardSharedTo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobBoardSharedTo,
     * or with status {@code 400 (Bad Request)} if the jobBoardSharedTo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobBoardSharedTo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-board-shared-tos/{id}")
    public ResponseEntity<JobBoardSharedTo> updateJobBoardSharedTo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobBoardSharedTo jobBoardSharedTo
    ) throws URISyntaxException {
        log.debug("REST request to update JobBoardSharedTo : {}, {}", id, jobBoardSharedTo);
        if (jobBoardSharedTo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobBoardSharedTo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobBoardSharedToRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobBoardSharedTo result = jobBoardSharedToService.update(jobBoardSharedTo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobBoardSharedTo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-board-shared-tos/:id} : Partial updates given fields of an existing jobBoardSharedTo, field will ignore if it is null
     *
     * @param id the id of the jobBoardSharedTo to save.
     * @param jobBoardSharedTo the jobBoardSharedTo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobBoardSharedTo,
     * or with status {@code 400 (Bad Request)} if the jobBoardSharedTo is not valid,
     * or with status {@code 404 (Not Found)} if the jobBoardSharedTo is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobBoardSharedTo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-board-shared-tos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobBoardSharedTo> partialUpdateJobBoardSharedTo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobBoardSharedTo jobBoardSharedTo
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobBoardSharedTo partially : {}, {}", id, jobBoardSharedTo);
        if (jobBoardSharedTo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobBoardSharedTo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobBoardSharedToRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobBoardSharedTo> result = jobBoardSharedToService.partialUpdate(jobBoardSharedTo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobBoardSharedTo.getId().toString())
        );
    }

    /**
     * {@code GET  /job-board-shared-tos} : get all the jobBoardSharedTos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobBoardSharedTos in body.
     */
    @GetMapping("/job-board-shared-tos")
    public ResponseEntity<List<JobBoardSharedTo>> getAllJobBoardSharedTos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of JobBoardSharedTos");
        Page<JobBoardSharedTo> page = jobBoardSharedToService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-board-shared-tos/:id} : get the "id" jobBoardSharedTo.
     *
     * @param id the id of the jobBoardSharedTo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobBoardSharedTo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-board-shared-tos/{id}")
    public ResponseEntity<JobBoardSharedTo> getJobBoardSharedTo(@PathVariable Long id) {
        log.debug("REST request to get JobBoardSharedTo : {}", id);
        Optional<JobBoardSharedTo> jobBoardSharedTo = jobBoardSharedToService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobBoardSharedTo);
    }

    /**
     * {@code DELETE  /job-board-shared-tos/:id} : delete the "id" jobBoardSharedTo.
     *
     * @param id the id of the jobBoardSharedTo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-board-shared-tos/{id}")
    public ResponseEntity<Void> deleteJobBoardSharedTo(@PathVariable Long id) {
        log.debug("REST request to delete JobBoardSharedTo : {}", id);
        jobBoardSharedToService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
