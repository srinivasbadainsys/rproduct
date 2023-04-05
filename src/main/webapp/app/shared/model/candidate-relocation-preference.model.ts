import { ICandidate } from 'app/shared/model/candidate.model';

export interface ICandidateRelocationPreference {
  id?: number;
  candidateId?: number | null;
  city?: string | null;
  state?: string | null;
  stateCode?: string | null;
  county?: string | null;
  country?: string | null;
  countryCode?: string | null;
  zipCode?: string | null;
  candidateId?: ICandidate | null;
}

export const defaultValue: Readonly<ICandidateRelocationPreference> = {};
