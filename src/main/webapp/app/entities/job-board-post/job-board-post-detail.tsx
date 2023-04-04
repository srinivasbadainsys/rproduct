import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-board-post.reducer';

export const JobBoardPostDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobBoardPostEntity = useAppSelector(state => state.jobBoardPost.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobBoardPostDetailsHeading">
          <Translate contentKey="rproductApp.jobBoardPost.detail.title">JobBoardPost</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobBoardPostEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.jobBoardPost.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{jobBoardPostEntity.jobId}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="rproductApp.jobBoardPost.status">Status</Translate>
            </span>
          </dt>
          <dd>{jobBoardPostEntity.status}</dd>
          <dt>
            <Translate contentKey="rproductApp.jobBoardPost.job">Job</Translate>
          </dt>
          <dd>{jobBoardPostEntity.job ? jobBoardPostEntity.job.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.jobBoardPost.jobBoard">Job Board</Translate>
          </dt>
          <dd>{jobBoardPostEntity.jobBoard ? jobBoardPostEntity.jobBoard.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-board-post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-board-post/${jobBoardPostEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobBoardPostDetail;
