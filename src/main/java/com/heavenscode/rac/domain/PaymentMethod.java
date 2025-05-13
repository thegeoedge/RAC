package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A PaymentMethod.
 */
@Entity
@Table(name = "paymentmethods")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "paymentmethodname")
    private String paymentMethodName;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "companybankaccountid")
    private Integer companyBankAccountId;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "lmu")
    private Integer lmu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentMethod id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethodName() {
        return this.paymentMethodName;
    }

    public PaymentMethod paymentMethodName(String paymentMethodName) {
        this.setPaymentMethodName(paymentMethodName);
        return this;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Double getCommission() {
        return this.commission;
    }

    public PaymentMethod commission(Double commission) {
        this.setCommission(commission);
        return this;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Integer getCompanyBankAccountId() {
        return this.companyBankAccountId;
    }

    public PaymentMethod companyBankAccountId(Integer companyBankAccountId) {
        this.setCompanyBankAccountId(companyBankAccountId);
        return this;
    }

    public void setCompanyBankAccountId(Integer companyBankAccountId) {
        this.companyBankAccountId = companyBankAccountId;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public PaymentMethod lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public PaymentMethod lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethod)) {
            return false;
        }
        return getId() != null && getId().equals(((PaymentMethod) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethod{" +
            "id=" + getId() +
            ", paymentMethodName='" + getPaymentMethodName() + "'" +
            ", commission=" + getCommission() +
            ", companyBankAccountId=" + getCompanyBankAccountId() +
            ", lmd='" + getLmd() + "'" +
            ", lmu=" + getLmu() +
            "}";
    }
}
