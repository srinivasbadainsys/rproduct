import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-board.reducer';

export const JobBoardDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobBoardEntity = useAppSelector(state => state.jobBoard.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobBoardDetailsHeading">
          <Translate contentKey="rproductApp.jobBoard.detail.title">JobBoard</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.id}</dd>
          <dt>
            <span id="jobBoardName">
              <Translate contentKey="rproductApp.jobBoard.jobBoardName">Job Board Name</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.jobBoardName}</dd>
          <dt>
            <span id="jobBoardType">
              <Translate contentKey="rproductApp.jobBoard.jobBoardType">Job Board Type</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.jobBoardType}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="rproductApp.jobBoard.username">Username</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.username}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="rproductApp.jobBoard.password">Password</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.password}</dd>
          <dt>
            <span id="settings">
              <Translate contentKey="rproductApp.jobBoard.settings">Settings</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.settings}</dd>
          <dt>
            <span id="autoRefresh">
              <Translate contentKey="rproductApp.jobBoard.autoRefresh">Auto Refresh</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.autoRefresh}</dd>
          <dt>
            <span id="jobDuration">
              <Translate contentKey="rproductApp.jobBoard.jobDuration">Job Duration</Translate>
            </span>
          </dt>
          <dd>{jobBoardEntity.jobDuration}</dd>
        </dl>
        <Button tag={Link} to="/job-board" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-board/${jobBoardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobBoardDetail;
