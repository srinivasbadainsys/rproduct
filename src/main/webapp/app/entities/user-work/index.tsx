import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserWork from './user-work';
import UserWorkDetail from './user-work-detail';
import UserWorkUpdate from './user-work-update';
import UserWorkDeleteDialog from './user-work-delete-dialog';

const UserWorkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserWork />} />
    <Route path="new" element={<UserWorkUpdate />} />
    <Route path=":id">
      <Route index element={<UserWorkDetail />} />
      <Route path="edit" element={<UserWorkUpdate />} />
      <Route path="delete" element={<UserWorkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserWorkRoutes;
