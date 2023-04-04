import { ITeamMember } from 'app/shared/model/team-member.model';
import { IWorkspace } from 'app/shared/model/workspace.model';
import { TeamType } from 'app/shared/model/enumerations/team-type.model';

export interface ITeam {
  id?: number;
  name?: string | null;
  teamGroupEmail?: string | null;
  type?: TeamType | null;
  notifyOnJobPostingToUsers?: string | null;
  notifyOnJobSharingToUsers?: string | null;
  notifyOnJobClosingToUsers?: string | null;
  notifyOnCandSubmissionToUsers?: string | null;
  notifyOnCandStatusChangeToUsers?: string | null;
  teamMembers?: ITeamMember[] | null;
  workspace?: IWorkspace | null;
}

export const defaultValue: Readonly<ITeam> = {};
