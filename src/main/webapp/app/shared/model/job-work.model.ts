export interface IJobWork {
  id?: number;
  jobId?: number | null;
  assignedToTeams?: string | null;
  assignedToUsers?: string | null;
}

export const defaultValue: Readonly<IJobWork> = {};
