package com.rp.service;

import com.rp.domain.Job;
import com.rp.repository.JobRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Job}.
 */
@Service
@Transactional
public class JobService {

    private final Logger log = LoggerFactory.getLogger(JobService.class);

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Save a job.
     *
     * @param job the entity to save.
     * @return the persisted entity.
     */
    public Job save(Job job) {
        log.debug("Request to save Job : {}", job);
        return jobRepository.save(job);
    }

    /**
     * Update a job.
     *
     * @param job the entity to save.
     * @return the persisted entity.
     */
    public Job update(Job job) {
        log.debug("Request to update Job : {}", job);
        return jobRepository.save(job);
    }

    /**
     * Partially update a job.
     *
     * @param job the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Job> partialUpdate(Job job) {
        log.debug("Request to partially update Job : {}", job);

        return jobRepository
            .findById(job.getId())
            .map(existingJob -> {
                if (job.getTitle() != null) {
                    existingJob.setTitle(job.getTitle());
                }
                if (job.getJobCode() != null) {
                    existingJob.setJobCode(job.getJobCode());
                }
                if (job.getClientJobCode() != null) {
                    existingJob.setClientJobCode(job.getClientJobCode());
                }
                if (job.getOrgName() != null) {
                    existingJob.setOrgName(job.getOrgName());
                }
                if (job.getOrgEmploymentTypeIds() != null) {
                    existingJob.setOrgEmploymentTypeIds(job.getOrgEmploymentTypeIds());
                }
                if (job.getJobRef() != null) {
                    existingJob.setJobRef(job.getJobRef());
                }
                if (job.getJobSource() != null) {
                    existingJob.setJobSource(job.getJobSource());
                }
                if (job.getUrl() != null) {
                    existingJob.setUrl(job.getUrl());
                }
                if (job.getDescription() != null) {
                    existingJob.setDescription(job.getDescription());
                }
                if (job.getPublicJobTitle() != null) {
                    existingJob.setPublicJobTitle(job.getPublicJobTitle());
                }
                if (job.getPublicJobDescription() != null) {
                    existingJob.setPublicJobDescription(job.getPublicJobDescription());
                }
                if (job.getAutoCloseDate() != null) {
                    existingJob.setAutoCloseDate(job.getAutoCloseDate());
                }
                if (job.getNoOfPositions() != null) {
                    existingJob.setNoOfPositions(job.getNoOfPositions());
                }
                if (job.getDepartmentAltText() != null) {
                    existingJob.setDepartmentAltText(job.getDepartmentAltText());
                }
                if (job.getDisplayCandRate() != null) {
                    existingJob.setDisplayCandRate(job.getDisplayCandRate());
                }
                if (job.getCandRateCurrency() != null) {
                    existingJob.setCandRateCurrency(job.getCandRateCurrency());
                }
                if (job.getCandMinRate() != null) {
                    existingJob.setCandMinRate(job.getCandMinRate());
                }
                if (job.getCandMaxRate() != null) {
                    existingJob.setCandMaxRate(job.getCandMaxRate());
                }
                if (job.getCandRateTimeSpan() != null) {
                    existingJob.setCandRateTimeSpan(job.getCandRateTimeSpan());
                }
                if (job.getCandRateTaxTerm() != null) {
                    existingJob.setCandRateTaxTerm(job.getCandRateTaxTerm());
                }
                if (job.getCandSalaryAltDisplayText() != null) {
                    existingJob.setCandSalaryAltDisplayText(job.getCandSalaryAltDisplayText());
                }
                if (job.getOtherBenefitDetails() != null) {
                    existingJob.setOtherBenefitDetails(job.getOtherBenefitDetails());
                }
                if (job.getClientBillRateCurrency() != null) {
                    existingJob.setClientBillRateCurrency(job.getClientBillRateCurrency());
                }
                if (job.getClientBillRate() != null) {
                    existingJob.setClientBillRate(job.getClientBillRate());
                }
                if (job.getClientBillRateTimeSpan() != null) {
                    existingJob.setClientBillRateTimeSpan(job.getClientBillRateTimeSpan());
                }
                if (job.getClientBillRateTaxTerm() != null) {
                    existingJob.setClientBillRateTaxTerm(job.getClientBillRateTaxTerm());
                }
                if (job.getWorkDuration() != null) {
                    existingJob.setWorkDuration(job.getWorkDuration());
                }
                if (job.getImmigrationStatus() != null) {
                    existingJob.setImmigrationStatus(job.getImmigrationStatus());
                }
                if (job.getDisplayImmigrationStatus() != null) {
                    existingJob.setDisplayImmigrationStatus(job.getDisplayImmigrationStatus());
                }
                if (job.getSkills() != null) {
                    existingJob.setSkills(job.getSkills());
                }
                if (job.getAltSkills() != null) {
                    existingJob.setAltSkills(job.getAltSkills());
                }
                if (job.getTags() != null) {
                    existingJob.setTags(job.getTags());
                }
                if (job.getQualificationIds() != null) {
                    existingJob.setQualificationIds(job.getQualificationIds());
                }
                if (job.getQualificationsAltText() != null) {
                    existingJob.setQualificationsAltText(job.getQualificationsAltText());
                }
                if (job.getEduRequirements() != null) {
                    existingJob.setEduRequirements(job.getEduRequirements());
                }
                if (job.getExpRequirements() != null) {
                    existingJob.setExpRequirements(job.getExpRequirements());
                }
                if (job.getExpAltText() != null) {
                    existingJob.setExpAltText(job.getExpAltText());
                }
                if (job.getMinExpInYears() != null) {
                    existingJob.setMinExpInYears(job.getMinExpInYears());
                }
                if (job.getMaxExpInYears() != null) {
                    existingJob.setMaxExpInYears(job.getMaxExpInYears());
                }
                if (job.getLanguageIds() != null) {
                    existingJob.setLanguageIds(job.getLanguageIds());
                }
                if (job.getVisaRequirements() != null) {
                    existingJob.setVisaRequirements(job.getVisaRequirements());
                }
                if (job.getWorkAuthorizationIds() != null) {
                    existingJob.setWorkAuthorizationIds(job.getWorkAuthorizationIds());
                }
                if (job.getApplicationFormType() != null) {
                    existingJob.setApplicationFormType(job.getApplicationFormType());
                }
                if (job.getIsPartnerJob() != null) {
                    existingJob.setIsPartnerJob(job.getIsPartnerJob());
                }
                if (job.getRedirectionUrl() != null) {
                    existingJob.setRedirectionUrl(job.getRedirectionUrl());
                }
                if (job.getJobStatus() != null) {
                    existingJob.setJobStatus(job.getJobStatus());
                }
                if (job.getEndClient() != null) {
                    existingJob.setEndClient(job.getEndClient());
                }
                if (job.getDomainAlt() != null) {
                    existingJob.setDomainAlt(job.getDomainAlt());
                }
                if (job.getComments() != null) {
                    existingJob.setComments(job.getComments());
                }
                if (job.getAdditionalNotificationsToUserIds() != null) {
                    existingJob.setAdditionalNotificationsToUserIds(job.getAdditionalNotificationsToUserIds());
                }
                if (job.getAdditionalNotificationsToTeamIds() != null) {
                    existingJob.setAdditionalNotificationsToTeamIds(job.getAdditionalNotificationsToTeamIds());
                }
                if (job.getRequiredDocumentIds() != null) {
                    existingJob.setRequiredDocumentIds(job.getRequiredDocumentIds());
                }
                if (job.getJobCloseReasonOtherAlt() != null) {
                    existingJob.setJobCloseReasonOtherAlt(job.getJobCloseReasonOtherAlt());
                }
                if (job.getAddToCareerPage() != null) {
                    existingJob.setAddToCareerPage(job.getAddToCareerPage());
                }
                if (job.getRemoteJob() != null) {
                    existingJob.setRemoteJob(job.getRemoteJob());
                }
                if (job.getHiringFor() != null) {
                    existingJob.setHiringFor(job.getHiringFor());
                }
                if (job.getWorkDurationTimeSpan() != null) {
                    existingJob.setWorkDurationTimeSpan(job.getWorkDurationTimeSpan());
                }
                if (job.getTaxTerms() != null) {
                    existingJob.setTaxTerms(job.getTaxTerms());
                }

                return existingJob;
            })
            .map(jobRepository::save);
    }

    /**
     * Get all the jobs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Job> findAll(Pageable pageable) {
        log.debug("Request to get all Jobs");
        return jobRepository.findAll(pageable);
    }

    /**
     * Get one job by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Job> findOne(Long id) {
        log.debug("Request to get Job : {}", id);
        return jobRepository.findById(id);
    }

    /**
     * Delete the job by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Job : {}", id);
        jobRepository.deleteById(id);
    }
}
