import { IJob } from 'app/shared/model/job.model';

export interface IJobLocation {
  id?: number;
  jobId?: number | null;
  area?: string | null;
  city?: string | null;
  state?: string | null;
  stateCode?: string | null;
  country?: string | null;
  countryCode?: string | null;
  zipCode?: string | null;
  lat?: number | null;
  lon?: number | null;
  continent?: string | null;
  continentCode?: string | null;
  point?: string | null;
  job?: IJob | null;
}

export const defaultValue: Readonly<IJobLocation> = {};
