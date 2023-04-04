package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.JobLocation;
import com.rp.repository.JobLocationRepository;
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
 * Integration tests for the {@link JobLocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobLocationResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;

    private static final String DEFAULT_CONTINENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTINENT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTINENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONTINENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POINT = "AAAAAAAAAA";
    private static final String UPDATED_POINT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobLocationRepository jobLocationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobLocationMockMvc;

    private JobLocation jobLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobLocation createEntity(EntityManager em) {
        JobLocation jobLocation = new JobLocation()
            .jobId(DEFAULT_JOB_ID)
            .area(DEFAULT_AREA)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .stateCode(DEFAULT_STATE_CODE)
            .country(DEFAULT_COUNTRY)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .zipCode(DEFAULT_ZIP_CODE)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .continent(DEFAULT_CONTINENT)
            .continentCode(DEFAULT_CONTINENT_CODE)
            .point(DEFAULT_POINT);
        return jobLocation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobLocation createUpdatedEntity(EntityManager em) {
        JobLocation jobLocation = new JobLocation()
            .jobId(UPDATED_JOB_ID)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .continent(UPDATED_CONTINENT)
            .continentCode(UPDATED_CONTINENT_CODE)
            .point(UPDATED_POINT);
        return jobLocation;
    }

    @BeforeEach
    public void initTest() {
        jobLocation = createEntity(em);
    }

    @Test
    @Transactional
    void createJobLocation() throws Exception {
        int databaseSizeBeforeCreate = jobLocationRepository.findAll().size();
        // Create the JobLocation
        restJobLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobLocation)))
            .andExpect(status().isCreated());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeCreate + 1);
        JobLocation testJobLocation = jobLocationList.get(jobLocationList.size() - 1);
        assertThat(testJobLocation.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobLocation.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testJobLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testJobLocation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testJobLocation.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testJobLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testJobLocation.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testJobLocation.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testJobLocation.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testJobLocation.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testJobLocation.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testJobLocation.getContinentCode()).isEqualTo(DEFAULT_CONTINENT_CODE);
        assertThat(testJobLocation.getPoint()).isEqualTo(DEFAULT_POINT);
    }

    @Test
    @Transactional
    void createJobLocationWithExistingId() throws Exception {
        // Create the JobLocation with an existing ID
        jobLocation.setId(1L);

        int databaseSizeBeforeCreate = jobLocationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobLocation)))
            .andExpect(status().isBadRequest());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobLocations() throws Exception {
        // Initialize the database
        jobLocationRepository.saveAndFlush(jobLocation);

        // Get all the jobLocationList
        restJobLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT)))
            .andExpect(jsonPath("$.[*].continentCode").value(hasItem(DEFAULT_CONTINENT_CODE)))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)));
    }

    @Test
    @Transactional
    void getJobLocation() throws Exception {
        // Initialize the database
        jobLocationRepository.saveAndFlush(jobLocation);

        // Get the jobLocation
        restJobLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, jobLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobLocation.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT))
            .andExpect(jsonPath("$.continentCode").value(DEFAULT_CONTINENT_CODE))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT));
    }

    @Test
    @Transactional
    void getNonExistingJobLocation() throws Exception {
        // Get the jobLocation
        restJobLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobLocation() throws Exception {
        // Initialize the database
        jobLocationRepository.saveAndFlush(jobLocation);

        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();

        // Update the jobLocation
        JobLocation updatedJobLocation = jobLocationRepository.findById(jobLocation.getId()).get();
        // Disconnect from session so that the updates on updatedJobLocation are not directly saved in db
        em.detach(updatedJobLocation);
        updatedJobLocation
            .jobId(UPDATED_JOB_ID)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .continent(UPDATED_CONTINENT)
            .continentCode(UPDATED_CONTINENT_CODE)
            .point(UPDATED_POINT);

        restJobLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobLocation))
            )
            .andExpect(status().isOk());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
        JobLocation testJobLocation = jobLocationList.get(jobLocationList.size() - 1);
        assertThat(testJobLocation.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobLocation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testJobLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testJobLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testJobLocation.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testJobLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testJobLocation.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testJobLocation.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testJobLocation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testJobLocation.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testJobLocation.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testJobLocation.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testJobLocation.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void putNonExistingJobLocation() throws Exception {
        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();
        jobLocation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobLocation() throws Exception {
        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();
        jobLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobLocation() throws Exception {
        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();
        jobLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobLocation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobLocationWithPatch() throws Exception {
        // Initialize the database
        jobLocationRepository.saveAndFlush(jobLocation);

        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();

        // Update the jobLocation using partial update
        JobLocation partialUpdatedJobLocation = new JobLocation();
        partialUpdatedJobLocation.setId(jobLocation.getId());

        partialUpdatedJobLocation.jobId(UPDATED_JOB_ID).state(UPDATED_STATE).lat(UPDATED_LAT).continentCode(UPDATED_CONTINENT_CODE);

        restJobLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobLocation))
            )
            .andExpect(status().isOk());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
        JobLocation testJobLocation = jobLocationList.get(jobLocationList.size() - 1);
        assertThat(testJobLocation.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobLocation.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testJobLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testJobLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testJobLocation.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testJobLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testJobLocation.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testJobLocation.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testJobLocation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testJobLocation.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testJobLocation.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testJobLocation.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testJobLocation.getPoint()).isEqualTo(DEFAULT_POINT);
    }

    @Test
    @Transactional
    void fullUpdateJobLocationWithPatch() throws Exception {
        // Initialize the database
        jobLocationRepository.saveAndFlush(jobLocation);

        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();

        // Update the jobLocation using partial update
        JobLocation partialUpdatedJobLocation = new JobLocation();
        partialUpdatedJobLocation.setId(jobLocation.getId());

        partialUpdatedJobLocation
            .jobId(UPDATED_JOB_ID)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .continent(UPDATED_CONTINENT)
            .continentCode(UPDATED_CONTINENT_CODE)
            .point(UPDATED_POINT);

        restJobLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobLocation))
            )
            .andExpect(status().isOk());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
        JobLocation testJobLocation = jobLocationList.get(jobLocationList.size() - 1);
        assertThat(testJobLocation.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobLocation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testJobLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testJobLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testJobLocation.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testJobLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testJobLocation.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testJobLocation.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testJobLocation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testJobLocation.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testJobLocation.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testJobLocation.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testJobLocation.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void patchNonExistingJobLocation() throws Exception {
        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();
        jobLocation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobLocation() throws Exception {
        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();
        jobLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobLocation() throws Exception {
        int databaseSizeBeforeUpdate = jobLocationRepository.findAll().size();
        jobLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobLocationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobLocation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobLocation in the database
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobLocation() throws Exception {
        // Initialize the database
        jobLocationRepository.saveAndFlush(jobLocation);

        int databaseSizeBeforeDelete = jobLocationRepository.findAll().size();

        // Delete the jobLocation
        restJobLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobLocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobLocation> jobLocationList = jobLocationRepository.findAll();
        assertThat(jobLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
