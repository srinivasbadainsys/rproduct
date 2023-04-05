package com.rp.service;

import com.rp.domain.Candidate;
import com.rp.repository.CandidateRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Candidate}.
 */
@Service
@Transactional
public class CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateService.class);

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    /**
     * Save a candidate.
     *
     * @param candidate the entity to save.
     * @return the persisted entity.
     */
    public Candidate save(Candidate candidate) {
        log.debug("Request to save Candidate : {}", candidate);
        return candidateRepository.save(candidate);
    }

    /**
     * Update a candidate.
     *
     * @param candidate the entity to save.
     * @return the persisted entity.
     */
    public Candidate update(Candidate candidate) {
        log.debug("Request to update Candidate : {}", candidate);
        return candidateRepository.save(candidate);
    }

    /**
     * Partially update a candidate.
     *
     * @param candidate the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Candidate> partialUpdate(Candidate candidate) {
        log.debug("Request to partially update Candidate : {}", candidate);

        return candidateRepository
            .findById(candidate.getId())
            .map(existingCandidate -> {
                if (candidate.getSalutation() != null) {
                    existingCandidate.setSalutation(candidate.getSalutation());
                }
                if (candidate.getFirstName() != null) {
                    existingCandidate.setFirstName(candidate.getFirstName());
                }
                if (candidate.getMiddleName() != null) {
                    existingCandidate.setMiddleName(candidate.getMiddleName());
                }
                if (candidate.getLastName() != null) {
                    existingCandidate.setLastName(candidate.getLastName());
                }
                if (candidate.getEmail() != null) {
                    existingCandidate.setEmail(candidate.getEmail());
                }
                if (candidate.getAltEmails() != null) {
                    existingCandidate.setAltEmails(candidate.getAltEmails());
                }
                if (candidate.getPhone() != null) {
                    existingCandidate.setPhone(candidate.getPhone());
                }
                if (candidate.getAltPhones() != null) {
                    existingCandidate.setAltPhones(candidate.getAltPhones());
                }
                if (candidate.getDob() != null) {
                    existingCandidate.setDob(candidate.getDob());
                }
                if (candidate.getNationality() != null) {
                    existingCandidate.setNationality(candidate.getNationality());
                }
                if (candidate.getWorkAuthorizationId() != null) {
                    existingCandidate.setWorkAuthorizationId(candidate.getWorkAuthorizationId());
                }
                if (candidate.getAddress() != null) {
                    existingCandidate.setAddress(candidate.getAddress());
                }
                if (candidate.getArea() != null) {
                    existingCandidate.setArea(candidate.getArea());
                }
                if (candidate.getCity() != null) {
                    existingCandidate.setCity(candidate.getCity());
                }
                if (candidate.getState() != null) {
                    existingCandidate.setState(candidate.getState());
                }
                if (candidate.getStateCode() != null) {
                    existingCandidate.setStateCode(candidate.getStateCode());
                }
                if (candidate.getCounty() != null) {
                    existingCandidate.setCounty(candidate.getCounty());
                }
                if (candidate.getCountry() != null) {
                    existingCandidate.setCountry(candidate.getCountry());
                }
                if (candidate.getCountryCode() != null) {
                    existingCandidate.setCountryCode(candidate.getCountryCode());
                }
                if (candidate.getZipCode() != null) {
                    existingCandidate.setZipCode(candidate.getZipCode());
                }
                if (candidate.getSource() != null) {
                    existingCandidate.setSource(candidate.getSource());
                }
                if (candidate.getTotalExpInYears() != null) {
                    existingCandidate.setTotalExpInYears(candidate.getTotalExpInYears());
                }
                if (candidate.getTotalExpInMonths() != null) {
                    existingCandidate.setTotalExpInMonths(candidate.getTotalExpInMonths());
                }
                if (candidate.getRelevantExpInYears() != null) {
                    existingCandidate.setRelevantExpInYears(candidate.getRelevantExpInYears());
                }
                if (candidate.getRelevantExpInMonths() != null) {
                    existingCandidate.setRelevantExpInMonths(candidate.getRelevantExpInMonths());
                }
                if (candidate.getReferredBy() != null) {
                    existingCandidate.setReferredBy(candidate.getReferredBy());
                }
                if (candidate.getOwnershipUserId() != null) {
                    existingCandidate.setOwnershipUserId(candidate.getOwnershipUserId());
                }
                if (candidate.getCurrentJobTitle() != null) {
                    existingCandidate.setCurrentJobTitle(candidate.getCurrentJobTitle());
                }
                if (candidate.getCurrentEmployer() != null) {
                    existingCandidate.setCurrentEmployer(candidate.getCurrentEmployer());
                }
                if (candidate.getCurrentJobTypeId() != null) {
                    existingCandidate.setCurrentJobTypeId(candidate.getCurrentJobTypeId());
                }
                if (candidate.getCurrentPayCurrency() != null) {
                    existingCandidate.setCurrentPayCurrency(candidate.getCurrentPayCurrency());
                }
                if (candidate.getCurrentPay() != null) {
                    existingCandidate.setCurrentPay(candidate.getCurrentPay());
                }
                if (candidate.getCurrentPayMonthly() != null) {
                    existingCandidate.setCurrentPayMonthly(candidate.getCurrentPayMonthly());
                }
                if (candidate.getCurrentPayHourly() != null) {
                    existingCandidate.setCurrentPayHourly(candidate.getCurrentPayHourly());
                }
                if (candidate.getCurrentPayYearly() != null) {
                    existingCandidate.setCurrentPayYearly(candidate.getCurrentPayYearly());
                }
                if (candidate.getCurrentPayTimeSpan() != null) {
                    existingCandidate.setCurrentPayTimeSpan(candidate.getCurrentPayTimeSpan());
                }
                if (candidate.getOtherCurrentBenefits() != null) {
                    existingCandidate.setOtherCurrentBenefits(candidate.getOtherCurrentBenefits());
                }
                if (candidate.getExpectedPayCurrency() != null) {
                    existingCandidate.setExpectedPayCurrency(candidate.getExpectedPayCurrency());
                }
                if (candidate.getExpectedPayMin() != null) {
                    existingCandidate.setExpectedPayMin(candidate.getExpectedPayMin());
                }
                if (candidate.getExpectedPayMax() != null) {
                    existingCandidate.setExpectedPayMax(candidate.getExpectedPayMax());
                }
                if (candidate.getExpectedPayMinMonthly() != null) {
                    existingCandidate.setExpectedPayMinMonthly(candidate.getExpectedPayMinMonthly());
                }
                if (candidate.getExpectedPayMinHourly() != null) {
                    existingCandidate.setExpectedPayMinHourly(candidate.getExpectedPayMinHourly());
                }
                if (candidate.getExpectedPayMinYearly() != null) {
                    existingCandidate.setExpectedPayMinYearly(candidate.getExpectedPayMinYearly());
                }
                if (candidate.getExpectedPayMaxMonthly() != null) {
                    existingCandidate.setExpectedPayMaxMonthly(candidate.getExpectedPayMaxMonthly());
                }
                if (candidate.getExpectedPayMaxHourly() != null) {
                    existingCandidate.setExpectedPayMaxHourly(candidate.getExpectedPayMaxHourly());
                }
                if (candidate.getExpectedPayMaxYearly() != null) {
                    existingCandidate.setExpectedPayMaxYearly(candidate.getExpectedPayMaxYearly());
                }
                if (candidate.getExpectedPayTimeSpan() != null) {
                    existingCandidate.setExpectedPayTimeSpan(candidate.getExpectedPayTimeSpan());
                }
                if (candidate.getExpectedPayTaxTermId() != null) {
                    existingCandidate.setExpectedPayTaxTermId(candidate.getExpectedPayTaxTermId());
                }
                if (candidate.getOtherExpectedBenefits() != null) {
                    existingCandidate.setOtherExpectedBenefits(candidate.getOtherExpectedBenefits());
                }
                if (candidate.getAdditionalComments() != null) {
                    existingCandidate.setAdditionalComments(candidate.getAdditionalComments());
                }
                if (candidate.getRelocation() != null) {
                    existingCandidate.setRelocation(candidate.getRelocation());
                }
                if (candidate.getFamilyWillingToRelocate() != null) {
                    existingCandidate.setFamilyWillingToRelocate(candidate.getFamilyWillingToRelocate());
                }
                if (candidate.getRelocationType() != null) {
                    existingCandidate.setRelocationType(candidate.getRelocationType());
                }
                if (candidate.getRelocationRemarks() != null) {
                    existingCandidate.setRelocationRemarks(candidate.getRelocationRemarks());
                }
                if (candidate.getTaxTermIds() != null) {
                    existingCandidate.setTaxTermIds(candidate.getTaxTermIds());
                }
                if (candidate.getNoticePeriodInDays() != null) {
                    existingCandidate.setNoticePeriodInDays(candidate.getNoticePeriodInDays());
                }
                if (candidate.getWorkAuthorizationExpiry() != null) {
                    existingCandidate.setWorkAuthorizationExpiry(candidate.getWorkAuthorizationExpiry());
                }
                if (candidate.getGpa() != null) {
                    existingCandidate.setGpa(candidate.getGpa());
                }
                if (candidate.getAadharNumber() != null) {
                    existingCandidate.setAadharNumber(candidate.getAadharNumber());
                }
                if (candidate.getLinkedInProfileURL() != null) {
                    existingCandidate.setLinkedInProfileURL(candidate.getLinkedInProfileURL());
                }
                if (candidate.getOtherSocialURLs() != null) {
                    existingCandidate.setOtherSocialURLs(candidate.getOtherSocialURLs());
                }
                if (candidate.getTags() != null) {
                    existingCandidate.setTags(candidate.getTags());
                }
                if (candidate.getResumes() != null) {
                    existingCandidate.setResumes(candidate.getResumes());
                }
                if (candidate.getRightToReperesent() != null) {
                    existingCandidate.setRightToReperesent(candidate.getRightToReperesent());
                }
                if (candidate.getSkills() != null) {
                    existingCandidate.setSkills(candidate.getSkills());
                }
                if (candidate.getAltSkills() != null) {
                    existingCandidate.setAltSkills(candidate.getAltSkills());
                }
                if (candidate.getTechnologies() != null) {
                    existingCandidate.setTechnologies(candidate.getTechnologies());
                }
                if (candidate.getCertifications() != null) {
                    existingCandidate.setCertifications(candidate.getCertifications());
                }
                if (candidate.getLanguages() != null) {
                    existingCandidate.setLanguages(candidate.getLanguages());
                }
                if (candidate.getWorkExperience() != null) {
                    existingCandidate.setWorkExperience(candidate.getWorkExperience());
                }
                if (candidate.getEducation() != null) {
                    existingCandidate.setEducation(candidate.getEducation());
                }
                if (candidate.getEmployerDetails() != null) {
                    existingCandidate.setEmployerDetails(candidate.getEmployerDetails());
                }
                if (candidate.getDocuments() != null) {
                    existingCandidate.setDocuments(candidate.getDocuments());
                }

                return existingCandidate;
            })
            .map(candidateRepository::save);
    }

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Candidate> findAll(Pageable pageable) {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll(pageable);
    }

    /**
     * Get one candidate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Candidate> findOne(Long id) {
        log.debug("Request to get Candidate : {}", id);
        return candidateRepository.findById(id);
    }

    /**
     * Delete the candidate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }
}
