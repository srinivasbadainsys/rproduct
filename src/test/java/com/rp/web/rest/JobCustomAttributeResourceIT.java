package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.JobCustomAttribute;
import com.rp.domain.enumeration.JobAttributeType;
import com.rp.repository.JobCustomAttributeRepository;
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
 * Integration tests for the {@link JobCustomAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobCustomAttributeResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final JobAttributeType DEFAULT_ATTRIBUTE_TYPE = JobAttributeType.Single_Value;
    private static final JobAttributeType UPDATED_ATTRIBUTE_TYPE = JobAttributeType.Multi_Value;

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-custom-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobCustomAttributeRepository jobCustomAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobCustomAttributeMockMvc;

    private JobCustomAttribute jobCustomAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobCustomAttribute createEntity(EntityManager em) {
        JobCustomAttribute jobCustomAttribute = new JobCustomAttribute()
            .jobId(DEFAULT_JOB_ID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeType(DEFAULT_ATTRIBUTE_TYPE)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return jobCustomAttribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobCustomAttribute createUpdatedEntity(EntityManager em) {
        JobCustomAttribute jobCustomAttribute = new JobCustomAttribute()
            .jobId(UPDATED_JOB_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return jobCustomAttribute;
    }

    @BeforeEach
    public void initTest() {
        jobCustomAttribute = createEntity(em);
    }

    @Test
    @Transactional
    void createJobCustomAttribute() throws Exception {
        int databaseSizeBeforeCreate = jobCustomAttributeRepository.findAll().size();
        // Create the JobCustomAttribute
        restJobCustomAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isCreated());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        JobCustomAttribute testJobCustomAttribute = jobCustomAttributeList.get(jobCustomAttributeList.size() - 1);
        assertThat(testJobCustomAttribute.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobCustomAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testJobCustomAttribute.getAttributeType()).isEqualTo(DEFAULT_ATTRIBUTE_TYPE);
        assertThat(testJobCustomAttribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createJobCustomAttributeWithExistingId() throws Exception {
        // Create the JobCustomAttribute with an existing ID
        jobCustomAttribute.setId(1L);

        int databaseSizeBeforeCreate = jobCustomAttributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobCustomAttributeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobCustomAttributes() throws Exception {
        // Initialize the database
        jobCustomAttributeRepository.saveAndFlush(jobCustomAttribute);

        // Get all the jobCustomAttributeList
        restJobCustomAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobCustomAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeType").value(hasItem(DEFAULT_ATTRIBUTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getJobCustomAttribute() throws Exception {
        // Initialize the database
        jobCustomAttributeRepository.saveAndFlush(jobCustomAttribute);

        // Get the jobCustomAttribute
        restJobCustomAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, jobCustomAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobCustomAttribute.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeType").value(DEFAULT_ATTRIBUTE_TYPE.toString()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingJobCustomAttribute() throws Exception {
        // Get the jobCustomAttribute
        restJobCustomAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobCustomAttribute() throws Exception {
        // Initialize the database
        jobCustomAttributeRepository.saveAndFlush(jobCustomAttribute);

        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();

        // Update the jobCustomAttribute
        JobCustomAttribute updatedJobCustomAttribute = jobCustomAttributeRepository.findById(jobCustomAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedJobCustomAttribute are not directly saved in db
        em.detach(updatedJobCustomAttribute);
        updatedJobCustomAttribute
            .jobId(UPDATED_JOB_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restJobCustomAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobCustomAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobCustomAttribute))
            )
            .andExpect(status().isOk());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
        JobCustomAttribute testJobCustomAttribute = jobCustomAttributeList.get(jobCustomAttributeList.size() - 1);
        assertThat(testJobCustomAttribute.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobCustomAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testJobCustomAttribute.getAttributeType()).isEqualTo(UPDATED_ATTRIBUTE_TYPE);
        assertThat(testJobCustomAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingJobCustomAttribute() throws Exception {
        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();
        jobCustomAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobCustomAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobCustomAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobCustomAttribute() throws Exception {
        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();
        jobCustomAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCustomAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobCustomAttribute() throws Exception {
        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();
        jobCustomAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCustomAttributeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobCustomAttributeWithPatch() throws Exception {
        // Initialize the database
        jobCustomAttributeRepository.saveAndFlush(jobCustomAttribute);

        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();

        // Update the jobCustomAttribute using partial update
        JobCustomAttribute partialUpdatedJobCustomAttribute = new JobCustomAttribute();
        partialUpdatedJobCustomAttribute.setId(jobCustomAttribute.getId());

        partialUpdatedJobCustomAttribute.attributeType(UPDATED_ATTRIBUTE_TYPE);

        restJobCustomAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobCustomAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobCustomAttribute))
            )
            .andExpect(status().isOk());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
        JobCustomAttribute testJobCustomAttribute = jobCustomAttributeList.get(jobCustomAttributeList.size() - 1);
        assertThat(testJobCustomAttribute.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobCustomAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testJobCustomAttribute.getAttributeType()).isEqualTo(UPDATED_ATTRIBUTE_TYPE);
        assertThat(testJobCustomAttribute.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateJobCustomAttributeWithPatch() throws Exception {
        // Initialize the database
        jobCustomAttributeRepository.saveAndFlush(jobCustomAttribute);

        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();

        // Update the jobCustomAttribute using partial update
        JobCustomAttribute partialUpdatedJobCustomAttribute = new JobCustomAttribute();
        partialUpdatedJobCustomAttribute.setId(jobCustomAttribute.getId());

        partialUpdatedJobCustomAttribute
            .jobId(UPDATED_JOB_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restJobCustomAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobCustomAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobCustomAttribute))
            )
            .andExpect(status().isOk());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
        JobCustomAttribute testJobCustomAttribute = jobCustomAttributeList.get(jobCustomAttributeList.size() - 1);
        assertThat(testJobCustomAttribute.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobCustomAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testJobCustomAttribute.getAttributeType()).isEqualTo(UPDATED_ATTRIBUTE_TYPE);
        assertThat(testJobCustomAttribute.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingJobCustomAttribute() throws Exception {
        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();
        jobCustomAttribute.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobCustomAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobCustomAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobCustomAttribute() throws Exception {
        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();
        jobCustomAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCustomAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobCustomAttribute() throws Exception {
        int databaseSizeBeforeUpdate = jobCustomAttributeRepository.findAll().size();
        jobCustomAttribute.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCustomAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCustomAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobCustomAttribute in the database
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobCustomAttribute() throws Exception {
        // Initialize the database
        jobCustomAttributeRepository.saveAndFlush(jobCustomAttribute);

        int databaseSizeBeforeDelete = jobCustomAttributeRepository.findAll().size();

        // Delete the jobCustomAttribute
        restJobCustomAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobCustomAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobCustomAttribute> jobCustomAttributeList = jobCustomAttributeRepository.findAll();
        assertThat(jobCustomAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
