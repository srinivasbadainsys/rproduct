package com.rp.service;

import com.rp.domain.Contact;
import com.rp.repository.ContactRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contact}.
 */
@Service
@Transactional
public class ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Save a contact.
     *
     * @param contact the entity to save.
     * @return the persisted entity.
     */
    public Contact save(Contact contact) {
        log.debug("Request to save Contact : {}", contact);
        return contactRepository.save(contact);
    }

    /**
     * Update a contact.
     *
     * @param contact the entity to save.
     * @return the persisted entity.
     */
    public Contact update(Contact contact) {
        log.debug("Request to update Contact : {}", contact);
        return contactRepository.save(contact);
    }

    /**
     * Partially update a contact.
     *
     * @param contact the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Contact> partialUpdate(Contact contact) {
        log.debug("Request to partially update Contact : {}", contact);

        return contactRepository
            .findById(contact.getId())
            .map(existingContact -> {
                if (contact.getClientId() != null) {
                    existingContact.setClientId(contact.getClientId());
                }
                if (contact.getFirstName() != null) {
                    existingContact.setFirstName(contact.getFirstName());
                }
                if (contact.getLastName() != null) {
                    existingContact.setLastName(contact.getLastName());
                }
                if (contact.getDesignation() != null) {
                    existingContact.setDesignation(contact.getDesignation());
                }
                if (contact.getOfficeNumber() != null) {
                    existingContact.setOfficeNumber(contact.getOfficeNumber());
                }
                if (contact.getOfficeNumberExtn() != null) {
                    existingContact.setOfficeNumberExtn(contact.getOfficeNumberExtn());
                }
                if (contact.getMobileNumber() != null) {
                    existingContact.setMobileNumber(contact.getMobileNumber());
                }
                if (contact.getEmailID() != null) {
                    existingContact.setEmailID(contact.getEmailID());
                }
                if (contact.getAltEmailID() != null) {
                    existingContact.setAltEmailID(contact.getAltEmailID());
                }
                if (contact.getOwnershipIds() != null) {
                    existingContact.setOwnershipIds(contact.getOwnershipIds());
                }
                if (contact.getAllowAccessToAllUsers() != null) {
                    existingContact.setAllowAccessToAllUsers(contact.getAllowAccessToAllUsers());
                }
                if (contact.getAddress1() != null) {
                    existingContact.setAddress1(contact.getAddress1());
                }
                if (contact.getAddress2() != null) {
                    existingContact.setAddress2(contact.getAddress2());
                }
                if (contact.getArea() != null) {
                    existingContact.setArea(contact.getArea());
                }
                if (contact.getCity() != null) {
                    existingContact.setCity(contact.getCity());
                }
                if (contact.getState() != null) {
                    existingContact.setState(contact.getState());
                }
                if (contact.getStateCode() != null) {
                    existingContact.setStateCode(contact.getStateCode());
                }
                if (contact.getCounty() != null) {
                    existingContact.setCounty(contact.getCounty());
                }
                if (contact.getCountry() != null) {
                    existingContact.setCountry(contact.getCountry());
                }
                if (contact.getCountryCode() != null) {
                    existingContact.setCountryCode(contact.getCountryCode());
                }
                if (contact.getZipCode() != null) {
                    existingContact.setZipCode(contact.getZipCode());
                }
                if (contact.getProfileURLs() != null) {
                    existingContact.setProfileURLs(contact.getProfileURLs());
                }
                if (contact.getMessengerIDs() != null) {
                    existingContact.setMessengerIDs(contact.getMessengerIDs());
                }
                if (contact.getStatus() != null) {
                    existingContact.setStatus(contact.getStatus());
                }
                if (contact.getClientGroup() != null) {
                    existingContact.setClientGroup(contact.getClientGroup());
                }
                if (contact.getAbout() != null) {
                    existingContact.setAbout(contact.getAbout());
                }

                return existingContact;
            })
            .map(contactRepository::save);
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Contact> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll(pageable);
    }

    /**
     * Get one contact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Contact> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id);
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}
