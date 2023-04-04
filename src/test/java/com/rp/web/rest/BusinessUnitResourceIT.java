package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.BusinessUnit;
import com.rp.repository.BusinessUnitRepository;
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
 * Integration tests for the {@link BusinessUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusinessUnitResourceIT {

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_CONTACT_EXTN = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_CONTACT_EXTN = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/business-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessUnitMockMvc;

    private BusinessUnit businessUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnit createEntity(EntityManager em) {
        BusinessUnit businessUnit = new BusinessUnit()
            .unitName(DEFAULT_UNIT_NAME)
            .address(DEFAULT_ADDRESS)
            .mobileContact(DEFAULT_MOBILE_CONTACT)
            .officeContact(DEFAULT_OFFICE_CONTACT)
            .officeContactExtn(DEFAULT_OFFICE_CONTACT_EXTN)
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
        return businessUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnit createUpdatedEntity(EntityManager em) {
        BusinessUnit businessUnit = new BusinessUnit()
            .unitName(UPDATED_UNIT_NAME)
            .address(UPDATED_ADDRESS)
            .mobileContact(UPDATED_MOBILE_CONTACT)
            .officeContact(UPDATED_OFFICE_CONTACT)
            .officeContactExtn(UPDATED_OFFICE_CONTACT_EXTN)
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
        return businessUnit;
    }

    @BeforeEach
    public void initTest() {
        businessUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createBusinessUnit() throws Exception {
        int databaseSizeBeforeCreate = businessUnitRepository.findAll().size();
        // Create the BusinessUnit
        restBusinessUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessUnit)))
            .andExpect(status().isCreated());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
        assertThat(testBusinessUnit.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBusinessUnit.getMobileContact()).isEqualTo(DEFAULT_MOBILE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContact()).isEqualTo(DEFAULT_OFFICE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContactExtn()).isEqualTo(DEFAULT_OFFICE_CONTACT_EXTN);
        assertThat(testBusinessUnit.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testBusinessUnit.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBusinessUnit.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testBusinessUnit.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testBusinessUnit.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testBusinessUnit.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testBusinessUnit.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testBusinessUnit.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testBusinessUnit.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testBusinessUnit.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testBusinessUnit.getContinentCode()).isEqualTo(DEFAULT_CONTINENT_CODE);
        assertThat(testBusinessUnit.getPoint()).isEqualTo(DEFAULT_POINT);
    }

    @Test
    @Transactional
    void createBusinessUnitWithExistingId() throws Exception {
        // Create the BusinessUnit with an existing ID
        businessUnit.setId(1L);

        int databaseSizeBeforeCreate = businessUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessUnit)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBusinessUnits() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        // Get all the businessUnitList
        restBusinessUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].mobileContact").value(hasItem(DEFAULT_MOBILE_CONTACT)))
            .andExpect(jsonPath("$.[*].officeContact").value(hasItem(DEFAULT_OFFICE_CONTACT)))
            .andExpect(jsonPath("$.[*].officeContactExtn").value(hasItem(DEFAULT_OFFICE_CONTACT_EXTN)))
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
    void getBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        // Get the businessUnit
        restBusinessUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, businessUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessUnit.getId().intValue()))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.mobileContact").value(DEFAULT_MOBILE_CONTACT))
            .andExpect(jsonPath("$.officeContact").value(DEFAULT_OFFICE_CONTACT))
            .andExpect(jsonPath("$.officeContactExtn").value(DEFAULT_OFFICE_CONTACT_EXTN))
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
    void getNonExistingBusinessUnit() throws Exception {
        // Get the businessUnit
        restBusinessUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Update the businessUnit
        BusinessUnit updatedBusinessUnit = businessUnitRepository.findById(businessUnit.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessUnit are not directly saved in db
        em.detach(updatedBusinessUnit);
        updatedBusinessUnit
            .unitName(UPDATED_UNIT_NAME)
            .address(UPDATED_ADDRESS)
            .mobileContact(UPDATED_MOBILE_CONTACT)
            .officeContact(UPDATED_OFFICE_CONTACT)
            .officeContactExtn(UPDATED_OFFICE_CONTACT_EXTN)
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

        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBusinessUnit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBusinessUnit))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
        assertThat(testBusinessUnit.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBusinessUnit.getMobileContact()).isEqualTo(UPDATED_MOBILE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContact()).isEqualTo(UPDATED_OFFICE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContactExtn()).isEqualTo(UPDATED_OFFICE_CONTACT_EXTN);
        assertThat(testBusinessUnit.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testBusinessUnit.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBusinessUnit.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testBusinessUnit.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testBusinessUnit.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testBusinessUnit.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testBusinessUnit.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testBusinessUnit.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testBusinessUnit.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testBusinessUnit.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testBusinessUnit.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testBusinessUnit.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void putNonExistingBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessUnit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessUnit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessUnitWithPatch() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Update the businessUnit using partial update
        BusinessUnit partialUpdatedBusinessUnit = new BusinessUnit();
        partialUpdatedBusinessUnit.setId(businessUnit.getId());

        partialUpdatedBusinessUnit
            .unitName(UPDATED_UNIT_NAME)
            .mobileContact(UPDATED_MOBILE_CONTACT)
            .officeContact(UPDATED_OFFICE_CONTACT)
            .area(UPDATED_AREA)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .lon(UPDATED_LON)
            .continent(UPDATED_CONTINENT)
            .point(UPDATED_POINT);

        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessUnit))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
        assertThat(testBusinessUnit.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBusinessUnit.getMobileContact()).isEqualTo(UPDATED_MOBILE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContact()).isEqualTo(UPDATED_OFFICE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContactExtn()).isEqualTo(DEFAULT_OFFICE_CONTACT_EXTN);
        assertThat(testBusinessUnit.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testBusinessUnit.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBusinessUnit.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testBusinessUnit.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testBusinessUnit.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testBusinessUnit.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testBusinessUnit.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testBusinessUnit.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testBusinessUnit.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testBusinessUnit.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testBusinessUnit.getContinentCode()).isEqualTo(DEFAULT_CONTINENT_CODE);
        assertThat(testBusinessUnit.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void fullUpdateBusinessUnitWithPatch() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Update the businessUnit using partial update
        BusinessUnit partialUpdatedBusinessUnit = new BusinessUnit();
        partialUpdatedBusinessUnit.setId(businessUnit.getId());

        partialUpdatedBusinessUnit
            .unitName(UPDATED_UNIT_NAME)
            .address(UPDATED_ADDRESS)
            .mobileContact(UPDATED_MOBILE_CONTACT)
            .officeContact(UPDATED_OFFICE_CONTACT)
            .officeContactExtn(UPDATED_OFFICE_CONTACT_EXTN)
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

        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessUnit))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
        assertThat(testBusinessUnit.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBusinessUnit.getMobileContact()).isEqualTo(UPDATED_MOBILE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContact()).isEqualTo(UPDATED_OFFICE_CONTACT);
        assertThat(testBusinessUnit.getOfficeContactExtn()).isEqualTo(UPDATED_OFFICE_CONTACT_EXTN);
        assertThat(testBusinessUnit.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testBusinessUnit.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBusinessUnit.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testBusinessUnit.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testBusinessUnit.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testBusinessUnit.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testBusinessUnit.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testBusinessUnit.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testBusinessUnit.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testBusinessUnit.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testBusinessUnit.getContinentCode()).isEqualTo(UPDATED_CONTINENT_CODE);
        assertThat(testBusinessUnit.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    void patchNonExistingBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(businessUnit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeDelete = businessUnitRepository.findAll().size();

        // Delete the businessUnit
        restBusinessUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
