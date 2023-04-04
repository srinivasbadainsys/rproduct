import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './workspace-user.reducer';

export const WorkspaceUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workspaceUserEntity = useAppSelector(state => state.workspaceUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workspaceUserDetailsHeading">
          <Translate contentKey="rproductApp.workspaceUser.detail.title">WorkspaceUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="rproductApp.workspaceUser.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.userId}</dd>
          <dt>
            <span id="invitationKey">
              <Translate contentKey="rproductApp.workspaceUser.invitationKey">Invitation Key</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.invitationKey}</dd>
          <dt>
            <span id="owns">
              <Translate contentKey="rproductApp.workspaceUser.owns">Owns</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.owns ? 'true' : 'false'}</dd>
          <dt>
            <span id="accepted">
              <Translate contentKey="rproductApp.workspaceUser.accepted">Accepted</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.accepted ? 'true' : 'false'}</dd>
          <dt>
            <span id="invited">
              <Translate contentKey="rproductApp.workspaceUser.invited">Invited</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.invited ? 'true' : 'false'}</dd>
          <dt>
            <span id="requested">
              <Translate contentKey="rproductApp.workspaceUser.requested">Requested</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.requested ? 'true' : 'false'}</dd>
          <dt>
            <span id="barred">
              <Translate contentKey="rproductApp.workspaceUser.barred">Barred</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.barred ? 'true' : 'false'}</dd>
          <dt>
            <span id="roles">
              <Translate contentKey="rproductApp.workspaceUser.roles">Roles</Translate>
            </span>
          </dt>
          <dd>{workspaceUserEntity.roles}</dd>
          <dt>
            <span id="requestedOn">
              <Translate contentKey="rproductApp.workspaceUser.requestedOn">Requested On</Translate>
            </span>
          </dt>
          <dd>
            {workspaceUserEntity.requestedOn ? (
              <TextFormat value={workspaceUserEntity.requestedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="rproductApp.workspaceUser.workspace">Workspace</Translate>
          </dt>
          <dd>{workspaceUserEntity.workspace ? workspaceUserEntity.workspace.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/workspace-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/workspace-user/${workspaceUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkspaceUserDetail;
