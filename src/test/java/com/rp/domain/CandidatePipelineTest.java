package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidatePipelineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatePipeline.class);
        CandidatePipeline candidatePipeline1 = new CandidatePipeline();
        candidatePipeline1.setId(1L);
        CandidatePipeline candidatePipeline2 = new CandidatePipeline();
        candidatePipeline2.setId(candidatePipeline1.getId());
        assertThat(candidatePipeline1).isEqualTo(candidatePipeline2);
        candidatePipeline2.setId(2L);
        assertThat(candidatePipeline1).isNotEqualTo(candidatePipeline2);
        candidatePipeline1.setId(null);
        assertThat(candidatePipeline1).isNotEqualTo(candidatePipeline2);
    }
}
