package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.TeamMember;
import com.rp.repository.TeamMemberRepository;
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
 * Integration tests for the {@link TeamMemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamMemberResourceIT {

    private static final Long DEFAULT_TEAM_ID = 1L;
    private static final Long UPDATED_TEAM_ID = 2L;

    private static final String ENTITY_API_URL = "/api/team-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamMemberMockMvc;

    private TeamMember teamMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamMember createEntity(EntityManager em) {
        TeamMember teamMember = new TeamMember().teamId(DEFAULT_TEAM_ID);
        return teamMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamMember createUpdatedEntity(EntityManager em) {
        TeamMember teamMember = new TeamMember().teamId(UPDATED_TEAM_ID);
        return teamMember;
    }

    @BeforeEach
    public void initTest() {
        teamMember = createEntity(em);
    }

    @Test
    @Transactional
    void createTeamMember() throws Exception {
        int databaseSizeBeforeCreate = teamMemberRepository.findAll().size();
        // Create the TeamMember
        restTeamMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamMember)))
            .andExpect(status().isCreated());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeCreate + 1);
        TeamMember testTeamMember = teamMemberList.get(teamMemberList.size() - 1);
        assertThat(testTeamMember.getTeamId()).isEqualTo(DEFAULT_TEAM_ID);
    }

    @Test
    @Transactional
    void createTeamMemberWithExistingId() throws Exception {
        // Create the TeamMember with an existing ID
        teamMember.setId(1L);

        int databaseSizeBeforeCreate = teamMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamMember)))
            .andExpect(status().isBadRequest());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeamMembers() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        // Get all the teamMemberList
        restTeamMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamId").value(hasItem(DEFAULT_TEAM_ID.intValue())));
    }

    @Test
    @Transactional
    void getTeamMember() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        // Get the teamMember
        restTeamMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, teamMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamMember.getId().intValue()))
            .andExpect(jsonPath("$.teamId").value(DEFAULT_TEAM_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTeamMember() throws Exception {
        // Get the teamMember
        restTeamMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeamMember() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();

        // Update the teamMember
        TeamMember updatedTeamMember = teamMemberRepository.findById(teamMember.getId()).get();
        // Disconnect from session so that the updates on updatedTeamMember are not directly saved in db
        em.detach(updatedTeamMember);
        updatedTeamMember.teamId(UPDATED_TEAM_ID);

        restTeamMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTeamMember.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTeamMember))
            )
            .andExpect(status().isOk());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
        TeamMember testTeamMember = teamMemberList.get(teamMemberList.size() - 1);
        assertThat(testTeamMember.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
    }

    @Test
    @Transactional
    void putNonExistingTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();
        teamMember.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamMember.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();
        teamMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();
        teamMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMemberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamMember)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamMemberWithPatch() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();

        // Update the teamMember using partial update
        TeamMember partialUpdatedTeamMember = new TeamMember();
        partialUpdatedTeamMember.setId(teamMember.getId());

        restTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamMember))
            )
            .andExpect(status().isOk());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
        TeamMember testTeamMember = teamMemberList.get(teamMemberList.size() - 1);
        assertThat(testTeamMember.getTeamId()).isEqualTo(DEFAULT_TEAM_ID);
    }

    @Test
    @Transactional
    void fullUpdateTeamMemberWithPatch() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();

        // Update the teamMember using partial update
        TeamMember partialUpdatedTeamMember = new TeamMember();
        partialUpdatedTeamMember.setId(teamMember.getId());

        partialUpdatedTeamMember.teamId(UPDATED_TEAM_ID);

        restTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamMember))
            )
            .andExpect(status().isOk());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
        TeamMember testTeamMember = teamMemberList.get(teamMemberList.size() - 1);
        assertThat(testTeamMember.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();
        teamMember.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();
        teamMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();
        teamMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teamMember))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeamMember() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        int databaseSizeBeforeDelete = teamMemberRepository.findAll().size();

        // Delete the teamMember
        restTeamMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamMember.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
