package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.Team;
import com.rp.domain.enumeration.TeamType;
import com.rp.repository.TeamRepository;
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
 * Integration tests for the {@link TeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_GROUP_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_GROUP_EMAIL = "BBBBBBBBBB";

    private static final TeamType DEFAULT_TYPE = TeamType.TEAM;
    private static final TeamType UPDATED_TYPE = TeamType.GROUP;

    private static final String DEFAULT_NOTIFY_ON_JOB_POSTING_TO_USERS = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_ON_JOB_SHARING_TO_USERS = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_ON_JOB_CLOSING_TO_USERS = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ON_JOB_CLOSING_TO_USERS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_ON_CAND_SUBMISSION_TO_USERS = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ON_CAND_SUBMISSION_TO_USERS = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamMockMvc;

    private Team team;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createEntity(EntityManager em) {
        Team team = new Team()
            .name(DEFAULT_NAME)
            .teamGroupEmail(DEFAULT_TEAM_GROUP_EMAIL)
            .type(DEFAULT_TYPE)
            .notifyOnJobPostingToUsers(DEFAULT_NOTIFY_ON_JOB_POSTING_TO_USERS)
            .notifyOnJobSharingToUsers(DEFAULT_NOTIFY_ON_JOB_SHARING_TO_USERS)
            .notifyOnJobClosingToUsers(DEFAULT_NOTIFY_ON_JOB_CLOSING_TO_USERS)
            .notifyOnCandSubmissionToUsers(DEFAULT_NOTIFY_ON_CAND_SUBMISSION_TO_USERS)
            .notifyOnCandStatusChangeToUsers(DEFAULT_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);
        return team;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createUpdatedEntity(EntityManager em) {
        Team team = new Team()
            .name(UPDATED_NAME)
            .teamGroupEmail(UPDATED_TEAM_GROUP_EMAIL)
            .type(UPDATED_TYPE)
            .notifyOnJobPostingToUsers(UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS)
            .notifyOnJobSharingToUsers(UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS)
            .notifyOnJobClosingToUsers(UPDATED_NOTIFY_ON_JOB_CLOSING_TO_USERS)
            .notifyOnCandSubmissionToUsers(UPDATED_NOTIFY_ON_CAND_SUBMISSION_TO_USERS)
            .notifyOnCandStatusChangeToUsers(UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);
        return team;
    }

    @BeforeEach
    public void initTest() {
        team = createEntity(em);
    }

    @Test
    @Transactional
    void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();
        // Create the Team
        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getTeamGroupEmail()).isEqualTo(DEFAULT_TEAM_GROUP_EMAIL);
        assertThat(testTeam.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTeam.getNotifyOnJobPostingToUsers()).isEqualTo(DEFAULT_NOTIFY_ON_JOB_POSTING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobSharingToUsers()).isEqualTo(DEFAULT_NOTIFY_ON_JOB_SHARING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobClosingToUsers()).isEqualTo(DEFAULT_NOTIFY_ON_JOB_CLOSING_TO_USERS);
        assertThat(testTeam.getNotifyOnCandSubmissionToUsers()).isEqualTo(DEFAULT_NOTIFY_ON_CAND_SUBMISSION_TO_USERS);
        assertThat(testTeam.getNotifyOnCandStatusChangeToUsers()).isEqualTo(DEFAULT_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);
    }

    @Test
    @Transactional
    void createTeamWithExistingId() throws Exception {
        // Create the Team with an existing ID
        team.setId(1L);

        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].teamGroupEmail").value(hasItem(DEFAULT_TEAM_GROUP_EMAIL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].notifyOnJobPostingToUsers").value(hasItem(DEFAULT_NOTIFY_ON_JOB_POSTING_TO_USERS)))
            .andExpect(jsonPath("$.[*].notifyOnJobSharingToUsers").value(hasItem(DEFAULT_NOTIFY_ON_JOB_SHARING_TO_USERS)))
            .andExpect(jsonPath("$.[*].notifyOnJobClosingToUsers").value(hasItem(DEFAULT_NOTIFY_ON_JOB_CLOSING_TO_USERS)))
            .andExpect(jsonPath("$.[*].notifyOnCandSubmissionToUsers").value(hasItem(DEFAULT_NOTIFY_ON_CAND_SUBMISSION_TO_USERS)))
            .andExpect(jsonPath("$.[*].notifyOnCandStatusChangeToUsers").value(hasItem(DEFAULT_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS)));
    }

    @Test
    @Transactional
    void getTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get the team
        restTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.teamGroupEmail").value(DEFAULT_TEAM_GROUP_EMAIL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.notifyOnJobPostingToUsers").value(DEFAULT_NOTIFY_ON_JOB_POSTING_TO_USERS))
            .andExpect(jsonPath("$.notifyOnJobSharingToUsers").value(DEFAULT_NOTIFY_ON_JOB_SHARING_TO_USERS))
            .andExpect(jsonPath("$.notifyOnJobClosingToUsers").value(DEFAULT_NOTIFY_ON_JOB_CLOSING_TO_USERS))
            .andExpect(jsonPath("$.notifyOnCandSubmissionToUsers").value(DEFAULT_NOTIFY_ON_CAND_SUBMISSION_TO_USERS))
            .andExpect(jsonPath("$.notifyOnCandStatusChangeToUsers").value(DEFAULT_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS));
    }

    @Test
    @Transactional
    void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findById(team.getId()).get();
        // Disconnect from session so that the updates on updatedTeam are not directly saved in db
        em.detach(updatedTeam);
        updatedTeam
            .name(UPDATED_NAME)
            .teamGroupEmail(UPDATED_TEAM_GROUP_EMAIL)
            .type(UPDATED_TYPE)
            .notifyOnJobPostingToUsers(UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS)
            .notifyOnJobSharingToUsers(UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS)
            .notifyOnJobClosingToUsers(UPDATED_NOTIFY_ON_JOB_CLOSING_TO_USERS)
            .notifyOnCandSubmissionToUsers(UPDATED_NOTIFY_ON_CAND_SUBMISSION_TO_USERS)
            .notifyOnCandStatusChangeToUsers(UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);

        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTeam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getTeamGroupEmail()).isEqualTo(UPDATED_TEAM_GROUP_EMAIL);
        assertThat(testTeam.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTeam.getNotifyOnJobPostingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobSharingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobClosingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_CLOSING_TO_USERS);
        assertThat(testTeam.getNotifyOnCandSubmissionToUsers()).isEqualTo(UPDATED_NOTIFY_ON_CAND_SUBMISSION_TO_USERS);
        assertThat(testTeam.getNotifyOnCandStatusChangeToUsers()).isEqualTo(UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);
    }

    @Test
    @Transactional
    void putNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, team.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam
            .type(UPDATED_TYPE)
            .notifyOnJobPostingToUsers(UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS)
            .notifyOnJobSharingToUsers(UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS)
            .notifyOnCandStatusChangeToUsers(UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getTeamGroupEmail()).isEqualTo(DEFAULT_TEAM_GROUP_EMAIL);
        assertThat(testTeam.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTeam.getNotifyOnJobPostingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobSharingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobClosingToUsers()).isEqualTo(DEFAULT_NOTIFY_ON_JOB_CLOSING_TO_USERS);
        assertThat(testTeam.getNotifyOnCandSubmissionToUsers()).isEqualTo(DEFAULT_NOTIFY_ON_CAND_SUBMISSION_TO_USERS);
        assertThat(testTeam.getNotifyOnCandStatusChangeToUsers()).isEqualTo(UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);
    }

    @Test
    @Transactional
    void fullUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam
            .name(UPDATED_NAME)
            .teamGroupEmail(UPDATED_TEAM_GROUP_EMAIL)
            .type(UPDATED_TYPE)
            .notifyOnJobPostingToUsers(UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS)
            .notifyOnJobSharingToUsers(UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS)
            .notifyOnJobClosingToUsers(UPDATED_NOTIFY_ON_JOB_CLOSING_TO_USERS)
            .notifyOnCandSubmissionToUsers(UPDATED_NOTIFY_ON_CAND_SUBMISSION_TO_USERS)
            .notifyOnCandStatusChangeToUsers(UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getTeamGroupEmail()).isEqualTo(UPDATED_TEAM_GROUP_EMAIL);
        assertThat(testTeam.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTeam.getNotifyOnJobPostingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_POSTING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobSharingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_SHARING_TO_USERS);
        assertThat(testTeam.getNotifyOnJobClosingToUsers()).isEqualTo(UPDATED_NOTIFY_ON_JOB_CLOSING_TO_USERS);
        assertThat(testTeam.getNotifyOnCandSubmissionToUsers()).isEqualTo(UPDATED_NOTIFY_ON_CAND_SUBMISSION_TO_USERS);
        assertThat(testTeam.getNotifyOnCandStatusChangeToUsers()).isEqualTo(UPDATED_NOTIFY_ON_CAND_STATUS_CHANGE_TO_USERS);
    }

    @Test
    @Transactional
    void patchNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, team.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(team))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, team.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
