package com.rp.web.rest;

import com.rp.domain.CandidatePipeline;
import com.rp.repository.CandidatePipelineRepository;
import com.rp.service.CandidatePipelineService;
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
 * REST controller for managing {@link com.rp.domain.CandidatePipeline}.
 */
@RestController
@RequestMapping("/api")
public class CandidatePipelineResource {

    private final Logger log = LoggerFactory.getLogger(CandidatePipelineResource.class);

    private static final String ENTITY_NAME = "candidatePipeline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidatePipelineService candidatePipelineService;

    private final CandidatePipelineRepository candidatePipelineRepository;

    public CandidatePipelineResource(
        CandidatePipelineService candidatePipelineService,
        CandidatePipelineRepository candidatePipelineRepository
    ) {
        this.candidatePipelineService = candidatePipelineService;
        this.candidatePipelineRepository = candidatePipelineRepository;
    }

    /**
     * {@code POST  /candidate-pipelines} : Create a new candidatePipeline.
     *
     * @param candidatePipeline the candidatePipeline to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidatePipeline, or with status {@code 400 (Bad Request)} if the candidatePipeline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidate-pipelines")
    public ResponseEntity<CandidatePipeline> createCandidatePipeline(@RequestBody CandidatePipeline candidatePipeline)
        throws URISyntaxException {
        log.debug("REST request to save CandidatePipeline : {}", candidatePipeline);
        if (candidatePipeline.getId() != null) {
            throw new BadRequestAlertException("A new candidatePipeline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidatePipeline result = candidatePipelineService.save(candidatePipeline);
        return ResponseEntity
            .created(new URI("/api/candidate-pipelines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidate-pipelines/:id} : Updates an existing candidatePipeline.
     *
     * @param id the id of the candidatePipeline to save.
     * @param candidatePipeline the candidatePipeline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatePipeline,
     * or with status {@code 400 (Bad Request)} if the candidatePipeline is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidatePipeline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidate-pipelines/{id}")
    public ResponseEntity<CandidatePipeline> updateCandidatePipeline(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidatePipeline candidatePipeline
    ) throws URISyntaxException {
        log.debug("REST request to update CandidatePipeline : {}, {}", id, candidatePipeline);
        if (candidatePipeline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidatePipeline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatePipelineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CandidatePipeline result = candidatePipelineService.update(candidatePipeline);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidatePipeline.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /candidate-pipelines/:id} : Partial updates given fields of an existing candidatePipeline, field will ignore if it is null
     *
     * @param id the id of the candidatePipeline to save.
     * @param candidatePipeline the candidatePipeline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidatePipeline,
     * or with status {@code 400 (Bad Request)} if the candidatePipeline is not valid,
     * or with status {@code 404 (Not Found)} if the candidatePipeline is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidatePipeline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/candidate-pipelines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CandidatePipeline> partialUpdateCandidatePipeline(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidatePipeline candidatePipeline
    ) throws URISyntaxException {
        log.debug("REST request to partial update CandidatePipeline partially : {}, {}", id, candidatePipeline);
        if (candidatePipeline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidatePipeline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidatePipelineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CandidatePipeline> result = candidatePipelineService.partialUpdate(candidatePipeline);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidatePipeline.getId().toString())
        );
    }

    /**
     * {@code GET  /candidate-pipelines} : get all the candidatePipelines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidatePipelines in body.
     */
    @GetMapping("/candidate-pipelines")
    public ResponseEntity<List<CandidatePipeline>> getAllCandidatePipelines(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CandidatePipelines");
        Page<CandidatePipeline> page = candidatePipelineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidate-pipelines/:id} : get the "id" candidatePipeline.
     *
     * @param id the id of the candidatePipeline to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidatePipeline, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidate-pipelines/{id}")
    public ResponseEntity<CandidatePipeline> getCandidatePipeline(@PathVariable Long id) {
        log.debug("REST request to get CandidatePipeline : {}", id);
        Optional<CandidatePipeline> candidatePipeline = candidatePipelineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidatePipeline);
    }

    /**
     * {@code DELETE  /candidate-pipelines/:id} : delete the "id" candidatePipeline.
     *
     * @param id the id of the candidatePipeline to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidate-pipelines/{id}")
    public ResponseEntity<Void> deleteCandidatePipeline(@PathVariable Long id) {
        log.debug("REST request to delete CandidatePipeline : {}", id);
        candidatePipelineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
