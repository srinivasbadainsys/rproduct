import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './team-member.reducer';

export const TeamMemberDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const teamMemberEntity = useAppSelector(state => state.teamMember.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="teamMemberDetailsHeading">
          <Translate contentKey="rproductApp.teamMember.detail.title">TeamMember</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{teamMemberEntity.id}</dd>
          <dt>
            <span id="teamId">
              <Translate contentKey="rproductApp.teamMember.teamId">Team Id</Translate>
            </span>
          </dt>
          <dd>{teamMemberEntity.teamId}</dd>
          <dt>
            <Translate contentKey="rproductApp.teamMember.memberUser">Member User</Translate>
          </dt>
          <dd>{teamMemberEntity.memberUser ? teamMemberEntity.memberUser.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.teamMember.team">Team</Translate>
          </dt>
          <dd>{teamMemberEntity.team ? teamMemberEntity.team.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/team-member" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/team-member/${teamMemberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TeamMemberDetail;
