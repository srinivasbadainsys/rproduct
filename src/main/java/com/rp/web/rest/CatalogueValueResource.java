package com.rp.web.rest;

import com.rp.domain.CatalogueValue;
import com.rp.repository.CatalogueValueRepository;
import com.rp.service.CatalogueValueService;
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
 * REST controller for managing {@link com.rp.domain.CatalogueValue}.
 */
@RestController
@RequestMapping("/api")
public class CatalogueValueResource {

    private final Logger log = LoggerFactory.getLogger(CatalogueValueResource.class);

    private static final String ENTITY_NAME = "catalogueValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatalogueValueService catalogueValueService;

    private final CatalogueValueRepository catalogueValueRepository;

    public CatalogueValueResource(CatalogueValueService catalogueValueService, CatalogueValueRepository catalogueValueRepository) {
        this.catalogueValueService = catalogueValueService;
        this.catalogueValueRepository = catalogueValueRepository;
    }

    /**
     * {@code POST  /catalogue-values} : Create a new catalogueValue.
     *
     * @param catalogueValue the catalogueValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catalogueValue, or with status {@code 400 (Bad Request)} if the catalogueValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/catalogue-values")
    public ResponseEntity<CatalogueValue> createCatalogueValue(@RequestBody CatalogueValue catalogueValue) throws URISyntaxException {
        log.debug("REST request to save CatalogueValue : {}", catalogueValue);
        if (catalogueValue.getId() != null) {
            throw new BadRequestAlertException("A new catalogueValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatalogueValue result = catalogueValueService.save(catalogueValue);
        return ResponseEntity
            .created(new URI("/api/catalogue-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /catalogue-values/:id} : Updates an existing catalogueValue.
     *
     * @param id the id of the catalogueValue to save.
     * @param catalogueValue the catalogueValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogueValue,
     * or with status {@code 400 (Bad Request)} if the catalogueValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catalogueValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/catalogue-values/{id}")
    public ResponseEntity<CatalogueValue> updateCatalogueValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CatalogueValue catalogueValue
    ) throws URISyntaxException {
        log.debug("REST request to update CatalogueValue : {}, {}", id, catalogueValue);
        if (catalogueValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catalogueValue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!catalogueValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CatalogueValue result = catalogueValueService.update(catalogueValue);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catalogueValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /catalogue-values/:id} : Partial updates given fields of an existing catalogueValue, field will ignore if it is null
     *
     * @param id the id of the catalogueValue to save.
     * @param catalogueValue the catalogueValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catalogueValue,
     * or with status {@code 400 (Bad Request)} if the catalogueValue is not valid,
     * or with status {@code 404 (Not Found)} if the catalogueValue is not found,
     * or with status {@code 500 (Internal Server Error)} if the catalogueValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/catalogue-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CatalogueValue> partialUpdateCatalogueValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CatalogueValue catalogueValue
    ) throws URISyntaxException {
        log.debug("REST request to partial update CatalogueValue partially : {}, {}", id, catalogueValue);
        if (catalogueValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catalogueValue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!catalogueValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CatalogueValue> result = catalogueValueService.partialUpdate(catalogueValue);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catalogueValue.getId().toString())
        );
    }

    /**
     * {@code GET  /catalogue-values} : get all the catalogueValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catalogueValues in body.
     */
    @GetMapping("/catalogue-values")
    public ResponseEntity<List<CatalogueValue>> getAllCatalogueValues(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CatalogueValues");
        Page<CatalogueValue> page = catalogueValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /catalogue-values/:id} : get the "id" catalogueValue.
     *
     * @param id the id of the catalogueValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catalogueValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/catalogue-values/{id}")
    public ResponseEntity<CatalogueValue> getCatalogueValue(@PathVariable Long id) {
        log.debug("REST request to get CatalogueValue : {}", id);
        Optional<CatalogueValue> catalogueValue = catalogueValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catalogueValue);
    }

    /**
     * {@code DELETE  /catalogue-values/:id} : delete the "id" catalogueValue.
     *
     * @param id the id of the catalogueValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/catalogue-values/{id}")
    public ResponseEntity<Void> deleteCatalogueValue(@PathVariable Long id) {
        log.debug("REST request to delete CatalogueValue : {}", id);
        catalogueValueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
