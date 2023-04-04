import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobDocument from './job-document';
import JobDocumentDetail from './job-document-detail';
import JobDocumentUpdate from './job-document-update';
import JobDocumentDeleteDialog from './job-document-delete-dialog';

const JobDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobDocument />} />
    <Route path="new" element={<JobDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<JobDocumentDetail />} />
      <Route path="edit" element={<JobDocumentUpdate />} />
      <Route path="delete" element={<JobDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobDocumentRoutes;
