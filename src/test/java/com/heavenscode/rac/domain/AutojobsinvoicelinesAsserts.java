package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AutojobsinvoicelinesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutojobsinvoicelinesAllPropertiesEquals(Autojobsinvoicelines expected, Autojobsinvoicelines actual) {
        assertAutojobsinvoicelinesAutoGeneratedPropertiesEquals(expected, actual);
        assertAutojobsinvoicelinesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutojobsinvoicelinesAllUpdatablePropertiesEquals(Autojobsinvoicelines expected, Autojobsinvoicelines actual) {
        assertAutojobsinvoicelinesUpdatableFieldsEquals(expected, actual);
        assertAutojobsinvoicelinesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutojobsinvoicelinesAutoGeneratedPropertiesEquals(Autojobsinvoicelines expected, Autojobsinvoicelines actual) {
        assertThat(expected)
            .as("Verify Autojobsinvoicelines auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutojobsinvoicelinesUpdatableFieldsEquals(Autojobsinvoicelines expected, Autojobsinvoicelines actual) {
        assertThat(expected)
            .as("Verify Autojobsinvoicelines relevant properties")
            .satisfies(e -> assertThat(e.getInvocieid()).as("check invocieid").isEqualTo(actual.getInvocieid()))
            .satisfies(e -> assertThat(e.getLineid()).as("check lineid").isEqualTo(actual.getLineid()))
            .satisfies(e -> assertThat(e.getItemid()).as("check itemid").isEqualTo(actual.getItemid()))
            .satisfies(e -> assertThat(e.getItemcode()).as("check itemcode").isEqualTo(actual.getItemcode()))
            .satisfies(e -> assertThat(e.getItemname()).as("check itemname").isEqualTo(actual.getItemname()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getUnitofmeasurement()).as("check unitofmeasurement").isEqualTo(actual.getUnitofmeasurement()))
            .satisfies(e -> assertThat(e.getQuantity()).as("check quantity").isEqualTo(actual.getQuantity()))
            .satisfies(e -> assertThat(e.getItemcost()).as("check itemcost").isEqualTo(actual.getItemcost()))
            .satisfies(e -> assertThat(e.getItemprice()).as("check itemprice").isEqualTo(actual.getItemprice()))
            .satisfies(e -> assertThat(e.getDiscount()).as("check discount").isEqualTo(actual.getDiscount()))
            .satisfies(e -> assertThat(e.getTax()).as("check tax").isEqualTo(actual.getTax()))
            .satisfies(e -> assertThat(e.getSellingprice()).as("check sellingprice").isEqualTo(actual.getSellingprice()))
            .satisfies(e -> assertThat(e.getLinetotal()).as("check linetotal").isEqualTo(actual.getLinetotal()))
            .satisfies(e -> assertThat(e.getLmu()).as("check lmu").isEqualTo(actual.getLmu()))
            .satisfies(e -> assertThat(e.getLmd()).as("check lmd").isEqualTo(actual.getLmd()))
            .satisfies(e -> assertThat(e.getNbt()).as("check nbt").isEqualTo(actual.getNbt()))
            .satisfies(e -> assertThat(e.getVat()).as("check vat").isEqualTo(actual.getVat()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAutojobsinvoicelinesUpdatableRelationshipsEquals(Autojobsinvoicelines expected, Autojobsinvoicelines actual) {
        // empty method
    }
}
