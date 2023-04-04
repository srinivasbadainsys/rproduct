package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rp.domain.enumeration.ClientVisibility;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_visibility")
    private ClientVisibility clientVisibility;

    @Column(name = "business_unit_ids")
    private String businessUnitIds;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "vms_client_name")
    private String vmsClientName;

    @Column(name = "federal_id")
    private String federalID;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

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

    @Column(name = "website")
    private String website;

    @Column(name = "send_requirement")
    private Boolean sendRequirement;

    @Column(name = "send_hot_list")
    private Boolean sendHotList;

    @Column(name = "fax")
    private String fax;

    @Column(name = "status")
    private String status;

    @Column(name = "allow_access_to_all_users")
    private Boolean allowAccessToAllUsers;

    @Column(name = "ownership_ids")
    private String ownershipIds;

    @Column(name = "client_lead_ids")
    private String clientLeadIds;

    @Column(name = "required_documents")
    private String requiredDocuments;

    @Column(name = "practice_alt")
    private String practiceAlt;

    @Column(name = "about_company")
    private String aboutCompany;

    @Column(name = "stop_notifying_client_contact_on_submit_to_client")
    private Boolean stopNotifyingClientContactOnSubmitToClient;

    @Column(name = "default_for_job_postings")
    private Boolean defaultForJobPostings;

    @Column(name = "submission_guidelines")
    private String submissionGuidelines;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "assigned_to_teams")
    private String assignedToTeams;

    @Column(name = "sales_managers")
    private String salesManagers;

    @Column(name = "account_managers")
    private String accountManagers;

    @Column(name = "recruitment_managers")
    private String recruitmentManagers;

    @Column(name = "default_tat_config_for_job_posting_or_vms_jobs")
    private Integer defaultTATConfigForJobPostingOrVMSJobs;

    @Column(name = "default_tat_config_timespan")
    private String defaultTATConfigTimespan;

    @Column(name = "notify_on_tat_to_user_types")
    private String notifyOnTATToUserTypes;

    @Column(name = "notify_on_tat_to_user_ids")
    private String notifyOnTATToUserIds;

    @Column(name = "tax_term_ids")
    private String taxTermIds;

    @Column(name = "work_authorization_ids")
    private String workAuthorizationIds;

    @Column(name = "post_job_on_careers_page")
    private Boolean postJobOnCareersPage;

    @Column(name = "default_pay_rate_tax_term")
    private String defaultPayRateTaxTerm;

    @Column(name = "default_bill_rate_tax_term")
    private String defaultBillRateTaxTerm;

    @Column(name = "references_mandatory_for_submission")
    private Boolean referencesMandatoryForSubmission;

    @Column(name = "max_submissions")
    private Integer maxSubmissions;

    @Column(name = "no_of_positions")
    private Integer noOfPositions;

    @Column(name = "mark_up")
    private String markUp;

    @Column(name = "msp")
    private String msp;

    @Column(name = "mail_subject")
    private String mailSubject;

    @Column(name = "mail_body")
    private String mailBody;

    @Column(name = "fields_for_excel")
    private String fieldsForExcel;

    @OneToOne
    @JoinColumn(unique = true)
    private BusinessUnit primaryBusinessUnit;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue industry;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue category;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue paymentTerms;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue practice;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser primaryOwnerUser;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Set<ClientAccount> clientAccounts = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Set<ClientNote> clientNotes = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Set<ClientDocument> clientDocuments = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "primaryOwnerUser", "client" }, allowSetters = true)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Set<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientVisibility getClientVisibility() {
        return this.clientVisibility;
    }

    public Client clientVisibility(ClientVisibility clientVisibility) {
        this.setClientVisibility(clientVisibility);
        return this;
    }

    public void setClientVisibility(ClientVisibility clientVisibility) {
        this.clientVisibility = clientVisibility;
    }

    public String getBusinessUnitIds() {
        return this.businessUnitIds;
    }

    public Client businessUnitIds(String businessUnitIds) {
        this.setBusinessUnitIds(businessUnitIds);
        return this;
    }

    public void setBusinessUnitIds(String businessUnitIds) {
        this.businessUnitIds = businessUnitIds;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Client clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getVmsClientName() {
        return this.vmsClientName;
    }

    public Client vmsClientName(String vmsClientName) {
        this.setVmsClientName(vmsClientName);
        return this;
    }

    public void setVmsClientName(String vmsClientName) {
        this.vmsClientName = vmsClientName;
    }

    public String getFederalID() {
        return this.federalID;
    }

    public Client federalID(String federalID) {
        this.setFederalID(federalID);
        return this;
    }

    public void setFederalID(String federalID) {
        this.federalID = federalID;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public Client contactNumber(String contactNumber) {
        this.setContactNumber(contactNumber);
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public Client address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return this.area;
    }

    public Client area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return this.city;
    }

    public Client city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Client state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public Client stateCode(String stateCode) {
        this.setStateCode(stateCode);
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCounty() {
        return this.county;
    }

    public Client county(String county) {
        this.setCounty(county);
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return this.country;
    }

    public Client country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Client countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Client zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWebsite() {
        return this.website;
    }

    public Client website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getSendRequirement() {
        return this.sendRequirement;
    }

    public Client sendRequirement(Boolean sendRequirement) {
        this.setSendRequirement(sendRequirement);
        return this;
    }

    public void setSendRequirement(Boolean sendRequirement) {
        this.sendRequirement = sendRequirement;
    }

    public Boolean getSendHotList() {
        return this.sendHotList;
    }

    public Client sendHotList(Boolean sendHotList) {
        this.setSendHotList(sendHotList);
        return this;
    }

    public void setSendHotList(Boolean sendHotList) {
        this.sendHotList = sendHotList;
    }

    public String getFax() {
        return this.fax;
    }

    public Client fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getStatus() {
        return this.status;
    }

    public Client status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getAllowAccessToAllUsers() {
        return this.allowAccessToAllUsers;
    }

    public Client allowAccessToAllUsers(Boolean allowAccessToAllUsers) {
        this.setAllowAccessToAllUsers(allowAccessToAllUsers);
        return this;
    }

    public void setAllowAccessToAllUsers(Boolean allowAccessToAllUsers) {
        this.allowAccessToAllUsers = allowAccessToAllUsers;
    }

    public String getOwnershipIds() {
        return this.ownershipIds;
    }

    public Client ownershipIds(String ownershipIds) {
        this.setOwnershipIds(ownershipIds);
        return this;
    }

    public void setOwnershipIds(String ownershipIds) {
        this.ownershipIds = ownershipIds;
    }

    public String getClientLeadIds() {
        return this.clientLeadIds;
    }

    public Client clientLeadIds(String clientLeadIds) {
        this.setClientLeadIds(clientLeadIds);
        return this;
    }

    public void setClientLeadIds(String clientLeadIds) {
        this.clientLeadIds = clientLeadIds;
    }

    public String getRequiredDocuments() {
        return this.requiredDocuments;
    }

    public Client requiredDocuments(String requiredDocuments) {
        this.setRequiredDocuments(requiredDocuments);
        return this;
    }

    public void setRequiredDocuments(String requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public String getPracticeAlt() {
        return this.practiceAlt;
    }

    public Client practiceAlt(String practiceAlt) {
        this.setPracticeAlt(practiceAlt);
        return this;
    }

    public void setPracticeAlt(String practiceAlt) {
        this.practiceAlt = practiceAlt;
    }

    public String getAboutCompany() {
        return this.aboutCompany;
    }

    public Client aboutCompany(String aboutCompany) {
        this.setAboutCompany(aboutCompany);
        return this;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    public Boolean getStopNotifyingClientContactOnSubmitToClient() {
        return this.stopNotifyingClientContactOnSubmitToClient;
    }

    public Client stopNotifyingClientContactOnSubmitToClient(Boolean stopNotifyingClientContactOnSubmitToClient) {
        this.setStopNotifyingClientContactOnSubmitToClient(stopNotifyingClientContactOnSubmitToClient);
        return this;
    }

    public void setStopNotifyingClientContactOnSubmitToClient(Boolean stopNotifyingClientContactOnSubmitToClient) {
        this.stopNotifyingClientContactOnSubmitToClient = stopNotifyingClientContactOnSubmitToClient;
    }

    public Boolean getDefaultForJobPostings() {
        return this.defaultForJobPostings;
    }

    public Client defaultForJobPostings(Boolean defaultForJobPostings) {
        this.setDefaultForJobPostings(defaultForJobPostings);
        return this;
    }

    public void setDefaultForJobPostings(Boolean defaultForJobPostings) {
        this.defaultForJobPostings = defaultForJobPostings;
    }

    public String getSubmissionGuidelines() {
        return this.submissionGuidelines;
    }

    public Client submissionGuidelines(String submissionGuidelines) {
        this.setSubmissionGuidelines(submissionGuidelines);
        return this;
    }

    public void setSubmissionGuidelines(String submissionGuidelines) {
        this.submissionGuidelines = submissionGuidelines;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }

    public Client assignedTo(String assignedTo) {
        this.setAssignedTo(assignedTo);
        return this;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedToTeams() {
        return this.assignedToTeams;
    }

    public Client assignedToTeams(String assignedToTeams) {
        this.setAssignedToTeams(assignedToTeams);
        return this;
    }

    public void setAssignedToTeams(String assignedToTeams) {
        this.assignedToTeams = assignedToTeams;
    }

    public String getSalesManagers() {
        return this.salesManagers;
    }

    public Client salesManagers(String salesManagers) {
        this.setSalesManagers(salesManagers);
        return this;
    }

    public void setSalesManagers(String salesManagers) {
        this.salesManagers = salesManagers;
    }

    public String getAccountManagers() {
        return this.accountManagers;
    }

    public Client accountManagers(String accountManagers) {
        this.setAccountManagers(accountManagers);
        return this;
    }

    public void setAccountManagers(String accountManagers) {
        this.accountManagers = accountManagers;
    }

    public String getRecruitmentManagers() {
        return this.recruitmentManagers;
    }

    public Client recruitmentManagers(String recruitmentManagers) {
        this.setRecruitmentManagers(recruitmentManagers);
        return this;
    }

    public void setRecruitmentManagers(String recruitmentManagers) {
        this.recruitmentManagers = recruitmentManagers;
    }

    public Integer getDefaultTATConfigForJobPostingOrVMSJobs() {
        return this.defaultTATConfigForJobPostingOrVMSJobs;
    }

    public Client defaultTATConfigForJobPostingOrVMSJobs(Integer defaultTATConfigForJobPostingOrVMSJobs) {
        this.setDefaultTATConfigForJobPostingOrVMSJobs(defaultTATConfigForJobPostingOrVMSJobs);
        return this;
    }

    public void setDefaultTATConfigForJobPostingOrVMSJobs(Integer defaultTATConfigForJobPostingOrVMSJobs) {
        this.defaultTATConfigForJobPostingOrVMSJobs = defaultTATConfigForJobPostingOrVMSJobs;
    }

    public String getDefaultTATConfigTimespan() {
        return this.defaultTATConfigTimespan;
    }

    public Client defaultTATConfigTimespan(String defaultTATConfigTimespan) {
        this.setDefaultTATConfigTimespan(defaultTATConfigTimespan);
        return this;
    }

    public void setDefaultTATConfigTimespan(String defaultTATConfigTimespan) {
        this.defaultTATConfigTimespan = defaultTATConfigTimespan;
    }

    public String getNotifyOnTATToUserTypes() {
        return this.notifyOnTATToUserTypes;
    }

    public Client notifyOnTATToUserTypes(String notifyOnTATToUserTypes) {
        this.setNotifyOnTATToUserTypes(notifyOnTATToUserTypes);
        return this;
    }

    public void setNotifyOnTATToUserTypes(String notifyOnTATToUserTypes) {
        this.notifyOnTATToUserTypes = notifyOnTATToUserTypes;
    }

    public String getNotifyOnTATToUserIds() {
        return this.notifyOnTATToUserIds;
    }

    public Client notifyOnTATToUserIds(String notifyOnTATToUserIds) {
        this.setNotifyOnTATToUserIds(notifyOnTATToUserIds);
        return this;
    }

    public void setNotifyOnTATToUserIds(String notifyOnTATToUserIds) {
        this.notifyOnTATToUserIds = notifyOnTATToUserIds;
    }

    public String getTaxTermIds() {
        return this.taxTermIds;
    }

    public Client taxTermIds(String taxTermIds) {
        this.setTaxTermIds(taxTermIds);
        return this;
    }

    public void setTaxTermIds(String taxTermIds) {
        this.taxTermIds = taxTermIds;
    }

    public String getWorkAuthorizationIds() {
        return this.workAuthorizationIds;
    }

    public Client workAuthorizationIds(String workAuthorizationIds) {
        this.setWorkAuthorizationIds(workAuthorizationIds);
        return this;
    }

    public void setWorkAuthorizationIds(String workAuthorizationIds) {
        this.workAuthorizationIds = workAuthorizationIds;
    }

    public Boolean getPostJobOnCareersPage() {
        return this.postJobOnCareersPage;
    }

    public Client postJobOnCareersPage(Boolean postJobOnCareersPage) {
        this.setPostJobOnCareersPage(postJobOnCareersPage);
        return this;
    }

    public void setPostJobOnCareersPage(Boolean postJobOnCareersPage) {
        this.postJobOnCareersPage = postJobOnCareersPage;
    }

    public String getDefaultPayRateTaxTerm() {
        return this.defaultPayRateTaxTerm;
    }

    public Client defaultPayRateTaxTerm(String defaultPayRateTaxTerm) {
        this.setDefaultPayRateTaxTerm(defaultPayRateTaxTerm);
        return this;
    }

    public void setDefaultPayRateTaxTerm(String defaultPayRateTaxTerm) {
        this.defaultPayRateTaxTerm = defaultPayRateTaxTerm;
    }

    public String getDefaultBillRateTaxTerm() {
        return this.defaultBillRateTaxTerm;
    }

    public Client defaultBillRateTaxTerm(String defaultBillRateTaxTerm) {
        this.setDefaultBillRateTaxTerm(defaultBillRateTaxTerm);
        return this;
    }

    public void setDefaultBillRateTaxTerm(String defaultBillRateTaxTerm) {
        this.defaultBillRateTaxTerm = defaultBillRateTaxTerm;
    }

    public Boolean getReferencesMandatoryForSubmission() {
        return this.referencesMandatoryForSubmission;
    }

    public Client referencesMandatoryForSubmission(Boolean referencesMandatoryForSubmission) {
        this.setReferencesMandatoryForSubmission(referencesMandatoryForSubmission);
        return this;
    }

    public void setReferencesMandatoryForSubmission(Boolean referencesMandatoryForSubmission) {
        this.referencesMandatoryForSubmission = referencesMandatoryForSubmission;
    }

    public Integer getMaxSubmissions() {
        return this.maxSubmissions;
    }

    public Client maxSubmissions(Integer maxSubmissions) {
        this.setMaxSubmissions(maxSubmissions);
        return this;
    }

    public void setMaxSubmissions(Integer maxSubmissions) {
        this.maxSubmissions = maxSubmissions;
    }

    public Integer getNoOfPositions() {
        return this.noOfPositions;
    }

    public Client noOfPositions(Integer noOfPositions) {
        this.setNoOfPositions(noOfPositions);
        return this;
    }

    public void setNoOfPositions(Integer noOfPositions) {
        this.noOfPositions = noOfPositions;
    }

    public String getMarkUp() {
        return this.markUp;
    }

    public Client markUp(String markUp) {
        this.setMarkUp(markUp);
        return this;
    }

    public void setMarkUp(String markUp) {
        this.markUp = markUp;
    }

    public String getMsp() {
        return this.msp;
    }

    public Client msp(String msp) {
        this.setMsp(msp);
        return this;
    }

    public void setMsp(String msp) {
        this.msp = msp;
    }

    public String getMailSubject() {
        return this.mailSubject;
    }

    public Client mailSubject(String mailSubject) {
        this.setMailSubject(mailSubject);
        return this;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailBody() {
        return this.mailBody;
    }

    public Client mailBody(String mailBody) {
        this.setMailBody(mailBody);
        return this;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public String getFieldsForExcel() {
        return this.fieldsForExcel;
    }

    public Client fieldsForExcel(String fieldsForExcel) {
        this.setFieldsForExcel(fieldsForExcel);
        return this;
    }

    public void setFieldsForExcel(String fieldsForExcel) {
        this.fieldsForExcel = fieldsForExcel;
    }

    public BusinessUnit getPrimaryBusinessUnit() {
        return this.primaryBusinessUnit;
    }

    public void setPrimaryBusinessUnit(BusinessUnit businessUnit) {
        this.primaryBusinessUnit = businessUnit;
    }

    public Client primaryBusinessUnit(BusinessUnit businessUnit) {
        this.setPrimaryBusinessUnit(businessUnit);
        return this;
    }

    public CatalogueValue getIndustry() {
        return this.industry;
    }

    public void setIndustry(CatalogueValue catalogueValue) {
        this.industry = catalogueValue;
    }

    public Client industry(CatalogueValue catalogueValue) {
        this.setIndustry(catalogueValue);
        return this;
    }

    public CatalogueValue getCategory() {
        return this.category;
    }

    public void setCategory(CatalogueValue catalogueValue) {
        this.category = catalogueValue;
    }

    public Client category(CatalogueValue catalogueValue) {
        this.setCategory(catalogueValue);
        return this;
    }

    public CatalogueValue getPaymentTerms() {
        return this.paymentTerms;
    }

    public void setPaymentTerms(CatalogueValue catalogueValue) {
        this.paymentTerms = catalogueValue;
    }

    public Client paymentTerms(CatalogueValue catalogueValue) {
        this.setPaymentTerms(catalogueValue);
        return this;
    }

    public CatalogueValue getPractice() {
        return this.practice;
    }

    public void setPractice(CatalogueValue catalogueValue) {
        this.practice = catalogueValue;
    }

    public Client practice(CatalogueValue catalogueValue) {
        this.setPractice(catalogueValue);
        return this;
    }

    public Ruser getPrimaryOwnerUser() {
        return this.primaryOwnerUser;
    }

    public void setPrimaryOwnerUser(Ruser ruser) {
        this.primaryOwnerUser = ruser;
    }

    public Client primaryOwnerUser(Ruser ruser) {
        this.setPrimaryOwnerUser(ruser);
        return this;
    }

    public Set<ClientAccount> getClientAccounts() {
        return this.clientAccounts;
    }

    public void setClientAccounts(Set<ClientAccount> clientAccounts) {
        if (this.clientAccounts != null) {
            this.clientAccounts.forEach(i -> i.setClient(null));
        }
        if (clientAccounts != null) {
            clientAccounts.forEach(i -> i.setClient(this));
        }
        this.clientAccounts = clientAccounts;
    }

    public Client clientAccounts(Set<ClientAccount> clientAccounts) {
        this.setClientAccounts(clientAccounts);
        return this;
    }

    public Client addClientAccount(ClientAccount clientAccount) {
        this.clientAccounts.add(clientAccount);
        clientAccount.setClient(this);
        return this;
    }

    public Client removeClientAccount(ClientAccount clientAccount) {
        this.clientAccounts.remove(clientAccount);
        clientAccount.setClient(null);
        return this;
    }

    public Set<ClientNote> getClientNotes() {
        return this.clientNotes;
    }

    public void setClientNotes(Set<ClientNote> clientNotes) {
        if (this.clientNotes != null) {
            this.clientNotes.forEach(i -> i.setClient(null));
        }
        if (clientNotes != null) {
            clientNotes.forEach(i -> i.setClient(this));
        }
        this.clientNotes = clientNotes;
    }

    public Client clientNotes(Set<ClientNote> clientNotes) {
        this.setClientNotes(clientNotes);
        return this;
    }

    public Client addClientNote(ClientNote clientNote) {
        this.clientNotes.add(clientNote);
        clientNote.setClient(this);
        return this;
    }

    public Client removeClientNote(ClientNote clientNote) {
        this.clientNotes.remove(clientNote);
        clientNote.setClient(null);
        return this;
    }

    public Set<ClientDocument> getClientDocuments() {
        return this.clientDocuments;
    }

    public void setClientDocuments(Set<ClientDocument> clientDocuments) {
        if (this.clientDocuments != null) {
            this.clientDocuments.forEach(i -> i.setClient(null));
        }
        if (clientDocuments != null) {
            clientDocuments.forEach(i -> i.setClient(this));
        }
        this.clientDocuments = clientDocuments;
    }

    public Client clientDocuments(Set<ClientDocument> clientDocuments) {
        this.setClientDocuments(clientDocuments);
        return this;
    }

    public Client addClientDocument(ClientDocument clientDocument) {
        this.clientDocuments.add(clientDocument);
        clientDocument.setClient(this);
        return this;
    }

    public Client removeClientDocument(ClientDocument clientDocument) {
        this.clientDocuments.remove(clientDocument);
        clientDocument.setClient(null);
        return this;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.setClient(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setClient(this));
        }
        this.contacts = contacts;
    }

    public Client contacts(Set<Contact> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public Client addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setClient(this);
        return this;
    }

    public Client removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setClient(null);
        return this;
    }

    public Set<ClientGuidelineSubmissionDocument> getClientGuidelineSubmissionDocuments() {
        return this.clientGuidelineSubmissionDocuments;
    }

    public void setClientGuidelineSubmissionDocuments(Set<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocuments) {
        if (this.clientGuidelineSubmissionDocuments != null) {
            this.clientGuidelineSubmissionDocuments.forEach(i -> i.setClient(null));
        }
        if (clientGuidelineSubmissionDocuments != null) {
            clientGuidelineSubmissionDocuments.forEach(i -> i.setClient(this));
        }
        this.clientGuidelineSubmissionDocuments = clientGuidelineSubmissionDocuments;
    }

    public Client clientGuidelineSubmissionDocuments(Set<ClientGuidelineSubmissionDocument> clientGuidelineSubmissionDocuments) {
        this.setClientGuidelineSubmissionDocuments(clientGuidelineSubmissionDocuments);
        return this;
    }

    public Client addClientGuidelineSubmissionDocument(ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument) {
        this.clientGuidelineSubmissionDocuments.add(clientGuidelineSubmissionDocument);
        clientGuidelineSubmissionDocument.setClient(this);
        return this;
    }

    public Client removeClientGuidelineSubmissionDocument(ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument) {
        this.clientGuidelineSubmissionDocuments.remove(clientGuidelineSubmissionDocument);
        clientGuidelineSubmissionDocument.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientVisibility='" + getClientVisibility() + "'" +
            ", businessUnitIds='" + getBusinessUnitIds() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", vmsClientName='" + getVmsClientName() + "'" +
            ", federalID='" + getFederalID() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", area='" + getArea() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            ", county='" + getCounty() + "'" +
            ", country='" + getCountry() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", website='" + getWebsite() + "'" +
            ", sendRequirement='" + getSendRequirement() + "'" +
            ", sendHotList='" + getSendHotList() + "'" +
            ", fax='" + getFax() + "'" +
            ", status='" + getStatus() + "'" +
            ", allowAccessToAllUsers='" + getAllowAccessToAllUsers() + "'" +
            ", ownershipIds='" + getOwnershipIds() + "'" +
            ", clientLeadIds='" + getClientLeadIds() + "'" +
            ", requiredDocuments='" + getRequiredDocuments() + "'" +
            ", practiceAlt='" + getPracticeAlt() + "'" +
            ", aboutCompany='" + getAboutCompany() + "'" +
            ", stopNotifyingClientContactOnSubmitToClient='" + getStopNotifyingClientContactOnSubmitToClient() + "'" +
            ", defaultForJobPostings='" + getDefaultForJobPostings() + "'" +
            ", submissionGuidelines='" + getSubmissionGuidelines() + "'" +
            ", assignedTo='" + getAssignedTo() + "'" +
            ", assignedToTeams='" + getAssignedToTeams() + "'" +
            ", salesManagers='" + getSalesManagers() + "'" +
            ", accountManagers='" + getAccountManagers() + "'" +
            ", recruitmentManagers='" + getRecruitmentManagers() + "'" +
            ", defaultTATConfigForJobPostingOrVMSJobs=" + getDefaultTATConfigForJobPostingOrVMSJobs() +
            ", defaultTATConfigTimespan='" + getDefaultTATConfigTimespan() + "'" +
            ", notifyOnTATToUserTypes='" + getNotifyOnTATToUserTypes() + "'" +
            ", notifyOnTATToUserIds='" + getNotifyOnTATToUserIds() + "'" +
            ", taxTermIds='" + getTaxTermIds() + "'" +
            ", workAuthorizationIds='" + getWorkAuthorizationIds() + "'" +
            ", postJobOnCareersPage='" + getPostJobOnCareersPage() + "'" +
            ", defaultPayRateTaxTerm='" + getDefaultPayRateTaxTerm() + "'" +
            ", defaultBillRateTaxTerm='" + getDefaultBillRateTaxTerm() + "'" +
            ", referencesMandatoryForSubmission='" + getReferencesMandatoryForSubmission() + "'" +
            ", maxSubmissions=" + getMaxSubmissions() +
            ", noOfPositions=" + getNoOfPositions() +
            ", markUp='" + getMarkUp() + "'" +
            ", msp='" + getMsp() + "'" +
            ", mailSubject='" + getMailSubject() + "'" +
            ", mailBody='" + getMailBody() + "'" +
            ", fieldsForExcel='" + getFieldsForExcel() + "'" +
            "}";
    }
}
