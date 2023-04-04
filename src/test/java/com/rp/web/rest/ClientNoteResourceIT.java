package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.ClientNote;
import com.rp.repository.ClientNoteRepository;
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
 * Integration tests for the {@link ClientNoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientNoteResourceIT {

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_NOTES_PRIORITY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_TO_USERS = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_TO_USERS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMIND_ME = false;
    private static final Boolean UPDATED_REMIND_ME = true;

    private static final String ENTITY_API_URL = "/api/client-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientNoteRepository clientNoteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientNoteMockMvc;

    private ClientNote clientNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientNote createEntity(EntityManager em) {
        ClientNote clientNote = new ClientNote()
            .clientId(DEFAULT_CLIENT_ID)
            .action(DEFAULT_ACTION)
            .notesPriority(DEFAULT_NOTES_PRIORITY)
            .note(DEFAULT_NOTE)
            .notifyToUsers(DEFAULT_NOTIFY_TO_USERS)
            .remindMe(DEFAULT_REMIND_ME);
        return clientNote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientNote createUpdatedEntity(EntityManager em) {
        ClientNote clientNote = new ClientNote()
            .clientId(UPDATED_CLIENT_ID)
            .action(UPDATED_ACTION)
            .notesPriority(UPDATED_NOTES_PRIORITY)
            .note(UPDATED_NOTE)
            .notifyToUsers(UPDATED_NOTIFY_TO_USERS)
            .remindMe(UPDATED_REMIND_ME);
        return clientNote;
    }

    @BeforeEach
    public void initTest() {
        clientNote = createEntity(em);
    }

    @Test
    @Transactional
    void createClientNote() throws Exception {
        int databaseSizeBeforeCreate = clientNoteRepository.findAll().size();
        // Create the ClientNote
        restClientNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientNote)))
            .andExpect(status().isCreated());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeCreate + 1);
        ClientNote testClientNote = clientNoteList.get(clientNoteList.size() - 1);
        assertThat(testClientNote.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientNote.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testClientNote.getNotesPriority()).isEqualTo(DEFAULT_NOTES_PRIORITY);
        assertThat(testClientNote.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testClientNote.getNotifyToUsers()).isEqualTo(DEFAULT_NOTIFY_TO_USERS);
        assertThat(testClientNote.getRemindMe()).isEqualTo(DEFAULT_REMIND_ME);
    }

    @Test
    @Transactional
    void createClientNoteWithExistingId() throws Exception {
        // Create the ClientNote with an existing ID
        clientNote.setId(1L);

        int databaseSizeBeforeCreate = clientNoteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientNote)))
            .andExpect(status().isBadRequest());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientNotes() throws Exception {
        // Initialize the database
        clientNoteRepository.saveAndFlush(clientNote);

        // Get all the clientNoteList
        restClientNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].notesPriority").value(hasItem(DEFAULT_NOTES_PRIORITY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].notifyToUsers").value(hasItem(DEFAULT_NOTIFY_TO_USERS)))
            .andExpect(jsonPath("$.[*].remindMe").value(hasItem(DEFAULT_REMIND_ME.booleanValue())));
    }

    @Test
    @Transactional
    void getClientNote() throws Exception {
        // Initialize the database
        clientNoteRepository.saveAndFlush(clientNote);

        // Get the clientNote
        restClientNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, clientNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientNote.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.notesPriority").value(DEFAULT_NOTES_PRIORITY))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.notifyToUsers").value(DEFAULT_NOTIFY_TO_USERS))
            .andExpect(jsonPath("$.remindMe").value(DEFAULT_REMIND_ME.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingClientNote() throws Exception {
        // Get the clientNote
        restClientNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientNote() throws Exception {
        // Initialize the database
        clientNoteRepository.saveAndFlush(clientNote);

        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();

        // Update the clientNote
        ClientNote updatedClientNote = clientNoteRepository.findById(clientNote.getId()).get();
        // Disconnect from session so that the updates on updatedClientNote are not directly saved in db
        em.detach(updatedClientNote);
        updatedClientNote
            .clientId(UPDATED_CLIENT_ID)
            .action(UPDATED_ACTION)
            .notesPriority(UPDATED_NOTES_PRIORITY)
            .note(UPDATED_NOTE)
            .notifyToUsers(UPDATED_NOTIFY_TO_USERS)
            .remindMe(UPDATED_REMIND_ME);

        restClientNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientNote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientNote))
            )
            .andExpect(status().isOk());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
        ClientNote testClientNote = clientNoteList.get(clientNoteList.size() - 1);
        assertThat(testClientNote.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientNote.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testClientNote.getNotesPriority()).isEqualTo(UPDATED_NOTES_PRIORITY);
        assertThat(testClientNote.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testClientNote.getNotifyToUsers()).isEqualTo(UPDATED_NOTIFY_TO_USERS);
        assertThat(testClientNote.getRemindMe()).isEqualTo(UPDATED_REMIND_ME);
    }

    @Test
    @Transactional
    void putNonExistingClientNote() throws Exception {
        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();
        clientNote.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientNote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientNote() throws Exception {
        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();
        clientNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientNote() throws Exception {
        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();
        clientNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientNoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientNote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientNoteWithPatch() throws Exception {
        // Initialize the database
        clientNoteRepository.saveAndFlush(clientNote);

        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();

        // Update the clientNote using partial update
        ClientNote partialUpdatedClientNote = new ClientNote();
        partialUpdatedClientNote.setId(clientNote.getId());

        partialUpdatedClientNote
            .action(UPDATED_ACTION)
            .notesPriority(UPDATED_NOTES_PRIORITY)
            .notifyToUsers(UPDATED_NOTIFY_TO_USERS)
            .remindMe(UPDATED_REMIND_ME);

        restClientNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientNote))
            )
            .andExpect(status().isOk());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
        ClientNote testClientNote = clientNoteList.get(clientNoteList.size() - 1);
        assertThat(testClientNote.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientNote.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testClientNote.getNotesPriority()).isEqualTo(UPDATED_NOTES_PRIORITY);
        assertThat(testClientNote.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testClientNote.getNotifyToUsers()).isEqualTo(UPDATED_NOTIFY_TO_USERS);
        assertThat(testClientNote.getRemindMe()).isEqualTo(UPDATED_REMIND_ME);
    }

    @Test
    @Transactional
    void fullUpdateClientNoteWithPatch() throws Exception {
        // Initialize the database
        clientNoteRepository.saveAndFlush(clientNote);

        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();

        // Update the clientNote using partial update
        ClientNote partialUpdatedClientNote = new ClientNote();
        partialUpdatedClientNote.setId(clientNote.getId());

        partialUpdatedClientNote
            .clientId(UPDATED_CLIENT_ID)
            .action(UPDATED_ACTION)
            .notesPriority(UPDATED_NOTES_PRIORITY)
            .note(UPDATED_NOTE)
            .notifyToUsers(UPDATED_NOTIFY_TO_USERS)
            .remindMe(UPDATED_REMIND_ME);

        restClientNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientNote))
            )
            .andExpect(status().isOk());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
        ClientNote testClientNote = clientNoteList.get(clientNoteList.size() - 1);
        assertThat(testClientNote.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientNote.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testClientNote.getNotesPriority()).isEqualTo(UPDATED_NOTES_PRIORITY);
        assertThat(testClientNote.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testClientNote.getNotifyToUsers()).isEqualTo(UPDATED_NOTIFY_TO_USERS);
        assertThat(testClientNote.getRemindMe()).isEqualTo(UPDATED_REMIND_ME);
    }

    @Test
    @Transactional
    void patchNonExistingClientNote() throws Exception {
        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();
        clientNote.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientNote() throws Exception {
        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();
        clientNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientNote))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientNote() throws Exception {
        int databaseSizeBeforeUpdate = clientNoteRepository.findAll().size();
        clientNote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientNoteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientNote))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientNote in the database
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientNote() throws Exception {
        // Initialize the database
        clientNoteRepository.saveAndFlush(clientNote);

        int databaseSizeBeforeDelete = clientNoteRepository.findAll().size();

        // Delete the clientNote
        restClientNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientNote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientNote> clientNoteList = clientNoteRepository.findAll();
        assertThat(clientNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
