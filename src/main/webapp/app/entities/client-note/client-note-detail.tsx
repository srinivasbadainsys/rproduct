import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client-note.reducer';

export const ClientNoteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientNoteEntity = useAppSelector(state => state.clientNote.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientNoteDetailsHeading">
          <Translate contentKey="rproductApp.clientNote.detail.title">ClientNote</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientNoteEntity.id}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="rproductApp.clientNote.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{clientNoteEntity.clientId}</dd>
          <dt>
            <span id="action">
              <Translate contentKey="rproductApp.clientNote.action">Action</Translate>
            </span>
          </dt>
          <dd>{clientNoteEntity.action}</dd>
          <dt>
            <span id="notesPriority">
              <Translate contentKey="rproductApp.clientNote.notesPriority">Notes Priority</Translate>
            </span>
          </dt>
          <dd>{clientNoteEntity.notesPriority}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="rproductApp.clientNote.note">Note</Translate>
            </span>
          </dt>
          <dd>{clientNoteEntity.note}</dd>
          <dt>
            <span id="notifyToUsers">
              <Translate contentKey="rproductApp.clientNote.notifyToUsers">Notify To Users</Translate>
            </span>
          </dt>
          <dd>{clientNoteEntity.notifyToUsers}</dd>
          <dt>
            <span id="remindMe">
              <Translate contentKey="rproductApp.clientNote.remindMe">Remind Me</Translate>
            </span>
          </dt>
          <dd>{clientNoteEntity.remindMe ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="rproductApp.clientNote.client">Client</Translate>
          </dt>
          <dd>{clientNoteEntity.client ? clientNoteEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/client-note" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client-note/${clientNoteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientNoteDetail;
