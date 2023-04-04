import { IWorkspaceUser } from 'app/shared/model/workspace-user.model';
import { IWorkspaceLocation } from 'app/shared/model/workspace-location.model';
import { ITeam } from 'app/shared/model/team.model';

export interface IWorkspace {
  id?: number;
  name?: string | null;
  orgName?: string | null;
  about?: string | null;
  link?: string | null;
  orgURLs?: string | null;
  ownerUserId?: number | null;
  mainPhoneNumber?: string | null;
  altPhoneNumbers?: string | null;
  mainContactEmail?: string | null;
  altContactEmails?: string | null;
  status?: string | null;
  enableAutoJoin?: boolean | null;
  maxUsers?: number | null;
  tags?: string | null;
  domains?: string | null;
  workspaceUsers?: IWorkspaceUser[] | null;
  workspaceLocations?: IWorkspaceLocation[] | null;
  teams?: ITeam[] | null;
}

export const defaultValue: Readonly<IWorkspace> = {
  enableAutoJoin: false,
};
