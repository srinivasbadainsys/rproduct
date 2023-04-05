package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.CandidateRelocationPreference;
import com.rp.repository.CandidateRelocationPreferenceRepository;
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
 * Integration tests for the {@link CandidateRelocationPreferenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CandidateRelocationPreferenceResourceIT {

    private static final Long DEFAULT_CANDIDATE_ID = 1L;
    private static final Long UPDATED_CANDIDATE_ID = 2L;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/candidate-relocation-preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CandidateRelocationPreferenceRepository candidateRelocationPreferenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateRelocationPreferenceMockMvc;

    private CandidateRelocationPreference candidateRelocationPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateRelocationPreference createEntity(EntityManager em) {
        CandidateRelocationPreference candidateRelocationPreference = new CandidateRelocationPreference()
            .candidateId(DEFAULT_CANDIDATE_ID)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .stateCode(DEFAULT_STATE_CODE)
            .county(DEFAULT_COUNTY)
            .country(DEFAULT_COUNTRY)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .zipCode(DEFAULT_ZIP_CODE);
        return candidateRelocationPreference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateRelocationPreference createUpdatedEntity(EntityManager em) {
        CandidateRelocationPreference candidateRelocationPreference = new CandidateRelocationPreference()
            .candidateId(UPDATED_CANDIDATE_ID)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE);
        return candidateRelocationPreference;
    }

    @BeforeEach
    public void initTest() {
        candidateRelocationPreference = createEntity(em);
    }

    @Test
    @Transactional
    void createCandidateRelocationPreference() throws Exception {
        int databaseSizeBeforeCreate = candidateRelocationPreferenceRepository.findAll().size();
        // Create the CandidateRelocationPreference
        restCandidateRelocationPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isCreated());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateRelocationPreference testCandidateRelocationPreference = candidateRelocationPreferenceList.get(
            candidateRelocationPreferenceList.size() - 1
        );
        assertThat(testCandidateRelocationPreference.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testCandidateRelocationPreference.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCandidateRelocationPreference.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCandidateRelocationPreference.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testCandidateRelocationPreference.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testCandidateRelocationPreference.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCandidateRelocationPreference.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCandidateRelocationPreference.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
    }

    @Test
    @Transactional
    void createCandidateRelocationPreferenceWithExistingId() throws Exception {
        // Create the CandidateRelocationPreference with an existing ID
        candidateRelocationPreference.setId(1L);

        int databaseSizeBeforeCreate = candidateRelocationPreferenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateRelocationPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidateRelocationPreferences() throws Exception {
        // Initialize the database
        candidateRelocationPreferenceRepository.saveAndFlush(candidateRelocationPreference);

        // Get all the candidateRelocationPreferenceList
        restCandidateRelocationPreferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateRelocationPreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)));
    }

    @Test
    @Transactional
    void getCandidateRelocationPreference() throws Exception {
        // Initialize the database
        candidateRelocationPreferenceRepository.saveAndFlush(candidateRelocationPreference);

        // Get the candidateRelocationPreference
        restCandidateRelocationPreferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, candidateRelocationPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidateRelocationPreference.getId().intValue()))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID.intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCandidateRelocationPreference() throws Exception {
        // Get the candidateRelocationPreference
        restCandidateRelocationPreferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidateRelocationPreference() throws Exception {
        // Initialize the database
        candidateRelocationPreferenceRepository.saveAndFlush(candidateRelocationPreference);

        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();

        // Update the candidateRelocationPreference
        CandidateRelocationPreference updatedCandidateRelocationPreference = candidateRelocationPreferenceRepository
            .findById(candidateRelocationPreference.getId())
            .get();
        // Disconnect from session so that the updates on updatedCandidateRelocationPreference are not directly saved in db
        em.detach(updatedCandidateRelocationPreference);
        updatedCandidateRelocationPreference
            .candidateId(UPDATED_CANDIDATE_ID)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE);

        restCandidateRelocationPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCandidateRelocationPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCandidateRelocationPreference))
            )
            .andExpect(status().isOk());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
        CandidateRelocationPreference testCandidateRelocationPreference = candidateRelocationPreferenceList.get(
            candidateRelocationPreferenceList.size() - 1
        );
        assertThat(testCandidateRelocationPreference.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidateRelocationPreference.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCandidateRelocationPreference.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidateRelocationPreference.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCandidateRelocationPreference.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCandidateRelocationPreference.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCandidateRelocationPreference.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCandidateRelocationPreference.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCandidateRelocationPreference() throws Exception {
        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();
        candidateRelocationPreference.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateRelocationPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateRelocationPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidateRelocationPreference() throws Exception {
        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();
        candidateRelocationPreference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateRelocationPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidateRelocationPreference() throws Exception {
        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();
        candidateRelocationPreference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateRelocationPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidateRelocationPreferenceWithPatch() throws Exception {
        // Initialize the database
        candidateRelocationPreferenceRepository.saveAndFlush(candidateRelocationPreference);

        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();

        // Update the candidateRelocationPreference using partial update
        CandidateRelocationPreference partialUpdatedCandidateRelocationPreference = new CandidateRelocationPreference();
        partialUpdatedCandidateRelocationPreference.setId(candidateRelocationPreference.getId());

        partialUpdatedCandidateRelocationPreference
            .candidateId(UPDATED_CANDIDATE_ID)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .zipCode(UPDATED_ZIP_CODE);

        restCandidateRelocationPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidateRelocationPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidateRelocationPreference))
            )
            .andExpect(status().isOk());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
        CandidateRelocationPreference testCandidateRelocationPreference = candidateRelocationPreferenceList.get(
            candidateRelocationPreferenceList.size() - 1
        );
        assertThat(testCandidateRelocationPreference.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidateRelocationPreference.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCandidateRelocationPreference.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidateRelocationPreference.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCandidateRelocationPreference.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCandidateRelocationPreference.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCandidateRelocationPreference.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCandidateRelocationPreference.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCandidateRelocationPreferenceWithPatch() throws Exception {
        // Initialize the database
        candidateRelocationPreferenceRepository.saveAndFlush(candidateRelocationPreference);

        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();

        // Update the candidateRelocationPreference using partial update
        CandidateRelocationPreference partialUpdatedCandidateRelocationPreference = new CandidateRelocationPreference();
        partialUpdatedCandidateRelocationPreference.setId(candidateRelocationPreference.getId());

        partialUpdatedCandidateRelocationPreference
            .candidateId(UPDATED_CANDIDATE_ID)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE);

        restCandidateRelocationPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidateRelocationPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidateRelocationPreference))
            )
            .andExpect(status().isOk());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
        CandidateRelocationPreference testCandidateRelocationPreference = candidateRelocationPreferenceList.get(
            candidateRelocationPreferenceList.size() - 1
        );
        assertThat(testCandidateRelocationPreference.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidateRelocationPreference.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCandidateRelocationPreference.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidateRelocationPreference.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCandidateRelocationPreference.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCandidateRelocationPreference.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCandidateRelocationPreference.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCandidateRelocationPreference.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCandidateRelocationPreference() throws Exception {
        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();
        candidateRelocationPreference.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateRelocationPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidateRelocationPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidateRelocationPreference() throws Exception {
        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();
        candidateRelocationPreference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateRelocationPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidateRelocationPreference() throws Exception {
        int databaseSizeBeforeUpdate = candidateRelocationPreferenceRepository.findAll().size();
        candidateRelocationPreference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateRelocationPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateRelocationPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidateRelocationPreference in the database
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidateRelocationPreference() throws Exception {
        // Initialize the database
        candidateRelocationPreferenceRepository.saveAndFlush(candidateRelocationPreference);

        int databaseSizeBeforeDelete = candidateRelocationPreferenceRepository.findAll().size();

        // Delete the candidateRelocationPreference
        restCandidateRelocationPreferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidateRelocationPreference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CandidateRelocationPreference> candidateRelocationPreferenceList = candidateRelocationPreferenceRepository.findAll();
        assertThat(candidateRelocationPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
