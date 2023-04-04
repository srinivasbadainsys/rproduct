package com.rp.web.rest;

import com.rp.domain.JobCustomAttribute;
import com.rp.repository.JobCustomAttributeRepository;
import com.rp.service.JobCustomAttributeService;
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
 * REST controller for managing {@link com.rp.domain.JobCustomAttribute}.
 */
@RestController
@RequestMapping("/api")
public class JobCustomAttributeResource {

    private final Logger log = LoggerFactory.getLogger(JobCustomAttributeResource.class);

    private static final String ENTITY_NAME = "jobCustomAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobCustomAttributeService jobCustomAttributeService;

    private final JobCustomAttributeRepository jobCustomAttributeRepository;

    public JobCustomAttributeResource(
        JobCustomAttributeService jobCustomAttributeService,
        JobCustomAttributeRepository jobCustomAttributeRepository
    ) {
        this.jobCustomAttributeService = jobCustomAttributeService;
        this.jobCustomAttributeRepository = jobCustomAttributeRepository;
    }

    /**
     * {@code POST  /job-custom-attributes} : Create a new jobCustomAttribute.
     *
     * @param jobCustomAttribute the jobCustomAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobCustomAttribute, or with status {@code 400 (Bad Request)} if the jobCustomAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-custom-attributes")
    public ResponseEntity<JobCustomAttribute> createJobCustomAttribute(@RequestBody JobCustomAttribute jobCustomAttribute)
        throws URISyntaxException {
        log.debug("REST request to save JobCustomAttribute : {}", jobCustomAttribute);
        if (jobCustomAttribute.getId() != null) {
            throw new BadRequestAlertException("A new jobCustomAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobCustomAttribute result = jobCustomAttributeService.save(jobCustomAttribute);
        return ResponseEntity
            .created(new URI("/api/job-custom-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-custom-attributes/:id} : Updates an existing jobCustomAttribute.
     *
     * @param id the id of the jobCustomAttribute to save.
     * @param jobCustomAttribute the jobCustomAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobCustomAttribute,
     * or with status {@code 400 (Bad Request)} if the jobCustomAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobCustomAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-custom-attributes/{id}")
    public ResponseEntity<JobCustomAttribute> updateJobCustomAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobCustomAttribute jobCustomAttribute
    ) throws URISyntaxException {
        log.debug("REST request to update JobCustomAttribute : {}, {}", id, jobCustomAttribute);
        if (jobCustomAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobCustomAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobCustomAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobCustomAttribute result = jobCustomAttributeService.update(jobCustomAttribute);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobCustomAttribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-custom-attributes/:id} : Partial updates given fields of an existing jobCustomAttribute, field will ignore if it is null
     *
     * @param id the id of the jobCustomAttribute to save.
     * @param jobCustomAttribute the jobCustomAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobCustomAttribute,
     * or with status {@code 400 (Bad Request)} if the jobCustomAttribute is not valid,
     * or with status {@code 404 (Not Found)} if the jobCustomAttribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobCustomAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-custom-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobCustomAttribute> partialUpdateJobCustomAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobCustomAttribute jobCustomAttribute
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobCustomAttribute partially : {}, {}", id, jobCustomAttribute);
        if (jobCustomAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobCustomAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobCustomAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobCustomAttribute> result = jobCustomAttributeService.partialUpdate(jobCustomAttribute);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobCustomAttribute.getId().toString())
        );
    }

    /**
     * {@code GET  /job-custom-attributes} : get all the jobCustomAttributes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobCustomAttributes in body.
     */
    @GetMapping("/job-custom-attributes")
    public ResponseEntity<List<JobCustomAttribute>> getAllJobCustomAttributes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of JobCustomAttributes");
        Page<JobCustomAttribute> page = jobCustomAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-custom-attributes/:id} : get the "id" jobCustomAttribute.
     *
     * @param id the id of the jobCustomAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobCustomAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-custom-attributes/{id}")
    public ResponseEntity<JobCustomAttribute> getJobCustomAttribute(@PathVariable Long id) {
        log.debug("REST request to get JobCustomAttribute : {}", id);
        Optional<JobCustomAttribute> jobCustomAttribute = jobCustomAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobCustomAttribute);
    }

    /**
     * {@code DELETE  /job-custom-attributes/:id} : delete the "id" jobCustomAttribute.
     *
     * @param id the id of the jobCustomAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-custom-attributes/{id}")
    public ResponseEntity<Void> deleteJobCustomAttribute(@PathVariable Long id) {
        log.debug("REST request to delete JobCustomAttribute : {}", id);
        jobCustomAttributeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
