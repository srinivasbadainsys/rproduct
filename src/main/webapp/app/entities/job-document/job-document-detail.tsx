import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-document.reducer';

export const JobDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobDocumentEntity = useAppSelector(state => state.jobDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobDocumentDetailsHeading">
          <Translate contentKey="rproductApp.jobDocument.detail.title">JobDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobDocumentEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.jobDocument.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{jobDocumentEntity.jobId}</dd>
          <dt>
            <span id="documentTitle">
              <Translate contentKey="rproductApp.jobDocument.documentTitle">Document Title</Translate>
            </span>
          </dt>
          <dd>{jobDocumentEntity.documentTitle}</dd>
          <dt>
            <span id="documentLocation">
              <Translate contentKey="rproductApp.jobDocument.documentLocation">Document Location</Translate>
            </span>
          </dt>
          <dd>{jobDocumentEntity.documentLocation}</dd>
          <dt>
            <span id="documentPassword">
              <Translate contentKey="rproductApp.jobDocument.documentPassword">Document Password</Translate>
            </span>
          </dt>
          <dd>{jobDocumentEntity.documentPassword}</dd>
          <dt>
            <Translate contentKey="rproductApp.jobDocument.job">Job</Translate>
          </dt>
          <dd>{jobDocumentEntity.job ? jobDocumentEntity.job.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-document/${jobDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobDocumentDetail;
