package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EmpFunctionsTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EmpFunctions getEmpFunctionsSample1() {
        return new EmpFunctions().functionId(1).functionName("functionName1").moduleId(1);
    }

    public static EmpFunctions getEmpFunctionsSample2() {
        return new EmpFunctions().functionId(2).functionName("functionName2").moduleId(2);
    }

    public static EmpFunctions getEmpFunctionsRandomSampleGenerator() {
        return new EmpFunctions()
            .functionId(intCount.incrementAndGet())
            .functionName(UUID.randomUUID().toString())
            .moduleId(intCount.incrementAndGet());
    }
}
