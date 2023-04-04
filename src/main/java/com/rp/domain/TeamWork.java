package com.rp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TeamWork.
 */
@Entity
@Table(name = "team_work")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamWork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "team_id")
    private Long teamId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamWork id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public TeamWork jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getTeamId() {
        return this.teamId;
    }

    public TeamWork teamId(Long teamId) {
        this.setTeamId(teamId);
        return this;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamWork)) {
            return false;
        }
        return id != null && id.equals(((TeamWork) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamWork{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", teamId=" + getTeamId() +
            "}";
    }
}
