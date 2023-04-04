package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobDocument.
 */
@Entity
@Table(name = "job_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "document_title")
    private String documentTitle;

    @Column(name = "document_location")
    private String documentLocation;

    @Column(name = "document_password")
    private String documentPassword;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "postedByTeam",
            "businessUnit",
            "client",
            "jobType",
            "industry",
            "department",
            "clientRecruiter",
            "clientManager",
            "accountManager",
            "headOfRecruitment",
            "deliveryLeadManager",
            "domain",
            "srDeliveryManager",
            "teamLead",
            "priority",
            "jobCloseReason",
            "jobCategory",
            "jobOccupation",
            "jobLocations",
            "jobDocuments",
            "jobCustomAttributes",
        },
        allowSetters = true
    )
    private Job job;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public JobDocument jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getDocumentTitle() {
        return this.documentTitle;
    }

    public JobDocument documentTitle(String documentTitle) {
        this.setDocumentTitle(documentTitle);
        return this;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentLocation() {
        return this.documentLocation;
    }

    public JobDocument documentLocation(String documentLocation) {
        this.setDocumentLocation(documentLocation);
        return this;
    }

    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
    }

    public String getDocumentPassword() {
        return this.documentPassword;
    }

    public JobDocument documentPassword(String documentPassword) {
        this.setDocumentPassword(documentPassword);
        return this;
    }

    public void setDocumentPassword(String documentPassword) {
        this.documentPassword = documentPassword;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobDocument job(Job job) {
        this.setJob(job);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobDocument)) {
            return false;
        }
        return id != null && id.equals(((JobDocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobDocument{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", documentTitle='" + getDocumentTitle() + "'" +
            ", documentLocation='" + getDocumentLocation() + "'" +
            ", documentPassword='" + getDocumentPassword() + "'" +
            "}";
    }
}
