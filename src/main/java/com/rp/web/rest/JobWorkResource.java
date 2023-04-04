package com.rp.web.rest;

import com.rp.domain.JobWork;
import com.rp.repository.JobWorkRepository;
import com.rp.service.JobWorkService;
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
 * REST controller for managing {@link com.rp.domain.JobWork}.
 */
@RestController
@RequestMapping("/api")
public class JobWorkResource {

    private final Logger log = LoggerFactory.getLogger(JobWorkResource.class);

    private static final String ENTITY_NAME = "jobWork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobWorkService jobWorkService;

    private final JobWorkRepository jobWorkRepository;

    public JobWorkResource(JobWorkService jobWorkService, JobWorkRepository jobWorkRepository) {
        this.jobWorkService = jobWorkService;
        this.jobWorkRepository = jobWorkRepository;
    }

    /**
     * {@code POST  /job-works} : Create a new jobWork.
     *
     * @param jobWork the jobWork to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobWork, or with status {@code 400 (Bad Request)} if the jobWork has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-works")
    public ResponseEntity<JobWork> createJobWork(@RequestBody JobWork jobWork) throws URISyntaxException {
        log.debug("REST request to save JobWork : {}", jobWork);
        if (jobWork.getId() != null) {
            throw new BadRequestAlertException("A new jobWork cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobWork result = jobWorkService.save(jobWork);
        return ResponseEntity
            .created(new URI("/api/job-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-works/:id} : Updates an existing jobWork.
     *
     * @param id the id of the jobWork to save.
     * @param jobWork the jobWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobWork,
     * or with status {@code 400 (Bad Request)} if the jobWork is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-works/{id}")
    public ResponseEntity<JobWork> updateJobWork(@PathVariable(value = "id", required = false) final Long id, @RequestBody JobWork jobWork)
        throws URISyntaxException {
        log.debug("REST request to update JobWork : {}, {}", id, jobWork);
        if (jobWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobWork.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobWorkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobWork result = jobWorkService.update(jobWork);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobWork.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-works/:id} : Partial updates given fields of an existing jobWork, field will ignore if it is null
     *
     * @param id the id of the jobWork to save.
     * @param jobWork the jobWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobWork,
     * or with status {@code 400 (Bad Request)} if the jobWork is not valid,
     * or with status {@code 404 (Not Found)} if the jobWork is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-works/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobWork> partialUpdateJobWork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobWork jobWork
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobWork partially : {}, {}", id, jobWork);
        if (jobWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobWork.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobWorkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobWork> result = jobWorkService.partialUpdate(jobWork);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobWork.getId().toString())
        );
    }

    /**
     * {@code GET  /job-works} : get all the jobWorks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobWorks in body.
     */
    @GetMapping("/job-works")
    public ResponseEntity<List<JobWork>> getAllJobWorks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JobWorks");
        Page<JobWork> page = jobWorkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-works/:id} : get the "id" jobWork.
     *
     * @param id the id of the jobWork to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobWork, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-works/{id}")
    public ResponseEntity<JobWork> getJobWork(@PathVariable Long id) {
        log.debug("REST request to get JobWork : {}", id);
        Optional<JobWork> jobWork = jobWorkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobWork);
    }

    /**
     * {@code DELETE  /job-works/:id} : delete the "id" jobWork.
     *
     * @param id the id of the jobWork to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-works/{id}")
    public ResponseEntity<Void> deleteJobWork(@PathVariable Long id) {
        log.debug("REST request to delete JobWork : {}", id);
        jobWorkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
