import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRuser } from 'app/shared/model/ruser.model';
import { getEntities as getRusers } from 'app/entities/ruser/ruser.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { getEntity, updateEntity, createEntity, reset } from './contact.reducer';

export const ContactUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rusers = useAppSelector(state => state.ruser.entities);
  const clients = useAppSelector(state => state.client.entities);
  const contactEntity = useAppSelector(state => state.contact.entity);
  const loading = useAppSelector(state => state.contact.loading);
  const updating = useAppSelector(state => state.contact.updating);
  const updateSuccess = useAppSelector(state => state.contact.updateSuccess);

  const handleClose = () => {
    navigate('/contact' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRusers({}));
    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contactEntity,
      ...values,
      primaryOwnerUser: rusers.find(it => it.id.toString() === values.primaryOwnerUser.toString()),
      client: clients.find(it => it.id.toString() === values.client.toString()),
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
          ...contactEntity,
          primaryOwnerUser: contactEntity?.primaryOwnerUser?.id,
          client: contactEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.contact.home.createOrEditLabel" data-cy="ContactCreateUpdateHeading">
            <Translate contentKey="rproductApp.contact.home.createOrEditLabel">Create or edit a Contact</Translate>
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
                  id="contact-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.contact.clientId')}
                id="contact-clientId"
                name="clientId"
                data-cy="clientId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.firstName')}
                id="contact-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.lastName')}
                id="contact-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.designation')}
                id="contact-designation"
                name="designation"
                data-cy="designation"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.officeNumber')}
                id="contact-officeNumber"
                name="officeNumber"
                data-cy="officeNumber"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.officeNumberExtn')}
                id="contact-officeNumberExtn"
                name="officeNumberExtn"
                data-cy="officeNumberExtn"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.mobileNumber')}
                id="contact-mobileNumber"
                name="mobileNumber"
                data-cy="mobileNumber"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.emailID')}
                id="contact-emailID"
                name="emailID"
                data-cy="emailID"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.altEmailID')}
                id="contact-altEmailID"
                name="altEmailID"
                data-cy="altEmailID"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.ownershipIds')}
                id="contact-ownershipIds"
                name="ownershipIds"
                data-cy="ownershipIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.allowAccessToAllUsers')}
                id="contact-allowAccessToAllUsers"
                name="allowAccessToAllUsers"
                data-cy="allowAccessToAllUsers"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.contact.address1')}
                id="contact-address1"
                name="address1"
                data-cy="address1"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.address2')}
                id="contact-address2"
                name="address2"
                data-cy="address2"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.contact.area')} id="contact-area" name="area" data-cy="area" type="text" />
              <ValidatedField label={translate('rproductApp.contact.city')} id="contact-city" name="city" data-cy="city" type="text" />
              <ValidatedField label={translate('rproductApp.contact.state')} id="contact-state" name="state" data-cy="state" type="text" />
              <ValidatedField
                label={translate('rproductApp.contact.stateCode')}
                id="contact-stateCode"
                name="stateCode"
                data-cy="stateCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.county')}
                id="contact-county"
                name="county"
                data-cy="county"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.country')}
                id="contact-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.countryCode')}
                id="contact-countryCode"
                name="countryCode"
                data-cy="countryCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.zipCode')}
                id="contact-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.profileURLs')}
                id="contact-profileURLs"
                name="profileURLs"
                data-cy="profileURLs"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.messengerIDs')}
                id="contact-messengerIDs"
                name="messengerIDs"
                data-cy="messengerIDs"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.status')}
                id="contact-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.contact.clientGroup')}
                id="contact-clientGroup"
                name="clientGroup"
                data-cy="clientGroup"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.contact.about')} id="contact-about" name="about" data-cy="about" type="text" />
              <ValidatedField
                id="contact-primaryOwnerUser"
                name="primaryOwnerUser"
                data-cy="primaryOwnerUser"
                label={translate('rproductApp.contact.primaryOwnerUser')}
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
                id="contact-client"
                name="client"
                data-cy="client"
                label={translate('rproductApp.contact.client')}
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contact" replace color="info">
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

export default ContactUpdate;
