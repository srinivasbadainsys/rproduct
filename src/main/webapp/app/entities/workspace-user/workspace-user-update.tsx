import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkspace } from 'app/shared/model/workspace.model';
import { getEntities as getWorkspaces } from 'app/entities/workspace/workspace.reducer';
import { IWorkspaceUser } from 'app/shared/model/workspace-user.model';
import { getEntity, updateEntity, createEntity, reset } from './workspace-user.reducer';

export const WorkspaceUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workspaces = useAppSelector(state => state.workspace.entities);
  const workspaceUserEntity = useAppSelector(state => state.workspaceUser.entity);
  const loading = useAppSelector(state => state.workspaceUser.loading);
  const updating = useAppSelector(state => state.workspaceUser.updating);
  const updateSuccess = useAppSelector(state => state.workspaceUser.updateSuccess);

  const handleClose = () => {
    navigate('/workspace-user' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWorkspaces({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.requestedOn = convertDateTimeToServer(values.requestedOn);

    const entity = {
      ...workspaceUserEntity,
      ...values,
      workspace: workspaces.find(it => it.id.toString() === values.workspace.toString()),
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
          requestedOn: displayDefaultDateTime(),
        }
      : {
          ...workspaceUserEntity,
          requestedOn: convertDateTimeFromServer(workspaceUserEntity.requestedOn),
          workspace: workspaceUserEntity?.workspace?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.workspaceUser.home.createOrEditLabel" data-cy="WorkspaceUserCreateUpdateHeading">
            <Translate contentKey="rproductApp.workspaceUser.home.createOrEditLabel">Create or edit a WorkspaceUser</Translate>
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
                  id="workspace-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.workspaceUser.userId')}
                id="workspace-user-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.invitationKey')}
                id="workspace-user-invitationKey"
                name="invitationKey"
                data-cy="invitationKey"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.owns')}
                id="workspace-user-owns"
                name="owns"
                data-cy="owns"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.accepted')}
                id="workspace-user-accepted"
                name="accepted"
                data-cy="accepted"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.invited')}
                id="workspace-user-invited"
                name="invited"
                data-cy="invited"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.requested')}
                id="workspace-user-requested"
                name="requested"
                data-cy="requested"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.barred')}
                id="workspace-user-barred"
                name="barred"
                data-cy="barred"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.roles')}
                id="workspace-user-roles"
                name="roles"
                data-cy="roles"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceUser.requestedOn')}
                id="workspace-user-requestedOn"
                name="requestedOn"
                data-cy="requestedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="workspace-user-workspace"
                name="workspace"
                data-cy="workspace"
                label={translate('rproductApp.workspaceUser.workspace')}
                type="select"
              >
                <option value="" key="0" />
                {workspaces
                  ? workspaces.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/workspace-user" replace color="info">
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

export default WorkspaceUserUpdate;
