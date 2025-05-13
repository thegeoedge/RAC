package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class EmpRoleFunctionPermissionTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EmpRoleFunctionPermission getEmpRoleFunctionPermissionSample1() {
        return new EmpRoleFunctionPermission().id(1).roleId(1).functionId(1);
    }

    public static EmpRoleFunctionPermission getEmpRoleFunctionPermissionSample2() {
        return new EmpRoleFunctionPermission().id(2).roleId(2).functionId(2);
    }

    public static EmpRoleFunctionPermission getEmpRoleFunctionPermissionRandomSampleGenerator() {
        return new EmpRoleFunctionPermission()
            .id(intCount.incrementAndGet())
            .roleId(intCount.incrementAndGet())
            .functionId(intCount.incrementAndGet());
    }
}
