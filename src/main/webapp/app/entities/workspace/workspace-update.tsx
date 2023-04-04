import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkspace } from 'app/shared/model/workspace.model';
import { getEntity, updateEntity, createEntity, reset } from './workspace.reducer';

export const WorkspaceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workspaceEntity = useAppSelector(state => state.workspace.entity);
  const loading = useAppSelector(state => state.workspace.loading);
  const updating = useAppSelector(state => state.workspace.updating);
  const updateSuccess = useAppSelector(state => state.workspace.updateSuccess);

  const handleClose = () => {
    navigate('/workspace' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...workspaceEntity,
      ...values,
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
          ...workspaceEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.workspace.home.createOrEditLabel" data-cy="WorkspaceCreateUpdateHeading">
            <Translate contentKey="rproductApp.workspace.home.createOrEditLabel">Create or edit a Workspace</Translate>
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
                  id="workspace-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('rproductApp.workspace.name')} id="workspace-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('rproductApp.workspace.orgName')}
                id="workspace-orgName"
                name="orgName"
                data-cy="orgName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.about')}
                id="workspace-about"
                name="about"
                data-cy="about"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.workspace.link')} id="workspace-link" name="link" data-cy="link" type="text" />
              <ValidatedField
                label={translate('rproductApp.workspace.orgURLs')}
                id="workspace-orgURLs"
                name="orgURLs"
                data-cy="orgURLs"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.ownerUserId')}
                id="workspace-ownerUserId"
                name="ownerUserId"
                data-cy="ownerUserId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.mainPhoneNumber')}
                id="workspace-mainPhoneNumber"
                name="mainPhoneNumber"
                data-cy="mainPhoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.altPhoneNumbers')}
                id="workspace-altPhoneNumbers"
                name="altPhoneNumbers"
                data-cy="altPhoneNumbers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.mainContactEmail')}
                id="workspace-mainContactEmail"
                name="mainContactEmail"
                data-cy="mainContactEmail"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.altContactEmails')}
                id="workspace-altContactEmails"
                name="altContactEmails"
                data-cy="altContactEmails"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.status')}
                id="workspace-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.enableAutoJoin')}
                id="workspace-enableAutoJoin"
                name="enableAutoJoin"
                data-cy="enableAutoJoin"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.workspace.maxUsers')}
                id="workspace-maxUsers"
                name="maxUsers"
                data-cy="maxUsers"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.workspace.tags')} id="workspace-tags" name="tags" data-cy="tags" type="text" />
              <ValidatedField
                label={translate('rproductApp.workspace.domains')}
                id="workspace-domains"
                name="domains"
                data-cy="domains"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/workspace" replace color="info">
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

export default WorkspaceUpdate;
