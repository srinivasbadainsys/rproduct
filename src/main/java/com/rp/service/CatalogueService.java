package com.rp.service;

import com.rp.domain.Catalogue;
import com.rp.repository.CatalogueRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Catalogue}.
 */
@Service
@Transactional
public class CatalogueService {

    private final Logger log = LoggerFactory.getLogger(CatalogueService.class);

    private final CatalogueRepository catalogueRepository;

    public CatalogueService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    /**
     * Save a catalogue.
     *
     * @param catalogue the entity to save.
     * @return the persisted entity.
     */
    public Catalogue save(Catalogue catalogue) {
        log.debug("Request to save Catalogue : {}", catalogue);
        return catalogueRepository.save(catalogue);
    }

    /**
     * Update a catalogue.
     *
     * @param catalogue the entity to save.
     * @return the persisted entity.
     */
    public Catalogue update(Catalogue catalogue) {
        log.debug("Request to update Catalogue : {}", catalogue);
        return catalogueRepository.save(catalogue);
    }

    /**
     * Partially update a catalogue.
     *
     * @param catalogue the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Catalogue> partialUpdate(Catalogue catalogue) {
        log.debug("Request to partially update Catalogue : {}", catalogue);

        return catalogueRepository
            .findById(catalogue.getId())
            .map(existingCatalogue -> {
                if (catalogue.getType() != null) {
                    existingCatalogue.setType(catalogue.getType());
                }
                if (catalogue.getTypeCode() != null) {
                    existingCatalogue.setTypeCode(catalogue.getTypeCode());
                }

                return existingCatalogue;
            })
            .map(catalogueRepository::save);
    }

    /**
     * Get all the catalogues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Catalogue> findAll(Pageable pageable) {
        log.debug("Request to get all Catalogues");
        return catalogueRepository.findAll(pageable);
    }

    /**
     * Get one catalogue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Catalogue> findOne(Long id) {
        log.debug("Request to get Catalogue : {}", id);
        return catalogueRepository.findById(id);
    }

    /**
     * Delete the catalogue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Catalogue : {}", id);
        catalogueRepository.deleteById(id);
    }
}
