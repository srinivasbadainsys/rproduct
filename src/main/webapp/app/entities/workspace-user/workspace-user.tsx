import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkspaceUser } from 'app/shared/model/workspace-user.model';
import { getEntities } from './workspace-user.reducer';

export const WorkspaceUser = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const workspaceUserList = useAppSelector(state => state.workspaceUser.entities);
  const loading = useAppSelector(state => state.workspaceUser.loading);
  const totalItems = useAppSelector(state => state.workspaceUser.totalItems);

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
      <h2 id="workspace-user-heading" data-cy="WorkspaceUserHeading">
        <Translate contentKey="rproductApp.workspaceUser.home.title">Workspace Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.workspaceUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/workspace-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.workspaceUser.home.createLabel">Create new Workspace User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {workspaceUserList && workspaceUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.workspaceUser.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userId')}>
                  <Translate contentKey="rproductApp.workspaceUser.userId">User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('invitationKey')}>
                  <Translate contentKey="rproductApp.workspaceUser.invitationKey">Invitation Key</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('owns')}>
                  <Translate contentKey="rproductApp.workspaceUser.owns">Owns</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accepted')}>
                  <Translate contentKey="rproductApp.workspaceUser.accepted">Accepted</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('invited')}>
                  <Translate contentKey="rproductApp.workspaceUser.invited">Invited</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requested')}>
                  <Translate contentKey="rproductApp.workspaceUser.requested">Requested</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('barred')}>
                  <Translate contentKey="rproductApp.workspaceUser.barred">Barred</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('roles')}>
                  <Translate contentKey="rproductApp.workspaceUser.roles">Roles</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requestedOn')}>
                  <Translate contentKey="rproductApp.workspaceUser.requestedOn">Requested On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.workspaceUser.workspace">Workspace</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workspaceUserList.map((workspaceUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/workspace-user/${workspaceUser.id}`} color="link" size="sm">
                      {workspaceUser.id}
                    </Button>
                  </td>
                  <td>{workspaceUser.userId}</td>
                  <td>{workspaceUser.invitationKey}</td>
                  <td>{workspaceUser.owns ? 'true' : 'false'}</td>
                  <td>{workspaceUser.accepted ? 'true' : 'false'}</td>
                  <td>{workspaceUser.invited ? 'true' : 'false'}</td>
                  <td>{workspaceUser.requested ? 'true' : 'false'}</td>
                  <td>{workspaceUser.barred ? 'true' : 'false'}</td>
                  <td>{workspaceUser.roles}</td>
                  <td>
                    {workspaceUser.requestedOn ? (
                      <TextFormat type="date" value={workspaceUser.requestedOn} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {workspaceUser.workspace ? (
                      <Link to={`/workspace/${workspaceUser.workspace.id}`}>{workspaceUser.workspace.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/workspace-user/${workspaceUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/workspace-user/${workspaceUser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/workspace-user/${workspaceUser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.workspaceUser.home.notFound">No Workspace Users found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={workspaceUserList && workspaceUserList.length > 0 ? '' : 'd-none'}>
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

export default WorkspaceUser;
