package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.JobBoard;
import com.rp.repository.JobBoardRepository;
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
 * Integration tests for the {@link JobBoardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobBoardResourceIT {

    private static final String DEFAULT_JOB_BOARD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_BOARD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_BOARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_BOARD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_SETTINGS = "AAAAAAAAAA";
    private static final String UPDATED_SETTINGS = "BBBBBBBBBB";

    private static final String DEFAULT_AUTO_REFRESH = "AAAAAAAAAA";
    private static final String UPDATED_AUTO_REFRESH = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_JOB_DURATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-boards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobBoardRepository jobBoardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobBoardMockMvc;

    private JobBoard jobBoard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobBoard createEntity(EntityManager em) {
        JobBoard jobBoard = new JobBoard()
            .jobBoardName(DEFAULT_JOB_BOARD_NAME)
            .jobBoardType(DEFAULT_JOB_BOARD_TYPE)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .settings(DEFAULT_SETTINGS)
            .autoRefresh(DEFAULT_AUTO_REFRESH)
            .jobDuration(DEFAULT_JOB_DURATION);
        return jobBoard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobBoard createUpdatedEntity(EntityManager em) {
        JobBoard jobBoard = new JobBoard()
            .jobBoardName(UPDATED_JOB_BOARD_NAME)
            .jobBoardType(UPDATED_JOB_BOARD_TYPE)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .settings(UPDATED_SETTINGS)
            .autoRefresh(UPDATED_AUTO_REFRESH)
            .jobDuration(UPDATED_JOB_DURATION);
        return jobBoard;
    }

    @BeforeEach
    public void initTest() {
        jobBoard = createEntity(em);
    }

    @Test
    @Transactional
    void createJobBoard() throws Exception {
        int databaseSizeBeforeCreate = jobBoardRepository.findAll().size();
        // Create the JobBoard
        restJobBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoard)))
            .andExpect(status().isCreated());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeCreate + 1);
        JobBoard testJobBoard = jobBoardList.get(jobBoardList.size() - 1);
        assertThat(testJobBoard.getJobBoardName()).isEqualTo(DEFAULT_JOB_BOARD_NAME);
        assertThat(testJobBoard.getJobBoardType()).isEqualTo(DEFAULT_JOB_BOARD_TYPE);
        assertThat(testJobBoard.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testJobBoard.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testJobBoard.getSettings()).isEqualTo(DEFAULT_SETTINGS);
        assertThat(testJobBoard.getAutoRefresh()).isEqualTo(DEFAULT_AUTO_REFRESH);
        assertThat(testJobBoard.getJobDuration()).isEqualTo(DEFAULT_JOB_DURATION);
    }

    @Test
    @Transactional
    void createJobBoardWithExistingId() throws Exception {
        // Create the JobBoard with an existing ID
        jobBoard.setId(1L);

        int databaseSizeBeforeCreate = jobBoardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoard)))
            .andExpect(status().isBadRequest());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobBoards() throws Exception {
        // Initialize the database
        jobBoardRepository.saveAndFlush(jobBoard);

        // Get all the jobBoardList
        restJobBoardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobBoardName").value(hasItem(DEFAULT_JOB_BOARD_NAME)))
            .andExpect(jsonPath("$.[*].jobBoardType").value(hasItem(DEFAULT_JOB_BOARD_TYPE)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].settings").value(hasItem(DEFAULT_SETTINGS)))
            .andExpect(jsonPath("$.[*].autoRefresh").value(hasItem(DEFAULT_AUTO_REFRESH)))
            .andExpect(jsonPath("$.[*].jobDuration").value(hasItem(DEFAULT_JOB_DURATION)));
    }

    @Test
    @Transactional
    void getJobBoard() throws Exception {
        // Initialize the database
        jobBoardRepository.saveAndFlush(jobBoard);

        // Get the jobBoard
        restJobBoardMockMvc
            .perform(get(ENTITY_API_URL_ID, jobBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobBoard.getId().intValue()))
            .andExpect(jsonPath("$.jobBoardName").value(DEFAULT_JOB_BOARD_NAME))
            .andExpect(jsonPath("$.jobBoardType").value(DEFAULT_JOB_BOARD_TYPE))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.settings").value(DEFAULT_SETTINGS))
            .andExpect(jsonPath("$.autoRefresh").value(DEFAULT_AUTO_REFRESH))
            .andExpect(jsonPath("$.jobDuration").value(DEFAULT_JOB_DURATION));
    }

    @Test
    @Transactional
    void getNonExistingJobBoard() throws Exception {
        // Get the jobBoard
        restJobBoardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobBoard() throws Exception {
        // Initialize the database
        jobBoardRepository.saveAndFlush(jobBoard);

        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();

        // Update the jobBoard
        JobBoard updatedJobBoard = jobBoardRepository.findById(jobBoard.getId()).get();
        // Disconnect from session so that the updates on updatedJobBoard are not directly saved in db
        em.detach(updatedJobBoard);
        updatedJobBoard
            .jobBoardName(UPDATED_JOB_BOARD_NAME)
            .jobBoardType(UPDATED_JOB_BOARD_TYPE)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .settings(UPDATED_SETTINGS)
            .autoRefresh(UPDATED_AUTO_REFRESH)
            .jobDuration(UPDATED_JOB_DURATION);

        restJobBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobBoard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobBoard))
            )
            .andExpect(status().isOk());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
        JobBoard testJobBoard = jobBoardList.get(jobBoardList.size() - 1);
        assertThat(testJobBoard.getJobBoardName()).isEqualTo(UPDATED_JOB_BOARD_NAME);
        assertThat(testJobBoard.getJobBoardType()).isEqualTo(UPDATED_JOB_BOARD_TYPE);
        assertThat(testJobBoard.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testJobBoard.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testJobBoard.getSettings()).isEqualTo(UPDATED_SETTINGS);
        assertThat(testJobBoard.getAutoRefresh()).isEqualTo(UPDATED_AUTO_REFRESH);
        assertThat(testJobBoard.getJobDuration()).isEqualTo(UPDATED_JOB_DURATION);
    }

    @Test
    @Transactional
    void putNonExistingJobBoard() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();
        jobBoard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobBoard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobBoard() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();
        jobBoard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobBoard() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();
        jobBoard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobBoardWithPatch() throws Exception {
        // Initialize the database
        jobBoardRepository.saveAndFlush(jobBoard);

        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();

        // Update the jobBoard using partial update
        JobBoard partialUpdatedJobBoard = new JobBoard();
        partialUpdatedJobBoard.setId(jobBoard.getId());

        partialUpdatedJobBoard
            .jobBoardType(UPDATED_JOB_BOARD_TYPE)
            .username(UPDATED_USERNAME)
            .autoRefresh(UPDATED_AUTO_REFRESH)
            .jobDuration(UPDATED_JOB_DURATION);

        restJobBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobBoard))
            )
            .andExpect(status().isOk());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
        JobBoard testJobBoard = jobBoardList.get(jobBoardList.size() - 1);
        assertThat(testJobBoard.getJobBoardName()).isEqualTo(DEFAULT_JOB_BOARD_NAME);
        assertThat(testJobBoard.getJobBoardType()).isEqualTo(UPDATED_JOB_BOARD_TYPE);
        assertThat(testJobBoard.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testJobBoard.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testJobBoard.getSettings()).isEqualTo(DEFAULT_SETTINGS);
        assertThat(testJobBoard.getAutoRefresh()).isEqualTo(UPDATED_AUTO_REFRESH);
        assertThat(testJobBoard.getJobDuration()).isEqualTo(UPDATED_JOB_DURATION);
    }

    @Test
    @Transactional
    void fullUpdateJobBoardWithPatch() throws Exception {
        // Initialize the database
        jobBoardRepository.saveAndFlush(jobBoard);

        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();

        // Update the jobBoard using partial update
        JobBoard partialUpdatedJobBoard = new JobBoard();
        partialUpdatedJobBoard.setId(jobBoard.getId());

        partialUpdatedJobBoard
            .jobBoardName(UPDATED_JOB_BOARD_NAME)
            .jobBoardType(UPDATED_JOB_BOARD_TYPE)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .settings(UPDATED_SETTINGS)
            .autoRefresh(UPDATED_AUTO_REFRESH)
            .jobDuration(UPDATED_JOB_DURATION);

        restJobBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobBoard))
            )
            .andExpect(status().isOk());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
        JobBoard testJobBoard = jobBoardList.get(jobBoardList.size() - 1);
        assertThat(testJobBoard.getJobBoardName()).isEqualTo(UPDATED_JOB_BOARD_NAME);
        assertThat(testJobBoard.getJobBoardType()).isEqualTo(UPDATED_JOB_BOARD_TYPE);
        assertThat(testJobBoard.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testJobBoard.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testJobBoard.getSettings()).isEqualTo(UPDATED_SETTINGS);
        assertThat(testJobBoard.getAutoRefresh()).isEqualTo(UPDATED_AUTO_REFRESH);
        assertThat(testJobBoard.getJobDuration()).isEqualTo(UPDATED_JOB_DURATION);
    }

    @Test
    @Transactional
    void patchNonExistingJobBoard() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();
        jobBoard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobBoard() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();
        jobBoard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobBoard))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobBoard() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardRepository.findAll().size();
        jobBoard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobBoard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobBoard in the database
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobBoard() throws Exception {
        // Initialize the database
        jobBoardRepository.saveAndFlush(jobBoard);

        int databaseSizeBeforeDelete = jobBoardRepository.findAll().size();

        // Delete the jobBoard
        restJobBoardMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobBoard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobBoard> jobBoardList = jobBoardRepository.findAll();
        assertThat(jobBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
