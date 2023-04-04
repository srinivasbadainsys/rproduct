package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.JobWork;
import com.rp.repository.JobWorkRepository;
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
 * Integration tests for the {@link JobWorkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobWorkResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final String DEFAULT_ASSIGNED_TO_TEAMS = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_TO_TEAMS = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_TO_USERS = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_TO_USERS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-works";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobWorkRepository jobWorkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobWorkMockMvc;

    private JobWork jobWork;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobWork createEntity(EntityManager em) {
        JobWork jobWork = new JobWork()
            .jobId(DEFAULT_JOB_ID)
            .assignedToTeams(DEFAULT_ASSIGNED_TO_TEAMS)
            .assignedToUsers(DEFAULT_ASSIGNED_TO_USERS);
        return jobWork;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobWork createUpdatedEntity(EntityManager em) {
        JobWork jobWork = new JobWork()
            .jobId(UPDATED_JOB_ID)
            .assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS)
            .assignedToUsers(UPDATED_ASSIGNED_TO_USERS);
        return jobWork;
    }

    @BeforeEach
    public void initTest() {
        jobWork = createEntity(em);
    }

    @Test
    @Transactional
    void createJobWork() throws Exception {
        int databaseSizeBeforeCreate = jobWorkRepository.findAll().size();
        // Create the JobWork
        restJobWorkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobWork)))
            .andExpect(status().isCreated());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeCreate + 1);
        JobWork testJobWork = jobWorkList.get(jobWorkList.size() - 1);
        assertThat(testJobWork.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobWork.getAssignedToTeams()).isEqualTo(DEFAULT_ASSIGNED_TO_TEAMS);
        assertThat(testJobWork.getAssignedToUsers()).isEqualTo(DEFAULT_ASSIGNED_TO_USERS);
    }

    @Test
    @Transactional
    void createJobWorkWithExistingId() throws Exception {
        // Create the JobWork with an existing ID
        jobWork.setId(1L);

        int databaseSizeBeforeCreate = jobWorkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobWorkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobWork)))
            .andExpect(status().isBadRequest());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobWorks() throws Exception {
        // Initialize the database
        jobWorkRepository.saveAndFlush(jobWork);

        // Get all the jobWorkList
        restJobWorkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].assignedToTeams").value(hasItem(DEFAULT_ASSIGNED_TO_TEAMS)))
            .andExpect(jsonPath("$.[*].assignedToUsers").value(hasItem(DEFAULT_ASSIGNED_TO_USERS)));
    }

    @Test
    @Transactional
    void getJobWork() throws Exception {
        // Initialize the database
        jobWorkRepository.saveAndFlush(jobWork);

        // Get the jobWork
        restJobWorkMockMvc
            .perform(get(ENTITY_API_URL_ID, jobWork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobWork.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.assignedToTeams").value(DEFAULT_ASSIGNED_TO_TEAMS))
            .andExpect(jsonPath("$.assignedToUsers").value(DEFAULT_ASSIGNED_TO_USERS));
    }

    @Test
    @Transactional
    void getNonExistingJobWork() throws Exception {
        // Get the jobWork
        restJobWorkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobWork() throws Exception {
        // Initialize the database
        jobWorkRepository.saveAndFlush(jobWork);

        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();

        // Update the jobWork
        JobWork updatedJobWork = jobWorkRepository.findById(jobWork.getId()).get();
        // Disconnect from session so that the updates on updatedJobWork are not directly saved in db
        em.detach(updatedJobWork);
        updatedJobWork.jobId(UPDATED_JOB_ID).assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS).assignedToUsers(UPDATED_ASSIGNED_TO_USERS);

        restJobWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobWork.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobWork))
            )
            .andExpect(status().isOk());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
        JobWork testJobWork = jobWorkList.get(jobWorkList.size() - 1);
        assertThat(testJobWork.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobWork.getAssignedToTeams()).isEqualTo(UPDATED_ASSIGNED_TO_TEAMS);
        assertThat(testJobWork.getAssignedToUsers()).isEqualTo(UPDATED_ASSIGNED_TO_USERS);
    }

    @Test
    @Transactional
    void putNonExistingJobWork() throws Exception {
        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();
        jobWork.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobWork.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobWork() throws Exception {
        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();
        jobWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobWork() throws Exception {
        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();
        jobWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobWorkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobWork)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobWorkWithPatch() throws Exception {
        // Initialize the database
        jobWorkRepository.saveAndFlush(jobWork);

        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();

        // Update the jobWork using partial update
        JobWork partialUpdatedJobWork = new JobWork();
        partialUpdatedJobWork.setId(jobWork.getId());

        partialUpdatedJobWork.jobId(UPDATED_JOB_ID).assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS).assignedToUsers(UPDATED_ASSIGNED_TO_USERS);

        restJobWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobWork))
            )
            .andExpect(status().isOk());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
        JobWork testJobWork = jobWorkList.get(jobWorkList.size() - 1);
        assertThat(testJobWork.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobWork.getAssignedToTeams()).isEqualTo(UPDATED_ASSIGNED_TO_TEAMS);
        assertThat(testJobWork.getAssignedToUsers()).isEqualTo(UPDATED_ASSIGNED_TO_USERS);
    }

    @Test
    @Transactional
    void fullUpdateJobWorkWithPatch() throws Exception {
        // Initialize the database
        jobWorkRepository.saveAndFlush(jobWork);

        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();

        // Update the jobWork using partial update
        JobWork partialUpdatedJobWork = new JobWork();
        partialUpdatedJobWork.setId(jobWork.getId());

        partialUpdatedJobWork.jobId(UPDATED_JOB_ID).assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS).assignedToUsers(UPDATED_ASSIGNED_TO_USERS);

        restJobWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobWork))
            )
            .andExpect(status().isOk());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
        JobWork testJobWork = jobWorkList.get(jobWorkList.size() - 1);
        assertThat(testJobWork.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobWork.getAssignedToTeams()).isEqualTo(UPDATED_ASSIGNED_TO_TEAMS);
        assertThat(testJobWork.getAssignedToUsers()).isEqualTo(UPDATED_ASSIGNED_TO_USERS);
    }

    @Test
    @Transactional
    void patchNonExistingJobWork() throws Exception {
        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();
        jobWork.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobWork() throws Exception {
        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();
        jobWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobWork() throws Exception {
        int databaseSizeBeforeUpdate = jobWorkRepository.findAll().size();
        jobWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobWorkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobWork)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobWork in the database
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobWork() throws Exception {
        // Initialize the database
        jobWorkRepository.saveAndFlush(jobWork);

        int databaseSizeBeforeDelete = jobWorkRepository.findAll().size();

        // Delete the jobWork
        restJobWorkMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobWork.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobWork> jobWorkList = jobWorkRepository.findAll();
        assertThat(jobWorkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
