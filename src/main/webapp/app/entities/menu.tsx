import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/candidate">
        <Translate contentKey="global.menu.entities.candidate" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/candidate-relocation-preference">
        <Translate contentKey="global.menu.entities.candidateRelocationPreference" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/candidate-pipeline">
        <Translate contentKey="global.menu.entities.candidatePipeline" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/catalogue-value">
        <Translate contentKey="global.menu.entities.catalogueValue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job">
        <Translate contentKey="global.menu.entities.job" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/r-user">
        <Translate contentKey="global.menu.entities.rUser" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
