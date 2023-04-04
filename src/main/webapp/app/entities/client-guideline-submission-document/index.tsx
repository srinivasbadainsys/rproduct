import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClientGuidelineSubmissionDocument from './client-guideline-submission-document';
import ClientGuidelineSubmissionDocumentDetail from './client-guideline-submission-document-detail';
import ClientGuidelineSubmissionDocumentUpdate from './client-guideline-submission-document-update';
import ClientGuidelineSubmissionDocumentDeleteDialog from './client-guideline-submission-document-delete-dialog';

const ClientGuidelineSubmissionDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClientGuidelineSubmissionDocument />} />
    <Route path="new" element={<ClientGuidelineSubmissionDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<ClientGuidelineSubmissionDocumentDetail />} />
      <Route path="edit" element={<ClientGuidelineSubmissionDocumentUpdate />} />
      <Route path="delete" element={<ClientGuidelineSubmissionDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClientGuidelineSubmissionDocumentRoutes;
