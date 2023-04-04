package com.rp.web.rest;

import static com.rp.web.rest.TestUtil.sameInstant;
import static com.rp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.Job;
import com.rp.domain.enumeration.JobApplicationForm;
import com.rp.domain.enumeration.JobStatus;
import com.rp.domain.enumeration.RemoteJob;
import com.rp.domain.enumeration.SalaryTimeSpan;
import com.rp.domain.enumeration.SalaryTimeSpan;
import com.rp.repository.JobRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link JobResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_CODE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_JOB_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_JOB_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_EMPLOYMENT_TYPE_IDS = "AAAAAAAAAA";
    private static final String UPDATED_ORG_EMPLOYMENT_TYPE_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_REF = "AAAAAAAAAA";
    private static final String UPDATED_JOB_REF = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIC_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIC_JOB_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_JOB_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_AUTO_CLOSE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AUTO_CLOSE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_NO_OF_POSITIONS = 1;
    private static final Integer UPDATED_NO_OF_POSITIONS = 2;

    private static final String DEFAULT_DEPARTMENT_ALT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_ALT_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY_CAND_RATE = false;
    private static final Boolean UPDATED_DISPLAY_CAND_RATE = true;

    private static final String DEFAULT_CAND_RATE_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CAND_RATE_CURRENCY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CAND_MIN_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CAND_MIN_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CAND_MAX_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CAND_MAX_RATE = new BigDecimal(2);

    private static final SalaryTimeSpan DEFAULT_CAND_RATE_TIME_SPAN = SalaryTimeSpan.PerAnnum;
    private static final SalaryTimeSpan UPDATED_CAND_RATE_TIME_SPAN = SalaryTimeSpan.PerMonth;

    private static final String DEFAULT_CAND_RATE_TAX_TERM = "AAAAAAAAAA";
    private static final String UPDATED_CAND_RATE_TAX_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_CAND_SALARY_ALT_DISPLAY_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_CAND_SALARY_ALT_DISPLAY_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_BENEFIT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_BENEFIT_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_BILL_RATE_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_BILL_RATE_CURRENCY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CLIENT_BILL_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CLIENT_BILL_RATE = new BigDecimal(2);

    private static final SalaryTimeSpan DEFAULT_CLIENT_BILL_RATE_TIME_SPAN = SalaryTimeSpan.PerAnnum;
    private static final SalaryTimeSpan UPDATED_CLIENT_BILL_RATE_TIME_SPAN = SalaryTimeSpan.PerMonth;

    private static final String DEFAULT_CLIENT_BILL_RATE_TAX_TERM = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_BILL_RATE_TAX_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_WORK_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_IMMIGRATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_IMMIGRATION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_IMMIGRATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_IMMIGRATION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_ALT_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATION_IDS = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATIONS_ALT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATIONS_ALT_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_EDU_REQUIREMENTS = "AAAAAAAAAA";
    private static final String UPDATED_EDU_REQUIREMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_EXP_REQUIREMENTS = "AAAAAAAAAA";
    private static final String UPDATED_EXP_REQUIREMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_EXP_ALT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_EXP_ALT_TEXT = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_EXP_IN_YEARS = 1D;
    private static final Double UPDATED_MIN_EXP_IN_YEARS = 2D;

    private static final Double DEFAULT_MAX_EXP_IN_YEARS = 1D;
    private static final Double UPDATED_MAX_EXP_IN_YEARS = 2D;

    private static final String DEFAULT_LANGUAGE_IDS = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_VISA_REQUIREMENTS = "AAAAAAAAAA";
    private static final String UPDATED_VISA_REQUIREMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_AUTHORIZATION_IDS = "AAAAAAAAAA";
    private static final String UPDATED_WORK_AUTHORIZATION_IDS = "BBBBBBBBBB";

    private static final JobApplicationForm DEFAULT_APPLICATION_FORM_TYPE = JobApplicationForm.General_Application;
    private static final JobApplicationForm UPDATED_APPLICATION_FORM_TYPE = JobApplicationForm.Referral_Portal_Application;

    private static final Boolean DEFAULT_IS_PARTNER_JOB = false;
    private static final Boolean UPDATED_IS_PARTNER_JOB = true;

    private static final String DEFAULT_REDIRECTION_URL = "AAAAAAAAAA";
    private static final String UPDATED_REDIRECTION_URL = "BBBBBBBBBB";

    private static final JobStatus DEFAULT_JOB_STATUS = JobStatus.Active;
    private static final JobStatus UPDATED_JOB_STATUS = JobStatus.Closed;

    private static final String DEFAULT_END_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_END_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_ALT = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_ALT = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIRED_DOCUMENT_IDS = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRED_DOCUMENT_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_CLOSE_REASON_OTHER_ALT = "AAAAAAAAAA";
    private static final String UPDATED_JOB_CLOSE_REASON_OTHER_ALT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ADD_TO_CAREER_PAGE = false;
    private static final Boolean UPDATED_ADD_TO_CAREER_PAGE = true;

    private static final RemoteJob DEFAULT_REMOTE_JOB = RemoteJob.Yes;
    private static final RemoteJob UPDATED_REMOTE_JOB = RemoteJob.No;

    private static final String DEFAULT_HIRING_FOR = "AAAAAAAAAA";
    private static final String UPDATED_HIRING_FOR = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_DURATION_TIME_SPAN = "AAAAAAAAAA";
    private static final String UPDATED_WORK_DURATION_TIME_SPAN = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_TAX_TERMS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jobs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobMockMvc;

    private Job job;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Job createEntity(EntityManager em) {
        Job job = new Job()
            .title(DEFAULT_TITLE)
            .jobCode(DEFAULT_JOB_CODE)
            .clientJobCode(DEFAULT_CLIENT_JOB_CODE)
            .orgName(DEFAULT_ORG_NAME)
            .orgEmploymentTypeIds(DEFAULT_ORG_EMPLOYMENT_TYPE_IDS)
            .jobRef(DEFAULT_JOB_REF)
            .jobSource(DEFAULT_JOB_SOURCE)
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION)
            .publicJobTitle(DEFAULT_PUBLIC_JOB_TITLE)
            .publicJobDescription(DEFAULT_PUBLIC_JOB_DESCRIPTION)
            .autoCloseDate(DEFAULT_AUTO_CLOSE_DATE)
            .noOfPositions(DEFAULT_NO_OF_POSITIONS)
            .departmentAltText(DEFAULT_DEPARTMENT_ALT_TEXT)
            .displayCandRate(DEFAULT_DISPLAY_CAND_RATE)
            .candRateCurrency(DEFAULT_CAND_RATE_CURRENCY)
            .candMinRate(DEFAULT_CAND_MIN_RATE)
            .candMaxRate(DEFAULT_CAND_MAX_RATE)
            .candRateTimeSpan(DEFAULT_CAND_RATE_TIME_SPAN)
            .candRateTaxTerm(DEFAULT_CAND_RATE_TAX_TERM)
            .candSalaryAltDisplayText(DEFAULT_CAND_SALARY_ALT_DISPLAY_TEXT)
            .otherBenefitDetails(DEFAULT_OTHER_BENEFIT_DETAILS)
            .clientBillRateCurrency(DEFAULT_CLIENT_BILL_RATE_CURRENCY)
            .clientBillRate(DEFAULT_CLIENT_BILL_RATE)
            .clientBillRateTimeSpan(DEFAULT_CLIENT_BILL_RATE_TIME_SPAN)
            .clientBillRateTaxTerm(DEFAULT_CLIENT_BILL_RATE_TAX_TERM)
            .workDuration(DEFAULT_WORK_DURATION)
            .immigrationStatus(DEFAULT_IMMIGRATION_STATUS)
            .displayImmigrationStatus(DEFAULT_DISPLAY_IMMIGRATION_STATUS)
            .skills(DEFAULT_SKILLS)
            .altSkills(DEFAULT_ALT_SKILLS)
            .tags(DEFAULT_TAGS)
            .qualificationIds(DEFAULT_QUALIFICATION_IDS)
            .qualificationsAltText(DEFAULT_QUALIFICATIONS_ALT_TEXT)
            .eduRequirements(DEFAULT_EDU_REQUIREMENTS)
            .expRequirements(DEFAULT_EXP_REQUIREMENTS)
            .expAltText(DEFAULT_EXP_ALT_TEXT)
            .minExpInYears(DEFAULT_MIN_EXP_IN_YEARS)
            .maxExpInYears(DEFAULT_MAX_EXP_IN_YEARS)
            .languageIds(DEFAULT_LANGUAGE_IDS)
            .visaRequirements(DEFAULT_VISA_REQUIREMENTS)
            .workAuthorizationIds(DEFAULT_WORK_AUTHORIZATION_IDS)
            .applicationFormType(DEFAULT_APPLICATION_FORM_TYPE)
            .isPartnerJob(DEFAULT_IS_PARTNER_JOB)
            .redirectionUrl(DEFAULT_REDIRECTION_URL)
            .jobStatus(DEFAULT_JOB_STATUS)
            .endClient(DEFAULT_END_CLIENT)
            .domainAlt(DEFAULT_DOMAIN_ALT)
            .comments(DEFAULT_COMMENTS)
            .additionalNotificationsToUserIds(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS)
            .additionalNotificationsToTeamIds(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS)
            .requiredDocumentIds(DEFAULT_REQUIRED_DOCUMENT_IDS)
            .jobCloseReasonOtherAlt(DEFAULT_JOB_CLOSE_REASON_OTHER_ALT)
            .addToCareerPage(DEFAULT_ADD_TO_CAREER_PAGE)
            .remoteJob(DEFAULT_REMOTE_JOB)
            .hiringFor(DEFAULT_HIRING_FOR)
            .workDurationTimeSpan(DEFAULT_WORK_DURATION_TIME_SPAN)
            .taxTerms(DEFAULT_TAX_TERMS);
        return job;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Job createUpdatedEntity(EntityManager em) {
        Job job = new Job()
            .title(UPDATED_TITLE)
            .jobCode(UPDATED_JOB_CODE)
            .clientJobCode(UPDATED_CLIENT_JOB_CODE)
            .orgName(UPDATED_ORG_NAME)
            .orgEmploymentTypeIds(UPDATED_ORG_EMPLOYMENT_TYPE_IDS)
            .jobRef(UPDATED_JOB_REF)
            .jobSource(UPDATED_JOB_SOURCE)
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION)
            .publicJobTitle(UPDATED_PUBLIC_JOB_TITLE)
            .publicJobDescription(UPDATED_PUBLIC_JOB_DESCRIPTION)
            .autoCloseDate(UPDATED_AUTO_CLOSE_DATE)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .departmentAltText(UPDATED_DEPARTMENT_ALT_TEXT)
            .displayCandRate(UPDATED_DISPLAY_CAND_RATE)
            .candRateCurrency(UPDATED_CAND_RATE_CURRENCY)
            .candMinRate(UPDATED_CAND_MIN_RATE)
            .candMaxRate(UPDATED_CAND_MAX_RATE)
            .candRateTimeSpan(UPDATED_CAND_RATE_TIME_SPAN)
            .candRateTaxTerm(UPDATED_CAND_RATE_TAX_TERM)
            .candSalaryAltDisplayText(UPDATED_CAND_SALARY_ALT_DISPLAY_TEXT)
            .otherBenefitDetails(UPDATED_OTHER_BENEFIT_DETAILS)
            .clientBillRateCurrency(UPDATED_CLIENT_BILL_RATE_CURRENCY)
            .clientBillRate(UPDATED_CLIENT_BILL_RATE)
            .clientBillRateTimeSpan(UPDATED_CLIENT_BILL_RATE_TIME_SPAN)
            .clientBillRateTaxTerm(UPDATED_CLIENT_BILL_RATE_TAX_TERM)
            .workDuration(UPDATED_WORK_DURATION)
            .immigrationStatus(UPDATED_IMMIGRATION_STATUS)
            .displayImmigrationStatus(UPDATED_DISPLAY_IMMIGRATION_STATUS)
            .skills(UPDATED_SKILLS)
            .altSkills(UPDATED_ALT_SKILLS)
            .tags(UPDATED_TAGS)
            .qualificationIds(UPDATED_QUALIFICATION_IDS)
            .qualificationsAltText(UPDATED_QUALIFICATIONS_ALT_TEXT)
            .eduRequirements(UPDATED_EDU_REQUIREMENTS)
            .expRequirements(UPDATED_EXP_REQUIREMENTS)
            .expAltText(UPDATED_EXP_ALT_TEXT)
            .minExpInYears(UPDATED_MIN_EXP_IN_YEARS)
            .maxExpInYears(UPDATED_MAX_EXP_IN_YEARS)
            .languageIds(UPDATED_LANGUAGE_IDS)
            .visaRequirements(UPDATED_VISA_REQUIREMENTS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .applicationFormType(UPDATED_APPLICATION_FORM_TYPE)
            .isPartnerJob(UPDATED_IS_PARTNER_JOB)
            .redirectionUrl(UPDATED_REDIRECTION_URL)
            .jobStatus(UPDATED_JOB_STATUS)
            .endClient(UPDATED_END_CLIENT)
            .domainAlt(UPDATED_DOMAIN_ALT)
            .comments(UPDATED_COMMENTS)
            .additionalNotificationsToUserIds(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS)
            .additionalNotificationsToTeamIds(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS)
            .requiredDocumentIds(UPDATED_REQUIRED_DOCUMENT_IDS)
            .jobCloseReasonOtherAlt(UPDATED_JOB_CLOSE_REASON_OTHER_ALT)
            .addToCareerPage(UPDATED_ADD_TO_CAREER_PAGE)
            .remoteJob(UPDATED_REMOTE_JOB)
            .hiringFor(UPDATED_HIRING_FOR)
            .workDurationTimeSpan(UPDATED_WORK_DURATION_TIME_SPAN)
            .taxTerms(UPDATED_TAX_TERMS);
        return job;
    }

    @BeforeEach
    public void initTest() {
        job = createEntity(em);
    }

    @Test
    @Transactional
    void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();
        // Create the Job
        restJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJob.getJobCode()).isEqualTo(DEFAULT_JOB_CODE);
        assertThat(testJob.getClientJobCode()).isEqualTo(DEFAULT_CLIENT_JOB_CODE);
        assertThat(testJob.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testJob.getOrgEmploymentTypeIds()).isEqualTo(DEFAULT_ORG_EMPLOYMENT_TYPE_IDS);
        assertThat(testJob.getJobRef()).isEqualTo(DEFAULT_JOB_REF);
        assertThat(testJob.getJobSource()).isEqualTo(DEFAULT_JOB_SOURCE);
        assertThat(testJob.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testJob.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJob.getPublicJobTitle()).isEqualTo(DEFAULT_PUBLIC_JOB_TITLE);
        assertThat(testJob.getPublicJobDescription()).isEqualTo(DEFAULT_PUBLIC_JOB_DESCRIPTION);
        assertThat(testJob.getAutoCloseDate()).isEqualTo(DEFAULT_AUTO_CLOSE_DATE);
        assertThat(testJob.getNoOfPositions()).isEqualTo(DEFAULT_NO_OF_POSITIONS);
        assertThat(testJob.getDepartmentAltText()).isEqualTo(DEFAULT_DEPARTMENT_ALT_TEXT);
        assertThat(testJob.getDisplayCandRate()).isEqualTo(DEFAULT_DISPLAY_CAND_RATE);
        assertThat(testJob.getCandRateCurrency()).isEqualTo(DEFAULT_CAND_RATE_CURRENCY);
        assertThat(testJob.getCandMinRate()).isEqualByComparingTo(DEFAULT_CAND_MIN_RATE);
        assertThat(testJob.getCandMaxRate()).isEqualByComparingTo(DEFAULT_CAND_MAX_RATE);
        assertThat(testJob.getCandRateTimeSpan()).isEqualTo(DEFAULT_CAND_RATE_TIME_SPAN);
        assertThat(testJob.getCandRateTaxTerm()).isEqualTo(DEFAULT_CAND_RATE_TAX_TERM);
        assertThat(testJob.getCandSalaryAltDisplayText()).isEqualTo(DEFAULT_CAND_SALARY_ALT_DISPLAY_TEXT);
        assertThat(testJob.getOtherBenefitDetails()).isEqualTo(DEFAULT_OTHER_BENEFIT_DETAILS);
        assertThat(testJob.getClientBillRateCurrency()).isEqualTo(DEFAULT_CLIENT_BILL_RATE_CURRENCY);
        assertThat(testJob.getClientBillRate()).isEqualByComparingTo(DEFAULT_CLIENT_BILL_RATE);
        assertThat(testJob.getClientBillRateTimeSpan()).isEqualTo(DEFAULT_CLIENT_BILL_RATE_TIME_SPAN);
        assertThat(testJob.getClientBillRateTaxTerm()).isEqualTo(DEFAULT_CLIENT_BILL_RATE_TAX_TERM);
        assertThat(testJob.getWorkDuration()).isEqualTo(DEFAULT_WORK_DURATION);
        assertThat(testJob.getImmigrationStatus()).isEqualTo(DEFAULT_IMMIGRATION_STATUS);
        assertThat(testJob.getDisplayImmigrationStatus()).isEqualTo(DEFAULT_DISPLAY_IMMIGRATION_STATUS);
        assertThat(testJob.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testJob.getAltSkills()).isEqualTo(DEFAULT_ALT_SKILLS);
        assertThat(testJob.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testJob.getQualificationIds()).isEqualTo(DEFAULT_QUALIFICATION_IDS);
        assertThat(testJob.getQualificationsAltText()).isEqualTo(DEFAULT_QUALIFICATIONS_ALT_TEXT);
        assertThat(testJob.getEduRequirements()).isEqualTo(DEFAULT_EDU_REQUIREMENTS);
        assertThat(testJob.getExpRequirements()).isEqualTo(DEFAULT_EXP_REQUIREMENTS);
        assertThat(testJob.getExpAltText()).isEqualTo(DEFAULT_EXP_ALT_TEXT);
        assertThat(testJob.getMinExpInYears()).isEqualTo(DEFAULT_MIN_EXP_IN_YEARS);
        assertThat(testJob.getMaxExpInYears()).isEqualTo(DEFAULT_MAX_EXP_IN_YEARS);
        assertThat(testJob.getLanguageIds()).isEqualTo(DEFAULT_LANGUAGE_IDS);
        assertThat(testJob.getVisaRequirements()).isEqualTo(DEFAULT_VISA_REQUIREMENTS);
        assertThat(testJob.getWorkAuthorizationIds()).isEqualTo(DEFAULT_WORK_AUTHORIZATION_IDS);
        assertThat(testJob.getApplicationFormType()).isEqualTo(DEFAULT_APPLICATION_FORM_TYPE);
        assertThat(testJob.getIsPartnerJob()).isEqualTo(DEFAULT_IS_PARTNER_JOB);
        assertThat(testJob.getRedirectionUrl()).isEqualTo(DEFAULT_REDIRECTION_URL);
        assertThat(testJob.getJobStatus()).isEqualTo(DEFAULT_JOB_STATUS);
        assertThat(testJob.getEndClient()).isEqualTo(DEFAULT_END_CLIENT);
        assertThat(testJob.getDomainAlt()).isEqualTo(DEFAULT_DOMAIN_ALT);
        assertThat(testJob.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testJob.getAdditionalNotificationsToUserIds()).isEqualTo(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS);
        assertThat(testJob.getAdditionalNotificationsToTeamIds()).isEqualTo(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS);
        assertThat(testJob.getRequiredDocumentIds()).isEqualTo(DEFAULT_REQUIRED_DOCUMENT_IDS);
        assertThat(testJob.getJobCloseReasonOtherAlt()).isEqualTo(DEFAULT_JOB_CLOSE_REASON_OTHER_ALT);
        assertThat(testJob.getAddToCareerPage()).isEqualTo(DEFAULT_ADD_TO_CAREER_PAGE);
        assertThat(testJob.getRemoteJob()).isEqualTo(DEFAULT_REMOTE_JOB);
        assertThat(testJob.getHiringFor()).isEqualTo(DEFAULT_HIRING_FOR);
        assertThat(testJob.getWorkDurationTimeSpan()).isEqualTo(DEFAULT_WORK_DURATION_TIME_SPAN);
        assertThat(testJob.getTaxTerms()).isEqualTo(DEFAULT_TAX_TERMS);
    }

    @Test
    @Transactional
    void createJobWithExistingId() throws Exception {
        // Create the Job with an existing ID
        job.setId(1L);

        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList
        restJobMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].jobCode").value(hasItem(DEFAULT_JOB_CODE)))
            .andExpect(jsonPath("$.[*].clientJobCode").value(hasItem(DEFAULT_CLIENT_JOB_CODE)))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].orgEmploymentTypeIds").value(hasItem(DEFAULT_ORG_EMPLOYMENT_TYPE_IDS)))
            .andExpect(jsonPath("$.[*].jobRef").value(hasItem(DEFAULT_JOB_REF)))
            .andExpect(jsonPath("$.[*].jobSource").value(hasItem(DEFAULT_JOB_SOURCE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].publicJobTitle").value(hasItem(DEFAULT_PUBLIC_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].publicJobDescription").value(hasItem(DEFAULT_PUBLIC_JOB_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].autoCloseDate").value(hasItem(sameInstant(DEFAULT_AUTO_CLOSE_DATE))))
            .andExpect(jsonPath("$.[*].noOfPositions").value(hasItem(DEFAULT_NO_OF_POSITIONS)))
            .andExpect(jsonPath("$.[*].departmentAltText").value(hasItem(DEFAULT_DEPARTMENT_ALT_TEXT)))
            .andExpect(jsonPath("$.[*].displayCandRate").value(hasItem(DEFAULT_DISPLAY_CAND_RATE.booleanValue())))
            .andExpect(jsonPath("$.[*].candRateCurrency").value(hasItem(DEFAULT_CAND_RATE_CURRENCY)))
            .andExpect(jsonPath("$.[*].candMinRate").value(hasItem(sameNumber(DEFAULT_CAND_MIN_RATE))))
            .andExpect(jsonPath("$.[*].candMaxRate").value(hasItem(sameNumber(DEFAULT_CAND_MAX_RATE))))
            .andExpect(jsonPath("$.[*].candRateTimeSpan").value(hasItem(DEFAULT_CAND_RATE_TIME_SPAN.toString())))
            .andExpect(jsonPath("$.[*].candRateTaxTerm").value(hasItem(DEFAULT_CAND_RATE_TAX_TERM)))
            .andExpect(jsonPath("$.[*].candSalaryAltDisplayText").value(hasItem(DEFAULT_CAND_SALARY_ALT_DISPLAY_TEXT)))
            .andExpect(jsonPath("$.[*].otherBenefitDetails").value(hasItem(DEFAULT_OTHER_BENEFIT_DETAILS)))
            .andExpect(jsonPath("$.[*].clientBillRateCurrency").value(hasItem(DEFAULT_CLIENT_BILL_RATE_CURRENCY)))
            .andExpect(jsonPath("$.[*].clientBillRate").value(hasItem(sameNumber(DEFAULT_CLIENT_BILL_RATE))))
            .andExpect(jsonPath("$.[*].clientBillRateTimeSpan").value(hasItem(DEFAULT_CLIENT_BILL_RATE_TIME_SPAN.toString())))
            .andExpect(jsonPath("$.[*].clientBillRateTaxTerm").value(hasItem(DEFAULT_CLIENT_BILL_RATE_TAX_TERM)))
            .andExpect(jsonPath("$.[*].workDuration").value(hasItem(DEFAULT_WORK_DURATION)))
            .andExpect(jsonPath("$.[*].immigrationStatus").value(hasItem(DEFAULT_IMMIGRATION_STATUS)))
            .andExpect(jsonPath("$.[*].displayImmigrationStatus").value(hasItem(DEFAULT_DISPLAY_IMMIGRATION_STATUS)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)))
            .andExpect(jsonPath("$.[*].altSkills").value(hasItem(DEFAULT_ALT_SKILLS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].qualificationIds").value(hasItem(DEFAULT_QUALIFICATION_IDS)))
            .andExpect(jsonPath("$.[*].qualificationsAltText").value(hasItem(DEFAULT_QUALIFICATIONS_ALT_TEXT)))
            .andExpect(jsonPath("$.[*].eduRequirements").value(hasItem(DEFAULT_EDU_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].expRequirements").value(hasItem(DEFAULT_EXP_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].expAltText").value(hasItem(DEFAULT_EXP_ALT_TEXT)))
            .andExpect(jsonPath("$.[*].minExpInYears").value(hasItem(DEFAULT_MIN_EXP_IN_YEARS.doubleValue())))
            .andExpect(jsonPath("$.[*].maxExpInYears").value(hasItem(DEFAULT_MAX_EXP_IN_YEARS.doubleValue())))
            .andExpect(jsonPath("$.[*].languageIds").value(hasItem(DEFAULT_LANGUAGE_IDS)))
            .andExpect(jsonPath("$.[*].visaRequirements").value(hasItem(DEFAULT_VISA_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].workAuthorizationIds").value(hasItem(DEFAULT_WORK_AUTHORIZATION_IDS)))
            .andExpect(jsonPath("$.[*].applicationFormType").value(hasItem(DEFAULT_APPLICATION_FORM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isPartnerJob").value(hasItem(DEFAULT_IS_PARTNER_JOB.booleanValue())))
            .andExpect(jsonPath("$.[*].redirectionUrl").value(hasItem(DEFAULT_REDIRECTION_URL)))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS.toString())))
            .andExpect(jsonPath("$.[*].endClient").value(hasItem(DEFAULT_END_CLIENT)))
            .andExpect(jsonPath("$.[*].domainAlt").value(hasItem(DEFAULT_DOMAIN_ALT)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].additionalNotificationsToUserIds").value(hasItem(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS)))
            .andExpect(jsonPath("$.[*].additionalNotificationsToTeamIds").value(hasItem(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS)))
            .andExpect(jsonPath("$.[*].requiredDocumentIds").value(hasItem(DEFAULT_REQUIRED_DOCUMENT_IDS)))
            .andExpect(jsonPath("$.[*].jobCloseReasonOtherAlt").value(hasItem(DEFAULT_JOB_CLOSE_REASON_OTHER_ALT)))
            .andExpect(jsonPath("$.[*].addToCareerPage").value(hasItem(DEFAULT_ADD_TO_CAREER_PAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].remoteJob").value(hasItem(DEFAULT_REMOTE_JOB.toString())))
            .andExpect(jsonPath("$.[*].hiringFor").value(hasItem(DEFAULT_HIRING_FOR)))
            .andExpect(jsonPath("$.[*].workDurationTimeSpan").value(hasItem(DEFAULT_WORK_DURATION_TIME_SPAN)))
            .andExpect(jsonPath("$.[*].taxTerms").value(hasItem(DEFAULT_TAX_TERMS)));
    }

    @Test
    @Transactional
    void getJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get the job
        restJobMockMvc
            .perform(get(ENTITY_API_URL_ID, job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(job.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.jobCode").value(DEFAULT_JOB_CODE))
            .andExpect(jsonPath("$.clientJobCode").value(DEFAULT_CLIENT_JOB_CODE))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME))
            .andExpect(jsonPath("$.orgEmploymentTypeIds").value(DEFAULT_ORG_EMPLOYMENT_TYPE_IDS))
            .andExpect(jsonPath("$.jobRef").value(DEFAULT_JOB_REF))
            .andExpect(jsonPath("$.jobSource").value(DEFAULT_JOB_SOURCE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.publicJobTitle").value(DEFAULT_PUBLIC_JOB_TITLE))
            .andExpect(jsonPath("$.publicJobDescription").value(DEFAULT_PUBLIC_JOB_DESCRIPTION))
            .andExpect(jsonPath("$.autoCloseDate").value(sameInstant(DEFAULT_AUTO_CLOSE_DATE)))
            .andExpect(jsonPath("$.noOfPositions").value(DEFAULT_NO_OF_POSITIONS))
            .andExpect(jsonPath("$.departmentAltText").value(DEFAULT_DEPARTMENT_ALT_TEXT))
            .andExpect(jsonPath("$.displayCandRate").value(DEFAULT_DISPLAY_CAND_RATE.booleanValue()))
            .andExpect(jsonPath("$.candRateCurrency").value(DEFAULT_CAND_RATE_CURRENCY))
            .andExpect(jsonPath("$.candMinRate").value(sameNumber(DEFAULT_CAND_MIN_RATE)))
            .andExpect(jsonPath("$.candMaxRate").value(sameNumber(DEFAULT_CAND_MAX_RATE)))
            .andExpect(jsonPath("$.candRateTimeSpan").value(DEFAULT_CAND_RATE_TIME_SPAN.toString()))
            .andExpect(jsonPath("$.candRateTaxTerm").value(DEFAULT_CAND_RATE_TAX_TERM))
            .andExpect(jsonPath("$.candSalaryAltDisplayText").value(DEFAULT_CAND_SALARY_ALT_DISPLAY_TEXT))
            .andExpect(jsonPath("$.otherBenefitDetails").value(DEFAULT_OTHER_BENEFIT_DETAILS))
            .andExpect(jsonPath("$.clientBillRateCurrency").value(DEFAULT_CLIENT_BILL_RATE_CURRENCY))
            .andExpect(jsonPath("$.clientBillRate").value(sameNumber(DEFAULT_CLIENT_BILL_RATE)))
            .andExpect(jsonPath("$.clientBillRateTimeSpan").value(DEFAULT_CLIENT_BILL_RATE_TIME_SPAN.toString()))
            .andExpect(jsonPath("$.clientBillRateTaxTerm").value(DEFAULT_CLIENT_BILL_RATE_TAX_TERM))
            .andExpect(jsonPath("$.workDuration").value(DEFAULT_WORK_DURATION))
            .andExpect(jsonPath("$.immigrationStatus").value(DEFAULT_IMMIGRATION_STATUS))
            .andExpect(jsonPath("$.displayImmigrationStatus").value(DEFAULT_DISPLAY_IMMIGRATION_STATUS))
            .andExpect(jsonPath("$.skills").value(DEFAULT_SKILLS))
            .andExpect(jsonPath("$.altSkills").value(DEFAULT_ALT_SKILLS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.qualificationIds").value(DEFAULT_QUALIFICATION_IDS))
            .andExpect(jsonPath("$.qualificationsAltText").value(DEFAULT_QUALIFICATIONS_ALT_TEXT))
            .andExpect(jsonPath("$.eduRequirements").value(DEFAULT_EDU_REQUIREMENTS))
            .andExpect(jsonPath("$.expRequirements").value(DEFAULT_EXP_REQUIREMENTS))
            .andExpect(jsonPath("$.expAltText").value(DEFAULT_EXP_ALT_TEXT))
            .andExpect(jsonPath("$.minExpInYears").value(DEFAULT_MIN_EXP_IN_YEARS.doubleValue()))
            .andExpect(jsonPath("$.maxExpInYears").value(DEFAULT_MAX_EXP_IN_YEARS.doubleValue()))
            .andExpect(jsonPath("$.languageIds").value(DEFAULT_LANGUAGE_IDS))
            .andExpect(jsonPath("$.visaRequirements").value(DEFAULT_VISA_REQUIREMENTS))
            .andExpect(jsonPath("$.workAuthorizationIds").value(DEFAULT_WORK_AUTHORIZATION_IDS))
            .andExpect(jsonPath("$.applicationFormType").value(DEFAULT_APPLICATION_FORM_TYPE.toString()))
            .andExpect(jsonPath("$.isPartnerJob").value(DEFAULT_IS_PARTNER_JOB.booleanValue()))
            .andExpect(jsonPath("$.redirectionUrl").value(DEFAULT_REDIRECTION_URL))
            .andExpect(jsonPath("$.jobStatus").value(DEFAULT_JOB_STATUS.toString()))
            .andExpect(jsonPath("$.endClient").value(DEFAULT_END_CLIENT))
            .andExpect(jsonPath("$.domainAlt").value(DEFAULT_DOMAIN_ALT))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.additionalNotificationsToUserIds").value(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS))
            .andExpect(jsonPath("$.additionalNotificationsToTeamIds").value(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS))
            .andExpect(jsonPath("$.requiredDocumentIds").value(DEFAULT_REQUIRED_DOCUMENT_IDS))
            .andExpect(jsonPath("$.jobCloseReasonOtherAlt").value(DEFAULT_JOB_CLOSE_REASON_OTHER_ALT))
            .andExpect(jsonPath("$.addToCareerPage").value(DEFAULT_ADD_TO_CAREER_PAGE.booleanValue()))
            .andExpect(jsonPath("$.remoteJob").value(DEFAULT_REMOTE_JOB.toString()))
            .andExpect(jsonPath("$.hiringFor").value(DEFAULT_HIRING_FOR))
            .andExpect(jsonPath("$.workDurationTimeSpan").value(DEFAULT_WORK_DURATION_TIME_SPAN))
            .andExpect(jsonPath("$.taxTerms").value(DEFAULT_TAX_TERMS));
    }

    @Test
    @Transactional
    void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        Job updatedJob = jobRepository.findById(job.getId()).get();
        // Disconnect from session so that the updates on updatedJob are not directly saved in db
        em.detach(updatedJob);
        updatedJob
            .title(UPDATED_TITLE)
            .jobCode(UPDATED_JOB_CODE)
            .clientJobCode(UPDATED_CLIENT_JOB_CODE)
            .orgName(UPDATED_ORG_NAME)
            .orgEmploymentTypeIds(UPDATED_ORG_EMPLOYMENT_TYPE_IDS)
            .jobRef(UPDATED_JOB_REF)
            .jobSource(UPDATED_JOB_SOURCE)
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION)
            .publicJobTitle(UPDATED_PUBLIC_JOB_TITLE)
            .publicJobDescription(UPDATED_PUBLIC_JOB_DESCRIPTION)
            .autoCloseDate(UPDATED_AUTO_CLOSE_DATE)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .departmentAltText(UPDATED_DEPARTMENT_ALT_TEXT)
            .displayCandRate(UPDATED_DISPLAY_CAND_RATE)
            .candRateCurrency(UPDATED_CAND_RATE_CURRENCY)
            .candMinRate(UPDATED_CAND_MIN_RATE)
            .candMaxRate(UPDATED_CAND_MAX_RATE)
            .candRateTimeSpan(UPDATED_CAND_RATE_TIME_SPAN)
            .candRateTaxTerm(UPDATED_CAND_RATE_TAX_TERM)
            .candSalaryAltDisplayText(UPDATED_CAND_SALARY_ALT_DISPLAY_TEXT)
            .otherBenefitDetails(UPDATED_OTHER_BENEFIT_DETAILS)
            .clientBillRateCurrency(UPDATED_CLIENT_BILL_RATE_CURRENCY)
            .clientBillRate(UPDATED_CLIENT_BILL_RATE)
            .clientBillRateTimeSpan(UPDATED_CLIENT_BILL_RATE_TIME_SPAN)
            .clientBillRateTaxTerm(UPDATED_CLIENT_BILL_RATE_TAX_TERM)
            .workDuration(UPDATED_WORK_DURATION)
            .immigrationStatus(UPDATED_IMMIGRATION_STATUS)
            .displayImmigrationStatus(UPDATED_DISPLAY_IMMIGRATION_STATUS)
            .skills(UPDATED_SKILLS)
            .altSkills(UPDATED_ALT_SKILLS)
            .tags(UPDATED_TAGS)
            .qualificationIds(UPDATED_QUALIFICATION_IDS)
            .qualificationsAltText(UPDATED_QUALIFICATIONS_ALT_TEXT)
            .eduRequirements(UPDATED_EDU_REQUIREMENTS)
            .expRequirements(UPDATED_EXP_REQUIREMENTS)
            .expAltText(UPDATED_EXP_ALT_TEXT)
            .minExpInYears(UPDATED_MIN_EXP_IN_YEARS)
            .maxExpInYears(UPDATED_MAX_EXP_IN_YEARS)
            .languageIds(UPDATED_LANGUAGE_IDS)
            .visaRequirements(UPDATED_VISA_REQUIREMENTS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .applicationFormType(UPDATED_APPLICATION_FORM_TYPE)
            .isPartnerJob(UPDATED_IS_PARTNER_JOB)
            .redirectionUrl(UPDATED_REDIRECTION_URL)
            .jobStatus(UPDATED_JOB_STATUS)
            .endClient(UPDATED_END_CLIENT)
            .domainAlt(UPDATED_DOMAIN_ALT)
            .comments(UPDATED_COMMENTS)
            .additionalNotificationsToUserIds(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS)
            .additionalNotificationsToTeamIds(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS)
            .requiredDocumentIds(UPDATED_REQUIRED_DOCUMENT_IDS)
            .jobCloseReasonOtherAlt(UPDATED_JOB_CLOSE_REASON_OTHER_ALT)
            .addToCareerPage(UPDATED_ADD_TO_CAREER_PAGE)
            .remoteJob(UPDATED_REMOTE_JOB)
            .hiringFor(UPDATED_HIRING_FOR)
            .workDurationTimeSpan(UPDATED_WORK_DURATION_TIME_SPAN)
            .taxTerms(UPDATED_TAX_TERMS);

        restJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJob.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJob))
            )
            .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJob.getJobCode()).isEqualTo(UPDATED_JOB_CODE);
        assertThat(testJob.getClientJobCode()).isEqualTo(UPDATED_CLIENT_JOB_CODE);
        assertThat(testJob.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testJob.getOrgEmploymentTypeIds()).isEqualTo(UPDATED_ORG_EMPLOYMENT_TYPE_IDS);
        assertThat(testJob.getJobRef()).isEqualTo(UPDATED_JOB_REF);
        assertThat(testJob.getJobSource()).isEqualTo(UPDATED_JOB_SOURCE);
        assertThat(testJob.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testJob.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJob.getPublicJobTitle()).isEqualTo(UPDATED_PUBLIC_JOB_TITLE);
        assertThat(testJob.getPublicJobDescription()).isEqualTo(UPDATED_PUBLIC_JOB_DESCRIPTION);
        assertThat(testJob.getAutoCloseDate()).isEqualTo(UPDATED_AUTO_CLOSE_DATE);
        assertThat(testJob.getNoOfPositions()).isEqualTo(UPDATED_NO_OF_POSITIONS);
        assertThat(testJob.getDepartmentAltText()).isEqualTo(UPDATED_DEPARTMENT_ALT_TEXT);
        assertThat(testJob.getDisplayCandRate()).isEqualTo(UPDATED_DISPLAY_CAND_RATE);
        assertThat(testJob.getCandRateCurrency()).isEqualTo(UPDATED_CAND_RATE_CURRENCY);
        assertThat(testJob.getCandMinRate()).isEqualByComparingTo(UPDATED_CAND_MIN_RATE);
        assertThat(testJob.getCandMaxRate()).isEqualByComparingTo(UPDATED_CAND_MAX_RATE);
        assertThat(testJob.getCandRateTimeSpan()).isEqualTo(UPDATED_CAND_RATE_TIME_SPAN);
        assertThat(testJob.getCandRateTaxTerm()).isEqualTo(UPDATED_CAND_RATE_TAX_TERM);
        assertThat(testJob.getCandSalaryAltDisplayText()).isEqualTo(UPDATED_CAND_SALARY_ALT_DISPLAY_TEXT);
        assertThat(testJob.getOtherBenefitDetails()).isEqualTo(UPDATED_OTHER_BENEFIT_DETAILS);
        assertThat(testJob.getClientBillRateCurrency()).isEqualTo(UPDATED_CLIENT_BILL_RATE_CURRENCY);
        assertThat(testJob.getClientBillRate()).isEqualByComparingTo(UPDATED_CLIENT_BILL_RATE);
        assertThat(testJob.getClientBillRateTimeSpan()).isEqualTo(UPDATED_CLIENT_BILL_RATE_TIME_SPAN);
        assertThat(testJob.getClientBillRateTaxTerm()).isEqualTo(UPDATED_CLIENT_BILL_RATE_TAX_TERM);
        assertThat(testJob.getWorkDuration()).isEqualTo(UPDATED_WORK_DURATION);
        assertThat(testJob.getImmigrationStatus()).isEqualTo(UPDATED_IMMIGRATION_STATUS);
        assertThat(testJob.getDisplayImmigrationStatus()).isEqualTo(UPDATED_DISPLAY_IMMIGRATION_STATUS);
        assertThat(testJob.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testJob.getAltSkills()).isEqualTo(UPDATED_ALT_SKILLS);
        assertThat(testJob.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testJob.getQualificationIds()).isEqualTo(UPDATED_QUALIFICATION_IDS);
        assertThat(testJob.getQualificationsAltText()).isEqualTo(UPDATED_QUALIFICATIONS_ALT_TEXT);
        assertThat(testJob.getEduRequirements()).isEqualTo(UPDATED_EDU_REQUIREMENTS);
        assertThat(testJob.getExpRequirements()).isEqualTo(UPDATED_EXP_REQUIREMENTS);
        assertThat(testJob.getExpAltText()).isEqualTo(UPDATED_EXP_ALT_TEXT);
        assertThat(testJob.getMinExpInYears()).isEqualTo(UPDATED_MIN_EXP_IN_YEARS);
        assertThat(testJob.getMaxExpInYears()).isEqualTo(UPDATED_MAX_EXP_IN_YEARS);
        assertThat(testJob.getLanguageIds()).isEqualTo(UPDATED_LANGUAGE_IDS);
        assertThat(testJob.getVisaRequirements()).isEqualTo(UPDATED_VISA_REQUIREMENTS);
        assertThat(testJob.getWorkAuthorizationIds()).isEqualTo(UPDATED_WORK_AUTHORIZATION_IDS);
        assertThat(testJob.getApplicationFormType()).isEqualTo(UPDATED_APPLICATION_FORM_TYPE);
        assertThat(testJob.getIsPartnerJob()).isEqualTo(UPDATED_IS_PARTNER_JOB);
        assertThat(testJob.getRedirectionUrl()).isEqualTo(UPDATED_REDIRECTION_URL);
        assertThat(testJob.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);
        assertThat(testJob.getEndClient()).isEqualTo(UPDATED_END_CLIENT);
        assertThat(testJob.getDomainAlt()).isEqualTo(UPDATED_DOMAIN_ALT);
        assertThat(testJob.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testJob.getAdditionalNotificationsToUserIds()).isEqualTo(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS);
        assertThat(testJob.getAdditionalNotificationsToTeamIds()).isEqualTo(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS);
        assertThat(testJob.getRequiredDocumentIds()).isEqualTo(UPDATED_REQUIRED_DOCUMENT_IDS);
        assertThat(testJob.getJobCloseReasonOtherAlt()).isEqualTo(UPDATED_JOB_CLOSE_REASON_OTHER_ALT);
        assertThat(testJob.getAddToCareerPage()).isEqualTo(UPDATED_ADD_TO_CAREER_PAGE);
        assertThat(testJob.getRemoteJob()).isEqualTo(UPDATED_REMOTE_JOB);
        assertThat(testJob.getHiringFor()).isEqualTo(UPDATED_HIRING_FOR);
        assertThat(testJob.getWorkDurationTimeSpan()).isEqualTo(UPDATED_WORK_DURATION_TIME_SPAN);
        assertThat(testJob.getTaxTerms()).isEqualTo(UPDATED_TAX_TERMS);
    }

    @Test
    @Transactional
    void putNonExistingJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();
        job.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, job.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(job))
            )
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();
        job.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(job))
            )
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();
        job.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobWithPatch() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job using partial update
        Job partialUpdatedJob = new Job();
        partialUpdatedJob.setId(job.getId());

        partialUpdatedJob
            .jobCode(UPDATED_JOB_CODE)
            .orgEmploymentTypeIds(UPDATED_ORG_EMPLOYMENT_TYPE_IDS)
            .jobRef(UPDATED_JOB_REF)
            .jobSource(UPDATED_JOB_SOURCE)
            .publicJobDescription(UPDATED_PUBLIC_JOB_DESCRIPTION)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .departmentAltText(UPDATED_DEPARTMENT_ALT_TEXT)
            .displayCandRate(UPDATED_DISPLAY_CAND_RATE)
            .candRateTimeSpan(UPDATED_CAND_RATE_TIME_SPAN)
            .otherBenefitDetails(UPDATED_OTHER_BENEFIT_DETAILS)
            .clientBillRateCurrency(UPDATED_CLIENT_BILL_RATE_CURRENCY)
            .clientBillRateTaxTerm(UPDATED_CLIENT_BILL_RATE_TAX_TERM)
            .skills(UPDATED_SKILLS)
            .qualificationIds(UPDATED_QUALIFICATION_IDS)
            .eduRequirements(UPDATED_EDU_REQUIREMENTS)
            .expRequirements(UPDATED_EXP_REQUIREMENTS)
            .minExpInYears(UPDATED_MIN_EXP_IN_YEARS)
            .maxExpInYears(UPDATED_MAX_EXP_IN_YEARS)
            .languageIds(UPDATED_LANGUAGE_IDS)
            .visaRequirements(UPDATED_VISA_REQUIREMENTS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .applicationFormType(UPDATED_APPLICATION_FORM_TYPE)
            .isPartnerJob(UPDATED_IS_PARTNER_JOB)
            .jobStatus(UPDATED_JOB_STATUS)
            .domainAlt(UPDATED_DOMAIN_ALT)
            .additionalNotificationsToUserIds(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS)
            .jobCloseReasonOtherAlt(UPDATED_JOB_CLOSE_REASON_OTHER_ALT)
            .remoteJob(UPDATED_REMOTE_JOB)
            .hiringFor(UPDATED_HIRING_FOR)
            .workDurationTimeSpan(UPDATED_WORK_DURATION_TIME_SPAN);

        restJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJob))
            )
            .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJob.getJobCode()).isEqualTo(UPDATED_JOB_CODE);
        assertThat(testJob.getClientJobCode()).isEqualTo(DEFAULT_CLIENT_JOB_CODE);
        assertThat(testJob.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testJob.getOrgEmploymentTypeIds()).isEqualTo(UPDATED_ORG_EMPLOYMENT_TYPE_IDS);
        assertThat(testJob.getJobRef()).isEqualTo(UPDATED_JOB_REF);
        assertThat(testJob.getJobSource()).isEqualTo(UPDATED_JOB_SOURCE);
        assertThat(testJob.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testJob.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJob.getPublicJobTitle()).isEqualTo(DEFAULT_PUBLIC_JOB_TITLE);
        assertThat(testJob.getPublicJobDescription()).isEqualTo(UPDATED_PUBLIC_JOB_DESCRIPTION);
        assertThat(testJob.getAutoCloseDate()).isEqualTo(DEFAULT_AUTO_CLOSE_DATE);
        assertThat(testJob.getNoOfPositions()).isEqualTo(UPDATED_NO_OF_POSITIONS);
        assertThat(testJob.getDepartmentAltText()).isEqualTo(UPDATED_DEPARTMENT_ALT_TEXT);
        assertThat(testJob.getDisplayCandRate()).isEqualTo(UPDATED_DISPLAY_CAND_RATE);
        assertThat(testJob.getCandRateCurrency()).isEqualTo(DEFAULT_CAND_RATE_CURRENCY);
        assertThat(testJob.getCandMinRate()).isEqualByComparingTo(DEFAULT_CAND_MIN_RATE);
        assertThat(testJob.getCandMaxRate()).isEqualByComparingTo(DEFAULT_CAND_MAX_RATE);
        assertThat(testJob.getCandRateTimeSpan()).isEqualTo(UPDATED_CAND_RATE_TIME_SPAN);
        assertThat(testJob.getCandRateTaxTerm()).isEqualTo(DEFAULT_CAND_RATE_TAX_TERM);
        assertThat(testJob.getCandSalaryAltDisplayText()).isEqualTo(DEFAULT_CAND_SALARY_ALT_DISPLAY_TEXT);
        assertThat(testJob.getOtherBenefitDetails()).isEqualTo(UPDATED_OTHER_BENEFIT_DETAILS);
        assertThat(testJob.getClientBillRateCurrency()).isEqualTo(UPDATED_CLIENT_BILL_RATE_CURRENCY);
        assertThat(testJob.getClientBillRate()).isEqualByComparingTo(DEFAULT_CLIENT_BILL_RATE);
        assertThat(testJob.getClientBillRateTimeSpan()).isEqualTo(DEFAULT_CLIENT_BILL_RATE_TIME_SPAN);
        assertThat(testJob.getClientBillRateTaxTerm()).isEqualTo(UPDATED_CLIENT_BILL_RATE_TAX_TERM);
        assertThat(testJob.getWorkDuration()).isEqualTo(DEFAULT_WORK_DURATION);
        assertThat(testJob.getImmigrationStatus()).isEqualTo(DEFAULT_IMMIGRATION_STATUS);
        assertThat(testJob.getDisplayImmigrationStatus()).isEqualTo(DEFAULT_DISPLAY_IMMIGRATION_STATUS);
        assertThat(testJob.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testJob.getAltSkills()).isEqualTo(DEFAULT_ALT_SKILLS);
        assertThat(testJob.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testJob.getQualificationIds()).isEqualTo(UPDATED_QUALIFICATION_IDS);
        assertThat(testJob.getQualificationsAltText()).isEqualTo(DEFAULT_QUALIFICATIONS_ALT_TEXT);
        assertThat(testJob.getEduRequirements()).isEqualTo(UPDATED_EDU_REQUIREMENTS);
        assertThat(testJob.getExpRequirements()).isEqualTo(UPDATED_EXP_REQUIREMENTS);
        assertThat(testJob.getExpAltText()).isEqualTo(DEFAULT_EXP_ALT_TEXT);
        assertThat(testJob.getMinExpInYears()).isEqualTo(UPDATED_MIN_EXP_IN_YEARS);
        assertThat(testJob.getMaxExpInYears()).isEqualTo(UPDATED_MAX_EXP_IN_YEARS);
        assertThat(testJob.getLanguageIds()).isEqualTo(UPDATED_LANGUAGE_IDS);
        assertThat(testJob.getVisaRequirements()).isEqualTo(UPDATED_VISA_REQUIREMENTS);
        assertThat(testJob.getWorkAuthorizationIds()).isEqualTo(UPDATED_WORK_AUTHORIZATION_IDS);
        assertThat(testJob.getApplicationFormType()).isEqualTo(UPDATED_APPLICATION_FORM_TYPE);
        assertThat(testJob.getIsPartnerJob()).isEqualTo(UPDATED_IS_PARTNER_JOB);
        assertThat(testJob.getRedirectionUrl()).isEqualTo(DEFAULT_REDIRECTION_URL);
        assertThat(testJob.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);
        assertThat(testJob.getEndClient()).isEqualTo(DEFAULT_END_CLIENT);
        assertThat(testJob.getDomainAlt()).isEqualTo(UPDATED_DOMAIN_ALT);
        assertThat(testJob.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testJob.getAdditionalNotificationsToUserIds()).isEqualTo(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS);
        assertThat(testJob.getAdditionalNotificationsToTeamIds()).isEqualTo(DEFAULT_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS);
        assertThat(testJob.getRequiredDocumentIds()).isEqualTo(DEFAULT_REQUIRED_DOCUMENT_IDS);
        assertThat(testJob.getJobCloseReasonOtherAlt()).isEqualTo(UPDATED_JOB_CLOSE_REASON_OTHER_ALT);
        assertThat(testJob.getAddToCareerPage()).isEqualTo(DEFAULT_ADD_TO_CAREER_PAGE);
        assertThat(testJob.getRemoteJob()).isEqualTo(UPDATED_REMOTE_JOB);
        assertThat(testJob.getHiringFor()).isEqualTo(UPDATED_HIRING_FOR);
        assertThat(testJob.getWorkDurationTimeSpan()).isEqualTo(UPDATED_WORK_DURATION_TIME_SPAN);
        assertThat(testJob.getTaxTerms()).isEqualTo(DEFAULT_TAX_TERMS);
    }

    @Test
    @Transactional
    void fullUpdateJobWithPatch() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job using partial update
        Job partialUpdatedJob = new Job();
        partialUpdatedJob.setId(job.getId());

        partialUpdatedJob
            .title(UPDATED_TITLE)
            .jobCode(UPDATED_JOB_CODE)
            .clientJobCode(UPDATED_CLIENT_JOB_CODE)
            .orgName(UPDATED_ORG_NAME)
            .orgEmploymentTypeIds(UPDATED_ORG_EMPLOYMENT_TYPE_IDS)
            .jobRef(UPDATED_JOB_REF)
            .jobSource(UPDATED_JOB_SOURCE)
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION)
            .publicJobTitle(UPDATED_PUBLIC_JOB_TITLE)
            .publicJobDescription(UPDATED_PUBLIC_JOB_DESCRIPTION)
            .autoCloseDate(UPDATED_AUTO_CLOSE_DATE)
            .noOfPositions(UPDATED_NO_OF_POSITIONS)
            .departmentAltText(UPDATED_DEPARTMENT_ALT_TEXT)
            .displayCandRate(UPDATED_DISPLAY_CAND_RATE)
            .candRateCurrency(UPDATED_CAND_RATE_CURRENCY)
            .candMinRate(UPDATED_CAND_MIN_RATE)
            .candMaxRate(UPDATED_CAND_MAX_RATE)
            .candRateTimeSpan(UPDATED_CAND_RATE_TIME_SPAN)
            .candRateTaxTerm(UPDATED_CAND_RATE_TAX_TERM)
            .candSalaryAltDisplayText(UPDATED_CAND_SALARY_ALT_DISPLAY_TEXT)
            .otherBenefitDetails(UPDATED_OTHER_BENEFIT_DETAILS)
            .clientBillRateCurrency(UPDATED_CLIENT_BILL_RATE_CURRENCY)
            .clientBillRate(UPDATED_CLIENT_BILL_RATE)
            .clientBillRateTimeSpan(UPDATED_CLIENT_BILL_RATE_TIME_SPAN)
            .clientBillRateTaxTerm(UPDATED_CLIENT_BILL_RATE_TAX_TERM)
            .workDuration(UPDATED_WORK_DURATION)
            .immigrationStatus(UPDATED_IMMIGRATION_STATUS)
            .displayImmigrationStatus(UPDATED_DISPLAY_IMMIGRATION_STATUS)
            .skills(UPDATED_SKILLS)
            .altSkills(UPDATED_ALT_SKILLS)
            .tags(UPDATED_TAGS)
            .qualificationIds(UPDATED_QUALIFICATION_IDS)
            .qualificationsAltText(UPDATED_QUALIFICATIONS_ALT_TEXT)
            .eduRequirements(UPDATED_EDU_REQUIREMENTS)
            .expRequirements(UPDATED_EXP_REQUIREMENTS)
            .expAltText(UPDATED_EXP_ALT_TEXT)
            .minExpInYears(UPDATED_MIN_EXP_IN_YEARS)
            .maxExpInYears(UPDATED_MAX_EXP_IN_YEARS)
            .languageIds(UPDATED_LANGUAGE_IDS)
            .visaRequirements(UPDATED_VISA_REQUIREMENTS)
            .workAuthorizationIds(UPDATED_WORK_AUTHORIZATION_IDS)
            .applicationFormType(UPDATED_APPLICATION_FORM_TYPE)
            .isPartnerJob(UPDATED_IS_PARTNER_JOB)
            .redirectionUrl(UPDATED_REDIRECTION_URL)
            .jobStatus(UPDATED_JOB_STATUS)
            .endClient(UPDATED_END_CLIENT)
            .domainAlt(UPDATED_DOMAIN_ALT)
            .comments(UPDATED_COMMENTS)
            .additionalNotificationsToUserIds(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS)
            .additionalNotificationsToTeamIds(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS)
            .requiredDocumentIds(UPDATED_REQUIRED_DOCUMENT_IDS)
            .jobCloseReasonOtherAlt(UPDATED_JOB_CLOSE_REASON_OTHER_ALT)
            .addToCareerPage(UPDATED_ADD_TO_CAREER_PAGE)
            .remoteJob(UPDATED_REMOTE_JOB)
            .hiringFor(UPDATED_HIRING_FOR)
            .workDurationTimeSpan(UPDATED_WORK_DURATION_TIME_SPAN)
            .taxTerms(UPDATED_TAX_TERMS);

        restJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJob))
            )
            .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJob.getJobCode()).isEqualTo(UPDATED_JOB_CODE);
        assertThat(testJob.getClientJobCode()).isEqualTo(UPDATED_CLIENT_JOB_CODE);
        assertThat(testJob.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testJob.getOrgEmploymentTypeIds()).isEqualTo(UPDATED_ORG_EMPLOYMENT_TYPE_IDS);
        assertThat(testJob.getJobRef()).isEqualTo(UPDATED_JOB_REF);
        assertThat(testJob.getJobSource()).isEqualTo(UPDATED_JOB_SOURCE);
        assertThat(testJob.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testJob.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJob.getPublicJobTitle()).isEqualTo(UPDATED_PUBLIC_JOB_TITLE);
        assertThat(testJob.getPublicJobDescription()).isEqualTo(UPDATED_PUBLIC_JOB_DESCRIPTION);
        assertThat(testJob.getAutoCloseDate()).isEqualTo(UPDATED_AUTO_CLOSE_DATE);
        assertThat(testJob.getNoOfPositions()).isEqualTo(UPDATED_NO_OF_POSITIONS);
        assertThat(testJob.getDepartmentAltText()).isEqualTo(UPDATED_DEPARTMENT_ALT_TEXT);
        assertThat(testJob.getDisplayCandRate()).isEqualTo(UPDATED_DISPLAY_CAND_RATE);
        assertThat(testJob.getCandRateCurrency()).isEqualTo(UPDATED_CAND_RATE_CURRENCY);
        assertThat(testJob.getCandMinRate()).isEqualByComparingTo(UPDATED_CAND_MIN_RATE);
        assertThat(testJob.getCandMaxRate()).isEqualByComparingTo(UPDATED_CAND_MAX_RATE);
        assertThat(testJob.getCandRateTimeSpan()).isEqualTo(UPDATED_CAND_RATE_TIME_SPAN);
        assertThat(testJob.getCandRateTaxTerm()).isEqualTo(UPDATED_CAND_RATE_TAX_TERM);
        assertThat(testJob.getCandSalaryAltDisplayText()).isEqualTo(UPDATED_CAND_SALARY_ALT_DISPLAY_TEXT);
        assertThat(testJob.getOtherBenefitDetails()).isEqualTo(UPDATED_OTHER_BENEFIT_DETAILS);
        assertThat(testJob.getClientBillRateCurrency()).isEqualTo(UPDATED_CLIENT_BILL_RATE_CURRENCY);
        assertThat(testJob.getClientBillRate()).isEqualByComparingTo(UPDATED_CLIENT_BILL_RATE);
        assertThat(testJob.getClientBillRateTimeSpan()).isEqualTo(UPDATED_CLIENT_BILL_RATE_TIME_SPAN);
        assertThat(testJob.getClientBillRateTaxTerm()).isEqualTo(UPDATED_CLIENT_BILL_RATE_TAX_TERM);
        assertThat(testJob.getWorkDuration()).isEqualTo(UPDATED_WORK_DURATION);
        assertThat(testJob.getImmigrationStatus()).isEqualTo(UPDATED_IMMIGRATION_STATUS);
        assertThat(testJob.getDisplayImmigrationStatus()).isEqualTo(UPDATED_DISPLAY_IMMIGRATION_STATUS);
        assertThat(testJob.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testJob.getAltSkills()).isEqualTo(UPDATED_ALT_SKILLS);
        assertThat(testJob.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testJob.getQualificationIds()).isEqualTo(UPDATED_QUALIFICATION_IDS);
        assertThat(testJob.getQualificationsAltText()).isEqualTo(UPDATED_QUALIFICATIONS_ALT_TEXT);
        assertThat(testJob.getEduRequirements()).isEqualTo(UPDATED_EDU_REQUIREMENTS);
        assertThat(testJob.getExpRequirements()).isEqualTo(UPDATED_EXP_REQUIREMENTS);
        assertThat(testJob.getExpAltText()).isEqualTo(UPDATED_EXP_ALT_TEXT);
        assertThat(testJob.getMinExpInYears()).isEqualTo(UPDATED_MIN_EXP_IN_YEARS);
        assertThat(testJob.getMaxExpInYears()).isEqualTo(UPDATED_MAX_EXP_IN_YEARS);
        assertThat(testJob.getLanguageIds()).isEqualTo(UPDATED_LANGUAGE_IDS);
        assertThat(testJob.getVisaRequirements()).isEqualTo(UPDATED_VISA_REQUIREMENTS);
        assertThat(testJob.getWorkAuthorizationIds()).isEqualTo(UPDATED_WORK_AUTHORIZATION_IDS);
        assertThat(testJob.getApplicationFormType()).isEqualTo(UPDATED_APPLICATION_FORM_TYPE);
        assertThat(testJob.getIsPartnerJob()).isEqualTo(UPDATED_IS_PARTNER_JOB);
        assertThat(testJob.getRedirectionUrl()).isEqualTo(UPDATED_REDIRECTION_URL);
        assertThat(testJob.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);
        assertThat(testJob.getEndClient()).isEqualTo(UPDATED_END_CLIENT);
        assertThat(testJob.getDomainAlt()).isEqualTo(UPDATED_DOMAIN_ALT);
        assertThat(testJob.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testJob.getAdditionalNotificationsToUserIds()).isEqualTo(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_USER_IDS);
        assertThat(testJob.getAdditionalNotificationsToTeamIds()).isEqualTo(UPDATED_ADDITIONAL_NOTIFICATIONS_TO_TEAM_IDS);
        assertThat(testJob.getRequiredDocumentIds()).isEqualTo(UPDATED_REQUIRED_DOCUMENT_IDS);
        assertThat(testJob.getJobCloseReasonOtherAlt()).isEqualTo(UPDATED_JOB_CLOSE_REASON_OTHER_ALT);
        assertThat(testJob.getAddToCareerPage()).isEqualTo(UPDATED_ADD_TO_CAREER_PAGE);
        assertThat(testJob.getRemoteJob()).isEqualTo(UPDATED_REMOTE_JOB);
        assertThat(testJob.getHiringFor()).isEqualTo(UPDATED_HIRING_FOR);
        assertThat(testJob.getWorkDurationTimeSpan()).isEqualTo(UPDATED_WORK_DURATION_TIME_SPAN);
        assertThat(testJob.getTaxTerms()).isEqualTo(UPDATED_TAX_TERMS);
    }

    @Test
    @Transactional
    void patchNonExistingJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();
        job.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, job.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(job))
            )
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();
        job.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(job))
            )
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();
        job.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Delete the job
        restJobMockMvc.perform(delete(ENTITY_API_URL_ID, job.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
