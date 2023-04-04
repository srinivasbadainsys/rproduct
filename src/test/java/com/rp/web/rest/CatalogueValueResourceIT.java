package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.CatalogueValue;
import com.rp.repository.CatalogueValueRepository;
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
 * Integration tests for the {@link CatalogueValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CatalogueValueResourceIT {

    private static final Long DEFAULT_CATALOGUE_ID = 1L;
    private static final Long UPDATED_CATALOGUE_ID = 2L;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/catalogue-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CatalogueValueRepository catalogueValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogueValueMockMvc;

    private CatalogueValue catalogueValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogueValue createEntity(EntityManager em) {
        CatalogueValue catalogueValue = new CatalogueValue().catalogueId(DEFAULT_CATALOGUE_ID).value(DEFAULT_VALUE);
        return catalogueValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogueValue createUpdatedEntity(EntityManager em) {
        CatalogueValue catalogueValue = new CatalogueValue().catalogueId(UPDATED_CATALOGUE_ID).value(UPDATED_VALUE);
        return catalogueValue;
    }

    @BeforeEach
    public void initTest() {
        catalogueValue = createEntity(em);
    }

    @Test
    @Transactional
    void createCatalogueValue() throws Exception {
        int databaseSizeBeforeCreate = catalogueValueRepository.findAll().size();
        // Create the CatalogueValue
        restCatalogueValueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueValue))
            )
            .andExpect(status().isCreated());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeCreate + 1);
        CatalogueValue testCatalogueValue = catalogueValueList.get(catalogueValueList.size() - 1);
        assertThat(testCatalogueValue.getCatalogueId()).isEqualTo(DEFAULT_CATALOGUE_ID);
        assertThat(testCatalogueValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createCatalogueValueWithExistingId() throws Exception {
        // Create the CatalogueValue with an existing ID
        catalogueValue.setId(1L);

        int databaseSizeBeforeCreate = catalogueValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueValueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCatalogueValues() throws Exception {
        // Initialize the database
        catalogueValueRepository.saveAndFlush(catalogueValue);

        // Get all the catalogueValueList
        restCatalogueValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogueValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].catalogueId").value(hasItem(DEFAULT_CATALOGUE_ID.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getCatalogueValue() throws Exception {
        // Initialize the database
        catalogueValueRepository.saveAndFlush(catalogueValue);

        // Get the catalogueValue
        restCatalogueValueMockMvc
            .perform(get(ENTITY_API_URL_ID, catalogueValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogueValue.getId().intValue()))
            .andExpect(jsonPath("$.catalogueId").value(DEFAULT_CATALOGUE_ID.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingCatalogueValue() throws Exception {
        // Get the catalogueValue
        restCatalogueValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCatalogueValue() throws Exception {
        // Initialize the database
        catalogueValueRepository.saveAndFlush(catalogueValue);

        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();

        // Update the catalogueValue
        CatalogueValue updatedCatalogueValue = catalogueValueRepository.findById(catalogueValue.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogueValue are not directly saved in db
        em.detach(updatedCatalogueValue);
        updatedCatalogueValue.catalogueId(UPDATED_CATALOGUE_ID).value(UPDATED_VALUE);

        restCatalogueValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCatalogueValue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCatalogueValue))
            )
            .andExpect(status().isOk());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
        CatalogueValue testCatalogueValue = catalogueValueList.get(catalogueValueList.size() - 1);
        assertThat(testCatalogueValue.getCatalogueId()).isEqualTo(UPDATED_CATALOGUE_ID);
        assertThat(testCatalogueValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingCatalogueValue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();
        catalogueValue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, catalogueValue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catalogueValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCatalogueValue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();
        catalogueValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catalogueValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCatalogueValue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();
        catalogueValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueValueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogueValue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCatalogueValueWithPatch() throws Exception {
        // Initialize the database
        catalogueValueRepository.saveAndFlush(catalogueValue);

        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();

        // Update the catalogueValue using partial update
        CatalogueValue partialUpdatedCatalogueValue = new CatalogueValue();
        partialUpdatedCatalogueValue.setId(catalogueValue.getId());

        partialUpdatedCatalogueValue.catalogueId(UPDATED_CATALOGUE_ID).value(UPDATED_VALUE);

        restCatalogueValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCatalogueValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogueValue))
            )
            .andExpect(status().isOk());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
        CatalogueValue testCatalogueValue = catalogueValueList.get(catalogueValueList.size() - 1);
        assertThat(testCatalogueValue.getCatalogueId()).isEqualTo(UPDATED_CATALOGUE_ID);
        assertThat(testCatalogueValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateCatalogueValueWithPatch() throws Exception {
        // Initialize the database
        catalogueValueRepository.saveAndFlush(catalogueValue);

        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();

        // Update the catalogueValue using partial update
        CatalogueValue partialUpdatedCatalogueValue = new CatalogueValue();
        partialUpdatedCatalogueValue.setId(catalogueValue.getId());

        partialUpdatedCatalogueValue.catalogueId(UPDATED_CATALOGUE_ID).value(UPDATED_VALUE);

        restCatalogueValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCatalogueValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogueValue))
            )
            .andExpect(status().isOk());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
        CatalogueValue testCatalogueValue = catalogueValueList.get(catalogueValueList.size() - 1);
        assertThat(testCatalogueValue.getCatalogueId()).isEqualTo(UPDATED_CATALOGUE_ID);
        assertThat(testCatalogueValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingCatalogueValue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();
        catalogueValue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, catalogueValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catalogueValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCatalogueValue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();
        catalogueValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catalogueValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCatalogueValue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueValueRepository.findAll().size();
        catalogueValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueValueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(catalogueValue))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CatalogueValue in the database
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCatalogueValue() throws Exception {
        // Initialize the database
        catalogueValueRepository.saveAndFlush(catalogueValue);

        int databaseSizeBeforeDelete = catalogueValueRepository.findAll().size();

        // Delete the catalogueValue
        restCatalogueValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, catalogueValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatalogueValue> catalogueValueList = catalogueValueRepository.findAll();
        assertThat(catalogueValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
