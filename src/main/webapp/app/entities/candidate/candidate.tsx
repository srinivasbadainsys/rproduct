import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICandidate } from 'app/shared/model/candidate.model';
import { getEntities } from './candidate.reducer';

export const Candidate = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const candidateList = useAppSelector(state => state.candidate.entities);
  const loading = useAppSelector(state => state.candidate.loading);
  const totalItems = useAppSelector(state => state.candidate.totalItems);

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
      <h2 id="candidate-heading" data-cy="CandidateHeading">
        <Translate contentKey="rproductApp.candidate.home.title">Candidates</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rproductApp.candidate.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/candidate/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rproductApp.candidate.home.createLabel">Create new Candidate</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {candidateList && candidateList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rproductApp.candidate.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('salutation')}>
                  <Translate contentKey="rproductApp.candidate.salutation">Salutation</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="rproductApp.candidate.firstName">First Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('middleName')}>
                  <Translate contentKey="rproductApp.candidate.middleName">Middle Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="rproductApp.candidate.lastName">Last Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="rproductApp.candidate.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('altEmails')}>
                  <Translate contentKey="rproductApp.candidate.altEmails">Alt Emails</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phone')}>
                  <Translate contentKey="rproductApp.candidate.phone">Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('altPhones')}>
                  <Translate contentKey="rproductApp.candidate.altPhones">Alt Phones</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dob')}>
                  <Translate contentKey="rproductApp.candidate.dob">Dob</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nationality')}>
                  <Translate contentKey="rproductApp.candidate.nationality">Nationality</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workAuthorizationId')}>
                  <Translate contentKey="rproductApp.candidate.workAuthorizationId">Work Authorization Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="rproductApp.candidate.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('area')}>
                  <Translate contentKey="rproductApp.candidate.area">Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="rproductApp.candidate.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="rproductApp.candidate.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stateCode')}>
                  <Translate contentKey="rproductApp.candidate.stateCode">State Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('county')}>
                  <Translate contentKey="rproductApp.candidate.county">County</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="rproductApp.candidate.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('countryCode')}>
                  <Translate contentKey="rproductApp.candidate.countryCode">Country Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="rproductApp.candidate.zipCode">Zip Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('source')}>
                  <Translate contentKey="rproductApp.candidate.source">Source</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalExpInYears')}>
                  <Translate contentKey="rproductApp.candidate.totalExpInYears">Total Exp In Years</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalExpInMonths')}>
                  <Translate contentKey="rproductApp.candidate.totalExpInMonths">Total Exp In Months</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('relevantExpInYears')}>
                  <Translate contentKey="rproductApp.candidate.relevantExpInYears">Relevant Exp In Years</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('relevantExpInMonths')}>
                  <Translate contentKey="rproductApp.candidate.relevantExpInMonths">Relevant Exp In Months</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('referredBy')}>
                  <Translate contentKey="rproductApp.candidate.referredBy">Referred By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownershipUserId')}>
                  <Translate contentKey="rproductApp.candidate.ownershipUserId">Ownership User Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentJobTitle')}>
                  <Translate contentKey="rproductApp.candidate.currentJobTitle">Current Job Title</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentEmployer')}>
                  <Translate contentKey="rproductApp.candidate.currentEmployer">Current Employer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentJobTypeId')}>
                  <Translate contentKey="rproductApp.candidate.currentJobTypeId">Current Job Type Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentPayCurrency')}>
                  <Translate contentKey="rproductApp.candidate.currentPayCurrency">Current Pay Currency</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentPay')}>
                  <Translate contentKey="rproductApp.candidate.currentPay">Current Pay</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentPayMonthly')}>
                  <Translate contentKey="rproductApp.candidate.currentPayMonthly">Current Pay Monthly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentPayHourly')}>
                  <Translate contentKey="rproductApp.candidate.currentPayHourly">Current Pay Hourly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentPayYearly')}>
                  <Translate contentKey="rproductApp.candidate.currentPayYearly">Current Pay Yearly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currentPayTimeSpan')}>
                  <Translate contentKey="rproductApp.candidate.currentPayTimeSpan">Current Pay Time Span</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherCurrentBenefits')}>
                  <Translate contentKey="rproductApp.candidate.otherCurrentBenefits">Other Current Benefits</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayCurrency')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayCurrency">Expected Pay Currency</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMin')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMin">Expected Pay Min</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMax')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMax">Expected Pay Max</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMinMonthly')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMinMonthly">Expected Pay Min Monthly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMinHourly')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMinHourly">Expected Pay Min Hourly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMinYearly')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMinYearly">Expected Pay Min Yearly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMaxMonthly')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMaxMonthly">Expected Pay Max Monthly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMaxHourly')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMaxHourly">Expected Pay Max Hourly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayMaxYearly')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayMaxYearly">Expected Pay Max Yearly</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayTimeSpan')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayTimeSpan">Expected Pay Time Span</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expectedPayTaxTermId')}>
                  <Translate contentKey="rproductApp.candidate.expectedPayTaxTermId">Expected Pay Tax Term Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherExpectedBenefits')}>
                  <Translate contentKey="rproductApp.candidate.otherExpectedBenefits">Other Expected Benefits</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('additionalComments')}>
                  <Translate contentKey="rproductApp.candidate.additionalComments">Additional Comments</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('relocation')}>
                  <Translate contentKey="rproductApp.candidate.relocation">Relocation</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('familyWillingToRelocate')}>
                  <Translate contentKey="rproductApp.candidate.familyWillingToRelocate">Family Willing To Relocate</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('relocationType')}>
                  <Translate contentKey="rproductApp.candidate.relocationType">Relocation Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('relocationRemarks')}>
                  <Translate contentKey="rproductApp.candidate.relocationRemarks">Relocation Remarks</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxTermIds')}>
                  <Translate contentKey="rproductApp.candidate.taxTermIds">Tax Term Ids</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('noticePeriodInDays')}>
                  <Translate contentKey="rproductApp.candidate.noticePeriodInDays">Notice Period In Days</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workAuthorizationExpiry')}>
                  <Translate contentKey="rproductApp.candidate.workAuthorizationExpiry">Work Authorization Expiry</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gpa')}>
                  <Translate contentKey="rproductApp.candidate.gpa">Gpa</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('aadharNumber')}>
                  <Translate contentKey="rproductApp.candidate.aadharNumber">Aadhar Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkedInProfileURL')}>
                  <Translate contentKey="rproductApp.candidate.linkedInProfileURL">Linked In Profile URL</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherSocialURLs')}>
                  <Translate contentKey="rproductApp.candidate.otherSocialURLs">Other Social UR Ls</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tags')}>
                  <Translate contentKey="rproductApp.candidate.tags">Tags</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resumes')}>
                  <Translate contentKey="rproductApp.candidate.resumes">Resumes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rightToReperesent')}>
                  <Translate contentKey="rproductApp.candidate.rightToReperesent">Right To Reperesent</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('skills')}>
                  <Translate contentKey="rproductApp.candidate.skills">Skills</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('altSkills')}>
                  <Translate contentKey="rproductApp.candidate.altSkills">Alt Skills</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('technologies')}>
                  <Translate contentKey="rproductApp.candidate.technologies">Technologies</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('certifications')}>
                  <Translate contentKey="rproductApp.candidate.certifications">Certifications</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('languages')}>
                  <Translate contentKey="rproductApp.candidate.languages">Languages</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workExperience')}>
                  <Translate contentKey="rproductApp.candidate.workExperience">Work Experience</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('education')}>
                  <Translate contentKey="rproductApp.candidate.education">Education</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('employerDetails')}>
                  <Translate contentKey="rproductApp.candidate.employerDetails">Employer Details</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('documents')}>
                  <Translate contentKey="rproductApp.candidate.documents">Documents</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.candidate.workAuthorization">Work Authorization</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.candidate.ownershipUser">Ownership User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.candidate.currentJobType">Current Job Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="rproductApp.candidate.expectedPayTaxTerm">Expected Pay Tax Term</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {candidateList.map((candidate, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/candidate/${candidate.id}`} color="link" size="sm">
                      {candidate.id}
                    </Button>
                  </td>
                  <td>{candidate.salutation}</td>
                  <td>{candidate.firstName}</td>
                  <td>{candidate.middleName}</td>
                  <td>{candidate.lastName}</td>
                  <td>{candidate.email}</td>
                  <td>{candidate.altEmails}</td>
                  <td>{candidate.phone}</td>
                  <td>{candidate.altPhones}</td>
                  <td>{candidate.dob ? <TextFormat type="date" value={candidate.dob} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{candidate.nationality}</td>
                  <td>{candidate.workAuthorizationId}</td>
                  <td>{candidate.address}</td>
                  <td>{candidate.area}</td>
                  <td>{candidate.city}</td>
                  <td>{candidate.state}</td>
                  <td>{candidate.stateCode}</td>
                  <td>{candidate.county}</td>
                  <td>{candidate.country}</td>
                  <td>{candidate.countryCode}</td>
                  <td>{candidate.zipCode}</td>
                  <td>{candidate.source}</td>
                  <td>{candidate.totalExpInYears}</td>
                  <td>{candidate.totalExpInMonths}</td>
                  <td>{candidate.relevantExpInYears}</td>
                  <td>{candidate.relevantExpInMonths}</td>
                  <td>{candidate.referredBy}</td>
                  <td>{candidate.ownershipUserId}</td>
                  <td>{candidate.currentJobTitle}</td>
                  <td>{candidate.currentEmployer}</td>
                  <td>{candidate.currentJobTypeId}</td>
                  <td>{candidate.currentPayCurrency}</td>
                  <td>{candidate.currentPay}</td>
                  <td>{candidate.currentPayMonthly}</td>
                  <td>{candidate.currentPayHourly}</td>
                  <td>{candidate.currentPayYearly}</td>
                  <td>
                    <Translate contentKey={`rproductApp.SalaryTimeSpan.${candidate.currentPayTimeSpan}`} />
                  </td>
                  <td>{candidate.otherCurrentBenefits}</td>
                  <td>{candidate.expectedPayCurrency}</td>
                  <td>{candidate.expectedPayMin}</td>
                  <td>{candidate.expectedPayMax}</td>
                  <td>{candidate.expectedPayMinMonthly}</td>
                  <td>{candidate.expectedPayMinHourly}</td>
                  <td>{candidate.expectedPayMinYearly}</td>
                  <td>{candidate.expectedPayMaxMonthly}</td>
                  <td>{candidate.expectedPayMaxHourly}</td>
                  <td>{candidate.expectedPayMaxYearly}</td>
                  <td>
                    <Translate contentKey={`rproductApp.SalaryTimeSpan.${candidate.expectedPayTimeSpan}`} />
                  </td>
                  <td>{candidate.expectedPayTaxTermId}</td>
                  <td>{candidate.otherExpectedBenefits}</td>
                  <td>{candidate.additionalComments}</td>
                  <td>{candidate.relocation ? 'true' : 'false'}</td>
                  <td>{candidate.familyWillingToRelocate ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`rproductApp.CandidateRelocationType.${candidate.relocationType}`} />
                  </td>
                  <td>{candidate.relocationRemarks}</td>
                  <td>{candidate.taxTermIds}</td>
                  <td>{candidate.noticePeriodInDays}</td>
                  <td>
                    {candidate.workAuthorizationExpiry ? (
                      <TextFormat type="date" value={candidate.workAuthorizationExpiry} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{candidate.gpa}</td>
                  <td>{candidate.aadharNumber}</td>
                  <td>{candidate.linkedInProfileURL}</td>
                  <td>{candidate.otherSocialURLs}</td>
                  <td>{candidate.tags}</td>
                  <td>{candidate.resumes}</td>
                  <td>{candidate.rightToReperesent}</td>
                  <td>{candidate.skills}</td>
                  <td>{candidate.altSkills}</td>
                  <td>{candidate.technologies}</td>
                  <td>{candidate.certifications}</td>
                  <td>{candidate.languages}</td>
                  <td>{candidate.workExperience}</td>
                  <td>{candidate.education}</td>
                  <td>{candidate.employerDetails}</td>
                  <td>{candidate.documents}</td>
                  <td>
                    {candidate.workAuthorization ? (
                      <Link to={`/catalogue-value/${candidate.workAuthorization.id}`}>{candidate.workAuthorization.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {candidate.ownershipUser ? <Link to={`/r-user/${candidate.ownershipUser.id}`}>{candidate.ownershipUser.id}</Link> : ''}
                  </td>
                  <td>
                    {candidate.currentJobType ? (
                      <Link to={`/catalogue-value/${candidate.currentJobType.id}`}>{candidate.currentJobType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {candidate.expectedPayTaxTerm ? (
                      <Link to={`/catalogue-value/${candidate.expectedPayTaxTerm.id}`}>{candidate.expectedPayTaxTerm.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/candidate/${candidate.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/candidate/${candidate.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/candidate/${candidate.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rproductApp.candidate.home.notFound">No Candidates found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={candidateList && candidateList.length > 0 ? '' : 'd-none'}>
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

export default Candidate;
