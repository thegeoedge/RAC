package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SalesInvoiceLineBatchCriteriaTest {

    @Test
    void newSalesInvoiceLineBatchCriteriaHasAllFiltersNullTest() {
        var salesInvoiceLineBatchCriteria = new SalesInvoiceLineBatchCriteria();
        assertThat(salesInvoiceLineBatchCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void salesInvoiceLineBatchCriteriaFluentMethodsCreatesFiltersTest() {
        var salesInvoiceLineBatchCriteria = new SalesInvoiceLineBatchCriteria();

        setAllFilters(salesInvoiceLineBatchCriteria);

        assertThat(salesInvoiceLineBatchCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void salesInvoiceLineBatchCriteriaCopyCreatesNullFilterTest() {
        var salesInvoiceLineBatchCriteria = new SalesInvoiceLineBatchCriteria();
        var copy = salesInvoiceLineBatchCriteria.copy();

        assertThat(salesInvoiceLineBatchCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceLineBatchCriteria)
        );
    }

    @Test
    void salesInvoiceLineBatchCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var salesInvoiceLineBatchCriteria = new SalesInvoiceLineBatchCriteria();
        setAllFilters(salesInvoiceLineBatchCriteria);

        var copy = salesInvoiceLineBatchCriteria.copy();

        assertThat(salesInvoiceLineBatchCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceLineBatchCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var salesInvoiceLineBatchCriteria = new SalesInvoiceLineBatchCriteria();

        assertThat(salesInvoiceLineBatchCriteria).hasToString("SalesInvoiceLineBatchCriteria{}");
    }

    private static void setAllFilters(SalesInvoiceLineBatchCriteria salesInvoiceLineBatchCriteria) {
        salesInvoiceLineBatchCriteria.id();
        salesInvoiceLineBatchCriteria.lineId();
        salesInvoiceLineBatchCriteria.batchLineId();
        salesInvoiceLineBatchCriteria.itemId();
        salesInvoiceLineBatchCriteria.code();
        salesInvoiceLineBatchCriteria.batchId();
        salesInvoiceLineBatchCriteria.batchCode();
        salesInvoiceLineBatchCriteria.txDate();
        salesInvoiceLineBatchCriteria.manufactureDate();
        salesInvoiceLineBatchCriteria.expiredDate();
        salesInvoiceLineBatchCriteria.qty();
        salesInvoiceLineBatchCriteria.cost();
        salesInvoiceLineBatchCriteria.price();
        salesInvoiceLineBatchCriteria.notes();
        salesInvoiceLineBatchCriteria.lmu();
        salesInvoiceLineBatchCriteria.lmd();
        salesInvoiceLineBatchCriteria.nbt();
        salesInvoiceLineBatchCriteria.vat();
        salesInvoiceLineBatchCriteria.addedById();
        salesInvoiceLineBatchCriteria.distinct();
    }

    private static Condition<SalesInvoiceLineBatchCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLineId()) &&
                condition.apply(criteria.getBatchLineId()) &&
                condition.apply(criteria.getItemId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getBatchId()) &&
                condition.apply(criteria.getBatchCode()) &&
                condition.apply(criteria.getTxDate()) &&
                condition.apply(criteria.getManufactureDate()) &&
                condition.apply(criteria.getExpiredDate()) &&
                condition.apply(criteria.getQty()) &&
                condition.apply(criteria.getCost()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getNbt()) &&
                condition.apply(criteria.getVat()) &&
                condition.apply(criteria.getAddedById()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SalesInvoiceLineBatchCriteria> copyFiltersAre(
        SalesInvoiceLineBatchCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLineId(), copy.getLineId()) &&
                condition.apply(criteria.getBatchLineId(), copy.getBatchLineId()) &&
                condition.apply(criteria.getItemId(), copy.getItemId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getBatchId(), copy.getBatchId()) &&
                condition.apply(criteria.getBatchCode(), copy.getBatchCode()) &&
                condition.apply(criteria.getTxDate(), copy.getTxDate()) &&
                condition.apply(criteria.getManufactureDate(), copy.getManufactureDate()) &&
                condition.apply(criteria.getExpiredDate(), copy.getExpiredDate()) &&
                condition.apply(criteria.getQty(), copy.getQty()) &&
                condition.apply(criteria.getCost(), copy.getCost()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getNbt(), copy.getNbt()) &&
                condition.apply(criteria.getVat(), copy.getVat()) &&
                condition.apply(criteria.getAddedById(), copy.getAddedById()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
