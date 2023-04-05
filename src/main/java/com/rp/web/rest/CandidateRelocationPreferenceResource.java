package com.rp.web.rest;

import com.rp.domain.CandidateRelocationPreference;
import com.rp.repository.CandidateRelocationPreferenceRepository;
import com.rp.service.CandidateRelocationPreferenceService;
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
 * REST controller for managing {@link com.rp.domain.CandidateRelocationPreference}.
 */
@RestController
@RequestMapping("/api")
public class CandidateRelocationPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(CandidateRelocationPreferenceResource.class);

    private static final String ENTITY_NAME = "candidateRelocationPreference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateRelocationPreferenceService candidateRelocationPreferenceService;

    private final CandidateRelocationPreferenceRepository candidateRelocationPreferenceRepository;

    public CandidateRelocationPreferenceResource(
        CandidateRelocationPreferenceService candidateRelocationPreferenceService,
        CandidateRelocationPreferenceRepository candidateRelocationPreferenceRepository
    ) {
        this.candidateRelocationPreferenceService = candidateRelocationPreferenceService;
        this.candidateRelocationPreferenceRepository = candidateRelocationPreferenceRepository;
    }

    /**
     * {@code POST  /candidate-relocation-preferences} : Create a new candidateRelocationPreference.
     *
     * @param candidateRelocationPreference the candidateRelocationPreference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateRelocationPreference, or with status {@code 400 (Bad Request)} if the candidateRelocationPreference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidate-relocation-preferences")
    public ResponseEntity<CandidateRelocationPreference> createCandidateRelocationPreference(
        @RequestBody CandidateRelocationPreference candidateRelocationPreference
    ) throws URISyntaxException {
        log.debug("REST request to save CandidateRelocationPreference : {}", candidateRelocationPreference);
        if (candidateRelocationPreference.getId() != null) {
            throw new BadRequestAlertException("A new candidateRelocationPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateRelocationPreference result = candidateRelocationPreferenceService.save(candidateRelocationPreference);
        return ResponseEntity
            .created(new URI("/api/candidate-relocation-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidate-relocation-preferences/:id} : Updates an existing candidateRelocationPreference.
     *
     * @param id the id of the candidateRelocationPreference to save.
     * @param candidateRelocationPreference the candidateRelocationPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateRelocationPreference,
     * or with status {@code 400 (Bad Request)} if the candidateRelocationPreference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateRelocationPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidate-relocation-preferences/{id}")
    public ResponseEntity<CandidateRelocationPreference> updateCandidateRelocationPreference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidateRelocationPreference candidateRelocationPreference
    ) throws URISyntaxException {
        log.debug("REST request to update CandidateRelocationPreference : {}, {}", id, candidateRelocationPreference);
        if (candidateRelocationPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidateRelocationPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidateRelocationPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CandidateRelocationPreference result = candidateRelocationPreferenceService.update(candidateRelocationPreference);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateRelocationPreference.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /candidate-relocation-preferences/:id} : Partial updates given fields of an existing candidateRelocationPreference, field will ignore if it is null
     *
     * @param id the id of the candidateRelocationPreference to save.
     * @param candidateRelocationPreference the candidateRelocationPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateRelocationPreference,
     * or with status {@code 400 (Bad Request)} if the candidateRelocationPreference is not valid,
     * or with status {@code 404 (Not Found)} if the candidateRelocationPreference is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidateRelocationPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/candidate-relocation-preferences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CandidateRelocationPreference> partialUpdateCandidateRelocationPreference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CandidateRelocationPreference candidateRelocationPreference
    ) throws URISyntaxException {
        log.debug("REST request to partial update CandidateRelocationPreference partially : {}, {}", id, candidateRelocationPreference);
        if (candidateRelocationPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, candidateRelocationPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!candidateRelocationPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CandidateRelocationPreference> result = candidateRelocationPreferenceService.partialUpdate(candidateRelocationPreference);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateRelocationPreference.getId().toString())
        );
    }

    /**
     * {@code GET  /candidate-relocation-preferences} : get all the candidateRelocationPreferences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidateRelocationPreferences in body.
     */
    @GetMapping("/candidate-relocation-preferences")
    public ResponseEntity<List<CandidateRelocationPreference>> getAllCandidateRelocationPreferences(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CandidateRelocationPreferences");
        Page<CandidateRelocationPreference> page = candidateRelocationPreferenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidate-relocation-preferences/:id} : get the "id" candidateRelocationPreference.
     *
     * @param id the id of the candidateRelocationPreference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateRelocationPreference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidate-relocation-preferences/{id}")
    public ResponseEntity<CandidateRelocationPreference> getCandidateRelocationPreference(@PathVariable Long id) {
        log.debug("REST request to get CandidateRelocationPreference : {}", id);
        Optional<CandidateRelocationPreference> candidateRelocationPreference = candidateRelocationPreferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidateRelocationPreference);
    }

    /**
     * {@code DELETE  /candidate-relocation-preferences/:id} : delete the "id" candidateRelocationPreference.
     *
     * @param id the id of the candidateRelocationPreference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidate-relocation-preferences/{id}")
    public ResponseEntity<Void> deleteCandidateRelocationPreference(@PathVariable Long id) {
        log.debug("REST request to delete CandidateRelocationPreference : {}", id);
        candidateRelocationPreferenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
