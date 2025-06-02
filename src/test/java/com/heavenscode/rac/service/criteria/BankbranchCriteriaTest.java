package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BankbranchCriteriaTest {

    @Test
    void newBankbranchCriteriaHasAllFiltersNullTest() {
        var bankbranchCriteria = new BankbranchCriteria();
        assertThat(bankbranchCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void bankbranchCriteriaFluentMethodsCreatesFiltersTest() {
        var bankbranchCriteria = new BankbranchCriteria();

        setAllFilters(bankbranchCriteria);

        assertThat(bankbranchCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void bankbranchCriteriaCopyCreatesNullFilterTest() {
        var bankbranchCriteria = new BankbranchCriteria();
        var copy = bankbranchCriteria.copy();

        assertThat(bankbranchCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(bankbranchCriteria)
        );
    }

    @Test
    void bankbranchCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var bankbranchCriteria = new BankbranchCriteria();
        setAllFilters(bankbranchCriteria);

        var copy = bankbranchCriteria.copy();

        assertThat(bankbranchCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(bankbranchCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var bankbranchCriteria = new BankbranchCriteria();

        assertThat(bankbranchCriteria).hasToString("BankbranchCriteria{}");
    }

    private static void setAllFilters(BankbranchCriteria bankbranchCriteria) {
        bankbranchCriteria.id();
        bankbranchCriteria.bankcode();
        bankbranchCriteria.branchcode();
        bankbranchCriteria.branchname();
        bankbranchCriteria.distinct();
    }

    private static Condition<BankbranchCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getBankcode()) &&
                condition.apply(criteria.getBranchcode()) &&
                condition.apply(criteria.getBranchname()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BankbranchCriteria> copyFiltersAre(BankbranchCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getBankcode(), copy.getBankcode()) &&
                condition.apply(criteria.getBranchcode(), copy.getBranchcode()) &&
                condition.apply(criteria.getBranchname(), copy.getBranchname()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
