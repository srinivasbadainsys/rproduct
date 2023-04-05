import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RUser from './r-user';
import RUserDetail from './r-user-detail';
import RUserUpdate from './r-user-update';
import RUserDeleteDialog from './r-user-delete-dialog';

const RUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RUser />} />
    <Route path="new" element={<RUserUpdate />} />
    <Route path=":id">
      <Route index element={<RUserDetail />} />
      <Route path="edit" element={<RUserUpdate />} />
      <Route path="delete" element={<RUserDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RUserRoutes;
