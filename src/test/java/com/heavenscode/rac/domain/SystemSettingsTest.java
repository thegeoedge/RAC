package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SystemSettingsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SystemSettingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemSettings.class);
        SystemSettings systemSettings1 = getSystemSettingsSample1();
        SystemSettings systemSettings2 = new SystemSettings();
        assertThat(systemSettings1).isNotEqualTo(systemSettings2);

        systemSettings2.setId(systemSettings1.getId());
        assertThat(systemSettings1).isEqualTo(systemSettings2);

        systemSettings2 = getSystemSettingsSample2();
        assertThat(systemSettings1).isNotEqualTo(systemSettings2);
    }
}
