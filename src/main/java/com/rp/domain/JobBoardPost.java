package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rp.domain.enumeration.JobBoardPostStatus;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobBoardPost.
 */
@Entity
@Table(name = "job_board_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobBoardPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobBoardPostStatus status;

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
    @OneToOne
    @JoinColumn(unique = true)
    private Job job;

    @OneToOne
    @JoinColumn(unique = true)
    private JobBoard jobBoard;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobBoardPost id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public JobBoardPost jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public JobBoardPostStatus getStatus() {
        return this.status;
    }

    public JobBoardPost status(JobBoardPostStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(JobBoardPostStatus status) {
        this.status = status;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobBoardPost job(Job job) {
        this.setJob(job);
        return this;
    }

    public JobBoard getJobBoard() {
        return this.jobBoard;
    }

    public void setJobBoard(JobBoard jobBoard) {
        this.jobBoard = jobBoard;
    }

    public JobBoardPost jobBoard(JobBoard jobBoard) {
        this.setJobBoard(jobBoard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobBoardPost)) {
            return false;
        }
        return id != null && id.equals(((JobBoardPost) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobBoardPost{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
