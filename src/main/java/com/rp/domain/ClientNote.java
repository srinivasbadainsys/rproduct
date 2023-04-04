package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClientNote.
 */
@Entity
@Table(name = "client_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "action")
    private String action;

    @Column(name = "notes_priority")
    private String notesPriority;

    @Column(name = "note")
    private String note;

    @Column(name = "notify_to_users")
    private String notifyToUsers;

    @Column(name = "remind_me")
    private Boolean remindMe;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "primaryBusinessUnit",
            "industry",
            "category",
            "paymentTerms",
            "practice",
            "primaryOwnerUser",
            "clientAccounts",
            "clientNotes",
            "clientDocuments",
            "contacts",
            "clientGuidelineSubmissionDocuments",
        },
        allowSetters = true
    )
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientNote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public ClientNote clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getAction() {
        return this.action;
    }

    public ClientNote action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNotesPriority() {
        return this.notesPriority;
    }

    public ClientNote notesPriority(String notesPriority) {
        this.setNotesPriority(notesPriority);
        return this;
    }

    public void setNotesPriority(String notesPriority) {
        this.notesPriority = notesPriority;
    }

    public String getNote() {
        return this.note;
    }

    public ClientNote note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNotifyToUsers() {
        return this.notifyToUsers;
    }

    public ClientNote notifyToUsers(String notifyToUsers) {
        this.setNotifyToUsers(notifyToUsers);
        return this;
    }

    public void setNotifyToUsers(String notifyToUsers) {
        this.notifyToUsers = notifyToUsers;
    }

    public Boolean getRemindMe() {
        return this.remindMe;
    }

    public ClientNote remindMe(Boolean remindMe) {
        this.setRemindMe(remindMe);
        return this;
    }

    public void setRemindMe(Boolean remindMe) {
        this.remindMe = remindMe;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientNote client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientNote)) {
            return false;
        }
        return id != null && id.equals(((ClientNote) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientNote{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", action='" + getAction() + "'" +
            ", notesPriority='" + getNotesPriority() + "'" +
            ", note='" + getNote() + "'" +
            ", notifyToUsers='" + getNotifyToUsers() + "'" +
            ", remindMe='" + getRemindMe() + "'" +
            "}";
    }
}
