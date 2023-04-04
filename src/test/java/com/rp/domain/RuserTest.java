package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RuserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ruser.class);
        Ruser ruser1 = new Ruser();
        ruser1.setId(1L);
        Ruser ruser2 = new Ruser();
        ruser2.setId(ruser1.getId());
        assertThat(ruser1).isEqualTo(ruser2);
        ruser2.setId(2L);
        assertThat(ruser1).isNotEqualTo(ruser2);
        ruser1.setId(null);
        assertThat(ruser1).isNotEqualTo(ruser2);
    }
}
