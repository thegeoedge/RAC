package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceServiceChargeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInvoiceServiceChargeAllPropertiesEquals(InvoiceServiceCharge expected, InvoiceServiceCharge actual) {
        assertInvoiceServiceChargeAutoGeneratedPropertiesEquals(expected, actual);
        assertInvoiceServiceChargeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInvoiceServiceChargeAllUpdatablePropertiesEquals(InvoiceServiceCharge expected, InvoiceServiceCharge actual) {
        assertInvoiceServiceChargeUpdatableFieldsEquals(expected, actual);
        assertInvoiceServiceChargeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInvoiceServiceChargeAutoGeneratedPropertiesEquals(InvoiceServiceCharge expected, InvoiceServiceCharge actual) {
        assertThat(expected)
            .as("Verify InvoiceServiceCharge auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInvoiceServiceChargeUpdatableFieldsEquals(InvoiceServiceCharge expected, InvoiceServiceCharge actual) {
        assertThat(expected)
            .as("Verify InvoiceServiceCharge relevant properties")
            .satisfies(e -> assertThat(e.getInvoiceId()).as("check invoiceId").isEqualTo(actual.getInvoiceId()))
            .satisfies(e -> assertThat(e.getLineId()).as("check lineId").isEqualTo(actual.getLineId()))
            .satisfies(e -> assertThat(e.getOptionId()).as("check optionId").isEqualTo(actual.getOptionId()))
            .satisfies(e -> assertThat(e.getServiceName()).as("check serviceName").isEqualTo(actual.getServiceName()))
            .satisfies(e -> assertThat(e.getServiceDiscription()).as("check serviceDiscription").isEqualTo(actual.getServiceDiscription()))
            .satisfies(e -> assertThat(e.getValue()).as("check value").isEqualTo(actual.getValue()))
            .satisfies(e -> assertThat(e.getAddedById()).as("check addedById").isEqualTo(actual.getAddedById()))
            .satisfies(e -> assertThat(e.getIsCustomerSrvice()).as("check isCustomerSrvice").isEqualTo(actual.getIsCustomerSrvice()))
            .satisfies(e -> assertThat(e.getDiscount()).as("check discount").isEqualTo(actual.getDiscount()))
            .satisfies(e -> assertThat(e.getServicePrice()).as("check servicePrice").isEqualTo(actual.getServicePrice()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInvoiceServiceChargeUpdatableRelationshipsEquals(InvoiceServiceCharge expected, InvoiceServiceCharge actual) {
        // empty method
    }
}
