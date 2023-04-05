import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CatalogueValue from './catalogue-value';
import CatalogueValueDetail from './catalogue-value-detail';
import CatalogueValueUpdate from './catalogue-value-update';
import CatalogueValueDeleteDialog from './catalogue-value-delete-dialog';

const CatalogueValueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CatalogueValue />} />
    <Route path="new" element={<CatalogueValueUpdate />} />
    <Route path=":id">
      <Route index element={<CatalogueValueDetail />} />
      <Route path="edit" element={<CatalogueValueUpdate />} />
      <Route path="delete" element={<CatalogueValueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CatalogueValueRoutes;
