package com.rp.web.rest;

import static com.rp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.JobBoardSharedTo;
import com.rp.repository.JobBoardSharedToRepository;
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
 * Integration tests for the {@link JobBoardSharedToResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobBoardSharedToResourceIT {

    private static final Long DEFAULT_JOB_BOARD_ID = 1L;
    private static final Long UPDATED_JOB_BOARD_ID = 2L;

    private static final String DEFAULT_SHARED_TO_EMAILS = "AAAAAAAAAA";
    private static final String UPDATED_SHARED_TO_EMAILS = "BBBBBBBBBB";

    private static final String DEFAULT_SHARED_TO_USER_IDS = "AAAAAAAAAA";
    private static final String UPDATED_SHARED_TO_USER_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_SHARED_TO_TEAM_IDS = "AAAAAAAAAA";
    private static final String UPDATED_SHARED_TO_TEAM_IDS = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_EXAMILS_MONTHLY = 1;
    private static final Integer UPDATED_MAX_EXAMILS_MONTHLY = 2;

    private static final ZonedDateTime DEFAULT_EXPIRES_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRES_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/job-board-shared-tos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobBoardSharedToRepository jobBoardSharedToRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobBoardSharedToMockMvc;

    private JobBoardSharedTo jobBoardSharedTo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobBoardSharedTo createEntity(EntityManager em) {
        JobBoardSharedTo jobBoardSharedTo = new JobBoardSharedTo()
            .jobBoardId(DEFAULT_JOB_BOARD_ID)
            .sharedToEmails(DEFAULT_SHARED_TO_EMAILS)
            .sharedToUserIds(DEFAULT_SHARED_TO_USER_IDS)
            .sharedToTeamIds(DEFAULT_SHARED_TO_TEAM_IDS)
            .maxExamilsMonthly(DEFAULT_MAX_EXAMILS_MONTHLY)
            .expiresOn(DEFAULT_EXPIRES_ON);
        return jobBoardSharedTo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobBoardSharedTo createUpdatedEntity(EntityManager em) {
        JobBoardSharedTo jobBoardSharedTo = new JobBoardSharedTo()
            .jobBoardId(UPDATED_JOB_BOARD_ID)
            .sharedToEmails(UPDATED_SHARED_TO_EMAILS)
            .sharedToUserIds(UPDATED_SHARED_TO_USER_IDS)
            .sharedToTeamIds(UPDATED_SHARED_TO_TEAM_IDS)
            .maxExamilsMonthly(UPDATED_MAX_EXAMILS_MONTHLY)
            .expiresOn(UPDATED_EXPIRES_ON);
        return jobBoardSharedTo;
    }

    @BeforeEach
    public void initTest() {
        jobBoardSharedTo = createEntity(em);
    }

    @Test
    @Transactional
    void createJobBoardSharedTo() throws Exception {
        int databaseSizeBeforeCreate = jobBoardSharedToRepository.findAll().size();
        // Create the JobBoardSharedTo
        restJobBoardSharedToMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isCreated());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeCreate + 1);
        JobBoardSharedTo testJobBoardSharedTo = jobBoardSharedToList.get(jobBoardSharedToList.size() - 1);
        assertThat(testJobBoardSharedTo.getJobBoardId()).isEqualTo(DEFAULT_JOB_BOARD_ID);
        assertThat(testJobBoardSharedTo.getSharedToEmails()).isEqualTo(DEFAULT_SHARED_TO_EMAILS);
        assertThat(testJobBoardSharedTo.getSharedToUserIds()).isEqualTo(DEFAULT_SHARED_TO_USER_IDS);
        assertThat(testJobBoardSharedTo.getSharedToTeamIds()).isEqualTo(DEFAULT_SHARED_TO_TEAM_IDS);
        assertThat(testJobBoardSharedTo.getMaxExamilsMonthly()).isEqualTo(DEFAULT_MAX_EXAMILS_MONTHLY);
        assertThat(testJobBoardSharedTo.getExpiresOn()).isEqualTo(DEFAULT_EXPIRES_ON);
    }

    @Test
    @Transactional
    void createJobBoardSharedToWithExistingId() throws Exception {
        // Create the JobBoardSharedTo with an existing ID
        jobBoardSharedTo.setId(1L);

        int databaseSizeBeforeCreate = jobBoardSharedToRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobBoardSharedToMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobBoardSharedTos() throws Exception {
        // Initialize the database
        jobBoardSharedToRepository.saveAndFlush(jobBoardSharedTo);

        // Get all the jobBoardSharedToList
        restJobBoardSharedToMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobBoardSharedTo.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobBoardId").value(hasItem(DEFAULT_JOB_BOARD_ID.intValue())))
            .andExpect(jsonPath("$.[*].sharedToEmails").value(hasItem(DEFAULT_SHARED_TO_EMAILS)))
            .andExpect(jsonPath("$.[*].sharedToUserIds").value(hasItem(DEFAULT_SHARED_TO_USER_IDS)))
            .andExpect(jsonPath("$.[*].sharedToTeamIds").value(hasItem(DEFAULT_SHARED_TO_TEAM_IDS)))
            .andExpect(jsonPath("$.[*].maxExamilsMonthly").value(hasItem(DEFAULT_MAX_EXAMILS_MONTHLY)))
            .andExpect(jsonPath("$.[*].expiresOn").value(hasItem(sameInstant(DEFAULT_EXPIRES_ON))));
    }

    @Test
    @Transactional
    void getJobBoardSharedTo() throws Exception {
        // Initialize the database
        jobBoardSharedToRepository.saveAndFlush(jobBoardSharedTo);

        // Get the jobBoardSharedTo
        restJobBoardSharedToMockMvc
            .perform(get(ENTITY_API_URL_ID, jobBoardSharedTo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobBoardSharedTo.getId().intValue()))
            .andExpect(jsonPath("$.jobBoardId").value(DEFAULT_JOB_BOARD_ID.intValue()))
            .andExpect(jsonPath("$.sharedToEmails").value(DEFAULT_SHARED_TO_EMAILS))
            .andExpect(jsonPath("$.sharedToUserIds").value(DEFAULT_SHARED_TO_USER_IDS))
            .andExpect(jsonPath("$.sharedToTeamIds").value(DEFAULT_SHARED_TO_TEAM_IDS))
            .andExpect(jsonPath("$.maxExamilsMonthly").value(DEFAULT_MAX_EXAMILS_MONTHLY))
            .andExpect(jsonPath("$.expiresOn").value(sameInstant(DEFAULT_EXPIRES_ON)));
    }

    @Test
    @Transactional
    void getNonExistingJobBoardSharedTo() throws Exception {
        // Get the jobBoardSharedTo
        restJobBoardSharedToMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobBoardSharedTo() throws Exception {
        // Initialize the database
        jobBoardSharedToRepository.saveAndFlush(jobBoardSharedTo);

        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();

        // Update the jobBoardSharedTo
        JobBoardSharedTo updatedJobBoardSharedTo = jobBoardSharedToRepository.findById(jobBoardSharedTo.getId()).get();
        // Disconnect from session so that the updates on updatedJobBoardSharedTo are not directly saved in db
        em.detach(updatedJobBoardSharedTo);
        updatedJobBoardSharedTo
            .jobBoardId(UPDATED_JOB_BOARD_ID)
            .sharedToEmails(UPDATED_SHARED_TO_EMAILS)
            .sharedToUserIds(UPDATED_SHARED_TO_USER_IDS)
            .sharedToTeamIds(UPDATED_SHARED_TO_TEAM_IDS)
            .maxExamilsMonthly(UPDATED_MAX_EXAMILS_MONTHLY)
            .expiresOn(UPDATED_EXPIRES_ON);

        restJobBoardSharedToMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobBoardSharedTo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobBoardSharedTo))
            )
            .andExpect(status().isOk());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
        JobBoardSharedTo testJobBoardSharedTo = jobBoardSharedToList.get(jobBoardSharedToList.size() - 1);
        assertThat(testJobBoardSharedTo.getJobBoardId()).isEqualTo(UPDATED_JOB_BOARD_ID);
        assertThat(testJobBoardSharedTo.getSharedToEmails()).isEqualTo(UPDATED_SHARED_TO_EMAILS);
        assertThat(testJobBoardSharedTo.getSharedToUserIds()).isEqualTo(UPDATED_SHARED_TO_USER_IDS);
        assertThat(testJobBoardSharedTo.getSharedToTeamIds()).isEqualTo(UPDATED_SHARED_TO_TEAM_IDS);
        assertThat(testJobBoardSharedTo.getMaxExamilsMonthly()).isEqualTo(UPDATED_MAX_EXAMILS_MONTHLY);
        assertThat(testJobBoardSharedTo.getExpiresOn()).isEqualTo(UPDATED_EXPIRES_ON);
    }

    @Test
    @Transactional
    void putNonExistingJobBoardSharedTo() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();
        jobBoardSharedTo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobBoardSharedToMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobBoardSharedTo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobBoardSharedTo() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();
        jobBoardSharedTo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardSharedToMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobBoardSharedTo() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();
        jobBoardSharedTo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardSharedToMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobBoardSharedToWithPatch() throws Exception {
        // Initialize the database
        jobBoardSharedToRepository.saveAndFlush(jobBoardSharedTo);

        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();

        // Update the jobBoardSharedTo using partial update
        JobBoardSharedTo partialUpdatedJobBoardSharedTo = new JobBoardSharedTo();
        partialUpdatedJobBoardSharedTo.setId(jobBoardSharedTo.getId());

        partialUpdatedJobBoardSharedTo
            .sharedToUserIds(UPDATED_SHARED_TO_USER_IDS)
            .sharedToTeamIds(UPDATED_SHARED_TO_TEAM_IDS)
            .expiresOn(UPDATED_EXPIRES_ON);

        restJobBoardSharedToMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobBoardSharedTo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobBoardSharedTo))
            )
            .andExpect(status().isOk());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
        JobBoardSharedTo testJobBoardSharedTo = jobBoardSharedToList.get(jobBoardSharedToList.size() - 1);
        assertThat(testJobBoardSharedTo.getJobBoardId()).isEqualTo(DEFAULT_JOB_BOARD_ID);
        assertThat(testJobBoardSharedTo.getSharedToEmails()).isEqualTo(DEFAULT_SHARED_TO_EMAILS);
        assertThat(testJobBoardSharedTo.getSharedToUserIds()).isEqualTo(UPDATED_SHARED_TO_USER_IDS);
        assertThat(testJobBoardSharedTo.getSharedToTeamIds()).isEqualTo(UPDATED_SHARED_TO_TEAM_IDS);
        assertThat(testJobBoardSharedTo.getMaxExamilsMonthly()).isEqualTo(DEFAULT_MAX_EXAMILS_MONTHLY);
        assertThat(testJobBoardSharedTo.getExpiresOn()).isEqualTo(UPDATED_EXPIRES_ON);
    }

    @Test
    @Transactional
    void fullUpdateJobBoardSharedToWithPatch() throws Exception {
        // Initialize the database
        jobBoardSharedToRepository.saveAndFlush(jobBoardSharedTo);

        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();

        // Update the jobBoardSharedTo using partial update
        JobBoardSharedTo partialUpdatedJobBoardSharedTo = new JobBoardSharedTo();
        partialUpdatedJobBoardSharedTo.setId(jobBoardSharedTo.getId());

        partialUpdatedJobBoardSharedTo
            .jobBoardId(UPDATED_JOB_BOARD_ID)
            .sharedToEmails(UPDATED_SHARED_TO_EMAILS)
            .sharedToUserIds(UPDATED_SHARED_TO_USER_IDS)
            .sharedToTeamIds(UPDATED_SHARED_TO_TEAM_IDS)
            .maxExamilsMonthly(UPDATED_MAX_EXAMILS_MONTHLY)
            .expiresOn(UPDATED_EXPIRES_ON);

        restJobBoardSharedToMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobBoardSharedTo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobBoardSharedTo))
            )
            .andExpect(status().isOk());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
        JobBoardSharedTo testJobBoardSharedTo = jobBoardSharedToList.get(jobBoardSharedToList.size() - 1);
        assertThat(testJobBoardSharedTo.getJobBoardId()).isEqualTo(UPDATED_JOB_BOARD_ID);
        assertThat(testJobBoardSharedTo.getSharedToEmails()).isEqualTo(UPDATED_SHARED_TO_EMAILS);
        assertThat(testJobBoardSharedTo.getSharedToUserIds()).isEqualTo(UPDATED_SHARED_TO_USER_IDS);
        assertThat(testJobBoardSharedTo.getSharedToTeamIds()).isEqualTo(UPDATED_SHARED_TO_TEAM_IDS);
        assertThat(testJobBoardSharedTo.getMaxExamilsMonthly()).isEqualTo(UPDATED_MAX_EXAMILS_MONTHLY);
        assertThat(testJobBoardSharedTo.getExpiresOn()).isEqualTo(UPDATED_EXPIRES_ON);
    }

    @Test
    @Transactional
    void patchNonExistingJobBoardSharedTo() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();
        jobBoardSharedTo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobBoardSharedToMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobBoardSharedTo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobBoardSharedTo() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();
        jobBoardSharedTo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardSharedToMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobBoardSharedTo() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardSharedToRepository.findAll().size();
        jobBoardSharedTo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardSharedToMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardSharedTo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobBoardSharedTo in the database
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobBoardSharedTo() throws Exception {
        // Initialize the database
        jobBoardSharedToRepository.saveAndFlush(jobBoardSharedTo);

        int databaseSizeBeforeDelete = jobBoardSharedToRepository.findAll().size();

        // Delete the jobBoardSharedTo
        restJobBoardSharedToMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobBoardSharedTo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobBoardSharedTo> jobBoardSharedToList = jobBoardSharedToRepository.findAll();
        assertThat(jobBoardSharedToList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
