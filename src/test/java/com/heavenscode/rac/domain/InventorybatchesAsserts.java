package com.heavenscode.rac.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InventorybatchesAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventorybatchesAllPropertiesEquals(Inventorybatches expected, Inventorybatches actual) {
        assertInventorybatchesAutoGeneratedPropertiesEquals(expected, actual);
        assertInventorybatchesAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventorybatchesAllUpdatablePropertiesEquals(Inventorybatches expected, Inventorybatches actual) {
        assertInventorybatchesUpdatableFieldsEquals(expected, actual);
        assertInventorybatchesUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventorybatchesAutoGeneratedPropertiesEquals(Inventorybatches expected, Inventorybatches actual) {
        assertThat(expected)
            .as("Verify Inventorybatches auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventorybatchesUpdatableFieldsEquals(Inventorybatches expected, Inventorybatches actual) {
        assertThat(expected)
            .as("Verify Inventorybatches relevant properties")
            .satisfies(e -> assertThat(e.getItemid()).as("check itemid").isEqualTo(actual.getItemid()))
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getTxdate()).as("check txdate").isEqualTo(actual.getTxdate()))
            .satisfies(e -> assertThat(e.getCost()).as("check cost").isEqualTo(actual.getCost()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").isEqualTo(actual.getPrice()))
            .satisfies(e -> assertThat(e.getCostwithoutvat()).as("check costwithoutvat").isEqualTo(actual.getCostwithoutvat()))
            .satisfies(e -> assertThat(e.getPricewithoutvat()).as("check pricewithoutvat").isEqualTo(actual.getPricewithoutvat()))
            .satisfies(e -> assertThat(e.getNotes()).as("check notes").isEqualTo(actual.getNotes()))
            .satisfies(e -> assertThat(e.getLmu()).as("check lmu").isEqualTo(actual.getLmu()))
            .satisfies(e -> assertThat(e.getLmd()).as("check lmd").isEqualTo(actual.getLmd()))
            .satisfies(e -> assertThat(e.getLineid()).as("check lineid").isEqualTo(actual.getLineid()))
            .satisfies(e -> assertThat(e.getManufacturedate()).as("check manufacturedate").isEqualTo(actual.getManufacturedate()))
            .satisfies(e -> assertThat(e.getExpiredate()).as("check expiredate").isEqualTo(actual.getExpiredate()))
            .satisfies(e -> assertThat(e.getQuantity()).as("check quantity").isEqualTo(actual.getQuantity()))
            .satisfies(e -> assertThat(e.getAddeddate()).as("check addeddate").isEqualTo(actual.getAddeddate()))
            .satisfies(e -> assertThat(e.getCosttotal()).as("check costtotal").isEqualTo(actual.getCosttotal()))
            .satisfies(e -> assertThat(e.getReturnprice()).as("check returnprice").isEqualTo(actual.getReturnprice()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventorybatchesUpdatableRelationshipsEquals(Inventorybatches expected, Inventorybatches actual) {
        // empty method
    }
}
