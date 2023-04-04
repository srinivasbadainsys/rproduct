import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobLocation from './job-location';
import JobLocationDetail from './job-location-detail';
import JobLocationUpdate from './job-location-update';
import JobLocationDeleteDialog from './job-location-delete-dialog';

const JobLocationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobLocation />} />
    <Route path="new" element={<JobLocationUpdate />} />
    <Route path=":id">
      <Route index element={<JobLocationDetail />} />
      <Route path="edit" element={<JobLocationUpdate />} />
      <Route path="delete" element={<JobLocationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobLocationRoutes;
