package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobWorkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobWork.class);
        JobWork jobWork1 = new JobWork();
        jobWork1.setId(1L);
        JobWork jobWork2 = new JobWork();
        jobWork2.setId(jobWork1.getId());
        assertThat(jobWork1).isEqualTo(jobWork2);
        jobWork2.setId(2L);
        assertThat(jobWork1).isNotEqualTo(jobWork2);
        jobWork1.setId(null);
        assertThat(jobWork1).isNotEqualTo(jobWork2);
    }
}
