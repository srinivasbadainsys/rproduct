import dayjs from 'dayjs';
import { IWorkspace } from 'app/shared/model/workspace.model';

export interface IWorkspaceUser {
  id?: number;
  userId?: number | null;
  invitationKey?: string | null;
  owns?: boolean | null;
  accepted?: boolean | null;
  invited?: boolean | null;
  requested?: boolean | null;
  barred?: boolean | null;
  roles?: string | null;
  requestedOn?: string | null;
  workspace?: IWorkspace | null;
}

export const defaultValue: Readonly<IWorkspaceUser> = {
  owns: false,
  accepted: false,
  invited: false,
  requested: false,
  barred: false,
};
