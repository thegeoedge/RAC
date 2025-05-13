package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BinCardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static BinCard getBinCardSample1() {
        return new BinCard()
            .id(1L)
            .itemID(1L)
            .itemCode("itemCode1")
            .reference("reference1")
            .lMU(1)
            .referenceCode("referenceCode1")
            .batchId(1L)
            .locationID(1L)
            .locationCode("locationCode1")
            .description("description1")
            .referenceDoc("referenceDoc1");
    }

    public static BinCard getBinCardSample2() {
        return new BinCard()
            .id(2L)
            .itemID(2L)
            .itemCode("itemCode2")
            .reference("reference2")
            .lMU(2)
            .referenceCode("referenceCode2")
            .batchId(2L)
            .locationID(2L)
            .locationCode("locationCode2")
            .description("description2")
            .referenceDoc("referenceDoc2");
    }

    public static BinCard getBinCardRandomSampleGenerator() {
        return new BinCard()
            .id(longCount.incrementAndGet())
            .itemID(longCount.incrementAndGet())
            .itemCode(UUID.randomUUID().toString())
            .reference(UUID.randomUUID().toString())
            .lMU(intCount.incrementAndGet())
            .referenceCode(UUID.randomUUID().toString())
            .batchId(longCount.incrementAndGet())
            .locationID(longCount.incrementAndGet())
            .locationCode(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .referenceDoc(UUID.randomUUID().toString());
    }
}
