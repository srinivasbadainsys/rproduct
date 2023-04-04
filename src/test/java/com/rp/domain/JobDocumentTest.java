package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobDocument.class);
        JobDocument jobDocument1 = new JobDocument();
        jobDocument1.setId(1L);
        JobDocument jobDocument2 = new JobDocument();
        jobDocument2.setId(jobDocument1.getId());
        assertThat(jobDocument1).isEqualTo(jobDocument2);
        jobDocument2.setId(2L);
        assertThat(jobDocument1).isNotEqualTo(jobDocument2);
        jobDocument1.setId(null);
        assertThat(jobDocument1).isNotEqualTo(jobDocument2);
    }
}
