import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TeamWork from './team-work';
import TeamWorkDetail from './team-work-detail';
import TeamWorkUpdate from './team-work-update';
import TeamWorkDeleteDialog from './team-work-delete-dialog';

const TeamWorkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TeamWork />} />
    <Route path="new" element={<TeamWorkUpdate />} />
    <Route path=":id">
      <Route index element={<TeamWorkDetail />} />
      <Route path="edit" element={<TeamWorkUpdate />} />
      <Route path="delete" element={<TeamWorkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TeamWorkRoutes;
