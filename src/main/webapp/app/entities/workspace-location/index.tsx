import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WorkspaceLocation from './workspace-location';
import WorkspaceLocationDetail from './workspace-location-detail';
import WorkspaceLocationUpdate from './workspace-location-update';
import WorkspaceLocationDeleteDialog from './workspace-location-delete-dialog';

const WorkspaceLocationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WorkspaceLocation />} />
    <Route path="new" element={<WorkspaceLocationUpdate />} />
    <Route path=":id">
      <Route index element={<WorkspaceLocationDetail />} />
      <Route path="edit" element={<WorkspaceLocationUpdate />} />
      <Route path="delete" element={<WorkspaceLocationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WorkspaceLocationRoutes;
