package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.ClientAccount;
import com.rp.repository.ClientAccountRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClientAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientAccountResourceIT {

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_NUMBER_EXTN = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NUMBER_EXTN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientAccountRepository clientAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientAccountMockMvc;

    private ClientAccount clientAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientAccount createEntity(EntityManager em) {
        ClientAccount clientAccount = new ClientAccount()
            .clientId(DEFAULT_CLIENT_ID)
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .officeNumber(DEFAULT_OFFICE_NUMBER)
            .officeNumberExtn(DEFAULT_OFFICE_NUMBER_EXTN)
            .emailID(DEFAULT_EMAIL_ID)
            .designation(DEFAULT_DESIGNATION);
        return clientAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientAccount createUpdatedEntity(EntityManager em) {
        ClientAccount clientAccount = new ClientAccount()
            .clientId(UPDATED_CLIENT_ID)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .officeNumber(UPDATED_OFFICE_NUMBER)
            .officeNumberExtn(UPDATED_OFFICE_NUMBER_EXTN)
            .emailID(UPDATED_EMAIL_ID)
            .designation(UPDATED_DESIGNATION);
        return clientAccount;
    }

    @BeforeEach
    public void initTest() {
        clientAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createClientAccount() throws Exception {
        int databaseSizeBeforeCreate = clientAccountRepository.findAll().size();
        // Create the ClientAccount
        restClientAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientAccount)))
            .andExpect(status().isCreated());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ClientAccount testClientAccount = clientAccountList.get(clientAccountList.size() - 1);
        assertThat(testClientAccount.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientAccount.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testClientAccount.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testClientAccount.getOfficeNumber()).isEqualTo(DEFAULT_OFFICE_NUMBER);
        assertThat(testClientAccount.getOfficeNumberExtn()).isEqualTo(DEFAULT_OFFICE_NUMBER_EXTN);
        assertThat(testClientAccount.getEmailID()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testClientAccount.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createClientAccountWithExistingId() throws Exception {
        // Create the ClientAccount with an existing ID
        clientAccount.setId(1L);

        int databaseSizeBeforeCreate = clientAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientAccount)))
            .andExpect(status().isBadRequest());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientAccounts() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get all the clientAccountList
        restClientAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].officeNumber").value(hasItem(DEFAULT_OFFICE_NUMBER)))
            .andExpect(jsonPath("$.[*].officeNumberExtn").value(hasItem(DEFAULT_OFFICE_NUMBER_EXTN)))
            .andExpect(jsonPath("$.[*].emailID").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getClientAccount() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        // Get the clientAccount
        restClientAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, clientAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientAccount.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.officeNumber").value(DEFAULT_OFFICE_NUMBER))
            .andExpect(jsonPath("$.officeNumberExtn").value(DEFAULT_OFFICE_NUMBER_EXTN))
            .andExpect(jsonPath("$.emailID").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getNonExistingClientAccount() throws Exception {
        // Get the clientAccount
        restClientAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientAccount() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();

        // Update the clientAccount
        ClientAccount updatedClientAccount = clientAccountRepository.findById(clientAccount.getId()).get();
        // Disconnect from session so that the updates on updatedClientAccount are not directly saved in db
        em.detach(updatedClientAccount);
        updatedClientAccount
            .clientId(UPDATED_CLIENT_ID)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .officeNumber(UPDATED_OFFICE_NUMBER)
            .officeNumberExtn(UPDATED_OFFICE_NUMBER_EXTN)
            .emailID(UPDATED_EMAIL_ID)
            .designation(UPDATED_DESIGNATION);

        restClientAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientAccount))
            )
            .andExpect(status().isOk());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
        ClientAccount testClientAccount = clientAccountList.get(clientAccountList.size() - 1);
        assertThat(testClientAccount.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientAccount.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testClientAccount.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testClientAccount.getOfficeNumber()).isEqualTo(UPDATED_OFFICE_NUMBER);
        assertThat(testClientAccount.getOfficeNumberExtn()).isEqualTo(UPDATED_OFFICE_NUMBER_EXTN);
        assertThat(testClientAccount.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testClientAccount.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingClientAccount() throws Exception {
        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();
        clientAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientAccount() throws Exception {
        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();
        clientAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientAccount() throws Exception {
        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();
        clientAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientAccountWithPatch() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();

        // Update the clientAccount using partial update
        ClientAccount partialUpdatedClientAccount = new ClientAccount();
        partialUpdatedClientAccount.setId(clientAccount.getId());

        partialUpdatedClientAccount
            .clientId(UPDATED_CLIENT_ID)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .officeNumber(UPDATED_OFFICE_NUMBER)
            .officeNumberExtn(UPDATED_OFFICE_NUMBER_EXTN)
            .emailID(UPDATED_EMAIL_ID)
            .designation(UPDATED_DESIGNATION);

        restClientAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientAccount))
            )
            .andExpect(status().isOk());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
        ClientAccount testClientAccount = clientAccountList.get(clientAccountList.size() - 1);
        assertThat(testClientAccount.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientAccount.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testClientAccount.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testClientAccount.getOfficeNumber()).isEqualTo(UPDATED_OFFICE_NUMBER);
        assertThat(testClientAccount.getOfficeNumberExtn()).isEqualTo(UPDATED_OFFICE_NUMBER_EXTN);
        assertThat(testClientAccount.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testClientAccount.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateClientAccountWithPatch() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();

        // Update the clientAccount using partial update
        ClientAccount partialUpdatedClientAccount = new ClientAccount();
        partialUpdatedClientAccount.setId(clientAccount.getId());

        partialUpdatedClientAccount
            .clientId(UPDATED_CLIENT_ID)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .officeNumber(UPDATED_OFFICE_NUMBER)
            .officeNumberExtn(UPDATED_OFFICE_NUMBER_EXTN)
            .emailID(UPDATED_EMAIL_ID)
            .designation(UPDATED_DESIGNATION);

        restClientAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientAccount))
            )
            .andExpect(status().isOk());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
        ClientAccount testClientAccount = clientAccountList.get(clientAccountList.size() - 1);
        assertThat(testClientAccount.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientAccount.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testClientAccount.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testClientAccount.getOfficeNumber()).isEqualTo(UPDATED_OFFICE_NUMBER);
        assertThat(testClientAccount.getOfficeNumberExtn()).isEqualTo(UPDATED_OFFICE_NUMBER_EXTN);
        assertThat(testClientAccount.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testClientAccount.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingClientAccount() throws Exception {
        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();
        clientAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientAccount() throws Exception {
        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();
        clientAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientAccount() throws Exception {
        int databaseSizeBeforeUpdate = clientAccountRepository.findAll().size();
        clientAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientAccount in the database
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientAccount() throws Exception {
        // Initialize the database
        clientAccountRepository.saveAndFlush(clientAccount);

        int databaseSizeBeforeDelete = clientAccountRepository.findAll().size();

        // Delete the clientAccount
        restClientAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientAccount> clientAccountList = clientAccountRepository.findAll();
        assertThat(clientAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
