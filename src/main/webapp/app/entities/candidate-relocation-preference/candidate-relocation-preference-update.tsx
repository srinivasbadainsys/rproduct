import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICandidate } from 'app/shared/model/candidate.model';
import { getEntities as getCandidates } from 'app/entities/candidate/candidate.reducer';
import { ICandidateRelocationPreference } from 'app/shared/model/candidate-relocation-preference.model';
import { getEntity, updateEntity, createEntity, reset } from './candidate-relocation-preference.reducer';

export const CandidateRelocationPreferenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const candidates = useAppSelector(state => state.candidate.entities);
  const candidateRelocationPreferenceEntity = useAppSelector(state => state.candidateRelocationPreference.entity);
  const loading = useAppSelector(state => state.candidateRelocationPreference.loading);
  const updating = useAppSelector(state => state.candidateRelocationPreference.updating);
  const updateSuccess = useAppSelector(state => state.candidateRelocationPreference.updateSuccess);

  const handleClose = () => {
    navigate('/candidate-relocation-preference' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCandidates({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...candidateRelocationPreferenceEntity,
      ...values,
      candidateId: candidates.find(it => it.id.toString() === values.candidateId.toString()),
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
          ...candidateRelocationPreferenceEntity,
          candidateId: candidateRelocationPreferenceEntity?.candidateId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="rproductApp.candidateRelocationPreference.home.createOrEditLabel"
            data-cy="CandidateRelocationPreferenceCreateUpdateHeading"
          >
            <Translate contentKey="rproductApp.candidateRelocationPreference.home.createOrEditLabel">
              Create or edit a CandidateRelocationPreference
            </Translate>
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
                  id="candidate-relocation-preference-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.candidateId')}
                id="candidate-relocation-preference-candidateId"
                name="candidateId"
                data-cy="candidateId"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.city')}
                id="candidate-relocation-preference-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.state')}
                id="candidate-relocation-preference-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.stateCode')}
                id="candidate-relocation-preference-stateCode"
                name="stateCode"
                data-cy="stateCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.county')}
                id="candidate-relocation-preference-county"
                name="county"
                data-cy="county"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.country')}
                id="candidate-relocation-preference-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.countryCode')}
                id="candidate-relocation-preference-countryCode"
                name="countryCode"
                data-cy="countryCode"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.candidateRelocationPreference.zipCode')}
                id="candidate-relocation-preference-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                id="candidate-relocation-preference-candidateId"
                name="candidateId"
                data-cy="candidateId"
                label={translate('rproductApp.candidateRelocationPreference.candidateId')}
                type="select"
              >
                <option value="" key="0" />
                {candidates
                  ? candidates.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/candidate-relocation-preference"
                replace
                color="info"
              >
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

export default CandidateRelocationPreferenceUpdate;
