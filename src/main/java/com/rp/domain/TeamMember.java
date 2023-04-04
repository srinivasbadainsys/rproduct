package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TeamMember.
 */
@Entity
@Table(name = "team_member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "team_id")
    private Long teamId;

    @OneToOne
    @JoinColumn(unique = true)
    private Ruser memberUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "teamMembers", "workspace" }, allowSetters = true)
    private Team team;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamId() {
        return this.teamId;
    }

    public TeamMember teamId(Long teamId) {
        this.setTeamId(teamId);
        return this;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Ruser getMemberUser() {
        return this.memberUser;
    }

    public void setMemberUser(Ruser ruser) {
        this.memberUser = ruser;
    }

    public TeamMember memberUser(Ruser ruser) {
        this.setMemberUser(ruser);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamMember team(Team team) {
        this.setTeam(team);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamMember)) {
            return false;
        }
        return id != null && id.equals(((TeamMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamMember{" +
            "id=" + getId() +
            ", teamId=" + getTeamId() +
            "}";
    }
}
