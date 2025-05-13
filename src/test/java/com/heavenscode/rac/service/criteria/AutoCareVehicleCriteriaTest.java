package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutoCareVehicleCriteriaTest {

    @Test
    void newAutoCareVehicleCriteriaHasAllFiltersNullTest() {
        var autoCareVehicleCriteria = new AutoCareVehicleCriteria();
        assertThat(autoCareVehicleCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void autoCareVehicleCriteriaFluentMethodsCreatesFiltersTest() {
        var autoCareVehicleCriteria = new AutoCareVehicleCriteria();

        setAllFilters(autoCareVehicleCriteria);

        assertThat(autoCareVehicleCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void autoCareVehicleCriteriaCopyCreatesNullFilterTest() {
        var autoCareVehicleCriteria = new AutoCareVehicleCriteria();
        var copy = autoCareVehicleCriteria.copy();

        assertThat(autoCareVehicleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(autoCareVehicleCriteria)
        );
    }

    @Test
    void autoCareVehicleCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autoCareVehicleCriteria = new AutoCareVehicleCriteria();
        setAllFilters(autoCareVehicleCriteria);

        var copy = autoCareVehicleCriteria.copy();

        assertThat(autoCareVehicleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(autoCareVehicleCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autoCareVehicleCriteria = new AutoCareVehicleCriteria();

        assertThat(autoCareVehicleCriteria).hasToString("AutoCareVehicleCriteria{}");
    }

    private static void setAllFilters(AutoCareVehicleCriteria autoCareVehicleCriteria) {
        autoCareVehicleCriteria.id();
        autoCareVehicleCriteria.customerId();
        autoCareVehicleCriteria.customerName();
        autoCareVehicleCriteria.customerTel();
        autoCareVehicleCriteria.vehicleNumber();
        autoCareVehicleCriteria.brandId();
        autoCareVehicleCriteria.brandName();
        autoCareVehicleCriteria.model();
        autoCareVehicleCriteria.millage();
        autoCareVehicleCriteria.manufactureYear();
        autoCareVehicleCriteria.lastServiceDate();
        autoCareVehicleCriteria.nextServiceDate();
        autoCareVehicleCriteria.description();
        autoCareVehicleCriteria.lmu();
        autoCareVehicleCriteria.lmd();
        autoCareVehicleCriteria.distinct();
    }

    private static Condition<AutoCareVehicleCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCustomerId()) &&
                condition.apply(criteria.getCustomerName()) &&
                condition.apply(criteria.getCustomerTel()) &&
                condition.apply(criteria.getVehicleNumber()) &&
                condition.apply(criteria.getBrandId()) &&
                condition.apply(criteria.getBrandName()) &&
                condition.apply(criteria.getModel()) &&
                condition.apply(criteria.getMillage()) &&
                condition.apply(criteria.getManufactureYear()) &&
                condition.apply(criteria.getLastServiceDate()) &&
                condition.apply(criteria.getNextServiceDate()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AutoCareVehicleCriteria> copyFiltersAre(
        AutoCareVehicleCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCustomerId(), copy.getCustomerId()) &&
                condition.apply(criteria.getCustomerName(), copy.getCustomerName()) &&
                condition.apply(criteria.getCustomerTel(), copy.getCustomerTel()) &&
                condition.apply(criteria.getVehicleNumber(), copy.getVehicleNumber()) &&
                condition.apply(criteria.getBrandId(), copy.getBrandId()) &&
                condition.apply(criteria.getBrandName(), copy.getBrandName()) &&
                condition.apply(criteria.getModel(), copy.getModel()) &&
                condition.apply(criteria.getMillage(), copy.getMillage()) &&
                condition.apply(criteria.getManufactureYear(), copy.getManufactureYear()) &&
                condition.apply(criteria.getLastServiceDate(), copy.getLastServiceDate()) &&
                condition.apply(criteria.getNextServiceDate(), copy.getNextServiceDate()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
