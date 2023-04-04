package com.rp.service;

import com.rp.domain.Client;
import com.rp.repository.ClientRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Save a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    /**
     * Update a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    public Client update(Client client) {
        log.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    /**
     * Partially update a client.
     *
     * @param client the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getClientVisibility() != null) {
                    existingClient.setClientVisibility(client.getClientVisibility());
                }
                if (client.getBusinessUnitIds() != null) {
                    existingClient.setBusinessUnitIds(client.getBusinessUnitIds());
                }
                if (client.getClientName() != null) {
                    existingClient.setClientName(client.getClientName());
                }
                if (client.getVmsClientName() != null) {
                    existingClient.setVmsClientName(client.getVmsClientName());
                }
                if (client.getFederalID() != null) {
                    existingClient.setFederalID(client.getFederalID());
                }
                if (client.getContactNumber() != null) {
                    existingClient.setContactNumber(client.getContactNumber());
                }
                if (client.getEmail() != null) {
                    existingClient.setEmail(client.getEmail());
                }
                if (client.getAddress() != null) {
                    existingClient.setAddress(client.getAddress());
                }
                if (client.getArea() != null) {
                    existingClient.setArea(client.getArea());
                }
                if (client.getCity() != null) {
                    existingClient.setCity(client.getCity());
                }
                if (client.getState() != null) {
                    existingClient.setState(client.getState());
                }
                if (client.getStateCode() != null) {
                    existingClient.setStateCode(client.getStateCode());
                }
                if (client.getCounty() != null) {
                    existingClient.setCounty(client.getCounty());
                }
                if (client.getCountry() != null) {
                    existingClient.setCountry(client.getCountry());
                }
                if (client.getCountryCode() != null) {
                    existingClient.setCountryCode(client.getCountryCode());
                }
                if (client.getZipCode() != null) {
                    existingClient.setZipCode(client.getZipCode());
                }
                if (client.getWebsite() != null) {
                    existingClient.setWebsite(client.getWebsite());
                }
                if (client.getSendRequirement() != null) {
                    existingClient.setSendRequirement(client.getSendRequirement());
                }
                if (client.getSendHotList() != null) {
                    existingClient.setSendHotList(client.getSendHotList());
                }
                if (client.getFax() != null) {
                    existingClient.setFax(client.getFax());
                }
                if (client.getStatus() != null) {
                    existingClient.setStatus(client.getStatus());
                }
                if (client.getAllowAccessToAllUsers() != null) {
                    existingClient.setAllowAccessToAllUsers(client.getAllowAccessToAllUsers());
                }
                if (client.getOwnershipIds() != null) {
                    existingClient.setOwnershipIds(client.getOwnershipIds());
                }
                if (client.getClientLeadIds() != null) {
                    existingClient.setClientLeadIds(client.getClientLeadIds());
                }
                if (client.getRequiredDocuments() != null) {
                    existingClient.setRequiredDocuments(client.getRequiredDocuments());
                }
                if (client.getPracticeAlt() != null) {
                    existingClient.setPracticeAlt(client.getPracticeAlt());
                }
                if (client.getAboutCompany() != null) {
                    existingClient.setAboutCompany(client.getAboutCompany());
                }
                if (client.getStopNotifyingClientContactOnSubmitToClient() != null) {
                    existingClient.setStopNotifyingClientContactOnSubmitToClient(client.getStopNotifyingClientContactOnSubmitToClient());
                }
                if (client.getDefaultForJobPostings() != null) {
                    existingClient.setDefaultForJobPostings(client.getDefaultForJobPostings());
                }
                if (client.getSubmissionGuidelines() != null) {
                    existingClient.setSubmissionGuidelines(client.getSubmissionGuidelines());
                }
                if (client.getAssignedTo() != null) {
                    existingClient.setAssignedTo(client.getAssignedTo());
                }
                if (client.getAssignedToTeams() != null) {
                    existingClient.setAssignedToTeams(client.getAssignedToTeams());
                }
                if (client.getSalesManagers() != null) {
                    existingClient.setSalesManagers(client.getSalesManagers());
                }
                if (client.getAccountManagers() != null) {
                    existingClient.setAccountManagers(client.getAccountManagers());
                }
                if (client.getRecruitmentManagers() != null) {
                    existingClient.setRecruitmentManagers(client.getRecruitmentManagers());
                }
                if (client.getDefaultTATConfigForJobPostingOrVMSJobs() != null) {
                    existingClient.setDefaultTATConfigForJobPostingOrVMSJobs(client.getDefaultTATConfigForJobPostingOrVMSJobs());
                }
                if (client.getDefaultTATConfigTimespan() != null) {
                    existingClient.setDefaultTATConfigTimespan(client.getDefaultTATConfigTimespan());
                }
                if (client.getNotifyOnTATToUserTypes() != null) {
                    existingClient.setNotifyOnTATToUserTypes(client.getNotifyOnTATToUserTypes());
                }
                if (client.getNotifyOnTATToUserIds() != null) {
                    existingClient.setNotifyOnTATToUserIds(client.getNotifyOnTATToUserIds());
                }
                if (client.getTaxTermIds() != null) {
                    existingClient.setTaxTermIds(client.getTaxTermIds());
                }
                if (client.getWorkAuthorizationIds() != null) {
                    existingClient.setWorkAuthorizationIds(client.getWorkAuthorizationIds());
                }
                if (client.getPostJobOnCareersPage() != null) {
                    existingClient.setPostJobOnCareersPage(client.getPostJobOnCareersPage());
                }
                if (client.getDefaultPayRateTaxTerm() != null) {
                    existingClient.setDefaultPayRateTaxTerm(client.getDefaultPayRateTaxTerm());
                }
                if (client.getDefaultBillRateTaxTerm() != null) {
                    existingClient.setDefaultBillRateTaxTerm(client.getDefaultBillRateTaxTerm());
                }
                if (client.getReferencesMandatoryForSubmission() != null) {
                    existingClient.setReferencesMandatoryForSubmission(client.getReferencesMandatoryForSubmission());
                }
                if (client.getMaxSubmissions() != null) {
                    existingClient.setMaxSubmissions(client.getMaxSubmissions());
                }
                if (client.getNoOfPositions() != null) {
                    existingClient.setNoOfPositions(client.getNoOfPositions());
                }
                if (client.getMarkUp() != null) {
                    existingClient.setMarkUp(client.getMarkUp());
                }
                if (client.getMsp() != null) {
                    existingClient.setMsp(client.getMsp());
                }
                if (client.getMailSubject() != null) {
                    existingClient.setMailSubject(client.getMailSubject());
                }
                if (client.getMailBody() != null) {
                    existingClient.setMailBody(client.getMailBody());
                }
                if (client.getFieldsForExcel() != null) {
                    existingClient.setFieldsForExcel(client.getFieldsForExcel());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
