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
import { IJob } from 'app/shared/model/job.model';
import { getEntities as getJobs } from 'app/entities/job/job.reducer';
import { IUserWork } from 'app/shared/model/user-work.model';
import { getEntity, updateEntity, createEntity, reset } from './user-work.reducer';

export const UserWorkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rusers = useAppSelector(state => state.ruser.entities);
  const teams = useAppSelector(state => state.team.entities);
  const jobs = useAppSelector(state => state.job.entities);
  const userWorkEntity = useAppSelector(state => state.userWork.entity);
  const loading = useAppSelector(state => state.userWork.loading);
  const updating = useAppSelector(state => state.userWork.updating);
  const updateSuccess = useAppSelector(state => state.userWork.updateSuccess);

  const handleClose = () => {
    navigate('/user-work' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRusers({}));
    dispatch(getTeams({}));
    dispatch(getJobs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...userWorkEntity,
      ...values,
      user: rusers.find(it => it.id.toString() === values.user.toString()),
      team: teams.find(it => it.id.toString() === values.team.toString()),
      job: jobs.find(it => it.id.toString() === values.job.toString()),
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
          ...userWorkEntity,
          user: userWorkEntity?.user?.id,
          team: userWorkEntity?.team?.id,
          job: userWorkEntity?.job?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.userWork.home.createOrEditLabel" data-cy="UserWorkCreateUpdateHeading">
            <Translate contentKey="rproductApp.userWork.home.createOrEditLabel">Create or edit a UserWork</Translate>
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
                  id="user-work-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.userWork.jobId')}
                id="user-work-jobId"
                name="jobId"
                data-cy="jobId"
                type="text"
              />
              <ValidatedField id="user-work-user" name="user" data-cy="user" label={translate('rproductApp.userWork.user')} type="select">
                <option value="" key="0" />
                {rusers
                  ? rusers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="user-work-team" name="team" data-cy="team" label={translate('rproductApp.userWork.team')} type="select">
                <option value="" key="0" />
                {teams
                  ? teams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="user-work-job" name="job" data-cy="job" label={translate('rproductApp.userWork.job')} type="select">
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-work" replace color="info">
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

export default UserWorkUpdate;
