import { IClient } from 'app/shared/model/client.model';

export interface IClientGuidelineSubmissionDocument {
  id?: number;
  clientId?: number | null;
  documentTitle?: string | null;
  description?: string | null;
  documentPath?: string | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IClientGuidelineSubmissionDocument> = {};
