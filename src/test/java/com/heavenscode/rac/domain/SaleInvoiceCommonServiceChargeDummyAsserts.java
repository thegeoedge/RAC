package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SaleInvoiceCommonServiceChargeDummyAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSaleInvoiceCommonServiceChargeDummyAllPropertiesEquals(
        SaleInvoiceCommonServiceChargeDummy expected,
        SaleInvoiceCommonServiceChargeDummy actual
    ) {
        assertSaleInvoiceCommonServiceChargeDummyAutoGeneratedPropertiesEquals(expected, actual);
        assertSaleInvoiceCommonServiceChargeDummyAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSaleInvoiceCommonServiceChargeDummyAllUpdatablePropertiesEquals(
        SaleInvoiceCommonServiceChargeDummy expected,
        SaleInvoiceCommonServiceChargeDummy actual
    ) {
        assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(expected, actual);
        assertSaleInvoiceCommonServiceChargeDummyUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSaleInvoiceCommonServiceChargeDummyAutoGeneratedPropertiesEquals(
        SaleInvoiceCommonServiceChargeDummy expected,
        SaleInvoiceCommonServiceChargeDummy actual
    ) {
        assertThat(expected)
            .as("Verify SaleInvoiceCommonServiceChargeDummy auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(
        SaleInvoiceCommonServiceChargeDummy expected,
        SaleInvoiceCommonServiceChargeDummy actual
    ) {
        assertThat(expected)
            .as("Verify SaleInvoiceCommonServiceChargeDummy relevant properties")
            .satisfies(e -> assertThat(e.getInvoiceid()).as("check invoiceid").isEqualTo(actual.getInvoiceid()))
            .satisfies(e -> assertThat(e.getLineid()).as("check lineid").isEqualTo(actual.getLineid()))
            .satisfies(e -> assertThat(e.getOptionid()).as("check optionid").isEqualTo(actual.getOptionid()))
            .satisfies(e -> assertThat(e.getMainid()).as("check mainid").isEqualTo(actual.getMainid()))
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getValue()).as("check value").isEqualTo(actual.getValue()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSaleInvoiceCommonServiceChargeDummyUpdatableRelationshipsEquals(
        SaleInvoiceCommonServiceChargeDummy expected,
        SaleInvoiceCommonServiceChargeDummy actual
    ) {
        // empty method
    }
}
