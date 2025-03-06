package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SystemSettingsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SystemSettings getSystemSettingsSample1() {
        return new SystemSettings().id(1L).key("key1").lastValue("lastValue1").nextValue("nextValue1").lmu(1);
    }

    public static SystemSettings getSystemSettingsSample2() {
        return new SystemSettings().id(2L).key("key2").lastValue("lastValue2").nextValue("nextValue2").lmu(2);
    }

    public static SystemSettings getSystemSettingsRandomSampleGenerator() {
        return new SystemSettings()
            .id(longCount.incrementAndGet())
            .key(UUID.randomUUID().toString())
            .lastValue(UUID.randomUUID().toString())
            .nextValue(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet());
    }
}
