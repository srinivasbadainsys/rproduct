import { IClient } from 'app/shared/model/client.model';

export interface IClientNote {
  id?: number;
  clientId?: number | null;
  action?: string | null;
  notesPriority?: string | null;
  note?: string | null;
  notifyToUsers?: string | null;
  remindMe?: boolean | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IClientNote> = {
  remindMe: false,
};
