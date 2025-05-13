package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.PaymentMethod} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.PaymentMethodResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-methods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentMethodCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentMethodName;

    private DoubleFilter commission;

    private IntegerFilter companyBankAccountId;

    private InstantFilter lmd;

    private IntegerFilter lmu;

    private Boolean distinct;

    public PaymentMethodCriteria() {}

    public PaymentMethodCriteria(PaymentMethodCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.paymentMethodName = other.optionalPaymentMethodName().map(StringFilter::copy).orElse(null);
        this.commission = other.optionalCommission().map(DoubleFilter::copy).orElse(null);
        this.companyBankAccountId = other.optionalCompanyBankAccountId().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PaymentMethodCriteria copy() {
        return new PaymentMethodCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPaymentMethodName() {
        return paymentMethodName;
    }

    public Optional<StringFilter> optionalPaymentMethodName() {
        return Optional.ofNullable(paymentMethodName);
    }

    public StringFilter paymentMethodName() {
        if (paymentMethodName == null) {
            setPaymentMethodName(new StringFilter());
        }
        return paymentMethodName;
    }

    public void setPaymentMethodName(StringFilter paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public DoubleFilter getCommission() {
        return commission;
    }

    public Optional<DoubleFilter> optionalCommission() {
        return Optional.ofNullable(commission);
    }

    public DoubleFilter commission() {
        if (commission == null) {
            setCommission(new DoubleFilter());
        }
        return commission;
    }

    public void setCommission(DoubleFilter commission) {
        this.commission = commission;
    }

    public IntegerFilter getCompanyBankAccountId() {
        return companyBankAccountId;
    }

    public Optional<IntegerFilter> optionalCompanyBankAccountId() {
        return Optional.ofNullable(companyBankAccountId);
    }

    public IntegerFilter companyBankAccountId() {
        if (companyBankAccountId == null) {
            setCompanyBankAccountId(new IntegerFilter());
        }
        return companyBankAccountId;
    }

    public void setCompanyBankAccountId(IntegerFilter companyBankAccountId) {
        this.companyBankAccountId = companyBankAccountId;
    }

    public InstantFilter getLmd() {
        return lmd;
    }

    public Optional<InstantFilter> optionalLmd() {
        return Optional.ofNullable(lmd);
    }

    public InstantFilter lmd() {
        if (lmd == null) {
            setLmd(new InstantFilter());
        }
        return lmd;
    }

    public void setLmd(InstantFilter lmd) {
        this.lmd = lmd;
    }

    public IntegerFilter getLmu() {
        return lmu;
    }

    public Optional<IntegerFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public IntegerFilter lmu() {
        if (lmu == null) {
            setLmu(new IntegerFilter());
        }
        return lmu;
    }

    public void setLmu(IntegerFilter lmu) {
        this.lmu = lmu;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentMethodCriteria that = (PaymentMethodCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(paymentMethodName, that.paymentMethodName) &&
            Objects.equals(commission, that.commission) &&
            Objects.equals(companyBankAccountId, that.companyBankAccountId) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentMethodName, commission, companyBankAccountId, lmd, lmu, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethodCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPaymentMethodName().map(f -> "paymentMethodName=" + f + ", ").orElse("") +
            optionalCommission().map(f -> "commission=" + f + ", ").orElse("") +
            optionalCompanyBankAccountId().map(f -> "companyBankAccountId=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
