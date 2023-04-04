package com.rp.service;

import com.rp.domain.BusinessUnit;
import com.rp.repository.BusinessUnitRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessUnit}.
 */
@Service
@Transactional
public class BusinessUnitService {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitService.class);

    private final BusinessUnitRepository businessUnitRepository;

    public BusinessUnitService(BusinessUnitRepository businessUnitRepository) {
        this.businessUnitRepository = businessUnitRepository;
    }

    /**
     * Save a businessUnit.
     *
     * @param businessUnit the entity to save.
     * @return the persisted entity.
     */
    public BusinessUnit save(BusinessUnit businessUnit) {
        log.debug("Request to save BusinessUnit : {}", businessUnit);
        return businessUnitRepository.save(businessUnit);
    }

    /**
     * Update a businessUnit.
     *
     * @param businessUnit the entity to save.
     * @return the persisted entity.
     */
    public BusinessUnit update(BusinessUnit businessUnit) {
        log.debug("Request to update BusinessUnit : {}", businessUnit);
        return businessUnitRepository.save(businessUnit);
    }

    /**
     * Partially update a businessUnit.
     *
     * @param businessUnit the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BusinessUnit> partialUpdate(BusinessUnit businessUnit) {
        log.debug("Request to partially update BusinessUnit : {}", businessUnit);

        return businessUnitRepository
            .findById(businessUnit.getId())
            .map(existingBusinessUnit -> {
                if (businessUnit.getUnitName() != null) {
                    existingBusinessUnit.setUnitName(businessUnit.getUnitName());
                }
                if (businessUnit.getAddress() != null) {
                    existingBusinessUnit.setAddress(businessUnit.getAddress());
                }
                if (businessUnit.getMobileContact() != null) {
                    existingBusinessUnit.setMobileContact(businessUnit.getMobileContact());
                }
                if (businessUnit.getOfficeContact() != null) {
                    existingBusinessUnit.setOfficeContact(businessUnit.getOfficeContact());
                }
                if (businessUnit.getOfficeContactExtn() != null) {
                    existingBusinessUnit.setOfficeContactExtn(businessUnit.getOfficeContactExtn());
                }
                if (businessUnit.getArea() != null) {
                    existingBusinessUnit.setArea(businessUnit.getArea());
                }
                if (businessUnit.getCity() != null) {
                    existingBusinessUnit.setCity(businessUnit.getCity());
                }
                if (businessUnit.getState() != null) {
                    existingBusinessUnit.setState(businessUnit.getState());
                }
                if (businessUnit.getStateCode() != null) {
                    existingBusinessUnit.setStateCode(businessUnit.getStateCode());
                }
                if (businessUnit.getCountry() != null) {
                    existingBusinessUnit.setCountry(businessUnit.getCountry());
                }
                if (businessUnit.getCountryCode() != null) {
                    existingBusinessUnit.setCountryCode(businessUnit.getCountryCode());
                }
                if (businessUnit.getZipCode() != null) {
                    existingBusinessUnit.setZipCode(businessUnit.getZipCode());
                }
                if (businessUnit.getLat() != null) {
                    existingBusinessUnit.setLat(businessUnit.getLat());
                }
                if (businessUnit.getLon() != null) {
                    existingBusinessUnit.setLon(businessUnit.getLon());
                }
                if (businessUnit.getContinent() != null) {
                    existingBusinessUnit.setContinent(businessUnit.getContinent());
                }
                if (businessUnit.getContinentCode() != null) {
                    existingBusinessUnit.setContinentCode(businessUnit.getContinentCode());
                }
                if (businessUnit.getPoint() != null) {
                    existingBusinessUnit.setPoint(businessUnit.getPoint());
                }

                return existingBusinessUnit;
            })
            .map(businessUnitRepository::save);
    }

    /**
     * Get all the businessUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessUnit> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessUnits");
        return businessUnitRepository.findAll(pageable);
    }

    /**
     * Get one businessUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessUnit> findOne(Long id) {
        log.debug("Request to get BusinessUnit : {}", id);
        return businessUnitRepository.findById(id);
    }

    /**
     * Delete the businessUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessUnit : {}", id);
        businessUnitRepository.deleteById(id);
    }
}
