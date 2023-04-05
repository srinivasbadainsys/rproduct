import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Candidate from './candidate';
import CandidateRelocationPreference from './candidate-relocation-preference';
import CandidatePipeline from './candidate-pipeline';
import CatalogueValue from './catalogue-value';
import Job from './job';
import RUser from './r-user';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="candidate/*" element={<Candidate />} />
        <Route path="candidate-relocation-preference/*" element={<CandidateRelocationPreference />} />
        <Route path="candidate-pipeline/*" element={<CandidatePipeline />} />
        <Route path="catalogue-value/*" element={<CatalogueValue />} />
        <Route path="job/*" element={<Job />} />
        <Route path="r-user/*" element={<RUser />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
