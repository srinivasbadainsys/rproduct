import { IJob } from 'app/shared/model/job.model';
import { JobAttributeType } from 'app/shared/model/enumerations/job-attribute-type.model';

export interface IJobCustomAttribute {
  id?: number;
  jobId?: number | null;
  attributeName?: string | null;
  attributeType?: JobAttributeType | null;
  attributeValue?: string | null;
  job?: IJob | null;
}

export const defaultValue: Readonly<IJobCustomAttribute> = {};
