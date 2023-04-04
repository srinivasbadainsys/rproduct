package com.rp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessUnit.
 */
@Entity
@Table(name = "business_unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_contact")
    private String mobileContact;

    @Column(name = "office_contact")
    private String officeContact;

    @Column(name = "office_contact_extn")
    private String officeContactExtn;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BusinessUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public BusinessUnit unitName(String unitName) {
        this.setUnitName(unitName);
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAddress() {
        return this.address;
    }

    public BusinessUnit address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileContact() {
        return this.mobileContact;
    }

    public BusinessUnit mobileContact(String mobileContact) {
        this.setMobileContact(mobileContact);
        return this;
    }

    public void setMobileContact(String mobileContact) {
        this.mobileContact = mobileContact;
    }

    public String getOfficeContact() {
        return this.officeContact;
    }

    public BusinessUnit officeContact(String officeContact) {
        this.setOfficeContact(officeContact);
        return this;
    }

    public void setOfficeContact(String officeContact) {
        this.officeContact = officeContact;
    }

    public String getOfficeContactExtn() {
        return this.officeContactExtn;
    }

    public BusinessUnit officeContactExtn(String officeContactExtn) {
        this.setOfficeContactExtn(officeContactExtn);
        return this;
    }

    public void setOfficeContactExtn(String officeContactExtn) {
        this.officeContactExtn = officeContactExtn;
    }

    public String getArea() {
        return this.area;
    }

    public BusinessUnit area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return this.city;
    }

    public BusinessUnit city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public BusinessUnit state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public BusinessUnit stateCode(String stateCode) {
        this.setStateCode(stateCode);
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountry() {
        return this.country;
    }

    public BusinessUnit country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public BusinessUnit countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public BusinessUnit zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getLat() {
        return this.lat;
    }

    public BusinessUnit lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public BusinessUnit lon(Double lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getContinent() {
        return this.continent;
    }

    public BusinessUnit continent(String continent) {
        this.setContinent(continent);
        return this;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getContinentCode() {
        return this.continentCode;
    }

    public BusinessUnit continentCode(String continentCode) {
        this.setContinentCode(continentCode);
        return this;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public String getPoint() {
        return this.point;
    }

    public BusinessUnit point(String point) {
        this.setPoint(point);
        return this;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessUnit)) {
            return false;
        }
        return id != null && id.equals(((BusinessUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessUnit{" +
            "id=" + getId() +
            ", unitName='" + getUnitName() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobileContact='" + getMobileContact() + "'" +
            ", officeContact='" + getOfficeContact() + "'" +
            ", officeContactExtn='" + getOfficeContactExtn() + "'" +
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
