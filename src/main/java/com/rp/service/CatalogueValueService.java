package com.rp.service;

import com.rp.domain.CatalogueValue;
import com.rp.repository.CatalogueValueRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CatalogueValue}.
 */
@Service
@Transactional
public class CatalogueValueService {

    private final Logger log = LoggerFactory.getLogger(CatalogueValueService.class);

    private final CatalogueValueRepository catalogueValueRepository;

    public CatalogueValueService(CatalogueValueRepository catalogueValueRepository) {
        this.catalogueValueRepository = catalogueValueRepository;
    }

    /**
     * Save a catalogueValue.
     *
     * @param catalogueValue the entity to save.
     * @return the persisted entity.
     */
    public CatalogueValue save(CatalogueValue catalogueValue) {
        log.debug("Request to save CatalogueValue : {}", catalogueValue);
        return catalogueValueRepository.save(catalogueValue);
    }

    /**
     * Update a catalogueValue.
     *
     * @param catalogueValue the entity to save.
     * @return the persisted entity.
     */
    public CatalogueValue update(CatalogueValue catalogueValue) {
        log.debug("Request to update CatalogueValue : {}", catalogueValue);
        // no save call needed as we have no fields that can be updated
        return catalogueValue;
    }

    /**
     * Partially update a catalogueValue.
     *
     * @param catalogueValue the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CatalogueValue> partialUpdate(CatalogueValue catalogueValue) {
        log.debug("Request to partially update CatalogueValue : {}", catalogueValue);

        return catalogueValueRepository
            .findById(catalogueValue.getId())
            .map(existingCatalogueValue -> {
                return existingCatalogueValue;
            })// .map(catalogueValueRepository::save)
        ;
    }

    /**
     * Get all the catalogueValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatalogueValue> findAll(Pageable pageable) {
        log.debug("Request to get all CatalogueValues");
        return catalogueValueRepository.findAll(pageable);
    }

    /**
     * Get one catalogueValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatalogueValue> findOne(Long id) {
        log.debug("Request to get CatalogueValue : {}", id);
        return catalogueValueRepository.findById(id);
    }

    /**
     * Delete the catalogueValue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CatalogueValue : {}", id);
        catalogueValueRepository.deleteById(id);
    }
}
