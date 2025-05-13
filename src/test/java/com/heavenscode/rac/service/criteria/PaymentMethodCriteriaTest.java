package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PaymentMethodCriteriaTest {

    @Test
    void newPaymentMethodCriteriaHasAllFiltersNullTest() {
        var paymentMethodCriteria = new PaymentMethodCriteria();
        assertThat(paymentMethodCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void paymentMethodCriteriaFluentMethodsCreatesFiltersTest() {
        var paymentMethodCriteria = new PaymentMethodCriteria();

        setAllFilters(paymentMethodCriteria);

        assertThat(paymentMethodCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void paymentMethodCriteriaCopyCreatesNullFilterTest() {
        var paymentMethodCriteria = new PaymentMethodCriteria();
        var copy = paymentMethodCriteria.copy();

        assertThat(paymentMethodCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(paymentMethodCriteria)
        );
    }

    @Test
    void paymentMethodCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var paymentMethodCriteria = new PaymentMethodCriteria();
        setAllFilters(paymentMethodCriteria);

        var copy = paymentMethodCriteria.copy();

        assertThat(paymentMethodCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(paymentMethodCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var paymentMethodCriteria = new PaymentMethodCriteria();

        assertThat(paymentMethodCriteria).hasToString("PaymentMethodCriteria{}");
    }

    private static void setAllFilters(PaymentMethodCriteria paymentMethodCriteria) {
        paymentMethodCriteria.id();
        paymentMethodCriteria.paymentMethodName();
        paymentMethodCriteria.commission();
        paymentMethodCriteria.companyBankAccountId();
        paymentMethodCriteria.lmd();
        paymentMethodCriteria.lmu();
        paymentMethodCriteria.distinct();
    }

    private static Condition<PaymentMethodCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPaymentMethodName()) &&
                condition.apply(criteria.getCommission()) &&
                condition.apply(criteria.getCompanyBankAccountId()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PaymentMethodCriteria> copyFiltersAre(
        PaymentMethodCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPaymentMethodName(), copy.getPaymentMethodName()) &&
                condition.apply(criteria.getCommission(), copy.getCommission()) &&
                condition.apply(criteria.getCompanyBankAccountId(), copy.getCompanyBankAccountId()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
