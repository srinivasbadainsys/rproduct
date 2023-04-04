package com.rp.service;

import com.rp.domain.ClientGuidelineSubmissionDocument;
import com.rp.repository.ClientGuidelineSubmissionDocumentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientGuidelineSubmissionDocument}.
 */
@Service
@Transactional
public class ClientGuidelineSubmissionDocumentService {

    private final Logger log = LoggerFactory.getLogger(ClientGuidelineSubmissionDocumentService.class);

    private final ClientGuidelineSubmissionDocumentRepository clientGuidelineSubmissionDocumentRepository;

    public ClientGuidelineSubmissionDocumentService(
        ClientGuidelineSubmissionDocumentRepository clientGuidelineSubmissionDocumentRepository
    ) {
        this.clientGuidelineSubmissionDocumentRepository = clientGuidelineSubmissionDocumentRepository;
    }

    /**
     * Save a clientGuidelineSubmissionDocument.
     *
     * @param clientGuidelineSubmissionDocument the entity to save.
     * @return the persisted entity.
     */
    public ClientGuidelineSubmissionDocument save(ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument) {
        log.debug("Request to save ClientGuidelineSubmissionDocument : {}", clientGuidelineSubmissionDocument);
        return clientGuidelineSubmissionDocumentRepository.save(clientGuidelineSubmissionDocument);
    }

    /**
     * Update a clientGuidelineSubmissionDocument.
     *
     * @param clientGuidelineSubmissionDocument the entity to save.
     * @return the persisted entity.
     */
    public ClientGuidelineSubmissionDocument update(ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument) {
        log.debug("Request to update ClientGuidelineSubmissionDocument : {}", clientGuidelineSubmissionDocument);
        return clientGuidelineSubmissionDocumentRepository.save(clientGuidelineSubmissionDocument);
    }

    /**
     * Partially update a clientGuidelineSubmissionDocument.
     *
     * @param clientGuidelineSubmissionDocument the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClientGuidelineSubmissionDocument> partialUpdate(ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument) {
        log.debug("Request to partially update ClientGuidelineSubmissionDocument : {}", clientGuidelineSubmissionDocument);

        return clientGuidelineSubmissionDocumentRepository
            .findById(clientGuidelineSubmissionDocument.getId())
            .map(existingClientGuidelineSubmissionDocument -> {
                if (clientGuidelineSubmissionDocument.getClientId() != null) {
                    existingClientGuidelineSubmissionDocument.setClientId(clientGuidelineSubmissionDocument.getClientId());
                }
                if (clientGuidelineSubmissionDocument.getDocumentTitle() != null) {
                    existingClientGuidelineSubmissionDocument.setDocumentTitle(clientGuidelineSubmissionDocument.getDocumentTitle());
                }
                if (clientGuidelineSubmissionDocument.getDescription() != null) {
                    existingClientGuidelineSubmissionDocument.setDescription(clientGuidelineSubmissionDocument.getDescription());
                }
                if (clientGuidelineSubmissionDocument.getDocumentPath() != null) {
                    existingClientGuidelineSubmissionDocument.setDocumentPath(clientGuidelineSubmissionDocument.getDocumentPath());
                }

                return existingClientGuidelineSubmissionDocument;
            })
            .map(clientGuidelineSubmissionDocumentRepository::save);
    }

    /**
     * Get all the clientGuidelineSubmissionDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientGuidelineSubmissionDocument> findAll(Pageable pageable) {
        log.debug("Request to get all ClientGuidelineSubmissionDocuments");
        return clientGuidelineSubmissionDocumentRepository.findAll(pageable);
    }

    /**
     * Get one clientGuidelineSubmissionDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientGuidelineSubmissionDocument> findOne(Long id) {
        log.debug("Request to get ClientGuidelineSubmissionDocument : {}", id);
        return clientGuidelineSubmissionDocumentRepository.findById(id);
    }

    /**
     * Delete the clientGuidelineSubmissionDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientGuidelineSubmissionDocument : {}", id);
        clientGuidelineSubmissionDocumentRepository.deleteById(id);
    }
}
