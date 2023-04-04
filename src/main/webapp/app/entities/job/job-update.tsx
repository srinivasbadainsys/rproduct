import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITeam } from 'app/shared/model/team.model';
import { getEntities as getTeams } from 'app/entities/team/team.reducer';
import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { getEntities as getBusinessUnits } from 'app/entities/business-unit/business-unit.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { ICatalogueValue } from 'app/shared/model/catalogue-value.model';
import { getEntities as getCatalogueValues } from 'app/entities/catalogue-value/catalogue-value.reducer';
import { IRuser } from 'app/shared/model/ruser.model';
import { getEntities as getRusers } from 'app/entities/ruser/ruser.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { getEntities as getContacts } from 'app/entities/contact/contact.reducer';
import { IJob } from 'app/shared/model/job.model';
import { SalaryTimeSpan } from 'app/shared/model/enumerations/salary-time-span.model';
import { JobApplicationForm } from 'app/shared/model/enumerations/job-application-form.model';
import { JobStatus } from 'app/shared/model/enumerations/job-status.model';
import { RemoteJob } from 'app/shared/model/enumerations/remote-job.model';
import { getEntity, updateEntity, createEntity, reset } from './job.reducer';

export const JobUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const teams = useAppSelector(state => state.team.entities);
  const businessUnits = useAppSelector(state => state.businessUnit.entities);
  const clients = useAppSelector(state => state.client.entities);
  const catalogueValues = useAppSelector(state => state.catalogueValue.entities);
  const rusers = useAppSelector(state => state.ruser.entities);
  const contacts = useAppSelector(state => state.contact.entities);
  const jobEntity = useAppSelector(state => state.job.entity);
  const loading = useAppSelector(state => state.job.loading);
  const updating = useAppSelector(state => state.job.updating);
  const updateSuccess = useAppSelector(state => state.job.updateSuccess);
  const salaryTimeSpanValues = Object.keys(SalaryTimeSpan);
  const jobApplicationFormValues = Object.keys(JobApplicationForm);
  const jobStatusValues = Object.keys(JobStatus);
  const remoteJobValues = Object.keys(RemoteJob);

  const handleClose = () => {
    navigate('/job' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTeams({}));
    dispatch(getBusinessUnits({}));
    dispatch(getClients({}));
    dispatch(getCatalogueValues({}));
    dispatch(getRusers({}));
    dispatch(getContacts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.autoCloseDate = convertDateTimeToServer(values.autoCloseDate);

    const entity = {
      ...jobEntity,
      ...values,
      postedByTeam: teams.find(it => it.id.toString() === values.postedByTeam.toString()),
      businessUnit: businessUnits.find(it => it.id.toString() === values.businessUnit.toString()),
      client: clients.find(it => it.id.toString() === values.client.toString()),
      jobType: catalogueValues.find(it => it.id.toString() === values.jobType.toString()),
      industry: catalogueValues.find(it => it.id.toString() === values.industry.toString()),
      department: catalogueValues.find(it => it.id.toString() === values.department.toString()),
      domain: catalogueValues.find(it => it.id.toString() === values.domain.toString()),
      priority: catalogueValues.find(it => it.id.toString() === values.priority.toString()),
      jobCloseReason: catalogueValues.find(it => it.id.toString() === values.jobCloseReason.toString()),
      jobCategory: catalogueValues.find(it => it.id.toString() === values.jobCategory.toString()),
      jobOccupation: catalogueValues.find(it => it.id.toString() === values.jobOccupation.toString()),
      clientRecruiter: rusers.find(it => it.id.toString() === values.clientRecruiter.toString()),
      accountManager: rusers.find(it => it.id.toString() === values.accountManager.toString()),
      headOfRecruitment: rusers.find(it => it.id.toString() === values.headOfRecruitment.toString()),
      deliveryLeadManager: rusers.find(it => it.id.toString() === values.deliveryLeadManager.toString()),
      srDeliveryManager: rusers.find(it => it.id.toString() === values.srDeliveryManager.toString()),
      teamLead: rusers.find(it => it.id.toString() === values.teamLead.toString()),
      clientManager: contacts.find(it => it.id.toString() === values.clientManager.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          autoCloseDate: displayDefaultDateTime(),
        }
      : {
          candRateTimeSpan: 'PerAnnum',
          clientBillRateTimeSpan: 'PerAnnum',
          applicationFormType: 'General_Application',
          jobStatus: 'Active',
          remoteJob: 'Yes',
          ...jobEntity,
          autoCloseDate: convertDateTimeFromServer(jobEntity.autoCloseDate),
          postedByTeam: jobEntity?.postedByTeam?.id,
          businessUnit: jobEntity?.businessUnit?.id,
          client: jobEntity?.client?.id,
          jobType: jobEntity?.jobType?.id,
          industry: jobEntity?.industry?.id,
          department: jobEntity?.department?.id,
          clientRecruiter: jobEntity?.clientRecruiter?.id,
          clientManager: jobEntity?.clientManager?.id,
          accountManager: jobEntity?.accountManager?.id,
          headOfRecruitment: jobEntity?.headOfRecruitment?.id,
          deliveryLeadManager: jobEntity?.deliveryLeadManager?.id,
          domain: jobEntity?.domain?.id,
          srDeliveryManager: jobEntity?.srDeliveryManager?.id,
          teamLead: jobEntity?.teamLead?.id,
          priority: jobEntity?.priority?.id,
          jobCloseReason: jobEntity?.jobCloseReason?.id,
          jobCategory: jobEntity?.jobCategory?.id,
          jobOccupation: jobEntity?.jobOccupation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.job.home.createOrEditLabel" data-cy="JobCreateUpdateHeading">
            <Translate contentKey="rproductApp.job.home.createOrEditLabel">Create or edit a Job</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="job-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('rproductApp.job.title')} id="job-title" name="title" data-cy="title" type="text" />
              <ValidatedField label={translate('rproductApp.job.jobCode')} id="job-jobCode" name="jobCode" data-cy="jobCode" type="text" />
              <ValidatedField
                label={translate('rproductApp.job.clientJobCode')}
                id="job-clientJobCode"
                name="clientJobCode"
                data-cy="clientJobCode"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.job.orgName')} id="job-orgName" name="orgName" data-cy="orgName" type="text" />
              <ValidatedField
                label={translate('rproductApp.job.orgEmploymentTypeIds')}
                id="job-orgEmploymentTypeIds"
                name="orgEmploymentTypeIds"
                data-cy="orgEmploymentTypeIds"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.job.jobRef')} id="job-jobRef" name="jobRef" data-cy="jobRef" type="text" />
              <ValidatedField
                label={translate('rproductApp.job.jobSource')}
                id="job-jobSource"
                name="jobSource"
                data-cy="jobSource"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.job.url')} id="job-url" name="url" data-cy="url" type="text" />
              <ValidatedField
                label={translate('rproductApp.job.description')}
                id="job-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.publicJobTitle')}
                id="job-publicJobTitle"
                name="publicJobTitle"
                data-cy="publicJobTitle"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.publicJobDescription')}
                id="job-publicJobDescription"
                name="publicJobDescription"
                data-cy="publicJobDescription"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.autoCloseDate')}
                id="job-autoCloseDate"
                name="autoCloseDate"
                data-cy="autoCloseDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rproductApp.job.noOfPositions')}
                id="job-noOfPositions"
                name="noOfPositions"
                data-cy="noOfPositions"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.departmentAltText')}
                id="job-departmentAltText"
                name="departmentAltText"
                data-cy="departmentAltText"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.displayCandRate')}
                id="job-displayCandRate"
                name="displayCandRate"
                data-cy="displayCandRate"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.job.candRateCurrency')}
                id="job-candRateCurrency"
                name="candRateCurrency"
                data-cy="candRateCurrency"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.candMinRate')}
                id="job-candMinRate"
                name="candMinRate"
                data-cy="candMinRate"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.candMaxRate')}
                id="job-candMaxRate"
                name="candMaxRate"
                data-cy="candMaxRate"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.candRateTimeSpan')}
                id="job-candRateTimeSpan"
                name="candRateTimeSpan"
                data-cy="candRateTimeSpan"
                type="select"
              >
                {salaryTimeSpanValues.map(salaryTimeSpan => (
                  <option value={salaryTimeSpan} key={salaryTimeSpan}>
                    {translate('rproductApp.SalaryTimeSpan.' + salaryTimeSpan)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.job.candRateTaxTerm')}
                id="job-candRateTaxTerm"
                name="candRateTaxTerm"
                data-cy="candRateTaxTerm"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.candSalaryAltDisplayText')}
                id="job-candSalaryAltDisplayText"
                name="candSalaryAltDisplayText"
                data-cy="candSalaryAltDisplayText"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.otherBenefitDetails')}
                id="job-otherBenefitDetails"
                name="otherBenefitDetails"
                data-cy="otherBenefitDetails"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.clientBillRateCurrency')}
                id="job-clientBillRateCurrency"
                name="clientBillRateCurrency"
                data-cy="clientBillRateCurrency"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.clientBillRate')}
                id="job-clientBillRate"
                name="clientBillRate"
                data-cy="clientBillRate"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.clientBillRateTimeSpan')}
                id="job-clientBillRateTimeSpan"
                name="clientBillRateTimeSpan"
                data-cy="clientBillRateTimeSpan"
                type="select"
              >
                {salaryTimeSpanValues.map(salaryTimeSpan => (
                  <option value={salaryTimeSpan} key={salaryTimeSpan}>
                    {translate('rproductApp.SalaryTimeSpan.' + salaryTimeSpan)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.job.clientBillRateTaxTerm')}
                id="job-clientBillRateTaxTerm"
                name="clientBillRateTaxTerm"
                data-cy="clientBillRateTaxTerm"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.workDuration')}
                id="job-workDuration"
                name="workDuration"
                data-cy="workDuration"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.immigrationStatus')}
                id="job-immigrationStatus"
                name="immigrationStatus"
                data-cy="immigrationStatus"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.displayImmigrationStatus')}
                id="job-displayImmigrationStatus"
                name="displayImmigrationStatus"
                data-cy="displayImmigrationStatus"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.job.skills')} id="job-skills" name="skills" data-cy="skills" type="text" />
              <ValidatedField
                label={translate('rproductApp.job.altSkills')}
                id="job-altSkills"
                name="altSkills"
                data-cy="altSkills"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.job.tags')} id="job-tags" name="tags" data-cy="tags" type="text" />
              <ValidatedField
                label={translate('rproductApp.job.qualificationIds')}
                id="job-qualificationIds"
                name="qualificationIds"
                data-cy="qualificationIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.qualificationsAltText')}
                id="job-qualificationsAltText"
                name="qualificationsAltText"
                data-cy="qualificationsAltText"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.eduRequirements')}
                id="job-eduRequirements"
                name="eduRequirements"
                data-cy="eduRequirements"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.expRequirements')}
                id="job-expRequirements"
                name="expRequirements"
                data-cy="expRequirements"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.expAltText')}
                id="job-expAltText"
                name="expAltText"
                data-cy="expAltText"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.minExpInYears')}
                id="job-minExpInYears"
                name="minExpInYears"
                data-cy="minExpInYears"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.maxExpInYears')}
                id="job-maxExpInYears"
                name="maxExpInYears"
                data-cy="maxExpInYears"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.languageIds')}
                id="job-languageIds"
                name="languageIds"
                data-cy="languageIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.visaRequirements')}
                id="job-visaRequirements"
                name="visaRequirements"
                data-cy="visaRequirements"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.workAuthorizationIds')}
                id="job-workAuthorizationIds"
                name="workAuthorizationIds"
                data-cy="workAuthorizationIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.applicationFormType')}
                id="job-applicationFormType"
                name="applicationFormType"
                data-cy="applicationFormType"
                type="select"
              >
                {jobApplicationFormValues.map(jobApplicationForm => (
                  <option value={jobApplicationForm} key={jobApplicationForm}>
                    {translate('rproductApp.JobApplicationForm.' + jobApplicationForm)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.job.isPartnerJob')}
                id="job-isPartnerJob"
                name="isPartnerJob"
                data-cy="isPartnerJob"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.job.redirectionUrl')}
                id="job-redirectionUrl"
                name="redirectionUrl"
                data-cy="redirectionUrl"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.jobStatus')}
                id="job-jobStatus"
                name="jobStatus"
                data-cy="jobStatus"
                type="select"
              >
                {jobStatusValues.map(jobStatus => (
                  <option value={jobStatus} key={jobStatus}>
                    {translate('rproductApp.JobStatus.' + jobStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.job.endClient')}
                id="job-endClient"
                name="endClient"
                data-cy="endClient"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.domainAlt')}
                id="job-domainAlt"
                name="domainAlt"
                data-cy="domainAlt"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.comments')}
                id="job-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.additionalNotificationsToUserIds')}
                id="job-additionalNotificationsToUserIds"
                name="additionalNotificationsToUserIds"
                data-cy="additionalNotificationsToUserIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.additionalNotificationsToTeamIds')}
                id="job-additionalNotificationsToTeamIds"
                name="additionalNotificationsToTeamIds"
                data-cy="additionalNotificationsToTeamIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.requiredDocumentIds')}
                id="job-requiredDocumentIds"
                name="requiredDocumentIds"
                data-cy="requiredDocumentIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.jobCloseReasonOtherAlt')}
                id="job-jobCloseReasonOtherAlt"
                name="jobCloseReasonOtherAlt"
                data-cy="jobCloseReasonOtherAlt"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.addToCareerPage')}
                id="job-addToCareerPage"
                name="addToCareerPage"
                data-cy="addToCareerPage"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.job.remoteJob')}
                id="job-remoteJob"
                name="remoteJob"
                data-cy="remoteJob"
                type="select"
              >
                {remoteJobValues.map(remoteJob => (
                  <option value={remoteJob} key={remoteJob}>
                    {translate('rproductApp.RemoteJob.' + remoteJob)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.job.hiringFor')}
                id="job-hiringFor"
                name="hiringFor"
                data-cy="hiringFor"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.workDurationTimeSpan')}
                id="job-workDurationTimeSpan"
                name="workDurationTimeSpan"
                data-cy="workDurationTimeSpan"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.job.taxTerms')}
                id="job-taxTerms"
                name="taxTerms"
                data-cy="taxTerms"
                type="text"
              />
              <ValidatedField
                id="job-postedByTeam"
                name="postedByTeam"
                data-cy="postedByTeam"
                label={translate('rproductApp.job.postedByTeam')}
                type="select"
              >
                <option value="" key="0" />
                {teams
                  ? teams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-businessUnit"
                name="businessUnit"
                data-cy="businessUnit"
                label={translate('rproductApp.job.businessUnit')}
                type="select"
              >
                <option value="" key="0" />
                {businessUnits
                  ? businessUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="job-client" name="client" data-cy="client" label={translate('rproductApp.job.client')} type="select">
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="job-jobType" name="jobType" data-cy="jobType" label={translate('rproductApp.job.jobType')} type="select">
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-industry"
                name="industry"
                data-cy="industry"
                label={translate('rproductApp.job.industry')}
                type="select"
              >
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-department"
                name="department"
                data-cy="department"
                label={translate('rproductApp.job.department')}
                type="select"
              >
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-clientRecruiter"
                name="clientRecruiter"
                data-cy="clientRecruiter"
                label={translate('rproductApp.job.clientRecruiter')}
                type="select"
              >
                <option value="" key="0" />
                {rusers
                  ? rusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-clientManager"
                name="clientManager"
                data-cy="clientManager"
                label={translate('rproductApp.job.clientManager')}
                type="select"
              >
                <option value="" key="0" />
                {contacts
                  ? contacts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-accountManager"
                name="accountManager"
                data-cy="accountManager"
                label={translate('rproductApp.job.accountManager')}
                type="select"
              >
                <option value="" key="0" />
                {rusers
                  ? rusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-headOfRecruitment"
                name="headOfRecruitment"
                data-cy="headOfRecruitment"
                label={translate('rproductApp.job.headOfRecruitment')}
                type="select"
              >
                <option value="" key="0" />
                {rusers
                  ? rusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-deliveryLeadManager"
                name="deliveryLeadManager"
                data-cy="deliveryLeadManager"
                label={translate('rproductApp.job.deliveryLeadManager')}
                type="select"
              >
                <option value="" key="0" />
                {rusers
                  ? rusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="job-domain" name="domain" data-cy="domain" label={translate('rproductApp.job.domain')} type="select">
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-srDeliveryManager"
                name="srDeliveryManager"
                data-cy="srDeliveryManager"
                label={translate('rproductApp.job.srDeliveryManager')}
                type="select"
              >
                <option value="" key="0" />
                {rusers
                  ? rusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-teamLead"
                name="teamLead"
                data-cy="teamLead"
                label={translate('rproductApp.job.teamLead')}
                type="select"
              >
                <option value="" key="0" />
                {rusers
                  ? rusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-priority"
                name="priority"
                data-cy="priority"
                label={translate('rproductApp.job.priority')}
                type="select"
              >
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-jobCloseReason"
                name="jobCloseReason"
                data-cy="jobCloseReason"
                label={translate('rproductApp.job.jobCloseReason')}
                type="select"
              >
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-jobCategory"
                name="jobCategory"
                data-cy="jobCategory"
                label={translate('rproductApp.job.jobCategory')}
                type="select"
              >
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-jobOccupation"
                name="jobOccupation"
                data-cy="jobOccupation"
                label={translate('rproductApp.job.jobOccupation')}
                type="select"
              >
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default JobUpdate;
