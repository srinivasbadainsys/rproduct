package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.TeamWork;
import com.rp.repository.TeamWorkRepository;
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
 * Integration tests for the {@link TeamWorkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamWorkResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final Long DEFAULT_TEAM_ID = 1L;
    private static final Long UPDATED_TEAM_ID = 2L;

    private static final String ENTITY_API_URL = "/api/team-works";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamWorkRepository teamWorkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamWorkMockMvc;

    private TeamWork teamWork;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamWork createEntity(EntityManager em) {
        TeamWork teamWork = new TeamWork().jobId(DEFAULT_JOB_ID).teamId(DEFAULT_TEAM_ID);
        return teamWork;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamWork createUpdatedEntity(EntityManager em) {
        TeamWork teamWork = new TeamWork().jobId(UPDATED_JOB_ID).teamId(UPDATED_TEAM_ID);
        return teamWork;
    }

    @BeforeEach
    public void initTest() {
        teamWork = createEntity(em);
    }

    @Test
    @Transactional
    void createTeamWork() throws Exception {
        int databaseSizeBeforeCreate = teamWorkRepository.findAll().size();
        // Create the TeamWork
        restTeamWorkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamWork)))
            .andExpect(status().isCreated());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeCreate + 1);
        TeamWork testTeamWork = teamWorkList.get(teamWorkList.size() - 1);
        assertThat(testTeamWork.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testTeamWork.getTeamId()).isEqualTo(DEFAULT_TEAM_ID);
    }

    @Test
    @Transactional
    void createTeamWorkWithExistingId() throws Exception {
        // Create the TeamWork with an existing ID
        teamWork.setId(1L);

        int databaseSizeBeforeCreate = teamWorkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamWorkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamWork)))
            .andExpect(status().isBadRequest());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeamWorks() throws Exception {
        // Initialize the database
        teamWorkRepository.saveAndFlush(teamWork);

        // Get all the teamWorkList
        restTeamWorkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].teamId").value(hasItem(DEFAULT_TEAM_ID.intValue())));
    }

    @Test
    @Transactional
    void getTeamWork() throws Exception {
        // Initialize the database
        teamWorkRepository.saveAndFlush(teamWork);

        // Get the teamWork
        restTeamWorkMockMvc
            .perform(get(ENTITY_API_URL_ID, teamWork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamWork.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.teamId").value(DEFAULT_TEAM_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTeamWork() throws Exception {
        // Get the teamWork
        restTeamWorkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeamWork() throws Exception {
        // Initialize the database
        teamWorkRepository.saveAndFlush(teamWork);

        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();

        // Update the teamWork
        TeamWork updatedTeamWork = teamWorkRepository.findById(teamWork.getId()).get();
        // Disconnect from session so that the updates on updatedTeamWork are not directly saved in db
        em.detach(updatedTeamWork);
        updatedTeamWork.jobId(UPDATED_JOB_ID).teamId(UPDATED_TEAM_ID);

        restTeamWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTeamWork.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTeamWork))
            )
            .andExpect(status().isOk());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
        TeamWork testTeamWork = teamWorkList.get(teamWorkList.size() - 1);
        assertThat(testTeamWork.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testTeamWork.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
    }

    @Test
    @Transactional
    void putNonExistingTeamWork() throws Exception {
        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();
        teamWork.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamWork.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeamWork() throws Exception {
        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();
        teamWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeamWork() throws Exception {
        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();
        teamWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamWorkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamWork)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamWorkWithPatch() throws Exception {
        // Initialize the database
        teamWorkRepository.saveAndFlush(teamWork);

        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();

        // Update the teamWork using partial update
        TeamWork partialUpdatedTeamWork = new TeamWork();
        partialUpdatedTeamWork.setId(teamWork.getId());

        partialUpdatedTeamWork.teamId(UPDATED_TEAM_ID);

        restTeamWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamWork))
            )
            .andExpect(status().isOk());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
        TeamWork testTeamWork = teamWorkList.get(teamWorkList.size() - 1);
        assertThat(testTeamWork.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testTeamWork.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
    }

    @Test
    @Transactional
    void fullUpdateTeamWorkWithPatch() throws Exception {
        // Initialize the database
        teamWorkRepository.saveAndFlush(teamWork);

        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();

        // Update the teamWork using partial update
        TeamWork partialUpdatedTeamWork = new TeamWork();
        partialUpdatedTeamWork.setId(teamWork.getId());

        partialUpdatedTeamWork.jobId(UPDATED_JOB_ID).teamId(UPDATED_TEAM_ID);

        restTeamWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamWork))
            )
            .andExpect(status().isOk());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
        TeamWork testTeamWork = teamWorkList.get(teamWorkList.size() - 1);
        assertThat(testTeamWork.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testTeamWork.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTeamWork() throws Exception {
        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();
        teamWork.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeamWork() throws Exception {
        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();
        teamWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeamWork() throws Exception {
        int databaseSizeBeforeUpdate = teamWorkRepository.findAll().size();
        teamWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamWorkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teamWork)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamWork in the database
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeamWork() throws Exception {
        // Initialize the database
        teamWorkRepository.saveAndFlush(teamWork);

        int databaseSizeBeforeDelete = teamWorkRepository.findAll().size();

        // Delete the teamWork
        restTeamWorkMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamWork.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamWork> teamWorkList = teamWorkRepository.findAll();
        assertThat(teamWorkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
