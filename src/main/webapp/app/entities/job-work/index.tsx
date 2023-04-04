import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobWork from './job-work';
import JobWorkDetail from './job-work-detail';
import JobWorkUpdate from './job-work-update';
import JobWorkDeleteDialog from './job-work-delete-dialog';

const JobWorkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobWork />} />
    <Route path="new" element={<JobWorkUpdate />} />
    <Route path=":id">
      <Route index element={<JobWorkDetail />} />
      <Route path="edit" element={<JobWorkUpdate />} />
      <Route path="delete" element={<JobWorkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobWorkRoutes;
