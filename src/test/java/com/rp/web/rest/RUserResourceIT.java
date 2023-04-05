package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.RUser;
import com.rp.repository.RUserRepository;
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
 * Integration tests for the {@link RUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RUserResourceIT {

    private static final String ENTITY_API_URL = "/api/r-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RUserRepository rUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRUserMockMvc;

    private RUser rUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RUser createEntity(EntityManager em) {
        RUser rUser = new RUser();
        return rUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RUser createUpdatedEntity(EntityManager em) {
        RUser rUser = new RUser();
        return rUser;
    }

    @BeforeEach
    public void initTest() {
        rUser = createEntity(em);
    }

    @Test
    @Transactional
    void createRUser() throws Exception {
        int databaseSizeBeforeCreate = rUserRepository.findAll().size();
        // Create the RUser
        restRUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rUser)))
            .andExpect(status().isCreated());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeCreate + 1);
        RUser testRUser = rUserList.get(rUserList.size() - 1);
    }

    @Test
    @Transactional
    void createRUserWithExistingId() throws Exception {
        // Create the RUser with an existing ID
        rUser.setId(1L);

        int databaseSizeBeforeCreate = rUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rUser)))
            .andExpect(status().isBadRequest());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRUsers() throws Exception {
        // Initialize the database
        rUserRepository.saveAndFlush(rUser);

        // Get all the rUserList
        restRUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rUser.getId().intValue())));
    }

    @Test
    @Transactional
    void getRUser() throws Exception {
        // Initialize the database
        rUserRepository.saveAndFlush(rUser);

        // Get the rUser
        restRUserMockMvc
            .perform(get(ENTITY_API_URL_ID, rUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rUser.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRUser() throws Exception {
        // Get the rUser
        restRUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRUser() throws Exception {
        // Initialize the database
        rUserRepository.saveAndFlush(rUser);

        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();

        // Update the rUser
        RUser updatedRUser = rUserRepository.findById(rUser.getId()).get();
        // Disconnect from session so that the updates on updatedRUser are not directly saved in db
        em.detach(updatedRUser);

        restRUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRUser))
            )
            .andExpect(status().isOk());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
        RUser testRUser = rUserList.get(rUserList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingRUser() throws Exception {
        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();
        rUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRUser() throws Exception {
        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();
        rUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRUser() throws Exception {
        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();
        rUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRUserWithPatch() throws Exception {
        // Initialize the database
        rUserRepository.saveAndFlush(rUser);

        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();

        // Update the rUser using partial update
        RUser partialUpdatedRUser = new RUser();
        partialUpdatedRUser.setId(rUser.getId());

        restRUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRUser))
            )
            .andExpect(status().isOk());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
        RUser testRUser = rUserList.get(rUserList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateRUserWithPatch() throws Exception {
        // Initialize the database
        rUserRepository.saveAndFlush(rUser);

        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();

        // Update the rUser using partial update
        RUser partialUpdatedRUser = new RUser();
        partialUpdatedRUser.setId(rUser.getId());

        restRUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRUser))
            )
            .andExpect(status().isOk());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
        RUser testRUser = rUserList.get(rUserList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingRUser() throws Exception {
        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();
        rUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRUser() throws Exception {
        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();
        rUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRUser() throws Exception {
        int databaseSizeBeforeUpdate = rUserRepository.findAll().size();
        rUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RUser in the database
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRUser() throws Exception {
        // Initialize the database
        rUserRepository.saveAndFlush(rUser);

        int databaseSizeBeforeDelete = rUserRepository.findAll().size();

        // Delete the rUser
        restRUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, rUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RUser> rUserList = rUserRepository.findAll();
        assertThat(rUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
