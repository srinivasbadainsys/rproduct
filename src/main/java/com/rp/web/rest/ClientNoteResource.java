package com.rp.web.rest;

import com.rp.domain.ClientNote;
import com.rp.repository.ClientNoteRepository;
import com.rp.service.ClientNoteService;
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
 * REST controller for managing {@link com.rp.domain.ClientNote}.
 */
@RestController
@RequestMapping("/api")
public class ClientNoteResource {

    private final Logger log = LoggerFactory.getLogger(ClientNoteResource.class);

    private static final String ENTITY_NAME = "clientNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientNoteService clientNoteService;

    private final ClientNoteRepository clientNoteRepository;

    public ClientNoteResource(ClientNoteService clientNoteService, ClientNoteRepository clientNoteRepository) {
        this.clientNoteService = clientNoteService;
        this.clientNoteRepository = clientNoteRepository;
    }

    /**
     * {@code POST  /client-notes} : Create a new clientNote.
     *
     * @param clientNote the clientNote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientNote, or with status {@code 400 (Bad Request)} if the clientNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-notes")
    public ResponseEntity<ClientNote> createClientNote(@RequestBody ClientNote clientNote) throws URISyntaxException {
        log.debug("REST request to save ClientNote : {}", clientNote);
        if (clientNote.getId() != null) {
            throw new BadRequestAlertException("A new clientNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientNote result = clientNoteService.save(clientNote);
        return ResponseEntity
            .created(new URI("/api/client-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-notes/:id} : Updates an existing clientNote.
     *
     * @param id the id of the clientNote to save.
     * @param clientNote the clientNote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientNote,
     * or with status {@code 400 (Bad Request)} if the clientNote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientNote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-notes/{id}")
    public ResponseEntity<ClientNote> updateClientNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientNote clientNote
    ) throws URISyntaxException {
        log.debug("REST request to update ClientNote : {}, {}", id, clientNote);
        if (clientNote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientNote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientNote result = clientNoteService.update(clientNote);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientNote.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-notes/:id} : Partial updates given fields of an existing clientNote, field will ignore if it is null
     *
     * @param id the id of the clientNote to save.
     * @param clientNote the clientNote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientNote,
     * or with status {@code 400 (Bad Request)} if the clientNote is not valid,
     * or with status {@code 404 (Not Found)} if the clientNote is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientNote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-notes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientNote> partialUpdateClientNote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientNote clientNote
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientNote partially : {}, {}", id, clientNote);
        if (clientNote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientNote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientNote> result = clientNoteService.partialUpdate(clientNote);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientNote.getId().toString())
        );
    }

    /**
     * {@code GET  /client-notes} : get all the clientNotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientNotes in body.
     */
    @GetMapping("/client-notes")
    public ResponseEntity<List<ClientNote>> getAllClientNotes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ClientNotes");
        Page<ClientNote> page = clientNoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-notes/:id} : get the "id" clientNote.
     *
     * @param id the id of the clientNote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientNote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-notes/{id}")
    public ResponseEntity<ClientNote> getClientNote(@PathVariable Long id) {
        log.debug("REST request to get ClientNote : {}", id);
        Optional<ClientNote> clientNote = clientNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientNote);
    }

    /**
     * {@code DELETE  /client-notes/:id} : delete the "id" clientNote.
     *
     * @param id the id of the clientNote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-notes/{id}")
    public ResponseEntity<Void> deleteClientNote(@PathVariable Long id) {
        log.debug("REST request to delete ClientNote : {}", id);
        clientNoteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
