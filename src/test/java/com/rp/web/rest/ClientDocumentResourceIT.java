package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.ClientDocument;
import com.rp.repository.ClientDocumentRepository;
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
 * Integration tests for the {@link ClientDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientDocumentResourceIT {

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final String DEFAULT_DOCUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/client-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientDocumentRepository clientDocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientDocumentMockMvc;

    private ClientDocument clientDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDocument createEntity(EntityManager em) {
        ClientDocument clientDocument = new ClientDocument()
            .clientId(DEFAULT_CLIENT_ID)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .documentPath(DEFAULT_DOCUMENT_PATH);
        return clientDocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDocument createUpdatedEntity(EntityManager em) {
        ClientDocument clientDocument = new ClientDocument()
            .clientId(UPDATED_CLIENT_ID)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .documentPath(UPDATED_DOCUMENT_PATH);
        return clientDocument;
    }

    @BeforeEach
    public void initTest() {
        clientDocument = createEntity(em);
    }

    @Test
    @Transactional
    void createClientDocument() throws Exception {
        int databaseSizeBeforeCreate = clientDocumentRepository.findAll().size();
        // Create the ClientDocument
        restClientDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDocument))
            )
            .andExpect(status().isCreated());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ClientDocument testClientDocument = clientDocumentList.get(clientDocumentList.size() - 1);
        assertThat(testClientDocument.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testClientDocument.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testClientDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClientDocument.getDocumentPath()).isEqualTo(DEFAULT_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void createClientDocumentWithExistingId() throws Exception {
        // Create the ClientDocument with an existing ID
        clientDocument.setId(1L);

        int databaseSizeBeforeCreate = clientDocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientDocuments() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get all the clientDocumentList
        restClientDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].documentPath").value(hasItem(DEFAULT_DOCUMENT_PATH)));
    }

    @Test
    @Transactional
    void getClientDocument() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        // Get the clientDocument
        restClientDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, clientDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientDocument.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.documentPath").value(DEFAULT_DOCUMENT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingClientDocument() throws Exception {
        // Get the clientDocument
        restClientDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientDocument() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();

        // Update the clientDocument
        ClientDocument updatedClientDocument = clientDocumentRepository.findById(clientDocument.getId()).get();
        // Disconnect from session so that the updates on updatedClientDocument are not directly saved in db
        em.detach(updatedClientDocument);
        updatedClientDocument
            .clientId(UPDATED_CLIENT_ID)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .documentPath(UPDATED_DOCUMENT_PATH);

        restClientDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientDocument))
            )
            .andExpect(status().isOk());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
        ClientDocument testClientDocument = clientDocumentList.get(clientDocumentList.size() - 1);
        assertThat(testClientDocument.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testClientDocument.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClientDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientDocument.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void putNonExistingClientDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();
        clientDocument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();
        clientDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();
        clientDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientDocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientDocumentWithPatch() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();

        // Update the clientDocument using partial update
        ClientDocument partialUpdatedClientDocument = new ClientDocument();
        partialUpdatedClientDocument.setId(clientDocument.getId());

        partialUpdatedClientDocument.title(UPDATED_TITLE).documentPath(UPDATED_DOCUMENT_PATH);

        restClientDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientDocument))
            )
            .andExpect(status().isOk());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
        ClientDocument testClientDocument = clientDocumentList.get(clientDocumentList.size() - 1);
        assertThat(testClientDocument.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testClientDocument.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClientDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClientDocument.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void fullUpdateClientDocumentWithPatch() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();

        // Update the clientDocument using partial update
        ClientDocument partialUpdatedClientDocument = new ClientDocument();
        partialUpdatedClientDocument.setId(clientDocument.getId());

        partialUpdatedClientDocument
            .clientId(UPDATED_CLIENT_ID)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .documentPath(UPDATED_DOCUMENT_PATH);

        restClientDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientDocument))
            )
            .andExpect(status().isOk());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
        ClientDocument testClientDocument = clientDocumentList.get(clientDocumentList.size() - 1);
        assertThat(testClientDocument.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testClientDocument.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClientDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientDocument.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingClientDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();
        clientDocument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();
        clientDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientDocument() throws Exception {
        int databaseSizeBeforeUpdate = clientDocumentRepository.findAll().size();
        clientDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientDocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientDocument in the database
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientDocument() throws Exception {
        // Initialize the database
        clientDocumentRepository.saveAndFlush(clientDocument);

        int databaseSizeBeforeDelete = clientDocumentRepository.findAll().size();

        // Delete the clientDocument
        restClientDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientDocument> clientDocumentList = clientDocumentRepository.findAll();
        assertThat(clientDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
