import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './workspace.reducer';

export const WorkspaceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workspaceEntity = useAppSelector(state => state.workspace.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workspaceDetailsHeading">
          <Translate contentKey="rproductApp.workspace.detail.title">Workspace</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="rproductApp.workspace.name">Name</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.name}</dd>
          <dt>
            <span id="orgName">
              <Translate contentKey="rproductApp.workspace.orgName">Org Name</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.orgName}</dd>
          <dt>
            <span id="about">
              <Translate contentKey="rproductApp.workspace.about">About</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.about}</dd>
          <dt>
            <span id="link">
              <Translate contentKey="rproductApp.workspace.link">Link</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.link}</dd>
          <dt>
            <span id="orgURLs">
              <Translate contentKey="rproductApp.workspace.orgURLs">Org UR Ls</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.orgURLs}</dd>
          <dt>
            <span id="ownerUserId">
              <Translate contentKey="rproductApp.workspace.ownerUserId">Owner User Id</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.ownerUserId}</dd>
          <dt>
            <span id="mainPhoneNumber">
              <Translate contentKey="rproductApp.workspace.mainPhoneNumber">Main Phone Number</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.mainPhoneNumber}</dd>
          <dt>
            <span id="altPhoneNumbers">
              <Translate contentKey="rproductApp.workspace.altPhoneNumbers">Alt Phone Numbers</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.altPhoneNumbers}</dd>
          <dt>
            <span id="mainContactEmail">
              <Translate contentKey="rproductApp.workspace.mainContactEmail">Main Contact Email</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.mainContactEmail}</dd>
          <dt>
            <span id="altContactEmails">
              <Translate contentKey="rproductApp.workspace.altContactEmails">Alt Contact Emails</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.altContactEmails}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="rproductApp.workspace.status">Status</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.status}</dd>
          <dt>
            <span id="enableAutoJoin">
              <Translate contentKey="rproductApp.workspace.enableAutoJoin">Enable Auto Join</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.enableAutoJoin ? 'true' : 'false'}</dd>
          <dt>
            <span id="maxUsers">
              <Translate contentKey="rproductApp.workspace.maxUsers">Max Users</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.maxUsers}</dd>
          <dt>
            <span id="tags">
              <Translate contentKey="rproductApp.workspace.tags">Tags</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.tags}</dd>
          <dt>
            <span id="domains">
              <Translate contentKey="rproductApp.workspace.domains">Domains</Translate>
            </span>
          </dt>
          <dd>{workspaceEntity.domains}</dd>
        </dl>
        <Button tag={Link} to="/workspace" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/workspace/${workspaceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkspaceDetail;
