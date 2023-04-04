package com.rp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobLocation.
 */
@Entity
@Table(name = "job_location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "area")
    private String area;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "country")
    private String country;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "continent")
    private String continent;

    @Column(name = "continent_code")
    private String continentCode;

    @Column(name = "point")
    private String point;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "postedByTeam",
            "businessUnit",
            "client",
            "jobType",
            "industry",
            "department",
            "clientRecruiter",
            "clientManager",
            "accountManager",
            "headOfRecruitment",
            "deliveryLeadManager",
            "domain",
            "srDeliveryManager",
            "teamLead",
            "priority",
            "jobCloseReason",
            "jobCategory",
            "jobOccupation",
            "jobLocations",
            "jobDocuments",
            "jobCustomAttributes",
        },
        allowSetters = true
    )
    private Job job;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobLocation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public JobLocation jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getArea() {
        return this.area;
    }

    public JobLocation area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return this.city;
    }

    public JobLocation city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public JobLocation state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public JobLocation stateCode(String stateCode) {
        this.setStateCode(stateCode);
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountry() {
        return this.country;
    }

    public JobLocation country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public JobLocation countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public JobLocation zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getLat() {
        return this.lat;
    }

    public JobLocation lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public JobLocation lon(Double lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getContinent() {
        return this.continent;
    }

    public JobLocation continent(String continent) {
        this.setContinent(continent);
        return this;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getContinentCode() {
        return this.continentCode;
    }

    public JobLocation continentCode(String continentCode) {
        this.setContinentCode(continentCode);
        return this;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public String getPoint() {
        return this.point;
    }

    public JobLocation point(String point) {
        this.setPoint(point);
        return this;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobLocation job(Job job) {
        this.setJob(job);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobLocation)) {
            return false;
        }
        return id != null && id.equals(((JobLocation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobLocation{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", area='" + getArea() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", continent='" + getContinent() + "'" +
            ", continentCode='" + getContinentCode() + "'" +
            ", point='" + getPoint() + "'" +
            "}";
    }
}
