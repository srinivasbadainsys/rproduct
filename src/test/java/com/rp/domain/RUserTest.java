package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RUser.class);
        RUser rUser1 = new RUser();
        rUser1.setId(1L);
        RUser rUser2 = new RUser();
        rUser2.setId(rUser1.getId());
        assertThat(rUser1).isEqualTo(rUser2);
        rUser2.setId(2L);
        assertThat(rUser1).isNotEqualTo(rUser2);
        rUser1.setId(null);
        assertThat(rUser1).isNotEqualTo(rUser2);
    }
}
