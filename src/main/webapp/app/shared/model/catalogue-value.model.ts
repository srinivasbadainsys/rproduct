export interface ICatalogueValue {
  id?: number;
  catalogueId?: number | null;
  value?: string | null;
}

export const defaultValue: Readonly<ICatalogueValue> = {};
