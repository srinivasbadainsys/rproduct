import { IJob } from 'app/shared/model/job.model';
import { IJobBoard } from 'app/shared/model/job-board.model';
import { JobBoardPostStatus } from 'app/shared/model/enumerations/job-board-post-status.model';

export interface IJobBoardPost {
  id?: number;
  jobId?: number | null;
  status?: JobBoardPostStatus | null;
  job?: IJob | null;
  jobBoard?: IJobBoard | null;
}

export const defaultValue: Readonly<IJobBoardPost> = {};
