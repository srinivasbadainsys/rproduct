package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamWorkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamWork.class);
        TeamWork teamWork1 = new TeamWork();
        teamWork1.setId(1L);
        TeamWork teamWork2 = new TeamWork();
        teamWork2.setId(teamWork1.getId());
        assertThat(teamWork1).isEqualTo(teamWork2);
        teamWork2.setId(2L);
        assertThat(teamWork1).isNotEqualTo(teamWork2);
        teamWork1.setId(null);
        assertThat(teamWork1).isNotEqualTo(teamWork2);
    }
}
