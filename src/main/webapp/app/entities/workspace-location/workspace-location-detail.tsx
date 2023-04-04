import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './workspace-location.reducer';

export const WorkspaceLocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workspaceLocationEntity = useAppSelector(state => state.workspaceLocation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workspaceLocationDetailsHeading">
          <Translate contentKey="rproductApp.workspaceLocation.detail.title">WorkspaceLocation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.id}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="rproductApp.workspaceLocation.area">Area</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.area}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="rproductApp.workspaceLocation.city">City</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="rproductApp.workspaceLocation.state">State</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.state}</dd>
          <dt>
            <span id="stateCode">
              <Translate contentKey="rproductApp.workspaceLocation.stateCode">State Code</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.stateCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="rproductApp.workspaceLocation.country">Country</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.country}</dd>
          <dt>
            <span id="countryCode">
              <Translate contentKey="rproductApp.workspaceLocation.countryCode">Country Code</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.countryCode}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="rproductApp.workspaceLocation.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.zipCode}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="rproductApp.workspaceLocation.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="rproductApp.workspaceLocation.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.lon}</dd>
          <dt>
            <span id="continent">
              <Translate contentKey="rproductApp.workspaceLocation.continent">Continent</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.continent}</dd>
          <dt>
            <span id="continentCode">
              <Translate contentKey="rproductApp.workspaceLocation.continentCode">Continent Code</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.continentCode}</dd>
          <dt>
            <span id="point">
              <Translate contentKey="rproductApp.workspaceLocation.point">Point</Translate>
            </span>
          </dt>
          <dd>{workspaceLocationEntity.point}</dd>
          <dt>
            <Translate contentKey="rproductApp.workspaceLocation.workspace">Workspace</Translate>
          </dt>
          <dd>{workspaceLocationEntity.workspace ? workspaceLocationEntity.workspace.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/workspace-location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/workspace-location/${workspaceLocationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkspaceLocationDetail;
