package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.Contact;
import com.rp.repository.ContactRepository;
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
 * Integration tests for the {@link ContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactResourceIT {

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_NUMBER_EXTN = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NUMBER_EXTN = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_ALT_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OWNERSHIP_IDS = "AAAAAAAAAA";
    private static final String UPDATED_OWNERSHIP_IDS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOW_ACCESS_TO_ALL_USERS = false;
    private static final Boolean UPDATED_ALLOW_ACCESS_TO_ALL_USERS = true;

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

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

    private static final String DEFAULT_PROFILE_UR_LS = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_UR_LS = "BBBBBBBBBB";

    private static final String DEFAULT_MESSENGER_I_DS = "AAAAAAAAAA";
    private static final String UPDATED_MESSENGER_I_DS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMockMvc;

    private Contact contact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .clientId(DEFAULT_CLIENT_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .designation(DEFAULT_DESIGNATION)
            .officeNumber(DEFAULT_OFFICE_NUMBER)
            .officeNumberExtn(DEFAULT_OFFICE_NUMBER_EXTN)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .emailID(DEFAULT_EMAIL_ID)
            .altEmailID(DEFAULT_ALT_EMAIL_ID)
            .ownershipIds(DEFAULT_OWNERSHIP_IDS)
            .allowAccessToAllUsers(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .area(DEFAULT_AREA)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .stateCode(DEFAULT_STATE_CODE)
            .county(DEFAULT_COUNTY)
            .country(DEFAULT_COUNTRY)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .zipCode(DEFAULT_ZIP_CODE)
            .profileURLs(DEFAULT_PROFILE_UR_LS)
            .messengerIDs(DEFAULT_MESSENGER_I_DS)
            .status(DEFAULT_STATUS)
            .clientGroup(DEFAULT_CLIENT_GROUP)
            .about(DEFAULT_ABOUT);
        return contact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createUpdatedEntity(EntityManager em) {
        Contact contact = new Contact()
            .clientId(UPDATED_CLIENT_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION)
            .officeNumber(UPDATED_OFFICE_NUMBER)
            .officeNumberExtn(UPDATED_OFFICE_NUMBER_EXTN)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailID(UPDATED_EMAIL_ID)
            .altEmailID(UPDATED_ALT_EMAIL_ID)
            .ownershipIds(UPDATED_OWNERSHIP_IDS)
            .allowAccessToAllUsers(UPDATED_ALLOW_ACCESS_TO_ALL_USERS)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .profileURLs(UPDATED_PROFILE_UR_LS)
            .messengerIDs(UPDATED_MESSENGER_I_DS)
            .status(UPDATED_STATUS)
            .clientGroup(UPDATED_CLIENT_GROUP)
            .about(UPDATED_ABOUT);
        return contact;
    }

    @BeforeEach
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();
        // Create the Contact
        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContact.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testContact.getOfficeNumber()).isEqualTo(DEFAULT_OFFICE_NUMBER);
        assertThat(testContact.getOfficeNumberExtn()).isEqualTo(DEFAULT_OFFICE_NUMBER_EXTN);
        assertThat(testContact.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testContact.getEmailID()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testContact.getAltEmailID()).isEqualTo(DEFAULT_ALT_EMAIL_ID);
        assertThat(testContact.getOwnershipIds()).isEqualTo(DEFAULT_OWNERSHIP_IDS);
        assertThat(testContact.getAllowAccessToAllUsers()).isEqualTo(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testContact.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testContact.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testContact.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testContact.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContact.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testContact.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testContact.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testContact.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testContact.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testContact.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testContact.getProfileURLs()).isEqualTo(DEFAULT_PROFILE_UR_LS);
        assertThat(testContact.getMessengerIDs()).isEqualTo(DEFAULT_MESSENGER_I_DS);
        assertThat(testContact.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testContact.getClientGroup()).isEqualTo(DEFAULT_CLIENT_GROUP);
        assertThat(testContact.getAbout()).isEqualTo(DEFAULT_ABOUT);
    }

    @Test
    @Transactional
    void createContactWithExistingId() throws Exception {
        // Create the Contact with an existing ID
        contact.setId(1L);

        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList
        restContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].officeNumber").value(hasItem(DEFAULT_OFFICE_NUMBER)))
            .andExpect(jsonPath("$.[*].officeNumberExtn").value(hasItem(DEFAULT_OFFICE_NUMBER_EXTN)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailID").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].altEmailID").value(hasItem(DEFAULT_ALT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].ownershipIds").value(hasItem(DEFAULT_OWNERSHIP_IDS)))
            .andExpect(jsonPath("$.[*].allowAccessToAllUsers").value(hasItem(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS.booleanValue())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].profileURLs").value(hasItem(DEFAULT_PROFILE_UR_LS)))
            .andExpect(jsonPath("$.[*].messengerIDs").value(hasItem(DEFAULT_MESSENGER_I_DS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].clientGroup").value(hasItem(DEFAULT_CLIENT_GROUP)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)));
    }

    @Test
    @Transactional
    void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc
            .perform(get(ENTITY_API_URL_ID, contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.officeNumber").value(DEFAULT_OFFICE_NUMBER))
            .andExpect(jsonPath("$.officeNumberExtn").value(DEFAULT_OFFICE_NUMBER_EXTN))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.emailID").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.altEmailID").value(DEFAULT_ALT_EMAIL_ID))
            .andExpect(jsonPath("$.ownershipIds").value(DEFAULT_OWNERSHIP_IDS))
            .andExpect(jsonPath("$.allowAccessToAllUsers").value(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS.booleanValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.profileURLs").value(DEFAULT_PROFILE_UR_LS))
            .andExpect(jsonPath("$.messengerIDs").value(DEFAULT_MESSENGER_I_DS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.clientGroup").value(DEFAULT_CLIENT_GROUP))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT));
    }

    @Test
    @Transactional
    void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact
            .clientId(UPDATED_CLIENT_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION)
            .officeNumber(UPDATED_OFFICE_NUMBER)
            .officeNumberExtn(UPDATED_OFFICE_NUMBER_EXTN)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailID(UPDATED_EMAIL_ID)
            .altEmailID(UPDATED_ALT_EMAIL_ID)
            .ownershipIds(UPDATED_OWNERSHIP_IDS)
            .allowAccessToAllUsers(UPDATED_ALLOW_ACCESS_TO_ALL_USERS)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .profileURLs(UPDATED_PROFILE_UR_LS)
            .messengerIDs(UPDATED_MESSENGER_I_DS)
            .status(UPDATED_STATUS)
            .clientGroup(UPDATED_CLIENT_GROUP)
            .about(UPDATED_ABOUT);

        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContact.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testContact.getOfficeNumber()).isEqualTo(UPDATED_OFFICE_NUMBER);
        assertThat(testContact.getOfficeNumberExtn()).isEqualTo(UPDATED_OFFICE_NUMBER_EXTN);
        assertThat(testContact.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testContact.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testContact.getAltEmailID()).isEqualTo(UPDATED_ALT_EMAIL_ID);
        assertThat(testContact.getOwnershipIds()).isEqualTo(UPDATED_OWNERSHIP_IDS);
        assertThat(testContact.getAllowAccessToAllUsers()).isEqualTo(UPDATED_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testContact.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testContact.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testContact.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContact.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContact.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testContact.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testContact.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testContact.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testContact.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testContact.getProfileURLs()).isEqualTo(UPDATED_PROFILE_UR_LS);
        assertThat(testContact.getMessengerIDs()).isEqualTo(UPDATED_MESSENGER_I_DS);
        assertThat(testContact.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testContact.getClientGroup()).isEqualTo(UPDATED_CLIENT_GROUP);
        assertThat(testContact.getAbout()).isEqualTo(UPDATED_ABOUT);
    }

    @Test
    @Transactional
    void putNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contact.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactWithPatch() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact using partial update
        Contact partialUpdatedContact = new Contact();
        partialUpdatedContact.setId(contact.getId());

        partialUpdatedContact
            .clientId(UPDATED_CLIENT_ID)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailID(UPDATED_EMAIL_ID)
            .altEmailID(UPDATED_ALT_EMAIL_ID)
            .ownershipIds(UPDATED_OWNERSHIP_IDS)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);

        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContact.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testContact.getOfficeNumber()).isEqualTo(DEFAULT_OFFICE_NUMBER);
        assertThat(testContact.getOfficeNumberExtn()).isEqualTo(DEFAULT_OFFICE_NUMBER_EXTN);
        assertThat(testContact.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testContact.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testContact.getAltEmailID()).isEqualTo(UPDATED_ALT_EMAIL_ID);
        assertThat(testContact.getOwnershipIds()).isEqualTo(UPDATED_OWNERSHIP_IDS);
        assertThat(testContact.getAllowAccessToAllUsers()).isEqualTo(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testContact.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testContact.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testContact.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContact.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testContact.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testContact.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testContact.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testContact.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testContact.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testContact.getProfileURLs()).isEqualTo(DEFAULT_PROFILE_UR_LS);
        assertThat(testContact.getMessengerIDs()).isEqualTo(DEFAULT_MESSENGER_I_DS);
        assertThat(testContact.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testContact.getClientGroup()).isEqualTo(DEFAULT_CLIENT_GROUP);
        assertThat(testContact.getAbout()).isEqualTo(DEFAULT_ABOUT);
    }

    @Test
    @Transactional
    void fullUpdateContactWithPatch() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact using partial update
        Contact partialUpdatedContact = new Contact();
        partialUpdatedContact.setId(contact.getId());

        partialUpdatedContact
            .clientId(UPDATED_CLIENT_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION)
            .officeNumber(UPDATED_OFFICE_NUMBER)
            .officeNumberExtn(UPDATED_OFFICE_NUMBER_EXTN)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailID(UPDATED_EMAIL_ID)
            .altEmailID(UPDATED_ALT_EMAIL_ID)
            .ownershipIds(UPDATED_OWNERSHIP_IDS)
            .allowAccessToAllUsers(UPDATED_ALLOW_ACCESS_TO_ALL_USERS)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .profileURLs(UPDATED_PROFILE_UR_LS)
            .messengerIDs(UPDATED_MESSENGER_I_DS)
            .status(UPDATED_STATUS)
            .clientGroup(UPDATED_CLIENT_GROUP)
            .about(UPDATED_ABOUT);

        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContact.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testContact.getOfficeNumber()).isEqualTo(UPDATED_OFFICE_NUMBER);
        assertThat(testContact.getOfficeNumberExtn()).isEqualTo(UPDATED_OFFICE_NUMBER_EXTN);
        assertThat(testContact.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testContact.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testContact.getAltEmailID()).isEqualTo(UPDATED_ALT_EMAIL_ID);
        assertThat(testContact.getOwnershipIds()).isEqualTo(UPDATED_OWNERSHIP_IDS);
        assertThat(testContact.getAllowAccessToAllUsers()).isEqualTo(UPDATED_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testContact.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testContact.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testContact.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testContact.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContact.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContact.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testContact.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testContact.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testContact.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testContact.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testContact.getProfileURLs()).isEqualTo(UPDATED_PROFILE_UR_LS);
        assertThat(testContact.getMessengerIDs()).isEqualTo(UPDATED_MESSENGER_I_DS);
        assertThat(testContact.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testContact.getClientGroup()).isEqualTo(UPDATED_CLIENT_GROUP);
        assertThat(testContact.getAbout()).isEqualTo(UPDATED_ABOUT);
    }

    @Test
    @Transactional
    void patchNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contact))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Delete the contact
        restContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, contact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
