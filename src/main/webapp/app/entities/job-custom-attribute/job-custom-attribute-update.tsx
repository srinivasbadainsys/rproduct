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
import { IJobCustomAttribute } from 'app/shared/model/job-custom-attribute.model';
import { JobAttributeType } from 'app/shared/model/enumerations/job-attribute-type.model';
import { getEntity, updateEntity, createEntity, reset } from './job-custom-attribute.reducer';

export const JobCustomAttributeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobs = useAppSelector(state => state.job.entities);
  const jobCustomAttributeEntity = useAppSelector(state => state.jobCustomAttribute.entity);
  const loading = useAppSelector(state => state.jobCustomAttribute.loading);
  const updating = useAppSelector(state => state.jobCustomAttribute.updating);
  const updateSuccess = useAppSelector(state => state.jobCustomAttribute.updateSuccess);
  const jobAttributeTypeValues = Object.keys(JobAttributeType);

  const handleClose = () => {
    navigate('/job-custom-attribute' + location.search);
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
      ...jobCustomAttributeEntity,
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
          attributeType: 'Single_Value',
          ...jobCustomAttributeEntity,
          job: jobCustomAttributeEntity?.job?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.jobCustomAttribute.home.createOrEditLabel" data-cy="JobCustomAttributeCreateUpdateHeading">
            <Translate contentKey="rproductApp.jobCustomAttribute.home.createOrEditLabel">Create or edit a JobCustomAttribute</Translate>
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
                  id="job-custom-attribute-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.jobCustomAttribute.jobId')}
                id="job-custom-attribute-jobId"
                name="jobId"
                data-cy="jobId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobCustomAttribute.attributeName')}
                id="job-custom-attribute-attributeName"
                name="attributeName"
                data-cy="attributeName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobCustomAttribute.attributeType')}
                id="job-custom-attribute-attributeType"
                name="attributeType"
                data-cy="attributeType"
                type="select"
              >
                {jobAttributeTypeValues.map(jobAttributeType => (
                  <option value={jobAttributeType} key={jobAttributeType}>
                    {translate('rproductApp.JobAttributeType.' + jobAttributeType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('rproductApp.jobCustomAttribute.attributeValue')}
                id="job-custom-attribute-attributeValue"
                name="attributeValue"
                data-cy="attributeValue"
                type="text"
              />
              <ValidatedField
                id="job-custom-attribute-job"
                name="job"
                data-cy="job"
                label={translate('rproductApp.jobCustomAttribute.job')}
                type="select"
              >
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-custom-attribute" replace color="info">
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

export default JobCustomAttributeUpdate;
