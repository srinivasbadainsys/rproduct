package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "office_number")
    private String officeNumber;

    @Column(name = "office_number_extn")
    private String officeNumberExtn;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_id")
    private String emailID;

    @Column(name = "alt_email_id")
    private String altEmailID;

    @Column(name = "ownership_ids")
    private String ownershipIds;

    @Column(name = "allow_access_to_all_users")
    private Boolean allowAccessToAllUsers;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

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

    @Column(name = "profile_ur_ls")
    private String profileURLs;

    @Column(name = "messenger_i_ds")
    private String messengerIDs;

    @Column(name = "status")
    private String status;

    @Column(name = "client_group")
    private String clientGroup;

    @Column(name = "about")
    private String about;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser primaryOwnerUser;

    @ManyToOne
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
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public Contact clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Contact firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Contact lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Contact designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOfficeNumber() {
        return this.officeNumber;
    }

    public Contact officeNumber(String officeNumber) {
        this.setOfficeNumber(officeNumber);
        return this;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getOfficeNumberExtn() {
        return this.officeNumberExtn;
    }

    public Contact officeNumberExtn(String officeNumberExtn) {
        this.setOfficeNumberExtn(officeNumberExtn);
        return this;
    }

    public void setOfficeNumberExtn(String officeNumberExtn) {
        this.officeNumberExtn = officeNumberExtn;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public Contact mobileNumber(String mobileNumber) {
        this.setMobileNumber(mobileNumber);
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailID() {
        return this.emailID;
    }

    public Contact emailID(String emailID) {
        this.setEmailID(emailID);
        return this;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getAltEmailID() {
        return this.altEmailID;
    }

    public Contact altEmailID(String altEmailID) {
        this.setAltEmailID(altEmailID);
        return this;
    }

    public void setAltEmailID(String altEmailID) {
        this.altEmailID = altEmailID;
    }

    public String getOwnershipIds() {
        return this.ownershipIds;
    }

    public Contact ownershipIds(String ownershipIds) {
        this.setOwnershipIds(ownershipIds);
        return this;
    }

    public void setOwnershipIds(String ownershipIds) {
        this.ownershipIds = ownershipIds;
    }

    public Boolean getAllowAccessToAllUsers() {
        return this.allowAccessToAllUsers;
    }

    public Contact allowAccessToAllUsers(Boolean allowAccessToAllUsers) {
        this.setAllowAccessToAllUsers(allowAccessToAllUsers);
        return this;
    }

    public void setAllowAccessToAllUsers(Boolean allowAccessToAllUsers) {
        this.allowAccessToAllUsers = allowAccessToAllUsers;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Contact address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public Contact address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return this.area;
    }

    public Contact area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return this.city;
    }

    public Contact city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Contact state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public Contact stateCode(String stateCode) {
        this.setStateCode(stateCode);
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCounty() {
        return this.county;
    }

    public Contact county(String county) {
        this.setCounty(county);
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return this.country;
    }

    public Contact country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Contact countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Contact zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProfileURLs() {
        return this.profileURLs;
    }

    public Contact profileURLs(String profileURLs) {
        this.setProfileURLs(profileURLs);
        return this;
    }

    public void setProfileURLs(String profileURLs) {
        this.profileURLs = profileURLs;
    }

    public String getMessengerIDs() {
        return this.messengerIDs;
    }

    public Contact messengerIDs(String messengerIDs) {
        this.setMessengerIDs(messengerIDs);
        return this;
    }

    public void setMessengerIDs(String messengerIDs) {
        this.messengerIDs = messengerIDs;
    }

    public String getStatus() {
        return this.status;
    }

    public Contact status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientGroup() {
        return this.clientGroup;
    }

    public Contact clientGroup(String clientGroup) {
        this.setClientGroup(clientGroup);
        return this;
    }

    public void setClientGroup(String clientGroup) {
        this.clientGroup = clientGroup;
    }

    public String getAbout() {
        return this.about;
    }

    public Contact about(String about) {
        this.setAbout(about);
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Ruser getPrimaryOwnerUser() {
        return this.primaryOwnerUser;
    }

    public void setPrimaryOwnerUser(Ruser ruser) {
        this.primaryOwnerUser = ruser;
    }

    public Contact primaryOwnerUser(Ruser ruser) {
        this.setPrimaryOwnerUser(ruser);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Contact client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", officeNumber='" + getOfficeNumber() + "'" +
            ", officeNumberExtn='" + getOfficeNumberExtn() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", emailID='" + getEmailID() + "'" +
            ", altEmailID='" + getAltEmailID() + "'" +
            ", ownershipIds='" + getOwnershipIds() + "'" +
            ", allowAccessToAllUsers='" + getAllowAccessToAllUsers() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", area='" + getArea() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            ", county='" + getCounty() + "'" +
            ", country='" + getCountry() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", profileURLs='" + getProfileURLs() + "'" +
            ", messengerIDs='" + getMessengerIDs() + "'" +
            ", status='" + getStatus() + "'" +
            ", clientGroup='" + getClientGroup() + "'" +
            ", about='" + getAbout() + "'" +
            "}";
    }
}
