package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDocument.class);
        ClientDocument clientDocument1 = new ClientDocument();
        clientDocument1.setId(1L);
        ClientDocument clientDocument2 = new ClientDocument();
        clientDocument2.setId(clientDocument1.getId());
        assertThat(clientDocument1).isEqualTo(clientDocument2);
        clientDocument2.setId(2L);
        assertThat(clientDocument1).isNotEqualTo(clientDocument2);
        clientDocument1.setId(null);
        assertThat(clientDocument1).isNotEqualTo(clientDocument2);
    }
}
