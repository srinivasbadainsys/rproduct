package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkspaceUser.
 */
@Entity
@Table(name = "workspace_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkspaceUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "invitation_key")
    private String invitationKey;

    @Column(name = "owns")
    private Boolean owns;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "invited")
    private Boolean invited;

    @Column(name = "requested")
    private Boolean requested;

    @Column(name = "barred")
    private Boolean barred;

    @Column(name = "roles")
    private String roles;

    @Column(name = "requested_on")
    private ZonedDateTime requestedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "workspaceUsers", "workspaceLocations", "teams" }, allowSetters = true)
    private Workspace workspace;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkspaceUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public WorkspaceUser userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInvitationKey() {
        return this.invitationKey;
    }

    public WorkspaceUser invitationKey(String invitationKey) {
        this.setInvitationKey(invitationKey);
        return this;
    }

    public void setInvitationKey(String invitationKey) {
        this.invitationKey = invitationKey;
    }

    public Boolean getOwns() {
        return this.owns;
    }

    public WorkspaceUser owns(Boolean owns) {
        this.setOwns(owns);
        return this;
    }

    public void setOwns(Boolean owns) {
        this.owns = owns;
    }

    public Boolean getAccepted() {
        return this.accepted;
    }

    public WorkspaceUser accepted(Boolean accepted) {
        this.setAccepted(accepted);
        return this;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getInvited() {
        return this.invited;
    }

    public WorkspaceUser invited(Boolean invited) {
        this.setInvited(invited);
        return this;
    }

    public void setInvited(Boolean invited) {
        this.invited = invited;
    }

    public Boolean getRequested() {
        return this.requested;
    }

    public WorkspaceUser requested(Boolean requested) {
        this.setRequested(requested);
        return this;
    }

    public void setRequested(Boolean requested) {
        this.requested = requested;
    }

    public Boolean getBarred() {
        return this.barred;
    }

    public WorkspaceUser barred(Boolean barred) {
        this.setBarred(barred);
        return this;
    }

    public void setBarred(Boolean barred) {
        this.barred = barred;
    }

    public String getRoles() {
        return this.roles;
    }

    public WorkspaceUser roles(String roles) {
        this.setRoles(roles);
        return this;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public ZonedDateTime getRequestedOn() {
        return this.requestedOn;
    }

    public WorkspaceUser requestedOn(ZonedDateTime requestedOn) {
        this.setRequestedOn(requestedOn);
        return this;
    }

    public void setRequestedOn(ZonedDateTime requestedOn) {
        this.requestedOn = requestedOn;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public WorkspaceUser workspace(Workspace workspace) {
        this.setWorkspace(workspace);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkspaceUser)) {
            return false;
        }
        return id != null && id.equals(((WorkspaceUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkspaceUser{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", invitationKey='" + getInvitationKey() + "'" +
            ", owns='" + getOwns() + "'" +
            ", accepted='" + getAccepted() + "'" +
            ", invited='" + getInvited() + "'" +
            ", requested='" + getRequested() + "'" +
            ", barred='" + getBarred() + "'" +
            ", roles='" + getRoles() + "'" +
            ", requestedOn='" + getRequestedOn() + "'" +
            "}";
    }
}
