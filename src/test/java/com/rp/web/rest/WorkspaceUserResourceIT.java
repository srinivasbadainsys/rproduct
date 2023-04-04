package com.rp.web.rest;

import static com.rp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.WorkspaceUser;
import com.rp.repository.WorkspaceUserRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link WorkspaceUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkspaceUserResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_INVITATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_INVITATION_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OWNS = false;
    private static final Boolean UPDATED_OWNS = true;

    private static final Boolean DEFAULT_ACCEPTED = false;
    private static final Boolean UPDATED_ACCEPTED = true;

    private static final Boolean DEFAULT_INVITED = false;
    private static final Boolean UPDATED_INVITED = true;

    private static final Boolean DEFAULT_REQUESTED = false;
    private static final Boolean UPDATED_REQUESTED = true;

    private static final Boolean DEFAULT_BARRED = false;
    private static final Boolean UPDATED_BARRED = true;

    private static final String DEFAULT_ROLES = "AAAAAAAAAA";
    private static final String UPDATED_ROLES = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REQUESTED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REQUESTED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/workspace-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkspaceUserMockMvc;

    private WorkspaceUser workspaceUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkspaceUser createEntity(EntityManager em) {
        WorkspaceUser workspaceUser = new WorkspaceUser()
            .userId(DEFAULT_USER_ID)
            .invitationKey(DEFAULT_INVITATION_KEY)
            .owns(DEFAULT_OWNS)
            .accepted(DEFAULT_ACCEPTED)
            .invited(DEFAULT_INVITED)
            .requested(DEFAULT_REQUESTED)
            .barred(DEFAULT_BARRED)
            .roles(DEFAULT_ROLES)
            .requestedOn(DEFAULT_REQUESTED_ON);
        return workspaceUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkspaceUser createUpdatedEntity(EntityManager em) {
        WorkspaceUser workspaceUser = new WorkspaceUser()
            .userId(UPDATED_USER_ID)
            .invitationKey(UPDATED_INVITATION_KEY)
            .owns(UPDATED_OWNS)
            .accepted(UPDATED_ACCEPTED)
            .invited(UPDATED_INVITED)
            .requested(UPDATED_REQUESTED)
            .barred(UPDATED_BARRED)
            .roles(UPDATED_ROLES)
            .requestedOn(UPDATED_REQUESTED_ON);
        return workspaceUser;
    }

    @BeforeEach
    public void initTest() {
        workspaceUser = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkspaceUser() throws Exception {
        int databaseSizeBeforeCreate = workspaceUserRepository.findAll().size();
        // Create the WorkspaceUser
        restWorkspaceUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspaceUser)))
            .andExpect(status().isCreated());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeCreate + 1);
        WorkspaceUser testWorkspaceUser = workspaceUserList.get(workspaceUserList.size() - 1);
        assertThat(testWorkspaceUser.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testWorkspaceUser.getInvitationKey()).isEqualTo(DEFAULT_INVITATION_KEY);
        assertThat(testWorkspaceUser.getOwns()).isEqualTo(DEFAULT_OWNS);
        assertThat(testWorkspaceUser.getAccepted()).isEqualTo(DEFAULT_ACCEPTED);
        assertThat(testWorkspaceUser.getInvited()).isEqualTo(DEFAULT_INVITED);
        assertThat(testWorkspaceUser.getRequested()).isEqualTo(DEFAULT_REQUESTED);
        assertThat(testWorkspaceUser.getBarred()).isEqualTo(DEFAULT_BARRED);
        assertThat(testWorkspaceUser.getRoles()).isEqualTo(DEFAULT_ROLES);
        assertThat(testWorkspaceUser.getRequestedOn()).isEqualTo(DEFAULT_REQUESTED_ON);
    }

    @Test
    @Transactional
    void createWorkspaceUserWithExistingId() throws Exception {
        // Create the WorkspaceUser with an existing ID
        workspaceUser.setId(1L);

        int databaseSizeBeforeCreate = workspaceUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkspaceUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspaceUser)))
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkspaceUsers() throws Exception {
        // Initialize the database
        workspaceUserRepository.saveAndFlush(workspaceUser);

        // Get all the workspaceUserList
        restWorkspaceUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspaceUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].invitationKey").value(hasItem(DEFAULT_INVITATION_KEY)))
            .andExpect(jsonPath("$.[*].owns").value(hasItem(DEFAULT_OWNS.booleanValue())))
            .andExpect(jsonPath("$.[*].accepted").value(hasItem(DEFAULT_ACCEPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].invited").value(hasItem(DEFAULT_INVITED.booleanValue())))
            .andExpect(jsonPath("$.[*].requested").value(hasItem(DEFAULT_REQUESTED.booleanValue())))
            .andExpect(jsonPath("$.[*].barred").value(hasItem(DEFAULT_BARRED.booleanValue())))
            .andExpect(jsonPath("$.[*].roles").value(hasItem(DEFAULT_ROLES)))
            .andExpect(jsonPath("$.[*].requestedOn").value(hasItem(sameInstant(DEFAULT_REQUESTED_ON))));
    }

    @Test
    @Transactional
    void getWorkspaceUser() throws Exception {
        // Initialize the database
        workspaceUserRepository.saveAndFlush(workspaceUser);

        // Get the workspaceUser
        restWorkspaceUserMockMvc
            .perform(get(ENTITY_API_URL_ID, workspaceUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workspaceUser.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.invitationKey").value(DEFAULT_INVITATION_KEY))
            .andExpect(jsonPath("$.owns").value(DEFAULT_OWNS.booleanValue()))
            .andExpect(jsonPath("$.accepted").value(DEFAULT_ACCEPTED.booleanValue()))
            .andExpect(jsonPath("$.invited").value(DEFAULT_INVITED.booleanValue()))
            .andExpect(jsonPath("$.requested").value(DEFAULT_REQUESTED.booleanValue()))
            .andExpect(jsonPath("$.barred").value(DEFAULT_BARRED.booleanValue()))
            .andExpect(jsonPath("$.roles").value(DEFAULT_ROLES))
            .andExpect(jsonPath("$.requestedOn").value(sameInstant(DEFAULT_REQUESTED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingWorkspaceUser() throws Exception {
        // Get the workspaceUser
        restWorkspaceUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkspaceUser() throws Exception {
        // Initialize the database
        workspaceUserRepository.saveAndFlush(workspaceUser);

        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();

        // Update the workspaceUser
        WorkspaceUser updatedWorkspaceUser = workspaceUserRepository.findById(workspaceUser.getId()).get();
        // Disconnect from session so that the updates on updatedWorkspaceUser are not directly saved in db
        em.detach(updatedWorkspaceUser);
        updatedWorkspaceUser
            .userId(UPDATED_USER_ID)
            .invitationKey(UPDATED_INVITATION_KEY)
            .owns(UPDATED_OWNS)
            .accepted(UPDATED_ACCEPTED)
            .invited(UPDATED_INVITED)
            .requested(UPDATED_REQUESTED)
            .barred(UPDATED_BARRED)
            .roles(UPDATED_ROLES)
            .requestedOn(UPDATED_REQUESTED_ON);

        restWorkspaceUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkspaceUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkspaceUser))
            )
            .andExpect(status().isOk());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
        WorkspaceUser testWorkspaceUser = workspaceUserList.get(workspaceUserList.size() - 1);
        assertThat(testWorkspaceUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testWorkspaceUser.getInvitationKey()).isEqualTo(UPDATED_INVITATION_KEY);
        assertThat(testWorkspaceUser.getOwns()).isEqualTo(UPDATED_OWNS);
        assertThat(testWorkspaceUser.getAccepted()).isEqualTo(UPDATED_ACCEPTED);
        assertThat(testWorkspaceUser.getInvited()).isEqualTo(UPDATED_INVITED);
        assertThat(testWorkspaceUser.getRequested()).isEqualTo(UPDATED_REQUESTED);
        assertThat(testWorkspaceUser.getBarred()).isEqualTo(UPDATED_BARRED);
        assertThat(testWorkspaceUser.getRoles()).isEqualTo(UPDATED_ROLES);
        assertThat(testWorkspaceUser.getRequestedOn()).isEqualTo(UPDATED_REQUESTED_ON);
    }

    @Test
    @Transactional
    void putNonExistingWorkspaceUser() throws Exception {
        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();
        workspaceUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workspaceUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workspaceUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkspaceUser() throws Exception {
        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();
        workspaceUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workspaceUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkspaceUser() throws Exception {
        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();
        workspaceUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspaceUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkspaceUserWithPatch() throws Exception {
        // Initialize the database
        workspaceUserRepository.saveAndFlush(workspaceUser);

        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();

        // Update the workspaceUser using partial update
        WorkspaceUser partialUpdatedWorkspaceUser = new WorkspaceUser();
        partialUpdatedWorkspaceUser.setId(workspaceUser.getId());

        partialUpdatedWorkspaceUser
            .userId(UPDATED_USER_ID)
            .invitationKey(UPDATED_INVITATION_KEY)
            .owns(UPDATED_OWNS)
            .invited(UPDATED_INVITED)
            .barred(UPDATED_BARRED)
            .requestedOn(UPDATED_REQUESTED_ON);

        restWorkspaceUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkspaceUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkspaceUser))
            )
            .andExpect(status().isOk());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
        WorkspaceUser testWorkspaceUser = workspaceUserList.get(workspaceUserList.size() - 1);
        assertThat(testWorkspaceUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testWorkspaceUser.getInvitationKey()).isEqualTo(UPDATED_INVITATION_KEY);
        assertThat(testWorkspaceUser.getOwns()).isEqualTo(UPDATED_OWNS);
        assertThat(testWorkspaceUser.getAccepted()).isEqualTo(DEFAULT_ACCEPTED);
        assertThat(testWorkspaceUser.getInvited()).isEqualTo(UPDATED_INVITED);
        assertThat(testWorkspaceUser.getRequested()).isEqualTo(DEFAULT_REQUESTED);
        assertThat(testWorkspaceUser.getBarred()).isEqualTo(UPDATED_BARRED);
        assertThat(testWorkspaceUser.getRoles()).isEqualTo(DEFAULT_ROLES);
        assertThat(testWorkspaceUser.getRequestedOn()).isEqualTo(UPDATED_REQUESTED_ON);
    }

    @Test
    @Transactional
    void fullUpdateWorkspaceUserWithPatch() throws Exception {
        // Initialize the database
        workspaceUserRepository.saveAndFlush(workspaceUser);

        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();

        // Update the workspaceUser using partial update
        WorkspaceUser partialUpdatedWorkspaceUser = new WorkspaceUser();
        partialUpdatedWorkspaceUser.setId(workspaceUser.getId());

        partialUpdatedWorkspaceUser
            .userId(UPDATED_USER_ID)
            .invitationKey(UPDATED_INVITATION_KEY)
            .owns(UPDATED_OWNS)
            .accepted(UPDATED_ACCEPTED)
            .invited(UPDATED_INVITED)
            .requested(UPDATED_REQUESTED)
            .barred(UPDATED_BARRED)
            .roles(UPDATED_ROLES)
            .requestedOn(UPDATED_REQUESTED_ON);

        restWorkspaceUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkspaceUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkspaceUser))
            )
            .andExpect(status().isOk());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
        WorkspaceUser testWorkspaceUser = workspaceUserList.get(workspaceUserList.size() - 1);
        assertThat(testWorkspaceUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testWorkspaceUser.getInvitationKey()).isEqualTo(UPDATED_INVITATION_KEY);
        assertThat(testWorkspaceUser.getOwns()).isEqualTo(UPDATED_OWNS);
        assertThat(testWorkspaceUser.getAccepted()).isEqualTo(UPDATED_ACCEPTED);
        assertThat(testWorkspaceUser.getInvited()).isEqualTo(UPDATED_INVITED);
        assertThat(testWorkspaceUser.getRequested()).isEqualTo(UPDATED_REQUESTED);
        assertThat(testWorkspaceUser.getBarred()).isEqualTo(UPDATED_BARRED);
        assertThat(testWorkspaceUser.getRoles()).isEqualTo(UPDATED_ROLES);
        assertThat(testWorkspaceUser.getRequestedOn()).isEqualTo(UPDATED_REQUESTED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingWorkspaceUser() throws Exception {
        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();
        workspaceUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workspaceUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workspaceUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkspaceUser() throws Exception {
        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();
        workspaceUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workspaceUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkspaceUser() throws Exception {
        int databaseSizeBeforeUpdate = workspaceUserRepository.findAll().size();
        workspaceUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workspaceUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkspaceUser in the database
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkspaceUser() throws Exception {
        // Initialize the database
        workspaceUserRepository.saveAndFlush(workspaceUser);

        int databaseSizeBeforeDelete = workspaceUserRepository.findAll().size();

        // Delete the workspaceUser
        restWorkspaceUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, workspaceUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAll();
        assertThat(workspaceUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
