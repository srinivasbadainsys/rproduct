import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client-guideline-submission-document.reducer';

export const ClientGuidelineSubmissionDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientGuidelineSubmissionDocumentEntity = useAppSelector(state => state.clientGuidelineSubmissionDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientGuidelineSubmissionDocumentDetailsHeading">
          <Translate contentKey="rproductApp.clientGuidelineSubmissionDocument.detail.title">ClientGuidelineSubmissionDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientGuidelineSubmissionDocumentEntity.id}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="rproductApp.clientGuidelineSubmissionDocument.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{clientGuidelineSubmissionDocumentEntity.clientId}</dd>
          <dt>
            <span id="documentTitle">
              <Translate contentKey="rproductApp.clientGuidelineSubmissionDocument.documentTitle">Document Title</Translate>
            </span>
          </dt>
          <dd>{clientGuidelineSubmissionDocumentEntity.documentTitle}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="rproductApp.clientGuidelineSubmissionDocument.description">Description</Translate>
            </span>
          </dt>
          <dd>{clientGuidelineSubmissionDocumentEntity.description}</dd>
          <dt>
            <span id="documentPath">
              <Translate contentKey="rproductApp.clientGuidelineSubmissionDocument.documentPath">Document Path</Translate>
            </span>
          </dt>
          <dd>{clientGuidelineSubmissionDocumentEntity.documentPath}</dd>
          <dt>
            <Translate contentKey="rproductApp.clientGuidelineSubmissionDocument.client">Client</Translate>
          </dt>
          <dd>{clientGuidelineSubmissionDocumentEntity.client ? clientGuidelineSubmissionDocumentEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client-guideline-submission-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/client-guideline-submission-document/${clientGuidelineSubmissionDocumentEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientGuidelineSubmissionDocumentDetail;
