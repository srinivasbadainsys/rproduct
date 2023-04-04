package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobBoardSharedToTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobBoardSharedTo.class);
        JobBoardSharedTo jobBoardSharedTo1 = new JobBoardSharedTo();
        jobBoardSharedTo1.setId(1L);
        JobBoardSharedTo jobBoardSharedTo2 = new JobBoardSharedTo();
        jobBoardSharedTo2.setId(jobBoardSharedTo1.getId());
        assertThat(jobBoardSharedTo1).isEqualTo(jobBoardSharedTo2);
        jobBoardSharedTo2.setId(2L);
        assertThat(jobBoardSharedTo1).isNotEqualTo(jobBoardSharedTo2);
        jobBoardSharedTo1.setId(null);
        assertThat(jobBoardSharedTo1).isNotEqualTo(jobBoardSharedTo2);
    }
}
