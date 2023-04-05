import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICatalogueValue } from 'app/shared/model/catalogue-value.model';
import { getEntities as getCatalogueValues } from 'app/entities/catalogue-value/catalogue-value.reducer';
import { IJob } from 'app/shared/model/job.model';
import { getEntities as getJobs } from 'app/entities/job/job.reducer';
import { ICandidatePipeline } from 'app/shared/model/candidate-pipeline.model';
import { CandidatePipelineType } from 'app/shared/model/enumerations/candidate-pipeline-type.model';
import { getEntity, updateEntity, createEntity, reset } from './candidate-pipeline.reducer';

export const CandidatePipelineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const catalogueValues = useAppSelector(state => state.catalogueValue.entities);
  const jobs = useAppSelector(state => state.job.entities);
  const candidatePipelineEntity = useAppSelector(state => state.candidatePipeline.entity);
  const loading = useAppSelector(state => state.candidatePipeline.loading);
  const updating = useAppSelector(state => state.candidatePipeline.updating);
  const updateSuccess = useAppSelector(state => state.candidatePipeline.updateSuccess);
  const candidatePipelineTypeValues = Object.keys(CandidatePipelineType);

  const handleClose = () => {
    navigate('/candidate-pipeline' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCatalogueValues({}));
    dispatch(getJobs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...candidatePipelineEntity,
      ...values,
      status: catalogueValues.find(it => it.id.toString() === values.status.toString()),
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
          pipelineType: 'TAGGED_TO_JOB',
          ...candidatePipelineEntity,
          status: candidatePipelineEntity?.status?.id,
          job: candidatePipelineEntity?.job?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.candidatePipeline.home.createOrEditLabel" data-cy="CandidatePipelineCreateUpdateHeading">
            <Translate contentKey="rproductApp.candidatePipeline.home.createOrEditLabel">Create or edit a CandidatePipeline</Translate>
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
                  id="candidate-pipeline-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.jobId')}
                id="candidate-pipeline-jobId"
                name="jobId"
                data-cy="jobId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.statusId')}
                id="candidate-pipeline-statusId"
                name="statusId"
                data-cy="statusId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.submissionStatus')}
                id="candidate-pipeline-submissionStatus"
                name="submissionStatus"
                data-cy="submissionStatus"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.submissionStage')}
                id="candidate-pipeline-submissionStage"
                name="submissionStage"
                data-cy="submissionStage"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.recruiterActions')}
                id="candidate-pipeline-recruiterActions"
                name="recruiterActions"
                data-cy="recruiterActions"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.candidateResponses')}
                id="candidate-pipeline-candidateResponses"
                name="candidateResponses"
                data-cy="candidateResponses"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.pipelineType')}
                id="candidate-pipeline-pipelineType"
                name="pipelineType"
                data-cy="pipelineType"
                type="select"
              >
                {candidatePipelineTypeValues.map(candidatePipelineType => (
                  <option value={candidatePipelineType} key={candidatePipelineType}>
                    {translate('rproductApp.CandidatePipelineType.' + candidatePipelineType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.candidatePipeline.reasonForNewJob')}
                id="candidate-pipeline-reasonForNewJob"
                name="reasonForNewJob"
                data-cy="reasonForNewJob"
                type="text"
              />
              <ValidatedField
                id="candidate-pipeline-status"
                name="status"
                data-cy="status"
                label={translate('rproductApp.candidatePipeline.status')}
                type="select"
              >
                <option value="" key="0" />
                {catalogueValues
                  ? catalogueValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="candidate-pipeline-job"
                name="job"
                data-cy="job"
                label={translate('rproductApp.candidatePipeline.job')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/candidate-pipeline" replace color="info">
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

export default CandidatePipelineUpdate;
