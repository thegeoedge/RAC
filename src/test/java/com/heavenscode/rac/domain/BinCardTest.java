package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.BinCardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BinCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BinCard.class);
        BinCard binCard1 = getBinCardSample1();
        BinCard binCard2 = new BinCard();
        assertThat(binCard1).isNotEqualTo(binCard2);

        binCard2.setId(binCard1.getId());
        assertThat(binCard1).isEqualTo(binCard2);

        binCard2 = getBinCardSample2();
        assertThat(binCard1).isNotEqualTo(binCard2);
    }
}
