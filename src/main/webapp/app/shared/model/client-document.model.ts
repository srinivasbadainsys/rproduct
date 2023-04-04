import { IClient } from 'app/shared/model/client.model';

export interface IClientDocument {
  id?: number;
  clientId?: number | null;
  documentType?: string | null;
  title?: string | null;
  description?: string | null;
  documentPath?: string | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IClientDocument> = {};
