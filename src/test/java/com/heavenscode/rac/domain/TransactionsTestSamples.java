package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Transactions getTransactionsSample1() {
        return new Transactions()
            .id(1L)
            .accountId(1)
            .accountCode("accountCode1")
            .refDoc("refDoc1")
            .refId(1)
            .source("source1")
            .paymentTermId(1)
            .paymentTermName("paymentTermName1")
            .lmu(1);
    }

    public static Transactions getTransactionsSample2() {
        return new Transactions()
            .id(2L)
            .accountId(2)
            .accountCode("accountCode2")
            .refDoc("refDoc2")
            .refId(2)
            .source("source2")
            .paymentTermId(2)
            .paymentTermName("paymentTermName2")
            .lmu(2);
    }

    public static Transactions getTransactionsRandomSampleGenerator() {
        return new Transactions()
            .id(longCount.incrementAndGet())
            .accountId(intCount.incrementAndGet())
            .accountCode(UUID.randomUUID().toString())
            .refDoc(UUID.randomUUID().toString())
            .refId(intCount.incrementAndGet())
            .source(UUID.randomUUID().toString())
            .paymentTermId(intCount.incrementAndGet())
            .paymentTermName(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet());
    }
}
