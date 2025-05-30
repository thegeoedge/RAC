package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoCareVehicleAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutoCareVehicleAllPropertiesEquals(AutoCareVehicle expected, AutoCareVehicle actual) {
        assertAutoCareVehicleAutoGeneratedPropertiesEquals(expected, actual);
        assertAutoCareVehicleAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutoCareVehicleAllUpdatablePropertiesEquals(AutoCareVehicle expected, AutoCareVehicle actual) {
        assertAutoCareVehicleUpdatableFieldsEquals(expected, actual);
        assertAutoCareVehicleUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutoCareVehicleAutoGeneratedPropertiesEquals(AutoCareVehicle expected, AutoCareVehicle actual) {
        assertThat(actual)
            .as("Verify AutoCareVehicle auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutoCareVehicleUpdatableFieldsEquals(AutoCareVehicle expected, AutoCareVehicle actual) {
        assertThat(actual)
            .as("Verify AutoCareVehicle relevant properties")
            .satisfies(a -> assertThat(a.getCustomerId()).as("check customerId").isEqualTo(expected.getCustomerId()))
            .satisfies(a -> assertThat(a.getCustomerName()).as("check customerName").isEqualTo(expected.getCustomerName()))
            .satisfies(a -> assertThat(a.getCustomerTel()).as("check customerTel").isEqualTo(expected.getCustomerTel()))
            .satisfies(a -> assertThat(a.getVehicleNumber()).as("check vehicleNumber").isEqualTo(expected.getVehicleNumber()))
            .satisfies(a -> assertThat(a.getBrandId()).as("check brandId").isEqualTo(expected.getBrandId()))
            .satisfies(a -> assertThat(a.getBrandName()).as("check brandName").isEqualTo(expected.getBrandName()))
            .satisfies(a -> assertThat(a.getModel()).as("check model").isEqualTo(expected.getModel()))
            .satisfies(a -> assertThat(a.getMillage()).as("check millage").isEqualTo(expected.getMillage()))
            .satisfies(a -> assertThat(a.getManufactureYear()).as("check manufactureYear").isEqualTo(expected.getManufactureYear()))
            .satisfies(a -> assertThat(a.getLastServiceDate()).as("check lastServiceDate").isEqualTo(expected.getLastServiceDate()))
            .satisfies(a -> assertThat(a.getNextServiceDate()).as("check nextServiceDate").isEqualTo(expected.getNextServiceDate()))
            .satisfies(a -> assertThat(a.getDescription()).as("check description").isEqualTo(expected.getDescription()))
            .satisfies(a -> assertThat(a.getLmu()).as("check lmu").isEqualTo(expected.getLmu()))
            .satisfies(a -> assertThat(a.getLmd()).as("check lmd").isEqualTo(expected.getLmd()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutoCareVehicleUpdatableRelationshipsEquals(AutoCareVehicle expected, AutoCareVehicle actual) {
        // empty method
    }
}
