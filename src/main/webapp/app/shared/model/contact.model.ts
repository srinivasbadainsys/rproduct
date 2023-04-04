import { IRuser } from 'app/shared/model/ruser.model';
import { IClient } from 'app/shared/model/client.model';

export interface IContact {
  id?: number;
  clientId?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  designation?: string | null;
  officeNumber?: string | null;
  officeNumberExtn?: string | null;
  mobileNumber?: string | null;
  emailID?: string | null;
  altEmailID?: string | null;
  ownershipIds?: string | null;
  allowAccessToAllUsers?: boolean | null;
  address1?: string | null;
  address2?: string | null;
  area?: string | null;
  city?: string | null;
  state?: string | null;
  stateCode?: string | null;
  county?: string | null;
  country?: string | null;
  countryCode?: string | null;
  zipCode?: string | null;
  profileURLs?: string | null;
  messengerIDs?: string | null;
  status?: string | null;
  clientGroup?: string | null;
  about?: string | null;
  primaryOwnerUser?: IRuser | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IContact> = {
  allowAccessToAllUsers: false,
};
