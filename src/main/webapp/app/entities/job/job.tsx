import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJob } from 'app/shared/model/job.model';
import { getEntities } from './job.reducer';

export const Job = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const jobList = useAppSelector(state => state.job.entities);
  const loading = useAppSelector(state => state.job.loading);
  const totalItems = useAppSelector(state => state.job.totalItems);

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
      <h2 id="job-heading" data-cy="JobHeading">
        <Translate contentKey="rproductApp.job.home.title">Jobs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.job.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/job/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.job.home.createLabel">Create new Job</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {jobList && jobList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.job.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="rproductApp.job.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobCode')}>
                  <Translate contentKey="rproductApp.job.jobCode">Job Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientJobCode')}>
                  <Translate contentKey="rproductApp.job.clientJobCode">Client Job Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgName')}>
                  <Translate contentKey="rproductApp.job.orgName">Org Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgEmploymentTypeIds')}>
                  <Translate contentKey="rproductApp.job.orgEmploymentTypeIds">Org Employment Type Ids</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobRef')}>
                  <Translate contentKey="rproductApp.job.jobRef">Job Ref</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobSource')}>
                  <Translate contentKey="rproductApp.job.jobSource">Job Source</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('url')}>
                  <Translate contentKey="rproductApp.job.url">Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="rproductApp.job.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('publicJobTitle')}>
                  <Translate contentKey="rproductApp.job.publicJobTitle">Public Job Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('publicJobDescription')}>
                  <Translate contentKey="rproductApp.job.publicJobDescription">Public Job Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('autoCloseDate')}>
                  <Translate contentKey="rproductApp.job.autoCloseDate">Auto Close Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noOfPositions')}>
                  <Translate contentKey="rproductApp.job.noOfPositions">No Of Positions</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('departmentAltText')}>
                  <Translate contentKey="rproductApp.job.departmentAltText">Department Alt Text</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('displayCandRate')}>
                  <Translate contentKey="rproductApp.job.displayCandRate">Display Cand Rate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('candRateCurrency')}>
                  <Translate contentKey="rproductApp.job.candRateCurrency">Cand Rate Currency</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('candMinRate')}>
                  <Translate contentKey="rproductApp.job.candMinRate">Cand Min Rate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('candMaxRate')}>
                  <Translate contentKey="rproductApp.job.candMaxRate">Cand Max Rate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('candRateTimeSpan')}>
                  <Translate contentKey="rproductApp.job.candRateTimeSpan">Cand Rate Time Span</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('candRateTaxTerm')}>
                  <Translate contentKey="rproductApp.job.candRateTaxTerm">Cand Rate Tax Term</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('candSalaryAltDisplayText')}>
                  <Translate contentKey="rproductApp.job.candSalaryAltDisplayText">Cand Salary Alt Display Text</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherBenefitDetails')}>
                  <Translate contentKey="rproductApp.job.otherBenefitDetails">Other Benefit Details</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientBillRateCurrency')}>
                  <Translate contentKey="rproductApp.job.clientBillRateCurrency">Client Bill Rate Currency</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientBillRate')}>
                  <Translate contentKey="rproductApp.job.clientBillRate">Client Bill Rate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientBillRateTimeSpan')}>
                  <Translate contentKey="rproductApp.job.clientBillRateTimeSpan">Client Bill Rate Time Span</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientBillRateTaxTerm')}>
                  <Translate contentKey="rproductApp.job.clientBillRateTaxTerm">Client Bill Rate Tax Term</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workDuration')}>
                  <Translate contentKey="rproductApp.job.workDuration">Work Duration</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('immigrationStatus')}>
                  <Translate contentKey="rproductApp.job.immigrationStatus">Immigration Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('displayImmigrationStatus')}>
                  <Translate contentKey="rproductApp.job.displayImmigrationStatus">Display Immigration Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('skills')}>
                  <Translate contentKey="rproductApp.job.skills">Skills</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('altSkills')}>
                  <Translate contentKey="rproductApp.job.altSkills">Alt Skills</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tags')}>
                  <Translate contentKey="rproductApp.job.tags">Tags</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('qualificationIds')}>
                  <Translate contentKey="rproductApp.job.qualificationIds">Qualification Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('qualificationsAltText')}>
                  <Translate contentKey="rproductApp.job.qualificationsAltText">Qualifications Alt Text</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eduRequirements')}>
                  <Translate contentKey="rproductApp.job.eduRequirements">Edu Requirements</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expRequirements')}>
                  <Translate contentKey="rproductApp.job.expRequirements">Exp Requirements</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expAltText')}>
                  <Translate contentKey="rproductApp.job.expAltText">Exp Alt Text</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('minExpInYears')}>
                  <Translate contentKey="rproductApp.job.minExpInYears">Min Exp In Years</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maxExpInYears')}>
                  <Translate contentKey="rproductApp.job.maxExpInYears">Max Exp In Years</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('languageIds')}>
                  <Translate contentKey="rproductApp.job.languageIds">Language Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('visaRequirements')}>
                  <Translate contentKey="rproductApp.job.visaRequirements">Visa Requirements</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workAuthorizationIds')}>
                  <Translate contentKey="rproductApp.job.workAuthorizationIds">Work Authorization Ids</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('applicationFormType')}>
                  <Translate contentKey="rproductApp.job.applicationFormType">Application Form Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isPartnerJob')}>
                  <Translate contentKey="rproductApp.job.isPartnerJob">Is Partner Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('redirectionUrl')}>
                  <Translate contentKey="rproductApp.job.redirectionUrl">Redirection Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobStatus')}>
                  <Translate contentKey="rproductApp.job.jobStatus">Job Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('endClient')}>
                  <Translate contentKey="rproductApp.job.endClient">End Client</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('domainAlt')}>
                  <Translate contentKey="rproductApp.job.domainAlt">Domain Alt</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="rproductApp.job.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('additionalNotificationsToUserIds')}>
                  <Translate contentKey="rproductApp.job.additionalNotificationsToUserIds">Additional Notifications To User Ids</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('additionalNotificationsToTeamIds')}>
                  <Translate contentKey="rproductApp.job.additionalNotificationsToTeamIds">Additional Notifications To Team Ids</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requiredDocumentIds')}>
                  <Translate contentKey="rproductApp.job.requiredDocumentIds">Required Document Ids</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobCloseReasonOtherAlt')}>
                  <Translate contentKey="rproductApp.job.jobCloseReasonOtherAlt">Job Close Reason Other Alt</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addToCareerPage')}>
                  <Translate contentKey="rproductApp.job.addToCareerPage">Add To Career Page</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remoteJob')}>
                  <Translate contentKey="rproductApp.job.remoteJob">Remote Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hiringFor')}>
                  <Translate contentKey="rproductApp.job.hiringFor">Hiring For</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workDurationTimeSpan')}>
                  <Translate contentKey="rproductApp.job.workDurationTimeSpan">Work Duration Time Span</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxTerms')}>
                  <Translate contentKey="rproductApp.job.taxTerms">Tax Terms</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.postedByTeam">Posted By Team</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.businessUnit">Business Unit</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.client">Client</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.jobType">Job Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.industry">Industry</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.department">Department</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.clientRecruiter">Client Recruiter</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.clientManager">Client Manager</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.accountManager">Account Manager</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.headOfRecruitment">Head Of Recruitment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.deliveryLeadManager">Delivery Lead Manager</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.domain">Domain</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.srDeliveryManager">Sr Delivery Manager</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.teamLead">Team Lead</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.priority">Priority</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.jobCloseReason">Job Close Reason</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.jobCategory">Job Category</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.job.jobOccupation">Job Occupation</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {jobList.map((job, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/job/${job.id}`} color="link" size="sm">
                      {job.id}
                    </Button>
                  </td>
                  <td>{job.title}</td>
                  <td>{job.jobCode}</td>
                  <td>{job.clientJobCode}</td>
                  <td>{job.orgName}</td>
                  <td>{job.orgEmploymentTypeIds}</td>
                  <td>{job.jobRef}</td>
                  <td>{job.jobSource}</td>
                  <td>{job.url}</td>
                  <td>{job.description}</td>
                  <td>{job.publicJobTitle}</td>
                  <td>{job.publicJobDescription}</td>
                  <td>{job.autoCloseDate ? <TextFormat type="date" value={job.autoCloseDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{job.noOfPositions}</td>
                  <td>{job.departmentAltText}</td>
                  <td>{job.displayCandRate ? 'true' : 'false'}</td>
                  <td>{job.candRateCurrency}</td>
                  <td>{job.candMinRate}</td>
                  <td>{job.candMaxRate}</td>
                  <td>
                    <Translate contentKey={`rproductApp.SalaryTimeSpan.${job.candRateTimeSpan}`} />
                  </td>
                  <td>{job.candRateTaxTerm}</td>
                  <td>{job.candSalaryAltDisplayText}</td>
                  <td>{job.otherBenefitDetails}</td>
                  <td>{job.clientBillRateCurrency}</td>
                  <td>{job.clientBillRate}</td>
                  <td>
                    <Translate contentKey={`rproductApp.SalaryTimeSpan.${job.clientBillRateTimeSpan}`} />
                  </td>
                  <td>{job.clientBillRateTaxTerm}</td>
                  <td>{job.workDuration}</td>
                  <td>{job.immigrationStatus}</td>
                  <td>{job.displayImmigrationStatus}</td>
                  <td>{job.skills}</td>
                  <td>{job.altSkills}</td>
                  <td>{job.tags}</td>
                  <td>{job.qualificationIds}</td>
                  <td>{job.qualificationsAltText}</td>
                  <td>{job.eduRequirements}</td>
                  <td>{job.expRequirements}</td>
                  <td>{job.expAltText}</td>
                  <td>{job.minExpInYears}</td>
                  <td>{job.maxExpInYears}</td>
                  <td>{job.languageIds}</td>
                  <td>{job.visaRequirements}</td>
                  <td>{job.workAuthorizationIds}</td>
                  <td>
                    <Translate contentKey={`rproductApp.JobApplicationForm.${job.applicationFormType}`} />
                  </td>
                  <td>{job.isPartnerJob ? 'true' : 'false'}</td>
                  <td>{job.redirectionUrl}</td>
                  <td>
                    <Translate contentKey={`rproductApp.JobStatus.${job.jobStatus}`} />
                  </td>
                  <td>{job.endClient}</td>
                  <td>{job.domainAlt}</td>
                  <td>{job.comments}</td>
                  <td>{job.additionalNotificationsToUserIds}</td>
                  <td>{job.additionalNotificationsToTeamIds}</td>
                  <td>{job.requiredDocumentIds}</td>
                  <td>{job.jobCloseReasonOtherAlt}</td>
                  <td>{job.addToCareerPage ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`rproductApp.RemoteJob.${job.remoteJob}`} />
                  </td>
                  <td>{job.hiringFor}</td>
                  <td>{job.workDurationTimeSpan}</td>
                  <td>{job.taxTerms}</td>
                  <td>{job.postedByTeam ? <Link to={`/team/${job.postedByTeam.id}`}>{job.postedByTeam.id}</Link> : ''}</td>
                  <td>{job.businessUnit ? <Link to={`/business-unit/${job.businessUnit.id}`}>{job.businessUnit.id}</Link> : ''}</td>
                  <td>{job.client ? <Link to={`/client/${job.client.id}`}>{job.client.id}</Link> : ''}</td>
                  <td>{job.jobType ? <Link to={`/catalogue-value/${job.jobType.id}`}>{job.jobType.id}</Link> : ''}</td>
                  <td>{job.industry ? <Link to={`/catalogue-value/${job.industry.id}`}>{job.industry.id}</Link> : ''}</td>
                  <td>{job.department ? <Link to={`/catalogue-value/${job.department.id}`}>{job.department.id}</Link> : ''}</td>
                  <td>{job.clientRecruiter ? <Link to={`/ruser/${job.clientRecruiter.id}`}>{job.clientRecruiter.id}</Link> : ''}</td>
                  <td>{job.clientManager ? <Link to={`/contact/${job.clientManager.id}`}>{job.clientManager.id}</Link> : ''}</td>
                  <td>{job.accountManager ? <Link to={`/ruser/${job.accountManager.id}`}>{job.accountManager.id}</Link> : ''}</td>
                  <td>{job.headOfRecruitment ? <Link to={`/ruser/${job.headOfRecruitment.id}`}>{job.headOfRecruitment.id}</Link> : ''}</td>
                  <td>
                    {job.deliveryLeadManager ? <Link to={`/ruser/${job.deliveryLeadManager.id}`}>{job.deliveryLeadManager.id}</Link> : ''}
                  </td>
                  <td>{job.domain ? <Link to={`/catalogue-value/${job.domain.id}`}>{job.domain.id}</Link> : ''}</td>
                  <td>{job.srDeliveryManager ? <Link to={`/ruser/${job.srDeliveryManager.id}`}>{job.srDeliveryManager.id}</Link> : ''}</td>
                  <td>{job.teamLead ? <Link to={`/ruser/${job.teamLead.id}`}>{job.teamLead.id}</Link> : ''}</td>
                  <td>{job.priority ? <Link to={`/catalogue-value/${job.priority.id}`}>{job.priority.id}</Link> : ''}</td>
                  <td>{job.jobCloseReason ? <Link to={`/catalogue-value/${job.jobCloseReason.id}`}>{job.jobCloseReason.id}</Link> : ''}</td>
                  <td>{job.jobCategory ? <Link to={`/catalogue-value/${job.jobCategory.id}`}>{job.jobCategory.id}</Link> : ''}</td>
                  <td>{job.jobOccupation ? <Link to={`/catalogue-value/${job.jobOccupation.id}`}>{job.jobOccupation.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/job/${job.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/job/${job.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/job/${job.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.job.home.notFound">No Jobs found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={jobList && jobList.length > 0 ? '' : 'd-none'}>
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

export default Job;
