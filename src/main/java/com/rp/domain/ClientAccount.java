package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClientAccount.
 */
@Entity
@Table(name = "client_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "office_number")
    private String officeNumber;

    @Column(name = "office_number_extn")
    private String officeNumberExtn;

    @Column(name = "email_id")
    private String emailID;

    @Column(name = "designation")
    private String designation;

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

    public ClientAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public ClientAccount clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public ClientAccount contactPerson(String contactPerson) {
        this.setContactPerson(contactPerson);
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public ClientAccount mobileNumber(String mobileNumber) {
        this.setMobileNumber(mobileNumber);
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOfficeNumber() {
        return this.officeNumber;
    }

    public ClientAccount officeNumber(String officeNumber) {
        this.setOfficeNumber(officeNumber);
        return this;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getOfficeNumberExtn() {
        return this.officeNumberExtn;
    }

    public ClientAccount officeNumberExtn(String officeNumberExtn) {
        this.setOfficeNumberExtn(officeNumberExtn);
        return this;
    }

    public void setOfficeNumberExtn(String officeNumberExtn) {
        this.officeNumberExtn = officeNumberExtn;
    }

    public String getEmailID() {
        return this.emailID;
    }

    public ClientAccount emailID(String emailID) {
        this.setEmailID(emailID);
        return this;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getDesignation() {
        return this.designation;
    }

    public ClientAccount designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientAccount client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientAccount)) {
            return false;
        }
        return id != null && id.equals(((ClientAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientAccount{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", contactPerson='" + getContactPerson() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", officeNumber='" + getOfficeNumber() + "'" +
            ", officeNumberExtn='" + getOfficeNumberExtn() + "'" +
            ", emailID='" + getEmailID() + "'" +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
