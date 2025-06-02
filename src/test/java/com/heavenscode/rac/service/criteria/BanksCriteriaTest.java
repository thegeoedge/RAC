package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BanksCriteriaTest {

    @Test
    void newBanksCriteriaHasAllFiltersNullTest() {
        var banksCriteria = new BanksCriteria();
        assertThat(banksCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void banksCriteriaFluentMethodsCreatesFiltersTest() {
        var banksCriteria = new BanksCriteria();

        setAllFilters(banksCriteria);

        assertThat(banksCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void banksCriteriaCopyCreatesNullFilterTest() {
        var banksCriteria = new BanksCriteria();
        var copy = banksCriteria.copy();

        assertThat(banksCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(banksCriteria)
        );
    }

    @Test
    void banksCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var banksCriteria = new BanksCriteria();
        setAllFilters(banksCriteria);

        var copy = banksCriteria.copy();

        assertThat(banksCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(banksCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var banksCriteria = new BanksCriteria();

        assertThat(banksCriteria).hasToString("BanksCriteria{}");
    }

    private static void setAllFilters(BanksCriteria banksCriteria) {
        banksCriteria.id();
        banksCriteria.code();
        banksCriteria.name();
        banksCriteria.description();
        banksCriteria.lmu();
        banksCriteria.lmd();
        banksCriteria.distinct();
    }

    private static Condition<BanksCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BanksCriteria> copyFiltersAre(BanksCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
