package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmpRolesCriteriaTest {

    @Test
    void newEmpRolesCriteriaHasAllFiltersNullTest() {
        var empRolesCriteria = new EmpRolesCriteria();
        assertThat(empRolesCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void empRolesCriteriaFluentMethodsCreatesFiltersTest() {
        var empRolesCriteria = new EmpRolesCriteria();

        setAllFilters(empRolesCriteria);

        assertThat(empRolesCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void empRolesCriteriaCopyCreatesNullFilterTest() {
        var empRolesCriteria = new EmpRolesCriteria();
        var copy = empRolesCriteria.copy();

        assertThat(empRolesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(empRolesCriteria)
        );
    }

    @Test
    void empRolesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var empRolesCriteria = new EmpRolesCriteria();
        setAllFilters(empRolesCriteria);

        var copy = empRolesCriteria.copy();

        assertThat(empRolesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(empRolesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var empRolesCriteria = new EmpRolesCriteria();

        assertThat(empRolesCriteria).hasToString("EmpRolesCriteria{}");
    }

    private static void setAllFilters(EmpRolesCriteria empRolesCriteria) {
        empRolesCriteria.roleId();
        empRolesCriteria.roleName();
        empRolesCriteria.distinct();
    }

    private static Condition<EmpRolesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getRoleId()) && condition.apply(criteria.getRoleName()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmpRolesCriteria> copyFiltersAre(EmpRolesCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getRoleId(), copy.getRoleId()) &&
                condition.apply(criteria.getRoleName(), copy.getRoleName()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
