import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client-account.reducer';

export const ClientAccountDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientAccountEntity = useAppSelector(state => state.clientAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientAccountDetailsHeading">
          <Translate contentKey="rproductApp.clientAccount.detail.title">ClientAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.id}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="rproductApp.clientAccount.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.clientId}</dd>
          <dt>
            <span id="contactPerson">
              <Translate contentKey="rproductApp.clientAccount.contactPerson">Contact Person</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.contactPerson}</dd>
          <dt>
            <span id="mobileNumber">
              <Translate contentKey="rproductApp.clientAccount.mobileNumber">Mobile Number</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.mobileNumber}</dd>
          <dt>
            <span id="officeNumber">
              <Translate contentKey="rproductApp.clientAccount.officeNumber">Office Number</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.officeNumber}</dd>
          <dt>
            <span id="officeNumberExtn">
              <Translate contentKey="rproductApp.clientAccount.officeNumberExtn">Office Number Extn</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.officeNumberExtn}</dd>
          <dt>
            <span id="emailID">
              <Translate contentKey="rproductApp.clientAccount.emailID">Email ID</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.emailID}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="rproductApp.clientAccount.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{clientAccountEntity.designation}</dd>
          <dt>
            <Translate contentKey="rproductApp.clientAccount.client">Client</Translate>
          </dt>
          <dd>{clientAccountEntity.client ? clientAccountEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client-account/${clientAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientAccountDetail;
