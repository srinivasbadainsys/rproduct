import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client-document.reducer';

export const ClientDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientDocumentEntity = useAppSelector(state => state.clientDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDocumentDetailsHeading">
          <Translate contentKey="rproductApp.clientDocument.detail.title">ClientDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.id}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="rproductApp.clientDocument.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.clientId}</dd>
          <dt>
            <span id="documentType">
              <Translate contentKey="rproductApp.clientDocument.documentType">Document Type</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.documentType}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="rproductApp.clientDocument.title">Title</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="rproductApp.clientDocument.description">Description</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.description}</dd>
          <dt>
            <span id="documentPath">
              <Translate contentKey="rproductApp.clientDocument.documentPath">Document Path</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.documentPath}</dd>
          <dt>
            <Translate contentKey="rproductApp.clientDocument.client">Client</Translate>
          </dt>
          <dd>{clientDocumentEntity.client ? clientDocumentEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client-document/${clientDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDocumentDetail;
