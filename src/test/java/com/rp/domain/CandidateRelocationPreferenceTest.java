package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidateRelocationPreferenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateRelocationPreference.class);
        CandidateRelocationPreference candidateRelocationPreference1 = new CandidateRelocationPreference();
        candidateRelocationPreference1.setId(1L);
        CandidateRelocationPreference candidateRelocationPreference2 = new CandidateRelocationPreference();
        candidateRelocationPreference2.setId(candidateRelocationPreference1.getId());
        assertThat(candidateRelocationPreference1).isEqualTo(candidateRelocationPreference2);
        candidateRelocationPreference2.setId(2L);
        assertThat(candidateRelocationPreference1).isNotEqualTo(candidateRelocationPreference2);
        candidateRelocationPreference1.setId(null);
        assertThat(candidateRelocationPreference1).isNotEqualTo(candidateRelocationPreference2);
    }
}
