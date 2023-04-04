import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './team-work.reducer';

export const TeamWorkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const teamWorkEntity = useAppSelector(state => state.teamWork.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="teamWorkDetailsHeading">
          <Translate contentKey="rproductApp.teamWork.detail.title">TeamWork</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{teamWorkEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.teamWork.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{teamWorkEntity.jobId}</dd>
          <dt>
            <span id="teamId">
              <Translate contentKey="rproductApp.teamWork.teamId">Team Id</Translate>
            </span>
          </dt>
          <dd>{teamWorkEntity.teamId}</dd>
        </dl>
        <Button tag={Link} to="/team-work" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/team-work/${teamWorkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TeamWorkDetail;
