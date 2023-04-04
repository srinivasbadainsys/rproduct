import ruser from 'app/entities/ruser/ruser.reducer';
import workspace from 'app/entities/workspace/workspace.reducer';
import workspaceLocation from 'app/entities/workspace-location/workspace-location.reducer';
import businessUnit from 'app/entities/business-unit/business-unit.reducer';
import workspaceUser from 'app/entities/workspace-user/workspace-user.reducer';
import teamMember from 'app/entities/team-member/team-member.reducer';
import team from 'app/entities/team/team.reducer';
import job from 'app/entities/job/job.reducer';
import jobLocation from 'app/entities/job-location/job-location.reducer';
import jobDocument from 'app/entities/job-document/job-document.reducer';
import jobWork from 'app/entities/job-work/job-work.reducer';
import userWork from 'app/entities/user-work/user-work.reducer';
import teamWork from 'app/entities/team-work/team-work.reducer';
import jobBoard from 'app/entities/job-board/job-board.reducer';
import jobBoardSharedTo from 'app/entities/job-board-shared-to/job-board-shared-to.reducer';
import jobBoardPost from 'app/entities/job-board-post/job-board-post.reducer';
import jobCustomAttribute from 'app/entities/job-custom-attribute/job-custom-attribute.reducer';
import client from 'app/entities/client/client.reducer';
import clientAccount from 'app/entities/client-account/client-account.reducer';
import clientNote from 'app/entities/client-note/client-note.reducer';
import clientDocument from 'app/entities/client-document/client-document.reducer';
import clientGuidelineSubmissionDocument from 'app/entities/client-guideline-submission-document/client-guideline-submission-document.reducer';
import contact from 'app/entities/contact/contact.reducer';
import catalogue from 'app/entities/catalogue/catalogue.reducer';
import catalogueValue from 'app/entities/catalogue-value/catalogue-value.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  ruser,
  workspace,
  workspaceLocation,
  businessUnit,
  workspaceUser,
  teamMember,
  team,
  job,
  jobLocation,
  jobDocument,
  jobWork,
  userWork,
  teamWork,
  jobBoard,
  jobBoardSharedTo,
  jobBoardPost,
  jobCustomAttribute,
  client,
  clientAccount,
  clientNote,
  clientDocument,
  clientGuidelineSubmissionDocument,
  contact,
  catalogue,
  catalogueValue,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
