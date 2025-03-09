package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InventorybatchesCriteriaTest {

    @Test
    void newInventorybatchesCriteriaHasAllFiltersNullTest() {
        var inventorybatchesCriteria = new InventorybatchesCriteria();
        assertThat(inventorybatchesCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void inventorybatchesCriteriaFluentMethodsCreatesFiltersTest() {
        var inventorybatchesCriteria = new InventorybatchesCriteria();

        setAllFilters(inventorybatchesCriteria);

        assertThat(inventorybatchesCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void inventorybatchesCriteriaCopyCreatesNullFilterTest() {
        var inventorybatchesCriteria = new InventorybatchesCriteria();
        var copy = inventorybatchesCriteria.copy();

        assertThat(inventorybatchesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(inventorybatchesCriteria)
        );
    }

    @Test
    void inventorybatchesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var inventorybatchesCriteria = new InventorybatchesCriteria();
        setAllFilters(inventorybatchesCriteria);

        var copy = inventorybatchesCriteria.copy();

        assertThat(inventorybatchesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(inventorybatchesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var inventorybatchesCriteria = new InventorybatchesCriteria();

        assertThat(inventorybatchesCriteria).hasToString("InventorybatchesCriteria{}");
    }

    private static void setAllFilters(InventorybatchesCriteria inventorybatchesCriteria) {
        inventorybatchesCriteria.id();
        inventorybatchesCriteria.itemid();
        inventorybatchesCriteria.code();
        inventorybatchesCriteria.txdate();
        inventorybatchesCriteria.cost();
        inventorybatchesCriteria.price();
        inventorybatchesCriteria.costwithoutvat();
        inventorybatchesCriteria.pricewithoutvat();
        inventorybatchesCriteria.notes();
        inventorybatchesCriteria.lmu();
        inventorybatchesCriteria.lmd();
        inventorybatchesCriteria.lineid();
        inventorybatchesCriteria.manufacturedate();
        inventorybatchesCriteria.expiredate();
        inventorybatchesCriteria.quantity();
        inventorybatchesCriteria.addeddate();
        inventorybatchesCriteria.costtotal();
        inventorybatchesCriteria.returnprice();
        inventorybatchesCriteria.distinct();
    }

    private static Condition<InventorybatchesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getItemid()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getTxdate()) &&
                condition.apply(criteria.getCost()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getCostwithoutvat()) &&
                condition.apply(criteria.getPricewithoutvat()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getLineid()) &&
                condition.apply(criteria.getManufacturedate()) &&
                condition.apply(criteria.getExpiredate()) &&
                condition.apply(criteria.getQuantity()) &&
                condition.apply(criteria.getAddeddate()) &&
                condition.apply(criteria.getCosttotal()) &&
                condition.apply(criteria.getReturnprice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InventorybatchesCriteria> copyFiltersAre(
        InventorybatchesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getItemid(), copy.getItemid()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getTxdate(), copy.getTxdate()) &&
                condition.apply(criteria.getCost(), copy.getCost()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getCostwithoutvat(), copy.getCostwithoutvat()) &&
                condition.apply(criteria.getPricewithoutvat(), copy.getPricewithoutvat()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getLineid(), copy.getLineid()) &&
                condition.apply(criteria.getManufacturedate(), copy.getManufacturedate()) &&
                condition.apply(criteria.getExpiredate(), copy.getExpiredate()) &&
                condition.apply(criteria.getQuantity(), copy.getQuantity()) &&
                condition.apply(criteria.getAddeddate(), copy.getAddeddate()) &&
                condition.apply(criteria.getCosttotal(), copy.getCosttotal()) &&
                condition.apply(criteria.getReturnprice(), copy.getReturnprice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
