export interface ICatalogue {
  id?: number;
  type?: string | null;
  typeCode?: string | null;
}

export const defaultValue: Readonly<ICatalogue> = {};
