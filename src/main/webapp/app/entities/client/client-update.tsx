import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { getEntities as getBusinessUnits } from 'app/entities/business-unit/business-unit.reducer';
import { ICatalogueValue } from 'app/shared/model/catalogue-value.model';
import { getEntities as getCatalogueValues } from 'app/entities/catalogue-value/catalogue-value.reducer';
import { IRuser } from 'app/shared/model/ruser.model';
import { getEntities as getRusers } from 'app/entities/ruser/ruser.reducer';
import { IClient } from 'app/shared/model/client.model';
import { ClientVisibility } from 'app/shared/model/enumerations/client-visibility.model';
import { getEntity, updateEntity, createEntity, reset } from './client.reducer';

export const ClientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const businessUnits = useAppSelector(state => state.businessUnit.entities);
  const catalogueValues = useAppSelector(state => state.catalogueValue.entities);
  const rusers = useAppSelector(state => state.ruser.entities);
  const clientEntity = useAppSelector(state => state.client.entity);
  const loading = useAppSelector(state => state.client.loading);
  const updating = useAppSelector(state => state.client.updating);
  const updateSuccess = useAppSelector(state => state.client.updateSuccess);
  const clientVisibilityValues = Object.keys(ClientVisibility);

  const handleClose = () => {
    navigate('/client' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBusinessUnits({}));
    dispatch(getCatalogueValues({}));
    dispatch(getRusers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clientEntity,
      ...values,
      primaryBusinessUnit: businessUnits.find(it => it.id.toString() === values.primaryBusinessUnit.toString()),
      industry: catalogueValues.find(it => it.id.toString() === values.industry.toString()),
      category: catalogueValues.find(it => it.id.toString() === values.category.toString()),
      paymentTerms: catalogueValues.find(it => it.id.toString() === values.paymentTerms.toString()),
      practice: catalogueValues.find(it => it.id.toString() === values.practice.toString()),
      primaryOwnerUser: rusers.find(it => it.id.toString() === values.primaryOwnerUser.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          clientVisibility: 'Organization_Level',
          ...clientEntity,
          primaryBusinessUnit: clientEntity?.primaryBusinessUnit?.id,
          industry: clientEntity?.industry?.id,
          category: clientEntity?.category?.id,
          paymentTerms: clientEntity?.paymentTerms?.id,
          practice: clientEntity?.practice?.id,
          primaryOwnerUser: clientEntity?.primaryOwnerUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.client.home.createOrEditLabel" data-cy="ClientCreateUpdateHeading">
            <Translate contentKey="rproductApp.client.home.createOrEditLabel">Create or edit a Client</Translate>
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
                  id="client-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.client.clientVisibility')}
                id="client-clientVisibility"
                name="clientVisibility"
                data-cy="clientVisibility"
                type="select"
              >
                {clientVisibilityValues.map(clientVisibility => (
                  <option value={clientVisibility} key={clientVisibility}>
                    {translate('rproductApp.ClientVisibility.' + clientVisibility)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.client.businessUnitIds')}
                id="client-businessUnitIds"
                name="businessUnitIds"
                data-cy="businessUnitIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.clientName')}
                id="client-clientName"
                name="clientName"
                data-cy="clientName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.vmsClientName')}
                id="client-vmsClientName"
                name="vmsClientName"
                data-cy="vmsClientName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.federalID')}
                id="client-federalID"
                name="federalID"
                data-cy="federalID"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.contactNumber')}
                id="client-contactNumber"
                name="contactNumber"
                data-cy="contactNumber"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.client.email')} id="client-email" name="email" data-cy="email" type="text" />
              <ValidatedField
                label={translate('rproductApp.client.address')}
                id="client-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.client.area')} id="client-area" name="area" data-cy="area" type="text" />
              <ValidatedField label={translate('rproductApp.client.city')} id="client-city" name="city" data-cy="city" type="text" />
              <ValidatedField label={translate('rproductApp.client.state')} id="client-state" name="state" data-cy="state" type="text" />
              <ValidatedField
                label={translate('rproductApp.client.stateCode')}
                id="client-stateCode"
                name="stateCode"
                data-cy="stateCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.county')}
                id="client-county"
                name="county"
                data-cy="county"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.country')}
                id="client-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.countryCode')}
                id="client-countryCode"
                name="countryCode"
                data-cy="countryCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.zipCode')}
                id="client-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.website')}
                id="client-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.sendRequirement')}
                id="client-sendRequirement"
                name="sendRequirement"
                data-cy="sendRequirement"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.client.sendHotList')}
                id="client-sendHotList"
                name="sendHotList"
                data-cy="sendHotList"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('rproductApp.client.fax')} id="client-fax" name="fax" data-cy="fax" type="text" />
              <ValidatedField
                label={translate('rproductApp.client.status')}
                id="client-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.allowAccessToAllUsers')}
                id="client-allowAccessToAllUsers"
                name="allowAccessToAllUsers"
                data-cy="allowAccessToAllUsers"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.client.ownershipIds')}
                id="client-ownershipIds"
                name="ownershipIds"
                data-cy="ownershipIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.clientLeadIds')}
                id="client-clientLeadIds"
                name="clientLeadIds"
                data-cy="clientLeadIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.requiredDocuments')}
                id="client-requiredDocuments"
                name="requiredDocuments"
                data-cy="requiredDocuments"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.practiceAlt')}
                id="client-practiceAlt"
                name="practiceAlt"
                data-cy="practiceAlt"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.aboutCompany')}
                id="client-aboutCompany"
                name="aboutCompany"
                data-cy="aboutCompany"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.stopNotifyingClientContactOnSubmitToClient')}
                id="client-stopNotifyingClientContactOnSubmitToClient"
                name="stopNotifyingClientContactOnSubmitToClient"
                data-cy="stopNotifyingClientContactOnSubmitToClient"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.client.defaultForJobPostings')}
                id="client-defaultForJobPostings"
                name="defaultForJobPostings"
                data-cy="defaultForJobPostings"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.client.submissionGuidelines')}
                id="client-submissionGuidelines"
                name="submissionGuidelines"
                data-cy="submissionGuidelines"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.assignedTo')}
                id="client-assignedTo"
                name="assignedTo"
                data-cy="assignedTo"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.assignedToTeams')}
                id="client-assignedToTeams"
                name="assignedToTeams"
                data-cy="assignedToTeams"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.salesManagers')}
                id="client-salesManagers"
                name="salesManagers"
                data-cy="salesManagers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.accountManagers')}
                id="client-accountManagers"
                name="accountManagers"
                data-cy="accountManagers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.recruitmentManagers')}
                id="client-recruitmentManagers"
                name="recruitmentManagers"
                data-cy="recruitmentManagers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.defaultTATConfigForJobPostingOrVMSJobs')}
                id="client-defaultTATConfigForJobPostingOrVMSJobs"
                name="defaultTATConfigForJobPostingOrVMSJobs"
                data-cy="defaultTATConfigForJobPostingOrVMSJobs"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.defaultTATConfigTimespan')}
                id="client-defaultTATConfigTimespan"
                name="defaultTATConfigTimespan"
                data-cy="defaultTATConfigTimespan"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.notifyOnTATToUserTypes')}
                id="client-notifyOnTATToUserTypes"
                name="notifyOnTATToUserTypes"
                data-cy="notifyOnTATToUserTypes"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.notifyOnTATToUserIds')}
                id="client-notifyOnTATToUserIds"
                name="notifyOnTATToUserIds"
                data-cy="notifyOnTATToUserIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.taxTermIds')}
                id="client-taxTermIds"
                name="taxTermIds"
                data-cy="taxTermIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.workAuthorizationIds')}
                id="client-workAuthorizationIds"
                name="workAuthorizationIds"
                data-cy="workAuthorizationIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.postJobOnCareersPage')}
                id="client-postJobOnCareersPage"
                name="postJobOnCareersPage"
                data-cy="postJobOnCareersPage"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.client.defaultPayRateTaxTerm')}
                id="client-defaultPayRateTaxTerm"
                name="defaultPayRateTaxTerm"
                data-cy="defaultPayRateTaxTerm"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.defaultBillRateTaxTerm')}
                id="client-defaultBillRateTaxTerm"
                name="defaultBillRateTaxTerm"
                data-cy="defaultBillRateTaxTerm"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.referencesMandatoryForSubmission')}
                id="client-referencesMandatoryForSubmission"
                name="referencesMandatoryForSubmission"
                data-cy="referencesMandatoryForSubmission"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.client.maxSubmissions')}
                id="client-maxSubmissions"
                name="maxSubmissions"
                data-cy="maxSubmissions"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.noOfPositions')}
                id="client-noOfPositions"
                name="noOfPositions"
                data-cy="noOfPositions"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.markUp')}
                id="client-markUp"
                name="markUp"
                data-cy="markUp"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.client.msp')} id="client-msp" name="msp" data-cy="msp" type="text" />
              <ValidatedField
                label={translate('rproductApp.client.mailSubject')}
                id="client-mailSubject"
                name="mailSubject"
                data-cy="mailSubject"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.mailBody')}
                id="client-mailBody"
                name="mailBody"
                data-cy="mailBody"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.client.fieldsForExcel')}
                id="client-fieldsForExcel"
                name="fieldsForExcel"
                data-cy="fieldsForExcel"
                type="text"
              />
              <ValidatedField
                id="client-primaryBusinessUnit"
                name="primaryBusinessUnit"
                data-cy="primaryBusinessUnit"
                label={translate('rproductApp.client.primaryBusinessUnit')}
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
              <ValidatedField
                id="client-industry"
                name="industry"
                data-cy="industry"
                label={translate('rproductApp.client.industry')}
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
                id="client-category"
                name="category"
                data-cy="category"
                label={translate('rproductApp.client.category')}
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
                id="client-paymentTerms"
                name="paymentTerms"
                data-cy="paymentTerms"
                label={translate('rproductApp.client.paymentTerms')}
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
                id="client-practice"
                name="practice"
                data-cy="practice"
                label={translate('rproductApp.client.practice')}
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
                id="client-primaryOwnerUser"
                name="primaryOwnerUser"
                data-cy="primaryOwnerUser"
                label={translate('rproductApp.client.primaryOwnerUser')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client" replace color="info">
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

export default ClientUpdate;
