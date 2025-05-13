package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AutoCareVehicleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AutoCareVehicle getAutoCareVehicleSample1() {
        return new AutoCareVehicle()
            .id(1L)
            .customerId(1)
            .customerName("customerName1")
            .customerTel("customerTel1")
            .vehicleNumber("vehicleNumber1")
            .brandId(1)
            .brandName("brandName1")
            .model("model1")
            .manufactureYear("manufactureYear1")
            .description("description1")
            .lmu(1);
    }

    public static AutoCareVehicle getAutoCareVehicleSample2() {
        return new AutoCareVehicle()
            .id(2L)
            .customerId(2)
            .customerName("customerName2")
            .customerTel("customerTel2")
            .vehicleNumber("vehicleNumber2")
            .brandId(2)
            .brandName("brandName2")
            .model("model2")
            .manufactureYear("manufactureYear2")
            .description("description2")
            .lmu(2);
    }

    public static AutoCareVehicle getAutoCareVehicleRandomSampleGenerator() {
        return new AutoCareVehicle()
            .id(longCount.incrementAndGet())
            .customerId(intCount.incrementAndGet())
            .customerName(UUID.randomUUID().toString())
            .customerTel(UUID.randomUUID().toString())
            .vehicleNumber(UUID.randomUUID().toString())
            .brandId(intCount.incrementAndGet())
            .brandName(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .manufactureYear(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet());
    }
}
