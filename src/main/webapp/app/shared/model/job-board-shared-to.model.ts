import dayjs from 'dayjs';
import { IJobBoard } from 'app/shared/model/job-board.model';

export interface IJobBoardSharedTo {
  id?: number;
  jobBoardId?: number | null;
  sharedToEmails?: string | null;
  sharedToUserIds?: string | null;
  sharedToTeamIds?: string | null;
  maxExamilsMonthly?: number | null;
  expiresOn?: string | null;
  jobBoard?: IJobBoard | null;
}

export const defaultValue: Readonly<IJobBoardSharedTo> = {};
