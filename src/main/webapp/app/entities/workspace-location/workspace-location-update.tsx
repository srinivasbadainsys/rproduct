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
import { IWorkspaceLocation } from 'app/shared/model/workspace-location.model';
import { getEntity, updateEntity, createEntity, reset } from './workspace-location.reducer';

export const WorkspaceLocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workspaces = useAppSelector(state => state.workspace.entities);
  const workspaceLocationEntity = useAppSelector(state => state.workspaceLocation.entity);
  const loading = useAppSelector(state => state.workspaceLocation.loading);
  const updating = useAppSelector(state => state.workspaceLocation.updating);
  const updateSuccess = useAppSelector(state => state.workspaceLocation.updateSuccess);

  const handleClose = () => {
    navigate('/workspace-location' + location.search);
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
    const entity = {
      ...workspaceLocationEntity,
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
      ? {}
      : {
          ...workspaceLocationEntity,
          workspace: workspaceLocationEntity?.workspace?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.workspaceLocation.home.createOrEditLabel" data-cy="WorkspaceLocationCreateUpdateHeading">
            <Translate contentKey="rproductApp.workspaceLocation.home.createOrEditLabel">Create or edit a WorkspaceLocation</Translate>
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
                  id="workspace-location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.area')}
                id="workspace-location-area"
                name="area"
                data-cy="area"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.city')}
                id="workspace-location-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.state')}
                id="workspace-location-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.stateCode')}
                id="workspace-location-stateCode"
                name="stateCode"
                data-cy="stateCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.country')}
                id="workspace-location-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.countryCode')}
                id="workspace-location-countryCode"
                name="countryCode"
                data-cy="countryCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.zipCode')}
                id="workspace-location-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.lat')}
                id="workspace-location-lat"
                name="lat"
                data-cy="lat"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.lon')}
                id="workspace-location-lon"
                name="lon"
                data-cy="lon"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.continent')}
                id="workspace-location-continent"
                name="continent"
                data-cy="continent"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.continentCode')}
                id="workspace-location-continentCode"
                name="continentCode"
                data-cy="continentCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.workspaceLocation.point')}
                id="workspace-location-point"
                name="point"
                data-cy="point"
                type="text"
              />
              <ValidatedField
                id="workspace-location-workspace"
                name="workspace"
                data-cy="workspace"
                label={translate('rproductApp.workspaceLocation.workspace')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/workspace-location" replace color="info">
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

export default WorkspaceLocationUpdate;
