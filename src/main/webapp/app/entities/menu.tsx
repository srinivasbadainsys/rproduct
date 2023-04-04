import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/ruser">
        <Translate contentKey="global.menu.entities.ruser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/workspace">
        <Translate contentKey="global.menu.entities.workspace" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/workspace-location">
        <Translate contentKey="global.menu.entities.workspaceLocation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/business-unit">
        <Translate contentKey="global.menu.entities.businessUnit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/workspace-user">
        <Translate contentKey="global.menu.entities.workspaceUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/team-member">
        <Translate contentKey="global.menu.entities.teamMember" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/team">
        <Translate contentKey="global.menu.entities.team" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job">
        <Translate contentKey="global.menu.entities.job" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-location">
        <Translate contentKey="global.menu.entities.jobLocation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-document">
        <Translate contentKey="global.menu.entities.jobDocument" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-work">
        <Translate contentKey="global.menu.entities.jobWork" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-work">
        <Translate contentKey="global.menu.entities.userWork" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/team-work">
        <Translate contentKey="global.menu.entities.teamWork" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-board">
        <Translate contentKey="global.menu.entities.jobBoard" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-board-shared-to">
        <Translate contentKey="global.menu.entities.jobBoardSharedTo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-board-post">
        <Translate contentKey="global.menu.entities.jobBoardPost" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-custom-attribute">
        <Translate contentKey="global.menu.entities.jobCustomAttribute" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client-account">
        <Translate contentKey="global.menu.entities.clientAccount" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client-note">
        <Translate contentKey="global.menu.entities.clientNote" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client-document">
        <Translate contentKey="global.menu.entities.clientDocument" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client-guideline-submission-document">
        <Translate contentKey="global.menu.entities.clientGuidelineSubmissionDocument" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/contact">
        <Translate contentKey="global.menu.entities.contact" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/catalogue">
        <Translate contentKey="global.menu.entities.catalogue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/catalogue-value">
        <Translate contentKey="global.menu.entities.catalogueValue" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
