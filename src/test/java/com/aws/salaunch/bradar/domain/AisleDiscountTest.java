package com.aws.salaunch.bradar.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.aws.salaunch.bradar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AisleDiscountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AisleDiscount.class);
        AisleDiscount aisleDiscount1 = new AisleDiscount();
        aisleDiscount1.setId(1L);
        AisleDiscount aisleDiscount2 = new AisleDiscount();
        aisleDiscount2.setId(aisleDiscount1.getId());
        assertThat(aisleDiscount1).isEqualTo(aisleDiscount2);
        aisleDiscount2.setId(2L);
        assertThat(aisleDiscount1).isNotEqualTo(aisleDiscount2);
        aisleDiscount1.setId(null);
        assertThat(aisleDiscount1).isNotEqualTo(aisleDiscount2);
    }
}
