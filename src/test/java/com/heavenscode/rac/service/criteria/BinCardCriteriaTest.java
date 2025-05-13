package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BinCardCriteriaTest {

    @Test
    void newBinCardCriteriaHasAllFiltersNullTest() {
        var binCardCriteria = new BinCardCriteria();
        assertThat(binCardCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void binCardCriteriaFluentMethodsCreatesFiltersTest() {
        var binCardCriteria = new BinCardCriteria();

        setAllFilters(binCardCriteria);

        assertThat(binCardCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void binCardCriteriaCopyCreatesNullFilterTest() {
        var binCardCriteria = new BinCardCriteria();
        var copy = binCardCriteria.copy();

        assertThat(binCardCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(binCardCriteria)
        );
    }

    @Test
    void binCardCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var binCardCriteria = new BinCardCriteria();
        setAllFilters(binCardCriteria);

        var copy = binCardCriteria.copy();

        assertThat(binCardCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(binCardCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var binCardCriteria = new BinCardCriteria();

        assertThat(binCardCriteria).hasToString("BinCardCriteria{}");
    }

    private static void setAllFilters(BinCardCriteria binCardCriteria) {
        binCardCriteria.id();
        binCardCriteria.itemID();
        binCardCriteria.itemCode();
        binCardCriteria.reference();
        binCardCriteria.txDate();
        binCardCriteria.qtyIn();
        binCardCriteria.qtyOut();
        binCardCriteria.price();
        binCardCriteria.lMU();
        binCardCriteria.lMD();
        binCardCriteria.referenceCode();
        binCardCriteria.recordDate();
        binCardCriteria.batchId();
        binCardCriteria.locationID();
        binCardCriteria.locationCode();
        binCardCriteria.opening();
        binCardCriteria.description();
        binCardCriteria.referenceDoc();
        binCardCriteria.distinct();
    }

    private static Condition<BinCardCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getItemID()) &&
                condition.apply(criteria.getItemCode()) &&
                condition.apply(criteria.getReference()) &&
                condition.apply(criteria.getTxDate()) &&
                condition.apply(criteria.getQtyIn()) &&
                condition.apply(criteria.getQtyOut()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getlMU()) &&
                condition.apply(criteria.getlMD()) &&
                condition.apply(criteria.getReferenceCode()) &&
                condition.apply(criteria.getRecordDate()) &&
                condition.apply(criteria.getBatchId()) &&
                condition.apply(criteria.getLocationID()) &&
                condition.apply(criteria.getLocationCode()) &&
                condition.apply(criteria.getOpening()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getReferenceDoc()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BinCardCriteria> copyFiltersAre(BinCardCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getItemID(), copy.getItemID()) &&
                condition.apply(criteria.getItemCode(), copy.getItemCode()) &&
                condition.apply(criteria.getReference(), copy.getReference()) &&
                condition.apply(criteria.getTxDate(), copy.getTxDate()) &&
                condition.apply(criteria.getQtyIn(), copy.getQtyIn()) &&
                condition.apply(criteria.getQtyOut(), copy.getQtyOut()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getlMU(), copy.getlMU()) &&
                condition.apply(criteria.getlMD(), copy.getlMD()) &&
                condition.apply(criteria.getReferenceCode(), copy.getReferenceCode()) &&
                condition.apply(criteria.getRecordDate(), copy.getRecordDate()) &&
                condition.apply(criteria.getBatchId(), copy.getBatchId()) &&
                condition.apply(criteria.getLocationID(), copy.getLocationID()) &&
                condition.apply(criteria.getLocationCode(), copy.getLocationCode()) &&
                condition.apply(criteria.getOpening(), copy.getOpening()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getReferenceDoc(), copy.getReferenceDoc()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
