import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClientDocument from './client-document';
import ClientDocumentDetail from './client-document-detail';
import ClientDocumentUpdate from './client-document-update';
import ClientDocumentDeleteDialog from './client-document-delete-dialog';

const ClientDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClientDocument />} />
    <Route path="new" element={<ClientDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<ClientDocumentDetail />} />
      <Route path="edit" element={<ClientDocumentUpdate />} />
      <Route path="delete" element={<ClientDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClientDocumentRoutes;
