package com.rp.web.rest;

import com.rp.domain.ClientDocument;
import com.rp.repository.ClientDocumentRepository;
import com.rp.service.ClientDocumentService;
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
 * REST controller for managing {@link com.rp.domain.ClientDocument}.
 */
@RestController
@RequestMapping("/api")
public class ClientDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ClientDocumentResource.class);

    private static final String ENTITY_NAME = "clientDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientDocumentService clientDocumentService;

    private final ClientDocumentRepository clientDocumentRepository;

    public ClientDocumentResource(ClientDocumentService clientDocumentService, ClientDocumentRepository clientDocumentRepository) {
        this.clientDocumentService = clientDocumentService;
        this.clientDocumentRepository = clientDocumentRepository;
    }

    /**
     * {@code POST  /client-documents} : Create a new clientDocument.
     *
     * @param clientDocument the clientDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientDocument, or with status {@code 400 (Bad Request)} if the clientDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-documents")
    public ResponseEntity<ClientDocument> createClientDocument(@RequestBody ClientDocument clientDocument) throws URISyntaxException {
        log.debug("REST request to save ClientDocument : {}", clientDocument);
        if (clientDocument.getId() != null) {
            throw new BadRequestAlertException("A new clientDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientDocument result = clientDocumentService.save(clientDocument);
        return ResponseEntity
            .created(new URI("/api/client-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-documents/:id} : Updates an existing clientDocument.
     *
     * @param id the id of the clientDocument to save.
     * @param clientDocument the clientDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDocument,
     * or with status {@code 400 (Bad Request)} if the clientDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-documents/{id}")
    public ResponseEntity<ClientDocument> updateClientDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientDocument clientDocument
    ) throws URISyntaxException {
        log.debug("REST request to update ClientDocument : {}, {}", id, clientDocument);
        if (clientDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientDocument result = clientDocumentService.update(clientDocument);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientDocument.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-documents/:id} : Partial updates given fields of an existing clientDocument, field will ignore if it is null
     *
     * @param id the id of the clientDocument to save.
     * @param clientDocument the clientDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDocument,
     * or with status {@code 400 (Bad Request)} if the clientDocument is not valid,
     * or with status {@code 404 (Not Found)} if the clientDocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientDocument> partialUpdateClientDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientDocument clientDocument
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientDocument partially : {}, {}", id, clientDocument);
        if (clientDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientDocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientDocument> result = clientDocumentService.partialUpdate(clientDocument);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientDocument.getId().toString())
        );
    }

    /**
     * {@code GET  /client-documents} : get all the clientDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientDocuments in body.
     */
    @GetMapping("/client-documents")
    public ResponseEntity<List<ClientDocument>> getAllClientDocuments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ClientDocuments");
        Page<ClientDocument> page = clientDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-documents/:id} : get the "id" clientDocument.
     *
     * @param id the id of the clientDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-documents/{id}")
    public ResponseEntity<ClientDocument> getClientDocument(@PathVariable Long id) {
        log.debug("REST request to get ClientDocument : {}", id);
        Optional<ClientDocument> clientDocument = clientDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientDocument);
    }

    /**
     * {@code DELETE  /client-documents/:id} : delete the "id" clientDocument.
     *
     * @param id the id of the clientDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-documents/{id}")
    public ResponseEntity<Void> deleteClientDocument(@PathVariable Long id) {
        log.debug("REST request to delete ClientDocument : {}", id);
        clientDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
