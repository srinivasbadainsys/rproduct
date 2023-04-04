package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserWork.
 */
@Entity
@Table(name = "user_work")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserWork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser user;

    @JsonIgnoreProperties(value = { "teamMembers", "workspace" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Team team;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserWork id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public UserWork jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Ruser getUser() {
        return this.user;
    }

    public void setUser(Ruser ruser) {
        this.user = ruser;
    }

    public UserWork user(Ruser ruser) {
        this.setUser(ruser);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UserWork team(Team team) {
        this.setTeam(team);
        return this;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public UserWork job(Job job) {
        this.setJob(job);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserWork)) {
            return false;
        }
        return id != null && id.equals(((UserWork) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserWork{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            "}";
    }
}
