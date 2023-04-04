package com.rp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.Client;
import com.rp.domain.enumeration.ClientVisibility;
import com.rp.repository.ClientRepository;
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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final ClientVisibility DEFAULT_CLIENT_VISIBILITY = ClientVisibility.Organization_Level;
    private static final ClientVisibility UPDATED_CLIENT_VISIBILITY = ClientVisibility.Unit_Level;

    private static final String DEFAULT_BUSINESS_UNIT_IDS = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_UNIT_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VMS_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VMS_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FEDERAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_FEDERAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

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

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEND_REQUIREMENT = false;
    private static final Boolean UPDATED_SEND_REQUIREMENT = true;

    private static final Boolean DEFAULT_SEND_HOT_LIST = false;
    private static final Boolean UPDATED_SEND_HOT_LIST = true;

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOW_ACCESS_TO_ALL_USERS = false;
    private static final Boolean UPDATED_ALLOW_ACCESS_TO_ALL_USERS = true;

    private static final String DEFAULT_OWNERSHIP_IDS = "AAAAAAAAAA";
    private static final String UPDATED_OWNERSHIP_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_LEAD_IDS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_LEAD_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIRED_DOCUMENTS = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRED_DOCUMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_PRACTICE_ALT = "AAAAAAAAAA";
    private static final String UPDATED_PRACTICE_ALT = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT_COMPANY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT = false;
    private static final Boolean UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT = true;

    private static final Boolean DEFAULT_DEFAULT_FOR_JOB_POSTINGS = false;
    private static final Boolean UPDATED_DEFAULT_FOR_JOB_POSTINGS = true;

    private static final String DEFAULT_SUBMISSION_GUIDELINES = "AAAAAAAAAA";
    private static final String UPDATED_SUBMISSION_GUIDELINES = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_TO = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_TO_TEAMS = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_TO_TEAMS = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_MANAGERS = "AAAAAAAAAA";
    private static final String UPDATED_SALES_MANAGERS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_MANAGERS = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_MANAGERS = "BBBBBBBBBB";

    private static final String DEFAULT_RECRUITMENT_MANAGERS = "AAAAAAAAAA";
    private static final String UPDATED_RECRUITMENT_MANAGERS = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS = 1;
    private static final Integer UPDATED_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS = 2;

    private static final String DEFAULT_DEFAULT_TAT_CONFIG_TIMESPAN = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_TAT_CONFIG_TIMESPAN = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_ON_TAT_TO_USER_TYPES = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_ON_TAT_TO_USER_IDS = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ON_TAT_TO_USER_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_TERM_IDS = "AAAAAAAAAA";
    private static final String UPDATED_TAX_TERM_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_AUTHORIZATION_IDS = "AAAAAAAAAA";
    private static final String UPDATED_WORK_AUTHORIZATION_IDS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POST_JOB_ON_CAREERS_PAGE = false;
    private static final Boolean UPDATED_POST_JOB_ON_CAREERS_PAGE = true;

    private static final String DEFAULT_DEFAULT_PAY_RATE_TAX_TERM = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_PAY_RATE_TAX_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_BILL_RATE_TAX_TERM = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_BILL_RATE_TAX_TERM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REFERENCES_MANDATORY_FOR_SUBMISSION = false;
    private static final Boolean UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION = true;

    private static final Integer DEFAULT_MAX_SUBMISSIONS = 1;
    private static final Integer UPDATED_MAX_SUBMISSIONS = 2;

    private static final Integer DEFAULT_NO_OF_POSITIONS = 1;
    private static final Integer UPDATED_NO_OF_POSITIONS = 2;

    private static final String DEFAULT_MARK_UP = "AAAAAAAAAA";
    private static final String UPDATED_MARK_UP = "BBBBBBBBBB";

    private static final String DEFAULT_MSP = "AAAAAAAAAA";
    private static final String UPDATED_MSP = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_MAIL_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL_BODY = "AAAAAAAAAA";
    private static final String UPDATED_MAIL_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDS_FOR_EXCEL = "AAAAAAAAAA";
    private static final String UPDATED_FIELDS_FOR_EXCEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .clientVisibility(DEFAULT_CLIENT_VISIBILITY)
            .businessUnitIds(DEFAULT_BUSINESS_UNIT_IDS)
            .clientName(DEFAULT_CLIENT_NAME)
            .vmsClientName(DEFAULT_VMS_CLIENT_NAME)
            .federalID(DEFAULT_FEDERAL_ID)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .area(DEFAULT_AREA)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .stateCode(DEFAULT_STATE_CODE)
            .county(DEFAULT_COUNTY)
            .country(DEFAULT_COUNTRY)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .zipCode(DEFAULT_ZIP_CODE)
            .website(DEFAULT_WEBSITE)
            .sendRequirement(DEFAULT_SEND_REQUIREMENT)
            .sendHotList(DEFAULT_SEND_HOT_LIST)
            .fax(DEFAULT_FAX)
            .status(DEFAULT_STATUS)
            .allowAccessToAllUsers(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS)
            .ownershipIds(DEFAULT_OWNERSHIP_IDS)
            .clientLeadIds(DEFAULT_CLIENT_LEAD_IDS)
            .requiredDocuments(DEFAULT_REQUIRED_DOCUMENTS)
            .practiceAlt(DEFAULT_PRACTICE_ALT)
            .aboutCompany(DEFAULT_ABOUT_COMPANY)
            .stopNotifyingClientContactOnSubmitToClient(DEFAULT_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT)
            .defaultForJobPostings(DEFAULT_DEFAULT_FOR_JOB_POSTINGS)
            .submissionGuidelines(DEFAULT_SUBMISSION_GUIDELINES)
            .assignedTo(DEFAULT_ASSIGNED_TO)
            .assignedToTeams(DEFAULT_ASSIGNED_TO_TEAMS)
            .salesManagers(DEFAULT_SALES_MANAGERS)
            .accountManagers(DEFAULT_ACCOUNT_MANAGERS)
            .recruitmentManagers(DEFAULT_RECRUITMENT_MANAGERS)
            .defaultTATConfigForJobPostingOrVMSJobs(DEFAULT_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS)
            .defaultTATConfigTimespan(DEFAULT_DEFAULT_TAT_CONFIG_TIMESPAN)
            .notifyOnTATToUserTypes(DEFAULT_NOTIFY_ON_TAT_TO_USER_TYPES)
            .notifyOnTATToUserIds(DEFAULT_NOTIFY_ON_TAT_TO_USER_IDS)
            .taxTermIds(DEFAULT_TAX_TERM_IDS)
            .workAuthorizationIds(DEFAULT_WORK_AUTHORIZATION_IDS)
            .postJobOnCareersPage(DEFAULT_POST_JOB_ON_CAREERS_PAGE)
            .defaultPayRateTaxTerm(DEFAULT_DEFAULT_PAY_RATE_TAX_TERM)
            .defaultBillRateTaxTerm(DEFAULT_DEFAULT_BILL_RATE_TAX_TERM)
            .referencesMandatoryForSubmission(DEFAULT_REFERENCES_MANDATORY_FOR_SUBMISSION)
            .maxSubmissions(DEFAULT_MAX_SUBMISSIONS)
            .noOfPositions(DEFAULT_NO_OF_POSITIONS)
            .markUp(DEFAULT_MARK_UP)
            .msp(DEFAULT_MSP)
            .mailSubject(DEFAULT_MAIL_SUBJECT)
            .mailBody(DEFAULT_MAIL_BODY)
            .fieldsForExcel(DEFAULT_FIELDS_FOR_EXCEL);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .clientVisibility(UPDATED_CLIENT_VISIBILITY)
            .businessUnitIds(UPDATED_BUSINESS_UNIT_IDS)
            .clientName(UPDATED_CLIENT_NAME)
            .vmsClientName(UPDATED_VMS_CLIENT_NAME)
            .federalID(UPDATED_FEDERAL_ID)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .website(UPDATED_WEBSITE)
            .sendRequirement(UPDATED_SEND_REQUIREMENT)
            .sendHotList(UPDATED_SEND_HOT_LIST)
            .fax(UPDATED_FAX)
            .status(UPDATED_STATUS)
            .allowAccessToAllUsers(UPDATED_ALLOW_ACCESS_TO_ALL_USERS)
            .ownershipIds(UPDATED_OWNERSHIP_IDS)
            .clientLeadIds(UPDATED_CLIENT_LEAD_IDS)
            .requiredDocuments(UPDATED_REQUIRED_DOCUMENTS)
            .practiceAlt(UPDATED_PRACTICE_ALT)
            .aboutCompany(UPDATED_ABOUT_COMPANY)
            .stopNotifyingClientContactOnSubmitToClient(UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT)
            .defaultForJobPostings(UPDATED_DEFAULT_FOR_JOB_POSTINGS)
            .submissionGuidelines(UPDATED_SUBMISSION_GUIDELINES)
            .assignedTo(UPDATED_ASSIGNED_TO)
            .assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS)
            .salesManagers(UPDATED_SALES_MANAGERS)
            .accountManagers(UPDATED_ACCOUNT_MANAGERS)
            .recruitmentManagers(UPDATED_RECRUITMENT_MANAGERS)
            .defaultTATConfigForJobPostingOrVMSJobs(UPDATED_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS)
            .defaultTATConfigTimespan(UPDATED_DEFAULT_TAT_CONFIG_TIMESPAN)
            .notifyOnTATToUserTypes(UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES)
            .notifyOnTATToUserIds(UPDATED_NOTIFY_ON_TAT_TO_USER_IDS)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .postJobOnCareersPage(UPDATED_POST_JOB_ON_CAREERS_PAGE)
            .defaultPayRateTaxTerm(UPDATED_DEFAULT_PAY_RATE_TAX_TERM)
            .defaultBillRateTaxTerm(UPDATED_DEFAULT_BILL_RATE_TAX_TERM)
            .referencesMandatoryForSubmission(UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION)
            .maxSubmissions(UPDATED_MAX_SUBMISSIONS)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .markUp(UPDATED_MARK_UP)
            .msp(UPDATED_MSP)
            .mailSubject(UPDATED_MAIL_SUBJECT)
            .mailBody(UPDATED_MAIL_BODY)
            .fieldsForExcel(UPDATED_FIELDS_FOR_EXCEL);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientVisibility()).isEqualTo(DEFAULT_CLIENT_VISIBILITY);
        assertThat(testClient.getBusinessUnitIds()).isEqualTo(DEFAULT_BUSINESS_UNIT_IDS);
        assertThat(testClient.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testClient.getVmsClientName()).isEqualTo(DEFAULT_VMS_CLIENT_NAME);
        assertThat(testClient.getFederalID()).isEqualTo(DEFAULT_FEDERAL_ID);
        assertThat(testClient.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClient.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testClient.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClient.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testClient.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testClient.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testClient.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testClient.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testClient.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testClient.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testClient.getSendRequirement()).isEqualTo(DEFAULT_SEND_REQUIREMENT);
        assertThat(testClient.getSendHotList()).isEqualTo(DEFAULT_SEND_HOT_LIST);
        assertThat(testClient.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testClient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClient.getAllowAccessToAllUsers()).isEqualTo(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testClient.getOwnershipIds()).isEqualTo(DEFAULT_OWNERSHIP_IDS);
        assertThat(testClient.getClientLeadIds()).isEqualTo(DEFAULT_CLIENT_LEAD_IDS);
        assertThat(testClient.getRequiredDocuments()).isEqualTo(DEFAULT_REQUIRED_DOCUMENTS);
        assertThat(testClient.getPracticeAlt()).isEqualTo(DEFAULT_PRACTICE_ALT);
        assertThat(testClient.getAboutCompany()).isEqualTo(DEFAULT_ABOUT_COMPANY);
        assertThat(testClient.getStopNotifyingClientContactOnSubmitToClient())
            .isEqualTo(DEFAULT_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT);
        assertThat(testClient.getDefaultForJobPostings()).isEqualTo(DEFAULT_DEFAULT_FOR_JOB_POSTINGS);
        assertThat(testClient.getSubmissionGuidelines()).isEqualTo(DEFAULT_SUBMISSION_GUIDELINES);
        assertThat(testClient.getAssignedTo()).isEqualTo(DEFAULT_ASSIGNED_TO);
        assertThat(testClient.getAssignedToTeams()).isEqualTo(DEFAULT_ASSIGNED_TO_TEAMS);
        assertThat(testClient.getSalesManagers()).isEqualTo(DEFAULT_SALES_MANAGERS);
        assertThat(testClient.getAccountManagers()).isEqualTo(DEFAULT_ACCOUNT_MANAGERS);
        assertThat(testClient.getRecruitmentManagers()).isEqualTo(DEFAULT_RECRUITMENT_MANAGERS);
        assertThat(testClient.getDefaultTATConfigForJobPostingOrVMSJobs())
            .isEqualTo(DEFAULT_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS);
        assertThat(testClient.getDefaultTATConfigTimespan()).isEqualTo(DEFAULT_DEFAULT_TAT_CONFIG_TIMESPAN);
        assertThat(testClient.getNotifyOnTATToUserTypes()).isEqualTo(DEFAULT_NOTIFY_ON_TAT_TO_USER_TYPES);
        assertThat(testClient.getNotifyOnTATToUserIds()).isEqualTo(DEFAULT_NOTIFY_ON_TAT_TO_USER_IDS);
        assertThat(testClient.getTaxTermIds()).isEqualTo(DEFAULT_TAX_TERM_IDS);
        assertThat(testClient.getWorkAuthorizationIds()).isEqualTo(DEFAULT_WORK_AUTHORIZATION_IDS);
        assertThat(testClient.getPostJobOnCareersPage()).isEqualTo(DEFAULT_POST_JOB_ON_CAREERS_PAGE);
        assertThat(testClient.getDefaultPayRateTaxTerm()).isEqualTo(DEFAULT_DEFAULT_PAY_RATE_TAX_TERM);
        assertThat(testClient.getDefaultBillRateTaxTerm()).isEqualTo(DEFAULT_DEFAULT_BILL_RATE_TAX_TERM);
        assertThat(testClient.getReferencesMandatoryForSubmission()).isEqualTo(DEFAULT_REFERENCES_MANDATORY_FOR_SUBMISSION);
        assertThat(testClient.getMaxSubmissions()).isEqualTo(DEFAULT_MAX_SUBMISSIONS);
        assertThat(testClient.getNoOfPositions()).isEqualTo(DEFAULT_NO_OF_POSITIONS);
        assertThat(testClient.getMarkUp()).isEqualTo(DEFAULT_MARK_UP);
        assertThat(testClient.getMsp()).isEqualTo(DEFAULT_MSP);
        assertThat(testClient.getMailSubject()).isEqualTo(DEFAULT_MAIL_SUBJECT);
        assertThat(testClient.getMailBody()).isEqualTo(DEFAULT_MAIL_BODY);
        assertThat(testClient.getFieldsForExcel()).isEqualTo(DEFAULT_FIELDS_FOR_EXCEL);
    }

    @Test
    @Transactional
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId(1L);

        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientVisibility").value(hasItem(DEFAULT_CLIENT_VISIBILITY.toString())))
            .andExpect(jsonPath("$.[*].businessUnitIds").value(hasItem(DEFAULT_BUSINESS_UNIT_IDS)))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].vmsClientName").value(hasItem(DEFAULT_VMS_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].federalID").value(hasItem(DEFAULT_FEDERAL_ID)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].sendRequirement").value(hasItem(DEFAULT_SEND_REQUIREMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].sendHotList").value(hasItem(DEFAULT_SEND_HOT_LIST.booleanValue())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].allowAccessToAllUsers").value(hasItem(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS.booleanValue())))
            .andExpect(jsonPath("$.[*].ownershipIds").value(hasItem(DEFAULT_OWNERSHIP_IDS)))
            .andExpect(jsonPath("$.[*].clientLeadIds").value(hasItem(DEFAULT_CLIENT_LEAD_IDS)))
            .andExpect(jsonPath("$.[*].requiredDocuments").value(hasItem(DEFAULT_REQUIRED_DOCUMENTS)))
            .andExpect(jsonPath("$.[*].practiceAlt").value(hasItem(DEFAULT_PRACTICE_ALT)))
            .andExpect(jsonPath("$.[*].aboutCompany").value(hasItem(DEFAULT_ABOUT_COMPANY)))
            .andExpect(
                jsonPath("$.[*].stopNotifyingClientContactOnSubmitToClient")
                    .value(hasItem(DEFAULT_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].defaultForJobPostings").value(hasItem(DEFAULT_DEFAULT_FOR_JOB_POSTINGS.booleanValue())))
            .andExpect(jsonPath("$.[*].submissionGuidelines").value(hasItem(DEFAULT_SUBMISSION_GUIDELINES)))
            .andExpect(jsonPath("$.[*].assignedTo").value(hasItem(DEFAULT_ASSIGNED_TO)))
            .andExpect(jsonPath("$.[*].assignedToTeams").value(hasItem(DEFAULT_ASSIGNED_TO_TEAMS)))
            .andExpect(jsonPath("$.[*].salesManagers").value(hasItem(DEFAULT_SALES_MANAGERS)))
            .andExpect(jsonPath("$.[*].accountManagers").value(hasItem(DEFAULT_ACCOUNT_MANAGERS)))
            .andExpect(jsonPath("$.[*].recruitmentManagers").value(hasItem(DEFAULT_RECRUITMENT_MANAGERS)))
            .andExpect(
                jsonPath("$.[*].defaultTATConfigForJobPostingOrVMSJobs")
                    .value(hasItem(DEFAULT_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS))
            )
            .andExpect(jsonPath("$.[*].defaultTATConfigTimespan").value(hasItem(DEFAULT_DEFAULT_TAT_CONFIG_TIMESPAN)))
            .andExpect(jsonPath("$.[*].notifyOnTATToUserTypes").value(hasItem(DEFAULT_NOTIFY_ON_TAT_TO_USER_TYPES)))
            .andExpect(jsonPath("$.[*].notifyOnTATToUserIds").value(hasItem(DEFAULT_NOTIFY_ON_TAT_TO_USER_IDS)))
            .andExpect(jsonPath("$.[*].taxTermIds").value(hasItem(DEFAULT_TAX_TERM_IDS)))
            .andExpect(jsonPath("$.[*].workAuthorizationIds").value(hasItem(DEFAULT_WORK_AUTHORIZATION_IDS)))
            .andExpect(jsonPath("$.[*].postJobOnCareersPage").value(hasItem(DEFAULT_POST_JOB_ON_CAREERS_PAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultPayRateTaxTerm").value(hasItem(DEFAULT_DEFAULT_PAY_RATE_TAX_TERM)))
            .andExpect(jsonPath("$.[*].defaultBillRateTaxTerm").value(hasItem(DEFAULT_DEFAULT_BILL_RATE_TAX_TERM)))
            .andExpect(
                jsonPath("$.[*].referencesMandatoryForSubmission")
                    .value(hasItem(DEFAULT_REFERENCES_MANDATORY_FOR_SUBMISSION.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].maxSubmissions").value(hasItem(DEFAULT_MAX_SUBMISSIONS)))
            .andExpect(jsonPath("$.[*].noOfPositions").value(hasItem(DEFAULT_NO_OF_POSITIONS)))
            .andExpect(jsonPath("$.[*].markUp").value(hasItem(DEFAULT_MARK_UP)))
            .andExpect(jsonPath("$.[*].msp").value(hasItem(DEFAULT_MSP)))
            .andExpect(jsonPath("$.[*].mailSubject").value(hasItem(DEFAULT_MAIL_SUBJECT)))
            .andExpect(jsonPath("$.[*].mailBody").value(hasItem(DEFAULT_MAIL_BODY)))
            .andExpect(jsonPath("$.[*].fieldsForExcel").value(hasItem(DEFAULT_FIELDS_FOR_EXCEL)));
    }

    @Test
    @Transactional
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.clientVisibility").value(DEFAULT_CLIENT_VISIBILITY.toString()))
            .andExpect(jsonPath("$.businessUnitIds").value(DEFAULT_BUSINESS_UNIT_IDS))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.vmsClientName").value(DEFAULT_VMS_CLIENT_NAME))
            .andExpect(jsonPath("$.federalID").value(DEFAULT_FEDERAL_ID))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.sendRequirement").value(DEFAULT_SEND_REQUIREMENT.booleanValue()))
            .andExpect(jsonPath("$.sendHotList").value(DEFAULT_SEND_HOT_LIST.booleanValue()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.allowAccessToAllUsers").value(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS.booleanValue()))
            .andExpect(jsonPath("$.ownershipIds").value(DEFAULT_OWNERSHIP_IDS))
            .andExpect(jsonPath("$.clientLeadIds").value(DEFAULT_CLIENT_LEAD_IDS))
            .andExpect(jsonPath("$.requiredDocuments").value(DEFAULT_REQUIRED_DOCUMENTS))
            .andExpect(jsonPath("$.practiceAlt").value(DEFAULT_PRACTICE_ALT))
            .andExpect(jsonPath("$.aboutCompany").value(DEFAULT_ABOUT_COMPANY))
            .andExpect(
                jsonPath("$.stopNotifyingClientContactOnSubmitToClient")
                    .value(DEFAULT_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT.booleanValue())
            )
            .andExpect(jsonPath("$.defaultForJobPostings").value(DEFAULT_DEFAULT_FOR_JOB_POSTINGS.booleanValue()))
            .andExpect(jsonPath("$.submissionGuidelines").value(DEFAULT_SUBMISSION_GUIDELINES))
            .andExpect(jsonPath("$.assignedTo").value(DEFAULT_ASSIGNED_TO))
            .andExpect(jsonPath("$.assignedToTeams").value(DEFAULT_ASSIGNED_TO_TEAMS))
            .andExpect(jsonPath("$.salesManagers").value(DEFAULT_SALES_MANAGERS))
            .andExpect(jsonPath("$.accountManagers").value(DEFAULT_ACCOUNT_MANAGERS))
            .andExpect(jsonPath("$.recruitmentManagers").value(DEFAULT_RECRUITMENT_MANAGERS))
            .andExpect(jsonPath("$.defaultTATConfigForJobPostingOrVMSJobs").value(DEFAULT_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS))
            .andExpect(jsonPath("$.defaultTATConfigTimespan").value(DEFAULT_DEFAULT_TAT_CONFIG_TIMESPAN))
            .andExpect(jsonPath("$.notifyOnTATToUserTypes").value(DEFAULT_NOTIFY_ON_TAT_TO_USER_TYPES))
            .andExpect(jsonPath("$.notifyOnTATToUserIds").value(DEFAULT_NOTIFY_ON_TAT_TO_USER_IDS))
            .andExpect(jsonPath("$.taxTermIds").value(DEFAULT_TAX_TERM_IDS))
            .andExpect(jsonPath("$.workAuthorizationIds").value(DEFAULT_WORK_AUTHORIZATION_IDS))
            .andExpect(jsonPath("$.postJobOnCareersPage").value(DEFAULT_POST_JOB_ON_CAREERS_PAGE.booleanValue()))
            .andExpect(jsonPath("$.defaultPayRateTaxTerm").value(DEFAULT_DEFAULT_PAY_RATE_TAX_TERM))
            .andExpect(jsonPath("$.defaultBillRateTaxTerm").value(DEFAULT_DEFAULT_BILL_RATE_TAX_TERM))
            .andExpect(jsonPath("$.referencesMandatoryForSubmission").value(DEFAULT_REFERENCES_MANDATORY_FOR_SUBMISSION.booleanValue()))
            .andExpect(jsonPath("$.maxSubmissions").value(DEFAULT_MAX_SUBMISSIONS))
            .andExpect(jsonPath("$.noOfPositions").value(DEFAULT_NO_OF_POSITIONS))
            .andExpect(jsonPath("$.markUp").value(DEFAULT_MARK_UP))
            .andExpect(jsonPath("$.msp").value(DEFAULT_MSP))
            .andExpect(jsonPath("$.mailSubject").value(DEFAULT_MAIL_SUBJECT))
            .andExpect(jsonPath("$.mailBody").value(DEFAULT_MAIL_BODY))
            .andExpect(jsonPath("$.fieldsForExcel").value(DEFAULT_FIELDS_FOR_EXCEL));
    }

    @Test
    @Transactional
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .clientVisibility(UPDATED_CLIENT_VISIBILITY)
            .businessUnitIds(UPDATED_BUSINESS_UNIT_IDS)
            .clientName(UPDATED_CLIENT_NAME)
            .vmsClientName(UPDATED_VMS_CLIENT_NAME)
            .federalID(UPDATED_FEDERAL_ID)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .website(UPDATED_WEBSITE)
            .sendRequirement(UPDATED_SEND_REQUIREMENT)
            .sendHotList(UPDATED_SEND_HOT_LIST)
            .fax(UPDATED_FAX)
            .status(UPDATED_STATUS)
            .allowAccessToAllUsers(UPDATED_ALLOW_ACCESS_TO_ALL_USERS)
            .ownershipIds(UPDATED_OWNERSHIP_IDS)
            .clientLeadIds(UPDATED_CLIENT_LEAD_IDS)
            .requiredDocuments(UPDATED_REQUIRED_DOCUMENTS)
            .practiceAlt(UPDATED_PRACTICE_ALT)
            .aboutCompany(UPDATED_ABOUT_COMPANY)
            .stopNotifyingClientContactOnSubmitToClient(UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT)
            .defaultForJobPostings(UPDATED_DEFAULT_FOR_JOB_POSTINGS)
            .submissionGuidelines(UPDATED_SUBMISSION_GUIDELINES)
            .assignedTo(UPDATED_ASSIGNED_TO)
            .assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS)
            .salesManagers(UPDATED_SALES_MANAGERS)
            .accountManagers(UPDATED_ACCOUNT_MANAGERS)
            .recruitmentManagers(UPDATED_RECRUITMENT_MANAGERS)
            .defaultTATConfigForJobPostingOrVMSJobs(UPDATED_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS)
            .defaultTATConfigTimespan(UPDATED_DEFAULT_TAT_CONFIG_TIMESPAN)
            .notifyOnTATToUserTypes(UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES)
            .notifyOnTATToUserIds(UPDATED_NOTIFY_ON_TAT_TO_USER_IDS)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .postJobOnCareersPage(UPDATED_POST_JOB_ON_CAREERS_PAGE)
            .defaultPayRateTaxTerm(UPDATED_DEFAULT_PAY_RATE_TAX_TERM)
            .defaultBillRateTaxTerm(UPDATED_DEFAULT_BILL_RATE_TAX_TERM)
            .referencesMandatoryForSubmission(UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION)
            .maxSubmissions(UPDATED_MAX_SUBMISSIONS)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .markUp(UPDATED_MARK_UP)
            .msp(UPDATED_MSP)
            .mailSubject(UPDATED_MAIL_SUBJECT)
            .mailBody(UPDATED_MAIL_BODY)
            .fieldsForExcel(UPDATED_FIELDS_FOR_EXCEL);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientVisibility()).isEqualTo(UPDATED_CLIENT_VISIBILITY);
        assertThat(testClient.getBusinessUnitIds()).isEqualTo(UPDATED_BUSINESS_UNIT_IDS);
        assertThat(testClient.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClient.getVmsClientName()).isEqualTo(UPDATED_VMS_CLIENT_NAME);
        assertThat(testClient.getFederalID()).isEqualTo(UPDATED_FEDERAL_ID);
        assertThat(testClient.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testClient.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClient.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testClient.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testClient.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testClient.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testClient.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testClient.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testClient.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testClient.getSendRequirement()).isEqualTo(UPDATED_SEND_REQUIREMENT);
        assertThat(testClient.getSendHotList()).isEqualTo(UPDATED_SEND_HOT_LIST);
        assertThat(testClient.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getAllowAccessToAllUsers()).isEqualTo(UPDATED_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testClient.getOwnershipIds()).isEqualTo(UPDATED_OWNERSHIP_IDS);
        assertThat(testClient.getClientLeadIds()).isEqualTo(UPDATED_CLIENT_LEAD_IDS);
        assertThat(testClient.getRequiredDocuments()).isEqualTo(UPDATED_REQUIRED_DOCUMENTS);
        assertThat(testClient.getPracticeAlt()).isEqualTo(UPDATED_PRACTICE_ALT);
        assertThat(testClient.getAboutCompany()).isEqualTo(UPDATED_ABOUT_COMPANY);
        assertThat(testClient.getStopNotifyingClientContactOnSubmitToClient())
            .isEqualTo(UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT);
        assertThat(testClient.getDefaultForJobPostings()).isEqualTo(UPDATED_DEFAULT_FOR_JOB_POSTINGS);
        assertThat(testClient.getSubmissionGuidelines()).isEqualTo(UPDATED_SUBMISSION_GUIDELINES);
        assertThat(testClient.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testClient.getAssignedToTeams()).isEqualTo(UPDATED_ASSIGNED_TO_TEAMS);
        assertThat(testClient.getSalesManagers()).isEqualTo(UPDATED_SALES_MANAGERS);
        assertThat(testClient.getAccountManagers()).isEqualTo(UPDATED_ACCOUNT_MANAGERS);
        assertThat(testClient.getRecruitmentManagers()).isEqualTo(UPDATED_RECRUITMENT_MANAGERS);
        assertThat(testClient.getDefaultTATConfigForJobPostingOrVMSJobs())
            .isEqualTo(UPDATED_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS);
        assertThat(testClient.getDefaultTATConfigTimespan()).isEqualTo(UPDATED_DEFAULT_TAT_CONFIG_TIMESPAN);
        assertThat(testClient.getNotifyOnTATToUserTypes()).isEqualTo(UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES);
        assertThat(testClient.getNotifyOnTATToUserIds()).isEqualTo(UPDATED_NOTIFY_ON_TAT_TO_USER_IDS);
        assertThat(testClient.getTaxTermIds()).isEqualTo(UPDATED_TAX_TERM_IDS);
        assertThat(testClient.getWorkAuthorizationIds()).isEqualTo(UPDATED_WORK_AUTHORIZATION_IDS);
        assertThat(testClient.getPostJobOnCareersPage()).isEqualTo(UPDATED_POST_JOB_ON_CAREERS_PAGE);
        assertThat(testClient.getDefaultPayRateTaxTerm()).isEqualTo(UPDATED_DEFAULT_PAY_RATE_TAX_TERM);
        assertThat(testClient.getDefaultBillRateTaxTerm()).isEqualTo(UPDATED_DEFAULT_BILL_RATE_TAX_TERM);
        assertThat(testClient.getReferencesMandatoryForSubmission()).isEqualTo(UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION);
        assertThat(testClient.getMaxSubmissions()).isEqualTo(UPDATED_MAX_SUBMISSIONS);
        assertThat(testClient.getNoOfPositions()).isEqualTo(UPDATED_NO_OF_POSITIONS);
        assertThat(testClient.getMarkUp()).isEqualTo(UPDATED_MARK_UP);
        assertThat(testClient.getMsp()).isEqualTo(UPDATED_MSP);
        assertThat(testClient.getMailSubject()).isEqualTo(UPDATED_MAIL_SUBJECT);
        assertThat(testClient.getMailBody()).isEqualTo(UPDATED_MAIL_BODY);
        assertThat(testClient.getFieldsForExcel()).isEqualTo(UPDATED_FIELDS_FOR_EXCEL);
    }

    @Test
    @Transactional
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .clientVisibility(UPDATED_CLIENT_VISIBILITY)
            .businessUnitIds(UPDATED_BUSINESS_UNIT_IDS)
            .clientName(UPDATED_CLIENT_NAME)
            .email(UPDATED_EMAIL)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .website(UPDATED_WEBSITE)
            .fax(UPDATED_FAX)
            .status(UPDATED_STATUS)
            .clientLeadIds(UPDATED_CLIENT_LEAD_IDS)
            .requiredDocuments(UPDATED_REQUIRED_DOCUMENTS)
            .practiceAlt(UPDATED_PRACTICE_ALT)
            .stopNotifyingClientContactOnSubmitToClient(UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT)
            .defaultForJobPostings(UPDATED_DEFAULT_FOR_JOB_POSTINGS)
            .assignedTo(UPDATED_ASSIGNED_TO)
            .assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS)
            .recruitmentManagers(UPDATED_RECRUITMENT_MANAGERS)
            .notifyOnTATToUserTypes(UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES)
            .notifyOnTATToUserIds(UPDATED_NOTIFY_ON_TAT_TO_USER_IDS)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .defaultPayRateTaxTerm(UPDATED_DEFAULT_PAY_RATE_TAX_TERM)
            .defaultBillRateTaxTerm(UPDATED_DEFAULT_BILL_RATE_TAX_TERM)
            .referencesMandatoryForSubmission(UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION)
            .maxSubmissions(UPDATED_MAX_SUBMISSIONS)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .markUp(UPDATED_MARK_UP)
            .fieldsForExcel(UPDATED_FIELDS_FOR_EXCEL);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientVisibility()).isEqualTo(UPDATED_CLIENT_VISIBILITY);
        assertThat(testClient.getBusinessUnitIds()).isEqualTo(UPDATED_BUSINESS_UNIT_IDS);
        assertThat(testClient.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClient.getVmsClientName()).isEqualTo(DEFAULT_VMS_CLIENT_NAME);
        assertThat(testClient.getFederalID()).isEqualTo(DEFAULT_FEDERAL_ID);
        assertThat(testClient.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClient.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testClient.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClient.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testClient.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testClient.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testClient.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testClient.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testClient.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testClient.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testClient.getSendRequirement()).isEqualTo(DEFAULT_SEND_REQUIREMENT);
        assertThat(testClient.getSendHotList()).isEqualTo(DEFAULT_SEND_HOT_LIST);
        assertThat(testClient.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getAllowAccessToAllUsers()).isEqualTo(DEFAULT_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testClient.getOwnershipIds()).isEqualTo(DEFAULT_OWNERSHIP_IDS);
        assertThat(testClient.getClientLeadIds()).isEqualTo(UPDATED_CLIENT_LEAD_IDS);
        assertThat(testClient.getRequiredDocuments()).isEqualTo(UPDATED_REQUIRED_DOCUMENTS);
        assertThat(testClient.getPracticeAlt()).isEqualTo(UPDATED_PRACTICE_ALT);
        assertThat(testClient.getAboutCompany()).isEqualTo(DEFAULT_ABOUT_COMPANY);
        assertThat(testClient.getStopNotifyingClientContactOnSubmitToClient())
            .isEqualTo(UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT);
        assertThat(testClient.getDefaultForJobPostings()).isEqualTo(UPDATED_DEFAULT_FOR_JOB_POSTINGS);
        assertThat(testClient.getSubmissionGuidelines()).isEqualTo(DEFAULT_SUBMISSION_GUIDELINES);
        assertThat(testClient.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testClient.getAssignedToTeams()).isEqualTo(UPDATED_ASSIGNED_TO_TEAMS);
        assertThat(testClient.getSalesManagers()).isEqualTo(DEFAULT_SALES_MANAGERS);
        assertThat(testClient.getAccountManagers()).isEqualTo(DEFAULT_ACCOUNT_MANAGERS);
        assertThat(testClient.getRecruitmentManagers()).isEqualTo(UPDATED_RECRUITMENT_MANAGERS);
        assertThat(testClient.getDefaultTATConfigForJobPostingOrVMSJobs())
            .isEqualTo(DEFAULT_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS);
        assertThat(testClient.getDefaultTATConfigTimespan()).isEqualTo(DEFAULT_DEFAULT_TAT_CONFIG_TIMESPAN);
        assertThat(testClient.getNotifyOnTATToUserTypes()).isEqualTo(UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES);
        assertThat(testClient.getNotifyOnTATToUserIds()).isEqualTo(UPDATED_NOTIFY_ON_TAT_TO_USER_IDS);
        assertThat(testClient.getTaxTermIds()).isEqualTo(UPDATED_TAX_TERM_IDS);
        assertThat(testClient.getWorkAuthorizationIds()).isEqualTo(UPDATED_WORK_AUTHORIZATION_IDS);
        assertThat(testClient.getPostJobOnCareersPage()).isEqualTo(DEFAULT_POST_JOB_ON_CAREERS_PAGE);
        assertThat(testClient.getDefaultPayRateTaxTerm()).isEqualTo(UPDATED_DEFAULT_PAY_RATE_TAX_TERM);
        assertThat(testClient.getDefaultBillRateTaxTerm()).isEqualTo(UPDATED_DEFAULT_BILL_RATE_TAX_TERM);
        assertThat(testClient.getReferencesMandatoryForSubmission()).isEqualTo(UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION);
        assertThat(testClient.getMaxSubmissions()).isEqualTo(UPDATED_MAX_SUBMISSIONS);
        assertThat(testClient.getNoOfPositions()).isEqualTo(UPDATED_NO_OF_POSITIONS);
        assertThat(testClient.getMarkUp()).isEqualTo(UPDATED_MARK_UP);
        assertThat(testClient.getMsp()).isEqualTo(DEFAULT_MSP);
        assertThat(testClient.getMailSubject()).isEqualTo(DEFAULT_MAIL_SUBJECT);
        assertThat(testClient.getMailBody()).isEqualTo(DEFAULT_MAIL_BODY);
        assertThat(testClient.getFieldsForExcel()).isEqualTo(UPDATED_FIELDS_FOR_EXCEL);
    }

    @Test
    @Transactional
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .clientVisibility(UPDATED_CLIENT_VISIBILITY)
            .businessUnitIds(UPDATED_BUSINESS_UNIT_IDS)
            .clientName(UPDATED_CLIENT_NAME)
            .vmsClientName(UPDATED_VMS_CLIENT_NAME)
            .federalID(UPDATED_FEDERAL_ID)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .website(UPDATED_WEBSITE)
            .sendRequirement(UPDATED_SEND_REQUIREMENT)
            .sendHotList(UPDATED_SEND_HOT_LIST)
            .fax(UPDATED_FAX)
            .status(UPDATED_STATUS)
            .allowAccessToAllUsers(UPDATED_ALLOW_ACCESS_TO_ALL_USERS)
            .ownershipIds(UPDATED_OWNERSHIP_IDS)
            .clientLeadIds(UPDATED_CLIENT_LEAD_IDS)
            .requiredDocuments(UPDATED_REQUIRED_DOCUMENTS)
            .practiceAlt(UPDATED_PRACTICE_ALT)
            .aboutCompany(UPDATED_ABOUT_COMPANY)
            .stopNotifyingClientContactOnSubmitToClient(UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT)
            .defaultForJobPostings(UPDATED_DEFAULT_FOR_JOB_POSTINGS)
            .submissionGuidelines(UPDATED_SUBMISSION_GUIDELINES)
            .assignedTo(UPDATED_ASSIGNED_TO)
            .assignedToTeams(UPDATED_ASSIGNED_TO_TEAMS)
            .salesManagers(UPDATED_SALES_MANAGERS)
            .accountManagers(UPDATED_ACCOUNT_MANAGERS)
            .recruitmentManagers(UPDATED_RECRUITMENT_MANAGERS)
            .defaultTATConfigForJobPostingOrVMSJobs(UPDATED_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS)
            .defaultTATConfigTimespan(UPDATED_DEFAULT_TAT_CONFIG_TIMESPAN)
            .notifyOnTATToUserTypes(UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES)
            .notifyOnTATToUserIds(UPDATED_NOTIFY_ON_TAT_TO_USER_IDS)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .postJobOnCareersPage(UPDATED_POST_JOB_ON_CAREERS_PAGE)
            .defaultPayRateTaxTerm(UPDATED_DEFAULT_PAY_RATE_TAX_TERM)
            .defaultBillRateTaxTerm(UPDATED_DEFAULT_BILL_RATE_TAX_TERM)
            .referencesMandatoryForSubmission(UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION)
            .maxSubmissions(UPDATED_MAX_SUBMISSIONS)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .markUp(UPDATED_MARK_UP)
            .msp(UPDATED_MSP)
            .mailSubject(UPDATED_MAIL_SUBJECT)
            .mailBody(UPDATED_MAIL_BODY)
            .fieldsForExcel(UPDATED_FIELDS_FOR_EXCEL);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getClientVisibility()).isEqualTo(UPDATED_CLIENT_VISIBILITY);
        assertThat(testClient.getBusinessUnitIds()).isEqualTo(UPDATED_BUSINESS_UNIT_IDS);
        assertThat(testClient.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testClient.getVmsClientName()).isEqualTo(UPDATED_VMS_CLIENT_NAME);
        assertThat(testClient.getFederalID()).isEqualTo(UPDATED_FEDERAL_ID);
        assertThat(testClient.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testClient.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClient.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testClient.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testClient.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testClient.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testClient.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testClient.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testClient.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testClient.getSendRequirement()).isEqualTo(UPDATED_SEND_REQUIREMENT);
        assertThat(testClient.getSendHotList()).isEqualTo(UPDATED_SEND_HOT_LIST);
        assertThat(testClient.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testClient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClient.getAllowAccessToAllUsers()).isEqualTo(UPDATED_ALLOW_ACCESS_TO_ALL_USERS);
        assertThat(testClient.getOwnershipIds()).isEqualTo(UPDATED_OWNERSHIP_IDS);
        assertThat(testClient.getClientLeadIds()).isEqualTo(UPDATED_CLIENT_LEAD_IDS);
        assertThat(testClient.getRequiredDocuments()).isEqualTo(UPDATED_REQUIRED_DOCUMENTS);
        assertThat(testClient.getPracticeAlt()).isEqualTo(UPDATED_PRACTICE_ALT);
        assertThat(testClient.getAboutCompany()).isEqualTo(UPDATED_ABOUT_COMPANY);
        assertThat(testClient.getStopNotifyingClientContactOnSubmitToClient())
            .isEqualTo(UPDATED_STOP_NOTIFYING_CLIENT_CONTACT_ON_SUBMIT_TO_CLIENT);
        assertThat(testClient.getDefaultForJobPostings()).isEqualTo(UPDATED_DEFAULT_FOR_JOB_POSTINGS);
        assertThat(testClient.getSubmissionGuidelines()).isEqualTo(UPDATED_SUBMISSION_GUIDELINES);
        assertThat(testClient.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testClient.getAssignedToTeams()).isEqualTo(UPDATED_ASSIGNED_TO_TEAMS);
        assertThat(testClient.getSalesManagers()).isEqualTo(UPDATED_SALES_MANAGERS);
        assertThat(testClient.getAccountManagers()).isEqualTo(UPDATED_ACCOUNT_MANAGERS);
        assertThat(testClient.getRecruitmentManagers()).isEqualTo(UPDATED_RECRUITMENT_MANAGERS);
        assertThat(testClient.getDefaultTATConfigForJobPostingOrVMSJobs())
            .isEqualTo(UPDATED_DEFAULT_TAT_CONFIG_FOR_JOB_POSTING_OR_VMS_JOBS);
        assertThat(testClient.getDefaultTATConfigTimespan()).isEqualTo(UPDATED_DEFAULT_TAT_CONFIG_TIMESPAN);
        assertThat(testClient.getNotifyOnTATToUserTypes()).isEqualTo(UPDATED_NOTIFY_ON_TAT_TO_USER_TYPES);
        assertThat(testClient.getNotifyOnTATToUserIds()).isEqualTo(UPDATED_NOTIFY_ON_TAT_TO_USER_IDS);
        assertThat(testClient.getTaxTermIds()).isEqualTo(UPDATED_TAX_TERM_IDS);
        assertThat(testClient.getWorkAuthorizationIds()).isEqualTo(UPDATED_WORK_AUTHORIZATION_IDS);
        assertThat(testClient.getPostJobOnCareersPage()).isEqualTo(UPDATED_POST_JOB_ON_CAREERS_PAGE);
        assertThat(testClient.getDefaultPayRateTaxTerm()).isEqualTo(UPDATED_DEFAULT_PAY_RATE_TAX_TERM);
        assertThat(testClient.getDefaultBillRateTaxTerm()).isEqualTo(UPDATED_DEFAULT_BILL_RATE_TAX_TERM);
        assertThat(testClient.getReferencesMandatoryForSubmission()).isEqualTo(UPDATED_REFERENCES_MANDATORY_FOR_SUBMISSION);
        assertThat(testClient.getMaxSubmissions()).isEqualTo(UPDATED_MAX_SUBMISSIONS);
        assertThat(testClient.getNoOfPositions()).isEqualTo(UPDATED_NO_OF_POSITIONS);
        assertThat(testClient.getMarkUp()).isEqualTo(UPDATED_MARK_UP);
        assertThat(testClient.getMsp()).isEqualTo(UPDATED_MSP);
        assertThat(testClient.getMailSubject()).isEqualTo(UPDATED_MAIL_SUBJECT);
        assertThat(testClient.getMailBody()).isEqualTo(UPDATED_MAIL_BODY);
        assertThat(testClient.getFieldsForExcel()).isEqualTo(UPDATED_FIELDS_FOR_EXCEL);
    }

    @Test
    @Transactional
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, client.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
