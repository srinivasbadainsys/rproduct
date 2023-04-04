package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.WorkspaceLocation;
import com.rp.repository.WorkspaceLocationRepository;
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
 * Integration tests for the {@link WorkspaceLocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkspaceLocationResourceIT {

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

    private static final String ENTITY_API_URL = "/api/workspace-locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkspaceLocationRepository workspaceLocationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkspaceLocationMockMvc;

    private WorkspaceLocation workspaceLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkspaceLocation createEntity(EntityManager em) {
        WorkspaceLocation workspaceLocation = new WorkspaceLocation()
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
        return workspaceLocation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkspaceLocation createUpdatedEntity(EntityManager em) {
        WorkspaceLocation workspaceLocation = new WorkspaceLocation()
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
        return workspaceLocation;
    }

    @BeforeEach
    public void initTest() {
        workspaceLocation = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkspaceLocation() throws Exception {
        int databaseSizeBeforeCreate = workspaceLocationRepository.findAll().size();
        // Create the WorkspaceLocation
        restWorkspaceLocationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isCreated());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeCreate + 1);
        WorkspaceLocation testWorkspaceLocation = workspaceLocationList.get(workspaceLocationList.size() - 1);
        assertThat(testWorkspaceLocation.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testWorkspaceLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWorkspaceLocation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWorkspaceLocation.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testWorkspaceLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWorkspaceLocation.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testWorkspaceLocation.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testWorkspaceLocation.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testWorkspaceLocation.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testWorkspaceLocation.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testWorkspaceLocation.getContinentCode()).isEqualTo(DEFAULT_CONTINENT_CODE);
        assertThat(testWorkspaceLocation.getPoint()).isEqualTo(DEFAULT_POINT);
    }

    @Test
    @Transactional
    void createWorkspaceLocationWithExistingId() throws Exception {
        // Create the WorkspaceLocation with an existing ID
        workspaceLocation.setId(1L);

        int databaseSizeBeforeCreate = workspaceLocationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkspaceLocationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkspaceLocations() throws Exception {
        // Initialize the database
        workspaceLocationRepository.saveAndFlush(workspaceLocation);

        // Get all the workspaceLocationList
        restWorkspaceLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspaceLocation.getId().intValue())))
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
    void getWorkspaceLocation() throws Exception {
        // Initialize the database
        workspaceLocationRepository.saveAndFlush(workspaceLocation);

        // Get the workspaceLocation
        restWorkspaceLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, workspaceLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workspaceLocation.getId().intValue()))
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
    void getNonExistingWorkspaceLocation() throws Exception {
        // Get the workspaceLocation
        restWorkspaceLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkspaceLocation() throws Exception {
        // Initialize the database
        workspaceLocationRepository.saveAndFlush(workspaceLocation);

        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();

        // Update the workspaceLocation
        WorkspaceLocation updatedWorkspaceLocation = workspaceLocationRepository.findById(workspaceLocation.getId()).get();
        // Disconnect from session so that the updates on updatedWorkspaceLocation are not directly saved in db
        em.detach(updatedWorkspaceLocation);
        updatedWorkspaceLocation
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

        restWorkspaceLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkspaceLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkspaceLocation))
            )
            .andExpect(status().isOk());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
        WorkspaceLocation testWorkspaceLocation = workspaceLocationList.get(workspaceLocationList.size() - 1);
        assertThat(testWorkspaceLocation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testWorkspaceLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWorkspaceLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkspaceLocation.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testWorkspaceLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWorkspaceLocation.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testWorkspaceLocation.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testWorkspaceLocation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testWorkspaceLocation.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testWorkspaceLocation.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testWorkspaceLocation.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testWorkspaceLocation.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void putNonExistingWorkspaceLocation() throws Exception {
        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();
        workspaceLocation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workspaceLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkspaceLocation() throws Exception {
        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();
        workspaceLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkspaceLocation() throws Exception {
        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();
        workspaceLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceLocationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkspaceLocationWithPatch() throws Exception {
        // Initialize the database
        workspaceLocationRepository.saveAndFlush(workspaceLocation);

        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();

        // Update the workspaceLocation using partial update
        WorkspaceLocation partialUpdatedWorkspaceLocation = new WorkspaceLocation();
        partialUpdatedWorkspaceLocation.setId(workspaceLocation.getId());

        partialUpdatedWorkspaceLocation
            .area(UPDATED_AREA)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .zipCode(UPDATED_ZIP_CODE)
            .lat(UPDATED_LAT)
            .continentCode(UPDATED_CONTINENT_CODE)
            .point(UPDATED_POINT);

        restWorkspaceLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkspaceLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkspaceLocation))
            )
            .andExpect(status().isOk());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
        WorkspaceLocation testWorkspaceLocation = workspaceLocationList.get(workspaceLocationList.size() - 1);
        assertThat(testWorkspaceLocation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testWorkspaceLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWorkspaceLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkspaceLocation.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testWorkspaceLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWorkspaceLocation.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testWorkspaceLocation.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testWorkspaceLocation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testWorkspaceLocation.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testWorkspaceLocation.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testWorkspaceLocation.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testWorkspaceLocation.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void fullUpdateWorkspaceLocationWithPatch() throws Exception {
        // Initialize the database
        workspaceLocationRepository.saveAndFlush(workspaceLocation);

        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();

        // Update the workspaceLocation using partial update
        WorkspaceLocation partialUpdatedWorkspaceLocation = new WorkspaceLocation();
        partialUpdatedWorkspaceLocation.setId(workspaceLocation.getId());

        partialUpdatedWorkspaceLocation
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

        restWorkspaceLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkspaceLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkspaceLocation))
            )
            .andExpect(status().isOk());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
        WorkspaceLocation testWorkspaceLocation = workspaceLocationList.get(workspaceLocationList.size() - 1);
        assertThat(testWorkspaceLocation.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testWorkspaceLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWorkspaceLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWorkspaceLocation.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testWorkspaceLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWorkspaceLocation.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testWorkspaceLocation.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testWorkspaceLocation.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testWorkspaceLocation.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testWorkspaceLocation.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testWorkspaceLocation.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testWorkspaceLocation.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void patchNonExistingWorkspaceLocation() throws Exception {
        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();
        workspaceLocation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workspaceLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkspaceLocation() throws Exception {
        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();
        workspaceLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkspaceLocation() throws Exception {
        int databaseSizeBeforeUpdate = workspaceLocationRepository.findAll().size();
        workspaceLocation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceLocationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workspaceLocation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkspaceLocation in the database
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkspaceLocation() throws Exception {
        // Initialize the database
        workspaceLocationRepository.saveAndFlush(workspaceLocation);

        int databaseSizeBeforeDelete = workspaceLocationRepository.findAll().size();

        // Delete the workspaceLocation
        restWorkspaceLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, workspaceLocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkspaceLocation> workspaceLocationList = workspaceLocationRepository.findAll();
        assertThat(workspaceLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
