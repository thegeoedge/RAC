package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SalesInvoiceLineBatchTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SalesInvoiceLineBatch getSalesInvoiceLineBatchSample1() {
        return new SalesInvoiceLineBatch()
            .id(1L)
            .lineId(1L)
            .batchLineId(1L)
            .itemId(1L)
            .code("code1")
            .batchId(1L)
            .batchCode("batchCode1")
            .notes("notes1")
            .lmu(1L)
            .addedById(1L);
    }

    public static SalesInvoiceLineBatch getSalesInvoiceLineBatchSample2() {
        return new SalesInvoiceLineBatch()
            .id(2L)
            .lineId(2L)
            .batchLineId(2L)
            .itemId(2L)
            .code("code2")
            .batchId(2L)
            .batchCode("batchCode2")
            .notes("notes2")
            .lmu(2L)
            .addedById(2L);
    }

    public static SalesInvoiceLineBatch getSalesInvoiceLineBatchRandomSampleGenerator() {
        return new SalesInvoiceLineBatch()
            .id(longCount.incrementAndGet())
            .lineId(longCount.incrementAndGet())
            .batchLineId(longCount.incrementAndGet())
            .itemId(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .batchId(longCount.incrementAndGet())
            .batchCode(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .lmu(longCount.incrementAndGet())
            .addedById(longCount.incrementAndGet());
    }
}
