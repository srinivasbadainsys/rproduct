package com.rp.web.rest;

import com.rp.domain.ClientGuidelineSubmissionDocument;
import com.rp.repository.ClientGuidelineSubmissionDocumentRepository;
import com.rp.service.ClientGuidelineSubmissionDocumentService;
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
 * REST controller for managing {@link com.rp.domain.ClientGuidelineSubmissionDocument}.
 */
@RestController
@RequestMapping("/api")
public class ClientGuidelineSubmissionDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ClientGuidelineSubmissionDocumentResource.class);

    private static final String ENTITY_NAME = "clientGuidelineSubmissionDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientGuidelineSubmissionDocumentService clientGuidelineSubmissionDocumentService;

    private final ClientGuidelineSubmissionDocumentRepository clientGuidelineSubmissionDocumentRepository;

    public ClientGuidelineSubmissionDocumentResource(
        ClientGuidelineSubmissionDocumentService clientGuidelineSubmissionDocumentService,
        ClientGuidelineSubmissionDocumentRepository clientGuidelineSubmissionDocumentRepository
    ) {
        this.clientGuidelineSubmissionDocumentService = clientGuidelineSubmissionDocumentService;
        this.clientGuidelineSubmissionDocumentRepository = clientGuidelineSubmissionDocumentRepository;
    }

    /**
     * {@code POST  /client-guideline-submission-documents} : Create a new clientGuidelineSubmissionDocument.
     *
     * @param clientGuidelineSubmissionDocument the clientGuidelineSubmissionDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientGuidelineSubmissionDocument, or with status {@code 400 (Bad Request)} if the clientGuidelineSubmissionDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-guideline-submission-documents")
    public ResponseEntity<ClientGuidelineSubmissionDocument> createClientGuidelineSubmissionDocument(
        @RequestBody ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument
    ) throws URISyntaxException {
        log.debug("REST request to save ClientGuidelineSubmissionDocument : {}", clientGuidelineSubmissionDocument);
        if (clientGuidelineSubmissionDocument.getId() != null) {
            throw new BadRequestAlertException(
                "A new clientGuidelineSubmissionDocument cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        ClientGuidelineSubmissionDocument result = clientGuidelineSubmissionDocumentService.save(clientGuidelineSubmissionDocument);
        return ResponseEntity
            .created(new URI("/api/client-guideline-submission-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-guideline-submission-documents/:id} : Updates an existing clientGuidelineSubmissionDocument.
     *
     * @param id the id of the clientGuidelineSubmissionDocument to save.
     * @param clientGuidelineSubmissionDocument the clientGuidelineSubmissionDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientGuidelineSubmissionDocument,
     * or with status {@code 400 (Bad Request)} if the clientGuidelineSubmissionDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientGuidelineSubmissionDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-guideline-submission-documents/{id}")
    public ResponseEntity<ClientGuidelineSubmissionDocument> updateClientGuidelineSubmissionDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument
    ) throws URISyntaxException {
        log.debug("REST request to update ClientGuidelineSubmissionDocument : {}, {}", id, clientGuidelineSubmissionDocument);
        if (clientGuidelineSubmissionDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientGuidelineSubmissionDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientGuidelineSubmissionDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientGuidelineSubmissionDocument result = clientGuidelineSubmissionDocumentService.update(clientGuidelineSubmissionDocument);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientGuidelineSubmissionDocument.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /client-guideline-submission-documents/:id} : Partial updates given fields of an existing clientGuidelineSubmissionDocument, field will ignore if it is null
     *
     * @param id the id of the clientGuidelineSubmissionDocument to save.
     * @param clientGuidelineSubmissionDocument the clientGuidelineSubmissionDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientGuidelineSubmissionDocument,
     * or with status {@code 400 (Bad Request)} if the clientGuidelineSubmissionDocument is not valid,
     * or with status {@code 404 (Not Found)} if the clientGuidelineSubmissionDocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientGuidelineSubmissionDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-guideline-submission-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientGuidelineSubmissionDocument> partialUpdateClientGuidelineSubmissionDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ClientGuidelineSubmissionDocument partially : {}, {}",
            id,
            clientGuidelineSubmissionDocument
        );
        if (clientGuidelineSubmissionDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientGuidelineSubmissionDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientGuidelineSubmissionDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientGuidelineSubmissionDocument> result = clientGuidelineSubmissionDocumentService.partialUpdate(
            clientGuidelineSubmissionDocument
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientGuidelineSubmissionDocument.getId().toString())
        );
    }

    /**
     * {@code GET  /client-guideline-submission-documents} : get all the clientGuidelineSubmissionDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientGuidelineSubmissionDocuments in body.
     */
    @GetMapping("/client-guideline-submission-documents")
    public ResponseEntity<List<ClientGuidelineSubmissionDocument>> getAllClientGuidelineSubmissionDocuments(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ClientGuidelineSubmissionDocuments");
        Page<ClientGuidelineSubmissionDocument> page = clientGuidelineSubmissionDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-guideline-submission-documents/:id} : get the "id" clientGuidelineSubmissionDocument.
     *
     * @param id the id of the clientGuidelineSubmissionDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientGuidelineSubmissionDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-guideline-submission-documents/{id}")
    public ResponseEntity<ClientGuidelineSubmissionDocument> getClientGuidelineSubmissionDocument(@PathVariable Long id) {
        log.debug("REST request to get ClientGuidelineSubmissionDocument : {}", id);
        Optional<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocument = clientGuidelineSubmissionDocumentService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(clientGuidelineSubmissionDocument);
    }

    /**
     * {@code DELETE  /client-guideline-submission-documents/:id} : delete the "id" clientGuidelineSubmissionDocument.
     *
     * @param id the id of the clientGuidelineSubmissionDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-guideline-submission-documents/{id}")
    public ResponseEntity<Void> deleteClientGuidelineSubmissionDocument(@PathVariable Long id) {
        log.debug("REST request to delete ClientGuidelineSubmissionDocument : {}", id);
        clientGuidelineSubmissionDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
