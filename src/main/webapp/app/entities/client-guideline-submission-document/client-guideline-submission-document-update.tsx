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
import { IClientGuidelineSubmissionDocument } from 'app/shared/model/client-guideline-submission-document.model';
import { getEntity, updateEntity, createEntity, reset } from './client-guideline-submission-document.reducer';

export const ClientGuidelineSubmissionDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const clientGuidelineSubmissionDocumentEntity = useAppSelector(state => state.clientGuidelineSubmissionDocument.entity);
  const loading = useAppSelector(state => state.clientGuidelineSubmissionDocument.loading);
  const updating = useAppSelector(state => state.clientGuidelineSubmissionDocument.updating);
  const updateSuccess = useAppSelector(state => state.clientGuidelineSubmissionDocument.updateSuccess);

  const handleClose = () => {
    navigate('/client-guideline-submission-document' + location.search);
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
      ...clientGuidelineSubmissionDocumentEntity,
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
          ...clientGuidelineSubmissionDocumentEntity,
          client: clientGuidelineSubmissionDocumentEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="rproductApp.clientGuidelineSubmissionDocument.home.createOrEditLabel"
            data-cy="ClientGuidelineSubmissionDocumentCreateUpdateHeading"
          >
            <Translate contentKey="rproductApp.clientGuidelineSubmissionDocument.home.createOrEditLabel">
              Create or edit a ClientGuidelineSubmissionDocument
            </Translate>
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
                  id="client-guideline-submission-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.clientGuidelineSubmissionDocument.clientId')}
                id="client-guideline-submission-document-clientId"
                name="clientId"
                data-cy="clientId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientGuidelineSubmissionDocument.documentTitle')}
                id="client-guideline-submission-document-documentTitle"
                name="documentTitle"
                data-cy="documentTitle"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientGuidelineSubmissionDocument.description')}
                id="client-guideline-submission-document-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientGuidelineSubmissionDocument.documentPath')}
                id="client-guideline-submission-document-documentPath"
                name="documentPath"
                data-cy="documentPath"
                type="text"
              />
              <ValidatedField
                id="client-guideline-submission-document-client"
                name="client"
                data-cy="client"
                label={translate('rproductApp.clientGuidelineSubmissionDocument.client')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/client-guideline-submission-document"
                replace
                color="info"
              >
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

export default ClientGuidelineSubmissionDocumentUpdate;
