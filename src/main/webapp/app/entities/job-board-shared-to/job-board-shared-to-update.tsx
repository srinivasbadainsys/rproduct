import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobBoard } from 'app/shared/model/job-board.model';
import { getEntities as getJobBoards } from 'app/entities/job-board/job-board.reducer';
import { IJobBoardSharedTo } from 'app/shared/model/job-board-shared-to.model';
import { getEntity, updateEntity, createEntity, reset } from './job-board-shared-to.reducer';

export const JobBoardSharedToUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobBoards = useAppSelector(state => state.jobBoard.entities);
  const jobBoardSharedToEntity = useAppSelector(state => state.jobBoardSharedTo.entity);
  const loading = useAppSelector(state => state.jobBoardSharedTo.loading);
  const updating = useAppSelector(state => state.jobBoardSharedTo.updating);
  const updateSuccess = useAppSelector(state => state.jobBoardSharedTo.updateSuccess);

  const handleClose = () => {
    navigate('/job-board-shared-to' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJobBoards({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.expiresOn = convertDateTimeToServer(values.expiresOn);

    const entity = {
      ...jobBoardSharedToEntity,
      ...values,
      jobBoard: jobBoards.find(it => it.id.toString() === values.jobBoard.toString()),
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
          expiresOn: displayDefaultDateTime(),
        }
      : {
          ...jobBoardSharedToEntity,
          expiresOn: convertDateTimeFromServer(jobBoardSharedToEntity.expiresOn),
          jobBoard: jobBoardSharedToEntity?.jobBoard?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.jobBoardSharedTo.home.createOrEditLabel" data-cy="JobBoardSharedToCreateUpdateHeading">
            <Translate contentKey="rproductApp.jobBoardSharedTo.home.createOrEditLabel">Create or edit a JobBoardSharedTo</Translate>
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
                  id="job-board-shared-to-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.jobBoardSharedTo.jobBoardId')}
                id="job-board-shared-to-jobBoardId"
                name="jobBoardId"
                data-cy="jobBoardId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoardSharedTo.sharedToEmails')}
                id="job-board-shared-to-sharedToEmails"
                name="sharedToEmails"
                data-cy="sharedToEmails"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoardSharedTo.sharedToUserIds')}
                id="job-board-shared-to-sharedToUserIds"
                name="sharedToUserIds"
                data-cy="sharedToUserIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoardSharedTo.sharedToTeamIds')}
                id="job-board-shared-to-sharedToTeamIds"
                name="sharedToTeamIds"
                data-cy="sharedToTeamIds"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoardSharedTo.maxExamilsMonthly')}
                id="job-board-shared-to-maxExamilsMonthly"
                name="maxExamilsMonthly"
                data-cy="maxExamilsMonthly"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoardSharedTo.expiresOn')}
                id="job-board-shared-to-expiresOn"
                name="expiresOn"
                data-cy="expiresOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="job-board-shared-to-jobBoard"
                name="jobBoard"
                data-cy="jobBoard"
                label={translate('rproductApp.jobBoardSharedTo.jobBoard')}
                type="select"
              >
                <option value="" key="0" />
                {jobBoards
                  ? jobBoards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-board-shared-to" replace color="info">
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

export default JobBoardSharedToUpdate;
