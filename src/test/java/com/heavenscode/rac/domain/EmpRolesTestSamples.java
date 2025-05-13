package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EmpRolesTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EmpRoles getEmpRolesSample1() {
        return new EmpRoles().roleId(1).roleName("roleName1");
    }

    public static EmpRoles getEmpRolesSample2() {
        return new EmpRoles().roleId(2).roleName("roleName2");
    }

    public static EmpRoles getEmpRolesRandomSampleGenerator() {
        return new EmpRoles().roleId(intCount.incrementAndGet()).roleName(UUID.randomUUID().toString());
    }
}
