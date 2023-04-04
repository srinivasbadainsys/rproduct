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
import { IClientNote } from 'app/shared/model/client-note.model';
import { getEntity, updateEntity, createEntity, reset } from './client-note.reducer';

export const ClientNoteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const clientNoteEntity = useAppSelector(state => state.clientNote.entity);
  const loading = useAppSelector(state => state.clientNote.loading);
  const updating = useAppSelector(state => state.clientNote.updating);
  const updateSuccess = useAppSelector(state => state.clientNote.updateSuccess);

  const handleClose = () => {
    navigate('/client-note' + location.search);
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
      ...clientNoteEntity,
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
          ...clientNoteEntity,
          client: clientNoteEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.clientNote.home.createOrEditLabel" data-cy="ClientNoteCreateUpdateHeading">
            <Translate contentKey="rproductApp.clientNote.home.createOrEditLabel">Create or edit a ClientNote</Translate>
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
                  id="client-note-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.clientNote.clientId')}
                id="client-note-clientId"
                name="clientId"
                data-cy="clientId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientNote.action')}
                id="client-note-action"
                name="action"
                data-cy="action"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientNote.notesPriority')}
                id="client-note-notesPriority"
                name="notesPriority"
                data-cy="notesPriority"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientNote.note')}
                id="client-note-note"
                name="note"
                data-cy="note"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientNote.notifyToUsers')}
                id="client-note-notifyToUsers"
                name="notifyToUsers"
                data-cy="notifyToUsers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.clientNote.remindMe')}
                id="client-note-remindMe"
                name="remindMe"
                data-cy="remindMe"
                check
                type="checkbox"
              />
              <ValidatedField
                id="client-note-client"
                name="client"
                data-cy="client"
                label={translate('rproductApp.clientNote.client')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/client-note" replace color="info">
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

export default ClientNoteUpdate;
