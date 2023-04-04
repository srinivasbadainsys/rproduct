package com.rp.web.rest;

import com.rp.domain.JobLocation;
import com.rp.repository.JobLocationRepository;
import com.rp.service.JobLocationService;
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
 * REST controller for managing {@link com.rp.domain.JobLocation}.
 */
@RestController
@RequestMapping("/api")
public class JobLocationResource {

    private final Logger log = LoggerFactory.getLogger(JobLocationResource.class);

    private static final String ENTITY_NAME = "jobLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobLocationService jobLocationService;

    private final JobLocationRepository jobLocationRepository;

    public JobLocationResource(JobLocationService jobLocationService, JobLocationRepository jobLocationRepository) {
        this.jobLocationService = jobLocationService;
        this.jobLocationRepository = jobLocationRepository;
    }

    /**
     * {@code POST  /job-locations} : Create a new jobLocation.
     *
     * @param jobLocation the jobLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobLocation, or with status {@code 400 (Bad Request)} if the jobLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-locations")
    public ResponseEntity<JobLocation> createJobLocation(@RequestBody JobLocation jobLocation) throws URISyntaxException {
        log.debug("REST request to save JobLocation : {}", jobLocation);
        if (jobLocation.getId() != null) {
            throw new BadRequestAlertException("A new jobLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobLocation result = jobLocationService.save(jobLocation);
        return ResponseEntity
            .created(new URI("/api/job-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-locations/:id} : Updates an existing jobLocation.
     *
     * @param id the id of the jobLocation to save.
     * @param jobLocation the jobLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobLocation,
     * or with status {@code 400 (Bad Request)} if the jobLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-locations/{id}")
    public ResponseEntity<JobLocation> updateJobLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobLocation jobLocation
    ) throws URISyntaxException {
        log.debug("REST request to update JobLocation : {}, {}", id, jobLocation);
        if (jobLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobLocation result = jobLocationService.update(jobLocation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-locations/:id} : Partial updates given fields of an existing jobLocation, field will ignore if it is null
     *
     * @param id the id of the jobLocation to save.
     * @param jobLocation the jobLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobLocation,
     * or with status {@code 400 (Bad Request)} if the jobLocation is not valid,
     * or with status {@code 404 (Not Found)} if the jobLocation is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-locations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobLocation> partialUpdateJobLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobLocation jobLocation
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobLocation partially : {}, {}", id, jobLocation);
        if (jobLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobLocation> result = jobLocationService.partialUpdate(jobLocation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobLocation.getId().toString())
        );
    }

    /**
     * {@code GET  /job-locations} : get all the jobLocations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobLocations in body.
     */
    @GetMapping("/job-locations")
    public ResponseEntity<List<JobLocation>> getAllJobLocations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JobLocations");
        Page<JobLocation> page = jobLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-locations/:id} : get the "id" jobLocation.
     *
     * @param id the id of the jobLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-locations/{id}")
    public ResponseEntity<JobLocation> getJobLocation(@PathVariable Long id) {
        log.debug("REST request to get JobLocation : {}", id);
        Optional<JobLocation> jobLocation = jobLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobLocation);
    }

    /**
     * {@code DELETE  /job-locations/:id} : delete the "id" jobLocation.
     *
     * @param id the id of the jobLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-locations/{id}")
    public ResponseEntity<Void> deleteJobLocation(@PathVariable Long id) {
        log.debug("REST request to delete JobLocation : {}", id);
        jobLocationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
