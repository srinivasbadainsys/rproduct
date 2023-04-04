import { IRuser } from 'app/shared/model/ruser.model';
import { ITeam } from 'app/shared/model/team.model';
import { IJob } from 'app/shared/model/job.model';

export interface IUserWork {
  id?: number;
  jobId?: number | null;
  user?: IRuser | null;
  team?: ITeam | null;
  job?: IJob | null;
}

export const defaultValue: Readonly<IUserWork> = {};
