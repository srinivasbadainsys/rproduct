import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IClientAccount } from 'app/shared/model/client-account.model';
import { getEntity, updateEntity, createEntity, reset } from './client-account.reducer';

export const ClientAccountUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const clientAccountEntity = useAppSelector(state => state.clientAccount.entity);
  const loading = useAppSelector(state => state.clientAccount.loading);
  const updating = useAppSelector(state => state.clientAccount.updating);
  const updateSuccess = useAppSelector(state => state.clientAccount.updateSuccess);

  const handleClose = () => {
    navigate('/client-account' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clientAccountEntity,
      ...values,
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
          ...clientAccountEntity,
          client: clientAccountEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.clientAccount.home.createOrEditLabel" data-cy="ClientAccountCreateUpdateHeading">
            <Translate contentKey="rproductApp.clientAccount.home.createOrEditLabel">Create or edit a ClientAccount</Translate>
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
                  id="client-account-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.clientAccount.clientId')}
                id="client-account-clientId"
                name="clientId"
                data-cy="clientId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientAccount.contactPerson')}
                id="client-account-contactPerson"
                name="contactPerson"
                data-cy="contactPerson"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientAccount.mobileNumber')}
                id="client-account-mobileNumber"
                name="mobileNumber"
                data-cy="mobileNumber"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientAccount.officeNumber')}
                id="client-account-officeNumber"
                name="officeNumber"
                data-cy="officeNumber"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientAccount.officeNumberExtn')}
                id="client-account-officeNumberExtn"
                name="officeNumberExtn"
                data-cy="officeNumberExtn"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientAccount.emailID')}
                id="client-account-emailID"
                name="emailID"
                data-cy="emailID"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientAccount.designation')}
                id="client-account-designation"
                name="designation"
                data-cy="designation"
                type="text"
              />
              <ValidatedField
                id="client-account-client"
                name="client"
                data-cy="client"
                label={translate('rproductApp.clientAccount.client')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client-account" replace color="info">
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

export default ClientAccountUpdate;
