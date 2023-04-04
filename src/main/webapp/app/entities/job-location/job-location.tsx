import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobLocation } from 'app/shared/model/job-location.model';
import { getEntities } from './job-location.reducer';

export const JobLocation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const jobLocationList = useAppSelector(state => state.jobLocation.entities);
  const loading = useAppSelector(state => state.jobLocation.loading);
  const totalItems = useAppSelector(state => state.jobLocation.totalItems);

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
      <h2 id="job-location-heading" data-cy="JobLocationHeading">
        <Translate contentKey="rproductApp.jobLocation.home.title">Job Locations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.jobLocation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/job-location/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.jobLocation.home.createLabel">Create new Job Location</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {jobLocationList && jobLocationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.jobLocation.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobId')}>
                  <Translate contentKey="rproductApp.jobLocation.jobId">Job Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('area')}>
                  <Translate contentKey="rproductApp.jobLocation.area">Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="rproductApp.jobLocation.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="rproductApp.jobLocation.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stateCode')}>
                  <Translate contentKey="rproductApp.jobLocation.stateCode">State Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="rproductApp.jobLocation.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('countryCode')}>
                  <Translate contentKey="rproductApp.jobLocation.countryCode">Country Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="rproductApp.jobLocation.zipCode">Zip Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lat')}>
                  <Translate contentKey="rproductApp.jobLocation.lat">Lat</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lon')}>
                  <Translate contentKey="rproductApp.jobLocation.lon">Lon</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('continent')}>
                  <Translate contentKey="rproductApp.jobLocation.continent">Continent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('continentCode')}>
                  <Translate contentKey="rproductApp.jobLocation.continentCode">Continent Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('point')}>
                  <Translate contentKey="rproductApp.jobLocation.point">Point</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.jobLocation.job">Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {jobLocationList.map((jobLocation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/job-location/${jobLocation.id}`} color="link" size="sm">
                      {jobLocation.id}
                    </Button>
                  </td>
                  <td>{jobLocation.jobId}</td>
                  <td>{jobLocation.area}</td>
                  <td>{jobLocation.city}</td>
                  <td>{jobLocation.state}</td>
                  <td>{jobLocation.stateCode}</td>
                  <td>{jobLocation.country}</td>
                  <td>{jobLocation.countryCode}</td>
                  <td>{jobLocation.zipCode}</td>
                  <td>{jobLocation.lat}</td>
                  <td>{jobLocation.lon}</td>
                  <td>{jobLocation.continent}</td>
                  <td>{jobLocation.continentCode}</td>
                  <td>{jobLocation.point}</td>
                  <td>{jobLocation.job ? <Link to={`/job/${jobLocation.job.id}`}>{jobLocation.job.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/job-location/${jobLocation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/job-location/${jobLocation.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/job-location/${jobLocation.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.jobLocation.home.notFound">No Job Locations found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={jobLocationList && jobLocationList.length > 0 ? '' : 'd-none'}>
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

export default JobLocation;
