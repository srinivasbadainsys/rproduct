package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.CandidatePipeline;
import com.rp.domain.enumeration.CandidatePipelineType;
import com.rp.repository.CandidatePipelineRepository;
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
 * Integration tests for the {@link CandidatePipelineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CandidatePipelineResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final Long DEFAULT_STATUS_ID = 1L;
    private static final Long UPDATED_STATUS_ID = 2L;

    private static final String DEFAULT_SUBMISSION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_SUBMISSION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SUBMISSION_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_SUBMISSION_STAGE = "BBBBBBBBBB";

    private static final String DEFAULT_RECRUITER_ACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_RECRUITER_ACTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_CANDIDATE_RESPONSES = "AAAAAAAAAA";
    private static final String UPDATED_CANDIDATE_RESPONSES = "BBBBBBBBBB";

    private static final CandidatePipelineType DEFAULT_PIPELINE_TYPE = CandidatePipelineType.TAGGED_TO_JOB;
    private static final CandidatePipelineType UPDATED_PIPELINE_TYPE = CandidatePipelineType.SUBMISSION;

    private static final String DEFAULT_REASON_FOR_NEW_JOB = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_NEW_JOB = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/candidate-pipelines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CandidatePipelineRepository candidatePipelineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidatePipelineMockMvc;

    private CandidatePipeline candidatePipeline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidatePipeline createEntity(EntityManager em) {
        CandidatePipeline candidatePipeline = new CandidatePipeline()
            .jobId(DEFAULT_JOB_ID)
            .statusId(DEFAULT_STATUS_ID)
            .submissionStatus(DEFAULT_SUBMISSION_STATUS)
            .submissionStage(DEFAULT_SUBMISSION_STAGE)
            .recruiterActions(DEFAULT_RECRUITER_ACTIONS)
            .candidateResponses(DEFAULT_CANDIDATE_RESPONSES)
            .pipelineType(DEFAULT_PIPELINE_TYPE)
            .reasonForNewJob(DEFAULT_REASON_FOR_NEW_JOB);
        return candidatePipeline;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidatePipeline createUpdatedEntity(EntityManager em) {
        CandidatePipeline candidatePipeline = new CandidatePipeline()
            .jobId(UPDATED_JOB_ID)
            .statusId(UPDATED_STATUS_ID)
            .submissionStatus(UPDATED_SUBMISSION_STATUS)
            .submissionStage(UPDATED_SUBMISSION_STAGE)
            .recruiterActions(UPDATED_RECRUITER_ACTIONS)
            .candidateResponses(UPDATED_CANDIDATE_RESPONSES)
            .pipelineType(UPDATED_PIPELINE_TYPE)
            .reasonForNewJob(UPDATED_REASON_FOR_NEW_JOB);
        return candidatePipeline;
    }

    @BeforeEach
    public void initTest() {
        candidatePipeline = createEntity(em);
    }

    @Test
    @Transactional
    void createCandidatePipeline() throws Exception {
        int databaseSizeBeforeCreate = candidatePipelineRepository.findAll().size();
        // Create the CandidatePipeline
        restCandidatePipelineMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isCreated());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeCreate + 1);
        CandidatePipeline testCandidatePipeline = candidatePipelineList.get(candidatePipelineList.size() - 1);
        assertThat(testCandidatePipeline.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testCandidatePipeline.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testCandidatePipeline.getSubmissionStatus()).isEqualTo(DEFAULT_SUBMISSION_STATUS);
        assertThat(testCandidatePipeline.getSubmissionStage()).isEqualTo(DEFAULT_SUBMISSION_STAGE);
        assertThat(testCandidatePipeline.getRecruiterActions()).isEqualTo(DEFAULT_RECRUITER_ACTIONS);
        assertThat(testCandidatePipeline.getCandidateResponses()).isEqualTo(DEFAULT_CANDIDATE_RESPONSES);
        assertThat(testCandidatePipeline.getPipelineType()).isEqualTo(DEFAULT_PIPELINE_TYPE);
        assertThat(testCandidatePipeline.getReasonForNewJob()).isEqualTo(DEFAULT_REASON_FOR_NEW_JOB);
    }

    @Test
    @Transactional
    void createCandidatePipelineWithExistingId() throws Exception {
        // Create the CandidatePipeline with an existing ID
        candidatePipeline.setId(1L);

        int databaseSizeBeforeCreate = candidatePipelineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatePipelineMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidatePipelines() throws Exception {
        // Initialize the database
        candidatePipelineRepository.saveAndFlush(candidatePipeline);

        // Get all the candidatePipelineList
        restCandidatePipelineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidatePipeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].statusId").value(hasItem(DEFAULT_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].submissionStatus").value(hasItem(DEFAULT_SUBMISSION_STATUS)))
            .andExpect(jsonPath("$.[*].submissionStage").value(hasItem(DEFAULT_SUBMISSION_STAGE)))
            .andExpect(jsonPath("$.[*].recruiterActions").value(hasItem(DEFAULT_RECRUITER_ACTIONS)))
            .andExpect(jsonPath("$.[*].candidateResponses").value(hasItem(DEFAULT_CANDIDATE_RESPONSES)))
            .andExpect(jsonPath("$.[*].pipelineType").value(hasItem(DEFAULT_PIPELINE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reasonForNewJob").value(hasItem(DEFAULT_REASON_FOR_NEW_JOB)));
    }

    @Test
    @Transactional
    void getCandidatePipeline() throws Exception {
        // Initialize the database
        candidatePipelineRepository.saveAndFlush(candidatePipeline);

        // Get the candidatePipeline
        restCandidatePipelineMockMvc
            .perform(get(ENTITY_API_URL_ID, candidatePipeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidatePipeline.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.statusId").value(DEFAULT_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.submissionStatus").value(DEFAULT_SUBMISSION_STATUS))
            .andExpect(jsonPath("$.submissionStage").value(DEFAULT_SUBMISSION_STAGE))
            .andExpect(jsonPath("$.recruiterActions").value(DEFAULT_RECRUITER_ACTIONS))
            .andExpect(jsonPath("$.candidateResponses").value(DEFAULT_CANDIDATE_RESPONSES))
            .andExpect(jsonPath("$.pipelineType").value(DEFAULT_PIPELINE_TYPE.toString()))
            .andExpect(jsonPath("$.reasonForNewJob").value(DEFAULT_REASON_FOR_NEW_JOB));
    }

    @Test
    @Transactional
    void getNonExistingCandidatePipeline() throws Exception {
        // Get the candidatePipeline
        restCandidatePipelineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidatePipeline() throws Exception {
        // Initialize the database
        candidatePipelineRepository.saveAndFlush(candidatePipeline);

        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();

        // Update the candidatePipeline
        CandidatePipeline updatedCandidatePipeline = candidatePipelineRepository.findById(candidatePipeline.getId()).get();
        // Disconnect from session so that the updates on updatedCandidatePipeline are not directly saved in db
        em.detach(updatedCandidatePipeline);
        updatedCandidatePipeline
            .jobId(UPDATED_JOB_ID)
            .statusId(UPDATED_STATUS_ID)
            .submissionStatus(UPDATED_SUBMISSION_STATUS)
            .submissionStage(UPDATED_SUBMISSION_STAGE)
            .recruiterActions(UPDATED_RECRUITER_ACTIONS)
            .candidateResponses(UPDATED_CANDIDATE_RESPONSES)
            .pipelineType(UPDATED_PIPELINE_TYPE)
            .reasonForNewJob(UPDATED_REASON_FOR_NEW_JOB);

        restCandidatePipelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCandidatePipeline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCandidatePipeline))
            )
            .andExpect(status().isOk());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
        CandidatePipeline testCandidatePipeline = candidatePipelineList.get(candidatePipelineList.size() - 1);
        assertThat(testCandidatePipeline.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testCandidatePipeline.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testCandidatePipeline.getSubmissionStatus()).isEqualTo(UPDATED_SUBMISSION_STATUS);
        assertThat(testCandidatePipeline.getSubmissionStage()).isEqualTo(UPDATED_SUBMISSION_STAGE);
        assertThat(testCandidatePipeline.getRecruiterActions()).isEqualTo(UPDATED_RECRUITER_ACTIONS);
        assertThat(testCandidatePipeline.getCandidateResponses()).isEqualTo(UPDATED_CANDIDATE_RESPONSES);
        assertThat(testCandidatePipeline.getPipelineType()).isEqualTo(UPDATED_PIPELINE_TYPE);
        assertThat(testCandidatePipeline.getReasonForNewJob()).isEqualTo(UPDATED_REASON_FOR_NEW_JOB);
    }

    @Test
    @Transactional
    void putNonExistingCandidatePipeline() throws Exception {
        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();
        candidatePipeline.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatePipelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidatePipeline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidatePipeline() throws Exception {
        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();
        candidatePipeline.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatePipelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidatePipeline() throws Exception {
        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();
        candidatePipeline.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatePipelineMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidatePipelineWithPatch() throws Exception {
        // Initialize the database
        candidatePipelineRepository.saveAndFlush(candidatePipeline);

        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();

        // Update the candidatePipeline using partial update
        CandidatePipeline partialUpdatedCandidatePipeline = new CandidatePipeline();
        partialUpdatedCandidatePipeline.setId(candidatePipeline.getId());

        partialUpdatedCandidatePipeline
            .jobId(UPDATED_JOB_ID)
            .pipelineType(UPDATED_PIPELINE_TYPE)
            .reasonForNewJob(UPDATED_REASON_FOR_NEW_JOB);

        restCandidatePipelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidatePipeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidatePipeline))
            )
            .andExpect(status().isOk());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
        CandidatePipeline testCandidatePipeline = candidatePipelineList.get(candidatePipelineList.size() - 1);
        assertThat(testCandidatePipeline.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testCandidatePipeline.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testCandidatePipeline.getSubmissionStatus()).isEqualTo(DEFAULT_SUBMISSION_STATUS);
        assertThat(testCandidatePipeline.getSubmissionStage()).isEqualTo(DEFAULT_SUBMISSION_STAGE);
        assertThat(testCandidatePipeline.getRecruiterActions()).isEqualTo(DEFAULT_RECRUITER_ACTIONS);
        assertThat(testCandidatePipeline.getCandidateResponses()).isEqualTo(DEFAULT_CANDIDATE_RESPONSES);
        assertThat(testCandidatePipeline.getPipelineType()).isEqualTo(UPDATED_PIPELINE_TYPE);
        assertThat(testCandidatePipeline.getReasonForNewJob()).isEqualTo(UPDATED_REASON_FOR_NEW_JOB);
    }

    @Test
    @Transactional
    void fullUpdateCandidatePipelineWithPatch() throws Exception {
        // Initialize the database
        candidatePipelineRepository.saveAndFlush(candidatePipeline);

        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();

        // Update the candidatePipeline using partial update
        CandidatePipeline partialUpdatedCandidatePipeline = new CandidatePipeline();
        partialUpdatedCandidatePipeline.setId(candidatePipeline.getId());

        partialUpdatedCandidatePipeline
            .jobId(UPDATED_JOB_ID)
            .statusId(UPDATED_STATUS_ID)
            .submissionStatus(UPDATED_SUBMISSION_STATUS)
            .submissionStage(UPDATED_SUBMISSION_STAGE)
            .recruiterActions(UPDATED_RECRUITER_ACTIONS)
            .candidateResponses(UPDATED_CANDIDATE_RESPONSES)
            .pipelineType(UPDATED_PIPELINE_TYPE)
            .reasonForNewJob(UPDATED_REASON_FOR_NEW_JOB);

        restCandidatePipelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidatePipeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidatePipeline))
            )
            .andExpect(status().isOk());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
        CandidatePipeline testCandidatePipeline = candidatePipelineList.get(candidatePipelineList.size() - 1);
        assertThat(testCandidatePipeline.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testCandidatePipeline.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testCandidatePipeline.getSubmissionStatus()).isEqualTo(UPDATED_SUBMISSION_STATUS);
        assertThat(testCandidatePipeline.getSubmissionStage()).isEqualTo(UPDATED_SUBMISSION_STAGE);
        assertThat(testCandidatePipeline.getRecruiterActions()).isEqualTo(UPDATED_RECRUITER_ACTIONS);
        assertThat(testCandidatePipeline.getCandidateResponses()).isEqualTo(UPDATED_CANDIDATE_RESPONSES);
        assertThat(testCandidatePipeline.getPipelineType()).isEqualTo(UPDATED_PIPELINE_TYPE);
        assertThat(testCandidatePipeline.getReasonForNewJob()).isEqualTo(UPDATED_REASON_FOR_NEW_JOB);
    }

    @Test
    @Transactional
    void patchNonExistingCandidatePipeline() throws Exception {
        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();
        candidatePipeline.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatePipelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidatePipeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidatePipeline() throws Exception {
        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();
        candidatePipeline.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatePipelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidatePipeline() throws Exception {
        int databaseSizeBeforeUpdate = candidatePipelineRepository.findAll().size();
        candidatePipeline.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidatePipelineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidatePipeline))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidatePipeline in the database
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidatePipeline() throws Exception {
        // Initialize the database
        candidatePipelineRepository.saveAndFlush(candidatePipeline);

        int databaseSizeBeforeDelete = candidatePipelineRepository.findAll().size();

        // Delete the candidatePipeline
        restCandidatePipelineMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidatePipeline.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CandidatePipeline> candidatePipelineList = candidatePipelineRepository.findAll();
        assertThat(candidatePipelineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
