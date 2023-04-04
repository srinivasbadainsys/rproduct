package com.rp.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobBoardSharedTo.
 */
@Entity
@Table(name = "job_board_shared_to")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobBoardSharedTo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_board_id")
    private Long jobBoardId;

    @Column(name = "shared_to_emails")
    private String sharedToEmails;

    @Column(name = "shared_to_user_ids")
    private String sharedToUserIds;

    @Column(name = "shared_to_team_ids")
    private String sharedToTeamIds;

    @Column(name = "max_examils_monthly")
    private Integer maxExamilsMonthly;

    @Column(name = "expires_on")
    private ZonedDateTime expiresOn;

    @OneToOne
    @JoinColumn(unique = true)
    private JobBoard jobBoard;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobBoardSharedTo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobBoardId() {
        return this.jobBoardId;
    }

    public JobBoardSharedTo jobBoardId(Long jobBoardId) {
        this.setJobBoardId(jobBoardId);
        return this;
    }

    public void setJobBoardId(Long jobBoardId) {
        this.jobBoardId = jobBoardId;
    }

    public String getSharedToEmails() {
        return this.sharedToEmails;
    }

    public JobBoardSharedTo sharedToEmails(String sharedToEmails) {
        this.setSharedToEmails(sharedToEmails);
        return this;
    }

    public void setSharedToEmails(String sharedToEmails) {
        this.sharedToEmails = sharedToEmails;
    }

    public String getSharedToUserIds() {
        return this.sharedToUserIds;
    }

    public JobBoardSharedTo sharedToUserIds(String sharedToUserIds) {
        this.setSharedToUserIds(sharedToUserIds);
        return this;
    }

    public void setSharedToUserIds(String sharedToUserIds) {
        this.sharedToUserIds = sharedToUserIds;
    }

    public String getSharedToTeamIds() {
        return this.sharedToTeamIds;
    }

    public JobBoardSharedTo sharedToTeamIds(String sharedToTeamIds) {
        this.setSharedToTeamIds(sharedToTeamIds);
        return this;
    }

    public void setSharedToTeamIds(String sharedToTeamIds) {
        this.sharedToTeamIds = sharedToTeamIds;
    }

    public Integer getMaxExamilsMonthly() {
        return this.maxExamilsMonthly;
    }

    public JobBoardSharedTo maxExamilsMonthly(Integer maxExamilsMonthly) {
        this.setMaxExamilsMonthly(maxExamilsMonthly);
        return this;
    }

    public void setMaxExamilsMonthly(Integer maxExamilsMonthly) {
        this.maxExamilsMonthly = maxExamilsMonthly;
    }

    public ZonedDateTime getExpiresOn() {
        return this.expiresOn;
    }

    public JobBoardSharedTo expiresOn(ZonedDateTime expiresOn) {
        this.setExpiresOn(expiresOn);
        return this;
    }

    public void setExpiresOn(ZonedDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public JobBoard getJobBoard() {
        return this.jobBoard;
    }

    public void setJobBoard(JobBoard jobBoard) {
        this.jobBoard = jobBoard;
    }

    public JobBoardSharedTo jobBoard(JobBoard jobBoard) {
        this.setJobBoard(jobBoard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobBoardSharedTo)) {
            return false;
        }
        return id != null && id.equals(((JobBoardSharedTo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobBoardSharedTo{" +
            "id=" + getId() +
            ", jobBoardId=" + getJobBoardId() +
            ", sharedToEmails='" + getSharedToEmails() + "'" +
            ", sharedToUserIds='" + getSharedToUserIds() + "'" +
            ", sharedToTeamIds='" + getSharedToTeamIds() + "'" +
            ", maxExamilsMonthly=" + getMaxExamilsMonthly() +
            ", expiresOn='" + getExpiresOn() + "'" +
            "}";
    }
}
