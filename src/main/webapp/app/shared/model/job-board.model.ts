export interface IJobBoard {
  id?: number;
  jobBoardName?: string | null;
  jobBoardType?: string | null;
  username?: string | null;
  password?: string | null;
  settings?: string | null;
  autoRefresh?: string | null;
  jobDuration?: string | null;
}

export const defaultValue: Readonly<IJobBoard> = {};
