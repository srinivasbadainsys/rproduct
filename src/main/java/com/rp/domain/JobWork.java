package com.rp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobWork.
 */
@Entity
@Table(name = "job_work")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobWork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "assigned_to_teams")
    private String assignedToTeams;

    @Column(name = "assigned_to_users")
    private String assignedToUsers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobWork id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public JobWork jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getAssignedToTeams() {
        return this.assignedToTeams;
    }

    public JobWork assignedToTeams(String assignedToTeams) {
        this.setAssignedToTeams(assignedToTeams);
        return this;
    }

    public void setAssignedToTeams(String assignedToTeams) {
        this.assignedToTeams = assignedToTeams;
    }

    public String getAssignedToUsers() {
        return this.assignedToUsers;
    }

    public JobWork assignedToUsers(String assignedToUsers) {
        this.setAssignedToUsers(assignedToUsers);
        return this;
    }

    public void setAssignedToUsers(String assignedToUsers) {
        this.assignedToUsers = assignedToUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobWork)) {
            return false;
        }
        return id != null && id.equals(((JobWork) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobWork{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", assignedToTeams='" + getAssignedToTeams() + "'" +
            ", assignedToUsers='" + getAssignedToUsers() + "'" +
            "}";
    }
}
