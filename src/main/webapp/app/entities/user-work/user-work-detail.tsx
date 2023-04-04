import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-work.reducer';

export const UserWorkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userWorkEntity = useAppSelector(state => state.userWork.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userWorkDetailsHeading">
          <Translate contentKey="rproductApp.userWork.detail.title">UserWork</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userWorkEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.userWork.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{userWorkEntity.jobId}</dd>
          <dt>
            <Translate contentKey="rproductApp.userWork.user">User</Translate>
          </dt>
          <dd>{userWorkEntity.user ? userWorkEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.userWork.team">Team</Translate>
          </dt>
          <dd>{userWorkEntity.team ? userWorkEntity.team.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.userWork.job">Job</Translate>
          </dt>
          <dd>{userWorkEntity.job ? userWorkEntity.job.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-work" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-work/${userWorkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserWorkDetail;
