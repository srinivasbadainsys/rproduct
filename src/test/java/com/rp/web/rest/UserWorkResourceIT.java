package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.UserWork;
import com.rp.repository.UserWorkRepository;
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
 * Integration tests for the {@link UserWorkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserWorkResourceIT {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final String ENTITY_API_URL = "/api/user-works";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserWorkRepository userWorkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserWorkMockMvc;

    private UserWork userWork;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWork createEntity(EntityManager em) {
        UserWork userWork = new UserWork().jobId(DEFAULT_JOB_ID);
        return userWork;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWork createUpdatedEntity(EntityManager em) {
        UserWork userWork = new UserWork().jobId(UPDATED_JOB_ID);
        return userWork;
    }

    @BeforeEach
    public void initTest() {
        userWork = createEntity(em);
    }

    @Test
    @Transactional
    void createUserWork() throws Exception {
        int databaseSizeBeforeCreate = userWorkRepository.findAll().size();
        // Create the UserWork
        restUserWorkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userWork)))
            .andExpect(status().isCreated());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeCreate + 1);
        UserWork testUserWork = userWorkList.get(userWorkList.size() - 1);
        assertThat(testUserWork.getJobId()).isEqualTo(DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    void createUserWorkWithExistingId() throws Exception {
        // Create the UserWork with an existing ID
        userWork.setId(1L);

        int databaseSizeBeforeCreate = userWorkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWorkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userWork)))
            .andExpect(status().isBadRequest());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserWorks() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get all the userWorkList
        restUserWorkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())));
    }

    @Test
    @Transactional
    void getUserWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        // Get the userWork
        restUserWorkMockMvc
            .perform(get(ENTITY_API_URL_ID, userWork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userWork.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserWork() throws Exception {
        // Get the userWork
        restUserWorkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();

        // Update the userWork
        UserWork updatedUserWork = userWorkRepository.findById(userWork.getId()).get();
        // Disconnect from session so that the updates on updatedUserWork are not directly saved in db
        em.detach(updatedUserWork);
        updatedUserWork.jobId(UPDATED_JOB_ID);

        restUserWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserWork.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserWork))
            )
            .andExpect(status().isOk());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
        UserWork testUserWork = userWorkList.get(userWorkList.size() - 1);
        assertThat(testUserWork.getJobId()).isEqualTo(UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void putNonExistingUserWork() throws Exception {
        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();
        userWork.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userWork.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserWork() throws Exception {
        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();
        userWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWorkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserWork() throws Exception {
        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();
        userWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWorkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userWork)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserWorkWithPatch() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();

        // Update the userWork using partial update
        UserWork partialUpdatedUserWork = new UserWork();
        partialUpdatedUserWork.setId(userWork.getId());

        restUserWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserWork))
            )
            .andExpect(status().isOk());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
        UserWork testUserWork = userWorkList.get(userWorkList.size() - 1);
        assertThat(testUserWork.getJobId()).isEqualTo(DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    void fullUpdateUserWorkWithPatch() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();

        // Update the userWork using partial update
        UserWork partialUpdatedUserWork = new UserWork();
        partialUpdatedUserWork.setId(userWork.getId());

        partialUpdatedUserWork.jobId(UPDATED_JOB_ID);

        restUserWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserWork))
            )
            .andExpect(status().isOk());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
        UserWork testUserWork = userWorkList.get(userWorkList.size() - 1);
        assertThat(testUserWork.getJobId()).isEqualTo(UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void patchNonExistingUserWork() throws Exception {
        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();
        userWork.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userWork.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserWork() throws Exception {
        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();
        userWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWorkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userWork))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserWork() throws Exception {
        int databaseSizeBeforeUpdate = userWorkRepository.findAll().size();
        userWork.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWorkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userWork)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserWork in the database
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserWork() throws Exception {
        // Initialize the database
        userWorkRepository.saveAndFlush(userWork);

        int databaseSizeBeforeDelete = userWorkRepository.findAll().size();

        // Delete the userWork
        restUserWorkMockMvc
            .perform(delete(ENTITY_API_URL_ID, userWork.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserWork> userWorkList = userWorkRepository.findAll();
        assertThat(userWorkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
