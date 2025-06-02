package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Bankbranch} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.BankbranchResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bankbranches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankbranchCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bankcode;

    private StringFilter branchcode;

    private StringFilter branchname;

    private Boolean distinct;

    public BankbranchCriteria() {}

    public BankbranchCriteria(BankbranchCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.bankcode = other.optionalBankcode().map(StringFilter::copy).orElse(null);
        this.branchcode = other.optionalBranchcode().map(StringFilter::copy).orElse(null);
        this.branchname = other.optionalBranchname().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BankbranchCriteria copy() {
        return new BankbranchCriteria(this);
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

    public StringFilter getBankcode() {
        return bankcode;
    }

    public Optional<StringFilter> optionalBankcode() {
        return Optional.ofNullable(bankcode);
    }

    public StringFilter bankcode() {
        if (bankcode == null) {
            setBankcode(new StringFilter());
        }
        return bankcode;
    }

    public void setBankcode(StringFilter bankcode) {
        this.bankcode = bankcode;
    }

    public StringFilter getBranchcode() {
        return branchcode;
    }

    public Optional<StringFilter> optionalBranchcode() {
        return Optional.ofNullable(branchcode);
    }

    public StringFilter branchcode() {
        if (branchcode == null) {
            setBranchcode(new StringFilter());
        }
        return branchcode;
    }

    public void setBranchcode(StringFilter branchcode) {
        this.branchcode = branchcode;
    }

    public StringFilter getBranchname() {
        return branchname;
    }

    public Optional<StringFilter> optionalBranchname() {
        return Optional.ofNullable(branchname);
    }

    public StringFilter branchname() {
        if (branchname == null) {
            setBranchname(new StringFilter());
        }
        return branchname;
    }

    public void setBranchname(StringFilter branchname) {
        this.branchname = branchname;
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
        final BankbranchCriteria that = (BankbranchCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bankcode, that.bankcode) &&
            Objects.equals(branchcode, that.branchcode) &&
            Objects.equals(branchname, that.branchname) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankcode, branchcode, branchname, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankbranchCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalBankcode().map(f -> "bankcode=" + f + ", ").orElse("") +
            optionalBranchcode().map(f -> "branchcode=" + f + ", ").orElse("") +
            optionalBranchname().map(f -> "branchname=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
