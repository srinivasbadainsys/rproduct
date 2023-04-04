import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ruser from './ruser';
import RuserDetail from './ruser-detail';
import RuserUpdate from './ruser-update';
import RuserDeleteDialog from './ruser-delete-dialog';

const RuserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ruser />} />
    <Route path="new" element={<RuserUpdate />} />
    <Route path=":id">
      <Route index element={<RuserDetail />} />
      <Route path="edit" element={<RuserUpdate />} />
      <Route path="delete" element={<RuserDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RuserRoutes;
