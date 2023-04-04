package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkspaceUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkspaceUser.class);
        WorkspaceUser workspaceUser1 = new WorkspaceUser();
        workspaceUser1.setId(1L);
        WorkspaceUser workspaceUser2 = new WorkspaceUser();
        workspaceUser2.setId(workspaceUser1.getId());
        assertThat(workspaceUser1).isEqualTo(workspaceUser2);
        workspaceUser2.setId(2L);
        assertThat(workspaceUser1).isNotEqualTo(workspaceUser2);
        workspaceUser1.setId(null);
        assertThat(workspaceUser1).isNotEqualTo(workspaceUser2);
    }
}
