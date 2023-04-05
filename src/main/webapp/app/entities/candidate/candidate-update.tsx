import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICatalogueValue } from 'app/shared/model/catalogue-value.model';
import { getEntities as getCatalogueValues } from 'app/entities/catalogue-value/catalogue-value.reducer';
import { IRUser } from 'app/shared/model/r-user.model';
import { getEntities as getRUsers } from 'app/entities/r-user/r-user.reducer';
import { ICandidate } from 'app/shared/model/candidate.model';
import { SalaryTimeSpan } from 'app/shared/model/enumerations/salary-time-span.model';
import { CandidateRelocationType } from 'app/shared/model/enumerations/candidate-relocation-type.model';
import { getEntity, updateEntity, createEntity, reset } from './candidate.reducer';

export const CandidateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const catalogueValues = useAppSelector(state => state.catalogueValue.entities);
  const rUsers = useAppSelector(state => state.rUser.entities);
  const candidateEntity = useAppSelector(state => state.candidate.entity);
  const loading = useAppSelector(state => state.candidate.loading);
  const updating = useAppSelector(state => state.candidate.updating);
  const updateSuccess = useAppSelector(state => state.candidate.updateSuccess);
  const salaryTimeSpanValues = Object.keys(SalaryTimeSpan);
  const candidateRelocationTypeValues = Object.keys(CandidateRelocationType);

  const handleClose = () => {
    navigate('/candidate' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCatalogueValues({}));
    dispatch(getRUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dob = convertDateTimeToServer(values.dob);
    values.workAuthorizationExpiry = convertDateTimeToServer(values.workAuthorizationExpiry);

    const entity = {
      ...candidateEntity,
      ...values,
      workAuthorization: catalogueValues.find(it => it.id.toString() === values.workAuthorization.toString()),
      currentJobType: catalogueValues.find(it => it.id.toString() === values.currentJobType.toString()),
      expectedPayTaxTerm: catalogueValues.find(it => it.id.toString() === values.expectedPayTaxTerm.toString()),
      ownershipUser: rUsers.find(it => it.id.toString() === values.ownershipUser.toString()),
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
          dob: displayDefaultDateTime(),
          workAuthorizationExpiry: displayDefaultDateTime(),
        }
      : {
          currentPayTimeSpan: 'Fortnight',
          expectedPayTimeSpan: 'Fortnight',
          relocationType: 'Within_City',
          ...candidateEntity,
          dob: convertDateTimeFromServer(candidateEntity.dob),
          workAuthorizationExpiry: convertDateTimeFromServer(candidateEntity.workAuthorizationExpiry),
          workAuthorization: candidateEntity?.workAuthorization?.id,
          ownershipUser: candidateEntity?.ownershipUser?.id,
          currentJobType: candidateEntity?.currentJobType?.id,
          expectedPayTaxTerm: candidateEntity?.expectedPayTaxTerm?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.candidate.home.createOrEditLabel" data-cy="CandidateCreateUpdateHeading">
            <Translate contentKey="rproductApp.candidate.home.createOrEditLabel">Create or edit a Candidate</Translate>
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
                  id="candidate-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.candidate.salutation')}
                id="candidate-salutation"
                name="salutation"
                data-cy="salutation"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.firstName')}
                id="candidate-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.middleName')}
                id="candidate-middleName"
                name="middleName"
                data-cy="middleName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.lastName')}
                id="candidate-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.email')}
                id="candidate-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.altEmails')}
                id="candidate-altEmails"
                name="altEmails"
                data-cy="altEmails"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.phone')}
                id="candidate-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.altPhones')}
                id="candidate-altPhones"
                name="altPhones"
                data-cy="altPhones"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.dob')}
                id="candidate-dob"
                name="dob"
                data-cy="dob"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.nationality')}
                id="candidate-nationality"
                name="nationality"
                data-cy="nationality"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.workAuthorizationId')}
                id="candidate-workAuthorizationId"
                name="workAuthorizationId"
                data-cy="workAuthorizationId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.address')}
                id="candidate-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.candidate.area')} id="candidate-area" name="area" data-cy="area" type="text" />
              <ValidatedField label={translate('rproductApp.candidate.city')} id="candidate-city" name="city" data-cy="city" type="text" />
              <ValidatedField
                label={translate('rproductApp.candidate.state')}
                id="candidate-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.stateCode')}
                id="candidate-stateCode"
                name="stateCode"
                data-cy="stateCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.county')}
                id="candidate-county"
                name="county"
                data-cy="county"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.country')}
                id="candidate-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.countryCode')}
                id="candidate-countryCode"
                name="countryCode"
                data-cy="countryCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.zipCode')}
                id="candidate-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.source')}
                id="candidate-source"
                name="source"
                data-cy="source"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.totalExpInYears')}
                id="candidate-totalExpInYears"
                name="totalExpInYears"
                data-cy="totalExpInYears"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.totalExpInMonths')}
                id="candidate-totalExpInMonths"
                name="totalExpInMonths"
                data-cy="totalExpInMonths"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.relevantExpInYears')}
                id="candidate-relevantExpInYears"
                name="relevantExpInYears"
                data-cy="relevantExpInYears"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.relevantExpInMonths')}
                id="candidate-relevantExpInMonths"
                name="relevantExpInMonths"
                data-cy="relevantExpInMonths"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.referredBy')}
                id="candidate-referredBy"
                name="referredBy"
                data-cy="referredBy"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.ownershipUserId')}
                id="candidate-ownershipUserId"
                name="ownershipUserId"
                data-cy="ownershipUserId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentJobTitle')}
                id="candidate-currentJobTitle"
                name="currentJobTitle"
                data-cy="currentJobTitle"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentEmployer')}
                id="candidate-currentEmployer"
                name="currentEmployer"
                data-cy="currentEmployer"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentJobTypeId')}
                id="candidate-currentJobTypeId"
                name="currentJobTypeId"
                data-cy="currentJobTypeId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentPayCurrency')}
                id="candidate-currentPayCurrency"
                name="currentPayCurrency"
                data-cy="currentPayCurrency"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentPay')}
                id="candidate-currentPay"
                name="currentPay"
                data-cy="currentPay"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentPayMonthly')}
                id="candidate-currentPayMonthly"
                name="currentPayMonthly"
                data-cy="currentPayMonthly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentPayHourly')}
                id="candidate-currentPayHourly"
                name="currentPayHourly"
                data-cy="currentPayHourly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentPayYearly')}
                id="candidate-currentPayYearly"
                name="currentPayYearly"
                data-cy="currentPayYearly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.currentPayTimeSpan')}
                id="candidate-currentPayTimeSpan"
                name="currentPayTimeSpan"
                data-cy="currentPayTimeSpan"
                type="select"
              >
                {salaryTimeSpanValues.map(salaryTimeSpan => (
                  <option value={salaryTimeSpan} key={salaryTimeSpan}>
                    {translate('rproductApp.SalaryTimeSpan.' + salaryTimeSpan)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.candidate.otherCurrentBenefits')}
                id="candidate-otherCurrentBenefits"
                name="otherCurrentBenefits"
                data-cy="otherCurrentBenefits"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayCurrency')}
                id="candidate-expectedPayCurrency"
                name="expectedPayCurrency"
                data-cy="expectedPayCurrency"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMin')}
                id="candidate-expectedPayMin"
                name="expectedPayMin"
                data-cy="expectedPayMin"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMax')}
                id="candidate-expectedPayMax"
                name="expectedPayMax"
                data-cy="expectedPayMax"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMinMonthly')}
                id="candidate-expectedPayMinMonthly"
                name="expectedPayMinMonthly"
                data-cy="expectedPayMinMonthly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMinHourly')}
                id="candidate-expectedPayMinHourly"
                name="expectedPayMinHourly"
                data-cy="expectedPayMinHourly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMinYearly')}
                id="candidate-expectedPayMinYearly"
                name="expectedPayMinYearly"
                data-cy="expectedPayMinYearly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMaxMonthly')}
                id="candidate-expectedPayMaxMonthly"
                name="expectedPayMaxMonthly"
                data-cy="expectedPayMaxMonthly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMaxHourly')}
                id="candidate-expectedPayMaxHourly"
                name="expectedPayMaxHourly"
                data-cy="expectedPayMaxHourly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayMaxYearly')}
                id="candidate-expectedPayMaxYearly"
                name="expectedPayMaxYearly"
                data-cy="expectedPayMaxYearly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayTimeSpan')}
                id="candidate-expectedPayTimeSpan"
                name="expectedPayTimeSpan"
                data-cy="expectedPayTimeSpan"
                type="select"
              >
                {salaryTimeSpanValues.map(salaryTimeSpan => (
                  <option value={salaryTimeSpan} key={salaryTimeSpan}>
                    {translate('rproductApp.SalaryTimeSpan.' + salaryTimeSpan)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.candidate.expectedPayTaxTermId')}
                id="candidate-expectedPayTaxTermId"
                name="expectedPayTaxTermId"
                data-cy="expectedPayTaxTermId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.otherExpectedBenefits')}
                id="candidate-otherExpectedBenefits"
                name="otherExpectedBenefits"
                data-cy="otherExpectedBenefits"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.additionalComments')}
                id="candidate-additionalComments"
                name="additionalComments"
                data-cy="additionalComments"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.relocation')}
                id="candidate-relocation"
                name="relocation"
                data-cy="relocation"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.familyWillingToRelocate')}
                id="candidate-familyWillingToRelocate"
                name="familyWillingToRelocate"
                data-cy="familyWillingToRelocate"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.relocationType')}
                id="candidate-relocationType"
                name="relocationType"
                data-cy="relocationType"
                type="select"
              >
                {candidateRelocationTypeValues.map(candidateRelocationType => (
                  <option value={candidateRelocationType} key={candidateRelocationType}>
                    {translate('rproductApp.CandidateRelocationType.' + candidateRelocationType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.candidate.relocationRemarks')}
                id="candidate-relocationRemarks"
                name="relocationRemarks"
                data-cy="relocationRemarks"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.taxTermIds')}
                id="candidate-taxTermIds"
                name="taxTermIds"
                data-cy="taxTermIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.noticePeriodInDays')}
                id="candidate-noticePeriodInDays"
                name="noticePeriodInDays"
                data-cy="noticePeriodInDays"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.workAuthorizationExpiry')}
                id="candidate-workAuthorizationExpiry"
                name="workAuthorizationExpiry"
                data-cy="workAuthorizationExpiry"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('rproductApp.candidate.gpa')} id="candidate-gpa" name="gpa" data-cy="gpa" type="text" />
              <ValidatedField
                label={translate('rproductApp.candidate.aadharNumber')}
                id="candidate-aadharNumber"
                name="aadharNumber"
                data-cy="aadharNumber"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.linkedInProfileURL')}
                id="candidate-linkedInProfileURL"
                name="linkedInProfileURL"
                data-cy="linkedInProfileURL"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.otherSocialURLs')}
                id="candidate-otherSocialURLs"
                name="otherSocialURLs"
                data-cy="otherSocialURLs"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.candidate.tags')} id="candidate-tags" name="tags" data-cy="tags" type="text" />
              <ValidatedField
                label={translate('rproductApp.candidate.resumes')}
                id="candidate-resumes"
                name="resumes"
                data-cy="resumes"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.rightToReperesent')}
                id="candidate-rightToReperesent"
                name="rightToReperesent"
                data-cy="rightToReperesent"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.skills')}
                id="candidate-skills"
                name="skills"
                data-cy="skills"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.altSkills')}
                id="candidate-altSkills"
                name="altSkills"
                data-cy="altSkills"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.technologies')}
                id="candidate-technologies"
                name="technologies"
                data-cy="technologies"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.certifications')}
                id="candidate-certifications"
                name="certifications"
                data-cy="certifications"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.languages')}
                id="candidate-languages"
                name="languages"
                data-cy="languages"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.workExperience')}
                id="candidate-workExperience"
                name="workExperience"
                data-cy="workExperience"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.education')}
                id="candidate-education"
                name="education"
                data-cy="education"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.employerDetails')}
                id="candidate-employerDetails"
                name="employerDetails"
                data-cy="employerDetails"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidate.documents')}
                id="candidate-documents"
                name="documents"
                data-cy="documents"
                type="text"
              />
              <ValidatedField
                id="candidate-workAuthorization"
                name="workAuthorization"
                data-cy="workAuthorization"
                label={translate('rproductApp.candidate.workAuthorization')}
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
                id="candidate-ownershipUser"
                name="ownershipUser"
                data-cy="ownershipUser"
                label={translate('rproductApp.candidate.ownershipUser')}
                type="select"
              >
                <option value="" key="0" />
                {rUsers
                  ? rUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="candidate-currentJobType"
                name="currentJobType"
                data-cy="currentJobType"
                label={translate('rproductApp.candidate.currentJobType')}
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
                id="candidate-expectedPayTaxTerm"
                name="expectedPayTaxTerm"
                data-cy="expectedPayTaxTerm"
                label={translate('rproductApp.candidate.expectedPayTaxTerm')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/candidate" replace color="info">
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

export default CandidateUpdate;
