package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobBoardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobBoard.class);
        JobBoard jobBoard1 = new JobBoard();
        jobBoard1.setId(1L);
        JobBoard jobBoard2 = new JobBoard();
        jobBoard2.setId(jobBoard1.getId());
        assertThat(jobBoard1).isEqualTo(jobBoard2);
        jobBoard2.setId(2L);
        assertThat(jobBoard1).isNotEqualTo(jobBoard2);
        jobBoard1.setId(null);
        assertThat(jobBoard1).isNotEqualTo(jobBoard2);
    }
}
