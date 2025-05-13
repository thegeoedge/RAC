package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Transactions} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.TransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransactionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter accountId;

    private StringFilter accountCode;

    private DoubleFilter debit;

    private DoubleFilter credit;

    private InstantFilter date;

    private StringFilter refDoc;

    private IntegerFilter refId;

    private StringFilter source;

    private IntegerFilter paymentTermId;

    private StringFilter paymentTermName;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private Boolean distinct;

    public TransactionsCriteria() {}

    public TransactionsCriteria(TransactionsCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.accountId = other.optionalAccountId().map(IntegerFilter::copy).orElse(null);
        this.accountCode = other.optionalAccountCode().map(StringFilter::copy).orElse(null);
        this.debit = other.optionalDebit().map(DoubleFilter::copy).orElse(null);
        this.credit = other.optionalCredit().map(DoubleFilter::copy).orElse(null);
        this.date = other.optionalDate().map(InstantFilter::copy).orElse(null);
        this.refDoc = other.optionalRefDoc().map(StringFilter::copy).orElse(null);
        this.refId = other.optionalRefId().map(IntegerFilter::copy).orElse(null);
        this.source = other.optionalSource().map(StringFilter::copy).orElse(null);
        this.paymentTermId = other.optionalPaymentTermId().map(IntegerFilter::copy).orElse(null);
        this.paymentTermName = other.optionalPaymentTermName().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TransactionsCriteria copy() {
        return new TransactionsCriteria(this);
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

    public IntegerFilter getAccountId() {
        return accountId;
    }

    public Optional<IntegerFilter> optionalAccountId() {
        return Optional.ofNullable(accountId);
    }

    public IntegerFilter accountId() {
        if (accountId == null) {
            setAccountId(new IntegerFilter());
        }
        return accountId;
    }

    public void setAccountId(IntegerFilter accountId) {
        this.accountId = accountId;
    }

    public StringFilter getAccountCode() {
        return accountCode;
    }

    public Optional<StringFilter> optionalAccountCode() {
        return Optional.ofNullable(accountCode);
    }

    public StringFilter accountCode() {
        if (accountCode == null) {
            setAccountCode(new StringFilter());
        }
        return accountCode;
    }

    public void setAccountCode(StringFilter accountCode) {
        this.accountCode = accountCode;
    }

    public DoubleFilter getDebit() {
        return debit;
    }

    public Optional<DoubleFilter> optionalDebit() {
        return Optional.ofNullable(debit);
    }

    public DoubleFilter debit() {
        if (debit == null) {
            setDebit(new DoubleFilter());
        }
        return debit;
    }

    public void setDebit(DoubleFilter debit) {
        this.debit = debit;
    }

    public DoubleFilter getCredit() {
        return credit;
    }

    public Optional<DoubleFilter> optionalCredit() {
        return Optional.ofNullable(credit);
    }

    public DoubleFilter credit() {
        if (credit == null) {
            setCredit(new DoubleFilter());
        }
        return credit;
    }

    public void setCredit(DoubleFilter credit) {
        this.credit = credit;
    }

    public InstantFilter getDate() {
        return date;
    }

    public Optional<InstantFilter> optionalDate() {
        return Optional.ofNullable(date);
    }

    public InstantFilter date() {
        if (date == null) {
            setDate(new InstantFilter());
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getRefDoc() {
        return refDoc;
    }

    public Optional<StringFilter> optionalRefDoc() {
        return Optional.ofNullable(refDoc);
    }

    public StringFilter refDoc() {
        if (refDoc == null) {
            setRefDoc(new StringFilter());
        }
        return refDoc;
    }

    public void setRefDoc(StringFilter refDoc) {
        this.refDoc = refDoc;
    }

    public IntegerFilter getRefId() {
        return refId;
    }

    public Optional<IntegerFilter> optionalRefId() {
        return Optional.ofNullable(refId);
    }

    public IntegerFilter refId() {
        if (refId == null) {
            setRefId(new IntegerFilter());
        }
        return refId;
    }

    public void setRefId(IntegerFilter refId) {
        this.refId = refId;
    }

    public StringFilter getSource() {
        return source;
    }

    public Optional<StringFilter> optionalSource() {
        return Optional.ofNullable(source);
    }

    public StringFilter source() {
        if (source == null) {
            setSource(new StringFilter());
        }
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public IntegerFilter getPaymentTermId() {
        return paymentTermId;
    }

    public Optional<IntegerFilter> optionalPaymentTermId() {
        return Optional.ofNullable(paymentTermId);
    }

    public IntegerFilter paymentTermId() {
        if (paymentTermId == null) {
            setPaymentTermId(new IntegerFilter());
        }
        return paymentTermId;
    }

    public void setPaymentTermId(IntegerFilter paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public StringFilter getPaymentTermName() {
        return paymentTermName;
    }

    public Optional<StringFilter> optionalPaymentTermName() {
        return Optional.ofNullable(paymentTermName);
    }

    public StringFilter paymentTermName() {
        if (paymentTermName == null) {
            setPaymentTermName(new StringFilter());
        }
        return paymentTermName;
    }

    public void setPaymentTermName(StringFilter paymentTermName) {
        this.paymentTermName = paymentTermName;
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
        final TransactionsCriteria that = (TransactionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(accountCode, that.accountCode) &&
            Objects.equals(debit, that.debit) &&
            Objects.equals(credit, that.credit) &&
            Objects.equals(date, that.date) &&
            Objects.equals(refDoc, that.refDoc) &&
            Objects.equals(refId, that.refId) &&
            Objects.equals(source, that.source) &&
            Objects.equals(paymentTermId, that.paymentTermId) &&
            Objects.equals(paymentTermName, that.paymentTermName) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            accountId,
            accountCode,
            debit,
            credit,
            date,
            refDoc,
            refId,
            source,
            paymentTermId,
            paymentTermName,
            lmu,
            lmd,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionsCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAccountId().map(f -> "accountId=" + f + ", ").orElse("") +
            optionalAccountCode().map(f -> "accountCode=" + f + ", ").orElse("") +
            optionalDebit().map(f -> "debit=" + f + ", ").orElse("") +
            optionalCredit().map(f -> "credit=" + f + ", ").orElse("") +
            optionalDate().map(f -> "date=" + f + ", ").orElse("") +
            optionalRefDoc().map(f -> "refDoc=" + f + ", ").orElse("") +
            optionalRefId().map(f -> "refId=" + f + ", ").orElse("") +
            optionalSource().map(f -> "source=" + f + ", ").orElse("") +
            optionalPaymentTermId().map(f -> "paymentTermId=" + f + ", ").orElse("") +
            optionalPaymentTermName().map(f -> "paymentTermName=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
