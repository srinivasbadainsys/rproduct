import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { getEntity, updateEntity, createEntity, reset } from './business-unit.reducer';

export const BusinessUnitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const businessUnitEntity = useAppSelector(state => state.businessUnit.entity);
  const loading = useAppSelector(state => state.businessUnit.loading);
  const updating = useAppSelector(state => state.businessUnit.updating);
  const updateSuccess = useAppSelector(state => state.businessUnit.updateSuccess);

  const handleClose = () => {
    navigate('/business-unit' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...businessUnitEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...businessUnitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.businessUnit.home.createOrEditLabel" data-cy="BusinessUnitCreateUpdateHeading">
            <Translate contentKey="rproductApp.businessUnit.home.createOrEditLabel">Create or edit a BusinessUnit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="business-unit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.businessUnit.unitName')}
                id="business-unit-unitName"
                name="unitName"
                data-cy="unitName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.address')}
                id="business-unit-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.mobileContact')}
                id="business-unit-mobileContact"
                name="mobileContact"
                data-cy="mobileContact"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.officeContact')}
                id="business-unit-officeContact"
                name="officeContact"
                data-cy="officeContact"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.officeContactExtn')}
                id="business-unit-officeContactExtn"
                name="officeContactExtn"
                data-cy="officeContactExtn"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.area')}
                id="business-unit-area"
                name="area"
                data-cy="area"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.city')}
                id="business-unit-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.state')}
                id="business-unit-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.stateCode')}
                id="business-unit-stateCode"
                name="stateCode"
                data-cy="stateCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.country')}
                id="business-unit-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.countryCode')}
                id="business-unit-countryCode"
                name="countryCode"
                data-cy="countryCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.zipCode')}
                id="business-unit-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.lat')}
                id="business-unit-lat"
                name="lat"
                data-cy="lat"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.lon')}
                id="business-unit-lon"
                name="lon"
                data-cy="lon"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.continent')}
                id="business-unit-continent"
                name="continent"
                data-cy="continent"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.continentCode')}
                id="business-unit-continentCode"
                name="continentCode"
                data-cy="continentCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.businessUnit.point')}
                id="business-unit-point"
                name="point"
                data-cy="point"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/business-unit" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BusinessUnitUpdate;
