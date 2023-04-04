package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.Workspace;
import com.rp.repository.WorkspaceRepository;
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
 * Integration tests for the {@link WorkspaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkspaceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_UR_LS = "AAAAAAAAAA";
    private static final String UPDATED_ORG_UR_LS = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_USER_ID = 1L;
    private static final Long UPDATED_OWNER_USER_ID = 2L;

    private static final String DEFAULT_MAIN_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_PHONE_NUMBERS = "AAAAAAAAAA";
    private static final String UPDATED_ALT_PHONE_NUMBERS = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_CONTACT_EMAILS = "AAAAAAAAAA";
    private static final String UPDATED_ALT_CONTACT_EMAILS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_AUTO_JOIN = false;
    private static final Boolean UPDATED_ENABLE_AUTO_JOIN = true;

    private static final Integer DEFAULT_MAX_USERS = 1;
    private static final Integer UPDATED_MAX_USERS = 2;

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINS = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/workspaces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkspaceMockMvc;

    private Workspace workspace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workspace createEntity(EntityManager em) {
        Workspace workspace = new Workspace()
            .name(DEFAULT_NAME)
            .orgName(DEFAULT_ORG_NAME)
            .about(DEFAULT_ABOUT)
            .link(DEFAULT_LINK)
            .orgURLs(DEFAULT_ORG_UR_LS)
            .ownerUserId(DEFAULT_OWNER_USER_ID)
            .mainPhoneNumber(DEFAULT_MAIN_PHONE_NUMBER)
            .altPhoneNumbers(DEFAULT_ALT_PHONE_NUMBERS)
            .mainContactEmail(DEFAULT_MAIN_CONTACT_EMAIL)
            .altContactEmails(DEFAULT_ALT_CONTACT_EMAILS)
            .status(DEFAULT_STATUS)
            .enableAutoJoin(DEFAULT_ENABLE_AUTO_JOIN)
            .maxUsers(DEFAULT_MAX_USERS)
            .tags(DEFAULT_TAGS)
            .domains(DEFAULT_DOMAINS);
        return workspace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workspace createUpdatedEntity(EntityManager em) {
        Workspace workspace = new Workspace()
            .name(UPDATED_NAME)
            .orgName(UPDATED_ORG_NAME)
            .about(UPDATED_ABOUT)
            .link(UPDATED_LINK)
            .orgURLs(UPDATED_ORG_UR_LS)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .mainPhoneNumber(UPDATED_MAIN_PHONE_NUMBER)
            .altPhoneNumbers(UPDATED_ALT_PHONE_NUMBERS)
            .mainContactEmail(UPDATED_MAIN_CONTACT_EMAIL)
            .altContactEmails(UPDATED_ALT_CONTACT_EMAILS)
            .status(UPDATED_STATUS)
            .enableAutoJoin(UPDATED_ENABLE_AUTO_JOIN)
            .maxUsers(UPDATED_MAX_USERS)
            .tags(UPDATED_TAGS)
            .domains(UPDATED_DOMAINS);
        return workspace;
    }

    @BeforeEach
    public void initTest() {
        workspace = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkspace() throws Exception {
        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();
        // Create the Workspace
        restWorkspaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspace)))
            .andExpect(status().isCreated());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate + 1);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkspace.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testWorkspace.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testWorkspace.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testWorkspace.getOrgURLs()).isEqualTo(DEFAULT_ORG_UR_LS);
        assertThat(testWorkspace.getOwnerUserId()).isEqualTo(DEFAULT_OWNER_USER_ID);
        assertThat(testWorkspace.getMainPhoneNumber()).isEqualTo(DEFAULT_MAIN_PHONE_NUMBER);
        assertThat(testWorkspace.getAltPhoneNumbers()).isEqualTo(DEFAULT_ALT_PHONE_NUMBERS);
        assertThat(testWorkspace.getMainContactEmail()).isEqualTo(DEFAULT_MAIN_CONTACT_EMAIL);
        assertThat(testWorkspace.getAltContactEmails()).isEqualTo(DEFAULT_ALT_CONTACT_EMAILS);
        assertThat(testWorkspace.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkspace.getEnableAutoJoin()).isEqualTo(DEFAULT_ENABLE_AUTO_JOIN);
        assertThat(testWorkspace.getMaxUsers()).isEqualTo(DEFAULT_MAX_USERS);
        assertThat(testWorkspace.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testWorkspace.getDomains()).isEqualTo(DEFAULT_DOMAINS);
    }

    @Test
    @Transactional
    void createWorkspaceWithExistingId() throws Exception {
        // Create the Workspace with an existing ID
        workspace.setId(1L);

        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkspaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspace)))
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkspaces() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList
        restWorkspaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspace.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].orgURLs").value(hasItem(DEFAULT_ORG_UR_LS)))
            .andExpect(jsonPath("$.[*].ownerUserId").value(hasItem(DEFAULT_OWNER_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].mainPhoneNumber").value(hasItem(DEFAULT_MAIN_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].altPhoneNumbers").value(hasItem(DEFAULT_ALT_PHONE_NUMBERS)))
            .andExpect(jsonPath("$.[*].mainContactEmail").value(hasItem(DEFAULT_MAIN_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].altContactEmails").value(hasItem(DEFAULT_ALT_CONTACT_EMAILS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].enableAutoJoin").value(hasItem(DEFAULT_ENABLE_AUTO_JOIN.booleanValue())))
            .andExpect(jsonPath("$.[*].maxUsers").value(hasItem(DEFAULT_MAX_USERS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].domains").value(hasItem(DEFAULT_DOMAINS)));
    }

    @Test
    @Transactional
    void getWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get the workspace
        restWorkspaceMockMvc
            .perform(get(ENTITY_API_URL_ID, workspace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workspace.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.orgURLs").value(DEFAULT_ORG_UR_LS))
            .andExpect(jsonPath("$.ownerUserId").value(DEFAULT_OWNER_USER_ID.intValue()))
            .andExpect(jsonPath("$.mainPhoneNumber").value(DEFAULT_MAIN_PHONE_NUMBER))
            .andExpect(jsonPath("$.altPhoneNumbers").value(DEFAULT_ALT_PHONE_NUMBERS))
            .andExpect(jsonPath("$.mainContactEmail").value(DEFAULT_MAIN_CONTACT_EMAIL))
            .andExpect(jsonPath("$.altContactEmails").value(DEFAULT_ALT_CONTACT_EMAILS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.enableAutoJoin").value(DEFAULT_ENABLE_AUTO_JOIN.booleanValue()))
            .andExpect(jsonPath("$.maxUsers").value(DEFAULT_MAX_USERS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.domains").value(DEFAULT_DOMAINS));
    }

    @Test
    @Transactional
    void getNonExistingWorkspace() throws Exception {
        // Get the workspace
        restWorkspaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Update the workspace
        Workspace updatedWorkspace = workspaceRepository.findById(workspace.getId()).get();
        // Disconnect from session so that the updates on updatedWorkspace are not directly saved in db
        em.detach(updatedWorkspace);
        updatedWorkspace
            .name(UPDATED_NAME)
            .orgName(UPDATED_ORG_NAME)
            .about(UPDATED_ABOUT)
            .link(UPDATED_LINK)
            .orgURLs(UPDATED_ORG_UR_LS)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .mainPhoneNumber(UPDATED_MAIN_PHONE_NUMBER)
            .altPhoneNumbers(UPDATED_ALT_PHONE_NUMBERS)
            .mainContactEmail(UPDATED_MAIN_CONTACT_EMAIL)
            .altContactEmails(UPDATED_ALT_CONTACT_EMAILS)
            .status(UPDATED_STATUS)
            .enableAutoJoin(UPDATED_ENABLE_AUTO_JOIN)
            .maxUsers(UPDATED_MAX_USERS)
            .tags(UPDATED_TAGS)
            .domains(UPDATED_DOMAINS);

        restWorkspaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkspace.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkspace))
            )
            .andExpect(status().isOk());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkspace.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testWorkspace.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testWorkspace.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testWorkspace.getOrgURLs()).isEqualTo(UPDATED_ORG_UR_LS);
        assertThat(testWorkspace.getOwnerUserId()).isEqualTo(UPDATED_OWNER_USER_ID);
        assertThat(testWorkspace.getMainPhoneNumber()).isEqualTo(UPDATED_MAIN_PHONE_NUMBER);
        assertThat(testWorkspace.getAltPhoneNumbers()).isEqualTo(UPDATED_ALT_PHONE_NUMBERS);
        assertThat(testWorkspace.getMainContactEmail()).isEqualTo(UPDATED_MAIN_CONTACT_EMAIL);
        assertThat(testWorkspace.getAltContactEmails()).isEqualTo(UPDATED_ALT_CONTACT_EMAILS);
        assertThat(testWorkspace.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkspace.getEnableAutoJoin()).isEqualTo(UPDATED_ENABLE_AUTO_JOIN);
        assertThat(testWorkspace.getMaxUsers()).isEqualTo(UPDATED_MAX_USERS);
        assertThat(testWorkspace.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testWorkspace.getDomains()).isEqualTo(UPDATED_DOMAINS);
    }

    @Test
    @Transactional
    void putNonExistingWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();
        workspace.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workspace.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workspace))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();
        workspace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workspace))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();
        workspace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workspace)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkspaceWithPatch() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Update the workspace using partial update
        Workspace partialUpdatedWorkspace = new Workspace();
        partialUpdatedWorkspace.setId(workspace.getId());

        partialUpdatedWorkspace
            .about(UPDATED_ABOUT)
            .link(UPDATED_LINK)
            .orgURLs(UPDATED_ORG_UR_LS)
            .mainPhoneNumber(UPDATED_MAIN_PHONE_NUMBER)
            .maxUsers(UPDATED_MAX_USERS)
            .tags(UPDATED_TAGS);

        restWorkspaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkspace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkspace))
            )
            .andExpect(status().isOk());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkspace.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testWorkspace.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testWorkspace.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testWorkspace.getOrgURLs()).isEqualTo(UPDATED_ORG_UR_LS);
        assertThat(testWorkspace.getOwnerUserId()).isEqualTo(DEFAULT_OWNER_USER_ID);
        assertThat(testWorkspace.getMainPhoneNumber()).isEqualTo(UPDATED_MAIN_PHONE_NUMBER);
        assertThat(testWorkspace.getAltPhoneNumbers()).isEqualTo(DEFAULT_ALT_PHONE_NUMBERS);
        assertThat(testWorkspace.getMainContactEmail()).isEqualTo(DEFAULT_MAIN_CONTACT_EMAIL);
        assertThat(testWorkspace.getAltContactEmails()).isEqualTo(DEFAULT_ALT_CONTACT_EMAILS);
        assertThat(testWorkspace.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkspace.getEnableAutoJoin()).isEqualTo(DEFAULT_ENABLE_AUTO_JOIN);
        assertThat(testWorkspace.getMaxUsers()).isEqualTo(UPDATED_MAX_USERS);
        assertThat(testWorkspace.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testWorkspace.getDomains()).isEqualTo(DEFAULT_DOMAINS);
    }

    @Test
    @Transactional
    void fullUpdateWorkspaceWithPatch() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Update the workspace using partial update
        Workspace partialUpdatedWorkspace = new Workspace();
        partialUpdatedWorkspace.setId(workspace.getId());

        partialUpdatedWorkspace
            .name(UPDATED_NAME)
            .orgName(UPDATED_ORG_NAME)
            .about(UPDATED_ABOUT)
            .link(UPDATED_LINK)
            .orgURLs(UPDATED_ORG_UR_LS)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .mainPhoneNumber(UPDATED_MAIN_PHONE_NUMBER)
            .altPhoneNumbers(UPDATED_ALT_PHONE_NUMBERS)
            .mainContactEmail(UPDATED_MAIN_CONTACT_EMAIL)
            .altContactEmails(UPDATED_ALT_CONTACT_EMAILS)
            .status(UPDATED_STATUS)
            .enableAutoJoin(UPDATED_ENABLE_AUTO_JOIN)
            .maxUsers(UPDATED_MAX_USERS)
            .tags(UPDATED_TAGS)
            .domains(UPDATED_DOMAINS);

        restWorkspaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkspace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkspace))
            )
            .andExpect(status().isOk());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkspace.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testWorkspace.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testWorkspace.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testWorkspace.getOrgURLs()).isEqualTo(UPDATED_ORG_UR_LS);
        assertThat(testWorkspace.getOwnerUserId()).isEqualTo(UPDATED_OWNER_USER_ID);
        assertThat(testWorkspace.getMainPhoneNumber()).isEqualTo(UPDATED_MAIN_PHONE_NUMBER);
        assertThat(testWorkspace.getAltPhoneNumbers()).isEqualTo(UPDATED_ALT_PHONE_NUMBERS);
        assertThat(testWorkspace.getMainContactEmail()).isEqualTo(UPDATED_MAIN_CONTACT_EMAIL);
        assertThat(testWorkspace.getAltContactEmails()).isEqualTo(UPDATED_ALT_CONTACT_EMAILS);
        assertThat(testWorkspace.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkspace.getEnableAutoJoin()).isEqualTo(UPDATED_ENABLE_AUTO_JOIN);
        assertThat(testWorkspace.getMaxUsers()).isEqualTo(UPDATED_MAX_USERS);
        assertThat(testWorkspace.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testWorkspace.getDomains()).isEqualTo(UPDATED_DOMAINS);
    }

    @Test
    @Transactional
    void patchNonExistingWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();
        workspace.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workspace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workspace))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();
        workspace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workspace))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();
        workspace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workspace))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeDelete = workspaceRepository.findAll().size();

        // Delete the workspace
        restWorkspaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, workspace.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
