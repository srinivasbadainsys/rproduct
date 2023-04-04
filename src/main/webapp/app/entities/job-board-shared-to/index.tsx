import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobBoardSharedTo from './job-board-shared-to';
import JobBoardSharedToDetail from './job-board-shared-to-detail';
import JobBoardSharedToUpdate from './job-board-shared-to-update';
import JobBoardSharedToDeleteDialog from './job-board-shared-to-delete-dialog';

const JobBoardSharedToRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobBoardSharedTo />} />
    <Route path="new" element={<JobBoardSharedToUpdate />} />
    <Route path=":id">
      <Route index element={<JobBoardSharedToDetail />} />
      <Route path="edit" element={<JobBoardSharedToUpdate />} />
      <Route path="delete" element={<JobBoardSharedToDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobBoardSharedToRoutes;
