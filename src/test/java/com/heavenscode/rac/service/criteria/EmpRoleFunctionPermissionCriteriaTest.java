package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmpRoleFunctionPermissionCriteriaTest {

    @Test
    void newEmpRoleFunctionPermissionCriteriaHasAllFiltersNullTest() {
        var empRoleFunctionPermissionCriteria = new EmpRoleFunctionPermissionCriteria();
        assertThat(empRoleFunctionPermissionCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void empRoleFunctionPermissionCriteriaFluentMethodsCreatesFiltersTest() {
        var empRoleFunctionPermissionCriteria = new EmpRoleFunctionPermissionCriteria();

        setAllFilters(empRoleFunctionPermissionCriteria);

        assertThat(empRoleFunctionPermissionCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void empRoleFunctionPermissionCriteriaCopyCreatesNullFilterTest() {
        var empRoleFunctionPermissionCriteria = new EmpRoleFunctionPermissionCriteria();
        var copy = empRoleFunctionPermissionCriteria.copy();

        assertThat(empRoleFunctionPermissionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(empRoleFunctionPermissionCriteria)
        );
    }

    @Test
    void empRoleFunctionPermissionCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var empRoleFunctionPermissionCriteria = new EmpRoleFunctionPermissionCriteria();
        setAllFilters(empRoleFunctionPermissionCriteria);

        var copy = empRoleFunctionPermissionCriteria.copy();

        assertThat(empRoleFunctionPermissionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(empRoleFunctionPermissionCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var empRoleFunctionPermissionCriteria = new EmpRoleFunctionPermissionCriteria();

        assertThat(empRoleFunctionPermissionCriteria).hasToString("EmpRoleFunctionPermissionCriteria{}");
    }

    private static void setAllFilters(EmpRoleFunctionPermissionCriteria empRoleFunctionPermissionCriteria) {
        empRoleFunctionPermissionCriteria.id();
        empRoleFunctionPermissionCriteria.roleId();
        empRoleFunctionPermissionCriteria.functionId();
        empRoleFunctionPermissionCriteria.distinct();
    }

    private static Condition<EmpRoleFunctionPermissionCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRoleId()) &&
                condition.apply(criteria.getFunctionId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmpRoleFunctionPermissionCriteria> copyFiltersAre(
        EmpRoleFunctionPermissionCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRoleId(), copy.getRoleId()) &&
                condition.apply(criteria.getFunctionId(), copy.getFunctionId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
