import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './candidate-relocation-preference.reducer';

export const CandidateRelocationPreferenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const candidateRelocationPreferenceEntity = useAppSelector(state => state.candidateRelocationPreference.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="candidateRelocationPreferenceDetailsHeading">
          <Translate contentKey="rproductApp.candidateRelocationPreference.detail.title">CandidateRelocationPreference</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.id}</dd>
          <dt>
            <span id="candidateId">
              <Translate contentKey="rproductApp.candidateRelocationPreference.candidateId">Candidate Id</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.candidateId}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="rproductApp.candidateRelocationPreference.city">City</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="rproductApp.candidateRelocationPreference.state">State</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.state}</dd>
          <dt>
            <span id="stateCode">
              <Translate contentKey="rproductApp.candidateRelocationPreference.stateCode">State Code</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.stateCode}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="rproductApp.candidateRelocationPreference.county">County</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.county}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="rproductApp.candidateRelocationPreference.country">Country</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.country}</dd>
          <dt>
            <span id="countryCode">
              <Translate contentKey="rproductApp.candidateRelocationPreference.countryCode">Country Code</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.countryCode}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="rproductApp.candidateRelocationPreference.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.zipCode}</dd>
          <dt>
            <Translate contentKey="rproductApp.candidateRelocationPreference.candidateId">Candidate Id</Translate>
          </dt>
          <dd>{candidateRelocationPreferenceEntity.candidateId ? candidateRelocationPreferenceEntity.candidateId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/candidate-relocation-preference" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/candidate-relocation-preference/${candidateRelocationPreferenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CandidateRelocationPreferenceDetail;
