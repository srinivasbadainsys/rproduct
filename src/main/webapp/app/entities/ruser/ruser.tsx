import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRuser } from 'app/shared/model/ruser.model';
import { getEntities } from './ruser.reducer';

export const Ruser = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const ruserList = useAppSelector(state => state.ruser.entities);
  const loading = useAppSelector(state => state.ruser.loading);
  const totalItems = useAppSelector(state => state.ruser.totalItems);

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
      <h2 id="ruser-heading" data-cy="RuserHeading">
        <Translate contentKey="rproductApp.ruser.home.title">Rusers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.ruser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/ruser/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.ruser.home.createLabel">Create new Ruser</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ruserList && ruserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.ruser.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('login')}>
                  <Translate contentKey="rproductApp.ruser.login">Login</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('password')}>
                  <Translate contentKey="rproductApp.ruser.password">Password</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="rproductApp.ruser.firstName">First Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="rproductApp.ruser.lastName">Last Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="rproductApp.ruser.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activated')}>
                  <Translate contentKey="rproductApp.ruser.activated">Activated</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('langKey')}>
                  <Translate contentKey="rproductApp.ruser.langKey">Lang Key</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('imageUrl')}>
                  <Translate contentKey="rproductApp.ruser.imageUrl">Image Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activationKey')}>
                  <Translate contentKey="rproductApp.ruser.activationKey">Activation Key</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resetKey')}>
                  <Translate contentKey="rproductApp.ruser.resetKey">Reset Key</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resetDate')}>
                  <Translate contentKey="rproductApp.ruser.resetDate">Reset Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ruserList.map((ruser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ruser/${ruser.id}`} color="link" size="sm">
                      {ruser.id}
                    </Button>
                  </td>
                  <td>{ruser.login}</td>
                  <td>{ruser.password}</td>
                  <td>{ruser.firstName}</td>
                  <td>{ruser.lastName}</td>
                  <td>{ruser.email}</td>
                  <td>{ruser.activated ? 'true' : 'false'}</td>
                  <td>{ruser.langKey}</td>
                  <td>{ruser.imageUrl}</td>
                  <td>{ruser.activationKey}</td>
                  <td>{ruser.resetKey}</td>
                  <td>{ruser.resetDate ? <TextFormat type="date" value={ruser.resetDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ruser/${ruser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/ruser/${ruser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/ruser/${ruser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.ruser.home.notFound">No Rusers found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={ruserList && ruserList.length > 0 ? '' : 'd-none'}>
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

export default Ruser;
