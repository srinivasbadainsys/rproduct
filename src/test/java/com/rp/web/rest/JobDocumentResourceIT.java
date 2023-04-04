package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.JobDocument;
import com.rp.repository.JobDocumentRepository;
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
 * Integration tests for the {@link JobDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobDocumentResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final String DEFAULT_DOCUMENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobDocumentRepository jobDocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobDocumentMockMvc;

    private JobDocument jobDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobDocument createEntity(EntityManager em) {
        JobDocument jobDocument = new JobDocument()
            .jobId(DEFAULT_JOB_ID)
            .documentTitle(DEFAULT_DOCUMENT_TITLE)
            .documentLocation(DEFAULT_DOCUMENT_LOCATION)
            .documentPassword(DEFAULT_DOCUMENT_PASSWORD);
        return jobDocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobDocument createUpdatedEntity(EntityManager em) {
        JobDocument jobDocument = new JobDocument()
            .jobId(UPDATED_JOB_ID)
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .documentLocation(UPDATED_DOCUMENT_LOCATION)
            .documentPassword(UPDATED_DOCUMENT_PASSWORD);
        return jobDocument;
    }

    @BeforeEach
    public void initTest() {
        jobDocument = createEntity(em);
    }

    @Test
    @Transactional
    void createJobDocument() throws Exception {
        int databaseSizeBeforeCreate = jobDocumentRepository.findAll().size();
        // Create the JobDocument
        restJobDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobDocument)))
            .andExpect(status().isCreated());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        JobDocument testJobDocument = jobDocumentList.get(jobDocumentList.size() - 1);
        assertThat(testJobDocument.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobDocument.getDocumentTitle()).isEqualTo(DEFAULT_DOCUMENT_TITLE);
        assertThat(testJobDocument.getDocumentLocation()).isEqualTo(DEFAULT_DOCUMENT_LOCATION);
        assertThat(testJobDocument.getDocumentPassword()).isEqualTo(DEFAULT_DOCUMENT_PASSWORD);
    }

    @Test
    @Transactional
    void createJobDocumentWithExistingId() throws Exception {
        // Create the JobDocument with an existing ID
        jobDocument.setId(1L);

        int databaseSizeBeforeCreate = jobDocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobDocument)))
            .andExpect(status().isBadRequest());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobDocuments() throws Exception {
        // Initialize the database
        jobDocumentRepository.saveAndFlush(jobDocument);

        // Get all the jobDocumentList
        restJobDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].documentTitle").value(hasItem(DEFAULT_DOCUMENT_TITLE)))
            .andExpect(jsonPath("$.[*].documentLocation").value(hasItem(DEFAULT_DOCUMENT_LOCATION)))
            .andExpect(jsonPath("$.[*].documentPassword").value(hasItem(DEFAULT_DOCUMENT_PASSWORD)));
    }

    @Test
    @Transactional
    void getJobDocument() throws Exception {
        // Initialize the database
        jobDocumentRepository.saveAndFlush(jobDocument);

        // Get the jobDocument
        restJobDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, jobDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobDocument.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.documentTitle").value(DEFAULT_DOCUMENT_TITLE))
            .andExpect(jsonPath("$.documentLocation").value(DEFAULT_DOCUMENT_LOCATION))
            .andExpect(jsonPath("$.documentPassword").value(DEFAULT_DOCUMENT_PASSWORD));
    }

    @Test
    @Transactional
    void getNonExistingJobDocument() throws Exception {
        // Get the jobDocument
        restJobDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobDocument() throws Exception {
        // Initialize the database
        jobDocumentRepository.saveAndFlush(jobDocument);

        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();

        // Update the jobDocument
        JobDocument updatedJobDocument = jobDocumentRepository.findById(jobDocument.getId()).get();
        // Disconnect from session so that the updates on updatedJobDocument are not directly saved in db
        em.detach(updatedJobDocument);
        updatedJobDocument
            .jobId(UPDATED_JOB_ID)
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .documentLocation(UPDATED_DOCUMENT_LOCATION)
            .documentPassword(UPDATED_DOCUMENT_PASSWORD);

        restJobDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobDocument))
            )
            .andExpect(status().isOk());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
        JobDocument testJobDocument = jobDocumentList.get(jobDocumentList.size() - 1);
        assertThat(testJobDocument.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobDocument.getDocumentTitle()).isEqualTo(UPDATED_DOCUMENT_TITLE);
        assertThat(testJobDocument.getDocumentLocation()).isEqualTo(UPDATED_DOCUMENT_LOCATION);
        assertThat(testJobDocument.getDocumentPassword()).isEqualTo(UPDATED_DOCUMENT_PASSWORD);
    }

    @Test
    @Transactional
    void putNonExistingJobDocument() throws Exception {
        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();
        jobDocument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobDocument() throws Exception {
        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();
        jobDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobDocument() throws Exception {
        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();
        jobDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobDocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobDocumentWithPatch() throws Exception {
        // Initialize the database
        jobDocumentRepository.saveAndFlush(jobDocument);

        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();

        // Update the jobDocument using partial update
        JobDocument partialUpdatedJobDocument = new JobDocument();
        partialUpdatedJobDocument.setId(jobDocument.getId());

        partialUpdatedJobDocument.jobId(UPDATED_JOB_ID);

        restJobDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobDocument))
            )
            .andExpect(status().isOk());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
        JobDocument testJobDocument = jobDocumentList.get(jobDocumentList.size() - 1);
        assertThat(testJobDocument.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobDocument.getDocumentTitle()).isEqualTo(DEFAULT_DOCUMENT_TITLE);
        assertThat(testJobDocument.getDocumentLocation()).isEqualTo(DEFAULT_DOCUMENT_LOCATION);
        assertThat(testJobDocument.getDocumentPassword()).isEqualTo(DEFAULT_DOCUMENT_PASSWORD);
    }

    @Test
    @Transactional
    void fullUpdateJobDocumentWithPatch() throws Exception {
        // Initialize the database
        jobDocumentRepository.saveAndFlush(jobDocument);

        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();

        // Update the jobDocument using partial update
        JobDocument partialUpdatedJobDocument = new JobDocument();
        partialUpdatedJobDocument.setId(jobDocument.getId());

        partialUpdatedJobDocument
            .jobId(UPDATED_JOB_ID)
            .documentTitle(UPDATED_DOCUMENT_TITLE)
            .documentLocation(UPDATED_DOCUMENT_LOCATION)
            .documentPassword(UPDATED_DOCUMENT_PASSWORD);

        restJobDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobDocument))
            )
            .andExpect(status().isOk());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
        JobDocument testJobDocument = jobDocumentList.get(jobDocumentList.size() - 1);
        assertThat(testJobDocument.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobDocument.getDocumentTitle()).isEqualTo(UPDATED_DOCUMENT_TITLE);
        assertThat(testJobDocument.getDocumentLocation()).isEqualTo(UPDATED_DOCUMENT_LOCATION);
        assertThat(testJobDocument.getDocumentPassword()).isEqualTo(UPDATED_DOCUMENT_PASSWORD);
    }

    @Test
    @Transactional
    void patchNonExistingJobDocument() throws Exception {
        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();
        jobDocument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobDocument() throws Exception {
        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();
        jobDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobDocument() throws Exception {
        int databaseSizeBeforeUpdate = jobDocumentRepository.findAll().size();
        jobDocument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobDocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobDocument in the database
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobDocument() throws Exception {
        // Initialize the database
        jobDocumentRepository.saveAndFlush(jobDocument);

        int databaseSizeBeforeDelete = jobDocumentRepository.findAll().size();

        // Delete the jobDocument
        restJobDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobDocument> jobDocumentList = jobDocumentRepository.findAll();
        assertThat(jobDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
