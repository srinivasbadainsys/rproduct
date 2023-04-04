import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { getEntities } from './client.reducer';

export const Client = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const clientList = useAppSelector(state => state.client.entities);
  const loading = useAppSelector(state => state.client.loading);
  const totalItems = useAppSelector(state => state.client.totalItems);

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
      <h2 id="client-heading" data-cy="ClientHeading">
        <Translate contentKey="rproductApp.client.home.title">Clients</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.client.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/client/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.client.home.createLabel">Create new Client</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clientList && clientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.client.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientVisibility')}>
                  <Translate contentKey="rproductApp.client.clientVisibility">Client Visibility</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('businessUnitIds')}>
                  <Translate contentKey="rproductApp.client.businessUnitIds">Business Unit Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientName')}>
                  <Translate contentKey="rproductApp.client.clientName">Client Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vmsClientName')}>
                  <Translate contentKey="rproductApp.client.vmsClientName">Vms Client Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('federalID')}>
                  <Translate contentKey="rproductApp.client.federalID">Federal ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contactNumber')}>
                  <Translate contentKey="rproductApp.client.contactNumber">Contact Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="rproductApp.client.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="rproductApp.client.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('area')}>
                  <Translate contentKey="rproductApp.client.area">Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="rproductApp.client.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="rproductApp.client.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stateCode')}>
                  <Translate contentKey="rproductApp.client.stateCode">State Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('county')}>
                  <Translate contentKey="rproductApp.client.county">County</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="rproductApp.client.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('countryCode')}>
                  <Translate contentKey="rproductApp.client.countryCode">Country Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="rproductApp.client.zipCode">Zip Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('website')}>
                  <Translate contentKey="rproductApp.client.website">Website</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sendRequirement')}>
                  <Translate contentKey="rproductApp.client.sendRequirement">Send Requirement</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sendHotList')}>
                  <Translate contentKey="rproductApp.client.sendHotList">Send Hot List</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fax')}>
                  <Translate contentKey="rproductApp.client.fax">Fax</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="rproductApp.client.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allowAccessToAllUsers')}>
                  <Translate contentKey="rproductApp.client.allowAccessToAllUsers">Allow Access To All Users</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownershipIds')}>
                  <Translate contentKey="rproductApp.client.ownershipIds">Ownership Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientLeadIds')}>
                  <Translate contentKey="rproductApp.client.clientLeadIds">Client Lead Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requiredDocuments')}>
                  <Translate contentKey="rproductApp.client.requiredDocuments">Required Documents</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('practiceAlt')}>
                  <Translate contentKey="rproductApp.client.practiceAlt">Practice Alt</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('aboutCompany')}>
                  <Translate contentKey="rproductApp.client.aboutCompany">About Company</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stopNotifyingClientContactOnSubmitToClient')}>
                  <Translate contentKey="rproductApp.client.stopNotifyingClientContactOnSubmitToClient">
                    Stop Notifying Client Contact On Submit To Client
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('defaultForJobPostings')}>
                  <Translate contentKey="rproductApp.client.defaultForJobPostings">Default For Job Postings</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('submissionGuidelines')}>
                  <Translate contentKey="rproductApp.client.submissionGuidelines">Submission Guidelines</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('assignedTo')}>
                  <Translate contentKey="rproductApp.client.assignedTo">Assigned To</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('assignedToTeams')}>
                  <Translate contentKey="rproductApp.client.assignedToTeams">Assigned To Teams</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('salesManagers')}>
                  <Translate contentKey="rproductApp.client.salesManagers">Sales Managers</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accountManagers')}>
                  <Translate contentKey="rproductApp.client.accountManagers">Account Managers</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recruitmentManagers')}>
                  <Translate contentKey="rproductApp.client.recruitmentManagers">Recruitment Managers</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('defaultTATConfigForJobPostingOrVMSJobs')}>
                  <Translate contentKey="rproductApp.client.defaultTATConfigForJobPostingOrVMSJobs">
                    Default TAT Config For Job Posting Or VMS Jobs
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('defaultTATConfigTimespan')}>
                  <Translate contentKey="rproductApp.client.defaultTATConfigTimespan">Default TAT Config Timespan</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notifyOnTATToUserTypes')}>
                  <Translate contentKey="rproductApp.client.notifyOnTATToUserTypes">Notify On TAT To User Types</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notifyOnTATToUserIds')}>
                  <Translate contentKey="rproductApp.client.notifyOnTATToUserIds">Notify On TAT To User Ids</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxTermIds')}>
                  <Translate contentKey="rproductApp.client.taxTermIds">Tax Term Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workAuthorizationIds')}>
                  <Translate contentKey="rproductApp.client.workAuthorizationIds">Work Authorization Ids</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('postJobOnCareersPage')}>
                  <Translate contentKey="rproductApp.client.postJobOnCareersPage">Post Job On Careers Page</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('defaultPayRateTaxTerm')}>
                  <Translate contentKey="rproductApp.client.defaultPayRateTaxTerm">Default Pay Rate Tax Term</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('defaultBillRateTaxTerm')}>
                  <Translate contentKey="rproductApp.client.defaultBillRateTaxTerm">Default Bill Rate Tax Term</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('referencesMandatoryForSubmission')}>
                  <Translate contentKey="rproductApp.client.referencesMandatoryForSubmission">
                    References Mandatory For Submission
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maxSubmissions')}>
                  <Translate contentKey="rproductApp.client.maxSubmissions">Max Submissions</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noOfPositions')}>
                  <Translate contentKey="rproductApp.client.noOfPositions">No Of Positions</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('markUp')}>
                  <Translate contentKey="rproductApp.client.markUp">Mark Up</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('msp')}>
                  <Translate contentKey="rproductApp.client.msp">Msp</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailSubject')}>
                  <Translate contentKey="rproductApp.client.mailSubject">Mail Subject</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailBody')}>
                  <Translate contentKey="rproductApp.client.mailBody">Mail Body</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fieldsForExcel')}>
                  <Translate contentKey="rproductApp.client.fieldsForExcel">Fields For Excel</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.client.primaryBusinessUnit">Primary Business Unit</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.client.industry">Industry</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.client.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.client.paymentTerms">Payment Terms</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.client.practice">Practice</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.client.primaryOwnerUser">Primary Owner User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clientList.map((client, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/client/${client.id}`} color="link" size="sm">
                      {client.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`rproductApp.ClientVisibility.${client.clientVisibility}`} />
                  </td>
                  <td>{client.businessUnitIds}</td>
                  <td>{client.clientName}</td>
                  <td>{client.vmsClientName}</td>
                  <td>{client.federalID}</td>
                  <td>{client.contactNumber}</td>
                  <td>{client.email}</td>
                  <td>{client.address}</td>
                  <td>{client.area}</td>
                  <td>{client.city}</td>
                  <td>{client.state}</td>
                  <td>{client.stateCode}</td>
                  <td>{client.county}</td>
                  <td>{client.country}</td>
                  <td>{client.countryCode}</td>
                  <td>{client.zipCode}</td>
                  <td>{client.website}</td>
                  <td>{client.sendRequirement ? 'true' : 'false'}</td>
                  <td>{client.sendHotList ? 'true' : 'false'}</td>
                  <td>{client.fax}</td>
                  <td>{client.status}</td>
                  <td>{client.allowAccessToAllUsers ? 'true' : 'false'}</td>
                  <td>{client.ownershipIds}</td>
                  <td>{client.clientLeadIds}</td>
                  <td>{client.requiredDocuments}</td>
                  <td>{client.practiceAlt}</td>
                  <td>{client.aboutCompany}</td>
                  <td>{client.stopNotifyingClientContactOnSubmitToClient ? 'true' : 'false'}</td>
                  <td>{client.defaultForJobPostings ? 'true' : 'false'}</td>
                  <td>{client.submissionGuidelines}</td>
                  <td>{client.assignedTo}</td>
                  <td>{client.assignedToTeams}</td>
                  <td>{client.salesManagers}</td>
                  <td>{client.accountManagers}</td>
                  <td>{client.recruitmentManagers}</td>
                  <td>{client.defaultTATConfigForJobPostingOrVMSJobs}</td>
                  <td>{client.defaultTATConfigTimespan}</td>
                  <td>{client.notifyOnTATToUserTypes}</td>
                  <td>{client.notifyOnTATToUserIds}</td>
                  <td>{client.taxTermIds}</td>
                  <td>{client.workAuthorizationIds}</td>
                  <td>{client.postJobOnCareersPage ? 'true' : 'false'}</td>
                  <td>{client.defaultPayRateTaxTerm}</td>
                  <td>{client.defaultBillRateTaxTerm}</td>
                  <td>{client.referencesMandatoryForSubmission ? 'true' : 'false'}</td>
                  <td>{client.maxSubmissions}</td>
                  <td>{client.noOfPositions}</td>
                  <td>{client.markUp}</td>
                  <td>{client.msp}</td>
                  <td>{client.mailSubject}</td>
                  <td>{client.mailBody}</td>
                  <td>{client.fieldsForExcel}</td>
                  <td>
                    {client.primaryBusinessUnit ? (
                      <Link to={`/business-unit/${client.primaryBusinessUnit.id}`}>{client.primaryBusinessUnit.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{client.industry ? <Link to={`/catalogue-value/${client.industry.id}`}>{client.industry.id}</Link> : ''}</td>
                  <td>{client.category ? <Link to={`/catalogue-value/${client.category.id}`}>{client.category.id}</Link> : ''}</td>
                  <td>
                    {client.paymentTerms ? <Link to={`/catalogue-value/${client.paymentTerms.id}`}>{client.paymentTerms.id}</Link> : ''}
                  </td>
                  <td>{client.practice ? <Link to={`/catalogue-value/${client.practice.id}`}>{client.practice.id}</Link> : ''}</td>
                  <td>
                    {client.primaryOwnerUser ? <Link to={`/ruser/${client.primaryOwnerUser.id}`}>{client.primaryOwnerUser.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/client/${client.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/client/${client.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/client/${client.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.client.home.notFound">No Clients found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={clientList && clientList.length > 0 ? '' : 'd-none'}>
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

export default Client;
