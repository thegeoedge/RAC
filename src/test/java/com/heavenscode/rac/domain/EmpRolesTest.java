package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.EmpRolesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpRolesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpRoles.class);
        EmpRoles empRoles1 = getEmpRolesSample1();
        EmpRoles empRoles2 = new EmpRoles();
        assertThat(empRoles1).isNotEqualTo(empRoles2);

        empRoles2.setRoleId(empRoles1.getRoleId());
        assertThat(empRoles1).isEqualTo(empRoles2);

        empRoles2 = getEmpRolesSample2();
        assertThat(empRoles1).isNotEqualTo(empRoles2);
    }
}
