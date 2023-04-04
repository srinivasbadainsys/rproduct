import { IClient } from 'app/shared/model/client.model';

export interface IClientAccount {
  id?: number;
  clientId?: number | null;
  contactPerson?: string | null;
  mobileNumber?: string | null;
  officeNumber?: string | null;
  officeNumberExtn?: string | null;
  emailID?: string | null;
  designation?: string | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IClientAccount> = {};
