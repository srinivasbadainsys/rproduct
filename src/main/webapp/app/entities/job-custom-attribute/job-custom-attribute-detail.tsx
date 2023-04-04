import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-custom-attribute.reducer';

export const JobCustomAttributeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobCustomAttributeEntity = useAppSelector(state => state.jobCustomAttribute.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobCustomAttributeDetailsHeading">
          <Translate contentKey="rproductApp.jobCustomAttribute.detail.title">JobCustomAttribute</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobCustomAttributeEntity.id}</dd>
          <dt>
            <span id="jobId">
              <Translate contentKey="rproductApp.jobCustomAttribute.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{jobCustomAttributeEntity.jobId}</dd>
          <dt>
            <span id="attributeName">
              <Translate contentKey="rproductApp.jobCustomAttribute.attributeName">Attribute Name</Translate>
            </span>
          </dt>
          <dd>{jobCustomAttributeEntity.attributeName}</dd>
          <dt>
            <span id="attributeType">
              <Translate contentKey="rproductApp.jobCustomAttribute.attributeType">Attribute Type</Translate>
            </span>
          </dt>
          <dd>{jobCustomAttributeEntity.attributeType}</dd>
          <dt>
            <span id="attributeValue">
              <Translate contentKey="rproductApp.jobCustomAttribute.attributeValue">Attribute Value</Translate>
            </span>
          </dt>
          <dd>{jobCustomAttributeEntity.attributeValue}</dd>
          <dt>
            <Translate contentKey="rproductApp.jobCustomAttribute.job">Job</Translate>
          </dt>
          <dd>{jobCustomAttributeEntity.job ? jobCustomAttributeEntity.job.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-custom-attribute" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-custom-attribute/${jobCustomAttributeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobCustomAttributeDetail;
