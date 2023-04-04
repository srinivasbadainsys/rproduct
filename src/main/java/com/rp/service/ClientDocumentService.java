package com.rp.service;

import com.rp.domain.ClientDocument;
import com.rp.repository.ClientDocumentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientDocument}.
 */
@Service
@Transactional
public class ClientDocumentService {

    private final Logger log = LoggerFactory.getLogger(ClientDocumentService.class);

    private final ClientDocumentRepository clientDocumentRepository;

    public ClientDocumentService(ClientDocumentRepository clientDocumentRepository) {
        this.clientDocumentRepository = clientDocumentRepository;
    }

    /**
     * Save a clientDocument.
     *
     * @param clientDocument the entity to save.
     * @return the persisted entity.
     */
    public ClientDocument save(ClientDocument clientDocument) {
        log.debug("Request to save ClientDocument : {}", clientDocument);
        return clientDocumentRepository.save(clientDocument);
    }

    /**
     * Update a clientDocument.
     *
     * @param clientDocument the entity to save.
     * @return the persisted entity.
     */
    public ClientDocument update(ClientDocument clientDocument) {
        log.debug("Request to update ClientDocument : {}", clientDocument);
        return clientDocumentRepository.save(clientDocument);
    }

    /**
     * Partially update a clientDocument.
     *
     * @param clientDocument the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClientDocument> partialUpdate(ClientDocument clientDocument) {
        log.debug("Request to partially update ClientDocument : {}", clientDocument);

        return clientDocumentRepository
            .findById(clientDocument.getId())
            .map(existingClientDocument -> {
                if (clientDocument.getClientId() != null) {
                    existingClientDocument.setClientId(clientDocument.getClientId());
                }
                if (clientDocument.getDocumentType() != null) {
                    existingClientDocument.setDocumentType(clientDocument.getDocumentType());
                }
                if (clientDocument.getTitle() != null) {
                    existingClientDocument.setTitle(clientDocument.getTitle());
                }
                if (clientDocument.getDescription() != null) {
                    existingClientDocument.setDescription(clientDocument.getDescription());
                }
                if (clientDocument.getDocumentPath() != null) {
                    existingClientDocument.setDocumentPath(clientDocument.getDocumentPath());
                }

                return existingClientDocument;
            })
            .map(clientDocumentRepository::save);
    }

    /**
     * Get all the clientDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDocument> findAll(Pageable pageable) {
        log.debug("Request to get all ClientDocuments");
        return clientDocumentRepository.findAll(pageable);
    }

    /**
     * Get one clientDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientDocument> findOne(Long id) {
        log.debug("Request to get ClientDocument : {}", id);
        return clientDocumentRepository.findById(id);
    }

    /**
     * Delete the clientDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientDocument : {}", id);
        clientDocumentRepository.deleteById(id);
    }
}
