package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClientDocument.
 */
@Entity
@Table(name = "client_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "document_path")
    private String documentPath;

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

    public ClientDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public ClientDocument clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getDocumentType() {
        return this.documentType;
    }

    public ClientDocument documentType(String documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getTitle() {
        return this.title;
    }

    public ClientDocument title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public ClientDocument description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentPath() {
        return this.documentPath;
    }

    public ClientDocument documentPath(String documentPath) {
        this.setDocumentPath(documentPath);
        return this;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientDocument client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDocument)) {
            return false;
        }
        return id != null && id.equals(((ClientDocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDocument{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", documentType='" + getDocumentType() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", documentPath='" + getDocumentPath() + "'" +
            "}";
    }
}
