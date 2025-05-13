package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.AutoCareVehicleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutoCareVehicleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoCareVehicle.class);
        AutoCareVehicle autoCareVehicle1 = getAutoCareVehicleSample1();
        AutoCareVehicle autoCareVehicle2 = new AutoCareVehicle();
        assertThat(autoCareVehicle1).isNotEqualTo(autoCareVehicle2);

        autoCareVehicle2.setId(autoCareVehicle1.getId());
        assertThat(autoCareVehicle1).isEqualTo(autoCareVehicle2);

        autoCareVehicle2 = getAutoCareVehicleSample2();
        assertThat(autoCareVehicle1).isNotEqualTo(autoCareVehicle2);
    }
}
