package com.rp.domain;

import com.rp.domain.enumeration.CandidatePipelineType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CandidatePipeline.
 */
@Entity
@Table(name = "candidate_pipeline")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidatePipeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "submission_status")
    private String submissionStatus;

    @Column(name = "submission_stage")
    private String submissionStage;

    @Column(name = "recruiter_actions")
    private String recruiterActions;

    @Column(name = "candidate_responses")
    private String candidateResponses;

    @Enumerated(EnumType.STRING)
    @Column(name = "pipeline_type")
    private CandidatePipelineType pipelineType;

    @Column(name = "reason_for_new_job")
    private String reasonForNewJob;

    @OneToOne
    @JoinColumn(unique = true)
    private CatalogueValue status;

    @OneToOne
    @JoinColumn(unique = true)
    private Job job;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CandidatePipeline id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public CandidatePipeline jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getStatusId() {
        return this.statusId;
    }

    public CandidatePipeline statusId(Long statusId) {
        this.setStatusId(statusId);
        return this;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getSubmissionStatus() {
        return this.submissionStatus;
    }

    public CandidatePipeline submissionStatus(String submissionStatus) {
        this.setSubmissionStatus(submissionStatus);
        return this;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public String getSubmissionStage() {
        return this.submissionStage;
    }

    public CandidatePipeline submissionStage(String submissionStage) {
        this.setSubmissionStage(submissionStage);
        return this;
    }

    public void setSubmissionStage(String submissionStage) {
        this.submissionStage = submissionStage;
    }

    public String getRecruiterActions() {
        return this.recruiterActions;
    }

    public CandidatePipeline recruiterActions(String recruiterActions) {
        this.setRecruiterActions(recruiterActions);
        return this;
    }

    public void setRecruiterActions(String recruiterActions) {
        this.recruiterActions = recruiterActions;
    }

    public String getCandidateResponses() {
        return this.candidateResponses;
    }

    public CandidatePipeline candidateResponses(String candidateResponses) {
        this.setCandidateResponses(candidateResponses);
        return this;
    }

    public void setCandidateResponses(String candidateResponses) {
        this.candidateResponses = candidateResponses;
    }

    public CandidatePipelineType getPipelineType() {
        return this.pipelineType;
    }

    public CandidatePipeline pipelineType(CandidatePipelineType pipelineType) {
        this.setPipelineType(pipelineType);
        return this;
    }

    public void setPipelineType(CandidatePipelineType pipelineType) {
        this.pipelineType = pipelineType;
    }

    public String getReasonForNewJob() {
        return this.reasonForNewJob;
    }

    public CandidatePipeline reasonForNewJob(String reasonForNewJob) {
        this.setReasonForNewJob(reasonForNewJob);
        return this;
    }

    public void setReasonForNewJob(String reasonForNewJob) {
        this.reasonForNewJob = reasonForNewJob;
    }

    public CatalogueValue getStatus() {
        return this.status;
    }

    public void setStatus(CatalogueValue catalogueValue) {
        this.status = catalogueValue;
    }

    public CandidatePipeline status(CatalogueValue catalogueValue) {
        this.setStatus(catalogueValue);
        return this;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public CandidatePipeline job(Job job) {
        this.setJob(job);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidatePipeline)) {
            return false;
        }
        return id != null && id.equals(((CandidatePipeline) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidatePipeline{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", statusId=" + getStatusId() +
            ", submissionStatus='" + getSubmissionStatus() + "'" +
            ", submissionStage='" + getSubmissionStage() + "'" +
            ", recruiterActions='" + getRecruiterActions() + "'" +
            ", candidateResponses='" + getCandidateResponses() + "'" +
            ", pipelineType='" + getPipelineType() + "'" +
            ", reasonForNewJob='" + getReasonForNewJob() + "'" +
            "}";
    }
}
