import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobBoard from './job-board';
import JobBoardDetail from './job-board-detail';
import JobBoardUpdate from './job-board-update';
import JobBoardDeleteDialog from './job-board-delete-dialog';

const JobBoardRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobBoard />} />
    <Route path="new" element={<JobBoardUpdate />} />
    <Route path=":id">
      <Route index element={<JobBoardDetail />} />
      <Route path="edit" element={<JobBoardUpdate />} />
      <Route path="delete" element={<JobBoardDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobBoardRoutes;
