import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkspace } from 'app/shared/model/workspace.model';
import { getEntities } from './workspace.reducer';

export const Workspace = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const workspaceList = useAppSelector(state => state.workspace.entities);
  const loading = useAppSelector(state => state.workspace.loading);
  const totalItems = useAppSelector(state => state.workspace.totalItems);

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
      <h2 id="workspace-heading" data-cy="WorkspaceHeading">
        <Translate contentKey="rproductApp.workspace.home.title">Workspaces</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.workspace.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/workspace/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.workspace.home.createLabel">Create new Workspace</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {workspaceList && workspaceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.workspace.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="rproductApp.workspace.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgName')}>
                  <Translate contentKey="rproductApp.workspace.orgName">Org Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('about')}>
                  <Translate contentKey="rproductApp.workspace.about">About</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('link')}>
                  <Translate contentKey="rproductApp.workspace.link">Link</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgURLs')}>
                  <Translate contentKey="rproductApp.workspace.orgURLs">Org UR Ls</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownerUserId')}>
                  <Translate contentKey="rproductApp.workspace.ownerUserId">Owner User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mainPhoneNumber')}>
                  <Translate contentKey="rproductApp.workspace.mainPhoneNumber">Main Phone Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('altPhoneNumbers')}>
                  <Translate contentKey="rproductApp.workspace.altPhoneNumbers">Alt Phone Numbers</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mainContactEmail')}>
                  <Translate contentKey="rproductApp.workspace.mainContactEmail">Main Contact Email</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('altContactEmails')}>
                  <Translate contentKey="rproductApp.workspace.altContactEmails">Alt Contact Emails</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="rproductApp.workspace.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enableAutoJoin')}>
                  <Translate contentKey="rproductApp.workspace.enableAutoJoin">Enable Auto Join</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maxUsers')}>
                  <Translate contentKey="rproductApp.workspace.maxUsers">Max Users</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tags')}>
                  <Translate contentKey="rproductApp.workspace.tags">Tags</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('domains')}>
                  <Translate contentKey="rproductApp.workspace.domains">Domains</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workspaceList.map((workspace, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/workspace/${workspace.id}`} color="link" size="sm">
                      {workspace.id}
                    </Button>
                  </td>
                  <td>{workspace.name}</td>
                  <td>{workspace.orgName}</td>
                  <td>{workspace.about}</td>
                  <td>{workspace.link}</td>
                  <td>{workspace.orgURLs}</td>
                  <td>{workspace.ownerUserId}</td>
                  <td>{workspace.mainPhoneNumber}</td>
                  <td>{workspace.altPhoneNumbers}</td>
                  <td>{workspace.mainContactEmail}</td>
                  <td>{workspace.altContactEmails}</td>
                  <td>{workspace.status}</td>
                  <td>{workspace.enableAutoJoin ? 'true' : 'false'}</td>
                  <td>{workspace.maxUsers}</td>
                  <td>{workspace.tags}</td>
                  <td>{workspace.domains}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/workspace/${workspace.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/workspace/${workspace.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/workspace/${workspace.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.workspace.home.notFound">No Workspaces found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={workspaceList && workspaceList.length > 0 ? '' : 'd-none'}>
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

export default Workspace;
