package com.rp.web.rest;

import com.rp.domain.JobDocument;
import com.rp.repository.JobDocumentRepository;
import com.rp.service.JobDocumentService;
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
 * REST controller for managing {@link com.rp.domain.JobDocument}.
 */
@RestController
@RequestMapping("/api")
public class JobDocumentResource {

    private final Logger log = LoggerFactory.getLogger(JobDocumentResource.class);

    private static final String ENTITY_NAME = "jobDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobDocumentService jobDocumentService;

    private final JobDocumentRepository jobDocumentRepository;

    public JobDocumentResource(JobDocumentService jobDocumentService, JobDocumentRepository jobDocumentRepository) {
        this.jobDocumentService = jobDocumentService;
        this.jobDocumentRepository = jobDocumentRepository;
    }

    /**
     * {@code POST  /job-documents} : Create a new jobDocument.
     *
     * @param jobDocument the jobDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobDocument, or with status {@code 400 (Bad Request)} if the jobDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-documents")
    public ResponseEntity<JobDocument> createJobDocument(@RequestBody JobDocument jobDocument) throws URISyntaxException {
        log.debug("REST request to save JobDocument : {}", jobDocument);
        if (jobDocument.getId() != null) {
            throw new BadRequestAlertException("A new jobDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobDocument result = jobDocumentService.save(jobDocument);
        return ResponseEntity
            .created(new URI("/api/job-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-documents/:id} : Updates an existing jobDocument.
     *
     * @param id the id of the jobDocument to save.
     * @param jobDocument the jobDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobDocument,
     * or with status {@code 400 (Bad Request)} if the jobDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-documents/{id}")
    public ResponseEntity<JobDocument> updateJobDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobDocument jobDocument
    ) throws URISyntaxException {
        log.debug("REST request to update JobDocument : {}, {}", id, jobDocument);
        if (jobDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobDocument result = jobDocumentService.update(jobDocument);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobDocument.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-documents/:id} : Partial updates given fields of an existing jobDocument, field will ignore if it is null
     *
     * @param id the id of the jobDocument to save.
     * @param jobDocument the jobDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobDocument,
     * or with status {@code 400 (Bad Request)} if the jobDocument is not valid,
     * or with status {@code 404 (Not Found)} if the jobDocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobDocument> partialUpdateJobDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobDocument jobDocument
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobDocument partially : {}, {}", id, jobDocument);
        if (jobDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobDocument> result = jobDocumentService.partialUpdate(jobDocument);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobDocument.getId().toString())
        );
    }

    /**
     * {@code GET  /job-documents} : get all the jobDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobDocuments in body.
     */
    @GetMapping("/job-documents")
    public ResponseEntity<List<JobDocument>> getAllJobDocuments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JobDocuments");
        Page<JobDocument> page = jobDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-documents/:id} : get the "id" jobDocument.
     *
     * @param id the id of the jobDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-documents/{id}")
    public ResponseEntity<JobDocument> getJobDocument(@PathVariable Long id) {
        log.debug("REST request to get JobDocument : {}", id);
        Optional<JobDocument> jobDocument = jobDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobDocument);
    }

    /**
     * {@code DELETE  /job-documents/:id} : delete the "id" jobDocument.
     *
     * @param id the id of the jobDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-documents/{id}")
    public ResponseEntity<Void> deleteJobDocument(@PathVariable Long id) {
        log.debug("REST request to delete JobDocument : {}", id);
        jobDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
