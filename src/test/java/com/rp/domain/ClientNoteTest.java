package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientNoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientNote.class);
        ClientNote clientNote1 = new ClientNote();
        clientNote1.setId(1L);
        ClientNote clientNote2 = new ClientNote();
        clientNote2.setId(clientNote1.getId());
        assertThat(clientNote1).isEqualTo(clientNote2);
        clientNote2.setId(2L);
        assertThat(clientNote1).isNotEqualTo(clientNote2);
        clientNote1.setId(null);
        assertThat(clientNote1).isNotEqualTo(clientNote2);
    }
}
