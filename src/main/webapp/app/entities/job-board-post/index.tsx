import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobBoardPost from './job-board-post';
import JobBoardPostDetail from './job-board-post-detail';
import JobBoardPostUpdate from './job-board-post-update';
import JobBoardPostDeleteDialog from './job-board-post-delete-dialog';

const JobBoardPostRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobBoardPost />} />
    <Route path="new" element={<JobBoardPostUpdate />} />
    <Route path=":id">
      <Route index element={<JobBoardPostDetail />} />
      <Route path="edit" element={<JobBoardPostUpdate />} />
      <Route path="delete" element={<JobBoardPostDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobBoardPostRoutes;
