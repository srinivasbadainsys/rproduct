import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-work.reducer';

export const JobWorkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobWorkEntity = useAppSelector(state => state.jobWork.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobWorkDetailsHeading">
          <Translate contentKey="rproductApp.jobWork.detail.title">JobWork</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobWorkEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.jobWork.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{jobWorkEntity.jobId}</dd>
          <dt>
            <span id="assignedToTeams">
              <Translate contentKey="rproductApp.jobWork.assignedToTeams">Assigned To Teams</Translate>
            </span>
          </dt>
          <dd>{jobWorkEntity.assignedToTeams}</dd>
          <dt>
            <span id="assignedToUsers">
              <Translate contentKey="rproductApp.jobWork.assignedToUsers">Assigned To Users</Translate>
            </span>
          </dt>
          <dd>{jobWorkEntity.assignedToUsers}</dd>
        </dl>
        <Button tag={Link} to="/job-work" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-work/${jobWorkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobWorkDetail;
