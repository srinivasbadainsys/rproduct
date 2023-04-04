import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client.reducer';

export const ClientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">
          <Translate contentKey="rproductApp.client.detail.title">Client</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="clientVisibility">
              <Translate contentKey="rproductApp.client.clientVisibility">Client Visibility</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientVisibility}</dd>
          <dt>
            <span id="businessUnitIds">
              <Translate contentKey="rproductApp.client.businessUnitIds">Business Unit Ids</Translate>
            </span>
          </dt>
          <dd>{clientEntity.businessUnitIds}</dd>
          <dt>
            <span id="clientName">
              <Translate contentKey="rproductApp.client.clientName">Client Name</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientName}</dd>
          <dt>
            <span id="vmsClientName">
              <Translate contentKey="rproductApp.client.vmsClientName">Vms Client Name</Translate>
            </span>
          </dt>
          <dd>{clientEntity.vmsClientName}</dd>
          <dt>
            <span id="federalID">
              <Translate contentKey="rproductApp.client.federalID">Federal ID</Translate>
            </span>
          </dt>
          <dd>{clientEntity.federalID}</dd>
          <dt>
            <span id="contactNumber">
              <Translate contentKey="rproductApp.client.contactNumber">Contact Number</Translate>
            </span>
          </dt>
          <dd>{clientEntity.contactNumber}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="rproductApp.client.email">Email</Translate>
            </span>
          </dt>
          <dd>{clientEntity.email}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="rproductApp.client.address">Address</Translate>
            </span>
          </dt>
          <dd>{clientEntity.address}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="rproductApp.client.area">Area</Translate>
            </span>
          </dt>
          <dd>{clientEntity.area}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="rproductApp.client.city">City</Translate>
            </span>
          </dt>
          <dd>{clientEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="rproductApp.client.state">State</Translate>
            </span>
          </dt>
          <dd>{clientEntity.state}</dd>
          <dt>
            <span id="stateCode">
              <Translate contentKey="rproductApp.client.stateCode">State Code</Translate>
            </span>
          </dt>
          <dd>{clientEntity.stateCode}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="rproductApp.client.county">County</Translate>
            </span>
          </dt>
          <dd>{clientEntity.county}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="rproductApp.client.country">Country</Translate>
            </span>
          </dt>
          <dd>{clientEntity.country}</dd>
          <dt>
            <span id="countryCode">
              <Translate contentKey="rproductApp.client.countryCode">Country Code</Translate>
            </span>
          </dt>
          <dd>{clientEntity.countryCode}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="rproductApp.client.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{clientEntity.zipCode}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="rproductApp.client.website">Website</Translate>
            </span>
          </dt>
          <dd>{clientEntity.website}</dd>
          <dt>
            <span id="sendRequirement">
              <Translate contentKey="rproductApp.client.sendRequirement">Send Requirement</Translate>
            </span>
          </dt>
          <dd>{clientEntity.sendRequirement ? 'true' : 'false'}</dd>
          <dt>
            <span id="sendHotList">
              <Translate contentKey="rproductApp.client.sendHotList">Send Hot List</Translate>
            </span>
          </dt>
          <dd>{clientEntity.sendHotList ? 'true' : 'false'}</dd>
          <dt>
            <span id="fax">
              <Translate contentKey="rproductApp.client.fax">Fax</Translate>
            </span>
          </dt>
          <dd>{clientEntity.fax}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="rproductApp.client.status">Status</Translate>
            </span>
          </dt>
          <dd>{clientEntity.status}</dd>
          <dt>
            <span id="allowAccessToAllUsers">
              <Translate contentKey="rproductApp.client.allowAccessToAllUsers">Allow Access To All Users</Translate>
            </span>
          </dt>
          <dd>{clientEntity.allowAccessToAllUsers ? 'true' : 'false'}</dd>
          <dt>
            <span id="ownershipIds">
              <Translate contentKey="rproductApp.client.ownershipIds">Ownership Ids</Translate>
            </span>
          </dt>
          <dd>{clientEntity.ownershipIds}</dd>
          <dt>
            <span id="clientLeadIds">
              <Translate contentKey="rproductApp.client.clientLeadIds">Client Lead Ids</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientLeadIds}</dd>
          <dt>
            <span id="requiredDocuments">
              <Translate contentKey="rproductApp.client.requiredDocuments">Required Documents</Translate>
            </span>
          </dt>
          <dd>{clientEntity.requiredDocuments}</dd>
          <dt>
            <span id="practiceAlt">
              <Translate contentKey="rproductApp.client.practiceAlt">Practice Alt</Translate>
            </span>
          </dt>
          <dd>{clientEntity.practiceAlt}</dd>
          <dt>
            <span id="aboutCompany">
              <Translate contentKey="rproductApp.client.aboutCompany">About Company</Translate>
            </span>
          </dt>
          <dd>{clientEntity.aboutCompany}</dd>
          <dt>
            <span id="stopNotifyingClientContactOnSubmitToClient">
              <Translate contentKey="rproductApp.client.stopNotifyingClientContactOnSubmitToClient">
                Stop Notifying Client Contact On Submit To Client
              </Translate>
            </span>
          </dt>
          <dd>{clientEntity.stopNotifyingClientContactOnSubmitToClient ? 'true' : 'false'}</dd>
          <dt>
            <span id="defaultForJobPostings">
              <Translate contentKey="rproductApp.client.defaultForJobPostings">Default For Job Postings</Translate>
            </span>
          </dt>
          <dd>{clientEntity.defaultForJobPostings ? 'true' : 'false'}</dd>
          <dt>
            <span id="submissionGuidelines">
              <Translate contentKey="rproductApp.client.submissionGuidelines">Submission Guidelines</Translate>
            </span>
          </dt>
          <dd>{clientEntity.submissionGuidelines}</dd>
          <dt>
            <span id="assignedTo">
              <Translate contentKey="rproductApp.client.assignedTo">Assigned To</Translate>
            </span>
          </dt>
          <dd>{clientEntity.assignedTo}</dd>
          <dt>
            <span id="assignedToTeams">
              <Translate contentKey="rproductApp.client.assignedToTeams">Assigned To Teams</Translate>
            </span>
          </dt>
          <dd>{clientEntity.assignedToTeams}</dd>
          <dt>
            <span id="salesManagers">
              <Translate contentKey="rproductApp.client.salesManagers">Sales Managers</Translate>
            </span>
          </dt>
          <dd>{clientEntity.salesManagers}</dd>
          <dt>
            <span id="accountManagers">
              <Translate contentKey="rproductApp.client.accountManagers">Account Managers</Translate>
            </span>
          </dt>
          <dd>{clientEntity.accountManagers}</dd>
          <dt>
            <span id="recruitmentManagers">
              <Translate contentKey="rproductApp.client.recruitmentManagers">Recruitment Managers</Translate>
            </span>
          </dt>
          <dd>{clientEntity.recruitmentManagers}</dd>
          <dt>
            <span id="defaultTATConfigForJobPostingOrVMSJobs">
              <Translate contentKey="rproductApp.client.defaultTATConfigForJobPostingOrVMSJobs">
                Default TAT Config For Job Posting Or VMS Jobs
              </Translate>
            </span>
          </dt>
          <dd>{clientEntity.defaultTATConfigForJobPostingOrVMSJobs}</dd>
          <dt>
            <span id="defaultTATConfigTimespan">
              <Translate contentKey="rproductApp.client.defaultTATConfigTimespan">Default TAT Config Timespan</Translate>
            </span>
          </dt>
          <dd>{clientEntity.defaultTATConfigTimespan}</dd>
          <dt>
            <span id="notifyOnTATToUserTypes">
              <Translate contentKey="rproductApp.client.notifyOnTATToUserTypes">Notify On TAT To User Types</Translate>
            </span>
          </dt>
          <dd>{clientEntity.notifyOnTATToUserTypes}</dd>
          <dt>
            <span id="notifyOnTATToUserIds">
              <Translate contentKey="rproductApp.client.notifyOnTATToUserIds">Notify On TAT To User Ids</Translate>
            </span>
          </dt>
          <dd>{clientEntity.notifyOnTATToUserIds}</dd>
          <dt>
            <span id="taxTermIds">
              <Translate contentKey="rproductApp.client.taxTermIds">Tax Term Ids</Translate>
            </span>
          </dt>
          <dd>{clientEntity.taxTermIds}</dd>
          <dt>
            <span id="workAuthorizationIds">
              <Translate contentKey="rproductApp.client.workAuthorizationIds">Work Authorization Ids</Translate>
            </span>
          </dt>
          <dd>{clientEntity.workAuthorizationIds}</dd>
          <dt>
            <span id="postJobOnCareersPage">
              <Translate contentKey="rproductApp.client.postJobOnCareersPage">Post Job On Careers Page</Translate>
            </span>
          </dt>
          <dd>{clientEntity.postJobOnCareersPage ? 'true' : 'false'}</dd>
          <dt>
            <span id="defaultPayRateTaxTerm">
              <Translate contentKey="rproductApp.client.defaultPayRateTaxTerm">Default Pay Rate Tax Term</Translate>
            </span>
          </dt>
          <dd>{clientEntity.defaultPayRateTaxTerm}</dd>
          <dt>
            <span id="defaultBillRateTaxTerm">
              <Translate contentKey="rproductApp.client.defaultBillRateTaxTerm">Default Bill Rate Tax Term</Translate>
            </span>
          </dt>
          <dd>{clientEntity.defaultBillRateTaxTerm}</dd>
          <dt>
            <span id="referencesMandatoryForSubmission">
              <Translate contentKey="rproductApp.client.referencesMandatoryForSubmission">References Mandatory For Submission</Translate>
            </span>
          </dt>
          <dd>{clientEntity.referencesMandatoryForSubmission ? 'true' : 'false'}</dd>
          <dt>
            <span id="maxSubmissions">
              <Translate contentKey="rproductApp.client.maxSubmissions">Max Submissions</Translate>
            </span>
          </dt>
          <dd>{clientEntity.maxSubmissions}</dd>
          <dt>
            <span id="noOfPositions">
              <Translate contentKey="rproductApp.client.noOfPositions">No Of Positions</Translate>
            </span>
          </dt>
          <dd>{clientEntity.noOfPositions}</dd>
          <dt>
            <span id="markUp">
              <Translate contentKey="rproductApp.client.markUp">Mark Up</Translate>
            </span>
          </dt>
          <dd>{clientEntity.markUp}</dd>
          <dt>
            <span id="msp">
              <Translate contentKey="rproductApp.client.msp">Msp</Translate>
            </span>
          </dt>
          <dd>{clientEntity.msp}</dd>
          <dt>
            <span id="mailSubject">
              <Translate contentKey="rproductApp.client.mailSubject">Mail Subject</Translate>
            </span>
          </dt>
          <dd>{clientEntity.mailSubject}</dd>
          <dt>
            <span id="mailBody">
              <Translate contentKey="rproductApp.client.mailBody">Mail Body</Translate>
            </span>
          </dt>
          <dd>{clientEntity.mailBody}</dd>
          <dt>
            <span id="fieldsForExcel">
              <Translate contentKey="rproductApp.client.fieldsForExcel">Fields For Excel</Translate>
            </span>
          </dt>
          <dd>{clientEntity.fieldsForExcel}</dd>
          <dt>
            <Translate contentKey="rproductApp.client.primaryBusinessUnit">Primary Business Unit</Translate>
          </dt>
          <dd>{clientEntity.primaryBusinessUnit ? clientEntity.primaryBusinessUnit.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.client.industry">Industry</Translate>
          </dt>
          <dd>{clientEntity.industry ? clientEntity.industry.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.client.category">Category</Translate>
          </dt>
          <dd>{clientEntity.category ? clientEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.client.paymentTerms">Payment Terms</Translate>
          </dt>
          <dd>{clientEntity.paymentTerms ? clientEntity.paymentTerms.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.client.practice">Practice</Translate>
          </dt>
          <dd>{clientEntity.practice ? clientEntity.practice.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.client.primaryOwnerUser">Primary Owner User</Translate>
          </dt>
          <dd>{clientEntity.primaryOwnerUser ? clientEntity.primaryOwnerUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;
