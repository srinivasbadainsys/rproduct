export interface ITeamWork {
  id?: number;
  jobId?: number | null;
  teamId?: number | null;
}

export const defaultValue: Readonly<ITeamWork> = {};
