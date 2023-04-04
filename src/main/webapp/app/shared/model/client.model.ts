import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { ICatalogueValue } from 'app/shared/model/catalogue-value.model';
import { IRuser } from 'app/shared/model/ruser.model';
import { IClientAccount } from 'app/shared/model/client-account.model';
import { IClientNote } from 'app/shared/model/client-note.model';
import { IClientDocument } from 'app/shared/model/client-document.model';
import { IContact } from 'app/shared/model/contact.model';
import { IClientGuidelineSubmissionDocument } from 'app/shared/model/client-guideline-submission-document.model';
import { ClientVisibility } from 'app/shared/model/enumerations/client-visibility.model';

export interface IClient {
  id?: number;
  clientVisibility?: ClientVisibility | null;
  businessUnitIds?: string | null;
  clientName?: string | null;
  vmsClientName?: string | null;
  federalID?: string | null;
  contactNumber?: string | null;
  email?: string | null;
  address?: string | null;
  area?: string | null;
  city?: string | null;
  state?: string | null;
  stateCode?: string | null;
  county?: string | null;
  country?: string | null;
  countryCode?: string | null;
  zipCode?: string | null;
  website?: string | null;
  sendRequirement?: boolean | null;
  sendHotList?: boolean | null;
  fax?: string | null;
  status?: string | null;
  allowAccessToAllUsers?: boolean | null;
  ownershipIds?: string | null;
  clientLeadIds?: string | null;
  requiredDocuments?: string | null;
  practiceAlt?: string | null;
  aboutCompany?: string | null;
  stopNotifyingClientContactOnSubmitToClient?: boolean | null;
  defaultForJobPostings?: boolean | null;
  submissionGuidelines?: string | null;
  assignedTo?: string | null;
  assignedToTeams?: string | null;
  salesManagers?: string | null;
  accountManagers?: string | null;
  recruitmentManagers?: string | null;
  defaultTATConfigForJobPostingOrVMSJobs?: number | null;
  defaultTATConfigTimespan?: string | null;
  notifyOnTATToUserTypes?: string | null;
  notifyOnTATToUserIds?: string | null;
  taxTermIds?: string | null;
  workAuthorizationIds?: string | null;
  postJobOnCareersPage?: boolean | null;
  defaultPayRateTaxTerm?: string | null;
  defaultBillRateTaxTerm?: string | null;
  referencesMandatoryForSubmission?: boolean | null;
  maxSubmissions?: number | null;
  noOfPositions?: number | null;
  markUp?: string | null;
  msp?: string | null;
  mailSubject?: string | null;
  mailBody?: string | null;
  fieldsForExcel?: string | null;
  primaryBusinessUnit?: IBusinessUnit | null;
  industry?: ICatalogueValue | null;
  category?: ICatalogueValue | null;
  paymentTerms?: ICatalogueValue | null;
  practice?: ICatalogueValue | null;
  primaryOwnerUser?: IRuser | null;
  clientAccounts?: IClientAccount[] | null;
  clientNotes?: IClientNote[] | null;
  clientDocuments?: IClientDocument[] | null;
  contacts?: IContact[] | null;
  clientGuidelineSubmissionDocuments?: IClientGuidelineSubmissionDocument[] | null;
}

export const defaultValue: Readonly<IClient> = {
  sendRequirement: false,
  sendHotList: false,
  allowAccessToAllUsers: false,
  stopNotifyingClientContactOnSubmitToClient: false,
  defaultForJobPostings: false,
  postJobOnCareersPage: false,
  referencesMandatoryForSubmission: false,
};
