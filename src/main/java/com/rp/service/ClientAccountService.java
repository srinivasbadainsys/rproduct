package com.rp.service;

import com.rp.domain.ClientAccount;
import com.rp.repository.ClientAccountRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientAccount}.
 */
@Service
@Transactional
public class ClientAccountService {

    private final Logger log = LoggerFactory.getLogger(ClientAccountService.class);

    private final ClientAccountRepository clientAccountRepository;

    public ClientAccountService(ClientAccountRepository clientAccountRepository) {
        this.clientAccountRepository = clientAccountRepository;
    }

    /**
     * Save a clientAccount.
     *
     * @param clientAccount the entity to save.
     * @return the persisted entity.
     */
    public ClientAccount save(ClientAccount clientAccount) {
        log.debug("Request to save ClientAccount : {}", clientAccount);
        return clientAccountRepository.save(clientAccount);
    }

    /**
     * Update a clientAccount.
     *
     * @param clientAccount the entity to save.
     * @return the persisted entity.
     */
    public ClientAccount update(ClientAccount clientAccount) {
        log.debug("Request to update ClientAccount : {}", clientAccount);
        return clientAccountRepository.save(clientAccount);
    }

    /**
     * Partially update a clientAccount.
     *
     * @param clientAccount the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClientAccount> partialUpdate(ClientAccount clientAccount) {
        log.debug("Request to partially update ClientAccount : {}", clientAccount);

        return clientAccountRepository
            .findById(clientAccount.getId())
            .map(existingClientAccount -> {
                if (clientAccount.getClientId() != null) {
                    existingClientAccount.setClientId(clientAccount.getClientId());
                }
                if (clientAccount.getContactPerson() != null) {
                    existingClientAccount.setContactPerson(clientAccount.getContactPerson());
                }
                if (clientAccount.getMobileNumber() != null) {
                    existingClientAccount.setMobileNumber(clientAccount.getMobileNumber());
                }
                if (clientAccount.getOfficeNumber() != null) {
                    existingClientAccount.setOfficeNumber(clientAccount.getOfficeNumber());
                }
                if (clientAccount.getOfficeNumberExtn() != null) {
                    existingClientAccount.setOfficeNumberExtn(clientAccount.getOfficeNumberExtn());
                }
                if (clientAccount.getEmailID() != null) {
                    existingClientAccount.setEmailID(clientAccount.getEmailID());
                }
                if (clientAccount.getDesignation() != null) {
                    existingClientAccount.setDesignation(clientAccount.getDesignation());
                }

                return existingClientAccount;
            })
            .map(clientAccountRepository::save);
    }

    /**
     * Get all the clientAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientAccount> findAll(Pageable pageable) {
        log.debug("Request to get all ClientAccounts");
        return clientAccountRepository.findAll(pageable);
    }

    /**
     * Get one clientAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientAccount> findOne(Long id) {
        log.debug("Request to get ClientAccount : {}", id);
        return clientAccountRepository.findById(id);
    }

    /**
     * Delete the clientAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientAccount : {}", id);
        clientAccountRepository.deleteById(id);
    }
}
