import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contact.reducer';

export const ContactDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactEntity = useAppSelector(state => state.contact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactDetailsHeading">
          <Translate contentKey="rproductApp.contact.detail.title">Contact</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactEntity.id}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="rproductApp.contact.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{contactEntity.clientId}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="rproductApp.contact.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="rproductApp.contact.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.lastName}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="rproductApp.contact.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{contactEntity.designation}</dd>
          <dt>
            <span id="officeNumber">
              <Translate contentKey="rproductApp.contact.officeNumber">Office Number</Translate>
            </span>
          </dt>
          <dd>{contactEntity.officeNumber}</dd>
          <dt>
            <span id="officeNumberExtn">
              <Translate contentKey="rproductApp.contact.officeNumberExtn">Office Number Extn</Translate>
            </span>
          </dt>
          <dd>{contactEntity.officeNumberExtn}</dd>
          <dt>
            <span id="mobileNumber">
              <Translate contentKey="rproductApp.contact.mobileNumber">Mobile Number</Translate>
            </span>
          </dt>
          <dd>{contactEntity.mobileNumber}</dd>
          <dt>
            <span id="emailID">
              <Translate contentKey="rproductApp.contact.emailID">Email ID</Translate>
            </span>
          </dt>
          <dd>{contactEntity.emailID}</dd>
          <dt>
            <span id="altEmailID">
              <Translate contentKey="rproductApp.contact.altEmailID">Alt Email ID</Translate>
            </span>
          </dt>
          <dd>{contactEntity.altEmailID}</dd>
          <dt>
            <span id="ownershipIds">
              <Translate contentKey="rproductApp.contact.ownershipIds">Ownership Ids</Translate>
            </span>
          </dt>
          <dd>{contactEntity.ownershipIds}</dd>
          <dt>
            <span id="allowAccessToAllUsers">
              <Translate contentKey="rproductApp.contact.allowAccessToAllUsers">Allow Access To All Users</Translate>
            </span>
          </dt>
          <dd>{contactEntity.allowAccessToAllUsers ? 'true' : 'false'}</dd>
          <dt>
            <span id="address1">
              <Translate contentKey="rproductApp.contact.address1">Address 1</Translate>
            </span>
          </dt>
          <dd>{contactEntity.address1}</dd>
          <dt>
            <span id="address2">
              <Translate contentKey="rproductApp.contact.address2">Address 2</Translate>
            </span>
          </dt>
          <dd>{contactEntity.address2}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="rproductApp.contact.area">Area</Translate>
            </span>
          </dt>
          <dd>{contactEntity.area}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="rproductApp.contact.city">City</Translate>
            </span>
          </dt>
          <dd>{contactEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="rproductApp.contact.state">State</Translate>
            </span>
          </dt>
          <dd>{contactEntity.state}</dd>
          <dt>
            <span id="stateCode">
              <Translate contentKey="rproductApp.contact.stateCode">State Code</Translate>
            </span>
          </dt>
          <dd>{contactEntity.stateCode}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="rproductApp.contact.county">County</Translate>
            </span>
          </dt>
          <dd>{contactEntity.county}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="rproductApp.contact.country">Country</Translate>
            </span>
          </dt>
          <dd>{contactEntity.country}</dd>
          <dt>
            <span id="countryCode">
              <Translate contentKey="rproductApp.contact.countryCode">Country Code</Translate>
            </span>
          </dt>
          <dd>{contactEntity.countryCode}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="rproductApp.contact.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{contactEntity.zipCode}</dd>
          <dt>
            <span id="profileURLs">
              <Translate contentKey="rproductApp.contact.profileURLs">Profile UR Ls</Translate>
            </span>
          </dt>
          <dd>{contactEntity.profileURLs}</dd>
          <dt>
            <span id="messengerIDs">
              <Translate contentKey="rproductApp.contact.messengerIDs">Messenger I Ds</Translate>
            </span>
          </dt>
          <dd>{contactEntity.messengerIDs}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="rproductApp.contact.status">Status</Translate>
            </span>
          </dt>
          <dd>{contactEntity.status}</dd>
          <dt>
            <span id="clientGroup">
              <Translate contentKey="rproductApp.contact.clientGroup">Client Group</Translate>
            </span>
          </dt>
          <dd>{contactEntity.clientGroup}</dd>
          <dt>
            <span id="about">
              <Translate contentKey="rproductApp.contact.about">About</Translate>
            </span>
          </dt>
          <dd>{contactEntity.about}</dd>
          <dt>
            <Translate contentKey="rproductApp.contact.primaryOwnerUser">Primary Owner User</Translate>
          </dt>
          <dd>{contactEntity.primaryOwnerUser ? contactEntity.primaryOwnerUser.id : ''}</dd>
          <dt>
            <Translate contentKey="rproductApp.contact.client">Client</Translate>
          </dt>
          <dd>{contactEntity.client ? contactEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactDetail;
