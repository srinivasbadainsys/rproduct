package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobCustomAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobCustomAttribute.class);
        JobCustomAttribute jobCustomAttribute1 = new JobCustomAttribute();
        jobCustomAttribute1.setId(1L);
        JobCustomAttribute jobCustomAttribute2 = new JobCustomAttribute();
        jobCustomAttribute2.setId(jobCustomAttribute1.getId());
        assertThat(jobCustomAttribute1).isEqualTo(jobCustomAttribute2);
        jobCustomAttribute2.setId(2L);
        assertThat(jobCustomAttribute1).isNotEqualTo(jobCustomAttribute2);
        jobCustomAttribute1.setId(null);
        assertThat(jobCustomAttribute1).isNotEqualTo(jobCustomAttribute2);
    }
}
