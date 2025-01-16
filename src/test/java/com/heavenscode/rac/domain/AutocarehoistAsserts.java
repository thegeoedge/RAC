package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AutocarehoistAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarehoistAllPropertiesEquals(Autocarehoist expected, Autocarehoist actual) {
        assertAutocarehoistAutoGeneratedPropertiesEquals(expected, actual);
        assertAutocarehoistAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarehoistAllUpdatablePropertiesEquals(Autocarehoist expected, Autocarehoist actual) {
        assertAutocarehoistUpdatableFieldsEquals(expected, actual);
        assertAutocarehoistUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarehoistAutoGeneratedPropertiesEquals(Autocarehoist expected, Autocarehoist actual) {
        assertThat(expected)
            .as("Verify Autocarehoist auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarehoistUpdatableFieldsEquals(Autocarehoist expected, Autocarehoist actual) {
        assertThat(expected)
            .as("Verify Autocarehoist relevant properties")
            .satisfies(e -> assertThat(e.getHoistname()).as("check hoistname").isEqualTo(actual.getHoistname()))
            .satisfies(e -> assertThat(e.getHoistcode()).as("check hoistcode").isEqualTo(actual.getHoistcode()))
            .satisfies(e -> assertThat(e.getHoisttypeid()).as("check hoisttypeid").isEqualTo(actual.getHoisttypeid()))
            .satisfies(e -> assertThat(e.getHoisttypename()).as("check hoisttypename").isEqualTo(actual.getHoisttypename()))
            .satisfies(e -> assertThat(e.getLmu()).as("check lmu").isEqualTo(actual.getLmu()))
            .satisfies(e -> assertThat(e.getLmd()).as("check lmd").isEqualTo(actual.getLmd()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutocarehoistUpdatableRelationshipsEquals(Autocarehoist expected, Autocarehoist actual) {}
}
