package com.rp.web.rest;

import com.rp.domain.ClientAccount;
import com.rp.repository.ClientAccountRepository;
import com.rp.service.ClientAccountService;
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
 * REST controller for managing {@link com.rp.domain.ClientAccount}.
 */
@RestController
@RequestMapping("/api")
public class ClientAccountResource {

    private final Logger log = LoggerFactory.getLogger(ClientAccountResource.class);

    private static final String ENTITY_NAME = "clientAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientAccountService clientAccountService;

    private final ClientAccountRepository clientAccountRepository;

    public ClientAccountResource(ClientAccountService clientAccountService, ClientAccountRepository clientAccountRepository) {
        this.clientAccountService = clientAccountService;
        this.clientAccountRepository = clientAccountRepository;
    }

    /**
     * {@code POST  /client-accounts} : Create a new clientAccount.
     *
     * @param clientAccount the clientAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientAccount, or with status {@code 400 (Bad Request)} if the clientAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-accounts")
    public ResponseEntity<ClientAccount> createClientAccount(@RequestBody ClientAccount clientAccount) throws URISyntaxException {
        log.debug("REST request to save ClientAccount : {}", clientAccount);
        if (clientAccount.getId() != null) {
            throw new BadRequestAlertException("A new clientAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientAccount result = clientAccountService.save(clientAccount);
        return ResponseEntity
            .created(new URI("/api/client-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-accounts/:id} : Updates an existing clientAccount.
     *
     * @param id the id of the clientAccount to save.
     * @param clientAccount the clientAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientAccount,
     * or with status {@code 400 (Bad Request)} if the clientAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-accounts/{id}")
    public ResponseEntity<ClientAccount> updateClientAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientAccount clientAccount
    ) throws URISyntaxException {
        log.debug("REST request to update ClientAccount : {}, {}", id, clientAccount);
        if (clientAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientAccount result = clientAccountService.update(clientAccount);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-accounts/:id} : Partial updates given fields of an existing clientAccount, field will ignore if it is null
     *
     * @param id the id of the clientAccount to save.
     * @param clientAccount the clientAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientAccount,
     * or with status {@code 400 (Bad Request)} if the clientAccount is not valid,
     * or with status {@code 404 (Not Found)} if the clientAccount is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientAccount> partialUpdateClientAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientAccount clientAccount
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientAccount partially : {}, {}", id, clientAccount);
        if (clientAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientAccount> result = clientAccountService.partialUpdate(clientAccount);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientAccount.getId().toString())
        );
    }

    /**
     * {@code GET  /client-accounts} : get all the clientAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientAccounts in body.
     */
    @GetMapping("/client-accounts")
    public ResponseEntity<List<ClientAccount>> getAllClientAccounts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ClientAccounts");
        Page<ClientAccount> page = clientAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-accounts/:id} : get the "id" clientAccount.
     *
     * @param id the id of the clientAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-accounts/{id}")
    public ResponseEntity<ClientAccount> getClientAccount(@PathVariable Long id) {
        log.debug("REST request to get ClientAccount : {}", id);
        Optional<ClientAccount> clientAccount = clientAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientAccount);
    }

    /**
     * {@code DELETE  /client-accounts/:id} : delete the "id" clientAccount.
     *
     * @param id the id of the clientAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-accounts/{id}")
    public ResponseEntity<Void> deleteClientAccount(@PathVariable Long id) {
        log.debug("REST request to delete ClientAccount : {}", id);
        clientAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
