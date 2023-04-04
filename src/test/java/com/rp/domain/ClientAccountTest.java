package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientAccount.class);
        ClientAccount clientAccount1 = new ClientAccount();
        clientAccount1.setId(1L);
        ClientAccount clientAccount2 = new ClientAccount();
        clientAccount2.setId(clientAccount1.getId());
        assertThat(clientAccount1).isEqualTo(clientAccount2);
        clientAccount2.setId(2L);
        assertThat(clientAccount1).isNotEqualTo(clientAccount2);
        clientAccount1.setId(null);
        assertThat(clientAccount1).isNotEqualTo(clientAccount2);
    }
}
