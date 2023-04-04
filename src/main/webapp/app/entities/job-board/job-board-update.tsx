import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobBoard } from 'app/shared/model/job-board.model';
import { getEntity, updateEntity, createEntity, reset } from './job-board.reducer';

export const JobBoardUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobBoardEntity = useAppSelector(state => state.jobBoard.entity);
  const loading = useAppSelector(state => state.jobBoard.loading);
  const updating = useAppSelector(state => state.jobBoard.updating);
  const updateSuccess = useAppSelector(state => state.jobBoard.updateSuccess);

  const handleClose = () => {
    navigate('/job-board' + location.search);
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
      ...jobBoardEntity,
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
          ...jobBoardEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.jobBoard.home.createOrEditLabel" data-cy="JobBoardCreateUpdateHeading">
            <Translate contentKey="rproductApp.jobBoard.home.createOrEditLabel">Create or edit a JobBoard</Translate>
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
                  id="job-board-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.jobBoard.jobBoardName')}
                id="job-board-jobBoardName"
                name="jobBoardName"
                data-cy="jobBoardName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoard.jobBoardType')}
                id="job-board-jobBoardType"
                name="jobBoardType"
                data-cy="jobBoardType"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoard.username')}
                id="job-board-username"
                name="username"
                data-cy="username"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoard.password')}
                id="job-board-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoard.settings')}
                id="job-board-settings"
                name="settings"
                data-cy="settings"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoard.autoRefresh')}
                id="job-board-autoRefresh"
                name="autoRefresh"
                data-cy="autoRefresh"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoard.jobDuration')}
                id="job-board-jobDuration"
                name="jobDuration"
                data-cy="jobDuration"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-board" replace color="info">
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

export default JobBoardUpdate;
