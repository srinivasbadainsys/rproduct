import { ICatalogueValue } from 'app/shared/model/catalogue-value.model';
import { IJob } from 'app/shared/model/job.model';
import { CandidatePipelineType } from 'app/shared/model/enumerations/candidate-pipeline-type.model';

export interface ICandidatePipeline {
  id?: number;
  jobId?: number | null;
  statusId?: number | null;
  submissionStatus?: string | null;
  submissionStage?: string | null;
  recruiterActions?: string | null;
  candidateResponses?: string | null;
  pipelineType?: CandidatePipelineType | null;
  reasonForNewJob?: string | null;
  status?: ICatalogueValue | null;
  job?: IJob | null;
}

export const defaultValue: Readonly<ICandidatePipeline> = {};
