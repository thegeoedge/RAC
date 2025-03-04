package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ServicecategoryAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServicecategoryAllPropertiesEquals(Servicecategory expected, Servicecategory actual) {
        assertServicecategoryAutoGeneratedPropertiesEquals(expected, actual);
        assertServicecategoryAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServicecategoryAllUpdatablePropertiesEquals(Servicecategory expected, Servicecategory actual) {
        assertServicecategoryUpdatableFieldsEquals(expected, actual);
        assertServicecategoryUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServicecategoryAutoGeneratedPropertiesEquals(Servicecategory expected, Servicecategory actual) {
        assertThat(expected)
            .as("Verify Servicecategory auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServicecategoryUpdatableFieldsEquals(Servicecategory expected, Servicecategory actual) {
        assertThat(expected)
            .as("Verify Servicecategory relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getLmu()).as("check lmu").isEqualTo(actual.getLmu()))
            .satisfies(e -> assertThat(e.getLmd()).as("check lmd").isEqualTo(actual.getLmd()))
            .satisfies(e -> assertThat(e.getShowsecurity()).as("check showsecurity").isEqualTo(actual.getShowsecurity()))
            .satisfies(e -> assertThat(e.getSortorder()).as("check sortorder").isEqualTo(actual.getSortorder()))
            .satisfies(e -> assertThat(e.getIsactive()).as("check isactive").isEqualTo(actual.getIsactive()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServicecategoryUpdatableRelationshipsEquals(Servicecategory expected, Servicecategory actual) {
        // empty method
    }
}
