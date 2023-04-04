package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobBoardPostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobBoardPost.class);
        JobBoardPost jobBoardPost1 = new JobBoardPost();
        jobBoardPost1.setId(1L);
        JobBoardPost jobBoardPost2 = new JobBoardPost();
        jobBoardPost2.setId(jobBoardPost1.getId());
        assertThat(jobBoardPost1).isEqualTo(jobBoardPost2);
        jobBoardPost2.setId(2L);
        assertThat(jobBoardPost1).isNotEqualTo(jobBoardPost2);
        jobBoardPost1.setId(null);
        assertThat(jobBoardPost1).isNotEqualTo(jobBoardPost2);
    }
}
