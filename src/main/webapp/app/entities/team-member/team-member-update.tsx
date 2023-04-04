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
import { ITeam } from 'app/shared/model/team.model';
import { getEntities as getTeams } from 'app/entities/team/team.reducer';
import { ITeamMember } from 'app/shared/model/team-member.model';
import { getEntity, updateEntity, createEntity, reset } from './team-member.reducer';

export const TeamMemberUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rusers = useAppSelector(state => state.ruser.entities);
  const teams = useAppSelector(state => state.team.entities);
  const teamMemberEntity = useAppSelector(state => state.teamMember.entity);
  const loading = useAppSelector(state => state.teamMember.loading);
  const updating = useAppSelector(state => state.teamMember.updating);
  const updateSuccess = useAppSelector(state => state.teamMember.updateSuccess);

  const handleClose = () => {
    navigate('/team-member' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRusers({}));
    dispatch(getTeams({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...teamMemberEntity,
      ...values,
      memberUser: rusers.find(it => it.id.toString() === values.memberUser.toString()),
      team: teams.find(it => it.id.toString() === values.team.toString()),
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
          ...teamMemberEntity,
          memberUser: teamMemberEntity?.memberUser?.id,
          team: teamMemberEntity?.team?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.teamMember.home.createOrEditLabel" data-cy="TeamMemberCreateUpdateHeading">
            <Translate contentKey="rproductApp.teamMember.home.createOrEditLabel">Create or edit a TeamMember</Translate>
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
                  id="team-member-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.teamMember.teamId')}
                id="team-member-teamId"
                name="teamId"
                data-cy="teamId"
                type="text"
              />
              <ValidatedField
                id="team-member-memberUser"
                name="memberUser"
                data-cy="memberUser"
                label={translate('rproductApp.teamMember.memberUser')}
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
                id="team-member-team"
                name="team"
                data-cy="team"
                label={translate('rproductApp.teamMember.team')}
                type="select"
              >
                <option value="" key="0" />
                {teams
                  ? teams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/team-member" replace color="info">
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

export default TeamMemberUpdate;
