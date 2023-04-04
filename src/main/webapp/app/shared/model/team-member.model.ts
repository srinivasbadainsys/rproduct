import { IRuser } from 'app/shared/model/ruser.model';
import { ITeam } from 'app/shared/model/team.model';

export interface ITeamMember {
  id?: number;
  teamId?: number | null;
  memberUser?: IRuser | null;
  team?: ITeam | null;
}

export const defaultValue: Readonly<ITeamMember> = {};
