import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './candidate-pipeline.reducer';

export const CandidatePipelineDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const candidatePipelineEntity = useAppSelector(state => state.candidatePipeline.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="candidatePipelineDetailsHeading">
          <Translate contentKey="rproductApp.candidatePipeline.detail.title">CandidatePipeline</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.candidatePipeline.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.jobId}</dd>
          <dt>
            <span id="statusId">
              <Translate contentKey="rproductApp.candidatePipeline.statusId">Status Id</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.statusId}</dd>
          <dt>
            <span id="submissionStatus">
              <Translate contentKey="rproductApp.candidatePipeline.submissionStatus">Submission Status</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.submissionStatus}</dd>
          <dt>
            <span id="submissionStage">
              <Translate contentKey="rproductApp.candidatePipeline.submissionStage">Submission Stage</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.submissionStage}</dd>
          <dt>
            <span id="recruiterActions">
              <Translate contentKey="rproductApp.candidatePipeline.recruiterActions">Recruiter Actions</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.recruiterActions}</dd>
          <dt>
            <span id="candidateResponses">
              <Translate contentKey="rproductApp.candidatePipeline.candidateResponses">Candidate Responses</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.candidateResponses}</dd>
          <dt>
            <span id="pipelineType">
              <Translate contentKey="rproductApp.candidatePipeline.pipelineType">Pipeline Type</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.pipelineType}</dd>
          <dt>
            <span id="reasonForNewJob">
              <Translate contentKey="rproductApp.candidatePipeline.reasonForNewJob">Reason For New Job</Translate>
            </span>
          </dt>
          <dd>{candidatePipelineEntity.reasonForNewJob}</dd>
          <dt>
            <Translate contentKey="rproductApp.candidatePipeline.status">Status</Translate>
          </dt>
          <dd>{candidatePipelineEntity.status ? candidatePipelineEntity.status.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.candidatePipeline.job">Job</Translate>
          </dt>
          <dd>{candidatePipelineEntity.job ? candidatePipelineEntity.job.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/candidate-pipeline" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/candidate-pipeline/${candidatePipelineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CandidatePipelineDetail;
