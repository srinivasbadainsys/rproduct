import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CandidatePipeline from './candidate-pipeline';
import CandidatePipelineDetail from './candidate-pipeline-detail';
import CandidatePipelineUpdate from './candidate-pipeline-update';
import CandidatePipelineDeleteDialog from './candidate-pipeline-delete-dialog';

const CandidatePipelineRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CandidatePipeline />} />
    <Route path="new" element={<CandidatePipelineUpdate />} />
    <Route path=":id">
      <Route index element={<CandidatePipelineDetail />} />
      <Route path="edit" element={<CandidatePipelineUpdate />} />
      <Route path="delete" element={<CandidatePipelineDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CandidatePipelineRoutes;
