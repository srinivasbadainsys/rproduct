package com.rp.web.rest;

import static com.rp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.Ruser;
import com.rp.repository.RuserRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link RuserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RuserResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_LANG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LANG_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESET_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESET_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/rusers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RuserRepository ruserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuserMockMvc;

    private Ruser ruser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ruser createEntity(EntityManager em) {
        Ruser ruser = new Ruser()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .activated(DEFAULT_ACTIVATED)
            .langKey(DEFAULT_LANG_KEY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE);
        return ruser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ruser createUpdatedEntity(EntityManager em) {
        Ruser ruser = new Ruser()
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE);
        return ruser;
    }

    @BeforeEach
    public void initTest() {
        ruser = createEntity(em);
    }

    @Test
    @Transactional
    void createRuser() throws Exception {
        int databaseSizeBeforeCreate = ruserRepository.findAll().size();
        // Create the Ruser
        restRuserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruser)))
            .andExpect(status().isCreated());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeCreate + 1);
        Ruser testRuser = ruserList.get(ruserList.size() - 1);
        assertThat(testRuser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testRuser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testRuser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testRuser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testRuser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRuser.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testRuser.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testRuser.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testRuser.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testRuser.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testRuser.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
    }

    @Test
    @Transactional
    void createRuserWithExistingId() throws Exception {
        // Create the Ruser with an existing ID
        ruser.setId(1L);

        int databaseSizeBeforeCreate = ruserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruser)))
            .andExpect(status().isBadRequest());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRusers() throws Exception {
        // Initialize the database
        ruserRepository.saveAndFlush(ruser);

        // Get all the ruserList
        restRuserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruser.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(sameInstant(DEFAULT_RESET_DATE))));
    }

    @Test
    @Transactional
    void getRuser() throws Exception {
        // Initialize the database
        ruserRepository.saveAndFlush(ruser);

        // Get the ruser
        restRuserMockMvc
            .perform(get(ENTITY_API_URL_ID, ruser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruser.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY))
            .andExpect(jsonPath("$.resetDate").value(sameInstant(DEFAULT_RESET_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingRuser() throws Exception {
        // Get the ruser
        restRuserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRuser() throws Exception {
        // Initialize the database
        ruserRepository.saveAndFlush(ruser);

        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();

        // Update the ruser
        Ruser updatedRuser = ruserRepository.findById(ruser.getId()).get();
        // Disconnect from session so that the updates on updatedRuser are not directly saved in db
        em.detach(updatedRuser);
        updatedRuser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE);

        restRuserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRuser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRuser))
            )
            .andExpect(status().isOk());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
        Ruser testRuser = ruserList.get(ruserList.size() - 1);
        assertThat(testRuser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testRuser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testRuser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testRuser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testRuser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRuser.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testRuser.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testRuser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testRuser.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testRuser.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testRuser.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRuser() throws Exception {
        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();
        ruser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ruser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ruser))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRuser() throws Exception {
        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();
        ruser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ruser))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRuser() throws Exception {
        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();
        ruser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRuserWithPatch() throws Exception {
        // Initialize the database
        ruserRepository.saveAndFlush(ruser);

        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();

        // Update the ruser using partial update
        Ruser partialUpdatedRuser = new Ruser();
        partialUpdatedRuser.setId(ruser.getId());

        partialUpdatedRuser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .resetDate(UPDATED_RESET_DATE);

        restRuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRuser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRuser))
            )
            .andExpect(status().isOk());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
        Ruser testRuser = ruserList.get(ruserList.size() - 1);
        assertThat(testRuser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testRuser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testRuser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testRuser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testRuser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRuser.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testRuser.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testRuser.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testRuser.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testRuser.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testRuser.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRuserWithPatch() throws Exception {
        // Initialize the database
        ruserRepository.saveAndFlush(ruser);

        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();

        // Update the ruser using partial update
        Ruser partialUpdatedRuser = new Ruser();
        partialUpdatedRuser.setId(ruser.getId());

        partialUpdatedRuser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .imageUrl(UPDATED_IMAGE_URL)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE);

        restRuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRuser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRuser))
            )
            .andExpect(status().isOk());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
        Ruser testRuser = ruserList.get(ruserList.size() - 1);
        assertThat(testRuser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testRuser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testRuser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testRuser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testRuser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRuser.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testRuser.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testRuser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testRuser.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testRuser.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testRuser.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRuser() throws Exception {
        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();
        ruser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ruser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ruser))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRuser() throws Exception {
        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();
        ruser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ruser))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRuser() throws Exception {
        int databaseSizeBeforeUpdate = ruserRepository.findAll().size();
        ruser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRuserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ruser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ruser in the database
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRuser() throws Exception {
        // Initialize the database
        ruserRepository.saveAndFlush(ruser);

        int databaseSizeBeforeDelete = ruserRepository.findAll().size();

        // Delete the ruser
        restRuserMockMvc
            .perform(delete(ENTITY_API_URL_ID, ruser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ruser> ruserList = ruserRepository.findAll();
        assertThat(ruserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
