import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJob } from 'app/shared/model/job.model';
import { getEntities as getJobs } from 'app/entities/job/job.reducer';
import { IJobBoard } from 'app/shared/model/job-board.model';
import { getEntities as getJobBoards } from 'app/entities/job-board/job-board.reducer';
import { IJobBoardPost } from 'app/shared/model/job-board-post.model';
import { JobBoardPostStatus } from 'app/shared/model/enumerations/job-board-post-status.model';
import { getEntity, updateEntity, createEntity, reset } from './job-board-post.reducer';

export const JobBoardPostUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobs = useAppSelector(state => state.job.entities);
  const jobBoards = useAppSelector(state => state.jobBoard.entities);
  const jobBoardPostEntity = useAppSelector(state => state.jobBoardPost.entity);
  const loading = useAppSelector(state => state.jobBoardPost.loading);
  const updating = useAppSelector(state => state.jobBoardPost.updating);
  const updateSuccess = useAppSelector(state => state.jobBoardPost.updateSuccess);
  const jobBoardPostStatusValues = Object.keys(JobBoardPostStatus);

  const handleClose = () => {
    navigate('/job-board-post' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJobs({}));
    dispatch(getJobBoards({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...jobBoardPostEntity,
      ...values,
      job: jobs.find(it => it.id.toString() === values.job.toString()),
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
      ? {}
      : {
          status: 'Not_Posted',
          ...jobBoardPostEntity,
          job: jobBoardPostEntity?.job?.id,
          jobBoard: jobBoardPostEntity?.jobBoard?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.jobBoardPost.home.createOrEditLabel" data-cy="JobBoardPostCreateUpdateHeading">
            <Translate contentKey="rproductApp.jobBoardPost.home.createOrEditLabel">Create or edit a JobBoardPost</Translate>
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
                  id="job-board-post-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.jobBoardPost.jobId')}
                id="job-board-post-jobId"
                name="jobId"
                data-cy="jobId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobBoardPost.status')}
                id="job-board-post-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {jobBoardPostStatusValues.map(jobBoardPostStatus => (
                  <option value={jobBoardPostStatus} key={jobBoardPostStatus}>
                    {translate('rproductApp.JobBoardPostStatus.' + jobBoardPostStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="job-board-post-job"
                name="job"
                data-cy="job"
                label={translate('rproductApp.jobBoardPost.job')}
                type="select"
              >
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="job-board-post-jobBoard"
                name="jobBoard"
                data-cy="jobBoard"
                label={translate('rproductApp.jobBoardPost.jobBoard')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-board-post" replace color="info">
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

export default JobBoardPostUpdate;
