import candidate from 'app/entities/candidate/candidate.reducer';
import candidateRelocationPreference from 'app/entities/candidate-relocation-preference/candidate-relocation-preference.reducer';
import candidatePipeline from 'app/entities/candidate-pipeline/candidate-pipeline.reducer';
import catalogueValue from 'app/entities/catalogue-value/catalogue-value.reducer';
import job from 'app/entities/job/job.reducer';
import rUser from 'app/entities/r-user/r-user.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  candidate,
  candidateRelocationPreference,
  candidatePipeline,
  catalogueValue,
  job,
  rUser,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
