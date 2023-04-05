import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICandidatePipeline } from 'app/shared/model/candidate-pipeline.model';
import { getEntities } from './candidate-pipeline.reducer';

export const CandidatePipeline = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const candidatePipelineList = useAppSelector(state => state.candidatePipeline.entities);
  const loading = useAppSelector(state => state.candidatePipeline.loading);
  const totalItems = useAppSelector(state => state.candidatePipeline.totalItems);

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
      <h2 id="candidate-pipeline-heading" data-cy="CandidatePipelineHeading">
        <Translate contentKey="rproductApp.candidatePipeline.home.title">Candidate Pipelines</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.candidatePipeline.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/candidate-pipeline/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.candidatePipeline.home.createLabel">Create new Candidate Pipeline</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {candidatePipelineList && candidatePipelineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.candidatePipeline.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobId')}>
                  <Translate contentKey="rproductApp.candidatePipeline.jobId">Job Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('statusId')}>
                  <Translate contentKey="rproductApp.candidatePipeline.statusId">Status Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('submissionStatus')}>
                  <Translate contentKey="rproductApp.candidatePipeline.submissionStatus">Submission Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('submissionStage')}>
                  <Translate contentKey="rproductApp.candidatePipeline.submissionStage">Submission Stage</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recruiterActions')}>
                  <Translate contentKey="rproductApp.candidatePipeline.recruiterActions">Recruiter Actions</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('candidateResponses')}>
                  <Translate contentKey="rproductApp.candidatePipeline.candidateResponses">Candidate Responses</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pipelineType')}>
                  <Translate contentKey="rproductApp.candidatePipeline.pipelineType">Pipeline Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reasonForNewJob')}>
                  <Translate contentKey="rproductApp.candidatePipeline.reasonForNewJob">Reason For New Job</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.candidatePipeline.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.candidatePipeline.job">Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {candidatePipelineList.map((candidatePipeline, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/candidate-pipeline/${candidatePipeline.id}`} color="link" size="sm">
                      {candidatePipeline.id}
                    </Button>
                  </td>
                  <td>{candidatePipeline.jobId}</td>
                  <td>{candidatePipeline.statusId}</td>
                  <td>{candidatePipeline.submissionStatus}</td>
                  <td>{candidatePipeline.submissionStage}</td>
                  <td>{candidatePipeline.recruiterActions}</td>
                  <td>{candidatePipeline.candidateResponses}</td>
                  <td>
                    <Translate contentKey={`rproductApp.CandidatePipelineType.${candidatePipeline.pipelineType}`} />
                  </td>
                  <td>{candidatePipeline.reasonForNewJob}</td>
                  <td>
                    {candidatePipeline.status ? (
                      <Link to={`/catalogue-value/${candidatePipeline.status.id}`}>{candidatePipeline.status.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{candidatePipeline.job ? <Link to={`/job/${candidatePipeline.job.id}`}>{candidatePipeline.job.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/candidate-pipeline/${candidatePipeline.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/candidate-pipeline/${candidatePipeline.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/candidate-pipeline/${candidatePipeline.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.candidatePipeline.home.notFound">No Candidate Pipelines found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={candidatePipelineList && candidatePipelineList.length > 0 ? '' : 'd-none'}>
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

export default CandidatePipeline;
