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
import { IClientDocument } from 'app/shared/model/client-document.model';
import { getEntity, updateEntity, createEntity, reset } from './client-document.reducer';

export const ClientDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const clientDocumentEntity = useAppSelector(state => state.clientDocument.entity);
  const loading = useAppSelector(state => state.clientDocument.loading);
  const updating = useAppSelector(state => state.clientDocument.updating);
  const updateSuccess = useAppSelector(state => state.clientDocument.updateSuccess);

  const handleClose = () => {
    navigate('/client-document' + location.search);
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
      ...clientDocumentEntity,
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
          ...clientDocumentEntity,
          client: clientDocumentEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.clientDocument.home.createOrEditLabel" data-cy="ClientDocumentCreateUpdateHeading">
            <Translate contentKey="rproductApp.clientDocument.home.createOrEditLabel">Create or edit a ClientDocument</Translate>
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
                  id="client-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.clientDocument.clientId')}
                id="client-document-clientId"
                name="clientId"
                data-cy="clientId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientDocument.documentType')}
                id="client-document-documentType"
                name="documentType"
                data-cy="documentType"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientDocument.title')}
                id="client-document-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientDocument.description')}
                id="client-document-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientDocument.documentPath')}
                id="client-document-documentPath"
                name="documentPath"
                data-cy="documentPath"
                type="text"
              />
              <ValidatedField
                id="client-document-client"
                name="client"
                data-cy="client"
                label={translate('rproductApp.clientDocument.client')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client-document" replace color="info">
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

export default ClientDocumentUpdate;
