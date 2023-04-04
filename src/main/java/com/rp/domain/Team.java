package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rp.domain.enumeration.TeamType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "team_group_email")
    private String teamGroupEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TeamType type;

    @Column(name = "notify_on_job_posting_to_users")
    private String notifyOnJobPostingToUsers;

    @Column(name = "notify_on_job_sharing_to_users")
    private String notifyOnJobSharingToUsers;

    @Column(name = "notify_on_job_closing_to_users")
    private String notifyOnJobClosingToUsers;

    @Column(name = "notify_on_cand_submission_to_users")
    private String notifyOnCandSubmissionToUsers;

    @Column(name = "notify_on_cand_status_change_to_users")
    private String notifyOnCandStatusChangeToUsers;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "memberUser", "team" }, allowSetters = true)
    private Set<TeamMember> teamMembers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "workspaceUsers", "workspaceLocations", "teams" }, allowSetters = true)
    private Workspace workspace;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Team name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamGroupEmail() {
        return this.teamGroupEmail;
    }

    public Team teamGroupEmail(String teamGroupEmail) {
        this.setTeamGroupEmail(teamGroupEmail);
        return this;
    }

    public void setTeamGroupEmail(String teamGroupEmail) {
        this.teamGroupEmail = teamGroupEmail;
    }

    public TeamType getType() {
        return this.type;
    }

    public Team type(TeamType type) {
        this.setType(type);
        return this;
    }

    public void setType(TeamType type) {
        this.type = type;
    }

    public String getNotifyOnJobPostingToUsers() {
        return this.notifyOnJobPostingToUsers;
    }

    public Team notifyOnJobPostingToUsers(String notifyOnJobPostingToUsers) {
        this.setNotifyOnJobPostingToUsers(notifyOnJobPostingToUsers);
        return this;
    }

    public void setNotifyOnJobPostingToUsers(String notifyOnJobPostingToUsers) {
        this.notifyOnJobPostingToUsers = notifyOnJobPostingToUsers;
    }

    public String getNotifyOnJobSharingToUsers() {
        return this.notifyOnJobSharingToUsers;
    }

    public Team notifyOnJobSharingToUsers(String notifyOnJobSharingToUsers) {
        this.setNotifyOnJobSharingToUsers(notifyOnJobSharingToUsers);
        return this;
    }

    public void setNotifyOnJobSharingToUsers(String notifyOnJobSharingToUsers) {
        this.notifyOnJobSharingToUsers = notifyOnJobSharingToUsers;
    }

    public String getNotifyOnJobClosingToUsers() {
        return this.notifyOnJobClosingToUsers;
    }

    public Team notifyOnJobClosingToUsers(String notifyOnJobClosingToUsers) {
        this.setNotifyOnJobClosingToUsers(notifyOnJobClosingToUsers);
        return this;
    }

    public void setNotifyOnJobClosingToUsers(String notifyOnJobClosingToUsers) {
        this.notifyOnJobClosingToUsers = notifyOnJobClosingToUsers;
    }

    public String getNotifyOnCandSubmissionToUsers() {
        return this.notifyOnCandSubmissionToUsers;
    }

    public Team notifyOnCandSubmissionToUsers(String notifyOnCandSubmissionToUsers) {
        this.setNotifyOnCandSubmissionToUsers(notifyOnCandSubmissionToUsers);
        return this;
    }

    public void setNotifyOnCandSubmissionToUsers(String notifyOnCandSubmissionToUsers) {
        this.notifyOnCandSubmissionToUsers = notifyOnCandSubmissionToUsers;
    }

    public String getNotifyOnCandStatusChangeToUsers() {
        return this.notifyOnCandStatusChangeToUsers;
    }

    public Team notifyOnCandStatusChangeToUsers(String notifyOnCandStatusChangeToUsers) {
        this.setNotifyOnCandStatusChangeToUsers(notifyOnCandStatusChangeToUsers);
        return this;
    }

    public void setNotifyOnCandStatusChangeToUsers(String notifyOnCandStatusChangeToUsers) {
        this.notifyOnCandStatusChangeToUsers = notifyOnCandStatusChangeToUsers;
    }

    public Set<TeamMember> getTeamMembers() {
        return this.teamMembers;
    }

    public void setTeamMembers(Set<TeamMember> teamMembers) {
        if (this.teamMembers != null) {
            this.teamMembers.forEach(i -> i.setTeam(null));
        }
        if (teamMembers != null) {
            teamMembers.forEach(i -> i.setTeam(this));
        }
        this.teamMembers = teamMembers;
    }

    public Team teamMembers(Set<TeamMember> teamMembers) {
        this.setTeamMembers(teamMembers);
        return this;
    }

    public Team addTeamMember(TeamMember teamMember) {
        this.teamMembers.add(teamMember);
        teamMember.setTeam(this);
        return this;
    }

    public Team removeTeamMember(TeamMember teamMember) {
        this.teamMembers.remove(teamMember);
        teamMember.setTeam(null);
        return this;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Team workspace(Workspace workspace) {
        this.setWorkspace(workspace);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", teamGroupEmail='" + getTeamGroupEmail() + "'" +
            ", type='" + getType() + "'" +
            ", notifyOnJobPostingToUsers='" + getNotifyOnJobPostingToUsers() + "'" +
            ", notifyOnJobSharingToUsers='" + getNotifyOnJobSharingToUsers() + "'" +
            ", notifyOnJobClosingToUsers='" + getNotifyOnJobClosingToUsers() + "'" +
            ", notifyOnCandSubmissionToUsers='" + getNotifyOnCandSubmissionToUsers() + "'" +
            ", notifyOnCandStatusChangeToUsers='" + getNotifyOnCandStatusChangeToUsers() + "'" +
            "}";
    }
}
