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
import { IJobDocument } from 'app/shared/model/job-document.model';
import { getEntity, updateEntity, createEntity, reset } from './job-document.reducer';

export const JobDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobs = useAppSelector(state => state.job.entities);
  const jobDocumentEntity = useAppSelector(state => state.jobDocument.entity);
  const loading = useAppSelector(state => state.jobDocument.loading);
  const updating = useAppSelector(state => state.jobDocument.updating);
  const updateSuccess = useAppSelector(state => state.jobDocument.updateSuccess);

  const handleClose = () => {
    navigate('/job-document' + location.search);
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
      ...jobDocumentEntity,
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
          ...jobDocumentEntity,
          job: jobDocumentEntity?.job?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.jobDocument.home.createOrEditLabel" data-cy="JobDocumentCreateUpdateHeading">
            <Translate contentKey="rproductApp.jobDocument.home.createOrEditLabel">Create or edit a JobDocument</Translate>
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
                  id="job-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.jobDocument.jobId')}
                id="job-document-jobId"
                name="jobId"
                data-cy="jobId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobDocument.documentTitle')}
                id="job-document-documentTitle"
                name="documentTitle"
                data-cy="documentTitle"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobDocument.documentLocation')}
                id="job-document-documentLocation"
                name="documentLocation"
                data-cy="documentLocation"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.jobDocument.documentPassword')}
                id="job-document-documentPassword"
                name="documentPassword"
                data-cy="documentPassword"
                type="text"
              />
              <ValidatedField id="job-document-job" name="job" data-cy="job" label={translate('rproductApp.jobDocument.job')} type="select">
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-document" replace color="info">
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

export default JobDocumentUpdate;
