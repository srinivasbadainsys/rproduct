package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientGuidelineSubmissionDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientGuidelineSubmissionDocument.class);
        ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument1 = new ClientGuidelineSubmissionDocument();
        clientGuidelineSubmissionDocument1.setId(1L);
        ClientGuidelineSubmissionDocument clientGuidelineSubmissionDocument2 = new ClientGuidelineSubmissionDocument();
        clientGuidelineSubmissionDocument2.setId(clientGuidelineSubmissionDocument1.getId());
        assertThat(clientGuidelineSubmissionDocument1).isEqualTo(clientGuidelineSubmissionDocument2);
        clientGuidelineSubmissionDocument2.setId(2L);
        assertThat(clientGuidelineSubmissionDocument1).isNotEqualTo(clientGuidelineSubmissionDocument2);
        clientGuidelineSubmissionDocument1.setId(null);
        assertThat(clientGuidelineSubmissionDocument1).isNotEqualTo(clientGuidelineSubmissionDocument2);
    }
}
