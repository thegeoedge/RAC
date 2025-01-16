package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesorderAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSalesorderAllPropertiesEquals(Salesorder expected, Salesorder actual) {
        assertSalesorderAutoGeneratedPropertiesEquals(expected, actual);
        assertSalesorderAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSalesorderAllUpdatablePropertiesEquals(Salesorder expected, Salesorder actual) {
        assertSalesorderUpdatableFieldsEquals(expected, actual);
        assertSalesorderUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSalesorderAutoGeneratedPropertiesEquals(Salesorder expected, Salesorder actual) {
        assertThat(expected)
            .as("Verify Salesorder auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSalesorderUpdatableFieldsEquals(Salesorder expected, Salesorder actual) {
        assertThat(expected)
            .as("Verify Salesorder relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getOrderdate()).as("check orderdate").isEqualTo(actual.getOrderdate()))
            .satisfies(e -> assertThat(e.getCreateddate()).as("check createddate").isEqualTo(actual.getCreateddate()))
            .satisfies(e -> assertThat(e.getQuoteid()).as("check quoteid").isEqualTo(actual.getQuoteid()))
            .satisfies(e -> assertThat(e.getSalesrepid()).as("check salesrepid").isEqualTo(actual.getSalesrepid()))
            .satisfies(e -> assertThat(e.getSalesrepname()).as("check salesrepname").isEqualTo(actual.getSalesrepname()))
            .satisfies(e -> assertThat(e.getDelieverfrom()).as("check delieverfrom").isEqualTo(actual.getDelieverfrom()))
            .satisfies(e -> assertThat(e.getCustomerid()).as("check customerid").isEqualTo(actual.getCustomerid()))
            .satisfies(e -> assertThat(e.getCustomername()).as("check customername").isEqualTo(actual.getCustomername()))
            .satisfies(e -> assertThat(e.getCustomeraddress()).as("check customeraddress").isEqualTo(actual.getCustomeraddress()))
            .satisfies(e -> assertThat(e.getDeliveryaddress()).as("check deliveryaddress").isEqualTo(actual.getDeliveryaddress()))
            .satisfies(e -> assertThat(e.getSubtotal()).as("check subtotal").isEqualTo(actual.getSubtotal()))
            .satisfies(e -> assertThat(e.getTotaltax()).as("check totaltax").isEqualTo(actual.getTotaltax()))
            .satisfies(e -> assertThat(e.getTotaldiscount()).as("check totaldiscount").isEqualTo(actual.getTotaldiscount()))
            .satisfies(e -> assertThat(e.getNettotal()).as("check nettotal").isEqualTo(actual.getNettotal()))
            .satisfies(e -> assertThat(e.getMessage()).as("check message").isEqualTo(actual.getMessage()))
            .satisfies(e -> assertThat(e.getIsinvoiced()).as("check isinvoiced").isEqualTo(actual.getIsinvoiced()))
            .satisfies(e -> assertThat(e.getRefinvoiceid()).as("check refinvoiceid").isEqualTo(actual.getRefinvoiceid()))
            .satisfies(e -> assertThat(e.getLmu()).as("check lmu").isEqualTo(actual.getLmu()))
            .satisfies(e -> assertThat(e.getLmd()).as("check lmd").isEqualTo(actual.getLmd()))
            .satisfies(e -> assertThat(e.getIsfixed()).as("check isfixed").isEqualTo(actual.getIsfixed()))
            .satisfies(e -> assertThat(e.getIsactive()).as("check isactive").isEqualTo(actual.getIsactive()))
            .satisfies(e -> assertThat(e.getAdvancepayment()).as("check advancepayment").isEqualTo(actual.getAdvancepayment()))
            .satisfies(
                e ->
                    assertThat(e.getAdvancepaymentreturnamount())
                        .as("check advancepaymentreturnamount")
                        .isEqualTo(actual.getAdvancepaymentreturnamount())
            )
            .satisfies(
                e ->
                    assertThat(e.getAdvancepaymentreturndate())
                        .as("check advancepaymentreturndate")
                        .isEqualTo(actual.getAdvancepaymentreturndate())
            )
            .satisfies(e -> assertThat(e.getAdvancepaymentby()).as("check advancepaymentby").isEqualTo(actual.getAdvancepaymentby()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSalesorderUpdatableRelationshipsEquals(Salesorder expected, Salesorder actual) {}
}
