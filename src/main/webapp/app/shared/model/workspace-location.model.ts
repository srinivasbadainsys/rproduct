import { IWorkspace } from 'app/shared/model/workspace.model';

export interface IWorkspaceLocation {
  id?: number;
  area?: string | null;
  city?: string | null;
  state?: string | null;
  stateCode?: string | null;
  country?: string | null;
  countryCode?: string | null;
  zipCode?: string | null;
  lat?: number | null;
  lon?: number | null;
  continent?: string | null;
  continentCode?: string | null;
  point?: string | null;
  workspace?: IWorkspace | null;
}

export const defaultValue: Readonly<IWorkspaceLocation> = {};
