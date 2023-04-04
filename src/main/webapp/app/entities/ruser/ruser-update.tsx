import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRuser } from 'app/shared/model/ruser.model';
import { getEntity, updateEntity, createEntity, reset } from './ruser.reducer';

export const RuserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ruserEntity = useAppSelector(state => state.ruser.entity);
  const loading = useAppSelector(state => state.ruser.loading);
  const updating = useAppSelector(state => state.ruser.updating);
  const updateSuccess = useAppSelector(state => state.ruser.updateSuccess);

  const handleClose = () => {
    navigate('/ruser' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.resetDate = convertDateTimeToServer(values.resetDate);

    const entity = {
      ...ruserEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          resetDate: displayDefaultDateTime(),
        }
      : {
          ...ruserEntity,
          resetDate: convertDateTimeFromServer(ruserEntity.resetDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rproductApp.ruser.home.createOrEditLabel" data-cy="RuserCreateUpdateHeading">
            <Translate contentKey="rproductApp.ruser.home.createOrEditLabel">Create or edit a Ruser</Translate>
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
                  id="ruser-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('rproductApp.ruser.login')} id="ruser-login" name="login" data-cy="login" type="text" />
              <ValidatedField
                label={translate('rproductApp.ruser.password')}
                id="ruser-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.ruser.firstName')}
                id="ruser-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.ruser.lastName')}
                id="ruser-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField label={translate('rproductApp.ruser.email')} id="ruser-email" name="email" data-cy="email" type="text" />
              <ValidatedField
                label={translate('rproductApp.ruser.activated')}
                id="ruser-activated"
                name="activated"
                data-cy="activated"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('rproductApp.ruser.langKey')}
                id="ruser-langKey"
                name="langKey"
                data-cy="langKey"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.ruser.imageUrl')}
                id="ruser-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.ruser.activationKey')}
                id="ruser-activationKey"
                name="activationKey"
                data-cy="activationKey"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.ruser.resetKey')}
                id="ruser-resetKey"
                name="resetKey"
                data-cy="resetKey"
                type="text"
              />
              <ValidatedField
                label={translate('rproductApp.ruser.resetDate')}
                id="ruser-resetDate"
                name="resetDate"
                data-cy="resetDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ruser" replace color="info">
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

export default RuserUpdate;
