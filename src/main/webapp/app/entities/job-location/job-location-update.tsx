import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJob } from 'app/shared/model/job.model';
import { getEntities as getJobs } from 'app/entities/job/job.reducer';
import { IJobLocation } from 'app/shared/model/job-location.model';
import { getEntity, updateEntity, createEntity, reset } from './job-location.reducer';

export const JobLocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobs = useAppSelector(state => state.job.entities);
  const jobLocationEntity = useAppSelector(state => state.jobLocation.entity);
  const loading = useAppSelector(state => state.jobLocation.loading);
  const updating = useAppSelector(state => state.jobLocation.updating);
  const updateSuccess = useAppSelector(state => state.jobLocation.updateSuccess);

  const handleClose = () => {
    navigate('/job-location' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJobs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...jobLocationEntity,
      ...values,
      job: jobs.find(it => it.id.toString() === values.job.toString()),
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
          ...jobLocationEntity,
          job: jobLocationEntity?.job?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.jobLocation.home.createOrEditLabel" data-cy="JobLocationCreateUpdateHeading">
            <Translate contentKey="rproductApp.jobLocation.home.createOrEditLabel">Create or edit a JobLocation</Translate>
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
                  id="job-location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.jobLocation.jobId')}
                id="job-location-jobId"
                name="jobId"
                data-cy="jobId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.area')}
                id="job-location-area"
                name="area"
                data-cy="area"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.city')}
                id="job-location-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.state')}
                id="job-location-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.stateCode')}
                id="job-location-stateCode"
                name="stateCode"
                data-cy="stateCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.country')}
                id="job-location-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.countryCode')}
                id="job-location-countryCode"
                name="countryCode"
                data-cy="countryCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.zipCode')}
                id="job-location-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.jobLocation.lat')} id="job-location-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField label={translate('rproductApp.jobLocation.lon')} id="job-location-lon" name="lon" data-cy="lon" type="text" />
              <ValidatedField
                label={translate('rproductApp.jobLocation.continent')}
                id="job-location-continent"
                name="continent"
                data-cy="continent"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.continentCode')}
                id="job-location-continentCode"
                name="continentCode"
                data-cy="continentCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobLocation.point')}
                id="job-location-point"
                name="point"
                data-cy="point"
                type="text"
              />
              <ValidatedField id="job-location-job" name="job" data-cy="job" label={translate('rproductApp.jobLocation.job')} type="select">
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-location" replace color="info">
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

export default JobLocationUpdate;
