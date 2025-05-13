package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.EmpRoleFunctionPermissionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpRoleFunctionPermissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpRoleFunctionPermission.class);
        EmpRoleFunctionPermission empRoleFunctionPermission1 = getEmpRoleFunctionPermissionSample1();
        EmpRoleFunctionPermission empRoleFunctionPermission2 = new EmpRoleFunctionPermission();
        assertThat(empRoleFunctionPermission1).isNotEqualTo(empRoleFunctionPermission2);

        empRoleFunctionPermission2.setId(empRoleFunctionPermission1.getId());
        assertThat(empRoleFunctionPermission1).isEqualTo(empRoleFunctionPermission2);

        empRoleFunctionPermission2 = getEmpRoleFunctionPermissionSample2();
        assertThat(empRoleFunctionPermission1).isNotEqualTo(empRoleFunctionPermission2);
    }
}
