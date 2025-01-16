package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymenttermAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPaymenttermAllPropertiesEquals(Paymentterm expected, Paymentterm actual) {
        assertPaymenttermAutoGeneratedPropertiesEquals(expected, actual);
        assertPaymenttermAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPaymenttermAllUpdatablePropertiesEquals(Paymentterm expected, Paymentterm actual) {
        assertPaymenttermUpdatableFieldsEquals(expected, actual);
        assertPaymenttermUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPaymenttermAutoGeneratedPropertiesEquals(Paymentterm expected, Paymentterm actual) {
        assertThat(expected)
            .as("Verify Paymentterm auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPaymenttermUpdatableFieldsEquals(Paymentterm expected, Paymentterm actual) {
        assertThat(expected)
            .as("Verify Paymentterm relevant properties")
            .satisfies(e -> assertThat(e.getPaymenttype()).as("check paymenttype").isEqualTo(actual.getPaymenttype()))
            .satisfies(e -> assertThat(e.getForvoucher()).as("check forvoucher").isEqualTo(actual.getForvoucher()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPaymenttermUpdatableRelationshipsEquals(Paymentterm expected, Paymentterm actual) {}
}