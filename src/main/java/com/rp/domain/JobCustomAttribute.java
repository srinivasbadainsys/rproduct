package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rp.domain.enumeration.JobAttributeType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobCustomAttribute.
 */
@Entity
@Table(name = "job_custom_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobCustomAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "attribute_type")
    private JobAttributeType attributeType;

    @Column(name = "attribute_value")
    private String attributeValue;

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

    public JobCustomAttribute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public JobCustomAttribute jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public JobCustomAttribute attributeName(String attributeName) {
        this.setAttributeName(attributeName);
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public JobAttributeType getAttributeType() {
        return this.attributeType;
    }

    public JobCustomAttribute attributeType(JobAttributeType attributeType) {
        this.setAttributeType(attributeType);
        return this;
    }

    public void setAttributeType(JobAttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public JobCustomAttribute attributeValue(String attributeValue) {
        this.setAttributeValue(attributeValue);
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobCustomAttribute job(Job job) {
        this.setJob(job);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobCustomAttribute)) {
            return false;
        }
        return id != null && id.equals(((JobCustomAttribute) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobCustomAttribute{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeType='" + getAttributeType() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
