import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ruser from './ruser';
import Workspace from './workspace';
import WorkspaceLocation from './workspace-location';
import BusinessUnit from './business-unit';
import WorkspaceUser from './workspace-user';
import TeamMember from './team-member';
import Team from './team';
import Job from './job';
import JobLocation from './job-location';
import JobDocument from './job-document';
import JobWork from './job-work';
import UserWork from './user-work';
import TeamWork from './team-work';
import JobBoard from './job-board';
import JobBoardSharedTo from './job-board-shared-to';
import JobBoardPost from './job-board-post';
import JobCustomAttribute from './job-custom-attribute';
import Client from './client';
import ClientAccount from './client-account';
import ClientNote from './client-note';
import ClientDocument from './client-document';
import ClientGuidelineSubmissionDocument from './client-guideline-submission-document';
import Contact from './contact';
import Catalogue from './catalogue';
import CatalogueValue from './catalogue-value';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="ruser/*" element={<Ruser />} />
        <Route path="workspace/*" element={<Workspace />} />
        <Route path="workspace-location/*" element={<WorkspaceLocation />} />
        <Route path="business-unit/*" element={<BusinessUnit />} />
        <Route path="workspace-user/*" element={<WorkspaceUser />} />
        <Route path="team-member/*" element={<TeamMember />} />
        <Route path="team/*" element={<Team />} />
        <Route path="job/*" element={<Job />} />
        <Route path="job-location/*" element={<JobLocation />} />
        <Route path="job-document/*" element={<JobDocument />} />
        <Route path="job-work/*" element={<JobWork />} />
        <Route path="user-work/*" element={<UserWork />} />
        <Route path="team-work/*" element={<TeamWork />} />
        <Route path="job-board/*" element={<JobBoard />} />
        <Route path="job-board-shared-to/*" element={<JobBoardSharedTo />} />
        <Route path="job-board-post/*" element={<JobBoardPost />} />
        <Route path="job-custom-attribute/*" element={<JobCustomAttribute />} />
        <Route path="client/*" element={<Client />} />
        <Route path="client-account/*" element={<ClientAccount />} />
        <Route path="client-note/*" element={<ClientNote />} />
        <Route path="client-document/*" element={<ClientDocument />} />
        <Route path="client-guideline-submission-document/*" element={<ClientGuidelineSubmissionDocument />} />
        <Route path="contact/*" element={<Contact />} />
        <Route path="catalogue/*" element={<Catalogue />} />
        <Route path="catalogue-value/*" element={<CatalogueValue />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
