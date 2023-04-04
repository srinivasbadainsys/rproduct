package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobLocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobLocation.class);
        JobLocation jobLocation1 = new JobLocation();
        jobLocation1.setId(1L);
        JobLocation jobLocation2 = new JobLocation();
        jobLocation2.setId(jobLocation1.getId());
        assertThat(jobLocation1).isEqualTo(jobLocation2);
        jobLocation2.setId(2L);
        assertThat(jobLocation1).isNotEqualTo(jobLocation2);
        jobLocation1.setId(null);
        assertThat(jobLocation1).isNotEqualTo(jobLocation2);
    }
}
