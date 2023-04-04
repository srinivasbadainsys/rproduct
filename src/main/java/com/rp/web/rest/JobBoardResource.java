package com.rp.web.rest;

import com.rp.domain.JobBoard;
import com.rp.repository.JobBoardRepository;
import com.rp.service.JobBoardService;
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
 * REST controller for managing {@link com.rp.domain.JobBoard}.
 */
@RestController
@RequestMapping("/api")
public class JobBoardResource {

    private final Logger log = LoggerFactory.getLogger(JobBoardResource.class);

    private static final String ENTITY_NAME = "jobBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobBoardService jobBoardService;

    private final JobBoardRepository jobBoardRepository;

    public JobBoardResource(JobBoardService jobBoardService, JobBoardRepository jobBoardRepository) {
        this.jobBoardService = jobBoardService;
        this.jobBoardRepository = jobBoardRepository;
    }

    /**
     * {@code POST  /job-boards} : Create a new jobBoard.
     *
     * @param jobBoard the jobBoard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobBoard, or with status {@code 400 (Bad Request)} if the jobBoard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-boards")
    public ResponseEntity<JobBoard> createJobBoard(@RequestBody JobBoard jobBoard) throws URISyntaxException {
        log.debug("REST request to save JobBoard : {}", jobBoard);
        if (jobBoard.getId() != null) {
            throw new BadRequestAlertException("A new jobBoard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobBoard result = jobBoardService.save(jobBoard);
        return ResponseEntity
            .created(new URI("/api/job-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-boards/:id} : Updates an existing jobBoard.
     *
     * @param id the id of the jobBoard to save.
     * @param jobBoard the jobBoard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobBoard,
     * or with status {@code 400 (Bad Request)} if the jobBoard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobBoard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-boards/{id}")
    public ResponseEntity<JobBoard> updateJobBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobBoard jobBoard
    ) throws URISyntaxException {
        log.debug("REST request to update JobBoard : {}, {}", id, jobBoard);
        if (jobBoard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobBoard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobBoard result = jobBoardService.update(jobBoard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobBoard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-boards/:id} : Partial updates given fields of an existing jobBoard, field will ignore if it is null
     *
     * @param id the id of the jobBoard to save.
     * @param jobBoard the jobBoard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobBoard,
     * or with status {@code 400 (Bad Request)} if the jobBoard is not valid,
     * or with status {@code 404 (Not Found)} if the jobBoard is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobBoard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-boards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobBoard> partialUpdateJobBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobBoard jobBoard
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobBoard partially : {}, {}", id, jobBoard);
        if (jobBoard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobBoard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobBoard> result = jobBoardService.partialUpdate(jobBoard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobBoard.getId().toString())
        );
    }

    /**
     * {@code GET  /job-boards} : get all the jobBoards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobBoards in body.
     */
    @GetMapping("/job-boards")
    public ResponseEntity<List<JobBoard>> getAllJobBoards(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JobBoards");
        Page<JobBoard> page = jobBoardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-boards/:id} : get the "id" jobBoard.
     *
     * @param id the id of the jobBoard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobBoard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-boards/{id}")
    public ResponseEntity<JobBoard> getJobBoard(@PathVariable Long id) {
        log.debug("REST request to get JobBoard : {}", id);
        Optional<JobBoard> jobBoard = jobBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobBoard);
    }

    /**
     * {@code DELETE  /job-boards/:id} : delete the "id" jobBoard.
     *
     * @param id the id of the jobBoard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-boards/{id}")
    public ResponseEntity<Void> deleteJobBoard(@PathVariable Long id) {
        log.debug("REST request to delete JobBoard : {}", id);
        jobBoardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
