package com.rp.web.rest;

import com.rp.domain.Ruser;
import com.rp.repository.RuserRepository;
import com.rp.service.RuserService;
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
 * REST controller for managing {@link com.rp.domain.Ruser}.
 */
@RestController
@RequestMapping("/api")
public class RuserResource {

    private final Logger log = LoggerFactory.getLogger(RuserResource.class);

    private static final String ENTITY_NAME = "ruser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuserService ruserService;

    private final RuserRepository ruserRepository;

    public RuserResource(RuserService ruserService, RuserRepository ruserRepository) {
        this.ruserService = ruserService;
        this.ruserRepository = ruserRepository;
    }

    /**
     * {@code POST  /rusers} : Create a new ruser.
     *
     * @param ruser the ruser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruser, or with status {@code 400 (Bad Request)} if the ruser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rusers")
    public ResponseEntity<Ruser> createRuser(@RequestBody Ruser ruser) throws URISyntaxException {
        log.debug("REST request to save Ruser : {}", ruser);
        if (ruser.getId() != null) {
            throw new BadRequestAlertException("A new ruser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ruser result = ruserService.save(ruser);
        return ResponseEntity
            .created(new URI("/api/rusers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rusers/:id} : Updates an existing ruser.
     *
     * @param id the id of the ruser to save.
     * @param ruser the ruser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruser,
     * or with status {@code 400 (Bad Request)} if the ruser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rusers/{id}")
    public ResponseEntity<Ruser> updateRuser(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ruser ruser)
        throws URISyntaxException {
        log.debug("REST request to update Ruser : {}, {}", id, ruser);
        if (ruser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ruser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ruserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ruser result = ruserService.update(ruser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ruser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rusers/:id} : Partial updates given fields of an existing ruser, field will ignore if it is null
     *
     * @param id the id of the ruser to save.
     * @param ruser the ruser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruser,
     * or with status {@code 400 (Bad Request)} if the ruser is not valid,
     * or with status {@code 404 (Not Found)} if the ruser is not found,
     * or with status {@code 500 (Internal Server Error)} if the ruser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rusers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ruser> partialUpdateRuser(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ruser ruser)
        throws URISyntaxException {
        log.debug("REST request to partial update Ruser partially : {}, {}", id, ruser);
        if (ruser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ruser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ruserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ruser> result = ruserService.partialUpdate(ruser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ruser.getId().toString())
        );
    }

    /**
     * {@code GET  /rusers} : get all the rusers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rusers in body.
     */
    @GetMapping("/rusers")
    public ResponseEntity<List<Ruser>> getAllRusers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Rusers");
        Page<Ruser> page = ruserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rusers/:id} : get the "id" ruser.
     *
     * @param id the id of the ruser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rusers/{id}")
    public ResponseEntity<Ruser> getRuser(@PathVariable Long id) {
        log.debug("REST request to get Ruser : {}", id);
        Optional<Ruser> ruser = ruserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruser);
    }

    /**
     * {@code DELETE  /rusers/:id} : delete the "id" ruser.
     *
     * @param id the id of the ruser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rusers/{id}")
    public ResponseEntity<Void> deleteRuser(@PathVariable Long id) {
        log.debug("REST request to delete Ruser : {}", id);
        ruserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
