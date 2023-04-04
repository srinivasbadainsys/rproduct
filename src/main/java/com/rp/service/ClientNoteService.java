package com.rp.service;

import com.rp.domain.ClientNote;
import com.rp.repository.ClientNoteRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientNote}.
 */
@Service
@Transactional
public class ClientNoteService {

    private final Logger log = LoggerFactory.getLogger(ClientNoteService.class);

    private final ClientNoteRepository clientNoteRepository;

    public ClientNoteService(ClientNoteRepository clientNoteRepository) {
        this.clientNoteRepository = clientNoteRepository;
    }

    /**
     * Save a clientNote.
     *
     * @param clientNote the entity to save.
     * @return the persisted entity.
     */
    public ClientNote save(ClientNote clientNote) {
        log.debug("Request to save ClientNote : {}", clientNote);
        return clientNoteRepository.save(clientNote);
    }

    /**
     * Update a clientNote.
     *
     * @param clientNote the entity to save.
     * @return the persisted entity.
     */
    public ClientNote update(ClientNote clientNote) {
        log.debug("Request to update ClientNote : {}", clientNote);
        return clientNoteRepository.save(clientNote);
    }

    /**
     * Partially update a clientNote.
     *
     * @param clientNote the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClientNote> partialUpdate(ClientNote clientNote) {
        log.debug("Request to partially update ClientNote : {}", clientNote);

        return clientNoteRepository
            .findById(clientNote.getId())
            .map(existingClientNote -> {
                if (clientNote.getClientId() != null) {
                    existingClientNote.setClientId(clientNote.getClientId());
                }
                if (clientNote.getAction() != null) {
                    existingClientNote.setAction(clientNote.getAction());
                }
                if (clientNote.getNotesPriority() != null) {
                    existingClientNote.setNotesPriority(clientNote.getNotesPriority());
                }
                if (clientNote.getNote() != null) {
                    existingClientNote.setNote(clientNote.getNote());
                }
                if (clientNote.getNotifyToUsers() != null) {
                    existingClientNote.setNotifyToUsers(clientNote.getNotifyToUsers());
                }
                if (clientNote.getRemindMe() != null) {
                    existingClientNote.setRemindMe(clientNote.getRemindMe());
                }

                return existingClientNote;
            })
            .map(clientNoteRepository::save);
    }

    /**
     * Get all the clientNotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientNote> findAll(Pageable pageable) {
        log.debug("Request to get all ClientNotes");
        return clientNoteRepository.findAll(pageable);
    }

    /**
     * Get one clientNote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientNote> findOne(Long id) {
        log.debug("Request to get ClientNote : {}", id);
        return clientNoteRepository.findById(id);
    }

    /**
     * Delete the clientNote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientNote : {}", id);
        clientNoteRepository.deleteById(id);
    }
}
