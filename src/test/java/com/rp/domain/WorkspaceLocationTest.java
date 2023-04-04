package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkspaceLocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkspaceLocation.class);
        WorkspaceLocation workspaceLocation1 = new WorkspaceLocation();
        workspaceLocation1.setId(1L);
        WorkspaceLocation workspaceLocation2 = new WorkspaceLocation();
        workspaceLocation2.setId(workspaceLocation1.getId());
        assertThat(workspaceLocation1).isEqualTo(workspaceLocation2);
        workspaceLocation2.setId(2L);
        assertThat(workspaceLocation1).isNotEqualTo(workspaceLocation2);
        workspaceLocation1.setId(null);
        assertThat(workspaceLocation1).isNotEqualTo(workspaceLocation2);
    }
}
