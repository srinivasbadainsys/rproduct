import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { getEntities } from './business-unit.reducer';

export const BusinessUnit = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const businessUnitList = useAppSelector(state => state.businessUnit.entities);
  const loading = useAppSelector(state => state.businessUnit.loading);
  const totalItems = useAppSelector(state => state.businessUnit.totalItems);

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
      <h2 id="business-unit-heading" data-cy="BusinessUnitHeading">
        <Translate contentKey="rproductApp.businessUnit.home.title">Business Units</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.businessUnit.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/business-unit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.businessUnit.home.createLabel">Create new Business Unit</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {businessUnitList && businessUnitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.businessUnit.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('unitName')}>
                  <Translate contentKey="rproductApp.businessUnit.unitName">Unit Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="rproductApp.businessUnit.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mobileContact')}>
                  <Translate contentKey="rproductApp.businessUnit.mobileContact">Mobile Contact</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('officeContact')}>
                  <Translate contentKey="rproductApp.businessUnit.officeContact">Office Contact</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('officeContactExtn')}>
                  <Translate contentKey="rproductApp.businessUnit.officeContactExtn">Office Contact Extn</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('area')}>
                  <Translate contentKey="rproductApp.businessUnit.area">Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="rproductApp.businessUnit.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="rproductApp.businessUnit.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stateCode')}>
                  <Translate contentKey="rproductApp.businessUnit.stateCode">State Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="rproductApp.businessUnit.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('countryCode')}>
                  <Translate contentKey="rproductApp.businessUnit.countryCode">Country Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="rproductApp.businessUnit.zipCode">Zip Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lat')}>
                  <Translate contentKey="rproductApp.businessUnit.lat">Lat</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lon')}>
                  <Translate contentKey="rproductApp.businessUnit.lon">Lon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('continent')}>
                  <Translate contentKey="rproductApp.businessUnit.continent">Continent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('continentCode')}>
                  <Translate contentKey="rproductApp.businessUnit.continentCode">Continent Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('point')}>
                  <Translate contentKey="rproductApp.businessUnit.point">Point</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {businessUnitList.map((businessUnit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/business-unit/${businessUnit.id}`} color="link" size="sm">
                      {businessUnit.id}
                    </Button>
                  </td>
                  <td>{businessUnit.unitName}</td>
                  <td>{businessUnit.address}</td>
                  <td>{businessUnit.mobileContact}</td>
                  <td>{businessUnit.officeContact}</td>
                  <td>{businessUnit.officeContactExtn}</td>
                  <td>{businessUnit.area}</td>
                  <td>{businessUnit.city}</td>
                  <td>{businessUnit.state}</td>
                  <td>{businessUnit.stateCode}</td>
                  <td>{businessUnit.country}</td>
                  <td>{businessUnit.countryCode}</td>
                  <td>{businessUnit.zipCode}</td>
                  <td>{businessUnit.lat}</td>
                  <td>{businessUnit.lon}</td>
                  <td>{businessUnit.continent}</td>
                  <td>{businessUnit.continentCode}</td>
                  <td>{businessUnit.point}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/business-unit/${businessUnit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/business-unit/${businessUnit.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/business-unit/${businessUnit.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.businessUnit.home.notFound">No Business Units found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={businessUnitList && businessUnitList.length > 0 ? '' : 'd-none'}>
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

export default BusinessUnit;
