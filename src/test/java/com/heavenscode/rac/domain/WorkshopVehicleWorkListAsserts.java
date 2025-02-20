package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkshopVehicleWorkListAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkshopVehicleWorkListAllPropertiesEquals(WorkshopVehicleWorkList expected, WorkshopVehicleWorkList actual) {
        assertWorkshopVehicleWorkListAutoGeneratedPropertiesEquals(expected, actual);
        assertWorkshopVehicleWorkListAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkshopVehicleWorkListAllUpdatablePropertiesEquals(
        WorkshopVehicleWorkList expected,
        WorkshopVehicleWorkList actual
    ) {
        assertWorkshopVehicleWorkListUpdatableFieldsEquals(expected, actual);
        assertWorkshopVehicleWorkListUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkshopVehicleWorkListAutoGeneratedPropertiesEquals(
        WorkshopVehicleWorkList expected,
        WorkshopVehicleWorkList actual
    ) {
        assertThat(expected)
            .as("Verify WorkshopVehicleWorkList auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkshopVehicleWorkListUpdatableFieldsEquals(
        WorkshopVehicleWorkList expected,
        WorkshopVehicleWorkList actual
    ) {
        assertThat(expected)
            .as("Verify WorkshopVehicleWorkList relevant properties")
            .satisfies(e -> assertThat(e.getVehicleworkid()).as("check vehicleworkid").isEqualTo(actual.getVehicleworkid()))
            .satisfies(e -> assertThat(e.getLineid()).as("check lineid").isEqualTo(actual.getLineid()))
            .satisfies(e -> assertThat(e.getWorkid()).as("check workid").isEqualTo(actual.getWorkid()))
            .satisfies(e -> assertThat(e.getWorkshopwork()).as("check workshopwork").isEqualTo(actual.getWorkshopwork()))
            .satisfies(e -> assertThat(e.getIsjobdone()).as("check isjobdone").isEqualTo(actual.getIsjobdone()))
            .satisfies(e -> assertThat(e.getJobdonedate()).as("check jobdonedate").isEqualTo(actual.getJobdonedate()))
            .satisfies(e -> assertThat(e.getJobnumber()).as("check jobnumber").isEqualTo(actual.getJobnumber()))
            .satisfies(e -> assertThat(e.getJobvalue()).as("check jobvalue").isEqualTo(actual.getJobvalue()))
            .satisfies(e -> assertThat(e.getEstimatevalue()).as("check estimatevalue").isEqualTo(actual.getEstimatevalue()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkshopVehicleWorkListUpdatableRelationshipsEquals(
        WorkshopVehicleWorkList expected,
        WorkshopVehicleWorkList actual
    ) {
        // empty method
    }
}
