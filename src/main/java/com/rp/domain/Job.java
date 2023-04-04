package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rp.domain.enumeration.JobApplicationForm;
import com.rp.domain.enumeration.JobStatus;
import com.rp.domain.enumeration.RemoteJob;
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
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "job_code")
    private String jobCode;

    @Column(name = "client_job_code")
    private String clientJobCode;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_employment_type_ids")
    private String orgEmploymentTypeIds;

    @Column(name = "job_ref")
    private String jobRef;

    @Column(name = "job_source")
    private String jobSource;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "public_job_title")
    private String publicJobTitle;

    @Column(name = "public_job_description")
    private String publicJobDescription;

    @Column(name = "auto_close_date")
    private ZonedDateTime autoCloseDate;

    @Column(name = "no_of_positions")
    private Integer noOfPositions;

    @Column(name = "department_alt_text")
    private String departmentAltText;

    @Column(name = "display_cand_rate")
    private Boolean displayCandRate;

    @Column(name = "cand_rate_currency")
    private String candRateCurrency;

    @Column(name = "cand_min_rate", precision = 21, scale = 2)
    private BigDecimal candMinRate;

    @Column(name = "cand_max_rate", precision = 21, scale = 2)
    private BigDecimal candMaxRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "cand_rate_time_span")
    private SalaryTimeSpan candRateTimeSpan;

    @Column(name = "cand_rate_tax_term")
    private String candRateTaxTerm;

    @Column(name = "cand_salary_alt_display_text")
    private String candSalaryAltDisplayText;

    @Column(name = "other_benefit_details")
    private String otherBenefitDetails;

    @Column(name = "client_bill_rate_currency")
    private String clientBillRateCurrency;

    @Column(name = "client_bill_rate", precision = 21, scale = 2)
    private BigDecimal clientBillRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_bill_rate_time_span")
    private SalaryTimeSpan clientBillRateTimeSpan;

    @Column(name = "client_bill_rate_tax_term")
    private String clientBillRateTaxTerm;

    @Column(name = "work_duration")
    private String workDuration;

    @Column(name = "immigration_status")
    private String immigrationStatus;

    @Column(name = "display_immigration_status")
    private String displayImmigrationStatus;

    @Column(name = "skills")
    private String skills;

    @Column(name = "alt_skills")
    private String altSkills;

    @Column(name = "tags")
    private String tags;

    @Column(name = "qualification_ids")
    private String qualificationIds;

    @Column(name = "qualifications_alt_text")
    private String qualificationsAltText;

    @Column(name = "edu_requirements")
    private String eduRequirements;

    @Column(name = "exp_requirements")
    private String expRequirements;

    @Column(name = "exp_alt_text")
    private String expAltText;

    @Column(name = "min_exp_in_years")
    private Double minExpInYears;

    @Column(name = "max_exp_in_years")
    private Double maxExpInYears;

    @Column(name = "language_ids")
    private String languageIds;

    @Column(name = "visa_requirements")
    private String visaRequirements;

    @Column(name = "work_authorization_ids")
    private String workAuthorizationIds;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_form_type")
    private JobApplicationForm applicationFormType;

    @Column(name = "is_partner_job")
    private Boolean isPartnerJob;

    @Column(name = "redirection_url")
    private String redirectionUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_status")
    private JobStatus jobStatus;

    @Column(name = "end_client")
    private String endClient;

    @Column(name = "domain_alt")
    private String domainAlt;

    @Column(name = "comments")
    private String comments;

    @Column(name = "additional_notifications_to_user_ids")
    private String additionalNotificationsToUserIds;

    @Column(name = "additional_notifications_to_team_ids")
    private String additionalNotificationsToTeamIds;

    @Column(name = "required_document_ids")
    private String requiredDocumentIds;

    @Column(name = "job_close_reason_other_alt")
    private String jobCloseReasonOtherAlt;

    @Column(name = "add_to_career_page")
    private Boolean addToCareerPage;

    @Enumerated(EnumType.STRING)
    @Column(name = "remote_job")
    private RemoteJob remoteJob;

    @Column(name = "hiring_for")
    private String hiringFor;

    @Column(name = "work_duration_time_span")
    private String workDurationTimeSpan;

    @Column(name = "tax_terms")
    private String taxTerms;

    @JsonIgnoreProperties(value = { "teamMembers", "workspace" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team postedByTeam;

    @OneToOne
    @JoinColumn(unique = true)
    private BusinessUnit businessUnit;

    @JsonIgnoreProperties(
        value = {
            "primaryBusinessUnit",
            "industry",
            "category",
            "paymentTerms",
            "practice",
            "primaryOwnerUser",
            "clientAccounts",
            "clientNotes",
            "clientDocuments",
            "contacts",
            "clientGuidelineSubmissionDocuments",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue jobType;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue industry;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue department;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser clientRecruiter;

    @JsonIgnoreProperties(value = { "primaryOwnerUser", "client" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Contact clientManager;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser accountManager;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser headOfRecruitment;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser deliveryLeadManager;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue domain;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser srDeliveryManager;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser teamLead;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue priority;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue jobCloseReason;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue jobCategory;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue jobOccupation;

    @OneToMany(mappedBy = "job")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "job" }, allowSetters = true)
    private Set<JobLocation> jobLocations = new HashSet<>();

    @OneToMany(mappedBy = "job")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "job" }, allowSetters = true)
    private Set<JobDocument> jobDocuments = new HashSet<>();

    @OneToMany(mappedBy = "job")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "job" }, allowSetters = true)
    private Set<JobCustomAttribute> jobCustomAttributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Job id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Job title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobCode() {
        return this.jobCode;
    }

    public Job jobCode(String jobCode) {
        this.setJobCode(jobCode);
        return this;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getClientJobCode() {
        return this.clientJobCode;
    }

    public Job clientJobCode(String clientJobCode) {
        this.setClientJobCode(clientJobCode);
        return this;
    }

    public void setClientJobCode(String clientJobCode) {
        this.clientJobCode = clientJobCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public Job orgName(String orgName) {
        this.setOrgName(orgName);
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgEmploymentTypeIds() {
        return this.orgEmploymentTypeIds;
    }

    public Job orgEmploymentTypeIds(String orgEmploymentTypeIds) {
        this.setOrgEmploymentTypeIds(orgEmploymentTypeIds);
        return this;
    }

    public void setOrgEmploymentTypeIds(String orgEmploymentTypeIds) {
        this.orgEmploymentTypeIds = orgEmploymentTypeIds;
    }

    public String getJobRef() {
        return this.jobRef;
    }

    public Job jobRef(String jobRef) {
        this.setJobRef(jobRef);
        return this;
    }

    public void setJobRef(String jobRef) {
        this.jobRef = jobRef;
    }

    public String getJobSource() {
        return this.jobSource;
    }

    public Job jobSource(String jobSource) {
        this.setJobSource(jobSource);
        return this;
    }

    public void setJobSource(String jobSource) {
        this.jobSource = jobSource;
    }

    public String getUrl() {
        return this.url;
    }

    public Job url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return this.description;
    }

    public Job description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicJobTitle() {
        return this.publicJobTitle;
    }

    public Job publicJobTitle(String publicJobTitle) {
        this.setPublicJobTitle(publicJobTitle);
        return this;
    }

    public void setPublicJobTitle(String publicJobTitle) {
        this.publicJobTitle = publicJobTitle;
    }

    public String getPublicJobDescription() {
        return this.publicJobDescription;
    }

    public Job publicJobDescription(String publicJobDescription) {
        this.setPublicJobDescription(publicJobDescription);
        return this;
    }

    public void setPublicJobDescription(String publicJobDescription) {
        this.publicJobDescription = publicJobDescription;
    }

    public ZonedDateTime getAutoCloseDate() {
        return this.autoCloseDate;
    }

    public Job autoCloseDate(ZonedDateTime autoCloseDate) {
        this.setAutoCloseDate(autoCloseDate);
        return this;
    }

    public void setAutoCloseDate(ZonedDateTime autoCloseDate) {
        this.autoCloseDate = autoCloseDate;
    }

    public Integer getNoOfPositions() {
        return this.noOfPositions;
    }

    public Job noOfPositions(Integer noOfPositions) {
        this.setNoOfPositions(noOfPositions);
        return this;
    }

    public void setNoOfPositions(Integer noOfPositions) {
        this.noOfPositions = noOfPositions;
    }

    public String getDepartmentAltText() {
        return this.departmentAltText;
    }

    public Job departmentAltText(String departmentAltText) {
        this.setDepartmentAltText(departmentAltText);
        return this;
    }

    public void setDepartmentAltText(String departmentAltText) {
        this.departmentAltText = departmentAltText;
    }

    public Boolean getDisplayCandRate() {
        return this.displayCandRate;
    }

    public Job displayCandRate(Boolean displayCandRate) {
        this.setDisplayCandRate(displayCandRate);
        return this;
    }

    public void setDisplayCandRate(Boolean displayCandRate) {
        this.displayCandRate = displayCandRate;
    }

    public String getCandRateCurrency() {
        return this.candRateCurrency;
    }

    public Job candRateCurrency(String candRateCurrency) {
        this.setCandRateCurrency(candRateCurrency);
        return this;
    }

    public void setCandRateCurrency(String candRateCurrency) {
        this.candRateCurrency = candRateCurrency;
    }

    public BigDecimal getCandMinRate() {
        return this.candMinRate;
    }

    public Job candMinRate(BigDecimal candMinRate) {
        this.setCandMinRate(candMinRate);
        return this;
    }

    public void setCandMinRate(BigDecimal candMinRate) {
        this.candMinRate = candMinRate;
    }

    public BigDecimal getCandMaxRate() {
        return this.candMaxRate;
    }

    public Job candMaxRate(BigDecimal candMaxRate) {
        this.setCandMaxRate(candMaxRate);
        return this;
    }

    public void setCandMaxRate(BigDecimal candMaxRate) {
        this.candMaxRate = candMaxRate;
    }

    public SalaryTimeSpan getCandRateTimeSpan() {
        return this.candRateTimeSpan;
    }

    public Job candRateTimeSpan(SalaryTimeSpan candRateTimeSpan) {
        this.setCandRateTimeSpan(candRateTimeSpan);
        return this;
    }

    public void setCandRateTimeSpan(SalaryTimeSpan candRateTimeSpan) {
        this.candRateTimeSpan = candRateTimeSpan;
    }

    public String getCandRateTaxTerm() {
        return this.candRateTaxTerm;
    }

    public Job candRateTaxTerm(String candRateTaxTerm) {
        this.setCandRateTaxTerm(candRateTaxTerm);
        return this;
    }

    public void setCandRateTaxTerm(String candRateTaxTerm) {
        this.candRateTaxTerm = candRateTaxTerm;
    }

    public String getCandSalaryAltDisplayText() {
        return this.candSalaryAltDisplayText;
    }

    public Job candSalaryAltDisplayText(String candSalaryAltDisplayText) {
        this.setCandSalaryAltDisplayText(candSalaryAltDisplayText);
        return this;
    }

    public void setCandSalaryAltDisplayText(String candSalaryAltDisplayText) {
        this.candSalaryAltDisplayText = candSalaryAltDisplayText;
    }

    public String getOtherBenefitDetails() {
        return this.otherBenefitDetails;
    }

    public Job otherBenefitDetails(String otherBenefitDetails) {
        this.setOtherBenefitDetails(otherBenefitDetails);
        return this;
    }

    public void setOtherBenefitDetails(String otherBenefitDetails) {
        this.otherBenefitDetails = otherBenefitDetails;
    }

    public String getClientBillRateCurrency() {
        return this.clientBillRateCurrency;
    }

    public Job clientBillRateCurrency(String clientBillRateCurrency) {
        this.setClientBillRateCurrency(clientBillRateCurrency);
        return this;
    }

    public void setClientBillRateCurrency(String clientBillRateCurrency) {
        this.clientBillRateCurrency = clientBillRateCurrency;
    }

    public BigDecimal getClientBillRate() {
        return this.clientBillRate;
    }

    public Job clientBillRate(BigDecimal clientBillRate) {
        this.setClientBillRate(clientBillRate);
        return this;
    }

    public void setClientBillRate(BigDecimal clientBillRate) {
        this.clientBillRate = clientBillRate;
    }

    public SalaryTimeSpan getClientBillRateTimeSpan() {
        return this.clientBillRateTimeSpan;
    }

    public Job clientBillRateTimeSpan(SalaryTimeSpan clientBillRateTimeSpan) {
        this.setClientBillRateTimeSpan(clientBillRateTimeSpan);
        return this;
    }

    public void setClientBillRateTimeSpan(SalaryTimeSpan clientBillRateTimeSpan) {
        this.clientBillRateTimeSpan = clientBillRateTimeSpan;
    }

    public String getClientBillRateTaxTerm() {
        return this.clientBillRateTaxTerm;
    }

    public Job clientBillRateTaxTerm(String clientBillRateTaxTerm) {
        this.setClientBillRateTaxTerm(clientBillRateTaxTerm);
        return this;
    }

    public void setClientBillRateTaxTerm(String clientBillRateTaxTerm) {
        this.clientBillRateTaxTerm = clientBillRateTaxTerm;
    }

    public String getWorkDuration() {
        return this.workDuration;
    }

    public Job workDuration(String workDuration) {
        this.setWorkDuration(workDuration);
        return this;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }

    public String getImmigrationStatus() {
        return this.immigrationStatus;
    }

    public Job immigrationStatus(String immigrationStatus) {
        this.setImmigrationStatus(immigrationStatus);
        return this;
    }

    public void setImmigrationStatus(String immigrationStatus) {
        this.immigrationStatus = immigrationStatus;
    }

    public String getDisplayImmigrationStatus() {
        return this.displayImmigrationStatus;
    }

    public Job displayImmigrationStatus(String displayImmigrationStatus) {
        this.setDisplayImmigrationStatus(displayImmigrationStatus);
        return this;
    }

    public void setDisplayImmigrationStatus(String displayImmigrationStatus) {
        this.displayImmigrationStatus = displayImmigrationStatus;
    }

    public String getSkills() {
        return this.skills;
    }

    public Job skills(String skills) {
        this.setSkills(skills);
        return this;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAltSkills() {
        return this.altSkills;
    }

    public Job altSkills(String altSkills) {
        this.setAltSkills(altSkills);
        return this;
    }

    public void setAltSkills(String altSkills) {
        this.altSkills = altSkills;
    }

    public String getTags() {
        return this.tags;
    }

    public Job tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getQualificationIds() {
        return this.qualificationIds;
    }

    public Job qualificationIds(String qualificationIds) {
        this.setQualificationIds(qualificationIds);
        return this;
    }

    public void setQualificationIds(String qualificationIds) {
        this.qualificationIds = qualificationIds;
    }

    public String getQualificationsAltText() {
        return this.qualificationsAltText;
    }

    public Job qualificationsAltText(String qualificationsAltText) {
        this.setQualificationsAltText(qualificationsAltText);
        return this;
    }

    public void setQualificationsAltText(String qualificationsAltText) {
        this.qualificationsAltText = qualificationsAltText;
    }

    public String getEduRequirements() {
        return this.eduRequirements;
    }

    public Job eduRequirements(String eduRequirements) {
        this.setEduRequirements(eduRequirements);
        return this;
    }

    public void setEduRequirements(String eduRequirements) {
        this.eduRequirements = eduRequirements;
    }

    public String getExpRequirements() {
        return this.expRequirements;
    }

    public Job expRequirements(String expRequirements) {
        this.setExpRequirements(expRequirements);
        return this;
    }

    public void setExpRequirements(String expRequirements) {
        this.expRequirements = expRequirements;
    }

    public String getExpAltText() {
        return this.expAltText;
    }

    public Job expAltText(String expAltText) {
        this.setExpAltText(expAltText);
        return this;
    }

    public void setExpAltText(String expAltText) {
        this.expAltText = expAltText;
    }

    public Double getMinExpInYears() {
        return this.minExpInYears;
    }

    public Job minExpInYears(Double minExpInYears) {
        this.setMinExpInYears(minExpInYears);
        return this;
    }

    public void setMinExpInYears(Double minExpInYears) {
        this.minExpInYears = minExpInYears;
    }

    public Double getMaxExpInYears() {
        return this.maxExpInYears;
    }

    public Job maxExpInYears(Double maxExpInYears) {
        this.setMaxExpInYears(maxExpInYears);
        return this;
    }

    public void setMaxExpInYears(Double maxExpInYears) {
        this.maxExpInYears = maxExpInYears;
    }

    public String getLanguageIds() {
        return this.languageIds;
    }

    public Job languageIds(String languageIds) {
        this.setLanguageIds(languageIds);
        return this;
    }

    public void setLanguageIds(String languageIds) {
        this.languageIds = languageIds;
    }

    public String getVisaRequirements() {
        return this.visaRequirements;
    }

    public Job visaRequirements(String visaRequirements) {
        this.setVisaRequirements(visaRequirements);
        return this;
    }

    public void setVisaRequirements(String visaRequirements) {
        this.visaRequirements = visaRequirements;
    }

    public String getWorkAuthorizationIds() {
        return this.workAuthorizationIds;
    }

    public Job workAuthorizationIds(String workAuthorizationIds) {
        this.setWorkAuthorizationIds(workAuthorizationIds);
        return this;
    }

    public void setWorkAuthorizationIds(String workAuthorizationIds) {
        this.workAuthorizationIds = workAuthorizationIds;
    }

    public JobApplicationForm getApplicationFormType() {
        return this.applicationFormType;
    }

    public Job applicationFormType(JobApplicationForm applicationFormType) {
        this.setApplicationFormType(applicationFormType);
        return this;
    }

    public void setApplicationFormType(JobApplicationForm applicationFormType) {
        this.applicationFormType = applicationFormType;
    }

    public Boolean getIsPartnerJob() {
        return this.isPartnerJob;
    }

    public Job isPartnerJob(Boolean isPartnerJob) {
        this.setIsPartnerJob(isPartnerJob);
        return this;
    }

    public void setIsPartnerJob(Boolean isPartnerJob) {
        this.isPartnerJob = isPartnerJob;
    }

    public String getRedirectionUrl() {
        return this.redirectionUrl;
    }

    public Job redirectionUrl(String redirectionUrl) {
        this.setRedirectionUrl(redirectionUrl);
        return this;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    public JobStatus getJobStatus() {
        return this.jobStatus;
    }

    public Job jobStatus(JobStatus jobStatus) {
        this.setJobStatus(jobStatus);
        return this;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getEndClient() {
        return this.endClient;
    }

    public Job endClient(String endClient) {
        this.setEndClient(endClient);
        return this;
    }

    public void setEndClient(String endClient) {
        this.endClient = endClient;
    }

    public String getDomainAlt() {
        return this.domainAlt;
    }

    public Job domainAlt(String domainAlt) {
        this.setDomainAlt(domainAlt);
        return this;
    }

    public void setDomainAlt(String domainAlt) {
        this.domainAlt = domainAlt;
    }

    public String getComments() {
        return this.comments;
    }

    public Job comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAdditionalNotificationsToUserIds() {
        return this.additionalNotificationsToUserIds;
    }

    public Job additionalNotificationsToUserIds(String additionalNotificationsToUserIds) {
        this.setAdditionalNotificationsToUserIds(additionalNotificationsToUserIds);
        return this;
    }

    public void setAdditionalNotificationsToUserIds(String additionalNotificationsToUserIds) {
        this.additionalNotificationsToUserIds = additionalNotificationsToUserIds;
    }

    public String getAdditionalNotificationsToTeamIds() {
        return this.additionalNotificationsToTeamIds;
    }

    public Job additionalNotificationsToTeamIds(String additionalNotificationsToTeamIds) {
        this.setAdditionalNotificationsToTeamIds(additionalNotificationsToTeamIds);
        return this;
    }

    public void setAdditionalNotificationsToTeamIds(String additionalNotificationsToTeamIds) {
        this.additionalNotificationsToTeamIds = additionalNotificationsToTeamIds;
    }

    public String getRequiredDocumentIds() {
        return this.requiredDocumentIds;
    }

    public Job requiredDocumentIds(String requiredDocumentIds) {
        this.setRequiredDocumentIds(requiredDocumentIds);
        return this;
    }

    public void setRequiredDocumentIds(String requiredDocumentIds) {
        this.requiredDocumentIds = requiredDocumentIds;
    }

    public String getJobCloseReasonOtherAlt() {
        return this.jobCloseReasonOtherAlt;
    }

    public Job jobCloseReasonOtherAlt(String jobCloseReasonOtherAlt) {
        this.setJobCloseReasonOtherAlt(jobCloseReasonOtherAlt);
        return this;
    }

    public void setJobCloseReasonOtherAlt(String jobCloseReasonOtherAlt) {
        this.jobCloseReasonOtherAlt = jobCloseReasonOtherAlt;
    }

    public Boolean getAddToCareerPage() {
        return this.addToCareerPage;
    }

    public Job addToCareerPage(Boolean addToCareerPage) {
        this.setAddToCareerPage(addToCareerPage);
        return this;
    }

    public void setAddToCareerPage(Boolean addToCareerPage) {
        this.addToCareerPage = addToCareerPage;
    }

    public RemoteJob getRemoteJob() {
        return this.remoteJob;
    }

    public Job remoteJob(RemoteJob remoteJob) {
        this.setRemoteJob(remoteJob);
        return this;
    }

    public void setRemoteJob(RemoteJob remoteJob) {
        this.remoteJob = remoteJob;
    }

    public String getHiringFor() {
        return this.hiringFor;
    }

    public Job hiringFor(String hiringFor) {
        this.setHiringFor(hiringFor);
        return this;
    }

    public void setHiringFor(String hiringFor) {
        this.hiringFor = hiringFor;
    }

    public String getWorkDurationTimeSpan() {
        return this.workDurationTimeSpan;
    }

    public Job workDurationTimeSpan(String workDurationTimeSpan) {
        this.setWorkDurationTimeSpan(workDurationTimeSpan);
        return this;
    }

    public void setWorkDurationTimeSpan(String workDurationTimeSpan) {
        this.workDurationTimeSpan = workDurationTimeSpan;
    }

    public String getTaxTerms() {
        return this.taxTerms;
    }

    public Job taxTerms(String taxTerms) {
        this.setTaxTerms(taxTerms);
        return this;
    }

    public void setTaxTerms(String taxTerms) {
        this.taxTerms = taxTerms;
    }

    public Team getPostedByTeam() {
        return this.postedByTeam;
    }

    public void setPostedByTeam(Team team) {
        this.postedByTeam = team;
    }

    public Job postedByTeam(Team team) {
        this.setPostedByTeam(team);
        return this;
    }

    public BusinessUnit getBusinessUnit() {
        return this.businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public Job businessUnit(BusinessUnit businessUnit) {
        this.setBusinessUnit(businessUnit);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Job client(Client client) {
        this.setClient(client);
        return this;
    }

    public CatalogueValue getJobType() {
        return this.jobType;
    }

    public void setJobType(CatalogueValue catalogueValue) {
        this.jobType = catalogueValue;
    }

    public Job jobType(CatalogueValue catalogueValue) {
        this.setJobType(catalogueValue);
        return this;
    }

    public CatalogueValue getIndustry() {
        return this.industry;
    }

    public void setIndustry(CatalogueValue catalogueValue) {
        this.industry = catalogueValue;
    }

    public Job industry(CatalogueValue catalogueValue) {
        this.setIndustry(catalogueValue);
        return this;
    }

    public CatalogueValue getDepartment() {
        return this.department;
    }

    public void setDepartment(CatalogueValue catalogueValue) {
        this.department = catalogueValue;
    }

    public Job department(CatalogueValue catalogueValue) {
        this.setDepartment(catalogueValue);
        return this;
    }

    public Ruser getClientRecruiter() {
        return this.clientRecruiter;
    }

    public void setClientRecruiter(Ruser ruser) {
        this.clientRecruiter = ruser;
    }

    public Job clientRecruiter(Ruser ruser) {
        this.setClientRecruiter(ruser);
        return this;
    }

    public Contact getClientManager() {
        return this.clientManager;
    }

    public void setClientManager(Contact contact) {
        this.clientManager = contact;
    }

    public Job clientManager(Contact contact) {
        this.setClientManager(contact);
        return this;
    }

    public Ruser getAccountManager() {
        return this.accountManager;
    }

    public void setAccountManager(Ruser ruser) {
        this.accountManager = ruser;
    }

    public Job accountManager(Ruser ruser) {
        this.setAccountManager(ruser);
        return this;
    }

    public Ruser getHeadOfRecruitment() {
        return this.headOfRecruitment;
    }

    public void setHeadOfRecruitment(Ruser ruser) {
        this.headOfRecruitment = ruser;
    }

    public Job headOfRecruitment(Ruser ruser) {
        this.setHeadOfRecruitment(ruser);
        return this;
    }

    public Ruser getDeliveryLeadManager() {
        return this.deliveryLeadManager;
    }

    public void setDeliveryLeadManager(Ruser ruser) {
        this.deliveryLeadManager = ruser;
    }

    public Job deliveryLeadManager(Ruser ruser) {
        this.setDeliveryLeadManager(ruser);
        return this;
    }

    public CatalogueValue getDomain() {
        return this.domain;
    }

    public void setDomain(CatalogueValue catalogueValue) {
        this.domain = catalogueValue;
    }

    public Job domain(CatalogueValue catalogueValue) {
        this.setDomain(catalogueValue);
        return this;
    }

    public Ruser getSrDeliveryManager() {
        return this.srDeliveryManager;
    }

    public void setSrDeliveryManager(Ruser ruser) {
        this.srDeliveryManager = ruser;
    }

    public Job srDeliveryManager(Ruser ruser) {
        this.setSrDeliveryManager(ruser);
        return this;
    }

    public Ruser getTeamLead() {
        return this.teamLead;
    }

    public void setTeamLead(Ruser ruser) {
        this.teamLead = ruser;
    }

    public Job teamLead(Ruser ruser) {
        this.setTeamLead(ruser);
        return this;
    }

    public CatalogueValue getPriority() {
        return this.priority;
    }

    public void setPriority(CatalogueValue catalogueValue) {
        this.priority = catalogueValue;
    }

    public Job priority(CatalogueValue catalogueValue) {
        this.setPriority(catalogueValue);
        return this;
    }

    public CatalogueValue getJobCloseReason() {
        return this.jobCloseReason;
    }

    public void setJobCloseReason(CatalogueValue catalogueValue) {
        this.jobCloseReason = catalogueValue;
    }

    public Job jobCloseReason(CatalogueValue catalogueValue) {
        this.setJobCloseReason(catalogueValue);
        return this;
    }

    public CatalogueValue getJobCategory() {
        return this.jobCategory;
    }

    public void setJobCategory(CatalogueValue catalogueValue) {
        this.jobCategory = catalogueValue;
    }

    public Job jobCategory(CatalogueValue catalogueValue) {
        this.setJobCategory(catalogueValue);
        return this;
    }

    public CatalogueValue getJobOccupation() {
        return this.jobOccupation;
    }

    public void setJobOccupation(CatalogueValue catalogueValue) {
        this.jobOccupation = catalogueValue;
    }

    public Job jobOccupation(CatalogueValue catalogueValue) {
        this.setJobOccupation(catalogueValue);
        return this;
    }

    public Set<JobLocation> getJobLocations() {
        return this.jobLocations;
    }

    public void setJobLocations(Set<JobLocation> jobLocations) {
        if (this.jobLocations != null) {
            this.jobLocations.forEach(i -> i.setJob(null));
        }
        if (jobLocations != null) {
            jobLocations.forEach(i -> i.setJob(this));
        }
        this.jobLocations = jobLocations;
    }

    public Job jobLocations(Set<JobLocation> jobLocations) {
        this.setJobLocations(jobLocations);
        return this;
    }

    public Job addJobLocation(JobLocation jobLocation) {
        this.jobLocations.add(jobLocation);
        jobLocation.setJob(this);
        return this;
    }

    public Job removeJobLocation(JobLocation jobLocation) {
        this.jobLocations.remove(jobLocation);
        jobLocation.setJob(null);
        return this;
    }

    public Set<JobDocument> getJobDocuments() {
        return this.jobDocuments;
    }

    public void setJobDocuments(Set<JobDocument> jobDocuments) {
        if (this.jobDocuments != null) {
            this.jobDocuments.forEach(i -> i.setJob(null));
        }
        if (jobDocuments != null) {
            jobDocuments.forEach(i -> i.setJob(this));
        }
        this.jobDocuments = jobDocuments;
    }

    public Job jobDocuments(Set<JobDocument> jobDocuments) {
        this.setJobDocuments(jobDocuments);
        return this;
    }

    public Job addJobDocument(JobDocument jobDocument) {
        this.jobDocuments.add(jobDocument);
        jobDocument.setJob(this);
        return this;
    }

    public Job removeJobDocument(JobDocument jobDocument) {
        this.jobDocuments.remove(jobDocument);
        jobDocument.setJob(null);
        return this;
    }

    public Set<JobCustomAttribute> getJobCustomAttributes() {
        return this.jobCustomAttributes;
    }

    public void setJobCustomAttributes(Set<JobCustomAttribute> jobCustomAttributes) {
        if (this.jobCustomAttributes != null) {
            this.jobCustomAttributes.forEach(i -> i.setJob(null));
        }
        if (jobCustomAttributes != null) {
            jobCustomAttributes.forEach(i -> i.setJob(this));
        }
        this.jobCustomAttributes = jobCustomAttributes;
    }

    public Job jobCustomAttributes(Set<JobCustomAttribute> jobCustomAttributes) {
        this.setJobCustomAttributes(jobCustomAttributes);
        return this;
    }

    public Job addJobCustomAttribute(JobCustomAttribute jobCustomAttribute) {
        this.jobCustomAttributes.add(jobCustomAttribute);
        jobCustomAttribute.setJob(this);
        return this;
    }

    public Job removeJobCustomAttribute(JobCustomAttribute jobCustomAttribute) {
        this.jobCustomAttributes.remove(jobCustomAttribute);
        jobCustomAttribute.setJob(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return id != null && id.equals(((Job) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", jobCode='" + getJobCode() + "'" +
            ", clientJobCode='" + getClientJobCode() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", orgEmploymentTypeIds='" + getOrgEmploymentTypeIds() + "'" +
            ", jobRef='" + getJobRef() + "'" +
            ", jobSource='" + getJobSource() + "'" +
            ", url='" + getUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", publicJobTitle='" + getPublicJobTitle() + "'" +
            ", publicJobDescription='" + getPublicJobDescription() + "'" +
            ", autoCloseDate='" + getAutoCloseDate() + "'" +
            ", noOfPositions=" + getNoOfPositions() +
            ", departmentAltText='" + getDepartmentAltText() + "'" +
            ", displayCandRate='" + getDisplayCandRate() + "'" +
            ", candRateCurrency='" + getCandRateCurrency() + "'" +
            ", candMinRate=" + getCandMinRate() +
            ", candMaxRate=" + getCandMaxRate() +
            ", candRateTimeSpan='" + getCandRateTimeSpan() + "'" +
            ", candRateTaxTerm='" + getCandRateTaxTerm() + "'" +
            ", candSalaryAltDisplayText='" + getCandSalaryAltDisplayText() + "'" +
            ", otherBenefitDetails='" + getOtherBenefitDetails() + "'" +
            ", clientBillRateCurrency='" + getClientBillRateCurrency() + "'" +
            ", clientBillRate=" + getClientBillRate() +
            ", clientBillRateTimeSpan='" + getClientBillRateTimeSpan() + "'" +
            ", clientBillRateTaxTerm='" + getClientBillRateTaxTerm() + "'" +
            ", workDuration='" + getWorkDuration() + "'" +
            ", immigrationStatus='" + getImmigrationStatus() + "'" +
            ", displayImmigrationStatus='" + getDisplayImmigrationStatus() + "'" +
            ", skills='" + getSkills() + "'" +
            ", altSkills='" + getAltSkills() + "'" +
            ", tags='" + getTags() + "'" +
            ", qualificationIds='" + getQualificationIds() + "'" +
            ", qualificationsAltText='" + getQualificationsAltText() + "'" +
            ", eduRequirements='" + getEduRequirements() + "'" +
            ", expRequirements='" + getExpRequirements() + "'" +
            ", expAltText='" + getExpAltText() + "'" +
            ", minExpInYears=" + getMinExpInYears() +
            ", maxExpInYears=" + getMaxExpInYears() +
            ", languageIds='" + getLanguageIds() + "'" +
            ", visaRequirements='" + getVisaRequirements() + "'" +
            ", workAuthorizationIds='" + getWorkAuthorizationIds() + "'" +
            ", applicationFormType='" + getApplicationFormType() + "'" +
            ", isPartnerJob='" + getIsPartnerJob() + "'" +
            ", redirectionUrl='" + getRedirectionUrl() + "'" +
            ", jobStatus='" + getJobStatus() + "'" +
            ", endClient='" + getEndClient() + "'" +
            ", domainAlt='" + getDomainAlt() + "'" +
            ", comments='" + getComments() + "'" +
            ", additionalNotificationsToUserIds='" + getAdditionalNotificationsToUserIds() + "'" +
            ", additionalNotificationsToTeamIds='" + getAdditionalNotificationsToTeamIds() + "'" +
            ", requiredDocumentIds='" + getRequiredDocumentIds() + "'" +
            ", jobCloseReasonOtherAlt='" + getJobCloseReasonOtherAlt() + "'" +
            ", addToCareerPage='" + getAddToCareerPage() + "'" +
            ", remoteJob='" + getRemoteJob() + "'" +
            ", hiringFor='" + getHiringFor() + "'" +
            ", workDurationTimeSpan='" + getWorkDurationTimeSpan() + "'" +
            ", taxTerms='" + getTaxTerms() + "'" +
            "}";
    }
}
