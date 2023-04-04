import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContact } from 'app/shared/model/contact.model';
import { getEntities } from './contact.reducer';

export const Contact = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const contactList = useAppSelector(state => state.contact.entities);
  const loading = useAppSelector(state => state.contact.loading);
  const totalItems = useAppSelector(state => state.contact.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="contact-heading" data-cy="ContactHeading">
        <Translate contentKey="rproductApp.contact.home.title">Contacts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.contact.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/contact/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.contact.home.createLabel">Create new Contact</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contactList && contactList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.contact.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="rproductApp.contact.clientId">Client Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="rproductApp.contact.firstName">First Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="rproductApp.contact.lastName">Last Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('designation')}>
                  <Translate contentKey="rproductApp.contact.designation">Designation</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('officeNumber')}>
                  <Translate contentKey="rproductApp.contact.officeNumber">Office Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('officeNumberExtn')}>
                  <Translate contentKey="rproductApp.contact.officeNumberExtn">Office Number Extn</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mobileNumber')}>
                  <Translate contentKey="rproductApp.contact.mobileNumber">Mobile Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emailID')}>
                  <Translate contentKey="rproductApp.contact.emailID">Email ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('altEmailID')}>
                  <Translate contentKey="rproductApp.contact.altEmailID">Alt Email ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownershipIds')}>
                  <Translate contentKey="rproductApp.contact.ownershipIds">Ownership Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allowAccessToAllUsers')}>
                  <Translate contentKey="rproductApp.contact.allowAccessToAllUsers">Allow Access To All Users</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address1')}>
                  <Translate contentKey="rproductApp.contact.address1">Address 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address2')}>
                  <Translate contentKey="rproductApp.contact.address2">Address 2</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('area')}>
                  <Translate contentKey="rproductApp.contact.area">Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="rproductApp.contact.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="rproductApp.contact.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stateCode')}>
                  <Translate contentKey="rproductApp.contact.stateCode">State Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('county')}>
                  <Translate contentKey="rproductApp.contact.county">County</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="rproductApp.contact.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('countryCode')}>
                  <Translate contentKey="rproductApp.contact.countryCode">Country Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="rproductApp.contact.zipCode">Zip Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('profileURLs')}>
                  <Translate contentKey="rproductApp.contact.profileURLs">Profile UR Ls</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('messengerIDs')}>
                  <Translate contentKey="rproductApp.contact.messengerIDs">Messenger I Ds</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="rproductApp.contact.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientGroup')}>
                  <Translate contentKey="rproductApp.contact.clientGroup">Client Group</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('about')}>
                  <Translate contentKey="rproductApp.contact.about">About</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.contact.primaryOwnerUser">Primary Owner User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.contact.client">Client</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contactList.map((contact, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contact/${contact.id}`} color="link" size="sm">
                      {contact.id}
                    </Button>
                  </td>
                  <td>{contact.clientId}</td>
                  <td>{contact.firstName}</td>
                  <td>{contact.lastName}</td>
                  <td>{contact.designation}</td>
                  <td>{contact.officeNumber}</td>
                  <td>{contact.officeNumberExtn}</td>
                  <td>{contact.mobileNumber}</td>
                  <td>{contact.emailID}</td>
                  <td>{contact.altEmailID}</td>
                  <td>{contact.ownershipIds}</td>
                  <td>{contact.allowAccessToAllUsers ? 'true' : 'false'}</td>
                  <td>{contact.address1}</td>
                  <td>{contact.address2}</td>
                  <td>{contact.area}</td>
                  <td>{contact.city}</td>
                  <td>{contact.state}</td>
                  <td>{contact.stateCode}</td>
                  <td>{contact.county}</td>
                  <td>{contact.country}</td>
                  <td>{contact.countryCode}</td>
                  <td>{contact.zipCode}</td>
                  <td>{contact.profileURLs}</td>
                  <td>{contact.messengerIDs}</td>
                  <td>{contact.status}</td>
                  <td>{contact.clientGroup}</td>
                  <td>{contact.about}</td>
                  <td>
                    {contact.primaryOwnerUser ? (
                      <Link to={`/ruser/${contact.primaryOwnerUser.id}`}>{contact.primaryOwnerUser.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{contact.client ? <Link to={`/client/${contact.client.id}`}>{contact.client.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/contact/${contact.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/contact/${contact.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/contact/${contact.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="rproductApp.contact.home.notFound">No Contacts found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={contactList && contactList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Contact;
