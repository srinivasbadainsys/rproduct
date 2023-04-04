import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './team.reducer';

export const TeamDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const teamEntity = useAppSelector(state => state.team.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="teamDetailsHeading">
          <Translate contentKey="rproductApp.team.detail.title">Team</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{teamEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="rproductApp.team.name">Name</Translate>
            </span>
          </dt>
          <dd>{teamEntity.name}</dd>
          <dt>
            <span id="teamGroupEmail">
              <Translate contentKey="rproductApp.team.teamGroupEmail">Team Group Email</Translate>
            </span>
          </dt>
          <dd>{teamEntity.teamGroupEmail}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="rproductApp.team.type">Type</Translate>
            </span>
          </dt>
          <dd>{teamEntity.type}</dd>
          <dt>
            <span id="notifyOnJobPostingToUsers">
              <Translate contentKey="rproductApp.team.notifyOnJobPostingToUsers">Notify On Job Posting To Users</Translate>
            </span>
          </dt>
          <dd>{teamEntity.notifyOnJobPostingToUsers}</dd>
          <dt>
            <span id="notifyOnJobSharingToUsers">
              <Translate contentKey="rproductApp.team.notifyOnJobSharingToUsers">Notify On Job Sharing To Users</Translate>
            </span>
          </dt>
          <dd>{teamEntity.notifyOnJobSharingToUsers}</dd>
          <dt>
            <span id="notifyOnJobClosingToUsers">
              <Translate contentKey="rproductApp.team.notifyOnJobClosingToUsers">Notify On Job Closing To Users</Translate>
            </span>
          </dt>
          <dd>{teamEntity.notifyOnJobClosingToUsers}</dd>
          <dt>
            <span id="notifyOnCandSubmissionToUsers">
              <Translate contentKey="rproductApp.team.notifyOnCandSubmissionToUsers">Notify On Cand Submission To Users</Translate>
            </span>
          </dt>
          <dd>{teamEntity.notifyOnCandSubmissionToUsers}</dd>
          <dt>
            <span id="notifyOnCandStatusChangeToUsers">
              <Translate contentKey="rproductApp.team.notifyOnCandStatusChangeToUsers">Notify On Cand Status Change To Users</Translate>
            </span>
          </dt>
          <dd>{teamEntity.notifyOnCandStatusChangeToUsers}</dd>
          <dt>
            <Translate contentKey="rproductApp.team.workspace">Workspace</Translate>
          </dt>
          <dd>{teamEntity.workspace ? teamEntity.workspace.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/team" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/team/${teamEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TeamDetail;
