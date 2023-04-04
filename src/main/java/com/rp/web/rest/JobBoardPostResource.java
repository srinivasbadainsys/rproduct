package com.rp.web.rest;

import com.rp.domain.JobBoardPost;
import com.rp.repository.JobBoardPostRepository;
import com.rp.service.JobBoardPostService;
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
 * REST controller for managing {@link com.rp.domain.JobBoardPost}.
 */
@RestController
@RequestMapping("/api")
public class JobBoardPostResource {

    private final Logger log = LoggerFactory.getLogger(JobBoardPostResource.class);

    private static final String ENTITY_NAME = "jobBoardPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobBoardPostService jobBoardPostService;

    private final JobBoardPostRepository jobBoardPostRepository;

    public JobBoardPostResource(JobBoardPostService jobBoardPostService, JobBoardPostRepository jobBoardPostRepository) {
        this.jobBoardPostService = jobBoardPostService;
        this.jobBoardPostRepository = jobBoardPostRepository;
    }

    /**
     * {@code POST  /job-board-posts} : Create a new jobBoardPost.
     *
     * @param jobBoardPost the jobBoardPost to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobBoardPost, or with status {@code 400 (Bad Request)} if the jobBoardPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-board-posts")
    public ResponseEntity<JobBoardPost> createJobBoardPost(@RequestBody JobBoardPost jobBoardPost) throws URISyntaxException {
        log.debug("REST request to save JobBoardPost : {}", jobBoardPost);
        if (jobBoardPost.getId() != null) {
            throw new BadRequestAlertException("A new jobBoardPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobBoardPost result = jobBoardPostService.save(jobBoardPost);
        return ResponseEntity
            .created(new URI("/api/job-board-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-board-posts/:id} : Updates an existing jobBoardPost.
     *
     * @param id the id of the jobBoardPost to save.
     * @param jobBoardPost the jobBoardPost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobBoardPost,
     * or with status {@code 400 (Bad Request)} if the jobBoardPost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobBoardPost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-board-posts/{id}")
    public ResponseEntity<JobBoardPost> updateJobBoardPost(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobBoardPost jobBoardPost
    ) throws URISyntaxException {
        log.debug("REST request to update JobBoardPost : {}, {}", id, jobBoardPost);
        if (jobBoardPost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobBoardPost.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobBoardPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobBoardPost result = jobBoardPostService.update(jobBoardPost);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobBoardPost.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-board-posts/:id} : Partial updates given fields of an existing jobBoardPost, field will ignore if it is null
     *
     * @param id the id of the jobBoardPost to save.
     * @param jobBoardPost the jobBoardPost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobBoardPost,
     * or with status {@code 400 (Bad Request)} if the jobBoardPost is not valid,
     * or with status {@code 404 (Not Found)} if the jobBoardPost is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobBoardPost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-board-posts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobBoardPost> partialUpdateJobBoardPost(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobBoardPost jobBoardPost
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobBoardPost partially : {}, {}", id, jobBoardPost);
        if (jobBoardPost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobBoardPost.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobBoardPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobBoardPost> result = jobBoardPostService.partialUpdate(jobBoardPost);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobBoardPost.getId().toString())
        );
    }

    /**
     * {@code GET  /job-board-posts} : get all the jobBoardPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobBoardPosts in body.
     */
    @GetMapping("/job-board-posts")
    public ResponseEntity<List<JobBoardPost>> getAllJobBoardPosts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JobBoardPosts");
        Page<JobBoardPost> page = jobBoardPostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-board-posts/:id} : get the "id" jobBoardPost.
     *
     * @param id the id of the jobBoardPost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobBoardPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-board-posts/{id}")
    public ResponseEntity<JobBoardPost> getJobBoardPost(@PathVariable Long id) {
        log.debug("REST request to get JobBoardPost : {}", id);
        Optional<JobBoardPost> jobBoardPost = jobBoardPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobBoardPost);
    }

    /**
     * {@code DELETE  /job-board-posts/:id} : delete the "id" jobBoardPost.
     *
     * @param id the id of the jobBoardPost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-board-posts/{id}")
    public ResponseEntity<Void> deleteJobBoardPost(@PathVariable Long id) {
        log.debug("REST request to delete JobBoardPost : {}", id);
        jobBoardPostService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
