import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-location.reducer';

export const JobLocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobLocationEntity = useAppSelector(state => state.jobLocation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobLocationDetailsHeading">
          <Translate contentKey="rproductApp.jobLocation.detail.title">JobLocation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.jobLocation.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.jobId}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="rproductApp.jobLocation.area">Area</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.area}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="rproductApp.jobLocation.city">City</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="rproductApp.jobLocation.state">State</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.state}</dd>
          <dt>
            <span id="stateCode">
              <Translate contentKey="rproductApp.jobLocation.stateCode">State Code</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.stateCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="rproductApp.jobLocation.country">Country</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.country}</dd>
          <dt>
            <span id="countryCode">
              <Translate contentKey="rproductApp.jobLocation.countryCode">Country Code</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.countryCode}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="rproductApp.jobLocation.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.zipCode}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="rproductApp.jobLocation.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="rproductApp.jobLocation.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.lon}</dd>
          <dt>
            <span id="continent">
              <Translate contentKey="rproductApp.jobLocation.continent">Continent</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.continent}</dd>
          <dt>
            <span id="continentCode">
              <Translate contentKey="rproductApp.jobLocation.continentCode">Continent Code</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.continentCode}</dd>
          <dt>
            <span id="point">
              <Translate contentKey="rproductApp.jobLocation.point">Point</Translate>
            </span>
          </dt>
          <dd>{jobLocationEntity.point}</dd>
          <dt>
            <Translate contentKey="rproductApp.jobLocation.job">Job</Translate>
          </dt>
          <dd>{jobLocationEntity.job ? jobLocationEntity.job.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-location/${jobLocationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobLocationDetail;
