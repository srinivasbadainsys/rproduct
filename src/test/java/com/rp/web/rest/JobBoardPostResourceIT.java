package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.JobBoardPost;
import com.rp.domain.enumeration.JobBoardPostStatus;
import com.rp.repository.JobBoardPostRepository;
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
 * Integration tests for the {@link JobBoardPostResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobBoardPostResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final JobBoardPostStatus DEFAULT_STATUS = JobBoardPostStatus.Not_Posted;
    private static final JobBoardPostStatus UPDATED_STATUS = JobBoardPostStatus.Posted;

    private static final String ENTITY_API_URL = "/api/job-board-posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobBoardPostRepository jobBoardPostRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobBoardPostMockMvc;

    private JobBoardPost jobBoardPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobBoardPost createEntity(EntityManager em) {
        JobBoardPost jobBoardPost = new JobBoardPost().jobId(DEFAULT_JOB_ID).status(DEFAULT_STATUS);
        return jobBoardPost;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobBoardPost createUpdatedEntity(EntityManager em) {
        JobBoardPost jobBoardPost = new JobBoardPost().jobId(UPDATED_JOB_ID).status(UPDATED_STATUS);
        return jobBoardPost;
    }

    @BeforeEach
    public void initTest() {
        jobBoardPost = createEntity(em);
    }

    @Test
    @Transactional
    void createJobBoardPost() throws Exception {
        int databaseSizeBeforeCreate = jobBoardPostRepository.findAll().size();
        // Create the JobBoardPost
        restJobBoardPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoardPost)))
            .andExpect(status().isCreated());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeCreate + 1);
        JobBoardPost testJobBoardPost = jobBoardPostList.get(jobBoardPostList.size() - 1);
        assertThat(testJobBoardPost.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobBoardPost.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createJobBoardPostWithExistingId() throws Exception {
        // Create the JobBoardPost with an existing ID
        jobBoardPost.setId(1L);

        int databaseSizeBeforeCreate = jobBoardPostRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobBoardPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoardPost)))
            .andExpect(status().isBadRequest());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobBoardPosts() throws Exception {
        // Initialize the database
        jobBoardPostRepository.saveAndFlush(jobBoardPost);

        // Get all the jobBoardPostList
        restJobBoardPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobBoardPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getJobBoardPost() throws Exception {
        // Initialize the database
        jobBoardPostRepository.saveAndFlush(jobBoardPost);

        // Get the jobBoardPost
        restJobBoardPostMockMvc
            .perform(get(ENTITY_API_URL_ID, jobBoardPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobBoardPost.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingJobBoardPost() throws Exception {
        // Get the jobBoardPost
        restJobBoardPostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobBoardPost() throws Exception {
        // Initialize the database
        jobBoardPostRepository.saveAndFlush(jobBoardPost);

        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();

        // Update the jobBoardPost
        JobBoardPost updatedJobBoardPost = jobBoardPostRepository.findById(jobBoardPost.getId()).get();
        // Disconnect from session so that the updates on updatedJobBoardPost are not directly saved in db
        em.detach(updatedJobBoardPost);
        updatedJobBoardPost.jobId(UPDATED_JOB_ID).status(UPDATED_STATUS);

        restJobBoardPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobBoardPost.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobBoardPost))
            )
            .andExpect(status().isOk());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
        JobBoardPost testJobBoardPost = jobBoardPostList.get(jobBoardPostList.size() - 1);
        assertThat(testJobBoardPost.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobBoardPost.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingJobBoardPost() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();
        jobBoardPost.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobBoardPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobBoardPost.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobBoardPost() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();
        jobBoardPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobBoardPost() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();
        jobBoardPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardPostMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobBoardPost)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobBoardPostWithPatch() throws Exception {
        // Initialize the database
        jobBoardPostRepository.saveAndFlush(jobBoardPost);

        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();

        // Update the jobBoardPost using partial update
        JobBoardPost partialUpdatedJobBoardPost = new JobBoardPost();
        partialUpdatedJobBoardPost.setId(jobBoardPost.getId());

        partialUpdatedJobBoardPost.status(UPDATED_STATUS);

        restJobBoardPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobBoardPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobBoardPost))
            )
            .andExpect(status().isOk());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
        JobBoardPost testJobBoardPost = jobBoardPostList.get(jobBoardPostList.size() - 1);
        assertThat(testJobBoardPost.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobBoardPost.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateJobBoardPostWithPatch() throws Exception {
        // Initialize the database
        jobBoardPostRepository.saveAndFlush(jobBoardPost);

        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();

        // Update the jobBoardPost using partial update
        JobBoardPost partialUpdatedJobBoardPost = new JobBoardPost();
        partialUpdatedJobBoardPost.setId(jobBoardPost.getId());

        partialUpdatedJobBoardPost.jobId(UPDATED_JOB_ID).status(UPDATED_STATUS);

        restJobBoardPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobBoardPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobBoardPost))
            )
            .andExpect(status().isOk());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
        JobBoardPost testJobBoardPost = jobBoardPostList.get(jobBoardPostList.size() - 1);
        assertThat(testJobBoardPost.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobBoardPost.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingJobBoardPost() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();
        jobBoardPost.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobBoardPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobBoardPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobBoardPost() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();
        jobBoardPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobBoardPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobBoardPost() throws Exception {
        int databaseSizeBeforeUpdate = jobBoardPostRepository.findAll().size();
        jobBoardPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobBoardPostMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobBoardPost))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobBoardPost in the database
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobBoardPost() throws Exception {
        // Initialize the database
        jobBoardPostRepository.saveAndFlush(jobBoardPost);

        int databaseSizeBeforeDelete = jobBoardPostRepository.findAll().size();

        // Delete the jobBoardPost
        restJobBoardPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobBoardPost.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobBoardPost> jobBoardPostList = jobBoardPostRepository.findAll();
        assertThat(jobBoardPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
