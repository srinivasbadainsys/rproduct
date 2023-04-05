package com.rp.web.rest;

import static com.rp.web.rest.TestUtil.sameInstant;
import static com.rp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rp.IntegrationTest;
import com.rp.domain.Candidate;
import com.rp.domain.enumeration.CandidateRelocationType;
import com.rp.domain.enumeration.SalaryTimeSpan;
import com.rp.domain.enumeration.SalaryTimeSpan;
import com.rp.repository.CandidateRepository;
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
 * Integration tests for the {@link CandidateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CandidateResourceIT {

    private static final String DEFAULT_SALUTATION = "AAAAAAAAAA";
    private static final String UPDATED_SALUTATION = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_EMAILS = "AAAAAAAAAA";
    private static final String UPDATED_ALT_EMAILS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_PHONES = "AAAAAAAAAA";
    private static final String UPDATED_ALT_PHONES = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DOB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Long DEFAULT_WORK_AUTHORIZATION_ID = 1L;
    private static final Long UPDATED_WORK_AUTHORIZATION_ID = 2L;

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

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_EXP_IN_YEARS = 1D;
    private static final Double UPDATED_TOTAL_EXP_IN_YEARS = 2D;

    private static final Double DEFAULT_TOTAL_EXP_IN_MONTHS = 1D;
    private static final Double UPDATED_TOTAL_EXP_IN_MONTHS = 2D;

    private static final Double DEFAULT_RELEVANT_EXP_IN_YEARS = 1D;
    private static final Double UPDATED_RELEVANT_EXP_IN_YEARS = 2D;

    private static final Double DEFAULT_RELEVANT_EXP_IN_MONTHS = 1D;
    private static final Double UPDATED_RELEVANT_EXP_IN_MONTHS = 2D;

    private static final String DEFAULT_REFERRED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REFERRED_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNERSHIP_USER_ID = 1L;
    private static final Long UPDATED_OWNERSHIP_USER_ID = 2L;

    private static final String DEFAULT_CURRENT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_EMPLOYER = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_EMPLOYER = "BBBBBBBBBB";

    private static final Long DEFAULT_CURRENT_JOB_TYPE_ID = 1L;
    private static final Long UPDATED_CURRENT_JOB_TYPE_ID = 2L;

    private static final String DEFAULT_CURRENT_PAY_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PAY_CURRENCY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CURRENT_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_PAY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CURRENT_PAY_MONTHLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_PAY_MONTHLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CURRENT_PAY_HOURLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_PAY_HOURLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CURRENT_PAY_YEARLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_PAY_YEARLY = new BigDecimal(2);

    private static final SalaryTimeSpan DEFAULT_CURRENT_PAY_TIME_SPAN = SalaryTimeSpan.Fortnight;
    private static final SalaryTimeSpan UPDATED_CURRENT_PAY_TIME_SPAN = SalaryTimeSpan.Monthly;

    private static final String DEFAULT_OTHER_CURRENT_BENEFITS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_CURRENT_BENEFITS = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECTED_PAY_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED_PAY_CURRENCY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MIN_MONTHLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MIN_MONTHLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MIN_HOURLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MIN_HOURLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MIN_YEARLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MIN_YEARLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MAX_MONTHLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MAX_MONTHLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MAX_HOURLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MAX_HOURLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPECTED_PAY_MAX_YEARLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPECTED_PAY_MAX_YEARLY = new BigDecimal(2);

    private static final SalaryTimeSpan DEFAULT_EXPECTED_PAY_TIME_SPAN = SalaryTimeSpan.Fortnight;
    private static final SalaryTimeSpan UPDATED_EXPECTED_PAY_TIME_SPAN = SalaryTimeSpan.Monthly;

    private static final Long DEFAULT_EXPECTED_PAY_TAX_TERM_ID = 1L;
    private static final Long UPDATED_EXPECTED_PAY_TAX_TERM_ID = 2L;

    private static final String DEFAULT_OTHER_EXPECTED_BENEFITS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_EXPECTED_BENEFITS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_COMMENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RELOCATION = false;
    private static final Boolean UPDATED_RELOCATION = true;

    private static final Boolean DEFAULT_FAMILY_WILLING_TO_RELOCATE = false;
    private static final Boolean UPDATED_FAMILY_WILLING_TO_RELOCATE = true;

    private static final CandidateRelocationType DEFAULT_RELOCATION_TYPE = CandidateRelocationType.Within_City;
    private static final CandidateRelocationType UPDATED_RELOCATION_TYPE = CandidateRelocationType.Within_State;

    private static final String DEFAULT_RELOCATION_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_RELOCATION_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_TERM_IDS = "AAAAAAAAAA";
    private static final String UPDATED_TAX_TERM_IDS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTICE_PERIOD_IN_DAYS = 1;
    private static final Integer UPDATED_NOTICE_PERIOD_IN_DAYS = 2;

    private static final ZonedDateTime DEFAULT_WORK_AUTHORIZATION_EXPIRY = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_WORK_AUTHORIZATION_EXPIRY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GPA = "AAAAAAAAAA";
    private static final String UPDATED_GPA = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN_PROFILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN_PROFILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_SOCIAL_UR_LS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_SOCIAL_UR_LS = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_RESUMES = "AAAAAAAAAA";
    private static final String UPDATED_RESUMES = "BBBBBBBBBB";

    private static final String DEFAULT_RIGHT_TO_REPERESENT = "AAAAAAAAAA";
    private static final String UPDATED_RIGHT_TO_REPERESENT = "BBBBBBBBBB";

    private static final String DEFAULT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_ALT_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGIES = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGIES = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATIONS = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGES = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYER_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/candidates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .salutation(DEFAULT_SALUTATION)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .altEmails(DEFAULT_ALT_EMAILS)
            .phone(DEFAULT_PHONE)
            .altPhones(DEFAULT_ALT_PHONES)
            .dob(DEFAULT_DOB)
            .nationality(DEFAULT_NATIONALITY)
            .workAuthorizationId(DEFAULT_WORK_AUTHORIZATION_ID)
            .address(DEFAULT_ADDRESS)
            .area(DEFAULT_AREA)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .stateCode(DEFAULT_STATE_CODE)
            .county(DEFAULT_COUNTY)
            .country(DEFAULT_COUNTRY)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .zipCode(DEFAULT_ZIP_CODE)
            .source(DEFAULT_SOURCE)
            .totalExpInYears(DEFAULT_TOTAL_EXP_IN_YEARS)
            .totalExpInMonths(DEFAULT_TOTAL_EXP_IN_MONTHS)
            .relevantExpInYears(DEFAULT_RELEVANT_EXP_IN_YEARS)
            .relevantExpInMonths(DEFAULT_RELEVANT_EXP_IN_MONTHS)
            .referredBy(DEFAULT_REFERRED_BY)
            .ownershipUserId(DEFAULT_OWNERSHIP_USER_ID)
            .currentJobTitle(DEFAULT_CURRENT_JOB_TITLE)
            .currentEmployer(DEFAULT_CURRENT_EMPLOYER)
            .currentJobTypeId(DEFAULT_CURRENT_JOB_TYPE_ID)
            .currentPayCurrency(DEFAULT_CURRENT_PAY_CURRENCY)
            .currentPay(DEFAULT_CURRENT_PAY)
            .currentPayMonthly(DEFAULT_CURRENT_PAY_MONTHLY)
            .currentPayHourly(DEFAULT_CURRENT_PAY_HOURLY)
            .currentPayYearly(DEFAULT_CURRENT_PAY_YEARLY)
            .currentPayTimeSpan(DEFAULT_CURRENT_PAY_TIME_SPAN)
            .otherCurrentBenefits(DEFAULT_OTHER_CURRENT_BENEFITS)
            .expectedPayCurrency(DEFAULT_EXPECTED_PAY_CURRENCY)
            .expectedPayMin(DEFAULT_EXPECTED_PAY_MIN)
            .expectedPayMax(DEFAULT_EXPECTED_PAY_MAX)
            .expectedPayMinMonthly(DEFAULT_EXPECTED_PAY_MIN_MONTHLY)
            .expectedPayMinHourly(DEFAULT_EXPECTED_PAY_MIN_HOURLY)
            .expectedPayMinYearly(DEFAULT_EXPECTED_PAY_MIN_YEARLY)
            .expectedPayMaxMonthly(DEFAULT_EXPECTED_PAY_MAX_MONTHLY)
            .expectedPayMaxHourly(DEFAULT_EXPECTED_PAY_MAX_HOURLY)
            .expectedPayMaxYearly(DEFAULT_EXPECTED_PAY_MAX_YEARLY)
            .expectedPayTimeSpan(DEFAULT_EXPECTED_PAY_TIME_SPAN)
            .expectedPayTaxTermId(DEFAULT_EXPECTED_PAY_TAX_TERM_ID)
            .otherExpectedBenefits(DEFAULT_OTHER_EXPECTED_BENEFITS)
            .additionalComments(DEFAULT_ADDITIONAL_COMMENTS)
            .relocation(DEFAULT_RELOCATION)
            .familyWillingToRelocate(DEFAULT_FAMILY_WILLING_TO_RELOCATE)
            .relocationType(DEFAULT_RELOCATION_TYPE)
            .relocationRemarks(DEFAULT_RELOCATION_REMARKS)
            .taxTermIds(DEFAULT_TAX_TERM_IDS)
            .noticePeriodInDays(DEFAULT_NOTICE_PERIOD_IN_DAYS)
            .workAuthorizationExpiry(DEFAULT_WORK_AUTHORIZATION_EXPIRY)
            .gpa(DEFAULT_GPA)
            .aadharNumber(DEFAULT_AADHAR_NUMBER)
            .linkedInProfileURL(DEFAULT_LINKED_IN_PROFILE_URL)
            .otherSocialURLs(DEFAULT_OTHER_SOCIAL_UR_LS)
            .tags(DEFAULT_TAGS)
            .resumes(DEFAULT_RESUMES)
            .rightToReperesent(DEFAULT_RIGHT_TO_REPERESENT)
            .skills(DEFAULT_SKILLS)
            .altSkills(DEFAULT_ALT_SKILLS)
            .technologies(DEFAULT_TECHNOLOGIES)
            .certifications(DEFAULT_CERTIFICATIONS)
            .languages(DEFAULT_LANGUAGES)
            .workExperience(DEFAULT_WORK_EXPERIENCE)
            .education(DEFAULT_EDUCATION)
            .employerDetails(DEFAULT_EMPLOYER_DETAILS)
            .documents(DEFAULT_DOCUMENTS);
        return candidate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createUpdatedEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .altEmails(UPDATED_ALT_EMAILS)
            .phone(UPDATED_PHONE)
            .altPhones(UPDATED_ALT_PHONES)
            .dob(UPDATED_DOB)
            .nationality(UPDATED_NATIONALITY)
            .workAuthorizationId(UPDATED_WORK_AUTHORIZATION_ID)
            .address(UPDATED_ADDRESS)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .source(UPDATED_SOURCE)
            .totalExpInYears(UPDATED_TOTAL_EXP_IN_YEARS)
            .totalExpInMonths(UPDATED_TOTAL_EXP_IN_MONTHS)
            .relevantExpInYears(UPDATED_RELEVANT_EXP_IN_YEARS)
            .relevantExpInMonths(UPDATED_RELEVANT_EXP_IN_MONTHS)
            .referredBy(UPDATED_REFERRED_BY)
            .ownershipUserId(UPDATED_OWNERSHIP_USER_ID)
            .currentJobTitle(UPDATED_CURRENT_JOB_TITLE)
            .currentEmployer(UPDATED_CURRENT_EMPLOYER)
            .currentJobTypeId(UPDATED_CURRENT_JOB_TYPE_ID)
            .currentPayCurrency(UPDATED_CURRENT_PAY_CURRENCY)
            .currentPay(UPDATED_CURRENT_PAY)
            .currentPayMonthly(UPDATED_CURRENT_PAY_MONTHLY)
            .currentPayHourly(UPDATED_CURRENT_PAY_HOURLY)
            .currentPayYearly(UPDATED_CURRENT_PAY_YEARLY)
            .currentPayTimeSpan(UPDATED_CURRENT_PAY_TIME_SPAN)
            .otherCurrentBenefits(UPDATED_OTHER_CURRENT_BENEFITS)
            .expectedPayCurrency(UPDATED_EXPECTED_PAY_CURRENCY)
            .expectedPayMin(UPDATED_EXPECTED_PAY_MIN)
            .expectedPayMax(UPDATED_EXPECTED_PAY_MAX)
            .expectedPayMinMonthly(UPDATED_EXPECTED_PAY_MIN_MONTHLY)
            .expectedPayMinHourly(UPDATED_EXPECTED_PAY_MIN_HOURLY)
            .expectedPayMinYearly(UPDATED_EXPECTED_PAY_MIN_YEARLY)
            .expectedPayMaxMonthly(UPDATED_EXPECTED_PAY_MAX_MONTHLY)
            .expectedPayMaxHourly(UPDATED_EXPECTED_PAY_MAX_HOURLY)
            .expectedPayMaxYearly(UPDATED_EXPECTED_PAY_MAX_YEARLY)
            .expectedPayTimeSpan(UPDATED_EXPECTED_PAY_TIME_SPAN)
            .expectedPayTaxTermId(UPDATED_EXPECTED_PAY_TAX_TERM_ID)
            .otherExpectedBenefits(UPDATED_OTHER_EXPECTED_BENEFITS)
            .additionalComments(UPDATED_ADDITIONAL_COMMENTS)
            .relocation(UPDATED_RELOCATION)
            .familyWillingToRelocate(UPDATED_FAMILY_WILLING_TO_RELOCATE)
            .relocationType(UPDATED_RELOCATION_TYPE)
            .relocationRemarks(UPDATED_RELOCATION_REMARKS)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .noticePeriodInDays(UPDATED_NOTICE_PERIOD_IN_DAYS)
            .workAuthorizationExpiry(UPDATED_WORK_AUTHORIZATION_EXPIRY)
            .gpa(UPDATED_GPA)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .linkedInProfileURL(UPDATED_LINKED_IN_PROFILE_URL)
            .otherSocialURLs(UPDATED_OTHER_SOCIAL_UR_LS)
            .tags(UPDATED_TAGS)
            .resumes(UPDATED_RESUMES)
            .rightToReperesent(UPDATED_RIGHT_TO_REPERESENT)
            .skills(UPDATED_SKILLS)
            .altSkills(UPDATED_ALT_SKILLS)
            .technologies(UPDATED_TECHNOLOGIES)
            .certifications(UPDATED_CERTIFICATIONS)
            .languages(UPDATED_LANGUAGES)
            .workExperience(UPDATED_WORK_EXPERIENCE)
            .education(UPDATED_EDUCATION)
            .employerDetails(UPDATED_EMPLOYER_DETAILS)
            .documents(UPDATED_DOCUMENTS);
        return candidate;
    }

    @BeforeEach
    public void initTest() {
        candidate = createEntity(em);
    }

    @Test
    @Transactional
    void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();
        // Create the Candidate
        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testCandidate.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCandidate.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCandidate.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCandidate.getAltEmails()).isEqualTo(DEFAULT_ALT_EMAILS);
        assertThat(testCandidate.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCandidate.getAltPhones()).isEqualTo(DEFAULT_ALT_PHONES);
        assertThat(testCandidate.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testCandidate.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testCandidate.getWorkAuthorizationId()).isEqualTo(DEFAULT_WORK_AUTHORIZATION_ID);
        assertThat(testCandidate.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCandidate.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testCandidate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCandidate.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCandidate.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testCandidate.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testCandidate.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCandidate.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCandidate.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testCandidate.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCandidate.getTotalExpInYears()).isEqualTo(DEFAULT_TOTAL_EXP_IN_YEARS);
        assertThat(testCandidate.getTotalExpInMonths()).isEqualTo(DEFAULT_TOTAL_EXP_IN_MONTHS);
        assertThat(testCandidate.getRelevantExpInYears()).isEqualTo(DEFAULT_RELEVANT_EXP_IN_YEARS);
        assertThat(testCandidate.getRelevantExpInMonths()).isEqualTo(DEFAULT_RELEVANT_EXP_IN_MONTHS);
        assertThat(testCandidate.getReferredBy()).isEqualTo(DEFAULT_REFERRED_BY);
        assertThat(testCandidate.getOwnershipUserId()).isEqualTo(DEFAULT_OWNERSHIP_USER_ID);
        assertThat(testCandidate.getCurrentJobTitle()).isEqualTo(DEFAULT_CURRENT_JOB_TITLE);
        assertThat(testCandidate.getCurrentEmployer()).isEqualTo(DEFAULT_CURRENT_EMPLOYER);
        assertThat(testCandidate.getCurrentJobTypeId()).isEqualTo(DEFAULT_CURRENT_JOB_TYPE_ID);
        assertThat(testCandidate.getCurrentPayCurrency()).isEqualTo(DEFAULT_CURRENT_PAY_CURRENCY);
        assertThat(testCandidate.getCurrentPay()).isEqualByComparingTo(DEFAULT_CURRENT_PAY);
        assertThat(testCandidate.getCurrentPayMonthly()).isEqualByComparingTo(DEFAULT_CURRENT_PAY_MONTHLY);
        assertThat(testCandidate.getCurrentPayHourly()).isEqualByComparingTo(DEFAULT_CURRENT_PAY_HOURLY);
        assertThat(testCandidate.getCurrentPayYearly()).isEqualByComparingTo(DEFAULT_CURRENT_PAY_YEARLY);
        assertThat(testCandidate.getCurrentPayTimeSpan()).isEqualTo(DEFAULT_CURRENT_PAY_TIME_SPAN);
        assertThat(testCandidate.getOtherCurrentBenefits()).isEqualTo(DEFAULT_OTHER_CURRENT_BENEFITS);
        assertThat(testCandidate.getExpectedPayCurrency()).isEqualTo(DEFAULT_EXPECTED_PAY_CURRENCY);
        assertThat(testCandidate.getExpectedPayMin()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MIN);
        assertThat(testCandidate.getExpectedPayMax()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX);
        assertThat(testCandidate.getExpectedPayMinMonthly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MIN_MONTHLY);
        assertThat(testCandidate.getExpectedPayMinHourly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MIN_HOURLY);
        assertThat(testCandidate.getExpectedPayMinYearly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MIN_YEARLY);
        assertThat(testCandidate.getExpectedPayMaxMonthly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX_MONTHLY);
        assertThat(testCandidate.getExpectedPayMaxHourly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX_HOURLY);
        assertThat(testCandidate.getExpectedPayMaxYearly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX_YEARLY);
        assertThat(testCandidate.getExpectedPayTimeSpan()).isEqualTo(DEFAULT_EXPECTED_PAY_TIME_SPAN);
        assertThat(testCandidate.getExpectedPayTaxTermId()).isEqualTo(DEFAULT_EXPECTED_PAY_TAX_TERM_ID);
        assertThat(testCandidate.getOtherExpectedBenefits()).isEqualTo(DEFAULT_OTHER_EXPECTED_BENEFITS);
        assertThat(testCandidate.getAdditionalComments()).isEqualTo(DEFAULT_ADDITIONAL_COMMENTS);
        assertThat(testCandidate.getRelocation()).isEqualTo(DEFAULT_RELOCATION);
        assertThat(testCandidate.getFamilyWillingToRelocate()).isEqualTo(DEFAULT_FAMILY_WILLING_TO_RELOCATE);
        assertThat(testCandidate.getRelocationType()).isEqualTo(DEFAULT_RELOCATION_TYPE);
        assertThat(testCandidate.getRelocationRemarks()).isEqualTo(DEFAULT_RELOCATION_REMARKS);
        assertThat(testCandidate.getTaxTermIds()).isEqualTo(DEFAULT_TAX_TERM_IDS);
        assertThat(testCandidate.getNoticePeriodInDays()).isEqualTo(DEFAULT_NOTICE_PERIOD_IN_DAYS);
        assertThat(testCandidate.getWorkAuthorizationExpiry()).isEqualTo(DEFAULT_WORK_AUTHORIZATION_EXPIRY);
        assertThat(testCandidate.getGpa()).isEqualTo(DEFAULT_GPA);
        assertThat(testCandidate.getAadharNumber()).isEqualTo(DEFAULT_AADHAR_NUMBER);
        assertThat(testCandidate.getLinkedInProfileURL()).isEqualTo(DEFAULT_LINKED_IN_PROFILE_URL);
        assertThat(testCandidate.getOtherSocialURLs()).isEqualTo(DEFAULT_OTHER_SOCIAL_UR_LS);
        assertThat(testCandidate.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testCandidate.getResumes()).isEqualTo(DEFAULT_RESUMES);
        assertThat(testCandidate.getRightToReperesent()).isEqualTo(DEFAULT_RIGHT_TO_REPERESENT);
        assertThat(testCandidate.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testCandidate.getAltSkills()).isEqualTo(DEFAULT_ALT_SKILLS);
        assertThat(testCandidate.getTechnologies()).isEqualTo(DEFAULT_TECHNOLOGIES);
        assertThat(testCandidate.getCertifications()).isEqualTo(DEFAULT_CERTIFICATIONS);
        assertThat(testCandidate.getLanguages()).isEqualTo(DEFAULT_LANGUAGES);
        assertThat(testCandidate.getWorkExperience()).isEqualTo(DEFAULT_WORK_EXPERIENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testCandidate.getEmployerDetails()).isEqualTo(DEFAULT_EMPLOYER_DETAILS);
        assertThat(testCandidate.getDocuments()).isEqualTo(DEFAULT_DOCUMENTS);
    }

    @Test
    @Transactional
    void createCandidateWithExistingId() throws Exception {
        // Create the Candidate with an existing ID
        candidate.setId(1L);

        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].altEmails").value(hasItem(DEFAULT_ALT_EMAILS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].altPhones").value(hasItem(DEFAULT_ALT_PHONES)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(sameInstant(DEFAULT_DOB))))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].workAuthorizationId").value(hasItem(DEFAULT_WORK_AUTHORIZATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].totalExpInYears").value(hasItem(DEFAULT_TOTAL_EXP_IN_YEARS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalExpInMonths").value(hasItem(DEFAULT_TOTAL_EXP_IN_MONTHS.doubleValue())))
            .andExpect(jsonPath("$.[*].relevantExpInYears").value(hasItem(DEFAULT_RELEVANT_EXP_IN_YEARS.doubleValue())))
            .andExpect(jsonPath("$.[*].relevantExpInMonths").value(hasItem(DEFAULT_RELEVANT_EXP_IN_MONTHS.doubleValue())))
            .andExpect(jsonPath("$.[*].referredBy").value(hasItem(DEFAULT_REFERRED_BY)))
            .andExpect(jsonPath("$.[*].ownershipUserId").value(hasItem(DEFAULT_OWNERSHIP_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].currentJobTitle").value(hasItem(DEFAULT_CURRENT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].currentEmployer").value(hasItem(DEFAULT_CURRENT_EMPLOYER)))
            .andExpect(jsonPath("$.[*].currentJobTypeId").value(hasItem(DEFAULT_CURRENT_JOB_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].currentPayCurrency").value(hasItem(DEFAULT_CURRENT_PAY_CURRENCY)))
            .andExpect(jsonPath("$.[*].currentPay").value(hasItem(sameNumber(DEFAULT_CURRENT_PAY))))
            .andExpect(jsonPath("$.[*].currentPayMonthly").value(hasItem(sameNumber(DEFAULT_CURRENT_PAY_MONTHLY))))
            .andExpect(jsonPath("$.[*].currentPayHourly").value(hasItem(sameNumber(DEFAULT_CURRENT_PAY_HOURLY))))
            .andExpect(jsonPath("$.[*].currentPayYearly").value(hasItem(sameNumber(DEFAULT_CURRENT_PAY_YEARLY))))
            .andExpect(jsonPath("$.[*].currentPayTimeSpan").value(hasItem(DEFAULT_CURRENT_PAY_TIME_SPAN.toString())))
            .andExpect(jsonPath("$.[*].otherCurrentBenefits").value(hasItem(DEFAULT_OTHER_CURRENT_BENEFITS)))
            .andExpect(jsonPath("$.[*].expectedPayCurrency").value(hasItem(DEFAULT_EXPECTED_PAY_CURRENCY)))
            .andExpect(jsonPath("$.[*].expectedPayMin").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MIN))))
            .andExpect(jsonPath("$.[*].expectedPayMax").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MAX))))
            .andExpect(jsonPath("$.[*].expectedPayMinMonthly").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MIN_MONTHLY))))
            .andExpect(jsonPath("$.[*].expectedPayMinHourly").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MIN_HOURLY))))
            .andExpect(jsonPath("$.[*].expectedPayMinYearly").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MIN_YEARLY))))
            .andExpect(jsonPath("$.[*].expectedPayMaxMonthly").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MAX_MONTHLY))))
            .andExpect(jsonPath("$.[*].expectedPayMaxHourly").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MAX_HOURLY))))
            .andExpect(jsonPath("$.[*].expectedPayMaxYearly").value(hasItem(sameNumber(DEFAULT_EXPECTED_PAY_MAX_YEARLY))))
            .andExpect(jsonPath("$.[*].expectedPayTimeSpan").value(hasItem(DEFAULT_EXPECTED_PAY_TIME_SPAN.toString())))
            .andExpect(jsonPath("$.[*].expectedPayTaxTermId").value(hasItem(DEFAULT_EXPECTED_PAY_TAX_TERM_ID.intValue())))
            .andExpect(jsonPath("$.[*].otherExpectedBenefits").value(hasItem(DEFAULT_OTHER_EXPECTED_BENEFITS)))
            .andExpect(jsonPath("$.[*].additionalComments").value(hasItem(DEFAULT_ADDITIONAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].relocation").value(hasItem(DEFAULT_RELOCATION.booleanValue())))
            .andExpect(jsonPath("$.[*].familyWillingToRelocate").value(hasItem(DEFAULT_FAMILY_WILLING_TO_RELOCATE.booleanValue())))
            .andExpect(jsonPath("$.[*].relocationType").value(hasItem(DEFAULT_RELOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relocationRemarks").value(hasItem(DEFAULT_RELOCATION_REMARKS)))
            .andExpect(jsonPath("$.[*].taxTermIds").value(hasItem(DEFAULT_TAX_TERM_IDS)))
            .andExpect(jsonPath("$.[*].noticePeriodInDays").value(hasItem(DEFAULT_NOTICE_PERIOD_IN_DAYS)))
            .andExpect(jsonPath("$.[*].workAuthorizationExpiry").value(hasItem(sameInstant(DEFAULT_WORK_AUTHORIZATION_EXPIRY))))
            .andExpect(jsonPath("$.[*].gpa").value(hasItem(DEFAULT_GPA)))
            .andExpect(jsonPath("$.[*].aadharNumber").value(hasItem(DEFAULT_AADHAR_NUMBER)))
            .andExpect(jsonPath("$.[*].linkedInProfileURL").value(hasItem(DEFAULT_LINKED_IN_PROFILE_URL)))
            .andExpect(jsonPath("$.[*].otherSocialURLs").value(hasItem(DEFAULT_OTHER_SOCIAL_UR_LS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].resumes").value(hasItem(DEFAULT_RESUMES)))
            .andExpect(jsonPath("$.[*].rightToReperesent").value(hasItem(DEFAULT_RIGHT_TO_REPERESENT)))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS)))
            .andExpect(jsonPath("$.[*].altSkills").value(hasItem(DEFAULT_ALT_SKILLS)))
            .andExpect(jsonPath("$.[*].technologies").value(hasItem(DEFAULT_TECHNOLOGIES)))
            .andExpect(jsonPath("$.[*].certifications").value(hasItem(DEFAULT_CERTIFICATIONS)))
            .andExpect(jsonPath("$.[*].languages").value(hasItem(DEFAULT_LANGUAGES)))
            .andExpect(jsonPath("$.[*].workExperience").value(hasItem(DEFAULT_WORK_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].employerDetails").value(hasItem(DEFAULT_EMPLOYER_DETAILS)))
            .andExpect(jsonPath("$.[*].documents").value(hasItem(DEFAULT_DOCUMENTS)));
    }

    @Test
    @Transactional
    void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL_ID, candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.altEmails").value(DEFAULT_ALT_EMAILS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.altPhones").value(DEFAULT_ALT_PHONES))
            .andExpect(jsonPath("$.dob").value(sameInstant(DEFAULT_DOB)))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.workAuthorizationId").value(DEFAULT_WORK_AUTHORIZATION_ID.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.totalExpInYears").value(DEFAULT_TOTAL_EXP_IN_YEARS.doubleValue()))
            .andExpect(jsonPath("$.totalExpInMonths").value(DEFAULT_TOTAL_EXP_IN_MONTHS.doubleValue()))
            .andExpect(jsonPath("$.relevantExpInYears").value(DEFAULT_RELEVANT_EXP_IN_YEARS.doubleValue()))
            .andExpect(jsonPath("$.relevantExpInMonths").value(DEFAULT_RELEVANT_EXP_IN_MONTHS.doubleValue()))
            .andExpect(jsonPath("$.referredBy").value(DEFAULT_REFERRED_BY))
            .andExpect(jsonPath("$.ownershipUserId").value(DEFAULT_OWNERSHIP_USER_ID.intValue()))
            .andExpect(jsonPath("$.currentJobTitle").value(DEFAULT_CURRENT_JOB_TITLE))
            .andExpect(jsonPath("$.currentEmployer").value(DEFAULT_CURRENT_EMPLOYER))
            .andExpect(jsonPath("$.currentJobTypeId").value(DEFAULT_CURRENT_JOB_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.currentPayCurrency").value(DEFAULT_CURRENT_PAY_CURRENCY))
            .andExpect(jsonPath("$.currentPay").value(sameNumber(DEFAULT_CURRENT_PAY)))
            .andExpect(jsonPath("$.currentPayMonthly").value(sameNumber(DEFAULT_CURRENT_PAY_MONTHLY)))
            .andExpect(jsonPath("$.currentPayHourly").value(sameNumber(DEFAULT_CURRENT_PAY_HOURLY)))
            .andExpect(jsonPath("$.currentPayYearly").value(sameNumber(DEFAULT_CURRENT_PAY_YEARLY)))
            .andExpect(jsonPath("$.currentPayTimeSpan").value(DEFAULT_CURRENT_PAY_TIME_SPAN.toString()))
            .andExpect(jsonPath("$.otherCurrentBenefits").value(DEFAULT_OTHER_CURRENT_BENEFITS))
            .andExpect(jsonPath("$.expectedPayCurrency").value(DEFAULT_EXPECTED_PAY_CURRENCY))
            .andExpect(jsonPath("$.expectedPayMin").value(sameNumber(DEFAULT_EXPECTED_PAY_MIN)))
            .andExpect(jsonPath("$.expectedPayMax").value(sameNumber(DEFAULT_EXPECTED_PAY_MAX)))
            .andExpect(jsonPath("$.expectedPayMinMonthly").value(sameNumber(DEFAULT_EXPECTED_PAY_MIN_MONTHLY)))
            .andExpect(jsonPath("$.expectedPayMinHourly").value(sameNumber(DEFAULT_EXPECTED_PAY_MIN_HOURLY)))
            .andExpect(jsonPath("$.expectedPayMinYearly").value(sameNumber(DEFAULT_EXPECTED_PAY_MIN_YEARLY)))
            .andExpect(jsonPath("$.expectedPayMaxMonthly").value(sameNumber(DEFAULT_EXPECTED_PAY_MAX_MONTHLY)))
            .andExpect(jsonPath("$.expectedPayMaxHourly").value(sameNumber(DEFAULT_EXPECTED_PAY_MAX_HOURLY)))
            .andExpect(jsonPath("$.expectedPayMaxYearly").value(sameNumber(DEFAULT_EXPECTED_PAY_MAX_YEARLY)))
            .andExpect(jsonPath("$.expectedPayTimeSpan").value(DEFAULT_EXPECTED_PAY_TIME_SPAN.toString()))
            .andExpect(jsonPath("$.expectedPayTaxTermId").value(DEFAULT_EXPECTED_PAY_TAX_TERM_ID.intValue()))
            .andExpect(jsonPath("$.otherExpectedBenefits").value(DEFAULT_OTHER_EXPECTED_BENEFITS))
            .andExpect(jsonPath("$.additionalComments").value(DEFAULT_ADDITIONAL_COMMENTS))
            .andExpect(jsonPath("$.relocation").value(DEFAULT_RELOCATION.booleanValue()))
            .andExpect(jsonPath("$.familyWillingToRelocate").value(DEFAULT_FAMILY_WILLING_TO_RELOCATE.booleanValue()))
            .andExpect(jsonPath("$.relocationType").value(DEFAULT_RELOCATION_TYPE.toString()))
            .andExpect(jsonPath("$.relocationRemarks").value(DEFAULT_RELOCATION_REMARKS))
            .andExpect(jsonPath("$.taxTermIds").value(DEFAULT_TAX_TERM_IDS))
            .andExpect(jsonPath("$.noticePeriodInDays").value(DEFAULT_NOTICE_PERIOD_IN_DAYS))
            .andExpect(jsonPath("$.workAuthorizationExpiry").value(sameInstant(DEFAULT_WORK_AUTHORIZATION_EXPIRY)))
            .andExpect(jsonPath("$.gpa").value(DEFAULT_GPA))
            .andExpect(jsonPath("$.aadharNumber").value(DEFAULT_AADHAR_NUMBER))
            .andExpect(jsonPath("$.linkedInProfileURL").value(DEFAULT_LINKED_IN_PROFILE_URL))
            .andExpect(jsonPath("$.otherSocialURLs").value(DEFAULT_OTHER_SOCIAL_UR_LS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.resumes").value(DEFAULT_RESUMES))
            .andExpect(jsonPath("$.rightToReperesent").value(DEFAULT_RIGHT_TO_REPERESENT))
            .andExpect(jsonPath("$.skills").value(DEFAULT_SKILLS))
            .andExpect(jsonPath("$.altSkills").value(DEFAULT_ALT_SKILLS))
            .andExpect(jsonPath("$.technologies").value(DEFAULT_TECHNOLOGIES))
            .andExpect(jsonPath("$.certifications").value(DEFAULT_CERTIFICATIONS))
            .andExpect(jsonPath("$.languages").value(DEFAULT_LANGUAGES))
            .andExpect(jsonPath("$.workExperience").value(DEFAULT_WORK_EXPERIENCE))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.employerDetails").value(DEFAULT_EMPLOYER_DETAILS))
            .andExpect(jsonPath("$.documents").value(DEFAULT_DOCUMENTS));
    }

    @Test
    @Transactional
    void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findById(candidate.getId()).get();
        // Disconnect from session so that the updates on updatedCandidate are not directly saved in db
        em.detach(updatedCandidate);
        updatedCandidate
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .altEmails(UPDATED_ALT_EMAILS)
            .phone(UPDATED_PHONE)
            .altPhones(UPDATED_ALT_PHONES)
            .dob(UPDATED_DOB)
            .nationality(UPDATED_NATIONALITY)
            .workAuthorizationId(UPDATED_WORK_AUTHORIZATION_ID)
            .address(UPDATED_ADDRESS)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .source(UPDATED_SOURCE)
            .totalExpInYears(UPDATED_TOTAL_EXP_IN_YEARS)
            .totalExpInMonths(UPDATED_TOTAL_EXP_IN_MONTHS)
            .relevantExpInYears(UPDATED_RELEVANT_EXP_IN_YEARS)
            .relevantExpInMonths(UPDATED_RELEVANT_EXP_IN_MONTHS)
            .referredBy(UPDATED_REFERRED_BY)
            .ownershipUserId(UPDATED_OWNERSHIP_USER_ID)
            .currentJobTitle(UPDATED_CURRENT_JOB_TITLE)
            .currentEmployer(UPDATED_CURRENT_EMPLOYER)
            .currentJobTypeId(UPDATED_CURRENT_JOB_TYPE_ID)
            .currentPayCurrency(UPDATED_CURRENT_PAY_CURRENCY)
            .currentPay(UPDATED_CURRENT_PAY)
            .currentPayMonthly(UPDATED_CURRENT_PAY_MONTHLY)
            .currentPayHourly(UPDATED_CURRENT_PAY_HOURLY)
            .currentPayYearly(UPDATED_CURRENT_PAY_YEARLY)
            .currentPayTimeSpan(UPDATED_CURRENT_PAY_TIME_SPAN)
            .otherCurrentBenefits(UPDATED_OTHER_CURRENT_BENEFITS)
            .expectedPayCurrency(UPDATED_EXPECTED_PAY_CURRENCY)
            .expectedPayMin(UPDATED_EXPECTED_PAY_MIN)
            .expectedPayMax(UPDATED_EXPECTED_PAY_MAX)
            .expectedPayMinMonthly(UPDATED_EXPECTED_PAY_MIN_MONTHLY)
            .expectedPayMinHourly(UPDATED_EXPECTED_PAY_MIN_HOURLY)
            .expectedPayMinYearly(UPDATED_EXPECTED_PAY_MIN_YEARLY)
            .expectedPayMaxMonthly(UPDATED_EXPECTED_PAY_MAX_MONTHLY)
            .expectedPayMaxHourly(UPDATED_EXPECTED_PAY_MAX_HOURLY)
            .expectedPayMaxYearly(UPDATED_EXPECTED_PAY_MAX_YEARLY)
            .expectedPayTimeSpan(UPDATED_EXPECTED_PAY_TIME_SPAN)
            .expectedPayTaxTermId(UPDATED_EXPECTED_PAY_TAX_TERM_ID)
            .otherExpectedBenefits(UPDATED_OTHER_EXPECTED_BENEFITS)
            .additionalComments(UPDATED_ADDITIONAL_COMMENTS)
            .relocation(UPDATED_RELOCATION)
            .familyWillingToRelocate(UPDATED_FAMILY_WILLING_TO_RELOCATE)
            .relocationType(UPDATED_RELOCATION_TYPE)
            .relocationRemarks(UPDATED_RELOCATION_REMARKS)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .noticePeriodInDays(UPDATED_NOTICE_PERIOD_IN_DAYS)
            .workAuthorizationExpiry(UPDATED_WORK_AUTHORIZATION_EXPIRY)
            .gpa(UPDATED_GPA)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .linkedInProfileURL(UPDATED_LINKED_IN_PROFILE_URL)
            .otherSocialURLs(UPDATED_OTHER_SOCIAL_UR_LS)
            .tags(UPDATED_TAGS)
            .resumes(UPDATED_RESUMES)
            .rightToReperesent(UPDATED_RIGHT_TO_REPERESENT)
            .skills(UPDATED_SKILLS)
            .altSkills(UPDATED_ALT_SKILLS)
            .technologies(UPDATED_TECHNOLOGIES)
            .certifications(UPDATED_CERTIFICATIONS)
            .languages(UPDATED_LANGUAGES)
            .workExperience(UPDATED_WORK_EXPERIENCE)
            .education(UPDATED_EDUCATION)
            .employerDetails(UPDATED_EMPLOYER_DETAILS)
            .documents(UPDATED_DOCUMENTS);

        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCandidate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testCandidate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidate.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidate.getAltEmails()).isEqualTo(UPDATED_ALT_EMAILS);
        assertThat(testCandidate.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCandidate.getAltPhones()).isEqualTo(UPDATED_ALT_PHONES);
        assertThat(testCandidate.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testCandidate.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testCandidate.getWorkAuthorizationId()).isEqualTo(UPDATED_WORK_AUTHORIZATION_ID);
        assertThat(testCandidate.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCandidate.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testCandidate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCandidate.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidate.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCandidate.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCandidate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCandidate.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCandidate.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCandidate.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCandidate.getTotalExpInYears()).isEqualTo(UPDATED_TOTAL_EXP_IN_YEARS);
        assertThat(testCandidate.getTotalExpInMonths()).isEqualTo(UPDATED_TOTAL_EXP_IN_MONTHS);
        assertThat(testCandidate.getRelevantExpInYears()).isEqualTo(UPDATED_RELEVANT_EXP_IN_YEARS);
        assertThat(testCandidate.getRelevantExpInMonths()).isEqualTo(UPDATED_RELEVANT_EXP_IN_MONTHS);
        assertThat(testCandidate.getReferredBy()).isEqualTo(UPDATED_REFERRED_BY);
        assertThat(testCandidate.getOwnershipUserId()).isEqualTo(UPDATED_OWNERSHIP_USER_ID);
        assertThat(testCandidate.getCurrentJobTitle()).isEqualTo(UPDATED_CURRENT_JOB_TITLE);
        assertThat(testCandidate.getCurrentEmployer()).isEqualTo(UPDATED_CURRENT_EMPLOYER);
        assertThat(testCandidate.getCurrentJobTypeId()).isEqualTo(UPDATED_CURRENT_JOB_TYPE_ID);
        assertThat(testCandidate.getCurrentPayCurrency()).isEqualTo(UPDATED_CURRENT_PAY_CURRENCY);
        assertThat(testCandidate.getCurrentPay()).isEqualByComparingTo(UPDATED_CURRENT_PAY);
        assertThat(testCandidate.getCurrentPayMonthly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_MONTHLY);
        assertThat(testCandidate.getCurrentPayHourly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_HOURLY);
        assertThat(testCandidate.getCurrentPayYearly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_YEARLY);
        assertThat(testCandidate.getCurrentPayTimeSpan()).isEqualTo(UPDATED_CURRENT_PAY_TIME_SPAN);
        assertThat(testCandidate.getOtherCurrentBenefits()).isEqualTo(UPDATED_OTHER_CURRENT_BENEFITS);
        assertThat(testCandidate.getExpectedPayCurrency()).isEqualTo(UPDATED_EXPECTED_PAY_CURRENCY);
        assertThat(testCandidate.getExpectedPayMin()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN);
        assertThat(testCandidate.getExpectedPayMax()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX);
        assertThat(testCandidate.getExpectedPayMinMonthly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_MONTHLY);
        assertThat(testCandidate.getExpectedPayMinHourly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_HOURLY);
        assertThat(testCandidate.getExpectedPayMinYearly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_YEARLY);
        assertThat(testCandidate.getExpectedPayMaxMonthly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX_MONTHLY);
        assertThat(testCandidate.getExpectedPayMaxHourly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX_HOURLY);
        assertThat(testCandidate.getExpectedPayMaxYearly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX_YEARLY);
        assertThat(testCandidate.getExpectedPayTimeSpan()).isEqualTo(UPDATED_EXPECTED_PAY_TIME_SPAN);
        assertThat(testCandidate.getExpectedPayTaxTermId()).isEqualTo(UPDATED_EXPECTED_PAY_TAX_TERM_ID);
        assertThat(testCandidate.getOtherExpectedBenefits()).isEqualTo(UPDATED_OTHER_EXPECTED_BENEFITS);
        assertThat(testCandidate.getAdditionalComments()).isEqualTo(UPDATED_ADDITIONAL_COMMENTS);
        assertThat(testCandidate.getRelocation()).isEqualTo(UPDATED_RELOCATION);
        assertThat(testCandidate.getFamilyWillingToRelocate()).isEqualTo(UPDATED_FAMILY_WILLING_TO_RELOCATE);
        assertThat(testCandidate.getRelocationType()).isEqualTo(UPDATED_RELOCATION_TYPE);
        assertThat(testCandidate.getRelocationRemarks()).isEqualTo(UPDATED_RELOCATION_REMARKS);
        assertThat(testCandidate.getTaxTermIds()).isEqualTo(UPDATED_TAX_TERM_IDS);
        assertThat(testCandidate.getNoticePeriodInDays()).isEqualTo(UPDATED_NOTICE_PERIOD_IN_DAYS);
        assertThat(testCandidate.getWorkAuthorizationExpiry()).isEqualTo(UPDATED_WORK_AUTHORIZATION_EXPIRY);
        assertThat(testCandidate.getGpa()).isEqualTo(UPDATED_GPA);
        assertThat(testCandidate.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testCandidate.getLinkedInProfileURL()).isEqualTo(UPDATED_LINKED_IN_PROFILE_URL);
        assertThat(testCandidate.getOtherSocialURLs()).isEqualTo(UPDATED_OTHER_SOCIAL_UR_LS);
        assertThat(testCandidate.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testCandidate.getResumes()).isEqualTo(UPDATED_RESUMES);
        assertThat(testCandidate.getRightToReperesent()).isEqualTo(UPDATED_RIGHT_TO_REPERESENT);
        assertThat(testCandidate.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testCandidate.getAltSkills()).isEqualTo(UPDATED_ALT_SKILLS);
        assertThat(testCandidate.getTechnologies()).isEqualTo(UPDATED_TECHNOLOGIES);
        assertThat(testCandidate.getCertifications()).isEqualTo(UPDATED_CERTIFICATIONS);
        assertThat(testCandidate.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testCandidate.getWorkExperience()).isEqualTo(UPDATED_WORK_EXPERIENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testCandidate.getEmployerDetails()).isEqualTo(UPDATED_EMPLOYER_DETAILS);
        assertThat(testCandidate.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
    }

    @Test
    @Transactional
    void putNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate
            .salutation(UPDATED_SALUTATION)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .altEmails(UPDATED_ALT_EMAILS)
            .altPhones(UPDATED_ALT_PHONES)
            .address(UPDATED_ADDRESS)
            .area(UPDATED_AREA)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .zipCode(UPDATED_ZIP_CODE)
            .totalExpInMonths(UPDATED_TOTAL_EXP_IN_MONTHS)
            .relevantExpInYears(UPDATED_RELEVANT_EXP_IN_YEARS)
            .relevantExpInMonths(UPDATED_RELEVANT_EXP_IN_MONTHS)
            .referredBy(UPDATED_REFERRED_BY)
            .ownershipUserId(UPDATED_OWNERSHIP_USER_ID)
            .currentJobTypeId(UPDATED_CURRENT_JOB_TYPE_ID)
            .currentPayCurrency(UPDATED_CURRENT_PAY_CURRENCY)
            .currentPay(UPDATED_CURRENT_PAY)
            .currentPayHourly(UPDATED_CURRENT_PAY_HOURLY)
            .currentPayYearly(UPDATED_CURRENT_PAY_YEARLY)
            .otherCurrentBenefits(UPDATED_OTHER_CURRENT_BENEFITS)
            .expectedPayCurrency(UPDATED_EXPECTED_PAY_CURRENCY)
            .expectedPayMin(UPDATED_EXPECTED_PAY_MIN)
            .expectedPayMinMonthly(UPDATED_EXPECTED_PAY_MIN_MONTHLY)
            .expectedPayMinHourly(UPDATED_EXPECTED_PAY_MIN_HOURLY)
            .expectedPayMinYearly(UPDATED_EXPECTED_PAY_MIN_YEARLY)
            .expectedPayTaxTermId(UPDATED_EXPECTED_PAY_TAX_TERM_ID)
            .otherExpectedBenefits(UPDATED_OTHER_EXPECTED_BENEFITS)
            .additionalComments(UPDATED_ADDITIONAL_COMMENTS)
            .relocation(UPDATED_RELOCATION)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .noticePeriodInDays(UPDATED_NOTICE_PERIOD_IN_DAYS)
            .workAuthorizationExpiry(UPDATED_WORK_AUTHORIZATION_EXPIRY)
            .gpa(UPDATED_GPA)
            .linkedInProfileURL(UPDATED_LINKED_IN_PROFILE_URL)
            .otherSocialURLs(UPDATED_OTHER_SOCIAL_UR_LS)
            .rightToReperesent(UPDATED_RIGHT_TO_REPERESENT)
            .technologies(UPDATED_TECHNOLOGIES)
            .workExperience(UPDATED_WORK_EXPERIENCE)
            .education(UPDATED_EDUCATION);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testCandidate.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCandidate.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidate.getAltEmails()).isEqualTo(UPDATED_ALT_EMAILS);
        assertThat(testCandidate.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCandidate.getAltPhones()).isEqualTo(UPDATED_ALT_PHONES);
        assertThat(testCandidate.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testCandidate.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testCandidate.getWorkAuthorizationId()).isEqualTo(DEFAULT_WORK_AUTHORIZATION_ID);
        assertThat(testCandidate.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCandidate.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testCandidate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCandidate.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidate.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCandidate.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCandidate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCandidate.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCandidate.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCandidate.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCandidate.getTotalExpInYears()).isEqualTo(DEFAULT_TOTAL_EXP_IN_YEARS);
        assertThat(testCandidate.getTotalExpInMonths()).isEqualTo(UPDATED_TOTAL_EXP_IN_MONTHS);
        assertThat(testCandidate.getRelevantExpInYears()).isEqualTo(UPDATED_RELEVANT_EXP_IN_YEARS);
        assertThat(testCandidate.getRelevantExpInMonths()).isEqualTo(UPDATED_RELEVANT_EXP_IN_MONTHS);
        assertThat(testCandidate.getReferredBy()).isEqualTo(UPDATED_REFERRED_BY);
        assertThat(testCandidate.getOwnershipUserId()).isEqualTo(UPDATED_OWNERSHIP_USER_ID);
        assertThat(testCandidate.getCurrentJobTitle()).isEqualTo(DEFAULT_CURRENT_JOB_TITLE);
        assertThat(testCandidate.getCurrentEmployer()).isEqualTo(DEFAULT_CURRENT_EMPLOYER);
        assertThat(testCandidate.getCurrentJobTypeId()).isEqualTo(UPDATED_CURRENT_JOB_TYPE_ID);
        assertThat(testCandidate.getCurrentPayCurrency()).isEqualTo(UPDATED_CURRENT_PAY_CURRENCY);
        assertThat(testCandidate.getCurrentPay()).isEqualByComparingTo(UPDATED_CURRENT_PAY);
        assertThat(testCandidate.getCurrentPayMonthly()).isEqualByComparingTo(DEFAULT_CURRENT_PAY_MONTHLY);
        assertThat(testCandidate.getCurrentPayHourly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_HOURLY);
        assertThat(testCandidate.getCurrentPayYearly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_YEARLY);
        assertThat(testCandidate.getCurrentPayTimeSpan()).isEqualTo(DEFAULT_CURRENT_PAY_TIME_SPAN);
        assertThat(testCandidate.getOtherCurrentBenefits()).isEqualTo(UPDATED_OTHER_CURRENT_BENEFITS);
        assertThat(testCandidate.getExpectedPayCurrency()).isEqualTo(UPDATED_EXPECTED_PAY_CURRENCY);
        assertThat(testCandidate.getExpectedPayMin()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN);
        assertThat(testCandidate.getExpectedPayMax()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX);
        assertThat(testCandidate.getExpectedPayMinMonthly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_MONTHLY);
        assertThat(testCandidate.getExpectedPayMinHourly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_HOURLY);
        assertThat(testCandidate.getExpectedPayMinYearly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_YEARLY);
        assertThat(testCandidate.getExpectedPayMaxMonthly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX_MONTHLY);
        assertThat(testCandidate.getExpectedPayMaxHourly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX_HOURLY);
        assertThat(testCandidate.getExpectedPayMaxYearly()).isEqualByComparingTo(DEFAULT_EXPECTED_PAY_MAX_YEARLY);
        assertThat(testCandidate.getExpectedPayTimeSpan()).isEqualTo(DEFAULT_EXPECTED_PAY_TIME_SPAN);
        assertThat(testCandidate.getExpectedPayTaxTermId()).isEqualTo(UPDATED_EXPECTED_PAY_TAX_TERM_ID);
        assertThat(testCandidate.getOtherExpectedBenefits()).isEqualTo(UPDATED_OTHER_EXPECTED_BENEFITS);
        assertThat(testCandidate.getAdditionalComments()).isEqualTo(UPDATED_ADDITIONAL_COMMENTS);
        assertThat(testCandidate.getRelocation()).isEqualTo(UPDATED_RELOCATION);
        assertThat(testCandidate.getFamilyWillingToRelocate()).isEqualTo(DEFAULT_FAMILY_WILLING_TO_RELOCATE);
        assertThat(testCandidate.getRelocationType()).isEqualTo(DEFAULT_RELOCATION_TYPE);
        assertThat(testCandidate.getRelocationRemarks()).isEqualTo(DEFAULT_RELOCATION_REMARKS);
        assertThat(testCandidate.getTaxTermIds()).isEqualTo(UPDATED_TAX_TERM_IDS);
        assertThat(testCandidate.getNoticePeriodInDays()).isEqualTo(UPDATED_NOTICE_PERIOD_IN_DAYS);
        assertThat(testCandidate.getWorkAuthorizationExpiry()).isEqualTo(UPDATED_WORK_AUTHORIZATION_EXPIRY);
        assertThat(testCandidate.getGpa()).isEqualTo(UPDATED_GPA);
        assertThat(testCandidate.getAadharNumber()).isEqualTo(DEFAULT_AADHAR_NUMBER);
        assertThat(testCandidate.getLinkedInProfileURL()).isEqualTo(UPDATED_LINKED_IN_PROFILE_URL);
        assertThat(testCandidate.getOtherSocialURLs()).isEqualTo(UPDATED_OTHER_SOCIAL_UR_LS);
        assertThat(testCandidate.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testCandidate.getResumes()).isEqualTo(DEFAULT_RESUMES);
        assertThat(testCandidate.getRightToReperesent()).isEqualTo(UPDATED_RIGHT_TO_REPERESENT);
        assertThat(testCandidate.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testCandidate.getAltSkills()).isEqualTo(DEFAULT_ALT_SKILLS);
        assertThat(testCandidate.getTechnologies()).isEqualTo(UPDATED_TECHNOLOGIES);
        assertThat(testCandidate.getCertifications()).isEqualTo(DEFAULT_CERTIFICATIONS);
        assertThat(testCandidate.getLanguages()).isEqualTo(DEFAULT_LANGUAGES);
        assertThat(testCandidate.getWorkExperience()).isEqualTo(UPDATED_WORK_EXPERIENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testCandidate.getEmployerDetails()).isEqualTo(DEFAULT_EMPLOYER_DETAILS);
        assertThat(testCandidate.getDocuments()).isEqualTo(DEFAULT_DOCUMENTS);
    }

    @Test
    @Transactional
    void fullUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .altEmails(UPDATED_ALT_EMAILS)
            .phone(UPDATED_PHONE)
            .altPhones(UPDATED_ALT_PHONES)
            .dob(UPDATED_DOB)
            .nationality(UPDATED_NATIONALITY)
            .workAuthorizationId(UPDATED_WORK_AUTHORIZATION_ID)
            .address(UPDATED_ADDRESS)
            .area(UPDATED_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .stateCode(UPDATED_STATE_CODE)
            .county(UPDATED_COUNTY)
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .zipCode(UPDATED_ZIP_CODE)
            .source(UPDATED_SOURCE)
            .totalExpInYears(UPDATED_TOTAL_EXP_IN_YEARS)
            .totalExpInMonths(UPDATED_TOTAL_EXP_IN_MONTHS)
            .relevantExpInYears(UPDATED_RELEVANT_EXP_IN_YEARS)
            .relevantExpInMonths(UPDATED_RELEVANT_EXP_IN_MONTHS)
            .referredBy(UPDATED_REFERRED_BY)
            .ownershipUserId(UPDATED_OWNERSHIP_USER_ID)
            .currentJobTitle(UPDATED_CURRENT_JOB_TITLE)
            .currentEmployer(UPDATED_CURRENT_EMPLOYER)
            .currentJobTypeId(UPDATED_CURRENT_JOB_TYPE_ID)
            .currentPayCurrency(UPDATED_CURRENT_PAY_CURRENCY)
            .currentPay(UPDATED_CURRENT_PAY)
            .currentPayMonthly(UPDATED_CURRENT_PAY_MONTHLY)
            .currentPayHourly(UPDATED_CURRENT_PAY_HOURLY)
            .currentPayYearly(UPDATED_CURRENT_PAY_YEARLY)
            .currentPayTimeSpan(UPDATED_CURRENT_PAY_TIME_SPAN)
            .otherCurrentBenefits(UPDATED_OTHER_CURRENT_BENEFITS)
            .expectedPayCurrency(UPDATED_EXPECTED_PAY_CURRENCY)
            .expectedPayMin(UPDATED_EXPECTED_PAY_MIN)
            .expectedPayMax(UPDATED_EXPECTED_PAY_MAX)
            .expectedPayMinMonthly(UPDATED_EXPECTED_PAY_MIN_MONTHLY)
            .expectedPayMinHourly(UPDATED_EXPECTED_PAY_MIN_HOURLY)
            .expectedPayMinYearly(UPDATED_EXPECTED_PAY_MIN_YEARLY)
            .expectedPayMaxMonthly(UPDATED_EXPECTED_PAY_MAX_MONTHLY)
            .expectedPayMaxHourly(UPDATED_EXPECTED_PAY_MAX_HOURLY)
            .expectedPayMaxYearly(UPDATED_EXPECTED_PAY_MAX_YEARLY)
            .expectedPayTimeSpan(UPDATED_EXPECTED_PAY_TIME_SPAN)
            .expectedPayTaxTermId(UPDATED_EXPECTED_PAY_TAX_TERM_ID)
            .otherExpectedBenefits(UPDATED_OTHER_EXPECTED_BENEFITS)
            .additionalComments(UPDATED_ADDITIONAL_COMMENTS)
            .relocation(UPDATED_RELOCATION)
            .familyWillingToRelocate(UPDATED_FAMILY_WILLING_TO_RELOCATE)
            .relocationType(UPDATED_RELOCATION_TYPE)
            .relocationRemarks(UPDATED_RELOCATION_REMARKS)
            .taxTermIds(UPDATED_TAX_TERM_IDS)
            .noticePeriodInDays(UPDATED_NOTICE_PERIOD_IN_DAYS)
            .workAuthorizationExpiry(UPDATED_WORK_AUTHORIZATION_EXPIRY)
            .gpa(UPDATED_GPA)
            .aadharNumber(UPDATED_AADHAR_NUMBER)
            .linkedInProfileURL(UPDATED_LINKED_IN_PROFILE_URL)
            .otherSocialURLs(UPDATED_OTHER_SOCIAL_UR_LS)
            .tags(UPDATED_TAGS)
            .resumes(UPDATED_RESUMES)
            .rightToReperesent(UPDATED_RIGHT_TO_REPERESENT)
            .skills(UPDATED_SKILLS)
            .altSkills(UPDATED_ALT_SKILLS)
            .technologies(UPDATED_TECHNOLOGIES)
            .certifications(UPDATED_CERTIFICATIONS)
            .languages(UPDATED_LANGUAGES)
            .workExperience(UPDATED_WORK_EXPERIENCE)
            .education(UPDATED_EDUCATION)
            .employerDetails(UPDATED_EMPLOYER_DETAILS)
            .documents(UPDATED_DOCUMENTS);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testCandidate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidate.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidate.getAltEmails()).isEqualTo(UPDATED_ALT_EMAILS);
        assertThat(testCandidate.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCandidate.getAltPhones()).isEqualTo(UPDATED_ALT_PHONES);
        assertThat(testCandidate.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testCandidate.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testCandidate.getWorkAuthorizationId()).isEqualTo(UPDATED_WORK_AUTHORIZATION_ID);
        assertThat(testCandidate.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCandidate.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testCandidate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCandidate.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCandidate.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCandidate.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testCandidate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCandidate.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCandidate.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testCandidate.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCandidate.getTotalExpInYears()).isEqualTo(UPDATED_TOTAL_EXP_IN_YEARS);
        assertThat(testCandidate.getTotalExpInMonths()).isEqualTo(UPDATED_TOTAL_EXP_IN_MONTHS);
        assertThat(testCandidate.getRelevantExpInYears()).isEqualTo(UPDATED_RELEVANT_EXP_IN_YEARS);
        assertThat(testCandidate.getRelevantExpInMonths()).isEqualTo(UPDATED_RELEVANT_EXP_IN_MONTHS);
        assertThat(testCandidate.getReferredBy()).isEqualTo(UPDATED_REFERRED_BY);
        assertThat(testCandidate.getOwnershipUserId()).isEqualTo(UPDATED_OWNERSHIP_USER_ID);
        assertThat(testCandidate.getCurrentJobTitle()).isEqualTo(UPDATED_CURRENT_JOB_TITLE);
        assertThat(testCandidate.getCurrentEmployer()).isEqualTo(UPDATED_CURRENT_EMPLOYER);
        assertThat(testCandidate.getCurrentJobTypeId()).isEqualTo(UPDATED_CURRENT_JOB_TYPE_ID);
        assertThat(testCandidate.getCurrentPayCurrency()).isEqualTo(UPDATED_CURRENT_PAY_CURRENCY);
        assertThat(testCandidate.getCurrentPay()).isEqualByComparingTo(UPDATED_CURRENT_PAY);
        assertThat(testCandidate.getCurrentPayMonthly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_MONTHLY);
        assertThat(testCandidate.getCurrentPayHourly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_HOURLY);
        assertThat(testCandidate.getCurrentPayYearly()).isEqualByComparingTo(UPDATED_CURRENT_PAY_YEARLY);
        assertThat(testCandidate.getCurrentPayTimeSpan()).isEqualTo(UPDATED_CURRENT_PAY_TIME_SPAN);
        assertThat(testCandidate.getOtherCurrentBenefits()).isEqualTo(UPDATED_OTHER_CURRENT_BENEFITS);
        assertThat(testCandidate.getExpectedPayCurrency()).isEqualTo(UPDATED_EXPECTED_PAY_CURRENCY);
        assertThat(testCandidate.getExpectedPayMin()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN);
        assertThat(testCandidate.getExpectedPayMax()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX);
        assertThat(testCandidate.getExpectedPayMinMonthly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_MONTHLY);
        assertThat(testCandidate.getExpectedPayMinHourly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_HOURLY);
        assertThat(testCandidate.getExpectedPayMinYearly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MIN_YEARLY);
        assertThat(testCandidate.getExpectedPayMaxMonthly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX_MONTHLY);
        assertThat(testCandidate.getExpectedPayMaxHourly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX_HOURLY);
        assertThat(testCandidate.getExpectedPayMaxYearly()).isEqualByComparingTo(UPDATED_EXPECTED_PAY_MAX_YEARLY);
        assertThat(testCandidate.getExpectedPayTimeSpan()).isEqualTo(UPDATED_EXPECTED_PAY_TIME_SPAN);
        assertThat(testCandidate.getExpectedPayTaxTermId()).isEqualTo(UPDATED_EXPECTED_PAY_TAX_TERM_ID);
        assertThat(testCandidate.getOtherExpectedBenefits()).isEqualTo(UPDATED_OTHER_EXPECTED_BENEFITS);
        assertThat(testCandidate.getAdditionalComments()).isEqualTo(UPDATED_ADDITIONAL_COMMENTS);
        assertThat(testCandidate.getRelocation()).isEqualTo(UPDATED_RELOCATION);
        assertThat(testCandidate.getFamilyWillingToRelocate()).isEqualTo(UPDATED_FAMILY_WILLING_TO_RELOCATE);
        assertThat(testCandidate.getRelocationType()).isEqualTo(UPDATED_RELOCATION_TYPE);
        assertThat(testCandidate.getRelocationRemarks()).isEqualTo(UPDATED_RELOCATION_REMARKS);
        assertThat(testCandidate.getTaxTermIds()).isEqualTo(UPDATED_TAX_TERM_IDS);
        assertThat(testCandidate.getNoticePeriodInDays()).isEqualTo(UPDATED_NOTICE_PERIOD_IN_DAYS);
        assertThat(testCandidate.getWorkAuthorizationExpiry()).isEqualTo(UPDATED_WORK_AUTHORIZATION_EXPIRY);
        assertThat(testCandidate.getGpa()).isEqualTo(UPDATED_GPA);
        assertThat(testCandidate.getAadharNumber()).isEqualTo(UPDATED_AADHAR_NUMBER);
        assertThat(testCandidate.getLinkedInProfileURL()).isEqualTo(UPDATED_LINKED_IN_PROFILE_URL);
        assertThat(testCandidate.getOtherSocialURLs()).isEqualTo(UPDATED_OTHER_SOCIAL_UR_LS);
        assertThat(testCandidate.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testCandidate.getResumes()).isEqualTo(UPDATED_RESUMES);
        assertThat(testCandidate.getRightToReperesent()).isEqualTo(UPDATED_RIGHT_TO_REPERESENT);
        assertThat(testCandidate.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testCandidate.getAltSkills()).isEqualTo(UPDATED_ALT_SKILLS);
        assertThat(testCandidate.getTechnologies()).isEqualTo(UPDATED_TECHNOLOGIES);
        assertThat(testCandidate.getCertifications()).isEqualTo(UPDATED_CERTIFICATIONS);
        assertThat(testCandidate.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testCandidate.getWorkExperience()).isEqualTo(UPDATED_WORK_EXPERIENCE);
        assertThat(testCandidate.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testCandidate.getEmployerDetails()).isEqualTo(UPDATED_EMPLOYER_DETAILS);
        assertThat(testCandidate.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(candidate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Delete the candidate
        restCandidateMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
