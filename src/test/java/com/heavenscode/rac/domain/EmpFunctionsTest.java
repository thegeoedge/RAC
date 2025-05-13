package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.EmpFunctionsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpFunctionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpFunctions.class);
        EmpFunctions empFunctions1 = getEmpFunctionsSample1();
        EmpFunctions empFunctions2 = new EmpFunctions();
        assertThat(empFunctions1).isNotEqualTo(empFunctions2);

        empFunctions2.setFunctionId(empFunctions1.getFunctionId());
        assertThat(empFunctions1).isEqualTo(empFunctions2);

        empFunctions2 = getEmpFunctionsSample2();
        assertThat(empFunctions1).isNotEqualTo(empFunctions2);
    }
}
