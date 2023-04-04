package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Workspace.
 */
@Entity
@Table(name = "workspace")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "about")
    private String about;

    @Column(name = "link")
    private String link;

    @Column(name = "org_ur_ls")
    private String orgURLs;

    @Column(name = "owner_user_id")
    private Long ownerUserId;

    @Column(name = "main_phone_number")
    private String mainPhoneNumber;

    @Column(name = "alt_phone_numbers")
    private String altPhoneNumbers;

    @Column(name = "main_contact_email")
    private String mainContactEmail;

    @Column(name = "alt_contact_emails")
    private String altContactEmails;

    @Column(name = "status")
    private String status;

    @Column(name = "enable_auto_join")
    private Boolean enableAutoJoin;

    @Column(name = "max_users")
    private Integer maxUsers;

    @Column(name = "tags")
    private String tags;

    @Column(name = "domains")
    private String domains;

    @OneToMany(mappedBy = "workspace")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workspace" }, allowSetters = true)
    private Set<WorkspaceUser> workspaceUsers = new HashSet<>();

    @OneToMany(mappedBy = "workspace")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workspace" }, allowSetters = true)
    private Set<WorkspaceLocation> workspaceLocations = new HashSet<>();

    @OneToMany(mappedBy = "workspace")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "teamMembers", "workspace" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Workspace id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Workspace name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public Workspace orgName(String orgName) {
        this.setOrgName(orgName);
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAbout() {
        return this.about;
    }

    public Workspace about(String about) {
        this.setAbout(about);
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLink() {
        return this.link;
    }

    public Workspace link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOrgURLs() {
        return this.orgURLs;
    }

    public Workspace orgURLs(String orgURLs) {
        this.setOrgURLs(orgURLs);
        return this;
    }

    public void setOrgURLs(String orgURLs) {
        this.orgURLs = orgURLs;
    }

    public Long getOwnerUserId() {
        return this.ownerUserId;
    }

    public Workspace ownerUserId(Long ownerUserId) {
        this.setOwnerUserId(ownerUserId);
        return this;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getMainPhoneNumber() {
        return this.mainPhoneNumber;
    }

    public Workspace mainPhoneNumber(String mainPhoneNumber) {
        this.setMainPhoneNumber(mainPhoneNumber);
        return this;
    }

    public void setMainPhoneNumber(String mainPhoneNumber) {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    public String getAltPhoneNumbers() {
        return this.altPhoneNumbers;
    }

    public Workspace altPhoneNumbers(String altPhoneNumbers) {
        this.setAltPhoneNumbers(altPhoneNumbers);
        return this;
    }

    public void setAltPhoneNumbers(String altPhoneNumbers) {
        this.altPhoneNumbers = altPhoneNumbers;
    }

    public String getMainContactEmail() {
        return this.mainContactEmail;
    }

    public Workspace mainContactEmail(String mainContactEmail) {
        this.setMainContactEmail(mainContactEmail);
        return this;
    }

    public void setMainContactEmail(String mainContactEmail) {
        this.mainContactEmail = mainContactEmail;
    }

    public String getAltContactEmails() {
        return this.altContactEmails;
    }

    public Workspace altContactEmails(String altContactEmails) {
        this.setAltContactEmails(altContactEmails);
        return this;
    }

    public void setAltContactEmails(String altContactEmails) {
        this.altContactEmails = altContactEmails;
    }

    public String getStatus() {
        return this.status;
    }

    public Workspace status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getEnableAutoJoin() {
        return this.enableAutoJoin;
    }

    public Workspace enableAutoJoin(Boolean enableAutoJoin) {
        this.setEnableAutoJoin(enableAutoJoin);
        return this;
    }

    public void setEnableAutoJoin(Boolean enableAutoJoin) {
        this.enableAutoJoin = enableAutoJoin;
    }

    public Integer getMaxUsers() {
        return this.maxUsers;
    }

    public Workspace maxUsers(Integer maxUsers) {
        this.setMaxUsers(maxUsers);
        return this;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public String getTags() {
        return this.tags;
    }

    public Workspace tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDomains() {
        return this.domains;
    }

    public Workspace domains(String domains) {
        this.setDomains(domains);
        return this;
    }

    public void setDomains(String domains) {
        this.domains = domains;
    }

    public Set<WorkspaceUser> getWorkspaceUsers() {
        return this.workspaceUsers;
    }

    public void setWorkspaceUsers(Set<WorkspaceUser> workspaceUsers) {
        if (this.workspaceUsers != null) {
            this.workspaceUsers.forEach(i -> i.setWorkspace(null));
        }
        if (workspaceUsers != null) {
            workspaceUsers.forEach(i -> i.setWorkspace(this));
        }
        this.workspaceUsers = workspaceUsers;
    }

    public Workspace workspaceUsers(Set<WorkspaceUser> workspaceUsers) {
        this.setWorkspaceUsers(workspaceUsers);
        return this;
    }

    public Workspace addWorkspaceUser(WorkspaceUser workspaceUser) {
        this.workspaceUsers.add(workspaceUser);
        workspaceUser.setWorkspace(this);
        return this;
    }

    public Workspace removeWorkspaceUser(WorkspaceUser workspaceUser) {
        this.workspaceUsers.remove(workspaceUser);
        workspaceUser.setWorkspace(null);
        return this;
    }

    public Set<WorkspaceLocation> getWorkspaceLocations() {
        return this.workspaceLocations;
    }

    public void setWorkspaceLocations(Set<WorkspaceLocation> workspaceLocations) {
        if (this.workspaceLocations != null) {
            this.workspaceLocations.forEach(i -> i.setWorkspace(null));
        }
        if (workspaceLocations != null) {
            workspaceLocations.forEach(i -> i.setWorkspace(this));
        }
        this.workspaceLocations = workspaceLocations;
    }

    public Workspace workspaceLocations(Set<WorkspaceLocation> workspaceLocations) {
        this.setWorkspaceLocations(workspaceLocations);
        return this;
    }

    public Workspace addWorkspaceLocation(WorkspaceLocation workspaceLocation) {
        this.workspaceLocations.add(workspaceLocation);
        workspaceLocation.setWorkspace(this);
        return this;
    }

    public Workspace removeWorkspaceLocation(WorkspaceLocation workspaceLocation) {
        this.workspaceLocations.remove(workspaceLocation);
        workspaceLocation.setWorkspace(null);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setWorkspace(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setWorkspace(this));
        }
        this.teams = teams;
    }

    public Workspace teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Workspace addTeam(Team team) {
        this.teams.add(team);
        team.setWorkspace(this);
        return this;
    }

    public Workspace removeTeam(Team team) {
        this.teams.remove(team);
        team.setWorkspace(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workspace)) {
            return false;
        }
        return id != null && id.equals(((Workspace) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Workspace{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", about='" + getAbout() + "'" +
            ", link='" + getLink() + "'" +
            ", orgURLs='" + getOrgURLs() + "'" +
            ", ownerUserId=" + getOwnerUserId() +
            ", mainPhoneNumber='" + getMainPhoneNumber() + "'" +
            ", altPhoneNumbers='" + getAltPhoneNumbers() + "'" +
            ", mainContactEmail='" + getMainContactEmail() + "'" +
            ", altContactEmails='" + getAltContactEmails() + "'" +
            ", status='" + getStatus() + "'" +
            ", enableAutoJoin='" + getEnableAutoJoin() + "'" +
            ", maxUsers=" + getMaxUsers() +
            ", tags='" + getTags() + "'" +
            ", domains='" + getDomains() + "'" +
            "}";
    }
}
