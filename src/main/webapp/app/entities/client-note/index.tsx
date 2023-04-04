import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClientNote from './client-note';
import ClientNoteDetail from './client-note-detail';
import ClientNoteUpdate from './client-note-update';
import ClientNoteDeleteDialog from './client-note-delete-dialog';

const ClientNoteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClientNote />} />
    <Route path="new" element={<ClientNoteUpdate />} />
    <Route path=":id">
      <Route index element={<ClientNoteDetail />} />
      <Route path="edit" element={<ClientNoteUpdate />} />
      <Route path="delete" element={<ClientNoteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClientNoteRoutes;
