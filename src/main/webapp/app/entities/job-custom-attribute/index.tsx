import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobCustomAttribute from './job-custom-attribute';
import JobCustomAttributeDetail from './job-custom-attribute-detail';
import JobCustomAttributeUpdate from './job-custom-attribute-update';
import JobCustomAttributeDeleteDialog from './job-custom-attribute-delete-dialog';

const JobCustomAttributeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobCustomAttribute />} />
    <Route path="new" element={<JobCustomAttributeUpdate />} />
    <Route path=":id">
      <Route index element={<JobCustomAttributeDetail />} />
      <Route path="edit" element={<JobCustomAttributeUpdate />} />
      <Route path="delete" element={<JobCustomAttributeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobCustomAttributeRoutes;
