import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './business-unit.reducer';

export const BusinessUnitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const businessUnitEntity = useAppSelector(state => state.businessUnit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="businessUnitDetailsHeading">
          <Translate contentKey="rproductApp.businessUnit.detail.title">BusinessUnit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.id}</dd>
          <dt>
            <span id="unitName">
              <Translate contentKey="rproductApp.businessUnit.unitName">Unit Name</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.unitName}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="rproductApp.businessUnit.address">Address</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.address}</dd>
          <dt>
            <span id="mobileContact">
              <Translate contentKey="rproductApp.businessUnit.mobileContact">Mobile Contact</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.mobileContact}</dd>
          <dt>
            <span id="officeContact">
              <Translate contentKey="rproductApp.businessUnit.officeContact">Office Contact</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.officeContact}</dd>
          <dt>
            <span id="officeContactExtn">
              <Translate contentKey="rproductApp.businessUnit.officeContactExtn">Office Contact Extn</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.officeContactExtn}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="rproductApp.businessUnit.area">Area</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.area}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="rproductApp.businessUnit.city">City</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="rproductApp.businessUnit.state">State</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.state}</dd>
          <dt>
            <span id="stateCode">
              <Translate contentKey="rproductApp.businessUnit.stateCode">State Code</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.stateCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="rproductApp.businessUnit.country">Country</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.country}</dd>
          <dt>
            <span id="countryCode">
              <Translate contentKey="rproductApp.businessUnit.countryCode">Country Code</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.countryCode}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="rproductApp.businessUnit.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.zipCode}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="rproductApp.businessUnit.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="rproductApp.businessUnit.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.lon}</dd>
          <dt>
            <span id="continent">
              <Translate contentKey="rproductApp.businessUnit.continent">Continent</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.continent}</dd>
          <dt>
            <span id="continentCode">
              <Translate contentKey="rproductApp.businessUnit.continentCode">Continent Code</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.continentCode}</dd>
          <dt>
            <span id="point">
              <Translate contentKey="rproductApp.businessUnit.point">Point</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.point}</dd>
        </dl>
        <Button tag={Link} to="/business-unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/business-unit/${businessUnitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BusinessUnitDetail;
