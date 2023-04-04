import { IJob } from 'app/shared/model/job.model';

export interface IJobDocument {
  id?: number;
  jobId?: number | null;
  documentTitle?: string | null;
  documentLocation?: string | null;
  documentPassword?: string | null;
  job?: IJob | null;
}

export const defaultValue: Readonly<IJobDocument> = {};
