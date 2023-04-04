package com.rp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobBoard.
 */
@Entity
@Table(name = "job_board")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_board_name")
    private String jobBoardName;

    @Column(name = "job_board_type")
    private String jobBoardType;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "settings")
    private String settings;

    @Column(name = "auto_refresh")
    private String autoRefresh;

    @Column(name = "job_duration")
    private String jobDuration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobBoard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobBoardName() {
        return this.jobBoardName;
    }

    public JobBoard jobBoardName(String jobBoardName) {
        this.setJobBoardName(jobBoardName);
        return this;
    }

    public void setJobBoardName(String jobBoardName) {
        this.jobBoardName = jobBoardName;
    }

    public String getJobBoardType() {
        return this.jobBoardType;
    }

    public JobBoard jobBoardType(String jobBoardType) {
        this.setJobBoardType(jobBoardType);
        return this;
    }

    public void setJobBoardType(String jobBoardType) {
        this.jobBoardType = jobBoardType;
    }

    public String getUsername() {
        return this.username;
    }

    public JobBoard username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public JobBoard password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSettings() {
        return this.settings;
    }

    public JobBoard settings(String settings) {
        this.setSettings(settings);
        return this;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getAutoRefresh() {
        return this.autoRefresh;
    }

    public JobBoard autoRefresh(String autoRefresh) {
        this.setAutoRefresh(autoRefresh);
        return this;
    }

    public void setAutoRefresh(String autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public String getJobDuration() {
        return this.jobDuration;
    }

    public JobBoard jobDuration(String jobDuration) {
        this.setJobDuration(jobDuration);
        return this;
    }

    public void setJobDuration(String jobDuration) {
        this.jobDuration = jobDuration;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobBoard)) {
            return false;
        }
        return id != null && id.equals(((JobBoard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobBoard{" +
            "id=" + getId() +
            ", jobBoardName='" + getJobBoardName() + "'" +
            ", jobBoardType='" + getJobBoardType() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", settings='" + getSettings() + "'" +
            ", autoRefresh='" + getAutoRefresh() + "'" +
            ", jobDuration='" + getJobDuration() + "'" +
            "}";
    }
}
