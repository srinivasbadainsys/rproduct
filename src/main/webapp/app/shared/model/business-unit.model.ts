export interface IBusinessUnit {
  id?: number;
  unitName?: string | null;
  address?: string | null;
  mobileContact?: string | null;
  officeContact?: string | null;
  officeContactExtn?: string | null;
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
}

export const defaultValue: Readonly<IBusinessUnit> = {};
