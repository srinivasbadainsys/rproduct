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
import { ITeam } from 'app/shared/model/team.model';
import { TeamType } from 'app/shared/model/enumerations/team-type.model';
import { getEntity, updateEntity, createEntity, reset } from './team.reducer';

export const TeamUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workspaces = useAppSelector(state => state.workspace.entities);
  const teamEntity = useAppSelector(state => state.team.entity);
  const loading = useAppSelector(state => state.team.loading);
  const updating = useAppSelector(state => state.team.updating);
  const updateSuccess = useAppSelector(state => state.team.updateSuccess);
  const teamTypeValues = Object.keys(TeamType);

  const handleClose = () => {
    navigate('/team' + location.search);
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
      ...teamEntity,
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
          type: 'TEAM',
          ...teamEntity,
          workspace: teamEntity?.workspace?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.team.home.createOrEditLabel" data-cy="TeamCreateUpdateHeading">
            <Translate contentKey="rproductApp.team.home.createOrEditLabel">Create or edit a Team</Translate>
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
                  id="team-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('rproductApp.team.name')} id="team-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('rproductApp.team.teamGroupEmail')}
                id="team-teamGroupEmail"
                name="teamGroupEmail"
                data-cy="teamGroupEmail"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.team.type')} id="team-type" name="type" data-cy="type" type="select">
                {teamTypeValues.map(teamType => (
                  <option value={teamType} key={teamType}>
                    {translate('rproductApp.TeamType.' + teamType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.team.notifyOnJobPostingToUsers')}
                id="team-notifyOnJobPostingToUsers"
                name="notifyOnJobPostingToUsers"
                data-cy="notifyOnJobPostingToUsers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.team.notifyOnJobSharingToUsers')}
                id="team-notifyOnJobSharingToUsers"
                name="notifyOnJobSharingToUsers"
                data-cy="notifyOnJobSharingToUsers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.team.notifyOnJobClosingToUsers')}
                id="team-notifyOnJobClosingToUsers"
                name="notifyOnJobClosingToUsers"
                data-cy="notifyOnJobClosingToUsers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.team.notifyOnCandSubmissionToUsers')}
                id="team-notifyOnCandSubmissionToUsers"
                name="notifyOnCandSubmissionToUsers"
                data-cy="notifyOnCandSubmissionToUsers"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.team.notifyOnCandStatusChangeToUsers')}
                id="team-notifyOnCandStatusChangeToUsers"
                name="notifyOnCandStatusChangeToUsers"
                data-cy="notifyOnCandStatusChangeToUsers"
                type="text"
              />
              <ValidatedField
                id="team-workspace"
                name="workspace"
                data-cy="workspace"
                label={translate('rproductApp.team.workspace')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/team" replace color="info">
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

export default TeamUpdate;
