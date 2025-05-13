package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentMethodTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PaymentMethod getPaymentMethodSample1() {
        return new PaymentMethod().id(1L).paymentMethodName("paymentMethodName1").companyBankAccountId(1).lmu(1);
    }

    public static PaymentMethod getPaymentMethodSample2() {
        return new PaymentMethod().id(2L).paymentMethodName("paymentMethodName2").companyBankAccountId(2).lmu(2);
    }

    public static PaymentMethod getPaymentMethodRandomSampleGenerator() {
        return new PaymentMethod()
            .id(longCount.incrementAndGet())
            .paymentMethodName(UUID.randomUUID().toString())
            .companyBankAccountId(intCount.incrementAndGet())
            .lmu(intCount.incrementAndGet());
    }
}
