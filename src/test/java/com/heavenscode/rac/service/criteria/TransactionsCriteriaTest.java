package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TransactionsCriteriaTest {

    @Test
    void newTransactionsCriteriaHasAllFiltersNullTest() {
        var transactionsCriteria = new TransactionsCriteria();
        assertThat(transactionsCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void transactionsCriteriaFluentMethodsCreatesFiltersTest() {
        var transactionsCriteria = new TransactionsCriteria();

        setAllFilters(transactionsCriteria);

        assertThat(transactionsCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void transactionsCriteriaCopyCreatesNullFilterTest() {
        var transactionsCriteria = new TransactionsCriteria();
        var copy = transactionsCriteria.copy();

        assertThat(transactionsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(transactionsCriteria)
        );
    }

    @Test
    void transactionsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var transactionsCriteria = new TransactionsCriteria();
        setAllFilters(transactionsCriteria);

        var copy = transactionsCriteria.copy();

        assertThat(transactionsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(transactionsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var transactionsCriteria = new TransactionsCriteria();

        assertThat(transactionsCriteria).hasToString("TransactionsCriteria{}");
    }

    private static void setAllFilters(TransactionsCriteria transactionsCriteria) {
        transactionsCriteria.id();
        transactionsCriteria.accountId();
        transactionsCriteria.accountCode();
        transactionsCriteria.debit();
        transactionsCriteria.credit();
        transactionsCriteria.date();
        transactionsCriteria.refDoc();
        transactionsCriteria.refId();
        transactionsCriteria.source();
        transactionsCriteria.paymentTermId();
        transactionsCriteria.paymentTermName();
        transactionsCriteria.lmu();
        transactionsCriteria.lmd();
        transactionsCriteria.distinct();
    }

    private static Condition<TransactionsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAccountId()) &&
                condition.apply(criteria.getAccountCode()) &&
                condition.apply(criteria.getDebit()) &&
                condition.apply(criteria.getCredit()) &&
                condition.apply(criteria.getDate()) &&
                condition.apply(criteria.getRefDoc()) &&
                condition.apply(criteria.getRefId()) &&
                condition.apply(criteria.getSource()) &&
                condition.apply(criteria.getPaymentTermId()) &&
                condition.apply(criteria.getPaymentTermName()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TransactionsCriteria> copyFiltersAre(
        TransactionsCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAccountId(), copy.getAccountId()) &&
                condition.apply(criteria.getAccountCode(), copy.getAccountCode()) &&
                condition.apply(criteria.getDebit(), copy.getDebit()) &&
                condition.apply(criteria.getCredit(), copy.getCredit()) &&
                condition.apply(criteria.getDate(), copy.getDate()) &&
                condition.apply(criteria.getRefDoc(), copy.getRefDoc()) &&
                condition.apply(criteria.getRefId(), copy.getRefId()) &&
                condition.apply(criteria.getSource(), copy.getSource()) &&
                condition.apply(criteria.getPaymentTermId(), copy.getPaymentTermId()) &&
                condition.apply(criteria.getPaymentTermName(), copy.getPaymentTermName()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
