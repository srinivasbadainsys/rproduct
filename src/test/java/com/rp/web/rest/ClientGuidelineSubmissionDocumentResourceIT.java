package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.ClientGuidelineSubmissionDocument;
import com.rp.repository.ClientGuidelineSubmissionDocumentRepository;
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
 * Integration tests for the {@link ClientGuidelineSubmissionDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientGuidelineSubmissionDocumentResourceIT {

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final String DEFAULT_DOCUMENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-guideline-submission-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientGuidelineSubmissionDocumentRepository clientGuidelineSubmissionDocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientGuidelineSubmissionDocumentMockMvc;

    private ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientGuidelineSubmissionDocument createEntity(EntityManager em) {
        ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument = new ClientGuidelineSubmissionDocument()
            .clientId(DEFAULT_CLIENT_ID)
            .documentTitle(DEFAULT_DOCUMENT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .documentPath(DEFAULT_DOCUMENT_PATH);
        return clientGuidelineSubmissionDocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientGuidelineSubmissionDocument createUpdatedEntity(EntityManager em) {
        ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument = new ClientGuidelineSubmissionDocument()
            .clientId(UPDATED_CLIENT_ID)
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .description(UPDATED_DESCRIPTION)
            .documentPath(UPDATED_DOCUMENT_PATH);
        return clientGuidelineSubmissionDocument;
    }

    @BeforeEach
    public void initTest() {
        clientGuidelineSubmissionDocument = createEntity(em);
    }

    @Test
    @Transactional
    void createClientGuidelineSubmissionDocument() throws Exception {
        int databaseSizeBeforeCreate = clientGuidelineSubmissionDocumentRepository.findAll().size();
        // Create the ClientGuidelineSubmissionDocument
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isCreated());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ClientGuidelineSubmissionDocument testClientGuidelineSubmissionDocument = clientGuidelineSubmissionDocumentList.get(
            clientGuidelineSubmissionDocumentList.size() - 1
        );
        assertThat(testClientGuidelineSubmissionDocument.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentTitle()).isEqualTo(DEFAULT_DOCUMENT_TITLE);
        assertThat(testClientGuidelineSubmissionDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentPath()).isEqualTo(DEFAULT_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void createClientGuidelineSubmissionDocumentWithExistingId() throws Exception {
        // Create the ClientGuidelineSubmissionDocument with an existing ID
        clientGuidelineSubmissionDocument.setId(1L);

        int databaseSizeBeforeCreate = clientGuidelineSubmissionDocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientGuidelineSubmissionDocuments() throws Exception {
        // Initialize the database
        clientGuidelineSubmissionDocumentRepository.saveAndFlush(clientGuidelineSubmissionDocument);

        // Get all the clientGuidelineSubmissionDocumentList
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientGuidelineSubmissionDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].documentTitle").value(hasItem(DEFAULT_DOCUMENT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].documentPath").value(hasItem(DEFAULT_DOCUMENT_PATH)));
    }

    @Test
    @Transactional
    void getClientGuidelineSubmissionDocument() throws Exception {
        // Initialize the database
        clientGuidelineSubmissionDocumentRepository.saveAndFlush(clientGuidelineSubmissionDocument);

        // Get the clientGuidelineSubmissionDocument
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, clientGuidelineSubmissionDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientGuidelineSubmissionDocument.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.documentTitle").value(DEFAULT_DOCUMENT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.documentPath").value(DEFAULT_DOCUMENT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingClientGuidelineSubmissionDocument() throws Exception {
        // Get the clientGuidelineSubmissionDocument
        restClientGuidelineSubmissionDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientGuidelineSubmissionDocument() throws Exception {
        // Initialize the database
        clientGuidelineSubmissionDocumentRepository.saveAndFlush(clientGuidelineSubmissionDocument);

        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();

        // Update the clientGuidelineSubmissionDocument
        ClientGuidelineSubmissionDocument updatedClientGuidelineSubmissionDocument = clientGuidelineSubmissionDocumentRepository
            .findById(clientGuidelineSubmissionDocument.getId())
            .get();
        // Disconnect from session so that the updates on updatedClientGuidelineSubmissionDocument are not directly saved in db
        em.detach(updatedClientGuidelineSubmissionDocument);
        updatedClientGuidelineSubmissionDocument
            .clientId(UPDATED_CLIENT_ID)
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .description(UPDATED_DESCRIPTION)
            .documentPath(UPDATED_DOCUMENT_PATH);

        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientGuidelineSubmissionDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientGuidelineSubmissionDocument))
            )
            .andExpect(status().isOk());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
        ClientGuidelineSubmissionDocument testClientGuidelineSubmissionDocument = clientGuidelineSubmissionDocumentList.get(
            clientGuidelineSubmissionDocumentList.size() - 1
        );
        assertThat(testClientGuidelineSubmissionDocument.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentTitle()).isEqualTo(UPDATED_DOCUMENT_TITLE);
        assertThat(testClientGuidelineSubmissionDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void putNonExistingClientGuidelineSubmissionDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();
        clientGuidelineSubmissionDocument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientGuidelineSubmissionDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientGuidelineSubmissionDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();
        clientGuidelineSubmissionDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientGuidelineSubmissionDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();
        clientGuidelineSubmissionDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientGuidelineSubmissionDocumentWithPatch() throws Exception {
        // Initialize the database
        clientGuidelineSubmissionDocumentRepository.saveAndFlush(clientGuidelineSubmissionDocument);

        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();

        // Update the clientGuidelineSubmissionDocument using partial update
        ClientGuidelineSubmissionDocument partialUpdatedClientGuidelineSubmissionDocument = new ClientGuidelineSubmissionDocument();
        partialUpdatedClientGuidelineSubmissionDocument.setId(clientGuidelineSubmissionDocument.getId());

        partialUpdatedClientGuidelineSubmissionDocument.documentTitle(UPDATED_DOCUMENT_TITLE);

        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientGuidelineSubmissionDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientGuidelineSubmissionDocument))
            )
            .andExpect(status().isOk());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
        ClientGuidelineSubmissionDocument testClientGuidelineSubmissionDocument = clientGuidelineSubmissionDocumentList.get(
            clientGuidelineSubmissionDocumentList.size() - 1
        );
        assertThat(testClientGuidelineSubmissionDocument.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentTitle()).isEqualTo(UPDATED_DOCUMENT_TITLE);
        assertThat(testClientGuidelineSubmissionDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentPath()).isEqualTo(DEFAULT_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void fullUpdateClientGuidelineSubmissionDocumentWithPatch() throws Exception {
        // Initialize the database
        clientGuidelineSubmissionDocumentRepository.saveAndFlush(clientGuidelineSubmissionDocument);

        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();

        // Update the clientGuidelineSubmissionDocument using partial update
        ClientGuidelineSubmissionDocument partialUpdatedClientGuidelineSubmissionDocument = new ClientGuidelineSubmissionDocument();
        partialUpdatedClientGuidelineSubmissionDocument.setId(clientGuidelineSubmissionDocument.getId());

        partialUpdatedClientGuidelineSubmissionDocument
            .clientId(UPDATED_CLIENT_ID)
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .description(UPDATED_DESCRIPTION)
            .documentPath(UPDATED_DOCUMENT_PATH);

        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientGuidelineSubmissionDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientGuidelineSubmissionDocument))
            )
            .andExpect(status().isOk());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
        ClientGuidelineSubmissionDocument testClientGuidelineSubmissionDocument = clientGuidelineSubmissionDocumentList.get(
            clientGuidelineSubmissionDocumentList.size() - 1
        );
        assertThat(testClientGuidelineSubmissionDocument.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentTitle()).isEqualTo(UPDATED_DOCUMENT_TITLE);
        assertThat(testClientGuidelineSubmissionDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientGuidelineSubmissionDocument.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingClientGuidelineSubmissionDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();
        clientGuidelineSubmissionDocument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientGuidelineSubmissionDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientGuidelineSubmissionDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();
        clientGuidelineSubmissionDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientGuidelineSubmissionDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientGuidelineSubmissionDocumentRepository.findAll().size();
        clientGuidelineSubmissionDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientGuidelineSubmissionDocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientGuidelineSubmissionDocument in the database
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientGuidelineSubmissionDocument() throws Exception {
        // Initialize the database
        clientGuidelineSubmissionDocumentRepository.saveAndFlush(clientGuidelineSubmissionDocument);

        int databaseSizeBeforeDelete = clientGuidelineSubmissionDocumentRepository.findAll().size();

        // Delete the clientGuidelineSubmissionDocument
        restClientGuidelineSubmissionDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientGuidelineSubmissionDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocumentList = clientGuidelineSubmissionDocumentRepository.findAll();
        assertThat(clientGuidelineSubmissionDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
