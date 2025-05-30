package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionsAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransactionsAllPropertiesEquals(Transactions expected, Transactions actual) {
        assertTransactionsAutoGeneratedPropertiesEquals(expected, actual);
        assertTransactionsAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransactionsAllUpdatablePropertiesEquals(Transactions expected, Transactions actual) {
        assertTransactionsUpdatableFieldsEquals(expected, actual);
        assertTransactionsUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransactionsAutoGeneratedPropertiesEquals(Transactions expected, Transactions actual) {
        assertThat(actual)
            .as("Verify Transactions auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransactionsUpdatableFieldsEquals(Transactions expected, Transactions actual) {
        assertThat(actual)
            .as("Verify Transactions relevant properties")
            .satisfies(a -> assertThat(a.getAccountId()).as("check accountId").isEqualTo(expected.getAccountId()))
            .satisfies(a -> assertThat(a.getAccountCode()).as("check accountCode").isEqualTo(expected.getAccountCode()))
            .satisfies(a -> assertThat(a.getDebit()).as("check debit").isEqualTo(expected.getDebit()))
            .satisfies(a -> assertThat(a.getCredit()).as("check credit").isEqualTo(expected.getCredit()))
            .satisfies(a -> assertThat(a.getDate()).as("check date").isEqualTo(expected.getDate()))
            .satisfies(a -> assertThat(a.getRefDoc()).as("check refDoc").isEqualTo(expected.getRefDoc()))
            .satisfies(a -> assertThat(a.getRefId()).as("check refId").isEqualTo(expected.getRefId()))
            .satisfies(a -> assertThat(a.getSubId()).as("check subId").isEqualTo(expected.getSubId()))
            .satisfies(a -> assertThat(a.getSource()).as("check source").isEqualTo(expected.getSource()))
            .satisfies(a -> assertThat(a.getPaymentTermId()).as("check paymentTermId").isEqualTo(expected.getPaymentTermId()))
            .satisfies(a -> assertThat(a.getPaymentTermName()).as("check paymentTermName").isEqualTo(expected.getPaymentTermName()))
            .satisfies(a -> assertThat(a.getLmu()).as("check lmu").isEqualTo(expected.getLmu()))
            .satisfies(a -> assertThat(a.getLmd()).as("check lmd").isEqualTo(expected.getLmd()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransactionsUpdatableRelationshipsEquals(Transactions expected, Transactions actual) {
        // empty method
    }
}
