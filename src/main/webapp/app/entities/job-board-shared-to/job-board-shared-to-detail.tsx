import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-board-shared-to.reducer';

export const JobBoardSharedToDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobBoardSharedToEntity = useAppSelector(state => state.jobBoardSharedTo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobBoardSharedToDetailsHeading">
          <Translate contentKey="rproductApp.jobBoardSharedTo.detail.title">JobBoardSharedTo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobBoardSharedToEntity.id}</dd>
          <dt>
            <span id="jobBoardId">
              <Translate contentKey="rproductApp.jobBoardSharedTo.jobBoardId">Job Board Id</Translate>
            </span>
          </dt>
          <dd>{jobBoardSharedToEntity.jobBoardId}</dd>
          <dt>
            <span id="sharedToEmails">
              <Translate contentKey="rproductApp.jobBoardSharedTo.sharedToEmails">Shared To Emails</Translate>
            </span>
          </dt>
          <dd>{jobBoardSharedToEntity.sharedToEmails}</dd>
          <dt>
            <span id="sharedToUserIds">
              <Translate contentKey="rproductApp.jobBoardSharedTo.sharedToUserIds">Shared To User Ids</Translate>
            </span>
          </dt>
          <dd>{jobBoardSharedToEntity.sharedToUserIds}</dd>
          <dt>
            <span id="sharedToTeamIds">
              <Translate contentKey="rproductApp.jobBoardSharedTo.sharedToTeamIds">Shared To Team Ids</Translate>
            </span>
          </dt>
          <dd>{jobBoardSharedToEntity.sharedToTeamIds}</dd>
          <dt>
            <span id="maxExamilsMonthly">
              <Translate contentKey="rproductApp.jobBoardSharedTo.maxExamilsMonthly">Max Examils Monthly</Translate>
            </span>
          </dt>
          <dd>{jobBoardSharedToEntity.maxExamilsMonthly}</dd>
          <dt>
            <span id="expiresOn">
              <Translate contentKey="rproductApp.jobBoardSharedTo.expiresOn">Expires On</Translate>
            </span>
          </dt>
          <dd>
            {jobBoardSharedToEntity.expiresOn ? (
              <TextFormat value={jobBoardSharedToEntity.expiresOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="rproductApp.jobBoardSharedTo.jobBoard">Job Board</Translate>
          </dt>
          <dd>{jobBoardSharedToEntity.jobBoard ? jobBoardSharedToEntity.jobBoard.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-board-shared-to" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-board-shared-to/${jobBoardSharedToEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobBoardSharedToDetail;
