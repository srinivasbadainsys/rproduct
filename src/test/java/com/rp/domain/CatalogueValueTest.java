package com.rp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CatalogueValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatalogueValue.class);
        CatalogueValue catalogueValue1 = new CatalogueValue();
        catalogueValue1.setId(1L);
        CatalogueValue catalogueValue2 = new CatalogueValue();
        catalogueValue2.setId(catalogueValue1.getId());
        assertThat(catalogueValue1).isEqualTo(catalogueValue2);
        catalogueValue2.setId(2L);
        assertThat(catalogueValue1).isNotEqualTo(catalogueValue2);
        catalogueValue1.setId(null);
        assertThat(catalogueValue1).isNotEqualTo(catalogueValue2);
    }
}
