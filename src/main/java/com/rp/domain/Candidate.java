package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rp.domain.enumeration.CandidateRelocationType;
import com.rp.domain.enumeration.SalaryTimeSpan;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "alt_emails")
    private String altEmails;

    @Column(name = "phone")
    private String phone;

    @Column(name = "alt_phones")
    private String altPhones;

    @Column(name = "dob")
    private ZonedDateTime dob;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "work_authorization_id")
    private Long workAuthorizationId;

    @Column(name = "address")
    private String address;

    @Column(name = "area")
    private String area;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "county")
    private String county;

    @Column(name = "country")
    private String country;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "source")
    private String source;

    @Column(name = "total_exp_in_years")
    private Double totalExpInYears;

    @Column(name = "total_exp_in_months")
    private Double totalExpInMonths;

    @Column(name = "relevant_exp_in_years")
    private Double relevantExpInYears;

    @Column(name = "relevant_exp_in_months")
    private Double relevantExpInMonths;

    @Column(name = "referred_by")
    private String referredBy;

    @Column(name = "ownership_user_id")
    private Long ownershipUserId;

    @Column(name = "current_job_title")
    private String currentJobTitle;

    @Column(name = "current_employer")
    private String currentEmployer;

    @Column(name = "current_job_type_id")
    private Long currentJobTypeId;

    @Column(name = "current_pay_currency")
    private String currentPayCurrency;

    @Column(name = "current_pay", precision = 21, scale = 2)
    private BigDecimal currentPay;

    @Column(name = "current_pay_monthly", precision = 21, scale = 2)
    private BigDecimal currentPayMonthly;

    @Column(name = "current_pay_hourly", precision = 21, scale = 2)
    private BigDecimal currentPayHourly;

    @Column(name = "current_pay_yearly", precision = 21, scale = 2)
    private BigDecimal currentPayYearly;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_pay_time_span")
    private SalaryTimeSpan currentPayTimeSpan;

    @Column(name = "other_current_benefits")
    private String otherCurrentBenefits;

    @Column(name = "expected_pay_currency")
    private String expectedPayCurrency;

    @Column(name = "expected_pay_min", precision = 21, scale = 2)
    private BigDecimal expectedPayMin;

    @Column(name = "expected_pay_max", precision = 21, scale = 2)
    private BigDecimal expectedPayMax;

    @Column(name = "expected_pay_min_monthly", precision = 21, scale = 2)
    private BigDecimal expectedPayMinMonthly;

    @Column(name = "expected_pay_min_hourly", precision = 21, scale = 2)
    private BigDecimal expectedPayMinHourly;

    @Column(name = "expected_pay_min_yearly", precision = 21, scale = 2)
    private BigDecimal expectedPayMinYearly;

    @Column(name = "expected_pay_max_monthly", precision = 21, scale = 2)
    private BigDecimal expectedPayMaxMonthly;

    @Column(name = "expected_pay_max_hourly", precision = 21, scale = 2)
    private BigDecimal expectedPayMaxHourly;

    @Column(name = "expected_pay_max_yearly", precision = 21, scale = 2)
    private BigDecimal expectedPayMaxYearly;

    @Enumerated(EnumType.STRING)
    @Column(name = "expected_pay_time_span")
    private SalaryTimeSpan expectedPayTimeSpan;

    @Column(name = "expected_pay_tax_term_id")
    private Long expectedPayTaxTermId;

    @Column(name = "other_expected_benefits")
    private String otherExpectedBenefits;

    @Column(name = "additional_comments")
    private String additionalComments;

    @Column(name = "relocation")
    private Boolean relocation;

    @Column(name = "family_willing_to_relocate")
    private Boolean familyWillingToRelocate;

    @Enumerated(EnumType.STRING)
    @Column(name = "relocation_type")
    private CandidateRelocationType relocationType;

    @Column(name = "relocation_remarks")
    private String relocationRemarks;

    @Column(name = "tax_term_ids")
    private String taxTermIds;

    @Column(name = "notice_period_in_days")
    private Integer noticePeriodInDays;

    @Column(name = "work_authorization_expiry")
    private ZonedDateTime workAuthorizationExpiry;

    @Column(name = "gpa")
    private String gpa;

    @Column(name = "aadhar_number")
    private String aadharNumber;

    @Column(name = "linked_in_profile_url")
    private String linkedInProfileURL;

    @Column(name = "other_social_ur_ls")
    private String otherSocialURLs;

    @Column(name = "tags")
    private String tags;

    @Column(name = "resumes")
    private String resumes;

    @Column(name = "right_to_reperesent")
    private String rightToReperesent;

    @Column(name = "skills")
    private String skills;

    @Column(name = "alt_skills")
    private String altSkills;

    @Column(name = "technologies")
    private String technologies;

    @Column(name = "certifications")
    private String certifications;

    @Column(name = "languages")
    private String languages;

    @Column(name = "work_experience")
    private String workExperience;

    @Column(name = "education")
    private String education;

    @Column(name = "employer_details")
    private String employerDetails;

    @Column(name = "documents")
    private String documents;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue workAuthorization;

    @OneToOne
    @JoinColumn(unique = true)
    private RUser ownershipUser;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue currentJobType;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue expectedPayTaxTerm;

    @OneToMany(mappedBy = "candidateId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidateId" }, allowSetters = true)
    private Set<CandidateRelocationPreference> candidateRelocationPreferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Candidate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalutation() {
        return this.salutation;
    }

    public Candidate salutation(String salutation) {
        this.setSalutation(salutation);
        return this;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Candidate firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Candidate middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Candidate lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Candidate email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAltEmails() {
        return this.altEmails;
    }

    public Candidate altEmails(String altEmails) {
        this.setAltEmails(altEmails);
        return this;
    }

    public void setAltEmails(String altEmails) {
        this.altEmails = altEmails;
    }

    public String getPhone() {
        return this.phone;
    }

    public Candidate phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAltPhones() {
        return this.altPhones;
    }

    public Candidate altPhones(String altPhones) {
        this.setAltPhones(altPhones);
        return this;
    }

    public void setAltPhones(String altPhones) {
        this.altPhones = altPhones;
    }

    public ZonedDateTime getDob() {
        return this.dob;
    }

    public Candidate dob(ZonedDateTime dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Candidate nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getWorkAuthorizationId() {
        return this.workAuthorizationId;
    }

    public Candidate workAuthorizationId(Long workAuthorizationId) {
        this.setWorkAuthorizationId(workAuthorizationId);
        return this;
    }

    public void setWorkAuthorizationId(Long workAuthorizationId) {
        this.workAuthorizationId = workAuthorizationId;
    }

    public String getAddress() {
        return this.address;
    }

    public Candidate address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return this.area;
    }

    public Candidate area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return this.city;
    }

    public Candidate city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Candidate state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public Candidate stateCode(String stateCode) {
        this.setStateCode(stateCode);
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCounty() {
        return this.county;
    }

    public Candidate county(String county) {
        this.setCounty(county);
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return this.country;
    }

    public Candidate country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Candidate countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Candidate zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getSource() {
        return this.source;
    }

    public Candidate source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getTotalExpInYears() {
        return this.totalExpInYears;
    }

    public Candidate totalExpInYears(Double totalExpInYears) {
        this.setTotalExpInYears(totalExpInYears);
        return this;
    }

    public void setTotalExpInYears(Double totalExpInYears) {
        this.totalExpInYears = totalExpInYears;
    }

    public Double getTotalExpInMonths() {
        return this.totalExpInMonths;
    }

    public Candidate totalExpInMonths(Double totalExpInMonths) {
        this.setTotalExpInMonths(totalExpInMonths);
        return this;
    }

    public void setTotalExpInMonths(Double totalExpInMonths) {
        this.totalExpInMonths = totalExpInMonths;
    }

    public Double getRelevantExpInYears() {
        return this.relevantExpInYears;
    }

    public Candidate relevantExpInYears(Double relevantExpInYears) {
        this.setRelevantExpInYears(relevantExpInYears);
        return this;
    }

    public void setRelevantExpInYears(Double relevantExpInYears) {
        this.relevantExpInYears = relevantExpInYears;
    }

    public Double getRelevantExpInMonths() {
        return this.relevantExpInMonths;
    }

    public Candidate relevantExpInMonths(Double relevantExpInMonths) {
        this.setRelevantExpInMonths(relevantExpInMonths);
        return this;
    }

    public void setRelevantExpInMonths(Double relevantExpInMonths) {
        this.relevantExpInMonths = relevantExpInMonths;
    }

    public String getReferredBy() {
        return this.referredBy;
    }

    public Candidate referredBy(String referredBy) {
        this.setReferredBy(referredBy);
        return this;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public Long getOwnershipUserId() {
        return this.ownershipUserId;
    }

    public Candidate ownershipUserId(Long ownershipUserId) {
        this.setOwnershipUserId(ownershipUserId);
        return this;
    }

    public void setOwnershipUserId(Long ownershipUserId) {
        this.ownershipUserId = ownershipUserId;
    }

    public String getCurrentJobTitle() {
        return this.currentJobTitle;
    }

    public Candidate currentJobTitle(String currentJobTitle) {
        this.setCurrentJobTitle(currentJobTitle);
        return this;
    }

    public void setCurrentJobTitle(String currentJobTitle) {
        this.currentJobTitle = currentJobTitle;
    }

    public String getCurrentEmployer() {
        return this.currentEmployer;
    }

    public Candidate currentEmployer(String currentEmployer) {
        this.setCurrentEmployer(currentEmployer);
        return this;
    }

    public void setCurrentEmployer(String currentEmployer) {
        this.currentEmployer = currentEmployer;
    }

    public Long getCurrentJobTypeId() {
        return this.currentJobTypeId;
    }

    public Candidate currentJobTypeId(Long currentJobTypeId) {
        this.setCurrentJobTypeId(currentJobTypeId);
        return this;
    }

    public void setCurrentJobTypeId(Long currentJobTypeId) {
        this.currentJobTypeId = currentJobTypeId;
    }

    public String getCurrentPayCurrency() {
        return this.currentPayCurrency;
    }

    public Candidate currentPayCurrency(String currentPayCurrency) {
        this.setCurrentPayCurrency(currentPayCurrency);
        return this;
    }

    public void setCurrentPayCurrency(String currentPayCurrency) {
        this.currentPayCurrency = currentPayCurrency;
    }

    public BigDecimal getCurrentPay() {
        return this.currentPay;
    }

    public Candidate currentPay(BigDecimal currentPay) {
        this.setCurrentPay(currentPay);
        return this;
    }

    public void setCurrentPay(BigDecimal currentPay) {
        this.currentPay = currentPay;
    }

    public BigDecimal getCurrentPayMonthly() {
        return this.currentPayMonthly;
    }

    public Candidate currentPayMonthly(BigDecimal currentPayMonthly) {
        this.setCurrentPayMonthly(currentPayMonthly);
        return this;
    }

    public void setCurrentPayMonthly(BigDecimal currentPayMonthly) {
        this.currentPayMonthly = currentPayMonthly;
    }

    public BigDecimal getCurrentPayHourly() {
        return this.currentPayHourly;
    }

    public Candidate currentPayHourly(BigDecimal currentPayHourly) {
        this.setCurrentPayHourly(currentPayHourly);
        return this;
    }

    public void setCurrentPayHourly(BigDecimal currentPayHourly) {
        this.currentPayHourly = currentPayHourly;
    }

    public BigDecimal getCurrentPayYearly() {
        return this.currentPayYearly;
    }

    public Candidate currentPayYearly(BigDecimal currentPayYearly) {
        this.setCurrentPayYearly(currentPayYearly);
        return this;
    }

    public void setCurrentPayYearly(BigDecimal currentPayYearly) {
        this.currentPayYearly = currentPayYearly;
    }

    public SalaryTimeSpan getCurrentPayTimeSpan() {
        return this.currentPayTimeSpan;
    }

    public Candidate currentPayTimeSpan(SalaryTimeSpan currentPayTimeSpan) {
        this.setCurrentPayTimeSpan(currentPayTimeSpan);
        return this;
    }

    public void setCurrentPayTimeSpan(SalaryTimeSpan currentPayTimeSpan) {
        this.currentPayTimeSpan = currentPayTimeSpan;
    }

    public String getOtherCurrentBenefits() {
        return this.otherCurrentBenefits;
    }

    public Candidate otherCurrentBenefits(String otherCurrentBenefits) {
        this.setOtherCurrentBenefits(otherCurrentBenefits);
        return this;
    }

    public void setOtherCurrentBenefits(String otherCurrentBenefits) {
        this.otherCurrentBenefits = otherCurrentBenefits;
    }

    public String getExpectedPayCurrency() {
        return this.expectedPayCurrency;
    }

    public Candidate expectedPayCurrency(String expectedPayCurrency) {
        this.setExpectedPayCurrency(expectedPayCurrency);
        return this;
    }

    public void setExpectedPayCurrency(String expectedPayCurrency) {
        this.expectedPayCurrency = expectedPayCurrency;
    }

    public BigDecimal getExpectedPayMin() {
        return this.expectedPayMin;
    }

    public Candidate expectedPayMin(BigDecimal expectedPayMin) {
        this.setExpectedPayMin(expectedPayMin);
        return this;
    }

    public void setExpectedPayMin(BigDecimal expectedPayMin) {
        this.expectedPayMin = expectedPayMin;
    }

    public BigDecimal getExpectedPayMax() {
        return this.expectedPayMax;
    }

    public Candidate expectedPayMax(BigDecimal expectedPayMax) {
        this.setExpectedPayMax(expectedPayMax);
        return this;
    }

    public void setExpectedPayMax(BigDecimal expectedPayMax) {
        this.expectedPayMax = expectedPayMax;
    }

    public BigDecimal getExpectedPayMinMonthly() {
        return this.expectedPayMinMonthly;
    }

    public Candidate expectedPayMinMonthly(BigDecimal expectedPayMinMonthly) {
        this.setExpectedPayMinMonthly(expectedPayMinMonthly);
        return this;
    }

    public void setExpectedPayMinMonthly(BigDecimal expectedPayMinMonthly) {
        this.expectedPayMinMonthly = expectedPayMinMonthly;
    }

    public BigDecimal getExpectedPayMinHourly() {
        return this.expectedPayMinHourly;
    }

    public Candidate expectedPayMinHourly(BigDecimal expectedPayMinHourly) {
        this.setExpectedPayMinHourly(expectedPayMinHourly);
        return this;
    }

    public void setExpectedPayMinHourly(BigDecimal expectedPayMinHourly) {
        this.expectedPayMinHourly = expectedPayMinHourly;
    }

    public BigDecimal getExpectedPayMinYearly() {
        return this.expectedPayMinYearly;
    }

    public Candidate expectedPayMinYearly(BigDecimal expectedPayMinYearly) {
        this.setExpectedPayMinYearly(expectedPayMinYearly);
        return this;
    }

    public void setExpectedPayMinYearly(BigDecimal expectedPayMinYearly) {
        this.expectedPayMinYearly = expectedPayMinYearly;
    }

    public BigDecimal getExpectedPayMaxMonthly() {
        return this.expectedPayMaxMonthly;
    }

    public Candidate expectedPayMaxMonthly(BigDecimal expectedPayMaxMonthly) {
        this.setExpectedPayMaxMonthly(expectedPayMaxMonthly);
        return this;
    }

    public void setExpectedPayMaxMonthly(BigDecimal expectedPayMaxMonthly) {
        this.expectedPayMaxMonthly = expectedPayMaxMonthly;
    }

    public BigDecimal getExpectedPayMaxHourly() {
        return this.expectedPayMaxHourly;
    }

    public Candidate expectedPayMaxHourly(BigDecimal expectedPayMaxHourly) {
        this.setExpectedPayMaxHourly(expectedPayMaxHourly);
        return this;
    }

    public void setExpectedPayMaxHourly(BigDecimal expectedPayMaxHourly) {
        this.expectedPayMaxHourly = expectedPayMaxHourly;
    }

    public BigDecimal getExpectedPayMaxYearly() {
        return this.expectedPayMaxYearly;
    }

    public Candidate expectedPayMaxYearly(BigDecimal expectedPayMaxYearly) {
        this.setExpectedPayMaxYearly(expectedPayMaxYearly);
        return this;
    }

    public void setExpectedPayMaxYearly(BigDecimal expectedPayMaxYearly) {
        this.expectedPayMaxYearly = expectedPayMaxYearly;
    }

    public SalaryTimeSpan getExpectedPayTimeSpan() {
        return this.expectedPayTimeSpan;
    }

    public Candidate expectedPayTimeSpan(SalaryTimeSpan expectedPayTimeSpan) {
        this.setExpectedPayTimeSpan(expectedPayTimeSpan);
        return this;
    }

    public void setExpectedPayTimeSpan(SalaryTimeSpan expectedPayTimeSpan) {
        this.expectedPayTimeSpan = expectedPayTimeSpan;
    }

    public Long getExpectedPayTaxTermId() {
        return this.expectedPayTaxTermId;
    }

    public Candidate expectedPayTaxTermId(Long expectedPayTaxTermId) {
        this.setExpectedPayTaxTermId(expectedPayTaxTermId);
        return this;
    }

    public void setExpectedPayTaxTermId(Long expectedPayTaxTermId) {
        this.expectedPayTaxTermId = expectedPayTaxTermId;
    }

    public String getOtherExpectedBenefits() {
        return this.otherExpectedBenefits;
    }

    public Candidate otherExpectedBenefits(String otherExpectedBenefits) {
        this.setOtherExpectedBenefits(otherExpectedBenefits);
        return this;
    }

    public void setOtherExpectedBenefits(String otherExpectedBenefits) {
        this.otherExpectedBenefits = otherExpectedBenefits;
    }

    public String getAdditionalComments() {
        return this.additionalComments;
    }

    public Candidate additionalComments(String additionalComments) {
        this.setAdditionalComments(additionalComments);
        return this;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public Boolean getRelocation() {
        return this.relocation;
    }

    public Candidate relocation(Boolean relocation) {
        this.setRelocation(relocation);
        return this;
    }

    public void setRelocation(Boolean relocation) {
        this.relocation = relocation;
    }

    public Boolean getFamilyWillingToRelocate() {
        return this.familyWillingToRelocate;
    }

    public Candidate familyWillingToRelocate(Boolean familyWillingToRelocate) {
        this.setFamilyWillingToRelocate(familyWillingToRelocate);
        return this;
    }

    public void setFamilyWillingToRelocate(Boolean familyWillingToRelocate) {
        this.familyWillingToRelocate = familyWillingToRelocate;
    }

    public CandidateRelocationType getRelocationType() {
        return this.relocationType;
    }

    public Candidate relocationType(CandidateRelocationType relocationType) {
        this.setRelocationType(relocationType);
        return this;
    }

    public void setRelocationType(CandidateRelocationType relocationType) {
        this.relocationType = relocationType;
    }

    public String getRelocationRemarks() {
        return this.relocationRemarks;
    }

    public Candidate relocationRemarks(String relocationRemarks) {
        this.setRelocationRemarks(relocationRemarks);
        return this;
    }

    public void setRelocationRemarks(String relocationRemarks) {
        this.relocationRemarks = relocationRemarks;
    }

    public String getTaxTermIds() {
        return this.taxTermIds;
    }

    public Candidate taxTermIds(String taxTermIds) {
        this.setTaxTermIds(taxTermIds);
        return this;
    }

    public void setTaxTermIds(String taxTermIds) {
        this.taxTermIds = taxTermIds;
    }

    public Integer getNoticePeriodInDays() {
        return this.noticePeriodInDays;
    }

    public Candidate noticePeriodInDays(Integer noticePeriodInDays) {
        this.setNoticePeriodInDays(noticePeriodInDays);
        return this;
    }

    public void setNoticePeriodInDays(Integer noticePeriodInDays) {
        this.noticePeriodInDays = noticePeriodInDays;
    }

    public ZonedDateTime getWorkAuthorizationExpiry() {
        return this.workAuthorizationExpiry;
    }

    public Candidate workAuthorizationExpiry(ZonedDateTime workAuthorizationExpiry) {
        this.setWorkAuthorizationExpiry(workAuthorizationExpiry);
        return this;
    }

    public void setWorkAuthorizationExpiry(ZonedDateTime workAuthorizationExpiry) {
        this.workAuthorizationExpiry = workAuthorizationExpiry;
    }

    public String getGpa() {
        return this.gpa;
    }

    public Candidate gpa(String gpa) {
        this.setGpa(gpa);
        return this;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getAadharNumber() {
        return this.aadharNumber;
    }

    public Candidate aadharNumber(String aadharNumber) {
        this.setAadharNumber(aadharNumber);
        return this;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getLinkedInProfileURL() {
        return this.linkedInProfileURL;
    }

    public Candidate linkedInProfileURL(String linkedInProfileURL) {
        this.setLinkedInProfileURL(linkedInProfileURL);
        return this;
    }

    public void setLinkedInProfileURL(String linkedInProfileURL) {
        this.linkedInProfileURL = linkedInProfileURL;
    }

    public String getOtherSocialURLs() {
        return this.otherSocialURLs;
    }

    public Candidate otherSocialURLs(String otherSocialURLs) {
        this.setOtherSocialURLs(otherSocialURLs);
        return this;
    }

    public void setOtherSocialURLs(String otherSocialURLs) {
        this.otherSocialURLs = otherSocialURLs;
    }

    public String getTags() {
        return this.tags;
    }

    public Candidate tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getResumes() {
        return this.resumes;
    }

    public Candidate resumes(String resumes) {
        this.setResumes(resumes);
        return this;
    }

    public void setResumes(String resumes) {
        this.resumes = resumes;
    }

    public String getRightToReperesent() {
        return this.rightToReperesent;
    }

    public Candidate rightToReperesent(String rightToReperesent) {
        this.setRightToReperesent(rightToReperesent);
        return this;
    }

    public void setRightToReperesent(String rightToReperesent) {
        this.rightToReperesent = rightToReperesent;
    }

    public String getSkills() {
        return this.skills;
    }

    public Candidate skills(String skills) {
        this.setSkills(skills);
        return this;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAltSkills() {
        return this.altSkills;
    }

    public Candidate altSkills(String altSkills) {
        this.setAltSkills(altSkills);
        return this;
    }

    public void setAltSkills(String altSkills) {
        this.altSkills = altSkills;
    }

    public String getTechnologies() {
        return this.technologies;
    }

    public Candidate technologies(String technologies) {
        this.setTechnologies(technologies);
        return this;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getCertifications() {
        return this.certifications;
    }

    public Candidate certifications(String certifications) {
        this.setCertifications(certifications);
        return this;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getLanguages() {
        return this.languages;
    }

    public Candidate languages(String languages) {
        this.setLanguages(languages);
        return this;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getWorkExperience() {
        return this.workExperience;
    }

    public Candidate workExperience(String workExperience) {
        this.setWorkExperience(workExperience);
        return this;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getEducation() {
        return this.education;
    }

    public Candidate education(String education) {
        this.setEducation(education);
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmployerDetails() {
        return this.employerDetails;
    }

    public Candidate employerDetails(String employerDetails) {
        this.setEmployerDetails(employerDetails);
        return this;
    }

    public void setEmployerDetails(String employerDetails) {
        this.employerDetails = employerDetails;
    }

    public String getDocuments() {
        return this.documents;
    }

    public Candidate documents(String documents) {
        this.setDocuments(documents);
        return this;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public CatalogueValue getWorkAuthorization() {
        return this.workAuthorization;
    }

    public void setWorkAuthorization(CatalogueValue catalogueValue) {
        this.workAuthorization = catalogueValue;
    }

    public Candidate workAuthorization(CatalogueValue catalogueValue) {
        this.setWorkAuthorization(catalogueValue);
        return this;
    }

    public RUser getOwnershipUser() {
        return this.ownershipUser;
    }

    public void setOwnershipUser(RUser rUser) {
        this.ownershipUser = rUser;
    }

    public Candidate ownershipUser(RUser rUser) {
        this.setOwnershipUser(rUser);
        return this;
    }

    public CatalogueValue getCurrentJobType() {
        return this.currentJobType;
    }

    public void setCurrentJobType(CatalogueValue catalogueValue) {
        this.currentJobType = catalogueValue;
    }

    public Candidate currentJobType(CatalogueValue catalogueValue) {
        this.setCurrentJobType(catalogueValue);
        return this;
    }

    public CatalogueValue getExpectedPayTaxTerm() {
        return this.expectedPayTaxTerm;
    }

    public void setExpectedPayTaxTerm(CatalogueValue catalogueValue) {
        this.expectedPayTaxTerm = catalogueValue;
    }

    public Candidate expectedPayTaxTerm(CatalogueValue catalogueValue) {
        this.setExpectedPayTaxTerm(catalogueValue);
        return this;
    }

    public Set<CandidateRelocationPreference> getCandidateRelocationPreferences() {
        return this.candidateRelocationPreferences;
    }

    public void setCandidateRelocationPreferences(Set<CandidateRelocationPreference> candidateRelocationPreferences) {
        if (this.candidateRelocationPreferences != null) {
            this.candidateRelocationPreferences.forEach(i -> i.setCandidateId(null));
        }
        if (candidateRelocationPreferences != null) {
            candidateRelocationPreferences.forEach(i -> i.setCandidateId(this));
        }
        this.candidateRelocationPreferences = candidateRelocationPreferences;
    }

    public Candidate candidateRelocationPreferences(Set<CandidateRelocationPreference> candidateRelocationPreferences) {
        this.setCandidateRelocationPreferences(candidateRelocationPreferences);
        return this;
    }

    public Candidate addCandidateRelocationPreference(CandidateRelocationPreference candidateRelocationPreference) {
        this.candidateRelocationPreferences.add(candidateRelocationPreference);
        candidateRelocationPreference.setCandidateId(this);
        return this;
    }

    public Candidate removeCandidateRelocationPreference(CandidateRelocationPreference candidateRelocationPreference) {
        this.candidateRelocationPreferences.remove(candidateRelocationPreference);
        candidateRelocationPreference.setCandidateId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return id != null && id.equals(((Candidate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", salutation='" + getSalutation() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", altEmails='" + getAltEmails() + "'" +
            ", phone='" + getPhone() + "'" +
            ", altPhones='" + getAltPhones() + "'" +
            ", dob='" + getDob() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", workAuthorizationId=" + getWorkAuthorizationId() +
            ", address='" + getAddress() + "'" +
            ", area='" + getArea() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            ", county='" + getCounty() + "'" +
            ", country='" + getCountry() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", source='" + getSource() + "'" +
            ", totalExpInYears=" + getTotalExpInYears() +
            ", totalExpInMonths=" + getTotalExpInMonths() +
            ", relevantExpInYears=" + getRelevantExpInYears() +
            ", relevantExpInMonths=" + getRelevantExpInMonths() +
            ", referredBy='" + getReferredBy() + "'" +
            ", ownershipUserId=" + getOwnershipUserId() +
            ", currentJobTitle='" + getCurrentJobTitle() + "'" +
            ", currentEmployer='" + getCurrentEmployer() + "'" +
            ", currentJobTypeId=" + getCurrentJobTypeId() +
            ", currentPayCurrency='" + getCurrentPayCurrency() + "'" +
            ", currentPay=" + getCurrentPay() +
            ", currentPayMonthly=" + getCurrentPayMonthly() +
            ", currentPayHourly=" + getCurrentPayHourly() +
            ", currentPayYearly=" + getCurrentPayYearly() +
            ", currentPayTimeSpan='" + getCurrentPayTimeSpan() + "'" +
            ", otherCurrentBenefits='" + getOtherCurrentBenefits() + "'" +
            ", expectedPayCurrency='" + getExpectedPayCurrency() + "'" +
            ", expectedPayMin=" + getExpectedPayMin() +
            ", expectedPayMax=" + getExpectedPayMax() +
            ", expectedPayMinMonthly=" + getExpectedPayMinMonthly() +
            ", expectedPayMinHourly=" + getExpectedPayMinHourly() +
            ", expectedPayMinYearly=" + getExpectedPayMinYearly() +
            ", expectedPayMaxMonthly=" + getExpectedPayMaxMonthly() +
            ", expectedPayMaxHourly=" + getExpectedPayMaxHourly() +
            ", expectedPayMaxYearly=" + getExpectedPayMaxYearly() +
            ", expectedPayTimeSpan='" + getExpectedPayTimeSpan() + "'" +
            ", expectedPayTaxTermId=" + getExpectedPayTaxTermId() +
            ", otherExpectedBenefits='" + getOtherExpectedBenefits() + "'" +
            ", additionalComments='" + getAdditionalComments() + "'" +
            ", relocation='" + getRelocation() + "'" +
            ", familyWillingToRelocate='" + getFamilyWillingToRelocate() + "'" +
            ", relocationType='" + getRelocationType() + "'" +
            ", relocationRemarks='" + getRelocationRemarks() + "'" +
            ", taxTermIds='" + getTaxTermIds() + "'" +
            ", noticePeriodInDays=" + getNoticePeriodInDays() +
            ", workAuthorizationExpiry='" + getWorkAuthorizationExpiry() + "'" +
            ", gpa='" + getGpa() + "'" +
            ", aadharNumber='" + getAadharNumber() + "'" +
            ", linkedInProfileURL='" + getLinkedInProfileURL() + "'" +
            ", otherSocialURLs='" + getOtherSocialURLs() + "'" +
            ", tags='" + getTags() + "'" +
            ", resumes='" + getResumes() + "'" +
            ", rightToReperesent='" + getRightToReperesent() + "'" +
            ", skills='" + getSkills() + "'" +
            ", altSkills='" + getAltSkills() + "'" +
            ", technologies='" + getTechnologies() + "'" +
            ", certifications='" + getCertifications() + "'" +
            ", languages='" + getLanguages() + "'" +
            ", workExperience='" + getWorkExperience() + "'" +
            ", education='" + getEducation() + "'" +
            ", employerDetails='" + getEmployerDetails() + "'" +
            ", documents='" + getDocuments() + "'" +
            "}";
    }
}
