import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WorkspaceUser from './workspace-user';
import WorkspaceUserDetail from './workspace-user-detail';
import WorkspaceUserUpdate from './workspace-user-update';
import WorkspaceUserDeleteDialog from './workspace-user-delete-dialog';

const WorkspaceUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WorkspaceUser />} />
    <Route path="new" element={<WorkspaceUserUpdate />} />
    <Route path=":id">
      <Route index element={<WorkspaceUserDetail />} />
      <Route path="edit" element={<WorkspaceUserUpdate />} />
      <Route path="delete" element={<WorkspaceUserDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WorkspaceUserRoutes;
