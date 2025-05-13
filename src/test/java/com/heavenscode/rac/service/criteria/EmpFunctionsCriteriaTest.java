package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmpFunctionsCriteriaTest {

    @Test
    void newEmpFunctionsCriteriaHasAllFiltersNullTest() {
        var empFunctionsCriteria = new EmpFunctionsCriteria();
        assertThat(empFunctionsCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void empFunctionsCriteriaFluentMethodsCreatesFiltersTest() {
        var empFunctionsCriteria = new EmpFunctionsCriteria();

        setAllFilters(empFunctionsCriteria);

        assertThat(empFunctionsCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void empFunctionsCriteriaCopyCreatesNullFilterTest() {
        var empFunctionsCriteria = new EmpFunctionsCriteria();
        var copy = empFunctionsCriteria.copy();

        assertThat(empFunctionsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(empFunctionsCriteria)
        );
    }

    @Test
    void empFunctionsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var empFunctionsCriteria = new EmpFunctionsCriteria();
        setAllFilters(empFunctionsCriteria);

        var copy = empFunctionsCriteria.copy();

        assertThat(empFunctionsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(empFunctionsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var empFunctionsCriteria = new EmpFunctionsCriteria();

        assertThat(empFunctionsCriteria).hasToString("EmpFunctionsCriteria{}");
    }

    private static void setAllFilters(EmpFunctionsCriteria empFunctionsCriteria) {
        empFunctionsCriteria.functionId();
        empFunctionsCriteria.functionName();
        empFunctionsCriteria.moduleId();
        empFunctionsCriteria.distinct();
    }

    private static Condition<EmpFunctionsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getFunctionId()) &&
                condition.apply(criteria.getFunctionName()) &&
                condition.apply(criteria.getModuleId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmpFunctionsCriteria> copyFiltersAre(
        EmpFunctionsCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getFunctionId(), copy.getFunctionId()) &&
                condition.apply(criteria.getFunctionName(), copy.getFunctionName()) &&
                condition.apply(criteria.getModuleId(), copy.getModuleId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
