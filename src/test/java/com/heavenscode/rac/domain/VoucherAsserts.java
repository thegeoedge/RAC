package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVoucherAllPropertiesEquals(Voucher expected, Voucher actual) {
        assertVoucherAutoGeneratedPropertiesEquals(expected, actual);
        assertVoucherAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVoucherAllUpdatablePropertiesEquals(Voucher expected, Voucher actual) {
        assertVoucherUpdatableFieldsEquals(expected, actual);
        assertVoucherUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVoucherAutoGeneratedPropertiesEquals(Voucher expected, Voucher actual) {
        assertThat(expected)
            .as("Verify Voucher auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVoucherUpdatableFieldsEquals(Voucher expected, Voucher actual) {
        assertThat(expected)
            .as("Verify Voucher relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getVoucherDate()).as("check voucherDate").isEqualTo(actual.getVoucherDate()))
            .satisfies(e -> assertThat(e.getSupplierName()).as("check supplierName").isEqualTo(actual.getSupplierName()))
            .satisfies(e -> assertThat(e.getSupplierAddress()).as("check supplierAddress").isEqualTo(actual.getSupplierAddress()))
            .satisfies(e -> assertThat(e.getTotalAmount()).as("check totalAmount").isEqualTo(actual.getTotalAmount()))
            .satisfies(e -> assertThat(e.getTotalAmountInWord()).as("check totalAmountInWord").isEqualTo(actual.getTotalAmountInWord()))
            .satisfies(e -> assertThat(e.getComments()).as("check comments").isEqualTo(actual.getComments()))
            .satisfies(e -> assertThat(e.getLmu()).as("check lmu").isEqualTo(actual.getLmu()))
            .satisfies(e -> assertThat(e.getLmd()).as("check lmd").isEqualTo(actual.getLmd()))
            .satisfies(e -> assertThat(e.getTermId()).as("check termId").isEqualTo(actual.getTermId()))
            .satisfies(e -> assertThat(e.getTerm()).as("check term").isEqualTo(actual.getTerm()))
            .satisfies(e -> assertThat(e.getDate()).as("check date").isEqualTo(actual.getDate()))
            .satisfies(e -> assertThat(e.getAmountPaid()).as("check amountPaid").isEqualTo(actual.getAmountPaid()))
            .satisfies(e -> assertThat(e.getSupplierID()).as("check supplierID").isEqualTo(actual.getSupplierID()))
            .satisfies(e -> assertThat(e.getIsActive()).as("check isActive").isEqualTo(actual.getIsActive()))
            .satisfies(e -> assertThat(e.getCreatedBy()).as("check createdBy").isEqualTo(actual.getCreatedBy()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertVoucherUpdatableRelationshipsEquals(Voucher expected, Voucher actual) {
        // empty method
    }
}
