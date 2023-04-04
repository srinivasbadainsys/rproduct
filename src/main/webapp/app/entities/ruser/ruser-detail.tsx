import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ruser.reducer';

export const RuserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ruserEntity = useAppSelector(state => state.ruser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ruserDetailsHeading">
          <Translate contentKey="rproductApp.ruser.detail.title">Ruser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.id}</dd>
          <dt>
            <span id="login">
              <Translate contentKey="rproductApp.ruser.login">Login</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.login}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="rproductApp.ruser.password">Password</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.password}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="rproductApp.ruser.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="rproductApp.ruser.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="rproductApp.ruser.email">Email</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.email}</dd>
          <dt>
            <span id="activated">
              <Translate contentKey="rproductApp.ruser.activated">Activated</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.activated ? 'true' : 'false'}</dd>
          <dt>
            <span id="langKey">
              <Translate contentKey="rproductApp.ruser.langKey">Lang Key</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.langKey}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="rproductApp.ruser.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.imageUrl}</dd>
          <dt>
            <span id="activationKey">
              <Translate contentKey="rproductApp.ruser.activationKey">Activation Key</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.activationKey}</dd>
          <dt>
            <span id="resetKey">
              <Translate contentKey="rproductApp.ruser.resetKey">Reset Key</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.resetKey}</dd>
          <dt>
            <span id="resetDate">
              <Translate contentKey="rproductApp.ruser.resetDate">Reset Date</Translate>
            </span>
          </dt>
          <dd>{ruserEntity.resetDate ? <TextFormat value={ruserEntity.resetDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/ruser" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ruser/${ruserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RuserDetail;
