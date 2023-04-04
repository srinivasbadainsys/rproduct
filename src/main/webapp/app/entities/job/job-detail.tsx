import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job.reducer';

export const JobDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobEntity = useAppSelector(state => state.job.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobDetailsHeading">
          <Translate contentKey="rproductApp.job.detail.title">Job</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="rproductApp.job.title">Title</Translate>
            </span>
          </dt>
          <dd>{jobEntity.title}</dd>
          <dt>
            <span id="jobCode">
              <Translate contentKey="rproductApp.job.jobCode">Job Code</Translate>
            </span>
          </dt>
          <dd>{jobEntity.jobCode}</dd>
          <dt>
            <span id="clientJobCode">
              <Translate contentKey="rproductApp.job.clientJobCode">Client Job Code</Translate>
            </span>
          </dt>
          <dd>{jobEntity.clientJobCode}</dd>
          <dt>
            <span id="orgName">
              <Translate contentKey="rproductApp.job.orgName">Org Name</Translate>
            </span>
          </dt>
          <dd>{jobEntity.orgName}</dd>
          <dt>
            <span id="orgEmploymentTypeIds">
              <Translate contentKey="rproductApp.job.orgEmploymentTypeIds">Org Employment Type Ids</Translate>
            </span>
          </dt>
          <dd>{jobEntity.orgEmploymentTypeIds}</dd>
          <dt>
            <span id="jobRef">
              <Translate contentKey="rproductApp.job.jobRef">Job Ref</Translate>
            </span>
          </dt>
          <dd>{jobEntity.jobRef}</dd>
          <dt>
            <span id="jobSource">
              <Translate contentKey="rproductApp.job.jobSource">Job Source</Translate>
            </span>
          </dt>
          <dd>{jobEntity.jobSource}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="rproductApp.job.url">Url</Translate>
            </span>
          </dt>
          <dd>{jobEntity.url}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="rproductApp.job.description">Description</Translate>
            </span>
          </dt>
          <dd>{jobEntity.description}</dd>
          <dt>
            <span id="publicJobTitle">
              <Translate contentKey="rproductApp.job.publicJobTitle">Public Job Title</Translate>
            </span>
          </dt>
          <dd>{jobEntity.publicJobTitle}</dd>
          <dt>
            <span id="publicJobDescription">
              <Translate contentKey="rproductApp.job.publicJobDescription">Public Job Description</Translate>
            </span>
          </dt>
          <dd>{jobEntity.publicJobDescription}</dd>
          <dt>
            <span id="autoCloseDate">
              <Translate contentKey="rproductApp.job.autoCloseDate">Auto Close Date</Translate>
            </span>
          </dt>
          <dd>{jobEntity.autoCloseDate ? <TextFormat value={jobEntity.autoCloseDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="noOfPositions">
              <Translate contentKey="rproductApp.job.noOfPositions">No Of Positions</Translate>
            </span>
          </dt>
          <dd>{jobEntity.noOfPositions}</dd>
          <dt>
            <span id="departmentAltText">
              <Translate contentKey="rproductApp.job.departmentAltText">Department Alt Text</Translate>
            </span>
          </dt>
          <dd>{jobEntity.departmentAltText}</dd>
          <dt>
            <span id="displayCandRate">
              <Translate contentKey="rproductApp.job.displayCandRate">Display Cand Rate</Translate>
            </span>
          </dt>
          <dd>{jobEntity.displayCandRate ? 'true' : 'false'}</dd>
          <dt>
            <span id="candRateCurrency">
              <Translate contentKey="rproductApp.job.candRateCurrency">Cand Rate Currency</Translate>
            </span>
          </dt>
          <dd>{jobEntity.candRateCurrency}</dd>
          <dt>
            <span id="candMinRate">
              <Translate contentKey="rproductApp.job.candMinRate">Cand Min Rate</Translate>
            </span>
          </dt>
          <dd>{jobEntity.candMinRate}</dd>
          <dt>
            <span id="candMaxRate">
              <Translate contentKey="rproductApp.job.candMaxRate">Cand Max Rate</Translate>
            </span>
          </dt>
          <dd>{jobEntity.candMaxRate}</dd>
          <dt>
            <span id="candRateTimeSpan">
              <Translate contentKey="rproductApp.job.candRateTimeSpan">Cand Rate Time Span</Translate>
            </span>
          </dt>
          <dd>{jobEntity.candRateTimeSpan}</dd>
          <dt>
            <span id="candRateTaxTerm">
              <Translate contentKey="rproductApp.job.candRateTaxTerm">Cand Rate Tax Term</Translate>
            </span>
          </dt>
          <dd>{jobEntity.candRateTaxTerm}</dd>
          <dt>
            <span id="candSalaryAltDisplayText">
              <Translate contentKey="rproductApp.job.candSalaryAltDisplayText">Cand Salary Alt Display Text</Translate>
            </span>
          </dt>
          <dd>{jobEntity.candSalaryAltDisplayText}</dd>
          <dt>
            <span id="otherBenefitDetails">
              <Translate contentKey="rproductApp.job.otherBenefitDetails">Other Benefit Details</Translate>
            </span>
          </dt>
          <dd>{jobEntity.otherBenefitDetails}</dd>
          <dt>
            <span id="clientBillRateCurrency">
              <Translate contentKey="rproductApp.job.clientBillRateCurrency">Client Bill Rate Currency</Translate>
            </span>
          </dt>
          <dd>{jobEntity.clientBillRateCurrency}</dd>
          <dt>
            <span id="clientBillRate">
              <Translate contentKey="rproductApp.job.clientBillRate">Client Bill Rate</Translate>
            </span>
          </dt>
          <dd>{jobEntity.clientBillRate}</dd>
          <dt>
            <span id="clientBillRateTimeSpan">
              <Translate contentKey="rproductApp.job.clientBillRateTimeSpan">Client Bill Rate Time Span</Translate>
            </span>
          </dt>
          <dd>{jobEntity.clientBillRateTimeSpan}</dd>
          <dt>
            <span id="clientBillRateTaxTerm">
              <Translate contentKey="rproductApp.job.clientBillRateTaxTerm">Client Bill Rate Tax Term</Translate>
            </span>
          </dt>
          <dd>{jobEntity.clientBillRateTaxTerm}</dd>
          <dt>
            <span id="workDuration">
              <Translate contentKey="rproductApp.job.workDuration">Work Duration</Translate>
            </span>
          </dt>
          <dd>{jobEntity.workDuration}</dd>
          <dt>
            <span id="immigrationStatus">
              <Translate contentKey="rproductApp.job.immigrationStatus">Immigration Status</Translate>
            </span>
          </dt>
          <dd>{jobEntity.immigrationStatus}</dd>
          <dt>
            <span id="displayImmigrationStatus">
              <Translate contentKey="rproductApp.job.displayImmigrationStatus">Display Immigration Status</Translate>
            </span>
          </dt>
          <dd>{jobEntity.displayImmigrationStatus}</dd>
          <dt>
            <span id="skills">
              <Translate contentKey="rproductApp.job.skills">Skills</Translate>
            </span>
          </dt>
          <dd>{jobEntity.skills}</dd>
          <dt>
            <span id="altSkills">
              <Translate contentKey="rproductApp.job.altSkills">Alt Skills</Translate>
            </span>
          </dt>
          <dd>{jobEntity.altSkills}</dd>
          <dt>
            <span id="tags">
              <Translate contentKey="rproductApp.job.tags">Tags</Translate>
            </span>
          </dt>
          <dd>{jobEntity.tags}</dd>
          <dt>
            <span id="qualificationIds">
              <Translate contentKey="rproductApp.job.qualificationIds">Qualification Ids</Translate>
            </span>
          </dt>
          <dd>{jobEntity.qualificationIds}</dd>
          <dt>
            <span id="qualificationsAltText">
              <Translate contentKey="rproductApp.job.qualificationsAltText">Qualifications Alt Text</Translate>
            </span>
          </dt>
          <dd>{jobEntity.qualificationsAltText}</dd>
          <dt>
            <span id="eduRequirements">
              <Translate contentKey="rproductApp.job.eduRequirements">Edu Requirements</Translate>
            </span>
          </dt>
          <dd>{jobEntity.eduRequirements}</dd>
          <dt>
            <span id="expRequirements">
              <Translate contentKey="rproductApp.job.expRequirements">Exp Requirements</Translate>
            </span>
          </dt>
          <dd>{jobEntity.expRequirements}</dd>
          <dt>
            <span id="expAltText">
              <Translate contentKey="rproductApp.job.expAltText">Exp Alt Text</Translate>
            </span>
          </dt>
          <dd>{jobEntity.expAltText}</dd>
          <dt>
            <span id="minExpInYears">
              <Translate contentKey="rproductApp.job.minExpInYears">Min Exp In Years</Translate>
            </span>
          </dt>
          <dd>{jobEntity.minExpInYears}</dd>
          <dt>
            <span id="maxExpInYears">
              <Translate contentKey="rproductApp.job.maxExpInYears">Max Exp In Years</Translate>
            </span>
          </dt>
          <dd>{jobEntity.maxExpInYears}</dd>
          <dt>
            <span id="languageIds">
              <Translate contentKey="rproductApp.job.languageIds">Language Ids</Translate>
            </span>
          </dt>
          <dd>{jobEntity.languageIds}</dd>
          <dt>
            <span id="visaRequirements">
              <Translate contentKey="rproductApp.job.visaRequirements">Visa Requirements</Translate>
            </span>
          </dt>
          <dd>{jobEntity.visaRequirements}</dd>
          <dt>
            <span id="workAuthorizationIds">
              <Translate contentKey="rproductApp.job.workAuthorizationIds">Work Authorization Ids</Translate>
            </span>
          </dt>
          <dd>{jobEntity.workAuthorizationIds}</dd>
          <dt>
            <span id="applicationFormType">
              <Translate contentKey="rproductApp.job.applicationFormType">Application Form Type</Translate>
            </span>
          </dt>
          <dd>{jobEntity.applicationFormType}</dd>
          <dt>
            <span id="isPartnerJob">
              <Translate contentKey="rproductApp.job.isPartnerJob">Is Partner Job</Translate>
            </span>
          </dt>
          <dd>{jobEntity.isPartnerJob ? 'true' : 'false'}</dd>
          <dt>
            <span id="redirectionUrl">
              <Translate contentKey="rproductApp.job.redirectionUrl">Redirection Url</Translate>
            </span>
          </dt>
          <dd>{jobEntity.redirectionUrl}</dd>
          <dt>
            <span id="jobStatus">
              <Translate contentKey="rproductApp.job.jobStatus">Job Status</Translate>
            </span>
          </dt>
          <dd>{jobEntity.jobStatus}</dd>
          <dt>
            <span id="endClient">
              <Translate contentKey="rproductApp.job.endClient">End Client</Translate>
            </span>
          </dt>
          <dd>{jobEntity.endClient}</dd>
          <dt>
            <span id="domainAlt">
              <Translate contentKey="rproductApp.job.domainAlt">Domain Alt</Translate>
            </span>
          </dt>
          <dd>{jobEntity.domainAlt}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="rproductApp.job.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{jobEntity.comments}</dd>
          <dt>
            <span id="additionalNotificationsToUserIds">
              <Translate contentKey="rproductApp.job.additionalNotificationsToUserIds">Additional Notifications To User Ids</Translate>
            </span>
          </dt>
          <dd>{jobEntity.additionalNotificationsToUserIds}</dd>
          <dt>
            <span id="additionalNotificationsToTeamIds">
              <Translate contentKey="rproductApp.job.additionalNotificationsToTeamIds">Additional Notifications To Team Ids</Translate>
            </span>
          </dt>
          <dd>{jobEntity.additionalNotificationsToTeamIds}</dd>
          <dt>
            <span id="requiredDocumentIds">
              <Translate contentKey="rproductApp.job.requiredDocumentIds">Required Document Ids</Translate>
            </span>
          </dt>
          <dd>{jobEntity.requiredDocumentIds}</dd>
          <dt>
            <span id="jobCloseReasonOtherAlt">
              <Translate contentKey="rproductApp.job.jobCloseReasonOtherAlt">Job Close Reason Other Alt</Translate>
            </span>
          </dt>
          <dd>{jobEntity.jobCloseReasonOtherAlt}</dd>
          <dt>
            <span id="addToCareerPage">
              <Translate contentKey="rproductApp.job.addToCareerPage">Add To Career Page</Translate>
            </span>
          </dt>
          <dd>{jobEntity.addToCareerPage ? 'true' : 'false'}</dd>
          <dt>
            <span id="remoteJob">
              <Translate contentKey="rproductApp.job.remoteJob">Remote Job</Translate>
            </span>
          </dt>
          <dd>{jobEntity.remoteJob}</dd>
          <dt>
            <span id="hiringFor">
              <Translate contentKey="rproductApp.job.hiringFor">Hiring For</Translate>
            </span>
          </dt>
          <dd>{jobEntity.hiringFor}</dd>
          <dt>
            <span id="workDurationTimeSpan">
              <Translate contentKey="rproductApp.job.workDurationTimeSpan">Work Duration Time Span</Translate>
            </span>
          </dt>
          <dd>{jobEntity.workDurationTimeSpan}</dd>
          <dt>
            <span id="taxTerms">
              <Translate contentKey="rproductApp.job.taxTerms">Tax Terms</Translate>
            </span>
          </dt>
          <dd>{jobEntity.taxTerms}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.postedByTeam">Posted By Team</Translate>
          </dt>
          <dd>{jobEntity.postedByTeam ? jobEntity.postedByTeam.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.businessUnit">Business Unit</Translate>
          </dt>
          <dd>{jobEntity.businessUnit ? jobEntity.businessUnit.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.client">Client</Translate>
          </dt>
          <dd>{jobEntity.client ? jobEntity.client.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.jobType">Job Type</Translate>
          </dt>
          <dd>{jobEntity.jobType ? jobEntity.jobType.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.industry">Industry</Translate>
          </dt>
          <dd>{jobEntity.industry ? jobEntity.industry.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.department">Department</Translate>
          </dt>
          <dd>{jobEntity.department ? jobEntity.department.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.clientRecruiter">Client Recruiter</Translate>
          </dt>
          <dd>{jobEntity.clientRecruiter ? jobEntity.clientRecruiter.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.clientManager">Client Manager</Translate>
          </dt>
          <dd>{jobEntity.clientManager ? jobEntity.clientManager.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.accountManager">Account Manager</Translate>
          </dt>
          <dd>{jobEntity.accountManager ? jobEntity.accountManager.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.headOfRecruitment">Head Of Recruitment</Translate>
          </dt>
          <dd>{jobEntity.headOfRecruitment ? jobEntity.headOfRecruitment.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.deliveryLeadManager">Delivery Lead Manager</Translate>
          </dt>
          <dd>{jobEntity.deliveryLeadManager ? jobEntity.deliveryLeadManager.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.domain">Domain</Translate>
          </dt>
          <dd>{jobEntity.domain ? jobEntity.domain.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.srDeliveryManager">Sr Delivery Manager</Translate>
          </dt>
          <dd>{jobEntity.srDeliveryManager ? jobEntity.srDeliveryManager.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.teamLead">Team Lead</Translate>
          </dt>
          <dd>{jobEntity.teamLead ? jobEntity.teamLead.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.priority">Priority</Translate>
          </dt>
          <dd>{jobEntity.priority ? jobEntity.priority.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.jobCloseReason">Job Close Reason</Translate>
          </dt>
          <dd>{jobEntity.jobCloseReason ? jobEntity.jobCloseReason.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.jobCategory">Job Category</Translate>
          </dt>
          <dd>{jobEntity.jobCategory ? jobEntity.jobCategory.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.job.jobOccupation">Job Occupation</Translate>
          </dt>
          <dd>{jobEntity.jobOccupation ? jobEntity.jobOccupation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job/${jobEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobDetail;
