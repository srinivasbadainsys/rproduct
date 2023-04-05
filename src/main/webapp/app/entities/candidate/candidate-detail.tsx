import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './candidate.reducer';

export const CandidateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const candidateEntity = useAppSelector(state => state.candidate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="candidateDetailsHeading">
          <Translate contentKey="rproductApp.candidate.detail.title">Candidate</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.id}</dd>
          <dt>
            <span id="salutation">
              <Translate contentKey="rproductApp.candidate.salutation">Salutation</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.salutation}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="rproductApp.candidate.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="rproductApp.candidate.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="rproductApp.candidate.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="rproductApp.candidate.email">Email</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.email}</dd>
          <dt>
            <span id="altEmails">
              <Translate contentKey="rproductApp.candidate.altEmails">Alt Emails</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.altEmails}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="rproductApp.candidate.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.phone}</dd>
          <dt>
            <span id="altPhones">
              <Translate contentKey="rproductApp.candidate.altPhones">Alt Phones</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.altPhones}</dd>
          <dt>
            <span id="dob">
              <Translate contentKey="rproductApp.candidate.dob">Dob</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.dob ? <TextFormat value={candidateEntity.dob} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="nationality">
              <Translate contentKey="rproductApp.candidate.nationality">Nationality</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.nationality}</dd>
          <dt>
            <span id="workAuthorizationId">
              <Translate contentKey="rproductApp.candidate.workAuthorizationId">Work Authorization Id</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.workAuthorizationId}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="rproductApp.candidate.address">Address</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.address}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="rproductApp.candidate.area">Area</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.area}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="rproductApp.candidate.city">City</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="rproductApp.candidate.state">State</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.state}</dd>
          <dt>
            <span id="stateCode">
              <Translate contentKey="rproductApp.candidate.stateCode">State Code</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.stateCode}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="rproductApp.candidate.county">County</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.county}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="rproductApp.candidate.country">Country</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.country}</dd>
          <dt>
            <span id="countryCode">
              <Translate contentKey="rproductApp.candidate.countryCode">Country Code</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.countryCode}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="rproductApp.candidate.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.zipCode}</dd>
          <dt>
            <span id="source">
              <Translate contentKey="rproductApp.candidate.source">Source</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.source}</dd>
          <dt>
            <span id="totalExpInYears">
              <Translate contentKey="rproductApp.candidate.totalExpInYears">Total Exp In Years</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.totalExpInYears}</dd>
          <dt>
            <span id="totalExpInMonths">
              <Translate contentKey="rproductApp.candidate.totalExpInMonths">Total Exp In Months</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.totalExpInMonths}</dd>
          <dt>
            <span id="relevantExpInYears">
              <Translate contentKey="rproductApp.candidate.relevantExpInYears">Relevant Exp In Years</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.relevantExpInYears}</dd>
          <dt>
            <span id="relevantExpInMonths">
              <Translate contentKey="rproductApp.candidate.relevantExpInMonths">Relevant Exp In Months</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.relevantExpInMonths}</dd>
          <dt>
            <span id="referredBy">
              <Translate contentKey="rproductApp.candidate.referredBy">Referred By</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.referredBy}</dd>
          <dt>
            <span id="ownershipUserId">
              <Translate contentKey="rproductApp.candidate.ownershipUserId">Ownership User Id</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.ownershipUserId}</dd>
          <dt>
            <span id="currentJobTitle">
              <Translate contentKey="rproductApp.candidate.currentJobTitle">Current Job Title</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentJobTitle}</dd>
          <dt>
            <span id="currentEmployer">
              <Translate contentKey="rproductApp.candidate.currentEmployer">Current Employer</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentEmployer}</dd>
          <dt>
            <span id="currentJobTypeId">
              <Translate contentKey="rproductApp.candidate.currentJobTypeId">Current Job Type Id</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentJobTypeId}</dd>
          <dt>
            <span id="currentPayCurrency">
              <Translate contentKey="rproductApp.candidate.currentPayCurrency">Current Pay Currency</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentPayCurrency}</dd>
          <dt>
            <span id="currentPay">
              <Translate contentKey="rproductApp.candidate.currentPay">Current Pay</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentPay}</dd>
          <dt>
            <span id="currentPayMonthly">
              <Translate contentKey="rproductApp.candidate.currentPayMonthly">Current Pay Monthly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentPayMonthly}</dd>
          <dt>
            <span id="currentPayHourly">
              <Translate contentKey="rproductApp.candidate.currentPayHourly">Current Pay Hourly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentPayHourly}</dd>
          <dt>
            <span id="currentPayYearly">
              <Translate contentKey="rproductApp.candidate.currentPayYearly">Current Pay Yearly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentPayYearly}</dd>
          <dt>
            <span id="currentPayTimeSpan">
              <Translate contentKey="rproductApp.candidate.currentPayTimeSpan">Current Pay Time Span</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.currentPayTimeSpan}</dd>
          <dt>
            <span id="otherCurrentBenefits">
              <Translate contentKey="rproductApp.candidate.otherCurrentBenefits">Other Current Benefits</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.otherCurrentBenefits}</dd>
          <dt>
            <span id="expectedPayCurrency">
              <Translate contentKey="rproductApp.candidate.expectedPayCurrency">Expected Pay Currency</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayCurrency}</dd>
          <dt>
            <span id="expectedPayMin">
              <Translate contentKey="rproductApp.candidate.expectedPayMin">Expected Pay Min</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMin}</dd>
          <dt>
            <span id="expectedPayMax">
              <Translate contentKey="rproductApp.candidate.expectedPayMax">Expected Pay Max</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMax}</dd>
          <dt>
            <span id="expectedPayMinMonthly">
              <Translate contentKey="rproductApp.candidate.expectedPayMinMonthly">Expected Pay Min Monthly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMinMonthly}</dd>
          <dt>
            <span id="expectedPayMinHourly">
              <Translate contentKey="rproductApp.candidate.expectedPayMinHourly">Expected Pay Min Hourly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMinHourly}</dd>
          <dt>
            <span id="expectedPayMinYearly">
              <Translate contentKey="rproductApp.candidate.expectedPayMinYearly">Expected Pay Min Yearly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMinYearly}</dd>
          <dt>
            <span id="expectedPayMaxMonthly">
              <Translate contentKey="rproductApp.candidate.expectedPayMaxMonthly">Expected Pay Max Monthly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMaxMonthly}</dd>
          <dt>
            <span id="expectedPayMaxHourly">
              <Translate contentKey="rproductApp.candidate.expectedPayMaxHourly">Expected Pay Max Hourly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMaxHourly}</dd>
          <dt>
            <span id="expectedPayMaxYearly">
              <Translate contentKey="rproductApp.candidate.expectedPayMaxYearly">Expected Pay Max Yearly</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayMaxYearly}</dd>
          <dt>
            <span id="expectedPayTimeSpan">
              <Translate contentKey="rproductApp.candidate.expectedPayTimeSpan">Expected Pay Time Span</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayTimeSpan}</dd>
          <dt>
            <span id="expectedPayTaxTermId">
              <Translate contentKey="rproductApp.candidate.expectedPayTaxTermId">Expected Pay Tax Term Id</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.expectedPayTaxTermId}</dd>
          <dt>
            <span id="otherExpectedBenefits">
              <Translate contentKey="rproductApp.candidate.otherExpectedBenefits">Other Expected Benefits</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.otherExpectedBenefits}</dd>
          <dt>
            <span id="additionalComments">
              <Translate contentKey="rproductApp.candidate.additionalComments">Additional Comments</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.additionalComments}</dd>
          <dt>
            <span id="relocation">
              <Translate contentKey="rproductApp.candidate.relocation">Relocation</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.relocation ? 'true' : 'false'}</dd>
          <dt>
            <span id="familyWillingToRelocate">
              <Translate contentKey="rproductApp.candidate.familyWillingToRelocate">Family Willing To Relocate</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.familyWillingToRelocate ? 'true' : 'false'}</dd>
          <dt>
            <span id="relocationType">
              <Translate contentKey="rproductApp.candidate.relocationType">Relocation Type</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.relocationType}</dd>
          <dt>
            <span id="relocationRemarks">
              <Translate contentKey="rproductApp.candidate.relocationRemarks">Relocation Remarks</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.relocationRemarks}</dd>
          <dt>
            <span id="taxTermIds">
              <Translate contentKey="rproductApp.candidate.taxTermIds">Tax Term Ids</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.taxTermIds}</dd>
          <dt>
            <span id="noticePeriodInDays">
              <Translate contentKey="rproductApp.candidate.noticePeriodInDays">Notice Period In Days</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.noticePeriodInDays}</dd>
          <dt>
            <span id="workAuthorizationExpiry">
              <Translate contentKey="rproductApp.candidate.workAuthorizationExpiry">Work Authorization Expiry</Translate>
            </span>
          </dt>
          <dd>
            {candidateEntity.workAuthorizationExpiry ? (
              <TextFormat value={candidateEntity.workAuthorizationExpiry} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gpa">
              <Translate contentKey="rproductApp.candidate.gpa">Gpa</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.gpa}</dd>
          <dt>
            <span id="aadharNumber">
              <Translate contentKey="rproductApp.candidate.aadharNumber">Aadhar Number</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.aadharNumber}</dd>
          <dt>
            <span id="linkedInProfileURL">
              <Translate contentKey="rproductApp.candidate.linkedInProfileURL">Linked In Profile URL</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.linkedInProfileURL}</dd>
          <dt>
            <span id="otherSocialURLs">
              <Translate contentKey="rproductApp.candidate.otherSocialURLs">Other Social UR Ls</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.otherSocialURLs}</dd>
          <dt>
            <span id="tags">
              <Translate contentKey="rproductApp.candidate.tags">Tags</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.tags}</dd>
          <dt>
            <span id="resumes">
              <Translate contentKey="rproductApp.candidate.resumes">Resumes</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.resumes}</dd>
          <dt>
            <span id="rightToReperesent">
              <Translate contentKey="rproductApp.candidate.rightToReperesent">Right To Reperesent</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.rightToReperesent}</dd>
          <dt>
            <span id="skills">
              <Translate contentKey="rproductApp.candidate.skills">Skills</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.skills}</dd>
          <dt>
            <span id="altSkills">
              <Translate contentKey="rproductApp.candidate.altSkills">Alt Skills</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.altSkills}</dd>
          <dt>
            <span id="technologies">
              <Translate contentKey="rproductApp.candidate.technologies">Technologies</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.technologies}</dd>
          <dt>
            <span id="certifications">
              <Translate contentKey="rproductApp.candidate.certifications">Certifications</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.certifications}</dd>
          <dt>
            <span id="languages">
              <Translate contentKey="rproductApp.candidate.languages">Languages</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.languages}</dd>
          <dt>
            <span id="workExperience">
              <Translate contentKey="rproductApp.candidate.workExperience">Work Experience</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.workExperience}</dd>
          <dt>
            <span id="education">
              <Translate contentKey="rproductApp.candidate.education">Education</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.education}</dd>
          <dt>
            <span id="employerDetails">
              <Translate contentKey="rproductApp.candidate.employerDetails">Employer Details</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.employerDetails}</dd>
          <dt>
            <span id="documents">
              <Translate contentKey="rproductApp.candidate.documents">Documents</Translate>
            </span>
          </dt>
          <dd>{candidateEntity.documents}</dd>
          <dt>
            <Translate contentKey="rproductApp.candidate.workAuthorization">Work Authorization</Translate>
          </dt>
          <dd>{candidateEntity.workAuthorization ? candidateEntity.workAuthorization.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.candidate.ownershipUser">Ownership User</Translate>
          </dt>
          <dd>{candidateEntity.ownershipUser ? candidateEntity.ownershipUser.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.candidate.currentJobType">Current Job Type</Translate>
          </dt>
          <dd>{candidateEntity.currentJobType ? candidateEntity.currentJobType.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.candidate.expectedPayTaxTerm">Expected Pay Tax Term</Translate>
          </dt>
          <dd>{candidateEntity.expectedPayTaxTerm ? candidateEntity.expectedPayTaxTerm.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/candidate" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/candidate/${candidateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CandidateDetail;
