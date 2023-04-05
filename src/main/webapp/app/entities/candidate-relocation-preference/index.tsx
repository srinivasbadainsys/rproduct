import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CandidateRelocationPreference from './candidate-relocation-preference';
import CandidateRelocationPreferenceDetail from './candidate-relocation-preference-detail';
import CandidateRelocationPreferenceUpdate from './candidate-relocation-preference-update';
import CandidateRelocationPreferenceDeleteDialog from './candidate-relocation-preference-delete-dialog';

const CandidateRelocationPreferenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CandidateRelocationPreference />} />
    <Route path="new" element={<CandidateRelocationPreferenceUpdate />} />
    <Route path=":id">
      <Route index element={<CandidateRelocationPreferenceDetail />} />
      <Route path="edit" element={<CandidateRelocationPreferenceUpdate />} />
      <Route path="delete" element={<CandidateRelocationPreferenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CandidateRelocationPreferenceRoutes;
